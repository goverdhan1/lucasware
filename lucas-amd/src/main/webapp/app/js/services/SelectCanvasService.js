'use strict';

/**
 * Services related to User object.
 */
amdApp.factory('SelectCanvasService', ['RestApiService', 'Restangular', '$q', 'LocalStoreService', function (restApiService, restangular, q, LocalStoreService) {
    var canvases;
    return {

        getAllScreens: function () {
            return restApiService.getOne('canvasesbycategories', null, null).then(function (response) {
                return response;
            });
        }
    };
}]);
