package id.co.qualitas.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.co.qualitas.component.Cons;
import id.co.qualitas.component.Utils;
import id.co.qualitas.dao.interfaces.GoodReceiptFGDao;
import id.co.qualitas.dao.interfaces.TransferPostingDao;
import id.co.qualitas.dao.interfaces.UomConversionDao;
import id.co.qualitas.dao.interfaces.VendorDao;
import id.co.qualitas.domain.request.UomConversion;
import id.co.qualitas.domain.response.SAP_TpSenderDetailResponse;
import id.co.qualitas.domain.response.SAP_TpSenderResponse;
import id.co.qualitas.domain.response.Vendor;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.domain.webservice.tpsender.TABLEOFZTYWBTPDETAIL;
import id.co.qualitas.domain.webservice.tpsender.TABLEOFZTYWBTPHEADER;
import id.co.qualitas.domain.webservice.tpsender.ZFMWBTPSENDER;
import id.co.qualitas.domain.webservice.tpsender.ZFMWBTPSENDERResponse;
import id.co.qualitas.domain.webservice.tpsender.ZTYWBTPDETAIL;
import id.co.qualitas.domain.webservice.tpsender.ZTYWBTPHEADER;
import id.co.qualitas.service.interfaces.SAP_TpSenderService;

@Service
public class SAP_TpSenderServiceImpl implements SAP_TpSenderService {

	@Autowired
	TransferPostingDao tpostingDao;

	@Autowired
	UomConversionDao uomConversionDao;
	
	@Autowired
	VendorDao vendorDao;
	
	@Override
	public List<SAP_TpSenderResponse> getList(String idSloc, String idPlant, String username) {
		List<SAP_TpSenderResponse> result = new ArrayList<SAP_TpSenderResponse>();
		List<SAP_TpSenderResponse> listHeader = new ArrayList<SAP_TpSenderResponse>();
		List<SAP_TpSenderDetailResponse> listDetail = new ArrayList<SAP_TpSenderDetailResponse>();
		
		try {

			String idVendorSap = vendorDao.getIdVendorSapByUsername(username);
			
			ZFMWBTPSENDER request = new ZFMWBTPSENDER();
			request.setHEADER(new TABLEOFZTYWBTPHEADER());
			request.setDETAIL(new TABLEOFZTYWBTPDETAIL());
			request.setPBUDAT(Utils.getCurrentDate(Cons.DATE_FORMAT_1));
//			request.setPBUDAT("2021");

			request.setPLGORT(idSloc != null && idSloc.equals("null") ? null : idSloc);
			request.setPWERKS(idPlant);
			
			ZFMWBTPSENDERResponse sapResponse = new ZFMWBTPSENDERResponse();
			sapResponse = (ZFMWBTPSENDERResponse) Utils.WebserviceResponse(request, Cons.CONTEXT_TP_SENDER,
					Cons.WEBSERVICE_SAP + Cons.ENDPOINT_TP_SENDER, Cons.USERNAME, Cons.PASSWORD);

			if (sapResponse.getHEADER() != null && sapResponse.getHEADER().getItem() != null
					&& !sapResponse.getHEADER().getItem().isEmpty()) {

				for (ZTYWBTPHEADER sapHeader : sapResponse.getHEADER().getItem()) {
					SAP_TpSenderResponse response = new SAP_TpSenderResponse();
					
					if(tpostingDao.isDocNoCreated(sapHeader.getMBLNR(), idSloc, idPlant, Cons.TP_HEADER_STATUS_ALL)) {
						continue;
					}
					
					response.setDocNo(sapHeader.getMBLNR());
					response.setDocDate(sapHeader.getBUDAT());
					response.setDocHeader(sapHeader.getBKTXT());
					response.setIdSloc(sapHeader.getLGORT());
					response.setIdPlant(sapHeader.getWERKS());
					response.setOemFrom(sapHeader.getLGORT());//ket. ambil dari oemfrom - vendor/sloc name
					response.setOemTo(sapHeader.getDESTSLOC());//ket. ambil dari oem to - sloc name
					
//					Vendor vendorOrigin = vendorDao.getVendorDetail(response.getOemFrom(), response.getIdPlant());

					Vendor vendorOrigin = vendorDao.getSlocDetail(response.getOemFrom(), response.getIdPlant());
					
					if(vendorOrigin != null) {
						response.setOriginIdPlant(vendorOrigin.getIdSloc());
						response.setOriginPlantName(vendorOrigin.getSlocName());
						response.setOriginAddress(vendorOrigin.getAddress());
						response.setOriginPhone(vendorOrigin.getPhone());
					}
					
					Vendor vendorDest = vendorDao.getSlocDetail(response.getOemTo(), response.getIdPlant());
					
					
					if(vendorDest != null) {
						response.setDestinationIdPlant(vendorDest.getIdSloc());
						response.setDestinationPlantName(vendorDest.getSlocName());
						response.setDestinationAddress(vendorDest.getAddress());
						response.setDestinationPhone(vendorDest.getPhone());
					}

					listHeader.add(response);
				}
			} else {
				System.out.println("Header from sap is null or empty");
			}

			if (sapResponse.getDETAIL() != null && sapResponse.getDETAIL().getItem() != null
					&& !sapResponse.getDETAIL().getItem().isEmpty()) {

				for (ZTYWBTPDETAIL sapDetail : sapResponse.getDETAIL().getItem()) {

					if(tpostingDao.isDocNoCreated(sapDetail.getMBLNR(), idSloc, idPlant, Cons.TP_HEADER_STATUS_ALL)) {
						continue;
					}
					
					SAP_TpSenderDetailResponse detail = new SAP_TpSenderDetailResponse();
					detail.setDocNo(sapDetail.getMBLNR());
					detail.setDocItemNo(Utils.trimLeadingZeroes(sapDetail.getLINEID(), true));
					detail.setMaterialNo(Utils.trimLeadingZeroes(sapDetail.getMATNR()));
					detail.setMaterialDesc(sapDetail.getMAKTX());
					detail.setBatchNo(sapDetail.getCHARG());
					
					detail.setStockSap(sapDetail.getMENGE());
					detail.setUomSap(sapDetail.getMEINS());
					
					WSMessage msgConversion = uomConversionDao.getConversion(idVendorSap, detail.getMaterialNo(), detail.getStockSap(), detail.getUomSap());
					
					if(msgConversion.getIdMessage() == 1) {
						UomConversion convResult = (UomConversion)msgConversion.getResult();
						
						if(convResult != null) {
							detail.setBaseQtyOem(convResult.getBaseQtyOem());
							detail.setStockOem(convResult.getQtyOem());
							detail.setUomOem(convResult.getUomOem());
							detail.setOemConversion(true);
							detail.setMultiplier(convResult.getMultiplier());
						}else {
							detail.setBaseQtyOem(detail.getStockSap());
						}
					}else {
						System.out.println(idVendorSap + detail.getMaterialNo() + String.valueOf(detail.getStockSap()) + detail.getUomSap() + msgConversion.getMessage());
					}
					
					if(detail.getStockOem() != null) {
						detail.setQty(detail.getStockOem());
						detail.setUom(detail.getUomOem());
					}else {
						detail.setQty(detail.getStockSap());
						detail.setUom(detail.getUomSap());
					}
					

					listDetail.add(detail);
				}
			} else {
				System.out.println("Detail from sap is null or empty");
			}

			if (listHeader != null && !listHeader.isEmpty() && listDetail != null && !listDetail.isEmpty()) {
				result = combineList(listHeader, listDetail);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}


	private List<SAP_TpSenderResponse> combineList(List<SAP_TpSenderResponse> listHeader,
			List<SAP_TpSenderDetailResponse> listDetail) {

		Map<String, SAP_TpSenderResponse> headerMap = new HashMap<>(listHeader.size());
		for (SAP_TpSenderResponse header : listHeader) {
			headerMap.put(header.getDocNo(), header);
		}

		for (SAP_TpSenderDetailResponse detail : listDetail) {
			SAP_TpSenderResponse header = headerMap.get(detail.getDocNo());

			if (header != null) {
				if (header.getListDetail() == null) {
					header.setListDetail(new ArrayList<SAP_TpSenderDetailResponse>());
				}

				header.getListDetail().add(detail);
			}
		}

		return new ArrayList<>(headerMap.values());
	}


}
