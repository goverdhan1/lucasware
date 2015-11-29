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

amdApp.controller('TopRightController', ['$rootScope', '$modal', '$window', '$scope', '$state', 'LocalStoreService', 'UserService', 'SessionService', '$log', 'SecurityService', 'HomeCanvasListService',
    function (rootScope, modal, $window, scope, state, LocalStoreService, UserService, SessionService, log, SecurityService, HomeCanvasListService) {
        var userName = LocalStoreService.getLSItem('UserInfo');

        scope.isCreateCanvas = SecurityService.hasPermission('create-canvas');
        scope.isEditCompanyCanvas = SecurityService.hasPermission('edit-company-canvas');
        
        scope.isPublishCanvas = function() {
           return scope.activeCanvas.canvasType.toLowerCase() == 'private' && (SecurityService.hasPermission('publish-canvas') || SecurityService.hasPermission('edit-company-canvas'));
        };

        rootScope.$on('refreshFavoriteCanvasListUpdated', function(event, favoriteCanvasList) {
            if (favoriteCanvasList && favoriteCanvasList.length == 0) {
                scope.activeCanvas = null;
            }
        });

        scope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams) {   
            var favoriteCanvasList = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');                        
            if (favoriteCanvasList && favoriteCanvasList.length > 0) {
                scope.activeCanvas = HomeCanvasListService.findByCanvasId(favoriteCanvasList, toParams.canvasId);
            };
        });

        if (userName) {
            scope.userName = userName;
        } else {
            UserService.getUserInfo().then(function (userInfoData) {
                scope.userName = userInfoData.firstName;
                LocalStoreService.addLSItem(scope, 'UserInfo', userInfoData.firstName);
            }, function (error) {
                log.error("error: " + error);
            });
        }

        scope.makeHomeCanvas = function() {
            console.log("inside makeHomeCanvas");
            // Display Confirmation message
            var makeHomeCanvasModal = modal.open({
                templateUrl: 'views/modals/make-home-canvas.html',
                controller: 'MakeHomeCanvasModalController',
                backdrop: 'static'
            });
        };

        scope.handleLogout = function () {
            // Display logout confirmation message
            var logoutConfirmationModal = modal.open({
                templateUrl: 'views/modals/logout-confirmation.html',
                controller: 'LogoutConfirmationModalController',
                backdrop: 'static'
            });
        };

        // handles Manage Canvas
        scope.manageCanvas = function() {

        };

        // handles Clone Canvas
        scope.cloneCanvas = function() {
            log.info("Clone Canvas");
            var createCanvasModal = modal.open({
                templateUrl: 'views/modals/clone-canvas.html',
                controller: 'CloneCanvasModalController',
                backdrop: 'static'
            });
        };

        // handles Publish Canvas
        scope.publishCanvas = function() {
            log.info("Publish Canvas");
            // Read from the LocalStorage 
            var activeCanvasId = LocalStoreService.getLSItem("ActiveCanvasId");
            var favoriteCanvasListUpdated = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');
            var activeCanvas = HomeCanvasListService.findByCanvasId(favoriteCanvasListUpdated, activeCanvasId);
            var createCanvasModal = modal.open({
                templateUrl: 'views/modals/publish-canvas.html',
                controller: 'PublishCanvasModalController',
                backdrop: 'static',
                resolve: {
                    canvas: function() {
                        return {
                            canvasName: activeCanvas.name,
                            canvasId: activeCanvas.canvasId
                        }
                    }
                }
            });
        };

        // handles Resetting Canvas
        scope.resetCanvas = function() {

        };

        // handles My Preferences
        scope.myPreferences = function() {
            // Display preferences
            modal.open({
                templateUrl: 'views/modals/my-preferences.html',
                controller: 'MyPreferencesModalController',
                backdrop: 'static'
            });
        };

        scope.ShowWidgetsPopup = function() {                   
            // broadcast stopWatches event
            rootScope.$broadcast("stopWatches");
            // display the popup
            modal.open({
                templateUrl: 'views/modals/widgets-pallete.html',
                controller: 'WidgetsPalletteModalController',
                backdrop: 'static'
            });
        };

    }]);