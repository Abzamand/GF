package id.co.qualitas.controller;

import id.co.qualitas.component.Constants;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.service.interfaces.EmployeeService;
import id.co.qualitas.service.interfaces.FileService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/file/")
public class FileController extends BaseController{
	@Autowired
	private FileService fileService;
	
	@RequestMapping(value="getFile",method=RequestMethod.POST)
	public WSMessage getFile(@ModelAttribute("init_load")Map init,@RequestBody Object request){
		
		Map<String,Object> req = (Map<String, Object>) request;
		
		int param = (int) req.get("param");
		
		return fileService.getFile(param);
	}
	
}

