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

describe('EAIEventsGrid Controller Unit Tests', function() {

    // Global vars
    var def = null;
    var localEAIEventsGridController = null;
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
                        "name": "Mapping Name",
                        "visible": true,
                        "fieldName": "name",
                        "sortOrder": "1",
                        "width": 150,
                        "order": "1",
                        "allowFilter": true
                    },
                    "1": {
                        "name": "Description",
                        "visible": true,
                        "fieldName": "description",
                        "sortOrder": "1",
                        "width": 150,
                        "order": "2",
                        "allowFilter": true
                    },
                    "2": {
                        "name": "Source Message",
                        "visible": false,
                        "fieldName": "sourcemessage",
                        "sortOrder": "1",
                        "width": 150,
                        "order": "3",
                        "allowFilter": true
                    },
                    "3": {
                        "name": "Destination Message",
                        "visible": true,
                        "fieldName": "destinationmessage",
                        "sortOrder": "1",
                        "width": 150,
                        "order": "4",
                        "allowFilter": true
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
                        "name": "Mapping Name",
                        "visible": true,
                        "fieldName": "name",
                        "sortOrder": "1",
                        "width": 150,
                        "order": "1",
                        "allowFilter": true
                    },
                    "1": {
                        "name": "Description",
                        "visible": true,
                        "fieldName": "description",
                        "sortOrder": "1",
                        "width": 150,
                        "order": "2",
                        "allowFilter": true
                    },
                    "2": {
                        "name": "Source Message",
                        "visible": false,
                        "fieldName": "sourcemessage",
                        "sortOrder": "1",
                        "width": 150,
                        "order": "3",
                        "allowFilter": true
                    },
                    "3": {
                        "name": "Destination Message",
                        "visible": true,
                        "fieldName": "destinationmessage",
                        "sortOrder": "1",
                        "width": 150,
                        "order": "4",
                        "allowFilter": true
                    }
                }
            },
            "widgetDefinition": {
                "name": "eai-events-grid-widget",
                "id": 14,
                "widgetActionConfig": {
                    "widget-access": {
                        "eai-events-grid-widget-access": true
                    },
                    "widget-actions": {
                        "edit-group": true,
                        "create-group": true,
                        "delete-group": true,
                        "view-group": true
                    }
                },
                "broadcastList": ["groupName"],
                "shortName": "EAIEvents",
                "defaultData": {},
                "defaultViewConfig": {
                    "listensForList": ["groupName"],
                    "width": 485,
                    "height": 375,
                    "anchor": [1, 363],
                    "zindex": 1
                },
                "title": "EAI Events",
                "dataURL": {
                    "searchCriteria": {
                        "searchMap": {},
                        "pageNumber": "0",
                        "pageSize": "10",
                        "sortMap": {}
                    },
                    "url": "/events/event-list/search"
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
                        "url": "/events/event-list/search"
                    }
                }
            }
        };

        mockWidgetInstance = {
            "data": {
                "data": {
                    "grid": {
                        "0": {
                            "name": "Mapping Name",
                            "visible": true,
                            "fieldName": "name",
                            "sortOrder": "1",
                            "width": 150,
                            "order": "1",
                            "allowFilter": true
                        },
                        "1": {
                            "name": "Description",
                            "visible": true,
                            "fieldName": "description",
                            "sortOrder": "1",
                            "width": 150,
                            "order": "2",
                            "allowFilter": true
                        },
                        "2": {
                            "name": "Source Message",
                            "visible": false,
                            "fieldName": "sourcemessage",
                            "sortOrder": "1",
                            "width": 150,
                            "order": "3",
                            "allowFilter": true
                        },
                        "3": {
                            "name": "Destination Message",
                            "visible": true,
                            "fieldName": "destinationmessage",
                            "sortOrder": "1",
                            "width": 150,
                            "order": "4",
                            "allowFilter": true
                        }
                    }
                }
            },
            "widgetDefinition": {
                "name": "eai-events-grid-widget",
                "id": 14,
                "widgetActionConfig": {
                    "widget-access": {
                        "eai-events-grid-widget-access": true
                    },
                    "widget-actions": {
                        "edit-group": true,
                        "create-group": true,
                        "delete-group": true,
                        "view-group": true
                    }
                },
                "broadcastList": ["groupName"],
                "shortName": "EAIEvents",
                "defaultData": {},
                "defaultViewConfig": {
                    "listensForList": ["groupName"],
                    "width": 485,
                    "height": 375,
                    "anchor": [1, 363],
                    "zindex": 1
                },
                "title": "EAI Events",
                "dataURL": {
                    "searchCriteria": {
                        "searchMap": {},
                        "pageNumber": "0",
                        "pageSize": "10",
                        "sortMap": {}
                    },
                    "url": "/events/event-list/search"
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
                        "url": "/events/event-list/search"
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
            "title": "EAI Events",
            "dataURL": {
                "searchCriteria": {
                    "searchMap": {},
                    "pageNumber": "0",
                    "pageSize": "10",
                    "sortMap": {}
                },
                "url": "/events/event-list/search"
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
                    "url": "/events/event-list/search"
                }
            },
            "updateWidget": true,
            "clientId": 100,
            "isMaximized": false
        };

    }));

    // Inject Specs
    describe('EAIEvents Grid Injection Specs', function() {

        beforeEach(inject(function($controller) {
            localEAIEventsGridController = $controller('EAIEventsGridController', {
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
                "name": "Usage in Events",
                "visible": true,
                "fieldName": "usageinevents",
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

    describe('EAIEvents Grid updateExistingWidget test', function() {

        beforeEach(inject(function($controller) {
            spyOn(scope, '$on').andCallThrough();

            localEAIEventsGridController = $controller('EAIEventsGridController', {
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