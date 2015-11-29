'use strict';

amdApp.controller('LogoutModalController', ['$scope', '$modalInstance',
function(scope, modalInstance) {

	scope.closeModal = function() {
		modalInstance.close();
	};
}]);