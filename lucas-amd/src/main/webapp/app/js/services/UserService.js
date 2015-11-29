'use strict';
/**
 * Services related to User object.
 */
amdApp.factory('UserService', ['RestApiService', 'Restangular', 'LocalStoreService', 'DomainPath', 'ClientPath', '$http', 'AuthTokenService', '$q', 'UtilsService', '$translate',
    function(restApiService, restangular, LocalStoreService, DomainPath, ClientPath, http, AuthTokenService, $q, UtilsService, $translate) {
        var userService = {
            logout : function(uName) {

                // Send logout request to server
                return restApiService.post('logout', null, null)
                    .then(function(success) {
                        //logout was successful

                        // clear all user specific data from local storage
                        console.log('***UserService - clearing local storage for user [' + uName + ']');
                        LocalStoreService.clearUserItems(uName);

                        //clear the cached widgetData - this shouldn't be persisted across login sessions
                        //by the client - it's the servers responsibility to save the current state. Next time
                        //the user logs in, this information will be fetched
                        UtilsService.clearAllWidgetData();

                        //show logout successful message
                        UtilsService.raiseNotification({
                            "message" : $translate.instant('constants.LOGOUT_SUCCESS')
                        });
                    },
                    function(error) {
                        console.log("User logout failed");
                        throw new LucasBusinessException(error);
                    }
                );
            },

            getUserList : function(pageSize, batchIndex) {
                var restUrl = 'users/userlist/' + pageSize + '/' + batchIndex;
                return restApiService.getAll(restUrl, null, null)
                    .then(function (response) {
                        return response;
                    });
            },

            getUserCount : function() {
                var restUrl = 'users/userscount';
                return restApiService.getAll(restUrl, null, null)
                    .then(function (response) {
                        return response;
                    });
            },
            getUserInfo : function() {
                var localAuthenticatedInfo = AuthTokenService.getAuthToken();
                http.defaults.headers.common['Authentication-token'] = localAuthenticatedInfo.sessionToken;
                var restUrl = 'users/' + localAuthenticatedInfo.userName;
                return restApiService.getAll(restUrl, null, null)
                    .then(function (response) {
                        return response;
                    });
            },

            // force refresh based on refresh parameter
            getProfile: function (refresh) {
                // Force the server call
                if (refresh) {
                    return userService.getUserInfo();
                } else {
                    var profileData = LocalStoreService.getLSItem('ProfileData');
                    if (profileData != null) {
                        var def = $q.defer();
                        def.resolve(profileData);
                        return def.promise;
                    } else {
                        return userService.getUserInfo();
                    }
                }
            },

            saveProfileToLS: function (profileData) {
                LocalStoreService.addLSItem(null, 'ProfileData', profileData);
            },

            getAmdLogo: function () {
                // mock user logo
                var def = $q.defer();
                def.resolve("images/logos/jen-website-logo.png");
                return def.promise;
            },

            getCustomerLogo: function () {
                // mock customer logo
                var def = $q.defer();
                def.resolve( "images/logos/jen-website-logo.png");
                return def.promise;
            },

            getUserPermissions: function () {
                var jackMockData = {
                    "status": "success",
                    "code": "200",
                    "message": "Request processed successfully",
                    "level": null,
                    "uniqueKey": null,
                    "token": null,
                    "explicitDismissal": null,
                    "data": {
                        "user": {
                            "userId": 3,
                            "userName": "jack123",
                            "permissionSet": [
                                "create-canvas"
                            ]
                        }
                    }
                };
                var jillMockData = {
                    "status": "success",
                    "code": "200",
                    "message": "Request processed successfully",
                    "level": null,
                    "uniqueKey": null,
                    "token": null,
                    "explicitDismissal": null,
                    "data": {
                        "user": {
                            "userId": 3,
                            "userName": "jack123",
                            "permissionSet": [
                            ]
                        }
                    }
                };
                var def = $q.defer();
                if (LocalStoreService.getLSItem('UserInfo') === "Jack") {
                    def.resolve(jackMockData);
                } else if (LocalStoreService.getLSItem('UserInfo') === "Jill") {
                    def.resolve(jillMockData);
                }
                return def.promise;
            },

            //
            //returns the currently logged in user, else returns NULL
            //
            getCurrentUser: function() {
                return LocalStoreService.getLSItem('UserName');
            }
        };
        return userService;
    }]);