package id.co.qualitas.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.data.rest.core.util.MapUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import id.co.qualitas.component.Cons;
import id.co.qualitas.component.Utils;
import id.co.qualitas.dao.interfaces.GoodReceiptFGDao;
import id.co.qualitas.dao.interfaces.TransferPostingConfirmDao;
import id.co.qualitas.domain.request.GoodReceiptFGHeaderRequest;
import id.co.qualitas.domain.request.GoodReceiptFGMaterialWithComponentsRequest;
import id.co.qualitas.domain.request.GoodReceiptFGRequest;
import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.response.SAP_GoodReceiptFGComponentResponse;
import id.co.qualitas.domain.response.SAP_GoodReceiptFGMaterialResponse;
import id.co.qualitas.domain.response.SAP_GoodReceiptFGResponse;
import id.co.qualitas.domain.response.SAP_StockOEMResponse;
import id.co.qualitas.domain.response.SAP_TransferPostingDetailResponse;
import id.co.qualitas.domain.response.SAP_TransferPostingResponse;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.domain.webservice.goodsmvt.Bapi2017GmCode;
import id.co.qualitas.domain.webservice.goodsmvt.Bapi2017GmHead01;
import id.co.qualitas.domain.webservice.goodsmvt.Bapi2017GmItemCreate;
import id.co.qualitas.domain.webservice.goodsmvt.SpeBapi2017GmRefEwm;
import id.co.qualitas.domain.webservice.goodsmvt.TableOfBapi2017GmItemCreate;
import id.co.qualitas.domain.webservice.goodsmvt.TableOfBapi2017GmSerialnumber;
import id.co.qualitas.domain.webservice.goodsmvt.TableOfBapiparex;
import id.co.qualitas.domain.webservice.goodsmvt.TableOfBapiret2;
import id.co.qualitas.domain.webservice.goodsmvt.TableOfCwmBapi2017GmItemCreate;
import id.co.qualitas.domain.webservice.goodsmvt.TableOfSpeBapi2017ServicepartData;
import id.co.qualitas.domain.webservice.goodsmvt.ZfmWbGoodsmvt;
import id.co.qualitas.domain.webservice.goodsmvt.ZfmWbGoodsmvtResponse;
import id.co.qualitas.domain.webservice.pofg.TABLEOFZTYWBPOFGCOMPONENT;
import id.co.qualitas.domain.webservice.pofg.TABLEOFZTYWBPOFGDETAIL;
import id.co.qualitas.domain.webservice.pofg.TABLEOFZTYWBPOFGHEADER;
import id.co.qualitas.domain.webservice.pofg.ZFMWBPOFG;
import id.co.qualitas.domain.webservice.pofg.ZFMWBPOFGResponse;
import id.co.qualitas.domain.webservice.pofg.ZTYWBPOFGCOMPONENT;
import id.co.qualitas.domain.webservice.pofg.ZTYWBPOFGDETAIL;
import id.co.qualitas.domain.webservice.pofg.ZTYWBPOFGHEADER;
import id.co.qualitas.domain.webservice.stockoem.TABLEOFZTYWBOEM;
import id.co.qualitas.domain.webservice.stockoem.ZFMWBSTOCKOEM;
import id.co.qualitas.domain.webservice.stockoem.ZFMWBSTOCKOEMResponse;
import id.co.qualitas.domain.webservice.stockoem.ZTYWBOEM;
import id.co.qualitas.domain.webservice.tposting.ZTYWBTPHEADER;
import id.co.qualitas.service.interfaces.GoodReceiptFGService;
import id.co.qualitas.service.interfaces.TransferPostingConfirmService;

@Service
public class GoodReceiptFGServiceImpl implements GoodReceiptFGService {
	@Autowired
	GoodReceiptFGDao goodReceiptFGDao;

	@Override
	public WSMessage create(GoodReceiptFGRequest request) {
		WSMessage message = new WSMessage();

		if (request.getHeader() == null) {
			message.setMessage("No request detected");
		} else if (request.getListMaterialWithComponents() == null
				|| request.getListMaterialWithComponents().isEmpty()) {
			message.setMessage("This request doesn't have any material");
		} else {
			message = goodReceiptFGDao.create(request);
		}

		return message;
	}

	@Override
	public List<SAP_GoodReceiptFGResponse> getList(String idSloc, String idPlant) {
		List<SAP_GoodReceiptFGResponse> result = new ArrayList<SAP_GoodReceiptFGResponse>();
		List<SAP_GoodReceiptFGResponse> listHeader = new ArrayList<SAP_GoodReceiptFGResponse>();
		List<SAP_GoodReceiptFGMaterialResponse> listMaterial = new ArrayList<SAP_GoodReceiptFGMaterialResponse>();
		List<SAP_GoodReceiptFGComponentResponse> listComponent = new ArrayList<SAP_GoodReceiptFGComponentResponse>();

		try {
			ZFMWBPOFGResponse sapResponse = new ZFMWBPOFGResponse();
			ZFMWBPOFG request = new ZFMWBPOFG();

			request.setCOMPONENT(new TABLEOFZTYWBPOFGCOMPONENT());
			request.setDETAIL(new TABLEOFZTYWBPOFGDETAIL());
			request.setHEADER(new TABLEOFZTYWBPOFGHEADER());

			request.setPEINDT(Utils.getCurrentDate(Cons.DATE_FORMAT_1));
			request.setPLGORT(idSloc != null && idSloc.equals("null") ? null : idSloc);
			request.setPWERKS(idPlant);

			sapResponse = (ZFMWBPOFGResponse) Utils.WebserviceResponse(request, Cons.CONTEXT_POFG,
					Cons.WEBSERVICE_SAP + Cons.ENDPOINT_POFG, Cons.USERNAME, Cons.PASSWORD);

			if (sapResponse.getHEADER() != null && sapResponse.getHEADER().getItem() != null
					&& !sapResponse.getHEADER().getItem().isEmpty()) {

				for (ZTYWBPOFGHEADER sapHeader : sapResponse.getHEADER().getItem()) {
					SAP_GoodReceiptFGResponse response = new SAP_GoodReceiptFGResponse();
					response.setDocNo(sapHeader.getEBELN());
					response.setDocDate(sapHeader.getAEDAT());
					response.setDocType(sapHeader.getBSART());
					response.setDocHeader(sapHeader.getHTEXT());
					response.setIdSloc(sapHeader.getLGORT());
					response.setIdPlant(sapHeader.getWERKS());

					listHeader.add(response);
				}
			} else {
				System.out.println("Header from sap is null or empty");
			}

			if (sapResponse.getDETAIL() != null && sapResponse.getDETAIL().getItem() != null
					&& !sapResponse.getDETAIL().getItem().isEmpty()) {
				String PSTYP_FOR_PO_SUBCON = "3";
				String PO_DESC_FOR_PSTYP_3 = "Subcontracting";
				String PO_DESC_OTHER_THAN_PSTYP_3 = "Regular";

				for (ZTYWBPOFGDETAIL sapDetail : sapResponse.getDETAIL().getItem()) {
					if(sapDetail.getELIKZ() != null && !sapDetail.getELIKZ().toLowerCase().equals("x") && sapDetail.getELIKZ().equals("")) {
						SAP_GoodReceiptFGMaterialResponse response = new SAP_GoodReceiptFGMaterialResponse();
						response.setDocNo(sapDetail.getEBELN());
						response.setDocItemNo(Utils.trimLeadingZeroes(sapDetail.getEBELP(), true));
						response.setMaterialNo(Utils.trimLeadingZeroes(sapDetail.getMATNR()));
						response.setMaterialDesc(sapDetail.getTXZ01());
						response.setProdTargetDate(sapDetail.getEINDT());
						response.setOrderQty(sapDetail.getMENGE());
						response.setOutstandingQty(sapDetail.getZEMNG());
						response.setUom(sapDetail.getMEINS());
						response.setItemCategory(sapDetail.getPSTYP());
						response.setDeliveryDate(sapDetail.getEINDT());
						if (sapDetail.getPSTYP().equals(PSTYP_FOR_PO_SUBCON)) {
							response.setPoDesc(PO_DESC_FOR_PSTYP_3);
						} else {
							response.setPoDesc(PO_DESC_OTHER_THAN_PSTYP_3);
						}

						listMaterial.add(response);
					}
					
				}
			} else {
				System.out.println("Detail from sap is null or empty");
			}

			if (sapResponse.getCOMPONENT() != null && sapResponse.getCOMPONENT().getItem() != null
					&& !sapResponse.getCOMPONENT().getItem().isEmpty()) {

				for (ZTYWBPOFGCOMPONENT sapComponent : sapResponse.getCOMPONENT().getItem()) {
					SAP_GoodReceiptFGComponentResponse response = new SAP_GoodReceiptFGComponentResponse();
					response.setDocNo(sapComponent.getEBELN());
					response.setDocItemNo(Utils.trimLeadingZeroes(sapComponent.getEBELP(), true));
					response.setComponentItemNo(Utils.trimLeadingZeroes(sapComponent.getRSPOS(), true));
					response.setComponentNo(Utils.trimLeadingZeroes(sapComponent.getMATNR()));
					response.setComponentDesc(sapComponent.getMAKTX());

					listComponent.add(response);
				}
			} else {
				System.out.println("Component from sap is null or empty");
			}

			result = combineList(listHeader, listMaterial, listComponent);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private List<SAP_GoodReceiptFGResponse> combineList(List<SAP_GoodReceiptFGResponse> listHeader,
			List<SAP_GoodReceiptFGMaterialResponse> listMaterial,
			List<SAP_GoodReceiptFGComponentResponse> listComponent) {

		Map<String, SAP_GoodReceiptFGResponse> headerMap = new HashMap<>(listHeader.size());
		for (SAP_GoodReceiptFGResponse header : listHeader) {
			headerMap.put(header.getDocNo(), header);
		}

		for (SAP_GoodReceiptFGMaterialResponse detail : listMaterial) {

			SAP_GoodReceiptFGResponse header = headerMap.get(detail.getDocNo());

			if (header != null) {
				if (header.getListMaterial() == null) {
					header.setListMaterial(new ArrayList<SAP_GoodReceiptFGMaterialResponse>());
				}

				header.getListMaterial().add(detail);
			}
		}

		for (SAP_GoodReceiptFGComponentResponse comp : listComponent) {
			SAP_GoodReceiptFGResponse header = headerMap.get(comp.getDocNo());

			if (header != null) {
				if (header.getListMaterial() != null && !header.getListMaterial().isEmpty()) {
					for (SAP_GoodReceiptFGMaterialResponse material : header.getListMaterial()) {
						if (material.getDocItemNo().equals(comp.getDocItemNo())) {
							if (material.getListComponent() == null) {
								material.setListComponent(new ArrayList<SAP_GoodReceiptFGComponentResponse>());
							}
							material.getListComponent().add(comp);
						}
					}
				}
			}
		}

		return new ArrayList<>(headerMap.values());
	}

	@Override
	public WSMessage syncToSAP() {
		WSMessage message = new WSMessage();
		List<SAP_GoodReceiptFGResponse> listGreceiptFGHeader = new ArrayList<SAP_GoodReceiptFGResponse>();
		listGreceiptFGHeader = goodReceiptFGDao.getListHeader();

		if (listGreceiptFGHeader != null && !listGreceiptFGHeader.isEmpty()) {

			String DEFAULT_GM_CODE = "01";

			for (SAP_GoodReceiptFGResponse headerFromDB : listGreceiptFGHeader) {
				// mapping header from database to sap model
				Bapi2017GmHead01 requestHeader = new Bapi2017GmHead01();
//				requestHeader.setDocDate(Utils.validateToString(headerFromDB.getDocDate()));
//				requestHeader.setHeaderTxt(Utils.validateToString(headerFromDB.getHeaderTxt()));
				requestHeader.setHeaderTxt("INTERFACE OEM");
//				requestHeader.setPstngDate(Utils.validateToString(headerFromDB.getPstngDate()));

				// mapping gm code to sap model
				Bapi2017GmCode requestGmCode = new Bapi2017GmCode();
				requestGmCode.setGmCode(DEFAULT_GM_CODE);

				// sap model for details and components
				TableOfBapi2017GmItemCreate requestListItem = new TableOfBapi2017GmItemCreate();

				if (headerFromDB.getId() != 0) {
					String idHeader = Utils.validateToString(headerFromDB.getId());
					// list pair detail item no and line id
					List<Map<String, String>> listPairDetailItemNoAndLineId = new ArrayList<Map<String, String>>();

					List<Map<String, Object>> listGreceiptFGDetail = new ArrayList<Map<String, Object>>();
					listGreceiptFGDetail = goodReceiptFGDao.getListDetailBy(idHeader);

					if (listGreceiptFGDetail != null && !listGreceiptFGDetail.isEmpty()) {
						
						if(listGreceiptFGDetail.get(0) != null) {
							requestHeader.setDocDate(Utils.validateToString(listGreceiptFGDetail.get(0).get("postingDate")));
							requestHeader.setPstngDate(Utils.validateToString(listGreceiptFGDetail.get(0).get("postingDate")));
							requestHeader.setRefDocNoLong(Utils.validateToString(listGreceiptFGDetail.get(0).get("postingDate")));
						}

						String DEFAULT_MOVE_TYPE = "101";
						String DEFAULT_MVT_IND = "B";

						for (int i = 0; i < listGreceiptFGDetail.size(); i++) {
							Map<String, Object> detailFromDB = listGreceiptFGDetail.get(i);

							// mapping data detail from database to sap model
							Bapi2017GmItemCreate requestDetail = new Bapi2017GmItemCreate();
							requestDetail.setLineId(String.valueOf(i + 1));
							requestDetail.setPoNumber(Utils.validateToString(detailFromDB.get("poNumber")));
							requestDetail.setPoItem(Utils.validateToString(detailFromDB.get("poItem")));
							if(detailFromDB.get("material") != null) {
								String leadingMaterial = Utils.addLeadingZeroes(detailFromDB.get("material").toString(), Cons.MAX_DIGIT_MATERIAL_NO_SAP);

								requestDetail.setMaterial(leadingMaterial);
							}
							requestDetail.setEntryQnt(detailFromDB.get("entryQnt") != null
									? new BigDecimal(detailFromDB.get("entryQnt").toString())
									: null);
							requestDetail.setBatch(Utils.validateToString(detailFromDB.get("batch")));
							requestDetail.setExpirydate(Utils.validateToString(detailFromDB.get("expiryDate")));
							requestDetail.setMoveType(DEFAULT_MOVE_TYPE);
							requestDetail.setMvtInd(DEFAULT_MVT_IND);
							requestDetail.setEntryUom(Utils.validateToString(detailFromDB.get("entryUom")));
							requestDetail.setStgeLoc(Utils.validateToString(detailFromDB.get("stgeLoc")));
							requestDetail.setProdDate(Utils.validateToString(detailFromDB.get("postingDate")));

							// add content list item to TableOfBapi2017GmItemCreate requestListItem
							requestListItem.getItem().add(requestDetail);

							Map<String, String> pairDetailItemNoAndLineId = new HashMap<String, String>();
							pairDetailItemNoAndLineId.put("detailItemNo",
									Utils.validateToString(detailFromDB.get("detailItemNo")));
							pairDetailItemNoAndLineId.put("lineId", requestDetail.getLineId());

							listPairDetailItemNoAndLineId.add(pairDetailItemNoAndLineId);
						}
					}

					// component
					if (listPairDetailItemNoAndLineId != null && !listPairDetailItemNoAndLineId.isEmpty()) {
						for (int i = 0; i < listPairDetailItemNoAndLineId.size(); i++) {
							String detailItemNo = Utils
									.validateToString(listPairDetailItemNoAndLineId.get(i).get("detailItemNo"));
							String parentLineId = Utils
									.validateToString(listPairDetailItemNoAndLineId.get(i).get("lineId"));

							List<Map<String, Object>> listGreceiptFGComponent = new ArrayList<Map<String, Object>>();

							if (idHeader != null && detailItemNo != null) {
								listGreceiptFGComponent = goodReceiptFGDao.getListComponentBy(idHeader, detailItemNo);

								if (listGreceiptFGComponent != null && !listGreceiptFGComponent.isEmpty()) {

									String DEFAULT_COMPONENT_MOVE_TYPE = "543";
									String DEFAULT_COMPONENT_SPEC_STOCK = "O";

									for (int j = 0; j < listGreceiptFGComponent.size(); j++) {
										Map<String, Object> componentFromDB = listGreceiptFGComponent.get(j);

										// mapping data component from database to sap model
										Bapi2017GmItemCreate requestComponent = new Bapi2017GmItemCreate();
										requestComponent.setParentId(parentLineId);
										requestComponent
												.setLineId(String.valueOf(requestListItem.getItem().size() + 1));
//										requestComponent
//												.setMaterial(Utils.validateToString(componentFromDB.get("material")));
										if(componentFromDB.get("material") != null) {
											String leadingMaterial = Utils.addLeadingZeroes(componentFromDB.get("material").toString(), Cons.MAX_DIGIT_MATERIAL_NO_SAP);

											requestComponent.setMaterial(leadingMaterial);
										}
										requestComponent.setBatch(Utils.validateToString(componentFromDB.get("batch")));
										requestComponent.setEntryQnt(componentFromDB.get("entryQnt") != null
												? new BigDecimal(componentFromDB.get("entryQnt").toString())
												: null);
										requestComponent.setMoveType(DEFAULT_COMPONENT_MOVE_TYPE);
										requestComponent.setSpecStock(DEFAULT_COMPONENT_SPEC_STOCK);
										requestComponent.setEntryUom(componentFromDB.get("entryUom").toString());
										requestComponent.setPlant(componentFromDB.get("plant").toString());

										// add content list item to TableOfBapi2017GmItemCreate requestListItem
										requestListItem.getItem().add(requestComponent);
									}
								}
							}
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
					
					ZfmWbGoodsmvtResponse sapResponse = new ZfmWbGoodsmvtResponse();
					try {
						sapResponse = (ZfmWbGoodsmvtResponse) Utils.WebserviceResponse(request, Cons.CONTEXT_GOOD_SMVT,
								Cons.WEBSERVICE_SAP + Cons.ENDPOINT_GOOD_SMVT, Cons.USERNAME, Cons.PASSWORD);

						if (sapResponse != null) {
							// TODO tunggu sap nya nyala, test liat bentuk balikan dari sap di model mana

							// Sukses (S) kl sukses harusnya returnnya kosong, matdocnya ada isinya
							// Error (E) kl error harusnya returnnya isi, matdocnya yang kosong
							if (sapResponse.getMaterialdocument() != null && !sapResponse.getMaterialdocument().isEmpty()) {// success
								goodReceiptFGDao.updateFieldsAfterSynced(idHeader, sapResponse.getMaterialdocument(),
										"Sync Success", "Completed");

								message.setIdMessage(1);
								message.setMessage("Success sync gr_fg data to sap");
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

									message = goodReceiptFGDao.updateFieldsAfterSynced(idHeader, infoSync, "Failed");

									if (message.getIdMessage() == 1) {// update table success, return message info sync
										message.setIdMessage(0);
										message.setMessage("Failed sync gr_fg data to sap, caused by " + infoSync);
									} // else error update table dan mnculin exceptionnya
									else {
										message.setIdMessage(0);
										message.setMessage("Failed sync gr_fg data tot sap, caused by " + infoSync 
												+ " and failed to update to database");
									}
								}
							}
						}else {
							message.setIdMessage(0);
							message.setMessage("Error getting response from sap");
						}
					} catch (Exception e) {
						message.setIdMessage(0);
						message.setMessage(e.getMessage());
						e.printStackTrace();
					}
				}
			}
		}

		return message;
	}
}
