package id.co.qualitas.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.co.qualitas.component.ConsParam;
import id.co.qualitas.dao.interfaces.ReportDao;
import id.co.qualitas.domain.FilterReport;
import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.service.interfaces.ReportService;

@Service
public class ReportServiceImpl implements ReportService {
	@Autowired
	ReportDao rDao;

	@Override
	public WSMessage getFilter() {
		WSMessage result = new WSMessage();
		
		List<Object> data = rDao.getFilter();
		
		if(data.size() > 0) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}
	
	@Override
	public WSMessage cancelReport(Object request) {
		WSMessage result = new WSMessage();
		
		Boolean data = rDao.cancelReport(request);
		
		if(data) {
			result.setIdMessage(1);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}
	
	@Override
	public WSMessage TransferPosting(FilterReport filter) {
		WSMessage result = new WSMessage();
		
		List<Object> data = rDao.TrasnferPosting(filter);
		
		for(Object d : data) {
			Map<String,Object> dt = (Map<String, Object>) d;
			File files = new File(ConsParam.PATH_PDF_TP +dt.get("transferPostingNo").toString()+".pdf");
			byte[] bytesArray = null;
			String ext = null;
			if(files != null && files.length() > 0){
				bytesArray = new byte[(int) files.length()];
				ext = FilenameUtils.getExtension(files.getName()); 
				FileInputStream fis;
				try {
					fis = new FileInputStream(files);
					fis.read(bytesArray); // read file into bytes[]
					fis.close();
				} catch (FileNotFoundException e) {
					bytesArray = null;
					System.out.println("file not found");
				} catch (IOException e) {
					bytesArray = null;
					e.printStackTrace();
				}
				dt.put("pdf",bytesArray);
			}
			
		}
		
		if(data.size() > 0) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}

	@Override
	public WSMessage TransferPostingDetail(Integer id) {
		WSMessage result = new WSMessage();
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("material",rDao.TransferPostingMaterial(id));
		
		Map<String,Object> d = (Map<String, Object>) rDao.TrasnferPostingDetail(id);
		data.put("detail",d);
		
		File files = new File(ConsParam.PATH_PDF_TP +d.get("id").toString()+".pdf");
		byte[] bytesArray = null;
		String ext = null;
		if(files != null && files.length() > 0){
			bytesArray = new byte[(int) files.length()];
			ext = FilenameUtils.getExtension(files.getName()); 
			FileInputStream fis;
			try {
				fis = new FileInputStream(files);
				fis.read(bytesArray); // read file into bytes[]
				fis.close();
			} catch (FileNotFoundException e) {
				bytesArray = null;
				System.out.println("file not found");
			} catch (IOException e) {
				bytesArray = null;
				e.printStackTrace();
			}
			data.put("pdf",bytesArray);
		}
		
		if(data != null) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}
	
	@Override
	public WSMessage TransferPostingExport(FilterReport filter) {
		WSMessage result = new WSMessage();
		
		List<Object> data = rDao.TrasnferPostingForExport(filter);
		
		if(data.size() > 0) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}
	
	@Override
	public WSMessage TransferPostingConfrimation(FilterReport filter) {
		WSMessage result = new WSMessage();
		
		List<Object> data = rDao.TrasnferPostingConfirmation(filter);
		
		if(data.size() > 0) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}

	@Override
	public WSMessage TransferPostingConfrimationDetail(Integer id) {
		WSMessage result = new WSMessage();
		Map<String,Object> data = new HashMap<String, Object>();
//		data.put("status", rDao.transferPostingStatus(id));
		data.put("detail",rDao.TrasnferPostingConfirmationDetail(id));
		data.put("material",rDao.TransferPostingConfirmationMaterial(id));
		
		if(data != null) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}

	
	@Override
	public WSMessage pgiShipment(FilterReport filter) {
		WSMessage result = new WSMessage();
		
		List<Object> data = rDao.pgiShipment(filter);
		
		if(data.size() > 0) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}

	@Override
	public WSMessage pgiShipemtnDetail(Integer id) {
		WSMessage result = new WSMessage();
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("detail",rDao.pgiShipmentDetail(id));
		data.put("material",rDao.pgiShipmentMaterial(id));
		
		if(data != null) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}
	
	@Override
	public WSMessage Shipment(FilterReport filter) {
		WSMessage result = new WSMessage();
		
		List<Object> data = rDao.Shipment(filter);
		
		if(data.size() > 0) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}

	@Override
	public WSMessage ShipemtnDetail(String id) {
		WSMessage result = new WSMessage();
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("detail",rDao.ShipmentDetail(id));
		data.put("material",rDao.ShipmentMaterial(id));
		
		if(data != null) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}
	

	@Override
	public WSMessage shipmentExport(FilterReport filter) {
		WSMessage result = new WSMessage();
		
		List<Object> data = rDao.shipmentExport(filter);
		
		if(data.size() > 0) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}
	
	@Override
	public WSMessage deliveryOrder(FilterReport filter) {
		WSMessage result = new WSMessage();
		
		List<Object> data = rDao.deliveryOrder(filter);
		
		for(Object d : data) {
			Map<String,Object> dt = (Map<String, Object>) d;
			
			if(dt.get("docNo") == null) {
				continue;
			}
			
			File files = new File(ConsParam.PATH_PDF_DO +dt.get("docNo").toString()+".pdf");
			byte[] bytesArray = null;
			String ext = null;
			if(files != null && files.length() > 0){
				bytesArray = new byte[(int) files.length()];
				ext = FilenameUtils.getExtension(files.getName()); 
				FileInputStream fis;
				try {
					fis = new FileInputStream(files);
					fis.read(bytesArray); // read file into bytes[]
					fis.close();
				} catch (FileNotFoundException e) {
					bytesArray = null;
					System.out.println("file not found");
				} catch (IOException e) {
					bytesArray = null;
					e.printStackTrace();
				}
				dt.put("pdf",bytesArray);
			}
			
		}
		
		if(data.size() > 0) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}
	

	@Override
	public WSMessage deliveryOrderExport(FilterReport filter) {
		WSMessage result = new WSMessage();
		
		List<Object> data = rDao.deliveryOrderForExport(filter);
		
		if(data.size() > 0) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}

	@Override
	public WSMessage deliveryOrderDetail(String id) {
		WSMessage result = new WSMessage();
		Map<String,Object> data = new HashMap<String, Object>();
		
		data.put("material",rDao.deliveryOrderMaterial(id));
		data.put("batch",rDao.deliveryOrderBatch(id));
		
		Map<String,Object> d = (Map<String, Object>) rDao.deliveryOrderDetail(id);
		data.put("detail",d);
		
		File files = new File(ConsParam.PATH_PDF_DO +d.get("id").toString()+".pdf");
		byte[] bytesArray = null;
		String ext = null;
		if(files != null && files.length() > 0){
			bytesArray = new byte[(int) files.length()];
			ext = FilenameUtils.getExtension(files.getName()); 
			FileInputStream fis;
			try {
				fis = new FileInputStream(files);
				fis.read(bytesArray); // read file into bytes[]
				fis.close();
			} catch (FileNotFoundException e) {
				bytesArray = null;
				System.out.println("file not found");
			} catch (IOException e) {
				bytesArray = null;
				e.printStackTrace();
			}
			data.put("pdf",bytesArray);
		}
		
		
		if(data != null) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}
	
	@Override
	public WSMessage GRPOSTO(FilterReport filter) {
		WSMessage result = new WSMessage();
		
		List<Object> data = rDao.GRPOSTO(filter);
		
		if(data.size() > 0) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}

	@Override
	public WSMessage GRPOSTODetail(String id) {
		WSMessage result = new WSMessage();
		Map<String,Object> data = new HashMap<String, Object>();
		
		List<Object> material = rDao.GRPOSTOMaterial(id);
		
		for(Object mat : material) {
			Map<String,Object> m = (Map<String, Object>) mat;
			
			File files = new File(ConsParam.PATH_IMAGE_GRPOSTO +m.get("id").toString()+m.get("itemNo").toString()+".png");
			byte[] bytesArray = null;
			String ext = null;
			if(files != null && files.length() > 0){
				bytesArray = new byte[(int) files.length()];
				ext = FilenameUtils.getExtension(files.getName()); 
				FileInputStream fis;
				try {
					fis = new FileInputStream(files);
					fis.read(bytesArray); // read file into bytes[]
					fis.close();
				} catch (FileNotFoundException e) {
					bytesArray = null;
					System.out.println("file not found");
				} catch (IOException e) {
					bytesArray = null;
					e.printStackTrace();
				}
				m.put("photo",bytesArray);
			}
		}
		
		data.put("detail",rDao.GRPOSTODetail(id));
		data.put("material",material);
		
		if(data != null) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}
	
	@Override
	public WSMessage GRPOSTOExport(FilterReport filter) {
		WSMessage result = new WSMessage();
		
		List<Object> data = rDao.GRPOSTOForExport(filter);
		
		if(data.size() > 0) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}
	
	@Override
	public WSMessage GRFG(FilterReport filter) {
		WSMessage result = new WSMessage();
		
		List<Object> data = rDao.GRFG(filter);
		
		if(data.size() > 0) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}

	@Override
	public WSMessage GRFGDetail(String id) {
		WSMessage result = new WSMessage();
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("detail",rDao.GRFGDetail(id));
		data.put("material",rDao.GRFGMaterial(id));
		data.put("component",rDao.GRFGComponent(id));
		
		if(data != null) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}
	

	@Override
	public WSMessage GRFGExport(FilterReport filter) {
		WSMessage result = new WSMessage();
		
		List<Object> data = rDao.GRFGForExport(filter);
		
		if(data.size() > 0) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}

	@Override
	public WSMessage TransferPostingConfrimationExport(FilterReport filter) {
WSMessage result = new WSMessage();
		
		List<Object> data = rDao.TrasnferPostingConfirmationForExport(filter);
		
		if(data.size() > 0) {
			result.setIdMessage(1);
			result.setResult(data);
		}else {
			result.setIdMessage(0);
		}
		
		return result;
	}

	
}
