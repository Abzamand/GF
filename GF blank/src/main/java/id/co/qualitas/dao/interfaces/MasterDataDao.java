package id.co.qualitas.dao.interfaces;

import java.util.List;

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


public interface MasterDataDao {

	List<Sloc> getSLOC(Object filter);
	Boolean updateLoadingPoint(Sloc request);
	
	List<Material> getMaterial(Object filter);
	List<Plant> getPlant(Object filter);
	List<Plant> getAllPlant();
	String getPlantName(String idPlant);

	Object getUserlogin();
	List<Device> getDevice(Object filter);
	Device getDeviceDetail(String id);
	Boolean createDevice(Device request);
	Boolean updateDevice (Device request);
	Boolean updateUsername(String imei,String username,String status);
	Boolean deleteDevice(String id,String username);
	
	List<Group> getGroups(Object filter);
	Boolean createGroups(Group request);
	List<Group> getAllGroups();
	Boolean deleteGroups(String id,String username);
	
	Group getGroupsDetail(String id);
	List<GroupAuthority> getGroupAuthority(String id);
	List<Authority> getAuthority();
	Boolean updateGroups(Group request);
	Boolean updateGroupAuthority(List<GroupAuthority> request);
	
	List<EmployeeMD> getEmployee(Object filter);
	EmployeeMD getEmployeeDetail(String userlogin);
	Boolean createEmployee(EmployeeMD emp,List<GroupMember> gm);
	Boolean updateEmployee(EmployeeMD emp,List<GroupMember> gm, boolean isPassChanged);
	Boolean deleteEmployee(String id,String username);
	
	List<VendorMD> getVendor(Object filter);
//	VendorMD getVendorDetail(String userlogin);
	Boolean updateVendor(VendorMD vnd,List<GroupMember> gm, boolean isPassChanged);
	Boolean deleteVendor(String id,String username);
	
	Object getAllVendor(String username);
	Object getAllMaterial(String username);
	Object getAllUserPlantSloc(String username);
	
	Object getOEM(Object filter);
	Boolean createOEM(Object request);
	Boolean updateOEM(Object request);
	Boolean deleteOEM(Object request);
	boolean checkIsVendor(String username);
	VendorMD getVendorDetail(String idSap, String idSloc, String idPlant);
	
	
}
