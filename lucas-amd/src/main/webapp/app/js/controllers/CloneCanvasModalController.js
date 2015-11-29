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

amdApp.controller('CloneCanvasModalController', ['$state', '$rootScope', '$injector', '$scope', 'CanvasService', '$modalInstance', 'LocalStoreService', 'UtilsService',
    function(state, rootScope, $injector, scope, CanvasService, modalInstance, LocalStoreService, UtilsService) {
        scope.closeModal = function() {
            modalInstance.close();
        };

        // used for cloning the canvas
        scope.saveCanvas = function() {
            console.log(scope.canvasName);

            // reset the validation flags
            scope.userForm.canvasname.$setValidity("canvasNameIsRequired", true);
            scope.userForm.canvasname.$setValidity("canvasNameAlreadyExists", true);

            if (!scope.canvasName || scope.canvasName.length == 0) {
                scope.userForm.canvasname.$setValidity("canvasNameIsRequired", false);
                return;
            }

            // local variables
            var i, activeCanvasId, favoriteCanvasListUpdated, activeCanvas, canvasToSave;

            // To avoid circular dependecies use $injector
            var LocalStoreService = $injector.get('LocalStoreService');
            var HomeCanvasListService = $injector.get('HomeCanvasListService');
            var CanvasService = $injector.get('CanvasService');

            // Read from the LocalStorage 
            activeCanvasId = LocalStoreService.getLSItem("ActiveCanvasId");
            favoriteCanvasListUpdated = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');
            activeCanvas = HomeCanvasListService.findByCanvasId(favoriteCanvasListUpdated, activeCanvasId);

            // prepare canvas to save
            var canvasToSave = {
                "createdByUserName": LocalStoreService.getLSItem("UserName"),
                "createdDateTime": new Date().toJSON(),
                "updatedByUserName": null,
                "updatedDateTime": null, // this formats to something like "2015-04-06T10:30:30.816Z",
                "canvasId": null, // to create new canvas
                "canvasName": scope.canvasName,
                "shortName": scope.canvasName,
                "canvasType": "PRIVATE",
                "canvasLayout": null,
                "userSet": null,
                "widgetInstanceList": []
            };

            // get the widgetInstanceList from the active canvas
            canvasToSave = UtilsService.getCanvasWithWidgetInstanceList(activeCanvas, canvasToSave);

            if (canvasToSave.widgetInstanceList) {
                for (i = 0; i < canvasToSave.widgetInstanceList.length; i++) {
                    canvasToSave.widgetInstanceList[i].id = null;
                }
            }

            CanvasService.saveCanvas(canvasToSave).then(function(response) {

                // canvas created
                console.log("canvas created");

                var selectedCanvas = {
                    "canvasType": canvasToSave.canvasType.toUpperCase(),
                    "canvasId" : parseInt(response.canvasId),
                    "canvasName" : response.canvasName,
                    "widgetInstanceList": []
                };

                var userConfig = LocalStoreService.getLSItem('UserConfig');
                userConfig.openCanvases.push(selectedCanvas);
                LocalStoreService.addLSItem(null, 'UserConfig', userConfig);

                var favoriteCanvasList = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');

                // get widgetinstances for the selected canvas
                CanvasService.getCanvasData(selectedCanvas.canvasId).then(function(response) {
                    console.log(response);

                    // Generate the clientIds
                    if (response && response.widgetInstanceList) {
                        for (var i = 0; i < response.widgetInstanceList.length; i++) {
                            response.widgetInstanceList[i]["clientId"] = UtilsService.getClientWidgetInstanceId();
                        }
                    }

                    selectedCanvas.widgetInstanceList = response.widgetInstanceList;
                    favoriteCanvasList.push(selectedCanvas);
                    LocalStoreService.addLSItem(scope, 'FavoriteCanvasListUpdated', favoriteCanvasList);

                    CanvasService.saveOpenCanvases(favoriteCanvasList);


                    // load the newly created canvas
                    state.go('canvases.detail', {
                        canvasId: selectedCanvas.canvasId
                    }, {
                        location: true
                    });

                    rootScope.$emit('refreshFavoriteCanvasListUpdated', favoriteCanvasList);

                    // close the canvas
                    modalInstance.close();

                });
            }, function(error) {
                //canvas already exists
                scope.userForm.canvasname.$setValidity("canvasNameAlreadyExists", false);
            });

        };
    }
]);
