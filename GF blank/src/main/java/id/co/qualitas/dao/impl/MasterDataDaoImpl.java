 package id.co.qualitas.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
import org.springframework.stereotype.Repository;

import id.co.qualitas.component.ConsParam;
import id.co.qualitas.component.Utils;
import id.co.qualitas.dao.BaseDao;
import id.co.qualitas.dao.interfaces.MasterDataDao;
import id.co.qualitas.domain.Authority;
import id.co.qualitas.domain.Device;
import id.co.qualitas.domain.EmployeeMD;
import id.co.qualitas.domain.Group;
import id.co.qualitas.domain.GroupAuthority;
import id.co.qualitas.domain.GroupMember;
import id.co.qualitas.domain.Material;
import id.co.qualitas.domain.Plant;
import id.co.qualitas.domain.Sloc;
import id.co.qualitas.domain.Users;
import id.co.qualitas.domain.VendorMD;

@Repository
@Transactional
public class MasterDataDaoImpl extends BaseDao implements MasterDataDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Sloc> getSLOC(Object filter) {
		List<Sloc> result = new ArrayList<Sloc>();
		Map<String,Object> req = (Map<String, Object>) filter;
		Integer offset = Integer.valueOf(req.get("curPage").toString()) * 15;
		
		String query = "select id_plant as idPlant,plant_name as plantName, id_sloc as idSloc, name as slocName,loading_point as loadingPoint,"
				+ "COUNT(*) OVER () as totalPage  "
				+ "from sloc "
				+ "WHERE COALESCE(id_plant,'') LIKE :idPlant AND COALESCE(plant_name,'') LIKE :plantName "
				+ "AND COALESCE(id_sloc,'') LIKE :idSloc AND COALESCE(name,'') LIKE :slocName  AND COALESCE(loading_point,'') LIKE :loadingPoint "
				+ "ORDER BY id_plant "
				+ "OFFSET :offset ROWS FETCH NEXT 15 ROWS ONLY ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
					.setInteger("offset", offset)
					.setString("idPlant", (!req.get("idPlant") .toString().isEmpty() ? "%" +  req.get("idPlant").toString() + "%" : "%%"))
					.setString("plantName", (!req.get("plantName") .toString().isEmpty() ? "%" +  req.get("plantName").toString() + "%" : "%%"))
					.setString("idSloc", (!req.get("idSloc") .toString().isEmpty() ? "%" + req.get("idSloc").toString() + "%" : "%%"))
					.setString("slocName", (!req.get("slocName") .toString().isEmpty() ? "%" + req.get("slocName").toString() + "%" : "%%"))
					.setString("loadingPoint", (!req.get("loadingPoint") .toString().isEmpty() ? "%" + req.get("loadingPoint").toString() + "%" : "%%"))
					.setResultTransformer(Transformers.aliasToBean(Sloc.class))
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public Boolean updateLoadingPoint(Sloc request) {
		Session session =  sessionFactory.getCurrentSession();
		boolean result = true;
		Transaction tx = null;		
		String query = "UPDATE sloc set loading_point=:loadingPoint,updated_by=:updatedBy, updated_date = CURRENT_TIMESTAMP "
				+ "where id_plant=:idPlant and id_sloc=:idSloc ";
		
		try {
			tx = session.beginTransaction();
				session.createSQLQuery(query)
					.setString("loadingPoint",request.getLoadingPoint())
					.setString("idPlant",request.getIdPlant())
					.setString("idSloc",request.getIdSloc())
					.setString("updatedBy", request.getCreatedBy())
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
	public List<Material> getMaterial(Object filter) {
		List<Material> result = new ArrayList<Material>();
		Map<String,Object> req = (Map<String, Object>) filter;
		Integer offset = Integer.valueOf(req.get("curPage").toString()) * 15;

		String query = "select h.id,h.name,CONCAT(h.material_type, ' - ', h.material_type_name) as materialType, "
				+ "CONCAT(h.id_sloc,' - ', s.name) as idSloc, CONCAT(h.id_plant,' - ',s.plant_name) as idPlant,"
				+ "COUNT(*) OVER () as totalPage "
				+ "from material h "
				+ "LEFT join sloc s ON s.id_plant = h.id_plant and s.id_sloc = h.id_sloc "
				+ "WHERE h.id LIKE :id AND h.name LIKE :name and h.id_sLoc LIKE :idSloc AND h.id_plant LIKE :idPlant AND "
				+ "h.material_type LIKE :materialType "
				+ "ORDER BY h.id "
				+ "OFFSET :offset ROWS FETCH NEXT 15 ROWS ONLY ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
					.setString("id", (!req.get("id") .toString().isEmpty() ? "%" + req.get("id").toString() + "%" : "%%"))
					.setString("name", (!req.get("name") .toString().isEmpty() ? "%" + req.get("name").toString() + "%" : "%%"))
					.setString("idSloc", (!req.get("idSloc") .toString().isEmpty() ? "%" + req.get("idSloc").toString() + "%": "%%"))
					.setString("idPlant", (!req.get("idPlant") .toString().isEmpty() ? "%" + req.get("idPlant").toString() + "%": "%%"))
					.setString("materialType", (!req.get("materialType") .toString().isEmpty() ? "%" + req.get("materialType").toString() + "%": "%%"))
					.setInteger("offset", offset)
					.setResultTransformer(Transformers.aliasToBean(Material.class))
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public List<Plant> getPlant(Object filter) {
		List<Plant> result = new ArrayList<Plant>();
		Map<String,Object> req = (Map<String, Object>) filter;
		Integer offset = Integer.valueOf(req.get("curPage").toString()) * 15;

		String query = "SELECT id, name, COUNT(*) OVER () as totalPage "
				+ "FROM plant WHERE deleted = 0 "
				+ "AND id LIKE :id AND name LIKE :name "
				+ "ORDER BY id "
				+ "OFFSET :offset ROWS FETCH NEXT 15 ROWS ONLY ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
					.setString("id", (!req.get("id") .toString().isEmpty() ? "%" + req.get("id").toString() + "%" : "%%"))
					.setString("name", (!req.get("name") .toString().isEmpty() ? "%" + req.get("name").toString() + "%" : "%%"))
					.setInteger("offset", offset)
					.setResultTransformer(Transformers.aliasToBean(Plant.class))
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public Object getUserlogin() {
		List<Object> result = new ArrayList<Object>();
		String query = "SELECT username,userlogin from users where deleted = 0 ORDER BY username desc ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result = session.createSQLQuery(query)
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Device> getDevice(Object filter) {
		List<Device> result = new ArrayList<Device>();
		Map<String,Object> req = (Map<String, Object>) filter;
		Integer offset = Integer.valueOf(req.get("curPage").toString()) * 15;
		String query = "select cast(d.id as varchar) as id,d.device_name as device,d.phone_no as phoneNo,d.enabled as status,"
				+ "COALESCE(d.username,'') as username,d.ssaid,COALESCE(u.userlogin,'') as userlogin, "
				+ "COUNT(*) OVER () as totalPage  "
				+ "from device d "
				+ "LEFT JOIN users u ON u.username = d.username "
				+ "where d.deleted = 0 "
				+ "AND COALESCE(d.id,'') LIKE :id AND COALESCE(d.device_name,'') LIKE :device AND COALESCE(d.phone_no,'') LIKE :phoneNo and d.enabled LIKE :enabled AND "
				+ "COALESCE(d.ssaid,'') LIKE :ssaid "
				+ "ORDER BY id desc "
				+ "OFFSET :offset ROWS FETCH NEXT 15 ROWS ONLY ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
					.setString("id", (!req.get("id").toString().isEmpty() ? "%" + req.get("id").toString()  + "%" : "%%"))
					.setString("device", (!req.get("device").toString().isEmpty() ? "%" + req.get("device").toString() + "%" : "%%"))
					.setString("ssaid", (!req.get("ssaid").toString().isEmpty() ? "%" +req.get("ssaid").toString() + "%" : "%%"))
					.setString("phoneNo", (!req.get("phoneNo").toString().isEmpty() ? "%" +req.get("phoneNo").toString() + "%" : "%%"))
					.setString("enabled", (!req.get("status").toString().isEmpty() ? "%" +req.get("status").toString() + "%" : "%%"))
					.setInteger("offset", offset)
					.setResultTransformer(Transformers.aliasToBean(Device.class))
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Device getDeviceDetail(String id) {
		Device result = new Device();
		String query = "select cast(d.id as varchar) as id, d.ssaid, d.device_name as device, d.phone_no as phoneNo, "
				+ "d.enabled as status,u.username "
				+ "FROM device d "
				+ "LEFT JOIN users u ON u.username = d.username "
				+ "WHERE d.id=:id ";
		
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  (Device) session.createSQLQuery(query)
						.setString("id", id)
						.setResultTransformer(Transformers.aliasToBean(Device.class))
						.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Boolean createDevice(Device request) {
		Session session =  sessionFactory.getCurrentSession();
		boolean result = true;
		Transaction tx = null;		
		String query = "INSERT INTO device (ssaid,device_name,phone_no,username,enabled,deleted,created_by,created_date) values "
				+ "(:ssaid,:device,:phoneNo,:username,:enabled,0,:createdBy,CURRENT_TIMESTAMP)";
		
		int enabled = 0;
		
		if(request.getStatus() == 1) {
			enabled = 1;
		}	
		try {
			tx = session.beginTransaction();
				session.createSQLQuery(query)
					.setString("device",request.getDevice())
					.setString("createdBy", request.getCreatedBy())
					.setString("phoneNo", request.getPhoneNo())
					.setInteger("username", request.getUsername())
					.setString("ssaid", request.getSsaid())
					.setInteger("enabled", enabled)
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
	public Boolean updateDevice(Device request) {
		Session session =  sessionFactory.getCurrentSession();
		boolean result = true;
		Transaction tx = null;		
		String query = "UPDATE device set username=:username,ssaid=:ssaid, device_name =:device,phone_no=:phoneNo,enabled=:enabled,"
				+ "updated_by=:createdBy,updated_date=CURRENT_TIMESTAMP where id=:id";
		
		int enabled = 0;
		if(request.getStatus() == 1) {
			enabled = 1;
		}
		
		try {
			tx = session.beginTransaction();
				session.createSQLQuery(query)
					.setString("ssaid",request.getSsaid())
					.setString("device",request.getDevice())
					.setString("createdBy", request.getCreatedBy())
					.setInteger("username", request.getUsername())
					.setString("phoneNo", request.getPhoneNo())
					.setString("id", request.getId())
					.setInteger("enabled", enabled)
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
	public Boolean updateUsername(String imei, String username, String status) {
		Session session =  sessionFactory.getCurrentSession();
		boolean result = true;
		Transaction tx = null;		
		String query = "UPDATE device set username=:username,"
				+ "updated_by=:updatedBy,updated_date=CURRENT_TIMESTAMP where imei=:imei";
		
		String updatedby = username;
		if(status.contentEquals("logout"))
			username = "";
		try {
			tx = session.beginTransaction();
				session.createSQLQuery(query)
					.setString("imei",imei)
					.setString("username",username)
					.setString("updatedBy", updatedby)
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
	public Boolean deleteDevice(String id,String username) {
		Session session =  sessionFactory.getCurrentSession();
		boolean result = true;
		Transaction tx = null;		
		String query = "UPDATE device set enabled = 0,deleted = 1, updated_by=:username, updated_date = CURRENT_TIMESTAMP where id=:id";
		
		try {
			tx = session.beginTransaction();
				session.createSQLQuery(query)
					.setString("id", id)
					.setString("username", username)
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
	public List<Group> getGroups(Object filter) {
		List<Group> result = new ArrayList<Group>();
		Map<String,Object> req = (Map<String, Object>) filter;
		Integer offset = Integer.valueOf(req.get("curPage").toString()) * 15;
		
		String query = "select id, group_name as groupName, tipe, COUNT(*) OVER () as totalPage   "
				+ "from groups WHERE deleted = 0 "
				+ "AND id LIKE :id AND group_name LIKE :groupName "
				+ "ORDER BY id "
				+ "OFFSET :offset ROWS FETCH NEXT 15 ROWS ONLY ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =   session.createSQLQuery(query)
						.setInteger("offset", offset)
						.setString("id", (!req.get("id") .toString().isEmpty() ?  "%" +  req.get("id").toString()  + "%" : "%%"))
						.setString("groupName", (!req.get("groupName") .toString().isEmpty() ? "%" + req.get("groupName").toString()  + "%" : "%%"))
						.setResultTransformer(Transformers.aliasToBean(Group.class))
						.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public List<Group> getAllGroups() {
		List<Group> result = new ArrayList<Group>();
		String query = "select id, group_name as groupName, tipe, COUNT(*) OVER () as totalPage   "
				+ "from groups WHERE deleted = 0 ";
		
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
					.setResultTransformer(Transformers.aliasToBean(Group.class))
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public List<Plant> getAllPlant() {
		List<Plant> result = new ArrayList<Plant>();
		String query = "SELECT id, name "
				+ "FROM plant WHERE deleted = 0 ";

		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
					.setResultTransformer(Transformers.aliasToBean(Plant.class))
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public Boolean deleteGroups(String id,String username) {
		Session session =  sessionFactory.getCurrentSession();
		boolean result = true;
		Transaction tx = null;		
		String query = "UPDATE groups set enabled = 0,deleted = 1, updated_by=:username, updated_date = CURRENT_TIMESTAMP where id=:id";
		
		try {
			tx = session.beginTransaction();
				session.createSQLQuery(query)
					.setString("id", id)
					.setString("username", username)
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
	public Group getGroupsDetail(String id) {
		Group result = new Group();
		String query = "select id, group_name as groupName, tipe  from groups where id =:id ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =   (Group) session.createSQLQuery(query)
						.setString("id", id)
						.setResultTransformer(Transformers.aliasToBean(Group.class))
						.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<GroupAuthority> getGroupAuthority(String id) {
		List<GroupAuthority> result = new ArrayList<GroupAuthority>();
		
		String query = "select group_id as groupId, authority from group_authorities  "
				+ "where group_id =:id " ;
		try {
			Session session = sessionFactory.getCurrentSession();
			result =   session.createSQLQuery(query)
					.setString("id", id)
						.setResultTransformer(Transformers.aliasToBean(GroupAuthority.class))
						.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public List<Authority> getAuthority() {
		List<Authority> result = new ArrayList<Authority>();
		
		String query = "select authority,description from authority where enabled = 1 and deleted = 0 order by description ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =   session.createSQLQuery(query)
						.setResultTransformer(Transformers.aliasToBean(Authority.class))
						.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public Boolean createGroups(Group request) {
		Session session =  sessionFactory.getCurrentSession();
		boolean result = true;
		Transaction tx = null;		
		String query = "INSERT INTO groups (id,group_name,created_by,created_date) values (:id,:name,:username,CURRENT_TIMESTAMP) ";
		
		try {
			tx = session.beginTransaction();
				session.createSQLQuery(query)
					.setString("name",request.getGroupName())
					.setString("username", request.getCreatedBy())
					.setString("id", request.getId())
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
	public Boolean updateGroups(Group request) {
		Session session =  sessionFactory.getCurrentSession();
		boolean result = true;
		Transaction tx = null;		
		String query = "UPDATE groups set group_name =:name,updated_by=:username,updated_date=CURRENT_TIMESTAMP where id=:id ";
		
		try {
			tx = session.beginTransaction();
				session.createSQLQuery(query)
					.setString("name",request.getGroupName())
					.setString("username", request.getCreatedBy())
					.setString("id", request.getId())
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
	public Boolean updateGroupAuthority(List<GroupAuthority> request) {
		Session session =  sessionFactory.getCurrentSession();
		boolean result = true;
		Transaction tx = null;
		String delete = "DELETE FROM group_authorities where group_id =:id ";
		String query = "INSERT INTO group_authorities (group_id,authority,created_by,created_date) "
				+ "values (:groupId,:authority,:username,CURRENT_TIMESTAMP) ";
		
		try {
			tx = session.beginTransaction();
			
				session.createSQLQuery(delete)
					.setString("id", request.get(0).getGroupId())
					.executeUpdate();
				
				for(GroupAuthority d : request){
					session.createSQLQuery(query)
						.setString("groupId",d.getGroupId())
						.setString("authority", d.getAuthority())
						.setString("username", d.getCreatedBy())
						.executeUpdate();
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
	public List<EmployeeMD> getEmployee(Object filter) {
		List<EmployeeMD> result = new ArrayList<EmployeeMD>();
		Map<String,Object> req = (Map<String, Object>) filter;
		Integer offset = Integer.valueOf(req.get("curPage").toString()) * 15;
		
		String query = "SELECT u.username as id,u.userlogin as userlogin, e.full_name as name, u.enabled as status," 
				+ "CAST(STUFF((select ',' + group_id from group_members where username = u.username FOR XML PATH('')), 1, 1, '') AS VARCHAR) as role,e.id_plant as idPlant,p.name as namePlant, "
				+ "COUNT(*) OVER () as totalPage " 
				+ "FROM users u " 
				+ "INNER JOIN employee e ON u.username = e.id "
				+ "LEFT JOIN plant p ON e.id_plant = p.id "
				+ "WHERE e.deleted = 0 "
				+ "AND u.userlogin LIKE :userlogin AND e.full_name LIKE :fullName AND u.enabled LIKE :enabled AND COALESCE(e.id_plant,'') LIKE :idPlant AND COALESCE(p.name,'') LIKE :namePlant AND "
				+ "COALESCE(CAST(STUFF((select ',' + group_id from group_members where username = u.username FOR XML PATH('')), 1, 1, '') AS VARCHAR),'') LIKE :role "
				+ "ORDER BY u.username "
				+ "OFFSET :offset ROWS FETCH NEXT 15 ROWS ONLY ";
		try {
			Session session = sessionFactory.getCurrentSession();
			String role = null;
			if(req.get("role") != null) {
				role = "%"+req.get("role").toString()+"%";
			}
			
			result =  session.createSQLQuery(query)
					.setResultTransformer(Transformers.aliasToBean(EmployeeMD.class))
					.setString("role", role)
					.setString("userlogin", (!req.get("userlogin").toString().isEmpty() ? "%" + req.get("userlogin").toString() + "%" : "%%"))
					.setString("fullName", (!req.get("name").toString().isEmpty() ? "%" + req.get("name").toString() + "%" : "%%"))
					.setString("enabled", (!req.get("status").toString().isEmpty() ? "%" + req.get("status").toString() + "%": "%%"))
					.setString("idPlant", (!req.get("idPlant").toString().isEmpty() ? "%" + req.get("idPlant").toString() + "%": "%%"))
					.setString("namePlant", (!req.get("namePlant").toString().isEmpty() ? "%" + req.get("namePlant").toString() + "%": "%%"))
					.setInteger("offset", offset)
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public EmployeeMD getEmployeeDetail(String username) {
		EmployeeMD result = new EmployeeMD();
		String query = "SELECT u.username as id,u.userlogin as userlogin, e.full_name as name,e.id_plant as idPlant , u.enabled as status, e.email as email, " + 
				"CAST(STUFF((select ',' + group_id from group_members where username = u.username FOR XML PATH('')), 1, 1, '') AS VARCHAR) as role " + 
				"FROM users u " + 
				"INNER JOIN employee e ON u.username = e.id "
				+ "WHERE u.username =:username ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  (EmployeeMD) session.createSQLQuery(query)
						.setString("username", username)
						.setResultTransformer(Transformers.aliasToBean(EmployeeMD.class))
						.uniqueResult();
			
			File files = new File(ConsParam.PATH_IMAGE + result.getId() + ".png");
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
				result.setPhoto(bytesArray);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Boolean createEmployee(EmployeeMD emp, List<GroupMember> gm) {
		Session session =  sessionFactory.getCurrentSession();
		boolean result = true;
		Transaction tx = null;	
		String insertUser = "insert into users(userlogin,password,type,created_by,created_date,enabled) "
				+ "Output Inserted.username "
				+ "values (:userlogin, :password, 'E', :createdBy, CURRENT_TIMESTAMP, :enabled)";
		String insertGroupMember = "INSERT INTO group_members (group_id,username,created_by,created_date) values (:groupId,:username,:createdBy,CURRENT_TIMESTAMP)";
		String insertEmployee = "INSERT INTO employee (id,full_name,id_plant,email,created_by,created_date) values (:id,:name,:idPlant,:email,:createdBy,CURRENT_TIMESTAMP) ";
		
		int enabled = 0;
		
		if(emp.getStatus() == 1) {
			enabled = 1;
		}
		try {
			tx = session.beginTransaction();
			
			Integer username = (Integer) session.createSQLQuery(insertUser)
					.setString("userlogin",emp.getUserlogin())
					.setString("password", Utils.hashPassword(emp.getPassword()))
					.setString("createdBy", emp.getCreatedBy())
					.setInteger("enabled", enabled)
					.uniqueResult();
			
			if(username != null) {
				session.createSQLQuery(insertEmployee)
					.setString("id",username.toString())
					.setString("name", emp.getName())
					.setString("idPlant", emp.getIdPlant())
					.setString("email", emp.getEmail())
					.setString("createdBy", emp.getCreatedBy())
					.executeUpdate();
				
				for(GroupMember d : gm) {
					session.createSQLQuery(insertGroupMember)
						.setString("groupId",d.getGroupId())
						.setString("username", username.toString())
						.setString("createdBy", d.getCreatedBy())
						.executeUpdate();
				}
				
				if(emp.getPhoto().length > 0){
		    		Path pathRequestImage = Paths.get(ConsParam.PATH_IMAGE + username + ".png" );
			    	Files.write(pathRequestImage, emp.getPhoto());
		    	}
				
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
	public Boolean updateEmployee(EmployeeMD emp, List<GroupMember> gm, boolean isPassChanged) {
		Session session =  sessionFactory.getCurrentSession();
		boolean result = true;
		Transaction tx = null;	
		
		String user = "UPDATE users set userlogin =:userlogin, updated_by=:updatedBy, updated_date = CURRENT_TIMESTAMP, "
				+ "enabled =:enabled where username =:id ";
		String userPass = "UPDATE users set password =:password,changed=1,updated_by=:updatedBy, updated_date = CURRENT_TIMESTAMP where username=:id ";
		String employee = "UPDATE employee set full_name =:name,id_plant=:idPlant, email=:email, updated_by=:updatedBy, updated_date = CURRENT_TIMESTAMP where id =:id ";
		String deleteGroupMember = "DELETE FROM group_members where username =:id ";
		String insertGroupMember = "INSERT INTO group_members (group_id,username,created_by,created_date) values (:groupId,:username,:createdBy,CURRENT_TIMESTAMP)";
		
		
		int enabled = 0;
		
		if(emp.getStatus() == 1) {
			enabled = 1;
		}
		try {
			tx = session.beginTransaction();
			
			
			session.createSQLQuery(user)
					.setString("userlogin",emp.getUserlogin())
					.setString("updatedBy", emp.getCreatedBy())
					.setInteger("enabled", enabled)
					.setInteger("id",emp.getId())
					.executeUpdate();
			
			if(emp.getPassword() != null && isPassChanged) {
				session.createSQLQuery(userPass)
					.setString("password", Utils.hashPassword(emp.getPassword()))
					.setString("updatedBy", emp.getCreatedBy())
					.setInteger("id",emp.getId())
					.executeUpdate();
			}
			
			session.createSQLQuery(employee)
				.setInteger("id",emp.getId())
				.setString("name", emp.getName())
				.setString("idPlant", emp.getIdPlant())
				.setString("email", emp.getEmail())
				.setString("updatedBy", emp.getCreatedBy())
				.executeUpdate();
			
			session.createSQLQuery(deleteGroupMember)
				.setInteger("id",emp.getId())
				.executeUpdate();
			for(GroupMember d : gm) {
				session.createSQLQuery(insertGroupMember)
					.setString("groupId",d.getGroupId())
					.setInteger("username", emp.getId())
					.setString("createdBy", d.getCreatedBy())
					.executeUpdate();
			}
				
			if(emp.getPhoto().length > 0){
	    		Path pathRequestImage = Paths.get(ConsParam.PATH_IMAGE + emp.getId() + ".png" );
		    	Files.write(pathRequestImage, emp.getPhoto());
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
	public Boolean deleteEmployee(String id,String username) {
		Session session =  sessionFactory.getCurrentSession();
		boolean result = true;
		Transaction tx = null;		
		String updateEmployee = "UPDATE employee set enabled = 0,deleted = 1, updated_by=:username, updated_date = CURRENT_TIMESTAMP where id=:id";
		String updateUsers = "UPDATE users set enabled = 0,deleted = 1, updated_by=:username, updated_date = CURRENT_TIMESTAMP where username=:id";
		String groupMembers = "UPDATE group_members set enabled = 0,deleted = 1, updated_by=:username, updated_date = CURRENT_TIMESTAMP where username=:id";
		
		try {
			tx = session.beginTransaction();
				session.createSQLQuery(updateEmployee)
					.setString("id", id)
					.setString("username", username)
					.executeUpdate();
				
				session.createSQLQuery(updateUsers)
					.setString("id", id)
					.setString("username", username)
					.executeUpdate();
				
				session.createSQLQuery(groupMembers)
					.setString("id", id)
					.setString("username", username)
					.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			if (tx!=null) tx.rollback();
			result = false;
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VendorMD> getVendor(Object filter) {
		List<VendorMD> result = new ArrayList<VendorMD>();
		Map<String,Object> req = (Map<String, Object>) filter;
		Integer offset = Integer.valueOf(req.get("curPage").toString()) * 15;
		
		String getEnabledParam = "";
		
		if(!req.get("status").toString().isEmpty()) {
			getEnabledParam = " u.enabled = " + req.get("status").toString() + " AND "; 
		}
		
		String query = "select v.id_sap as idSap, u.userlogin as userlogin, "
				+ " username as id, CONCAT(v.id_sap, ' - ', v.full_name) as name,COALESCE(u.enabled,0) as status, " 
				+ " CAST(STUFF((select ',' + group_id from group_members where "
				+ " username = u.username FOR XML PATH('')), 1, 1, '') "
				+ " AS VARCHAR) as role, " 
				+ " CONCAT(v.id_sloc,' - ', s.name) as idSloc, "
				+ " v.id_sloc as baseIdSloc, "
				+ " CONCAT(v.id_plant,' - ',s.plant_name) as idPlant, "
				+ " v.id_plant as baseIdPlant, "
				+ " COUNT(*) OVER () as totalPage "
				+ " from vendor v " 
				+ " left join users u on u.username = v.id "
				+ " LEFT join sloc s ON s.id_plant = v.id_plant and s.id_sloc = v.id_sloc "
				+ " WHERE v.deleted = 0 "
				+ " AND COALESCE(u.userlogin,'') LIKE :userlogin "
				+ " AND COALESCE(CONCAT(v.id_sap, ' - ', v.full_name),'') LIKE :fullName "
				+ " AND COALESCE(CONCAT(v.id_sloc,' - ', s.name),'') LIKE :idSloc "
				+ " AND COALESCE(CONCAT(v.id_plant,' - ',s.plant_name),'') LIKE :idPlant AND " 
				+ getEnabledParam 
				+ " COALESCE(CAST(STUFF((select ',' + group_id from group_members "
				+ " where username = u.username FOR XML PATH('')), 1, 1, '') AS VARCHAR),'') "
				+ " LIKE :role "
				+ " ORDER BY v.id_sap asc "
				+ " OFFSET :offset ROWS FETCH NEXT 15 ROWS ONLY ";
		try {
			Session session = sessionFactory.getCurrentSession();
			String role = null;
			if(req.get("role") != null) {
				role = "%"+req.get("role").toString()+"%";
			}
			result = session.createSQLQuery(query)
					.setInteger("offset", offset)
					.setString("role", role)
					.setString("userlogin", (!req.get("userlogin").toString().isEmpty()? "%" + req.get("userlogin").toString() + "%" : "%%"))
					.setString("fullName", (!req.get("name").toString().isEmpty()? "%" + req.get("name").toString() + "%" : "%%"))
					.setString("idSloc", (!req.get("idSloc").toString().isEmpty()? "%" + req.get("idSloc").toString() + "%" : "%%"))
					.setString("idPlant", (!req.get("idPlant").toString().isEmpty()? "%" + req.get("idPlant").toString() + "%" : "%%"))
					.setResultTransformer(Transformers.aliasToBean(VendorMD.class))
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public VendorMD getVendorDetail(String idSap, String idSloc, String idPlant) {
		VendorMD result = new VendorMD();
		String query = "select v.id_sap as idSap,u.userlogin as userlogin,u.username as id,v.full_name as name,CONCAT(v.id_plant,' - ',p.name) as plant, CONCAT(v.id_sloc,' - ',sl.name) as sloc, COALESCE(u.enabled,0) as status, " +
				" v.phone as phone,v.email as email, cast(v.address as varchar) as address," +
				" CAST(STUFF((select ',' + group_id from group_members where username = u.username FOR XML PATH('')), 1, 1, '') AS VARCHAR) as role " + 
				" from vendor v " + 
				" LEFT JOIN plant p on v.id_plant = p.id " + 
				" LEFT JOIN sloc sl ON v.id_sloc = sl.id_sloc and p.id = sl.id_plant " + 
				" left join users u on u.username = v.id " + 
				" WHERE v.id_sap = :id1 and v.id_sloc = :idSloc and v.id_plant = :idPlant ";
		
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  (VendorMD) session.createSQLQuery(query)
						.setString("id1", idSap)
						.setString("idSloc", idSloc)
						.setString("idPlant", idPlant)
						.setResultTransformer(Transformers.aliasToBean(VendorMD.class))
						.uniqueResult();
		
			if(result != null && result.getId() != null) {
				File files = new File(ConsParam.PATH_IMAGE + result.getId() + ".png");
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
					result.setPhoto(bytesArray);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Boolean updateVendor(VendorMD vnd, List<GroupMember> gm, boolean isPassChanged) {
		Session session =  sessionFactory.getCurrentSession();
		boolean result = true;
		Transaction tx = null;	
		String insertUser = "insert into users(userlogin,password,type,created_by,created_date,enabled) "
				+ "Output Inserted.username "
				+ "values (:userlogin, :password, 'V', :createdBy, CURRENT_TIMESTAMP, :enabled)";
		String user = "UPDATE users set userlogin =:userlogin, updated_by=:updatedBy, updated_date = CURRENT_TIMESTAMP, "
				+ "enabled =:enabled where username =:id ";
		String userPassword = "UPDATE users set password =:password,changed=1,updated_by=:updatedBy, updated_date = CURRENT_TIMESTAMP, "
				+ "enabled =:enabled where username =:id ";
		String vendor = "UPDATE vendor set id=:username,email=:email, updated_by=:updatedBy, updated_date = CURRENT_TIMESTAMP, photo=:photo where id_sap =:idSap ";
		String deleteGroupMember = "DELETE FROM group_members where username =:id ";
		String insertGroupMember = "INSERT INTO group_members (group_id,username,created_by,created_date) values (:groupId,:username,:createdBy,CURRENT_TIMESTAMP)";
		
		
		int enabled = 0;
		
		if(vnd.getStatus() == 1) {
			enabled = 1;
		}
		try {
			tx = session.beginTransaction();
			Integer username;
			if(vnd.getId() == null) {
				 username = (Integer) session.createSQLQuery(insertUser)
						.setString("userlogin", vnd.getUserlogin())
						.setString("password", Utils.hashPassword(ConsParam.DEFAULT_PASS))
						.setString("createdBy", vnd.getCreatedBy())
						.setInteger("enabled", enabled)
						.uniqueResult();
			}else {
				username = vnd.getId();
				
				session.createSQLQuery(user)
					.setString("userlogin",vnd.getUserlogin())
					.setString("updatedBy", vnd.getCreatedBy())
					.setInteger("enabled", enabled)
					.setInteger("id",username)
					.executeUpdate();
				
				if(vnd.getPassword() != null && isPassChanged) {
					session.createSQLQuery(userPassword)
					.setString("password",Utils.hashPassword(vnd.getPassword()))
					.setString("updatedBy", vnd.getCreatedBy())
					.setInteger("enabled", enabled)
					.setInteger("id",username)
					.executeUpdate();
				}
			}
			
			if(vnd.getPhoto() != null){
				try {
		    		Path pathRequestImage = Paths.get(ConsParam.PATH_IMAGE + username + ".png" );
			    	Files.write(pathRequestImage, vnd.getPhoto());
				}catch (Exception e) {
					System.out.println(e.getMessage());
				}
	    	}
			
			session.createSQLQuery(vendor)
				.setInteger("username",username)
				.setString("idSap", vnd.getIdSap())
				.setString("updatedBy", vnd.getCreatedBy())
				.setBinary("photo", vnd.getPhoto())
				.setString("email", vnd.getEmail())
				.executeUpdate();
			
			session.createSQLQuery(deleteGroupMember)
				.setInteger("id",username)
				.executeUpdate();
			
			for(GroupMember d : gm) {
				session.createSQLQuery(insertGroupMember)
					.setString("groupId",d.getGroupId())
					.setInteger("username", username)
					.setString("createdBy", d.getCreatedBy())
					.executeUpdate();
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
	public Boolean deleteVendor(String id,String username) {
		Session session =  sessionFactory.getCurrentSession();
		boolean result = true;
		Transaction tx = null;		
		String query = "UPDATE vendor set enabled = 0,deleted = 1, updated_by=:username, updated_date = CURRENT_TIMESTAMP where id=:id";
		
		try {
			tx = session.beginTransaction();
				session.createSQLQuery(query)
					.setString("id", id)
					.setString("username", username)
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
	public Object getAllVendor(String username) {
		List<Object> result = new ArrayList<Object>();
		Map<String,Object> param = (Map<String, Object>) getUserPlantSloc(username);
		
		String query = "select id_sap as id, full_name as name,id_sloc as idSloc,id_plant as idPlant from vendor where id_sloc LIKE :idSloc and id_plant LIKE :idPlant "; 
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
					.setString("idSloc", (param.get("idSloc") != null ? param.get("idSloc").toString() : "%%" ))
					.setString("idPlant", (param.get("idPlant") != null ? param.get("idPlant").toString() : "%%" ))
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.list();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Object getAllMaterial(String username) {
		List<Object> result = new ArrayList<Object>();
		Map<String,Object> param = (Map<String, Object>) getUserPlantSloc(username);
		String query = "select id, name,id_sloc as idSloc,id_plant as idPlant from material where id_sloc LIKE :idSloc and id_plant LIKE :idPlant "; 
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
					.setString("idSloc", (param.get("idSloc") != null ? param.get("idSloc").toString() : "%%" ))
					.setString("idPlant", (param.get("idPlant") != null ? param.get("idPlant").toString() : "%%" ))
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.list();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public Object getAllUserPlantSloc(String username){
		List<Object> result = new ArrayList<Object>();
		String getTipe = "SELECT type FROM users where username =:username ";
		String employee = "SELECT id_sloc as idSloc, id_plant as idPlant FROM employee where id =:username ";
		String vendor = "SELECT id_sloc as idSloc, id_plant as idPlant FROM vendor where id =:username";
		
		try {
			Session session = sessionFactory.getCurrentSession();
			String tipe = (String) session.createSQLQuery(getTipe)
					.setString("username", username)
					.uniqueResult();
			
			if(tipe.toUpperCase().equals("E")) {
				result = session.createSQLQuery(employee)
						.setString("username", username)
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
						.list();
			}else {
				result = session.createSQLQuery(vendor)
						.setString("username", username)
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
						.list();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public boolean checkIsVendor(String username){
		boolean result = false;
		String query = 
				  "SELECT CASE WHEN EXISTS ("
				+ "	SELECT 1 "
				+ "	FROM users "
				+ "	where username = :username AND type = 'V'"
				+ ")"
				+ "THEN CAST(1 AS BIT)"
				+ "ELSE CAST(0 AS BIT) END";
		
		try {
			Session session = sessionFactory.getCurrentSession();
			result = (boolean) session.createSQLQuery(query)
					.setString("username", username)
					.uniqueResult();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}	
	
	@Override
	public Object getOEM(Object filter) {
		List<Object> result = new ArrayList<Object>();
		Map<String,Object> r = (Map<String, Object>) filter;
		Map<String,Object> param = (Map<String, Object>) getUserPlantSloc(r.get("username").toString());
		Integer offset = Integer.valueOf(r.get("curPage").toString()) * 15;
		
		String query = "select u.id_vendor_sap as idVendor,v.full_name as vendorName ,u.material_no as idMaterial, m.name as materialName, "
				+ "u.qty_oem as qtyOem,u.uom_oem as uomOem,u.qty_sap as qtySap,u.uom_sap as uomSap, "
				+ "COUNT(*) OVER () as totalPage  "
				+ "FROM uom_conversion u "
				+ "INNER JOIN VENDOR v ON u.id_vendor_sap = v.id_sap "
				+ "INNER JOIN material m ON u.material_no = m.id AND v.id_plant = m.id_plant and v.id_sloc = m.id_sloc "
				+ "where u.deleted = 0 "
				+ "AND COALESCE(u.id_vendor_sap,'') LIKE :idVendor AND COALESCE(v.full_name,'') LIKE :vendorName AND "
				+ "COALESCE(u.material_no,'') LIKE :idMaterial AND COALESCE(m.name,'') LIKE :materialName AND "
				+ "COALESCE(u.uom_sap,'') LIKE :uomSap AND COALESCE(u.uom_oem,'') LIKE :uomOem  "
				+ "AND COALESCE(v.id_sloc,'') LIKE :idSloc AND COALESCE(v.id_plant,'') LIKE :idPlant "
//				+ "AND COALESCE(CAST(u.qty_sap AS CHAR),'') LIKE :qtySap AND COALESCE(CAST(u.qty_oem AS CHAR),'') LIKE :qtyOem "
				+ "ORDER BY u.id_vendor_sap,u.material_no "
				+ "OFFSET :offset ROWS FETCH NEXT 15 ROWS ONLY ";
		try {
			Session session = sessionFactory.getCurrentSession();
			result =  session.createSQLQuery(query)
					.setString("idVendor",(!r.get("idVendor").toString().isEmpty()  ? "%" + r.get("idVendor").toString() + "%" : "%%")) 
					.setString("vendorName",(!r.get("vendorName").toString().isEmpty()  ? "%" +  r.get("vendorName").toString() + "%" : "%%")) 
					.setString("idMaterial", (!r.get("idMaterial").toString().isEmpty()  ? "%" +  r.get("idMaterial").toString() + "%" : "%%"))
					.setString("materialName", (!r.get("materialName").toString().isEmpty()  ? "%" +  r.get("materialName").toString() + "%" : "%%"))
					.setString("uomOem", (!r.get("uomOem").toString().isEmpty()  ? "%" +  r.get("uomOem").toString() + "%" : "%%"))
					.setString("uomSap", (!r.get("uomSap").toString().isEmpty() ? "%" + r.get("uomSap").toString() + "%" : "%%"))
//					.setString("qtyOem", (!r.get("qtyOem").toString().isEmpty()  ? r.get("qtyOem").toString() : "%%"))
//					.setString("qtySap", (r.get("qtySap").toString().isEmpty()  ? r.get("qtySap").toString() : "%%"))
					.setString("idSloc", (param.get("idSloc") != null ? "%" +  param.get("idSloc").toString() + "%" : "%%" ))
					.setString("idPlant", (param.get("idPlant") != null ? "%" + param.get("idPlant").toString() + "%" : "%%" ))
					.setInteger("offset", offset)
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Boolean createOEM(Object request) {
		Session session =  sessionFactory.getCurrentSession();
		boolean result = true;
		Transaction tx = null;		
		String query = "UPDATE uom_conversion "
				+ " SET qty_oem= :qtyOem, uom_oem= :uomOem, "
				+ " qty_sap = :qtySap, uom_sap = :uomSap, updated_by = :createdBy, updated_date = CURRENT_TIMESTAMP "
				+ " WHERE id_vendor_sap=:idVendor and material_no = :idMaterial "
				+ " IF @@ROWCOUNT = 0 "
				+ " INSERT INTO uom_conversion (id_vendor_sap,material_no,qty_oem,uom_oem,qty_sap,uom_sap,created_by,created_date) values "
				+ " (:idVendor,:idMaterial,:qtyOem,:uomOem,:qtySap,:uomSap,:createdBy,CURRENT_TIMESTAMP)";
		
		try {
			tx = session.beginTransaction();
			List<Object> req = (List<Object>) request;
			for(Object o : req) {
				Map<String,Object> r = (Map<String, Object>) o;
				
				session.createSQLQuery(query)
					.setString("idVendor",(r.get("idVendor") != null ? r.get("idVendor").toString() : "")) 
					.setString("idMaterial", (r.get("idMaterial") != null ? r.get("idMaterial").toString() : ""))
					.setBigDecimal("qtyOem", (BigDecimal) (r.get("qtyOem") != null ? new BigDecimal((r.get("qtyOem").toString())) : ""))
					.setString("uomOem", (r.get("uomOem") != null ? r.get("uomOem").toString() : ""))
					.setBigDecimal("qtySap", (BigDecimal) (r.get("qtySap") != null ? new BigDecimal((r.get("qtySap").toString())) : ""))
					.setString("uomSap", (r.get("uomSap") != null ? r.get("uomSap").toString() : ""))
					.setString("createdBy", (r.get("createdBy") != null ? r.get("createdBy").toString() : ""))
				.executeUpdate();
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
	public Boolean updateOEM(Object request) {
		Session session =  sessionFactory.getCurrentSession();
		boolean result = true;
		Transaction tx = null;		
		String query = "UPDATE uom_conversion SET qty_oem =:qtyOem,uom_oem =:uomOem, "
				+ "qty_sap =:qtySap, uom_sap =:uomSap ,updated_by =:updatedBy,updated_date=CURRENT_TIMESTAMP "
				+ "WHERE id_vendor_sap =:idVendor AND material_no =:idMaterial ";
		
		try {
			tx = session.beginTransaction();
				Map<String,Object> r = (Map<String, Object>) request;
				
				session.createSQLQuery(query)
					.setString("idVendor",(r.get("idVendor") != null ? r.get("idVendor").toString() : "")) 
					.setString("idMaterial", (r.get("idMaterial") != null ? r.get("idMaterial").toString() : ""))
					.setBigDecimal("qtyOem", (BigDecimal) (r.get("qtyOem") != null ? new BigDecimal((r.get("qtyOem").toString())) : ""))
					.setString("uomOem", (r.get("uomOem") != null ? r.get("uomOem").toString() : ""))
					.setBigDecimal("qtySap", (BigDecimal) (r.get("qtySap") != null ? new BigDecimal((r.get("qtySap").toString())) : ""))
					.setString("uomSap", (r.get("uomSap") != null ? r.get("uomSap").toString() : ""))
					.setString("updatedBy", (r.get("updatedBy") != null ? r.get("updatedBy").toString() : ""))
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
	public Boolean deleteOEM(Object request) {
		Session session =  sessionFactory.getCurrentSession();
		boolean result = true;
		Transaction tx = null;		
//		String query = "UPDATE uom_conversion SET enabled = 0,deleted=1,updated_by =:updatedBy,updated_date=CURRENT_TIMESTAMP "
//				+ "WHERE id_vendor =:idVendor AND material_no =:idMaterial ";
		
		String query = "DELETE FROM uom_conversion WHERE id_vendor_sap =:idVendor AND material_no =:idMaterial ";
		
		try {
			tx = session.beginTransaction();
				Map<String,Object> r = (Map<String, Object>) request;
				
				session.createSQLQuery(query)
					.setString("idVendor",(r.get("idVendor") != null ? r.get("idVendor").toString() : "")) 
					.setString("idMaterial", (r.get("idMaterial") != null ? r.get("idMaterial").toString() : ""))
				.executeUpdate();
				
			tx.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			if (tx!=null) tx.rollback();
			result = false;
		}
		
		return result;
	}
	
	Object getUserPlantSloc(String username){
		Map<String,Object> result = new HashMap<String, Object>();
		String getTipe = "SELECT type FROM users where username =:username ";
		String employee = "SELECT id_sloc as idSloc, id_plant as idPlant FROM employee where id =:username ";
		String vendor = "SELECT id_sloc as idSloc, id_plant as idPlant FROM vendor where id =:username";
		
		try {
			Session session = sessionFactory.getCurrentSession();
			String tipe = (String) session.createSQLQuery(getTipe)
					.setString("username", username)
					.uniqueResult();
			
			if(tipe.toUpperCase().equals("E")) {
				result = (Map<String, Object>) session.createSQLQuery(employee)
						.setString("username", username)
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
						.uniqueResult();
			}else {
				result = (Map<String, Object>) session.createSQLQuery(vendor)
						.setString("username", username)
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
						.uniqueResult();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String getPlantName(String idPlant) {
		String query = " SELECT TOP 1 name FROM PLANT WHERE id = :idPlant ";
		String result = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			 result = (String) session.createSQLQuery(query)
					.setString("idPlant", idPlant)
					.uniqueResult();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
