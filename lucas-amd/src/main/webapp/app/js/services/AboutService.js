'use strict';

/**
 * Services related to Version about.
 */
amdApp.factory('AboutService', ['RestApiService', 'Restangular',
function(restApiService, restangular) {
	return {
		getAboutDetails : function() {
            return restApiService.getOne('about/buildinfo', null, null);
		}
	};
}]);
