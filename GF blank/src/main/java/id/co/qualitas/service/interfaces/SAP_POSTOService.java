package id.co.qualitas.service.interfaces;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import id.co.qualitas.domain.request.GoodReceiptFGRequest;
import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.response.SAP_GoodReceiptFGResponse;
import id.co.qualitas.domain.response.SAP_POSTODetailResponse;
import id.co.qualitas.domain.response.SAP_POSTOResponse;
import id.co.qualitas.domain.response.SAP_POSTOSOResponse;
import id.co.qualitas.domain.response.SAP_StockOEMResponse;
import id.co.qualitas.domain.response.WSMessage;

public interface SAP_POSTOService {
//	List<SAP_POSTOResponse> getList(String idSloc, String idPlant, String date);

	List<SAP_POSTODetailResponse> getList(String idSloc, String idPlant);
}
