'use strict';

/**
 * Services related to Show Available Widgets.
 */
amdApp.factory('ShowAvailableWidgetsService', ['Restangular', 'AuthTokenService', '$http', 'RestApiService',
function(restangular, AuthTokenService, http, restApiService) {
	return {
		// getWidgetsByCategory - is used to show the widgets by category in a widget palette thereby allowing user to load a specific widget in the active canvas.
		getWidgetsByCategory : function() {
			var localAuthenticatedInfo = AuthTokenService.getAuthToken();
			http.defaults.headers.common['Authentication-token'] = localAuthenticatedInfo.sessionToken;
			//var restUrl = 'users/userscount';
			var restUrl = 'widgetdefinitions';
            return restApiService.getOne(restUrl, null, null);
		}
	};
}]);
