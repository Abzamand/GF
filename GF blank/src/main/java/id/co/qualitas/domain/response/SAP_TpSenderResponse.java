package id.co.qualitas.domain.response;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SAP_TpSenderResponse {
	private String docNo; //HEADER-MBLNR
	private String docDate; //HEADER-BUDAT
	private String docHeader; //HEADER-BKTXT
	private String idSloc; //HEADER-LGORT
	private String idPlant; //HEADER-WERKS
	private String oemFrom; //SOURCE_SLOC
	private String oemTo; //DEST_SLOC
	
//	YANG BARU KUTAMBAHIN
	private String destinationIdPlant;
	private String destinationPlantName;
	private String destinationAddress;
	private String destinationPhone;
	
	private String originIdPlant;
	private String originPlantName;
	private String originAddress;
	private String originPhone;

	
	public String getDestinationIdPlant() {
		return destinationIdPlant;
	}

	public void setDestinationIdPlant(String destinationIdPlant) {
		this.destinationIdPlant = destinationIdPlant;
	}

	public String getDestinationPlantName() {
		return destinationPlantName;
	}

	public void setDestinationPlantName(String destinationPlantName) {
		this.destinationPlantName = destinationPlantName;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getDestinationPhone() {
		return destinationPhone;
	}

	public void setDestinationPhone(String destinationPhone) {
		this.destinationPhone = destinationPhone;
	}

	public String getOriginIdPlant() {
		return originIdPlant;
	}

	public void setOriginIdPlant(String originIdPlant) {
		this.originIdPlant = originIdPlant;
	}

	public String getOriginPlantName() {
		return originPlantName;
	}

	public void setOriginPlantName(String originPlantName) {
		this.originPlantName = originPlantName;
	}

	public String getOriginAddress() {
		return originAddress;
	}

	public void setOriginAddress(String originAddress) {
		this.originAddress = originAddress;
	}

	public String getOriginPhone() {
		return originPhone;
	}

	public void setOriginPhone(String originPhone) {
		this.originPhone = originPhone;
	}
	
// END OF YANG KUTAMBAHIN
	
	private List<SAP_TpSenderDetailResponse> listDetail;

	public SAP_TpSenderResponse() {
		super();
	}

	public String getDocNo() {
		return docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	public String getDocDate() {
		return docDate;
	}

	public void setDocDate(String docDate) {
		this.docDate = docDate;
	}

	public String getDocHeader() {
		return docHeader;
	}

	public void setDocHeader(String docHeader) {
		this.docHeader = docHeader;
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

	public String getOemFrom() {
		return oemFrom;
	}

	public void setOemFrom(String oemFrom) {
		this.oemFrom = oemFrom;
	}

	public String getOemTo() {
		return oemTo;
	}

	public void setOemTo(String oemTo) {
		this.oemTo = oemTo;
	}

	public List<SAP_TpSenderDetailResponse> getListDetail() {
		return listDetail;
	}

	public void setListDetail(List<SAP_TpSenderDetailResponse> listDetail) {
		this.listDetail = listDetail;
	}

}
