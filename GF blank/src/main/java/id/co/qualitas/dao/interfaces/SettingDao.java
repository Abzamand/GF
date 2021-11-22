package id.co.qualitas.dao.interfaces;

import java.util.List;
import java.util.Map;

public interface SettingDao {
	Object getProfile(String username);
	Boolean updateProfile(Object req);
	
	String getCurrentPassword(String username);
	Boolean changePassword(Object req);

}
