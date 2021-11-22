package id.co.qualitas.service.interfaces;

import java.util.Map;

import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.response.WSMessage;

public interface TransferPostingConfirmService {
	Map<String, Object> getList(String idSloc, String idPlant);
	WSMessage confirm(TransferPostingConfirmRequest request);
	WSMessage syncToSAP();
}
