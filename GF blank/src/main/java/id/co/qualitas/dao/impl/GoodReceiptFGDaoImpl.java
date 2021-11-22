package id.co.qualitas.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.SharedSessionContract;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import id.co.qualitas.component.ConsParam;
import id.co.qualitas.component.Utils;
import id.co.qualitas.dao.BaseDao;
import id.co.qualitas.dao.interfaces.GoodReceiptFGDao;
import id.co.qualitas.dao.interfaces.TransferPostingConfirmDao;
import id.co.qualitas.domain.request.GoodReceiptFGComponentRequest;
import id.co.qualitas.domain.request.GoodReceiptFGHeaderRequest;
import id.co.qualitas.domain.request.GoodReceiptFGMaterialRequest;
import id.co.qualitas.domain.request.GoodReceiptFGRequest;
import id.co.qualitas.domain.request.TransferPostingConfirmDetailRequest;
import id.co.qualitas.domain.request.TransferPostingConfirmHeaderRequest;
import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.response.DOHistoryResponse;
import id.co.qualitas.domain.response.SAP_GoodReceiptFGResponse;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.domain.webservice.goodsmvt.Bapi2017GmItemCreate;

@Repository
@Transactional
public class GoodReceiptFGDaoImpl extends BaseDao implements GoodReceiptFGDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public WSMessage create(GoodReceiptFGRequest request) {
		WSMessage message = new WSMessage();
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;
		int idHeader = 0;

		try {
			tx = session.beginTransaction();

			idHeader = doInsertGrFgHeader(request, session);
			doInsertListGrFgMaterial(request, session, idHeader);

			tx.commit();

			message.setIdMessage(1);
			message.setMessage("Good Receipt FG created");
		} catch (Exception e) {
			tx.rollback();

			message.setIdMessage(0);
			message.setMessage(e.getMessage());
		}
		return message;
	}

	private void doInsertListGrFgComponent(SharedSessionContract session, int idHeader, int itemNoHeader,
			List<GoodReceiptFGComponentRequest> listComponent, String createdBy) {
		String queryInsertComponent = "INSERT INTO gr_fg_component "
				+ "(id_gr_header, item_no_header, item_no, component_no, component_desc, batch_no, actual_qty, "
				+ "uom, created_by, created_date, component_item_no, enabled, deleted, qty_oem, uom_oem) "
				+ "VALUES (:idHeader, :itemNoHeader, :itemNo, :componentNo, :componentDesc, :batchNo, :actualQty, "
				+ ":uom, :createdBy, GETDATE(), :componentItemNo, 1, 0, :qtyOem, :uomOem) ";

		for (int i = 0; i < listComponent.size(); i++) {
			GoodReceiptFGComponentRequest component = new GoodReceiptFGComponentRequest();
			component = listComponent.get(i);
			
			BigDecimal qtySap = BigDecimal.ZERO;
			BigDecimal qtyOem = BigDecimal.ZERO;
			
			String uomSap = null;
			String uomOem = null;
			if(component.isOemConversion()) {
				qtySap = component.getActualQty().multiply(component.getMultiplier());
				uomSap = component.getUomSap();
				
				qtyOem = component.getActualQty();
				uomOem = component.getUom();
			}else {
				qtySap = component.getActualQty();
				uomSap = component.getUomSap();
			}

			session.createSQLQuery(queryInsertComponent).setInteger("idHeader", idHeader)
					.setInteger("itemNoHeader", itemNoHeader).setInteger("itemNo", i + 1)
					.setString("componentNo", component.getComponentNo())
					.setString("componentDesc", component.getComponentDesc())
					.setString("batchNo", component.getBatchNo())
					.setBigDecimal("actualQty", qtySap)
					.setString("uom", uomSap)
					.setString("createdBy", createdBy)
					.setString("componentItemNo", component.getComponentItemNo())
					.setBigDecimal("qtyOem", qtyOem)
					.setString("uomOem", uomOem)
					.executeUpdate();
		}

	}

	private void doInsertListGrFgMaterial(GoodReceiptFGRequest request, SharedSessionContract session, int idHeader) {
		String queryInsertDetail = "INSERT INTO gr_fg_detail "
				+ "(id_gr_header, item_no, material_no , material_desc, prod_target_date, order_qty, outstanding_qty, "
				+ "gr_qty, uom, batch_no, exp_date, created_by, created_date, doc_no, doc_item_no, enabled, deleted, item_category, posting_date, "
				+ "prod_date) "
				+ "VALUES (:idHeader, :itemNo, :materialNo, :materialDesc, :prodTargetDate, :orderQty, :outstandingQty, "
				+ ":grQty, :uom, :batchNo, :expDate, :createdBy, GETDATE(), :docNo, :docItemNo, 1, 0, :itemCategory, :postingDate, :prodDate ) ";

		for (int i = 0; i < request.getListMaterialWithComponents().size(); i++) {
			GoodReceiptFGMaterialRequest material = new GoodReceiptFGMaterialRequest();
			material = request.getListMaterialWithComponents().get(i).getMaterial();

			session.createSQLQuery(queryInsertDetail).setInteger("idHeader", idHeader).setInteger("itemNo", i + 1)
					.setString("materialNo", material.getMaterialNo())
					.setString("materialDesc", material.getMaterialDesc())
					.setString("prodTargetDate", material.getProdTargetDate())
					.setBigDecimal("orderQty", material.getOrderQty())
					.setBigDecimal("outstandingQty", material.getOutstandingQty())
					.setBigDecimal("grQty", material.getGrQty()).setString("uom", material.getUom())
					.setString("batchNo", material.getBatchNo()).setDate("expDate", material.getExpDate())
					.setString("createdBy", request.getCreatedBy()).setString("docNo", material.getDocNo())
					.setString("docItemNo", material.getDocItemNo())
					.setString("itemCategory", material.getItemCategory())
					.setString("postingDate", material.getPostingDate())
					.setString("prodDate", material.getDeliveryDate())
					.executeUpdate();

			if (request.getListMaterialWithComponents().get(i).getListComponent() != null
					&& !request.getListMaterialWithComponents().get(i).getListComponent().isEmpty()) {
				doInsertListGrFgComponent(session, idHeader, i + 1,
						request.getListMaterialWithComponents().get(i).getListComponent(), request.getCreatedBy());
			}
		}

	}

	private int doInsertGrFgHeader(GoodReceiptFGRequest request, SharedSessionContract session) {
		int idHeader = 0;

		GoodReceiptFGHeaderRequest header = new GoodReceiptFGHeaderRequest();
		header = request.getHeader();

		String queryCreateHeader = "INSERT INTO gr_fg_header "
				+ "(date, doc_no, doc_date, doc_type, doc_header, id_sloc, id_plant, created_by, created_date, enabled, deleted, status) "
				+ "OUTPUT Inserted.id "
				+ "VALUES (:date, :docNo, :docDate, :docType, :docHeader, :idSloc, :idPlant, :createdBy, GETDATE(), 1, 0, 'On Progress') ";

		idHeader = (int) session.createSQLQuery(queryCreateHeader).setString("date", header.getDate())
				.setString("docNo", header.getDocNo()).setString("docDate", header.getDocDate())
				.setString("docType", header.getDocType()).setString("docHeader", header.getDocHeader())
				.setString("idSloc", header.getIdSloc()).setString("idPlant", header.getIdPlant())
				.setString("createdBy", request.getCreatedBy()).uniqueResult();

		return idHeader;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SAP_GoodReceiptFGResponse> getListHeader() {
		String query = "SELECT id, date as pstngDate, doc_date as docDate, doc_header as headerTxt "
				+ "FROM gr_fg_header " 
				+ "WHERE enabled = 1 AND deleted = 0 AND gr_no_sap is null;";

		Session session = sessionFactory.getCurrentSession();
		return session.createSQLQuery(query)
				.setResultTransformer(Transformers.aliasToBean(SAP_GoodReceiptFGResponse.class))
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getListDetailBy(String idHeader) {
		String query = "SELECT d.item_no as detailItemNo, d.doc_no as poNumber, doc_item_no as poItem, material_no as material, gr_qty as entryQnt, uom as entryUom, "
				+ "batch_no as batch, exp_date as expiryDate, h.id_sloc as stgeLoc, h.id_plant as plant, d.posting_date as postingDate "
				+ "FROM gr_fg_detail d  " + "LEFT JOIN gr_fg_header h ON d.id_gr_header = h.id "
				+ "WHERE d.enabled = 1 AND d.deleted = 0  " + "AND id_gr_header = :idHeader ";

		Session session = sessionFactory.getCurrentSession();
		return session.createSQLQuery(query)
				.setString("idHeader", idHeader)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getListComponentBy(String idHeader, String detailItemNo) {
		String query = "SELECT id_gr_header, item_no_header, item_no, component_no as material, batch_no as batch, "
				+ "actual_qty as entryQnt, uom as entryUom, h.id_sloc as stgeLoc, h.id_plant as plant " 
				+ "FROM gr_fg_component c "
				+ "LEFT JOIN gr_fg_header h ON c.id_gr_header = h.id " + "WHERE c.enabled = 1 AND c.deleted = 0 "
				+ "AND id_gr_header = :idHeader and item_no_header = :detailItemNo ";

		Session session = sessionFactory.getCurrentSession();
		return session.createSQLQuery(query)
				.setString("idHeader", idHeader)
				.setString("detailItemNo", detailItemNo)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.list();
	}

	@Override
	public WSMessage updateFieldsAfterSynced(String idHeader, String grNoSap, String infoSync, String status) {
		String query = "UPDATE gr_fg_header "
				+ "SET gr_no_sap = :grNoSap, info_sync = :infoSync, status = :status, updated_by='SYSTEM', updated_date=GETDATE() "
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
				.executeUpdate();

			tx.commit();
			
			message.setIdMessage(1);
			message.setMessage("Success update table gr_fg_header");
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
		String query = "UPDATE gr_fg_header "
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
			message.setMessage("Success update table gr_fg_header");
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

}
