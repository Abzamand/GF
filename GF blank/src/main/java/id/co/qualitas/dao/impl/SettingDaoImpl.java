package id.co.qualitas.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import id.co.qualitas.component.ConsParam;
import id.co.qualitas.component.Utils;
import id.co.qualitas.dao.BaseDao;
import id.co.qualitas.dao.interfaces.SettingDao;
import id.co.qualitas.domain.Sloc;

@Repository
@Transactional
public class SettingDaoImpl extends BaseDao implements SettingDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Object getProfile(String username) {
		Map<String,Object> result = new HashMap<String, Object>();
		String cekUser = "SELECT type FROM users WHERE username=:username ";
		String getEmployee = "SELECT id,full_name as fullName,email from employee where id=:username ";
		String getVendor = "SELECT id,full_name as fullName,email,phone from vendor where id=:username ";
		
		try {
			Session session = sessionFactory.getCurrentSession();
			String type =  (String) session.createSQLQuery(cekUser)
							.setString("username",username)
							.uniqueResult();
			
			if(type.equals("E")) {
				result = (Map<String, Object>) session.createSQLQuery(getEmployee)
					.setString("username",username)
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.uniqueResult();
			}else {
				result = (Map<String, Object>) session.createSQLQuery(getVendor)
						.setString("username",username)
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
						.uniqueResult();
			}
			
			File files = new File(ConsParam.PATH_IMAGE + result.get("id").toString() + ".png");
			byte[] bytesArray = null;
			String ext = null;
			if(files != null && files.length() > 0){
				bytesArray = new byte[(int) files.length()];
				ext = FilenameUtils.getExtension(files.getName()); 
				FileInputStream fis;
				try {
					fis = new FileInputStream(files);
					fis.read(bytesArray); // read file into bytes[]
					fis.close();
				} catch (FileNotFoundException e) {
					bytesArray = null;
					System.out.println("file not found");
				} catch (IOException e) {
					bytesArray = null;
					e.printStackTrace();
				}
				result.put("photo",bytesArray);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public Boolean updateProfile(Object req) {
		Session session =  sessionFactory.getCurrentSession();
		boolean result = true;
		Transaction tx = null;	
		Map<String,Object> request = (Map<String, Object>) req;
		String cekUser = "SELECT type FROM users WHERE username=:username ";
		String updateEmployee = "UPDATE employee set full_name =:name, email=:email,updated_by=:updatedBy, updated_date = CURRENT_TIMESTAMP where id =:username ";
		String updateVendor = "UPDATE vendor set full_name =:name, email=:email,updated_by=:updatedBy, updated_date = CURRENT_TIMESTAMP, phone=:phone where id =:username ";
		
		try {
			tx = session.beginTransaction();
			
			String type = (String) session.createSQLQuery(cekUser)
					.setString("username",request.get("username").toString())
					.uniqueResult();
			
			if(type.equals("E")) {
				session.createSQLQuery(updateEmployee)
					.setString("name", request.get("fullName").toString())
					.setString("email", request.get("email").toString())
					.setString("updatedBy",request.get("username").toString())
					.setString("username",request.get("username").toString())
					.executeUpdate();
			}else {
				session.createSQLQuery(updateVendor)
					.setString("name", request.get("fullName").toString())
					.setString("email", request.get("email").toString())
					.setString("phone", request.get("phone").toString())
					.setString("updatedBy",request.get("username").toString())
					.setString("username",request.get("username").toString())
					.executeUpdate();
			}
			
			if(request.get("photoString") != null){
				byte[] photo = Base64.decodeBase64(request.get("photoString").toString());
	    		Path pathRequestImage = Paths.get(ConsParam.PATH_IMAGE + request.get("username").toString() + ".png" );
		    	Files.write(pathRequestImage, photo);
	    	}
			
			tx.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			if (tx!=null) tx.rollback();
			result = false;
		}
		
		return result;
	}

	@Override
	public String getCurrentPassword(String username) {
		Transaction tx = null;	
		String result = "";
		String query = "SELECT password from users where username =:username ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  (String) session.createSQLQuery(query)
							.setString("username",username)
							.uniqueResult();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public Boolean changePassword(Object req) {
		Session session =  sessionFactory.getCurrentSession();
		boolean result = true;
		Transaction tx = null;	
		Map<String,Object> request = (Map<String, Object>) req;
		String query = "UPDATE users set password =:pass,updated_by=:username,updated_date = CURRENT_TIMESTAMP where username =:username ";
		
		try {
			tx = session.beginTransaction();
			
			session.createSQLQuery(query)
				.setString("username", request.get("username").toString())
				.setString("pass", Utils.hashPassword(request.get("newPassword").toString()))
				.executeUpdate();
			
			tx.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			if (tx!=null) tx.rollback();
			result = false;
		}
		
		return result;
	}

}
