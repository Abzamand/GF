package id.co.qualitas.service.interfaces;

import java.util.Map;

import id.co.qualitas.domain.request.GoodReceiptFGRequest;
import id.co.qualitas.domain.request.GoodReceiptSTORequest;
import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.response.WSMessage;

public interface GoodReceiptSTOService {
	WSMessage create(GoodReceiptSTORequest request);
}
