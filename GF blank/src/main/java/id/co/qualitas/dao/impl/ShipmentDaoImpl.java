package id.co.qualitas.dao.impl;

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

import id.co.qualitas.dao.BaseDao;
import id.co.qualitas.dao.interfaces.ShipmentDao;
import id.co.qualitas.domain.request.DODetail;
import id.co.qualitas.domain.request.PGIDetail;
import id.co.qualitas.domain.request.PGIHeader;
import id.co.qualitas.domain.request.PGIRequest;
import id.co.qualitas.domain.response.DOHistoryResponse;
import id.co.qualitas.domain.response.WSMessage;

@Repository
@Transactional
public class ShipmentDaoImpl extends BaseDao implements ShipmentDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	@Override
	public WSMessage create(PGIRequest request) {
		WSMessage message = new WSMessage();
		Session session = sessionFactory.getCurrentSession();

		PGIHeader header = new PGIHeader();
		header = request.getHeader();

		String queryCreateHeader = "INSERT INTO shipment_header "
				+ "(shipment_no, shipment_date, id_sloc, id_plant, vendor_forwarding, plate_no, "
				+ "vehicle_type, status, created_by, created_date, enabled, deleted) "
				+ "VALUES (:shipmentNo, :shipmentDate, :idSloc, :idPlant, :vendorForwarding, :plateNo, "
				+ ":vehicleType, 'On Progress', :createdBy, GETDATE(), 1, 0) ";

		session.createSQLQuery(queryCreateHeader).setString("shipmentNo", header.getShipmentNo())
				.setString("shipmentDate", header.getShipmentDate()).setString("idSloc", header.getIdSloc())
				.setString("idPlant", header.getIdPlant()).setString("vendorForwarding", header.getVendorForwarding())
				.setString("plateNo", header.getPlateNo()).setString("vehicleType", header.getVehicleType())
				.setString("createdBy", request.getCreatedBy()).uniqueResult();

		String queryCreateDetail = "INSERT INTO shipment_detail "
				+ "(shipment_no, do_no, do_date, id_sloc, id_plant, created_by, created_date, enabled, deleted) "
				+ "VALUES (:shipmentNo, :doNo, :doDate, :idSloc, :idPlant, :createdBy, GETDATE(), 1, 0) ";

		for (int i = 0; i < request.getListDetail().size(); i++) {
			PGIDetail detail = new PGIDetail();
			detail = request.getListDetail().get(i);

			session.createSQLQuery(queryCreateDetail).setString("shipmentNo", detail.getShipmentNo())
					.setString("doNo", detail.getDoNo()).setString("doDate", detail.getDoDate())
					.setString("idSloc", detail.getIdSloc()).setString("idPlant", detail.getIdPlant())
					.setString("createdBy", request.getCreatedBy()).executeUpdate();
		}
		return message;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> getHeadersForSync(){
		
		String query = "SELECT shipment_no "
				+ "FROM shipment_header "
				+ "WHERE ISNULL(status, '') != 'Completed' AND "
				+ "	enabled = 1 AND "
				+ "	deleted = 0;";
		
		Session session = sessionFactory.getCurrentSession();
		return (List<Map<String, String>>) session.createSQLQuery(query)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> getDetailsByShipmentNo(String shipmentNo){
		
		String query = "SELECT do_no "
				+ "FROM shipment_detail "
				+ "WHERE shipment_no = :shipmentNo AND "
				+ "	enabled = 1 AND "
				+ "	deleted = 0;";
		
		Session session = sessionFactory.getCurrentSession();
		return (List<Map<String, String>>) session.createSQLQuery(query)
				.setString("shipmentNo", shipmentNo)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.list();
	}

	@Override
	public WSMessage updateFieldsAfterSynced(String shipmentNo, String infoSync, String status) {
		String query = "UPDATE shipment_header "
				+ "SET info_sync = :infoSync, status = :status, updated_by='SYSTEM', updated_date=GETDATE() "
				+ "WHERE shipment_no = :shipmentNo ";

		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;
		
		WSMessage message = new WSMessage();
		
		try {
			tx = session.beginTransaction();
			session.createSQLQuery(query)
				.setString("shipmentNo", shipmentNo)
				.setString("infoSync", infoSync)
				.setString("status", status)
				.executeUpdate();

			tx.commit();
			
			message.setIdMessage(1);
			message.setMessage("Success update table shipment_header");
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
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean isShipmentNoCreated(String shipmentNo, String idSloc, String idPlant, String status) {
		String query = "SELECT top 1 shipment_no "
				+ "FROM shipment_header "
				+ "WHERE shipment_no = :shipmentNo "
				+ "AND id_sloc = :idSloc "
				+ "AND id_Plant = :idPlant "
				+ "AND created_date BETWEEN CAST(CAST(GETDATE() AS DATE) AS DATETIME) AND DATEADD(MONTH, 1, GETDATE()) "
				+ "AND status LIKE :status "
				+ "AND enabled = 1 "
				+ "AND deleted = 0;";

		Session session = sessionFactory.getCurrentSession();
		
		String result = (String) session.createSQLQuery(query)
				.setString("shipmentNo", shipmentNo)
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
}
