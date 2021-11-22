package id.co.qualitas.service.interfaces;

import java.util.List;
import java.util.Map;

import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.request.TransferPostingRequest;
import id.co.qualitas.domain.response.BatchNoStockResponse;
import id.co.qualitas.domain.response.StockOEMResponse;
import id.co.qualitas.domain.response.TransferPostingResponse;
import id.co.qualitas.domain.response.WSMessage;

public interface StockOEMService {

	List<StockOEMResponse> getList(String materialCode, String materialType, String idSloc, String idPlant);

	List<BatchNoStockResponse> getListBatchNoStock(String idSloc, String idPlant, String componentNo);
}
