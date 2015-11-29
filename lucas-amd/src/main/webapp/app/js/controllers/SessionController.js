'use strict';

amdApp.controller('SessionController', ['$scope', '$log', 'SessionService',
function(scope, log, SessionService) {
	scope.SessionService = SessionService;
	scope.username = SessionService.getUsername();
	scope.userLoggedIn = SessionService.isSessionActive();

	scope.$watch('SessionService.getUsername()', function(newVal, oldVal, scope) {
		//log.info("watching changes in username - old value: " + oldVal + ", new Value: " + newVal);
		if (newVal != undefined) {
			scope.username = newVal;
		}
	}, true);

	scope.$watch('SessionService.isSessionActive()', function(newVal, oldVal, scope) {
		//log.info("watching changes in login status - old value: " + oldVal + ", new Value: " + newVal);
		if (newVal != undefined) {
			scope.userLoggedIn = newVal;
		}
	}, true);

	scope.logoutSession = function() {
		SessionService.clear();
	};
}]);
