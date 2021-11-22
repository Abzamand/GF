'user strict';
angular.module('UpdateProfileApp').controller(
	'UpdateProfileCtrl',
	[ '$scope', '$rootScope', 'Restful', 'CONSTANTS', '$rootScope','$location','$localStorage',
	function($scope, $rootScope, Restful, CONSTANTS, $rootScope,$location,$localStorage) {
		$scope.init = function(){
			$scope.updateProfile = true;
			$scope.changePass = false;
			Restful.httpGet('/api/v1/setting/getProfile?', 
				null, null, callBackGetProfile, callBackErrorGetProfile, $scope, false, false);
		}
		
		var callBackGetProfile = function(response, result){
			if(response.data.idMessage > 0 ){
				$scope.request = response.data.result;
				$scope.request.photo = $scope.request.photo ? "data:image/PNG" + ";base64," + $scope.request.photo : null;
				$localStorage.userLoginDetail.photo = $scope.request.photo;
				$rootScope.userLoginDetail.photo = $scope.request.photo;
			}else{
				$('#loaderContainer').css('display','none');
				alert('Device tidak tersedia')
			}
		}
		
		$scope.save = function(){
			$('#loaderContainer').css('display','block');
			$scope.request.photoString = $scope.request.photo ? $scope.request.photo.substr($scope.request.photo.indexOf(',')+1) : null;
			$scope.request.photo = "";
			Restful.httpPost('api/v1/setting/updateProfile',$scope.request,null,
				callBackSave,callBackErrorSave,$scope,false,false);
		}
		
		
		
		
		var callBackSave = function(response,scope){
			$('#loaderContainer').css('display','none');
			if(response.data.idMessage == 1){
				alert("Save Success")
				Restful.httpGet('/api/v1/setting/getProfile?', 
				null, null, callBackGetProfile, callBackErrorGetProfile, $scope, false, false);
			}else{
				alert("Save Failed")
			}
		}
		
		$scope.changePassword = function(){
			if($scope.request.newPassword != $scope.request.confirmPassword){
				alert('Password Berbeda!')
			}else{
				$('#loaderContainer').css('display','block');
				Restful.httpPost('api/v1/setting/changePassword',$scope.request,null,
				callChangePassword,callBackErrorChangePassword,$scope,false,false);
			}
		}
		
		var callChangePassword = function(response,scope){
			$('#loaderContainer').css('display','none');
			if(response.data.idMessage == 1){
				alert("Change Password Success")
				Restful.logout();
				location.reload(true);
			}else{
				alert("Change Password Failed")
			}
		}
		
		var callBackErrorChangePassword = function(){$('#loaderContainer').css('display','none');}
		var callBackErrorSave = function(){$('#loaderContainer').css('display','none');}
		var callBackErrorGetProfile = function(){$('#loaderContainer').css('display','none');}
		
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
			
} ]);