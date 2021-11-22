package id.co.qualitas.dao.interfaces;

import java.util.List;

import id.co.qualitas.domain.FilterReport;


public interface ReportDao {
	
	List<Object> getFilter();
	Boolean cancelReport(Object request);
	
	List<Object> TrasnferPosting(FilterReport filter);
	Object TrasnferPostingDetail(Integer id);
	List<Object> TransferPostingMaterial(Integer id);
	
	List<Object> TrasnferPostingConfirmation(FilterReport filter);
	Object TrasnferPostingConfirmationDetail(Integer id);
	List<Object> TransferPostingConfirmationMaterial(Integer id);
	
	List<Object> pgiShipment(FilterReport filter);
	Object pgiShipmentDetail(Integer id);
	List<Object> pgiShipmentMaterial(Integer id);
	
	List<Object> Shipment(FilterReport filter);
	Object ShipmentDetail(String id);
	List<Object> ShipmentMaterial(String id);
	
	List<Object> deliveryOrder(FilterReport filter);
	Object deliveryOrderDetail(String id);
	List<Object> deliveryOrderMaterial(String id);
	List<Object> deliveryOrderBatch(String id);
	
	List<Object> GRPOSTO(FilterReport filter);
	Object GRPOSTODetail(String id);
	List<Object> GRPOSTOMaterial(String id);
	
	List<Object> GRFG(FilterReport filter);
	Object GRFGDetail(String id);
	List<Object> GRFGMaterial(String id);
	List<Object> GRFGComponent(String id);
	List<Object> TrasnferPostingForExport(FilterReport filter);
	List<Object> TrasnferPostingConfirmationForExport(FilterReport filter);
	List<Object> GRFGForExport(FilterReport filter);
	List<Object> GRPOSTOForExport(FilterReport filter);
	List<Object> deliveryOrderForExport(FilterReport filter);
	List<Object> shipmentExport(FilterReport filter);
	String transferPostingStatus(Integer id);
	
}
