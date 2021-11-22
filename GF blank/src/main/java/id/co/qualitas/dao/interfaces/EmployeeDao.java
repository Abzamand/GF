package id.co.qualitas.dao.interfaces;

import java.util.List;
import java.util.Map;


public interface EmployeeDao {

	String getType(String username);
	
	Map<String, Object> getDetailAsEmployee(String username);
	Map<String, Object> getDetailAsVendor(String username);
	
}
