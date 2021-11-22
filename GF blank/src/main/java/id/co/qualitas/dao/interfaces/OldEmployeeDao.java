package id.co.qualitas.dao.interfaces;

import java.util.List;
import java.util.Map;


public interface OldEmployeeDao {

	Map<String, Object> getDetail(String username);
	
	List<Map<String, Object>> getListEmployee(Object request);
	Object getDetailEmployee(Object request);
	Object saveEmployee(Object request);
	Object editEmployee(Object request);
	Object deleteEmployee(Object request);
	
	Object changePassword(Object request);

	Object getSetting(Object username);

	List<Map<String, Object>> getListUser(Object request);
	Object getUserFile(Object request);
}
