
'user strict';
angular.module('VendorAssignApp').controller(
		'VendorAssignCtrl',
				[ '$scope', '$rootScope', 'Restful', 'CONSTANTS', '$rootScope','$location','$localStorage',
				function($scope, $rootScope, Restful, CONSTANTS, $rootScope,$location,$localStorage) {
					$scope.isPasswordChanged = 0;
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
					}
					
					var callBackGetGroups = function(response, result){
						if(response.data.idMessage > 0 ){
							$scope.groups = response.data.result;
						}else{
							$('#loaderContainer').css('display','none');
							alert('Vendor tidak tersedia')
						}
					}
					
					var callBackGetData = function(response, result){
						if(response.data.idMessage > 0 ){
							$scope.request = response.data.result;
							$scope.request.photo = $scope.request.photo ? "data:image/PNG" + ";base64," + $scope.request.photo : null;
							$scope.request.password = $scope.request.id != null ? "123456" : null;
							if($scope.request.role){
								var active = $scope.request.role.split(',');
								for(var i = 0 ; i < active.length ; i ++){
									var idx = $scope.groups.indexOf($scope.groups.find(x => x.id == active[i]));
									if(idx != null){
										$scope.groups[idx].selected = true;
									}
							}
						}
							
						}else{
							$('#loaderContainer').css('display','none');
							alert('Vendor tidak tersedia')
						}
					}
					
					$scope.save = function(){
						$('#loaderContainer').css('display','block');
						$scope.request.photoString = $scope.request.photo ? $scope.request.photo.substr($scope.request.photo.indexOf(',')+1) : null;
						$scope.request.photo = "";
						var data = {
							"vendor":$scope.request,
							"groupmember":$scope.groups.filter(x => x.selected == true)
						}
						Restful.httpPost('api/v1/masterData/updateVendor',data,null,
							callBackSave,callBackErrorSave,$scope,false,false);
					}
					
					var callBackSave = function(response,scope){
						$('#loaderContainer').css('display','none');
						if(response.data.idMessage == 1){
							alert("Save Success")
							Restful.httpGet('/api/v1/masterData/getVendorDetail?id='+$location.path().split('/').pop(), 
								null, null, callBackGetData, callBackErrorGetData, $scope, false, false);
					 	 	$location.path('/masterData/vendorList'); 
						}else{
							alert("Save Failed")
						}
						
					}
					
					var callBackErrorGetData = function(){$('#loaderContainer').css('display','none');}
					var callBackErrorGetGroups = function(){$('#loaderContainer').css('display','none');}
					var callBackErrorSave = function(){$('#loaderContainer').css('display','none');}
					
					var targetId, fullPath, filename, ext;
					$(function () {
					    $(":file").change(function () {
					    	$rootScope.loading = true;
					    	targetId = event.target.id;
					    	fullPath = event.target.value;
					    	if (fullPath) {
					    	    var startIndex = (fullPath.indexOf('\\') >= 0 ? fullPath.lastIndexOf('\\') : fullPath.lastIndexOf('/'));
					    	    filename = fullPath.substring(startIndex);
					    	    if (filename.indexOf('\\') === 0 || filename.indexOf('/') === 0) {
					    	        filename = filename.substring(1);
					    	    }
					    	    if(filename){
					    	    	var re = /(?:\.([^.]+))?$/;
				    	    		ext = re.exec(filename)[0];
					    	    }
					    	}
					        if (this.files && this.files[0]) {
					            var reader = new FileReader();

					            reader.onload = imageIsLoaded;
					            if(targetId == 'photo'){
					            	reader.readAsArrayBuffer(this.files[0])
					            }else{
					            	reader.readAsDataURL(this.files[0]);
					            }
					        }
					    });
					});
					
					function imageIsLoaded(e) {
						if(targetId == 'photo'){
						      var blob = new Blob([e.target.result]);
						      window.URL = window.URL || window.webkitURL;
						      var blobURL = window.URL.createObjectURL(blob);
						      
						      var image = new Image();
						      image.src = blobURL;
						      image.onload = function() {
						        $scope.request.photo = resizeMe(image);
						        if(!$scope.$$phase) {
									$scope.$apply();
								}
						      }
						}else{
							$scope.request.photo = e.target.result;
						}
						$(":file").val('');
					    if(!$scope.$$phase) {
							$scope.$apply();
						}
					};
					
					function resizeMe(img) {
					  var canvas = document.createElement('canvas');
					  var width = 150;
					  var height = 150;
					  // resize the canvas and draw the image data into it
					  canvas.width = width;
					  canvas.height = height;
					  var ctx = canvas.getContext("2d");
					  ctx.drawImage(img, 0, 0, width, height);
					  return canvas.toDataURL("image/jpeg",0.7); // get the data from canvas as 70% JPG (can be also PNG, etc.)
					}
			
					$scope.passwordChanged = function(){
						$scope.isPasswordChanged = 1;
					}
					
		} ]);