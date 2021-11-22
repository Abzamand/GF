package id.co.qualitas.domain.request;

import java.math.BigDecimal;
import java.util.List;

import id.co.qualitas.domain.response.SAP_POSTOSODetailResponse;

/*new 090321*/
public class UomConversion {

	private String idVendorSap;
	private String materialNo;
	private BigDecimal qtyOem;
	private String uomOem;
	private BigDecimal qtySap;
	private String uomSap;
	
	private BigDecimal baseQtyOem;
	private Double baseQtyOemFloat;
	private String baseQtyOemString;
	
	private BigDecimal multiplier;
	
	public UomConversion() {
		super();
	}
	

	public String getBaseQtyOemString() {
		return baseQtyOemString;
	}


	public Double getBaseQtyOemFloat() {
		return baseQtyOemFloat;
	}


	public void setBaseQtyOemFloat(Double baseQtyOemFloat) {
		this.baseQtyOemFloat = baseQtyOemFloat;
	}


	public void setBaseQtyOemString(String baseQtyOemString) {
		this.baseQtyOemString = baseQtyOemString;
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

	public String getIdVendorSap() {
		return idVendorSap;
	}
	public void setIdVendorSap(String idVendorSap) {
		this.idVendorSap = idVendorSap;
	}
	public String getMaterialNo() {
		return materialNo;
	}
	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}
	public BigDecimal getQtyOem() {
		return qtyOem;
	}
	public void setQtyOem(BigDecimal qtyOem) {
		this.qtyOem = qtyOem;
	}
	public String getUomOem() {
		return uomOem;
	}
	public void setUomOem(String uomOem) {
		this.uomOem = uomOem;
	}
	
	public BigDecimal getQtySap() {
		return qtySap;
	}


	public void setQtySap(BigDecimal qtySap) {
		this.qtySap = qtySap;
	}


	public String getUomSap() {
		return uomSap;
	}
	public void setUomSap(String uomSap) {
		this.uomSap = uomSap;
	}
	
	
	
}
