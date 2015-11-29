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

/**
 * Services related to Canvas
 */
amdApp.factory('CanvasService', ['RestApiService', 'LocalStoreService', 'HomeCanvasListService', '$stateParams', '$q',
    function(restApiService, LocalStoreService, HomeCanvasListService, $stateParams, $q) {
        var factory = {
            // makes the selected canvas a HomeCanvas
            makeHomeCanvas: function(homeCanvasObj) {
                /*
                    var homeCanvasObj = {
                         "userId": "100",
                         "username": "jack123",
                         "homeCanvas": {
                             "name": "xyz",
                             "canvasId": 100
                         }
                    };

                    return restApiService.post('/users/update', null, homeCanvasObj);

                */
                // mock the response
                var def = $q.defer();
                def.resolve("success");
                return def.promise;
            },

            // returns active canvas id by reading state parameters
            getActiveCanvasId: function() {
                // return the canvasId by reading $stateParams
                return $stateParams.canvasId;
            },

            // return the constructed home cavnas by reading state parameters
            constructHomeCanvas: function() {
                // read the user config
                var userConfig = LocalStoreService.getLSItem('UserConfig');
                var favoriteCanvasList = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');
                // get the user active canvas
                var activeCanvas = HomeCanvasListService.findByCanvasId(favoriteCanvasList, $stateParams.canvasId);

                // construct home canvas object based on userConfig and active canvas
                return {
                    "userId": userConfig.userId,
                    "username": userConfig.userName,
                    "homeCanvas": {
                        "name": activeCanvas.canvasName,
                        "canvasId": activeCanvas.canvasId
                    }
                };
            },

            getConfigureCompanyCanvases: function() {
                // return RestApiService.getOne('/getConfigureCompanyCanvases');
                // Mock data
                var def = $q.defer();
                def.resolve({
                    "configureCompanyCanvases": true
                });
                return def.promise;
            },

            saveCanvas: function(canvas) {
                return restApiService.post('canvases/save', null, canvas).then(
                    function(response) {
                        //success handler
                        return response;
                    },
                    function(error) {
                        //error handler
                        throw new LucasBusinessException(error);
                    }
                );
            },

            // This method publishes the canvas
            publishCanvas: function(canvas) {
                return restApiService.post('canvases/save', null, canvas);
            },

            saveOpenAndActiveCanvas: function(canvases) {
                return factory.saveOpenCanvases(canvases.openCanvases) &&
                    factory.saveActiveCanvas(canvases.activeCanvas);
            },

            saveOpenCanvases: function(canvases) {
                var userConfig = LocalStoreService.getLSItem('UserConfig');
                var openCanvases = {};
                openCanvases.username = userConfig.username;
                openCanvases.userId = userConfig.userId;
                openCanvases.openUserCanvases = [];

                angular.forEach(canvases, function(canvas, key) {
                    var newcanvas = {};
                    newcanvas.canvas = {};
                    newcanvas.displayOrder = key + 1;
                    newcanvas.canvas.canvasId = canvas.canvasId;
                    newcanvas.canvas.canvasType = canvas.canvasType;
                    openCanvases.openUserCanvases.push(newcanvas)
                })

                return restApiService.post('/users/canvases/open', null, openCanvases);
            },

            saveActiveCanvas: function(canvas) {
                if (!canvas)
                    return;

                var userConfig = LocalStoreService.getLSItem('UserConfig');
                var activeCanvas = {};
                activeCanvas.username = userConfig.username;
                activeCanvas.userId = userConfig.userId;
                activeCanvas.activeCanvas = {};
                activeCanvas.activeCanvas.canvasId = canvas.canvasId;

                return restApiService.post('/users/canvases/active', null, activeCanvas);
            },

            getUserOpenCanvases: function() {
                var authToken = LocalStoreService.getLSItem('AuthToken');

                return restApiService.getAll('users/' + authToken.userName + '/canvases/open', null, null)
                    .then(function (response) {
                        var openCanvases = [];
                        angular.forEach(response, function(canvas, key){
                            var newCanvas = canvas.canvas;
                            newCanvas.displayOrder = canvas.displayOrder;
                            openCanvases.push(newCanvas);
                        });

                        return openCanvases;
                    });
            },

            getUserActiveCanvas: function() {
                var authToken = LocalStoreService.getLSItem('AuthToken');
                return restApiService.getOne('users/' + authToken.userName + '/canvases/active', null, null);
            },

            deleteCanvas: function(canvasId) {
                return restApiService.post('/canvas/delete/' + canvasId , null, canvasId);
            },

            getCanvasData: function(canvasId) {
                return restApiService.getOne('/canvases/' + canvasId);
            }
        };

        return factory;
    }
]);