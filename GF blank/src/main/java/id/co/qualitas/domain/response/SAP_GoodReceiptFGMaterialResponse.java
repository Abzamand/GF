package id.co.qualitas.domain.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SAP_GoodReceiptFGMaterialResponse {
	private String docNo;
	private String docItemNo;
	private String materialNo;
	private String materialDesc;
	private String prodTargetDate;
	private BigDecimal orderQty;
	private BigDecimal outstandingQty;
	private String uom;
	private String itemCategory;
	private String poDesc;
	private String deliveryDate;
	
	private List<SAP_GoodReceiptFGComponentResponse> listComponent;
	
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
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
	public String getProdTargetDate() {
		return prodTargetDate;
	}
	public void setProdTargetDate(String prodTargetDate) {
		this.prodTargetDate = prodTargetDate;
	}
	public BigDecimal getOrderQty() {
		return orderQty;
	}
	public void setOrderQty(BigDecimal orderQty) {
		this.orderQty = orderQty;
	}
	public BigDecimal getOutstandingQty() {
		return outstandingQty;
	}
	public void setOutstandingQty(BigDecimal outstandingQty) {
		this.outstandingQty = outstandingQty;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public List<SAP_GoodReceiptFGComponentResponse> getListComponent() {
		return listComponent;
	}
	public void setListComponent(List<SAP_GoodReceiptFGComponentResponse> listComponent) {
		this.listComponent = listComponent;
	}
	public String getItemCategory() {
		return itemCategory;
	}
	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}
	public String getPoDesc() {
		return poDesc;
	}
	public void setPoDesc(String poDesc) {
		this.poDesc = poDesc;
	}
	
	
}
