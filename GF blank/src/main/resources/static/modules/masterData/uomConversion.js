'user strict';
angular.module('UomConversionListApp').controller(
	'UomConversionListCtrl',
	[ '$scope', '$rootScope', 'Restful', 'CONSTANTS', '$rootScope','$location','$localStorage','$mdDialog','$timeout',
		function($scope, $rootScope, Restful, CONSTANTS, $rootScope,$location,$localStorage,$mdDialog,$timeout) {
			const EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
			$scope.isView = false;
			$scope.isEdit = false;
			$scope.filter ={
					"idVendor":"",
					"vendorName":"",
					"idMaterial":"",
					"materialName":"",
					"qtyOem":"",
					"uomOem":"",
					"qtySap":"",
					"uomSap":""
				}
			
			$scope.clearFilter = function(){
				$scope.filter ={
					"idVendor":"",
					"vendorName":"",
					"idMaterial":"",
					"materialName":"",
					"qtyOem":"",
					"uomOem":"",
					"qtySap":"",
					"uomSap":""
				}
			}
			
			$scope.changeParam = function(paramName, param){
				var tempData;
				if(paramName == 'idVendor'){
					tempData =  $scope.vendor.find(x => x.id == param);
					if(tempData){
						$scope.request.vendorName = tempData.name
						$scope.material = $scope.materialStart.filter(x=> (x.idSloc == tempData.idSloc && x.idPlant == tempData.idPlant))
					}else{
						$scope.request.vendorName = "";
						$scope.request.materialName  = "";
						$scope.request.idMaterial = "";
						$scope.material = {};
					}
				}else if(paramName == 'vendorName'){
					tempData =  $scope.vendor.find(x => x.name == param);
					if(tempData){
						$scope.request.idVendor = tempData.id
						$scope.request.materialName  = "";
						$scope.request.idMaterial = "";
						$scope.material = $scope.materialStart.filter(x=> (x.idSloc == tempData.idSloc && x.idPlant == tempData.idPlant))
					}else{
						$scope.request.idVendor = ""
						$scope.material = {};
					}
				}else if(paramName == 'idMaterial'){
					tempData =  $scope.material.find(x => x.id == param);
					(tempData ? $scope.request.materialName = tempData.name : $scope.request.materialName = "" );
				}else if(paramName == 'materialName'){
					tempData =  $scope.material.find(x => x.name == param);
					(tempData ? $scope.request.idMaterial = tempData.id : $scope.request.idMaterial = "" );
				}		
			}
			
			$scope.init = function(){
				Restful.httpGet('/api/v1/masterData/getParam?', null, null, callBackParam, callBackErrorParam, $scope, false, false);
				$scope.getRequest(0);
			}
			
			var callBackParam = function(response, result){
				if(response.data.idMessage > 0 ){
					$scope.vendor =  response.data.result.vendor;
					//$scope.material =  response.data.result.material;
					$scope.materialStart =  response.data.result.material;
				}else{
					$('#loaderContainer').css('display','none');
					alert('Userlogin tersedia')
				}
				
				$timeout(function() {
					return $scope.$apply(function() {
						$(".idVendor").chosen({width: "35%"})
						$(".vendorName").chosen({width: "35%"})
						$(".idMaterial").chosen({width: "35%"})
						$(".materialName").chosen({width: "35%"})
					});
				}, 1000);
				
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
						alert('List tidak tersedia')
					}
				}
		
				var callBackErrorGetList = function(){$('#loaderContainer').css('display','none');}
				Restful.httpPost('/api/v1/masterData/getOEM?', 
						$scope.filter, null, callBackGetList, callBackErrorGetList, $scope, false, false);
			}
			
		$scope.openView = function(a){
			$scope.create = true;
			$scope.view = true;
			$scope.edit = true;
			
			$scope.request = a;
			
		}
		
		$scope.openEdit = function(a){
			$scope.create = true;
			$scope.view = true;
			$scope.edit = false;
			
			$scope.request = a;
		}
		
		$scope.save = function(){
			$('#loaderContainer').css('display','block');
			var req = [];
			req.push($scope.request);
			Restful.httpPost('api/v1/masterData/createOEM',req,null,
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
			Restful.httpPost('api/v1/masterData/updateOEM',$scope.request,null,
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
		    Restful.httpPost('api/v1/masterData/deleteOEM',id,null,
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
		/*$scope.doDownload = function(){
			var data = new Blob(["C://Users/tjowi/Documents/Template Uom Conversion.xlsx"], { type: EXCEL_TYPE });
   			saveAs(data, 'Template Uom Conversion.xlsx');
		}*/
		var callBackErrorParam = function(){$('#loaderContainer').css('display','none');}
		var callBackErrorDelete = function(){$('#loaderContainer').css('display','none');}
		var callBackErrorSave = function(){$('#loaderContainer').css('display','none');}
		var callBackErrorUpdate = function(){$('#loaderContainer').css('display','none');}
		
		//---import excel
		$(function () {
		    $(":file").change(function (e) {
		    	$('#loaderContainer').css('display','block');
		    		var files = e.target.files;
			    	var f = files[0];
			    	if(f){
			    		var reader = new FileReader();
			    		var name = f.name;
			    		reader.onload = function(e) {
			    			var data = e.target.result;
		    				var wb;
		    				var arr = fixdata(data);
		    				try{
			    				wb = XLSX.read(btoa(arr), {type: 'base64'});
			    				process_wb(wb);
				    		}
				    		catch(err) {
				    			$('#loaderContainer').css('display','none');
				    			window.alert('unsupported file');
								$(":file").val('');
				    		}
			    		};
			    		
			    		reader.readAsArrayBuffer(f);
			    	}else{
			    		$('#loaderContainer').css('display','none');
			    	}
		    });
		});
		
		function fixdata(data) {
				var o = "", l = 0, w = 10240;
				for(; l<data.byteLength/w; ++l)
					o+=String.fromCharCode.apply(null,new Uint8Array(data.slice(l*w,l*w+w)));
				o+=String.fromCharCode.apply(null, new Uint8Array(data.slice(l*w)));
				return o;
		}
		
		function to_json(workbook) {
			var result = {};
			var i = 0;
			workbook.SheetNames.forEach(function(sheetName) {
				var roa = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
				if(roa.length > 0){
					result[i] = roa;
					i++;
				}
			});
			return result;
		}
		
		function process_wb(wb) {
			$scope.dataImport = [];
			var output = "";
			var temp = {};
			output = JSON.stringify(to_json(wb), 2, 2);
					
			$scope.check = JSON.parse(output);
			$scope.hasil = $scope.check[0];
			var header = "";
			for(var i=0; i < $scope.hasil.length; i++){
				temp = {
					"idVendor":$scope.hasil[i]['ID Vendor'],
				    "idMaterial":$scope.hasil[i]['Material No'],
				    "qtyOem":$scope.hasil[i]['Qty OEM'],
					"uomOem":$scope.hasil[i]['UoM OEM'],
					"qtySap":$scope.hasil[i]['Qty SAP'],
					"uomSap":$scope.hasil[i]['UoM SAP']
				}
				$scope.dataImport.push(temp);
			}
			
			$(":file").val('');
			$('#loaderContainer').css('display','block');
			Restful.httpPost('api/v1/masterData/createOEM',$scope.dataImport,null,
				callBackSave,callBackErrorSave,$scope,false,false);
		}
		
		
			
} ]);