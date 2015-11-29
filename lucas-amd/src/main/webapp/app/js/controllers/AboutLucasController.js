'use strict';

amdApp.controller('AboutLucasController', ['$log', '$scope', '$state', '$modalInstance', 'AboutService',
function(log, scope, state, modalInstance, AboutService) {

	AboutService.getAboutDetails().then(function(response) {
        var aboutDetails = response;
        scope.buildNumber = aboutDetails.buildNumber;
        scope.buildTimestamp = aboutDetails.buildTimestamp;
	}, function(error) {
		log.error("error: " + error);
	});
	scope.closeModal = function() {
		modalInstance.close('closed without selection');
	};
}]);
