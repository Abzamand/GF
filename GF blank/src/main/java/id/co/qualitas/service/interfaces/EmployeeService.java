package id.co.qualitas.service.interfaces;


import java.util.Map;

import id.co.qualitas.domain.response.WSMessage;


public interface EmployeeService {

	WSMessage getDetail(String username, String deviceId);
}
