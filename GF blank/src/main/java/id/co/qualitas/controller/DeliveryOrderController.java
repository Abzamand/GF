package id.co.qualitas.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.co.qualitas.component.Constants;
import id.co.qualitas.domain.request.DOHeader;
import id.co.qualitas.domain.request.DORequest;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.service.interfaces.DeliveryOrderService;

@RestController
@RequestMapping("api/v1/delivery_order/")
public class DeliveryOrderController extends BaseController {

	@Autowired
	private DeliveryOrderService doService;

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public WSMessage create(@ModelAttribute("init_load") Map init, @RequestBody DORequest request) {

//		request = DeliveryOrderDummyGenerator.generateDummy();
		request.setCreatedBy((String) init.get(Constants.USERNAME));

		return doService.create(request);
	}

	@RequestMapping(value = "getHistories", method = RequestMethod.GET)
	public WSMessage getListForHistory(@ModelAttribute("init_load") Map init, @RequestParam String dateFrom,
			@RequestParam String dateTo, @RequestParam String docType, @RequestParam String idSloc,
			@RequestParam String idPlant) {

		return doService.getListForHistory(dateFrom, dateTo, docType, (String) init.get(Constants.USERNAME), idSloc,
				idPlant);
	}

	@RequestMapping(value = "updateStatusPOD", method = RequestMethod.POST)
	public WSMessage updateStatusPOD(@ModelAttribute("init_load") Map init, @RequestParam String idSloc,
			@RequestParam String idPlant) {

		return doService.updateStatusPOD(idSloc, idPlant);
	}

	@RequestMapping(value = "updateStatusPODMobile", method = RequestMethod.GET)
	public WSMessage updateStatusPODMobile(@ModelAttribute("init_load") Map init, @RequestParam String idSloc,
			@RequestParam String idPlant) {

		return doService.updateStatusPOD(idSloc, idPlant);
	}

	@RequestMapping(value = "syncToSAP", method = RequestMethod.POST)
	public WSMessage syncToSAP(@ModelAttribute("init_load") Map init) {

		return doService.syncToSAP();
	}

	@RequestMapping(value="sendEmail", method=RequestMethod.POST)
	public WSMessage sendEmail(@ModelAttribute("init_load") Map init, @RequestBody DOHeader header) {
		return doService.sendEmail(header);
	}
	

	@RequestMapping(value="assign", method=RequestMethod.POST)
	public WSMessage assign() {
		
		
		return doService.assignDo();
	}

}
