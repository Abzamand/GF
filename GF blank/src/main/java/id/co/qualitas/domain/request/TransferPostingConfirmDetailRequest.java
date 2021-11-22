package id.co.qualitas.domain.request;

import java.math.BigDecimal;

import id.co.qualitas.domain.response.SAP_TransferPostingDetailResponse;

public class TransferPostingConfirmDetailRequest extends SAP_TransferPostingDetailResponse {
	private String notes;

//	private BigDecimal stockSap;
//	private String uomSap;
//	private BigDecimal stockOem;
//	private String uomOem;
//	private boolean oemConversion;
//	private BigDecimal multiplier;
	
	private BigDecimal baseQtyOem;
	
	public TransferPostingConfirmDetailRequest() {	}

	public BigDecimal getBaseQtyOem() {
		return baseQtyOem;
	}

	public void setBaseQtyOem(BigDecimal baseQtyOem) {
		this.baseQtyOem = baseQtyOem;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	
	
}
