'use strict';

amdApp.controller('WidgetDetailController', ['$rootScope', '$scope', '$state', 'LocalStoreService', 'WidgetService', 'HomeCanvasListService',
function(rootScope, scope, state, LocalStoreService, WidgetService, HomeCanvasListService) {
	//find for the widgetInstance which has isMaximized set to true in FavoriteCanvasListUpdated LS;
	//you have the clientId of the widget that is maximized; which is stored in LS as ActiveWidgetId; so use that.

	var ActiveWidgetId = LocalStoreService.getLSItem('ActiveWidgetId');

	//WidgetService.getWidgetInstanceObj(ActiveWidgetId key from LS) is a generic fn to retrieve the intended widgetinstance
	//stored in LS for showing up in maximized state, cog updates, delete etc.
	var maximizedWidgetDetails = WidgetService.getWidgetInstanceObj(ActiveWidgetId);
	if (maximizedWidgetDetails) {
		scope.widgetdetails = maximizedWidgetDetails;
		scope.handleMinState = function() {
			scope.widgetdetails['isMaximized'] = false;
			WidgetService.updateFavoriteCanvasListLocalStorage(scope.widgetdetails);
			var activeCanvasId = LocalStoreService.getLSItem('ActiveCanvasId');
			state.go('canvases.detail', {
				canvasId : activeCanvasId
			}, {
				location : true
			});
		};

        // opens cog menu
        scope.handleCogWidget = function(widgetInstance) {
            if (widgetInstance) {
                // set the active widget id
                LocalStoreService.addLSItem(scope, 'ActiveWidgetId', widgetInstance.clientId);
                // load cog menu pop up
                WidgetService.loadCogMenuPopup(widgetInstance);
            }
        };

        // removes the widget
        scope.handleRemoveWidget = function(widgetInstance) {
            if (widgetInstance) {
                // set the active widget id
                LocalStoreService.addLSItem(scope, 'ActiveWidgetId', widgetInstance.clientId);
                // load delete widget pop up
                WidgetService.loadDeleteWidgetPopup(widgetInstance);
            }
        };

	}

	// removes the widget from the DOM, when the event is raised
	var removeWidget = rootScope.$on('RemoveWidget', function(event, index, clientId) {
		if (ActiveWidgetId == clientId) {
			scope.widgetdetails = null;
		}
	});

	// handles clean up
	scope.$on('$destroy', function() {
		if (removeWidget) {
			removeWidget();
		}
	});

    scope.components = function(){
        scope.$broadcast("launchComponents", scope.widgetdetails.clientId)
    };
    scope.searchComponents= function(){
        scope.$broadcast("launchDetails", scope.widgetdetails.clientId)
    };
	var resetCanvas = scope.$on('resetCanvas', function(event, args) {
		if (args.length > 0) {
			var favoriteCanvasList = args;
			var activeCanvasId = LocalStoreService.getLSItem("ActiveCanvasId");
			var activeCanvas = HomeCanvasListService.findByCanvasId(favoriteCanvasList, activeCanvasId);
			if (activeCanvas) {
				//var widgetInstanceListforCanvas = activeCanvas.widgetInstanceList;
				console.log(activeCanvas);
				state.go('canvases.detail', {
					canvasId : activeCanvas.canvasId
				}, {
					location : true
				});
			} else {
				//go to first canvas because user is resetting from the newly created canvas, which obviously is going to get deleted.
				state.go('canvases.detail', {
					canvasId : favoriteCanvasList[0].canvasId
				}, {
					location : true
				});
			}

		}
	});
}]);
