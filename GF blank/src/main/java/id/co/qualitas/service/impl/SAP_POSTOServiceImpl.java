package id.co.qualitas.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import id.co.qualitas.component.Cons;
import id.co.qualitas.component.Utils;
import id.co.qualitas.dao.interfaces.DeliveryOrderDao;
import id.co.qualitas.dao.interfaces.GoodReceiptSTODao;
import id.co.qualitas.dao.interfaces.MasterDataDao;
import id.co.qualitas.domain.response.SAP_POSTODetailResponse;
import id.co.qualitas.domain.response.SAP_POSTOResponse;
import id.co.qualitas.domain.webservice.posto.TABLEOFZTYWBPOSTODETAIL;
import id.co.qualitas.domain.webservice.posto.TABLEOFZTYWBPOSTOHEADER;
import id.co.qualitas.domain.webservice.posto.ZFMWBPOSTO;
import id.co.qualitas.domain.webservice.posto.ZFMWBPOSTOResponse;
import id.co.qualitas.domain.webservice.posto.ZTYWBPOSTODETAIL;
import id.co.qualitas.domain.webservice.posto.ZTYWBPOSTOHEADER;
import id.co.qualitas.service.interfaces.SAP_POSTOService;

@Service
public class SAP_POSTOServiceImpl implements SAP_POSTOService {

	@Autowired
	DeliveryOrderDao doDao;
	
	@Autowired
	GoodReceiptSTODao grStoDao;
	
	@Autowired
	MasterDataDao masterDataDao;
	
	
	@Override
	public List<SAP_POSTODetailResponse> getList(String idSloc, String idPlant) {
		List<SAP_POSTODetailResponse> result = new ArrayList<SAP_POSTODetailResponse>();
		List<SAP_POSTOResponse> afterResult = new ArrayList<SAP_POSTOResponse>();
		List<SAP_POSTOResponse> listHeader = new ArrayList<SAP_POSTOResponse>();
		List<SAP_POSTODetailResponse> listDetail = new ArrayList<SAP_POSTODetailResponse>();

		try {
			ZFMWBPOSTO request = new ZFMWBPOSTO();
			request.setHEADER(new TABLEOFZTYWBPOSTOHEADER());
			request.setDETAIL(new TABLEOFZTYWBPOSTODETAIL());
			request.setPAEDAT(Utils.getCurrentDate(Cons.DATE_FORMAT_1));
			request.setPLGORT(idSloc != null && idSloc.equals("null") ? null : idSloc);
			request.setPWERKS(idPlant);
			
			ZFMWBPOSTOResponse sapResponse = new ZFMWBPOSTOResponse();
			sapResponse = (ZFMWBPOSTOResponse) Utils.WebserviceResponse(request, Cons.CONTEXT_PO_STO,
					Cons.WEBSERVICE_SAP + Cons.ENDPOINT_PO_STO, Cons.USERNAME, Cons.PASSWORD);
			new Gson().toJson(sapResponse);
			if (sapResponse.getHEADER() != null && sapResponse.getHEADER().getItem() != null
					&& !sapResponse.getHEADER().getItem().isEmpty()) {

				for (ZTYWBPOSTOHEADER sapHeader : sapResponse.getHEADER().getItem()) {
					SAP_POSTOResponse response = new SAP_POSTOResponse();
					
					response.setDocNo(sapHeader.getZDOCNO());
					response.setDocDate(sapHeader.getAEDAT());
					response.setIdSloc(sapHeader.getLGORT());
					response.setIdPlant(sapHeader.getWERKS());
					response.setPlantName(masterDataDao.getPlantName(response.getIdPlant()));

					listHeader.add(response);
				}
			} else {
				System.out.println("Header from sap is null or empty");
			}

			if (sapResponse.getDETAIL() != null && sapResponse.getDETAIL().getItem() != null
					&& !sapResponse.getDETAIL().getItem().isEmpty()) {

				for (ZTYWBPOSTODETAIL sapDetail : sapResponse.getDETAIL().getItem()) {
					SAP_POSTODetailResponse detail = new SAP_POSTODetailResponse();
					
					detail.setDocNo(sapDetail.getZDOCNO());
					detail.setDocItemNo(Utils.trimLeadingZeroes(sapDetail.getPOSNR(), true));
					detail.setMaterialNo(Utils.trimLeadingZeroes(sapDetail.getMATNR()));
					detail.setMaterialDesc(sapDetail.getTXZ01());
					detail.setOrderQty(sapDetail.getMENGE());
					detail.setDoNo(sapDetail.getVBELN());
					detail.setDoItemNo(Utils.trimLeadingZeroes(sapDetail.getZPOSNR(), true));
					detail.setHigherLevelItemBatch(Utils.trimLeadingZeroes(sapDetail.getUECHA(), true));
					detail.setBatchNo(sapDetail.getCHARG());
					detail.setExpDate(sapDetail.getVFDAT());
					detail.setDoQty(sapDetail.getLFIMG());
					detail.setUom(sapDetail.getVRKME());
					detail.setPgiNo(sapDetail.getMBLNR());
					detail.setPgiDate(sapDetail.getBUDAT());
					
					if(sapDetail.getMBLNR() != null) {
						//todo update pgi no sap by do no
						doDao.updateStatusPodByDoNoSap(sapDetail.getVBELN(), sapDetail.getMBLNR());
					}

					listDetail.add(detail);
				}
			} else {
				System.out.println("Detail from sap is null or empty");
			}

			if (listHeader != null && !listHeader.isEmpty() && listDetail != null && !listDetail.isEmpty()) {
				result = combineList(listHeader, listDetail);
				
//				if(result != null && !result.isEmpty()) {
//					for(int i = 0; i < result.size(); i++) {
//						if(result.get(i).getListDo() != null && !result.get(i).getListDo().isEmpty()) {
//							afterResult.add(result.get(i));
//						}
//					}
//				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	private List<SAP_POSTODetailResponse> mappingList(ArrayList<SAP_POSTODetailResponse> mData) {
        Map<String, SAP_POSTODetailResponse> mapping = new LinkedHashMap<>();
        
        ArrayList<SAP_POSTODetailResponse> newList = new ArrayList<SAP_POSTODetailResponse>();
        for (SAP_POSTODetailResponse item : mData) {
        	
            String doNo = item.getDoNo();
            SAP_POSTODetailResponse other = mapping.get(doNo);
            
            if (other == null) {
                newList = new ArrayList<>();
                other = new SAP_POSTODetailResponse(doNo, item.getDocNo(), item.getDoDate());
                other.setBatchNo(item.getBatchNo());
                other.setDocItemNo(item.getDocItemNo());
                other.setDocNo(item.getDocNo());
                other.setDoDate(item.getDoDate());
                other.setDoItemNo(item.getDoItemNo());
                other.setDoNo(item.getDoNo());
                other.setDoQty(item.getDoQty());
                other.setExpDate(item.getExpDate());
                other.setHigherLevelItemBatch(item.getHigherLevelItemBatch());
                other.setMaterialDesc(item.getMaterialDesc());
                other.setMaterialNo(item.getMaterialNo());
                other.setOrderQty(item.getOrderQty());
                other.setPgiNoSap(item.getPgiNoSap());
                other.setUom(item.getUom());
                other.setPgiNo(item.getPgiNo());
                other.setPgiDate(item.getPgiDate());
                mapping.put(doNo, other);
            }

			if(!grStoDao.isDoItemNoCreated(item.getDoNo(), Integer.parseInt(item.getDoItemNo()))) {
	            newList.add(item);
			}
            other.setListItem(newList);
        }

         return new ArrayList<>(mapping.values());
    }

	private List<SAP_POSTODetailResponse> combineList(List<SAP_POSTOResponse> listHeader,
			List<SAP_POSTODetailResponse> listDetail) {

		if(listHeader == null || listHeader.isEmpty() || listDetail == null || listDetail.isEmpty()) {
			return null;
		}
		
		List<SAP_POSTODetailResponse> listDetailAndItems = mappingList((ArrayList<SAP_POSTODetailResponse>) listDetail);

		Map<String, SAP_POSTOResponse> headerMap = new HashMap<>(listHeader.size());
		for (SAP_POSTOResponse header : listHeader) {
			
			headerMap.put(header.getDocNo(), header);
		}
		
		List<SAP_POSTODetailResponse> result = new ArrayList<SAP_POSTODetailResponse>();
		for (SAP_POSTODetailResponse detail : listDetailAndItems) {
				// kalo list item si do index sekian nya cuma 1 ( isinya higherlevelitem 00)
				//hapus list do di index itu
				if(!(detail.getListItem() != null && 
						detail.getListItem().size() == 1 && 
						detail.getListItem().get(0).getHigherLevelItemBatch() != null && 
						detail.getListItem().get(0).getHigherLevelItemBatch().equals("00"))) {
		
					detail.setDocDate(headerMap.get(detail.getDocNo()).getDocDate());
					detail.setIdSloc(headerMap.get(detail.getDocNo()).getIdSloc());
					detail.setIdPlant(headerMap.get(detail.getDocNo()).getIdPlant());
					detail.setPlantName(headerMap.get(detail.getDocNo()).getPlantName());
					result.add(detail);
				}
		}
		

		return result;

	}

}
