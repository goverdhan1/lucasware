'use strict';

amdApp.controller('TopCenterController', ['$scope', 'UtilsService', 'HomeCanvasListService', 'UserService',
function(scope, UtilsService, HomeCanvasListService, UserService) {
	scope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams) {
		var canvasInfo = HomeCanvasListService.getCanvasData();
		if (canvasInfo) {
			scope.canvasBar = canvasInfo;
			scope.activeCanvas = UtilsService.findById(scope.canvasBar, toParams.canvasId);
		}
	});

	// get the customer logo
	UserService.getCustomerLogo().then(function(logo) {
		scope.customerLogo = logo;
	});
}]);