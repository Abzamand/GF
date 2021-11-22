package id.co.qualitas.service.interfaces;

import java.util.Map;

import id.co.qualitas.domain.request.DOHeader;
import id.co.qualitas.domain.request.DORequest;
import id.co.qualitas.domain.request.PGIRequest;
import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.response.WSMessage;

public interface PGIService {
	WSMessage create(PGIRequest request);

	WSMessage syncToSAP();


	WSMessage assignShipment(DORequest request);
}
