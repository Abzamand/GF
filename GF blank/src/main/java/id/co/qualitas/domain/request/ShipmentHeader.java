package id.co.qualitas.domain.request;

import java.util.Date;

public class ShipmentHeader {
	private String shipmentNo;
	private Date shipmentDate;
	private String idSloc;
	private String idPlant;
	private String vendorForwarding;
	private String plateNo;
	private String vehicleType;
	private String status;
	
	public ShipmentHeader() {
	}
	
	public ShipmentHeader(String shipmentNo, Date shipmentDate, String idSloc, String idPlant, String vendorForwarding,
			String plateNo, String vehicleType, String status) {
		super();
		this.shipmentNo = shipmentNo;
		this.shipmentDate = shipmentDate;
		this.idSloc = idSloc;
		this.idPlant = idPlant;
		this.vendorForwarding = vendorForwarding;
		this.plateNo = plateNo;
		this.vehicleType = vehicleType;
		this.status = status;
	}



	public String getShipmentNo() {
		return shipmentNo;
	}
	public void setShipmentNo(String shipmentNo) {
		this.shipmentNo = shipmentNo;
	}
	public Date getShipmentDate() {
		return shipmentDate;
	}
	public void setShipmentDate(Date shipmentDate) {
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
