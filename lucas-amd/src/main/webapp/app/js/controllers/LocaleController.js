'use strict';

amdApp.controller('LocaleController', ['$translate', '$scope',
function(translate, scope) {
	scope.changeLanguage = function(langKey) {
		translate.use(langKey);
	};
}]);
