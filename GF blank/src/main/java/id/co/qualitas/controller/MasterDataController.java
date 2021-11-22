package id.co.qualitas.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import id.co.qualitas.component.Constants;
import id.co.qualitas.domain.Device;
import id.co.qualitas.domain.Sloc;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.service.interfaces.MasterDataService;

@RestController
@RequestMapping("/api/v1/masterData/")
public class MasterDataController extends BaseController{
	@Autowired
	private MasterDataService mdService;
	
	@RequestMapping(value="getSLOC",method=RequestMethod.POST)
	public WSMessage getSLOC(@ModelAttribute("init_load")Map init,@RequestBody Object filter){
		return mdService.getSloc(filter);
	}
	
	@RequestMapping(value = "updateLoadingPoint", method=RequestMethod.POST)
	public WSMessage updateLoadingPoint(@RequestBody Sloc request,@ModelAttribute("init_load")Map init){
		request.setCreatedBy(init.get(Constants.USERNAME).toString());
		return mdService.updateLoadingPoint(request);
	}
	
	@RequestMapping(value="getPlant",method=RequestMethod.POST)
	public WSMessage getPlant(@ModelAttribute("init_load")Map init,@RequestBody Object filter){
		return mdService.getPlant(filter);
	}
	@RequestMapping(value="getAllPlant",method=RequestMethod.GET)
	public WSMessage getAllPlant(@ModelAttribute("init_load")Map init){
		return mdService.getAllPlant();
	}

	@RequestMapping(value="getMaterial",method=RequestMethod.POST)
	public WSMessage getMaterial(@ModelAttribute("init_load")Map init, @RequestBody Object filter){
		return mdService.getMaterial(filter);
	}
	
	@RequestMapping(value="getUserlogin",method=RequestMethod.GET)
	public WSMessage getUserlogin(){
		return mdService.getUserlogin();
	}
	
	@RequestMapping(value="getDevice",method=RequestMethod.POST)
	public WSMessage getDevice(@ModelAttribute("init_load")Map init,@RequestBody Object filter){
		return mdService.getDevice(filter);
	}
	
	@RequestMapping(value="getDeviceDetail",method=RequestMethod.GET)
	public WSMessage getDevice(@ModelAttribute("init_load")Map init,String id){
		return mdService.getDeviceDetail(id);
	}
	
	@RequestMapping(value = "createDevice", method=RequestMethod.POST)
	public WSMessage createDevice(@RequestBody Device request,@ModelAttribute("init_load")Map init){
		request.setCreatedBy(init.get(Constants.USERNAME).toString());
		return mdService.createDevice(request);
	}
	
	@RequestMapping(value = "updateDevice", method=RequestMethod.POST)
	public WSMessage updateDevice(@RequestBody Device request,@ModelAttribute("init_load")Map init){
		request.setCreatedBy(init.get(Constants.USERNAME).toString());
		return mdService.updateDevice(request);
	}
	
	@RequestMapping(value = "updateUsername", method=RequestMethod.POST)
	public WSMessage updateUsername(@RequestBody Object request,@ModelAttribute("init_load")Map init){
		return mdService.updateUsername(request,init.get(Constants.USERNAME).toString());
	}
	
	@RequestMapping(value = "deleteDevice", method=RequestMethod.POST)
	public WSMessage deleteDevice(@RequestBody String id,@ModelAttribute("init_load")Map init){
		return mdService.deleteDevice(id,init.get(Constants.USERNAME).toString());
	}
	
	@RequestMapping(value="getGroups",method=RequestMethod.POST)
	public WSMessage getGroups(@ModelAttribute("init_load")Map init,@RequestBody Object filter){
		return mdService.getGroups(filter);
	}
	
	@RequestMapping(value="getAllGroups",method=RequestMethod.GET)
	public WSMessage getAllGroups(@ModelAttribute("init_load")Map init){
		return mdService.getAllGroups();
	}
	
	@RequestMapping(value="getAllAuthority",method=RequestMethod.GET)
	public WSMessage getAllAuthority(@ModelAttribute("init_load")Map init){
		return mdService.getAllAuthority();
	}
	
	@RequestMapping(value = "createGroups", method=RequestMethod.POST)
	public WSMessage createGroups(@RequestBody Object request,@ModelAttribute("init_load")Map init){
		return mdService.createGroups(request,init.get(Constants.USERNAME).toString());
	}
	
	@RequestMapping(value = "deleteGroups", method=RequestMethod.POST)
	public WSMessage deleteGroups(@RequestBody String id,@ModelAttribute("init_load")Map init){
		return mdService.deleteGroups(id,init.get(Constants.USERNAME).toString());
	}
	
	@RequestMapping(value="getGroupsDetail",method=RequestMethod.GET)
	public WSMessage getGroupsDetail(@ModelAttribute("init_load")Map init,String id){
		return mdService.getGroupsDetail(id);
	}
	
	@RequestMapping(value = "updateRole", method=RequestMethod.POST)
	public WSMessage updateRole(@RequestBody Object request,@ModelAttribute("init_load")Map init){
		return mdService.updateRole(request,init.get(Constants.USERNAME).toString());
	}
	
	@RequestMapping(value="getEmployee",method=RequestMethod.POST)
	public WSMessage getEmployee(@ModelAttribute("init_load")Map init,@RequestBody Object filter){
		return mdService.getEmployee(filter);
	}
	
	@RequestMapping(value="getEmployeeDetail",method=RequestMethod.GET)
	public WSMessage getEmployeeDetail(@ModelAttribute("init_load")Map init,String id){
		return mdService.getEmployeeDetail(id);
	}
	
	@RequestMapping(value = "createEmployee", method=RequestMethod.POST)
	public WSMessage createEmployee(@RequestBody Object request,@ModelAttribute("init_load")Map init){
		return mdService.createEmployee(request,init.get(Constants.USERNAME).toString());
	}
	
	@RequestMapping(value = "updateEmployee", method=RequestMethod.POST)
	public WSMessage updateEmployee(@RequestBody Object request,@ModelAttribute("init_load")Map init){
		return mdService.updateEmployee(request,init.get(Constants.USERNAME).toString());
	}
	
	@RequestMapping(value = "deleteEmployee", method=RequestMethod.POST)
	public WSMessage deleteEmployee(@RequestBody String id,@ModelAttribute("init_load")Map init){
		return mdService.deleteEmployee(id,init.get(Constants.USERNAME).toString());
	}
	
	@RequestMapping(value="getVendor",method=RequestMethod.POST)
	public WSMessage getVendor(@ModelAttribute("init_load")Map init,@RequestBody Object filter){
		return mdService.getVendor(filter);
	}
	
	@RequestMapping(value="getVendorDetail",method=RequestMethod.GET)
	public WSMessage getVendorDetail(@ModelAttribute("init_load")Map init,String id, String idSloc, String idPlant){//test
		return mdService.getVendorDetail(id, idSloc, idPlant);
	}
	
	@RequestMapping(value = "updateVendor", method=RequestMethod.POST)
	public WSMessage updateVendor(@RequestBody Object request,@ModelAttribute("init_load")Map init){
		return mdService.updateVendor(request,init.get(Constants.USERNAME).toString());
	}
	
	@RequestMapping(value = "deleteVendor", method=RequestMethod.POST)
	public WSMessage deleteVendor(@RequestBody String id,@ModelAttribute("init_load")Map init){
		return mdService.deleteVendor(id,init.get(Constants.USERNAME).toString());
	}
	
	@RequestMapping(value="getParam",method=RequestMethod.GET)
	public WSMessage getParam(@ModelAttribute("init_load")Map init){
		
		return mdService.getParam(init.get(Constants.USERNAME).toString());
	}
	
	@RequestMapping(value="getOEM",method=RequestMethod.POST)
	public WSMessage getOEM(@ModelAttribute("init_load")Map init,@RequestBody Object filter){
		Map<String,Object> req = (Map<String, Object>) filter;
		req.put("username",init.get(Constants.USERNAME).toString());
		return mdService.getOEM(filter);
	}
	
	@RequestMapping(value = "createOEM", method=RequestMethod.POST)
	public WSMessage createOEM(@RequestBody Object request,@ModelAttribute("init_load")Map init){
		return mdService.createOEM(request,init.get(Constants.USERNAME).toString());
	}
	
	@RequestMapping(value = "updateOEM", method=RequestMethod.POST)
	public WSMessage updateOEM(@RequestBody Object request,@ModelAttribute("init_load")Map init){
		return mdService.updateOEM(request,init.get(Constants.USERNAME).toString());
	}
	
	@RequestMapping(value = "deleteOEM", method=RequestMethod.POST)
	public WSMessage deleteOEM(@RequestBody Object request,@ModelAttribute("init_load")Map init){
		return mdService.deleteOEM(request,init.get(Constants.USERNAME).toString());
	}
}
