'use strict';
var isNavBar=false;
var isLogin = false;
angular.module('HomeApp', []);
angular.module('LoginApp',[]);
angular.module('UserActivityApp',[]);

angular.module('EmployeeListApp',[]);
angular.module('EmployeeCreateApp',[]);
angular.module('EmployeeEditApp',[]);
angular.module('EmployeeViewApp',[]);
angular.module('VendorListApp',[]);
angular.module('VendorAssignApp',[]);
angular.module('VendorViewApp',[]);
angular.module('RoleListApp',[]);
angular.module('RoleCreateApp',[]);
angular.module('RoleEditApp',[]);
angular.module('RoleViewApp',[]);
angular.module('StorageLocationListApp',[]);
angular.module('PlantListApp',[]);
angular.module('DeviceListApp',[]);
angular.module('UomConversionListApp',[]);
angular.module('MaterialListApp',[]);
angular.module('MaterialTypeListApp',[]);

angular.module('TransferPostingApp',[]);
angular.module('TransferPostingViewApp',[]);
angular.module('TransferPostingConfirmationApp',[]);
angular.module('TransferPostingConfirmationViewApp',[]);
angular.module('DeliveryOrderApp',[]);
angular.module('DeliveryOrderViewApp',[]);
angular.module('GoodReceiptFGApp',[]);
angular.module('GoodReceiptFGViewApp',[]);
angular.module('GoodReceiptPOSTOApp',[]);
angular.module('GoodReceiptPOSTOViewApp',[]);
angular.module('PGIShipmentApp',[]);
angular.module('PGIShipmentViewApp',[]);
angular.module('ShipmentApp',[]);
angular.module('ShipmentViewApp',[]);

angular.module('SyncApp',[]);
angular.module('ChangePasswordApp',[]);
angular.module('UpdateProfileApp',[]);

var tempRoot = window.location.href.split('/');
var tempUrl="";
for (var i = 0; i < tempRoot.length; i++) {
	if (tempRoot[i]!='#') {
		tempUrl += tempRoot[i]+'/'
	}else {
		break;
	}
};

var app = angular 
		.module(
				'App',
				['ng-fusioncharts','ngStorage', 'ngRoute', 'ngResource', 'ngCookies',
				 'ngAnimate', 'ngAria','ngMaterial', 'ngMessages',
				 'LoginApp','HomeApp','UserActivityApp',
				 'EmployeeListApp','EmployeeCreateApp','EmployeeEditApp','EmployeeViewApp',
				 'VendorListApp','VendorAssignApp','VendorViewApp',
			 	 'RoleListApp','RoleCreateApp','RoleEditApp','RoleViewApp',
				 'StorageLocationListApp','PlantListApp','DeviceListApp','UomConversionListApp','MaterialListApp','MaterialTypeListApp',
				 'TransferPostingApp','TransferPostingViewApp','TransferPostingConfirmationApp','TransferPostingConfirmationViewApp',
				 'DeliveryOrderApp','DeliveryOrderViewApp','GoodReceiptFGApp','GoodReceiptFGViewApp',
				 'GoodReceiptPOSTOApp','GoodReceiptPOSTOViewApp','PGIShipmentApp','PGIShipmentViewApp',
				 'ShipmentApp','ShipmentViewApp',
				 'SyncApp','ChangePasswordApp','UpdateProfileApp',

				  ])
		.constant(
				"CONSTANTS",
				{
					'ROOT_URL' : tempUrl,
					'SOCKET_URL' : 'api/v1/websocket/chat?access_token=',
					'ROOT_PATH' : "/",
					'CLIENT_API_IDENDITY' : 'WEB_CLIENT:WEB_CLIENT',
					'CLIENT_ID' : 'WEB_CLIENT',
					'CLIENT_SECRET' : 'WEB_CLIENT',
					'METHOD_PUT' : 'PUT',
					'METHOD_DELETE' : 'DELETE',
					'METHOD_POST' : 'POST',
					'METHOD_GET' : 'GET',
					'HTTP_CODE_FORBIDDEN' : 403,
					'HTTP_CODE_UNAUTHORIZED' : 401,
					'HTTP_CODE_BAD_REQUEST' : 400,
					'ERROR_CODE_CONNECTION_REFUSED' : -1,
					'ERROR_CODE_001' : 'invalid_grant',
					'ERROR_CODE_002' : 'access_denied',
					'ERROR_CODE_003' : 'invalid_token',
					'ERROR_MESSAGE_001' : 'Invalid username and Password',
					'ERROR_MESSAGE_002' : 'You don\'t have previllage.',
					'ERROR_MESSAGE_CONNECTION_REFUSED' : 'Error while connecting',
					'OAUTH' : 'oauth',
					'OAUTH_TOKEN_PATH' : 'oauth/token',
					'CLIENT' : 'client',
					'PIPE' : '|',
					'DOUBLE_DOT' : ':',
					'USERNAME' : 'username',
					'PASSWORD' : 'password',
					'GRANT_TYPE_PASSWORD' : 'password',
					'GRANT_TYPE_REFRESH_TOKEN' : 'refresh_token',
					'HTTP_HEADER_CONTENT_TYPE' : "application/x-www-form-urlencoded; charset=utf-8",
					'IDENTITY' : 'identity',
					'SPACE' : ' ',
					'MENU' : 'menu',
					'X_CSRF_TOKEN' : 'x-csrf-token'
						
				});
//app.config([ '$translateProvider',
//             function($translateProvider) {
//               $translateProvider.useStaticFilesLoader({
//                 prefix: '/scripts/',
//                 suffix: '.json'
//               });
//             }
//           ]);

app.controller('AppCtrl', [ '$scope', '$cookies', '$rootScope',
		'$location','$mdSidenav', 'Restful','$compile','$localStorage','$interval','Base64'
		,'CONSTANTS','$httpParamSerializer','$mdDialog',function($scope,$cookies,$rootScope,
				$location,$mdSidenav, Restful,$compile,$localStorage,$interval,Base64,CONSTANTS,$httpParamSerializer,$mdDialog) {
			$rootScope.showGuest = true;
			//filter
			$rootScope.filter = {
				"isChange":false,
				"from":new Date(),
				"to":new Date(),
				"search":{}
			};
			
			$rootScope.switchPage = function(){
				$rootScope.filter.isChange = false;
				$rootScope.filter.from = new Date();
				$rootScope.filter.to = new Date();
			};
			
//			paging
			$rootScope.pages = {};
			$scope.resetPages = function(){
				$rootScope.pages = {};
			}
//			notif
			$rootScope.activyNotify = function(){
				var token = $rootScope.oauth.access_token;
			    var socket = new SockJS(CONSTANTS.SOCKET_URL + token);
			    notifClient = Stomp.over(socket);
			    notifClient.connect({}, function(frame) {
			        console.log('Connected: ' + frame);
			        notifClient.subscribe('/topic/messages/Notif' + $rootScope.userLoginDetail.id, function(messageOutput) {
			        	var response = {};
			            	response.data = JSON.parse(messageOutput.body);
			            	$localStorage.newNotif.unshift(response.data);
			            	$scope.newNotif = $localStorage.newNotif;
			            	$scope.$apply();
			            	var audio = new Audio("./assets/sound/notif2.mp3");
			            	audio.play();
			        });
			    });
//    		    ----	
			}
			if($rootScope.oauth){
				$rootScope.activyNotify();
				$scope.newNotif = $localStorage.newNotif;
			}
			$scope.listNotif = $localStorage.listNotif;
			$scope.setNotif = function(){
				$scope.newNotif = $localStorage.newNotif;
				$scope.toggle.popNotif = !$scope.toggle.popNotif;
				if($scope.newNotif && $scope.newNotif.length){
					for(var i = 0; i < $scope.newNotif.length; i++){
						if($localStorage.listNotif.length ? !$localStorage.listNotif.find(x => x.id === $scope.newNotif[i].id) : true){
							$localStorage.listNotif.unshift($scope.newNotif[i]);
						}
					}
					var callBackUpdateNotif = function(response, scope){
					};
					var callBackErrorUpdateNotif = function(response){
					};
					Restful.httpPost('/api/v1/employee/updateNotif', $scope.newNotif, null, callBackUpdateNotif, callBackErrorUpdateNotif, $scope, false, false);
					$localStorage.newNotif = [];
					$scope.newNotif = [];
				}
					$scope.listNotif = $localStorage.listNotif;
					if(!$scope.toggle.popNotif){
						for(var i = 0; i <$scope.listNotif.length; i++){
							$scope.listNotif[i].is_new = 0;
						}
					}
			}
			$scope.clearNotif = function(){
				var callBackDeleteNotif = function(response, scope){
				};
				var callBackErrorDeleteNotif = function(response){
				};
				Restful.httpPost('/api/v1/employee/deleteNotif', $scope.listNotif, null, callBackDeleteNotif, callBackErrorDeleteNotif, $scope, false, false);
				$scope.listNotif = []; 
				$localStorage.listNotif = [];
			}
			//toggle dropdown
			$scope.toggle = {
					isUserMenu : false,
					popNotif : false
			};
			$scope.clearToggle = function(){
				for(var temp in $scope.toggle){
					$scope.toggle[temp] = false;
				}
				if($scope.listNotif){
					for(var i = 0; i <$scope.listNotif.length; i++){
						$scope.listNotif[i].is_new = 0;
					}
				}
			};
			$scope.toggleDropdown = function(prefix){
				for(var temp in $scope.toggle){
					if(temp == prefix){
						$scope.toggle[temp] = !$scope.toggle[temp]
					}else{
						$scope.toggle[temp] = false;
					}
				}
			};
			$(document).bind('click', function(event){
		        $scope.$apply(function(){
		        	$scope.clearToggle();
		        })
		    });
			//---
			
			$scope.goBack = function(){
				window.history.back();
			}
			
			
			//broadcast - depecreated
			var callBackBroadcast = function(response,scope){
				if(response.data != null && response.data != ''){
					$localStorage.userLoginDetail.notifDate = response.data.notifDate;
					alert(response.data.description);
					if(response.data.value == 'true'){
						$scope.logout();
					}
				}
			}
			
			var callBackErrorBroadcast = function(){
			}
			
			$rootScope.broadcast = function(){
				if($localStorage.userLoginDetail && $localStorage.userLoginDetail.notifDate){
					Restful.httpGet('api/v1/parameter/getBroadcast?date=' + $localStorage.userLoginDetail.notifDate, null, null, callBackBroadcast, callBackErrorBroadcast, false);
				}
				var locationNow = $location.path().split('/')[1];
				if($localStorage.userLoginDetail &&  $rootScope.userLoginDetail.id != $localStorage.userLoginDetail.id ){
					location.reload(true);
				}
			}
			$interval(function() {
				$rootScope.broadcast();
			}, 60000);
//			-----
			
			if($localStorage.menu==undefined){
				$rootScope.hideNavbar=true;
				$rootScope.checkBackground=false;
			}
			//--
			$scope.selectedItem = {
					"languange":""
			}
			 $scope.listOfOptions = ['English', 'Indonesia'];
			$rootScope.showTabletToolBar = false;
			$rootScope.showDesktopToolBar = false;
			
			 $scope.toggleLeft = function(){
				 buildToggler('left');
			 }
			
			$scope.openSideNavPanel = function() {
				isNavBar = !isNavBar
				if (isNavBar == false && document.getElementById("navBarPop").style.width=="250px") {
					document.getElementById("navBarPop").style.width="0px";
				}else{
					document.getElementById("navBarPop").style.width="250px";
				}
			};
			
			$scope.closeSideNavPanel = function() {
				$mdSidenav('left').close();
			};
		    $scope.navigate = function(path){
		    	$location.path(path.param);
		    	$mdSidenav('left').close();
		    }
			$scope.getClass = function (path) {
				return ($location.path().substr(0, path.length) === path) ? 'active' : '';
			}
			$scope.home = function(){
				$location.path("/");
			}
			$rootScope.regexId=/^[\w!@#$%^*.&+-]*$/;
			
			$rootScope.goto=function(location){
				$location.path(location);
			}
			
			$rootScope.cancel=function(){
				$scope.route= $location.path().split('/');
				$location.path($scope.route[1]); 
			}
			
			$scope.isActive = function (viewLocation) { 
		        return viewLocation === $location.path();
		    };
			
			$scope.closeMenu=function(index){
				var element = angular.element(document.querySelector("#item-"+index).lastChild);
				element.remove();
				$rootScope.menu.menus[index].isOpen=false;
			}
			
			$scope.closeOpenedMenu=function(){
				for(var i=0;i<$rootScope.menu.menus.length;i++){
					if($rootScope.menu.menus[i].isOpen){
						$scope.closeMenu(i);
					}
				}
			}
			
			
			$scope.navigate = function(path) {
				$location.path(path.param);
				$scope.closeOpenedMenu();
				$mdSidenav('left').close();
			}
			
			$scope.navigateChild =function(indexP,indexC){
				$location.path($rootScope.menu.menus[indexP].menu[indexC].path);
				$scope.closeOpenedMenu();
				$mdSidenav('left').close();
			}
			
			$scope.openChild=function(index){
				if($rootScope.menu.menus[index].isOpen==false){
					$rootScope.menu.menus[index].isOpen=true;
					var element = angular.element(document.querySelector("#item-"+index));
					var div=angular.element("<div class='md-custom-child'></div>");
					for(var i=0;i<$rootScope.menu.menus[index].menu.length;i++){
						if($rootScope.menu.menus[index].menu[i].path.indexOf(':')==-1){
							var menuElement = angular.element("<p role='button' ng-click='navigateChild("+index+","+i+")'>"+$rootScope.menu.menus[index].menu[i].menuName+"</p>");
							div.append(menuElement);
						}
					}
					element.append(div);
					var temp = $compile(div)($scope); 
				}else{
					$scope.closeMenu(index);
				}
			}
			
			
//download manual
				$scope.isDashboard = function(){
					return window.location.href.includes("dashboard");
				}

				$scope.doDownload = function(param){
					$('#loaderContainer').css('display','block');
					
					$scope.request = {};
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
			
			
			$scope.logout = function() {
				Restful.logout();
				location.reload(true);
			};
			
			//editparam
			$rootScope.employeeEdit = false;
						
			//tambahan imelda
			if($localStorage.name!=undefined){
		    	$rootScope.name=$localStorage.name;
		    }
			if ($localStorage.username != undefined) {
				$rootScope.username = $localStorage.username;	
			}
			if ($localStorage.userLoginDetail !=undefined) {
				$scope.userLoginDetail = $localStorage.userLoginDetail;
				$rootScope.userLoginDetail = $localStorage.userLoginDetail;
			}
			$rootScope.userDetail = function(data){
					$scope.userLoginDetail =data;
			}
			
			$rootScope.thousandSeparator = function(data, prefix){
				if(data[prefix] != undefined && data[prefix] != "" && data[prefix] != null){
					data[prefix] += '';
					var comma = /,/g;
					data[prefix] = data[prefix].replace(comma,'');
				    var x = data[prefix].split('.');
				    var x1 = parseInt(x[0]);
				    x1 += '';
                    var numeric = /^-?([0-9]{1,3}\,?)+$/;
				    if(!numeric.test(x1)){
						x1 = x1.substr(0,(x1.length - 1));
					}
					if(x.length > 1 && !numeric.test(x[1])){
						x[1] = x[1].substr(0,(x[1].length - 1));
					}
				    var x2 = x.length > 1 ? '.' + x[1] : '';
				    var rgx = /(\d+)(\d{3})/;
				    data[prefix + '_int'] = x1 + x2;
				    while (rgx.test(x1)) {
				        x1 = x1.replace(rgx, '$1' + ',' + '$2');
				    }
				    data[prefix] = x1 + x2;
				}else{
					data[prefix] = '0';
					data[prefix + '_int'] = 0;
				}
			}
			
//			window.onunload = function () {
//				$scope.logout();
//			}
			
//			idle timer
			// Set timeout variables.
			var timoutWarning = 1800000;// Display warning in 30 Mins.
			var timoutNow = 60000; // Warning has been shown, give the user 1 minute to interact
//			var timoutWarning = 600000;
//			var timoutNow = 60000;
			$scope.warning = {};
			var warningTimer;
			var timeoutTimer;
			// Start warning timer.
			function StartWarningTimer() {
				if($rootScope.oauth){
					warningTimer = setTimeout(IdleWarning, timoutWarning);
				}
			}
			// Reset timers.
			function ResetTimeOutTimer() {
				clearTimeout(timeoutTimer);
				clearTimeout(warningTimer);
			    StartWarningTimer();
//			    $scope.warning.show = false;
			}
			// Show idle timeout warning dialog.
			function IdleWarning() {
				clearTimeout(warningTimer);
				clearTimeout(timeoutTimer);
			    timeoutTimer = setTimeout(IdleTimeout, timoutNow);
			    $scope.warning.show = true;
			    $scope.warning.message = "You have been idle for " + timoutWarning/60000 + " minutes, you will be automatically log out after 1 minute if no activity detected";
			    $scope.$apply();
			    // Add code in the #timeout element to call ResetTimeOutTimer() if
			    // the "Stay Logged In" button is clicked
			}
			// Logout the user.
			function IdleTimeout() {
//				Restful.logout();
				$scope.logout();
			};
			$(document)
		    .on('click', ResetTimeOutTimer)
		    .on('mousemove', ResetTimeOutTimer);
//			--

			$rootScope.isSlocNull = function(item) {
            	if (item.idSloc === null) return false;
	
	          return true;
	        }
		} ]);
//function connect(id) {
//	var token = $localStorage.identity.refresh_token
//    var socket = new SockJS('http://localhost:8989/api/v1/websocket/chat?access_token='+token);
//    stompClient = Stomp.over(socket);
//    
//    stompClient.connect({}, function(frame) {
//        
//    	setConnected(true);
//        console.log('Connected: ' + frame);
//        stompClient.subscribe('/topic/messages/'+id, function(messageOutput) {
//        	
//            $rootScope[id] = JSON.parse(messageOutput.body);
//        });
//    });
//}

//integer month to roman
var arrayMonthRoman = ['I','II','III','IV','V','VI','VII','VIII','IX','X','XI','XII'];
var stompClient = null,notifClient = null;
function disconnect() {
    if(stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");

}