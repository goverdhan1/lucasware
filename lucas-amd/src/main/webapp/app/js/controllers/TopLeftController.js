/*
Lucas Systems Inc
11279 Perry Highway
Wexford
PA 15090
United States
 
 
The information in this file contains trade secrets and confidential
information which is the property of Lucas Systems Inc.
 
All trademarks, trade names, copyrights and other intellectual property
rights created, developed, embodied in or arising in connection with
this software shall remain the sole property of Lucas Systems Inc.
 
Copyright (c) Lucas Systems, 2014
ALL RIGHTS RESERVED
 
*/

'use strict';

amdApp.controller('TopLeftController', ['$rootScope', '$state', '$scope', '$modal', 'UtilsService', 'HomeCanvasListService', 'LocalStoreService', 'SecurityService', 'UserService',
    function (rootScope, state, scope, modal, UtilsService, HomeCanvasListService, LocalStoreService, SecurityService, UserService) {
        scope.isCreateCanvas = SecurityService.hasPermission('create-canvas');
        scope.showSave = false;

        rootScope.$on('refreshFavoriteCanvasListUpdated', function(event, favoriteCanvasList) {
            if (favoriteCanvasList && favoriteCanvasList.length == 0) {
                scope.activeCanvas = null;
            }
        });
        
        scope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams) {        	
            var favoriteCanvasList = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');                        

            if (favoriteCanvasList && favoriteCanvasList.length > 0) {
                scope.activeCanvas = HomeCanvasListService.findByCanvasId(favoriteCanvasList, toParams.canvasId);
                scope.CloseCanvas = function() {
                    var modalInstance = modal.open({
                        templateUrl : 'views/modals/canvas-detail-close.html',
                        controller : 'CanvasCloseController',
                        resolve : canvasScopeData(),
                        backdrop: 'static'
                    });
                };
                scope.EditCanvas = function() {
                };
                scope.ResetToDefaultCanvas = function(){
                	var favCanvasListOriginal = LocalStoreService.getLSItem("FavoriteCanvasList");
					LocalStoreService.removeLSItem('FavoriteCanvasListUpdated');
					LocalStoreService.addLSItem(scope, "FavoriteCanvasListUpdated", favCanvasListOriginal);
					rootScope.$broadcast("resetCanvas", favCanvasListOriginal);
					scope.isReset = false;
                };                
                scope.ShowWidgetsPopup = function() {                	
                    // broadcast stopWatches event
                    rootScope.$broadcast("stopWatches");
                    // display the popup
                    modal.open({
                        templateUrl: 'views/modals/widgets-pallete.html',
                        controller: 'WidgetsPalletteModalController',
                        backdrop: 'static'
                    });

                };
                var canvasScopeData = function() {
                    return {
                        activeCanvas : function() {
                            return scope.activeCanvas;
                        },
                        canvasBar : function() {
                            return favoriteCanvasList;
                        }
                    };
                };
                var checkCanvasChanges = function(){
					var favCanvasListOriginal = LocalStoreService.getLSItem("FavoriteCanvasList");
					var favoriteCanvasList = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');                	
		        	if(angular.equals(favCanvasListOriginal, favoriteCanvasList)){
		        		scope.isReset = false;
		        	} else {
		        		scope.isReset = true;
		        	}
				};
                var addNewWidget = scope.$on('addNewWidget', function(event, args) {
		        	checkCanvasChanges();
				});
				var updateWidget = scope.$on('UpdateWidget', function(event, args) {
					checkCanvasChanges();
				});
				checkCanvasChanges();
				scope.$on('$destroy', function() {
					updateWidget();
					addNewWidget();					
				});
            }
        });

        // get the AMD Logo
        UserService.getAmdLogo().then(function(logo) {
            scope.amdLogo = logo;
        });
    }]);