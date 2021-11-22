package id.co.qualitas.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.record.HeaderRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import id.co.qualitas.component.Cons;
import id.co.qualitas.component.ConsParam;
import id.co.qualitas.component.EmailUtils;
import id.co.qualitas.component.Utils;
import id.co.qualitas.dao.interfaces.DeliveryOrderDao;
import id.co.qualitas.dao.interfaces.PGIDao;
import id.co.qualitas.dao.interfaces.VendorDao;
import id.co.qualitas.domain.request.DODetail;
import id.co.qualitas.domain.request.DOHeader;
import id.co.qualitas.domain.request.DORequest;
import id.co.qualitas.domain.response.DOHistoryResponse;
import id.co.qualitas.domain.response.Vendor;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.domain.webservice.createdo.BAPIDELICIOUSCREATEDITEMS;
import id.co.qualitas.domain.webservice.createdo.BAPIDELICIOUSREQUEST;
import id.co.qualitas.domain.webservice.createdo.BAPIDLVREFTOSALESORDER;
import id.co.qualitas.domain.webservice.createdo.BAPISHPDELIVNUMB;
import id.co.qualitas.domain.webservice.createdo.TABLEOFBAPIDELICIOUSCREATEDITEMS;
import id.co.qualitas.domain.webservice.createdo.TABLEOFBAPIDELICIOUSREQUEST;
import id.co.qualitas.domain.webservice.createdo.TABLEOFBAPIDLVREFTOSALESORDER;
import id.co.qualitas.domain.webservice.createdo.TABLEOFBAPIRET2;
import id.co.qualitas.domain.webservice.createdo.TABLEOFBAPISHPDELIVNUMB;
import id.co.qualitas.domain.webservice.createdo.TABLEOFTLINE;
import id.co.qualitas.domain.webservice.createdo.TABLEOFZTYWBCREATEDODETAIL;
import id.co.qualitas.domain.webservice.createdo.TABLEOFZTYWBCREATEDOWEIGHT;
import id.co.qualitas.domain.webservice.createdo.THEAD;
import id.co.qualitas.domain.webservice.createdo.TLINE;
import id.co.qualitas.domain.webservice.createdo.ZFMWBCREATEDO;
import id.co.qualitas.domain.webservice.createdo.ZFMWBCREATEDOResponse;
import id.co.qualitas.domain.webservice.createdo.ZTYWBCREATEDODETAIL;
import id.co.qualitas.domain.webservice.createdo.ZTYWBCREATEDOHEADER;
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
import id.co.qualitas.domain.webservice.podstatus.TABLEOFVBELNTAB;
import id.co.qualitas.domain.webservice.podstatus.TABLEOFZTYWBPODSTATUS;
import id.co.qualitas.domain.webservice.podstatus.VBELNTAB;
import id.co.qualitas.domain.webservice.podstatus.ZFMWBPODSTATUS;
import id.co.qualitas.domain.webservice.podstatus.ZFMWBPODSTATUSResponse;
import id.co.qualitas.domain.webservice.podstatus.ZTYWBPODSTATUS;
import id.co.qualitas.domain.webservice.shipmentchange.BAPISHIPMENTHEADER;
import id.co.qualitas.domain.webservice.shipmentchange.BAPISHIPMENTHEADERACTION;
import id.co.qualitas.domain.webservice.shipmentchange.BAPISHIPMENTITEM;
import id.co.qualitas.domain.webservice.shipmentchange.BAPISHIPMENTITEMACTION;
import id.co.qualitas.domain.webservice.shipmentchange.TABLEOFBAPISHIPMENTITEM;
import id.co.qualitas.domain.webservice.shipmentchange.TABLEOFBAPISHIPMENTITEMACTION;
import id.co.qualitas.domain.webservice.shipmentchange.ZFMSHIPMENTCHANGE;
import id.co.qualitas.domain.webservice.shipmentchange.ZFMSHIPMENTCHANGEResponse;
import id.co.qualitas.service.interfaces.DeliveryOrderService;
import id.co.qualitas.service.interfaces.PGIService;
import jdk.nashorn.internal.parser.JSONParser;

@Service
public class DeliveryOrderServiceImpl implements DeliveryOrderService {
	@Autowired
	DeliveryOrderDao doDao;
	
	@Autowired
	VendorDao vendorDao;

	@Autowired
	PGIDao pgiDao;
	
	@Autowired
	PGIService pgiService;

	@Override
	public WSMessage create(DORequest request) {
		WSMessage message = new WSMessage();
		WSMessage syncDoMessage = new WSMessage();
		WSMessage assignDoMessage = new WSMessage();

		if (request.getDoHeader() == null) {
			message.setMessage("No request detected");
		} else if (request.getListDoDetail() == null || request.getListDoDetail().isEmpty()) {
			message.setMessage("This request doesn't have any detail");
		} else {
			message = doDao.create(request);
		}

		if (message.getIdMessage() == 1) {
			int idHeader = (int) message.getResult();
			//sync do ke sap dapetin do no sap nya
			syncDoMessage = syncToSAP((int)message.getResult());
			
			if(syncDoMessage.getIdMessage() == 1) {
				
				request.getDoHeader().setDoNoSap(String.valueOf(syncDoMessage.getResult()));
				
				assignDoMessage = pgiService.assignShipment(request);

			}

			message.setMessage(message.getMessage() + ",\n" + syncDoMessage.getMessage() + ",\n" + assignDoMessage.getMessage());
			
			message.setResult(doDao.getHeadersForHistory(idHeader));
		}

		return message;
	}

	@Override
	public WSMessage getListForHistory(String dateFrom, String dateTo, String docType, String username, String idSloc,
			String idPlant) {
		WSMessage message = new WSMessage();

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date dateFromInDate = null, dateToInDate = null;

		if (dateFrom == null || dateFrom.isEmpty()) {
			message.setMessage("Date from is required");
			return message;
		} else {
			try {
				dateFromInDate = format.parse(dateFrom);
			} catch (ParseException e) {
				message.setMessage(e.getMessage());
				e.printStackTrace();
			}
		}

		if (dateTo == null || dateTo.isEmpty()) {
			message.setMessage("Date to is required");
			return message;
		} else {
			try {
				dateToInDate = format.parse(dateTo);
			} catch (ParseException e) {
				message.setMessage(e.getMessage());
				e.printStackTrace();
			}
		}

		if (docType == null || docType.isEmpty()) {
			message.setMessage("Doc type is required");
			return message;
		}

		if (username == null || username.isEmpty()) {
			message.setMessage("Error occured, cannot obtain username for session");
			return message;
		}

		List<DOHistoryResponse> listHeader = new ArrayList<DOHistoryResponse>();
		listHeader = doDao.getHeadersForHistory(dateFromInDate, dateToInDate, "%" + docType + "%", idSloc, idPlant);

		if (listHeader != null && !listHeader.isEmpty()) {
			for (int i = 0; i < listHeader.size(); i++) {
				DOHistoryResponse doHeader = listHeader.get(i);

				List<DODetail> listDetail = new ArrayList<DODetail>();

				listDetail = doDao.getDetailsByIdHeader(doHeader.getId());

				if (listDetail != null && !listDetail.isEmpty()) {
					for (int i2 = 0; i2 < listDetail.size(); i2++) {
						DODetail detail = listDetail.get(i2);

						List<DODetail> listBatch = new ArrayList<DODetail>();
						listBatch = doDao.getBatchsByIdAndDocItemNo(doHeader.getId(), detail.getDocItemNo());
						detail.setListBatch(listBatch);

					}
				}

				doHeader.setListDetail(listDetail);
			}
			message.setIdMessage(1);
			message.setMessage("Success");
			message.setResult(listHeader);
		} else {
			message.setIdMessage(1);
			message.setMessage("Success, but there is no data");
			message.setResult(null);
		}

		return message;

	}

	@Override
	public WSMessage updateStatusPOD(String idSloc, String idPlant) {
		WSMessage message = new WSMessage();

		List<String> listDoNo = new ArrayList<String>();
		listDoNo = doDao.getListDoNoForUpdateStatusPOD(idSloc, idPlant);

		if (listDoNo != null && !listDoNo.isEmpty()) {

			ZFMWBPODSTATUS request = new ZFMWBPODSTATUS();

			TABLEOFVBELNTAB tableRequest = new TABLEOFVBELNTAB();

			for (int i = 0; i < listDoNo.size(); i++) {
				VBELNTAB vbelnTab = new VBELNTAB();

				if (listDoNo.get(i) != null) {
					vbelnTab.setVBELN(listDoNo.get(i));
					tableRequest.getItem().add(vbelnTab);
				}
			}
			request.setINPUT(tableRequest);
			request.setRESULT(new TABLEOFZTYWBPODSTATUS());
			new Gson().toJson(request);
			ZFMWBPODSTATUSResponse sapResponse = new ZFMWBPODSTATUSResponse();
			try {
				sapResponse = (ZFMWBPODSTATUSResponse) Utils.WebserviceResponse(request, Cons.CONTEXT_POD_STATUS,
						Cons.WEBSERVICE_SAP + Cons.ENDPOINT_POD_STATUS, Cons.USERNAME, Cons.PASSWORD);

				if (sapResponse != null) {
					if (sapResponse.getRESULT() != null) {
						if (sapResponse.getRESULT().getItem() != null && !sapResponse.getRESULT().getItem().isEmpty()) {
							for (int i = 0; i < sapResponse.getRESULT().getItem().size(); i++) {
								ZTYWBPODSTATUS resultItem = sapResponse.getRESULT().getItem().get(i);

								if (resultItem != null) {
									String status = resultItem.getMBLNR() != null && !resultItem.getMBLNR().equals("") ? Cons.STATUS_PGI_COMPLETED : Cons.STATUS_PGI_FAILED;
									if(doDao.isPodStatusAlreadyCompleted(resultItem.getVBELN())) {
										doDao.updateStatusPodByDoNoSap(resultItem.getVBELN(), resultItem.getMBLNR(), resultItem.getTVOLUM(), 
												resultItem.getVUOM(), resultItem.getTWEIGHT(), resultItem.getWUOM(), status);
									}else {
										doDao.updateStatusPodByDoNoSap(resultItem.getPDSTK(), resultItem.getDESCR(),
												resultItem.getVBELN(), resultItem.getMBLNR(), resultItem.getTVOLUM(), resultItem.getVUOM(), 
												resultItem.getTWEIGHT(), resultItem.getWUOM(), status);
									}
								}
							}

							message.setIdMessage(1);
							message.setMessage("Success update status");
						}
					} else {
						message.setIdMessage(0);
						message.setMessage("Getting sap response but result is null");
					}
				} else {
					message.setIdMessage(0);
					message.setMessage("SAP response is null");
				}

			} catch (Exception e) {
				message.setIdMessage(0);
				message.setMessage(e.getMessage());
			}

		} else {
			message.setIdMessage(0);
			message.setMessage("There is no list do no to proceed");
		}

		return message;
	}

	@Override
	public WSMessage syncToSAP() {
		WSMessage result = new WSMessage();

		List<Map<String, String>> listHeader = new ArrayList<Map<String, String>>();
		listHeader = doDao.getListHeaderForSync();

		if (listHeader != null && !listHeader.isEmpty()) {
			String message = "";
			for (int i = 0; i < listHeader.size(); i++) {// start looping

				Map<String, String> header = listHeader.get(i);

				String idHeader = null;

				if (header.get("id") != null) {
					idHeader = Utils.validateToString(header.get("id"));

					String messageIndexPrefix = "\r\n" + (i + 1) + ". ";

					if (header.get("doc_type") != null) {

						WSMessage messageAfterSync = requestCallZWBCREATEDO(mappingZfmWbCreateDo(header), idHeader,
								header.get("doc_type").toString());

						message = message.concat(messageIndexPrefix + messageAfterSync.getMessage());
					}
				}
			} // end looping

			result.setIdMessage(1);
			result.setMessage(message);
		} else {// error no list header
			result.setIdMessage(0);
			result.setMessage(
					"There is no list do_header to sync " + "(Query is looking for do_header that still null)");
		}

		return result;
	}
	
	@Override
	public WSMessage syncToSAP(int idHeader) {
		WSMessage result = new WSMessage();


		Map<String, String> header = doDao.getHeaderByIdHeader(idHeader);

		if (header.get("doc_type") != null) {

			result = requestCallZWBCREATEDO(mappingZfmWbCreateDo(header), String.valueOf(idHeader),
					header.get("doc_type").toString());

		}else {
			result.setMessage("Invalid doc type, id: " + String.valueOf(idHeader));
		}

		

		return result;
	}


	private ZFMWBCREATEDO mappingZfmWbCreateDo(Map<String, String> header) {
		// mapping header from database to sap model
		String docNo = Utils.validateToString(header.get("doc_no"));
		String docType = Utils.validateToString(header.get("doc_type"));

		ZTYWBCREATEDOHEADER requestHeader = new ZTYWBCREATEDOHEADER();
		requestHeader.setZDOCNO(docNo);
		requestHeader.setTKNUM(Utils.validateToString(header.get("shipment_no")));
		requestHeader.setJDALOADI(Utils.validateToString(header.get("jdaload_id")));
		requestHeader.setBSART(docType);
		requestHeader.setSINUMBER(Utils.validateToString(header.get("si_no")));
		requestHeader.setROUTE(Utils.validateToString(header.get("route")));
		requestHeader.setLSTEL(Utils.validateToString(header.get("loading_point")));

		THEAD thead = new THEAD();
		thead.setTDOBJECT("VBBK");
		thead.setTDID("ZA01");
//		thead.setTDSPRAS("EN");

		TLINE tline = new TLINE();
		tline.setTDFORMAT("*");
		tline.setTDLINE(Utils.validateToString(header.get("notes")));

		List<DODetail> listBatch = new ArrayList<DODetail>();
		List<DODetail> listDetailDistinct = new ArrayList<DODetail>();
		TABLEOFZTYWBCREATEDODETAIL requestListDetail = new TABLEOFZTYWBCREATEDODETAIL();
		TABLEOFBAPISHPDELIVNUMB requestListDeliv = new TABLEOFBAPISHPDELIVNUMB();
		TABLEOFBAPIDELICIOUSCREATEDITEMS requestListCreatedItems = new TABLEOFBAPIDELICIOUSCREATEDITEMS();
		TABLEOFBAPIDLVREFTOSALESORDER requestListItems = new TABLEOFBAPIDLVREFTOSALESORDER();
		TABLEOFBAPIDELICIOUSREQUEST requestListRequests = new TABLEOFBAPIDELICIOUSREQUEST();

		if (header.get("id") != null) {
			int idHeader = Integer.parseInt(String.valueOf(header.get("id")));
//			listDetail = doDao.getDetailsByIdHeader(Integer.parseInt(String.valueOf(header.get("id")))); // id pk jadi gamngkin null
			listBatch = doDao.getBatchsById(idHeader); // id pk jadi gamngkin null

			listDetailDistinct = doDao.getDistinctDetailsByIdHeader(idHeader); // id pk jadi gamngkin null

			if (listDetailDistinct != null && !listDetailDistinct.isEmpty()) {

				for (int i = 0; i < listDetailDistinct.size(); i++) {
					DODetail detail = listDetailDistinct.get(i);

					if (docType.equals(Cons.DOC_TYPE_STO)) {

						BAPIDLVREFTOSALESORDER requestItems = new BAPIDLVREFTOSALESORDER();
						requestItems.setREFDOC(docNo);
						requestItems.setREFITEM(Utils.addLeadingZeroes(String.valueOf(detail.getDocItemNo()), 6));
						requestItems.setDLVQTY(detail.getDoQty());
						requestItems.setSALESUNIT(detail.getUom());
						requestListItems.getItem().add(requestItems);

					} else if (docType.equals(Cons.DOC_TYPE_SO)) {

						BAPIDELICIOUSREQUEST requestRequests = new BAPIDELICIOUSREQUEST();
						requestRequests.setDOCUMENTNUMB(docNo);
						requestRequests
								.setDOCUMENTITEM(Utils.addLeadingZeroes(String.valueOf(detail.getDocItemNo()), 6));
						requestRequests.setQUANTITYSALESUOM(detail.getDoQty().floatValue());
//						requestRequests.setSALESQTYNUM(detail.getDoQty());
						requestRequests.setSALESUNIT(detail.getUom());
						requestRequests.setDOCUMENTTYPE("A");
						requestRequests.setDOCUMENTTYPEPREDECESSOR("A");
						requestRequests.setDOCUMENTTYPEDELIVERY("LF");
						requestRequests.setPARTIALDELIVERY("X");
						requestRequests.setPLANT(header.get("id_plant").toString());
						requestRequests.setSTGELOC(header.get("id_sloc").toString());

						requestListRequests.getItem().add(requestRequests);
					}
				}
			}

			if (listBatch != null && !listBatch.isEmpty()) {
				for (int i = 0; i < listBatch.size(); i++) {
					DODetail batch = listBatch.get(i);

					ZTYWBCREATEDODETAIL requestDetail = new ZTYWBCREATEDODETAIL();
					requestDetail.setLGORT(Utils.validateToString(header.get("id_sloc")));
					requestDetail.setCHARG(batch.getBatchNo());
					requestDetail.setBATCHQTY(batch.getActualQty());
					requestDetail.setLFIMG(batch.getActualQty());
					requestDetail.setPOSNR(Utils.addLeadingZeroes(String.valueOf(batch.getDocItemNo()), 6));

					requestListDetail.getItem().add(requestDetail);
				}
			}
		}

		ZFMWBCREATEDO request = new ZFMWBCREATEDO();
		request.setHEADER(requestHeader);
		request.setDETAILBATCH(requestListDetail);
		if (docType.equals(Cons.DOC_TYPE_STO)) {
			request.setDELIVERIES(requestListDeliv);
			request.setITEMS(requestListItems);
		} else if (docType.equals(Cons.DOC_TYPE_SO)) {
			request.setCREATEDITEMS(requestListCreatedItems);
			request.setREQUEST(requestListRequests);
		}
		request.setRETURNSTO(new TABLEOFBAPIRET2());
		request.setRETURNSOBU(new TABLEOFBAPIRET2());
		request.setTEXTHEADER(thead);
		TABLEOFTLINE textLines = new TABLEOFTLINE();
		textLines.getItem().add(tline);
		request.setTEXTLINES(textLines);
		
		TABLEOFZTYWBCREATEDOWEIGHT weight = new TABLEOFZTYWBCREATEDOWEIGHT();
		request.setWEIGHT(weight);

		new Gson().toJson(request);
		return request;
	}

	private WSMessage requestCallZWBCREATEDO(ZFMWBCREATEDO request, String idHeader, String docType) {
		WSMessage result = new WSMessage();
		try {
			ZFMWBCREATEDOResponse sapResponse = new ZFMWBCREATEDOResponse();
			sapResponse = (ZFMWBCREATEDOResponse) Utils.WebserviceResponse(request, Cons.CONTEXT_CREATE_DO,
					Cons.WEBSERVICE_SAP + Cons.ENDPOINT_CREATE_DO, Cons.USERNAME, Cons.PASSWORD);
			if (sapResponse != null) {

				if (docType.equals(Cons.DOC_TYPE_STO)) {
					if (sapResponse.getDELIVERIES() != null && sapResponse.getDELIVERIES().getItem() != null
							&& !sapResponse.getDELIVERIES().getItem().isEmpty()) {

						doDao.updateFieldsAfterSynced(idHeader,
								sapResponse.getDELIVERIES().getItem().get(0).getDELIVNUMB(), "Sync Success",
								"CREATE DO COMPLETED", 
								sapResponse.getWEIGHT().getItem().get(0).getTWEIGHT(),
								sapResponse.getWEIGHT().getItem().get(0).getWUOM(),
								sapResponse.getWEIGHT().getItem().get(0).getTVOLUM(),
								sapResponse.getWEIGHT().getItem().get(0).getVUOM());

						result.setIdMessage(1);
						result.setMessage("Success sync do to sap");
						result.setResult(sapResponse.getDELIVERIES().getItem().get(0).getDELIVNUMB());

					} else if (sapResponse.getRETURNSTO() != null) {// failed
						String infoSync = null;
						if (sapResponse.getRETURNSTO().getItem() != null
								&& !sapResponse.getRETURNSTO().getItem().isEmpty()) {
							for (int i = 0; i < sapResponse.getRETURNSTO().getItem().size(); i++) {
								if (sapResponse.getRETURNSTO().getItem().get(i).getTYPE().equals("E")) {
									String errorMessage = sapResponse.getRETURNSTO().getItem().get(i).getMESSAGE();

									if (infoSync != null) {
										infoSync = infoSync.concat("; ").concat(errorMessage);
									} else {
										infoSync = errorMessage;
									}
								}
							}

							result = doDao.updateFieldsAfterSynced(idHeader, infoSync, "CREATE DO FAILED");

							result.setResult(null);
							if (result.getIdMessage() == 1) {// update table success, return message info sync
								result.setIdMessage(0);
								result.setMessage("Failed sync do_header data to sap, caused by " + infoSync);
							} else {// else error update table dan mnculin exceptionnya
								result.setIdMessage(0);
								result.setMessage("Failed sync do_header data to sap, caused by " + infoSync
										+ " and failed to update to database");
							}
						}
					} // end if
				} else if (docType.equals(Cons.DOC_TYPE_SO)) {
					if (sapResponse.getCREATEDITEMS() != null && sapResponse.getCREATEDITEMS().getItem() != null
							&& !sapResponse.getCREATEDITEMS().getItem().isEmpty()) {

						doDao.updateFieldsAfterSynced(idHeader,
								sapResponse.getCREATEDITEMS().getItem().get(0).getDOCUMENTNUMB(), "Sync Success",
								"CREATE DO COMPLETED",
								sapResponse.getWEIGHT().getItem().get(0).getTWEIGHT(),
								sapResponse.getWEIGHT().getItem().get(0).getWUOM(),
								sapResponse.getWEIGHT().getItem().get(0).getTVOLUM(),
								sapResponse.getWEIGHT().getItem().get(0).getVUOM());

						result.setIdMessage(1);
						result.setMessage("Success sync do to sap");
						result.setResult(sapResponse.getCREATEDITEMS().getItem().get(0).getDOCUMENTNUMB());

					} else if (sapResponse.getRETURNSOBU() != null) {// failed
						String infoSync = null;
						if (sapResponse.getRETURNSOBU().getItem() != null
								&& !sapResponse.getRETURNSOBU().getItem().isEmpty()) {
							for (int i = 0; i < sapResponse.getRETURNSOBU().getItem().size(); i++) {
								if (sapResponse.getRETURNSOBU().getItem().get(i).getTYPE().equals("E")) {
									String errorMessage = sapResponse.getRETURNSOBU().getItem().get(i).getMESSAGE();

									if (infoSync != null) {
										infoSync = infoSync.concat("; ").concat(errorMessage);
									} else {
										infoSync = errorMessage;
									}
								}
							}

							result = doDao.updateFieldsAfterSynced(idHeader, infoSync, "CREATE DO FAILED");
							result.setResult(null);
							if (result.getIdMessage() == 1) {// update table success, return message info sync
								result.setIdMessage(0);
								result.setMessage("Failed sync do_header data to sap, caused by " + infoSync);
							} else {// else error update table dan mnculin exceptionnya
								result.setIdMessage(0);
								result.setMessage("Failed sync do_header data to sap, caused by " + infoSync
										+ " and failed to update to database");
							}
						}
					} // end if

				}
			}
		} catch (Exception e) {
			result.setIdMessage(0);
			result.setMessage(e.getMessage());
		}

		return result;

	}

	@Override
	public WSMessage sendEmail(DOHeader header) {
		WSMessage message = new WSMessage();

		header.setDestinationName("tester");
//		header.setDestinationName(header.getDestinationName());
//		header.setDestinationEmail(header.getDestinationEmail());
//		header.setDestinationEmail("thomz.q@gmail.com");
//		header.setDestinationEmail("andi@qualitas.co.id");
//		header.setDestinationEmail("willyspheal@gmail.com");
		header.setDestinationEmail("testerwilxoxo@gmail.com");

//		header.setDestinationName("Priska");
//		header.setDestinationEmail("priska@qualitas.co.id");

		if (header.getDestinationEmail() == null) {
			message.setIdMessage(0);
			message.setMessage("Destination email not found");
			return message;
		}

		String recipientName = header.getDestinationName() != null ? header.getDestinationName() : "";
		String recipientEmail = header.getDestinationEmail();


		String noSuratJalan = header.getDoNoSap() != null ? "SJ" + header.getDoNoSap() : "-";
		String plantPengirim = (header.getIdPlant() != null ? header.getIdPlant() : "-") + " - "
				+ (header.getPlantName() != null ? header.getPlantName() : "") + "(Sloc : "
				+ (header.getIdSloc() != null ? header.getIdSloc() : "-") + ")";
		String alamatPengirim = header.getSenderAddress() != null ? header.getSenderAddress() : "-";
		String noTlpPengirim = header.getSenderPhone() != null ? header.getSenderPhone() : "-";
		String plantPenerima = header.getDestinationName() != null ? header.getDestinationName() : "-";
		String alamatPenerima = header.getDestinationName2() != null ? header.getDestinationName2() : "-";
		String noTlpPenerima = header.getDestinationPhone() != null ? header.getDestinationPhone() : "-";
		String ekspedisi = header.getVendorExpeditionName() != null ? header.getVendorExpeditionName() : "-";
		String jenisKendaraan = header.getVehicleNo() != null ? header.getVehicleNo() : "-";
		String noPolisi = header.getPlateNo() != null ? header.getPlateNo() : "-";
		String tanggalPengiriman = header.getShipmentDate() != null ? header.getShipmentDate() : "-";
		String beratTotalProduk = (header.getTotalProductWeight() != null ? header.getTotalProductWeight() : "0") + " "
				+ (header.getUow() != null ? header.getUow() : "");
		String volumeTotalProduk = (header.getTotalProductVolume() != null ? header.getTotalProductVolume() : "0") + " "
				+ (header.getUov() != null ? header.getUov() : "");

		String currentDate = Utils.getCurrentDate("E, dd MMM yyyy hh:mm a");

		Vendor vendorDetail = vendorDao.getVendorDetail(header.getIdSloc(), header.getIdPlant());
		String namaPengirim = vendorDetail != null ? vendorDetail.getFullName() : "-";

		String subject ="SJ" 
						+ (header.getDoNoSap() != null ? Utils.trimLeadingZeroes(header.getDoNoSap()) : "-")
						+ " - OEM " + namaPengirim 
						+ " - " + alamatPengirim;
		
		String body = "<body>"
				+ "<div style=\"border-style: solid;padding-left:20px;padding-right:30px;padding-top:30px;padding-bottom:30px;line-height: 1.2;font-family:Calibri;font-size:11pt\"> "
				+ "<div>" + "	<p>" + currentDate + "</p>" + "</div>" + "" + "    <br/>" + "<div>"
				+ "	<h2 style=\"text-align:center;\">SURAT JALAN</h2>" + "</div>" + "    " + "    <br/>" + "" + "<div>"
				+ "  <p>Email ini menginformasikan Surat Jalan dengan detail sebagai berikut: </p>" + "</div>" + ""
				+ "    <br/>" + "<div>" + "        <p style=\"font-weight: bold;\">No surat jalan = " + noSuratJalan
				+ "</p>" + "      " + "      <br/>" + ""
				+ "    <div  style=\"width: 100%;border-style: solid;border-color:black;padding:5px;margin-right:20px;\">"
				+ "      <p style=\"font-weight: bold;\">Pengiriman dari = " + plantPengirim + "</p>"
				+ "        <p style=\"margin-left:5em\"> " + alamatPengirim + "</p>"
				+ "        <p style=\"margin-left:5em\">No tlp: " + noTlpPengirim + "</p>" + "      <br/>"
				+ "        <p style=\"font-weight: bold;\">Tujuan ke = " + plantPenerima + "</p>"
				+ "        <p style=\"margin-left:5em\"> " + alamatPenerima + "</p>"
				+ "        <p style=\"margin-left:5em\">No tlp: " + noTlpPenerima + "</p>" + "      <br/>"
				+ "        <p style=\"font-weight: bold;\">Ekspedisi = " + ekspedisi + "</p>"
				+ "        <p style=\"margin-left:5em\">Jenis kendaraan = " + jenisKendaraan + "</p>"
				+ "        <p style=\"margin-left:5em\">No Polisi = " + noPolisi + "</p>"
				+ "        <p style=\"margin-left:5em\">Tanggal pengiriman = " + tanggalPengiriman + "</p>"
				+ "      <br/>" + "        <p style=\"font-weight: bold;\">Berat total produk = " + beratTotalProduk
				+ "</p>" + "        <p style=\"font-weight: bold;\">Volume total produk = " + volumeTotalProduk + "</p>"
				+ "  </div>" + "</div>" + "" + "    <br/>" + "  " + "<div>"
				+ "  <p>Email ini juga melampirkan dokumen, silahkan download untuk detail produk yang dikirim.</p>"
				+ "</div>" + "" + "" + "</div>  " + "</body>";

		byte[] fileByte = null;

		File tempFile = null;
		try {
			if (header.getAttachment() != null) {
				fileByte = Base64.decodeBase64(header.getAttachment());
				String prefix = ConsParam.PATH_PDF_DO;
//				String suffix = Cons.PATH_TEMP_SUFFIX;
//				tempFile = File.createTempFile(prefix, suffix, null);
//				FileOutputStream fos = new FileOutputStream(tempFile);
//				fos.write(fileByte);

//	    		Path pathRequestPdf = Paths.get();
				String pathString = prefix + (header.getDocNo()) + ".pdf";

				Path path = Paths.get(pathString);
				Files.write(path, fileByte);
				
				tempFile = new File(pathString);

			}

			Map<String, Object> emailRequest = new HashMap<String, Object>();
			emailRequest.put("recipientName", recipientName);
			emailRequest.put("recipientEmail", recipientEmail);
			emailRequest.put("subject", subject);
			emailRequest.put("body", body);
			emailRequest.put("attachment", tempFile);

			boolean isSent = EmailUtils.sendEmail(emailRequest);

			if (isSent) {
				message.setIdMessage(1);
				message.setMessage("Email sent to " + header.getDestinationEmail());
			} else {
				message.setIdMessage(0);
				message.setMessage("Failed to send email");
			}
		} catch (Exception e) {
			message.setIdMessage(0);
			message.setMessage(e.getMessage());
		}

		return message;
	}

	@Override
	public WSMessage assignDo() {
		WSMessage result = new WSMessage();
		List<Map<String, String>> listHeader = new ArrayList<Map<String, String>>();
		listHeader = doDao.getShipmentNoForAssign();

		if (listHeader != null && !listHeader.isEmpty()) {
			String message = "";
			for (int i = 0; i < listHeader.size(); i++) {// start looping

				Map<String, String> header = listHeader.get(i);
				
				String shipmentNo = null;
				String doNoSap = null;
				
				if (header.get("shipmentNo") != null && header.get("doNoSap") != null) {

					shipmentNo = Utils.validateToString(header.get("shipmentNo"));
					doNoSap = Utils.validateToString(header.get("doNoSap"));
					
					String messageIndexPrefix = "\r\n" + (i + 1) + ". " + shipmentNo + " ";
					
					WSMessage messageAfterSync = requestAssign(mappingZfmShipmentAssign(shipmentNo, doNoSap), shipmentNo, doNoSap);

					message = message.concat(messageIndexPrefix + messageAfterSync.getMessage());
				}
			} // end looping

			result.setIdMessage(1);
			result.setMessage(message);
		} else {// error no list header
			result.setIdMessage(0);
			result.setMessage("There is no list to assign "
					+ "(Query is looking for status that assign still not completed)");
		}
		
		return result;
	}
	
	private ZFMSHIPMENTCHANGE mappingZfmShipmentAssign(String shipmentNo, String doNoSap) {

		BAPISHIPMENTHEADER requestHeader = new BAPISHIPMENTHEADER();
		requestHeader.setSHIPMENTNUM(shipmentNo);
		
		List<Map<String, String>> listDetail = new ArrayList<>();
		TABLEOFBAPISHIPMENTITEM listRequestItem = new TABLEOFBAPISHIPMENTITEM();
		TABLEOFBAPISHIPMENTITEMACTION listRequestItemAction = new TABLEOFBAPISHIPMENTITEMACTION();

		BAPISHIPMENTITEM requestItem = new BAPISHIPMENTITEM();
		requestItem.setDELIVERY(doNoSap);

		listRequestItem.getItem().add(requestItem);

		BAPISHIPMENTITEMACTION requestItemAction = new BAPISHIPMENTITEMACTION();
		requestItemAction.setDELIVERY("A");
		requestItemAction.setITENERARY("A");
		listRequestItemAction.getItem().add(requestItemAction);
		

		BAPISHIPMENTHEADERACTION requestHeaderAction = new BAPISHIPMENTHEADERACTION();
		requestHeaderAction.setSHIPMENTNUM("X");

		ZFMSHIPMENTCHANGE request = new ZFMSHIPMENTCHANGE();
		request.setHEADERDATA(requestHeader);
		request.setHEADERDATAACTION(requestHeaderAction);
		request.setITEMDATA(listRequestItem);
		request.setITEMDATAACTION(listRequestItemAction);
		request.setRETURN(new id.co.qualitas.domain.webservice.shipmentchange.TABLEOFBAPIRET2());

		new Gson().toJson(request);	
		return request;
	}
	
	private WSMessage requestAssign(ZFMSHIPMENTCHANGE request, String shipmentNo, String doNoSap) {
		WSMessage result = new WSMessage();
		try {
			ZFMSHIPMENTCHANGEResponse sapResponse = new ZFMSHIPMENTCHANGEResponse();
			sapResponse = (ZFMSHIPMENTCHANGEResponse) Utils.WebserviceResponse(request, Cons.CONTEXT_SHIPMENT_CHANGE,
					Cons.WEBSERVICE_SAP + Cons.ENDPOINT_SHIPMENT_CHANGE, Cons.USERNAME, Cons.PASSWORD);
			if (sapResponse != null) {
				if (sapResponse.getRETURN() != null) {// failed
					String infoSync = null;
					String status = "ASSIGN DO FAILED";
					if (sapResponse.getRETURN().getItem() != null && !sapResponse.getRETURN().getItem().isEmpty()) {
						for (int i = 0; i < sapResponse.getRETURN().getItem().size(); i++) {

//							String doNoSap = null;
							String pgiNo = null;
							
							if(sapResponse.getRETURN().getItem().get(i).getNUMBER().equals("482")) {
								infoSync = null;
								String successMessage = sapResponse.getRETURN().getItem().get(i).getMESSAGE();
								status = "ASSIGN DO COMPLETED";

								if (infoSync != null) {
									infoSync = infoSync.concat("; ").concat(successMessage);
								} else {
									infoSync = successMessage;
								}
								
//								if(sapResponse.getMATDOC() != null && sapResponse.getMATDOC().getItem() != null && !sapResponse.getMATDOC().getItem().isEmpty()) {
//									for(int j = 0; j < sapResponse.getMATDOC().getItem().size(); j++) {
//
//										doNoSap = sapResponse.getMATDOC().getItem().get(j).getVBELN();
//										pgiNo = sapResponse.getMATDOC().getItem().get(j).getMBLNR();
//										
//										result = doDao.updateFieldsAfterSyncedByDoNoSap(doNoSap, infoSync, status, pgiNo);
//									}
//								}
								
								break;
								
							} else if (sapResponse.getRETURN().getItem().get(i).getTYPE().equals("E")) {
								String errorMessage = sapResponse.getRETURN().getItem().get(i).getMESSAGE();
								status = "ASSIGN DO FAILED";

								if (infoSync != null) {
									infoSync = infoSync.concat("; ").concat(errorMessage);
								} else {
									infoSync = errorMessage;
								}
								

//								result = doDao.updateFieldsAfterSyncedByShipmentNo(shipmentNo, infoSync, status, pgiNo);
							}
						}

//						result = shipmentDao.updateFieldsAfterSynced(shipmentNo, infoSync, status);
						result = doDao.updateFieldsAfterSyncedByDoNoSap(doNoSap, infoSync, status, null);

						if (result.getIdMessage() == 1) {// update table success, return message info sync
							result.setIdMessage(1);
							result.setMessage(status + " " + infoSync);
						} else {// else error update table dan mnculin exceptionnya
							result.setIdMessage(0);
							result.setMessage(status + " " + infoSync + " but failed to update to database");
						}
						return result;
					}
				} // end if

			}
		} catch (Exception e) {
			result.setIdMessage(0);
			result.setMessage(e.getMessage());
		}

		return result;

	}

}
