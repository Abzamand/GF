'user strict';
angular.module('GoodReceiptPOSTOApp').controller(
		'GoodReceiptPOSTOCtrl',
		[ '$scope', '$rootScope', 'Restful', 'CONSTANTS', '$rootScope','$location','$localStorage',
		function($scope, $rootScope, Restful, CONSTANTS, $rootScope,$location,$localStorage) {

			const EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
			// Diganti Buat CSV
			const EXCEL_EXTENSION = '.csv';
			$scope.isUser = true;
			$scope.tempStatus = ["Completed","On Progress","Failed"]
			
			$scope.tempDocType = ["PO STO","SOBU"]
			$scope.clearFilter = function(){
				$rootScope.filter.search ={
					"id":"",
					"date":"",
					"idSloc":"",
					"idPlant":"",
					"plantPenerima":"",
					"poStoNo":"",
					"poStoDate":"",
					"status":""
				}
			}
			$scope.init = function(){
				if($rootScope.filter.isChange){
					$scope.filterReport = false;
					$scope.getRequest(0);
				}else{
					$rootScope.filter.search = {
						"id":"",
						"date":"",
						"idSloc":"",
						"idPlant":"",
						"plantPenerima":"",
						"poStoNo":"",
						"poStoDate":"",
						"status":""
					};
					$scope.filterReport = true;
					Restful.httpGet('/api/v1/report/getFilter?', null, null, callBackFilter, callBackErrorFilter, $scope, false, false);
				}
				
			}
			
			var callBackFilter = function(response, result){
				if(response.data.idMessage > 0 ){
					$scope.tempFilter = JSON.parse(localStorage.getItem('ngStorage-listSlocPlan'));
					if($scope.tempFilter[0].idPlant != null){
						var flagPlant = {};
						$scope.isUser = true;
						$scope.tempPlant = $scope.tempFilter.filter(function(entry){
							if(flagPlant[entry.idPlant]){
								return false;
								}
								flagPlant[entry.idPlant] = true;
								return true;
						});
						
						if($rootScope.isVendor && $scope.tempFilter[0].idPlant != null){
							$scope.filter.search.idPlant = $scope.tempFilter[0].idPlant;
						}else{
							$scope.filter.search.plantPenerima = $scope.tempFilter[0].idPlant;
						}
						
						var flagSloc = {};
						$scope.tempSloc = $scope.tempFilter.filter(function(entry){
								if(flagSloc[entry.idSloc]){
									return false;
								}
							flagSloc[entry.idSloc] = true;
							return true;
						});
						
						if($scope.tempFilter[0].idSloc != null){
							$scope.filter.search.idSloc = $scope.tempFilter[0].idSloc;
						}	
						
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
						
					}
					
						$scope.tempStatus = ["Completed","On Progress","Failed"]
						
						$scope.tempDocType = ["PO STO","SOBU"]
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
				$rootScope.filter.isChange = true;
				$scope.getRequest(0);
			}
			
			$scope.getRequest = function(data){
				$scope.filter.curPage = data;
				
				var callBackGetList = function(response, result){
				if(response.data.idMessage > 0 ){
					$scope.filterReport = false;
					$scope.list = response.data.result;
					/*paging*/
					var totalPage=Math.ceil($scope.list[0].totalPage / 15);
					var currentPage, startPage, endPage;
					currentPage = data;
					startPage = currentPage - 5;
		            endPage = currentPage + 4;
		            if (startPage <= 0)
		            {
		                endPage -= (startPage - 1);
		                startPage = 1;
		            }
		            if (endPage > totalPage)
		            {
		                endPage = totalPage;
		                if (endPage > 10)
		                {
		                    startPage = endPage - 9;
		                }
		            }
		            $scope.listPage = [];
		            for(var i = startPage; i <= endPage; i++){
		            	$scope.listPage.push(i);
		            }
		            $scope.listPage.currentPage = currentPage;
		            $scope.listPage.totalPage = totalPage;
		            $scope.listPage.endPage = endPage;
		            $scope.listPage.startPage = startPage;
		            /*-----*/
				}else{
					$('#loaderContainer').css('display','none');
					alert('Report tidak tersedia')
				}
			}
				var callBackErrorGetList = function(){$('#loaderContainer').css('display','none');}
				Restful.httpPost('/api/v1/report/grposto',$scope.filter, 
					 null, callBackGetList, callBackErrorGetList, $scope, false, false);
		}
		
		var callBackErrorFilter = function(){$('#loaderContainer').css('display','none');}
		
		/*new export*/
			$scope.export = function(data){
				var filteredData = JSON.stringify( data, function( key, value ) {
				    if( key === "$$hashKey" ) {
				        return undefined;
				    }
				
				    return value;
				});
				var gson = angular.fromJson(filteredData);
				const worksheet = XLSX.utils.json_to_sheet(gson, {
					header:["id", "GR NO SAPP", "DATE", "SLOC", "PLANT", "PO STO NO", "PO STO DATE", "STATUS",
					"DELIVERY ORDER NO", "DO ITEM NO", "MATERIAL NO", "MATERIAL DESC", "EXPIRED DATE", "BATCH NO", 
					"ORDER QTY", "UOM", "VARIANCE", "REASON" ]
					}
				);
				const workbook = {
					Sheets: {
						'data': worksheet
					},
					SheetNames: ['data']
				};
				// Diganti Buat CSV
				const excelBuffer = XLSX.write(workbook, { bookType: 'csv', type: 'array'});
				console.log(excelBuffer);
				saveAsExcel(excelBuffer, 'Report Good_Receipt_PO_STO_' + new Date().toJSON().slice(0,10).replace(/-/g,'/'));	
			}
			
			function saveAsExcel(buffer, filename){
				const data = new Blob([buffer], {type: EXCEL_TYPE});
				// Diganti Buat CSV
				saveAs(data, (filename + '.csv'));
			}
			
			$scope.doExport = function(){
				
				var callBackGetList = function(response, result){
					if(response.data.idMessage > 0 ){
						$scope.filterReport = false;
						
						$scope.export(response.data.result);
						
					}else{
						$('#loaderContainer').css('display','none');
						alert('Report tidak tersedia')
					}
				}
			var callBackErrorGetList = function(){
				$('#loaderContainer').css('display','none');
			}
				
			Restful.httpPost('/api/v1/report/grpostoExport',$scope.filter, 
				 null, callBackGetList, callBackErrorGetList, $scope, false, false);
			}
} ]);