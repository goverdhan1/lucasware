'use strict';

amdApp.factory('LocaleLoader', ['Restangular', 'ClientPath', 'JsonPath', 'UseLocalJsons',
function(Restangular, localClientPath, localJsonPath, UseLocalJsons) {

	// return loaderFn
	var localPath = localClientPath + localJsonPath;
	var getLocalePath = function(api) {
		UseLocalJsons = true;
		Restangular.setBaseUrl(localPath);
		Restangular.setRequestSuffix('.json');
		return api;
	};

	return function(options) {
        return Restangular.all(getLocalePath('i18n/' + options.key)).getList()
            .then(function(response) {
                return response[0].data;
            }
        );
	};
}]);