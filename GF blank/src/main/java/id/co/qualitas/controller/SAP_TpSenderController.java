package id.co.qualitas.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.co.qualitas.component.Constants;
import id.co.qualitas.domain.response.SAP_POSTOResponse;
import id.co.qualitas.domain.response.SAP_ShipmDOResponse;
import id.co.qualitas.domain.response.SAP_TpSenderResponse;
import id.co.qualitas.service.interfaces.SAP_POSTOService;
import id.co.qualitas.service.interfaces.SAP_ShipmDOService;
import id.co.qualitas.service.interfaces.SAP_TpSenderService;

@RestController
@RequestMapping("api/v1/tp_sender/")
public class SAP_TpSenderController extends BaseController {

	@Autowired
	private SAP_TpSenderService service;

	@RequestMapping(value="getList", method=RequestMethod.GET)
	public List<SAP_TpSenderResponse> getList(
			@ModelAttribute("init_load")Map init,
			@RequestParam String idSloc,
			@RequestParam String idPlant) {
		

		String username = init.get(Constants.USERNAME).toString();
		
		return service.getList(idSloc, idPlant, username);
	}
}
