package id.co.qualitas.dao.impl;

import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import id.co.qualitas.dao.BaseDao;
import id.co.qualitas.dao.interfaces.EmployeeDao;

@Repository
@Transactional
public class EmployeeDaoImpl extends BaseDao implements EmployeeDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public String getType(String username) {
		String query = "SELECT type "
				+ "FROM Users "
				+ "WHERE username = :username";
		
		Session session = sessionFactory.getCurrentSession();
		return (String) session.createSQLQuery(query)
				.setString("username", username)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getDetailAsEmployee(String username) {
		String query = "SELECT a.id, full_name as fullName, a.id_sloc as idSloc, a.id_plant as idPlant, "
				+ "p.name as plantName, c.name as slocName, c.loading_point as loadingPoint, a.photo, u.changed as isReset "
				+ "FROM Employee a "
				+ "LEFT JOIN Sloc c on a.id_sloc = c.id_sloc and a.id_plant = c.id_plant "
				+ "LEFT JOIN Plant p on p.id = a.id_plant "
				+ "JOIN Users u on a.id = u.username "
				+ "WHERE a.id = :username ";
		
		Session session = sessionFactory.getCurrentSession();
		return (Map<String, Object>) session.createSQLQuery(query)
				.setString("username", username)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getDetailAsVendor(String username) {	
		String query = "SELECT a.id, full_name as fullName, c.id_sloc as idSloc, c.id_plant as idPlant, "
				+ "po_type as poType, c.plant_name as plantName, c.name as slocName, c.loading_point as loadingPoint, u.changed as isReset, a.id_sap as idVendor "
				+ "FROM Vendor a "
				+ "LEFT JOIN Sloc c on a.id_sloc = c.id_sloc and a.id_plant = c.id_plant "
				+ "JOIN Users u on a.id = u.username "
				+ "WHERE a.id = :username ";
		
		Session session = sessionFactory.getCurrentSession();
		return (Map<String, Object>) session.createSQLQuery(query)
				.setString("username", username)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.uniqueResult();
	}
}

