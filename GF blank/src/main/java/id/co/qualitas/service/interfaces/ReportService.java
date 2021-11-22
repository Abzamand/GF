package id.co.qualitas.service.interfaces;

import id.co.qualitas.domain.FilterReport;
import id.co.qualitas.domain.response.WSMessage;

public interface ReportService {
	WSMessage getFilter();
	WSMessage cancelReport(Object request);
	
	WSMessage TransferPosting(FilterReport Filter);
	WSMessage TransferPostingDetail(Integer id);
	
	WSMessage TransferPostingConfrimation(FilterReport Filter);
	WSMessage TransferPostingConfrimationDetail(Integer id);
	
	WSMessage pgiShipment(FilterReport Filter);
	WSMessage pgiShipemtnDetail(Integer id);
	
	WSMessage Shipment(FilterReport Filter);
	WSMessage ShipemtnDetail(String id);
	
	WSMessage deliveryOrder(FilterReport Filter);
	WSMessage deliveryOrderDetail(String id);
	
	WSMessage GRPOSTO(FilterReport Filter);
	WSMessage GRPOSTODetail(String id);
	
	WSMessage GRFG(FilterReport Filter);
	WSMessage GRFGDetail(String id);
	WSMessage TransferPostingExport(FilterReport filter);
	WSMessage TransferPostingConfrimationExport(FilterReport filter);
	WSMessage GRFGExport(FilterReport filter);
	WSMessage GRPOSTOExport(FilterReport filter);
	WSMessage deliveryOrderExport(FilterReport filter);
	WSMessage shipmentExport(FilterReport filter);
}
