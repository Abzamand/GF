package id.co.qualitas.service.interfaces;

import java.util.List;
import java.util.Map;

import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.request.TransferPostingRequest;
import id.co.qualitas.domain.response.SAP_BatchNoStockResponse;
import id.co.qualitas.domain.response.SAP_StockOEMResponse;
import id.co.qualitas.domain.response.SAP_TransferPostingResponse;
import id.co.qualitas.domain.response.WSMessage;

public interface SAP_StockOEMService {

	List<SAP_StockOEMResponse> getList(String materialCode, String materialType, String idSloc, String idPlant, String username);

	List<SAP_BatchNoStockResponse> getListBatchNoStock(String idSloc, String idPlant, String componentNo, String menu, String username);

	List<SAP_StockOEMResponse> getListVendorStock(String idSloc, String idPlant, String username);

	List<SAP_StockOEMResponse> getListStockForDelivery(String idSloc, String idPlant, String materialNo);
}
