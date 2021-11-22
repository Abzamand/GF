package id.co.qualitas.domain.response;

import java.math.BigDecimal;
import java.util.List;

public class SAP_ShipmDODetailResponse {
	private String doNo; //DETAIL_DO-VBELN
	private String doDate; //DETAIL_DO-LFDAT
	private String idSloc; //DETAIL_DO-LGORT
	private String idPlant; //DETAIL_DO-WERKS
	private String shipmentNo; //DETAIL_DO-TKNUM
	private String poNo;
	private BigDecimal totalWeight;
	private String uow;
	private BigDecimal totalVolume;
	private String uov;
	
	private List<SAP_ShipmDODetailItemResponse> listItem;

	public SAP_ShipmDODetailResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public BigDecimal getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(BigDecimal bigDecimal) {
		this.totalWeight = bigDecimal;
	}

	public String getUow() {
		return uow;
	}

	public void setUow(String uow) {
		this.uow = uow;
	}

	public BigDecimal getTotalVolume() {
		return totalVolume;
	}

	public void setTotalVolume(BigDecimal totalVolume) {
		this.totalVolume = totalVolume;
	}

	public String getUov() {
		return uov;
	}

	public void setUov(String uov) {
		this.uov = uov;
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

	public String getShipmentNo() {
		return shipmentNo;
	}

	public void setShipmentNo(String shipmentNo) {
		this.shipmentNo = shipmentNo;
	}

	public List<SAP_ShipmDODetailItemResponse> getListItem() {
		return listItem;
	}

	public void setListItem(List<SAP_ShipmDODetailItemResponse> listItem) {
		this.listItem = listItem;
	}
	
	
}
