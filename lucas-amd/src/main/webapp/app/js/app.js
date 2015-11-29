'use strict';

//var amdApp = angular.module('amdApp', ['restangular', 'ui.bootstrap', 'd3', 'ui.router', 'ngAnimate']);
var amdApp = angular.module('amdApp', [
    'restangular',
    'ui.bootstrap',
    'ui.router',
    'pascalprecht.translate',
    'nvd3ChartDirectives',
    'ngAnimate',
    'ui.sortable',
    'ngDraggable',
    //ui-grid, and features
    'ui.grid',                //core
    'ui.grid.selection',      //row selection
    'ui.grid.edit',           //inline grid editing
    'ui.grid.pinning',        //column pinning
    'ui.grid.resizeColumns',  //column resizing
    'ui.grid.moveColumns',    //column moving
    'ui.grid.exporter',       //export to PDF/Excel/CSV
    'ui.grid.infiniteScroll', //infinite scrolling
    'ui.grid.autoResize'      //auto resize the grid to fit container
]);

amdApp.config(['$stateProvider', '$urlRouterProvider',
function(stateProvider, urlRouterProvider) {

	//REDIRECTS AND SETTING URLS

	// Use urlRouterProvider to configure any redirects (when) and invalid urls (otherwise).

	urlRouterProvider
	// The `when` method says if the url is ever the 1st param, then redirect to the 2nd param
	// Here we are just setting up some convenience urls.
	//.when('/welcome', '/welcome').when('/canvases', '/canvases')
	//.when('/c?id', '/canvases/:id')
	//.when('/canvases/:id', '/canvases/:id')
	// If the url is ever invalid, e.g. '/asdf', then redirect to '/' aka the home state
	.otherwise('/');

	//STATE CONFIGURATIONS

	// Use stateProvider to configure your states.
	stateProvider

	//for home
	.state("indexpage", {
		abstract : true,
		templateUrl : 'views/indexpage/index-tmpl.html'
	}).state("indexpage.home", {
		url : '/',
		views : {
			'logo' : {
				templateUrl : 'views/indexpage/logo.html',
				controller : function($scope) {
				}
			},
			'login' : {
				templateUrl : 'views/indexpage/login.html',
				controller : 'LoginController'
			},
			'locale' : {
				templateUrl : 'views/indexpage/locale.html',
				controller : 'LocaleController'
			},
			'content1' : {
				templateUrl : 'views/indexpage/content1.html',
			},
			'content2' : {
				templateUrl : 'views/indexpage/content2.html',
				controller : ['$log', 'HomeCanvasListService', '$scope', '$modal',
				function(log, HomeCanvasListService, scope, modal) {
					scope.handleAllPosts = function() {
						HomeCanvasListService.getUserFavoriteCanvas().then(function(favoriteCanvas) {
							console.log(favoriteCanvas);
						}, function(error) {
							log.error("error: " + error);
						});
					};
				}]

			},
			'footer' : {
				templateUrl : 'views/indexpage/footer.html',
				controller : 'FooterController'
			}
		}
		// You can pair a controller to your template. There *must* be a template to pair with.

	})

	//Canvases palette
	.state('canvases', {
		abstract : true,
		url : '/canvases',
		templateUrl : 'views/canvaspage/canvas-container.html',
	})
	//Canvas Detail palette
	.state('canvases.detail', {
		url : '/{canvasId:[0-9]{1,4}}',
		views : {
			'topLeftModule' : {
				templateUrl : 'views/canvaspage/topleft-tmpl.html',
				controller : 'TopLeftController'
			},
			'topCenterModule' : {
				templateUrl : 'views/canvaspage/topcenter-tmpl.html',
				controller : 'TopCenterController'
			},
			'topRightModule' : {
				templateUrl : 'views/canvaspage/topright-tmpl.html',
				controller : 'TopRightController'
			},
			// So this one is targeting the unnamed view within the parent state's template.
			'' : {
				templateUrl : 'views/canvaspage/canvas-detail.html',
				controller : 'CanvasDetailController'
			},
			'bottomLeftModule' : {
				templateUrl : 'views/canvaspage/bottomleft-tmpl.html',
				controller : 'BottomLeftController'
			},
			'bottomRightModule' : {
				templateUrl : 'views/canvaspage/bottomright-tmpl.html',
				controller : 'BottomRightController'
			}
		},
	}).state('canvases.widgetdetail', {
		url : '/wid/{canvasId:[0-9]{1,4}}',
		views : {
			'topLeftModule' : {
				templateUrl : 'views/canvaspage/topleft-tmpl.html',
				controller : 'TopLeftController'
			},
			'topCenterModule' : {
				templateUrl : 'views/canvaspage/topcenter-tmpl.html',
				controller : 'TopCenterController'
			},
			'topRightModule' : {
				templateUrl : 'views/canvaspage/topright-tmpl.html',
				controller : 'TopRightController'
			},
			// So this one is targeting the unnamed view within the parent state's template.
			'' : {
				templateUrl : 'views/widget-instance-detail.html',
				controller : 'WidgetDetailController'
			},
			'bottomLeftModule' : {
				templateUrl : 'views/canvaspage/bottomleft-tmpl.html',
				controller : 'BottomLeftController'
			},
			'bottomRightModule' : {
				templateUrl : 'views/canvaspage/bottomright-tmpl.html',
				controller : 'BottomRightController'
			}
		},
	}).state('canvases.detail.edit', {
		views : {
			'@canvases' : {
				templateUrl : 'views/canvaspage/canvas-detail-edit.html',
				controller : 'CanvasEditController'
			}
		}
	});
}]);

amdApp.run(['$rootScope', '$state', '$stateParams', '$modal', 'LoginService',
function($rootScope, $state, $stateParams, modal, LoginService) {
	// It's very handy to add references to $state and $stateParams to the $rootScope
	// so that you can access them from any scope within your applications.For example,
	// <li ui-sref-active="active }"> will set the <li> // to active whenever
	// 'contacts.list' or one of its decendents is active.

	window.$state = $state;

	$rootScope.$state = $state;
	$rootScope.$stateParams = $stateParams;
    // set the parameters, will be reused in case of authentication or authorization fail
    $rootScope.$on('$stateChangeStart', function (event, toState) {
    	// broadcast the saveActiveCanvas event
    	// This event will be used to immediately save the current canvas in cases of swithing the active canvas or closing the canvas
        $rootScope.$broadcast('saveActiveCanvas');

        LoginService.setStateParameters(arguments);
    });

    // handle authentication error 401 in app.js instead of handling in multiple files
    $rootScope.$on('401:loginRequired', function (msg, loginresponse) {
        LoginService.setAuthenticationOrAuthorizationFailed(true);
        console.log(msg, loginresponse);
        $rootScope.loginresponse = loginresponse;
        var invalidLoginInstance = modal.open({
            templateUrl: 'views/modals/login.html',
            controller: 'LoginModalController',
            windowClass: 'loginModal',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                invalidResponse: function () {
                    return $rootScope.loginresponse;
                }
            }
        });
    });


    // handle authorization error 403 in app.js instead of handling in multiple files
    $rootScope.$on('403:accessDenied', function (msg, loginresponse) {
        LoginService.setAuthenticationOrAuthorizationFailed(true);
        $rootScope.loginresponse = loginresponse;
        var invalidLoginInstance = modal.open({
            templateUrl: 'views/modals/login.html',
            controller: 'LoginModalController',
            windowClass: 'loginModal',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                invalidResponse: function () {
                    return $rootScope.loginresponse;
                }
            }
        });
    });

}]);

amdApp.config(['$httpProvider',
function(httpProvider) {
	delete httpProvider.defaults.headers.common['X-Requested-With'];
}]);

amdApp.config(['$httpProvider', '$locationProvider',
function(httpProvider, locationProvider) {
	httpProvider.responseInterceptors.push(amdApp.errorCheckInterceptor);
}]);

amdApp.config(['$translateProvider',
function(translateProvider) {
	translateProvider.useStorage('LangKeyStoreService');
	translateProvider.preferredLanguage('en');
	translateProvider.useLoader('LocaleLoader');
}]);

amdApp.config(['RestangularProvider',
function(RestangularProvider) {

	RestangularProvider.setResponseExtractor(function(response, operation, what, url) {
        //
        // all Restangular "getList()" operations require an array, nothing else - so if the response
        // is not an array we MUST make it one!
        //
        if (operation === "getList") {

            //if it's not an array, convert it
            if (!angular.isArray(response)) {
                var temp = [];
                temp.push(response);
                response = temp;
            }
        }

        return response;
	});
}]);

amdApp.run(['$rootScope', '$http', '$log', '$location', '$window', 'UtilsService', '$interval',
function(rootScope, http, log, location, window, UtilsService, $interval) {

	var canvasChanged = false,
		canvasSaveInProgress = false;

	var watchForLSChanges = function() {
		return localStorage.FavoriteCanvasListUpdated;
	};

    var saveActiveCanvas = function() {
        if (!canvasSaveInProgress && canvasChanged) {
            canvasSaveInProgress = true;
            try {
                // pass the date time, so it can be mocked and tested
                UtilsService.saveActiveCanvas(new Date().toJSON()).then(function(response) {
                    canvasSaveInProgress = false;
                }, function(response) {
                    canvasSaveInProgress = false;
                });
            } catch (error) {
                console.log(error);
                canvasSaveInProgress = false;
            }
            canvasChanged = false;
        }
    };

    // This event will be used to immediately save the current canvas in cases of swithing the active canvas or closing the canvas
    rootScope.$on('saveActiveCanvas', saveActiveCanvas);

	rootScope.$watch(watchForLSChanges, function(newValue, oldValue) {
		if (!angular.equals(newValue, oldValue)) {
			canvasChanged = true;
		}
	});

	// check for every 1 seconds. Will be read from the DB in the future tickets
    $interval(saveActiveCanvas, 1000);
}]);
