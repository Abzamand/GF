package id.co.qualitas.service.interfaces;

import id.co.qualitas.domain.Device;
import id.co.qualitas.domain.Sloc;
import id.co.qualitas.domain.response.WSMessage;

public interface MasterDataService {
	WSMessage getSloc(Object filter);
	WSMessage updateLoadingPoint(Sloc request);
	WSMessage getMaterial(Object filter);
	WSMessage getPlant(Object filter);
	WSMessage getAllPlant();

	WSMessage getUserlogin();
	WSMessage getDevice(Object filter);
	WSMessage getDeviceDetail(String id);
	WSMessage createDevice(Device request);
	WSMessage updateDevice(Device request);
	WSMessage updateUsername(Object request,String username);
	WSMessage deleteDevice(String id,String username);
	
	WSMessage getGroups(Object filter);
	WSMessage getAllGroups();
	WSMessage getAllAuthority();
	WSMessage createGroups(Object request,String username);
	WSMessage deleteGroups(String id,String username);
	WSMessage getGroupsDetail(String id);
	WSMessage updateRole(Object request,String username);
	
	WSMessage getEmployee(Object filter);
	WSMessage getEmployeeDetail(String userlogin);
	WSMessage createEmployee(Object request,String username);
	WSMessage updateEmployee(Object request,String username);
	WSMessage deleteEmployee(String id,String username);
	
	WSMessage getVendor(Object filter);
//	WSMessage getVendorDetail(String idSap);
	WSMessage updateVendor(Object request,String username);
	WSMessage deleteVendor(String id,String username);
	
	WSMessage getParam(String username);
	WSMessage getOEM(Object filter);
	WSMessage createOEM(Object request,String username);
	WSMessage updateOEM(Object request,String username);
	WSMessage deleteOEM(Object request,String username);
	WSMessage getVendorDetail(String idSap, String idSloc, String idPlant);
}
