package id.co.qualitas.domain.response;

import java.math.BigDecimal;

public class StockOEMResponse {
	private String materialNo;
	private String materialDesc;
	private String materialType;
	private String batchNo;
	private BigDecimal availableStock;
	private BigDecimal uuStock;
	private BigDecimal qiStock;
	private BigDecimal blockedStock;
	private BigDecimal stockProviderToVendor;
	private String uom;
	
	
	public StockOEMResponse() {
		super();
		// TODO Auto-generated constructor stub
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
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public BigDecimal getAvailableStock() {
		return availableStock;
	}
	public void setAvailableStock(BigDecimal availableStock) {
		this.availableStock = availableStock;
	}
	public BigDecimal getUuStock() {
		return uuStock;
	}
	public void setUuStock(BigDecimal uuStock) {
		this.uuStock = uuStock;
	}
	public BigDecimal getQiStock() {
		return qiStock;
	}
	public void setQiStock(BigDecimal qiStock) {
		this.qiStock = qiStock;
	}
	public BigDecimal getBlockedStock() {
		return blockedStock;
	}
	public void setBlockedStock(BigDecimal blockedStock) {
		this.blockedStock = blockedStock;
	}
	public BigDecimal getStockProviderToVendor() {
		return stockProviderToVendor;
	}
	public void setStockProviderToVendor(BigDecimal stockProviderToVendor) {
		this.stockProviderToVendor = stockProviderToVendor;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	
	

}
