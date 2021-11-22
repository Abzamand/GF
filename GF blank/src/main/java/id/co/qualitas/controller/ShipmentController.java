package id.co.qualitas.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import id.co.qualitas.component.Constants;
import id.co.qualitas.domain.request.PGIRequest;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.service.interfaces.PGIService;

@RestController
@RequestMapping("api/v1/shipment/")
public class ShipmentController extends BaseController {

	@Autowired
	private PGIService pgiService;

	@RequestMapping(value="assign", method=RequestMethod.POST)
	public WSMessage create(
			@ModelAttribute("init_load")Map init,
			@RequestBody PGIRequest request) {
		
//		request = PGIDummyGenerator.generateDummy();
		request.setCreatedBy((String) init.get(Constants.USERNAME));
		
		return pgiService.create(request);
	}

	@RequestMapping(value="syncToSAP", method=RequestMethod.POST)
	public WSMessage syncToSAP(
			@ModelAttribute("init_load")Map init) {
				
		
		return pgiService.syncToSAP();
	}
}
