package id.co.qualitas.dao.interfaces;

import java.util.List;
import java.util.Map;

import id.co.qualitas.domain.response.WSMessage;


public interface DeviceDao {
	WSMessage checkIsValidForLogin(String username, String deviceId);
	
}
