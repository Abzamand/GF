package id.co.qualitas.dao.interfaces;

import java.util.List;
import java.util.Map;

import id.co.qualitas.domain.request.DORequest;
import id.co.qualitas.domain.request.PGIRequest;
import id.co.qualitas.domain.request.ShipmentRequest;
import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.response.WSMessage;


public interface ShipmentDao {

	WSMessage create(PGIRequest request);

	List<Map<String, String>> getDetailsByShipmentNo(String shipmentNo);

	List<Map<String, String>> getHeadersForSync();

	WSMessage updateFieldsAfterSynced(String shipmentNo, String infoSync, String status);

	boolean isShipmentNoCreated(String shipmentNo, String idSloc, String idPlant, String status);
	
}
