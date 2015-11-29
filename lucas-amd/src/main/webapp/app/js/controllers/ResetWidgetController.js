//https://github.com/johnpapa/angularjs-styleguide#iife
(function() {'use strict';

	angular.module('amdApp').controller('ResetWidgetController', ResetWidgetController);

	//inject angular variables
	ResetWidgetController.$inject = ['$rootScope', '$scope', 'LocalStoreService', '$modalInstance', 'WidgetService'];

	//based on declared angular variables, pass your specific args in that order
	function ResetWidgetController(rootScope, scope, LocalStoreService, modalInstance, WidgetService) {
		var resetWidgetScope = scope;

		//get widgetInstance of widget that is going to get deleted
		var ActiveWidgetId = LocalStoreService.getLSItem('ActiveWidgetId');
		resetWidgetScope.widgetInstance = WidgetService.getWidgetInstanceObj(ActiveWidgetId);
		
		//handler for ok btn click in delete confirmation popup
		resetWidgetScope.okBtnHandler = resetWidget;

		//handler for cancel btn click in delete confirmation popup
		resetWidgetScope.cancelBtnHandler = cancelResetWidget;

		function resetWidget(instanceObj) {
			rootScope.$broadcast("resetWidgetInvoked", instanceObj);
			modalInstance.close();
		};

		function cancelResetWidget() {
			modalInstance.close();
		};
	};

})(); 