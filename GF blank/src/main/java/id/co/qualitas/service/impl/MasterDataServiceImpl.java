package id.co.qualitas.service.impl;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import id.co.qualitas.component.Cons;
import id.co.qualitas.component.ConsMessage;
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
import id.co.qualitas.domain.VendorMD;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.service.interfaces.MasterDataService;

@Service
public class MasterDataServiceImpl implements MasterDataService {
	@Autowired
	MasterDataDao mdDao;

	@Override
	public WSMessage getSloc(Object filter) {
		WSMessage result = new WSMessage();
		
		List<Sloc> data = mdDao.getSLOC(filter);
		
		if(data.size() > 0) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}
	
	@Override
	public WSMessage updateLoadingPoint(Sloc request) {
		WSMessage result = new WSMessage();
		
		Boolean data = mdDao.updateLoadingPoint(request);
		
		if(data) {
			result.setIdMessage(1);
			result.setMessage(ConsMessage.MESSAGE_SUCCESS);
		}else {
			result.setIdMessage(0);
			result.setMessage(ConsMessage.MESSAGE_FAILED);
		}
		
		return result;
	}

	@Override
	public WSMessage getMaterial(Object filter) {
		WSMessage result = new WSMessage();
		
		List<Material> data = mdDao.getMaterial(filter);
		
		if(data.size() > 0) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}
	
	public WSMessage getPlant(Object filter) {
		WSMessage result = new WSMessage();
		
		List<Plant> data = mdDao.getPlant(filter);
		
		if(data.size() > 0) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}

	@Override
	public WSMessage getAllPlant() {
		WSMessage result = new WSMessage();

		List<Plant> data = mdDao.getAllPlant();

		if(data.size() > 0) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}

		return result;
	}

	@Override
	public WSMessage getUserlogin() {
		WSMessage result = new WSMessage();
		
		Object data = mdDao.getUserlogin();
		
		if(data != null) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}

	@Override
	public WSMessage getDevice(Object filter) {
		WSMessage result = new WSMessage();
		
		List<Device> data = mdDao.getDevice(filter);
		
		if(data.size() > 0) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}

	@Override
	public WSMessage getDeviceDetail(String id) {
		WSMessage result = new WSMessage();
		
		Device data = mdDao.getDeviceDetail(id);
		
		if(data.getId() != null) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}

	@Override
	public WSMessage createDevice(Device request) {
		WSMessage result = new WSMessage();
		
		Boolean data = mdDao.createDevice(request);
		
		if(data) {
			result.setIdMessage(1);
			result.setMessage(ConsMessage.MESSAGE_SUCCESS);
		}else {
			result.setIdMessage(0);
			result.setMessage(ConsMessage.MESSAGE_FAILED);
		}
		
		return result;
	}
	
	@Override
	public WSMessage updateDevice(Device request) {
		WSMessage result = new WSMessage();
		
		Boolean data = mdDao.updateDevice(request);
		
		if(data) {
			result.setIdMessage(1);
			result.setMessage(ConsMessage.MESSAGE_SUCCESS);
		}else {
			result.setIdMessage(0);
			result.setMessage(ConsMessage.MESSAGE_FAILED);
		}
		
		return result;
	}
	
	@Override
	public WSMessage deleteDevice(String id,String username) {
		WSMessage result = new WSMessage();
		Boolean data = mdDao.deleteDevice(id, username);
		
		if(data) {
			result.setIdMessage(1);
			result.setMessage(ConsMessage.MESSAGE_SUCCESS);
		}else {
			result.setIdMessage(0);
			result.setMessage(ConsMessage.MESSAGE_FAILED);
		}
		
		return result;
	}
	

	@Override
	public WSMessage updateUsername(Object request,String username) {
		WSMessage result = new WSMessage();
		Map<String,Object> req = (Map<String, Object>) request;
		Boolean data = mdDao.updateUsername(req.get("imei").toString(),username,req.get("status").toString());
		
		if(data) {
			result.setIdMessage(1);
			result.setMessage(ConsMessage.MESSAGE_SUCCESS);
		}else {
			result.setIdMessage(0);
			result.setMessage(ConsMessage.MESSAGE_FAILED);
		}
		
		return result;
	}


	@Override
	public WSMessage getGroups(Object filter) {
		WSMessage result = new WSMessage();
		
		List<Group> data = mdDao.getGroups(filter);
		
		if(data.size() > 0) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}
	
	@Override
	public WSMessage getAllAuthority() {
		WSMessage result = new WSMessage();
		
		List<Authority> data = mdDao.getAuthority();
		
		if(data.size() > 0) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}
	
	@Override
	public WSMessage getAllGroups() {
		WSMessage result = new WSMessage();
		
		List<Group> data = mdDao.getAllGroups();
		
		if(data.size() > 0) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}
	
	@Override
	public WSMessage createGroups(Object request,String username) {
		WSMessage result = new WSMessage();
		Boolean saveResult = true;
		Map<String,Object> tempReq = (Map<String, Object>) request;
		ArrayList listAuth = (ArrayList) tempReq.get("authority");
		ObjectMapper mapper = new ObjectMapper(); 
		Group group = mapper.convertValue(tempReq.get("group"), Group.class);
		group.setCreatedBy(username);
		
		List<GroupAuthority> listGroupAuth = new ArrayList<GroupAuthority>();
		for(Object d : listAuth) {
			GroupAuthority groupAuth = new GroupAuthority();
			Authority auth = mapper.convertValue(d, Authority.class);
			
			groupAuth.setAuthority(auth.getAuthority());
			groupAuth.setGroupId(group.getId());
			groupAuth.setCreatedBy(username);
			
			listGroupAuth.add(groupAuth);
		}
		
		if(group.getId() != null) {
			saveResult = mdDao.createGroups(group);
			if(saveResult) {
				saveResult = mdDao.updateGroupAuthority(listGroupAuth);
			}
		}
		
		if(saveResult) {
			result.setIdMessage(1);
			result.setMessage(ConsMessage.MESSAGE_SUCCESS);
		}else {
			result.setIdMessage(0);
			result.setMessage(ConsMessage.MESSAGE_FAILED);
		}
		
		return result;
	}
	
	@Override
	public WSMessage deleteGroups(String id,String username) {
		WSMessage result = new WSMessage();
		Boolean data = mdDao.deleteGroups(id, username);
		
		if(data) {
			result.setIdMessage(1);
			result.setMessage(ConsMessage.MESSAGE_SUCCESS);
		}else {
			result.setIdMessage(0);
			result.setMessage(ConsMessage.MESSAGE_FAILED);
		}
		
		return result;
	}

	@Override
	public WSMessage getGroupsDetail(String id) {
		WSMessage result = new WSMessage();
		Map<String,Object> tempResult = new HashMap<String, Object>();
		
		Group data = mdDao.getGroupsDetail(id);
		
		if(data.getId() != null) {
			tempResult.put("data", data);
			tempResult.put("active", mdDao.getGroupAuthority(data.getId()));
			tempResult.put("authority", mdDao.getAuthority());
			result.setIdMessage(1);
			result.setResult(tempResult);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}

	@Override
	public WSMessage updateRole(Object request,String username) {
		WSMessage result = new WSMessage();
		Boolean saveResult = true;
		Map<String,Object> tempReq = (Map<String, Object>) request;
		ArrayList listAuth = (ArrayList) tempReq.get("authority");
		ObjectMapper mapper = new ObjectMapper(); 
		Group group = mapper.convertValue(tempReq.get("group"), Group.class);
		group.setCreatedBy(username);
		
		List<GroupAuthority> listGroupAuth = new ArrayList<GroupAuthority>();
		for(Object d : listAuth) {
			GroupAuthority groupAuth = new GroupAuthority();
			Authority auth = mapper.convertValue(d, Authority.class);
			
			groupAuth.setAuthority(auth.getAuthority());
			groupAuth.setGroupId(group.getId());
			groupAuth.setCreatedBy(username);
			
			listGroupAuth.add(groupAuth);
		}
		
		if(group.getId() != null) {
			saveResult = mdDao.updateGroups(group);
			if(saveResult) {
				saveResult = mdDao.updateGroupAuthority(listGroupAuth);
			}
		}
		
		if(saveResult) {
			result.setIdMessage(1);
			result.setMessage(ConsMessage.MESSAGE_SUCCESS);
		}else {
			result.setIdMessage(0);
			result.setMessage(ConsMessage.MESSAGE_FAILED);
		}
		
		return result;
	}

	@Override
	public WSMessage getEmployee(Object filter) {
		WSMessage result = new WSMessage();
		
		List<EmployeeMD> data = mdDao.getEmployee(filter);
		
		if(data.size() > 0) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}

	@Override
	public WSMessage getEmployeeDetail(String userlogin) {
		WSMessage result = new WSMessage();
		EmployeeMD data = mdDao.getEmployeeDetail(userlogin);
		
		if(data.getUserlogin() != null) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}

	@Override
	public WSMessage createEmployee(Object request,String username) {
		WSMessage result = new WSMessage();
		Map<String,Object> tempReq = (Map<String, Object>) request;
		ArrayList listMem = (ArrayList) tempReq.get("groupmember");
		ObjectMapper mapper = new ObjectMapper(); 
		EmployeeMD emp = mapper.convertValue(tempReq.get("employee"), EmployeeMD.class);
		emp.setCreatedBy(username);
		
		if(emp.getPhotoString() != null && !emp.getPhotoString().isEmpty()){
			emp.setPhoto(Base64.decodeBase64(emp.getPhotoString().toString()));
    	}
		
		List<GroupMember> gm = new ArrayList<GroupMember>();
		for(Object d : listMem) {
			Map<String,Object> temp = (Map<String, Object>) d;
			GroupMember groupMem = new GroupMember();
			groupMem.setGroupId(temp.get("id").toString());
			groupMem.setCreatedBy(username);
			gm.add(groupMem);
		}
		
		
		if(mdDao.createEmployee(emp, gm)) {
			result.setIdMessage(1);
			result.setMessage(ConsMessage.MESSAGE_SUCCESS);
		}else {
			result.setIdMessage(0);
			result.setMessage(ConsMessage.MESSAGE_FAILED);
		}
		
		return result;
	}
	
	@Override
	public WSMessage updateEmployee(Object request,String username) {
		WSMessage result = new WSMessage();
		Map<String,Object> tempReq = (Map<String, Object>) request;
		ArrayList listMem = (ArrayList) tempReq.get("groupmember");
		ObjectMapper mapper = new ObjectMapper(); 
		EmployeeMD emp = mapper.convertValue(tempReq.get("employee"), EmployeeMD.class);
		emp.setCreatedBy(username);
		
		if(emp.getPhotoString() != null && !emp.getPhotoString().isEmpty()){
			emp.setPhoto(Base64.decodeBase64(emp.getPhotoString().toString()));
    	}
		
		List<GroupMember> gm = new ArrayList<GroupMember>();
		for(Object d : listMem) {
			Map<String,Object> temp = (Map<String, Object>) d;
			GroupMember groupMem = new GroupMember();
			groupMem.setGroupId(temp.get("id").toString());
			groupMem.setCreatedBy(username);
			gm.add(groupMem);
		}
		
		boolean isPassChanged = emp.getIsPasswordChanged() == 1 ? true : false;
		
		if(mdDao.updateEmployee(emp, gm, isPassChanged)) {
			result.setIdMessage(1);
			result.setMessage(ConsMessage.MESSAGE_SUCCESS);
		}else {
			result.setIdMessage(0);
			result.setMessage(ConsMessage.MESSAGE_FAILED);
		}
		
		return result;
	}

	@Override
	public WSMessage deleteEmployee(String id,String username) {
		WSMessage result = new WSMessage();
		Boolean data = mdDao.deleteEmployee(id, username);
		
		if(data) {
			result.setIdMessage(1);
			result.setMessage(ConsMessage.MESSAGE_SUCCESS);
		}else {
			result.setIdMessage(0);
			result.setMessage(ConsMessage.MESSAGE_FAILED);
		}
		
		return result;
	}
	
	@Override
	public WSMessage getVendor(Object filter) {
		WSMessage result = new WSMessage();
		
		List<VendorMD> data = mdDao.getVendor(filter);
		
		if(data.size() > 0) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}

	@Override
	public WSMessage getVendorDetail(String idSap, String idSloc, String idPlant) {
		WSMessage result = new WSMessage();
		VendorMD data = mdDao.getVendorDetail(idSap, idSloc, idPlant);
		
		if(data.getIdSap() != null) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}

	@Override
	public WSMessage updateVendor(Object request, String username) {
		WSMessage result = new WSMessage();
		Map<String,Object> tempReq = (Map<String, Object>) request;
		ArrayList listMem = (ArrayList) tempReq.get("groupmember");
		ObjectMapper mapper = new ObjectMapper(); 
		VendorMD vnd = mapper.convertValue(tempReq.get("vendor"), VendorMD.class);
		
    	if(vnd.getPhotoString() != null && !vnd.getPhotoString().isEmpty()){
    		vnd.setPhoto(Base64.decodeBase64(vnd.getPhotoString().toString()));
    	}
    	
		vnd.setCreatedBy(username);
		
		List<GroupMember> gm = new ArrayList<GroupMember>();
		for(Object d : listMem) {
			Map<String,Object> temp = (Map<String, Object>) d;
			GroupMember groupMem = new GroupMember();
			groupMem.setGroupId(temp.get("id").toString());
			groupMem.setCreatedBy(username);
			gm.add(groupMem);
		}

		boolean isPassChanged = vnd.getIsPasswordChanged() == 1 ? true : false;
		
		if(mdDao.updateVendor(vnd, gm, isPassChanged)) {
			result.setIdMessage(1);
			result.setMessage(ConsMessage.MESSAGE_SUCCESS);
		}else {
			result.setIdMessage(0);
			result.setMessage(ConsMessage.MESSAGE_FAILED);
		}
		
		return result;
	}
	
	@Override
	public WSMessage deleteVendor(String id,String username) {
		WSMessage result = new WSMessage();
		Boolean data = mdDao.deleteVendor(id, username);
		
		if(data) {
			result.setIdMessage(1);
			result.setMessage(ConsMessage.MESSAGE_SUCCESS);
		}else {
			result.setIdMessage(0);
			result.setMessage(ConsMessage.MESSAGE_FAILED);
		}
		
		return result;
	}

	@Override
	public WSMessage getParam(String username) {
		WSMessage result = new WSMessage();
		
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("vendor", mdDao.getAllVendor(username));
		data.put("material", mdDao.getAllMaterial(username));
		data.put("user", mdDao.getAllUserPlantSloc(username));
		data.put("isVendor", mdDao.checkIsVendor(username));
		if(data.size() > 0) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}
	
	
	@Override
	public WSMessage getOEM(Object filter) {
		WSMessage result = new WSMessage();
		
		List<Object> data = (List<Object>) mdDao.getOEM(filter);
		
		if(data.size() > 0) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;	
	}
	
	@Override
	public WSMessage createOEM(Object request,String username) {
		WSMessage result = new WSMessage();
		
		List<Object> req = (List<Object>) request;
		
		for(Object m : req) {
			Map<String,Object> r = (Map<String, Object>) m;
			r.put("createdBy", username);
		}
		
		Boolean data = mdDao.createOEM(req);
		
		if(data) {
			result.setIdMessage(1);
			result.setMessage(ConsMessage.MESSAGE_SUCCESS);
		}else {
			result.setIdMessage(0);
			result.setMessage(ConsMessage.MESSAGE_FAILED);
		}
		
		return result;
	}

	@Override
	public WSMessage updateOEM(Object request,String username) {
		WSMessage result = new WSMessage();
		
		Map<String,Object> r = (Map<String, Object>) request;
		r.put("updatedBy", username);
		
		Boolean data = mdDao.updateOEM(r);
		
		if(data) {
			result.setIdMessage(1);
			result.setMessage(ConsMessage.MESSAGE_SUCCESS);
		}else {
			result.setIdMessage(0);
			result.setMessage(ConsMessage.MESSAGE_FAILED);
		}
		
		return result;
	}

	@Override
	public WSMessage deleteOEM(Object request,String username) {
		WSMessage result = new WSMessage();
		
		Map<String,Object> r = (Map<String, Object>) request;
		r.put("updatedBy", username);
		
		Boolean data = mdDao.deleteOEM(r);
		
		if(data) {
			result.setIdMessage(1);
			result.setMessage(ConsMessage.MESSAGE_SUCCESS);
		}else {
			result.setIdMessage(0);
			result.setMessage(ConsMessage.MESSAGE_FAILED);
		}
		
		return result;
	}
}
