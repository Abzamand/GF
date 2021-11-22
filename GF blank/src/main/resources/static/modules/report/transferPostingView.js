'user strict';
angular.module('TransferPostingViewApp').controller(
		'TransferPostingViewCtrl',
		[ '$scope', '$rootScope', 'Restful', 'CONSTANTS', '$rootScope','$location','$localStorage',
		function($scope, $rootScope, Restful, CONSTANTS, $rootScope,$location,$localStorage) {
			$scope.init = function(){
				Restful.httpGet('/api/v1/report/transferPostingDetail?id='+$location.path().split('/').pop(), 
					null, null, callBackGetList, callBackErrorGetList, $scope, false, false);
			}
			
			var callBackGetList = function(response, result){
				if(response.data.idMessage > 0 ){
					$scope.data = response.data.result.detail;
					$scope.material = response.data.result.material;
					$scope.tpNoDate = $scope.data.transferPostingNo + " - " + $scope.data.transferPostingDate;
				}else{
					$('#loaderContainer').css('display','none');
					alert('Transfer Posting tidak tersedia')
				}
			}
			
			var callBackErrorGetList = function(){$('#loaderContainer').css('display','none');}
			
} ]);