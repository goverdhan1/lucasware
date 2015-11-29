'use strict';

amdApp.controller('ShowCogmenuWidgetController', ['$scope', 'LocalStoreService', '$modalInstance', 'WidgetService', 'CogService',
function(scope, LocalStoreService, modalInstance, WidgetService, CogService) {

	var self = this;

	//self.cogWidgetInstance = LocalStoreService.getLSItem('CogWidgetInstance');
	var ActiveWidgetId = LocalStoreService.getLSItem('ActiveWidgetId');

	//WidgetService.getWidgetInstanceObj(ActiveWidgetId key from LS) is a generic fn to retrieve the intended widgetinstance
	//stored in LS for showing up in maximized state, cog updates, delete etc.
	self.cogWidgetInstance = WidgetService.getWidgetInstanceObj(ActiveWidgetId);

	if (self.cogWidgetInstance) {
		scope.cogWidgetInstance = self.cogWidgetInstance;

        if (!(angular.equals(scope.cogWidgetInstance.actualViewConfig.autoRefreshConfig, scope.cogWidgetInstance.widgetDefinition.defaultViewConfig.autoRefreshConfig)) ||
            !(angular.equals(scope.cogWidgetInstance.actualViewConfig.dateFormat, scope.cogWidgetInstance.widgetDefinition.defaultViewConfig.dateFormat)) ||
            (scope.cogWidgetInstance.actualViewConfig.gridColumns && !(angular.equals(scope.cogWidgetInstance.actualViewConfig.gridColumns, scope.cogWidgetInstance.widgetDefinition.defaultViewConfig.gridColumns))) ||
            !(angular.equals(scope.cogWidgetInstance.actualViewConfig.orientation, scope.cogWidgetInstance.widgetDefinition.defaultViewConfig.orientation))) {
            scope.isReset = true;
        } else {
            scope.isReset = false;
        }

        if (scope.cogWidgetInstance.widgetInteractionConfig && scope.cogWidgetInstance.widgetInteractionConfig.length) {
            scope.isReset = true;
        }

		if (typeof scope.cogWidgetInstance.widgetInteractionConfig === "undefined") {
			scope.cogWidgetInstance.widgetInteractionConfig = [];
		}

		scope.$watch('cogWidgetInstance.actualViewConfig', function(newVal, oldVal) {
			if (!(angular.equals(newVal, oldVal))) {
				scope.isInstanceModified = true;
			} else {
				scope.isInstanceModified = false;
			}
		}, true);

		scope.$watch('cogWidgetInstance.widgetInteractionConfig', function(newVal, oldVal) {
			if (!(angular.equals(newVal, oldVal))) {
				scope.isInstanceModified = true;
			} else {
				scope.isInstanceModified = false;
			}
		}, true);

		scope.saveCogSettings = function(instanceObj) {
			if (instanceObj) {
				CogService.refreshWidgetWithUpdatedValues(instanceObj);
				modalInstance.close();
			}
		};

		scope.resetCogSettings = function(instanceObj) {
			if (instanceObj) {
				WidgetService.loadResetWidgetPopup(instanceObj);
			}
		};

		//event listener for cog menu changes
		scope.$on('resetWidgetInvoked', function(event, args) {
			scope.cogWidgetInstance.actualViewConfig = scope.cogWidgetInstance.widgetDefinition.defaultViewConfig;
			scope.cogWidgetInstance.widgetInteractionConfig = [];
			scope.saveCogSettings(scope.cogWidgetInstance);			
		});

		scope.cancel = function() {
			modalInstance.close();
		};
	}

}]);
