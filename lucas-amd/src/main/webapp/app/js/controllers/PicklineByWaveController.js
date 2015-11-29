'use strict';

amdApp.controller('PicklineByWaveController', ['$rootScope', '$log', '$scope', 'WidgetChartService', 'UtilsService', 'LocalStoreService',
    function(rootScope, log, scope, WidgetChartService, UtilsService, LocalStoreService) {

        var self = this;
        self.staticClientId = scope.widgetdetails.clientId;


        // The data from the server is coming under data.data
        if (scope.widgetdetails.data && scope.widgetdetails.data.data) {
            scope.widgetdetails.data = scope.widgetdetails.data.data;
        }

        //Display chart with default orientation
        if(scope.widgetdetails.actualViewConfig.orientation.selected) {
            scope.orientation = scope.widgetdetails.actualViewConfig.orientation.selected;
        } else {
            scope.orientation = 'v';
        }

        scope.chartHeight = 0;
        if (scope.widgetdetails.isMaximized) {
            scope.chartHeight = $(".panel-body").height();
        }

        var chartKey = scope.widgetdetails.clientId + scope.widgetdetails.widgetDefinition.id + 'barchartValues';
        
        if (scope.widgetdetails.data && scope.widgetdetails.data.chart) {
            scope.horizontalChartData = WidgetChartService.processLineChartData(scope.widgetdetails.data.chart);
            scope.verticalChartData = WidgetChartService.processLineChartData(scope.widgetdetails.data.chart);
            scope.stackedData = WidgetChartService.processLineChartData(scope.widgetdetails.data.chart);
        }
        
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

        var dimensionsUpdateFn = scope.$on(scope.widgetdetails.clientId + "|" + scope.widgetdetails.widgetDefinition.name + "|" + scope.widgetdetails.widgetDefinition.id + "|ViewDimensions", function(event, args) {
            updateChartData(args);
        });
        var updateChartData = function(args) {
            scope.widgetdetails.actualViewConfig.width = args.width;
            scope.widgetdetails.actualViewConfig.height = args.height;
            scope.widgetdetails.actualViewConfig.zindex = 1;
            scope.horizontalChartData = WidgetChartService.processLineChartData(scope.widgetdetails.data.chart);
            scope.verticalChartData = WidgetChartService.processLineChartData(scope.widgetdetails.data.chart);
            // the chart is not getting updated upon resize
            scope.$apply();
        };
        scope.$on('$destroy', function() {
            dimensionsUpdateFn();
        });
        scope.$on('elementClick.directive', function(angularEvent, args) {
        	if (scope.widgetdetails.widgetDefinition.broadcastList) {
	            var obj = {
	                //"Completed": series.key, //may need legend info
	                "Score" : "point.value",
	                "Wave" : "point.label"
	            };
	            var getAreaArr = UtilsService.processBroadcastMap(args, obj);
	            var getItemsAsArr = UtilsService.processSelectedItemsAsArray(getAreaArr, scope.widgetdetails.widgetDefinition.broadcastList);
	            scope.$broadcast('fromcontroller', getItemsAsArr);
	            scope.$apply();
	        }
        });

        //apply cog menu settings
        scope.$on('updateExistingWidget', function(event, widgetInstance) {
            //we must only react to broadcast issue for our widgetInstance
            if (widgetInstance.clientId === self.staticClientId) {
                scope.widgetdetails.actualViewConfig = widgetInstance.actualViewConfig;
            }
        });
        
        //handle x axis and y axis click
        //jquery and controller specific functions
        var chartSelector = "#wid" + scope.widgetdetails.clientId + " svg";
		$(document).on("click", chartSelector, function(e) {
			handleAxisClick(e);
		});
		function handleAxisClick(e) {
			//since this is at specific controller level; we know what is x-axis and what is y-axis
			e.stopPropagation();
			//console.log(e.target.tagName);
			var selectedItem = e.target.__data__;
	
			if (selectedItem === parseInt(selectedItem, 10)) {
				//selected item is a integer
				var obj = {
					"Score" : selectedItem
				};
				broadcastAxisValue(obj);
			} else if (selectedItem.indexOf("Wave") !== -1) {
				var obj = {
					"Wave" : selectedItem
				};
				broadcastAxisValue(obj);
			} else {
				return;
			}
		};	
		function broadcastAxisValue(obj) {
			var areaArr = [];
			areaArr.push(obj);
			var getItemsAsArr = UtilsService.processSelectedItemsAsArray(areaArr, scope.widgetdetails.widgetDefinition.broadcastList);
			scope.$broadcast('fromcontroller', getItemsAsArr);
			scope.$apply();
		};        
    }]);