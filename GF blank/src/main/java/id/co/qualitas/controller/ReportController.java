package id.co.qualitas.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import id.co.qualitas.component.Constants;
import id.co.qualitas.domain.FilterReport;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.service.interfaces.ReportService;

@RestController
@RequestMapping("/api/v1/report/")
public class ReportController extends BaseController{
	@Autowired
	private ReportService rService;
	
	@RequestMapping(value="getFilter",method=RequestMethod.GET)
	public WSMessage getFilter(@ModelAttribute("init_load")Map init){
		return rService.getFilter();
	}
	
	@RequestMapping(value="cancelReport",method=RequestMethod.POST)
	public WSMessage cancelReport(@ModelAttribute("init_load")Map init,@RequestBody Object request){
		Map<String,Object> req = (Map<String, Object>) request;
		req.put("createdBy", init.get(Constants.USERNAME).toString());
		return rService.cancelReport(request);
	}
	
	@RequestMapping(value="transferPosting",method=RequestMethod.POST)
	public WSMessage transferPosting(@ModelAttribute("init_load")Map init,@RequestBody FilterReport filter){
		return rService.TransferPosting(filter);
	}
	
	@RequestMapping(value="transferPostingDetail",method=RequestMethod.GET)
	public WSMessage transferPostingDetail(@ModelAttribute("init_load")Map init,Integer id){
		return rService.TransferPostingDetail(id);
	}
	
	@RequestMapping(value="transferPostingExport",method=RequestMethod.POST)
	public WSMessage transferPostingExport(@ModelAttribute("init_load")Map init,@RequestBody FilterReport filter){
		return rService.TransferPostingExport(filter);
	}
	
	@RequestMapping(value="transferPostingConfirmation",method=RequestMethod.POST)
	public WSMessage transferPostingConfirmation(@ModelAttribute("init_load")Map init,@RequestBody FilterReport filter){
		return rService.TransferPostingConfrimation(filter);
	}
	
	@RequestMapping(value="transferPostingConfirmationExport",method=RequestMethod.POST)
	public WSMessage transferPostingConfirmationExport(@ModelAttribute("init_load")Map init,@RequestBody FilterReport filter){
		return rService.TransferPostingConfrimationExport(filter);
	}
	
	@RequestMapping(value="transferPostingConfirmationDetail",method=RequestMethod.GET)
	public WSMessage transferPostingConfirmationDetail(@ModelAttribute("init_load")Map init,Integer id){
		return rService.TransferPostingConfrimationDetail(id);
	}
	
	@RequestMapping(value="pgiShipment",method=RequestMethod.POST)
	public WSMessage pgiShipment(@ModelAttribute("init_load")Map init,@RequestBody FilterReport filter){
		return rService.pgiShipment(filter);
	}
	
	@RequestMapping(value="pgiShipmentDetail",method=RequestMethod.GET)
	public WSMessage pgiShipmentDetail(@ModelAttribute("init_load")Map init,Integer id){
		return rService.pgiShipemtnDetail(id);
	}
	
	@RequestMapping(value="Shipment",method=RequestMethod.POST)
	public WSMessage Shipment(@ModelAttribute("init_load")Map init,@RequestBody FilterReport filter){
		return rService.Shipment(filter);
	}
	
	@RequestMapping(value="ShipmentDetail",method=RequestMethod.GET)
	public WSMessage ShipmentDetail(@ModelAttribute("init_load")Map init,String id){
		return rService.ShipemtnDetail(id);
	}
	
	@RequestMapping(value="shipmentExport",method=RequestMethod.POST)
	public WSMessage shipmentExport(@ModelAttribute("init_load")Map init,@RequestBody FilterReport filter){
		return rService.shipmentExport(filter);
	}
	
	@RequestMapping(value="deliveryOrder",method=RequestMethod.POST)
	public WSMessage deliveryOrder(@ModelAttribute("init_load")Map init,@RequestBody FilterReport filter){
		return rService.deliveryOrder(filter);
	}
	
	@RequestMapping(value="deliveryOrderDetail",method=RequestMethod.GET)
	public WSMessage deliveryOrderDetail(@ModelAttribute("init_load")Map init,String id){
		return rService.deliveryOrderDetail(id);
	}
	
	@RequestMapping(value="deliveryOrderExport",method=RequestMethod.POST)
	public WSMessage deliveryOrderExport(@ModelAttribute("init_load")Map init,@RequestBody FilterReport filter){
		return rService.deliveryOrderExport(filter);
	}
	
	
	@RequestMapping(value="grposto",method=RequestMethod.POST)
	public WSMessage grposto(@ModelAttribute("init_load")Map init,@RequestBody FilterReport filter){
		return rService.GRPOSTO(filter);
	}
	
	@RequestMapping(value="grpostoDetail",method=RequestMethod.GET)
	public WSMessage grpostoDetail(@ModelAttribute("init_load")Map init,String id){
		return rService.GRPOSTODetail(id);
	}
	
	@RequestMapping(value="grpostoExport",method=RequestMethod.POST)
	public WSMessage grpostoExport(@ModelAttribute("init_load")Map init,@RequestBody FilterReport filter){
		return rService.GRPOSTOExport(filter);
	}
	
	@RequestMapping(value="grfg",method=RequestMethod.POST)
	public WSMessage grfg(@ModelAttribute("init_load")Map init,@RequestBody FilterReport filter){
		return rService.GRFG(filter);
	}
	
	@RequestMapping(value="grfgDetail",method=RequestMethod.GET)
	public WSMessage grfgDetail(@ModelAttribute("init_load")Map init,String id){
		return rService.GRFGDetail(id);
	}
	
	@RequestMapping(value="grfgExport",method=RequestMethod.POST)
	public WSMessage grfgExport(@ModelAttribute("init_load")Map init,@RequestBody FilterReport filter){
		return rService.GRFGExport(filter);
	}
	
}
