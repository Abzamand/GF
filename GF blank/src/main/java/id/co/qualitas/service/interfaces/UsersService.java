package id.co.qualitas.service.interfaces;


import java.util.Map;

import id.co.qualitas.domain.request.Users;
import id.co.qualitas.domain.response.WSMessage;


public interface UsersService {

//	public Users findUser(String username);
	public WSMessage changePassword(Users user);
	Users findUser(int username);
}
