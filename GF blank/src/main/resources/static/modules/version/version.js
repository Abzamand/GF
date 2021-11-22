'use strict';

angular.module('VersionApp',[])
.controller('VersionCtrl',['$rootScope','$scope','Restful', 'CONSTANTS','$log','Scope','$location','$mdDialog','$translate',
		function($rootScope,$scope,Restful,CONSTANTS,$log,Scope,$location,$mdDialog,$translate){
		$scope.init = function(){
			
		};
		
		
		};
		
		$scope.sync=function(data){
			$('#loaderContainer').css('display','block');
			Restful.httpGet('/api/v1/webservice/getSapPlant?requestConcat=' + data + "/" + date + "/" + check,null, null, callBackInit, callBackErrorInit, $scope, false, false);
		};

		var callBackInit=function(response,scope){
			if(response!=null){
				window.alert(response.data.message);
				$scope.clearToggle();
			}
			$('#loaderContainer').css('display','none');
		};
		
		var callBackErrorInit=function(){
			window.alert("error happen when sync");
			$scope.clearToggle();
			$('#loaderContainer').css('display','none');
		};
		
		$scope.toggleDate = function(prefix){
			for(var temp in $scope.toggle){
				if(temp == prefix){
					$scope.toggle[temp] = !$scope.toggle[temp]
				}else{
					$scope.toggle[temp] = false;
				}
			}
			$scope.requestCheck = false;
		};
		
		$(document).bind('click', function(event){
	        $scope.$apply(function(){
	        	$scope.clearToggle();
	        })
	    });
}]);