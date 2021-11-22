'user strict';
angular.module('SyncApp').controller(
	'SyncCtrl',
	[ '$scope', '$rootScope', 'Restful', 'CONSTANTS', '$rootScope','$location','$localStorage',
	function($scope, $rootScope, Restful, CONSTANTS, $rootScope,$location,$localStorage) {
			
			$scope.sync = function(param){
				var link = "";
				if(param == "masterData"){
					link = "api/v1/syncMasterData/sync";
				}else if(param == "tpConfirm"){
					link = "api/v1/tposting_confirm/syncToSAP";
				}else if(param == "grFg"){
					link = "api/v1/greceipt_fg/syncToSAP";
				}else if(param == "deliveryOrder"){
					link = "api/v1/delivery_order/syncToSAP";
				}else if(param == "pgiShipment"){
					link = "api/v1/shipment/syncToSAP";
				}else if(param == "oneTimeCustomer"){
					link = "api/v1/delivery_order/updateStatusPOD?idSloc=%25%25&idPlant=%25%25";
				}else if(param == "plant"){
					link = "api/v1/syncMasterData/plant";
				}else if(param == "assign"){
					link = "api/v1/delivery_order/assign";
				}
				
				Restful.httpPost(link,null,null,
					callBackSync,callBackErrorSync,$scope,false,false);
				$('#loaderContainer').css('display','block');	
			}
			
			var callBackSync = function(response,scope){
				$('#loaderContainer').css('display','none');
				if(response.data.idMessage == 1){
					alert(response.data.message)
				}else{
					alert(response.data.message)
				}
				
			}
			var callBackErrorSync = function(){$('#loaderContainer').css('display','none');}
			
} ]);