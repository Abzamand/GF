package id.co.qualitas.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import id.co.qualitas.component.ConsMessage;
import id.co.qualitas.component.ConsParam;
import id.co.qualitas.component.Constants;
import id.co.qualitas.component.Utils;
import id.co.qualitas.dao.BaseDao;
import id.co.qualitas.dao.interfaces.OldEmployeeDao;
import id.co.qualitas.domain.EmployeeRequest;
import id.co.qualitas.domain.response.WSMessage;

@Repository
@Transactional
public class OldEmployeeDaoImpl extends BaseDao implements OldEmployeeDao {
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getDetail(String username) {
		String query = 
				"SELECT a.id, a.full_name as name "
				+ "FROM Employee a "
				+ "JOIN Users b ON a.id = b.username "
				+ "WHERE b.username = :username AND b.type = 'employee' "
				+ "UNION "
				+ "SELECT a.id, full_name as name, id_sloc as idSloc, id_plant as idPlant, po_type as poType, b.name as plantName, "
				+ "c.name as slocName "
				+ "FROM Vendor a "
				+ "JOIN Plant b ON a.id_plant = b.id "
				+ "JOIN Sloc c ON a.id_sloc = c.id "
				+ "JOIN Users d ON a.id = d.username "
				+ "WHERE a.id = :username AND d.type = 'vendor' ";
		
		Session session = sessionFactory.getCurrentSession();
		return (Map<String, Object>) session.createSQLQuery(query)
				.setString("username", username)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.uniqueResult();
	}

	@Override
	public List<Map<String, Object>> getListEmployee(Object request) {
		Map requestMap = (Map) request;
		Map search = (Map) requestMap.get("search");
		String id = search.get("id") == null ? "" : search.get("id").toString(), 
			   name = search.get("name") == null ? "" : search.get("name").toString(), 
			   position = 	search.get("position") == null ? "" : search.get("position").toString(),
			   commodity = 	search.get("commodity") == null ? "" : search.get("commodity").toString(),
			   role = 	search.get("role") == null ? "" : search.get("role").toString();;
		String query = "SELECT a.id, a.full_name, b.userlogin, a.id_position, "
				+ "string_agg(DISTINCT d.name, ', ') commodity, "
				+ "string_agg(DISTINCT e.group_id, ', ') roles, "
				+ "count(*) OVER() AS \"totalPage\" "
				+ "FROM employee a "
				+ "INNER JOIN users b ON b.username = a.id AND b.deleted = 0 AND b.enabled = 1 "
				+ "LEFT JOIN assign_commodity c on c.id_user = a.id "
				+ "LEFT JOIN commodity d on d.id = c.id_commodity "
				+ "LEFT JOIN group_members e ON e.username = a.id "
				+ "WHERE a.deleted = 0 "
				+ "AND LOWER(b.userlogin) like LOWER(:id) "
				+ "AND LOWER(a.full_name) like LOWER(:name) "
				+ "AND LOWER(a.id_position) like LOWER(:position) "
				+ "GROUP BY a.id, a.full_name, b.userlogin, a.id_position "
				+ "HAVING LOWER(string_agg(DISTINCT COALESCE(d.name,''), ', ')) like LOWER(:commodity) "
				+ "AND LOWER(string_agg(DISTINCT COALESCE(e.group_id,''), ', ')) like LOWER(:role) "
				+ "ORDER BY a.created_date desc "
				+ "LIMIT :requestPage OFFSET :page";
		Session session = sessionFactory.getCurrentSession();
		return session.createSQLQuery(query)
				.setInteger("requestPage", (int) requestMap.get("requestPage"))
				.setInteger("page", (int) requestMap.get("page"))
				.setString("id", "%" + id + "%")
				.setString("name", "%" + name + "%")
				.setString("position", "%" + position + "%")
				.setString("commodity", "%" + commodity + "%")
				.setString("role", "%" + role + "%")
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.list();
	}

	@Override
	public Object getDetailEmployee(Object request) {
		String id = ((Map<String, Object>)request).get("searchId").toString();
		Map<String , Object> result = new HashMap<String, Object>();
		String queryEmployee = "SELECT id, full_name, id_position, email, enabled, TO_CHAR(updated_date, 'YYYY-MM-DD HH24:MI:SS') updated_date "
				+ "FROM employee WHERE id = :id AND deleted = 0";
		String queryTypeCatalog = "SELECT a.id_commodity, b.name, c.description, a.type_catalog "
				+ "FROM assign_commodity a "
				+ "INNER JOIN commodity b ON b.id = a.id_commodity "
				+ "INNER JOIN type_catalog c ON c.id_commodity = a.id_commodity AND c.id = a.type_catalog "
				+ "WHERE a.id_user = :id ORDER BY a.id_commodity, a.type_catalog";
		String queryRole = "SELECT a.group_id, b.group_name FROM group_members a "
				+ "INNER JOIN groups b ON b.id = a.group_id "
				+ "WHERE a.username = :id";
		Session session = sessionFactory.getCurrentSession();
		result = (Map<String, Object>) session.createSQLQuery(queryEmployee)
				.setString("id", id)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.uniqueResult();
		result.put("listCatalog", session.createSQLQuery(queryTypeCatalog)
				.setString("id", id)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.list());
		result.put("listRole", session.createSQLQuery(queryRole)
				.setString("id", id)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.list());
		File folder = new File(ConsParam.PATH_IMAGE + id + ConsParam.PATH_PHOTO);
		File[] files = folder.listFiles();
		byte[] bytesArray = null;
		String ext = null;
		if(files != null && files.length > 0){
			bytesArray = new byte[(int) files[0].length()];
			ext = FilenameUtils.getExtension(files[0].getName()); 
			FileInputStream fis;
			try {
				fis = new FileInputStream(files[0]);
				fis.read(bytesArray); // read file into bytes[]
				fis.close();
			} catch (FileNotFoundException e) {
				bytesArray = null;
				System.out.println("file not found");
			} catch (IOException e) {
				bytesArray = null;
				e.printStackTrace();
			}
			result.put("photo", bytesArray);
			result.put("photoFilename", files[0].getName());
			result.put("photoExt", ext);
		}
		folder = new File(ConsParam.PATH_IMAGE + id + ConsParam.PATH_CPROFILE);
		files = folder.listFiles();
		if(files != null && files.length > 0){
			result.put("cProfileFilename", files[0].getName());
		}
		return result;
	}
	
	@Override
	public Object saveEmployee(Object request) {
		WSMessage result = new WSMessage();
		EmployeeRequest requestMap =  (EmployeeRequest) request;
		Session session =  sessionFactory.getCurrentSession();
		Transaction tx = null;
		String id = null;
		
		try {
			List<Map<String, Object>> listCatalog = (List<Map<String, Object>>) requestMap.getListCatalog();
			List<Map<String, Object>> listRole = (List<Map<String, Object>>) requestMap.getListRole();
			if(listCatalog.size() == 0){
				result.setIdMessage(0);
				result.setMessage(ConsMessage.EMPTY_ITEM);
				return result;
			}
			if(listRole.size() == 0){
				result.setIdMessage(0);
				result.setMessage(ConsMessage.EMPTY_ITEM);
				return result;
			}
			
			tx = session.beginTransaction();
			id =  (String) session.createSQLQuery("INSERT INTO users "
				+ "(userlogin, password, tipe, created_by) "
				+ "VALUES "
				+ "(:email, :password, 'E', :username) "
				+ "RETURNING username")
					.setString("email", requestMap.getEmail())
//					.setString("password", Utils.hashPassword(requestMap.getPassword()))
					.setString("password", Utils.hashPassword(ConsParam.DEFAULT_PASS))
					.setString("username", requestMap.getUsername())
					.uniqueResult();
			if(id != null){
				File folder = new File(ConsParam.PATH_IMAGE + id);
				if(!folder.exists()){
					folder.mkdir();
				}
				if(requestMap.getPhoto() != null){
					folder = new File(ConsParam.PATH_IMAGE + id +  ConsParam.PATH_PHOTO);
					if(!folder.exists()){
						folder.mkdir();
					}
		    		Path pathRequestImage = Paths.get(ConsParam.PATH_IMAGE + id +  ConsParam.PATH_PHOTO + requestMap.getPhotoFilename());
			    	Files.write(pathRequestImage, requestMap.getPhoto());
		    	}
				if(requestMap.getcProfile() != null){
					folder = new File(ConsParam.PATH_IMAGE + id +  ConsParam.PATH_CPROFILE);
					if(!folder.exists()){
						folder.mkdir();
					}
		    		Path pathRequestImage = Paths.get(ConsParam.PATH_IMAGE + id +  ConsParam.PATH_CPROFILE + requestMap.getcProfileFilename());
			    	Files.write(pathRequestImage, requestMap.getcProfile());
		    	}
				session.createSQLQuery("INSERT INTO employee "
						+ "(id, full_name, id_position, email, enabled, created_by) "
						+ "VALUES "
						+ "(:id, :name, :position, :email, :enabled, :username)")
							.setString("id", id)
							.setString("name", requestMap.getFull_name())
							.setString("email", requestMap.getEmail())
							.setInteger("enabled", requestMap.getEnabledEmployee())
							.setString("position", requestMap.getId_position())
							.setString("username", requestMap.getUsername())
							.executeUpdate();
				for(Map<String, Object> temp : listCatalog){
					 session.createSQLQuery("INSERT INTO assign_commodity "
						+ "(id_user, id_commodity, type_catalog, created_by) "
						+ "VALUES "
						+ "(:id, :id_commodity, :type_catalog, :username) ")
						.setString("id", id)
						.setString("id_commodity", temp.get("id_commodity").toString())
						.setString("type_catalog", temp.get("id").toString())
						.setString("username", requestMap.getUsername())
						.executeUpdate();
				}
				for(Map<String, Object> temp : listRole){
					 session.createSQLQuery("INSERT INTO group_members "
								+ "(group_id, username, created_by) "
								+ "VALUES "
								+ "(:group_id, :id, :username) ")
								.setString("id", id)
								.setString("group_id", temp.get("id").toString())
								.setString("username", requestMap.getUsername())
								.executeUpdate();
				}
				tx.commit();
				result.setIdMessage(1);
				result.setMessage(ConsMessage.MESSAGE_SUCCESS);
			}else{
				result.setIdMessage(0);
				result.setMessage(ConsMessage.DOUBLE_EMAIL);
				tx.rollback();
			}
		} catch (Exception e) {
			if (tx!=null) tx.rollback();
			result.setIdMessage(0);
			result.setMessage(ConsMessage.MESSAGE_FAILED);
			e.printStackTrace(); 
		}
		return result;
	}
	
	@Override
	public Object editEmployee(Object request) {
		WSMessage result = new WSMessage();
		EmployeeRequest requestMap =  (EmployeeRequest) request;
		Session session =  sessionFactory.getCurrentSession();
		Transaction tx = null;
		String id = requestMap.getId();
		int checkUpdate = 0;
		try {
			List<Map<String, Object>> listCatalog = (List<Map<String, Object>>) requestMap.getListCatalog();
			List<Map<String, Object>> listRole = (List<Map<String, Object>>) requestMap.getListRole();
			if(listCatalog.size() == 0){
				result.setIdMessage(0);
				result.setMessage(ConsMessage.EMPTY_ITEM);
				return result;
			}
			if(listRole.size() == 0){
				result.setIdMessage(0);
				result.setMessage(ConsMessage.EMPTY_ITEM);
				return result;
			}
			
			tx = session.beginTransaction();
			checkUpdate = session.createSQLQuery("UPDATE employee "
					+ "SET full_name = :name, id_position = :position, email = :email, enabled = :enabled, updated_by = :username, updated_date = NOW() "
					+ "WHERE id = :id AND TO_CHAR(updated_date, 'YYYY-MM-DD HH24:MI:SS') = :updated_date")
						.setString("name", requestMap.getFull_name())
						.setString("email", requestMap.getEmail())
						.setInteger("enabled", requestMap.getEnabledEmployee())
						.setString("position", requestMap.getId_position())
						.setString("username", requestMap.getUsername())
						.setString("id", id)
						.setString("updated_date", requestMap.getUpdated_date())
						.executeUpdate();
			if(checkUpdate > 0){
				File folder = new File(ConsParam.PATH_IMAGE + id);
				File[] files = null;
				if(!folder.exists()){
					folder.mkdir();
				}
				if(requestMap.getPhoto() != null){
					folder = new File(ConsParam.PATH_IMAGE + id +  ConsParam.PATH_PHOTO);
					if(!folder.exists()){
						folder.mkdir();
					}else{
						files = folder.listFiles();
						for(File temp : files){
							temp.delete();
						}
					}
		    		Path pathRequestImage = Paths.get(ConsParam.PATH_IMAGE + id +  ConsParam.PATH_PHOTO + requestMap.getPhotoFilename());
			    	Files.write(pathRequestImage, requestMap.getPhoto());
		    	}
				if(requestMap.getcProfile() != null){
					folder = new File(ConsParam.PATH_IMAGE + id +  ConsParam.PATH_CPROFILE);
					if(!folder.exists()){
						folder.mkdir();
					}else{
						files = folder.listFiles();
						for(File temp : files){
							temp.delete();
						}
					}
		    		Path pathRequestImage = Paths.get(ConsParam.PATH_IMAGE + id +  ConsParam.PATH_CPROFILE + requestMap.getcProfileFilename());
			    	Files.write(pathRequestImage, requestMap.getcProfile());
		    	}
				session.createSQLQuery("UPDATE users "
						+ "set userlogin = :email, password = COALESCE(:password, password), "
						+ "updated_by = :username, updated_date = NOW() "
						+ "where username = :id")
							.setString("email", requestMap.getEmail())
							.setString("password", requestMap.isResetPassword() ? Utils.hashPassword(ConsParam.DEFAULT_PASS) : null)
							.setString("username", requestMap.getUsername())
							.setString("id", id)
							.executeUpdate();
				
				session.createSQLQuery("DELETE FROM assign_commodity where id_user = :id")
							.setString("id", id)
							.executeUpdate();
				for(Map<String, Object> temp : listCatalog){
					 session.createSQLQuery("INSERT INTO assign_commodity "
						+ "(id_user, id_commodity, type_catalog, created_by) "
						+ "VALUES "
						+ "(:id, :id_commodity, :type_catalog, :username) ")
						.setString("id", id)
						.setString("id_commodity", temp.get("id_commodity").toString())
						.setString("type_catalog", temp.get("id").toString())
						.setString("username", requestMap.getUsername())
						.executeUpdate();
				}
				session.createSQLQuery("DELETE FROM group_members where username = :id")
					.setString("id", id)
					.executeUpdate();
				for(Map<String, Object> temp : listRole){
					 session.createSQLQuery("INSERT INTO group_members "
								+ "(group_id, username, created_by) "
								+ "VALUES "
								+ "(:group_id, :id, :username) ")
								.setString("id", id)
								.setString("group_id", temp.get("id").toString())
								.setString("username", requestMap.getUsername())
								.executeUpdate();
				}
				tx.commit();
				result.setIdMessage(1);
				result.setMessage(ConsMessage.MESSAGE_SUCCESS);
			}else{
				result.setIdMessage(0);
				result.setMessage(ConsMessage.FAILED_OLD_DATA);
				tx.rollback();
			}
		} catch (Exception e) {
			if (tx!=null) tx.rollback();
			result.setIdMessage(0);
			result.setMessage(ConsMessage.DOUBLE_EMAIL);
			e.printStackTrace(); 
		}
		return result;
	}

	@Override
	public Object deleteEmployee(Object request) {
		WSMessage result = new WSMessage();
		Map<String, Object> requestMap = (Map<String, Object>) request;
		String username = requestMap.get("searchName").toString();
		String id = requestMap.get("id").toString();
		
		String queryUsers = "UPDATE users SET deleted = 1, enabled = 0,  "
				+ "updated_by = :username, updated_date = NOW() "
				+ "WHERE username = :id";
		
		String queryEmployee = "UPDATE employee SET deleted = 1, enabled = 0,  "
				+ "updated_by = :username, updated_date = NOW() "
				+ "WHERE id = :id";
		
		Session session =  sessionFactory.getCurrentSession();
		Transaction tx = null;
		int checkSave = 0;
		
		try {
			tx = session.beginTransaction();
			checkSave = session.createSQLQuery(queryUsers)
					.setString("username", username)
					.setString("id", id)
					.executeUpdate();
			
			if(checkSave > 0){
				checkSave = session.createSQLQuery(queryEmployee)
						.setString("username", username)
						.setString("id", id)
						.executeUpdate();
			}
			tx.commit();
			result.setIdMessage(1);
			result.setMessage(ConsMessage.MESSAGE_SUCCESS);
			result.setResult(request);
		} catch (Exception e) {
			if (tx!=null) tx.rollback();
			result.setIdMessage(0);
			result.setMessage(ConsMessage.MESSAGE_FAILED);
			e.printStackTrace(); 
		}
		return result;
	}
	
	@Override
	public Object changePassword(Object request) {
		WSMessage result = new WSMessage();
		Map<String, Object> requestMap = (Map<String, Object>) request;
		
		String changePass = "UPDATE users SET password = :pass,  "
				+ "updated_by = :username, updated_date = NOW() "
				+ "WHERE username = :username";
		String selectPassword = "SELECT password FROM users where username = :username";
		
		Session session =  sessionFactory.getCurrentSession();
		Transaction tx = null;
		try {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			boolean checkPass = passwordEncoder.matches(requestMap.get("old_password").toString(), (String)session.createSQLQuery(selectPassword)
					.setString("username", requestMap.get("username").toString())
					.uniqueResult());
			if(checkPass){
				tx = session.beginTransaction();
				session.createSQLQuery(changePass)
						.setString("pass", Utils.hashPassword(requestMap.get("password").toString()))
						.setString("username", requestMap.get("username").toString())
						.executeUpdate();
				
				tx.commit();
				result.setIdMessage(1);
				result.setMessage(ConsMessage.MESSAGE_SUCCESS);
			}else{
				result.setIdMessage(1);
				result.setMessage(ConsMessage.ERROR_WRONG_PASSWORD);
			}
		} catch (Exception e) {
			if (tx!=null) tx.rollback();
			result.setIdMessage(0);
			result.setMessage(ConsMessage.MESSAGE_FAILED);
			e.printStackTrace(); 
		}
		return result;
	}
	
	@Override
	public Object getSetting(Object username) {
		String id = (String)username;
		Map<String , Object> result = new HashMap<String, Object>();
		String query = "SELECT a.tipe, COALESCE(TO_CHAR(e.updated_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(c.updated_date, 'YYYY-MM-DD HH24:MI:SS')) updated_date, "
				+ "COALESCE(e.full_name, c.full_name) full_name, e.id_position, c.address, c.phone, c.email, c.member_type, c.regis_type, c.duration, c.local "
				+ "FROM users a "
				+ "LEFT JOIN employee e ON e.id = a.username "
				+ "LEFT JOIN customer c ON c.id = a.username "
				+ "WHERE a.username = :id";
		String queryTypeCatalog = "SELECT a.id_commodity, b.name, c.description, a.type_catalog "
				+ "FROM assign_commodity a "
				+ "INNER JOIN commodity b ON b.id = a.id_commodity "
				+ "INNER JOIN type_catalog c ON c.id_commodity = a.id_commodity AND c.id = a.type_catalog "
				+ "WHERE a.id_user = :id ORDER BY a.id_commodity, a.type_catalog";
		Session session = sessionFactory.getCurrentSession();
		result = (Map<String, Object>) session.createSQLQuery(query)
				.setString("id", id)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.uniqueResult();
		if(result.get("tipe").equals("C")){
			result.put("listCatalog", session.createSQLQuery(queryTypeCatalog)
					.setString("id", id)
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.list());
		}
		
		File folder = new File(ConsParam.PATH_IMAGE + id + ConsParam.PATH_PHOTO);
		File[] files = folder.listFiles();
		byte[] bytesArray = null;
		String ext = null;
		if(files != null && files.length > 0){
			bytesArray = new byte[(int) files[0].length()];
			ext = FilenameUtils.getExtension(files[0].getName()); 
			FileInputStream fis;
			try {
				fis = new FileInputStream(files[0]);
				fis.read(bytesArray);
				fis.close();
			} catch (FileNotFoundException e) {
				bytesArray = null;
				System.out.println("file not found");
			} catch (IOException e) {
				bytesArray = null;
				e.printStackTrace();
			}
			result.put("photo", bytesArray);
			result.put("photoFilename", files[0].getName());
			result.put("photoExt", ext);
		}
		folder = new File(ConsParam.PATH_IMAGE + id + ConsParam.PATH_CPROFILE);
		files = folder.listFiles();
		if(files != null && files.length > 0){
			result.put("cProfileFilename", files[0].getName());
		}
		return result;
	}
	
	@Override
	public List<Map<String, Object>> getListUser(Object request) {
		Map requestMap = (Map) request;
		Map search = (Map) requestMap.get("search");
		String id = search.get("id") == null ? "" : search.get("id").toString(), 
			   name = search.get("name") == null ? "" : search.get("name").toString(), 
			   prefix = requestMap.get("prefix") == null ? "" : requestMap.get("prefix").toString();
		String query = "SELECT COALESCE(b.full_name, COALESCE(c.full_name, d.full_name)) full_name, "
				+ "a.username id, a.userlogin nickname, TO_CHAR(a.updated_date, 'YYYY-MM-DD HH24:MI:SS') updated_date, "
				+ "COALESCE(b.email, COALESCE(c.email, d.email)) email, "
				+ "count(*) OVER() AS \"totalPage\" "
				+ "FROM users a "
				+ "LEFT JOIN customer b ON a.username = b.id AND b.deleted = 0 AND b.enabled = 1 "
				+ "AND LOWER(b.full_name) like LOWER(:name) "
				+ "LEFT JOIN member c ON a.username = c.id AND c.deleted = 0 AND c.enabled = 1 "
				+ "AND LOWER(c.full_name) like LOWER(:name) "
				+ "LEFT JOIN supplier d ON a.username = d.id AND d.deleted = 0 AND d.enabled = 1 "
				+ "AND LOWER(d.full_name) like LOWER(:name) "
				+ "WHERE a.tipe NOT LIKE :prefix AND a.tipe NOT LIKE '%E%' "
				+ "AND a.deleted = 0 and a.enabled = 1 "
				+ "AND LOWER(a.userlogin) like LOWER(:id) "
				+ "ORDER BY a.created_date desc "
				+ "LIMIT :requestPage OFFSET :page";
		Session session = sessionFactory.getCurrentSession();
		return session.createSQLQuery(query)
				.setInteger("requestPage", (int) requestMap.get("requestPage"))
				.setInteger("page", (int) requestMap.get("page"))
				.setString("id", "%" + id + "%")
				.setString("name", "%" + name + "%")
				.setString("prefix", "%" + prefix + "%")
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.list();
	}
	
	@Override
	public Object getUserFile(Object request) {
		Map requestMap = (Map) request;
		Map result = new HashMap<String, Object>();
		String id = requestMap.get("id").toString();
		File folder = new File(ConsParam.PATH_IMAGE + id + ConsParam.PATH_PHOTO);
		File[] files = folder.listFiles();
		byte[] bytesArray = null;
		String ext = null;
		if(files != null && files.length > 0){
			bytesArray = new byte[(int) files[0].length()];
			ext = FilenameUtils.getExtension(files[0].getName()); 
			FileInputStream fis;
			try {
				fis = new FileInputStream(files[0]);
				fis.read(bytesArray); // read file into bytes[]
				fis.close();
			} catch (FileNotFoundException e) {
				bytesArray = null;
				System.out.println("file not found");
			} catch (IOException e) {
				bytesArray = null;
				e.printStackTrace();
			}
			result.put("photo", bytesArray);
			result.put("photoFilename", files[0].getName());
			result.put("photoExt", ext);
		}
		folder = new File(ConsParam.PATH_IMAGE + id + ConsParam.PATH_CPROFILE);
		files = folder.listFiles();
		if(files != null && files.length > 0){
			result.put("cProfileFilename", files[0].getName());
		}
		
		String queryRole = "SELECT a.group_id, b.group_name FROM group_members a "
				+ "INNER JOIN groups b ON b.id = a.group_id "
				+ "WHERE a.username = :id";
		Session session = sessionFactory.getCurrentSession();
		result.put("listRole", session.createSQLQuery(queryRole)
				.setString("id", id)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.list());
		return result;
	}

}

