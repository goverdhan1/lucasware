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

amdApp.controller('CanvasDetailController', ['$scope', '$rootScope', '$state', 'CanvasService', 'UtilsService', 'LocalStoreService', 'HomeCanvasListService', 'WidgetService',
function(scope, rootScope, state, CanvasService, UtilsService, LocalStoreService, HomeCanvasListService, WidgetService) {

	// Used to set the min width and min height properties based on viewConfig and previous resize
	scope.getStyle = function(widgetInstance) {
		var obj = {};
		if (!widgetInstance || !widgetInstance.actualViewConfig) 
			return;

		var containerWidth = $('.uiViewContentCanvas').width();
		if (containerWidth < 300) 
        	containerWidth = 300;

        // originalResizedMinimumWidth
		if (widgetInstance.actualViewConfig.originalResizedMinimumWidth && widgetInstance.actualViewConfig.originalResizedMinimumWidth != 0) {
		    obj['min-width'] = widgetInstance.actualViewConfig.originalResizedMinimumWidth;
		} else if (widgetInstance.actualViewConfig.resizedMinimumWidth && widgetInstance.actualViewConfig.resizedMinimumWidth != 0) {
		    obj['min-width'] = widgetInstance.actualViewConfig.resizedMinimumWidth;
		} else {
		    obj['min-width'] = widgetInstance.actualViewConfig.minimumWidth;
		}

		if (obj['min-width'] > containerWidth) {
		    obj['min-width'] = containerWidth;
		}

		// originalResizedMinimumHeight
		if (widgetInstance.actualViewConfig.originalResizedMinimumHeight && widgetInstance.actualViewConfig.originalResizedMinimumHeight != 0) {
		    obj['min-height'] = widgetInstance.actualViewConfig.originalResizedMinimumHeight;
		} else if (widgetInstance.actualViewConfig.resizedMinimumHeight && widgetInstance.actualViewConfig.resizedMinimumHeight > widgetInstance.actualViewConfig.minimumHeight) {
		    obj['min-height'] = widgetInstance.actualViewConfig.resizedMinimumHeight;
		} else {
		    obj['min-height'] = widgetInstance.actualViewConfig.minimumHeight;
		}
	    
	    return obj;
	};

	scope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams) {
		var favoriteCanvasList = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');
		console.log(toState, toParams, fromState, fromParams);

		var init = function() {

			var userConfig = LocalStoreService.getLSItem('UserConfig');

			if (favoriteCanvasList && favoriteCanvasList.length > 0) {
				scope.activeCanvas = HomeCanvasListService.findByCanvasId(favoriteCanvasList, toParams.canvasId);

				userConfig.activeCanvas = scope.activeCanvas;
				
                if (scope.activeCanvas && !scope.activeCanvas.isLoaded) {
                    scope.activeCanvas.isLoaded = true;

                    CanvasService.getCanvasData(scope.activeCanvas.canvasId).then(function(response) {
                        console.log(response);
                        // Generate the clientIds
                        for (var i = 0; i < response.widgetInstanceList.length; i++) {
                            response.widgetInstanceList[i]["clientId"] = UtilsService.getClientWidgetInstanceId();
                            if (response.widgetInstanceList[i].dataURL) {
                            	response.widgetInstanceList[i].widgetDefinition.dataURL = response.widgetInstanceList[i].dataURL;
                            }
                        }
                        var FavoriteCanvasListUpdated = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');
                        var activeCanvasIndex = HomeCanvasListService.getArrayIndexOfCanvasId(FavoriteCanvasListUpdated, scope.activeCanvas.canvasId);
                        scope.widgetInstanceListforCanvas = scope.activeCanvas.widgetInstanceList = response.widgetInstanceList;
                        favoriteCanvasList[activeCanvasIndex].widgetInstanceList = response.widgetInstanceList;
                        LocalStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', favoriteCanvasList);
                    });
                }

                CanvasService.saveActiveCanvas(userConfig.activeCanvas);

				// update activecanvas in LS
				LocalStoreService.addLSItem(null,'UserConfig', userConfig);

				if (fromState.name != "indexpage.home" && fromState.name != "canvases.widgetdetail") {
					// save the updated canvases
					HomeCanvasListService.saveActiveAndOpenCanvases(userConfig);
				}
				
				if (scope.activeCanvas) {
					LocalStoreService.addLSItem(scope, 'ActiveCanvasId', scope.activeCanvas.canvasId);
					LocalStoreService.addLSItem(scope, 'ActiveCanvasName', scope.activeCanvas.canvasName);
					scope.widgetInstanceListforCanvas = scope.activeCanvas.widgetInstanceList;
				}
            } else {
				// When there are no canvases empty the variables
                if (fromState.name != "indexpage.home" && fromState.name != "canvases.widgetdetail") {
					userConfig.openCanvases = [];
					userConfig.activeCanvas = {};
					userConfig.homeCanvas = {};
					// save the updated canvases
                    HomeCanvasListService.saveActiveAndOpenCanvases(userConfig);
                }
            }
		};
		init();
	});

    var addNewWidget = scope.$on('addNewWidget', function(event, widgetInstance) {
        if (widgetInstance) {
        	// add the widget directly the list of widget instances - this avoids unnecessary reloads
            scope.widgetInstanceListforCanvas.push(widgetInstance);
            // trigger window resize for automatically sizing the DOM with out reloading the widgets
            UtilsService.triggerWindowResize();
        }
    });

	//watches for resetCanvas broadcast and updates the scope
	var resetCanvas = scope.$on('resetCanvas', function(event, args) {
		if(args.length>0){
			var favoriteCanvasList = args;
			var activeCanvasId = LocalStoreService.getLSItem("ActiveCanvasId");
			scope.activeCanvas = HomeCanvasListService.findByCanvasId(favoriteCanvasList, activeCanvasId);
			if(scope.activeCanvas){
				scope.widgetInstanceListforCanvas = scope.activeCanvas.widgetInstanceList;
			} else {
				//go to first canvas because user is resetting from the newly created canvas, which obviously is going to get deleted.
				state.go('canvases.detail', {
	                canvasId: favoriteCanvasList[0].canvasId
	            }, {
	                location: true
	            });
			}

		}
	});

	var refreshFavoriteCanvasListUpdated = rootScope.$on('refreshFavoriteCanvasListUpdated', function(event, favoriteCanvasList) {
        if (favoriteCanvasList.length == 0 ) {
        	scope.widgetInstanceListforCanvas = [];
        }
    });

	var removeWidget = rootScope.$on('RemoveWidget', function(event, index) {
		// remove the widget directly the list of widget instances - this avoids unnecessary reloads
		scope.widgetInstanceListforCanvas.splice(index, 1);
		// trigger window resize for automatically sizing the DOM with out reloading the widgets
		UtilsService.triggerWindowResize();
	});

	var updateWidget = scope.$on('UpdateWidget', function(event, args) {
		var currentWidgetInstance = args;
		currentWidgetInstance['updateWidget'] = true;
		scope.widgetdetails = currentWidgetInstance;
		//update the updatedFavoriteCanvasList for the changes and add at LS.
        WidgetService.updateFavoriteCanvasListLocalStorage(scope.widgetdetails);
		scope.$apply();
	});
	scope.$on('$destroy', function() {
		updateWidget();
		addNewWidget();
		removeWidget();
		refreshFavoriteCanvasListUpdated();
	});
}]);