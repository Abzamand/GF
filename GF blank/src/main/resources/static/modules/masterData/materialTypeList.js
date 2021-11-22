'user strict';
angular.module('MaterialTypeListApp').controller(
		'MaterialTypeListCtrl',
		[ '$scope', '$rootScope', 'Restful', 'CONSTANTS', '$rootScope','$location','$localStorage',
		function($scope, $rootScope, Restful, CONSTANTS, $rootScope,$location,$localStorage) {
			
			$scope.listEmployee = [
				{
					"id":"1",
					"name":"test"
				},
				{
					"id":"1",
					"name":"test"
				}
			]
			
			$scope.goEdit = function(){
				$rootScope.employeeEdit = true;
			}
			$scope.init = function(){
				$rootScope.employeeEdit = false;
			}
			
} ]);