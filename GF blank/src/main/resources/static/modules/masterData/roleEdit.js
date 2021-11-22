'user strict';
angular.module('RoleEditApp').controller(
		'RoleEditCtrl',
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
			
			$scope.save = function(){
				$('#loaderContainer').css('display','block');
				var tempRole = $scope.role.filter(x => x.selected == true);
				
				var request = {
					"group":$scope.data,
					"authority":tempRole
				} 
				Restful.httpPost('api/v1/masterData/updateRole',request,null,
					callBackSave,callBackErrorSave,$scope,false,false);
			}
			
			var callBackSave = function(response,scope){
				$('#loaderContainer').css('display','none');
				if(response.data.idMessage == 1){
					alert("Update Success")
					 $location.path('/masterData/roleList');
				}else{
					alert("Update Failed")
				}
				
			}
			
			var callBackErrorGetList = function(){$('#loaderContainer').css('display','none');}
			var callBackErrorSave = function(){$('#loaderContainer').css('display','none');}
			
			
} ]);