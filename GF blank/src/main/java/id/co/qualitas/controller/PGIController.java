package id.co.qualitas.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import id.co.qualitas.component.Constants;
import id.co.qualitas.domain.request.DORequest;
import id.co.qualitas.domain.request.PGIRequest;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.service.interfaces.PGIService;

@RestController
@RequestMapping("api/v1/do_pgi_shipment/")
public class PGIController extends BaseController {

	@Autowired
	private PGIService pgiService;

	@RequestMapping(value="assign", method=RequestMethod.POST)
	public WSMessage create(
			@ModelAttribute("init_load")Map init,
			@RequestBody PGIRequest request) {
		
		request.setCreatedBy((String) init.get(Constants.USERNAME));
		
		return pgiService.create(request);
	}
	

	@RequestMapping(value="assign2", method=RequestMethod.POST)
	public WSMessage assign2() {
		
		
		return pgiService.assignShipment(new DORequest());
	}
}
