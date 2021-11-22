package id.co.qualitas.dao.interfaces;

import java.util.List;
import java.util.Map;

import id.co.qualitas.domain.request.Users;
import id.co.qualitas.domain.response.WSMessage;


public interface UsersDao {

//	public Users findUser(String username);
	public WSMessage changePassword(Users user);
	Users findUser(int username);
}
