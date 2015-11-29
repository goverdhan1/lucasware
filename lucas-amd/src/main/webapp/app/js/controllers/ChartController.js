'use strict';

amdApp.controller('ChartController', ['$log', '$scope', 'LocalStoreService', 'WidgetGridService', '$timeout',
function(log, scope, LocalStoreService, WidgetGridService, timeout) {
	scope.chart = {
		type : 'pie'
	};
}]);
