package id.co.qualitas.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import id.co.qualitas.component.Cons;
import id.co.qualitas.component.ConsParam;
import id.co.qualitas.component.Utils;
import id.co.qualitas.dao.BaseDao;
import id.co.qualitas.dao.interfaces.TransferPostingConfirmDao;
import id.co.qualitas.dao.interfaces.TransferPostingDao;
import id.co.qualitas.domain.request.TransferPostingConfirmDetailRequest;
import id.co.qualitas.domain.request.TransferPostingRequest;
import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.request.TransferPostingDetail;
import id.co.qualitas.domain.request.TransferPostingHeader;
import id.co.qualitas.domain.request.TransferPostingRequest;
import id.co.qualitas.domain.response.DOHistoryResponse;
import id.co.qualitas.domain.response.TransferPostingResponse;
import id.co.qualitas.domain.response.WSMessage;

@Repository
@Transactional
public class TransferPostingDaoImpl extends BaseDao implements TransferPostingDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public WSMessage create(TransferPostingRequest request) {
		WSMessage message = new WSMessage();
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;
		int idHeader = 0;

		try {
			tx = session.beginTransaction();

			TransferPostingHeader header = new TransferPostingHeader();
			header = request.getHeader();

			String queryCreateHeader = "INSERT INTO tp_header "
					+ "(tp_no_sap, date, doc_no, doc_date, doc_header, oem_from, oem_to, id_sloc, "
					+ "id_plant, status, info_sync, created_by, created_date, enabled, deleted) " 
					+ "OUTPUT Inserted.id "
					+ "VALUES (:tpNoSap, :date, :docNo, :docDate, :docHeader, :oemFrom, :oemTo, "
					+ ":idSloc, :idPlant, 'On Progress', :infoSync, :createdBy, "
					+ "GETDATE(), 1, 0) ";

			idHeader = (int) session.createSQLQuery(queryCreateHeader)
					.setString("tpNoSap", header.getTpNoSap())
					.setDate("date", header.getDate())
					.setString("docNo", header.getDocNo())
					.setString("docDate", header.getDocDate())
					.setString("oemFrom", header.getOemFrom())
					.setString("oemTo", header.getOemTo())
					.setString("idSloc", header.getIdSloc())
					.setString("idPlant", header.getIdPlant())
					.setString("infoSync", header.getInfoSync())
					.setString("docHeader", header.getDocHeader())
					.setString("createdBy", request.getCreatedBy())
					.uniqueResult();

			String queryCreateDetail = "INSERT INTO tp_detail "
					+ "(id_tp_header, item_no, material_no, material_desc, order_qty, tp_qty, "
					+ "uom, batch_no, exp_date, doc_no, doc_item_no, created_by, created_date, enabled, deleted, qty_oem, uom_oem) "
					+ "VALUES (:idTpHeader, :itemNo, :materialNo, :materialDesc, :orderQty, "
					+ ":tpQty, :uom, :batchNo, :expDate, :docNo, :docItemNo, :createdBy, GETDATE(), 1, 0, :qtyOem, :uomOem ) ";
			
			

			for (int i = 0; i < request.getListDetail().size(); i++) {
				TransferPostingDetail detail = new TransferPostingDetail();
				detail = request.getListDetail().get(i);

				BigDecimal qtySap = BigDecimal.ZERO;
				BigDecimal qtyOem = BigDecimal.ZERO;
				
				String uomSap = null;
				String uomOem = null;
				if(detail.isOemConversion()) {
					qtySap = detail.getBaseQtyOem().multiply(detail.getMultiplier());
					uomSap = detail.getUomSap();
					
					qtyOem = detail.getBaseQtyOem();
					uomOem = detail.getUom();
				}else {
					qtySap = detail.getBaseQtyOem();
					uomSap = detail.getUomSap();
				}

				session.createSQLQuery(queryCreateDetail)
						.setInteger("idTpHeader", idHeader)
						.setInteger("itemNo", i+1)
						.setString("materialNo", detail.getMaterialNo())
						.setString("materialDesc", detail.getMaterialDesc())
						.setBigDecimal("orderQty", qtySap)
						.setBigDecimal("tpQty", detail.getTpQty())
						.setString("uom", uomSap)
						.setString("batchNo", detail.getBatchNo())
						.setDate("expDate", detail.getExpDate())
						.setString("docNo", detail.getDocNo())
						.setString("docItemNo", detail.getDocItemNo())
						.setString("createdBy", request.getCreatedBy())
						.setBigDecimal("qtyOem", qtyOem)
						.setString("uomOem", uomOem)
						.executeUpdate();
			}

			message.setIdMessage(1);
			message.setMessage("Transfer posting created");

			tx.commit();
		} catch (Exception e) {
			tx.rollback();

			message.setIdMessage(0);
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isDocNoCreated(String docNo, String idSloc, String idPlant, String status) {
		
		/*	
		 * String query = "SELECT top 1 doc_no " + "FROM tp_header " +
		 * "WHERE doc_no = :docNo " + "AND oem_to = :idSloc " +
		 * "AND id_Plant = :idPlant " +
		 * "AND created_date BETWEEN CAST(CAST(GETDATE() AS DATE) AS DATETIME) AND DATEADD(MONTH, 1, GETDATE()) "
		 * + "AND status LIKE :status " + "AND enabled = 1 " + "AND deleted = 0;";
		 */
		String query = "SELECT top 1 doc_no "
				+ "FROM tp_header "
				+ "WHERE doc_no = :docNo "
				+ "AND enabled = 1 "
				+ "AND deleted = 0;";

		Session session = sessionFactory.getCurrentSession();
		
		String result = (String) session.createSQLQuery(query)
				.setString("docNo", docNo)
				.uniqueResult();
		
		if(result != null) {
			return true;
		}else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean isDocNoCreatedFromTpSender(String docNo, String idSloc, String idPlant, String status) {
		
		
		String query = "SELECT top 1 doc_no " + "FROM tp_header " +
		"WHERE doc_no = :docNo " + "AND oem_to = :idSloc " +
		"AND id_Plant = :idPlant " +
		"AND created_date BETWEEN CAST(CAST(GETDATE() AS DATE) AS DATETIME) AND DATEADD(MONTH, 1, GETDATE()) "
		+ "AND status LIKE :status " + "AND enabled = 1 " + "AND deleted = 0;";
		
		

		Session session = sessionFactory.getCurrentSession();
		
		String result = (String) session.createSQLQuery(query)
				.setString("docNo", docNo)
				.setString("idSloc", idSloc)
				.setString("idPlant", idPlant)
				.setString("status", status)
				.uniqueResult();
		
		if(result != null) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public boolean isDocNoAndDocItemNoCreated(String docNo, int docItemNo) {
		
		String query = "SELECT TOP 1 h.doc_no "
				+ "FROM tp_confirm_header h "
				+ "LEFT JOIN tp_confirm_detail d ON h.id = d.id_tpconfirm_header "
				+ "WHERE h.doc_no = :docNo AND d.doc_item_no = :docItemNo ";

		Session session = sessionFactory.getCurrentSession();
		
		String result = (String) session.createSQLQuery(query)
				.setString("docNo", docNo)
				.setInteger("docItemNo", docItemNo)
				.uniqueResult();
		
		if(result != null) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public boolean isDocNoAndDocItemNoCreatedTpSender(String docNo, int docItemNo) {
		
		String query = "SELECT TOP 1 h.doc_no "
				+ "FROM tp_header h "
				+ "LEFT JOIN tp_detail d ON h.id = d.id_tp_header "
				+ "WHERE h.doc_no = :docNo AND d.doc_item_no = :docItemNo ";

		Session session = sessionFactory.getCurrentSession();
		
		String result = (String) session.createSQLQuery(query)
				.setString("docNo", docNo)
				.setInteger("docItemNo", docItemNo)
				.uniqueResult();
		
		if(result != null) {
			return true;
		}else {
			return false;
		}
	}
	
	//TODO bandingin kalo detail di tp confirm detail uda sama kayak di tp detail
	@Override
	public boolean isAllDetailAreConfirmed(String docNo) {
		String query1 = "SELECT COUNT(*) "
				+ "FROM tp_confirm_detail "
				+ "WHERE doc_no = :docNo ";

		Session session = sessionFactory.getCurrentSession();
		
		int result = (int) session.createSQLQuery(query1)
				.setString("docNo", docNo)
				.uniqueResult();
		
		String query2 = "SELECT COUNT(*) "
				+ "FROM tp_detail "
				+ "WHERE doc_no = :docNo ";

		Session session2 = sessionFactory.getCurrentSession();
		
		int result2 = (int) session2.createSQLQuery(query2)
				.setString("docNo", docNo)
				.uniqueResult();
		
		if(result == result2) {
			return true;
		}else {
			return false;
		}
	}
	

	
//	public List<TransferPostingResponse> getHeadersForEmail(Date dateFrom, Date dateTo, String docType, String idSloc, String idPlant){
//		
//		String query = "SELECT doh.id as id, "
//				+ "	do_no_sap as doNoSap, "
//				+ "	date, "
//				+ "	doh.id_sloc as idSloc, "
//				+ "	doh.id_plant as idPlant,"
//				+ "	s.plant_name as plantName,"
//				+ "	destination, "
//				+ "	doc_no as docNo, "
//				+ "	po_no as poNo, "
//				+ "	doc_date as docDate, "
//				+ "	status, "
//				+ "	pod_status as podStatus, "
//				+ "	pod_status_desc as podStatusDesc, "
//				+ "	destination_name as destinationName, "
//				+ "	destination_name2 as destinationName2, "
//				+ "	destination_phone as destinationPhone, "
//				+ " destination_email as destinationEmail, "
//				+ "	notes, "
//				+ "	shipment_date as shipmentDate, "
//				+ "	v.address as senderAddress, "
//				+ "	v.phone as senderPhone, "
//				+ "	doh.plate_no as plateNo, "
//				+ "	uom_weight as uow, "
//				+ "	uom_volume as uov, "
//				+ "	estimated_departure_date as estimatedDepartureDate, "
//				+ "	arrival_date as arrivalDate, "
//				+ " vehicle_no as vehicleNo, "
//				+ " vendor_expedition_name as vendorExpeditionName, "
//				+ " total_volume as totalProductVolume, "
//				+ " total_weight as totalProductWeight, "
//				+ " city as regency, "
//				+ " postal_code as postalCode "
//				+ "FROM do_header doh "
//				+ "LEFT JOIN sloc s ON doh.id_plant = s.id_plant AND doh.id_sloc = s.id_sloc "
//				+ "LEFT JOIN vendor v ON doh.id_plant = v.id_plant AND doh.id_sloc = v.id_sloc "
//				+ "WHERE doh.doc_type LIKE :docType AND "
//				+ "	doh.date BETWEEN :dateFrom AND :dateTo AND "
//				+ "	doh.id_sloc = :idSloc AND "
//				+ " doh.id_plant = :idPlant AND "
//				+ "	doh.enabled = 1 AND "
//				+ "	doh.deleted = 0;";
//		
//		Session session = sessionFactory.getCurrentSession();
//		return (List<DOHistoryResponse>) session.createSQLQuery(query)
//				.setString("docType", docType)
//				.setDate("dateFrom", dateFrom)
//				.setDate("dateTo", dateTo)
//				.setString("idSloc", idSloc)
//				.setString("idPlant", idPlant)
//				.setResultTransformer(Transformers.aliasToBean(DOHistoryResponse.class))
//				.list();
//	}
	
	
}
