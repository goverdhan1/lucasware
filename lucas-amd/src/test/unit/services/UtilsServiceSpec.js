'use strict';

describe('Utilities Service related Tests', function () {

    beforeEach(module('amdApp'));

    var localUtilsService;
    var localHomeCanvasListService;
    var localStoreService;
    var localCanvasService;

    var deferred = null;
    var ngWindow;
    var $rootScope;

    beforeEach(module('amdApp', function ($translateProvider) {
        $translateProvider.translations('en', {
            "language-code": "EN"
        }, 'fr', {
            "language-code": "fr"
        }, 'de', {
            "language-code": "de"
        }).preferredLanguage('en');
        $translateProvider.useLoader('LocaleLoader');
    }));
   

    describe('dependancy injection tests', function () {

        beforeEach(inject(function (UtilsService, HomeCanvasListService, CanvasService, LocalStoreService, _$rootScope_, $window) {
            localUtilsService = UtilsService;
            localHomeCanvasListService = HomeCanvasListService;
            localCanvasService = CanvasService;
            localStoreService = LocalStoreService;

            $rootScope = _$rootScope_.$new();
            ngWindow = $window;
        }));

        it('should inject dependencies', function () {
            expect(localUtilsService).toBeDefined();
        });

    });

    describe('function executions: ', function () {
        
        it('findById - should return an object by existing canvasId', inject(function ($rootScope) {
            var arr = [{
                "id": "10",
                "name": "Pick Monitoring"
            }];
            var id = "10";
            var fnResult = localUtilsService.findById(arr, id);
            expect(fnResult).toEqual(arr[0]);
        }));
        
        it('addItemInArray  - should add an item in array', inject(function ($rootScope) {
            var arr1 = ["userName", "firstName"];
            var arr2 = ["firstName", "lastName"];
            var fnResult = localUtilsService.addItemInArray(arr1, arr2);
            expect(fnResult).toEqual(["userName", "firstName", "lastName"]);
        }));
        
        it('removeItemInArray  - should remove an item in array', inject(function ($rootScope) {
            var arr1 = ["userName", "firstName"];
            var arr2 = ["firstName", "lastName"];
            var fnResult = localUtilsService.removeItemInArray(arr1, arr2);
            expect(fnResult).toEqual(["userName"]);
        }));
        

        it('getArrayIndexOfId - should return the position of existing canvasId', inject(function ($rootScope) {
            var arr = [{
                "id": "10",
                "name": "Pick Monitoring"
            }];
            var id = "10";
            var fnResult = localUtilsService.getArrayIndexOfId(arr, id);
            expect(fnResult).toEqual(0);
        }));

        it('getObjectByMatchingId - should return an array by matching widgetId', inject(function ($rootScope) {
            var arr = [{
                "id": "10",
                "widgetName": "Pick Widget"
            }];
            var id = "10";
            var fnResult = localUtilsService.getObjectByMatchingId(arr, id);
            expect(fnResult).toEqual(arr[0]);
        }));

        it('putItemsInArray - should return an object with key as property and value as Array', inject(function ($rootScope) {
            var key = "Area";
            var value = "Freezer Area";
            var obj = {};
            var fnResult = localUtilsService.putItemsInArray(key, value, obj);
            expect(fnResult).toEqual({
                Area: ['Freezer Area']
            });
        }));

        it('constructPattern - should return a string as pattern of canvasId, widgetId, and Name', inject(function ($rootScope) {
            var canvasId = 1;
            var widgetId = 10;
            var keyName = "AreaDimensions";
            var fnResult = localUtilsService.constructPattern(canvasId, widgetId, keyName);
            expect(fnResult).toEqual('1|10|AreaDimensions');
        }));

        it('getObjectProperties - should return a list of object properties', inject(function ($rootScope) {
            var obj = {
                "1": {
                    "name": "Picker",
                    "order": "3",
                    "sortOrder": "1",
                    "values": ["Jack", "Jane", "Tom", "Dick", "Harry", "John", "Jill"]
                }
            };
            var fnResult = localUtilsService.getObjectProperties(obj);
            expect(fnResult).toEqual({
                1: {
                    name: 'Picker',
                    order: '3',
                    sortOrder: '1',
                    values: ['Jack', 'Jane', 'Tom', 'Dick', 'Harry', 'John', 'Jill']
                },
                length: 1
            });
        }));

        it('showListenItems - should return a map array with properties based on listensForList, by taking a listenObject, and listensForList array', inject(function ($rootScope) {
            var listenObj = {
                "Area": ["Dry"],
                "Picker": ["John"],
                "Score": ["4"]
            };
            var listenConfig = ["Area", "Route", "Score"];
            var fnResult = localUtilsService.showListenItems(listenObj, listenConfig);
            expect(fnResult).toEqual({
                Area: ['Dry'],
                Score: ['4']
            });
        }));

    });

    describe('broadcastMap for nvd3 Bar Chart', function () {
        var broadcastMap = {
            "Area": "series.key",
            "Route": "point.label"
        };
        var areaObj = {
            "point": {
                "label": "Route505",
                "series": 2,
                "value": 40
            },
            "series": {
                "key": "Cooler"
            }
        };
        var getAreaArr = [];
        var getItemsAsArr = {};

        it('should call processBroadcastMap fn to identify the pointers in emitted object of nvd3', function () {
            getAreaArr = localUtilsService.processBroadcastMap(areaObj, broadcastMap);
            expect(getAreaArr).toEqual([{
                Area: 'Cooler',
                Route: 'Route505'
            }, {
                Area: 'Cooler',
                Route: 'Route505'
            }]);
        });

        it('should call processSelectedItemsAsArray fn to process the selected area of chart as per amdApp expectations', function () {
            getItemsAsArr = localUtilsService.processSelectedItemsAsArray(getAreaArr, ["Area"]);
            expect(getItemsAsArr).toEqual({
                Area: ['Cooler']
            });
        });

    });

    describe('broadcastMap for nvd3 Pie Chart', function () {
        var broadcastMap = {
            "Picker": "point.key",
            "Score": "point.y"
        };
        var areaObj = {
            "point": {
                "key": "John",
                "y": 40
            }
        };
        var getAreaArr = [];
        var getItemsAsArr = {};

        it('should call processBroadcastMap fn to identify the pointers in emitted object of nvd3', function () {
            getAreaArr = localUtilsService.processBroadcastMap(areaObj, broadcastMap);
            expect(getAreaArr).toEqual([{
                Picker: 'John',
                Score: 40
            }, {
                Picker: 'John',
                Score: 40
            }]);
        });

        it('should call processSelectedItemsAsArray fn to process the selected area of chart as per amdApp expectations', function () {
            getItemsAsArr = localUtilsService.processSelectedItemsAsArray(getAreaArr, ["Picker", "Score"]);
            expect(getItemsAsArr).toEqual({
                Picker: ['John'],
                Score: [40]
            });
        });
        it('should call compareTwoObjects fn to compare two objects', function () {
            var obj1 = {
                key: 'vallue1'
            };
            var obj2 = {
                key: 'vallue2'
            };
            var objectsTrue = localUtilsService.compareTwoObjects(obj1, obj2);
            expect(objectsTrue).toBe(false);
        });
    });

    it('should handle findIndexByName method', function () {
        var existingItems = [{
            name: "create-user"
        }, {
            name: "edit-user"
        }, {
            name: "delete-user"

        }];
        expect(localUtilsService.findIndexByName(existingItems, "create-user")).toEqual(0);
        expect(localUtilsService.findIndexByName(existingItems, "delete-widget")).toEqual(-1);
    });

    it('should handle parseNames method', function () {
        var groupPermissions = {
            "admin": [
                {
                    "name": "create-user"
                },
                {
                    "name": "edit-user"
                },
                {
                    "name": "delete-user"
                }
            ],
            "picker": [
                {
                    "name": "create-user"
                },
                {
                    "name": "create-canvas"
                }
            ],
            "supervisor": [
                {
                    "name": "delete-widget"
                }
            ]
        };
        expect(localUtilsService.parseNames(groupPermissions)).toEqual([{
            name: "admin"
        }, {
            name: "picker"
        }, {
            name: "supervisor"
        }]);
    });

    it('should handle parseNamesWithoutKey method', function () {
        var groupPermissions = {
            "admin": [{
                "name": "create-user"
            }, {
                "name": "edit-user"
            }, {
                "name": "delete-user"
            }],
            "picker": [{
                "name": "create-user"
            }, {
                "name": "create-canvas"
            }],
            "supervisor": [{
                "name": "delete-widget"
            }]
        };
        expect(localUtilsService.parseNames(groupPermissions)).toEqual([{
            name: "admin"
        }, {
            name: "picker"
        }, {
            name: "supervisor"
        }]);
    });

    it('should handle removeProperty method', function () {
        var user = {
            "userName": "jack123",
            "userId": 100,
            "permissionSet": ["manage-canvas", "clone-canvas", "delete-user", "disable-user"],
            "homeCanvas": {},
            "activeCanvas": {"canvasId": 140, "canvasName": "Throughput", "widgetInstanceList": []},
            "seeHomeCanvasIndicator": true,
            "openCanvases": [{"canvasId": 140, "canvasName": "Throughput", "widgetInstanceList": []}],
            "menuPermissions": {"manage-canvas": "Manage Canvas", "clone-canvas": "Clone Canvas"}
        };
        localUtilsService.removeProperty(user, 'userName');
        expect(user.userName).toBeUndefined();
    });

    it('should handle removeProperties method', function () {
        var user = {
            "userName": "jack123",
            "userId": 100,
            "permissionSet": ["manage-canvas", "clone-canvas", "delete-user", "disable-user"],
            "homeCanvas": {},
            "activeCanvas": {"canvasId": 140, "canvasName": "Throughput", "widgetInstanceList": []},
            "seeHomeCanvasIndicator": true,
            "openCanvases": [{"canvasId": 140, "canvasName": "Throughput", "widgetInstanceList": []}],
            "menuPermissions": {"manage-canvas": "Manage Canvas", "clone-canvas": "Clone Canvas"}
        };
        localUtilsService.removeProperties(user, ['userName', 'userId']);
        expect(user.userName).toBeUndefined();
        expect(user.userId).toBeUndefined();
    });

    it('should handle findIndexByProperty method', function () {
        var openCanvases = [
            {
                "canvasId": 140, "canvasName": "Throughput", "widgetInstanceList": []
            },
            {
                "canvasId": 104, "canvasName": "Pick  Monitoring", "widgetInstanceList": []
            }
        ];
        expect(localUtilsService.findIndexByProperty(openCanvases, 'canvasId', 104)).toEqual(1);
    });

    it('should handle swapArrayItems method', function () {
        var arr = [{
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
        }];
        var swappedArr = [{
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
        expect(localUtilsService.swapArrayItems(0, 1, arr)).toEqual(swappedArr);
    });

    it('handle saveActiveCanvas() method for "private" canvas', function() {
        spyOn(localCanvasService, 'saveCanvas').andCallThrough();
        localStoreService.addLSItem(null, "UserName", "jack123");
        localStoreService.addLSItem(null, "ActiveCanvasId", "221");
        localStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', [{
            "canvasName": "ProductManagement",
            "canvasId": "201",
            "canvasType": "COMPANY",
            "displayOrder": "1"
        }, {
            "canvasName": "Work Execution",
            "canvasId": "221",
            "canvasType": "PRIVATE",
            "displayOrder": "2",
            "widgetInstanceList": [{
                "data": [],
                "id": 8,
                "widgetDefinition": {
                    "name": "create-or-edit-user-form-widget",
                    "shortName": "CreateUser",
                    "widgetActionConfig": {
                        "widget-access": {
                            "create-edit-user-widget-access": true
                        },
                        "widget-actions": {
                            "edit-user": true,
                            "create-user": true
                        }
                    },
                    "definitionData": {
                        "User": [null]
                    },
                    "broadcastList": ["userName"],
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
                    "defaultViewConfig": {
                        "deviceWidths": {
                            "320": "mobile",
                            "540": "tablet",
                            "800": "desktop",
                            "1200": "wideScreen"
                        },
                        "dateFormat": {
                            "selectedFormat": "mm-dd-yyyy",
                            "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                        },
                        "minimumWidth": 300,
                        "minimumHeight": 480,
                        "anchor": [275, 295],
                        "zindex": 0,
                        "isMaximized": false,
                        "listensForList": ["userName", "Wave", "Score"],
                        "reactToList": [],
                        "originalMinimumWidth": 300,
                        "originalMinimumHeight": 480
                    },
                    "broadcastMap": ["userName", "firstName"]
                },
                "actualViewConfig": {
                    "deviceWidths": {
                        "320": "mobile",
                        "540": "tablet",
                        "800": "desktop",
                        "1200": "wideScreen"
                    },
                    "dateFormat": {
                        "selectedFormat": "mm-dd-yyyy",
                        "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                    },
                    "minimumWidth": 300,
                    "minimumHeight": 480,
                    "anchor": [275, 295],
                    "zindex": 0,
                    "isMaximized": false,
                    "listensForList": ["userName", "Wave", "Score"],
                    "reactToList": [],
                    "originalMinimumWidth": 300,
                    "originalMinimumHeight": 480
                },
                "updateWidget": true,
                "clientId": 100,
                "isMaximized": false
            }]
        }, {
            "canvasName": "AssignmentManagement",
            "canvasId": "231",
            "canvasType": "COMPANY",
            "displayOrder": "3"
        }, {
            "canvasName": "GroupManagement",
            "canvasId": "235",
            "canvasType": "COMPANY",
            "displayOrder": "4"
        }]);

        // // new Date().toJSON(), 
        localUtilsService.saveActiveCanvas("2015-04-09T12:13:54.156Z");
        expect(localCanvasService.saveCanvas).toHaveBeenCalledWith({
            "createdByUserName": localStoreService.getLSItem("UserName"),
            "createdDateTime": null,
            "updatedByUserName": null,
            "updatedDateTime": "2015-04-09T12:13:54.156Z",
            "canvasId": "221",
            "canvasName": "Work Execution",
            "shortName": "Work Execution",
            "canvasType": "PRIVATE",
            "canvasLayout": null,
            "userSet": null,
            "widgetInstanceList": [{
                "id": 8,
                "widgetInteractionConfig": [],
                "actualViewConfig": {
                    "dateFormat": {
                        "selectedFormat": "mm-dd-yyyy",
                        "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                    },
                    "minimumWidth": 300,
                    "minimumHeight": 480,
                    "anchor": [275, 295],
                    "zindex": 0,
                    "isMaximized": false,
                    "listensForList": ["userName", "Wave", "Score"],
                    position: 1,
                    gridColumns: undefined
                },
                "widgetDefinition": {
                    "name": "create-or-edit-user-form-widget",
                    "id": undefined
                },
                "delete": null
            }]
        });
    });

    it('handle saveActiveCanvas() method for "company" canvas', function() {
        spyOn(localCanvasService, 'saveCanvas').andCallThrough();

        localStoreService.addLSItem(null, "ActiveCanvasId", "221");
        localStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', [{
            "canvasName": "ProductManagement",
            "canvasId": "221",
            "canvasType": "COMPANY",
            "displayOrder": "2",
            "widgetInstanceList": [{
                "data": [],
                "id": 8,
                "widgetDefinition": {
                    "name": "create-or-edit-user-form-widget",
                    "shortName": "CreateUser",
                    "widgetActionConfig": {
                        "widget-access": {
                            "create-edit-user-widget-access": true
                        },
                        "widget-actions": {
                            "edit-user": true,
                            "create-user": true
                        }
                    },
                    "definitionData": {
                        "User": [null]
                    },
                    "broadcastList": ["userName"],
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
                    "defaultViewConfig": {
                        "deviceWidths": {
                            "320": "mobile",
                            "540": "tablet",
                            "800": "desktop",
                            "1200": "wideScreen"
                        },
                        "dateFormat": {
                            "selectedFormat": "mm-dd-yyyy",
                            "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                        },
                        "minimumWidth": 300,
                        "minimumHeight": 480,
                        "anchor": [275, 295],
                        "zindex": 0,
                        "isMaximized": false,
                        "listensForList": ["userName", "Wave", "Score"],
                        "reactToList": [],
                        "originalMinimumWidth": 300,
                        "originalMinimumHeight": 480
                    },
                    "broadcastMap": ["userName", "firstName"]
                },
                "actualViewConfig": {
                    "deviceWidths": {
                        "320": "mobile",
                        "540": "tablet",
                        "800": "desktop",
                        "1200": "wideScreen"
                    },
                    "dateFormat": {
                        "selectedFormat": "mm-dd-yyyy",
                        "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                    },
                    "minimumWidth": 300,
                    "minimumHeight": 480,
                    "anchor": [275, 295],
                    "zindex": 0,
                    "isMaximized": false,
                    "listensForList": ["userName", "Wave", "Score"],
                    "reactToList": [],
                    "originalMinimumWidth": 300,
                    "originalMinimumHeight": 480
                },
                "updateWidget": true,
                "clientId": 100,
                "isMaximized": false
            }]
        }, {
            "canvasName": "AssignmentManagement",
            "canvasId": "231",
            "canvasType": "COMPANY",
            "displayOrder": "3"
        }, {
            "canvasName": "GroupManagement",
            "canvasId": "235",
            "canvasType": "COMPANY",
            "displayOrder": "4"
        }]);

        // // new Date().toJSON(), 
        localUtilsService.saveActiveCanvas("2015-04-09T12:13:54.156Z");
        expect(localCanvasService.saveCanvas).not.toHaveBeenCalled();
    });

    it('handle saveActiveCanvas() method for "lucas" canvas', function() {
        spyOn(localCanvasService, 'saveCanvas').andCallThrough();

        localStoreService.addLSItem(null, "ActiveCanvasId", "221");
        localStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', [{
            "canvasName": "AssignmentManagement",
            "canvasId": "221",
            "canvasType": "LUCAS",
            "displayOrder": "2",
            "widgetInstanceList": [{
                "data": [],
                "id": 8,
                "widgetDefinition": {
                    "name": "create-or-edit-user-form-widget",
                    "shortName": "CreateUser",
                    "widgetActionConfig": {
                        "widget-access": {
                            "create-edit-user-widget-access": true
                        },
                        "widget-actions": {
                            "edit-user": true,
                            "create-user": true
                        }
                    },
                    "definitionData": {
                        "User": [null]
                    },
                    "broadcastList": ["userName"],
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
                    "defaultViewConfig": {
                        "deviceWidths": {
                            "320": "mobile",
                            "540": "tablet",
                            "800": "desktop",
                            "1200": "wideScreen"
                        },
                        "dateFormat": {
                            "selectedFormat": "mm-dd-yyyy",
                            "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                        },
                        "minimumWidth": 300,
                        "minimumHeight": 480,
                        "anchor": [275, 295],
                        "zindex": 0,
                        "isMaximized": false,
                        "listensForList": ["userName", "Wave", "Score"],
                        "reactToList": [],
                        "originalMinimumWidth": 300,
                        "originalMinimumHeight": 480
                    },
                    "broadcastMap": ["userName", "firstName"]
                },
                "actualViewConfig": {
                    "deviceWidths": {
                        "320": "mobile",
                        "540": "tablet",
                        "800": "desktop",
                        "1200": "wideScreen"
                    },
                    "dateFormat": {
                        "selectedFormat": "mm-dd-yyyy",
                        "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                    },
                    "minimumWidth": 300,
                    "minimumHeight": 480,
                    "anchor": [275, 295],
                    "zindex": 0,
                    "isMaximized": false,
                    "listensForList": ["userName", "Wave", "Score"],
                    "reactToList": [],
                    "originalMinimumWidth": 300,
                    "originalMinimumHeight": 480
                },
                "updateWidget": true,
                "clientId": 100,
                "isMaximized": false
            }]
        }]);

        // // new Date().toJSON(), 
        localUtilsService.saveActiveCanvas("2015-04-09T12:13:54.156Z");
        expect(localCanvasService.saveCanvas).not.toHaveBeenCalled();
    });
    
    it("should check if device is touch or not", function(){
        spyOn(localUtilsService, 'isTouchDevice').andCallThrough();
        expect(localUtilsService.isTouchDevice).toBeDefined();
        expect(localUtilsService.isTouchDevice()).toBeFalsy();
    })

    //Broadcasting specs
    describe('Widget interactions', function() {

        var broadcast;

        beforeEach(inject(function (_UtilsService_,_$rootScope_) {
            localUtilsService = _UtilsService_;
            $rootScope = _$rootScope_;

            //initialise new BroadcastObject
            broadcast = new BroadcastObject();

            //spies
            spyOn($rootScope, '$emit').andCallThrough();
        }));

        it('should broadcast a WidgetInteractions event', function() {
            //mock broadcast object
            broadcast.setWidgetId(1234);
            broadcast.setData({
                "username" : ["Lucas"]
            });

            //invoke service function
            localUtilsService.broadcast(broadcast);
            expect($rootScope.$emit).toHaveBeenCalledWith('WidgetBroadcast', broadcast);
        });

        it('should not broadcast an undefined object', function () {
            //mock undefined object
            broadcast = undefined;

            //invoke service function
            localUtilsService.broadcast(broadcast);
            expect($rootScope.$emit).not.toHaveBeenCalled();
        });

        it('should not broadcast when Widget ID is missing', function () {
            //invoke service function -
            localUtilsService.broadcast(broadcast);
            expect($rootScope.$emit).not.toHaveBeenCalled();
        });

        it('should not broadcast when Broadcast Data is missing', function () {
            //set only the WidgetID property
            broadcast.setWidgetId(1234);

            //invoke service function
            localUtilsService.broadcast(broadcast);
            expect($rootScope.$emit).not.toHaveBeenCalled();
        });
    });


    //Widget Data persistence specs
    describe('Widget persistence', function() {


        beforeEach(inject(function (_UtilsService_) {
            localUtilsService = _UtilsService_;
        }));

        it('should be possible to save and retrieve widget data', function() {

            //mock widget data to be persisted
            var widgetId = 100;
            var data = {
                "username" : "jack123"
            };

            //invoke service function to persist widget data
            localUtilsService.persistWidgetData(widgetId, data);

            //test that we're able to retrieve the saved data
            var result = localUtilsService.getPersistedWidgetData(widgetId);
            expect(result.username).toEqual('jack123');
        });


        it('should clear cached widget data', function() {

            //mock widget data to be persisted
            var widgetId = 100;
            var data = {
                "username" : "jack123"
            };

            //first persist the widget data
            localUtilsService.persistWidgetData(widgetId, data);

            //ensure it's persisted that we're able to retrieve the saved data
            var result = localUtilsService.getPersistedWidgetData(widgetId);
            expect(result.username).toEqual('jack123');

            //now invoke the service function to clear the data, and assert
            //no data exists for the widget
            localUtilsService.clearAllWidgetData();
            result = localUtilsService.getPersistedWidgetData(widgetId);
            expect(result).toBe(null);
        });
    });

    it('should handle triggerWindowResize method', function() {
        spyOn(localUtilsService, 'triggerWindowResize').andReturn();
        localUtilsService.triggerWindowResize();
        expect(localUtilsService.triggerWindowResize).toHaveBeenCalled();
    });

    it('should handle getProperty method', function() {
        var arr = [{
            "lastName": "Lucas System",
            "skill": null,
            "userName": "system",
            "firstName": "Lucas System"
        }, {
            "lastName": "User",
            "skill": null,
            "userName": "admin123",
            "firstName": "Admin"
        }, {
            "lastName": "User1",
            "skill": null,
            "userName": "jill123",
            "firstName": "Jill"
        }]
        expect(localUtilsService.getProperty(arr, "userName")).toEqual(["system", "admin123", "jill123"]);
    });

    it('should handle getWidgetDetailsOfActiveCanvas method', function() {
        localStoreService.addLSItem(null, "ActiveCanvasId", "231");
        localStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', [{
            "canvasName": "ProductManagement",
            "canvasId": "201",
            "canvasType": "COMPANY",
            "displayOrder": "1"
        }, {
            "canvasName": "AssignmentManagement",
            "canvasId": "231",
            "canvasType": "COMPANY",
            "displayOrder": "3",
            "widgetInstanceList": [{
                "data": null,
                "id": 26,
                "actualViewConfig": {
                    "position": 2,
                    "anchor": [1, 363],
                    "zindex": 0,
                    "dateFormat": {
                        "selectedFormat": "mm-dd-yyyy",
                        "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                    },
                    "minimumWidth": 300,
                    "minimumHeight": 480,
                    "orientation": {
                        "option": null,
                        "selected": null
                    },
                    "gridColumns": {
                        "1": {
                            "allowFilter": true,
                            "fieldName": "userName",
                            "name": "Lucas Login",
                            "order": "1",
                            "sortOrder": "1",
                            "width": 150,
                            "visible": true
                        },
                        "2": {
                            "allowFilter": true,
                            "fieldName": "wmsUser",
                            "name": "Host Login",
                            "order": "2",
                            "sortOrder": "1",
                            "width": 150,
                            "visible": false
                        },
                        "3": {
                            "allowFilter": true,
                            "fieldName": "firstName",
                            "name": "First Name",
                            "order": "3",
                            "sortOrder": "1",
                            "width": 150,
                            "visible": true
                        },
                        "4": {
                            "allowFilter": true,
                            "fieldName": "lastName",
                            "name": "Last Name",
                            "order": "4",
                            "sortOrder": "1",
                            "width": 150,
                            "visible": true
                        },
                        "5": {
                            "allowFilter": true,
                            "fieldName": "skill",
                            "name": "Skill",
                            "order": "5",
                            "sortOrder": "1",
                            "width": 150,
                            "visible": true
                        },
                        "6": {
                            "allowFilter": true,
                            "fieldName": "shift",
                            "name": "Shifts",
                            "order": "6",
                            "sortOrder": "1",
                            "width": 150,
                            "visible": true
                        },
                        "7": {
                            "allowFilter": true,
                            "fieldName": "j2uLanguage",
                            "name": "J2U Language",
                            "order": "7",
                            "sortOrder": "1",
                            "width": 150,
                            "visible": true
                        },
                        "8": {
                            "allowFilter": true,
                            "fieldName": "u2jLanguage",
                            "name": "U2J Language",
                            "order": "8",
                            "sortOrder": "1",
                            "width": 150,
                            "visible": true
                        },
                        "9": {
                            "allowFilter": true,
                            "fieldName": "hhLanguage",
                            "name": "HH Language",
                            "order": "9",
                            "sortOrder": "1",
                            "width": 150,
                            "visible": true
                        },
                        "10": {
                            "allowFilter": true,
                            "fieldName": "amdLanguage",
                            "name": "AMD Language",
                            "order": "10",
                            "sortOrder": "1",
                            "width": 150,
                            "visible": true
                        },
                        "11": {
                            "allowFilter": true,
                            "fieldName": "enable",
                            "name": "Status",
                            "order": "11",
                            "sortOrder": "1",
                            "width": 150,
                            "visible": true
                        },
                        "12": {
                            "allowFilter": true,
                            "fieldName": "employeeId",
                            "name": "Employee Id",
                            "order": "12",
                            "sortOrder": "1",
                            "width": 150,
                            "visible": false
                        },
                        "13": {
                            "allowFilter": true,
                            "fieldName": "title",
                            "name": "Title",
                            "order": "13",
                            "sortOrder": "1",
                            "width": 150,
                            "visible": false
                        },
                        "14": {
                            "allowFilter": true,
                            "fieldName": "startDate",
                            "name": "Start Date",
                            "order": "14",
                            "sortOrder": "1",
                            "width": 150,
                            "visible": true
                        },
                        "15": {
                            "allowFilter": true,
                            "fieldName": "birthDate",
                            "name": "Birth Date",
                            "order": "15",
                            "sortOrder": "1",
                            "width": 150,
                            "visible": false
                        },
                        "16": {
                            "allowFilter": true,
                            "fieldName": "mobileNumber",
                            "name": "Mobile Number",
                            "order": "16",
                            "sortOrder": "1",
                            "width": 150,
                            "visible": false
                        },
                        "17": {
                            "allowFilter": true,
                            "fieldName": "emailAddress",
                            "name": "Email Address",
                            "order": "17",
                            "sortOrder": "1",
                            "width": 150,
                            "visible": true
                        }
                    },
                    "listensForList": ["Score"],
                    "autoRefreshConfig": {
                        "enabled": true,
                        "globalOverride": false,
                        "interval": 120
                    }
                },
                "widgetInteractionConfig": [],
                "widgetDefinition": {
                    "name": "search-user-grid-widget",
                    "id": 9,
                    "shortName": "SearchUser",
                    "widgetActionConfig": {
                        "widget-access": {
                            "search-user-widget-access": true
                        },
                        "widget-actions": {
                            "delete-user": true,
                            "retrain-voice-model": true,
                            "disable-user": true,
                            "enable-user": true
                        }
                    },
                    "broadcastList": ["userName"],
                    "reactToList": ["userName"],
                    "defaultData": {},
                    "defaultViewConfig": {
                        "anchor": [1, 363],
                        "zindex": 1,
                        "dateFormat": {
                            "selectedFormat": "mm-dd-yyyy",
                            "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                        },
                        "minimumWidth": 485,
                        "minimumHeight": 375,
                        "orientation": {
                            "option": null,
                            "selected": null
                        },
                        "gridColumns": {
                            "1": {
                                "name": "Lucas Login",
                                "values": [],
                                "fieldName": "userName",
                                "width": 150,
                                "allowFilter": true,
                                "sortOrder": "1",
                                "visible": true,
                                "order": "1"
                            },
                            "2": {
                                "name": "Host Login",
                                "values": [],
                                "fieldName": "wmsUser",
                                "width": 150,
                                "allowFilter": true,
                                "sortOrder": "1",
                                "visible": false,
                                "order": "2"
                            },
                            "3": {
                                "name": "First Name",
                                "values": [],
                                "fieldName": "firstName",
                                "width": 150,
                                "allowFilter": true,
                                "sortOrder": "1",
                                "visible": true,
                                "order": "3"
                            },
                            "4": {
                                "name": "Last Name",
                                "values": [],
                                "fieldName": "lastName",
                                "width": 150,
                                "allowFilter": true,
                                "sortOrder": "1",
                                "visible": true,
                                "order": "4"
                            },
                            "5": {
                                "name": "Skill",
                                "values": [],
                                "fieldName": "skill",
                                "width": 150,
                                "allowFilter": true,
                                "sortOrder": "1",
                                "visible": true,
                                "order": "5"
                            },
                            "6": {
                                "name": "Shifts",
                                "values": [],
                                "fieldName": "shift",
                                "width": 150,
                                "allowFilter": true,
                                "sortOrder": "1",
                                "visible": true,
                                "order": "6"
                            },
                            "7": {
                                "name": "J2U Language",
                                "values": [],
                                "fieldName": "j2uLanguage",
                                "width": 150,
                                "allowFilter": true,
                                "sortOrder": "1",
                                "visible": true,
                                "order": "7"
                            },
                            "8": {
                                "name": "U2J Language",
                                "values": [],
                                "fieldName": "u2jLanguage",
                                "width": 150,
                                "allowFilter": true,
                                "sortOrder": "1",
                                "visible": true,
                                "order": "8"
                            },
                            "9": {
                                "name": "HH Language",
                                "values": [],
                                "fieldName": "hhLanguage",
                                "width": 150,
                                "allowFilter": true,
                                "sortOrder": "1",
                                "visible": true,
                                "order": "9"
                            },
                            "10": {
                                "name": "AMD Language",
                                "values": [],
                                "fieldName": "amdLanguage",
                                "width": 150,
                                "allowFilter": true,
                                "sortOrder": "1",
                                "visible": true,
                                "order": "10"
                            },
                            "11": {
                                "name": "Status",
                                "values": [],
                                "fieldName": "enable",
                                "width": 150,
                                "allowFilter": true,
                                "sortOrder": "1",
                                "visible": true,
                                "order": "11"
                            },
                            "12": {
                                "name": "Employee Id",
                                "values": [],
                                "fieldName": "employeeId",
                                "width": 150,
                                "allowFilter": true,
                                "sortOrder": "1",
                                "visible": false,
                                "order": "12"
                            },
                            "13": {
                                "name": "Title",
                                "values": [],
                                "fieldName": "title",
                                "width": 150,
                                "allowFilter": true,
                                "sortOrder": "1",
                                "visible": false,
                                "order": "13"
                            },
                            "14": {
                                "name": "Start Date",
                                "values": [],
                                "fieldName": "startDate",
                                "width": 150,
                                "allowFilter": true,
                                "sortOrder": "1",
                                "visible": true,
                                "order": "14"
                            },
                            "15": {
                                "name": "Birth Date",
                                "values": [],
                                "fieldName": "birthDate",
                                "width": 150,
                                "allowFilter": true,
                                "sortOrder": "1",
                                "visible": false,
                                "order": "15"
                            },
                            "16": {
                                "name": "Mobile Number",
                                "values": [],
                                "fieldName": "mobileNumber",
                                "width": 150,
                                "allowFilter": true,
                                "sortOrder": "1",
                                "visible": false,
                                "order": "16"
                            },
                            "17": {
                                "name": "Email Address",
                                "values": [],
                                "fieldName": "emailAddress",
                                "width": 150,
                                "allowFilter": true,
                                "sortOrder": "1",
                                "visible": true,
                                "order": "17"
                            }
                        },
                        "isMaximized": null,
                        "listensForList": ["userName"],
                        "deviceWidths": {
                            "320": "mobile",
                            "600": "tablet",
                            "800": "desktop",
                            "1200": "wideScreen"
                        },
                        "autoRefreshConfig": {
                            "enabled": true,
                            "globalOverride": false,
                            "interval": 120
                        }
                    },
                    "dataURL": {
                        "url": "/users/userlist/search",
                        "searchCriteria": {
                            "pageNumber": "0",
                            "pageSize": "100",
                            "searchMap": {},
                            "sortMap": {}
                        }
                    },
                    "title": "Search User"
                },
                "dataURL": {
                    "url": "/users/userlist/search",
                    "searchCriteria": {
                        "pageNumber": "0",
                        "pageSize": "100",
                        "searchMap": {},
                        "sortMap": {}
                    }
                },
                "clientId": 101
            }]
        }, {
            "canvasName": "GroupManagement",
            "canvasId": "235",
            "canvasType": "COMPANY",
            "displayOrder": "4"
        }]);
        expect(localUtilsService.getWidgetDetailsOfActiveCanvas('search-user-grid-widget', ['SearchUser'])).toEqual([{
            id: '26',
            name: 'SearchUser, 26'
        }]);
    });

    it('handle setAllChildProperties method', function() {
        var source = {
            "passwordEnabled": true,
            "confirmPasswordEnabled": true,
            "hostLoginEnabled": true,
            "hostPassswordEnabled": true,
            "skillEnabled": true,
            "shiftEnabled": true,
            "j2uLanguageEnabled": true,
            "u2jLanguageEnabled": true,
            "hhLanguageEnabled": true,
            "amdLanguageEnabled": true,
            "employeeNumberEnabled": true,
            "startDateEnabled": true,
            "firstNameEnabled": true,
            "lastNameEnabled": true,
            "birthDateEnabled": true,
            "mobileNumberEnabled": true,
            "emailAddressEnabled": true,
            "moveDataEnabled": true
        };
        expect(localUtilsService.setAllChildProperties(source, false)).toEqual({
            "passwordEnabled": false,
            "confirmPasswordEnabled": false,
            "hostLoginEnabled": false,
            "hostPassswordEnabled": false,
            "skillEnabled": false,
            "shiftEnabled": false,
            "j2uLanguageEnabled": false,
            "u2jLanguageEnabled": false,
            "hhLanguageEnabled": false,
            "amdLanguageEnabled": false,
            "employeeNumberEnabled": false,
            "startDateEnabled": false,
            "firstNameEnabled": false,
            "lastNameEnabled": false,
            "birthDateEnabled": false,
            "mobileNumberEnabled": false,
            "emailAddressEnabled": false,
            "moveDataEnabled": false
        });
    });

});