package id.co.qualitas.domain.request;

import java.math.BigDecimal;
import java.util.Date;

import id.co.qualitas.domain.response.SAP_POSTODetailResponse;

public class GoodReceiptSTODetail extends SAP_POSTODetailResponse {
	private int idGrHeader;
	private int itemNo;
	private String variance;
	private String reason;
	private byte[] photo;
	private BigDecimal grQty;
	
	private String photoString;
	
	public GoodReceiptSTODetail() {
	}

	public BigDecimal getGrQty() {
		return grQty;
	}

	public void setGrQty(BigDecimal grQty) {
		this.grQty = grQty;
	}

	public int getIdGrHeader() {
		return idGrHeader;
	}

	public void setIdGrHeader(int idGrHeader) {
		this.idGrHeader = idGrHeader;
	}

	public int getItemNo() {
		return itemNo;
	}

	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}

	public String getVariance() {
		return variance;
	}

	public void setVariance(String variance) {
		this.variance = variance;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getPhotoString() {
		return photoString;
	}

	public void setPhotoString(String photoString) {
		this.photoString = photoString;
	}
	
	
}
