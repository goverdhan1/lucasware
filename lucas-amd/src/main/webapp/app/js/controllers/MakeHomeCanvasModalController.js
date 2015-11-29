'use strict';

amdApp.controller('MakeHomeCanvasModalController', ['$scope', '$modalInstance', '$state', 'CanvasService', 'LocalStoreService', 'HomeCanvasListService', '$filter', '$rootScope',
    function (scope, modalInstance, state, CanvasService, LocalStoreService, HomeCanvasListService, $filter, rootScope) {
        // close the make home canvas confirmation modal
        scope.closeModal = function () {
            modalInstance.close();
        };

        var userConfig = LocalStoreService.getLSItem('UserConfig');
        var favoriteCanvasList = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');
        var canvas = HomeCanvasListService.findByCanvasId(favoriteCanvasList, CanvasService.getActiveCanvasId());

        // dynamically pass the canvasName for the translation, used in the template
        scope.translationData = {
            canvasName: canvas.canvasName
        };

        scope.makeHomeCanvas = function () {
            CanvasService.makeHomeCanvas(CanvasService.constructHomeCanvas()).then(function (response) {
                // update the Home Canvas in UserConfig
                userConfig.homeCanvas.canvasId = canvas.canvasId;
                userConfig.homeCanvas.canvasName = canvas.canvasName;
                userConfig.homeCanvas.canvasType = canvas.canvasType;
                userConfig.homeCanvas.isDataFetched = canvas.isDataFetched;
                userConfig.homeCanvas.widgetInstanceList = canvas.widgetInstanceList;

                // push the updated UserConfig to Local Storage
                LocalStoreService.addLSItem(null, 'UserConfig', userConfig);
                console.log(userConfig);
            });

            // close the modal
            scope.closeModal();
        };
    }]);
