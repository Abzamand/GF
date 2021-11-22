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
import id.co.qualitas.domain.response.SAP_BatchNoStockResponse;
import id.co.qualitas.domain.response.SAP_StockOEMResponse;
import id.co.qualitas.service.interfaces.SAP_StockOEMService;

@RestController
@RequestMapping("api/v1/stock_oem/")
public class SAP_StockOEMController extends BaseController {
	
	@Autowired
	private SAP_StockOEMService service;
	
	@RequestMapping(value="getList", method=RequestMethod.GET)//menu stock report
	public List<SAP_StockOEMResponse> getDetail(
			@ModelAttribute("init_load")Map init, 
			@RequestParam(required = false) String materialNo ,
			@RequestParam(required = false) String materialType,
			@RequestParam String idSloc,
			@RequestParam String idPlant){

		String username = init.get(Constants.USERNAME).toString();
		
		return service.getList(materialNo, materialType, idSloc, idPlant, username);
	}

	@RequestMapping(value="getListBatchNoStock", method=RequestMethod.GET)//data dropdown batch menu process - grfg
	public List<SAP_BatchNoStockResponse> getListBatchNoStock(
			@ModelAttribute("init_load")Map init,
			@RequestParam String idSloc,
			@RequestParam String idPlant,
			@RequestParam String componentNo,
			@RequestParam String menu) {
		
		String username = init.get(Constants.USERNAME).toString();
		
		return service.getListBatchNoStock(idSloc, idPlant, componentNo, menu, username);
	}

	@RequestMapping(value="getListVendorStock", method=RequestMethod.GET)//menu incoming-vendor confirm
	public List<SAP_StockOEMResponse> getListVendorStock(
			@ModelAttribute("init_load")Map init,
			@RequestParam String idSloc,
			@RequestParam String idPlant) {
		

		String username = init.get(Constants.USERNAME).toString();
		
		return service.getListVendorStock(idSloc, idPlant, username);
	}
}
