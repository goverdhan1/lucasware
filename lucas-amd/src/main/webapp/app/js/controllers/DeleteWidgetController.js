//wrap all angular components (initially, the controllers) in an IIFE 
//https://github.com/johnpapa/angularjs-styleguide#iife
(function() {'use strict';

	angular.module('amdApp').controller('DeleteWidgetController', DeleteWidgetController);

	//inject angular variables
	DeleteWidgetController.$inject = ['$rootScope', '$scope', 'LocalStoreService', '$modalInstance', 'WidgetService'];

	//based on declared angular variables, pass your specific args in that order
	function DeleteWidgetController(rootScope, scope, LocalStoreService, modalInstance, WidgetService) {
		var delWidgetScope = scope;
		
		//get widgetInstance of widget that is going to get deleted
		delWidgetScope.widgetInstance = getWidgetInstanceOfDeleteWidget();
		function getWidgetInstanceOfDeleteWidget() {
			var activeWidgetId = LocalStoreService.getLSItem('ActiveWidgetId');
			var widgetInstance = WidgetService.getWidgetInstanceObj(activeWidgetId);
			if (widgetInstance.clientId) {
				return widgetInstance;
			}
		};
		
		//handler for ok btn click in delete confirmation popup
		delWidgetScope.okBtnHandler = deleteWidget;
		
		//handler for cancel btn click in delete confirmation popup
		delWidgetScope.cancelBtnHandler = cancelDeleteWidget;
		
		function deleteWidget(instanceObj) {
			if (instanceObj) {
				WidgetService.deleteWidgetInstance(instanceObj).then(function(response) {
					modalInstance.close();
				});
			}
		};

		function cancelDeleteWidget() {
			modalInstance.close();
		};
	};

})();
