'use strict';

angular.module('SignApp')
	.controller('SignCtrl',
			['$scope', '$http', '$location', '$localStorage', 'Restful', 'CONSTANTS', 'Base64', '$rootScope', '$route', '$httpParamSerializer',
			function ($scope, $http, $location,  $localStorage, Restful, CONSTANTS, Base64, $rootScope, $route, $httpParamSerializer){
			 var onloadCallback=function() {
			        /* Place your recaptcha rendering code here */
					grecaptcha.render(document.getElementById('html_element'), {
				          'sitekey' : '6Leum8QUAAAAANkj9URqaaA1J3KaRBDmlYNBtEsP',
			        	  'callback' : verifyCallback
				        });
			    }
			 
			$scope.init=function(){
				onloadCallback()
				var a = typeof (onloadCallback)
				
			}
			
			
			 var verifyCallback = function(response) {
			        alert(response);
			        $scope.captcha = true;
			      };
				
			}]);

