package id.co.qualitas.domain.response;

import java.math.BigDecimal;

public class SAP_BatchNoStockResponse {
	private String batchNo;
	private BigDecimal stock;
	private String uom;
	
	private BigDecimal stockSap;
	private String uomSap;
	private BigDecimal stockOem;
	private String uomOem;
	private boolean isOemConversion;
	private BigDecimal multiplier;
	
	private BigDecimal baseQtyOem;
	
	public SAP_BatchNoStockResponse() {
		super();
		// TODO Auto-generated constructor stub
	}


	public BigDecimal getBaseQtyOem() {
		return baseQtyOem;
	}


	public void setBaseQtyOem(BigDecimal baseQtyOem) {
		this.baseQtyOem = baseQtyOem;
	}


	public BigDecimal getMultiplier() {
		return multiplier;
	}


	public void setMultiplier(BigDecimal multiplier) {
		this.multiplier = multiplier;
	}


	public boolean isOemConversion() {
		return isOemConversion;
	}


	public void setOemConversion(boolean isOemConversion) {
		this.isOemConversion = isOemConversion;
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


	public String getBatchNo() {
		return batchNo;
	}


	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}


	public BigDecimal getStock() {
		return stock;
	}


	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}


	public String getUom() {
		return uom;
	}


	public void setUom(String uom) {
		this.uom = uom;
	}

}
