package id.co.qualitas.domain.request;

import java.util.Date;

import id.co.qualitas.domain.response.SAP_TpSenderResponse;

public class TransferPostingHeader extends SAP_TpSenderResponse{
	private int id;
	private String tpNoSap;
	private Date date;
	private String status;
	private String infoSync;
	
	private String destinationName;
	private String destinationEmail;
	
	private String attachment;
	

	
	public TransferPostingHeader() {
	}
	

	public String getAttachment() {
		return attachment;
	}


	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}


	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getDestinationEmail() {
		return destinationEmail;
	}

	public void setDestinationEmail(String destinationEmail) {
		this.destinationEmail = destinationEmail;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTpNoSap() {
		return tpNoSap;
	}

	public void setTpNoSap(String tpNoSap) {
		this.tpNoSap = tpNoSap;
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

	public String getInfoSync() {
		return infoSync;
	}

	public void setInfoSync(String infoSync) {
		this.infoSync = infoSync;
	}
	
	
}
