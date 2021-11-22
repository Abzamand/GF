package id.co.qualitas.service.interfaces;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import id.co.qualitas.domain.request.GoodReceiptFGRequest;
import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.response.SAP_GoodReceiptFGResponse;
import id.co.qualitas.domain.response.SAP_POSTOSOResponse;
import id.co.qualitas.domain.response.SAP_StockOEMResponse;
import id.co.qualitas.domain.response.WSMessage;

public interface SAP_POSTOSOService {

	List<SAP_POSTOSOResponse> getList(String idSloc, String idPlant, String docType);
}
