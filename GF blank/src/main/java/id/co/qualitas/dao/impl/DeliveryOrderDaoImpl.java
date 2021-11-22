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

import com.google.gson.Gson;

import id.co.qualitas.dao.BaseDao;
import id.co.qualitas.dao.interfaces.DeliveryOrderDao;
import id.co.qualitas.domain.request.DODetail;
import id.co.qualitas.domain.request.DOHeader;
import id.co.qualitas.domain.request.DORequest;
import id.co.qualitas.domain.response.DOHistoryResponse;
import id.co.qualitas.domain.response.WSMessage;

@Repository
@Transactional
public class DeliveryOrderDaoImpl extends BaseDao implements DeliveryOrderDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public WSMessage create(DORequest request) {
		WSMessage message = new WSMessage();
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;
		int idHeader = 0;

		try {
			tx = session.beginTransaction();

			DOHeader header = new DOHeader();
			header = request.getDoHeader();

			String queryCreateHeader = "INSERT INTO do_header "
					+ "(date, doc_no, doc_date, id_sloc, id_plant, destination, shipment_date, shipment_no, plate_no, "
					+ "driver, status, vendor_expedition, created_by, created_date, doc_type, doc_header, enabled, deleted, "
					+ "si_no, arrival_date, vehicle_no, destination_name, destination_email, estimated_departure_date, vendor_expedition_name, "
					+ "route, jdaload_id, destination_name2, destination_phone, po_no, notes ) " 
					+ "OUTPUT Inserted.id "
					+ "VALUES (:date, :docNo, :docDate, :idSloc, :idPlant, :destination, :shipmentDate, :shipmentNo, :plateNo, "
					+ ":driver, 'On Progress', :vendorExpedition, :createdBy, GETDATE(), :docType, :docHeader, 1, 0, "
					+ ":siNo, :arrivalDate, :vehicleNo, :destinationName, :destinationEmail, :estimatedDepartureDate, :vendorExpeditionName, "
					+ ":route, :jdaLoadId, :destinationName2, :destinationPhone, :poNo, :notes ) ";

			idHeader = (int) session.createSQLQuery(queryCreateHeader)
					.setString("date", header.getDate())
					.setString("docNo", header.getDocNo())
					.setString("docDate", header.getDocDate())
					.setString("idSloc", header.getIdSloc())
					.setString("idPlant", header.getIdPlant())
					.setString("destination", header.getDestination())
					.setString("shipmentDate", header.getShipmentDate())
					.setString("shipmentNo", header.getShipmentNo())
					.setString("plateNo", header.getPlateNo())
					.setString("driver", header.getDriver())
					.setString("vendorExpedition", header.getVendorExpedition())
					.setString("createdBy", request.getCreatedBy())
					.setString("docType", header.getDocType())
					.setString("docHeader", header.getDocHeader())
					.setString("siNo", header.getSiNo())
					.setString("arrivalDate", header.getArrivalDate())
					.setString("vehicleNo", header.getVehicleNo())
					.setString("destinationName", header.getDestinationName())
					.setString("destinationEmail", header.getDestinationEmail())
					.setString("estimatedDepartureDate", header.getEstimatedDepartureDate())
					.setString("vendorExpeditionName", header.getVendorExpeditionName())
					.setString("route", header.getRoute())
					.setString("jdaLoadId", header.getJdaLoadId())
					.setString("destinationName2", header.getDestinationName2())
					.setString("destinationPhone", header.getDestinationPhone())
					.setString("poNo", header.getPoNo())
					.setString("notes", header.getNotes())
					.uniqueResult();

			String queryCreateDetail = "INSERT INTO do_detail "
					+ "(id_do_header, item_no, doc_no, doc_item_no, material_no, material_desc, order_qty, do_qty, outstanding_qty, "
					+ "uom, created_by, created_date, enabled, deleted ) "
					+ "VALUES (:idHeader, :itemNo, :docNo, :docItemNo, :materialNo, :materialDesc, :orderQty, :doQty, :outstandingQty, "
					+ ":uom, :createdBy, GETDATE(), 1, 0 ) ";
			

			String queryCreateBatch = "INSERT INTO do_batch "
					+ "(id_do_header, item_no_detail, batch_no, actual_qty, uom, created_by, created_date, enabled, deleted ) "
					+ "VALUES (:idHeader, :itemNoDetail, :batchNo, :acualtQty, :uom, :createdBy, GETDATE(), 1, 0 ) ";

			for (int i = 0; i < request.getListDoDetail().size(); i++) {
				DODetail detail = new DODetail();
				DODetail prevDetail = new DODetail();
				detail = request.getListDoDetail().get(i);
				
				if(i != 0) {
					prevDetail = request.getListDoDetail().get(i - 1);
				}
				
				if(i == 0 || (prevDetail.getMaterialNo() != null && !detail.getMaterialNo().equals(prevDetail.getMaterialNo()))) {
					session.createSQLQuery(queryCreateDetail)
					.setInteger("idHeader", idHeader)
					.setInteger("itemNo", i+1)
					.setString("docNo", detail.getDocNo())
					.setString("docItemNo", detail.getDocItemNo())
					.setString("materialNo", detail.getMaterialNo())
					.setString("materialDesc", detail.getMaterialDesc())
					.setBigDecimal("orderQty", detail.getOrderQty())
					.setBigDecimal("doQty", detail.getDoQty())
					.setBigDecimal("outstandingQty", detail.getOutstandingQty())
					.setString("uom", detail.getUom())
					.setString("createdBy", request.getCreatedBy())
					.executeUpdate();
				}

				session.createSQLQuery(queryCreateBatch)
						.setInteger("idHeader", idHeader)
						.setString("itemNoDetail", detail.getDocItemNo())
						.setString("batchNo", detail.getBatchNo())
						.setBigDecimal("acualtQty", detail.getActualQty())
						.setString("uom", detail.getUom())
						.setString("createdBy", request.getCreatedBy())
						.executeUpdate();
			}

			message.setIdMessage(1);
			message.setMessage("Delivery order created");
			message.setResult(idHeader);

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
	public List<DOHistoryResponse> getHeadersForHistory(Date dateFrom, Date dateTo, String docType, String idSloc, String idPlant){
		
		String query = "SELECT doh.id as id, "
				+ "	do_no_sap as doNoSap, "
				+ "	date, "
				+ "	doh.id_sloc as idSloc, "
				+ "	doh.id_plant as idPlant,"
				+ "	s.plant_name as plantName,"
				+ "	destination, "
				+ "	doc_no as docNo, "
				+ "	po_no as poNo, "
				+ "	doc_date as docDate, "
				+ "	CAST(status AS VARCHAR) as status, "
				+ "	pod_status as podStatus, "
				+ "	pod_status_desc as podStatusDesc, "
				+ "	destination_name as destinationName, "
				+ "	destination_name2 as destinationName2, "
				+ "	destination_phone as destinationPhone, "
				+ " destination_email as destinationEmail, "
				+ "	notes, "
				+ " shipment_no as shipmentNo, "
				+ "	shipment_date as shipmentDate, "
				+ "	v.address as senderAddress, "
				+ "	v.phone as senderPhone, "
				+ "	doh.plate_no as plateNo, "
				+ "	uom_weight as uow, "
				+ "	uom_volume as uov, "
				+ "	estimated_departure_date as estimatedDepartureDate, "
				+ "	arrival_date as arrivalDate, "
				+ " vehicle_no as vehicleNo, "
				+ " vendor_expedition_name as vendorExpeditionName, "
				+ " total_volume as totalProductVolume, "
				+ " total_weight as totalProductWeight, "
				+ " city as regency, "
				+ " postal_code as postalCode, "
				+ "	jdaload_id as jdaLoadId, "
				+ "	si_no as siNo, "
				+ " driver as driver, "
				+ "	vendor_expedition as vendorExpedition, "
				+ " CAST (info_sync as VARCHAR(3000)) as infoSync "
				+ "FROM do_header doh "
				+ "LEFT JOIN sloc s ON doh.id_plant = s.id_plant AND doh.id_sloc = s.id_sloc "
				+ "LEFT JOIN vendor v ON doh.created_by = v.id "
				+ "WHERE doh.doc_type LIKE :docType AND "
				+ "	doh.date BETWEEN :dateFrom AND :dateTo AND "
				+ "	doh.id_sloc = :idSloc AND "
				+ " doh.id_plant = :idPlant AND "
				+ "	doh.enabled = 1 AND "
				+ "	doh.deleted = 0;";
		
		Session session = sessionFactory.getCurrentSession();
		return (List<DOHistoryResponse>) session.createSQLQuery(query)
				.setString("docType", docType)
				.setDate("dateFrom", dateFrom)
				.setDate("dateTo", dateTo)
				.setString("idSloc", idSloc)
				.setString("idPlant", idPlant)
				.setResultTransformer(Transformers.aliasToBean(DOHistoryResponse.class))
				.list();
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public DOHistoryResponse getHeadersForHistory(int id){
		
		String query = "SELECT TOP 1 doh.id as id, "
				+ "	do_no_sap as doNoSap, "
				+ "	date, "
				+ "	doh.id_sloc as idSloc, "
				+ "	doh.id_plant as idPlant,"
				+ "	s.plant_name as plantName,"
				+ "	destination, "
				+ "	doc_no as docNo, "
				+ "	po_no as poNo, "
				+ "	doc_date as docDate, "
				+ "	CAST(status as VARCHAR) as status, "
				+ "	pod_status as podStatus, "
				+ "	pod_status_desc as podStatusDesc, "
				+ "	destination_name as destinationName, "
				+ "	destination_name2 as destinationName2, "
				+ "	destination_phone as destinationPhone, "
				+ " destination_email as destinationEmail, "
				+ "	notes, "
				+ " shipment_no as shipmentNo, "
				+ "	shipment_date as shipmentDate, "
				+ "	v.address as senderAddress, "
				+ "	v.phone as senderPhone, "
				+ "	doh.plate_no as plateNo, "
				+ "	uom_weight as uow, "
				+ "	uom_volume as uov, "
				+ "	estimated_departure_date as estimatedDepartureDate, "
				+ "	arrival_date as arrivalDate, "
				+ " vehicle_no as vehicleNo, "
				+ " vendor_expedition_name as vendorExpeditionName, "
				+ " total_volume as totalProductVolume, "
				+ " total_weight as totalProductWeight, "
				+ " city as regency, "
				+ " postal_code as postalCode, "
				+ "	jdaload_id as jdaLoadId, "
				+ "	si_no as siNo,"
				+ "	vendor_expedition as vendorExpedition, "
				+ " driver as driver "
				+ "FROM do_header doh "
				+ "LEFT JOIN sloc s ON doh.id_plant = s.id_plant AND doh.id_sloc = s.id_sloc "
				+ "LEFT JOIN vendor v ON doh.created_by = v.id  "
				+ "WHERE doh.id LIKE :id AND "
				+ "	doh.enabled = 1 AND "
				+ "	doh.deleted = 0;";
		
		Session session = sessionFactory.getCurrentSession();
		return (DOHistoryResponse) session.createSQLQuery(query)
				.setInteger("id", id)
				.setResultTransformer(Transformers.aliasToBean(DOHistoryResponse.class))
				.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> getListHeaderForSync(){
		String query = "SELECT id, "
				+ " do_no_sap, "
				+ "	doc_no, "
				+ "	doh.id_sloc, "
				+ " doh.id_plant, "
				+ "	shipment_no, "
				+ "	jdaload_id, "
				+ "	doc_type, "
				+ "	si_no, "	
				+ "	route, "
				+ "	s.loading_point, "
				+ " doh.notes "
				+ "FROM do_header doh "
				+ "LEFT JOIN sloc s ON doh.id_sloc = s.id_sloc AND doh.id_plant = s.id_plant "
				+ "WHERE do_no_sap IS NULL AND "
				+ "	doh.enabled = 1 AND "
				+ "	doh.deleted = 0 ";
		
		Session session = sessionFactory.getCurrentSession();
		
		return session.createSQLQuery(query)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.list();
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> getHeaderByIdHeader(int idHeader){
		String query = "SELECT id, "
				+ " do_no_sap, "
				+ "	doc_no, "
				+ "	doh.id_sloc, "
				+ " doh.id_plant, "
				+ "	shipment_no, "
				+ "	jdaload_id, "
				+ "	doc_type, "
				+ "	si_no, "	
				+ "	route, "
				+ "	s.loading_point, "
				+ " doh.notes "
				+ "FROM do_header doh "
				+ "LEFT JOIN sloc s ON doh.id_sloc = s.id_sloc AND doh.id_plant = s.id_plant "
				+ "WHERE id = :idHeader AND "
				+ "	doh.enabled = 1 AND "
				+ "	doh.deleted = 0 ";
		
		Session session = sessionFactory.getCurrentSession();
		
		return (Map<String, String>) session.createSQLQuery(query)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.setInteger("idHeader", idHeader)
				.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DODetail> getDetailsByIdHeader(int idHeader){
		
		String query = "SELECT id_do_header as idDoHeader, "
				+ "	item_no as itemNo, "
				+ "	material_no as materialNo,"
				+ "	material_desc as materialDesc,"
				+ "	order_qty as orderQty, "
				+ "	do_qty as doQty, "
				+ "	actual_qty as actualQty, "
				+ "	uom, "
				+ "	batch_no as batchNo, "
				+ " CAST(doc_item_no AS VARCHAR) as docItemNo "
				+ "FROM do_detail "
				+ "WHERE id_do_header = :idHeader AND "
				+ "	enabled = 1 AND "
				+ "	deleted = 0;";
		
		Session session = sessionFactory.getCurrentSession();
		return (List<DODetail>) session.createSQLQuery(query)
				.setInteger("idHeader", idHeader)
				.setResultTransformer(Transformers.aliasToBean(DODetail.class))
				.list();
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<DODetail> getBatchsByIdAndDocItemNo(int idHeader, String docItemNo){
		
		String query = "SELECT id_do_header as idDoHeader, "
				+ "	CAST(item_no_detail AS VARCHAR) as docItemNo, "
				+ "	batch_no as batchNo, "
				+ "	actual_qty as actualQty, "
				+ "	uom "
				+ "FROM do_batch "
				+ "WHERE id_do_header = :idHeader AND "
				+ " item_no_detail = :docItemNo AND "
				+ "	enabled = 1 AND "
				+ "	deleted = 0;";
		
		Session session = sessionFactory.getCurrentSession();
		return (List<DODetail>) session.createSQLQuery(query)
				.setInteger("idHeader", idHeader)
				.setString("docItemNo", docItemNo)
				.setResultTransformer(Transformers.aliasToBean(DODetail.class))
				.list();
	}
	


	@SuppressWarnings("unchecked")
	@Override
	public List<DODetail> getBatchsById(int idHeader){
		
		String query = "SELECT id_do_header as idDoHeader, "
				+ "	CAST(item_no_detail AS VARCHAR) as docItemNo, "
				+ "	batch_no as batchNo, "
				+ "	actual_qty as actualQty, "
				+ "	uom "
				+ "FROM do_batch "
				+ "WHERE id_do_header = :idHeader AND "
				+ "	enabled = 1 AND "
				+ "	deleted = 0;";
		
		Session session = sessionFactory.getCurrentSession();
		return (List<DODetail>) session.createSQLQuery(query)
				.setInteger("idHeader", idHeader)
				.setResultTransformer(Transformers.aliasToBean(DODetail.class))
				.list();
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<DODetail> getDistinctDetailsByIdHeader(int idHeader){
		
		String query = "SELECT   "
				+ "MAX(do_qty) as doQty,  "
				+ "MAX(uom) AS uom,  "
				+ "CAST(doc_item_no AS VARCHAR) as docItemNo  "
				+ "FROM do_detail  "
				+ "WHERE id_do_header = :idHeader AND "
				+ "enabled = 1 AND  "
				+ "deleted = 0 "
				+ "GROUP BY doc_item_no ";
		
		Session session = sessionFactory.getCurrentSession();
		return (List<DODetail>) session.createSQLQuery(query)
				.setInteger("idHeader", idHeader)
				.setResultTransformer(Transformers.aliasToBean(DODetail.class))
				.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getListDoNoForUpdateStatusPOD(String idSloc, String idPlant){
		String query = "SELECT do_no_sap "
				+ " FROM do_header "
				+ " WHERE  "
				+ "	(pgi_no_sap IS NULL OR pgi_no_sap = '' "
				+ " OR ISNULL(pod_status, '') != 'C' "
				+ " OR total_weight IS NULL "
				+ " OR total_volume IS NULL ) "
				+ "	AND enabled = 1 "
				+ "	AND deleted = 0 "
				+ " AND id_sloc LIKE :idSloc "
				+ " AND id_plant LIKE :idPlant "
				+ " AND ISNULL(CAST(status AS VARCHAR), '') IN ('ASSIGN DO COMPLETED','PGI FAILED','PGI COMPLETED') ";
		
		Session session = sessionFactory.getCurrentSession();
		return (List<String>) session.createSQLQuery(query)
				.setString("idSloc", "%"+idSloc+"%")
				.setString("idPlant", "%"+idPlant+"%")
				.list();
		
	}
	
	@Override
	public void updateStatusPodByDoNoSap(String podStatus, String podStatusDesc, String doNoSap, String pgiNoSap, BigDecimal totalVolume, String uov, BigDecimal totalWeight, String uow, String status) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			String queryCreateHeader = "UPDATE do_header "
					+ " SET pod_status = :podStatus, pod_status_desc = :podStatusDesc, pgi_no_sap = :pgiNoSap, "
					+ " total_volume = :totalVolume, uom_volume = :uov, total_weight = :totalWeight , uom_weight = :uow, status = :status "
					+ " WHERE do_no_sap = :doNoSap ";

			session.createSQLQuery(queryCreateHeader)
					.setString("podStatus", podStatus)
					.setString("podStatusDesc", podStatusDesc)
					.setString("doNoSap", doNoSap)
					.setString("pgiNoSap", pgiNoSap)
					.setBigDecimal("totalVolume", totalVolume)
					.setString("uov", uov)
					.setBigDecimal("totalWeight", totalWeight)
					.setString("uow", uow)
					.setString("doNoSap", doNoSap)
					.setString("status", status)
					.executeUpdate();

			tx.commit();
		} catch (Exception e) {
			tx.rollback();

		}
		
	}
	
	@Override
	public void updateStatusPodByDoNoSap(String doNoSap, String pgiNoSap, BigDecimal totalVolume, String uov, BigDecimal totalWeight, String uow, String status) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			String queryCreateHeader = "UPDATE do_header "
					+ " SET pgi_no_sap = :pgiNoSap, "
					+ " total_volume = :totalVolume, uom_volume = :uov, total_weight = :totalWeight , uom_weight = :uow, status = :status "
					+ " WHERE do_no_sap = :doNoSap ";

			session.createSQLQuery(queryCreateHeader)
					.setString("doNoSap", doNoSap)
					.setString("pgiNoSap", pgiNoSap)
					.setBigDecimal("totalVolume", totalVolume)
					.setString("uov", uov)
					.setBigDecimal("totalWeight", totalWeight)
					.setString("uow", uow)
					.setString("doNoSap", doNoSap)
					.setString("status", status)
					.executeUpdate();

			tx.commit();
		} catch (Exception e) {
			tx.rollback();

		}
		
	}
	

	@Override
	public void updateStatusPodByDoNoSap(String doNoSap, String pgiNoSap) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			String queryCreateHeader = "UPDATE do_header "
					+ "SET pgi_no_sap = :pgiNoSap "
					+ "WHERE do_no_sap = :doNoSap ";

			session.createSQLQuery(queryCreateHeader)
					.setString("doNoSap", doNoSap)
					.setString("pgiNoSap", pgiNoSap)
					.executeUpdate();

			tx.commit();
		} catch (Exception e) {
			tx.rollback();

		}
		
	}

	@Override
	public WSMessage updateFieldsAfterSynced(String idHeader, String doNoSap, String infoSync, String status, BigDecimal tweight, String wuom, BigDecimal tvolum, String vuom) {
		String query = "UPDATE do_header "
				+ " SET do_no_sap = :doNoSap, info_sync = :infoSync, status = :status, updated_by='SYSTEM', updated_date=GETDATE(), "
				+ " total_weight = :tweight, uom_weight = :wuom, total_volume = :tvolum, uom_volume = :vuom "
				+ " WHERE id=:idHeader ";

		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;
		
		WSMessage message = new WSMessage();
		
		try {
			tx = session.beginTransaction();
			session.createSQLQuery(query)
				.setString("idHeader", idHeader)
				.setString("doNoSap", doNoSap)
				.setString("infoSync", infoSync)
				.setString("status", status)
				.setBigDecimal("tweight", tweight)
				.setString("wuom", wuom)
				.setBigDecimal("tvolum", tvolum)
				.setString("vuom", vuom)
				.executeUpdate();

			tx.commit();
			
			message.setIdMessage(1);
			message.setMessage("Success update table do_header");
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
		String query = "UPDATE do_header "
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
			message.setMessage("Success update table do_header");
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
	public WSMessage updateFieldsAfterSyncedByShipmentNo(String shipmentNo, String infoSync, String status, String pgiNo) {
		String query = "UPDATE do_header "
				+ "SET pgi_no_sap = :pgiNo, info_sync = :infoSync, status = :status, updated_by='SYSTEM', updated_date=GETDATE() "
				+ "WHERE shipment_no=:shipmentNo ";

		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;
		
		WSMessage message = new WSMessage();
		
		try {
			tx = session.beginTransaction();
			session.createSQLQuery(query)
				.setString("shipmentNo", shipmentNo)
				.setString("infoSync", infoSync)
				.setString("status", status)
				.setString("pgiNo", pgiNo)
				.executeUpdate();

			tx.commit();
			
			message.setIdMessage(1);
			message.setMessage("Success update table do_header");
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
	public WSMessage updateFieldsAfterSyncedByDoNoSap(String doNoSap, String infoSync, String status, String pgiNo) {
		String query = "UPDATE do_header "
				+ "SET pgi_no_sap = :pgiNo, info_sync = :infoSync, status = :status, updated_by='SYSTEM', updated_date=GETDATE() "
				+ "WHERE do_no_sap=:doNoSap ";

		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;
		
		WSMessage message = new WSMessage();
		
		try {
			tx = session.beginTransaction();
			session.createSQLQuery(query)
				.setString("doNoSap", doNoSap)
				.setString("infoSync", infoSync)
				.setString("status", status)
				.setString("pgiNo", pgiNo)
				.executeUpdate();

			tx.commit();
			
			message.setIdMessage(1);
			message.setMessage("Success update table do_header");
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
	public WSMessage updateTotalVolumeWeightByDoNoSap(String doNoSap, BigDecimal totalVolume, String uov, BigDecimal totalWeight, String uow) {
		String query = "UPDATE do_header "
				+ "SET total_volume = :totalVolume, uom_volume = :uov, total_weight = :totalWeight , uom_weight = :uow,updated_by='SYSTEM', updated_date=GETDATE() "
				+ "WHERE do_no_sap = :doNoSap ";

		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;
		
		WSMessage message = new WSMessage();
		
		try {
			tx = session.beginTransaction();
			session.createSQLQuery(query)
				.setBigDecimal("totalVolume", totalVolume)
				.setString("uov", uov)
				.setBigDecimal("totalWeight", totalWeight)
				.setString("uow", uow)
				.setString("doNoSap", doNoSap)
				.executeUpdate();

			tx.commit();
			
			message.setIdMessage(1);
			message.setMessage("Success update table do_header");
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
	public boolean isPodStatusAlreadyCompleted(String doNoSap) {
		String query = "SELECT top 1 pod_status "
				+ "FROM do_header "
				+ "WHERE do_no_sap = :doNoSap "
				+ "AND enabled = 1 "
				+ "AND deleted = 0;";

		Session session = sessionFactory.getCurrentSession();
		
		String result = (String) session.createSQLQuery(query)
				.setString("doNoSap", doNoSap)
				.uniqueResult();
		
		if(result != null && result.equals("C")) {
			return true;
		}else {
			return false;
		}
		
	}
	
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<Map<String, String>> getHeadersForSync(){
	 * 
	 * String query =
	 * "SELECT shipment_no, MAX(driver) as driver, MAX(vehicle_no) as vehicle_no " +
	 * "FROM do_header " +
	 * " WHERE ISNULL(CAST(status AS VARCHAR), '') IN ('ASSIGN DO COMPLETED','PGI FAILED') AND "
	 * + "	enabled = 1 AND " + "	deleted = 0 " + "GROUP BY shipment_no;";
	 * 
	 * Session session = sessionFactory.getCurrentSession(); return
	 * (List<Map<String, String>>) session.createSQLQuery(query)
	 * .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP) .list(); }
	 */
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> getShipmentNoForAssign(){
		
		String query = "SELECT MAX(do_no_sap) as doNoSap, MAX(shipment_no) as shipmentNo "
				+ " FROM do_header "
				+ " WHERE ISNULL(CAST(status AS VARCHAR), '') IN ('CREATE DO COMPLETED','ASSIGN DO FAILED') AND "
				+ "	enabled = 1 AND "
				+ "	deleted = 0 "
				+ " GROUP BY do_no_sap, shipment_no;";
		
		Session session = sessionFactory.getCurrentSession();
		return (List<Map<String, String>>) session.createSQLQuery(query)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.list();
	}
	
	@Override
	public Map<String, String> getDriverAndVehicleNoByShipmentNo(String shipmentNo){
		String query = "SELECT MAX(driver) as driver, MAX(vehicle_no) as vehicleNo "
				+ "FROM do_header "
				+ "WHERE "
				+ "	enabled = 1 AND "
				+ "	deleted = 0 AND "
				+ " shipment_no = :shipmentNo "
				+ "GROUP BY shipment_no;";
		
		Session session = sessionFactory.getCurrentSession();
		return (Map<String, String>) session.createSQLQuery(query)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.setString("shipmentNo", shipmentNo)
				.uniqueResult();
	}

	@Override
	public void updateStatusPodByDoNoSap(String podStatus, String podStatusDesc, String doNoSap, String pgiNoSap,
			BigDecimal totalVolume, String uov, BigDecimal totalWeight, String uow) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateStatusPodByDoNoSap(String doNoSap, String pgiNoSap, BigDecimal totalVolume, String uov,
			BigDecimal totalWeight, String uow) {
		// TODO Auto-generated method stub
		
	}
}
