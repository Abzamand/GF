package id.co.qualitas.domain.request;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class GoodReceiptFGMaterialWithComponentsRequest {
	private GoodReceiptFGMaterialRequest material;
	private List<GoodReceiptFGComponentRequest> listComponent;

	public GoodReceiptFGMaterialWithComponentsRequest() {
	}

	public GoodReceiptFGMaterialRequest getMaterial() {
		return material;
	}

	public void setMaterial(GoodReceiptFGMaterialRequest material) {
		this.material = material;
	}

	public List<GoodReceiptFGComponentRequest> getListComponent() {
		return listComponent;
	}

	public void setListComponent(List<GoodReceiptFGComponentRequest> listComponent) {
		this.listComponent = listComponent;
	}
	
	
}
