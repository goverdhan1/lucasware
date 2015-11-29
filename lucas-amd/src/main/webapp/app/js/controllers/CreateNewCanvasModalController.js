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

amdApp.controller('CreateNewCanvasModalController', ['$state', '$rootScope', '$scope', 'CanvasService', '$modalInstance', 'LocalStoreService',
    function(state, rootScope, scope, CanvasService, modalInstance, LocalStoreService) {
        scope.closeModal = function() {
            modalInstance.close();
        };

        scope.saveCanvas = function() {
            console.log(scope.canvasName);
            // reset the validation flags
            scope.userForm.canvasname.$setValidity("canvasNameIsRequired", true);
            scope.userForm.canvasname.$setValidity("canvasNameAlreadyExists", true);

            if (!scope.canvasName || scope.canvasName.length == 0) {
                scope.userForm.canvasname.$setValidity("canvasNameIsRequired", false);
                return;
            }

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
                favoriteCanvasList.push({
                    canvasId: response.canvasId,
                    canvasType: "PRIVATE",
                    canvasName: response.canvasName
                });

                CanvasService.saveOpenCanvases(favoriteCanvasList);

                LocalStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', favoriteCanvasList);

                // load the newly created canvas
                state.go('canvases.detail', {
                    canvasId: response.canvasId
                }, {
                    location: true
                });

                rootScope.$emit('refreshFavoriteCanvasListUpdated', favoriteCanvasList);

                // close the canvas
                modalInstance.close();

            }, function(error) {
                // failure
                scope.userForm.canvasname.$setValidity("canvasNameAlreadyExists", false);
            });

        };
    }
]);