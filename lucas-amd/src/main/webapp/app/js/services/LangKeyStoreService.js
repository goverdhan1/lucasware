//LangKeyStoreService
'use strict';

/**
 * Services related to angular translate i18n localstorage object.
 */
amdApp.factory('LangKeyStoreService', [ '$window',
function($window) {
	var storage = ( typeof $window.localStorage === 'undefined') ? undefined : $window.localStorage;
	return {
		set : function(name, value) {
			storage.setItem(name, value);
		},
		get : function(name) {
			var item = storage.getItem(name);
			return item;
		}
	};
}]);