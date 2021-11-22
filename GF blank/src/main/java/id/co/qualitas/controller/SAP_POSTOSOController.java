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
import id.co.qualitas.domain.request.DORequest;
import id.co.qualitas.domain.response.SAP_GoodReceiptFGResponse;
import id.co.qualitas.domain.response.SAP_POSTOSOResponse;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.service.interfaces.DeliveryOrderService;
import id.co.qualitas.service.interfaces.SAP_POSTOSOService;

@RestController
@RequestMapping("api/v1/postoso/")
public class SAP_POSTOSOController extends BaseController {

	@Autowired
	private SAP_POSTOSOService service;

	@RequestMapping(value="getList", method=RequestMethod.GET)
	public List<SAP_POSTOSOResponse> getList(
			@ModelAttribute("init_load")Map init,
			@RequestParam String idSloc,
			@RequestParam String idPlant,
			@RequestParam String docType) {
		
		
		return service.getList(idSloc, idPlant, docType);
	}
}
