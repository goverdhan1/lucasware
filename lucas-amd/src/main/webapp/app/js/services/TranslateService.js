'use strict';

/**
 * Services related to TranslateService.
 */
amdApp.factory('TranslateService', ['$translate',
function(translate) {
	return {
		getTranslatedContent : function() {
			var promise = translate('landing.headline').then(function(translation) {
				return translation;
			});
			return promise;
		}
	};
}]); 