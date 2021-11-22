package id.co.qualitas.domain.response;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class GoodReceiptFGResponse {
	private String docNo;
	private String docDate;
	private String docType;
	private String docHeader;
	private String idSloc;
	private String idPlant;
	
	private List<GoodReceiptFGMaterialResponse> listMaterial;
	
	public GoodReceiptFGResponse() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
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

	public List<GoodReceiptFGMaterialResponse> getListMaterial() {
		return listMaterial;
	}

	public void setListMaterial(List<GoodReceiptFGMaterialResponse> listMaterial) {
		this.listMaterial = listMaterial;
	}
	
}
