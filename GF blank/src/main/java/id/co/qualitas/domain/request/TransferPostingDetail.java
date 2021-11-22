package id.co.qualitas.domain.request;

import java.math.BigDecimal;
import java.util.Date;

import id.co.qualitas.domain.response.SAP_TpSenderDetailResponse;

public class TransferPostingDetail extends SAP_TpSenderDetailResponse{
	private int idTpHeader;
	private int itemNo;
	private BigDecimal orderQty;
	private BigDecimal tpQty;
	private Date expDate;
	
	private BigDecimal baseQtyOem;
	
	public TransferPostingDetail() {
	}

	public BigDecimal getBaseQtyOem() {
		return baseQtyOem;
	}

	public void setBaseQtyOem(BigDecimal baseQtyOem) {
		this.baseQtyOem = baseQtyOem;
	}

	public int getIdTpHeader() {
		return idTpHeader;
	}

	public void setIdTpHeader(int idTpHeader) {
		this.idTpHeader = idTpHeader;
	}

	public int getItemNo() {
		return itemNo;
	}

	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}

	public BigDecimal getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(BigDecimal orderQty) {
		this.orderQty = orderQty;
	}

	public BigDecimal getTpQty() {
		return tpQty;
	}

	public void setTpQty(BigDecimal tpQty) {
		this.tpQty = tpQty;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}
	
	
}
