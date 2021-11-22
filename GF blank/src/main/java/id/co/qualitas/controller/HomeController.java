package id.co.qualitas.controller;

import id.co.qualitas.component.ConsMessage;
import id.co.qualitas.controller.api.QltApiController;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.service.interfaces.HomeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/v1/home/")
public class HomeController extends QltApiController{
	@Autowired
	private HomeService homeService;
	
	@RequestMapping(value="news",method=RequestMethod.GET)
	public WSMessage news(){
		WSMessage result = new WSMessage();
		try {
			result.setIdMessage(1);
			result.setResult(homeService.news("8"));
		} catch (Exception e){
			e.printStackTrace();
			result.setIdMessage(0);
			result.setMessage(ConsMessage.MESSAGE_FAILED);
		}
		return result;
	}
}
