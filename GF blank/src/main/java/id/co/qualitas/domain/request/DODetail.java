package id.co.qualitas.domain.request;

import java.math.BigDecimal;
import java.util.List;

import id.co.qualitas.domain.response.SAP_POSTOSODetailResponse;

public class DODetail {

	/*parent*/
	private String docNo;
	private String docItemNo;
	private String materialNo;
	private String materialDesc;
	private BigDecimal orderQty;
	private BigDecimal outstandingQty;
	private String uom;
	
	private BigDecimal doQty;
	private String batchNo;
	private BigDecimal actualQty;
	
	private int idDoHeader;
	private int itemNo;
	
	private int itemNoDetail;
	
	private String shipmentNo;
	
	private List<DODetail> listBatch;
	
	public DODetail() {
	}

	public String getShipmentNo() {
		return shipmentNo;
	}

	public void setShipmentNo(String shipmentNo) {
		this.shipmentNo = shipmentNo;
	}

	public List<DODetail> getListBatch() {
		return listBatch;
	}

	public void setListBatch(List<DODetail> listBatch) {
		this.listBatch = listBatch;
	}

	public int getItemNoDetail() {
		return itemNoDetail;
	}

	public void setItemNoDetail(int itemNoDetail) {
		this.itemNoDetail = itemNoDetail;
	}

	public BigDecimal getOutstandingQty() {
		return outstandingQty;
	}

	public void setOutstandingQty(BigDecimal outstandingQty) {
		this.outstandingQty = outstandingQty;
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

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public BigDecimal getDoQty() {
		return doQty;
	}

	public void setDoQty(BigDecimal doQty) {
		this.doQty = doQty;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public BigDecimal getActualQty() {
		return actualQty;
	}

	public void setActualQty(BigDecimal actualQty) {
		this.actualQty = actualQty;
	}

	public int getIdDoHeader() {
		return idDoHeader;
	}

	public void setIdDoHeader(int idDoHeader) {
		this.idDoHeader = idDoHeader;
	}

	public int getItemNo() {
		return itemNo;
	}

	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}

	
}
