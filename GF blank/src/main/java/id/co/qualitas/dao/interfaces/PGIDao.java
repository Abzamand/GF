package id.co.qualitas.dao.interfaces;

import java.util.List;
import java.util.Map;

import id.co.qualitas.domain.request.DORequest;
import id.co.qualitas.domain.request.PGIRequest;
import id.co.qualitas.domain.request.ShipmentRequest;
import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.response.WSMessage;


public interface PGIDao {

	WSMessage create(PGIRequest request);
	
}
