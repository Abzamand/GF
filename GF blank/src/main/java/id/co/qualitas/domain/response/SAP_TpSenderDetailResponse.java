package id.co.qualitas.domain.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SAP_TpSenderDetailResponse {

	private String docNo; //HEADER-MBLNR
	private String docItemNo; //DETAIL-LINE_ID
	private String materialNo; //DETAIL-MATNR
	private String materialDesc; //DETAIL-MAKTX
	private String batchNo; //DETAIL-CHARG
	private BigDecimal qty; //DETAIL-MENGE
	private String uom; //DETAIL-MEINS
	
	private BigDecimal stockSap;
	private String uomSap;
	private BigDecimal stockOem;
	private String uomOem;
	private boolean isOemConversion;
	private BigDecimal multiplier;
	
	private BigDecimal baseQtyOem;
	
	public SAP_TpSenderDetailResponse() {
		super();
	}

	public BigDecimal getBaseQtyOem() {
		return baseQtyOem;
	}

	public void setBaseQtyOem(BigDecimal baseQtyOem) {
		this.baseQtyOem = baseQtyOem;
	}

	public BigDecimal getStockSap() {
		return stockSap;
	}

	public void setStockSap(BigDecimal stockSap) {
		this.stockSap = stockSap;
	}

	public String getUomSap() {
		return uomSap;
	}

	public void setUomSap(String uomSap) {
		this.uomSap = uomSap;
	}

	public BigDecimal getStockOem() {
		return stockOem;
	}

	public void setStockOem(BigDecimal stockOem) {
		this.stockOem = stockOem;
	}

	public String getUomOem() {
		return uomOem;
	}

	public void setUomOem(String uomOem) {
		this.uomOem = uomOem;
	}

	public boolean isOemConversion() {
		return isOemConversion;
	}

	public void setOemConversion(boolean isOemConversion) {
		this.isOemConversion = isOemConversion;
	}

	public BigDecimal getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(BigDecimal multiplier) {
		this.multiplier = multiplier;
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
