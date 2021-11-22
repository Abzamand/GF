package id.co.qualitas.domain.response;

import java.util.List;

public class SAP_TransferPostingResponse {
	private String docNo;
	private String docDate;
	private String docHeader;
	private String idSloc;
	private String idPlant;
	
	private List<SAP_TransferPostingDetailResponse> listDetail;
	
	public SAP_TransferPostingResponse() {
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

	public List<SAP_TransferPostingDetailResponse> getListDetail() {
		return listDetail;
	}

	public void setListDetail(List<SAP_TransferPostingDetailResponse> listDetail) {
		this.listDetail = listDetail;
	}
	
	
}
