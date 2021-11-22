'use strict';

app.factory('Restful', function ($http, $httpParamSerializer, CONSTANTS, Base64, $cookies, $location, $rootScope,$localStorage) {
	var parameter = {
		http:null,
		callBackFunction:null,
		callBackErrorFunction:null,
		scope:null
	};
	var currentObject = null;
	return{
		httpPost: function(path, varData, headers, callBackFunction, callBackErrorFunction, scope, isLogin, isRefreshFunction){
//			var xcsrfToken = $cookies.get(CONSTANTS.X_CSRF_TOKEN);
//			if(xcsrfToken != undefined){
//				if(headers == null)
//					headers = {"x-csrf-token": xcsrfToken};
//				else
//					headers["x-csrf-token"] =  xcsrfToken;
//			}
			var req = {
					method : CONSTANTS.METHOD_POST,
					url : CONSTANTS.ROOT_URL + path,
					data : varData,
					headers : headers
			};
			return this.executeRequest(req, callBackFunction, callBackErrorFunction, scope, isLogin, isRefreshFunction);
		},
		httpPut: function(path, varData, headers, callBackFunction, callBackErrorFunction, scope, isLogin, isRefreshFunction){
			var req = {
					method : CONSTANTS.METHOD_PUT,
					url : CONSTANTS.ROOT_URL + path,
					data : varData,
					headers : headers
			};
			return this.executeRequest(req, callBackFunction, callBackErrorFunction, scope, isLogin, isRefreshFunction);
		},
		httpGet: function(path, varData, headers, callBackFunction, callBackErrorFunction, scope){
			var req = {
					method : CONSTANTS.METHOD_GET,
					url : CONSTANTS.ROOT_URL + path,
					params : varData,
					headers : headers
			};
			return this.executeRequest(req, callBackFunction, callBackErrorFunction, scope, false);
		},
		httpDelete: function(path, varData, headers, callBackFunction, callBackErrorFunction, scope, isLogin, isRefreshFunction){
			var req = {
					method : CONSTANTS.METHOD_DELETE,
					url : CONSTANTS.ROOT_URL + path,
					data : varData,
					headers : headers
			};
			return this.executeRequest(req, callBackFunction, callBackErrorFunction, scope, isLogin, isRefreshFunction);
		},
		executeRequest: function(http, callBackFunction, callBackErrorFunction, scope, isLogin, isrefreshToken){
			currentObject = this;
			return $http(http)
			.then(function (response, status, headers, config){
				if(scope.dataLoading != undefined)
					scope.dataLoading = false;
				return callBackFunction(response, scope);
			},function(data){
				if(scope.dataLoading != undefined)
					scope.dataLoading = false;
				if(isLogin){
					currentObject.callErrorFunction(data, callBackErrorFunction, scope);
				}else{
					if(data.status == CONSTANTS.HTTP_CODE_UNAUTHORIZED){
						parameter.http = http;
						parameter.callBackFunction = callBackFunction;
						parameter.callBackErrorFunction = callBackErrorFunction;
						parameter.scope = scope;
						if(data.data.error == CONSTANTS.ERROR_CODE_003 && !isrefreshToken){
							currentObject.refreshToken(scope);
						}else{
							currentObject.logout();
						}
						
						
					}else if(data.status == CONSTANTS.HTTP_CODE_FORBIDDEN){
						scope.error = CONSTANTS.ERROR_MESSAGE_002;
					}else if(data.status == CONSTANTS.ERROR_CODE_CONNECTION_REFUSED){
						scope.error = CONSTANTS.ERROR_MESSAGE_CONNECTION_REFUSED;
					}else{
						return currentObject.callErrorFunction(data, callBackErrorFunction, scope);
					}
				}
			});
		},
		callErrorFunction: function(response, callBackErrorFunction, scope){
			if(!jQuery.isFunction(callBackErrorFunction))
				this.errorReturn(response, scope);
			else{
				return callBackErrorFunction(response, scope);
			}
		},
		refreshToken: function(scope){
			var headers = {
					"Authorization": "Basic " + Base64.encode(CONSTANTS.CLIENT_API_IDENDITY),
	                "Content-type": CONSTANTS.HTTP_HEADER_CONTENT_TYPE
				}
			this.httpPost(CONSTANTS.OAUTH_TOKEN_PATH, JSON.parse($localStorage.identity), headers, this.successRefreshToken, this.logout, scope, false, true);
		},
		errorReturn: function(data, scope){
			if(data.status == CONSTANTS.HTTP_CODE_UNAUTHORIZED){
				if(data.data.error == CONSTANTS.ERROR_CODE_001)
					this.logout();
			}if(data.status == CONSTANTS.ERROR_CODE_CONNECTION_REFUSED){
				scope.error = CONSTANTS.ERROR_MESSAGE_CONNECTION_REFUSED;
			}
		},
		prepareIdentity: function(data){
			$http.defaults.headers.common.Authorization = data.data.token_type + CONSTANTS.SPACE + data.data.access_token;
			$localStorage.identity = JSON.stringify({'refresh_token': data.data.refresh_token, 'grant_type': CONSTANTS.GRANT_TYPE_REFRESH_TOKEN});
			$localStorage.oauth= JSON.stringify(data.data);
            $rootScope.oauth = data.data;
		},
		successRefreshToken: function(data, http, callBackFunction, callBackErrorFunction, scope){
			currentObject.prepareIdentity(data);
			currentObject.executeRequest(parameter.http, parameter.callBackFunction, parameter.callBackErrorFunction, parameter.scope, false);
		},
		logout: function(){
			delete $rootScope.showTabletToolBar;
			delete $rootScope.showDesktopToolBar;
			delete $rootScope.oauth;
			$localStorage.$reset();
			window.location.href = CONSTANTS.ROOT_PATH;
		}
	}
});
app.factory('Base64', function () {
    /* jshint ignore:start */
 
    var keyStr = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=';
 
    return {
        encode: function (input) {
            var output = "";
            var chr1, chr2, chr3 = "";
            var enc1, enc2, enc3, enc4 = "";
            var i = 0;
 
            do {
                chr1 = input.charCodeAt(i++);
                chr2 = input.charCodeAt(i++);
                chr3 = input.charCodeAt(i++);
 
                enc1 = chr1 >> 2;
                enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                enc4 = chr3 & 63;
 
                if (isNaN(chr2)) {
                    enc3 = enc4 = 64;
                } else if (isNaN(chr3)) {
                    enc4 = 64;
                }
 
                output = output +
                    keyStr.charAt(enc1) +
                    keyStr.charAt(enc2) +
                    keyStr.charAt(enc3) +
                    keyStr.charAt(enc4);
                chr1 = chr2 = chr3 = "";
                enc1 = enc2 = enc3 = enc4 = "";
            } while (i < input.length);
 
            return output;
        },
 
        decode: function (input) {
            var output = "";
            var chr1, chr2, chr3 = "";
            var enc1, enc2, enc3, enc4 = "";
            var i = 0;
 
            // remove all characters that are not A-Z, a-z, 0-9, +, /, or =
            var base64test = /[^A-Za-z0-9\+\/\=]/g;
            if (base64test.exec(input)) {
                window.alert("There were invalid base64 characters in the input text.\n" +
                    "Valid base64 characters are A-Z, a-z, 0-9, '+', '/',and '='\n" +
                    "Expect errors in decoding.");
            }
            input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
 
            do {
                enc1 = keyStr.indexOf(input.charAt(i++));
                enc2 = keyStr.indexOf(input.charAt(i++));
                enc3 = keyStr.indexOf(input.charAt(i++));
                enc4 = keyStr.indexOf(input.charAt(i++));
 
                chr1 = (enc1 << 2) | (enc2 >> 4);
                chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
                chr3 = ((enc3 & 3) << 6) | enc4;
 
                output = output + String.fromCharCode(chr1);
 
                if (enc3 != 64) {
                    output = output + String.fromCharCode(chr2);
                }
                if (enc4 != 64) {
                    output = output + String.fromCharCode(chr3);
                }
 
                chr1 = chr2 = chr3 = "";
                enc1 = enc2 = enc3 = enc4 = "";
 
            } while (i < input.length);
 
            return output;
        }
    };
 
    /* jshint ignore:end */
});

app.factory('Scope', function () {
	var temp={};
	return {
		store:function(value){
			temp=value;
		},
		get:function(){
			return temp;
		}
	};
});


