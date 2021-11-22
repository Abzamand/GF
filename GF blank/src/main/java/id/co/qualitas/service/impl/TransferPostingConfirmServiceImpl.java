package id.co.qualitas.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import id.co.qualitas.dao.interfaces.TransferPostingConfirmDao;
import id.co.qualitas.dao.interfaces.TransferPostingDao;
import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.response.SAP_GoodReceiptFGResponse;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.domain.webservice.goodsmvt.Bapi2017GmCode;
import id.co.qualitas.domain.webservice.goodsmvt.Bapi2017GmHead01;
import id.co.qualitas.domain.webservice.goodsmvt.Bapi2017GmItemCreate;
import id.co.qualitas.domain.webservice.goodsmvt.SpeBapi2017GmRefEwm;
import id.co.qualitas.domain.webservice.goodsmvt.TableOfBapi2017GmItemCreate;
import id.co.qualitas.domain.webservice.goodsmvt.TableOfBapi2017GmSerialnumber;
import id.co.qualitas.domain.webservice.goodsmvt.TableOfBapiparex;
import id.co.qualitas.domain.webservice.goodsmvt.TableOfBapiret2;
import id.co.qualitas.domain.webservice.goodsmvt.TableOfSpeBapi2017ServicepartData;
import id.co.qualitas.domain.webservice.goodsmvt.ZfmWbGoodsmvt;
import id.co.qualitas.domain.webservice.goodsmvt.ZfmWbGoodsmvtResponse;
import id.co.qualitas.domain.webservice.tposting.TABLEOFZTYWBTPDETAIL;
import id.co.qualitas.domain.webservice.tposting.TABLEOFZTYWBTPHEADER;
import id.co.qualitas.domain.webservice.tposting.ZFMWBTP;
import id.co.qualitas.domain.webservice.tposting.ZFMWBTPResponse;
import id.co.qualitas.service.interfaces.TransferPostingConfirmService;

@Service
public class TransferPostingConfirmServiceImpl implements TransferPostingConfirmService {
	@Autowired
	TransferPostingConfirmDao transferPostingDao;
	
	@Autowired
	TransferPostingDao tPostingDao;

	@Override
	public Map<String, Object> getList(String idSloc, String idPlant) {
		ZFMWBTP request = new ZFMWBTP();

		request.setDETAIL(new TABLEOFZTYWBTPDETAIL());
		request.setHEADER(new TABLEOFZTYWBTPHEADER());

//		request.setPBUDAT("202012");// tanggal yyyy-mm
		request.setPBUDAT(Utils.getCurrentDate(Cons.DATE_FORMAT_1));

		request.setPLGORT(idSloc != null && idSloc.equals("null") ? null : idSloc);
		request.setPWERKS(idPlant);// plant

		ZFMWBTPResponse response = new ZFMWBTPResponse();
		boolean result = true;
		try {
			response = (ZFMWBTPResponse) Utils.WebserviceResponse(request, Cons.CONTEXT_TPOSTING,
					Cons.WEBSERVICE_SAP + Cons.ENDPOINT_TPOSTING, Cons.USERNAME, Cons.PASSWORD);

		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return null;
	}

	@Override
	public WSMessage confirm(TransferPostingConfirmRequest request) {
		WSMessage message = new WSMessage();
		
		if (request.getHeader() != null ) {
			if(request.getHeader().getDocNo() != null && request.getListDetail() != null && !request.getListDetail().isEmpty()) {
				for(int i = 0; i < request.getListDetail().size(); i++) {
					try {
						if(request.getListDetail().get(i).getDocItemNo() != null) {
							if(tPostingDao.isDocNoAndDocItemNoCreated(request.getHeader().getDocNo(), Integer.parseInt(request.getListDetail().get(i).getDocItemNo()))) {
								message.setIdMessage(2);
								message.setMessage("Some document item(s) has already confirmed. \nRefreshing the data..");
					    		
								return message;
					    	}
						}
					}catch(Exception e) {
						
					}
				}
			}else if(request.getListDetail()!= null && !request.getListDetail().isEmpty()) {
				for(int i = 0; i < request.getListDetail().size(); i++) {
					if(transferPostingDao.isVendorStockConfirmed(request.getListDetail().get(i).getMaterialNo(), request.getListDetail().get(i).getBatchNo())) {
						message.setIdMessage(2);
						message.setMessage("Some document item(s) has already confirmed. \nRefreshing the data..");
			    		
						return message;
					}
				}
				
			}
		}

		if (request.getHeader() == null) {
			message.setMessage("No request detected");
		} else if (request.getListDetail() == null || request.getListDetail().isEmpty()) {
			message.setMessage("This request doesn't have any detail");
		} else if (request.getHeader().getDocNo() != null && tPostingDao.isAllDetailAreConfirmed(request.getHeader().getDocNo())){
			message.setIdMessage(2);
			message.setMessage("This document number has already confirmed");
		}else {
			message = transferPostingDao.confirm(request);
		}
		
		return message;
	}
	
	

	@Override
	public WSMessage syncToSAP() {
		WSMessage result = new WSMessage();

		List<Map<String, String>> listHeader = new ArrayList<Map<String, String>>();
		listHeader = transferPostingDao.getListHeaderForSync(); //get list header yang stock confirm no sap null

		if (listHeader != null && !listHeader.isEmpty()) {
			String message = "";

			for (int i = 0; i < listHeader.size(); i++) {//start looping
				Map<String, String> header = listHeader.get(i);

				// 1. send to sap, success? update tpconfirm_no_sap, doc_type -> 541
				// 2. send to sap lagi dengan doc_type baru, success? update
				// stockconfirm_no_sap, update status
				// failed? update status dan error_message

				String idHeader = null;

				if (header.get("id") != null) {
					idHeader = Utils.validateToString(header.get("id"));
					
					String messageIndexPrefix = "\r\n" + (i+1) + ". " + idHeader + " ";

					if (header.get("docType") != null) {
						if (header.get("docType").toString().equals(Cons.TP_CONFIRM_DOC_TYPE)) {//case transfer posting confirm

							WSMessage messageAfterSyncTpConf = requestCallZFMWBGOODSMVT(//1st step sync tp_conf
									mappingZfmWbGoodsmvt(header, Cons.TP_CONFIRM_DOC_TYPE), idHeader, 1);

							if (messageAfterSyncTpConf.getIdMessage() == 1) {//success sync tp_conf
								WSMessage messageAfterSyncVsConf = requestCallZFMWBGOODSMVT(//request 2nd step after success sync tp_conf -> sync vs_conf
										mappingZfmWbGoodsmvt(header, Cons.VENDOR_CONFIRM_DOC_TYPE), idHeader, 2);
								
								if(messageAfterSyncVsConf.getIdMessage() == 1) {//success sync vs_conf
									message = message.concat(messageIndexPrefix + " sync vs_confirm succeed");
								}else {//failed sync vs_conf
									message = message.concat(messageIndexPrefix + messageAfterSyncVsConf.getMessage());
								}
							} else {//failed sync tp_conf
								message = message.concat(messageIndexPrefix + messageAfterSyncTpConf.getMessage());
							}

						} else if (header.get("docType").toString().equals(Cons.VENDOR_CONFIRM_DOC_TYPE)) {//case vendor confirm
							// send to sap, success? update stockconfirm_no_sap
							WSMessage messageAfterSyncVsConf = requestCallZFMWBGOODSMVT(//langsung request 2nd step after success sync tp_conf -> sync vs_conf
									mappingZfmWbGoodsmvt(header, Cons.VENDOR_CONFIRM_DOC_TYPE), idHeader, 2);

							if(messageAfterSyncVsConf.getIdMessage() == 1) {//success sync vs_conf
								message = message.concat(messageIndexPrefix + " sync vs_confirm succeed");
							}else {//failed sync vs_conf
								message = message.concat(messageIndexPrefix + messageAfterSyncVsConf.getMessage());
							}
						} else {//case invalid doc type, doc_type isn't 315 nor 541
							message = message.concat(messageIndexPrefix + " invalid doc type, isn't 315 or 541");
						}
					} else {// invalid doc type, doc_type is null
						message = message.concat(messageIndexPrefix + " invalid doc type, null");
					}
				}
			}//end looping

			result.setIdMessage(1);
			result.setMessage(message);

		} else {//error no list header
			result.setIdMessage(0);
			result.setMessage("There is no list tp_confirm_header to sync "
					+ "(Query is looking for stockconfirm_no_sap that still null)");
		}

		return result;
	}

	private ZfmWbGoodsmvt mappingZfmWbGoodsmvt(Map<String, String> header, String moveType) {
		// mapping header from database to sap model
		Bapi2017GmHead01 requestHeader = new Bapi2017GmHead01();
		requestHeader.setDocDate(Utils.validateToString(header.get("docDate")));
//		requestHeader.setDocDate("2020-12-31");//TODO for testing purpose only
		requestHeader.setHeaderTxt(Utils.validateToString(header.get("headerTxt")));
		requestHeader.setPstngDate(Utils.validateToString(header.get("pstngDate")));
//		requestHeader.setPstngDate("2020-12-31");//TODO for testing purpose only

		if(moveType.equals(Cons.TP_CONFIRM_DOC_TYPE)) {
			requestHeader.setRefDocNo(Utils.validateToString(header.get("refDocNo")));
		}

		// mapping gm code to sap model
		Bapi2017GmCode requestGmCode = new Bapi2017GmCode();
		requestGmCode.setGmCode(Cons.TP_CONFIRM_DEFAULT_GM_CODE);

		// sap model for details and components
		TableOfBapi2017GmItemCreate requestListItem = new TableOfBapi2017GmItemCreate();

		String idHeader = Utils.validateToString(header.get("id"));

		List<Map<String, Object>> listDetail = new ArrayList<Map<String, Object>>();
		listDetail = transferPostingDao.getListDetailBy(idHeader);

		if (listDetail != null && !listDetail.isEmpty()) {
			for (int i = 0; i < listDetail.size(); i++) {
				Map<String, Object> detailFromDB = listDetail.get(i);

				// mapping data detail from database to sap model
				Bapi2017GmItemCreate requestDetail = new Bapi2017GmItemCreate();
				if (detailFromDB.get("material") != null) {
					String leadingMaterial = Utils.addLeadingZeroes(detailFromDB.get("material").toString(),
							Cons.MAX_DIGIT_MATERIAL_NO_SAP);

					requestDetail.setMaterial(leadingMaterial);
				}
				requestDetail.setEntryQnt(
						detailFromDB.get("entryQnt") != null ? new BigDecimal(detailFromDB.get("entryQnt").toString())
								: null);
				requestDetail.setMoveType(moveType);
				
				requestDetail.setEntryUom(Utils.validateToString(detailFromDB.get("entryUom")));
				requestDetail.setBatch(Utils.validateToString(detailFromDB.get("batch")));
				requestDetail.setStgeLoc(Utils.validateToString(detailFromDB.get("stgeLoc")));
				requestDetail.setPlant(Utils.validateToString(detailFromDB.get("plant")));
				if(moveType.equals(Cons.VENDOR_CONFIRM_DOC_TYPE)) {

					if(header.get("vendor") != null) {
						String leadingven = Utils.addLeadingZeroes(header.get("vendor").toString(),
								10);
						requestDetail.setVendor(leadingven);
					}
				}

				// add content list item to TableOfBapi2017GmItemCreate requestListItem
				requestListItem.getItem().add(requestDetail);
			}
		}

		// mapping request
		ZfmWbGoodsmvt request = new ZfmWbGoodsmvt();
		request.setExtensionin(new TableOfBapiparex());
		request.setGoodsmvtCode(requestGmCode);
		request.setGoodsmvtHeader(requestHeader);
		request.setGoodsmvtItem(requestListItem);
		request.setGoodsmvtRefEwm(new SpeBapi2017GmRefEwm());
		request.setGoodsmvtSerialnumber(new TableOfBapi2017GmSerialnumber());
		request.setGoodsmvtServPartData(new TableOfSpeBapi2017ServicepartData());
		request.setReturn(new TableOfBapiret2());
		request.setTestrun(null);
		new Gson().toJson(request);
		return request;
	}

	private WSMessage requestCallZFMWBGOODSMVT(ZfmWbGoodsmvt request, String idHeader, int step) {
		new Gson().toJson(request);
		WSMessage result = new WSMessage();
		try {
			ZfmWbGoodsmvtResponse sapResponse = new ZfmWbGoodsmvtResponse();
			sapResponse = (ZfmWbGoodsmvtResponse) Utils.WebserviceResponse(request, Cons.CONTEXT_GOOD_SMVT,
					Cons.WEBSERVICE_SAP + Cons.ENDPOINT_GOOD_SMVT, Cons.USERNAME, Cons.PASSWORD);
			if (sapResponse != null) {
				// Sukses (S) kl sukses harusnya returnnya kosong, matdocnya ada isinya
				// Error (E) kl error harusnya returnnya isi, matdocnya yang kosong
				if (sapResponse.getMaterialdocument() != null && !sapResponse.getMaterialdocument().isEmpty()) {// success

					if (step == 1) {
						// 1. send to sap, success? update tpconfirm_no_sap, doc_type -> 541
						transferPostingDao.doUpdateAfter1stStep(idHeader, sapResponse.getMaterialdocument(),
								"Sync tp_conf succeed, sync vs_conf on progress", null, Cons.VENDOR_CONFIRM_DOC_TYPE);
						
						result.setIdMessage(1);
					}else if(step == 2) {
						// 2. send to sap lagi dengan doc_type baru, success? update
						transferPostingDao.doUpdateAfter2ndStep(idHeader, sapResponse.getMaterialdocument(),
								"Sync tp_vs_conf succeed", "Completed");
						result.setIdMessage(1);
						result.setMessage(" sync vs_confirm succeed");
					}

				} else if (sapResponse.getReturn() != null) {// failed
					String infoSync = null;
					if (sapResponse.getReturn().getItem() != null && !sapResponse.getReturn().getItem().isEmpty()) {
						for (int i = 0; i < sapResponse.getReturn().getItem().size(); i++) {
							if (sapResponse.getReturn().getItem().get(i).getType().equals("E")) {
								String errorMessage = sapResponse.getReturn().getItem().get(i).getMessage();

								if (infoSync != null) {
									infoSync = infoSync.concat("; ").concat(errorMessage);
								} else {
									infoSync = errorMessage;
								}
							}
						}

						result = transferPostingDao.updateFieldsAfterSynced(idHeader, infoSync, "Failed");

						if (result.getIdMessage() == 1) {// update table success, return message info sync
							result.setIdMessage(0);
							result.setMessage("Failed sync tp_confirm data to sap, caused by " + infoSync);
						} else {// else error update table dan mnculin exceptionnya
							result.setIdMessage(0);
							result.setMessage("Failed sync tp_confirm data to sap, caused by " + infoSync
									+ " and failed to update to database");
						}
					}
				}
			}
		} catch (Exception e) {
			result.setIdMessage(0);
			result.setMessage(e.getMessage());
		}

		return result;

	}

}
