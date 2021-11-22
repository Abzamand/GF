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
import id.co.qualitas.domain.request.GoodReceiptFGRequest;
import id.co.qualitas.domain.response.SAP_GoodReceiptFGResponse;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.service.interfaces.GoodReceiptFGService;

@RestController
@RequestMapping("api/v1/greceipt_fg/")
public class GRFGController extends BaseController {
	
	@Autowired
	private GoodReceiptFGService goodReceiptFGService;
	

	@RequestMapping(value="getList", method=RequestMethod.GET)
	public List<SAP_GoodReceiptFGResponse> getList(
			@ModelAttribute("init_load")Map init,
			@RequestParam String idSloc,
			@RequestParam String idPlant) {
		
		
		return goodReceiptFGService.getList(idSloc, idPlant);
	}
	
	@RequestMapping(value="create", method=RequestMethod.POST)
	public WSMessage create(
			@ModelAttribute("init_load")Map init,
			@RequestBody GoodReceiptFGRequest request) {
		
		request.setCreatedBy((String) init.get(Constants.USERNAME));
		
		return goodReceiptFGService.create(request);
	}
	
	@RequestMapping(value="syncToSAP", method=RequestMethod.POST)
	public WSMessage syncToSAP(
			@ModelAttribute("init_load")Map init) {
				
		
		return goodReceiptFGService.syncToSAP();
	}

	
}
