'user strict';
angular.module('StorageLocationListApp').controller(
	'StorageLocationListCtrl',
	[ '$scope', '$rootScope', 'Restful', 'CONSTANTS', '$rootScope','$location','$localStorage',
	function($scope, $rootScope, Restful, CONSTANTS, $rootScope,$location,$localStorage) {
			$scope.filter ={
					"idPlant":"",
					"plantName":"",
					"idSloc":"",
					"slocName":"",
					"loadingPoint":""
				}
			
			$scope.clearFilter = function(){
				$scope.filter ={
					"idPlant":"",
					"plantName":"",
					"idSloc":"",
					"slocName":"",
					"loadingPoint":""
				}
			}
			$scope.init = function(){
				$scope.getRequest(0);
			}
			
			$scope.getRequest = function(data){
				$scope.filter.curPage = data;
				
				var callBackGetList = function(response, result){
					if(response.data.idMessage > 0 ){
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
						alert('Storage Location tidak tersedia')
					}
				}
		
			var callBackErrorGetList = function(){$('#loaderContainer').css('display','none');}
			
			Restful.httpPost('/api/v1/masterData/getSLOC?', 
					$scope.filter, null, callBackGetList, callBackErrorGetList, $scope, false, false);
		}
		
		$scope.save = function(data){
			$('#loaderContainer').css('display','block');
			Restful.httpPost('api/v1/masterData/updateLoadingPoint',data,null,
				callBackSave,callBackErrorSave,$scope,false,false);
		}
		
		var callBackSave = function(response,scope){
			$('#loaderContainer').css('display','none');
			if(response.data.idMessage == 1){
				alert("Save Success")
					Restful.httpGet('/api/v1/masterData/getSLOC?', 
						null, null, callBackGetList, callBackErrorGetList, $scope, false, false);
			}else{
				alert("Save Failed")
			}
			
		}
			
		var callBackErrorSave = function(){$('#loaderContainer').css('display','none');}
		
} ]);