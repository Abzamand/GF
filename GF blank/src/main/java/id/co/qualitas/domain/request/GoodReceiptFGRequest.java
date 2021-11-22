package id.co.qualitas.domain.request;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class GoodReceiptFGRequest {
	private GoodReceiptFGHeaderRequest header;
	private List<GoodReceiptFGMaterialWithComponentsRequest> listMaterialWithComponents;
	private String createdBy;
	
	public GoodReceiptFGRequest() {
	}

	public GoodReceiptFGHeaderRequest getHeader() {
		return header;
	}

	public void setHeader(GoodReceiptFGHeaderRequest header) {
		this.header = header;
	}

	public List<GoodReceiptFGMaterialWithComponentsRequest> getListMaterialWithComponents() {
		return listMaterialWithComponents;
	}

	public void setListMaterialWithComponents(List<GoodReceiptFGMaterialWithComponentsRequest> listMaterialWithComponents) {
		this.listMaterialWithComponents = listMaterialWithComponents;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
}
