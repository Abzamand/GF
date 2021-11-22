package id.co.qualitas.domain.request;

import java.util.Date;

public class ShipmentDetail {
	private String shipmentNo;
	private String doNo;
	private Date doDate;
	private String idSloc;
	private String idPlant;
	
	public ShipmentDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShipmentDetail(String shipmentNo, String doNo, Date doDate, String idSloc, String idPlant) {
		super();
		this.shipmentNo = shipmentNo;
		this.doNo = doNo;
		this.doDate = doDate;
		this.idSloc = idSloc;
		this.idPlant = idPlant;
	}



	public String getShipmentNo() {
		return shipmentNo;
	}

	public void setShipmentNo(String shipmentNo) {
		this.shipmentNo = shipmentNo;
	}

	public String getDoNo() {
		return doNo;
	}

	public void setDoNo(String doNo) {
		this.doNo = doNo;
	}

	public Date getDoDate() {
		return doDate;
	}

	public void setDoDate(Date doDate) {
		this.doDate = doDate;
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
	
	
}
