'use strict';

amdApp.controller('BottomLeftController', ['$scope', '$rootScope', '$state', '$modal', 'HomeCanvasListService', 'LocalStoreService', 'CanvasService',
function(scope, rootScope, state, modal, HomeCanvasListService, LocalStoreService, CanvasService) {
	var userCanvasList = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');

        rootScope.$on('refreshFavoriteCanvasListUpdated', function(event, favoriteCanvasList) {
            scope.canvasBar = favoriteCanvasList;
        });

    // return 'btn-primary' for the current active canvas based on state parameters canvas id 
    scope.getActiveClass = function(canvasId) {
        // return 'bnt-primary' if the current state is active
        if ($state.params && $state.params.canvasId && $state.params.canvasId == canvasId) {
            return 'btn-primary';
        }
    };

	if (userCanvasList.length > 0) {
		scope.canvasBar = userCanvasList;

        scope.CloseCanvas = function(canvasId) {
            // broadcast the saveActiveCanvas event
            // This event will be used to immediately save the current canvas in cases of swithing the active canvas or closing the canvas
            rootScope.$broadcast('saveActiveCanvas');

            scope.pickedCanvas = HomeCanvasListService.findByCanvasId(userCanvasList, canvasId);
            var modalInstance = modal.open({
                templateUrl : 'views/modals/canvas-detail-close.html',
                controller : 'CanvasCloseController',
                resolve : canvasScopeData(),
                backdrop: 'static' 
            });
        };
    }

    // The default sort options
    // Also start and update functions to catch the start and end index
    // of the element that has been displaced, and consequently update
    // the current indexes on the Local Storage
    scope.sortableOptions = {
        'ui-floating': true,
        'cursor': 'move',
        start: function(e, ui) {
            ui.item.startPos = ui.item.index();
        },
        stop: function(e, ui) {
            scope.rearrangeOpenCanvases(ui.item.startPos, ui.item.index())
        }
    };

    // Removes the item at the Start Index and places it after the Stop Index
    scope.rearrangeOpenCanvases = function(uiSortStartIndex, uiSortStopIndex) {
        var favCanvasList = LocalStoreService.getLSItem('FavoriteCanvasListUpdated'),
            uiSortStartCanvas = favCanvasList.splice(uiSortStartIndex, 1);

        favCanvasList.splice(uiSortStopIndex, 0, uiSortStartCanvas[0]);

        if(CanvasService.saveOpenCanvases(favCanvasList)) {
            LocalStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', favCanvasList);
            rootScope.$emit('refreshFavoriteCanvasListUpdated', favCanvasList);
        }
    }

    var canvasScopeData = function() {
        return {
            pickedCanvas : function() {
                return scope.pickedCanvas;
            },
            canvasBar : function() {
                return userCanvasList;
            }
        };
    };


    //listens for resetCanvas and updates the scope with the original fav canvas list
	var resetCanvas = scope.$on('resetCanvas', function(event, args) {
		if (args.length > 0) {
			scope.canvasBar = args;			
		}		
	});
}]);
