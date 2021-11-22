'user strict';
angular.module('RoleCreateApp').controller(
		'RoleCreateCtrl',
		[ '$scope', '$rootScope', 'Restful', 'CONSTANTS', '$rootScope','$location','$localStorage',
		function($scope, $rootScope, Restful, CONSTANTS, $rootScope,$location,$localStorage) {
			$scope.init = function(){
				Restful.httpGet('/api/v1/masterData/getAllAuthority', 
					null, null, callBackGetList, callBackErrorGetList, $scope, false, false);
			}
			
			var callBackGetList = function(response, result){
				if(response.data.idMessage > 0 ){
					$scope.role = response.data.result;
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
				Restful.httpPost('api/v1/masterData/createGroups',request,null,
					callBackSave,callBackErrorSave,$scope,false,false);
			}
			
			var callBackSave = function(response,scope){
				$('#loaderContainer').css('display','none');
				if(response.data.idMessage == 1){
					alert("Create Success")
					 $location.path('/masterData/roleList');
				}else{
					alert("Update Failed")
				}
				
			}
			
			var callBackErrorGetList = function(){$('#loaderContainer').css('display','none');}
			var callBackErrorSave = function(){$('#loaderContainer').css('display','none');}
			
			
} ]);