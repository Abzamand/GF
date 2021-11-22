package id.co.qualitas.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.co.qualitas.domain.response.SAP_ShipmDOResponse;
import id.co.qualitas.service.interfaces.SAP_ShipmDOService;

@RestController
@RequestMapping("api/v1/shipmdo/")
public class SAP_ShipmDOController extends BaseController {

	@Autowired
	private SAP_ShipmDOService service;

	@RequestMapping(value="getList", method=RequestMethod.GET)
	public List<SAP_ShipmDOResponse> getList(
			@ModelAttribute("init_load")Map init,
			@RequestParam String idSloc,
			@RequestParam String idPlant) {
		
		
		return service.getList(idSloc, idPlant);
	}
}
