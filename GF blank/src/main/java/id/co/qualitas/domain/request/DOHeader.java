package id.co.qualitas.domain.request;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import id.co.qualitas.domain.response.SAP_POSTOSOResponse;

public class DOHeader{

	private int id;
	private String doNoSap;
	private String date;
	
	private String docNo; //purchase order sto no
	private String docDate; //purchase order sto date
	private String docHeader;
	private String docType;
	
	private String idSloc;
	private String idPlant;

	private String shipmentNo;
	private String shipmentDate;
	private String arrivalDate;
	private String estimatedDepartureDate;
	
	private String destination;
	private String destinationName;
	private String destinationAddress;
	private String destinationEmail;

	private String siNo; //Shipment Instr. Number
	
	private String jdaLoadId;
	
	private String vendorExpedition;
	private String vendorExpeditionName;

	private String vehicleNo; //Vehicle No - Name
	private String plateNo; //no police
	private String driver;
	private String route;
	
	private String status;
	private String podStatus;
	private String podStatusDesc;
	
	private String infoSync;
	private String notes;

	private String attachment;
	
	private String destinationName2;
	private String destinationPhone;
	private String poNo;
	
	//tambah
	private String plantName;
	private String senderAddress;
	private String senderPhone;
	private BigDecimal totalProductWeight;
	private BigDecimal totalProductVolume;
	private String uow;
	private String uov;
	private String regency;
	private String postalCode;
	
	public DOHeader() {
	}

	public DOHeader(String docNo, String shipmentNo) {
		this.docNo = docNo;
		this.shipmentNo = shipmentNo;
	}

	public String getRegency() {
		return regency;
	}

	public void setRegency(String regency) {
		this.regency = regency;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}


	public BigDecimal getTotalProductWeight() {
		return totalProductWeight;
	}

	public void setTotalProductWeight(BigDecimal totalProductWeight) {
		this.totalProductWeight = totalProductWeight;
	}

	public BigDecimal getTotalProductVolume() {
		return totalProductVolume;
	}

	public void setTotalProductVolume(BigDecimal totalProductVolume) {
		this.totalProductVolume = totalProductVolume;
	}

	public String getUow() {
		return uow;
	}

	public void setUow(String uow) {
		this.uow = uow;
	}

	public String getUov() {
		return uov;
	}

	public void setUov(String uov) {
		this.uov = uov;
	}

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	public String getSenderPhone() {
		return senderPhone;
	}

	public void setSenderPhone(String senderPhone) {
		this.senderPhone = senderPhone;
	}

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public String getDestinationName2() {
		return destinationName2;
	}



	public void setDestinationName2(String destinationName2) {
		this.destinationName2 = destinationName2;
	}



	public String getDestinationPhone() {
		return destinationPhone;
	}



	public void setDestinationPhone(String destinationPhone) {
		this.destinationPhone = destinationPhone;
	}



	public String getPoNo() {
		return poNo;
	}



	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}



	public String getPodStatusDesc() {
		return podStatusDesc;
	}

	public void setPodStatusDesc(String podStatusDesc) {
		this.podStatusDesc = podStatusDesc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDoNoSap() {
		return doNoSap;
	}

	public void setDoNoSap(String doNoSap) {
		this.doNoSap = doNoSap;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDocNo() {
		return docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	public String getDocDate() {
		return docDate;
	}

	public void setDocDate(String docDate) {
		this.docDate = docDate;
	}

	public String getDocHeader() {
		return docHeader;
	}

	public void setDocHeader(String docHeader) {
		this.docHeader = docHeader;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getIdSloc() {
		return idSloc;
	}

	public void setIdSloc(String idSloc) {
		this.idSloc = idSloc;
	}

	public String getIdPlant() {
		return idPlant;
	}

	public void setIdPlant(String idPlant) {
		this.idPlant = idPlant;
	}

	public String getShipmentNo() {
		return shipmentNo;
	}

	public void setShipmentNo(String shipmentNo) {
		this.shipmentNo = shipmentNo;
	}

	public String getShipmentDate() {
		return shipmentDate;
	}

	public void setShipmentDate(String shipmentDate) {
		this.shipmentDate = shipmentDate;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getEstimatedDepartureDate() {
		return estimatedDepartureDate;
	}

	public void setEstimatedDepartureDate(String estimatedDepartureDate) {
		this.estimatedDepartureDate = estimatedDepartureDate;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getDestinationEmail() {
		return destinationEmail;
	}

	public void setDestinationEmail(String destinationEmail) {
		this.destinationEmail = destinationEmail;
	}

	public String getSiNo() {
		return siNo;
	}

	public void setSiNo(String siNo) {
		this.siNo = siNo;
	}

	public String getJdaLoadId() {
		return jdaLoadId;
	}

	public void setJdaLoadId(String jdaLoadId) {
		this.jdaLoadId = jdaLoadId;
	}

	public String getVendorExpedition() {
		return vendorExpedition;
	}

	public void setVendorExpedition(String vendorExpedition) {
		this.vendorExpedition = vendorExpedition;
	}

	public String getVendorExpeditionName() {
		return vendorExpeditionName;
	}

	public void setVendorExpeditionName(String vendorExpeditionName) {
		this.vendorExpeditionName = vendorExpeditionName;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPodStatus() {
		return podStatus;
	}

	public void setPodStatus(String podStatus) {
		this.podStatus = podStatus;
	}

	public String getInfoSync() {
		return infoSync;
	}

	public void setInfoSync(String infoSync) {
		this.infoSync = infoSync;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
	
	
}
