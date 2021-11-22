package id.co.qualitas.domain.request;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import id.co.qualitas.domain.response.SAP_ShipmDOResponse;


public class PGIHeader extends SAP_ShipmDOResponse{
	private Date date;
	private String status;
	
	private String vehicleNo;

	public PGIHeader() {
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	

}
