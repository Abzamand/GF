'user strict';
angular.module('ChangePasswordApp').controller(
	'ChangePasswordCtrl',
	[ '$scope', '$rootScope', 'Restful', 'CONSTANTS', '$rootScope','$location','$localStorage',
	function($scope, $rootScope, Restful, CONSTANTS, $rootScope,$location,$localStorage) {
		$scope.init = function(){
		}
		
		$scope.save = function(){
			if($scope.request.newPassword != $scope.request.confirmPassword){
				alert('Password Berbeda!')
			}else{
				$('#loaderContainer').css('display','block');
				Restful.httpPost('api/v1/setting/changePassword',$scope.request,null,
				callBackSave,callBackErrorSave,$scope,false,false);
			}
		}
		
		var callBackSave = function(response,scope){
			$('#loaderContainer').css('display','none');
			if(response.data.idMessage == 1){
				alert("Save Success")
			}else{
				alert("Save Failed")
			}
		}
		
		var callBackErrorSave = function(){$('#loaderContainer').css('display','none');}			
} ]);