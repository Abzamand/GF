package id.co.qualitas.service.interfaces;

import java.util.List;
import java.util.Map;

import id.co.qualitas.domain.request.DORequest;
import id.co.qualitas.domain.request.PGIRequest;
import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.response.WSMessage;

public interface MaterialTypeService {
	public List<Map<String, Object>> getMasterData(String idSloc, String idPlant);
}
