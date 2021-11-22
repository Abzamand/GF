package id.co.qualitas.domain.request;

import java.util.List;

public class TransferPostingRequest {
	private TransferPostingHeader header;
	private List<TransferPostingDetail> listDetail;
	private String createdBy;
	
	public TransferPostingRequest() {
	}
	public TransferPostingHeader getHeader() {
		return header;
	}
	public void setHeader(TransferPostingHeader header) {
		this.header = header;
	}
	public List<TransferPostingDetail> getListDetail() {
		return listDetail;
	}
	public void setListDetail(List<TransferPostingDetail> listDetail) {
		this.listDetail = listDetail;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
}
