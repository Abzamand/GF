package id.co.qualitas.dao.interfaces;

import java.util.List;
import java.util.Map;

import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.response.WSMessage;


public interface TransferPostingConfirmDao {

	WSMessage confirm(TransferPostingConfirmRequest request);

	List<Map<String, String>> getListHeaderForSync();

	List<Map<String, Object>> getListDetailBy(String idHeader);

	WSMessage updateFieldsAfterSynced(String idHeader, String infoSync, String status);

	WSMessage doUpdateAfter1stStep(String idHeader, String grNoSap, String infoSync, String status,
			String docType);

	WSMessage doUpdateAfter2ndStep(String idHeader, String stockConfirmNoSap, String infoSync, String status);

	boolean isVendorStockConfirmed(String materialNo, String batchNo);
	
}
