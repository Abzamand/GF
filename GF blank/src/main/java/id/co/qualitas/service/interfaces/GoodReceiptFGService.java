package id.co.qualitas.service.interfaces;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import id.co.qualitas.domain.request.GoodReceiptFGRequest;
import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.response.SAP_GoodReceiptFGResponse;
import id.co.qualitas.domain.response.SAP_StockOEMResponse;
import id.co.qualitas.domain.response.WSMessage;

public interface GoodReceiptFGService {
	WSMessage create(GoodReceiptFGRequest request);
	WSMessage syncToSAP();

	List<SAP_GoodReceiptFGResponse> getList(String idSloc, String idPlant);
}
