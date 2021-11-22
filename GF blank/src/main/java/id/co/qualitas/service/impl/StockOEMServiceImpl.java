package id.co.qualitas.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;

import id.co.qualitas.component.Cons;
import id.co.qualitas.component.Utils;
import id.co.qualitas.domain.response.BatchNoStockResponse;
import id.co.qualitas.domain.response.StockOEMResponse;
import id.co.qualitas.domain.webservice.stockoem.TABLEOFZTYWBOEM;
import id.co.qualitas.domain.webservice.stockoem.ZFMWBSTOCKOEM;
import id.co.qualitas.domain.webservice.stockoem.ZFMWBSTOCKOEMResponse;
import id.co.qualitas.domain.webservice.stockoem.ZTYWBOEM;
import id.co.qualitas.service.interfaces.StockOEMService;

@Service
public class StockOEMServiceImpl implements StockOEMService {
	

	@Override
	public List<StockOEMResponse> getList(String materialCode, String materialType, String idSloc, String idPlant) {
		List<StockOEMResponse> result = new ArrayList<StockOEMResponse>();

		try {
			ZFMWBSTOCKOEMResponse sapResponse = new ZFMWBSTOCKOEMResponse();
			ZFMWBSTOCKOEM request = new ZFMWBSTOCKOEM();

			request.setPLGORT(idSloc != null && idSloc.equals("null") ? null : idSloc);
			request.setPMATNR(Utils.addLeadingZeroes(materialCode, Cons.MAX_DIGIT_MATERIAL_NO_SAP));
			request.setPMTART(materialType);
			request.setPWERKS(idPlant);
			request.setRESULT(new TABLEOFZTYWBOEM());

			sapResponse = (ZFMWBSTOCKOEMResponse) Utils.WebserviceResponse(request, Cons.CONTEXT_STOCK_OEM,
					Cons.WEBSERVICE_SAP + Cons.ENDPOINT_STOCK_OEM, Cons.USERNAME, Cons.PASSWORD);
			
			if(sapResponse.getRESULT().getItem() != null && !sapResponse.getRESULT().getItem().isEmpty()) {
				String[] MATERIAL_TYPE_FOR_LBLAB = new String[] {"ZRAW", "ZPAC", "ZSFG"};
				
				for(ZTYWBOEM sapItem:sapResponse.getRESULT().getItem()) {
					StockOEMResponse stockOem = new StockOEMResponse();
					stockOem.setMaterialNo(Utils.trimLeadingZeroes(sapItem.getMATNR()));
					stockOem.setMaterialDesc(sapItem.getMAKTX());
					stockOem.setMaterialType(sapItem.getMTART());
					stockOem.setBatchNo(sapItem.getCHARG());
					if(Arrays.asList(MATERIAL_TYPE_FOR_LBLAB).contains(sapItem.getMTART())) {
						stockOem.setAvailableStock(sapItem.getLBLAB());
					}else {
						stockOem.setAvailableStock(sapItem.getCLABS());
					}
					stockOem.setUuStock(sapItem.getCLABS());
					stockOem.setQiStock(sapItem.getCINSM());
					stockOem.setBlockedStock(sapItem.getCSPEM());
					stockOem.setStockProviderToVendor(sapItem.getLBLAB());
					stockOem.setUom(sapItem.getMEINS());
					result.add(stockOem);
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public List<BatchNoStockResponse> getListBatchNoStock(String idSloc, String idPlant, String componentNo) {
		List<BatchNoStockResponse> result = new ArrayList<BatchNoStockResponse>();

		try {
			ZFMWBSTOCKOEMResponse sapResponse = new ZFMWBSTOCKOEMResponse();
			ZFMWBSTOCKOEM request = new ZFMWBSTOCKOEM();

			request.setPLGORT(idSloc);
			request.setPMATNR(Utils.addLeadingZeroes(componentNo, Cons.MAX_DIGIT_MATERIAL_NO_SAP));
			request.setPWERKS(idPlant);
			request.setRESULT(new TABLEOFZTYWBOEM());

			sapResponse = (ZFMWBSTOCKOEMResponse) Utils.WebserviceResponse(request, Cons.CONTEXT_STOCK_OEM,
					Cons.WEBSERVICE_SAP + Cons.ENDPOINT_STOCK_OEM, Cons.USERNAME, Cons.PASSWORD);
			
			if(sapResponse.getRESULT().getItem() != null && !sapResponse.getRESULT().getItem().isEmpty()) {
				
				for(ZTYWBOEM sapItem:sapResponse.getRESULT().getItem()) {
					BatchNoStockResponse stock = new BatchNoStockResponse();
					stock.setBatchNo(sapItem.getCHARG());
					stock.setStock(sapItem.getLBLAB());
					stock.setUom(sapItem.getMEINS());
					result.add(stock);
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	

}
