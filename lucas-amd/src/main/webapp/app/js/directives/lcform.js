'use strict';
amdApp.directive('lcform', ['WidgetChartService', '$rootScope', 'LocalStoreService',
function(WidgetChartService, rootScope, LocalStoreService) {
	return {
		restrict : 'A',
		scope : true,
		link : function(scope, element, attrs) {
			var formName = attrs.formname;
			scope.getForm = function() {
				return 'views/forms/' + angular.lowercase(formName) + '.html';
			};
			var formDimensionsUpdateFn = scope.$on(scope.currentWidgetInstance.id + "|" + scope.currentWidgetInstance.widgetDefinition.id + "|ViewDimensions", function(event, args) {
				var anchorPointsforWidgets = LocalStoreService.getLSItem(scope.currentWidgetInstance.id + "|" + scope.currentWidgetInstance.widgetDefinition.id + "|ViewAnchor");
				if (anchorPointsforWidgets) {
					scope.currentWidgetInstance.actualViewConfig.anchor = anchorPointsforWidgets;
				}
				scope.currentWidgetInstance.actualViewConfig.width = args.width;
				scope.currentWidgetInstance.actualViewConfig.height = args.height;
				scope.$apply();
			});
			scope.$on('$destroy', function() {
				formDimensionsUpdateFn();
			});
		},
		template : '<div ng-include="getForm()"></div>'
	};
}]); 