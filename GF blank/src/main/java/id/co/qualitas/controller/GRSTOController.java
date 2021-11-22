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
import id.co.qualitas.domain.request.GoodReceiptSTORequest;
import id.co.qualitas.domain.response.SAP_GoodReceiptFGResponse;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.service.interfaces.GoodReceiptSTOService;

@RestController
@RequestMapping("api/v1/greceipt_sto/")
public class GRSTOController extends BaseController {
	
	@Autowired
	private GoodReceiptSTOService grSTOService;

	@RequestMapping(value="create", method=RequestMethod.POST)
	public WSMessage create(
			@ModelAttribute("init_load")Map init,
			@RequestBody GoodReceiptSTORequest request) {
		
//		request = GoodReceiptSTODummyGenerator.generateDummy();
		request.setCreatedBy((String) init.get(Constants.USERNAME));
		
		return grSTOService.create(request);
	}
	
}
