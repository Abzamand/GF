package id.co.qualitas.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import id.co.qualitas.component.Cons;
import id.co.qualitas.component.Utils;
import id.co.qualitas.dao.interfaces.DeliveryOrderDao;
import id.co.qualitas.dao.interfaces.ShipmentDao;
import id.co.qualitas.domain.response.SAP_ShipmDODetailItemResponse;
import id.co.qualitas.domain.response.SAP_ShipmDODetailResponse;
import id.co.qualitas.domain.response.SAP_ShipmDOResponse;
import id.co.qualitas.domain.webservice.postoso.ZFMWBPOSTOSOResponse;
import id.co.qualitas.domain.webservice.shipmdo.TABLEOFZTYWBSHIPMDODETAILDO;
import id.co.qualitas.domain.webservice.shipmdo.TABLEOFZTYWBSHIPMDODETAILITEMDO;
import id.co.qualitas.domain.webservice.shipmdo.TABLEOFZTYWBSHIPMDOSHIPMENT;
import id.co.qualitas.domain.webservice.shipmdo.ZFMWBSHIPMDO;
import id.co.qualitas.domain.webservice.shipmdo.ZFMWBSHIPMDOResponse;
import id.co.qualitas.domain.webservice.shipmdo.ZTYWBSHIPMDODETAILDO;
import id.co.qualitas.domain.webservice.shipmdo.ZTYWBSHIPMDODETAILITEMDO;
import id.co.qualitas.domain.webservice.shipmdo.ZTYWBSHIPMDOSHIPMENT;
import id.co.qualitas.service.interfaces.SAP_ShipmDOService;

@Service
public class SAP_ShipmDOServiceImpl implements SAP_ShipmDOService {
	@Autowired
	ShipmentDao shipmentDao;
	@Autowired
	DeliveryOrderDao doDao;

	@Override
	public List<SAP_ShipmDOResponse> getList(String idSloc, String idPlant) {
		List<SAP_ShipmDOResponse> result = new ArrayList<SAP_ShipmDOResponse>();
		List<SAP_ShipmDOResponse> listHeader = new ArrayList<SAP_ShipmDOResponse>();
		List<SAP_ShipmDODetailResponse> listDetail = new ArrayList<SAP_ShipmDODetailResponse>();
		List<SAP_ShipmDODetailItemResponse> listitem = new ArrayList<SAP_ShipmDODetailItemResponse>();

		try {
			// request
			ZFMWBSHIPMDO request = new ZFMWBSHIPMDO();
			request.setSHIPMENT(new TABLEOFZTYWBSHIPMDOSHIPMENT());
			request.setDETAILDO(new TABLEOFZTYWBSHIPMDODETAILDO());
			request.setDETAILITEMDO(new TABLEOFZTYWBSHIPMDODETAILITEMDO());
			
			request.setPSIETD(Utils.getCurrentDate(Cons.DATE_FORMAT_1));
			request.setPLGORT(idSloc != null && idSloc.equals("null") ? null : idSloc);
			request.setPWERKS(idPlant);

			// response
			ZFMWBSHIPMDOResponse sapResponse = new ZFMWBSHIPMDOResponse();
			sapResponse = (ZFMWBSHIPMDOResponse) Utils.WebserviceResponse(request, Cons.CONTEXT_SHIPM_DO,
					Cons.ENDPOINT_SHIPM_DO);
//new Gson().toJson(sapResponse);
			if (sapResponse.getSHIPMENT() != null && sapResponse.getSHIPMENT().getItem() != null
					&& !sapResponse.getSHIPMENT().getItem().isEmpty()) {

				for (ZTYWBSHIPMDOSHIPMENT sapShipment : sapResponse.getSHIPMENT().getItem()) {
					SAP_ShipmDOResponse response = new SAP_ShipmDOResponse();
					
					if(shipmentDao.isShipmentNoCreated(sapShipment.getTKNUM(), idSloc, idPlant, Cons.TP_HEADER_STATUS_ALL)) {
						continue;
					}
					
					response.setShipmentNo(sapShipment.getTKNUM());
					response.setShipmentInstrNumber(sapShipment.getSINUMBER());
					response.setShipmentDate(sapShipment.getSIETD());
					response.setIdSloc(sapShipment.getLGORT());
					response.setIdPlant(sapShipment.getWERKS());
					response.setVendorForwarding(sapShipment.getTDLNR());
					response.setPlateNo(sapShipment.getTNDRTRKID());
					response.setVehicleType(sapShipment.getVSART());
					response.setDriver(sapShipment.getEXTI1());

					listHeader.add(response);
				}
			} else {
				System.out.println("Header from sap is null or empty");
			}

			if (sapResponse.getDETAILDO() != null && sapResponse.getDETAILDO().getItem() != null
					&& !sapResponse.getDETAILDO().getItem().isEmpty()) {

				for (ZTYWBSHIPMDODETAILDO sapDetail : sapResponse.getDETAILDO().getItem()) {
					SAP_ShipmDODetailResponse response = new SAP_ShipmDODetailResponse();
					response.setDoNo(sapDetail.getVBELN());
					response.setDoDate(sapDetail.getLFDAT());
					response.setIdSloc(sapDetail.getLGORT());
					response.setIdPlant(sapDetail.getWERKS());
					response.setShipmentNo(sapDetail.getTKNUM());
					response.setPoNo(sapDetail.getZDOCNO());
					response.setTotalWeight(sapDetail.getTWEIGHT());
					response.setUow(sapDetail.getWUOM());
					response.setTotalVolume(sapDetail.getTVOLUM());
					response.setUov(sapDetail.getVUOM());

					if(sapDetail.getVBELN() != null) {
						//update weight volume uow uov
						doDao.updateTotalVolumeWeightByDoNoSap(sapDetail.getVBELN(), sapDetail.getTVOLUM(), sapDetail.getVUOM(), sapDetail.getTWEIGHT(), sapDetail.getWUOM());
					}
					
					listDetail.add(response);
				}
			} else {
				System.out.println("Detail from sap is null or empty");
			}

			if (sapResponse.getDETAILITEMDO() != null && sapResponse.getDETAILITEMDO().getItem() != null
					&& !sapResponse.getDETAILITEMDO().getItem().isEmpty()) {

				for (ZTYWBSHIPMDODETAILITEMDO sapDItem : sapResponse.getDETAILITEMDO().getItem()) {
					SAP_ShipmDODetailItemResponse response = new SAP_ShipmDODetailItemResponse();
					response.setDoNo(sapDItem.getVBELN());
					response.setItemNr(Utils.trimLeadingZeroes(sapDItem.getPOSNR(),true));
					response.setHigherLevelItemBatch(Utils.trimLeadingZeroes(sapDItem.getUECHA(), true));
					response.setIdMaterial(Utils.trimLeadingZeroes(sapDItem.getMATNR()));
					response.setMaterialName(sapDItem.getARKTX());
					response.setBatchNo(sapDItem.getCHARG());
					response.setExpiredDate(sapDItem.getVFDAT());
					response.setOrderQty(sapDItem.getLFIMG());
					response.setUom(sapDItem.getVRKME());

					listitem.add(response);
				}
			} else {
				System.out.println("Component from sap is null or empty");
			}

			result = combineList(listHeader, listDetail, listitem);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private List<SAP_ShipmDOResponse> combineList(List<SAP_ShipmDOResponse> listHeader,
			List<SAP_ShipmDODetailResponse> listMaterial, List<SAP_ShipmDODetailItemResponse> listItem) {

		Map<String, SAP_ShipmDOResponse> headerMap = new HashMap<>(listHeader.size());
		for (SAP_ShipmDOResponse header : listHeader) {
			headerMap.put(header.getShipmentNo(), header);
		}


		Map<String, SAP_ShipmDODetailResponse> materialMap = new HashMap<>(listMaterial.size());
		for(SAP_ShipmDODetailResponse material : listMaterial) {
			String infix = material.getDoNo() != null && !material.getDoNo().isEmpty() ? "O" : "X";
			materialMap.put(material.getPoNo() + infix, material);
		}
		
		for (SAP_ShipmDODetailResponse detail : listMaterial) {
			
			if(detail.getDoNo() != null && detail.getDoNo().equals("") 
					&& materialMap.get(detail.getPoNo() + "O") != null) {
				continue;
			}
			
			SAP_ShipmDOResponse header = headerMap.get(detail.getShipmentNo());

			if (header != null) {
				if (header.getListDetail() == null) {
					header.setListDetail(new ArrayList<SAP_ShipmDODetailResponse>());
				}

				if(listItem != null && !listItem.isEmpty()) {
					for (SAP_ShipmDODetailItemResponse item : listItem) {
						if (detail.getDoNo().equals(item.getDoNo())) {
							if (detail.getListItem() == null) {
								detail.setListItem(new ArrayList<SAP_ShipmDODetailItemResponse>());
							}
							detail.getListItem().add(item);
						}
					}
				}
				

				header.getListDetail().add(detail);
			}
		}
		return new ArrayList<>(headerMap.values());
	}
}
