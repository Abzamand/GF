package id.co.qualitas.service.interfaces;

import java.util.Map;

import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.request.TransferPostingRequest;
import id.co.qualitas.domain.response.WSMessage;

public interface SyncMasterDataService {

	WSMessage syncMasterData();
	 WSMessage plantMasterData();
}
