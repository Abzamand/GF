package id.co.qualitas.service.impl;

import id.co.qualitas.dao.interfaces.DeviceDao;
import id.co.qualitas.dao.interfaces.EmployeeDao;
import id.co.qualitas.dao.interfaces.OldEmployeeDao;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.service.interfaces.EmployeeService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private DeviceDao deviceDao;

	@Override
	public WSMessage getDetail(String username, String deviceId) {
		String EMPLOYEE_TYPE = "E";
		String VENDOR_TYPE = "V";

		WSMessage message = new WSMessage();

		if (username == null || deviceId == null) {
			message.setIdMessage(0);
			message.setMessage("Username or SSAID is null");
			return message;
		}

		message = deviceDao.checkIsValidForLogin(username, deviceId);
		
		if(deviceId.equals("A")) {
			message.setIdMessage(1);
		}

		if (message.getIdMessage() == 1) {
			String type = employeeDao.getType(username);

			if (type != null) {
				if (type.equalsIgnoreCase(EMPLOYEE_TYPE)) {
					message.setMessage("Success get employee detail");
					message.setResult(employeeDao.getDetailAsEmployee(username));
				} else if (type.equalsIgnoreCase(VENDOR_TYPE)) {
					message.setMessage("Success get vendor detail");
					message.setResult(employeeDao.getDetailAsVendor(username));
				}else {
					message.setIdMessage(0);
					message.setMessage("Database Error: parameter not match, type is not E nor V");
				}
			} else {
				message.setIdMessage(0);
				message.setMessage("Failed getting type of username " + username);
			}

		} else {
			message.setMessage("User tidak punya otorisasi untuk login");
		}

		return message;
	}
	

//	@Override
//	public Map<String, Object> getDetail(String username, String deviceId) {
//		String EMPLOYEE_TYPE = "E";
//		String VENDOR_TYPE = "V";
//
//			String type = employeeDao.getType(username);
//
//			if (type != null) {
//				if (type.equalsIgnoreCase(EMPLOYEE_TYPE)) {
//					return employeeDao.getDetailAsEmployee(username);
//				} else if (type.equalsIgnoreCase(VENDOR_TYPE)) {
//					return employeeDao.getDetailAsVendor(username);
//				}
//			}
//
//		return null;
//	}
}
