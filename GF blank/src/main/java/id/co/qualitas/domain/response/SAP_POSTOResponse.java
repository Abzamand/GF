package id.co.qualitas.domain.response;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SAP_POSTOResponse {
	private String docNo; //HEADER-ZDOCNO
	private String docDate; //HEADER-AEDAT
	private String idSloc; //HEADER-LGORT
	private String idPlant; //HEADER-WERKS
	private String plantName;
	
	private List<SAP_POSTODetailResponse> listDo;

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
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

	public List<SAP_POSTODetailResponse> getListDo() {
		return listDo;
	}

	public void setListDo(List<SAP_POSTODetailResponse> listDo) {
		this.listDo = listDo;
	}


	
}
