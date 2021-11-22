package id.co.qualitas.domain.request;

import java.util.List;

public class PGIRequest {
	private PGIHeader header;
	private List<PGIDetail> listDetail;
	private String createdBy;
	public PGIRequest() {
	}
	public PGIHeader getHeader() {
		return header;
	}
	public void setHeader(PGIHeader header) {
		this.header = header;
	}
	public List<PGIDetail> getListDetail() {
		return listDetail;
	}
	public void setListDetail(List<PGIDetail> listDetail) {
		this.listDetail = listDetail;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
}
