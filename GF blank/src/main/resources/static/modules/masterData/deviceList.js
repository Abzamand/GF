'user strict';
angular.module('DeviceListApp').controller(
	'DeviceListCtrl',
	[ '$scope', '$rootScope', 'Restful', 'CONSTANTS', '$rootScope','$location','$localStorage','$mdDialog',
		function($scope, $rootScope, Restful, CONSTANTS, $rootScope,$location,$localStorage,$mdDialog) {
			$scope.isView = false;
			$scope.isEdit = false;	
			$scope.filter ={
					"id":"",
					"device":"",
					"ssaid":"",
					"phoneNo":"",
					"status":""
				}
			
			$scope.clearFilter = function(){
				$scope.filter ={
					"id":"",
					"device":"",
					"ssaid":"",
					"phoneNo":"",
					"status":""
				}
			}
		
			$scope.init = function(){
				Restful.httpGet('/api/v1/masterData/getUserlogin?', null, null, callBackUserlogin, callBackErrorUserlogin, $scope, false, false);
				$scope.getRequest(0);
			}
			
			var callBackUserlogin = function(response, result){
				if(response.data.idMessage > 0 ){
					$scope.userLogin =  response.data.result;
				}else{
					$('#loaderContainer').css('display','none');
					alert('Userlogin tersedia')
				}
				
			}
			
			$scope.getRequest = function(data){
				$scope.filter.curPage = data;
				$scope.request = {status:1};
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
					alert('Device tidak tersedia')
				}
			}
		
			var callBackErrorGetList = function(){$('#loaderContainer').css('display','none');}
			
				Restful.httpPost('/api/v1/masterData/getDevice?', 
						$scope.filter, null, callBackGetList, callBackErrorGetList, $scope, false, false);
			}
			
		$scope.openView = function(id){
			Restful.httpGet('/api/v1/masterData/getDeviceDetail?id='+id, 
				null, null, callBackGetDetail, callBackErrorGetDetail, $scope, false, false);
				
			$scope.create = true;
			$scope.view = true;
			$scope.edit = true;
		}
		
		$scope.openEdit = function(id){
			Restful.httpGet('/api/v1/masterData/getDeviceDetail?id='+id, 
				null, null, callBackGetDetail, callBackErrorGetDetail, $scope, false, false);
				
			$scope.create = true;
			$scope.view = true;
			$scope.edit = false;
		}
		
		var callBackGetDetail = function(response, result){
			if(response.data.idMessage > 0 ){
				$scope.request = response.data.result;
				var tempUsername = $scope.userLogin.find(x => x.username == $scope.request.username);
				$scope.request.username = tempUsername.username;
				
			}else{
				$('#loaderContainer').css('display','none');
				alert('Device tidak tersedia')
			}
		}
	
		$scope.save = function(){
			$('#loaderContainer').css('display','block');
			Restful.httpPost('api/v1/masterData/createDevice',$scope.request,null,
				callBackSave,callBackErrorSave,$scope,false,false);
		}
		var callBackSave = function(response,scope){
			$('#loaderContainer').css('display','none');
			if(response.data.idMessage == 1){
				alert("Save Success")
				$scope.create = false
				$scope.getRequest(0);
			}else{
				alert("Save Failed")
			}
			
		}
		
		$scope.update = function(){
			$('#loaderContainer').css('display','block');
			Restful.httpPost('api/v1/masterData/updateDevice',$scope.request,null,
				callBackUpdate,callBackErrorUpdate,$scope,false,false);
		}
		var callBackUpdate = function(response,scope){
			$('#loaderContainer').css('display','none');
			if(response.data.idMessage == 1){
				alert("Save Success")
				$scope.create = false
				$scope.getRequest(0);
			}else{
				alert("Save Failed")
			}
			
		}
		
		$scope.delete = function(id){
			var confirm = $mdDialog.confirm()
		      .title('Are you sure want to delete?')
		      .ok('YES')
		      .cancel('NO');
		
		    $mdDialog.show(confirm).then(function () {
		    Restful.httpPost('api/v1/masterData/deleteDevice',id,null,
				callBackDelete,callBackErrorDelete,$scope,false,false);
		    }, function () {
		      
		    });
		}	
		
		var callBackDelete = function(response,scope){
			$('#loaderContainer').css('display','none');
			if(response.data.idMessage == 1){
				alert("Delete Success")
				$scope.getRequest(0);
			}else{
				alert("Save Failed")
			}
			
		}
		
		var callBackErrorUserlogin = function(){$('#loaderContainer').css('display','none');}
		var callBackErrorDelete = function(){$('#loaderContainer').css('display','none');}
		var callBackErrorGetDetail = function(){$('#loaderContainer').css('display','none');}
		var callBackErrorSave = function(){$('#loaderContainer').css('display','none');}
		var callBackErrorUpdate = function(){$('#loaderContainer').css('display','none');}
			
} ]);