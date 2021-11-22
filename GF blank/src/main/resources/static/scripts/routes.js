'use strict';
var $routeProviderReference;

app.factory('httpErrorResponseInterceptor', ['$q', '$location',
                                               function($q, $location) {
                                                 return {
                                                   response: function(responseData) {
                                                     return responseData;
                                                   },
                                                   responseError: function error(response) {
                                                     switch (response.status) {
                                                       case 401:
                                                         $location.path('/');
                                                         break;
                                                       case 404:
                                                         $location.path('/404');
                                                         break;
                                                       default:
                                                         $location.path('/error');
                                                     }

                                                     return $q.reject(response);
                                                   }
                                                 };
                                               }
                                             ]);


app.config(['$routeProvider', '$locationProvider','$httpProvider', function($routeProvider, $locationProvider,$httpProvider){
		$routeProviderReference = $routeProvider;
		$httpProvider.interceptors.push('httpErrorResponseInterceptor');
//		$locationProvider.html5Mode(true);
	}])
	
	.run(['$rootScope', '$location', '$localStorage', '$http', 'CONSTANTS', 'Base64', 'Restful', '$route', '$routeParams',
    function ($rootScope, $location, $localStorage, $http, CONSTANTS, Base64, Restful, $route, $routeParams) {
		
        // keep user logged in after page refresh
		var oauth = $localStorage.oauth;
		if(oauth)
			$rootScope.oauth = JSON.parse(oauth);
		
		
		if($rootScope.oauth !== undefined){
			$http.defaults.headers.common.Authorization= $rootScope.oauth.token_type + CONSTANTS.SPACE + $rootScope.oauth.access_token;
			setMenuAfterLogin(JSON.parse($localStorage.menu), $route);
			$rootScope.menu = JSON.parse($localStorage.menu);
		}
			defaultRoutes($route);
		
        $rootScope.$on('$locationChangeStart', function (event, next, current) {
        	$('#timeHeader').fadeOut();
        	disconnect();
        	var path = $location.path().split('/')[1];
        	$rootScope.path = $location.path().split('/')[1];
        	if($location.path().split('/')[2]){
        		$rootScope.childPath = $location.path().split('/')[2];
        	}
        	$rootScope.hideTop = true;
        	// || path == "login"
            if ($rootScope.oauth == undefined) {
            	setToolbar($rootScope, false);
            	$rootScope.hideHeader = true;
            	$location.path('/');
            }else{
            	$rootScope.hideHeader = false;
            	setToolbar($rootScope, true);
            	if(path == 'login' || path == 'signUp'){
            		$rootScope.hideHeader = true;
            	}
            	$rootScope.broadcast();
            }
        });
    }]);

function setToolbar(rootScope, flag){
	rootScope.showDesktopToolBar = flag;
	rootScope.showTabletToolBar = flag;
	rootScope.showMobileToolBar=flag;
}

function setMenuAfterLogin(response,route){
	var menus = response.menus;
	for(var j=0;j<menus.length;j++){
		$routeProviderReference
		.when(menus[j].path,{
			templateUrl: menus[j].templateUrl,
			controller: menus[j].controller
		});
		var flag=false;
		if(menus[j].menu!=null){
			for(var k=0;k<menus[j].menu.length;k++){
				if(flag==false&&menus[j].menu[k].path.indexOf(':')==-1){
					menus[j].hasChild=true;
					menus[j].isOpen=false;
					flag=true;
				}
				$routeProviderReference
				.when(menus[j].menu[k].path,{
					templateUrl: menus[j].menu[k].template_url,
					controller: menus[j].menu[k].controller
				});
			}
		}
}
}
function defaultRoutes(route){
	$routeProviderReference
	.when('/dashboard',{
		templateUrl: 'modules/home/home.html',
		controller: 'HomeCtrl',
		resolve:{isCheck:function(){return true;}}
	})
	.when('/login',{
		templateUrl: 'modules/login/login.html',
		controller: 'LoginCtrl',
		resolve:{isCheck:function(){return false;}}
	})
	.when('/',{
		/*templateUrl: 'modules/home/home.html',
		controller: 'HomeCtrl',
		resolve:{isCheck:function(){return true;}}*/
		templateUrl: 'modules/login/login.html',
		controller: 'LoginCtrl',
		resolve:{isCheck:function(){return false;}}
	});
	
	
	route.reload();
}

