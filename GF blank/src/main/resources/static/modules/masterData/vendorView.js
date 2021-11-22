'user strict';
angular.module('VendorViewApp').controller(
		'VendorViewCtrl',
		[ '$scope', '$rootScope', 'Restful', 'CONSTANTS', '$rootScope','$location','$localStorage',
		function($scope, $rootScope, Restful, CONSTANTS, $rootScope,$location,$localStorage) {
		$scope.init = function(){
				Restful.httpGet('/api/v1/masterData/getAllGroups?', 
					null, null, callBackGetGroups, callBackErrorGetGroups, $scope, false, false);
				
				
				if($location.path().split('/').pop().split('_') != null){
					var idSap = $location.path().split('/').pop().split('_')[0];
					var idSloc = $location.path().split('/').pop().split('_')[1];
					var idPlant = $location.path().split('/').pop().split('_')[2];
				}
				
				Restful.httpGet('/api/v1/masterData/getVendorDetail?id='+idSap + '&idSloc=' + idSloc + '&idPlant=' + idPlant, 
					null, null, callBackGetData, callBackErrorGetData, $scope, false, false);
		
				/*Restful.httpGet('/api/v1/masterData/getVendorDetail?id='+$location.path().split('/').pop(), 
					null, null, callBackGetData, callBackErrorGetData, $scope, false, false);*/
			}
			
			var callBackGetGroups = function(response, result){
				if(response.data.idMessage > 0 ){
					$scope.groups = response.data.result;
				}else{
					$('#loaderContainer').css('display','none');
					alert('Employee tidak tersedia')
				}
			}
			
			var callBackGetData = function(response, result){
				if(response.data.idMessage > 0 ){
					$scope.request = response.data.result;
					$scope.request.photo = $scope.request.photo ? "data:image/PNG" + ";base64," + $scope.request.photo : null;
					var active = $scope.request.role.split(',');
					for(var i = 0 ; i < active.length ; i ++){
						var idx = $scope.groups.indexOf($scope.groups.find(x => x.id == active[i]));
						if(idx != -1){
							$scope.groups[idx].selected = true;
						}
					}
				}else{
					$('#loaderContainer').css('display','none');
					alert('Employee tidak tersedia')
				}
			}
			
			var callBackErrorGetData = function(){$('#loaderContainer').css('display','none');}
			var callBackErrorGetGroups = function(){$('#loaderContainer').css('display','none');}			
} ]);