package id.co.qualitas.domain.response;

import java.math.BigDecimal;

public class SAP_ShipmDODetailItemResponse {
	private String doNo; //DETAIL_ITEM_DO-VBELN
	private String itemNr; //DETAIL_ITEM_DO-POSNR
	private String higherLevelItemBatch; //DETAIL_ITEM_DO-UECHA
	private String idMaterial; //DETAIL_ITEM_DO-MATNR
	private String materialName; //DETAIL_ITEM_DO-ARKTX
	private String batchNo; //DETAIL_ITEM_DO-CHARG
	private String expiredDate; //DETAIL_ITEM_DO-VFDAT
	private BigDecimal orderQty; //DETAIL_ITEM_DO-LFIMG
	private String uom; //DETAIL_ITEM_DO-MEINS//..DETAIL_ITEM_DO-VRKME
	
	public SAP_ShipmDODetailItemResponse() {
		super();
	}
	public String getDoNo() {
		return doNo;
	}
	public void setDoNo(String doNo) {
		this.doNo = doNo;
	}
	public String getItemNr() {
		return itemNr;
	}
	public void setItemNr(String itemNr) {
		this.itemNr = itemNr;
	}
	public String getHigherLevelItemBatch() {
		return higherLevelItemBatch;
	}
	public void setHigherLevelItemBatch(String higherLevelItemBatch) {
		this.higherLevelItemBatch = higherLevelItemBatch;
	}
	public String getIdMaterial() {
		return idMaterial;
	}
	public void setIdMaterial(String idMaterial) {
		this.idMaterial = idMaterial;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}
	public BigDecimal getOrderQty() {
		return orderQty;
	}
	public void setOrderQty(BigDecimal orderQty) {
		this.orderQty = orderQty;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	
	
}
