package id.co.qualitas.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.data.rest.core.util.MapUtils;
import org.springframework.stereotype.Service;

import id.co.qualitas.dao.interfaces.GoodReceiptFGDao;
import id.co.qualitas.dao.interfaces.GoodReceiptSTODao;
import id.co.qualitas.dao.interfaces.TransferPostingConfirmDao;
import id.co.qualitas.domain.request.GoodReceiptFGRequest;
import id.co.qualitas.domain.request.GoodReceiptSTORequest;
import id.co.qualitas.domain.request.TransferPostingConfirmRequest;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.service.interfaces.GoodReceiptFGService;
import id.co.qualitas.service.interfaces.GoodReceiptSTOService;
import id.co.qualitas.service.interfaces.TransferPostingConfirmService;

@Service
public class GoodReceiptSTOServiceImpl implements GoodReceiptSTOService {
	@Autowired
	GoodReceiptSTODao grSTODao;

	@Override
	public WSMessage create(GoodReceiptSTORequest request) {
		WSMessage message = new WSMessage();

//		String string = "2021-02-18";
//		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//		Date date = null;
//		try {
//			date = format.parse(string);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		request.getHeader().setDate(date);
//		request.getHeader().setDocDate("2021-01-27");
//		request.getHeader().setIdSloc("3031");
//		request.getHeader().setIdPlant("3014");
		if (request.getHeader() == null) {
			message.setMessage("No request detected");
		} else if (request.getListDetail() == null || request.getListDetail().isEmpty()) {
			message.setMessage("This request doesn't have any detail");
		} else {
			message = grSTODao.create(request);
		}

		return message;
	}

}
