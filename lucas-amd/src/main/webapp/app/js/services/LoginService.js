'use strict';

amdApp.factory('LoginService', ['$state', '$q', '$log', 'SessionService', 'UtilsService', 'HomeCanvasListService', 'LocalStoreService', 'UserService', 'CanvasService', function (state, $q, log, SessionService, UtilsService, HomeCanvasListService, LocalStoreService, UserService, CanvasService) {
    var stateParameters;
    var isAuthenticationOrAuthorizationFailed = false;
    return {
        setAuthenticationOrAuthorizationFailed: function (status) {
            isAuthenticationOrAuthorizationFailed = status;
        },
        getAuthenticationOrAuthorizationFailed: function () {
            return isAuthenticationOrAuthorizationFailed;
        },
        setStateParameters: function (params) {
            stateParameters = params;
        },
        getStateParameters: function () {
            return stateParameters
        },

        // Common method used for logging into the application
        login: function (credentials) {
            var def = $q.defer();
            var canvasId;

            SessionService.create(credentials).then(function (response) {

                var userInfo = null;
                var userConfig = null;
                var openCanvases = null;
                var activeCanvas = null;

                $q.all([
                    UserService.getUserInfo(),
                    CanvasService.getUserOpenCanvases(),
                    CanvasService.getUserActiveCanvas()
                 ]).then(function(responses) {
                        console.log(JSON.stringify(responses[0]));
                        console.log(JSON.stringify(responses[1]));
                        console.log(JSON.stringify(responses[2]));
                    userInfo = responses[0];
                    openCanvases = responses[1];
                    activeCanvas = responses[2];

                    HomeCanvasListService.getUserConfig(userInfo.username).then(function(result) {
                        result.activeCanvas = activeCanvas;
                        result.openCanvases = openCanvases;

                        onResult(userInfo, result);
                    },
                    function(error) {
                        log.error("error: " + error);
                    });
                },
                function(error) {
                    log.error("error: " + error);
                });

                var onResult = function (profileData, userConfigObj) {
                    UserService.saveProfileToLS(profileData);
                    LocalStoreService.addLSItem(null, 'UserName', profileData.username);
                    LocalStoreService.addLSItem(null, 'UserInfo', profileData.firstName);

                    var userConfigData = userConfigObj;

                    // user config
                    LocalStoreService.addLSItem(null, 'UserConfig', userConfigData);

                    //FavoriteCanvasList - denotes factory settings; is used for reset feature
                    LocalStoreService.addLSItem(null, 'FavoriteCanvasList', userConfigData.openCanvases);

                    //FavoriteCanvasListUpdated - denotes user changes; you create a copy of FavoriteCanvasList and any user changes as to be set/get of FavoriteCanvasListUpdated
                    LocalStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', userConfigData.openCanvases);

                    if (isAuthenticationOrAuthorizationFailed) {
                        state.go(stateParameters[1].name, stateParameters[2]
                            , {
                                location: true,
                                reload: true
                            });
                    } else {
                        // read canvasId based on seeHomeCanvasIndicator
                        if (userConfigData.seeHomeCanvasIndicator && userConfigData.homeCanvas) {
                            canvasId = userConfigData.homeCanvas.canvasId;
                        } else if (!userConfigData.seeHomeCanvasIndicator && userConfigData.activeCanvas) {
                            canvasId = userConfigData.activeCanvas.canvasId;
                        }
                        if (canvasId) {
                            var favoriteCanvasList = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');
                            var activeCanvasIndex =  HomeCanvasListService.getArrayIndexOfCanvasId(favoriteCanvasList, canvasId);

                            // Normal login state change code
                            CanvasService.getCanvasData(canvasId).then(function(response) {
                                console.log(response);
                                // Generate the clientIds
                                for (var i = 0; i < response.widgetInstanceList.length; i++) {
                                    response.widgetInstanceList[i]["clientId"] = UtilsService.getClientWidgetInstanceId();
                                    if (response.widgetInstanceList[i].dataURL) {
                                        response.widgetInstanceList[i].widgetDefinition.dataURL = response.widgetInstanceList[i].dataURL;
                                    }
                                }
                                favoriteCanvasList[activeCanvasIndex].isLoaded = true;
                                favoriteCanvasList[activeCanvasIndex].widgetInstanceList = response.widgetInstanceList;
                                LocalStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', favoriteCanvasList);
                                state.go('canvases.detail', {
                                    canvasId: canvasId
                                }, {
                                    location: true
                                });
                            });
                        } else {
                            // Normal login state change code
                            state.go('canvases.detail', {
                                canvasId: canvasId // favoriteCanvasData[0].canvasList[0].canvasId
                            }, {
                                location: false
                            });
                        }
                    }

                    def.resolve("success");
                };
            }, function (error) {
                log.error("error: " + error);
            });
            return def.promise;
        }
    };
}]);