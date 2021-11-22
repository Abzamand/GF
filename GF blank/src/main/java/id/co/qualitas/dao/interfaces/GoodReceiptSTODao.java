package id.co.qualitas.dao.interfaces;

import java.util.List;
import java.util.Map;

import id.co.qualitas.domain.request.GoodReceiptSTORequest;
import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.request.TransferPostingRequest;
import id.co.qualitas.domain.response.WSMessage;


public interface GoodReceiptSTODao {

	WSMessage create(GoodReceiptSTORequest request);

//	boolean isDoItemNoCreated(int doItemNo);

	boolean isDoItemNoCreated(String docNo, int doItemNo);
	
}
