package id.co.qualitas.domain.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import id.co.qualitas.domain.response.SAP_TransferPostingResponse;

public class TransferPostingConfirmHeaderRequest extends SAP_TransferPostingResponse{
	private Date date;
	private String docType;
	private String materialDoc;
	
	private String idVendor;
	
	public TransferPostingConfirmHeaderRequest() { }


	public String getIdVendor() {
		return idVendor;
	}


	public void setIdVendor(String idVendor) {
		this.idVendor = idVendor;
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


	public String getMaterialDoc() {
		return materialDoc;
	}


	public void setMaterialDoc(String materialDoc) {
		this.materialDoc = materialDoc;
	}
	
	
}
