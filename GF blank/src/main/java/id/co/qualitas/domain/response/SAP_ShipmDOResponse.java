package id.co.qualitas.domain.response;

import java.util.List;

public class SAP_ShipmDOResponse {
	private String shipmentNo;
	private String shipmentDate;
	private String idSloc;
	private String idPlant;
	private String vendorForwarding;
	private String plateNo;
	private String vehicleType;
	
	private String shipmentInstrNumber;
	
	private String driver;
	
	private List<SAP_ShipmDODetailResponse> listDetail;



	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getShipmentInstrNumber() {
		return shipmentInstrNumber;
	}

	public void setShipmentInstrNumber(String shipmentInstrNumber) {
		this.shipmentInstrNumber = shipmentInstrNumber;
	}

	public SAP_ShipmDOResponse() {
		super();
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

	public String getVendorForwarding() {
		return vendorForwarding;
	}

	public void setVendorForwarding(String vendorForwarding) {
		this.vendorForwarding = vendorForwarding;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public List<SAP_ShipmDODetailResponse> getListDetail() {
		return listDetail;
	}

	public void setListDetail(List<SAP_ShipmDODetailResponse> listDetail) {
		this.listDetail = listDetail;
	}
	
	
	
}
