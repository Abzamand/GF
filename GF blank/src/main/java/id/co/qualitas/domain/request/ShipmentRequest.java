package id.co.qualitas.domain.request;

import java.util.List;

public class ShipmentRequest {
	private ShipmentHeader header;
	private List<ShipmentDetail> listDetail;
	private String createdBy;
	
	public ShipmentRequest() {
	}
	public ShipmentHeader getHeader() {
		return header;
	}
	public void setHeader(ShipmentHeader header) {
		this.header = header;
	}
	public List<ShipmentDetail> getListDetail() {
		return listDetail;
	}
	public void setListDetail(List<ShipmentDetail> listDetail) {
		this.listDetail = listDetail;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
}
