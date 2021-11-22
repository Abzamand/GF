package id.co.qualitas.dao.impl;

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
import id.co.qualitas.domain.response.WSMessage;

@Repository
@Transactional
public class DeviceDaoImpl extends BaseDao implements DeviceDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public WSMessage checkIsValidForLogin(String username, String deviceId) {
			String query = "SELECT COUNT(1) "
					+ "FROM Device "
					+ "WHERE username = :username AND ssaid = :deviceId ";
		
		Session session = sessionFactory.getCurrentSession();

		WSMessage message = new WSMessage();
		
		try {

			int result = (int) session.createSQLQuery(query)
				.setString("username", username)
				.setString("deviceId", deviceId)
				.uniqueResult();
			
			message.setIdMessage(result);

		}catch (Exception e) {
			message.setIdMessage(0);
		}
		
		
		return message;
	}
}

