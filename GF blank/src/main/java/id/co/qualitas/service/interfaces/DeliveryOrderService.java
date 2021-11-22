package id.co.qualitas.service.interfaces;

import java.util.Date;
import java.util.Map;

import id.co.qualitas.domain.request.DOHeader;
import id.co.qualitas.domain.request.DORequest;
import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.response.WSMessage;

public interface DeliveryOrderService {
	WSMessage create(DORequest request);

	WSMessage getListForHistory(String dateFrom, String dateTo, String docType, String username, String idSloc, String idPlant);

	WSMessage updateStatusPOD(String idSloc, String idPlant);

	WSMessage syncToSAP();

	WSMessage sendEmail(DOHeader header);

	WSMessage syncToSAP(int idHeader);

	WSMessage assignDo();
}
