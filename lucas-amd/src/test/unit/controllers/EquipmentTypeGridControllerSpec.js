/*
Lucas Systems Inc
11279 Perry Highway
Wexford
PA 15090
United States
 
 
The information in this file contains trade secrets and confidential
information which is the property of Lucas Systems Inc.
 
All trademarks, trade names, copyrights and other intellectual property
rights created, developed, embodied in or arising in connection with
this software shall remain the sole property of Lucas Systems Inc.
 
Copyright (c) Lucas Systems, 2014
ALL RIGHTS RESERVED
 
*/

'use strict';

describe('EquipmentTypeGrid Controller Unit Tests', function() {

    // Global vars
    var def = null;
    var localEquipmentTypeGridController = null;
    var scope = null;
    var localModalInstance = null;
    var mockWidgetInstance = null;
    var UtilsService = null;
    var WidgetGridService = null;
    var WidgetService = null;
    var uiGridConstants = null;
    var httpBackend = null;

    // Global test setup
    beforeEach(module('amdApp', function($translateProvider) {
        $translateProvider.translations('en', {
            "language-code": "EN"
        }, 'fr', {
            "language-code": "fr"
        }, 'de', {
            "language-code": "de"
        }).preferredLanguage('en');
        $translateProvider.useLoader('LocaleLoader');
    }));

    beforeEach(inject(function(_$controller_, _$rootScope_, _UtilsService_, _WidgetGridService_, _$modal_, _WidgetService_, _uiGridConstants_, $httpBackend) {
        scope = _$rootScope_.$new();
        UtilsService = _UtilsService_;
        WidgetGridService = _WidgetGridService_;
        WidgetService = _WidgetService_;
        uiGridConstants = _uiGridConstants_;
        localModalInstance = _$modal_;
        httpBackend = $httpBackend;

        httpBackend.when('GET').respond({});

        httpBackend.when('POST').respond({
            "status": "success",
            "code": "200",
            "message": "Request processed successfully",
            "level": null,
            "uniqueKey": null,
            "token": null,
            "explicitDismissal": null,
            "data": {
                "grid": {
                    "0": {
                        "values": [
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "zzz"
                        ]
                    },
                    "1": {
                        "values": [
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "zzz"
                        ]
                    },
                    "2": {
                        "values": [
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "zzz"
                        ]
                    },
                    "3": {
                        "values": [
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "Single Position Pallet Jack",
                            "Two Position Pallet Jack",
                            "Three Position Pallet Jack",
                            "Four Position Pallet Jack",
                            "8 Position One Sided Cart"
                        ]
                    },
                    "4": {
                        "values": [
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "zzz"
                        ]
                    },
                    "5": {
                        "values": [
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "zzz"
                        ]
                    },
                    "6": {
                        "values": [
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "zzz"
                        ]
                    },
                    "7": {
                        "values": [
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "zzz"
                        ]
                    },
                    "8": {
                        "values": [
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "zzz"
                        ]
                    },
                    "9": {
                        "values": [
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "zzz"
                        ]
                    },
                    "10": {
                        "values": [
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "yyy",
                            "xxxx",
                            "xxxx",
                            "xxxx",
                            "zzz"
                        ]
                    }
                }
            }
        });

        scope.widgetdetails = {
            "clientId": 300,
            "actualViewConfig": {
                "height": 500,
                "width": 1100,
                "anchor": [
                    1,
                    2
                ],
                "zindex": 1,
                "listensForList": [
                    "groupName"
                ],
                "gridColumns": {
                    "0": {
                        "name": "Group Name",
                        "fieldName": "groupName",
                        "order": "1",
                        "sortOrder": "1",
                        "visible": true,
                        "allowFilter": true
                    },
                    "1": {
                        "name": "Description",
                        "fieldName": "description",
                        "order": "2",
                        "sortOrder": "2",
                        "visible": true,
                        "allowFilter": true
                    },
                    "2": {
                        "name": "User Count",
                        "fieldName": "userCount",
                        "order": "3",
                        "sortOrder": "3",
                        "visible": true,
                        "allowFilter": true
                    }
                }
            },
            "widgetDefinition": {
                "name": "search-group-grid-widget",
                "id": 14,
                "widgetActionConfig": {
                    "widget-access": {
                        "search-group-grid-widget-access": true
                    },
                    "widget-actions": {
                        "edit-group": true,
                        "create-group": true,
                        "delete-group": true,
                        "view-group": true
                    }
                },
                "broadcastList": ["groupName"],
                "shortName": "SearchGroup",
                "defaultData": {},
                "defaultViewConfig": {
                    "listensForList": ["groupName"],
                    "width": 485,
                    "height": 375,
                    "anchor": [1, 363],
                    "zindex": 1
                },
                "title": "Search Group",
                "dataURL": {
                    "searchCriteria": {
                        "searchMap": {},
                        "pageNumber": "0",
                        "pageSize": "10",
                        "sortMap": {}
                    },
                    "url": "/groups/grouplist/search"
                },
                "reactToMap": {
                    "groupName": {
                        "searchCriteria": {
                            "searchMap": {
                                "groupName": ["$groupName"]
                            },
                            "pageNumber": "0",
                            "pageSize": "10"
                        },
                        "url": "/groups/grouplist/search"
                    }
                }
            }
        };

        mockWidgetInstance = {
            "data": {
                "data": {
                    "grid": {
                        "0": {
                            "values": [
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "zzz"
                            ]
                        },
                        "1": {
                            "values": [
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "zzz"
                            ]
                        },
                        "2": {
                            "values": [
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "zzz"
                            ]
                        },
                        "3": {
                            "values": [
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "Single Position Pallet Jack",
                                "Two Position Pallet Jack",
                                "Three Position Pallet Jack",
                                "Four Position Pallet Jack",
                                "8 Position One Sided Cart"
                            ]
                        },
                        "4": {
                            "values": [
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "zzz"
                            ]
                        },
                        "5": {
                            "values": [
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "zzz"
                            ]
                        },
                        "6": {
                            "values": [
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "zzz"
                            ]
                        },
                        "7": {
                            "values": [
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "zzz"
                            ]
                        },
                        "8": {
                            "values": [
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "zzz"
                            ]
                        },
                        "9": {
                            "values": [
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "zzz"
                            ]
                        },
                        "10": {
                            "values": [
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "yyy",
                                "xxxx",
                                "xxxx",
                                "xxxx",
                                "zzz"
                            ]
                        }
                    }
                }
            },
            "widgetDefinition": {
                "name": "search-group-grid-widget",
                "id": 14,
                "widgetActionConfig": {
                    "widget-access": {
                        "search-group-grid-widget-access": true
                    },
                    "widget-actions": {
                        "edit-group": true,
                        "create-group": true,
                        "delete-group": true,
                        "view-group": true
                    }
                },
                "broadcastList": ["groupName"],
                "shortName": "SearchGroup",
                "defaultData": {},
                "defaultViewConfig": {
                    "listensForList": ["groupName"],
                    "width": 485,
                    "height": 375,
                    "anchor": [1, 363],
                    "zindex": 1
                },
                "title": "Search Group",
                "dataURL": {
                    "searchCriteria": {
                        "searchMap": {},
                        "pageNumber": "0",
                        "pageSize": "10",
                        "sortMap": {}
                    },
                    "url": "/groups/grouplist/search"
                },
                "reactToMap": {
                    "groupName": {
                        "searchCriteria": {
                            "searchMap": {
                                "groupName": ["$groupName"]
                            },
                            "pageNumber": "0",
                            "pageSize": "10"
                        },
                        "url": "/groups/grouplist/search"
                    }
                }
            },
            "actualViewConfig": {

                "widget-actions": {
                    "edit-group": true,
                    "create-group": true,
                    "delete-group": true,
                    "view-group": true
                }
            },
            "defaultData": {},
            "defaultViewConfig": {
                "listensForList": ["groupName"],
                "width": 485,
                "height": 375,
                "anchor": [1, 363],
                "zindex": 1
            },
            "title": "Search Group",
            "dataURL": {
                "searchCriteria": {
                    "searchMap": {},
                    "pageNumber": "0",
                    "pageSize": "10",
                    "sortMap": {}
                },
                "url": "/groups/grouplist/search"
            },
            "reactToMap": {
                "groupName": {
                    "searchCriteria": {
                        "searchMap": {
                            "groupName": ["$groupName"]
                        },
                        "pageNumber": "0",
                        "pageSize": "10"
                    },
                    "url": "/groups/grouplist/search"
                }
            },
            "updateWidget": true,
            "clientId": 100,
            "isMaximized": false
        };

    }));

    // Inject Specs
    describe('EquipmentType Grid Injection Specs', function() {

        beforeEach(inject(function($controller) {
            localEquipmentTypeGridController = $controller('EquipmentTypeGridController', {
                $scope: scope,
                UtilsService: UtilsService,
                WidgetGridService: WidgetGridService,
                WidgetService: WidgetService,
                uiGridConstants: uiGridConstants
            });
        }));

        it('should inject $scope', function() {
            expect(scope).toBeDefined();
        });

        it('should be defined in scope', function() {
            scope.$digest();
            expect(scope.selectedRows).toBeDefined();
            expect(scope.widgetdetails).toBeDefined();
            expect(scope.sortCriteria).toBeDefined();
            expect(scope.filterCriteria).toBeDefined();
            expect(scope.columnDefinitions).toBeDefined();
            expect(scope.gridOptions).toBeDefined();
        });

        it('should react to columnDefinitions change', function() {
            var newColumn = {
                "name": "Usage in EquipmentType",
                "visible": true,
                "fieldName": "usageInEquipmentType",
                "sortOrder": "1",
                "width": 150,
                "order": "5",
                "allowFilter": true
            };
            spyOn(scope, '$watch').andCallThrough();
            httpBackend.flush();
            scope.$watch('columnDefinitions', scope.widgetdetails.actualViewConfig.gridColumns);
            scope.widgetdetails.actualViewConfig.gridColumns["4"] = newColumn;
            scope.$apply();
            expect(scope.$watch).toHaveBeenCalled();
        });

        it('should call loadRecords function to fetchLiveResults', function() {
            spyOn(scope, 'loadRecords').andReturn(null);
            expect(scope.fetchLiveResults).toBeDefined();
            scope.fetchLiveResults();
            expect(scope.loadRecords).toHaveBeenCalled();
        });

        it('Should call the destroy method', function() {
            spyOn(scope, "$on").andCallThrough();
            spyOn(scope, "$broadcast").andCallThrough();
            scope.$broadcast("$destroy");
            scope.$on();
            expect(scope.$broadcast).toHaveBeenCalledWith("$destroy");
            expect(scope.$on).toHaveBeenCalled();
        });

        it('handle onRegisterApi', function() {
            var mockGridObj = {
                colMovable: {
                    on: {
                        columnPositionChanged: function() {}
                    }
                },
                colResizable: {
                    on: {
                        columnSizeChanged: function() {}
                    }
                },
                core: {},
                grid: {},
                infiniteScroll: {
                    on: {
                        needLoadMoreData: function() {

                        },
                        rowSelectionChanged: function() {

                        }
                    }
                },
                listeners: [],
                selection: {
                    on: {
                        needLoadMoreData: function() {

                        },
                        rowSelectionChanged: function() {

                        },
                        rowSelectionChangedBatch: function() {}
                    }
                }
            };

            // spy on needLoadMoreData
            spyOn(mockGridObj.infiniteScroll.on, 'needLoadMoreData').andCallThrough();
            // spy on rowSelectionChanged
            spyOn(mockGridObj.selection.on, 'rowSelectionChanged').andCallThrough();
            // spy on rowSelectionChangedBatch
            spyOn(mockGridObj.selection.on, 'rowSelectionChangedBatch').andCallThrough();
            // spy on columnPositionChanged
            spyOn(mockGridObj.colMovable.on, 'columnPositionChanged').andCallThrough();
            // spy on columnSizeChanged
            spyOn(mockGridObj.colResizable.on, 'columnSizeChanged').andCallThrough();

            scope.gridOptions.onRegisterApi(mockGridObj);
            // test gridApi
            expect(scope.gridApi).toEqual(mockGridObj);

            // test needLoadMoreData
            expect(mockGridObj.infiniteScroll.on.needLoadMoreData).toHaveBeenCalled();
            // test rowSelectionChanged
            expect(mockGridObj.selection.on.rowSelectionChanged).toHaveBeenCalled();
            // test rowSelectionChangedBatch
            expect(mockGridObj.selection.on.rowSelectionChangedBatch).toHaveBeenCalled();
            // test columnPositionChanged
            expect(mockGridObj.colMovable.on.columnPositionChanged).toHaveBeenCalled();
            // test columnSizeChanged
            expect(mockGridObj.colResizable.on.columnSizeChanged).toHaveBeenCalled();
        });

    });

    describe('EquipmentType Grid updateExistingWidget test', function() {

        beforeEach(inject(function($controller) {
            spyOn(scope, '$on').andCallThrough();

            localEquipmentTypeGridController = $controller('EquipmentTypeGridController', {
                $scope: scope,
                UtilsService: UtilsService,
                WidgetGridService: WidgetGridService,
                WidgetService: WidgetService,
                uiGridConstants: uiGridConstants
            });

        }));

        it('should Test updateExistingWidget function', function() {
            var broadcastInstance = mockWidgetInstance;
            broadcastInstance.clientId = 987;
            scope.$broadcast('updateExistingWidget', mockWidgetInstance);
            expect(scope.$on).toHaveBeenCalled();
        });

    });


});