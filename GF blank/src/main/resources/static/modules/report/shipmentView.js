'user strict';
angular.module('ShipmentViewApp').controller(
		'ShipmentViewCtrl',
		[ '$scope', '$rootScope', 'Restful', 'CONSTANTS', '$rootScope','$location','$localStorage','$mdDialog',
		function($scope, $rootScope, Restful, CONSTANTS, $rootScope,$location,$localStorage,$mdDialog) {
			$scope.init = function(){
				Restful.httpGet('/api/v1/report/ShipmentDetail?id='+$location.path().split('/').pop(), 
					null, null, callBackGetList, callBackErrorGetList, $scope, false, false);
			}
			
			$scope.cancel = function(){
				 var confirm = $mdDialog.prompt()
			      .title('ADD YOUR REASON')
			      .ok('SAVE')
			      .cancel('CANCEL');
			
			    $mdDialog.show(confirm).then(function (result) {
					if(result){
						var request = {
							"reason":result,
							"id":$scope.data.shipmentNo,
							"tableName":"shipment_header"
						}
						Restful.httpPost('/api/v1/report/cancelReport',request, 
					 		null, callBackCancel, callBackErrorCancel, $scope, false, false);
					}else{
						alert('ADD REASON')
					}
			    }, function () {
				
			    });
			}
			
			var callBackGetList = function(response, result){
				if(response.data.idMessage > 0 ){
					$scope.data = response.data.result.detail;
					$scope.material = response.data.result.material;
					$scope.transport = $scope.data.vehicleType + " - " + $scope.data.policeNo
				}else{
					alert('Transfer Posting tidak tersedia')
				}
				$('#loaderContainer').css('display','none');
			}
			
			var callBackCancel = function(response, result){
				if(response.data.idMessage > 0 ){
					alert('Cancel Sukses')
					$location.path('/report/Shipment');
				}else{
					alert('Cancel Failed')
				}
				$('#loaderContainer').css('display','none');
			}
			
			var callBackErrorGetList = function(){$('#loaderContainer').css('display','none');}
			var callBackErrorCancel = function(){$('#loaderContainer').css('display','none');}
			
} ]);