package id.co.qualitas.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.co.qualitas.domain.response.SAP_POSTODetailResponse;
import id.co.qualitas.domain.response.SAP_POSTOResponse;
import id.co.qualitas.domain.response.SAP_ShipmDOResponse;
import id.co.qualitas.service.interfaces.SAP_POSTOService;
import id.co.qualitas.service.interfaces.SAP_ShipmDOService;

@RestController
@RequestMapping("api/v1/po_sto/")
public class SAP_POSTOController extends BaseController {

	@Autowired
	private SAP_POSTOService service;

	@RequestMapping(value="getList", method=RequestMethod.GET)
	public List<SAP_POSTODetailResponse> getList(
			@ModelAttribute("init_load")Map init,
			@RequestParam String idSloc,
			@RequestParam String idPlant) {
		
		
		return service.getList(idSloc, idPlant);
	}
}
