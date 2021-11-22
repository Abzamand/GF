package id.co.qualitas.dao.interfaces;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import id.co.qualitas.domain.request.DODetail;
import id.co.qualitas.domain.request.DOHeader;
import id.co.qualitas.domain.request.DORequest;
import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.response.DOHistoryResponse;
import id.co.qualitas.domain.response.WSMessage;


public interface DeliveryOrderDao {

	WSMessage create(DORequest request);

	List<DODetail> getDetailsByIdHeader(int idHeader);

//	List<DOHistoryResponse> getHeadersForHistory(Date dateFrom, Date dateTo, String docType, String username);


//	void updateStatusPodByDoNoSap(String podStatus, String podStatusDesc, String doNoSap, String pgiNoSap);

	void updateStatusPodByDoNoSap(String doNoSap, String pgiNoSap);
	
	WSMessage updateTotalVolumeWeightByDoNoSap(String doNoSap, BigDecimal bigDecimal, String uov, BigDecimal bigDecimal2, String uow);


	List<String> getListDoNoForUpdateStatusPOD(String idSloc, String idPlant);

	List<Map<String, String>> getListHeaderForSync();

	WSMessage updateFieldsAfterSynced(String idHeader, String doNoSap, String infoSync, String status, BigDecimal tweight, String wuom, BigDecimal tvolum, String vuom);

	WSMessage updateFieldsAfterSynced(String idHeader, String infoSync, String status);

	List<DODetail> getDistinctDetailsByIdHeader(int idHeader);

//	List<DODetail> getBatchsByIdAndDocItemNo(int idHeader, int docItemNo);

	List<DODetail> getBatchsByIdAndDocItemNo(int idHeader, String docItemNo);

	List<DOHistoryResponse> getHeadersForHistory(Date dateFrom, Date dateTo, String docType, String idSloc,
			String idPlant);

	List<DODetail> getBatchsById(int idHeader);

	void updateStatusPodByDoNoSap(String podStatus, String podStatusDesc, String doNoSap, String pgiNoSap,
			BigDecimal totalVolume, String uov, BigDecimal totalWeight, String uow);
	 boolean isPodStatusAlreadyCompleted(String doNoSap);

	void updateStatusPodByDoNoSap(String doNoSap, String pgiNoSap, BigDecimal totalVolume, String uov,
			BigDecimal totalWeight, String uow);

	Map<String, String> getHeaderByIdHeader(int idHeader);

//	WSMessage updateFieldsAfterSyncedByShipmentNo(String shipmentNo, String infoSync, String status);

//	List<Map<String, String>> getHeadersForSync();

	List<Map<String, String>> getShipmentNoForAssign();

//	DOHistoryResponse getHeadersForHistory(String doNoSap);

	WSMessage updateFieldsAfterSyncedByShipmentNo(String shipmentNo, String infoSync, String status, String pgiNo);

	Map<String, String> getDriverAndVehicleNoByShipmentNo(String shipmentNo);

	DOHistoryResponse getHeadersForHistory(int id);

	WSMessage updateFieldsAfterSyncedByDoNoSap(String doNoSap, String infoSync, String status, String pgiNo);

	void updateStatusPodByDoNoSap(String podStatus, String podStatusDesc, String doNoSap, String pgiNoSap,
			BigDecimal totalVolume, String uov, BigDecimal totalWeight, String uow, String status);

	void updateStatusPodByDoNoSap(String doNoSap, String pgiNoSap, BigDecimal totalVolume, String uov,
			BigDecimal totalWeight, String uow, String status);
}
