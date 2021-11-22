package id.co.qualitas.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.data.rest.core.util.MapUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import id.co.qualitas.component.Cons;
import id.co.qualitas.component.Utils;
import id.co.qualitas.dao.interfaces.DeliveryOrderDao;
import id.co.qualitas.dao.interfaces.PGIDao;
import id.co.qualitas.dao.interfaces.ShipmentDao;
import id.co.qualitas.dao.interfaces.TransferPostingConfirmDao;
import id.co.qualitas.domain.request.DODetail;
import id.co.qualitas.domain.request.DOHeader;
import id.co.qualitas.domain.request.DORequest;
import id.co.qualitas.domain.request.PGIHeader;
import id.co.qualitas.domain.request.PGIRequest;
import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.domain.webservice.createdo.BAPIDELICIOUSREQUEST;
import id.co.qualitas.domain.webservice.createdo.BAPIDLVREFTOSALESORDER;
import id.co.qualitas.domain.webservice.createdo.TABLEOFBAPIDELICIOUSCREATEDITEMS;
import id.co.qualitas.domain.webservice.createdo.TABLEOFBAPIDELICIOUSREQUEST;
import id.co.qualitas.domain.webservice.createdo.TABLEOFBAPIDLVREFTOSALESORDER;
import id.co.qualitas.domain.webservice.createdo.TABLEOFBAPISHPDELIVNUMB;
import id.co.qualitas.domain.webservice.createdo.TABLEOFZTYWBCREATEDODETAIL;
import id.co.qualitas.domain.webservice.createdo.ZFMWBCREATEDO;
import id.co.qualitas.domain.webservice.createdo.ZFMWBCREATEDOResponse;
import id.co.qualitas.domain.webservice.createdo.ZTYWBCREATEDODETAIL;
import id.co.qualitas.domain.webservice.createdo.ZTYWBCREATEDOHEADER;
import id.co.qualitas.domain.webservice.shipmentchange.BAPISHIPMENTHEADER;
import id.co.qualitas.domain.webservice.shipmentchange.BAPISHIPMENTHEADERACTION;
import id.co.qualitas.domain.webservice.shipmentchange.BAPISHIPMENTITEM;
import id.co.qualitas.domain.webservice.shipmentchange.BAPISHIPMENTITEMACTION;
import id.co.qualitas.domain.webservice.shipmentchange.TABLEOFBAPIRET2;
import id.co.qualitas.domain.webservice.shipmentchange.TABLEOFBAPISHIPMENTITEM;
import id.co.qualitas.domain.webservice.shipmentchange.TABLEOFBAPISHIPMENTITEMACTION;
import id.co.qualitas.domain.webservice.shipmentchange.TABLEOFZTYWBSHIPMENTCHANGE;
import id.co.qualitas.domain.webservice.shipmentchange.ZFMSHIPMENTCHANGE;
import id.co.qualitas.domain.webservice.shipmentchange.ZFMSHIPMENTCHANGEResponse;
import id.co.qualitas.service.interfaces.DeliveryOrderService;
import id.co.qualitas.service.interfaces.PGIService;
import id.co.qualitas.service.interfaces.TransferPostingConfirmService;

@Service
public class PGIServiceImpl implements PGIService {
	@Autowired
	ShipmentDao shipmentDao;

	@Autowired
	PGIDao pgiDao;
	
	@Autowired
	DeliveryOrderDao doDao;

	@Override
	public WSMessage create(PGIRequest request) {
		WSMessage message = new WSMessage();

//		message = pgiDao.create(request);
		
		if(request == null || request.getHeader() == null || request.getHeader().getShipmentNo() == null) {
			message.setMessage("Error. Shipment no. is required");
			
			return message;
		}
		
//		Map<String, String> header = doDao.getDriverAndVehicleNoByShipmentNo(request.getHeader().getShipmentNo());
//		
//		if(header != null ) {
//			request.getHeader().setDriver(Utils.validateToString(header.get("driver")));
//			request.getHeader().setVehicleNo(Utils.validateToString(header.get("vehicleNo")));
//		}
		
//		request.getHeader().setShipmentNo("1500000625");
//		request.getHeader().setDriver("Supir OEM 17");
//		request.getHeader().setPlateNo("B 9111TEST");
		
		message = requestCallZWBSHIPMENTCHANGE(mappingZfmShipmentChange(request.getHeader()), request.getHeader().getShipmentNo());

		return message;
	}

	@Override
	public WSMessage syncToSAP() {
		WSMessage result = new WSMessage();
		/*
		 * List<Map<String, String>> listHeader = new ArrayList<Map<String, String>>();
		 * // listHeader = shipmentDao.getHeadersForSync(); listHeader =
		 * doDao.getHeadersForSync();
		 * 
		 * if (listHeader != null && !listHeader.isEmpty()) { String message = ""; for
		 * (int i = 0; i < listHeader.size(); i++) {// start looping
		 * 
		 * Map<String, String> header = listHeader.get(i);
		 * 
		 * String shipmentNo = null;
		 * 
		 * if (header.get("shipment_no") != null) { shipmentNo =
		 * Utils.validateToString(header.get("shipment_no"));
		 * 
		 * String messageIndexPrefix = "\r\n" + (i + 1) + ". " + shipmentNo + " ";
		 * 
		 * WSMessage messageAfterSync =
		 * requestCallZWBSHIPMENTCHANGE(mappingZfmShipmentChange(header), shipmentNo);
		 * 
		 * message = message.concat(messageIndexPrefix + messageAfterSync.getMessage());
		 * } } // end looping
		 * 
		 * result.setIdMessage(1); result.setMessage(message); } else {// error no list
		 * header result.setIdMessage(0);
		 * result.setMessage("There is no list shipment_header to sync " +
		 * "(Query is looking for status that still not completed)"); }
		 */
		return result;
	}

	private ZFMSHIPMENTCHANGE mappingZfmShipmentChange(PGIHeader header) {

		BAPISHIPMENTHEADER requestHeader = new BAPISHIPMENTHEADER();
		requestHeader.setSHIPMENTNUM(Utils.validateToString(header.getShipmentNo()));
		requestHeader.setSTATUSPLAN("X");
		requestHeader.setSTATUSCHECKIN("X");
		requestHeader.setSTATUSLOADSTART("X");
		requestHeader.setSTATUSLOADEND("X");
		requestHeader.setSTATUSCOMPL("X");
		requestHeader.setSTATUSSHPMNTSTART("X");
		requestHeader.setSTATUSSHPMNTEND("X");
		requestHeader.setEXTERNALID1(header.getDriver());
		requestHeader.setTENDERINGCARRIERTRACKID(header.getPlateNo());

		BAPISHIPMENTHEADERACTION requestHeaderAction = new BAPISHIPMENTHEADERACTION();
		requestHeaderAction.setSTATUSPLAN("C");
		requestHeaderAction.setSTATUSCHECKIN("C");
		requestHeaderAction.setSTATUSLOADSTART("C");
		requestHeaderAction.setSTATUSLOADEND("C");
		requestHeaderAction.setSTATUSCOMPL("C");
		requestHeaderAction.setSTATUSSHPMNTSTART("C");
		requestHeaderAction.setSTATUSSHPMNTEND("C");
		requestHeaderAction.setEXTERNALID1("C");
		requestHeaderAction.setTENDERINGCARRIERTRACKID("C");

		TABLEOFBAPISHIPMENTITEM listRequestItem = new TABLEOFBAPISHIPMENTITEM();
		TABLEOFBAPISHIPMENTITEMACTION listRequestItemAction = new TABLEOFBAPISHIPMENTITEMACTION();


		ZFMSHIPMENTCHANGE request = new ZFMSHIPMENTCHANGE();
		request.setHEADERDATA(requestHeader);
		request.setHEADERDATAACTION(requestHeaderAction);
		request.setITEMDATA(listRequestItem);
		request.setITEMDATAACTION(listRequestItemAction);
		request.setRETURN(new TABLEOFBAPIRET2());
		request.setMATDOC(new TABLEOFZTYWBSHIPMENTCHANGE());

		new Gson().toJson(request);	
		return request;
	}

	private WSMessage requestCallZWBSHIPMENTCHANGE(ZFMSHIPMENTCHANGE request, String shipmentNo) {
		WSMessage result = new WSMessage();
		try {
			ZFMSHIPMENTCHANGEResponse sapResponse = new ZFMSHIPMENTCHANGEResponse();
			sapResponse = (ZFMSHIPMENTCHANGEResponse) Utils.WebserviceResponse(request, Cons.CONTEXT_SHIPMENT_CHANGE,
					Cons.WEBSERVICE_SAP + Cons.ENDPOINT_SHIPMENT_CHANGE, Cons.USERNAME, Cons.PASSWORD);
			if (sapResponse != null) {
				if (sapResponse.getRETURN() != null) {// failed
					String infoSync = null;
					String status = Cons.STATUS_PGI_FAILED;
					String pgiNo = null;
					if (sapResponse.getRETURN().getItem() != null && !sapResponse.getRETURN().getItem().isEmpty()) {
						for (int i = 0; i < sapResponse.getRETURN().getItem().size(); i++) {

							String doNoSap = null;
							if(sapResponse.getRETURN().getItem().get(i).getNUMBER().equals("494")) {
								infoSync = null;
								String successMessage = sapResponse.getRETURN().getItem().get(i).getMESSAGE();
								status = Cons.STATUS_PGI_COMPLETED;

								if (infoSync != null) {
									infoSync = infoSync.concat("; ").concat(successMessage);
								} else {
									infoSync = successMessage;
								}
								
								if(sapResponse.getMATDOC() != null && sapResponse.getMATDOC().getItem() != null && !sapResponse.getMATDOC().getItem().isEmpty()) {
									for(int j = 0; j < sapResponse.getMATDOC().getItem().size(); j++) {

										doNoSap = sapResponse.getMATDOC().getItem().get(j).getVBELN();
										pgiNo = sapResponse.getMATDOC().getItem().get(j).getMBLNR();
										
										result = doDao.updateFieldsAfterSyncedByDoNoSap(doNoSap, infoSync, status, pgiNo);
									}
								}
								
								break;
								
							} else if (sapResponse.getRETURN().getItem().get(i).getTYPE().equals("E")) {
								String errorMessage = sapResponse.getRETURN().getItem().get(i).getMESSAGE();
								status = Cons.STATUS_PGI_FAILED;

								if (infoSync != null) {
									infoSync = infoSync.concat("; ").concat(errorMessage);
								} else {
									infoSync = errorMessage;
								}
								

								result = doDao.updateFieldsAfterSyncedByShipmentNo(shipmentNo, infoSync, status, pgiNo);
							}

						}

//						result = shipmentDao.updateFieldsAfterSynced(shipmentNo, infoSync, status);
//						result = doDao.updateFieldsAfterSyncedByDoNoSap(shipmentNo, infoSync, status, pgiNo);

						if (result.getIdMessage() == 1) {// update table success, return message info sync
							result.setIdMessage(1);
							result.setMessage(status + " " + infoSync);
						} else {// else error update table dan mnculin exceptionnya
							result.setIdMessage(0);
							result.setMessage(status + " " + infoSync + " and failed to update to database");
						}
						return result;
					}
				} // end if

			}
		} catch (Exception e) {
			result.setIdMessage(0);
			result.setMessage(e.getMessage());
		}

		return result;

	}
	
	@Override
	public WSMessage assignShipment(DORequest request) {
		WSMessage result = new WSMessage();
		
		result = requestAssign(mappingZfmShipmentAssign(request), request.getDoHeader().getShipmentNo(), request.getDoHeader().getDoNoSap());
		
		return result;
	}
	
	private ZFMSHIPMENTCHANGE mappingZfmShipmentAssign(DORequest doRequest) {

		BAPISHIPMENTHEADER requestHeader = new BAPISHIPMENTHEADER();
		requestHeader.setSHIPMENTNUM(doRequest.getDoHeader().getShipmentNo());
		
		List<Map<String, String>> listDetail = new ArrayList<>();
		TABLEOFBAPISHIPMENTITEM listRequestItem = new TABLEOFBAPISHIPMENTITEM();
		TABLEOFBAPISHIPMENTITEMACTION listRequestItemAction = new TABLEOFBAPISHIPMENTITEMACTION();

		BAPISHIPMENTITEM requestItem = new BAPISHIPMENTITEM();
		requestItem.setDELIVERY(doRequest.getDoHeader().getDoNoSap());

		listRequestItem.getItem().add(requestItem);

		BAPISHIPMENTITEMACTION requestItemAction = new BAPISHIPMENTITEMACTION();
		requestItemAction.setDELIVERY("A");
		requestItemAction.setITENERARY("A");
		listRequestItemAction.getItem().add(requestItemAction);
		

		BAPISHIPMENTHEADERACTION requestHeaderAction = new BAPISHIPMENTHEADERACTION();
		requestHeaderAction.setSHIPMENTNUM("X");

		ZFMSHIPMENTCHANGE request = new ZFMSHIPMENTCHANGE();
		request.setHEADERDATA(requestHeader);
		request.setHEADERDATAACTION(requestHeaderAction);
		request.setITEMDATA(listRequestItem);
		request.setITEMDATAACTION(listRequestItemAction);
		request.setRETURN(new TABLEOFBAPIRET2());

		new Gson().toJson(request);	
		return request;
	}
	
	private WSMessage requestAssign(ZFMSHIPMENTCHANGE request, String shipmentNo, String doNoSap) {
		WSMessage result = new WSMessage();
		try {
			ZFMSHIPMENTCHANGEResponse sapResponse = new ZFMSHIPMENTCHANGEResponse();
			sapResponse = (ZFMSHIPMENTCHANGEResponse) Utils.WebserviceResponse(request, Cons.CONTEXT_SHIPMENT_CHANGE,
					Cons.WEBSERVICE_SAP + Cons.ENDPOINT_SHIPMENT_CHANGE, Cons.USERNAME, Cons.PASSWORD);
			if (sapResponse != null) {
				if (sapResponse.getRETURN() != null) {// failed
					String infoSync = null;
					String status = "ASSIGN DO FAILED";
					
					if (sapResponse.getRETURN().getItem() != null && !sapResponse.getRETURN().getItem().isEmpty()) {
						for (int i = 0; i < sapResponse.getRETURN().getItem().size(); i++) {

//							String doNoSap = null;
							String pgiNo = null;
							
							if(sapResponse.getRETURN().getItem().get(i).getNUMBER().equals("482")) {
								infoSync = null;
								String successMessage = sapResponse.getRETURN().getItem().get(i).getMESSAGE();
								status = "ASSIGN DO COMPLETED";

								if (infoSync != null) {
									infoSync = infoSync.concat("; ").concat(successMessage);
								} else {
									infoSync = successMessage;
								}
								
//								if(sapResponse.getMATDOC() != null && sapResponse.getMATDOC().getItem() != null && !sapResponse.getMATDOC().getItem().isEmpty()) {
//									for(int j = 0; j < sapResponse.getMATDOC().getItem().size(); j++) {
//
//										doNoSap = sapResponse.getMATDOC().getItem().get(j).getVBELN();
//										pgiNo = sapResponse.getMATDOC().getItem().get(j).getMBLNR();
//										
//										result = doDao.updateFieldsAfterSyncedByDoNoSap(doNoSap, infoSync, status, pgiNo);
//									}
//								}
								
								break;
								
							} else if (sapResponse.getRETURN().getItem().get(i).getTYPE().equals("E")) {
								String errorMessage = sapResponse.getRETURN().getItem().get(i).getMESSAGE();
								status = "ASSIGN DO FAILED";

								if (infoSync != null) {
									infoSync = infoSync.concat("; ").concat(errorMessage);
								} else {
									infoSync = errorMessage;
								}
								

//								result = doDao.updateFieldsAfterSyncedByShipmentNo(shipmentNo, infoSync, status, pgiNo);
							}
						}

//						result = shipmentDao.updateFieldsAfterSynced(shipmentNo, infoSync, status);
//						result = doDao.updateFieldsAfterSyncedByShipmentNo(shipmentNo, infoSync, status, null);
						result = doDao.updateFieldsAfterSyncedByDoNoSap(doNoSap, infoSync, status, null);

						if (result.getIdMessage() == 1) {// update table success, return message info sync
							result.setIdMessage(1);
							result.setMessage(status + " " + infoSync);
						} else {// else error update table dan mnculin exceptionnya
							result.setIdMessage(0);
							result.setMessage(status + " " + infoSync + " but failed to update to database");
						}
						return result;
					}
				} // end if

			}
		} catch (Exception e) {
			result.setIdMessage(0);
			result.setMessage(e.getMessage());
		}

		return result;

	}
}
