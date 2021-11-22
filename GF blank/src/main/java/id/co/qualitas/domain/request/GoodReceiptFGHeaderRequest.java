package id.co.qualitas.domain.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import id.co.qualitas.domain.response.SAP_GoodReceiptFGResponse;

public class GoodReceiptFGHeaderRequest extends SAP_GoodReceiptFGResponse {
	private String date;
	private String postingDate;
	
	public GoodReceiptFGHeaderRequest() { }


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getPostingDate() {
		return postingDate;
	}


	public void setPostingDate(String postingDate) {
		this.postingDate = postingDate;
	}
	
	
	
}
