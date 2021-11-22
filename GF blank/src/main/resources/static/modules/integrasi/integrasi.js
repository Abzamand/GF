'user strict';

angular.module('IntegrasiApp').controller(
		'IntegrasiCtrl',
		[ '$scope', '$rootScope', 'Restful', 'CONSTANTS', '$rootScope','$location','$localStorage',
		function($scope, $rootScope, Restful, CONSTANTS, $rootScope,$location,$localStorage) {
			$scope.init = function(){
				Restful.httpGet('/api/v1/integrasi/getFilename?',null,null,
						callBackGetFilename,callBackErrorGetFilename,$scope,false,false);
						
				Restful.httpGet('/api/v1/integrasi/viewLog?',null,null,
						callBackGetLog,callBackErrorGetLog,$scope,false,false);
			}
			
			var callBackGetFilename = function(response,scope){
				$('#loaderContainer').css('display','none');
				if(response!=null && response.data.idMessage){
					$scope.filenames = response.data.result;
				}
				
			}
			
			$scope.cari = function(){
				Restful.httpGet('/api/v1/integrasi/viewLog?request='+$scope.search,null,null,
						callBackGetLog,callBackErrorGetLog,$scope,false,false);
			}
			
			
			var callBackGetLog = function(response,scope){
				$('#loaderContainer').css('display','none');
				if(response!=null && response.data.idMessage){
					$scope.log = response.data.result;
				}
				
				for(var d in $scope.log){
					$scope.log[d].time = new Date($scope.log[d].time)
				}
				
			}
			
			$scope.start = function(){
				var filename = $scope.filenames.filter(x => x.selected == true && x.tipe == 'M');
				var listFileName = []
				for(var i in filename){
					listFileName.push(filename[i].filename)
				}
				
				if(listFileName.length > 0){
					$('#loaderContainer').css('display','block');
					Restful.httpPost('/api/v1/integrasi/insertToTable',listFileName,null,callBackStart,callBackErrorStart,
						$scope,false,false);	
				}else{
					alert("Choose Document")
				}
				
			}
			
			var callBackStart = function(response,scope){
				Restful.httpGet('/api/v1/integrasi/viewLog?',null,null,
						callBackGetLog,callBackErrorGetLog,$scope,false,false);
			}
		
			$scope.generate = function(){
				var filename = $scope.filenames.filter(x => x.selected == true && x.tipe == 'T');
				var listFileName = []
				for(var i in filename){
					listFileName.push(filename[i].filename)
				}
				
				if(listFileName.length > 0){
					$('#loaderContainer').css('display','block');
				Restful.httpPost('/api/v1/integrasi/generateCSV',listFileName,null,callBackGenerate,callBackErrorGenerate,
						$scope,false,false);
				}else{
					alert("Choose Document")
				}
				
			}
			
			var callBackGenerate = function(response,scope){
				Restful.httpGet('/api/v1/integrasi/viewLog?',null,null,
						callBackGetLog,callBackErrorGetLog,$scope,false,false);
			}
			
			$scope.toCsv = function(){
				var filename = $scope.filenames.filter(x => x.selected == true && x.tipe == 'T');
				var listFileName = []
				for(var i in filename){
					listFileName.push(filename[i].filename)
				}
				if(listFileName.length > 0){
					$('#loaderContainer').css('display','block');
					Restful.httpPost('/api/v1/integrasi/downloadCSV',listFileName,null,callBackDownload,callBackErrorDownload,
						$scope,false,false);
				}else{
					alert("Choose Document")
				}
				
			}
			
			var callBackDownload = function(response,scope){
				$('#loaderContainer').css('display','none');
				if(response!=null && response.data.idMessage){
					$scope.dataCSV = response.data.result;
					
					for(var x in $scope.dataCSV){
						JSONToCSVConvertor($scope.dataCSV[x][0].data,$scope.dataCSV[x][0].filename)
					}
				}
			}
						
			$scope.cleanError = function(){
				$('#loaderContainer').css('display','block');
				Restful.httpPost('/api/v1/integrasi/cleanError',null,null,callBackClean,callBackErrorClean,
						$scope,false,false);
			}
			
			var callBackClean = function(response,scope){
					location.reload();
			}

			
			function JSONToCSVConvertor(arrData,filename) {
				var CSV = '';
				var row = "";
				for ( var index in arrData[0]) {
					row += index + ',';
				}
				row = row.slice(0, -1);
				CSV += row + '\r\n';
				for (var i = 0; i < arrData.length; i++) {
					var row = "";
					for ( var index in arrData[i]) {
						row += '"' + (arrData[i][index] ? arrData[i][index] : "") + '",';
					}
					row.slice(0, row.length - 1);
					CSV += row + '\r\n';
				}

				if (CSV == '') {
					alert("Invalid data");
					return;
				}
				var fileName = filename;
				var uri = 'data:text/csv;charset=utf-8,'
						+ escape(CSV);
				var link = document.createElement("a");
				link.href = uri;
				link.style = "visibility:hidden";
				link.download = fileName + ".csv";
				document.body.appendChild(link);
				link.click();
				document.body.removeChild(link);
				$('#loaderContainer').css('display','none');
			}
			
			var callBackErrorGenerate= function(){
				$('#loaderContainer').css('display','none');
			}
			var callBackErrorStart= function(){
				$('#loaderContainer').css('display','none');
			}
			var callBackErrorGetFilename = function(){
				$('#loaderContainer').css('display','none');
			}
			var callBackErrorGetLog = function(){
				$('#loaderContainer').css('display','none');
			}
			var callBackErrorClean= function(){
				$('#loaderContainer').css('display','none');
			}
			var callBackErrorDownload= function(){
				$('#loaderContainer').css('display','none');
			}
			
} ]);