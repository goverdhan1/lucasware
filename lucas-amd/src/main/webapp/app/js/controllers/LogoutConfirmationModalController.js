'use strict';

amdApp.controller('LogoutConfirmationModalController', ['$scope', '$modalInstance', '$state', 'UserService', 'LocalStoreService',
function(scope, modalInstance, state, UserService, LocalStoreService) {
	// close the logout confirmation modal
	scope.closeModal = function() {
		modalInstance.close();
	};
	scope.handleLogout = function() {
		// We pass username here as we'll use it to clear user specific data
		// from local storage. Username is stored in Local Storage when the
		// user logs in, so we can retrieve it from here
		UserService.logout(LocalStoreService.getLSItem('UserInfo')).then(function(response) {
			// Redirect to landing page
			state.go('indexpage.home');
		});

		scope.closeModal();
	};	
}]);
