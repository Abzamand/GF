'user strict';
angular.module('GoodReceiptPOSTOViewApp').controller(
		'GoodReceiptPOSTOViewCtrl',
		[ '$scope', '$rootScope', 'Restful', 'CONSTANTS', '$rootScope','$location','$localStorage',
		function($scope, $rootScope, Restful, CONSTANTS, $rootScope,$location,$localStorage) {
			$scope.showPic = false;
			$scope.init = function(){
				Restful.httpGet('/api/v1/report/grpostoDetail?id='+$location.path().split('/').pop(), 
					null, null, callBackGetList, callBackErrorGetList, $scope, false, false);
			}
			
			$scope.showImage = function (img){
				if(img){
					$scope.showPic = true;
					$scope.photo = img ? "data:image/PNG" + ";base64," + img : null;
				}else{
					$('#loaderContainer').css('display','none');
					alert('Photo tidak tersedia')
				}
				
				
			}
			
			var callBackGetList = function(response, result){
				if(response.data.idMessage > 0 ){
					$scope.data = response.data.result.detail;
					$scope.material = response.data.result.material;
				}else{
					$('#loaderContainer').css('display','none');
					alert('Transfer Posting tidak tersedia')
				}
			}
			
			var callBackErrorGetList = function(){$('#loaderContainer').css('display','none');}
			
} ]);