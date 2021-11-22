package id.co.qualitas.domain.request;

import java.util.List;

public class DORequest {
	private DOHeader doHeader;
	private List<DODetail> listDoDetail;
	private String createdBy;
	
	public DORequest() {
	}
	public DOHeader getDoHeader() {
		return doHeader;
	}
	public void setDoHeader(DOHeader doHeader) {
		this.doHeader = doHeader;
	}
	public List<DODetail> getListDoDetail() {
		return listDoDetail;
	}
	public void setListDoDetail(List<DODetail> listDoDetail) {
		this.listDoDetail = listDoDetail;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
}
