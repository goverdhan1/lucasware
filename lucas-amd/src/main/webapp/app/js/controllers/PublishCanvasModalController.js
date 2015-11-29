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

amdApp.controller('PublishCanvasModalController', ['$state', '$rootScope', '$log', '$injector', '$scope', 'CanvasService', '$modalInstance', 'LocalStoreService', 'UtilsService', 'canvas',
    function(state, rootScope, log, $injector, scope, CanvasService, modalInstance, LocalStoreService, UtilsService, canvas) {
        scope.canvasName = canvas.canvasName;
        scope.closeModal = function() {
            modalInstance.close();
        };

        // used for publishing the canvas
        scope.publishCanvas = function() {
            // local variables
            var i, activeCanvasId, activeCanvasIndex, favoriteCanvasListUpdated, activeCanvas, canvasToSave;

            // To avoid circular dependecies use $injector
            var LocalStoreService = $injector.get('LocalStoreService');
            var HomeCanvasListService = $injector.get('HomeCanvasListService');
            var CanvasService = $injector.get('CanvasService');

            // Read from the LocalStorage 
            activeCanvasId = LocalStoreService.getLSItem("ActiveCanvasId");
            favoriteCanvasListUpdated = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');
            activeCanvas = HomeCanvasListService.findByCanvasId(favoriteCanvasListUpdated, activeCanvasId);
            activeCanvasIndex = HomeCanvasListService.getArrayIndexOfCanvasId(favoriteCanvasListUpdated, activeCanvasId);
           
            // prepare canvas to save
            var canvasToSave = {
                "createdByUserName": LocalStoreService.getLSItem("UserName"),
                "createdDateTime": null,
                "updatedByUserName": null,
                "updatedDateTime": new Date().toJSON(), // this formats to something like "2015-04-06T10:30:30.816Z",
                "canvasId": activeCanvasId, 
                "canvasName": activeCanvas.canvasName,
                "shortName": activeCanvas.canvasName,
                "canvasType": "COMPANY",
                "canvasLayout": null,
                "userSet": null,
                "widgetInstanceList": []
            };

            // get the widgetInstanceList from the active canvas
            canvasToSave = UtilsService.getCanvasWithWidgetInstanceList(activeCanvas, canvasToSave);

            // call the method to publish a canvas
            CanvasService.publishCanvas(canvasToSave).then(function(response) {
                // failure
                if (response && response.status == 'failure') {
                    log.error(response.message);
                } else {
                    // canvas published
                    log.info("canvas published");

                    var favoriteCanvasList = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');
                    favoriteCanvasList[activeCanvasIndex].canvasType = 'COMPANY'; 
                    LocalStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', favoriteCanvasList);
                    rootScope.$emit('refreshFavoriteCanvasListUpdated', favoriteCanvasList);

                    CanvasService.saveActiveCanvas({"canvasId":activeCanvasId});

                    // close the canvas
                    modalInstance.close();
                }
            }, function(error) {
                // failure
                log.error(error);
            });

        };
    }
]);