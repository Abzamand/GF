package id.co.qualitas.domain.response;

import java.util.List;

import id.co.qualitas.domain.request.DODetail;
import id.co.qualitas.domain.request.DOHeader;

public class DOHistoryResponse extends DOHeader{
	private List<DODetail> listDetail;

	public DOHistoryResponse() {
	}

	public List<DODetail> getListDetail() {
		return listDetail;
	}

	public void setListDetail(List<DODetail> listDetail) {
		this.listDetail = listDetail;
	}

}
