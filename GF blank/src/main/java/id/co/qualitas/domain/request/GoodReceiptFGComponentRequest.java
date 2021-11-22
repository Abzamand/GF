package id.co.qualitas.domain.request;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import id.co.qualitas.domain.response.SAP_GoodReceiptFGComponentResponse;

public class GoodReceiptFGComponentRequest extends SAP_GoodReceiptFGComponentResponse {
	private String batchNo;
	private BigDecimal actualQty;
	private String uom;
	
	private BigDecimal stockSap;
	private String uomSap;
	private BigDecimal stockOem;
	private String uomOem;
	private boolean oemConversion;
	private BigDecimal multiplier;
	
	public GoodReceiptFGComponentRequest() {
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
		return oemConversion;
	}

	public void setOemConversion(boolean oemConversion) {
		this.oemConversion = oemConversion;
	}

	public BigDecimal getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(BigDecimal multiplier) {
		this.multiplier = multiplier;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public BigDecimal getActualQty() {
		return actualQty;
	}

	public void setActualQty(BigDecimal actualQty) {
		this.actualQty = actualQty;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	
}
