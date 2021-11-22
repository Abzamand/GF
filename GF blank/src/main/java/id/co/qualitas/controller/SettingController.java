package id.co.qualitas.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import id.co.qualitas.component.Constants;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.service.interfaces.SettingService;

@RestController
@RequestMapping("api/v1/setting/")
public class SettingController extends BaseController {
	
	@Autowired
	private SettingService sService;
	
	@RequestMapping(value="getProfile",method=RequestMethod.GET)
	public WSMessage getProfile(@ModelAttribute("init_load")Map init){
		return sService.getProfile(init.get(Constants.USERNAME).toString());
	}
	
	@RequestMapping(value="updateProfile", method=RequestMethod.POST)
	public WSMessage getMasterData(@ModelAttribute("init_load")Map init,@RequestBody Object request){
		Map<String,Object> req = (Map<String, Object>) request;
		req.put("username", init.get(Constants.USERNAME).toString());
		
		return sService.updateProfile(req);
	}
	
	@RequestMapping(value="changePassword", method=RequestMethod.POST)
	public WSMessage changePassword(@ModelAttribute("init_load")Map init,@RequestBody Object request){
		Map<String,Object> req = (Map<String, Object>) request;
		req.put("username", init.get(Constants.USERNAME).toString());
		
		return sService.changePassword(req);
	}
	
}
