package id.co.qualitas.dao.interfaces;

import java.util.List;
import java.util.Map;

import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.request.TransferPostingRequest;
import id.co.qualitas.domain.response.WSMessage;


public interface TransferPostingDao {

	WSMessage create(TransferPostingRequest request);

	boolean isDocNoCreated(String docNo, String idSloc, String idPlant, String status);

//	boolean isDocNoAndDocItemNoCreated(String docNo, String docItemNo);

	boolean isDocNoAndDocItemNoCreated(String docNo, int docItemNo);

	boolean isDocNoCreatedFromTpSender(String docNo, String idSloc, String idPlant, String status);

	boolean isAllDetailAreConfirmed(String docNo);

	boolean isDocNoAndDocItemNoCreatedTpSender(String docNo, int docItemNo);
}
