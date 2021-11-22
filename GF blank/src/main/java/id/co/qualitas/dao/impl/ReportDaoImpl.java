package id.co.qualitas.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
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
import id.co.qualitas.dao.interfaces.ReportDao;
import id.co.qualitas.domain.FilterReport;

@Repository
@Transactional
public class ReportDaoImpl extends BaseDao implements ReportDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Object> getFilter(){
		List<Object> result = new ArrayList<Object>();
//		String query = "SELECT id_sloc as idSloc, id_plant as idPlant from sloc ORDER BY id_sloc DESC";
		
		String query = "select id_sloc as idSloc, id_plant as idPlant "
				+ "from employee "
				+ "where id_Plant is not null "
				+ "group by id_sloc, id_plant "
				+ "order by id_sloc desc ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
//		List<Object> result = new ArrayList<Object>();
//		String getTipe = "SELECT type FROM users where username =:username ";
//		String employee = "SELECT id_sloc as idSloc, id_plant as idPlant FROM employee where id =:username ";
//		String vendor = "SELECT id_sloc as idSloc, id_plant as idPlant FROM vendor where id =:username";
//		
//		try {
//			Session session = sessionFactory.getCurrentSession();
//			String tipe = (String) session.createSQLQuery(getTipe)
//					.setString("username", username)
//					.uniqueResult();
//			
//			if(tipe.toUpperCase().equals("E")) {
//				result = session.createSQLQuery(employee)
//						.setString("username", username)
//						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
//						.list();
//			}else {
//				result = session.createSQLQuery(vendor)
//						.setString("username", username)
//						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
//						.list();
//			}
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		return result;
	}
	
	
	
	@Override
	public Boolean cancelReport(Object request){
		Session session =  sessionFactory.getCurrentSession();
		boolean result = true;
		Transaction tx = null;	
		Map<String,Object> req = (Map<String, Object>) request;
		String query = "";
		if(req.get("tableName").toString().equals("do_header")) {
//			query = "UPDATE "+req.get("tableName").toString()+" SET status = 'Canceled', info_sync =:reason,"
//					+ "updated_by=:updatedBy,updated_date = CURRENT_TIMESTAMP "
//					+ "where shipment_no =:id "
//					+ "ORDER BY shipment_no DESC ";
			query = "UPDATE "+req.get("tableName").toString()
					+" SET enabled = 0, reason =:reason,"
					+ "updated_by=:updatedBy,updated_date = CURRENT_TIMESTAMP "
					+ "where id =:id  ";
		}else {
			query = "UPDATE "+req.get("tableName").toString()
					+" SET enabled = 0, notes =:reason,"
					+ "updated_by=:updatedBy,updated_date = CURRENT_TIMESTAMP "
					+ "where id =:id  ";
		}
		
		try {
			tx = session.beginTransaction();
				session.createSQLQuery(query)
					.setString("reason",req.get("reason").toString())
					.setString("id",req.get("id").toString())
					.setString("updatedBy", req.get("createdBy").toString())
					.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			if (tx!=null) tx.rollback();
			result = false;
		}
		
		return result;
	}

	@Override
	public List<Object> TrasnferPosting(FilterReport filter){
		List<Object> result = new ArrayList<Object>();
		Map<String,Object> req = new HashMap<String, Object>();
		if(filter.getSearch() != null) {
			req = (Map<String, Object>) filter.getSearch();
		}
		
		String query = "select h.id,h.date,"
				+ "CONCAT(h.oem_from,' - ',(SELECT name from sloc where id_sloc =h.oem_from and id_plant = h.id_plant)) as oemFrom,"
				+ "CONCAT(h.oem_to,' - ',(SELECT name from sloc where id_sloc =h.oem_to and id_plant = h.id_plant)) as oemTo,"
				+ "h.doc_no as transferPostingNo, "
				+ "h.doc_date as transferPostingDate,h.status,COUNT(*) OVER () as totalPage "
				+ "from tp_header h "
				+ "WHERE h.date between :from and :to AND "
				+ "COALESCE(h.id,'') LIKE :id AND COALESCE(h.date,'') LIKE :date AND COALESCE(h.oem_from,'') LIKE :oemFrom AND "
				+ "COALESCE(h.oem_to,'') LIKE :oemTo AND COALESCE(h.doc_no,'') LIKE :tpNo AND "
				+ "COALESCE(h.doc_date,'') LIKE :tpDate and COALESCE(h.status,'') LIKE :status and "
				+ "COALESCE(h.id_sloc,'') LIKE :idSloc and COALESCE(h.id_plant,'') LIKE :idPlant "
				+ setWhereParam(filter,0);
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
					.setDate("from", filter.getFrom())
					.setDate("to", filter.getTo())
					.setString("id", (req.get("id") != null && !req.get("id").toString().isEmpty() ?  "%"+ req.get("id").toString()+ "%" : "%%"))
					.setString("date", (req.get("date") != null && !req.get("date").toString().isEmpty() ? "%"+req.get("date").toString()+ "%" : "%%"))
					.setString("oemFrom", (req.get("oemFrom") != null && !req.get("oemFrom").toString().isEmpty() ? "%"+req.get("oemFrom").toString()+ "%" : "%%"))
					.setString("oemTo", (req.get("oemTo") != null && !req.get("oemTo").toString().isEmpty() ?"%"+ req.get("oemTo").toString()+ "%" : "%%"))
					.setString("tpNo", (req.get("tpNo") != null && !req.get("tpNo").toString().isEmpty() ? "%"+req.get("tpNo").toString()+ "%" : "%%"))
					.setString("tpDate", (req.get("tpDate") != null && !req.get("tpDate").toString().isEmpty() ? "%"+req.get("tpDate").toString()+ "%" : "%%"))
					.setString("status", (req.get("status") != null && !req.get("status").toString().isEmpty() ? "%"+req.get("status").toString()+ "%" : "%%"))
					.setString("idSloc", (req.get("idSloc") != null && !req.get("idSloc").toString().isEmpty() ? "%"+req.get("idSloc").toString()+ "%" : "%%"))
					.setString("idPlant", (req.get("idPlant") != null && !req.get("idPlant").toString().isEmpty() ? "%"+req.get("idPlant").toString()+ "%" : "%%"))
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	@Override
	public Object TrasnferPostingDetail(Integer id) {
		Object result = new Object();
		String query = "select id,tp_no_sap as noSap,date,doc_no as transferPostingNo,doc_date as transferPostingDate,"
				+ "CONCAT(h.oem_from,' - ',(SELECT name from sloc where id_sloc =h.oem_from and id_plant = h.id_plant)) as oemFrom,"
				+ "CONCAT(h.oem_to,' - ',(SELECT name from sloc where id_sloc =h.oem_to and id_plant = h.id_plant)) as oemTo,"
				+ "CAST(doc_header AS VARCHAR) as descr,status, CAST (info_sync as VARCHAR(3000)) as infoSync, "
				+ "tp_no_sap as tpNoSap "
				+ "from tp_header h where id=:id ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
						.setInteger("id", id)
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
						.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Object> TransferPostingMaterial(Integer id) {
		List<Object> result = new ArrayList<Object>();
		String query = "select d.id_tp_header as id,d.item_no as itemNo,d.material_no as matNo,d.material_desc as matDesc, d.batch_no as batchNo," 
				+ "d.exp_date as expDate, d.order_qty as orderQty, d.tp_qty as actualQty, d.uom as uom,d.doc_item_no as docItem,qty_oem as qtyOem, uom_oem as uomOem  "  
				+ "from tp_header h " 
				+ "inner join tp_detail d on h.id = d.id_tp_header " 
				+ "where h.id = :id ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
						.setInteger("id", id)
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
						.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public List<Object> TrasnferPostingConfirmation(FilterReport filter) {
		List<Object> result = new ArrayList<Object>();
		Map<String,Object> req = new HashMap<String, Object>();
		if(filter.getSearch() != null) {
			req = (Map<String, Object>) filter.getSearch();
		}
		
		String query = "select * from ("
				+ "select h.id as id,tpconfirm_no_sap as tcpNo,date as date,v.full_name as vName, "
				+ "CONCAT(h.id_sloc,' - ', s.name) as idSloc, CONCAT(h.id_plant,' - ',s.plant_name) as idPlant,"
				+ "doc_no as tpNo, doc_date as tpDate, stockconfirm_no_sap as stockConfirm,"
				+ "CASE WHEN h.enabled = 0 THEN 'Canceled' ELSE status END AS status"
				+ ",COUNT(*) OVER () as totalPage "  
				+ "from tp_confirm_header h "
				+ "LEFT JOIN vendor v ON v.id_plant = h.id_plant AND v.id_sloc = h.id_sloc AND v.id_sap = h.id_vendor "
				+ "LEFT join sloc s ON s.id_plant = h.id_plant and s.id_sloc = h.id_sloc "
				+ "WHERE h.date between :from and :to AND "
				+ "COALESCE(h.tpconfirm_no_sap,'') LIKE :tpcNo AND COALESCE(h.date,'') LIKE :date AND COALESCE(v.full_name,'') LIKE :vName AND COALESCE(h.id_sloc,'') LIKE :idSloc AND "
				+ "COALESCE(h.id_plant,'') LIKE :idPlant AND COALESCE(h.doc_no,'') LIKE :tpNo AND "
				+ "COALESCE(h.stockconfirm_no_sap,'') LIKE :stockConfirm AND COALESCE(h.doc_date,'') LIKE :tpDate "
				+ setWhereParam(filter,0)
				+ ") a where status LIKE :status";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
					.setDate("from", filter.getFrom())
					.setDate("to", filter.getTo())
					.setString("tpcNo", (req.get("tpcNo") != null && !req.get("tpcNo").toString().isEmpty()  ? "%"+req.get("tpcNo").toString() +"%" : "%%"))
					.setString("date", (req.get("date") != null && !req.get("date").toString().isEmpty() ? "%"+req.get("date").toString()+"%" : "%%"))
					.setString("vName", (req.get("vName") != null && !req.get("vName").toString().isEmpty() ? "%"+req.get("vName").toString()+"%" : "%%"))
					.setString("idSloc", (req.get("idSloc") != null && !req.get("idSloc").toString().isEmpty() ? "%"+req.get("idSloc").toString()+"%" : "%%"))
					.setString("idPlant", (req.get("idPlant") != null && !req.get("idPlant").toString().isEmpty() ? "%"+req.get("idPlant").toString()+"%" : "%%"))
					.setString("tpNo", (req.get("tpNo") != null && !req.get("tpNo").toString().isEmpty() ? "%"+req.get("tpNo").toString() +"%": "%%"))
					.setString("tpDate", (req.get("tpDate") != null && !req.get("tpDate").toString().isEmpty() ? "%"+req.get("tpDate").toString() +"%": "%%"))
					.setString("stockConfirm", (req.get("stockConfirm") != null && !req.get("stockConfirm").toString().isEmpty() ? "%"+req.get("stockConfirm").toString()+"%" : "%%"))
					.setString("status", (req.get("status") != null && !req.get("status").toString().isEmpty() ? "%"+ req.get("status").toString() +"%": "%%"))
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Object TrasnferPostingConfirmationDetail(Integer id) {
		Object result = new Object();
		String query = "select h.id as id,tpconfirm_no_sap as tcpNo,date as date, "
				+ "v.full_name as vName, CONCAT(h.id_sloc,' - ', s.name) as idSloc, CONCAT(h.id_plant,' - ',s.plant_name) as idPlant,"
				+ "doc_no as tpNo, doc_date as tpDate, status, h.enabled, " 
				+ "CAST(doc_header AS VARCHAR) as descr, CAST(info_sync AS VARCHAR(3000)) as infoSync, stockconfirm_no_sap as stockConfirm, "
				+ "CAST(h.notes as varchar) as notes " 
				+ "from tp_confirm_header h "
				+ "LEFT join sloc s ON s.id_plant = h.id_plant and s.id_sloc = h.id_sloc "
				+ "LEFT JOIN vendor v ON v.id_plant = h.id_plant AND v.id_sloc = h.id_sloc AND v.id_sap = h.id_vendor "
				+ "where h.id=:id ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
						.setInteger("id", id)
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
						.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Object> TransferPostingConfirmationMaterial(Integer id) {
		List<Object> result = new ArrayList<Object>();
		String query = "select d.id_tpconfirm_header as id,d.item_no as itemNo,d.doc_item_no as docItem ,d.material_no as matNo,d.material_desc as matDesc, d.batch_no as batchNo,"  
				+ "d.qty as qty,d.uom as uom,CAST(d.notes AS VARCHAR) as notes,qty_oem as qtyOem, uom_oem as uomOem "  
				+ "from tp_confirm_header h " 
				+ "inner join tp_confirm_detail d on h.id = d.id_tpconfirm_header " 
				+ "where h.id = :id ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
						.setInteger("id", id)
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
						.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public String transferPostingStatus(Integer id) {
		String result = null;
		String query = "SELECT status FROM TP_CONFIRM_HEADER WHERE id = :id ";
		
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  (String) session.createSQLQuery(query)
						.setInteger("id", id)
						.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	public List<Object> pgiShipment(FilterReport filter) {
		List<Object> result = new ArrayList<Object>();
		String query = "select id,pgi_no_sap as pgiShipmentNo, date,shipment_no as shipmentNo, shipment_date as shipmentDate, " 
				+ "id_sloc as idSloc, id_plant as idPlant, status,COUNT(*) OVER () as totalPage " 
				+ "from pgi_header "
				+ "WHERE date between :from and :to "
				+ setWhereParam(filter,0);
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
					.setDate("from", filter.getFrom())
					.setDate("to", filter.getTo())
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Object pgiShipmentDetail(Integer id) {
		Object result = new Object();
		String query = "select h.id as id,h.pgi_no_sap as pgiShipmentNo, h.date as date, h.shipment_no as shipmentNo, h.shipment_date as shipmentDate,"  
				+ "h.id_sloc as idSloc, h.id_plant as idPlant, h.vendor_forwarding as vendorForwarding, h.vehicle_no as vehicleNo,"
				+ "h.vehicle_type as vehicleType,h.plate_no as policeNo, h.status as status, CAST(h.info_sync as varchar(3000)) as infoSync "  
				+ "from pgi_header h " 
				+ "where id=:id ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
						.setInteger("id", id)
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
						.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Object> pgiShipmentMaterial(Integer id) {
		List<Object> result = new ArrayList<Object>();
		String query = "select d.do_no as doNo, d.do_date as doDate "  
				+ "from pgi_header h "  
				+ "INNER JOIN pgi_detail d ON h.id = d.id_pgi_header "
				+ "where h.id = :id ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
						.setInteger("id", id)
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
						.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public List<Object> Shipment(FilterReport filter) {
		List<Object> result = new ArrayList<Object>();
		Map<String,Object> req = new HashMap<String, Object>();
		if(filter.getSearch() != null) {
			req = (Map<String, Object>) filter.getSearch();
		}
		
		String query = "select shipment_no as shipmentNo,shipment_date as shipmentDate,status,"
				+ "posting_date as pgiDate, " 
				+ "CONCAT(h.id_sloc,' - ', s.name) as idSloc, CONCAT(h.id_plant,' - ',s.plant_name) as idPlant,COUNT(*) OVER () as totalPage "
				+ "from shipment_header h "
				+ "LEFT join sloc s ON s.id_plant = h.id_plant and s.id_sloc = h.id_sloc "
				+ "WHERE shipment_date between :from and :to AND "
				+ "COALESCE(h.shipment_no,'') LIKE :shipmentNo AND COALESCE(h.shipment_date,'') LIKE :shipmentDate AND "
				+ "COALESCE(h.id_sloc,'') LIKE :idSloc AND COALESCE(h.id_plant,'') LIKE :idPlant AND COALESCE(h.status,'') LIKE :status "
				+ setWhereParam(filter,1);
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
					.setDate("from", filter.getFrom())
					.setDate("to", filter.getTo())
					.setString("shipmentNo", (!req.get("shipmentNo").toString().isEmpty() ? "%"+req.get("shipmentNo").toString()+"%" : "%%"))
					.setString("shipmentDate", (!req.get("shipmentDate").toString().isEmpty() ? "%"+req.get("shipmentDate").toString()+"%" : "%%"))
					.setString("idSloc", (!req.get("idSloc").toString().isEmpty() ? "%"+req.get("idSloc").toString()+"%" : "%%"))
					.setString("idPlant", (!req.get("idPlant").toString().isEmpty() ? "%"+req.get("idPlant").toString()+"%" : "%%"))
					.setString("status", (!req.get("status").toString().isEmpty() ? "%"+req.get("status").toString()+"%" : "%%"))
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Object ShipmentDetail(String id) {
		Object result = new Object();
		String query = "select shipment_no as shipmentNo,shipment_date as shipmentDate, "  
				+ "vendor_forwarding as vendorForwarding, vehicle_no as vehicleNo, plate_no as policeNo, vehicle_type as vehicleType," 
				+ "status, CAST(info_sync as varchar(3000)) as infoSync, posting_date as pgiDate, "  
				+ "CONCAT(h.id_sloc,' - ', s.name) as idSloc, CONCAT(h.id_plant,' - ',s.plant_name) as idPlant,s.loading_point as loadingPoint "
				+ "from shipment_header h "
				+ "LEFT join sloc s ON s.id_plant = h.id_plant and s.id_sloc = h.id_sloc "
				+ "where shipment_no=:id ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
						.setString("id", id)
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
						.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Object> ShipmentMaterial(String id) {
		List<Object> result = new ArrayList<Object>();
		String query = "select d.do_no as doNo, d.do_date as doDate "
				+ "from shipment_header h " 
				+ "INNER JOIN shipment_detail d ON h.shipment_no = d.shipment_no " 
				+ "where h.shipment_no = :id ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
						.setString("id", id)
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
						.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	@Override
	public List<Object> deliveryOrder(FilterReport filter) {
		List<Object> result = new ArrayList<Object>();
		Map<String,Object> req = new HashMap<String, Object>();
		if(filter.getSearch() != null) {
			req = (Map<String, Object>) filter.getSearch();
		}
		
		String query = "select * from ("
				+ "select id,do_no_sap as doNo, date, destination, " 
				+ "doc_no as docNo, doc_date as docDate,"
				+ "CASE "
				+ "	WHEN CAST(doc_type AS VARCHAR)='SOBU' THEN 'SO' "
				+ "	WHEN CAST(doc_type AS VARCHAR)='STO' THEN 'PO STO' ELSE '' END as docType," 
				+ "CONCAT(h.id_sloc,' - ', s.name) as idSloc, CONCAT(h.id_plant,' - ',s.plant_name) as idPlant, pgi_no_sap as pgiNo,pod_status_desc as podStatus,"
				+ "COUNT(*) OVER () as totalPage, shipment_no as shipmentNo, si_no as siNo, "
				+ "CASE WHEN h.enabled = 0 THEN 'CANCELED' ELSE CAST(status as VARCHAR) END AS status, CAST(h.reason AS VARCHAR) as reason "
				+ "from do_header h "
				+ "LEFT join sloc s ON s.id_plant = h.id_plant and s.id_sloc = h.id_sloc "
				+ "WHERE date between :from and :to AND "
				+ "COALESCE(h.do_no_sap,'') LIKE :doNo AND COALESCE(h.shipment_no,'') LIKE :shipmentNo AND "
				+ "COALESCE(h.date,'') LIKE :date AND COALESCE(h.id_sloc,'') LIKE :idSloc AND "
				+ "COALESCE(h.destination,'') LIKE :destination AND COALESCE(h.doc_no,'') LIKE :docNo AND "
				+ "COALESCE(h.doc_date,'') LIKE :docDate AND "
//				+ "COALESCE(h.status,'') LIKE :status AND "
				+ "COALESCE(h.pgi_no_sap,'') LIKE :pgiNo AND COALESCE(h.pod_status_desc,'') LIKE :podStatus "
				+ "AND COALESCE(h.si_no,'') LIKE :siNo AND COALESCE(h.id_plant,'') LIKE :idPlant  "
				+ setWhereParam(filter,0)
				+ ")a WHERE docType LIKE :docType AND status LIKE :status ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
					.setDate("from", filter.getFrom())
					.setDate("to", filter.getTo())
					.setString("doNo", (req.get("doNo") != null && !req.get("doNo").toString().isEmpty() ? "%"+req.get("doNo").toString()+"%" : "%%"))
					.setString("date", (req.get("date") != null && !req.get("date").toString().isEmpty() ? "%"+req.get("date").toString()+"%" : "%%"))
					.setString("idSloc", (req.get("idSloc") != null && !req.get("idSloc").toString().isEmpty() ? "%"+req.get("idSloc").toString()+"%" : "%%"))
					.setString("destination", (req.get("destination") != null && !req.get("destination").toString().isEmpty() ?"%"+ req.get("destination").toString()+"%" : "%%"))
					.setString("siNo", (req.get("siNo") != null && !req.get("siNo").toString().isEmpty() ? "%"+req.get("siNo").toString()+"%" : "%%"))
					.setString("shipmentNo", (req.get("shipmentNo") != null && !req.get("shipmentNo").toString().isEmpty() ? "%"+req.get("shipmentNo").toString()+"%" : "%%"))
					.setString("docNo", (req.get("docNo") != null && !req.get("docNo").toString().isEmpty() ? "%"+req.get("docNo").toString()+"%" : "%%"))
					.setString("docDate", (req.get("docDate") != null && !req.get("docDate").toString().isEmpty() ? "%"+req.get("docDate").toString()+"%" : "%%"))
					.setString("docType", (req.get("docType") != null && !req.get("docType").toString().isEmpty() ? "%"+req.get("docType").toString()+"%" : "%%"))
					.setString("pgiNo", (req.get("pgiNo") != null && !req.get("pgiNo").toString().isEmpty() ? "%"+req.get("pgiNo").toString()+"%" : "%%"))
					.setString("podStatus", (req.get("podStatus") != null && !req.get("podStatus").toString().isEmpty() ? "%"+req.get("podStatus").toString()+"%" : "%%"))
					.setString("status", (req.get("status") != null && !req.get("status").toString().isEmpty() ? "%"+req.get("status").toString()+"%" : "%%"))
					.setString("idPlant", (req.get("idPlant") != null && !req.get("idPlant").toString().isEmpty() ? "%"+req.get("idPlant").toString()+"%" : "%%"))
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Object deliveryOrderDetail(String id) {
		Object result = new Object();
		String query = "select id,do_no_sap as doNo, date, destination, "  
				+ "CONCAT(h.id_sloc,' - ', s.name) as idSloc, CONCAT(h.id_plant,' - ',s.plant_name) as idPlant,"
				+ "doc_no as docNo,doc_date as docDate,shipment_no as shipmentNo,shipment_date as shipmentDate, estimated_departure_date as estDepartureTime,"  
				+ "vehicle_no as vehicleNo,plate_no as policeNo,driver,vendor_expedition as vendorExpedition, CAST(doc_type AS VARCHAR) as docType, "
				+ "CAST(h.reason as varchar) as notes, h.enabled, " 
				+ "CAST(status AS VARCHAR) as status, CAST(info_sync as varchar(3000)) as infoSync,route, jdaload_id as jdaLoad,pgi_no_sap as pgiNo,pod_status_desc as podStatus,si_no as shipmentInstuction, "
				+ "s.loading_point as loadingPoint "
				+ "from do_header h "
				+ "LEFT join sloc s ON s.id_plant = h.id_plant and s.id_sloc = h.id_sloc "
				+ "where id=:id ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
						.setString("id", id)
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
						.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Object> deliveryOrderMaterial(String id) {
		List<Object> result = new ArrayList<Object>();
		String query = "select item_no as itemNo, material_no as matNo, material_desc as matDesc, order_qty as orderQty, uom,"  
				+ "do_qty as doQty, batch_no as batchNo, actual_qty as actualQty,d.doc_item_no as docItem " 
				+ "from do_header h " 
				+ "INNER JOIN do_detail d on h.id = d.id_do_header " 
				+ "where h.id = :id ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
						.setString("id", id)
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
						.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public List<Object> deliveryOrderBatch(String id) {
		List<Object> result = new ArrayList<Object>();
		String query = "select item_no_detail as itemNo,batch_no as batchNo, actual_qty as qty,uom from do_batch where id_do_header =:id ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
						.setString("id", id)
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
						.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public List<Object> GRPOSTO(FilterReport filter) {
		List<Object> result = new ArrayList<Object>();
		Map<String,Object> req = new HashMap<String, Object>();
		if(filter.getSearch() != null) {
			req = (Map<String, Object>) filter.getSearch();
		}
		
		String query = "select h.id as id , date, doc_no as poStoNo,doc_date as poStoDate, status,"  
				+ "CONCAT(h.id_sloc,' - ', s.name) as idSloc, CONCAT(h.id_plant,' - ',p.name) as idPlant,COUNT(*) OVER () as totalPage, "
				+ "CONCAT(e.id_plant, ' - ', p2.name) as plantPenerima "
				+" from gr_sto_header h "
				+ "LEFT join sloc s ON s.id_plant = h.id_plant and s.id_sloc = h.id_sloc "
				+ "LEFT JOIN plant p ON h.id_plant = p.id "
				+ "LEFT JOIN employee e ON h.created_by = e.id "
				+ "LEFT JOIN plant p2 ON e.id_plant = p2.id "
				+ "WHERE date between :from and :to AND "
				+ "COALESCE(h.id,'') LIKE :id AND COALESCE(h.date,'') LIKE :date AND COALESCE(h.id_sloc,'') LIKE :idSloc AND "
				+ "CONCAT(h.id_plant,' - ',p.name) LIKE :idPlant AND COALESCE(h.doc_no,'') LIKE :poStoNo AND "
				+ "COALESCE(h.doc_date,'') LIKE :poStoDate and COALESCE(h.status,'') LIKE :status AND "
				+ "CONCAT(e.id_plant, ' - ', p2.name) LIKE :plantPenerima "
				+ setWhereParam(filter,0);
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
					.setDate("from", filter.getFrom())
					.setDate("to", filter.getTo())
					.setString("id", (req.get("id") != null && !req.get("id").toString().isEmpty() ? "%"+req.get("id").toString()+"%" : "%%"))
					.setString("date", (req.get("date") != null && !req.get("date").toString().isEmpty() ? "%"+req.get("date").toString()+"%" : "%%"))
					.setString("idSloc", (req.get("idSloc") != null && !req.get("idSloc").toString().isEmpty() ? "%"+req.get("idSloc").toString()+"%" : "%%"))
					.setString("idPlant", (req.get("idPlant") != null && !req.get("idPlant").toString().isEmpty() ? "%"+req.get("idPlant").toString()+"%" : "%%"))
					.setString("poStoNo", (req.get("poStoNo") != null && !req.get("poStoNo").toString().isEmpty() ? "%"+req.get("poStoNo").toString()+"%" : "%%"))
					.setString("poStoDate", (req.get("poStoDate") != null && !req.get("poStoDate").toString().isEmpty() ?"%"+ req.get("poStoDate").toString()+"%" : "%%"))
					.setString("status", (req.get("status") != null && !req.get("status").toString().isEmpty() ? "%"+req.get("status").toString()+"%" : "%%"))
					.setString("plantPenerima", (req.get("plantPenerima") != null && !req.get("plantPenerima").toString().isEmpty() ? "%"+req.get("plantPenerima").toString()+"%" : "%%"))
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Object GRPOSTODetail(String id) {
		Object result = new Object();
		String query = "select h.id,gr_no_sap as noSap,date,"
				+ "doc_no as poStoNo,doc_date as poStoDate, status, CAST(info_sync as varchar(3000)) as infoSync," 
				+ "CONCAT(h.id_sloc,' - ', s.name) as idSloc, CONCAT(h.id_plant,' - ',p.name) as idPlant, "
				+ "CONCAT(e.id_plant, ' - ', p2.name) as plantTo "
				+ "from gr_sto_header h " 
				+ "LEFT join sloc s ON s.id_plant = h.id_plant and s.id_sloc = h.id_sloc "
				+ "LEFT JOIN plant p ON h.id_plant = p.id "
				+ "LEFT JOIN employee e ON h.created_by = e.id "
				+ "LEFT JOIN plant p2 ON e.id_plant = p2.id "
				+ "where h.id=:id ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
						.setString("id", id)
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
						.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Object> GRPOSTOMaterial(String id) {
		List<Object> result = new ArrayList<Object>();
		String query = "select h.id as id,d.doc_no as docNo,d.do_no_sap as doNoSap,d.do_item_no as doItemNo,item_no as itemNo, material_no as matNo, material_desc as matDesc, "
				+ "batch_no as batchNo,exp_date as expDate,"
				+ "isnull(qty, 0) as ordQty, uom, variance, reason, isnull(gr_qty, 0) as grQty, gr_no_sap as grNoSap "  
				+ "from gr_sto_header h " 
				+ "inner join gr_sto_detail d on h.id = id_gr_header "
				+ "where h.id = :id ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
						.setString("id", id)
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
						.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public List<Object> GRFG(FilterReport filter) {
		List<Object> result = new ArrayList<Object>();
		Map<String,Object> req = new HashMap<String, Object>();
		if(filter.getSearch() != null) {
			req = (Map<String, Object>) filter.getSearch();
		}
		
		String query = "select * "
				+ "from ("
				+ "select DISTINCT(h.id),h.gr_no_sap as grNoSap,h.date, h.doc_no as purchOrderNo, h.doc_date as purchOrderDate,   "
				+ "CONCAT(h.id_sloc,' - ', s.name) as idSloc, CONCAT(h.id_plant,' - ',s.plant_name) as idPlant,COUNT(*) OVER () as totalPage,"
				+ "CASE WHEN d.item_category = 3 THEN 'PO Subcon' WHEN d.item_category = 0 THEN 'PO Reguler' ELSE '' END as infoDocType, "
				+ "CASE WHEN h.enabled = 0 THEN 'Canceled' ELSE status END AS status, h.enabled "  
				+ "from gr_fg_header h "
				+ "INNER JOIN gr_fg_detail d on h.id = d.id_gr_header " 
				+ "LEFT join sloc s ON s.id_plant = h.id_plant and s.id_sloc = h.id_sloc "
				+ "WHERE date between :from and :to AND "
				+ "COALESCE(h.gr_no_sap,'') LIKE :grNoSap AND COALESCE(h.date,'') LIKE :date AND COALESCE(h.id_sloc,'') LIKE :idSloc AND "
				+ "COALESCE(h.id_plant,'') LIKE :idPlant AND COALESCE(h.doc_no,'') LIKE :purchOrderNo AND COALESCE(h.doc_date,'') LIKE :purchOrderDate "
//				+ "COALESCE(h.status,'') LIKE :status "
				+ setWhereParam(filter,0)
				+ ") a "
				+ "where status LIKE :status";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
					.setDate("from", filter.getFrom())
					.setDate("to", filter.getTo())
					.setString("grNoSap", (req.get("grNoSap") != null && !req.get("grNoSap").toString().isEmpty() ? "%"+req.get("grNoSap").toString()+"%" : "%%"))
					.setString("date", (req.get("date") != null && !req.get("date").toString().isEmpty() ? "%"+req.get("date").toString()+"%" : "%%"))
					.setString("idSloc", (req.get("idSloc") != null && !req.get("idSloc").toString().isEmpty() ? "%"+req.get("idSloc").toString()+"%" : "%%"))
					.setString("idPlant", (req.get("idPlant") != null && !req.get("idPlant").toString().isEmpty() ? "%"+req.get("idPlant").toString()+"%" : "%%"))
					.setString("purchOrderNo", (req.get("purchOrderNo") != null && !req.get("purchOrderNo").toString().isEmpty() ? "%"+req.get("purchOrderNo").toString()+"%" : "%%"))
					.setString("purchOrderDate", (req.get("purchOrderDate") != null && !req.get("purchOrderDate").toString().isEmpty() ? "%"+req.get("purchOrderDate").toString()+"%" : "%%"))
					.setString("status", (req.get("status") != null && !req.get("status").toString().isEmpty() ? "%"+req.get("status").toString()+"%" : "%%"))
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Object GRFGDetail(String id) {
		Object result = new Object();
		String query = "select h.id,h.gr_no_sap as grNoSap,h.date,h.doc_no as purchOrderNo, h.doc_date as purchOrderDate, "
				+ "d.prod_date as prodDate, d.posting_date as postingDate, "
				+ "h.status, CAST(h.doc_header as varchar) as descr, CAST(h.info_sync as varchar(3000)) as infoSync, "
				+ "CONCAT(h.id_sloc,' - ', s.name) as idSloc, CONCAT(h.id_plant,' - ',s.plant_name) as idPlant, "
				+ "CASE WHEN d.item_category = 0 THEN 'PO Subcon' WHEN d.item_category = 3 THEN 'PO Reguler' ELSE '' END as infoDocType, "
				+ "h.enabled, CAST(h.notes as varchar) as notes " 
				+ "from gr_fg_header h " 
				+ "INNER JOIN gr_fg_detail d on h.id = d.id_gr_header "
				+ "LEFT join sloc s ON s.id_plant = h.id_plant and s.id_sloc = h.id_sloc "
				+ "where h.id=:id ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
						.setString("id", id)
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
						.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Object> GRFGMaterial(String id) {
		List<Object> result = new ArrayList<Object>();
		String query = "select item_no as itemNo, material_no as matNo, material_desc as matDesc, prod_target_date as prodTargetDate, order_qty as orderQty,"  
				+ "outstanding_qty as outstandingQty, gr_qty as grQty, uom,batch_no as batchNo, exp_date as expDate,d.item_category as itemCategory,d.doc_item_no as docItem "
				+ "from gr_fg_header h " 
				+ "inner join gr_fg_detail d on h.id = d.id_gr_header "
				+ "where h.id = :id ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
						.setString("id", id)
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
						.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public List<Object> GRFGComponent(String id) {
		List<Object> result = new ArrayList<Object>();
		String query = "select c.item_no_header as itemNoHeader,c.item_no as itemNo, c.component_no as componentNo, c.component_desc as componentDesc, c.batch_no as batchNo, "  
				+ "c.actual_qty as actualQty,c.uom,qty_oem as qtyOem, uom_oem as uomOem   " 
				+ "from gr_fg_header h " 
				+ "inner join gr_fg_detail d on h.id = d.id_gr_header "  
				+ "INNER JOIN gr_fg_component c on d.id_gr_header = c.id_gr_header and d.item_no = c.item_no_header " 
				+ "where h.id = :id ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
						.setString("id", id)
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
						.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	String setWhereParam(FilterReport filter,int isShipment) {
		String whereParam = " ";
		
		Integer offset = filter.getCurPage() * 15;
		
//		if(filter.getIdPlant() != null){
//			whereParam = whereParam + "AND h.id_plant = '"+filter.getIdPlant()+"'  ";
//		}
//		if(filter.getIdSloc() != null){
//			whereParam = whereParam + "AND h.id_sloc = '"+filter.getIdSloc()+"'  ";
//		}
//		if(filter.getStatus() != null){
//			whereParam = whereParam + "AND h.status = '"+filter.getStatus()+"'  ";
//		}
//		if(filter.getDocType() != null){
//			whereParam = whereParam + "AND h.doc_type = '"+filter.getDocType()+"'  ";
//		}
		
		if(isShipment == 0)
			whereParam  = whereParam + " ORDER BY h.id DESC OFFSET "+offset+" ROWS FETCH NEXT 15 ROWS ONLY ";
		else
			whereParam  = whereParam + " ORDER BY h.shipment_no DESC OFFSET "+offset+" ROWS FETCH NEXT 15 ROWS ONLY ";
		
		return whereParam;
	}

	
	@Override
	public List<Object> TrasnferPostingConfirmationForExport(FilterReport filter) {
		List<Object> result = new ArrayList<Object>();
		Map<String,Object> req = new HashMap<String, Object>();
		if(filter.getSearch() != null) {
			req = (Map<String, Object>) filter.getSearch();
		}
		
		String query = "select "
				+ "tpconfirm_no_sap as [TP CONFIRMATION NO],"
				+ "stockconfirm_no_sap as [VENDOR STOCK CONFIRMATION], "
				+ "date as [DATE], "
				+ "v.full_name as [VENDOR NAME], "
				+ "CONCAT(h.id_sloc,' - ', s.name) as [SLOC], "
				+ "CONCAT(h.id_plant,' - ',s.plant_name) as [PLANT], "
				+ "h.doc_no as [TRANSFER POSTING NO], "
				+ "doc_date as [TRANSFER POSTING DATE], "
				+ "status as [STATUS], "
				+ "d.item_no as [ITEM NO], "
				+ "d.doc_item_no as [DOC ITEM NO], "
				+ "d.material_no as [MATERIAL NO], "
				+ "d.material_desc as [MATERIAL DESC], "
				+ "d.batch_no as [BATCH NO], "
				+ "d.qty as [QTY], "
				+ "d.uom as [UOM], "
				+ "CAST(d.notes AS VARCHAR) as [NOTES] "
				+ "from tp_confirm_header h "
				+ "LEFT JOIN vendor v ON v.id_plant = h.id_plant AND v.id_sloc = h.id_sloc AND v.id_sap = h.id_vendor "
				+ "LEFT join sloc s ON s.id_plant = h.id_plant and s.id_sloc = h.id_sloc "
				+ "inner join tp_confirm_detail d on h.id = d.id_tpconfirm_header "
				+ "WHERE h.date between :from and :to AND "
				+ "COALESCE(h.tpconfirm_no_sap,'') LIKE :tpcNo AND COALESCE(h.date,'') LIKE :date AND COALESCE(h.id_sloc,'') LIKE :idSloc AND "
				+ "COALESCE(h.id_plant,'') LIKE :idPlant AND COALESCE(h.doc_no,'') LIKE :tpNo AND COALESCE(h.status,'') LIKE :status AND "
				+ "COALESCE(h.stockconfirm_no_sap,'') LIKE :stockConfirm AND COALESCE(h.doc_date,'') LIKE :tpDate "
				+ setWhereParam(filter,0);
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
					.setDate("from", filter.getFrom())
					.setDate("to", filter.getTo())
					.setString("tpcNo", (!req.get("tpcNo").toString().isEmpty() ? "%"+req.get("tpcNo").toString() +"%" : "%%"))
					.setString("date", (!req.get("date").toString().isEmpty() ? "%"+req.get("date").toString()+"%" : "%%"))
					.setString("idSloc", (!req.get("idSloc").toString().isEmpty() ? "%"+req.get("idSloc").toString()+"%" : "%%"))
					.setString("idPlant", (!req.get("idPlant").toString().isEmpty() ? "%"+req.get("idPlant").toString()+"%" : "%%"))
					.setString("tpNo", (!req.get("tpNo").toString().isEmpty() ? "%"+req.get("tpNo").toString() +"%": "%%"))
					.setString("tpDate", (!req.get("tpDate").toString().isEmpty() ? "%"+req.get("tpDate").toString() +"%": "%%"))
					.setString("stockConfirm", (!req.get("stockConfirm").toString().isEmpty() ? "%"+req.get("stockConfirm").toString()+"%" : "%%"))
					.setString("status", (!req.get("status").toString().isEmpty() ? "%"+ req.get("status").toString() +"%": "%%"))
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	//new

	@Override
	public List<Object> TrasnferPostingForExport(FilterReport filter){
		List<Object> result = new ArrayList<Object>();
		Map<String,Object> req = new HashMap<String, Object>();
		if(filter.getSearch() != null) {
			req = (Map<String, Object>) filter.getSearch();
		}
		
		String query = "select h.id as [ID], "
				+ " h.date as [DATE],"
				+ " CONCAT(h.oem_from,' - ',(SELECT name from sloc where id_sloc =h.oem_from and id_plant = h.id_plant)) as [OEM FROM],"
				+ " CONCAT(h.oem_to,' - ',(SELECT name from sloc where id_sloc =h.oem_to and id_plant = h.id_plant)) as [OEM TO],"
				+ " h.doc_no as [TRANSFER POSTING NO], "
				+ " h.doc_date as [TRANSFER POSTING DATE], "
				+ " h.status as [STATUS], "
				+ " d.item_no as [ITEM NO], "
				+ " d.material_no as [MATERIAL NO], "
				+ " d.doc_item_no as [DOC ITEM NO], "
				+ " d.material_desc as [MATERIAL DESC], "
				+ " d.batch_no as [BATCH NO], "
				+ " d.order_qty as [ORDER QTY] "
				+ " from tp_header h "
				+ " LEFT JOIN tp_detail d ON h.id = d.id_tp_header "
				+ " WHERE h.date between :from and :to AND "
				+ " COALESCE(h.id,'') LIKE :id AND COALESCE(h.date,'') LIKE :date AND COALESCE(h.oem_from,'') LIKE :oemFrom AND "
				+ " COALESCE(h.oem_to,'') LIKE :oemTo AND COALESCE(h.doc_no,'') LIKE :tpNo AND "
				+ " COALESCE(h.doc_date,'') LIKE :tpDate and COALESCE(h.status,'') LIKE :status " 
				+ setWhereParam(filter,0);
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
					.setDate("from", filter.getFrom())
					.setDate("to", filter.getTo())
					.setString("id", (!req.get("id").toString().isEmpty() ?  "%"+ req.get("id").toString()+ "%" : "%%"))
					.setString("date", (!req.get("date").toString().isEmpty() ? "%"+req.get("date").toString()+ "%" : "%%"))
					.setString("oemFrom", (!req.get("oemFrom").toString().isEmpty() ? "%"+req.get("oemFrom").toString()+ "%" : "%%"))
					.setString("oemTo", (!req.get("oemTo").toString().isEmpty() ?"%"+ req.get("oemTo").toString()+ "%" : "%%"))
					.setString("tpNo", (!req.get("tpNo").toString().isEmpty() ? "%"+req.get("tpNo").toString()+ "%" : "%%"))
					.setString("tpDate", (!req.get("tpDate").toString().isEmpty() ? "%"+req.get("tpDate").toString()+ "%" : "%%"))
					.setString("status", (!req.get("status").toString().isEmpty() ? "%"+req.get("status").toString()+ "%" : "%%"))
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Object> shipmentExport(FilterReport filter) {
		List<Object> result = new ArrayList<Object>();
		Map<String,Object> req = new HashMap<String, Object>();
		if(filter.getSearch() != null) {
			req = (Map<String, Object>) filter.getSearch();
		}
		
		String query = "select h.shipment_no as [SHIPMENT NO],"
				+ "shipment_date as [SHIPMENT DATE],"
				+ "CONCAT(h.id_sloc,' - ', s.name) as [SLOC],"
				+ "CONCAT(h.id_plant,' - ',s.plant_name) as [PLANT],"
				+ "status as [STATUS], "
				+ "d.do_no as [DO NO], "
				+ "d.do_date as [DO DATE]"
				+ "from shipment_header h "
				+ "LEFT join sloc s ON s.id_plant = h.id_plant and s.id_sloc = h.id_sloc "
				+ "INNER JOIN shipment_detail d ON h.shipment_no = d.shipment_no "
				+ "WHERE shipment_date between :from and :to AND " 
				+ "COALESCE(h.shipment_no,'') LIKE :shipmentNo AND COALESCE(h.shipment_date,'') LIKE :shipmentDate AND "
				+ "COALESCE(h.id_sloc,'') LIKE :idSloc AND COALESCE(h.id_plant,'') LIKE :idPlant AND COALESCE(h.status,'') LIKE :status "
				+ setWhereParam(filter,1);
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
					.setDate("from", filter.getFrom())
					.setDate("to", filter.getTo())
					.setString("shipmentNo", (!req.get("shipmentNo").toString().isEmpty() ? "%"+req.get("shipmentNo").toString()+"%" : "%%"))
					.setString("shipmentDate", (!req.get("shipmentDate").toString().isEmpty() ? "%"+req.get("shipmentDate").toString()+"%" : "%%"))
					.setString("idSloc", (!req.get("idSloc").toString().isEmpty() ? "%"+req.get("idSloc").toString()+"%" : "%%"))
					.setString("idPlant", (!req.get("idPlant").toString().isEmpty() ? "%"+req.get("idPlant").toString()+"%" : "%%"))
					.setString("status", (!req.get("status").toString().isEmpty() ? "%"+req.get("status").toString()+"%" : "%%"))
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	@Override
	public List<Object> deliveryOrderForExport(FilterReport filter) {
		List<Object> result = new ArrayList<Object>();
		Map<String,Object> req = new HashMap<String, Object>();
		if(filter.getSearch() != null) {
			req = (Map<String, Object>) filter.getSearch();
		}
		
		String query = "select id as [ID], "
				+ " do_no_sap as [DO NO], "
				+ " date as [DATE], "
				+ " CONCAT(h.id_sloc,' - ', s.name) as [SLOC],"
				+ " destination as [DESTINATION],  "
				+ " h.doc_no as [DOC NO], "
				+ " doc_date as [DOC DATE], "
				+ " CASE WHEN CAST(doc_type AS VARCHAR)='SOBU' THEN 'SO' WHEN CAST(doc_type AS VARCHAR)='STO' "
				+ " THEN 'PO STO' ELSE '' END as [DOC TYPE], "
				+ " pgi_no_sap as [PGI NO], "
				+ " pod_status_desc as [POD STATUS],"
				+ " h.status as [STATUS],"
				+ " d.item_no as [ITEM NO],"
				+ " d.doc_item_no as [DOC ITEM NO],"
				+ " material_no as [MATERIAL NO], "
				+ " material_desc as [MATERIAL DESC],"
				+ " order_qty as [ORDER QTY], "
				+ " d.uom as [ORDER UOM], "
				+ " do_qty as [DO QTY],"
				+ " d.uom as [DO UOM],"
				+ " b.batch_no as [BATCH NO], "
				+ " b.actual_qty as [ACTUAL QTY], "
				+ " b.uom as [ACTUAL UOM]"
				+ " from do_header h "
				+ " LEFT join sloc s ON s.id_plant = h.id_plant and s.id_sloc = h.id_sloc "
				+ " INNER JOIN do_detail d on h.id = d.id_do_header "
				+ " LEFT JOIN do_batch B on h.id = b.id_do_header and d.doc_item_no = b.item_no_detail" 
				+ " WHERE date between :from and :to AND "
				+ " COALESCE(h.do_no_sap,'') LIKE :doNo AND COALESCE(h.date,'') LIKE :date AND COALESCE(h.id_sloc,'') LIKE :idSloc AND "
				+ " COALESCE(h.destination,'') LIKE :destination AND COALESCE(h.doc_no,'') LIKE :docNo AND "
				+ " COALESCE(h.doc_date,'') LIKE :docDate AND COALESCE(h.doc_type,'') LIKE :docType  and COALESCE(h.status,'') LIKE :status AND "
				+ " COALESCE(h.pgi_no_sap,'') LIKE :pgiNo AND COALESCE(h.pod_status_desc,'') LIKE :podStatus "
				+ setWhereParam(filter,0);
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
					.setDate("from", filter.getFrom())
					.setDate("to", filter.getTo())
					.setString("doNo", (!req.get("doNo").toString().isEmpty() ? "%"+req.get("doNo").toString()+"%" : "%%"))
					.setString("date", (!req.get("date").toString().isEmpty() ? "%"+req.get("date").toString()+"%" : "%%"))
					.setString("idSloc", (!req.get("idSloc").toString().isEmpty() ? "%"+req.get("idSloc").toString()+"%" : "%%"))
					.setString("destination", (!req.get("destination").toString().isEmpty() ?"%"+ req.get("destination").toString()+"%" : "%%"))
					.setString("docNo", (!req.get("docNo").toString().isEmpty() ? "%"+req.get("docNo").toString()+"%" : "%%"))
					.setString("docDate", (!req.get("docDate").toString().isEmpty() ? "%"+req.get("docDate").toString()+"%" : "%%"))
					.setString("docType", (!req.get("docType").toString().isEmpty() ? "%"+req.get("docType").toString()+"%" : "%%"))
					.setString("pgiNo", (!req.get("pgiNo").toString().isEmpty() ? "%"+req.get("pgiNo").toString()+"%" : "%%"))
					.setString("podStatus", (!req.get("podStatus").toString().isEmpty() ? "%"+req.get("podStatus").toString()+"%" : "%%"))
					.setString("status", (!req.get("status").toString().isEmpty() ? "%"+req.get("status").toString()+"%" : "%%"))
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public List<Object> GRFGForExport(FilterReport filter) {
		List<Object> result = new ArrayList<Object>();
		Map<String,Object> req = new HashMap<String, Object>();
		if(filter.getSearch() != null) {
			req = (Map<String, Object>) filter.getSearch();
		}
		
		String query = "SELECT "
				+ "h.gr_no_sap as [GR FG NO],"
				+ "h.date as [DATE], "
				+ "CONCAT(h.id_sloc,' - ', s.name) as [SLOC], "
				+ "CONCAT(h.id_plant,' - ',s.plant_name) as [PLANT], "
				+ "h.doc_no as [PURCHASE ORDER NO], "
				+ "h.doc_date as [PURCHASE ORDER DATE],"
				+ "h.doc_type as [DOC TYPE],"
				+ "d.item_no as [ITEM NO], "
				+ "d.doc_item_no as [DOC ITEM NO],"
				+ "d.material_no as [MATERIAL NO], "
				+ "material_desc as [MATERIAL DESC], "
				+ "prod_target_date as [PROD TARGET DATE], "
				+ "order_qty as [ORDER QTY],  "
				+ "outstanding_qty as [OUTSTANDING QTY], "
				+ "gr_qty as [GR QTY], "
				+ "uom as [UOM], "
				+ "batch_no as [BATCH NO], "
				+ "exp_date as [EXPIRED DATE] "
				+ "FROM gr_fg_header h  "
				+ "INNER JOIN gr_fg_detail d on h.id = d.id_gr_header "
				+ "LEFT join sloc s ON s.id_plant = h.id_plant and s.id_sloc = h.id_sloc "
				+ "WHERE date between :from and :to AND " 
				+ "COALESCE(h.gr_no_sap,'') LIKE :grNoSap AND COALESCE(h.date,'') LIKE :date AND COALESCE(h.id_sloc,'') LIKE :idSloc AND "
				+ "COALESCE(h.id_plant,'') LIKE :idPlant AND COALESCE(h.doc_no,'') LIKE :purchOrderNo AND COALESCE(h.doc_date,'') LIKE :purchOrderDate and "
				+ "COALESCE(h.status,'') LIKE :status "
				+ setWhereParam(filter,0);
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
					.setDate("from", filter.getFrom())
					.setDate("to", filter.getTo())
					.setString("grNoSap", (!req.get("grNoSap").toString().isEmpty() ? "%"+req.get("grNoSap").toString()+"%" : "%%"))
					.setString("date", (!req.get("date").toString().isEmpty() ? "%"+req.get("date").toString()+"%" : "%%"))
					.setString("idSloc", (!req.get("idSloc").toString().isEmpty() ? "%"+req.get("idSloc").toString()+"%" : "%%"))
					.setString("idPlant", (!req.get("idPlant").toString().isEmpty() ? "%"+req.get("idPlant").toString()+"%" : "%%"))
					.setString("purchOrderNo", (!req.get("purchOrderNo").toString().isEmpty() ? "%"+req.get("purchOrderNo").toString()+"%" : "%%"))
					.setString("purchOrderDate", (!req.get("purchOrderDate").toString().isEmpty() ? "%"+req.get("purchOrderDate").toString()+"%" : "%%"))
					.setString("status", (!req.get("status").toString().isEmpty() ? "%"+req.get("status").toString()+"%" : "%%"))
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Object> GRPOSTOForExport(FilterReport filter) {
		List<Object> result = new ArrayList<Object>();
		Map<String,Object> req = new HashMap<String, Object>();
		if(filter.getSearch() != null) {
			req = (Map<String, Object>) filter.getSearch();
		}
		
		String query = "select h.id as [ID], "
				+ " h.gr_no_sap as [GR NO SAP], "
				+ " h.date as [DATE], "
				+ " CONCAT(h.id_sloc,' - ', s.name) as [SLOC], "
				+ " CONCAT(h.id_plant,' - ',s.plant_name) as [PLANT], "
				+ " h.doc_no as [PO STO NO], "
				+ " h.doc_date as [PO STO DATE], "
				+ " h.status as [STATUS], "
				+ " d.do_no_sap as [DELIVERY ORDER NO], "
				+ " d.item_no as [DO ITEM NO], "
				+ " d.material_no as [MATERIAL NO], "
				+ " d.material_desc as [MATERIAL DESC], "
				+ " d.exp_date as [EXPIRED DATE], "
				+ " d.batch_no as [BATCH NO], "
				+ " d.qty as [ORDER QTY], "
				+ " d.uom as [UOM], "
				+ " d.variance as [VARIANCE], "
				+ " d.reason as [REASON] "
				+ " from gr_sto_header h "
				+ " LEFT join sloc s ON s.id_plant = h.id_plant and s.id_sloc = h.id_sloc "
				+ " LEFT join gr_sto_detail d on h.id = id_gr_header "
				+ " WHERE date between :from and :to AND " 
				+ " COALESCE(h.id,'') LIKE :id AND COALESCE(h.date,'') LIKE :date AND COALESCE(h.id_sloc,'') LIKE :idSloc AND "
				+ " COALESCE(h.id_plant,'') LIKE :idPlant AND COALESCE(h.doc_no,'') LIKE :poStoNo AND "
				+ " COALESCE(h.doc_date,'') LIKE :poStoDate and COALESCE(h.status,'') LIKE :status "
				+ setWhereParam(filter,0);
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
					.setDate("from", filter.getFrom())
					.setDate("to", filter.getTo())
					.setString("id", (!req.get("id").toString().isEmpty() ? "%"+req.get("id").toString()+"%" : "%%"))
					.setString("date", (!req.get("date").toString().isEmpty() ? "%"+req.get("date").toString()+"%" : "%%"))
					.setString("idSloc", (!req.get("idSloc").toString().isEmpty() ? "%"+req.get("idSloc").toString()+"%" : "%%"))
					.setString("idPlant", (!req.get("idPlant").toString().isEmpty() ? "%"+req.get("idPlant").toString()+"%" : "%%"))
					.setString("poStoNo", (!req.get("poStoNo").toString().isEmpty() ? "%"+req.get("poStoNo").toString()+"%" : "%%"))
					.setString("poStoDate", (!req.get("poStoDate").toString().isEmpty() ?"%"+ req.get("poStoDate").toString()+"%" : "%%"))
					.setString("status", (!req.get("status").toString().isEmpty() ? "%"+req.get("status").toString()+"%" : "%%"))
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
