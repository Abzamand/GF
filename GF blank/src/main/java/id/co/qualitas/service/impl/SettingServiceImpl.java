package id.co.qualitas.service.impl;

import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import id.co.qualitas.component.ConsMessage;
import id.co.qualitas.component.ConsParam;
import id.co.qualitas.component.Utils;
import id.co.qualitas.dao.interfaces.SettingDao;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.service.interfaces.SettingService;


@Service
public class SettingServiceImpl implements SettingService {
	@Autowired
	SettingDao sDao;

	@Override
	public WSMessage getProfile(String username) {
		WSMessage result = new WSMessage();
		
		Object data = sDao.getProfile(username);
		
		if(data != null) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}
	
	@Override
	public WSMessage updateProfile(Object request) {
		WSMessage result = new WSMessage();
		
		Boolean data = sDao.updateProfile(request);
		
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
	public WSMessage changePassword(Object request) {
		WSMessage result = new WSMessage();
		Boolean data = false;
		Map<String,Object> req = (Map<String, Object>) request;
		
		String oldPassword = sDao.getCurrentPassword(req.get("username").toString());
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		boolean checkPass = passwordEncoder.matches(req.get("currentPassword").toString(),oldPassword);
		
		if(checkPass)
			data = sDao.changePassword(req);
		
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

