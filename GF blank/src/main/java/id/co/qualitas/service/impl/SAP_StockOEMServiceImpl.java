package id.co.qualitas.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import id.co.qualitas.component.Cons;
import id.co.qualitas.component.Utils;
import id.co.qualitas.dao.interfaces.TransferPostingConfirmDao;
import id.co.qualitas.dao.interfaces.UomConversionDao;
import id.co.qualitas.dao.interfaces.VendorDao;
import id.co.qualitas.domain.request.UomConversion;
import id.co.qualitas.domain.response.SAP_BatchNoStockResponse;
import id.co.qualitas.domain.response.SAP_StockOEMResponse;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.domain.webservice.stockoem.TABLEOFZTYWBOEM;
import id.co.qualitas.domain.webservice.stockoem.ZFMWBSTOCKOEM;
import id.co.qualitas.domain.webservice.stockoem.ZFMWBSTOCKOEMResponse;
import id.co.qualitas.domain.webservice.stockoem.ZTYWBOEM;
import id.co.qualitas.service.interfaces.SAP_StockOEMService;
import id.co.qualitas.service.interfaces.TransferPostingConfirmService;

@Service
public class SAP_StockOEMServiceImpl implements SAP_StockOEMService {
	@Autowired
	UomConversionDao uomConversionDao;
	
	@Autowired
	VendorDao vendorDao;
	
	@Autowired 
	TransferPostingConfirmDao tpostingConfirmDao;
	
	@Autowired
	TransferPostingConfirmService tpConfirmService;
	

//	@Override
//	public List<SAP_StockOEMResponse> getList(String materialCode, String materialType, String idSloc, String idPlant, String username) {
//		List<SAP_StockOEMResponse> result = new ArrayList<SAP_StockOEMResponse>();
//
//		try {
//			String idVendorSap = vendorDao.getIdVendorSapByUsername(username);
//			
//			ZFMWBSTOCKOEMResponse sapResponse = new ZFMWBSTOCKOEMResponse();
//			ZFMWBSTOCKOEM request = new ZFMWBSTOCKOEM();
//
//			request.setPLGORT(idSloc);
//			request.setPMATNR(Utils.addLeadingZeroes(materialCode, Cons.MAX_DIGIT_MATERIAL_NO_SAP));
//			request.setPMTART(materialType);
//			request.setPWERKS(idPlant);
//			request.setRESULT(new TABLEOFZTYWBOEM());
//
//			sapResponse = (ZFMWBSTOCKOEMResponse) Utils.WebserviceResponse(request, Cons.CONTEXT_STOCK_OEM,
//					Cons.WEBSERVICE_SAP + Cons.ENDPOINT_STOCK_OEM, Cons.USERNAME, Cons.PASSWORD);
//			
//			if(sapResponse.getRESULT().getItem() != null && !sapResponse.getRESULT().getItem().isEmpty()) {
//				String[] MATERIAL_TYPE_FOR_LBLAB = new String[] {"ZRAW", "ZPAC", "ZSFG"};
//				
//				for(ZTYWBOEM sapItem:sapResponse.getRESULT().getItem()) {
//					SAP_StockOEMResponse stockOem = new SAP_StockOEMResponse();
//					stockOem.setMaterialNo(Utils.trimLeadingZeroes(sapItem.getMATNR()));
//					stockOem.setMaterialDesc(sapItem.getMAKTX());
//					stockOem.setMaterialType(sapItem.getMTART());
//					stockOem.setBatchNo(sapItem.getCHARG());
//					
//					if(Arrays.asList(MATERIAL_TYPE_FOR_LBLAB).contains(sapItem.getMTART())) {
//						stockOem.setAvailableStock(sapItem.getLBLAB());
//					}else {
//						stockOem.setAvailableStock(sapItem.getCLABS());
//					}
//					stockOem.setUuStock(sapItem.getCLABS());
//					stockOem.setQiStock(sapItem.getCINSM());
//					stockOem.setBlockedStock(sapItem.getCSPEM());
//					stockOem.setStockProviderToVendor(sapItem.getLBLAB());
//					stockOem.setUom(sapItem.getMEINS());
//					result.add(stockOem);
//				}
//			}
//
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return result;
//	}
	
	@Override
	public List<SAP_StockOEMResponse> getList(String materialCode, String materialType, String idSloc, String idPlant, String username) {
		List<SAP_StockOEMResponse> result = new ArrayList<SAP_StockOEMResponse>();

		try {
			String idVendorSap = vendorDao.getIdVendorSapByUsername(username);
			
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
					SAP_StockOEMResponse stockOem = new SAP_StockOEMResponse();
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
					stockOem.setStockSap(sapItem.getLBLAB());
					stockOem.setUom(sapItem.getMEINS());
					stockOem.setUomSap(sapItem.getMEINS());
					stockOem.setProductionDate(sapItem.getHSDAT());
					stockOem.setExpiredDate(sapItem.getVFDAT());
					
					//start
					//Stock Provider To Vendor
					WSMessage msgConversion = uomConversionDao.getConversion(idVendorSap, stockOem.getMaterialNo(), stockOem.getStockSap(), stockOem.getUom());
					if(msgConversion.getIdMessage() == 1) {
						UomConversion convResult = (UomConversion)msgConversion.getResult();
						
						if(convResult != null) {
							stockOem.setStockOem(convResult.getQtyOem());
							stockOem.setUomOem(convResult.getUomOem());
							stockOem.setOemConversion(true);
							stockOem.setMultiplier(convResult.getMultiplier());
						}
					}else {
						System.out.println(idVendorSap + stockOem.getMaterialNo() + String.valueOf(stockOem.getStockSap()) + stockOem.getUomSap() + msgConversion.getMessage());
					}
					
					if(stockOem.getStockOem() != null) {
						stockOem.setStockProviderToVendor(stockOem.getStockOem());
						stockOem.setUomStockProviderToVendor(stockOem.getUomOem());
					}else {
						stockOem.setStockProviderToVendor(stockOem.getStockSap());
						stockOem.setUomStockProviderToVendor(stockOem.getUomSap());
					}
					
					// Available Stock
					WSMessage msgConversionAvail = uomConversionDao.getConversion(idVendorSap, stockOem.getMaterialNo(), stockOem.getAvailableStock(), stockOem.getUom());
					if(msgConversionAvail.getIdMessage() == 1) {
						UomConversion convResultAvail = (UomConversion)msgConversionAvail.getResult();
						stockOem.setStockOem(null);
						stockOem.setUomOem(null);
						if(convResultAvail != null) {
							stockOem.setStockOem(convResultAvail.getQtyOem());
							stockOem.setUomOem(convResultAvail.getUomOem());
							stockOem.setOemConversion(true);
							stockOem.setMultiplier(convResultAvail.getMultiplier());
						}
					}else {
						System.out.println(idVendorSap + stockOem.getMaterialNo() + String.valueOf(stockOem.getAvailableStock()) + stockOem.getUomSap() + msgConversion.getMessage());
					}
					
					if(stockOem.getStockOem() != null) {
						stockOem.setAvailableStock(stockOem.getStockOem());
						stockOem.setUomAvailableStock(stockOem.getUomOem());
					}else {
						stockOem.setAvailableStock(stockOem.getAvailableStock());
						stockOem.setUomAvailableStock(stockOem.getUomSap());
					}
					
					// Uu Stock
					WSMessage msgConversionUU = uomConversionDao.getConversion(idVendorSap, stockOem.getMaterialNo(), stockOem.getUuStock(), stockOem.getUom());
					if(msgConversionUU.getIdMessage() == 1) {
						UomConversion convResultUU = (UomConversion)msgConversionUU.getResult();
						stockOem.setStockOem(null);
						stockOem.setUomOem(null);
						if(convResultUU != null) {
							stockOem.setStockOem(convResultUU.getQtyOem());
							stockOem.setUomOem(convResultUU.getUomOem());
							stockOem.setOemConversion(true);
							stockOem.setMultiplier(convResultUU.getMultiplier());
						}
					}else {
						System.out.println(idVendorSap + stockOem.getMaterialNo() + String.valueOf(stockOem.getUuStock()) + stockOem.getUomSap() + msgConversion.getMessage());
					}
					
					if(stockOem.getStockOem() != null) {
						stockOem.setUuStock(stockOem.getStockOem());
						stockOem.setUomUuStock(stockOem.getUomOem());
					}else {
						stockOem.setUuStock(stockOem.getUuStock());
						stockOem.setUomUuStock(stockOem.getUomSap());
					}
					
					//QI Stock
					WSMessage msgConversionQI = uomConversionDao.getConversion(idVendorSap, stockOem.getMaterialNo(), stockOem.getQiStock(), stockOem.getUom());
					if(msgConversionQI.getIdMessage() == 1) {
						UomConversion convResultQI = (UomConversion)msgConversionQI.getResult();
						stockOem.setStockOem(null);
						stockOem.setUomOem(null);
						if(convResultQI != null) {
							stockOem.setStockOem(convResultQI.getQtyOem());
							stockOem.setUomOem(convResultQI.getUomOem());
							stockOem.setOemConversion(true);
							stockOem.setMultiplier(convResultQI.getMultiplier());
						}
					}else {
						System.out.println(idVendorSap + stockOem.getMaterialNo() + String.valueOf(stockOem.getQiStock()) + stockOem.getUomSap() + msgConversion.getMessage());
					}
					
					if(stockOem.getStockOem() != null) {
						stockOem.setQiStock(stockOem.getStockOem());
						stockOem.setUomQiStock(stockOem.getUomOem());
					}else {
						stockOem.setQiStock(stockOem.getQiStock());
						stockOem.setUomQiStock(stockOem.getUomSap());
					}
					
					//Blocked Stock
					WSMessage msgConversionBlock = uomConversionDao.getConversion(idVendorSap, stockOem.getMaterialNo(), stockOem.getBlockedStock(), stockOem.getUom());
					if(msgConversionBlock.getIdMessage() == 1) {
						UomConversion convResultBlock = (UomConversion)msgConversionBlock.getResult();
						stockOem.setStockOem(null);
						stockOem.setUomOem(null);
						if(convResultBlock != null) {
							stockOem.setStockOem(convResultBlock.getQtyOem());
							stockOem.setUomOem(convResultBlock.getUomOem());
							stockOem.setOemConversion(true);
							stockOem.setMultiplier(convResultBlock.getMultiplier());
						}
					}else {
						System.out.println(idVendorSap + stockOem.getMaterialNo() + String.valueOf(stockOem.getBlockedStock()) + stockOem.getUomSap() + msgConversion.getMessage());
					}
					
					if(stockOem.getStockOem() != null) {
						stockOem.setBlockedStock(stockOem.getStockOem());
						stockOem.setUomBlockedStock(stockOem.getUomOem());
					}else {
						stockOem.setBlockedStock(stockOem.getBlockedStock());
						stockOem.setUomBlockedStock(stockOem.getUomSap());
					}
					result.add(stockOem);
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public List<SAP_BatchNoStockResponse> getListBatchNoStock(String idSloc, String idPlant, String componentNo, String menu, String username) {
		List<SAP_BatchNoStockResponse> result = new ArrayList<SAP_BatchNoStockResponse>();

		try {
			String idVendorSap = vendorDao.getIdVendorSapByUsername(username);
			
			ZFMWBSTOCKOEMResponse sapResponse = new ZFMWBSTOCKOEMResponse();
			ZFMWBSTOCKOEM request = new ZFMWBSTOCKOEM();

			request.setPLGORT(idSloc);
			request.setPMATNR(Utils.addLeadingZeroes(componentNo, Cons.MAX_DIGIT_MATERIAL_NO_SAP));
			request.setPWERKS(idPlant);
			request.setRESULT(new TABLEOFZTYWBOEM());

			sapResponse = (ZFMWBSTOCKOEMResponse) Utils.WebserviceResponse(request, Cons.CONTEXT_STOCK_OEM,
					Cons.WEBSERVICE_SAP + Cons.ENDPOINT_STOCK_OEM, Cons.USERNAME, Cons.PASSWORD);
			
			if(sapResponse.getRESULT().getItem() != null && !sapResponse.getRESULT().getItem().isEmpty()) {
				String[] AVAILABLE_MATERIAL_TYPE = new String[] {};
				
				if(menu.equals(Cons.MENU_PROCESS)) {
					AVAILABLE_MATERIAL_TYPE = new String[] {"ZRAW", "ZPAC", "ZSFG"};
					
					for(ZTYWBOEM sapItem:sapResponse.getRESULT().getItem()) {
						if(sapItem.getLBLAB().compareTo(BigDecimal.ZERO) == 0 || !Arrays.asList(AVAILABLE_MATERIAL_TYPE).contains(sapItem.getMTART())) {//check if LBLAB is zero
							continue;
						}
						SAP_BatchNoStockResponse stock = new SAP_BatchNoStockResponse();
						stock.setBatchNo(sapItem.getCHARG());
						
						stock.setStockSap(sapItem.getLBLAB());
						stock.setUomSap(sapItem.getMEINS());
						
						WSMessage msgConversion = uomConversionDao.getConversion(idVendorSap, componentNo, stock.getStockSap(), stock.getUomSap());
						
						if(msgConversion.getIdMessage() == 1) {
							UomConversion convResult = (UomConversion)msgConversion.getResult();
							
							if(convResult != null) {
								stock.setBaseQtyOem(convResult.getBaseQtyOem());
								stock.setStockOem(convResult.getQtyOem());
								stock.setUomOem(convResult.getUomOem());
								stock.setOemConversion(true);
								stock.setMultiplier(convResult.getMultiplier());
							}else {
								stock.setBaseQtyOem(stock.getStockSap());
							}
						}else {
							System.out.println(idVendorSap + componentNo + String.valueOf(stock.getStockSap()) + stock.getUomSap() + msgConversion.getMessage());
						}
						
						if(stock.getStockOem() != null) {
							stock.setStock(stock.getStockOem());
							stock.setUom(stock.getUomOem());
						}else {
							stock.setStock(stock.getStockSap());
							stock.setUom(stock.getUomSap());
						}
						
						result.add(stock);
					}
				}else {
					AVAILABLE_MATERIAL_TYPE = new String[] {"ZFGS"};
					
					for(ZTYWBOEM sapItem:sapResponse.getRESULT().getItem()) {
						if(sapItem.getCLABS().compareTo(BigDecimal.ZERO) == 0 || !Arrays.asList(AVAILABLE_MATERIAL_TYPE).contains(sapItem.getMTART())) {//check if uu_stock/CLABS is zero
							continue;
						}
						SAP_BatchNoStockResponse stock = new SAP_BatchNoStockResponse();
						stock.setBatchNo(sapItem.getCHARG());
						stock.setStock(sapItem.getCLABS());
						stock.setUom(sapItem.getMEINS());
						
						result.add(stock);
					}
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public List<SAP_StockOEMResponse> getListVendorStock(String idSloc, String idPlant, String username) {
		List<SAP_StockOEMResponse> result = new ArrayList<SAP_StockOEMResponse>();

		//sync data terakhir tp confirm
//		tpConfirmService.syncToSAP();
		
		try {
			
			String idVendorSap = vendorDao.getIdVendorSapByUsername(username);
			
			ZFMWBSTOCKOEMResponse sapResponse = new ZFMWBSTOCKOEMResponse();
			ZFMWBSTOCKOEM request = new ZFMWBSTOCKOEM();

			request.setPLGORT(idSloc);
			request.setPWERKS(idPlant);
			request.setRESULT(new TABLEOFZTYWBOEM());

			sapResponse = (ZFMWBSTOCKOEMResponse) Utils.WebserviceResponse(request, Cons.CONTEXT_STOCK_OEM,
					Cons.WEBSERVICE_SAP + Cons.ENDPOINT_STOCK_OEM, Cons.USERNAME, Cons.PASSWORD);
			
			if(sapResponse.getRESULT().getItem() != null && !sapResponse.getRESULT().getItem().isEmpty()) {
				String[] MATERIAL_TYPE_FOR_VENDOR_STOCK = new String[] {"ZRAW", "ZPAC", "ZSFG"};
				new Gson().toJson(sapResponse);
				for(ZTYWBOEM sapItem:sapResponse.getRESULT().getItem()) {
					if(sapItem.getCLABS().compareTo(BigDecimal.ZERO) == 0 || !Arrays.asList(MATERIAL_TYPE_FOR_VENDOR_STOCK).contains(sapItem.getMTART())) {//check if uu_stock/CLABS is zero
						continue;
					}
					
//					if(!Utils.trimLeadingZeroes(sapItem.getMATNR()).equals("3000000513")) {
//						continue;
//					}
					
					if(tpostingConfirmDao.isVendorStockConfirmed(Utils.trimLeadingZeroes(sapItem.getMATNR()), sapItem.getCHARG())) {
						continue;
					}
					
					SAP_StockOEMResponse stockOem = new SAP_StockOEMResponse();
					stockOem.setMaterialNo(Utils.trimLeadingZeroes(sapItem.getMATNR()));
					stockOem.setMaterialDesc(sapItem.getMAKTX());
					stockOem.setMaterialType(sapItem.getMTART());
					stockOem.setBatchNo(sapItem.getCHARG());
					
//					stockOem.setUuStock(sapItem.getCLABS());
//					stockOem.setUom(sapItem.getMEINS());
					
					stockOem.setStockSap(sapItem.getCLABS());
					stockOem.setUomSap(sapItem.getMEINS());
					
					WSMessage msgConversion = uomConversionDao.getConversion(idVendorSap, stockOem.getMaterialNo(), stockOem.getStockSap(), stockOem.getUomSap());
					
					if(msgConversion.getIdMessage() == 1) {
						UomConversion convResult = (UomConversion)msgConversion.getResult();
						
						if(convResult != null) {
							stockOem.setBaseQtyOem(convResult.getBaseQtyOem());
							stockOem.setStockOem(convResult.getQtyOem());
							stockOem.setUomOem(convResult.getUomOem());
							stockOem.setOemConversion(true);
							stockOem.setMultiplier(convResult.getMultiplier());
						}else {
							stockOem.setBaseQtyOem(stockOem.getStockSap());
						}
					}else {
						System.out.println(idVendorSap + stockOem.getMaterialNo() + String.valueOf(stockOem.getStockSap()) + stockOem.getUomSap() + msgConversion.getMessage());
					}
					
					if(stockOem.getStockOem() != null) {
						stockOem.setUuStock(stockOem.getStockOem());
						stockOem.setUom(stockOem.getUomOem());
					}else {
						stockOem.setUuStock(stockOem.getStockSap());
						stockOem.setUom(stockOem.getUomSap());
					}
					result.add(stockOem);
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public List<SAP_StockOEMResponse> getListStockForDelivery(String idSloc, String idPlant, String materialNo) {
		List<SAP_StockOEMResponse> result = new ArrayList<SAP_StockOEMResponse>();

		try {
			ZFMWBSTOCKOEMResponse sapResponse = new ZFMWBSTOCKOEMResponse();
			ZFMWBSTOCKOEM request = new ZFMWBSTOCKOEM();

			request.setPLGORT(idSloc);
			request.setPMATNR(Utils.addLeadingZeroes(materialNo, Cons.MAX_DIGIT_MATERIAL_NO_SAP));
			request.setPWERKS(idPlant);
			request.setRESULT(new TABLEOFZTYWBOEM());

			sapResponse = (ZFMWBSTOCKOEMResponse) Utils.WebserviceResponse(request, Cons.CONTEXT_STOCK_OEM,
					Cons.WEBSERVICE_SAP + Cons.ENDPOINT_STOCK_OEM, Cons.USERNAME, Cons.PASSWORD);
			
			if(sapResponse.getRESULT().getItem() != null && !sapResponse.getRESULT().getItem().isEmpty()) {
				String[] MATERIAL_TYPE_FOR_BATCH_DELIVERY = new String[] {"ZFGS"};
				
				for(ZTYWBOEM sapItem:sapResponse.getRESULT().getItem()) {
					if(sapItem.getCLABS().compareTo(BigDecimal.ZERO) == 0 || !Arrays.asList(MATERIAL_TYPE_FOR_BATCH_DELIVERY).contains(sapItem.getMTART())) {//check if uu_stock/CLABS is zero
						continue;
					}
					
					SAP_StockOEMResponse stockOem = new SAP_StockOEMResponse();
					stockOem.setMaterialNo(Utils.trimLeadingZeroes(sapItem.getMATNR()));
					stockOem.setMaterialDesc(sapItem.getMAKTX());
					stockOem.setMaterialType(sapItem.getMTART());
					stockOem.setBatchNo(sapItem.getCHARG());
					stockOem.setUuStock(sapItem.getCLABS());
					stockOem.setUom(sapItem.getMEINS());
					result.add(stockOem);
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	

}
