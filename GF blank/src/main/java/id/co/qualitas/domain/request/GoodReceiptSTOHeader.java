package id.co.qualitas.domain.request;

import java.util.Date;

import id.co.qualitas.domain.response.SAP_POSTOResponse;

public class GoodReceiptSTOHeader extends SAP_POSTOResponse {
	private int id;
	private String grNoSap;
	private Date date;
	private String docType;
	private String status;
	private String infoSync;
	
	
	public GoodReceiptSTOHeader() {
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getGrNoSap() {
		return grNoSap;
	}


	public void setGrNoSap(String grNoSap) {
		this.grNoSap = grNoSap;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public String getDocType() {
		return docType;
	}


	public void setDocType(String docType) {
		this.docType = docType;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getInfoSync() {
		return infoSync;
	}


	public void setInfoSync(String infoSync) {
		this.infoSync = infoSync;
	}


	
}
