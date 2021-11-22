package id.co.qualitas.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.data.rest.core.util.MapUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import id.co.qualitas.component.Cons;
import id.co.qualitas.component.ConsParam;
import id.co.qualitas.component.EmailUtils;
import id.co.qualitas.component.Utils;
import id.co.qualitas.dao.interfaces.TransferPostingConfirmDao;
import id.co.qualitas.dao.interfaces.TransferPostingDao;
import id.co.qualitas.dao.interfaces.UomConversionDao;
import id.co.qualitas.dao.interfaces.VendorDao;
import id.co.qualitas.domain.request.DOHeader;
import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.request.TransferPostingDetail;
import id.co.qualitas.domain.request.TransferPostingHeader;
import id.co.qualitas.domain.request.TransferPostingRequest;
import id.co.qualitas.domain.request.UomConversion;
import id.co.qualitas.domain.response.SAP_TransferPostingDetailResponse;
import id.co.qualitas.domain.response.SAP_TransferPostingResponse;
import id.co.qualitas.domain.response.Vendor;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.domain.webservice.tposting.TABLEOFZTYWBTPDETAIL;
import id.co.qualitas.domain.webservice.tposting.TABLEOFZTYWBTPHEADER;
import id.co.qualitas.domain.webservice.tposting.ZFMWBTP;
import id.co.qualitas.domain.webservice.tposting.ZFMWBTPResponse;
import id.co.qualitas.domain.webservice.tposting.ZTYWBTPDETAIL;
import id.co.qualitas.domain.webservice.tposting.ZTYWBTPHEADER;
import id.co.qualitas.service.interfaces.TransferPostingConfirmService;
import id.co.qualitas.service.interfaces.TransferPostingService;

@Service
public class TransferPostingServiceImpl implements TransferPostingService {
	@Autowired
	TransferPostingDao transferPostingDao;
	
	@Autowired
	VendorDao vendorDao;
	
	@Autowired
	UomConversionDao uomConversionDao;

	@Override
	public WSMessage create(TransferPostingRequest request) {
		WSMessage message = new WSMessage();

//		if (request.getHeader() != null ) {
//			if(request.getHeader().getDocNo() != null && request.getListDetail() != null && !request.getListDetail().isEmpty()) {
//				for(int i = 0; i < request.getListDetail().size(); i++) {
//					try {
//						if(request.getListDetail().get(i).getDocItemNo() != null) {
//							if(transferPostingDao.isDocNoAndDocItemNoCreatedTpSender(request.getHeader().getDocNo(), Integer.parseInt(request.getListDetail().get(i).getDocItemNo()))) {
//								message.setIdMessage(2);
//								message.setMessage(request.getHeader().getDocNo() + " " + request.getListDetail().get(i).getMaterialNo() + " has already confirmed. \nRefreshing the data..");
//					    		
//								return message;
//					    	}
//						}
//					}catch(Exception e) {
//						
//					}
//				}
//			}
//		}
		
		if (request.getHeader() == null) {
			message.setMessage("No request detected");
		} else if (request.getListDetail() == null || request.getListDetail().isEmpty()) {
			message.setMessage("This request doesn't have any detail");
		} else if (request.getHeader().getDocNo() != null && transferPostingDao.
				isDocNoCreated(request.getHeader().getDocNo(), request.getHeader().getIdSloc(), request.getHeader().getIdPlant(), Cons.TP_HEADER_STATUS_ALL)){
			message.setIdMessage(2);
			message.setMessage("Document number " + request.getHeader().getDocNo() + " has already confirmed");
		} else {
			message = transferPostingDao.create(request);
			
			if(message.getIdMessage() == 1) {
				sendEmail(request);
			}
		}

		return message;
	}

	@Override
	public List<SAP_TransferPostingResponse> getList(String idSloc, String idPlant, String username) {
		List<SAP_TransferPostingResponse> result = new ArrayList<SAP_TransferPostingResponse>();
		List<SAP_TransferPostingResponse> listHeader = new ArrayList<SAP_TransferPostingResponse>();
		List<SAP_TransferPostingDetailResponse> listDetail = new ArrayList<SAP_TransferPostingDetailResponse>();

		try {
			String idVendorSap = vendorDao.getIdVendorSapByUsername(username);
			
			ZFMWBTPResponse sapResponse = new ZFMWBTPResponse();
			ZFMWBTP request = new ZFMWBTP();

			request.setDETAIL(new TABLEOFZTYWBTPDETAIL());
			request.setHEADER(new TABLEOFZTYWBTPHEADER());

			//TODO hapus dummy nya klo data uda siap
//			request.setPBUDAT("202012");// yyyyMM
			request.setPBUDAT(Utils.getCurrentDate(Cons.DATE_FORMAT_1));

			request.setPLGORT(idSloc != null && idSloc.equals("null") ? null : idSloc);
			request.setPWERKS(idPlant);

			sapResponse = (ZFMWBTPResponse) Utils.WebserviceResponse(request, Cons.CONTEXT_TPOSTING,
					Cons.WEBSERVICE_SAP + Cons.ENDPOINT_TPOSTING, Cons.USERNAME, Cons.PASSWORD);

			if (sapResponse.getHEADER() != null && sapResponse.getHEADER().getItem() != null
					&& !sapResponse.getHEADER().getItem().isEmpty()) {

				for (ZTYWBTPHEADER sapHeader : sapResponse.getHEADER().getItem()) {
					
					if(transferPostingDao.isDocNoCreatedFromTpSender(sapHeader.getMBLNR(), idSloc, idPlant, Cons.TP_HEADER_STATUS_ON_PROGRESS)) {
						SAP_TransferPostingResponse response = new SAP_TransferPostingResponse();

						response.setDocNo(sapHeader.getMBLNR());
						response.setDocDate(sapHeader.getBUDAT());
						response.setDocHeader(sapHeader.getBKTXT());
						response.setIdSloc(sapHeader.getLGORT());
						response.setIdPlant(sapHeader.getWERKS());

						listHeader.add(response);
					}
				}
			} else {
				System.out.println("Header from sap is null or empty");
			}

			if (sapResponse.getDETAIL() != null && sapResponse.getDETAIL().getItem() != null
					&& !sapResponse.getDETAIL().getItem().isEmpty()) {

				for (ZTYWBTPDETAIL sapDetail : sapResponse.getDETAIL().getItem()) {

					if(transferPostingDao.isDocNoCreatedFromTpSender(sapDetail.getMBLNR(), idSloc, idPlant, Cons.TP_HEADER_STATUS_ON_PROGRESS)) {
						SAP_TransferPostingDetailResponse detail = new SAP_TransferPostingDetailResponse();
						detail.setDocNo(sapDetail.getMBLNR());
						detail.setDocItemNo(Utils.trimLeadingZeroes(sapDetail.getLINEID(), true));
						detail.setMaterialNo(Utils.trimLeadingZeroes(sapDetail.getMATNR()));
						detail.setMaterialDesc(sapDetail.getMAKTX());
						detail.setBatchNo(sapDetail.getCHARG());
						detail.setStockSap(sapDetail.getMENGE());
						detail.setUom(sapDetail.getMEINS());
						detail.setUomSap(sapDetail.getMEINS());
						
						WSMessage msgConversion = uomConversionDao.getConversion(idVendorSap, detail.getMaterialNo(), detail.getStockSap(), detail.getUom());
						
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
				}
			} else {
				System.out.println("Detail from sap is null or empty");
			}
			
			if(listHeader != null && !listHeader.isEmpty() && listDetail != null && !listDetail.isEmpty()) {
				result = combineList(listHeader, listDetail);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	private List<SAP_TransferPostingResponse> combineList(List<SAP_TransferPostingResponse> listHeader, List<SAP_TransferPostingDetailResponse> listDetail){
		
		Map<String, SAP_TransferPostingResponse> headerMap = new HashMap<>(listHeader.size());
		for (SAP_TransferPostingResponse header : listHeader) {

			//todo cek kalo detail di header ini uda ke create semua atau blm
			//kalo uda header nya ga d mnculin lagi
			if(transferPostingDao.isAllDetailAreConfirmed(header.getDocNo())) {
				continue;
			}
			headerMap.put(header.getDocNo(), header);
		}

		for (SAP_TransferPostingDetailResponse detail : listDetail) {
			SAP_TransferPostingResponse header = headerMap.get(detail.getDocNo());
			
		    if (header != null) {
		    	if(header.getListDetail() == null) {
		    		header.setListDetail(new ArrayList<SAP_TransferPostingDetailResponse>());
		    	}
		    	
		    	//cek kalo di detail uda ada doc_no dan doc_item_no
		    	if(transferPostingDao.isDocNoAndDocItemNoCreated(header.getDocNo(), Integer.parseInt(detail.getDocItemNo()))) {
		    		continue;
		    	}

	    		header.getListDetail().add(detail);
		    }
		}
		
		return new ArrayList<>(headerMap.values());
	}
	

	public WSMessage sendEmail(TransferPostingRequest request) {
		WSMessage message = new WSMessage();
		
		TransferPostingHeader header = request.getHeader();
		
		Vendor oemFromDetail = vendorDao.getVendorDetail(header.getOemFrom(), header.getIdPlant());
		Vendor oemToDetail = vendorDao.getVendorDetail(header.getOemTo(), header.getIdPlant());
		

		header.setDestinationName("Tester");
//		header.setDestinationEmail("andi@qualitas.co.id");
//		if(oemToDetail != null) {
//			header.setDestinationName(oemToDetail.getFullName() != null ? oemToDetail.getFullName() : "");
//			header.setDestinationEmail(oemToDetail.getEmail());
//		}
//		header.setDestinationEmail("thomz.q@gmail.com");
//		header.setDestinationEmail("andi.lin1991@gmail.com");
//		header.setDestinationEmail("willyspheal@gmail.com");
		header.setDestinationEmail("testerwilxoxo@gmail.com");
		
//		header.setDestinationName("Priska");
//		header.setDestinationEmail("priska@qualitas.co.id");
		
		if(header.getDestinationEmail() == null) {
			message.setIdMessage(0);
			message.setMessage("Destination email not found");
			return message;
		}

		String recipientName = header.getDestinationName();
		String recipientEmail = header.getDestinationEmail();

		String subject = "Garuda Food OEM (TEST)";
		
		String transferPostingNo = header.getDocNo() != null ? "SJ" + header.getDocNo() : "-";
		
		String plantPengirim = oemFromDetail != null && oemFromDetail.getPlantName() != null ? oemFromDetail.getPlantName() : "-";
		String alamatPengirim = oemFromDetail != null && oemFromDetail.getAddress() != null ? oemFromDetail.getAddress() : "-";
		String noTlpPengirim = oemFromDetail != null && oemFromDetail.getPhone() != null ? oemFromDetail.getPhone() : "-";
		
		String plantPenerima = oemToDetail != null && oemToDetail.getPlantName() != null ? oemToDetail.getPlantName() : "-";
		String alamatPenerima = oemToDetail != null && oemToDetail.getAddress() != null ? oemToDetail.getAddress() : "-";
		String noTlpPenerima = oemToDetail != null && oemToDetail.getPhone() != null ? oemToDetail.getPhone() : "-";
		
		String detailTable = "";

		String currentDate = Utils.getCurrentDate("E, dd MMM yyyy hh:mm a");
		
		if(request.getListDetail() != null && !request.getListDetail().isEmpty()) {
			for(int i = 0 ; i < request.getListDetail().size(); i++) {
				TransferPostingDetail tpDetail = request.getListDetail().get(i);
				
				detailTable = detailTable 
						+ "<tr> "
						+ "      <td style=\""
						+ "  border: 1px solid #dddddd;"
						+ "  text-align: left;"
						+ "  padding: 8px;\">" + (i+1) + "</td>"
						+ "      <td style=\""
						+ "  border: 1px solid #dddddd;"
						+ "  text-align: left;"
						+ "  padding: 8px;\">" + (tpDetail.getMaterialNo() != null ? tpDetail.getMaterialNo() : "") + "</td>"
						+ "      <td style=\""
						+ "  border: 1px solid #dddddd;"
						+ "  text-align: left;"
						+ "  padding: 8px;\">" + (tpDetail.getMaterialDesc() != null ? tpDetail.getMaterialDesc() : "")  + "</td>"
						+ "      <td style=\""
						+ "  border: 1px solid #dddddd;"
						+ "  text-align: left;"
						+ "  padding: 8px;\">" + (tpDetail.getUom() != null ? tpDetail.getUom() : "") + "</td>"
						+ "      <td style=\""
						+ "  border: 1px solid #dddddd;"
						+ "  text-align: left;"
						+ "  padding: 8px;\">" + (tpDetail.getQty() != null ? String.valueOf(tpDetail.getQty()) : "0") + "</td>"
						+ "</tr>";
			}
		}
		
		
		String body = ""
				+ "<body >"
				+ "<!-- Start your code here -->"
				+ "<div style=\"border-style: solid;padding-left:50px;padding-right:50px;padding-top:30px;padding-bottom:30px;line-height: 1.2;font-family:Calibri;font-size:11pt\">  "
				+ "<div>"
				+ "	<p>"+ currentDate +"</p>"
				+ "</div>"
				+ ""
				+ "    <br/>"
				+ "<div>"
				+ "	<h2 style=\"text-align:center;\">TRANSFER POSTING ANTAR OEM</h2>"
				+ "</div>"
				+ "    "
				+ "    <br/>"
				+ ""
				+ "<div>"
				+ "  <p>Email ini menginformasikan Transfer Posting antar OEM dengan detail sebagai berikut: </p>"
				+ "</div>"
				+ ""
				+ "    <br/>"
				+ "    "
				+ "    <div style=\"margin-right:40px\">"
				+ "        <p style=\"font-weight: bold;\">Transfer posting no = " + transferPostingNo +" </p>"
				+ "      "
				+ "      <br/>"
				+ ""
				+ "    <div  style=\"width: 100%;\">"
				+ "      <p style=\"font-weight: bold;\">Pengiriman dari = " + plantPengirim + "</p>"
				+ "        <p style=\"margin-left:5em\">" + alamatPengirim +"</p>"
				+ "        <p style=\"margin-left:5em\">No tlp: " + noTlpPengirim + "</p>"
				+ "      <br/>"
				+ "        <p style=\"font-weight: bold;\">Tujuan ke = "+ plantPenerima +"</p>"
				+ "        <p style=\"margin-left:5em\">" + alamatPenerima + "</p>"
				+ "        <p style=\"margin-left:5em\">No tlp: " + noTlpPenerima + "</p>"
				+ "  </div>"
				+ "</div>"
				+ ""
				+ "    <br/>"
				+ "  "
				+ "<div>"
				+ "  <p>Email ini juga melampirkan dokumen, silahkan download untuk detail produk yang dikirim.</p>"
				+ "</div>"
				+ ""
				+ "</div>"
				+ "  "
				+ "<!-- End your code here -->"
				+ "  <br/>"
				+ "  "
				+ "  "
				+ "<div style=\"border-style: solid;padding-left:50px;padding-right:50px;padding-top:30px;padding-bottom:30px;line-height: 1.2;font-family:Calibri;font-size:11pt\">  "
				+ "  <div>"
				+ "	<p>Wednesday, February 17,2021 9:28 AM</p>"
				+ "</div>"
				+ ""
				+ "    <br/>"
				+ "<div>"
				+ "	<h2 style=\"text-align:center;\">Detail Produk</h2>"
				+ "</div>"
				+ "    "
				+ "    <br/>"
				+ ""
				+ "<div>"
				+ "  <p>Berikut detail produk: </p>"
				+ "</div>"
				+ ""
				+ "    <br/>"
				+ "  "
				+ "<div> "
				
				+ "<table style=\"width:100%;font-family: arial, sans-serif;"
				+ "  border-collapse: collapse;"
				+ "  width: 100%;\">"
				+ "    <tr>"
				+ "      <th style=\""
				+ "  border: 1px solid #dddddd;"
				+ "  text-align: left;"
				+ "  padding: 8px;\">No.</th>"
				+ "      <th style=\""
				+ "  border: 1px solid #dddddd;"
				+ "  text-align: left;"
				+ "  padding: 8px;\">Kode item</th>"
				+ "      <th style=\""
				+ "  border: 1px solid #dddddd;"
				+ "  text-align: left;"
				+ "  padding: 8px;\">Nama item</th>"
				+ "      <th style=\""
				+ "  border: 1px solid #dddddd;"
				+ "  text-align: left;"
				+ "  padding: 8px;\">Satuan</th>"
				+ "      <th style=\""
				+ "  border: 1px solid #dddddd;"
				+ "  text-align: left;"
				+ "  padding: 8px;\">Jumlah</th>"
				+ "    </tr>"
				+ "    "
				+ detailTable
				+ "    "
				+ "  </table>"
				+ "</div>"
				+ "  </div>"
				+ "  "
				+ "</body>";
		
		byte[] fileByte = null;                                                                

		File tempFile = null;
		try {
			if (header.getAttachment() != null) {
				fileByte = Base64.decodeBase64(header.getAttachment());
				String prefix = ConsParam.PATH_PDF_TP;
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
			
			if(isSent) {
				message.setIdMessage(1);
				message.setMessage("Email sent to " + header.getDestinationEmail());
			}else {
				message.setIdMessage(0);
				message.setMessage("Failed to send email");
			}
		} catch (Exception e) {
			message.setIdMessage(0);
			message.setMessage(e.getMessage());
		}
		
		return message;
	}
}
