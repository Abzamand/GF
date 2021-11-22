package id.co.qualitas.domain.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TransferPostingDetailResponse {
	private String docNo;
	private String docItemNo;
	private String materialNo;
	private String materialDesc;
	private String batchNo;
	private BigDecimal qty;
	private String uom;
	public TransferPostingDetailResponse() {
	}
	public TransferPostingDetailResponse(String docNo, String docItemNo, String materialNo, String materialDesc,
			String batchNo, BigDecimal qty, String uom) {
		super();
		this.docNo = docNo;
		this.docItemNo = docItemNo;
		this.materialNo = materialNo;
		this.materialDesc = materialDesc;
		this.batchNo = batchNo;
		this.qty = qty;
		this.uom = uom;
	}
	public String getDocNo() {
		return docNo;
	}
	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}
	public String getDocItemNo() {
		return docItemNo;
	}
	public void setDocItemNo(String docItemNo) {
		this.docItemNo = docItemNo;
	}
	public String getMaterialNo() {
		return materialNo;
	}
	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}
	public String getMaterialDesc() {
		return materialDesc;
	}
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public BigDecimal getQty() {
		return qty;
	}
	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	
	
}
