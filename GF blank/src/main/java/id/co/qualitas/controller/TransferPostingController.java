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
import id.co.qualitas.service.interfaces.TransferPostingService;

@RestController
@RequestMapping("api/v1/tposting/")
public class TransferPostingController extends BaseController {
	
	@Autowired
	private TransferPostingService tpostingService;
	
	@RequestMapping(value="getList", method=RequestMethod.GET)
	public List<SAP_TransferPostingResponse> getDetail(
			@ModelAttribute("init_load")Map init, 
			@RequestParam String idSloc,
			@RequestParam String idPlant){
		
		String username = init.get(Constants.USERNAME).toString();
		
		return tpostingService.getList(idSloc, idPlant, username);
	}
	
	@RequestMapping(value="create", method=RequestMethod.POST)
	public WSMessage create(
			@ModelAttribute("init_load")Map init,
			@RequestBody TransferPostingRequest request) {
		
//		request = TransferPostingDummyGenerator.generateDummy();
		request.setCreatedBy((String) init.get(Constants.USERNAME));
		
		return tpostingService.create(request);
	}
}
