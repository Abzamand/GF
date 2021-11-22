package id.co.qualitas.dao.interfaces;

import java.util.List;
import java.util.Map;

import id.co.qualitas.domain.request.GoodReceiptFGHeaderRequest;
import id.co.qualitas.domain.request.GoodReceiptFGRequest;
import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.response.SAP_GoodReceiptFGResponse;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.domain.webservice.goodsmvt.Bapi2017GmHead01;
import id.co.qualitas.domain.webservice.goodsmvt.Bapi2017GmItemCreate;
import id.co.qualitas.domain.webservice.goodsmvt.TableOfBapi2017GmItemCreate;


public interface GoodReceiptFGDao {

	WSMessage create(GoodReceiptFGRequest request);
	
	List<SAP_GoodReceiptFGResponse> getListHeader();
	List<Map<String, Object>> getListDetailBy(String idHeader);
	List<Map<String, Object>> getListComponentBy(String idHeader, String detailItemNo);
	
	WSMessage updateFieldsAfterSynced(String idHeader, String grNoSap, String infoSync, String status);
	WSMessage updateFieldsAfterSynced(String idHeader, String infoSync, String status);
	
}
