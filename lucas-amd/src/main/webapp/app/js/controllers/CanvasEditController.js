'use strict';

amdApp.controller('CanvasEditController', ['$scope', '$state', '$stateParams', 'UtilsService',
function(scope, state, stateParams, UtilsService) {
	scope.CancelEdit = function() {
		state.go('^', stateParams);
	};
}]);
