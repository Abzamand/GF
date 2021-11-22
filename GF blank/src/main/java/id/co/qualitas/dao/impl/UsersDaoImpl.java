package id.co.qualitas.dao.impl;

import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import id.co.qualitas.dao.BaseDao;
import id.co.qualitas.dao.interfaces.EmployeeDao;
import id.co.qualitas.dao.interfaces.UsersDao;
import id.co.qualitas.domain.request.Users;
import id.co.qualitas.domain.response.WSMessage;

@Repository
@Transactional
public class UsersDaoImpl extends BaseDao implements UsersDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Users findUser(int username) {
		String query = "SELECT username, password FROM users "
				+ "WHERE username = :username ";
		
		Session session = sessionFactory.getCurrentSession();
		return (Users) session.createSQLQuery(query)
				.setInteger("username", username)
				.setResultTransformer(Transformers.aliasToBean(Users.class))
				.uniqueResult();
	}

	@Override
	public WSMessage changePassword(Users user) {
		WSMessage result = new WSMessage();
		Session session = sessionFactory.getCurrentSession();
		
		String query = "update users "
				+ "set password = :password, changed = 0, updated_by = :username, updated_date = GETDATE() where username = :username";
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			session.createSQLQuery(query)
					.setString("password", user.getPassword())
					.setInteger("username", user.getUsername())
					.executeUpdate();

			tx.commit();
			result.setIdMessage(1);
			result.setMessage("Success updated password");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			result.setIdMessage(0);
			result.setMessage(e.getMessage());
		}

		return result;
	}
}

