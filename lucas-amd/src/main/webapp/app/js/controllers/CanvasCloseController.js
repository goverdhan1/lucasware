'use strict';

amdApp.controller('CanvasCloseController', ['$rootScope', '$scope', '$state', '$modalInstance', '$stateParams', 'HomeCanvasListService', 'LocalStoreService', 'pickedCanvas', 'canvasBar', 'UtilsService', 'CanvasService',
function(rootScope, scope, state, modalInstance, stateParams, HomeCanvasListService, LocalStoreService, pickedCanvas, canvasBar, UtilsService, CanvasService) {
	scope.CanvasName = pickedCanvas.canvasName;

	scope.cancel = function() {
		state.go('canvases.detail', stateParams);
		modalInstance.close();
	};

	scope.closeCanvas = function() {
		var pickedCanvasIndex = HomeCanvasListService.getArrayIndexOfCanvasId(canvasBar, pickedCanvas.canvasId);
        if(pickedCanvasIndex>=0) {
            var userConfig = LocalStoreService.getLSItem('UserConfig');
            var ActiveCanvasId = LocalStoreService.getLSItem('ActiveCanvasId');
            var canvasIndex = UtilsService.findIndexByProperty(userConfig.openCanvases, "canvasId", pickedCanvas.canvasId);
            var saveNeeded = userConfig.openCanvases[canvasIndex].canvasId !== userConfig.activeCanvas.canvasId;
            var canvases = {};
            userConfig.openCanvases.splice(canvasIndex, 1);

            if(userConfig.openCanvases.length == 0) {
                userConfig.activeCanvas = {};
                userConfig.homeCanvas = {};
            }

            canvasBar.splice(pickedCanvasIndex, 1);

            if(canvasBar.length == 0) ActiveCanvasId = 0;

            if (pickedCanvas.canvasId === ActiveCanvasId ) {
                if(canvasBar[pickedCanvasIndex - 1]) {
                    ActiveCanvasId = canvasBar[pickedCanvasIndex - 1].canvasId
                } else {
                    if(canvasBar[pickedCanvasIndex]) {
                        ActiveCanvasId = canvasBar[pickedCanvasIndex].canvasId;
                    }
                }
            }

            if( ActiveCanvasId > 0) {
                canvases.activeCanvas = HomeCanvasListService.findByCanvasId(canvasBar, ActiveCanvasId);
            } else {
                canvases.activeCanvas = {
                    canvasId: 0
                };
            }

            canvases.openCanvases = canvasBar;

            if (CanvasService.saveOpenAndActiveCanvas(canvases)) {

                LocalStoreService.addLSItem(null, 'UserConfig', userConfig);

                // need to save only for inactive canvases, rest of the canvas saving
                // is handled in CanvasDetailController when the state is changed
                if(saveNeeded &&  userConfig.openCanvases.length !=0) {
                    // save the canvases
                    HomeCanvasListService.saveActiveAndOpenCanvases(userConfig);
                }

                LocalStoreService.removeLSItem('FavoriteCanvasListUpdated');
                LocalStoreService.addLSItem(scope, 'FavoriteCanvasListUpdated', canvasBar);

                rootScope.$emit('refreshFavoriteCanvasListUpdated', canvasBar);

                state.go('canvases.detail', {
                    canvasId: ActiveCanvasId
                });

            }
        }
        modalInstance.close();
	};
}]);
