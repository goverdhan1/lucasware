(function() {'use strict';

	angular.module('amdApp').directive('dualSelect', dualSelect);

	//inject angular variables
	dualSelect.$inject = ['WidgetService', 'UtilsService'];

	//based on declared angular variables, pass your specific args in that order
	function dualSelect(WidgetService, UtilsService) {
		var linkFunction = function(scope, element, attrs) {
			var dualSelectScope = scope;

			//handlers
			dualSelectScope.moveItemsToListensForList = moveItemsToListensForList;
			dualSelectScope.moveItemsToReactToList = moveItemsToReactToList;
			dualSelectScope.moveAllItemsToListensForList = moveAllItemsToListensForList;
			dualSelectScope.moveAllItemsToReactToList = moveAllItemsToReactToList;

			function moveItemsToListensForList(itemArr) {
				var updatedListensForList = moveItems(itemArr, dualSelectScope.widgetinstance.actualViewConfig.listensForList, dualSelectScope.widgetinstance.actualViewConfig.reactToList);
				dualSelectScope.selectedReactToValue = [];
			};
			function moveItemsToReactToList(itemArr) {
				var updatedReactToList = moveItems(itemArr, dualSelectScope.widgetinstance.actualViewConfig.reactToList, dualSelectScope.widgetinstance.actualViewConfig.listensForList);
				dualSelectScope.selectedListenForValue = [];
			};
			function moveAllItemsToListensForList(itemArr) {
				var updatedListensForList = moveAllItems(itemArr, dualSelectScope.widgetinstance.actualViewConfig.listensForList);
				dualSelectScope.widgetinstance.actualViewConfig.listensForList = updatedListensForList;
				dualSelectScope.widgetinstance.actualViewConfig.reactToList = [];
			};
			function moveAllItemsToReactToList(itemArr) {
				var updatedReactToList = moveAllItems(itemArr, dualSelectScope.widgetinstance.actualViewConfig.reactToList);
				dualSelectScope.widgetinstance.actualViewConfig['reactToList'] = updatedReactToList;
				dualSelectScope.widgetinstance.actualViewConfig.listensForList = [];
			};
		};

		return {
			restrict : 'E',
			scope : {
				"widgetinstance" : "="
			},
			link : linkFunction,
			templateUrl : 'views/dual-select-dropdown.html'
		};
		function moveItems(selectedItemsToMove, toArr, fromArr) {
			//selectedItemsToMove -> items that are selected by the user
			//toArr -> the source array from where items are selected, which undergoes deletion
			//fromArr -> the destination array to where items are going to be moved
			for (var i = 0; i < selectedItemsToMove.length; i++) {
				toArr.splice(0, 0, selectedItemsToMove[i]);
			}
			UtilsService.removeItemInArray(fromArr, toArr);
		}

		function moveAllItems(arr1, arr2) {
			var returnArr = arr2;
			//2nd argument array is the one that accepts; 1st argument array donates.
			for (var i = 0; i < arr1.length; i++) {
				returnArr.splice(0, 0, arr1[i]);
			}
			return returnArr;
		};

	};

})();
