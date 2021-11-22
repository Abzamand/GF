'user strict';
angular.module('EmployeeCreateApp').controller(
		'EmployeeCreateCtrl',
		[ '$scope', '$rootScope', 'Restful', 'CONSTANTS', '$rootScope','$location','$localStorage',
		function($scope, $rootScope, Restful, CONSTANTS, $rootScope,$location,$localStorage) {
			$scope.request = {status:1};
			
			$scope.init = function(){
				Restful.httpGet('/api/v1/masterData/getAllGroups?', 
					null, null, callBackGetRole, callBackErrorGetRole, $scope, false, false);
				Restful.httpGet('/api/v1/masterData/getAllPlant?',
					null, null, callBackGetPlant, callBackErrorGetPlant, $scope, false, false);
			}
			
			var callBackGetRole = function(response, result){
				if(response.data.idMessage > 0 ){
					$scope.role = response.data.result;
				}else{
					$('#loaderContainer').css('display','none');
					alert('Role tidak tersedia')
				}
			}
			var callBackGetPlant = function(response, result){
				if(response.data.idMessage > 0 ){
					$scope.plantStart = response.data.result;
				}
			}
			$scope.save = function(){
				$('#loaderContainer').css('display','block');
				$scope.request.photoString = $scope.request.photo ? $scope.request.photo.substr($scope.request.photo.indexOf(',')+1) : null;
				$scope.request.photo = "";
				
				var data = {
					"employee":$scope.request,
					"groupmember":$scope.role.filter(x => x.selected == true)
				}
				
				Restful.httpPost('api/v1/masterData/createEmployee',data,null,
					callBackSave,callBackErrorSave,$scope,false,false);
			}
			
			var callBackSave = function(response,scope){
				$('#loaderContainer').css('display','none');
				if(response.data.idMessage == 1){
					alert("Save Success")
					$scope.create = false
					 $location.path('/masterData/employeeList');
				}else{
					alert("Save Failed")
				}
				
			}
			
			var callBackErrorGetRole = function(){$('#loaderContainer').css('display','none');}
			var callBackErrorSave = function(){$('#loaderContainer').css('display','none');}
			var callBackErrorGetPlant = function(){$('#loaderContainer').css('display','none');}

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