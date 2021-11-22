package id.co.qualitas.dao.interfaces;

import java.util.List;
import java.util.Map;

import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.request.TransferPostingRequest;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.domain.webservice.material.ZFMWBMATNRResponse;
import id.co.qualitas.domain.webservice.materialtype.ZFMWBMTARTResponse;
import id.co.qualitas.domain.webservice.plant.ZFMWBPLANTResponse;
import id.co.qualitas.domain.webservice.sloc.ZFMWBSLOCResponse;
import id.co.qualitas.domain.webservice.vendor.ZFMWBVENDORResponse;


public interface SyncMasterDataDao {


	boolean saveMaterial(ZFMWBMATNRResponse response);


	boolean saveVendor(ZFMWBVENDORResponse response);


	boolean savePlant(ZFMWBPLANTResponse response);


//	boolean saveSloc(ZFMWBVENDORResponse response);
	
}
