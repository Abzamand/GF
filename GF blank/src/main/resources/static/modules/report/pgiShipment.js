'user strict';
angular.module('PGIShipmentApp').controller(
		'PGIShipmentCtrl',
		[ '$scope', '$rootScope', 'Restful', 'CONSTANTS', '$rootScope','$location','$localStorage',
		function($scope, $rootScope, Restful, CONSTANTS, $rootScope,$location,$localStorage) {
			
			$scope.filter = {
				"from":new Date(),
				"to":new Date()
			}
			
			$scope.isUser = true;
			
			$scope.init = function(){
				$scope.filterReport = true;
				Restful.httpGet('/api/v1/report/getFilter?', null, null, callBackFilter, callBackErrorFilter, $scope, false, false);
			}
			
			var callBackFilter = function(response, result){
				if(response.data.idMessage > 0 ){
					$scope.tempFilter = JSON.parse(localStorage.getItem('ngStorage-listSlocPlan'));
					if($scope.tempFilter[0].idSloc != null && $scope.tempFilter[0].idPlant != null){
						var flagPlant = {};
						$scope.isUser = true;
						$scope.tempPlant = $scope.tempFilter.filter(function(entry){
							if(flagPlant[entry.idPlant]){
								return false;
								}
								flagPlant[entry.idPlant] = true;
								return true;
							});
							$scope.filter.idPlant = $scope.tempFilter[0].idPlant;
							var flagSloc = {};
							$scope.tempSloc = $scope.tempFilter.filter(function(entry){
									if(flagSloc[entry.idSloc]){
										return false;
									}
								flagSloc[entry.idSloc] = true;
								return true;
							});
							$scope.filter.idSloc = $scope.tempFilter[0].idSloc;
					}else{
							$scope.isUser = false;
							$scope.tempFilter = response.data.result;
							var flagPlant = {};
							
							$scope.tempPlant = $scope.tempFilter.filter(function(entry){
									if(flagPlant[entry.idPlant]){
										return false;
									}
									flagPlant[entry.idPlant] = true;
									return true;
								});
							
							var flagSloc = {};
							$scope.tempSloc = $scope.tempFilter.filter(function(entry){
									if(flagSloc[entry.idSloc]){
										return false;
									}
									flagSloc[entry.idSloc] = true;
									return true;
							});		
					$scope.tempStatus = ["Success","On Progress","Failed","Canceled"]
					$scope.tempDocType = ["PO STO","SOBU"]
					}
				}else{
					$('#loaderContainer').css('display','none');
					alert('Filter tidak tersedia')
				}
			}
			
			$scope.changeFilter = function(param){
				var tempData = [];
				var flagFilter = true;
				if(param == 'plant'){
					if($scope.filter.plant){
						tempData = $scope.tempFilter.filter(x => x.idPlant == $scope.filter.plant);
					}else{
						flagFilter = false;
					}
				}else if(param == 'sloc'){
					if($scope.filter.sloc){
						tempData = $scope.tempFilter.filter(x => x.idSloc == $scope.filter.sloc);
					}else{
						flagFilter = false;
					}
				}
				
				if(flagFilter)
					setFilterChanged(param, tempData);
				else if(!$scope.filter.sloc && !$scope.filter.plant)
					Restful.httpGet('/api/v1/report/getFilter?', null, null, callBackFilter, callBackErrorFilter, $scope, false, false);
			}	
			
			var setFilterChanged = function(param, tempData){
				if(param != 'plant'){
					var flagPlant = {};
					$scope.tempPlant = tempData.filter(function(entry){
							if(flagPlant[entry.idPlant]){
								return false;
							}
							flagPlant[entry.idPlant] = true;
							return true;
						});
				} 
				if(param != 'sloc'){
					var flagSloc = {};
					$scope.tempSloc = tempData.filter(function(entry){
							if(flagSloc[entry.idSloc]){
								return false;
							}
							flagSloc[entry.idSloc] = true;
							return true;
						});	
				}
			}
			
			$scope.search = function(){
				Restful.httpPost('/api/v1/report/pgiShipment',$scope.filter, 
					 null, callBackGetList, callBackErrorGetList, $scope, false, false);
			}
			
			var callBackGetList = function(response, result){
				if(response.data.idMessage > 0 ){
					$scope.list = response.data.result;
					$scope.filterReport = false;
				}else{
					$('#loaderContainer').css('display','none');
					alert('Report tidak tersedia')
				}
			}
			
			
			var callBackErrorGetList = function(){$('#loaderContainer').css('display','none');}
			var callBackErrorFilter = function(){$('#loaderContainer').css('display','none');}
} ]);