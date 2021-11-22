package id.co.qualitas.service.impl;

import id.co.qualitas.component.Utils;
import id.co.qualitas.dao.interfaces.UsersDao;
import id.co.qualitas.domain.request.Users;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.service.interfaces.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {
	@Autowired
	private UsersDao userDao;

	@Override
	public Users findUser(int username) {
		
		return userDao.findUser(username);
	}

	@Override
	public WSMessage changePassword(Users user) {
		WSMessage wsMessage = new WSMessage();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Users checkUser = findUser(user.getUsername());

		if (!encoder.matches(user.getOldPassword(), checkUser.getPassword())) {
			wsMessage.setIdMessage(0);
			wsMessage.setMessage("Old password doesn't match");
		} else if (encoder.matches(user.getOldPassword(), checkUser.getPassword())) {
			user.setPassword(Utils.hashPassword(user.getPassword()));
			wsMessage = userDao.changePassword(user);
		}
		
		return wsMessage;
	}

	
}
