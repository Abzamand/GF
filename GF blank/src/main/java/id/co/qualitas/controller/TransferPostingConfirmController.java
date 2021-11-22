package id.co.qualitas.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.co.qualitas.component.Constants;
import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.service.interfaces.TransferPostingConfirmService;

@RestController
@RequestMapping("api/v1/tposting_confirm/")
public class TransferPostingConfirmController extends BaseController {
	
	@Autowired
	private TransferPostingConfirmService tpostingService;
	
	@RequestMapping(value="getList", method=RequestMethod.GET)
	public Map<String, Object> getDetail(
			@ModelAttribute("init_load")Map init, 
			@RequestParam String idSloc,
			@RequestParam String idPlant){
		
		return tpostingService.getList(idSloc, idPlant);
	}
	
	@RequestMapping(value="create", method=RequestMethod.POST)
	public WSMessage create(
			@ModelAttribute("init_load")Map init,
			@RequestBody TransferPostingConfirmRequest request) {
		
		request.setCreatedBy((String) init.get(Constants.USERNAME));
		
		return tpostingService.confirm(request);
	}

	@RequestMapping(value="syncToSAP", method=RequestMethod.POST)
	public WSMessage syncToSAP(
			@ModelAttribute("init_load")Map init) {
				
		
		return tpostingService.syncToSAP();
	}
}
