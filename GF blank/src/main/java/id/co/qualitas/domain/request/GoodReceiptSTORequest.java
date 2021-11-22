package id.co.qualitas.domain.request;

import java.util.List;

public class GoodReceiptSTORequest {
	private GoodReceiptSTOHeader header;
	private List<GoodReceiptSTODetail> listDetail;
	private String createdBy;

	public GoodReceiptSTORequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GoodReceiptSTORequest(GoodReceiptSTOHeader header, List<GoodReceiptSTODetail> listDetail, String createdBy) {
		super();
		this.header = header;
		this.listDetail = listDetail;
		this.createdBy = createdBy;
	}
	public GoodReceiptSTOHeader getHeader() {
		return header;
	}
	public void setHeader(GoodReceiptSTOHeader header) {
		this.header = header;
	}
	public List<GoodReceiptSTODetail> getListDetail() {
		return listDetail;
	}
	public void setListDetail(List<GoodReceiptSTODetail> listDetail) {
		this.listDetail = listDetail;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
}
