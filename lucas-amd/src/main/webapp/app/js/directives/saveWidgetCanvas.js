//wrap all angular components (initially, the controllers) in an IIFE
//https://github.com/johnpapa/angularjs-styleguide#iife
(function() {'use strict';

	angular.module('amdApp').directive('saveWidgetCanvas', SaveWidgetCanvas);

	//inject angular variables
	SaveWidgetCanvas.$inject = ['$rootScope', 'WidgetService'];

	//based on declared angular variables, pass your specific args in that order
	function SaveWidgetCanvas(rootScope, WidgetService) {
		var linkFunction = function(scope, element, attrs) {
			var saveWidgetCanvasScope = scope;

			//get canvasList with widgets which are to be saved
			var canvasListToBeSaved = [];
			var canvasListToBeSaved = WidgetService.showUpdatedCanvas(attrs.canvaslevel);
			if (canvasListToBeSaved) {
				saveWidgetCanvasScope.canvasListToBeSaved = canvasListToBeSaved;
			}

			saveWidgetCanvasScope.SaveCanvas = saveCanvas;
			saveWidgetCanvasScope.SaveWidget = saveWidget;

		};

		function saveCanvas(canvas, $event) {
			$event.stopPropagation();
			var msg = {
				"status" : "success",
				"level" : "success",
				"code" : "Canvas is saved successfully. The canvas details : ",
				"explicitDismissal" : true,
				//"message" : angular.toJson(canvas, true)
				"message" : canvas.canvasName + canvas.canvasId
			};
			rootScope.$broadcast('showNotification', msg);
			console.log(canvas);
		};

		function saveWidget(widgetInstance, canvasName, canvasId) {
			console.log(widgetInstance, canvasName, canvasId);
			var instanceObj = {
				"canvasName" : canvasName,
				"canvasId" : canvasId,
				"widgetInstanceList" : [widgetInstance]
			};
			var msg = {
				"status" : "success",
				"level" : "success",
				"code" : "Widget is saved successfully. The widget details : ",
				"explicitDismissal" : true,
				//"message" : angular.toJson(instanceObj, true)
				"message" : canvasName + canvasId + widgetInstance.clientId
			};
			rootScope.$broadcast('showNotification', msg);
			console.log(instanceObj);
			/*var widgetInstanceObj = WidgetService.constructWidgetInstanceObj(widgetInstance);
			 WidgetService.widgetInstancePost(widgetInstanceObj).then(function(response) {
			 scope.widgetdetails.updateWidget = false;
			 scope.alerts = [{
			 type : 'success',
			 msg : widgetInstanceObj.widgetDefinition.name + ' is saved successfully.'
			 }];
			 WidgetService.deleteProperty('updateWidget', scope.widgetdetails.clientId);
			 }, function(error) {
			 log.error("error: " + error);
			 });*/
		};

		return {
			restrict : 'E',
			scope : {},
			link : linkFunction,
			templateUrl : 'views/save-widget-canvas.html'
		};

	};

})();
