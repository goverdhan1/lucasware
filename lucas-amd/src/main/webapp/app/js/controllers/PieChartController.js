'use strict';

amdApp.controller('PieChartController', ['$log', '$rootScope', '$scope', 'WidgetChartService', 'UtilsService',
function(log, rootScope, scope, WidgetChartService, UtilsService) {
	var dimensionsUpdateFn = scope.$on(scope.currentWidgetInstance.id + "|" + scope.currentWidgetInstance.canvasId + "|ViewDimensions", function(event, args) {
		updateChartData(args);
	});
	var chartKey = scope.currentWidgetInstance.id + scope.currentWidgetInstance.canvasId + 'piechartValues';
	WidgetChartService.getChartData(chartKey).then(function(data) {
		scope.piechartData = WidgetChartService.processChartData(data);

		scope.xFunctionPie = function() {
			return function(d) {
				return d.key;
			};
		};

		scope.yFunctionPie = function() {
			return function(d) {
				return d.y;
			};
		};
		var colorArrayPie = ['#000000', '#660000', '#CC0000', '#FF6666', '#FF3333', '#FF6666', '#FFE6E6'];
		scope.colorFunctionPie = function() {
			return function(d, i) {
				return colorArrayPie[i];
			};
		};

		scope.descriptionFunctionPie = function() {
			return function(d) {
				return d.key;
			};
		};

		scope.toolTipContentFunctionPie = function() {
			return function(key, x, y, e, graph) {
				return 'Super New Tooltip' + '<h1>' + key + '</h1>' + '<p>' + ' at ' + x + '</p>';
			};
		};

	});

	var updateChartData = function(args) {
		scope.currentWidgetInstance.actualViewConfig.width = args.width;
		scope.currentWidgetInstance.actualViewConfig.height = args.height;
		WidgetChartService.getChartData(chartKey).then(function(data) {
			scope.piechartData = WidgetChartService.processChartData(data);
		});
	};
	scope.$on('$destroy', function() {
		dimensionsUpdateFn();
	});
	scope.$on('elementClick.directive', function(angularEvent, event) {
		if (scope.currentWidgetInstance.widget.widgetActionConfig.broadcastMap) {
			var getAreaArr = UtilsService.processBroadcastMap(event, scope.currentWidgetInstance.widget.widgetActionConfig.broadcastMap);
			var getItemsAsArr = UtilsService.processSelectedItemsAsArray(getAreaArr, scope.currentWidgetInstance.widget.widgetActionConfig.listensFor);
			rootScope.$broadcast('filteredItems', getItemsAsArr);
			scope.$apply();
		}
	});
}]); 