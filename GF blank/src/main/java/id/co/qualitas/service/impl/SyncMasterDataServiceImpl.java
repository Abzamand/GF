package id.co.qualitas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import id.co.qualitas.component.Cons;
import id.co.qualitas.component.Utils;
import id.co.qualitas.dao.interfaces.SyncMasterDataDao;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.domain.webservice.material.TABLEOFZTYWBMATNR;
import id.co.qualitas.domain.webservice.material.ZFMWBMATNR;
import id.co.qualitas.domain.webservice.material.ZFMWBMATNRResponse;
import id.co.qualitas.domain.webservice.plant.TABLEOFZTYWBPLANT;
import id.co.qualitas.domain.webservice.plant.ZFMWBPLANT;
import id.co.qualitas.domain.webservice.plant.ZFMWBPLANTResponse;
import id.co.qualitas.domain.webservice.vendor.TABLEOFZTYWBVENDOR;
import id.co.qualitas.domain.webservice.vendor.ZFMWBVENDOR;
import id.co.qualitas.domain.webservice.vendor.ZFMWBVENDORResponse;
import id.co.qualitas.service.interfaces.SyncMasterDataService;

@Service
public class SyncMasterDataServiceImpl implements SyncMasterDataService {
	@Autowired
	SyncMasterDataDao syncMasterDataDao;

	@Override
	public WSMessage syncMasterData() {
		WSMessage message = new WSMessage();
		String plantResult = getPlant();
		String matResult = getMaterial();
		String vendorResult = getVendor();
		message.setIdMessage(1);
		message.setMessage(matResult + "\r\n" + vendorResult + "\r\n" + plantResult);
		return message;
	}
	
	public WSMessage plantMasterData() {
		WSMessage message = new WSMessage();
		
		message.setMessage(getPlant());
		return message;
	}

	public String getPlant() {
		String message = null;
		ZFMWBPLANT request = new ZFMWBPLANT();
		request.setPWERKS("");
		request.setRESULT(new TABLEOFZTYWBPLANT());
		ZFMWBPLANTResponse response = new ZFMWBPLANTResponse();
		boolean result = true;
		try {
			response = (ZFMWBPLANTResponse) Utils.WebserviceResponse(request, Cons.CONTEXT_PLANT,
					Cons.WEBSERVICE_SAP + Cons.ENDPOINT_PLANT, Cons.USERNAME, Cons.PASSWORD);
			result = syncMasterDataDao.savePlant(response);
			if (result)
				message = "Insert Plant success";
			else
				message = "Insert Plant failed";
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
			message = e.getMessage();
		}
		return message;
	}



//	public String getSloc() {
//		String message = null;
//		ZFMWBVENDOR request = new ZFMWBVENDOR();
//
//		request.setPLIFNR("");
//		request.setRESULT(new TABLEOFZTYWBVENDOR());
//
//		ZFMWBVENDORResponse response = new ZFMWBVENDORResponse();
//		boolean result = true;
//		try {
//			response = (ZFMWBVENDORResponse) Utils.WebserviceResponse(request, Cons.CONTEXT_SLOC,
//					Cons.WEBSERVICE_SAP + Cons.ENDPOINT_SLOC, Cons.USERNAME, Cons.PASSWORD);
//			result = syncMasterDataDao.saveSloc(response);
//			if (result)
//				message = "Insert Sloc success";
//			else
//				message = "Insert Sloc failed";
//		} catch (Exception e) {
//			e.printStackTrace();
//			result = false;
//			message = e.getMessage();
//		}
//		return message;
//	}


	public String getMaterial() {
		String message = null;
		ZFMWBMATNR request = new ZFMWBMATNR();

		request.setPMATNR("");
		;
		request.setRESULT(new TABLEOFZTYWBMATNR());

		ZFMWBMATNRResponse response = new ZFMWBMATNRResponse();
		boolean result = true;
		try {
			response = (ZFMWBMATNRResponse) Utils.WebserviceResponse(request, Cons.CONTEXT_MATERIAL,
					Cons.WEBSERVICE_SAP + Cons.ENDPOINT_MATERIAL, Cons.USERNAME, Cons.PASSWORD);
			result = syncMasterDataDao.saveMaterial(response);
			if (result)
				message = "Insert Material success";
			else
				message = "Insert Material failed";
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
			message = e.getMessage();
		}
		return message;
	}

	public String getVendor() {
		String message = null;
		ZFMWBVENDOR request = new ZFMWBVENDOR();

		request.setPLIFNR("");
		request.setRESULT(new TABLEOFZTYWBVENDOR());

		ZFMWBVENDORResponse response = new ZFMWBVENDORResponse();
		boolean result = true;
		try {
			response = (ZFMWBVENDORResponse) Utils.WebserviceResponse(request, Cons.CONTEXT_VENDOR,
					Cons.WEBSERVICE_SAP + Cons.ENDPOINT_VENDOR, Cons.USERNAME, Cons.PASSWORD);
			result = syncMasterDataDao.saveVendor(response);
			
			if (result)
				message = "Insert Vendor success";
			else
				message = "Insert Vendor failed";
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
			message = e.getMessage();
		}
		return message;
	}
	

}
