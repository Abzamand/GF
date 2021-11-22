//package id.co.qualitas.scheduler;
//
//import id.co.qualitas.domain.response.WSMessage;
//import id.co.qualitas.service.interfaces.DeliveryOrderService;
//import id.co.qualitas.service.interfaces.GoodReceiptFGService;
//import id.co.qualitas.service.interfaces.PGIService;
//import id.co.qualitas.service.interfaces.SyncMasterDataService;
//import id.co.qualitas.service.interfaces.TransferPostingConfirmService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//
//@Configuration
//@EnableScheduling
//public class GfJobScheduler{
//	
//	
//	@Autowired
//	TransferPostingConfirmService tpConfirmService;
//	
//	@Autowired
//	GoodReceiptFGService greceiptFgService;
//	
//	@Autowired
//	DeliveryOrderService deliveryOrderService;
//	
//	@Autowired
//	PGIService pgiService;
//	
//	@Autowired
//	private SyncMasterDataService syncMasterDataService;
//	
//	@Scheduled(cron = "${cron.gfjob.syncMasterData0000}")
//	public void syncMasterData0000(){
//		System.out.println("Sync Master Data started at " + new java.util.Date());
//
//		
//		WSMessage message = new WSMessage();
//		
//		message = syncMasterDataService.syncMasterData();
//		
//		System.out.println(message.getMessage());
//
//		System.out.println("Sync Master Data ended at " + new java.util.Date());
//	}
//	
////	@Scheduled(cron = "${cron.gfjob.syncMasterData1800}")
////	public void syncMasterData1800(){
////		System.out.println("Sync Master Data started at " + new java.util.Date());
////
////		
////		WSMessage message = new WSMessage();
////		
////		message = syncMasterDataService.syncMasterData();
////		
////		System.out.println(message.getMessage());
////
////		System.out.println("Sync Master Data ended at " + new java.util.Date());
////	}
////	
////	@Scheduled(cron = "${cron.gfjob.exportTpConfirmToSAP}")
////	public void exportTpConfirmToSAP(){
////		System.out.println("Sync Transfer Posting Confirm started at " + new java.util.Date());
////
////		
////		WSMessage message = new WSMessage();
////		
////		message = tpConfirmService.syncToSAP();
////		
////		System.out.println(message.getMessage());
////
////		System.out.println("Sync Transfer Posting Confirm ended at " + new java.util.Date());
////	}
////	
////	@Scheduled(cron = "${cron.gfjob.exportGrFgToSAP}")
////	public void exportGrFgToSAP(){
////		System.out.println("Sync GR_fg to SAP started at " + new java.util.Date());
////
////		WSMessage message = new WSMessage();
////		
////		message = greceiptFgService.syncToSAP();
////		
////		System.out.println(message.getMessage());
////		
////		System.out.println("Sync GR_FG ended at " + new java.util.Date());
////	}
////
////	@Scheduled(cron = "${cron.gfjob.exportDoToSAP}")
////	public void exportDoToSAP(){
////		System.out.println("Sync Delivey Order started at " + new java.util.Date());
////
////		WSMessage message = new WSMessage();
////		
////		message = deliveryOrderService.syncToSAP();
////		
////		System.out.println(message.getMessage());
////		
////		System.out.println("Sync Delivery Order ended at " + new java.util.Date());
////	}
////
////	@Scheduled(cron = "${cron.gfjob.exportShipmentToSAP}")
////	public void exportShipmentToSAP(){
////		System.out.println("Sync Assign DO started at " + new java.util.Date());
////		
////		WSMessage message = new WSMessage();
////		
////		message = deliveryOrderService.assignDo();
////		
////		System.out.println(message.getMessage());
////
////		System.out.println("Sync Assign DO ended at " + new java.util.Date());
////	}
////
////	@Scheduled(cron = "${cron.gfjob.updateStatusPODToSAP}")
////	public void updateStatusPODToSAP(){
////		System.out.println("Sync Update Status POD to SAP started at " + new java.util.Date());
////
////		WSMessage message = new WSMessage();
////		
////		message = deliveryOrderService.updateStatusPOD("%%", "%%");
////		
////		System.out.println(message.getMessage());
////		
////		System.out.println("Sync Update Status POD to SAP ended at " + new java.util.Date());
////	}
//	
//}
