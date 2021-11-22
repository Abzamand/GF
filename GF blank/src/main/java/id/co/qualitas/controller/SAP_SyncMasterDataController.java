package id.co.qualitas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import id.co.qualitas.domain.response.WSMessage;
import id.co.qualitas.service.interfaces.SyncMasterDataService;

@RestController
@RequestMapping("api/v1/syncMasterData/")
public class SAP_SyncMasterDataController extends BaseController {
	
	@Autowired
	private SyncMasterDataService syncMasterDataService;
	
	
	@RequestMapping(value = "/sync", method = RequestMethod.POST)
	public WSMessage sync() {
		
		return syncMasterDataService.syncMasterData();
	}

	@RequestMapping(value = "/plant", method = RequestMethod.POST)
	public WSMessage plant() {
		
		return syncMasterDataService.plantMasterData();
	}
}
