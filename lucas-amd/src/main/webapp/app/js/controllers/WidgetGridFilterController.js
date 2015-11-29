'use strict';
	
amdApp.controller('WidgetGridFilterController', ['$log', '$scope',
function(log, scope) {
	
	//filter visibility controls
	var filterVisible = false;
	scope.isFilterVisible = function() {
		return filterVisible;
	};
	scope.showFilters = function() {
		filterVisible = true;
	};
	scope.hideFilters = function() {
		filterVisible = false;
	};
}]);
