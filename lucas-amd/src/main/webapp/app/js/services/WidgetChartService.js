'use strict';

/**
 * Services related to grid placed on a widget.
 */
amdApp.factory('WidgetChartService', ['$log', '$q', 'LocalStoreService', 'UtilsService',
function(log, q, LocalStoreService, UtilsService) {
	var chartCache = {};
	return {
		getChartData : function(key, data) {
			var deferred = q.defer();
			if (chartCache[key]) {
				deferred.resolve(chartCache[key]);
			} else {
				chartCache[key] = data;
				deferred.resolve(chartCache[key]);
			}
			return deferred.promise;
		},
		processLineChartData : function(chartCache) {
			var objCollection = [];
			var propertyName = "";
			var index = 0;
			for (var property in chartCache) {
				var obj = {
					'key' : '',
					'color' : '',
					'values' : {}
				};
				propertyName = property;
				obj.key = propertyName;
				obj.color = UtilsService.getRandomHexColor();
				obj.values = chartCache[property];
				objCollection[index] = obj;
				index++;
				propertyName = "";
			};
			return objCollection;
		},
		processPieChartData : function(chartCache) {
			var pieData = [{
				key : "One",
				y : 5
			}, {
				key : "Two",
				y : 2
			}, {
				key : "Three",
				y : 9
			}, {
				key : "Four",
				y : 7
			}, {
				key : "Five",
				y : 4
			}, {
				key : "Six",
				y : 3
			}, {
				key : "Seven",
				y : 9
			}];
			return pieData;
		},
		/*processSelectedArea : function(listensFor, metaId) {
			var listenConfig = LocalStoreService.getLSItem(metaId);
			var listenObj = listensFor;
			return this.processSelectedItemsArr(listenObj, listenConfig);
		},
		processSelectedItemsArr : function(listenObj, listenConfig) {
			if (listenConfig) {
				console.log(listenConfig);
				console.log(listenObj);
				var aMap = {};
				var obj = {};
				var objArr = [];
				if (listenObj.label) {
					aMap["Label"] = listenObj.label;
				} else if (listenObj.value) {
					aMap["Key"] = listenObj.series.key;
					aMap["Label"] = listenObj.point.label;
					aMap["Value"] = listenObj.point.value;
				}
				objArr.push(aMap);
				return objArr;
			}
		}*/
	};
}]);
