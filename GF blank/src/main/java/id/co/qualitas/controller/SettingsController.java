package id.co.qualitas.controller;

import id.co.qualitas.component.Constants;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.service.interfaces.EmployeeService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/settings/")
public class SettingsController extends BaseController{
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping(value="changePassword",method=RequestMethod.GET)
	public WSMessage getDetail(@ModelAttribute("init_load")Map init,
			@RequestParam String deviceId){
		
		String username = init.get(Constants.USERNAME).toString();
		return employeeService.getDetail(username, deviceId);
	}
	

//	@RequestMapping(value="getDetail",method=RequestMethod.GET)
//	public Map<String, Object>  getDetail(@ModelAttribute("init_load")Map init){
//		
//		String username = init.get(Constants.USERNAME).toString();
//		return employeeService.getDetail(username, "");
//	}
	
}
