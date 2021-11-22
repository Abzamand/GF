package id.co.qualitas.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.data.rest.core.util.MapUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import id.co.qualitas.component.Cons;
import id.co.qualitas.component.Utils;
import id.co.qualitas.dao.interfaces.MaterialDao;
import id.co.qualitas.dao.interfaces.MaterialTypeDao;
import id.co.qualitas.dao.interfaces.TransferPostingConfirmDao;
import id.co.qualitas.dao.interfaces.TransferPostingDao;
import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.request.TransferPostingRequest;
import id.co.qualitas.domain.response.SAP_TransferPostingDetailResponse;
import id.co.qualitas.domain.response.SAP_TransferPostingResponse;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.domain.webservice.tposting.TABLEOFZTYWBTPDETAIL;
import id.co.qualitas.domain.webservice.tposting.TABLEOFZTYWBTPHEADER;
import id.co.qualitas.domain.webservice.tposting.ZFMWBTP;
import id.co.qualitas.domain.webservice.tposting.ZFMWBTPResponse;
import id.co.qualitas.domain.webservice.tposting.ZTYWBTPDETAIL;
import id.co.qualitas.domain.webservice.tposting.ZTYWBTPHEADER;
import id.co.qualitas.service.interfaces.MaterialService;
import id.co.qualitas.service.interfaces.MaterialTypeService;
import id.co.qualitas.service.interfaces.TransferPostingConfirmService;
import id.co.qualitas.service.interfaces.TransferPostingService;

@Service
public class MaterialTypeServiceImpl implements MaterialTypeService {
	@Autowired
	MaterialTypeDao dao;

	@Override
	public List<Map<String, Object>> getMasterData(String idSloc, String idPlant) {
		
		return dao.getMasterData(idSloc, idPlant);
	}

}
