package id.co.qualitas.dao.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import id.co.qualitas.component.ConsParam;
import id.co.qualitas.dao.BaseDao;
import id.co.qualitas.dao.interfaces.GoodReceiptSTODao;
import id.co.qualitas.domain.request.GoodReceiptSTODetail;
import id.co.qualitas.domain.request.GoodReceiptSTOHeader;
import id.co.qualitas.domain.request.GoodReceiptSTORequest;
import id.co.qualitas.domain.response.WSMessage;

@Repository
@Transactional
public class GoodReceiptSTODaoImpl extends BaseDao implements GoodReceiptSTODao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public WSMessage create(GoodReceiptSTORequest request) {
		WSMessage message = new WSMessage();
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;
		int idHeader = 0;

		try {
			tx = session.beginTransaction();

			idHeader = doInsertGrSTOHeader(request, session);
			doInsertGrSTODetail(request, session, idHeader);

			tx.commit();

			message.setIdMessage(1);
			message.setMessage("Good Receipt STO created");
		} catch (Exception e) {
			tx.rollback();

			message.setIdMessage(0);
			message.setMessage(e.getMessage());
		}
		return message;
	}


	private int doInsertGrSTOHeader(GoodReceiptSTORequest request, Session session) {
		int idHeader = 0;

		GoodReceiptSTOHeader header = new GoodReceiptSTOHeader();
		header = request.getHeader();

		String queryCreateHeader = "INSERT INTO gr_sto_header "
				+ "(gr_no_sap, date, doc_no, doc_date, doc_type, id_sloc, id_plant, status, "
				+ "info_sync, created_by, created_date, enabled, deleted) "
				+ "OUTPUT Inserted.id "
				+ "VALUES (:grNoSap, :date, :docNo, :docDate, :docType, :idSloc, :idPlant, "
				+ ":status, :infoSync, :createdBy, GETDATE(), 1, 0 ) ";

		idHeader = (int) session.createSQLQuery(queryCreateHeader)
				.setString("grNoSap", header.getGrNoSap())
				.setDate("date", header.getDate())
				.setString("docNo", header.getDocNo())
				.setString("docDate", header.getDocDate())
				.setString("docType", header.getDocType())
				.setString("idSloc", header.getIdSloc())
				.setString("idPlant", header.getIdPlant())
				.setString("status", "Completed")
				.setString("infoSync", header.getInfoSync())
				.setString("createdBy", request.getCreatedBy())
				.uniqueResult();

		return idHeader;
	}
	private void doInsertGrSTODetail(GoodReceiptSTORequest request, Session session, int idHeader) {
		String queryInsertDetail = "INSERT INTO gr_sto_detail "
				+ "(id_gr_header, item_no, material_no , material_desc, qty, uom, batch_no, "
				+ "exp_date, variance, reason, photo, doc_no, doc_item_no, created_by, created_date, enabled, deleted, "
				+ "do_no_sap, do_item_no, higher_level_item, gr_qty ) "
				+ "VALUES (:idHeader, :itemNo, :materialNo, :materialDesc, :qty, :uom, :batchNo, "
				+ ":expDate, :variance, :reason, :photo, :docNo, :docItemNo, :createdBy, GETDATE(), 1, 0, "
				+ ":doNoSap, :doItemNo, :higherLevelItem, :grQty ) ";

		for (int i = 0; i < request.getListDetail().size(); i++) {
			GoodReceiptSTODetail detail = new GoodReceiptSTODetail();
			detail = request.getListDetail().get(i);
			byte[] photoByte = null;

			if(detail.getPhotoString() != null && !detail.getPhotoString().isEmpty()) {
				try {
					photoByte= Base64.decodeBase64(detail.getPhotoString());
		    		Path pathRequestImage = Paths.get(ConsParam.PATH_IMAGE_GRPOSTO + (String.valueOf(idHeader) + String.valueOf(i + 1)) + ".png" );
			    	
					Files.write(pathRequestImage, photoByte);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			session.createSQLQuery(queryInsertDetail)
					.setInteger("idHeader", idHeader)
					.setInteger("itemNo", i + 1)
					.setString("materialNo", detail.getMaterialNo())
					.setString("materialDesc", detail.getMaterialDesc())
					.setBigDecimal("qty", detail.getDoQty())
					.setString("uom", detail.getUom())
					.setString("batchNo", detail.getBatchNo())
					.setString("expDate", detail.getExpDate())
					.setString("variance", detail.getVariance())
					.setString("reason", detail.getReason())
					.setBinary("photo", photoByte)
					.setString("docNo", detail.getDocNo())
					.setString("docItemNo", detail.getDocItemNo())
					.setString("createdBy", request.getCreatedBy())
					.setString("doNoSap", detail.getDoNo())
					.setString("doItemNo", detail.getDoItemNo())
					.setString("higherLevelItem", detail.getHigherLevelItemBatch())
					.setBigDecimal("grQty", detail.getGrQty())
					.executeUpdate();
			

			
			String queryUpdatePodStatusInDoHeader = "UPDATE do_header "
					+ "SET pod_status = :podStatus, "
					+ "pod_status_desc = :podStatusDesc "
					+ "WHERE do_no_sap = :doNoSap ";

			session.createSQLQuery(queryUpdatePodStatusInDoHeader)
					.setString("podStatus", "C")
					.setString("podStatusDesc", "Completely processed")
					.setString("doNoSap", detail.getDoNo())
					.executeUpdate();

		}
	}
	
	@Override	
	public boolean isDoItemNoCreated(String docNo, int doItemNo) {
		
		String query = "SELECT TOP 1 h.doc_no "
				+ "FROM gr_sto_header h "
				+ "LEFT JOIN gr_sto_detail d ON h.id = d.id_gr_header "
				+ "WHERE d.do_no_sap = :docNo AND d.do_item_no = :doitemNo  ";

		Session session = sessionFactory.getCurrentSession();
		
		String result = (String) session.createSQLQuery(query)
				.setString("docNo", docNo)
				.setInteger("doitemNo", doItemNo)
				.uniqueResult();
		
		if(result != null) {
			return true;
		}else {
			return false;
		}
	}
}
