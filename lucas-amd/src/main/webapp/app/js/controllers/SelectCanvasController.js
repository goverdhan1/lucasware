'use strict';

amdApp.controller('SelectCanvasController', ['$log', '$scope', '$state', '$modal', '$modalInstance', 'SelectCanvasService', 'HomeCanvasListService', 'LocalStoreService', 'UserService', 'SecurityService', 'UtilsService', 'CanvasService', 
	function(log, scope, state, modal, modalInstance, SelectCanvasService, HomeCanvasListService, LocalStoreService, UserService, SecurityService, UtilsService, CanvasService) {
		scope.availableScreens = [];
		scope.showInfo = false;
		scope.activeMessage = "";
	    scope.isCreateCanvas = SecurityService.hasPermission('create-canvas');

	    SelectCanvasService.getAllScreens().then(function(response) {
	        scope.availableScreens = response;
	    }, function(error) {
	        log.error("error: " + error);
	    });

		scope.selectScreen = function(canvasType, e) {
			var favoriteCanvasList = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');
			var pickedCanvas = HomeCanvasListService.findByCanvasId(favoriteCanvasList, e.canvasId);
			if (pickedCanvas === null) {// it is a fresh canvas
				var selectedCanvas = {
					"canvasType": canvasType ? canvasType.toUpperCase(): '',
					"canvasId" : parseInt(e.canvasId),
					"canvasName" : e.canvasName,
					"widgetInstanceList": []
				};

				var userConfig = LocalStoreService.getLSItem('UserConfig');
				userConfig.openCanvases.push(selectedCanvas);
				LocalStoreService.addLSItem(null, 'UserConfig', userConfig);
				
				scope.selected = selectedCanvas;

				// get widgetinstances for the selected canvas
				CanvasService.getCanvasData(e.canvasId).then(function(response) {
					console.log(response);

					selectedCanvas.isLoaded = true;

					// Generate the clientIds
	                for (var i = 0; i < response.widgetInstanceList.length; i++) {
	                    response.widgetInstanceList[i]["clientId"] = UtilsService.getClientWidgetInstanceId();
	                    if (response.widgetInstanceList[i].dataURL) {
	                        response.widgetInstanceList[i].widgetDefinition.dataURL = response.widgetInstanceList[i].dataURL;
	                    }
	                }

					selectedCanvas.widgetInstanceList = response.widgetInstanceList;
					favoriteCanvasList.push(selectedCanvas);
					LocalStoreService.addLSItem(scope, 'FavoriteCanvasListUpdated', favoriteCanvasList);

					CanvasService.saveOpenCanvases(favoriteCanvasList);
					CanvasService.saveActiveCanvas(scope.selected);

					state.go('canvases.detail', {
						canvasId : scope.selected.canvasId
					});
					modalInstance.close(scope.selected.canvasName + " is selected");
                });

				
			} else {// this canvas is already active
				scope.activeMessage = "This canvas is already active. Please select another.";
				scope.showInfo = true;
			}
		};

		scope.createNewCanvas = function() {
			modalInstance.close();
			log.info("Create New Canvas");
			var createCanvasModal = modal.open({
				templateUrl: 'views/modals/create-new-canvas.html',
				controller: 'CreateNewCanvasModalController',
                backdrop: 'static'
			});
		};

		scope.closeModal = function() {
			modalInstance.close('closed without selection');
			log.info('closed without selection');
		};

		scope.deleteCanvas = function(canvas, index) {
			log.info("deleting canvas: " + canvas.canvasId);
			scope.selectedCanvas = canvas;
			scope.selectedCanvasIndex = index;
			var deleteCanvasModal = modal.open({
				templateUrl: 'views/modals/delete-canvas.html',
				controller: 'DeleteCanvasModalController',
				resolve : canvasScopeData(),
				backdrop: 'static'
			});
		};

		var canvasScopeData = function() {
	        return {
	            canvasObj : function() {
	                return {
	                	canvas: scope.selectedCanvas,
	                	index: scope.selectedCanvasIndex,
	                	availableScreens: scope.availableScreens
	                }
	            }
	        };
	    };

		scope.getCanvasPermissionClass = function (canvas) {
			var favoriteCanvasListUpdated = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');
			if (favoriteCanvasListUpdated && UtilsService.findIndexByProperty(favoriteCanvasListUpdated, 'canvasName', canvas.canvasName) != null) {
				return 'canvas-already-loaded';
			} else if (!canvas.canvasActionConfig['view-canvas']) {
				return 'canvas-permissions-revoked';
			};

		};
	}]);