'use strict';
amdApp.directive('lcchart', ['WidgetChartService', '$rootScope',
function(WidgetChartService, rootScope) {
	return {
		restrict : 'A',
		scope : true,
		link : function(scope, element, attrs) {
			var chartType = attrs.charttype;
			scope.getChart = function() {
				if (chartType === "multi-bar-horizontal" || chartType === "multi-bar-vertical") {
					var barchartValues = scope.currentWidgetInstance.data.chartValues;
					var chartKey = scope.currentWidgetInstance.id + scope.currentWidgetInstance.widgetDefinition.id + 'barchartValues';
					WidgetChartService.getChartData(chartKey, barchartValues);
					if (attrs.orientation) {
						var orientType = attrs.orientation;
						if (orientType === "v") {
							chartType = "multi-bar-vertical";
						} else if (orientType === "h") {
							chartType = "multi-bar-horizontal";
						}
					}
				} else if (chartType === "pie" || chartType === "pie-donut") {
					var piechartValues = scope.currentWidgetInstance.data.chartValues;
					var chartKey = scope.currentWidgetInstance.id + scope.currentWidgetInstance.widgetDefinition.id + 'piechartValues';
					WidgetChartService.getChartData(chartKey, piechartValues);
				}

				return 'views/charts/lc' + angular.lowercase(chartType) + '.html';
			};

			element.bind('click', function() {
				rootScope.$broadcast('clearFilterItems');
			});

		},
		template : '<div ng-include="getChart()"></div>'
	};
}]); 