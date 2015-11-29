'use strict';

amdApp.controller('DeleteCanvasModalController', ['$scope', 'CanvasService', '$modalInstance', 'canvasObj', 
	function(scope, CanvasService, modalInstance, canvasObj) {
	scope.canvas = canvasObj.canvas;
	
	// cancel the operation
	scope.cancel = function() {
		modalInstance.close();
	};

	// delete the canvas
	scope.deleteCanvas = function() {
		CanvasService.deleteCanvas(scope.canvas.canvasId).then(function() {
			canvasObj.availableScreens['private'].splice(canvasObj.index, 1);
			modalInstance.close();
		});
	};

}]);