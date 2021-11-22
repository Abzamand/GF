package id.co.qualitas.controller;

import id.co.qualitas.component.Constants;
import id.co.qualitas.domain.request.Users;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.service.interfaces.UsersService;
import id.co.qualitas.service.interfaces.EmployeeService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController	
@RequestMapping("/api/v1/user/")
public class UsersController extends BaseController{
	@Autowired
	private UsersService service;
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public WSMessage changePassword(@RequestBody Users user, @ModelAttribute("init_load") Map init) {
		user.setUsername(Integer.parseInt(init.get(Constants.USERNAME).toString()));

		
		return service.changePassword(user);
	}
	
}
