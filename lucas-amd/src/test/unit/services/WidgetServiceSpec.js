'use strict';

describe('Widget Service related Tests', function() {

	beforeEach(module('amdApp'));

	var localWidgetService;
	var deferred = null;
	var favList2 = [{
		"canvasName" : "Hazmat Canvas",
		"canvasId" : 5
	}];
	var favList = [{
		"canvasName" : "Perishable Goods Canvas",
		"widgetInstanceList" : [{
			"data" : null,
			"widgetDefinition" : {
				"id" : 1,
				"name" : "user-productivity-widget",
				"shortName" : "pick",
				"type" : "GRID",
				"widgetActionConfig" : {
					"authenticated-user" : true,
					"create-assignment" : true,
					"view-report-productivity" : false,
					"configure-location" : false,
					"delete-canvas" : false,
					"create-canvas" : false
				},
				"broadCastMap" : {
					"Area" : "Area",
					"Score" : "Score",
					"Picker" : "Picker"
				},
				"listensForList" : ["Score", "Shift", "Picker"],
				"subtype" : "GRID"
			},
			"actualViewConfig" : {
				"height" : 240,
				"width" : 567,
				"anchor" : [275, 295],
				"zindex" : 1,
				"orientation" : {
					"option" : null,
					"selected" : null
				}
			},
			"id" : 1
		}, {
			"data" : null,
			"widgetDefinition" : {
				"id" : 2,
				"name" : "assignment-management-widget",
				"shortName" : "pick",
				"type" : "GRAPH_2D",
				"widgetActionConfig" : {
					"authenticated-user" : true,
					"create-assignment" : true,
					"view-report-productivity" : false,
					"configure-location" : false,
					"delete-canvas" : false,
					"create-canvas" : false
				},
				"broadCastMap" : null,
				"listensForList" : ["Score", "Shift", "Picker"],
				"subtype" : "CHART_BAR"
			},
			"actualViewConfig" : {
				"height" : 440,
				"width" : 567,
				"anchor" : [30, 30],
				"zindex" : 1,
				"orientation" : {
					"option" : [{
						"legend" : "horizontal",
						"value" : "h"
					}, {
						"legend" : "vertical",
						"value" : "v"
					}],
					"selected" : "h"
				}
			},
			"id" : 2
		}],
		"canvasId" : 1
	}, {
		"canvasName" : "Hazmat Canvas",
		"widgetInstanceList" : [{
			"data" : null,
			"widgetDefinition" : {
				"id" : 4,
				"name" : "equipment-widget",
				"shortName" : "Equipment",
				"type" : "GRAPH_2D",
				"widgetActionConfig" : {
					"authenticated-user" : true,
					"create-assignment" : true,
					"view-report-productivity" : false,
					"configure-location" : false,
					"delete-canvas" : false,
					"create-canvas" : false
				},
				"broadCastMap" : {
					"Area" : "series.key",
					"Route" : "point.label"
				},
				"listensForList" : ["Area", "Shift", "Picker"],
				"subtype" : "CHART_BAR"
			},
			"actualViewConfig" : {
				"height" : 290,
				"width" : 685,
				"anchor" : [0, 36],
				"zindex" : 1,
				"orientation" : {
					"option" : [{
						"legend" : "horizontal",
						"value" : "h"
					}, {
						"legend" : "vertical",
						"value" : "v"
					}],
					"selected" : "v"
				}
			},
			"id" : 4,
			"updateWidget" : true
		}, {
			"data" : null,
			"clientId" : 32,
			"widgetDefinition" : {
				"id" : 8,
				"name" : "user-create-or-edit-widget",
				"shortName" : "user",
				"type" : "FORM",
				"widgetActionConfig" : {
					"authenticated-user" : false,
					"create-assignment" : false,
					"view-report-productivity" : false,
					"configure-location" : false,
					"delete-canvas" : false,
					"create-canvas" : false
				},
				"broadCastMap" : {
					"Area" : "series.key",
					"Route" : "point.label"
				},
				"listensForList" : ["Area", "Shift", "Picker"],
				"definitionData" : {
					"User" : [{
						"startDate" : "08-21-2014"
					}],
					"handheldScreenLanguageList" : ["ENGLISH", "FRENCH", "GERMAN"],
					"amdScreenLanguageList" : ["ENGLISH", "FRENCH", "GERMAN"],
					"jenniferToUserLanguageList" : ["ENGLISH", "FRENCH", "GERMAN"],
					"userToJenniferLanguageList" : ["ENGLISH", "FRENCH", "GERMAN"]
				},
				"actualViewConfig" : {
					"height" : 440,
					"width" : 567,
					"anchor" : [30, 30],
					"zindex" : 1
				}
			},
			"actualViewConfig" : {
				"height" : 440,
				"width" : 567,
				"anchor" : [30, 30],
				"zindex" : 1
			},
			"updateWidget" : true
		}],
		"canvasId" : 3
	}];
	var widgetInstanceObj = {
		"data" : null,
		"id" : 32,
		"widgetDefinition" : {
			"id" : 8,
			"name" : "user-create-or-edit-widget",
			"shortName" : "user",
			"type" : "FORM",
			"widgetActionConfig" : {
				"authenticated-user" : false,
				"create-assignment" : false,
				"view-report-productivity" : false,
				"configure-location" : false,
				"delete-canvas" : false,
				"create-canvas" : false
			},
			"dataURL" : {
				"url" : "/waves/picklines",
				"searchCriteria" : {
					"pageNumber" : "0",
					"pageSize" : "10",
					"searchMap" : {},
					"sortMap" : {}
				}
			},
			"broadCastMap" : {
				"Area" : "series.key",
				"Route" : "point.label"
			},
			"listensForList" : ["Area", "Shift", "Picker"],
			"definitionData" : {
				"User" : [{
					"startDate" : "08-21-2014"
				}],
				"handheldScreenLanguageList" : ["ENGLISH", "FRENCH", "GERMAN"],
				"amdScreenLanguageList" : ["ENGLISH", "FRENCH", "GERMAN"],
				"jenniferToUserLanguageList" : ["ENGLISH", "FRENCH", "GERMAN"],
				"userToJenniferLanguageList" : ["ENGLISH", "FRENCH", "GERMAN"]
			},
			"actualViewConfig" : {
				"height" : 440,
				"width" : 567,
				"anchor" : [30, 30],
				"zindex" : 1
			}
		},
		"actualViewConfig" : {
			"height" : 440,
			"width" : 567,
			"anchor" : [30, 30],
			"zindex" : 1
		},
		"updateWidget" : true
	};

	var mockWidgetData = {
		"status" : "success",
		"code" : "200",
		"message" : "Request processed successfully",
		"level" : null,
		"uniqueKey" : null,
		"token" : null,
		"explicxitDismissal" : null,
		"data" : {
			"chart" : {
				"Completed" : [{
					"value" : "26",
					"label" : "Wave1"
				}, {
					"value" : "12",
					"label" : "Wave2"
				}, {
					"value" : "24",
					"label" : "Wave3"
				}, {
					"value" : "13",
					"label" : "Wave4"
				}],
				"Total" : [{
					"value" : "26",
					"label" : "Wave1"
				}, {
					"value" : "24",
					"label" : "Wave2"
				}, {
					"value" : "25",
					"label" : "Wave3"
				}, {
					"value" : "25",
					"label" : "Wave4"
				}]
			}
		}
	};

	var localRestangular;
	var httpBackend;
	var localRestApiService;
	var localScope;
	var rootScope;
	var localModalInstance;

	var injectDependencies = inject(function(WidgetService, Restangular, $httpBackend, RestApiService, $rootScope, $modal) {
		localWidgetService = WidgetService;
		localRestangular = Restangular;
		localRestApiService = RestApiService;
		httpBackend = $httpBackend;
		httpBackend.when('GET').respond(mockWidgetData);
		httpBackend.when('POST').respond(mockWidgetData);
		rootScope = $rootScope;
		localModalInstance = $modal;
	});

	describe('dependancy injection tests', function() {

		beforeEach(function() {
			injectDependencies();
		});

		it('should inject dependencies', function() {
			expect(localWidgetService).toBeDefined();
		});

	});

	describe('function executions: ', function() {
		beforeEach(function() {
			injectDependencies();
		});

		// Unit: Assert WidgetService.getWidgetData should return the data based on dataURL after making server call.
		it('getWidgetData should return the appropriate data', function() {
			spyOn(localWidgetService, 'getWidgetData').andCallThrough();
			spyOn(localRestApiService, 'post').andCallThrough();
			localWidgetService.getWidgetData(widgetInstanceObj).then(function(response) {
				widgetData = response;
			});
			httpBackend.flush();
			localScope = rootScope.$new();
			localScope.$digest();

			var widgetData = {
				"chart" : {
					"Completed" : [{
						"value" : "26",
						"label" : "Wave1"
					}, {
						"value" : "12",
						"label" : "Wave2"
					}, {
						"value" : "24",
						"label" : "Wave3"
					}, {
						"value" : "13",
						"label" : "Wave4"
					}],
					"Total" : [{
						"value" : "26",
						"label" : "Wave1"
					}, {
						"value" : "24",
						"label" : "Wave2"
					}, {
						"value" : "25",
						"label" : "Wave3"
					}, {
						"value" : "25",
						"label" : "Wave4"
					}]
				}
			};
			expect(widgetData).toEqual(mockWidgetData.data);
			//expect(widgetData.data).toEqual(mockWidgetData.data);
			expect(localRestApiService.post).toHaveBeenCalled();
		});

		it('putWidgetInstance - should add a widgetInstance in a existing canvas', inject(function($rootScope, LocalStoreService) {
			LocalStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', favList);
			LocalStoreService.addLSItem(null, 'ActiveCanvasId', 1);
			var fnResult = localWidgetService.putWidgetInstance(widgetInstanceObj);
			var favoriteCanvasList = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');
			expect(favoriteCanvasList).not.toEqual(favList);
		}));
		it('putWidgetInstance - should add a widgetInstance in a new canvas', inject(function($rootScope, LocalStoreService) {
			LocalStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', favList2);
			LocalStoreService.addLSItem(null, 'ActiveCanvasId', 5);
			var fnResult = localWidgetService.putWidgetInstance(widgetInstanceObj);
			var favoriteCanvasList = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');
			expect(favoriteCanvasList).not.toEqual(favList2);
		}));
		//5

		it('deleteProperty- should delete a property in widget instance object', inject(function($rootScope, LocalStoreService) {
			LocalStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', favList);
			var fnResult = localWidgetService.deleteProperty("updateWidget", 32);
			var favoriteCanvasList = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');
			expect(favoriteCanvasList).not.toEqual(favList);
		}));

		it('getReactToArrayFromMap - should return a array from object keys', inject(function($rootScope) {
			var objMap = {
				"userName": {},
				"firstName": {}
			};
			var listensForList = ["userName"];
			var expectedArr = ["firstName"];
			var fnResult = localWidgetService.getReactToArrayFromMap(objMap, listensForList);
			expect(fnResult).toEqual(expectedArr);
		}));

		/*it('addProperty - should add a property in widget instance object', inject(function($rootScope, LocalStoreService) {
		 var favoriteCanvasListBefore = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');
		 var fnResult = localWidgetService.addProperty("updateWidget", 32);
		 var favoriteCanvasListAfter = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');
		 expect(favoriteCanvasListBefore).not.toEqual(favoriteCanvasListAfter);
		 }));*/

		it('constructWidgetInstanceObj - should construct a widget instance object', inject(function($rootScope, LocalStoreService) {
			var constructedObj = {
				widgetDefinition : {
					name : 'user-create-or-edit-widget',
					id : 8
				},
				id : 32,
				canvas : {
					canvasName : null,
					canvasId : 1
				}
			};
			LocalStoreService.addLSItem(null, 'ActiveCanvasId', 1);
			var fnResult = localWidgetService.constructWidgetInstanceObj(widgetInstanceObj);
			expect(fnResult).toEqual(constructedObj);
		}));

		it('showUpdatedCanvas - should call fn showUpdatedCanvas for canvas level true', inject(function(LocalStoreService) {
			var favCanvasListUpdated = [{
				"canvasName" : "Pick Monitoring Canvas",
				"widgetInstanceList" : [{
					"id" : null,
					"updateWidget" : true,
					"clientId" : 100,
					"isMaximized" : false
				}],
				"widgetInstanceListDeleted" : [{
					"id" : null,
					"updateWidget" : true,
					"clientId" : 102,
					"isMaximized" : false
				}],
				"canvasId" : 5
			}, {
				"canvasName" : "User Management Canvas",
				"widgetInstanceList" : [],
				"canvasId" : 4
			}];
			var canvasArr = [{
				"widgetInstanceList" : [{
					"id" : null,
					"updateWidget" : true,
					"clientId" : 100,
					"isMaximized" : false
				}],
				"widgetInstanceListDeleted" : [{
					"id" : null,
					"updateWidget" : true,
					"clientId" : 102,
					"isMaximized" : false
				}],
				"canvasId" : 5,
				"canvasName" : "Pick Monitoring Canvas"
			}];
			LocalStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', favCanvasListUpdated);
			LocalStoreService.addLSItem(null, 'ActiveCanvasId', 5);
			var fnResult = localWidgetService.showUpdatedCanvas("true");
			expect(fnResult).toEqual(canvasArr);
			LocalStoreService.removeLSItem('ActiveCanvasId');
			LocalStoreService.removeLSItem('FavoriteCanvasListUpdated');
		}));

		it('showUpdatedCanvas - should call fn showUpdatedCanvas for canvas level false', inject(function(LocalStoreService) {
			var favCanvasListUpdated = [{
				"canvasName" : "Pick Monitoring Canvas",
				"widgetInstanceList" : [{
					"id" : null,
					"updateWidget" : true,
					"clientId" : 100,
					"isMaximized" : false
				}],
				"canvasId" : 5
			}, {
				"canvasName" : "User Management Canvas",
				"widgetInstanceList" : [{
					"id" : null,
					"updateWidget" : true,
					"clientId" : 101,
					"isMaximized" : false
				}],
				"canvasId" : 4
			}];
			var canvasArr = [{
				"canvasName" : "Pick Monitoring Canvas",
				"widgetInstanceList" : [{
					"id" : null,
					"updateWidget" : true,
					"clientId" : 100,
					"isMaximized" : false
				}],
				"canvasId" : 5,
				"widgetInstanceListDeleted" : []
			}, {
				"canvasName" : "User Management Canvas",
				"widgetInstanceList" : [{
					"id" : null,
					"updateWidget" : true,
					"clientId" : 101,
					"isMaximized" : false
				}],
				"canvasId" : 4,
				"widgetInstanceListDeleted" : []
			}];
			LocalStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', favCanvasListUpdated);
			var fnResult = localWidgetService.showUpdatedCanvas("false");
			expect(fnResult).toEqual(canvasArr);
			LocalStoreService.removeLSItem('ActiveCanvasId');
			LocalStoreService.removeLSItem('FavoriteCanvasListUpdated');
		}));

		it('showUpdatedCanvas - should call fn showUpdatedCanvas for only updateWidget set to true', inject(function(LocalStoreService) {
			var favCanvasListUpdated = [{
				"canvasName" : "Pick Monitoring Canvas",
				"widgetInstanceList" : [{
					"id" : null,
					"updateWidget" : false,
					"clientId" : 100,
					"isMaximized" : false
				}],
				"canvasId" : 5
			}, {
				"canvasName" : "User Management Canvas",
				"widgetInstanceList" : [{
					"id" : null,
					"updateWidget" : false,
					"clientId" : 101,
					"isMaximized" : false
				}],
				"canvasId" : 4
			}];
			var canvasArr = [];
			LocalStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', favCanvasListUpdated);
			var fnResult = localWidgetService.showUpdatedCanvas("false");
			expect(fnResult).toEqual(canvasArr);
			LocalStoreService.removeLSItem('FavoriteCanvasListUpdated');
		}));

		it('getWidgetInstanceObj - should call fn getWidgetInstanceObj to get widgetinstance of activewidgetId', inject(function(LocalStoreService) {
			var favCanvasListUpdated = [{
				"canvasName" : "Pick Monitoring Canvas",
				"widgetInstanceList" : [{
					"id" : null,
					"updateWidget" : false,
					"clientId" : 100,
					"isMaximized" : false
				}],
				"canvasId" : 5
			}];
			LocalStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', favCanvasListUpdated);
			LocalStoreService.addLSItem(null, 'ActiveCanvasId', 5);
			var fnResult = localWidgetService.getWidgetInstanceObj(100);
			expect(fnResult).toEqual(favCanvasListUpdated[0].widgetInstanceList[0]);
			LocalStoreService.removeLSItem('FavoriteCanvasListUpdated');
			LocalStoreService.removeLSItem('ActiveCanvasId');
		}));

		it('deleteWidgetInstance - should call fn deleteWidgetInstance to delete a widgetInstance from a canvas', inject(function(LocalStoreService) {
			var favCanvasListUpdated = [{
				"canvasName" : "Pick Monitoring Canvas",
				"widgetInstanceList" : [{
					"id" : null,
					"updateWidget" : false,
					"clientId" : 100,
					"isMaximized" : false
				}],
				"canvasId" : 5
			}];
			var expectedFavCanvasListAfterDeletion =  [{
			    canvasName: 'Pick Monitoring Canvas',
			    widgetInstanceList: [{
			        id: null,
			        updateWidget: false,
			        clientId: 100,
			        isMaximized: false
				}],
			    canvasId: 5
			}];
			LocalStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', favCanvasListUpdated);
			LocalStoreService.addLSItem(null, 'ActiveCanvasId', 5);
			var fnResult = localWidgetService.deleteWidgetInstance(favCanvasListUpdated[0].widgetInstanceList[0]);
			var newFavCanvasList = LocalStoreService.getLSItem("FavoriteCanvasListUpdated");
			fnResult.then(function(response) {
				expect(response).toEqual(favCanvasListUpdated[0].widgetInstanceList[0]);
			});
			expect(newFavCanvasList).toEqual(expectedFavCanvasListAfterDeletion);
			LocalStoreService.removeLSItem('FavoriteCanvasListUpdated');
			LocalStoreService.removeLSItem('ActiveCanvasId');
		}));

		it('updateFavoriteCanvasListLocalStorage - should call fn updateFavoriteCanvasListLocalStorage to set a widgetInstance in favoritecanvaslist', inject(function(LocalStoreService) {
			var favCanvasListUpdated = [{
				"canvasName" : "Pick Monitoring Canvas",
				"widgetInstanceList" : [{
					"id" : null,
					"updateWidget" : false,
					"clientId" : 100,
					"isMaximized" : false
				}],
				"canvasId" : 5
			}];
			var widgetInstance = {
				"id" : null,
				"updateWidget" : false,
				"clientId" : 100,
				"isMaximized" : false,
				"data" : {}
			};
			LocalStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', favCanvasListUpdated);
			LocalStoreService.addLSItem(null, 'ActiveCanvasId', 5);
			localWidgetService.updateFavoriteCanvasListLocalStorage(widgetInstance);
			var newFavCanvasList = LocalStoreService.getLSItem("FavoriteCanvasListUpdated");
			expect(newFavCanvasList[0].widgetInstanceList[0]).toEqual(widgetInstance);
			LocalStoreService.removeLSItem('FavoriteCanvasListUpdated');
			LocalStoreService.removeLSItem('ActiveCanvasId');
		}));

		it('updateWidgetInstance - should call fn updateWidgetInstance to update a widgetInstance through cog menu', inject(function(LocalStoreService) {
			var widgetInstance = {
				"id" : null,
				"updateWidget" : false,
				"clientId" : 100,
				"isMaximized" : false,
				"data" : {}
			};
			var favCanvasListUpdated = [{
				"canvasName" : "Pick Monitoring Canvas",
				"widgetInstanceList" : [{
					"id" : null,
					"updateWidget" : false,
					"clientId" : 100,
					"isMaximized" : false
				}],
				"canvasId" : 5
			}];
			LocalStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', favCanvasListUpdated);
			LocalStoreService.addLSItem(null, 'ActiveCanvasId', 5);
			spyOn(localWidgetService, "updateFavoriteCanvasListLocalStorage").andCallThrough();
			spyOn(rootScope, "$broadcast").andCallThrough();
			localWidgetService.updateWidgetInstance(widgetInstance);
			expect(localWidgetService.updateFavoriteCanvasListLocalStorage).toHaveBeenCalledWith(widgetInstance);
			expect(rootScope.$broadcast).toHaveBeenCalledWith('updateExistingWidget', widgetInstance);
			LocalStoreService.removeLSItem('FavoriteCanvasListUpdated');
			LocalStoreService.removeLSItem('ActiveCanvasId');
		}));
	});

	describe('load modal popup tests', function() {

		beforeEach(inject(function($modal) {
			injectDependencies();
			localModalInstance = $modal;
			spyOn(localModalInstance, 'open').andCallThrough();
		}));

		it('should loadCogMenuPopup', function() {
			localWidgetService.loadCogMenuPopup(widgetInstanceObj);
			expect(localModalInstance.open).toHaveBeenCalled();
		});
		it('should loadDeleteWidgetPopup', function() {
			localWidgetService.loadDeleteWidgetPopup(widgetInstanceObj);
			expect(localModalInstance.open).toHaveBeenCalled();
		});
		it('should loadResetWidgetPopup', function() {
			localWidgetService.loadResetWidgetPopup(widgetInstanceObj);
			expect(localModalInstance.open).toHaveBeenCalled();
		});
	});

	it('Should handle updateFavoriteCanvasListLocalStorageWithWidgetInstanceList method', inject(function($rootScope, LocalStoreService) {
	    var widgetInstanceList = [{
	        "data": [],
	        "id": null,
	        "widgetDefinition": {
	            "name": "create-or-edit-user-form-widget",
	            "shortName": "CreateUser",
	            "widgetActionConfig": {
	                "widget-access": {
	                    "create-edit-user-widget-access": false
	                },
	                "widget-actions": {
	                    "edit-user": true,
	                    "create-user": true
	                }
	            },
	            "definitionData": {
	                "User": [null]
	            },
	            "reactToMap": {
	                "lastName": {
	                    "url": "/users",
	                    "searchCriteria": {
	                        "pageSize": "10",
	                        "pageNumber": "0",
	                        "searchMap": {
	                            "lastName": ["jack123", "jill123", "admin123"]
	                        }
	                    }
	                },
	                "Wave": {
	                    "url": "/users",
	                    "searchCriteria": {
	                        "pageSize": "10",
	                        "pageNumber": "0",
	                        "searchMap": {
	                            "Wave": ["Wave 1", "Wave 2", "Wave 3"]
	                        }
	                    }
	                }
	            },
	            "broadcastList": ["userName"],
	            "defaultViewConfig": {
	                "dateFormat": {
	                    "selectedFormat": "mm-dd-yyyy",
	                    "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
	                },
	                "isMaximized": false,
	                "listensForList": ["userName", "Wave", "Score"],
	                "width": 567,
	                "height": 240,
	                "anchor": [275, 295],
	                "zindex": 0,
	                "reactToList": [],
	                "minWidth": 300,
	                "minHeight": 480,
	                "originalMinWidth": 300,
	                "originalMinHeight": 480
	            },
	            "broadcastMap": ["userName", "firstName"]
	        },
	        "actualViewConfig": {
	            "dateFormat": {
	                "selectedFormat": "mm-dd-yyyy",
	                "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
	            },
	            "isMaximized": false,
	            "listensForList": ["userName", "Wave", "Score"],
	            "width": 567,
	            "height": 240,
	            "anchor": [275, 295],
	            "zindex": 0,
	            "reactToList": [],
	            "minWidth": 300,
	            "minHeight": 480,
	            "originalMinWidth": 300,
	            "originalMinHeight": 480
	        },
	        "updateWidget": true,
	        "clientId": 101,
	        "isMaximized": false,
	        "strAvailableItems": "userGroup.availableGroups",
	        "strExistingItems": "userGroup.selectedGroups",
	        "availableItems": ["administrator", "basic-authenticated-user", "picker", "supervisor", "system", "warehouse-manager"],
	        "selectedAvailableItems": [],
	        "existingItems": [],
	        "selectedExistingItems": []
	    }, {
	        "data": [],
	        "id": null,
	        "widgetDefinition": {
	            "name": "create-or-edit-user-form-widget",
	            "shortName": "CreateUser",
	            "widgetActionConfig": {
	                "widget-access": {
	                    "create-edit-user-widget-access": false
	                },
	                "widget-actions": {
	                    "edit-user": false,
	                    "create-user": false
	                }
	            },
	            "definitionData": {
	                "User": [null]
	            },
	            "reactToMap": {
	                "lastName": {
	                    "url": "/users",
	                    "searchCriteria": {
	                        "pageSize": "10",
	                        "pageNumber": "0",
	                        "searchMap": {
	                            "lastName": ["jack123", "jill123", "admin123"]
	                        }
	                    }
	                },
	                "Wave": {
	                    "url": "/users",
	                    "searchCriteria": {
	                        "pageSize": "10",
	                        "pageNumber": "0",
	                        "searchMap": {
	                            "Wave": ["Wave 1", "Wave 2", "Wave 3"]
	                        }
	                    }
	                }
	            },
	            "broadcastList": ["userName"],
	            "defaultViewConfig": {
	                "dateFormat": {
	                    "selectedFormat": "mm-dd-yyyy",
	                    "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
	                },
	                "isMaximized": false,
	                "listensForList": ["userName", "Wave", "Score"],
	                "width": 567,
	                "height": 240,
	                "anchor": [275, 295],
	                "zindex": 0,
	                "reactToList": [],
	                "minWidth": 300,
	                "minHeight": 480,
	                "originalMinWidth": 300,
	                "originalMinHeight": 480
	            },
	            "broadcastMap": ["userName", "firstName"]
	        },
	        "actualViewConfig": {
	            "dateFormat": {
	                "selectedFormat": "mm-dd-yyyy",
	                "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
	            },
	            "isMaximized": false,
	            "listensForList": ["userName", "Wave", "Score"],
	            "width": 567,
	            "height": 240,
	            "anchor": [275, 295],
	            "zindex": 0,
	            "reactToList": [],
	            "minWidth": 300,
	            "minHeight": 480,
	            "originalMinWidth": 300,
	            "originalMinHeight": 480
	        },
	        "updateWidget": true,
	        "clientId": 100,
	        "isMaximized": false
	    }];
	    LocalStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', [{
	        "name": "Hazmat",
	        "canvasId": "201",
	        "canvasType": "saved",
	        "displayOrder": "1"
	    }, {
	        "name": "Work Execution",
	        "canvasId": "221",
	        "canvasType": "company",
	        "displayOrder": "2",
	        "widgetInstanceList": [{
	            "data": [],
	            "id": null,
	            "widgetDefinition": {
	                "name": "create-or-edit-user-form-widget",
	                "shortName": "CreateUser",
	                "widgetActionConfig": {
	                    "widget-access": {
	                        "create-edit-user-widget-access": false
	                    },
	                    "widget-actions": {
	                        "edit-user": false,
	                        "create-user": false
	                    }
	                },
	                "definitionData": {
	                    "User": [null]
	                },
	                "reactToMap": {
	                    "lastName": {
	                        "url": "/users",
	                        "searchCriteria": {
	                            "pageSize": "10",
	                            "pageNumber": "0",
	                            "searchMap": {
	                                "lastName": ["jack123", "jill123", "admin123"]
	                            }
	                        }
	                    },
	                    "Wave": {
	                        "url": "/users",
	                        "searchCriteria": {
	                            "pageSize": "10",
	                            "pageNumber": "0",
	                            "searchMap": {
	                                "Wave": ["Wave 1", "Wave 2", "Wave 3"]
	                            }
	                        }
	                    }
	                },
	                "broadcastList": ["userName"],
	                "defaultViewConfig": {
	                    "dateFormat": {
	                        "selectedFormat": "mm-dd-yyyy",
	                        "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
	                    },
	                    "isMaximized": false,
	                    "listensForList": ["userName", "Wave", "Score"],
	                    "width": 567,
	                    "height": 240,
	                    "anchor": [275, 295],
	                    "zindex": 0,
	                    "reactToList": [],
	                    "minWidth": 300,
	                    "minHeight": 480,
	                    "originalMinWidth": 300,
	                    "originalMinHeight": 480
	                },
	                "broadcastMap": ["userName", "firstName"]
	            },
	            "actualViewConfig": {
	                "dateFormat": {
	                    "selectedFormat": "mm-dd-yyyy",
	                    "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
	                },
	                "isMaximized": false,
	                "listensForList": ["userName", "Wave", "Score"],
	                "width": 567,
	                "height": 240,
	                "anchor": [275, 295],
	                "zindex": 0,
	                "reactToList": [],
	                "minWidth": 300,
	                "minHeight": 480,
	                "originalMinWidth": 300,
	                "originalMinHeight": 480
	            },
	            "updateWidget": true,
	            "clientId": 100,
	            "isMaximized": false
	        }, {
	            "data": [],
	            "id": null,
	            "widgetDefinition": {
	                "name": "create-or-edit-user-form-widget",
	                "shortName": "CreateUser",
	                "widgetActionConfig": {
	                    "widget-access": {
	                        "create-edit-user-widget-access": false
	                    },
	                    "widget-actions": {
	                        "edit-user": false,
	                        "create-user": false
	                    }
	                },
	                "definitionData": {
	                    "User": [null]
	                },
	                "reactToMap": {
	                    "lastName": {
	                        "url": "/users",
	                        "searchCriteria": {
	                            "pageSize": "10",
	                            "pageNumber": "0",
	                            "searchMap": {
	                                "lastName": ["jack123", "jill123", "admin123"]
	                            }
	                        }
	                    },
	                    "Wave": {
	                        "url": "/users",
	                        "searchCriteria": {
	                            "pageSize": "10",
	                            "pageNumber": "0",
	                            "searchMap": {
	                                "Wave": ["Wave 1", "Wave 2", "Wave 3"]
	                            }
	                        }
	                    }
	                },
	                "broadcastList": ["userName"],
	                "defaultViewConfig": {
	                    "dateFormat": {
	                        "selectedFormat": "mm-dd-yyyy",
	                        "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
	                    },
	                    "isMaximized": false,
	                    "listensForList": ["userName", "Wave", "Score"],
	                    "width": 567,
	                    "height": 240,
	                    "anchor": [275, 295],
	                    "zindex": 0,
	                    "reactToList": [],
	                    "minWidth": 300,
	                    "minHeight": 480,
	                    "originalMinWidth": 300,
	                    "originalMinHeight": 480
	                },
	                "broadcastMap": ["userName", "firstName"]
	            },
	            "actualViewConfig": {
	                "dateFormat": {
	                    "selectedFormat": "mm-dd-yyyy",
	                    "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
	                },
	                "isMaximized": false,
	                "listensForList": ["userName", "Wave", "Score"],
	                "width": 567,
	                "height": 240,
	                "anchor": [275, 295],
	                "zindex": 0,
	                "reactToList": [],
	                "minWidth": 300,
	                "minHeight": 480,
	                "originalMinWidth": 300,
	                "originalMinHeight": 480
	            },
	            "updateWidget": true,
	            "clientId": 101,
	            "isMaximized": false
	        }]
	    }, {
	        "name": "abc",
	        "canvasId": "231",
	        "canvasType": "saved",
	        "displayOrder": "3"
	    }, {
	        "name": "xyz",
	        "canvasId": "235",
	        "canvasType": "company",
	        "displayOrder": "4"
	    }]);
	    LocalStoreService.addLSItem(null, 'ActiveCanvasId', "221");
	    localWidgetService.updateFavoriteCanvasListLocalStorageWithWidgetInstanceList(widgetInstanceList);
	    expect(LocalStoreService.getLSItem("FavoriteCanvasListUpdated")).toEqual(
	        [{
	            "name": "Hazmat",
	            "canvasId": "201",
	            "canvasType": "saved",
	            "displayOrder": "1"
	        }, {
	            "name": "Work Execution",
	            "canvasId": "221",
	            "canvasType": "company",
	            "displayOrder": "2",
	            "widgetInstanceList": [{
	                "data": [],
	                "id": null,
	                "widgetDefinition": {
	                    "name": "create-or-edit-user-form-widget",
	                    "shortName": "CreateUser",
	                    "widgetActionConfig": {
	                        "widget-access": {
	                            "create-edit-user-widget-access": false
	                        },
	                        "widget-actions": {
	                            "edit-user": true,
	                            "create-user": true
	                        }
	                    },
	                    "definitionData": {
	                        "User": [null]
	                    },
	                    "reactToMap": {
	                        "lastName": {
	                            "url": "/users",
	                            "searchCriteria": {
	                                "pageSize": "10",
	                                "pageNumber": "0",
	                                "searchMap": {
	                                    "lastName": ["jack123", "jill123", "admin123"]
	                                }
	                            }
	                        },
	                        "Wave": {
	                            "url": "/users",
	                            "searchCriteria": {
	                                "pageSize": "10",
	                                "pageNumber": "0",
	                                "searchMap": {
	                                    "Wave": ["Wave 1", "Wave 2", "Wave 3"]
	                                }
	                            }
	                        }
	                    },
	                    "broadcastList": ["userName"],
	                    "defaultViewConfig": {
	                        "dateFormat": {
	                            "selectedFormat": "mm-dd-yyyy",
	                            "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
	                        },
	                        "isMaximized": false,
	                        "listensForList": ["userName", "Wave", "Score"],
	                        "width": 567,
	                        "height": 240,
	                        "anchor": [275, 295],
	                        "zindex": 0,
	                        "reactToList": [],
	                        "minWidth": 300,
	                        "minHeight": 480,
	                        "originalMinWidth": 300,
	                        "originalMinHeight": 480
	                    },
	                    "broadcastMap": ["userName", "firstName"]
	                },
	                "actualViewConfig": {
	                    "dateFormat": {
	                        "selectedFormat": "mm-dd-yyyy",
	                        "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
	                    },
	                    "isMaximized": false,
	                    "listensForList": ["userName", "Wave", "Score"],
	                    "width": 567,
	                    "height": 240,
	                    "anchor": [275, 295],
	                    "zindex": 0,
	                    "reactToList": [],
	                    "minWidth": 300,
	                    "minHeight": 480,
	                    "originalMinWidth": 300,
	                    "originalMinHeight": 480
	                },
	                "updateWidget": true,
	                "clientId": 101,
	                "isMaximized": false,
	                "strAvailableItems": "userGroup.availableGroups",
	                "strExistingItems": "userGroup.selectedGroups",
	                "availableItems": ["administrator", "basic-authenticated-user", "picker", "supervisor", "system", "warehouse-manager"],
	                "selectedAvailableItems": [],
	                "existingItems": [],
	                "selectedExistingItems": []
	            }, {
	                "data": [],
	                "id": null,
	                "widgetDefinition": {
	                    "name": "create-or-edit-user-form-widget",
	                    "shortName": "CreateUser",
	                    "widgetActionConfig": {
	                        "widget-access": {
	                            "create-edit-user-widget-access": false
	                        },
	                        "widget-actions": {
	                            "edit-user": false,
	                            "create-user": false
	                        }
	                    },
	                    "definitionData": {
	                        "User": [null]
	                    },
	                    "reactToMap": {
	                        "lastName": {
	                            "url": "/users",
	                            "searchCriteria": {
	                                "pageSize": "10",
	                                "pageNumber": "0",
	                                "searchMap": {
	                                    "lastName": ["jack123", "jill123", "admin123"]
	                                }
	                            }
	                        },
	                        "Wave": {
	                            "url": "/users",
	                            "searchCriteria": {
	                                "pageSize": "10",
	                                "pageNumber": "0",
	                                "searchMap": {
	                                    "Wave": ["Wave 1", "Wave 2", "Wave 3"]
	                                }
	                            }
	                        }
	                    },
	                    "broadcastList": ["userName"],
	                    "defaultViewConfig": {
	                        "dateFormat": {
	                            "selectedFormat": "mm-dd-yyyy",
	                            "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
	                        },
	                        "isMaximized": false,
	                        "listensForList": ["userName", "Wave", "Score"],
	                        "width": 567,
	                        "height": 240,
	                        "anchor": [275, 295],
	                        "zindex": 0,
	                        "reactToList": [],
	                        "minWidth": 300,
	                        "minHeight": 480,
	                        "originalMinWidth": 300,
	                        "originalMinHeight": 480
	                    },
	                    "broadcastMap": ["userName", "firstName"]
	                },
	                "actualViewConfig": {
	                    "dateFormat": {
	                        "selectedFormat": "mm-dd-yyyy",
	                        "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
	                    },
	                    "isMaximized": false,
	                    "listensForList": ["userName", "Wave", "Score"],
	                    "width": 567,
	                    "height": 240,
	                    "anchor": [275, 295],
	                    "zindex": 0,
	                    "reactToList": [],
	                    "minWidth": 300,
	                    "minHeight": 480,
	                    "originalMinWidth": 300,
	                    "originalMinHeight": 480
	                },
	                "updateWidget": true,
	                "clientId": 100,
	                "isMaximized": false
	            }]
	        }, {
	            "name": "abc",
	            "canvasId": "231",
	            "canvasType": "saved",
	            "displayOrder": "3"
	        }, {
	            "name": "xyz",
	            "canvasId": "235",
	            "canvasType": "company",
	            "displayOrder": "4"
	        }]
	    );
	}));
});
