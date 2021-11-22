'use strict';

angular.module('LoginApp')
	.controller('LoginCtrl',
			['$scope', '$http', '$location', '$localStorage', 'Restful', 'CONSTANTS', 'Base64', '$rootScope', '$route', '$httpParamSerializer',
			function ($scope, $http, $location,  $localStorage, Restful, CONSTANTS, Base64, $rootScope, $route, $httpParamSerializer){
				$rootScope.check=true;
				$scope.dataloading = false;
				$scope.request = {};
				
				$scope.popUpLupaPassword=false; 
				if($localStorage.menu!=undefined){
					Restful.logout();
					$location.path("/");
				}
				
				$scope.toggleForgotPasswod = function(){
					$scope.popUpLupaPassword=true; 
					$scope.newPassword ={
							username:"",
					}	
				}
				
				$scope.doDownload = function(param){
					$('#loaderContainer').css('display','block');
					
					$scope.request.param = param;
					Restful.httpPost('api/v1/file/getFile', $scope.request, null, 
						callBackFile, callBackErrorFile, $scope, false, false);
				
				}
				
				var callBackFile = function(response, scope){
					$('#loaderContainer').css('display','none');
					if(response != null){
						var sampleArr = base64ToArrayBuffer(response.data.result);
						
						if(response.data.idMessage == 1){
							saveByteArray("Manual of GF Admin & Mobile Application", sampleArr, "application/zip");
						}else{
							saveByteArray("OEM", sampleArr, "application/vnd.android.package-archive");
						}
					}
					
				}
					
				var callBackErrorFile = function(){
					
					$('#loaderContainer').css('display','none');
				}
				
				function base64ToArrayBuffer(base64) {
				    var binaryString = window.atob(base64);
				    var binaryLen = binaryString.length;
				    var bytes = new Uint8Array(binaryLen);
				    for (var i = 0; i < binaryLen; i++) {
				       var ascii = binaryString.charCodeAt(i);
				       bytes[i] = ascii;
				    }
				    return bytes;
				 }
				
				function saveByteArray(reportName, byte, mimeType) {
				    var blob = new Blob([byte], {type: mimeType});
				    var link = document.createElement('a');
				    link.href = window.URL.createObjectURL(blob);
				    var fileName = reportName;
				    link.download = fileName;
				    link.click();
				};
			
				
				/*function download(filename, text) {
				    var pom = document.createElement('a');
				    pom.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
				    pom.setAttribute('download', filename);
				
				    if (document.createEvent) {
				        var event = document.createEvent('MouseEvents');
				        event.initEvent('click', true, true);
				        pom.dispatchEvent(event);
				    }
				    else {
				        pom.click();
				    }
				}
				
				function downloadURI(uri, name) {
				    var link = document.createElement("a");
				    link.download = name;
				    link.href = uri;
				    link.click();
				}*/
				
				$scope.resetPassword = function(username){
					
					Restful.httpGet('api/v1/user/resetPassword/'+username,null, null
							, callBackResetPassword , callBackErrorResetPassword, $scope, false, false);
				}
				
				var callBackResetPassword =  function(response,scope){
					$scope.popUpLupaPassword=false;
					alert('SUKSES' + response.data);
				};
				
				var callBackErrorResetPassword = function(response){
					$scope.popUpLupaPassword=false;
					alert('ERROR' + response.data);
				};
				
				$scope.cancelChangePassword=function(){
					$scope.popUpLupaPassword=false;
				}

				$scope.inputType = 'password';
				$scope.hideShowPassword = function(){
				if ($scope.inputType == 'password'){
					$scope.inputType = 'text';
					$('#imgPass').attr('src','./assets/images/Eye_Icon.png');
				}
				else if($scope.inputType = 'text'){
					$scope.inputType = 'password';
					$('#imgPass').attr('src','./assets/images/Eye_off_icon.png');
				}};

				
				$scope.login = function (){
					$('#loaderContainer').css('display','block');
					$scope.dataLoading = true;
					var headers = {
						"Authorization": "Basic " + Base64.encode(CONSTANTS.CLIENT_API_IDENDITY),
		                "Content-type": CONSTANTS.HTTP_HEADER_CONTENT_TYPE
					};

					

					
					var data = {grant_type:CONSTANTS.GRANT_TYPE_PASSWORD, username:$scope.username.toLowerCase(), password:$scope.password};
				
					if(data.username!=undefined){
					$rootScope.username = data.username;
					$localStorage.username = data.username;
					}
					var callBackFunction = function (response){
						var callBackFunctionMenu = function(response, scope){
							setMenuAfterLogin(response.data, $route);
							$localStorage.menu= JSON.stringify(response.data);
							$rootScope.menu = response.data;
							$rootScope.name =response.data.name;
							$localStorage.name=response.data.name;
							Restful.httpGet('/api/v1/masterData/getParam?', $rootScope.name, null, callBackUser, callBackErrorUser, $scope,  true, false);
						};
						
						var callBackErrorFunctionMenu = function(response){
						};
						
						Restful.prepareIdentity(response);
						Restful.httpGet('api/v1/user/menu', null, null 
								, callBackFunctionMenu, callBackErrorFunctionMenu, $rootScope, false);
					};
					
					
					var callBackErrorFunction = function (response, scope){
						if(response.status == CONSTANTS.HTTP_CODE_BAD_REQUEST){
							if(response.data.error == CONSTANTS.ERROR_CODE_001)
								scope.error = CONSTANTS.ERROR_MESSAGE_001;
						}else if(data.status == CONSTANTS.ERROR_CODE_CONNECTION_REFUSED){
							scope.error = CONSTANTS.ERROR_MESSAGE_CONNECTION_REFUSED;
						}
						scope.dataLoading = false;
						alert("ID/PASSWORD ANDA SALAH!")
						$location.path('login');
						$('#loaderContainer').css('display','none');
					};
					
					var callBackUser = function(response, result){
						$rootScope.listSlocPlan = response.data.result.user;
						$localStorage.listSlocPlan = response.data.result.user;
						$rootScope.isVendor = response.data.result.isVendor;
						$localStorage.isVendor = response.data.result.isVendor;
						
						Restful.httpGet('/api/v1/employee/getDetail?deviceId='+"A", 
									null, null, callBackInitDetailUserLogin, callBackErrorInitDetailUserLogin, $scope, false, false);
					}
					
					
					var callBackInitDetailUserLogin = function(response,scope){
						if(response.data.result.fullName != null){
							$localStorage.userLoginDetail = {
									"username":response.data.result.fullName,
									"id":response.data.username,
									"photo": response.data.result.photo ? "data:image/PNG" + ";base64," + response.data.result.photo : null
							}
							$rootScope.userLoginDetail = {
									"username":response.data.result.fullName,
									"id":response.data.username,
									"photo": response.data.result.photo ? "data:image/PNG" + ";base64," + response.data.result.photo : null
							};
						    $rootScope.activyNotify();
							$rootScope.hideHeader=false;
							$rootScope.showDesktopToolBar = true;
							$rootScope.showTabletToolBar = true;
							$scope.dataLoading = false;
							$rootScope.hideNavbar=false;
							$rootScope.checkBackground=false;
							//-----
							/*$localStorage.newNotif = response.data.listNotif.filter(x => x.is_new === 1);
						    $localStorage.listNotif = response.data.listNotif.filter(x => x.is_new === 0);
						    $rootScope.newNotif = $localStorage.newNotif;*/
							$rootScope.userDetail($localStorage.userLoginDetail);
							//$location.path("/dashboard/userActivity");
							$location.path("/dashboard");
						}else{
							scope.dataLoading = false;
							alert("ANDA TIDAK MEMILIKI AKSES UNTUK LOGIN")
							Restful.logout();
							location.reload(true);
						}
						$('#loaderContainer').css('display','none');
					}
					
					var callBackErrorInitDetailUserLogin = function(responsse,scope){
						$('#loaderContainer').css('display','none');
					}
					var callBackErrorUser = function(){$('#loaderContainer').css('display','none');}
					var callBackErrorFunctionMenu = function(response,scope){
						$('#loaderContainer').css('display','none');
					}
					Restful.httpPost(CONSTANTS.OAUTH_TOKEN_PATH, $httpParamSerializer(data), headers, callBackFunction, callBackErrorFunction, $scope, true, false);
					
				};
			}]);

