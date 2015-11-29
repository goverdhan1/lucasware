'use strict';

amdApp.controller('WidgetController', ['$log', '$scope', '$rootScope', 'LocalStoreService', '$state', 'WidgetService', 'UtilsService', 'HomeCanvasListService',
function(log, scope, rootScope, LocalStoreService, state, WidgetService, UtilsService, HomeCanvasListService) {
	if (scope.widgetdetails) {
		//For createedituserform, somehow this requires to be added at controller level because the data for form has to be loaded based on the scope.receivedInfo watch being set
	    //grid widget and chart widget are working if I place this code at widgetController level.
	    //need to fix it for createedituserform    
	    if(scope.widgetdetails.isMaximized){
            LocalStoreService.addLSItem(scope, 'ActiveWidgetId', scope.widgetdetails.clientId);           
			var activeCanvasId = LocalStoreService.getLSItem('ActiveCanvasId');
			state.transitionTo("canvases.widgetdetail", {
				canvasId : activeCanvasId
			}, {
				location : "replace"
			});						
		}
		
		var zIndexWidgetInstance = LocalStoreService.getLSItem('zIndexAnchor');
		if (zIndexWidgetInstance === scope.widgetdetails.clientId + '|' + scope.widgetdetails.widgetDefinition.id) {
			scope.widgetdetails.actualViewConfig.zindex = 1;
			//update the updatedFavoriteCanvasList for the changes and add at LS.
            WidgetService.updateFavoriteCanvasListLocalStorage(scope.widgetdetails);
		} else {
			scope.widgetdetails.actualViewConfig.zindex = 0;
			//update the updatedFavoriteCanvasList for the changes and add at LS.
            WidgetService.updateFavoriteCanvasListLocalStorage(scope.widgetdetails);
		};
		scope.handleMaxState = function() {
			if (scope.widgetdetails && LocalStoreService) {
				scope.widgetdetails['isMaximized'] = true;               
               	//update the updatedFavoriteCanvasList for the changes and add at LS.
               	WidgetService.updateFavoriteCanvasListLocalStorage(scope.widgetdetails);
               	//additionally, you need a clientId stored at LS to lookup against that particular widgetinstanceobj of favcanvaslist.
               	//it is similar to activecanvasId; we name it as activeWidgetId
               	LocalStoreService.addLSItem(scope, 'ActiveWidgetId', scope.widgetdetails.clientId);
				var activeCanvasId = LocalStoreService.getLSItem('ActiveCanvasId');
				state.transitionTo("canvases.widgetdetail", {
					canvasId : activeCanvasId
				}, {
					location : "replace"
				});
			}
		};
        scope.components = function(){
            scope.$broadcast("launchComponents", scope.widgetdetails.clientId)
        };
        scope.searchComponents= function(){
            scope.$broadcast("launchDetails", scope.widgetdetails.clientId)
        };
        scope.diplayProductClassifications = function(){
            scope.$broadcast("launchProductClassifications", scope.widgetdetails.clientId);
        };
		scope.handleCogWidget = function(widgetInstance) {
            if (widgetInstance) {
               	LocalStoreService.addLSItem(scope, 'ActiveWidgetId', widgetInstance.clientId);
				WidgetService.loadCogMenuPopup(widgetInstance);				
			}
		};		
		scope.handleRemoveWidget = function(widgetInstance) {
			if(widgetInstance){				
               	LocalStoreService.addLSItem(scope, 'ActiveWidgetId', widgetInstance.clientId);
				WidgetService.loadDeleteWidgetPopup(widgetInstance);
			}			
		};
        scope.setActive = function(widgetInstance) {
            if (widgetInstance) {
                scope.widgetdetails = widgetInstance;
                LocalStoreService.addLSItem(scope, 'zIndexAnchor', scope.widgetdetails.clientId + "|" + scope.widgetdetails.widgetDefinition.id);
                scope.widgetdetails.actualViewConfig.zindex = 1;
                var zIndex = parseInt($("#wid" + scope.widgetdetails.clientId).css("zIndex"));
                // set zIndex only when not active
                if (zIndex != 1) {
                    $(".tile").css({
                        "zIndex": "0"
                    });
                    $("#wid" + scope.widgetdetails.clientId).css({
                        "zIndex": "1"
                    });
                }
            }
        };
 
		// when drop is completed swap the items in widgetInstanceList, scope.$apply() causes ng repeat will re render items
		scope.onDropComplete = function(index, event) {
			var activeCanvasId = LocalStoreService.getLSItem("ActiveCanvasId"),
                favoriteCanvasListUpdated = LocalStoreService.getLSItem('FavoriteCanvasListUpdated'),
                activeCanvas = HomeCanvasListService.findByCanvasId(favoriteCanvasListUpdated, activeCanvasId);

            // update widgetInteractionConfig
            for (var i = 0, l = activeCanvas.widgetInstanceList.length; scope.widgetInstanceList[i] && i < l; i++) {
                scope.widgetInstanceList[i].widgetInteractionConfig = activeCanvas.widgetInstanceList[i].widgetInteractionConfig;
            }
			// call UtilsService.swapArrayItems for swapping items, pass the indices and array to swap
			scope.widgetInstanceList = UtilsService.swapArrayItems(index, event.data, scope.widgetInstanceList);
			// Update the LocalStorage with the updated widgetInstanceList
			WidgetService.updateFavoriteCanvasListLocalStorageWithWidgetInstanceList(scope.widgetInstanceList);
			// trigger the digest life cycle
			scope.$apply();

			rootScope.$broadcast("updateWidgetInstanceList", [index, event.data]);
        };

	}
}]);
