'use strict';

amdApp.controller('BottomRightController', ['$log', '$scope', '$modal', 'LocalStoreService', 'HomeCanvasListService',
function(log, scope, modal, LocalStoreService, HomeCanvasListService) {
	scope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams) {
		var favoriteCanvasList = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');
		scope.activeCanvas = HomeCanvasListService.findByCanvasId(favoriteCanvasList, toParams.canvasId);
		scope.openSelectCanvas = function() {
			var modalInstance = modal.open({
				templateUrl : 'views/modals/select-canvas.html',
				controller : 'SelectCanvasController',
				windowClass : 'selectCanvasModal',
        		        backdrop: 'static' 
			});
			modalInstance.result.then(function(selectedItem) {
				scope.selected = selectedItem;
			}, function() {
				log.info("Modal dismissed at: " + new Date());
			});
		};
	});
}]);
