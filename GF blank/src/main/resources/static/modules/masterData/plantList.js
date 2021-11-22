'user strict';
angular.module('PlantListApp').controller(
		'PlantListCtrl',
		[ '$scope', '$rootScope', 'Restful', 'CONSTANTS', '$rootScope','$location','$localStorage','$mdDialog',
		function($scope, $rootScope, Restful, CONSTANTS, $rootScope,$location,$localStorage, $mdDialog) {
			$scope.filter ={
				"id":"",
				"name":""
			}
			
			$scope.clearFilter = function(){
				$scope.filter ={
					"id":"",
					"name":""
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
					alert('Plant tidak tersedia')
				}
			}
		
			var callBackErrorGetList = function(){$('#loaderContainer').css('display','none');}
			
				Restful.httpPost('/api/v1/masterData/getPlant?', 
						$scope.filter, null, callBackGetList, callBackErrorGetList, $scope, false, false);
			}			
} ]);