package id.co.qualitas.domain.response;

import java.math.BigDecimal;

public class BatchNoStockResponse {
	private String batchNo;
	private BigDecimal stock;
	private String uom;
	
	
	public BatchNoStockResponse() {
		super();
		// TODO Auto-generated constructor stub
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
