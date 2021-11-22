package id.co.qualitas.domain.request;

import java.util.List;

public class TransferPostingConfirmRequest {
	private TransferPostingConfirmHeaderRequest header;
	private List<TransferPostingConfirmDetailRequest> listDetail;
	private String createdBy;
	
	public TransferPostingConfirmHeaderRequest getHeader() {
		return header;
	}
	public void setHeader(TransferPostingConfirmHeaderRequest header) {
		this.header = header;
	}
	public List<TransferPostingConfirmDetailRequest> getListDetail() {
		return listDetail;
	}
	public void setListDetail(List<TransferPostingConfirmDetailRequest> listDetail) {
		this.listDetail = listDetail;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
}
