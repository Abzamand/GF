package id.co.qualitas.service.impl;

import id.co.qualitas.component.Cons;
import id.co.qualitas.component.ConsParam;
import id.co.qualitas.dao.interfaces.DeviceDao;
import id.co.qualitas.dao.interfaces.EmployeeDao;
import id.co.qualitas.dao.interfaces.OldEmployeeDao;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.service.interfaces.EmployeeService;
import id.co.qualitas.service.interfaces.FileService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public WSMessage getFile(int param) {
		String path;
		byte[] data = null;
		
		if(param == 1) {
			path = ConsParam.PATH_USER_MANUAL;
		}else {
			path = ConsParam.PATH_APK;
		}
		
		File f = new File(path);
		data = new byte[(int) f.length()];
		FileInputStream fis;
		try {
			fis = new FileInputStream(f);
			fis.read(data); // read file eninto bytes[]
			fis.close();

		} catch (FileNotFoundException e) {
			data = null;
			// TODO Auto-generated catch block
			System.out.println("file not found");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			data = null;
			e.printStackTrace();
		}
		
		WSMessage message = new WSMessage();
		if(data != null) {
			message.setIdMessage(param == 1 ? 1 : 2);
		}else {
			message.setIdMessage(0);
		}
		message.setResult(data);
		
		return message;
	}


}
