'user strict';
angular.module('RoleViewApp').controller(
		'RoleViewCtrl',
		[ '$scope', '$rootScope', 'Restful', 'CONSTANTS', '$rootScope','$location','$localStorage',
		function($scope, $rootScope, Restful, CONSTANTS, $rootScope,$location,$localStorage) {
			$scope.init = function(){
				Restful.httpGet('/api/v1/masterData/getGroupsDetail?id='+$location.path().split('/').pop(), 
					null, null, callBackGetList, callBackErrorGetList, $scope, false, false);
			}
			
			var callBackGetList = function(response, result){
				if(response.data.idMessage > 0 ){
					$scope.data = response.data.result.data;
					$scope.role = response.data.result.authority;
					var active = response.data.result.active;
					
					for(var i = 0 ; i < active.length ; i ++){
						var idx = $scope.role.indexOf($scope.role.find(x => x.authority == active[i].authority));
						if(idx != null){
							$scope.role[idx].selected = true;
						}
					}
				}else{
					$('#loaderContainer').css('display','none');
					alert('Device tidak tersedia')
				}
			}
			
			var callBackErrorGetList = function(){$('#loaderContainer').css('display','none');}
			
} ]);