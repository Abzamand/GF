package id.co.qualitas.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import id.co.qualitas.component.ConsParam;
import id.co.qualitas.component.Utils;
import id.co.qualitas.dao.BaseDao;
import id.co.qualitas.dao.interfaces.TransferPostingConfirmDao;
import id.co.qualitas.dao.interfaces.TransferPostingDao;
import id.co.qualitas.domain.request.TransferPostingConfirmDetailRequest;
import id.co.qualitas.domain.request.TransferPostingConfirmHeaderRequest;
import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.response.WSMessage;

@Repository
@Transactional
public class TransferPostingConfirmDaoImpl extends BaseDao implements TransferPostingConfirmDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	TransferPostingDao tPostingDao;

	@Override
	public WSMessage confirm(TransferPostingConfirmRequest request) {
		WSMessage result = new WSMessage();
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;
		int idHeader = 0;

		try {
			tx = session.beginTransaction();

			TransferPostingConfirmHeaderRequest header = new TransferPostingConfirmHeaderRequest();
			header = request.getHeader();

			String queryCreateHeader = "INSERT INTO tp_confirm_header "
					+ "(date, doc_no, doc_date, doc_type, doc_header, material_doc, id_sloc, id_plant, status, id_vendor, "
					+ "created_by, created_date, enabled, deleted) " 
					+ "OUTPUT Inserted.id "
					+ "VALUES (:date, :docNo, :docDate, :docType, :docHeader, :materialDoc, :idSloc, :idPlant, 'Completed', :idVendor, "
					+ ":createdBy, GETDATE(), 1, 0) ";

			idHeader = (int) session.createSQLQuery(queryCreateHeader)
					.setDate("date", header.getDate())
					.setString("docNo", header.getDocNo())
					.setString("docDate", header.getDocDate())
					.setString("docType", header.getDocType())
					.setString("docHeader", header.getDocHeader())
					.setString("materialDoc", header.getMaterialDoc())
					.setString("idSloc", header.getIdSloc())
					.setString("idPlant", header.getIdPlant())
					.setString("createdBy", request.getCreatedBy())
					.setString("idVendor", header.getIdVendor())
					.uniqueResult();

			String queryCreateDetail = "INSERT INTO tp_confirm_detail "
					+ "(id_tpconfirm_header ,item_no ,doc_no ,doc_item_no ,material_no ,material_desc ,qty ,uom ,"
					+ "batch_no ,notes, created_by, created_date, enabled, deleted, qty_oem, uom_oem) "
					+ "VALUES (:idHeader, :itemNo, :docNo, :docItemNo, :materialNo, :materialDesc, :qty, :uom, "
					+ ":batchNo, :notes, :createdBy, GETDATE(), 1, 0, :qtyOem, :uomOem ) ";
			
			String message = "";

			for (int i = 0; i < request.getListDetail().size(); i++) {
				TransferPostingConfirmDetailRequest detail = new TransferPostingConfirmDetailRequest();
				detail = request.getListDetail().get(i);
				

				BigDecimal qtySap = BigDecimal.ZERO;
				BigDecimal qtyOem = BigDecimal.ZERO;
				
				String uomSap = null;
				String uomOem = null;
				if(detail.isOemConversion()) {
//					qtySap = detail.getBaseQtyOem().multiply(detail.getMultiplier());
					qtySap = detail.getStockSap();
					uomSap = detail.getUomSap();
					
					qtyOem = detail.getBaseQtyOem();
					uomOem = detail.getUomOem();
				}else {
					qtySap = detail.getBaseQtyOem();
					uomSap = detail.getUomSap();
				}

				
				session.createSQLQuery(queryCreateDetail)
						.setInteger("idHeader", idHeader)
						.setInteger("itemNo", i+1)
						.setString("docNo", detail.getDocNo())
						.setString("docItemNo", detail.getDocItemNo())
						.setString("materialNo", detail.getMaterialNo())
						.setString("materialDesc", detail.getMaterialDesc())
						.setBigDecimal("qty", qtySap)
						.setString("uom", uomSap)
						.setString("batchNo", detail.getBatchNo())
						.setString("notes", detail.getNotes())
						.setString("createdBy", request.getCreatedBy())
						.setBigDecimal("qtyOem", qtyOem)
						.setString("uomOem", uomOem)
						.executeUpdate();
			}

			result.setIdMessage(1);
			result.setMessage("Transfer posting confirm created");

			tx.commit();
		} catch (Exception e) {
			tx.rollback();

			result.setIdMessage(0);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> getListHeaderForSync() {
		String query = "SELECT id, date as pstngDate, doc_date as docDate, doc_header as headerTxt, doc_type as docType, id_vendor as vendor, doc_no as refDocNo "
				+ "FROM tp_confirm_header " 
				+ "WHERE enabled = 1 AND deleted = 0 AND stockconfirm_no_sap is null AND "
				+ "(doc_type = '541' AND status != 'FAILED')"
				+ "OR"
				+ "(doc_type = '315' AND status like '%%')";

		Session session = sessionFactory.getCurrentSession();
		return session.createSQLQuery(query)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getListDetailBy(String idHeader) {
		String query = "SELECT d.item_no as detailItemNo, d.doc_no as poNumber, doc_item_no as poItem, material_no as material, "
				+ "qty as entryQnt, uom as entryUom, "
				+ "batch_no as batch, h.id_sloc as stgeLoc, h.id_plant as plant "
				+ "FROM tp_confirm_detail d LEFT JOIN tp_confirm_header h ON d.id_tpconfirm_header = h.id "
				+ "WHERE d.enabled = 1 AND d.deleted = 0 AND id_tpconfirm_header = :idHeader";

		Session session = sessionFactory.getCurrentSession();
		return session.createSQLQuery(query)
				.setString("idHeader", idHeader)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.list();
	}
	
	@Override
	public WSMessage doUpdateAfter1stStep(String idHeader, String grNoSap, String infoSync, String status, String docType) {
		String query = "UPDATE tp_confirm_header "
				+ "SET tpconfirm_no_sap = :grNoSap, info_sync = :infoSync, status = :status, updated_by='SYSTEM', updated_date=GETDATE(), doc_type = :docType "
				+ "WHERE id=:idHeader ";

		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;
		
		WSMessage message = new WSMessage();
		
		try {
			tx = session.beginTransaction();
			session.createSQLQuery(query)
				.setString("idHeader", idHeader)
				.setString("grNoSap", grNoSap)
				.setString("infoSync", infoSync)
				.setString("status", status)
				.setString("docType", docType)
				.executeUpdate();

			tx.commit();
			
			message.setIdMessage(1);
			message.setMessage("Success update table tp_confirm_header after 1st step");
		} catch (Exception e) {
			// TODO: handle exception
			if (tx != null) {
				tx.rollback();
			}

			message.setIdMessage(0);
			message.setMessage(e.getMessage());
		}

		return message;
	}

	@Override
	public WSMessage doUpdateAfter2ndStep(String idHeader, String stockConfirmNoSap, String infoSync, String status) {

		String query = "UPDATE tp_confirm_header "
				+ "SET stockconfirm_no_sap = :stockConfirmNoSap, info_sync = :infoSync, status = :status, updated_by='SYSTEM', updated_date=GETDATE() "
				+ "WHERE id=:idHeader ";

		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;
		
		WSMessage message = new WSMessage();
		
		try {
			tx = session.beginTransaction();
			session.createSQLQuery(query)
				.setString("idHeader", idHeader)
				.setString("stockConfirmNoSap", stockConfirmNoSap)
				.setString("infoSync", infoSync)
				.setString("status", status)
				.executeUpdate();

			tx.commit();
			
			message.setIdMessage(1);
			message.setMessage("Success update table tp_confirm_header after 2nd step");
		} catch (Exception e) {
			// TODO: handle exception
			if (tx != null) {
				tx.rollback();
			}

			message.setIdMessage(0);
			message.setMessage(e.getMessage());
		}

		return message;
	}
	
	@Override
	public WSMessage updateFieldsAfterSynced(String idHeader, String infoSync, String status) {
		String query = "UPDATE tp_confirm_header "
				+ "SET info_sync = :infoSync, status = :status, updated_by='SYSTEM', updated_date=GETDATE() "
				+ "WHERE id=:idHeader ";

		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;
		
		WSMessage message = new WSMessage();
		
		try {
			tx = session.beginTransaction();
			session.createSQLQuery(query)
				.setString("idHeader", idHeader)
				.setString("infoSync", infoSync)
				.setString("status", status)
				.executeUpdate();

			tx.commit();
			
			message.setIdMessage(1);
			message.setMessage("Success update table tp_confirm_header");
		} catch (Exception e) {
			// TODO: handle exception
			if (tx != null) {
				tx.rollback();
			}

			message.setIdMessage(0);
			message.setMessage(e.getMessage());
		}

		return message;
	}
	
	//TODO Tp sender doc no
	//TODO vendor material no & batch no 
	//TODO TP confirm doc no dan by item no (dari sap mngkin *doc item no), liat detail dlu baru hilangin header
	@Override
	public boolean isVendorStockConfirmed(String materialNo, String batchNo) {
//		String query = "SELECT top 1 material_no "
//				+ "FROM tp_confirm_detail "
//				+ "WHERE material_no = :materialNo AND "
//				+ "batch_no = :batchNo "
//				+ "AND enabled = 1 "
//				+ "AND deleted = 0;";
		
		String query = 
				  " select a.id from ( "
				+ "		select th.id, td.material_no, td.batch_no, th.stockconfirm_no_sap, th.status "
				+ "		from tp_confirm_header th "
				+ "		left join tp_confirm_detail td on td.id_tpconfirm_header = th.id "
				+ "		where td.material_no = :materialNo "
				+ "		and td.batch_no = :batchNo "
				+ "		and th.doc_no is null "
				+ " )a "
				+ " where a.stockconfirm_no_sap IS NULL AND a.status != 'Failed' ";

		Session session = sessionFactory.getCurrentSession();
		
		List<String> result = (List<String>) session.createSQLQuery(query)
				.setString("materialNo", materialNo)
				.setString("batchNo", batchNo)
				.list();
		
		if(result != null) {
			if(result.isEmpty()) {

				return false;
			}else {
				return true;
			}
		}else {
			return false;
		}
	}

}
