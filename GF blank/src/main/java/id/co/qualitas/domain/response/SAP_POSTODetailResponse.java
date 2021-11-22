package id.co.qualitas.domain.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SAP_POSTODetailResponse {
	private String docNo; //DETAIL-ZDOCNO
	private String docItemNo; //DETAIL-POSNR
	private String materialNo; //DETAIL-MATNR
	private String materialDesc; //DETAIL-TXZ01
	private BigDecimal orderQty; //DETAIL-MENGE
	private String doNo; //DETAIL-VBELN
	private String doDate; //DETAIL-VBELN
	private String doItemNo; //DETAIL-ZPOSNR
	private String higherLevelItemBatch; //DETAIL-UECHA
	private String batchNo; //DETAIL-CHARG
	private String expDate; //DETAIL-VFDAT
	private BigDecimal doQty; //DETAIL-LFIMG
	private String uom; //DETAIL-VRKME
	private String pgiNoSap; //DETAIL-MBLNR
	
	private String idSloc;
	private String idPlant;
	private String docDate;
	
	private String pgiNo;
	private String pgiDate;
	
	private String plantName;
	
	
	private List<SAP_POSTODetailResponse> listItem;

	public SAP_POSTODetailResponse() {
	}


	public SAP_POSTODetailResponse(String doNo, String docNo, String doDate) {
		this.doNo = doNo;
		this.docNo = docNo;
		this.doDate = doDate;
	}

	public String getPlantName() {
		return plantName;
	}


	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}


	public String getPgiNo() {
		return pgiNo;
	}


	public void setPgiNo(String pgiNo) {
		this.pgiNo = pgiNo;
	}


	public String getPgiDate() {
		return pgiDate;
	}


	public void setPgiDate(String pgiDate) {
		this.pgiDate = pgiDate;
	}


	public String getDocDate() {
		return docDate;
	}


	public String getIdSloc() {
		return idSloc;
	}


	public void setIdSloc(String idSloc) {
		this.idSloc = idSloc;
	}


	public String getIdPlant() {
		return idPlant;
	}


	public void setIdPlant(String idPlant) {
		this.idPlant = idPlant;
	}


	public void setDocDate(String docDate) {
		this.docDate = docDate;
	}


	public String getPgiNoSap() {
		return pgiNoSap;
	}

	public void setPgiNoSap(String pgiNoSap) {
		this.pgiNoSap = pgiNoSap;
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

	public BigDecimal getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(BigDecimal orderQty) {
		this.orderQty = orderQty;
	}

	public String getDoNo() {
		return doNo;
	}

	public void setDoNo(String doNo) {
		this.doNo = doNo;
	}

	public String getDoDate() {
		return doDate;
	}

	public void setDoDate(String doDate) {
		this.doDate = doDate;
	}

	public String getDoItemNo() {
		return doItemNo;
	}

	public void setDoItemNo(String doItemNo) {
		this.doItemNo = doItemNo;
	}

	public String getHigherLevelItemBatch() {
		return higherLevelItemBatch;
	}

	public void setHigherLevelItemBatch(String higherLevelItemBatch) {
		this.higherLevelItemBatch = higherLevelItemBatch;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public BigDecimal getDoQty() {
		return doQty;
	}

	public void setDoQty(BigDecimal doQty) {
		this.doQty = doQty;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public List<SAP_POSTODetailResponse> getListItem() {
		return listItem;
	}

	public void setListItem(List<SAP_POSTODetailResponse> listItem) {
		this.listItem = listItem;
	}

	
	
}
