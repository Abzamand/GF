package id.co.qualitas.domain.response;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import id.co.qualitas.domain.request.DOHeader;

public class SAP_POSTOSOResponse extends DOHeader{
//	private String docNo; //purchase order sto no
//	private String docDate; //purchase order sto date
//	private String docType;
//	
//	private String idSloc;
//	private String idPlant;
//
//	private String shipmentNo;
//	private String shipmentDate;
//	private String arrivalDate;
//	private String estimatedDepartureDate;
//	
//	private String destination;
//	private String destinationName;
//	private String destinationAddress;
//	private String destinationEmail;
//
//	private String siNo; //Shipment Instr. Number
//	
//	private String jdaLoadId;
//	
//	private String vendorExpedition;
//	private String vendorExpeditionName;
//
//	private String vehicleNo; //Vehicle No - Name
//	private String plateNo; //no police
//	private String driver;
//	private String route;
	
	private List<SAP_POSTOSODetailResponse> listDetail;


	public SAP_POSTOSOResponse() {
	}


	public List<SAP_POSTOSODetailResponse> getListDetail() {
		return listDetail;
	}


	public void setListDetail(List<SAP_POSTOSODetailResponse> listDetail) {
		this.listDetail = listDetail;
	}
	
	
	
}
