package id.co.qualitas.domain.response;

import java.math.BigDecimal;

public class SAP_StockOEMResponse {
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
	
	private BigDecimal stockSap;
	private String uomSap;
	private BigDecimal stockOem;
	private String uomOem;
	private boolean isOemConversion;
	private BigDecimal multiplier;
	
	private String uomAvailableStock;
	private String uomUuStock;
	private String uomQiStock;
	private String uomBlockedStock;
	private String uomStockProviderToVendor;
	
	private BigDecimal baseQtyOem;
	
	private String productionDate;
	private String expiredDate;
	
	public SAP_StockOEMResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public String getProductionDate() {
		return productionDate;
	}


	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}


	public String getExpiredDate() {
		return expiredDate;
	}


	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}


	public BigDecimal getBaseQtyOem() {
		return baseQtyOem;
	}


	public void setBaseQtyOem(BigDecimal baseQtyOem) {
		this.baseQtyOem = baseQtyOem;
	}


	public String getUomAvailableStock() {
		return uomAvailableStock;
	}


	public void setUomAvailableStock(String uomAvailableStock) {
		this.uomAvailableStock = uomAvailableStock;
	}


	public String getUomUuStock() {
		return uomUuStock;
	}


	public void setUomUuStock(String uomUuStock) {
		this.uomUuStock = uomUuStock;
	}


	public String getUomQiStock() {
		return uomQiStock;
	}


	public void setUomQiStock(String uomQiStock) {
		this.uomQiStock = uomQiStock;
	}


	public String getUomBlockedStock() {
		return uomBlockedStock;
	}


	public void setUomBlockedStock(String uomBlockedStock) {
		this.uomBlockedStock = uomBlockedStock;
	}


	public String getUomStockProviderToVendor() {
		return uomStockProviderToVendor;
	}


	public void setUomStockProviderToVendor(String uomStockProviderToVendor) {
		this.uomStockProviderToVendor = uomStockProviderToVendor;
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
