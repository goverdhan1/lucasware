'use strict';

amdApp.controller('AssignmentManagementController', ['$log', '$scope', '$rootScope', 'WidgetChartService', 'UtilsService', 'LocalStoreService',
function(log, scope, rootScope, WidgetChartService, UtilsService, LocalStoreService) {

	scope.chartHeight = 0;
	if (scope.widgetdetails.isMaximized) {
		scope.chartHeight = $(".panel-body").height();
	}
	var chartKey = scope.widgetdetails.clientId + scope.widgetdetails.widgetDefinition.id + 'barchartValues';
	//WidgetChartService.getChartData(chartKey).then(function(data) {
	scope.widgetdetails.data[0].chart = {
		"1" : {
			"key" : "Jack",
			"y" : 5
		},
		"2" : {
			"key" : "Jane",
			"y" : 15
		},
		"3" : {
			"key" : "Tom",
			"y" : 8
		},
		"4" : {
			"key" : "Dick",
			"y" : 4
		},
		"5" : {
			"key" : "Harry",
			"y" : 6
		},
		"6" : {
			"key" : "John",
			"y" : 12
		},
		"7" : {
			"key" : "Jill",
			"y" : 7
		}
	};
	scope.piechartData = WidgetChartService.processPieChartData(scope.widgetdetails.data[0].chart);
	scope.piedonutchartData = WidgetChartService.processPieChartData(scope.widgetdetails.data[0].chart);

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
	//});

	//81|pickline-by-wave-barchart-widget|10|ViewDimensions
	var dimensionsUpdateFn = scope.$on(scope.widgetdetails.clientId + "|" + scope.widgetdetails.widgetDefinition.name + "|" + scope.widgetdetails.widgetDefinition.id + "|ViewDimensions", function(event, args) {
		updateChartData(args);
	});
	var updateChartData = function(args) {
		var anchorPointsforWidgets = LocalStoreService.getLSItem(scope.widgetdetails.clientId + "|" + scope.widgetdetails.widgetDefinition.name + "|" + scope.widgetdetails.widgetDefinition.id + "|ViewAnchor");
		if (anchorPointsforWidgets) {
			scope.widgetdetails.actualViewConfig.anchor = anchorPointsforWidgets;
		}
		scope.widgetdetails.actualViewConfig.width = args.width;
		scope.widgetdetails.actualViewConfig.height = args.height;
		scope.piechartData = WidgetChartService.processPieChartData(scope.widgetdetails.data[0].chart);
		scope.piedonutchartData = WidgetChartService.processPieChartData(scope.widgetdetails.data[0].chart);
	};
	scope.$on('$destroy', function() {
		dimensionsUpdateFn();
	});
	scope.$on('elementClick.directive', function(angularEvent, event) {
		if (scope.widgetdetails.widgetDefinition.broadcastList) {
			var getAreaArr = UtilsService.processBroadcastMap(event, scope.widgetdetails.widgetDefinition.broadcastList);
			var getItemsAsArr = UtilsService.processSelectedItemsAsArray(getAreaArr, scope.widgetdetails.widgetDefinition.listensForList);
			rootScope.$broadcast('filteredItems', getItemsAsArr);
			scope.$apply();
		}
	});
}]);
