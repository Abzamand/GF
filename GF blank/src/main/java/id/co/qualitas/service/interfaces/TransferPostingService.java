package id.co.qualitas.service.interfaces;

import java.util.List;
import java.util.Map;

import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.request.TransferPostingRequest;
import id.co.qualitas.domain.response.SAP_TransferPostingResponse;
import id.co.qualitas.domain.response.WSMessage;

public interface TransferPostingService {
	WSMessage create(TransferPostingRequest request);

	List<SAP_TransferPostingResponse> getList(String idSloc, String idPlant, String username);
}
