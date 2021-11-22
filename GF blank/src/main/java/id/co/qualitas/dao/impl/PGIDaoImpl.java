package id.co.qualitas.dao.impl;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import id.co.qualitas.dao.BaseDao;
import id.co.qualitas.dao.interfaces.PGIDao;
import id.co.qualitas.domain.request.PGIDetail;
import id.co.qualitas.domain.request.PGIHeader;
import id.co.qualitas.domain.request.PGIRequest;
import id.co.qualitas.domain.response.WSMessage;

@Repository
@Transactional
public class PGIDaoImpl extends BaseDao implements PGIDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public WSMessage create(PGIRequest request) {
		WSMessage message = new WSMessage();
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;
		int idHeader = 0;

		try {
			tx = session.beginTransaction();

			PGIHeader header = new PGIHeader();
			header = request.getHeader();

			String queryCreateHeader2 = "INSERT INTO shipment_header "
					+ "(shipment_no, shipment_date, id_sloc, id_plant, vendor_forwarding, plate_no, "
					+ "vehicle_type, status, created_by, created_date, enabled, deleted) "
					+ "VALUES (:shipmentNo, :shipmentDate, :idSloc, :idPlant, :vendorForwarding, :plateNo, "
					+ ":vehicleType, 'On Progress', :createdBy, GETDATE(), 1, 0) ";

			session.createSQLQuery(queryCreateHeader2).setString("shipmentNo", header.getShipmentNo())
					.setString("shipmentDate", header.getShipmentDate()).setString("idSloc", header.getIdSloc())
					.setString("idPlant", header.getIdPlant())
					.setString("vendorForwarding", header.getVendorForwarding())
					.setString("plateNo", header.getPlateNo()).setString("vehicleType", header.getVehicleType())
					.setString("createdBy", request.getCreatedBy()).executeUpdate();

			String queryCreateDetail2 = "INSERT INTO shipment_detail "
					+ "(shipment_no, do_no, do_date, id_sloc, id_plant, created_by, created_date, enabled, deleted) "
					+ "VALUES (:shipmentNo, :doNo, :doDate, :idSloc, :idPlant, :createdBy, GETDATE(), 1, 0) ";

			for (int i = 0; i < request.getListDetail().size(); i++) {
				PGIDetail detail = new PGIDetail();
				detail = request.getListDetail().get(i);

				session.createSQLQuery(queryCreateDetail2).setString("shipmentNo", detail.getShipmentNo())
						.setString("doNo", detail.getDoNo()).setString("doDate", detail.getDoDate())
						.setString("idSloc", detail.getIdSloc()).setString("idPlant", detail.getIdPlant())
						.setString("createdBy", request.getCreatedBy()).executeUpdate();
			}

			message.setIdMessage(1);
			message.setMessage("Post good issues created");

			tx.commit();
		} catch (Exception e) {
			tx.rollback();

			message.setIdMessage(0);
			message.setMessage(e.getMessage());
		}
		return message;
	}

}
