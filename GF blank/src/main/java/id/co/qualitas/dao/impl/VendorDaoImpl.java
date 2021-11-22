package id.co.qualitas.dao.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import id.co.qualitas.dao.BaseDao;
import id.co.qualitas.dao.interfaces.DeviceDao;
import id.co.qualitas.dao.interfaces.EmployeeDao;
import id.co.qualitas.dao.interfaces.VendorDao;
import id.co.qualitas.domain.request.DODetail;
import id.co.qualitas.domain.response.Vendor;
import id.co.qualitas.domain.response.WSMessage;

@Repository
@Transactional
public class VendorDaoImpl extends BaseDao implements VendorDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Vendor getVendorDetail(String idSloc, String idPlant) {
		String query = "SELECT TOP 1 id_sap as idSap, full_name as fullName, id_sloc as idSloc, id_plant as idPlant, po_type as poType, "
				+ " address, phone, email, plant_name as plantName, sloc_name as slocName, city, postal_code as postalCode FROM vendor "
				+ "WHERE id_sloc = :idSloc AND "
				+ " id_plant = :idPlant AND "
				+ "	enabled = 1 AND "
				+ "	deleted = 0 ";
		
		Session session = sessionFactory.getCurrentSession();
		return (Vendor) session.createSQLQuery(query)
				.setString("idSloc", idSloc)
				.setString("idPlant", idPlant)
				.setResultTransformer(Transformers.aliasToBean(Vendor.class))
				.uniqueResult();
	}
	

	@Override
	public Vendor getSlocDetail(String idSloc, String idPlant) {
		String query = ""
				+ " SELECT TOP 1 s.id_sloc as idSloc,"
				+ " v.address, v.phone, s.name as slocName "
				+ "FROM sloc s "
				+ "LEFT JOIN VENDOR v ON s.id_sloc = v.id_sloc "
				+ "WHERE s.id_sloc = :idSloc AND "
				+ " v.id_plant = :idPlant AND"
				+ "	s.enabled = 1 AND "
				+ "	s.deleted = 0 ";
		
		Session session = sessionFactory.getCurrentSession();
		return (Vendor) session.createSQLQuery(query)
				.setString("idSloc", idSloc)
				.setString("idPlant", idPlant)
				.setResultTransformer(Transformers.aliasToBean(Vendor.class))
				.uniqueResult();
	}

	@Override
	public String getIdVendorSapByUsername(String username) {
		String query = "SELECT TOP 1 id_sap FROM vendor "
				+ "WHERE id = :username AND "
				+ "	enabled = 1 AND "
				+ "	deleted = 0 ";
		
		Session session = sessionFactory.getCurrentSession();
		return (String) session.createSQLQuery(query)
				.setString("username", username)
				.uniqueResult();
	}

}

