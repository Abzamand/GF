package id.co.qualitas.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.co.qualitas.component.Constants;
import id.co.qualitas.domain.request.TransferPostingRequest;
import id.co.qualitas.domain.response.SAP_TransferPostingResponse;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.service.interfaces.MaterialService;
import id.co.qualitas.service.interfaces.TransferPostingService;

@RestController
@RequestMapping("api/v1/material/")
public class MaterialController extends BaseController {
	
	@Autowired
	private MaterialService materialService;
	
	@RequestMapping(value="getMasterData", method=RequestMethod.GET)
	public List<Map<String, Object>> getMasterData(
			@ModelAttribute("init_load")Map init, 
			@RequestParam String idSloc,
			@RequestParam String idPlant){
		
		return materialService.getMasterData(idSloc, idPlant);
	}
	
}
