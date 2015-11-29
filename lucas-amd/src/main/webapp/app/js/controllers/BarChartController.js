'use strict';

amdApp.controller('BarChartController', ['$log', '$rootScope', '$scope', 'WidgetChartService', 'UtilsService', 'LocalStoreService',
function(log, rootScope, scope, WidgetChartService, UtilsService, LocalStoreService) {
	var dimensionsUpdateFn = scope.$on(scope.currentWidgetInstance.id + "|" + scope.currentWidgetInstance.widgetDefinition.id + "|ViewDimensions", function(event, args) {
		updateChartData(args);
	});
	//static data for broadcast and listensFor; once server gives proper data, need to remove this...
	scope.currentWidgetInstance.widgetDefinition.broadcastMap = {
		"Area" : "series.key",
		"Route" : "point.label"
	};
	scope.currentWidgetInstance.widgetDefinition.listensForList = ["Area", "Route", "Score"];
	var chartKey = scope.currentWidgetInstance.id + scope.currentWidgetInstance.widgetDefinition.id + 'barchartValues';
	WidgetChartService.getChartData(chartKey).then(function(data) {
		scope.chartData = WidgetChartService.processChartData(data);
		scope.stackedData = WidgetChartService.processChartData(data);

		//refer https://github.com/mbostock/d3/wiki/Time-Formatting
		/*scope.xAxisTickFormatFunction = function() {
		 return function(d) {
		 //%m - month as a decimal number [01,12]
		 return d3.time.format('%m')(new Date(d));
		 //%b - abbreviated month name [Jan, Feb]
		 //return d3.time.format('%b')(new Date(d));
		 };
		 };*/
		scope.xValues = function() {
			return function(d) {
				return d.label;
			};
		};
		scope.yValues = function() {
			return function(d) {
				return d.value;
			};
		};
	});
	var updateChartData = function(args) {
		var anchorPointsforWidgets = LocalStoreService.getLSItem(scope.currentWidgetInstance.id + "|" + scope.currentWidgetInstance.widgetDefinition.id + "|ViewAnchor");
		if(anchorPointsforWidgets){
			scope.currentWidgetInstance.actualViewConfig.anchor = anchorPointsforWidgets;
		}
		scope.currentWidgetInstance.actualViewConfig.width = args.width;
		scope.currentWidgetInstance.actualViewConfig.height = args.height;
		scope.currentWidgetInstance.actualViewConfig.zindex = 1;		
		chartKey = scope.currentWidgetInstance.id + scope.currentWidgetInstance.widgetDefinition.id + 'barchartValues';
		WidgetChartService.getChartData(chartKey).then(function(data) {
			scope.chartData = WidgetChartService.processChartData(data);
		});
	};
	scope.$on('$destroy', function() {
		dimensionsUpdateFn();
	});
	scope.$on('elementClick.directive', function(angularEvent, event) {
		if (scope.currentWidgetInstance.widgetDefinition.broadcastMap) {
			var getAreaArr = UtilsService.processBroadcastMap(event, scope.currentWidgetInstance.widgetDefinition.broadcastMap);
			var getItemsAsArr = UtilsService.processSelectedItemsAsArray(getAreaArr, scope.currentWidgetInstance.widgetDefinition.listensForList);
			rootScope.$broadcast('filteredItems', getItemsAsArr);
			scope.$apply();
		}
	});
}]);
