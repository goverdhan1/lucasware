'use strict';

amdApp.controller('LoginModalController', ['$log', 'SessionService', '$scope', '$modal', 'invalidResponse', 'LocalStoreService', '$modalInstance', 'LoginService',
function(log, SessionService, scope, modal, invalidResponse, LocalStoreService, modalInstance, LoginService) {
	var accessedUrl = LocalStoreService.getLSItem('requestedUrl');
	if (invalidResponse) {
		scope.loggedIn = false;
		scope.form = {};
		scope.invalidResponse = invalidResponse;
		scope.accessMessage = "login.youWantedToAccess";
		scope.accessUrl = " " + accessedUrl;
		scope.loginModalSubmit = function() {
			var credentials = {
				username : scope.form.username,
				password : scope.form.password
			};
            LoginService.login(credentials)
                .then(function (res) {
                    if (res) {
                        scope.closeWelcomeModal();
                    }
                });
		};

		var handle1001 = scope.$on('1001:loginRequired', function(msg, data) {
			scope.form.username = '';
			scope.form.password = '';
			scope.errorPanel = {
				"message" : data.message
			};
		});
	}

	scope.closeWelcomeModal = function() {
		modalInstance.close('closed without selection');
	};

	scope.$on('$destroy', function() {
		handle1001();
	});

}]);
