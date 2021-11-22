package id.co.qualitas.domain.request;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import id.co.qualitas.domain.response.SAP_GoodReceiptFGMaterialResponse;

public class GoodReceiptFGMaterialRequest extends SAP_GoodReceiptFGMaterialResponse {
	private BigDecimal grQty;
	private String batchNo;
	private Date expDate;
	private String postingDate;
	private String deliveryDate;

	public GoodReceiptFGMaterialRequest() {
	}

	public BigDecimal getGrQty() {
		return grQty;
	}

	public void setGrQty(BigDecimal grQty) {
		this.grQty = grQty;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	public String getPostingDate() {
		return postingDate;
	}

	public void setPostingDate(String postingDate) {
		this.postingDate = postingDate;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	
	
}
