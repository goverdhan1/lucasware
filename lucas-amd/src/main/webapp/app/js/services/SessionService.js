'use strict';

// This is the service responsible for storing user session-related information.
// loginStatus will be set, on a successful login, which can be watch'ed to show/hide certain sections of the application.
// userDetails stores the username, email, token etc.

amdApp.factory('SessionService', ['Restangular', '$http', '$location', 'RestApiService', 'LocalStoreService', 'AuthTokenService',
function(restangular, http, location, restApiService, LocalStoreService, AuthTokenService) {
	var userDetails = {};

	return {
		getUserDetails : function() {
			var localUserDetails = LocalStoreService.getLSItem('UserViewObject');
			if (localUserDetails) {
				return localUserDetails;
			} else {
				return userDetails;
			}
		},
		getUsername : function() {
			return userDetails.firstName + ' ' + userDetails.lastName;
		},
		isSessionActive : function() {
			return userDetails.loginStatus;
		},
		clear : function() {
            return restApiService.post('logout', null, null)
                .then(function (response) {
                    userDetails = {};
                    userDetails.loginStatus = false;

                    // User is logged-out and doesn't have a valid token anymore.
                    http.defaults.headers.common['Authentication-token'] = '';

                    //TODO:TRIPOD: Re-directing to dashboard. This might change.
                    location.path('/dashboard');
                    return response;
                });
		},
		create : function(data) {
            return restApiService.post('authenticate', null, data)
                .then(function (response) {
                    if (response != undefined || response != null) {
                        var authenticatedInfo = {
                            sessionToken: response.token,
                            userName: response.username
                        };
                        AuthTokenService.setAuthToken(authenticatedInfo);
                        //LocalStoreService.addLSItem(null, 'AuthenticatedInfo', authenticatedInfo);

                        //userDetails.loginStatus = true;
                        // We will send the token for all the rest calls following LOGIN
                        http.defaults.headers.common['Authentication-token'] = response.token;
                    }
                    return response;
                });
		},
		createStates : function() {
			return userDetails.canvasList;
		}
	};
}]);
