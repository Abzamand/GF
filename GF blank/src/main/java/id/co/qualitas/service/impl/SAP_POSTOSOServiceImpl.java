package id.co.qualitas.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.data.rest.core.util.MapUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import id.co.qualitas.component.Cons;
import id.co.qualitas.component.Utils;
import id.co.qualitas.dao.interfaces.GoodReceiptFGDao;
import id.co.qualitas.dao.interfaces.TransferPostingConfirmDao;
import id.co.qualitas.domain.request.DOHeader;
import id.co.qualitas.domain.request.GoodReceiptFGRequest;
import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.response.SAP_GoodReceiptFGComponentResponse;
import id.co.qualitas.domain.response.SAP_GoodReceiptFGMaterialResponse;
import id.co.qualitas.domain.response.SAP_GoodReceiptFGResponse;
import id.co.qualitas.domain.response.SAP_POSTOSODetailResponse;
import id.co.qualitas.domain.response.SAP_POSTOSOResponse;
import id.co.qualitas.domain.response.SAP_StockOEMResponse;
import id.co.qualitas.domain.response.SAP_TransferPostingDetailResponse;
import id.co.qualitas.domain.response.SAP_TransferPostingResponse;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.domain.webservice.pofg.TABLEOFZTYWBPOFGCOMPONENT;
import id.co.qualitas.domain.webservice.pofg.TABLEOFZTYWBPOFGDETAIL;
import id.co.qualitas.domain.webservice.pofg.TABLEOFZTYWBPOFGHEADER;
import id.co.qualitas.domain.webservice.pofg.ZFMWBPOFG;
import id.co.qualitas.domain.webservice.pofg.ZFMWBPOFGResponse;
import id.co.qualitas.domain.webservice.pofg.ZTYWBPOFGCOMPONENT;
import id.co.qualitas.domain.webservice.pofg.ZTYWBPOFGDETAIL;
import id.co.qualitas.domain.webservice.pofg.ZTYWBPOFGHEADER;
import id.co.qualitas.domain.webservice.postoso.TABLEOFZTYWBPOSTOSODETAIL;
import id.co.qualitas.domain.webservice.postoso.TABLEOFZTYWBPOSTOSOHEADER;
import id.co.qualitas.domain.webservice.postoso.ZFMWBPOSTOSO;
import id.co.qualitas.domain.webservice.postoso.ZFMWBPOSTOSOResponse;
import id.co.qualitas.domain.webservice.postoso.ZTYWBPOSTOSODETAIL;
import id.co.qualitas.domain.webservice.postoso.ZTYWBPOSTOSOHEADER;
import id.co.qualitas.domain.webservice.stockoem.TABLEOFZTYWBOEM;
import id.co.qualitas.domain.webservice.stockoem.ZFMWBSTOCKOEM;
import id.co.qualitas.domain.webservice.stockoem.ZFMWBSTOCKOEMResponse;
import id.co.qualitas.domain.webservice.stockoem.ZTYWBOEM;
import id.co.qualitas.domain.webservice.tposting.TABLEOFZTYWBTPDETAIL;
import id.co.qualitas.domain.webservice.tposting.TABLEOFZTYWBTPHEADER;
import id.co.qualitas.domain.webservice.tposting.ZFMWBTP;
import id.co.qualitas.domain.webservice.tposting.ZFMWBTPResponse;
import id.co.qualitas.domain.webservice.tposting.ZTYWBTPDETAIL;
import id.co.qualitas.domain.webservice.tposting.ZTYWBTPHEADER;
import id.co.qualitas.service.interfaces.GoodReceiptFGService;
import id.co.qualitas.service.interfaces.SAP_POSTOSOService;
import id.co.qualitas.service.interfaces.TransferPostingConfirmService;

@Service
public class SAP_POSTOSOServiceImpl implements SAP_POSTOSOService {

	@Override
	public List<SAP_POSTOSOResponse> getList(String idSloc, String idPlant, String docType) {
		List<SAP_POSTOSOResponse> result = new ArrayList<SAP_POSTOSOResponse>();
		List<SAP_POSTOSOResponse> listHeader = new ArrayList<SAP_POSTOSOResponse>();
		List<SAP_POSTOSODetailResponse> listDetail = new ArrayList<SAP_POSTOSODetailResponse>();

		try {
			ZFMWBPOSTOSO request = new ZFMWBPOSTOSO();
			request.setDETAIL(new TABLEOFZTYWBPOSTOSODETAIL());
			request.setHEADER(new TABLEOFZTYWBPOSTOSOHEADER());
			request.setPBSART(docType);
			
			if(docType.equals(Cons.DOC_TYPE_STO) || docType.equals(Cons.DOC_TYPE_SO)) {
				request.setPSIETD(Utils.getCurrentDate(Cons.DATE_FORMAT_1));
				request.setPLGORT(idSloc != null && idSloc.equals("null") ? null : idSloc);
				request.setPWERKS(idPlant);
			}else {
				return null;
			}

			ZFMWBPOSTOSOResponse sapResponse = new ZFMWBPOSTOSOResponse();

			sapResponse = (ZFMWBPOSTOSOResponse) Utils.WebserviceResponse(request, Cons.CONTEXT_POSTOSO,
					Cons.WEBSERVICE_SAP + Cons.ENDPOINT_POSTOSO, Cons.USERNAME, Cons.PASSWORD);

			if (sapResponse.getHEADER() != null && sapResponse.getHEADER().getItem() != null
					&& !sapResponse.getHEADER().getItem().isEmpty()) {

				for (ZTYWBPOSTOSOHEADER sapHeader : sapResponse.getHEADER().getItem()) {
					SAP_POSTOSOResponse response = new SAP_POSTOSOResponse();

					response.setDocNo(sapHeader.getZDOCNO());
					response.setDocDate(sapHeader.getZEDAT());
					response.setIdSloc(sapHeader.getLGORT());
					response.setIdPlant(sapHeader.getWERKSSOURCE());
					response.setDestination(sapHeader.getJDADEST());
					response.setDestinationName(sapHeader.getDESTN());
					response.setDestinationAddress(sapHeader.getDADDR());
					response.setShipmentDate(sapHeader.getSIETD());
					response.setShipmentNo(sapHeader.getTKNUM());
					response.setPlateNo(sapHeader.getTNDRTRKID());
					response.setDriver(sapHeader.getEXTI1());
					response.setVendorExpedition(sapHeader.getTDLNR());
					response.setVendorExpeditionName(sapHeader.getVNAME());
					response.setJdaLoadId(sapHeader.getJDALOADI());
					response.setDocType(sapHeader.getBSART());
					response.setSiNo(sapHeader.getSINUMBER());
					response.setArrivalDate(sapHeader.getJDAARDAT());
					response.setVehicleNo(sapHeader.getVSART());
					response.setDestinationEmail(sapHeader.getEMAIL());
					response.setEstimatedDepartureDate(sapHeader.getJDASHDAT());
					response.setRoute(sapHeader.getROUTE());
					response.setDestinationName2(sapHeader.getDESTN2());
					response.setDestinationPhone(sapHeader.getTELF1());
					response.setPoNo(sapHeader.getREFDOC());

					listHeader.add(response);
				}
			} else {
				System.out.println("Header from sap is null or empty");
			}

			if (sapResponse.getDETAIL() != null && sapResponse.getDETAIL().getItem() != null
					&& !sapResponse.getDETAIL().getItem().isEmpty()) {

				for (ZTYWBPOSTOSODETAIL sapDetail : sapResponse.getDETAIL().getItem()) {
					SAP_POSTOSODetailResponse detail = new SAP_POSTOSODetailResponse();
					detail.setDocNo(sapDetail.getZDOCNO());
					detail.setDocItemNo(Utils.trimLeadingZeroes(sapDetail.getPOSNR(), true));
					detail.setMaterialNo(Utils.trimLeadingZeroes(sapDetail.getMATNR()));
					detail.setMaterialDesc(sapDetail.getTXZ01());
					detail.setOrderQty(sapDetail.getMENGE());
					detail.setOutstandingQty(sapDetail.getZPJQTY());
					detail.setUom(sapDetail.getMEINS());
					detail.setShipmentNo(sapDetail.getTKNUM());

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

	private List<SAP_POSTOSOResponse> combineList(List<SAP_POSTOSOResponse> listHeader,
			List<SAP_POSTOSODetailResponse> listDetail) {

		Map<String, SAP_POSTOSOResponse> headerMap = new HashMap<>(listHeader.size());
		for (SAP_POSTOSOResponse header : listHeader) {
			String docNo = header.getDocNo() != null ? header.getDocNo() : "";
			String shipmentNo = header.getShipmentNo() != null ? header.getShipmentNo() : "";
			headerMap.put(docNo + shipmentNo, header);
		}

		for (SAP_POSTOSODetailResponse detail : listDetail) {
			String docNo = detail.getDocNo() != null ? detail.getDocNo() : "";
			String shipmentNo = detail.getShipmentNo() != null ? detail.getShipmentNo() : "";
			SAP_POSTOSOResponse header = headerMap.get(docNo + shipmentNo);

			if (header != null) {
				if (header.getListDetail() == null) {
					header.setListDetail(new ArrayList<SAP_POSTOSODetailResponse>());
				}

				header.getListDetail().add(detail);
			}
		}

		return new ArrayList<>(headerMap.values());
	}

}
