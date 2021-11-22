package id.co.qualitas.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;

import id.co.qualitas.component.Constants;
import id.co.qualitas.component.Utils;

public class BaseController {
	
	@ModelAttribute("init_load")
	public Map initial(OAuth2Authentication auth, HttpServletRequest httpServletRequest){
		if(auth != null){
//			Map identity = Utils.splitUsernameAndClient(auth.getName());
			Map identity = new HashMap<>();
			identity.put(Constants.USERNAME, auth.getName());
			identity.put(Constants.USER_AGENT, httpServletRequest.getHeader(Constants.USER_AGENT));
			
			return identity;
		}else{
			return new HashMap();
		}
		
	} 

}
