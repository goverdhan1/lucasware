/*
'use strict';

describe('Search Product Grid Controller Specs', function () {

    //Global vars
    var scope;
    var controller;
    var UtilsService;
    var WidgetGridService;

    var widgetdetails;

    //Mock translateProvide
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

    beforeEach(module('ui.grid'));

    //Global test setup
    beforeEach(inject(function (_UtilsService_, _WidgetGridService_) {
        UtilsService = _UtilsService_;
        WidgetGridService = _WidgetGridService_;

        //Skeleton mock of widgetDetails
        //Each test will add it's required properties to this
        widgetdetails = {
            "data": {
                "grid": {
                    "1": {
                        "values": [
                            "99-4939",
                            "98-9305"
                        ]
                    },
                    "2": {
                        "values": [
                            "Toro Pulley",
                            "Water/Fuel Filter ASM"
                        ]
                    },
                    "3": {
                        "values": [
                            "Available",
                            "Available"
                        ]
                    },
                    "4": {
                        "values": [
                            "99-4939",
                            "99-9305"
                        ]
                    },
                    "5": {
                        "values": [
                            "1",
                            "1"
                        ]
                    },
                    "6": {
                        "values": [
                            "EA",
                            "EA"
                        ]
                    },
                    "7": {
                        "values": [
                            "NO",
                            "NO"
                        ]
                    },
                    "8": {
                        "values": [
                            "",
                            ""
                        ]
                    },
                    "9": {
                        "values": [
                            "",
                            ""
                        ]
                    },
                    "10": {
                        "values": [
                            "",
                            ""
                        ]
                    },
                    "11": {
                        "values": [
                            "",
                            ""
                        ]
                    },
                    "12": {
                        "values": [
                            "",
                            ""
                        ]
                    },
                    "13": {
                        "values": [
                            "",
                            ""
                        ]
                    },
                    "14": {
                        "values": [
                            "",
                            ""
                        ]
                    },
                    "15": {
                        "values": [
                            "",
                            ""
                        ]
                    },
                    "16": {
                        "values": [
                            "",
                            ""
                        ]
                    }
                }
            },
            "name": "search-product-grid-widget",
            "id": 14,
            "shortName": "SearchProduct",
            "title": "Search Product",
            "type": "GRID",
            "subtype": "GRID",
            "widgetDefinition": {
                "name": "search-product-grid-widget",
                "id": 14,
                "shortName": "SearchProduct",
                "title": "Search Product",
                "type": "GRID",
                "subtype": "GRID",
                "widgetActionConfig": {
                    "delete-product": true,
                    "edit-product": true,
                    "view-product": true
                },
                "broadcastList": [
                    "productNumber"
                ],
                "broadcastMap": {},
                "reactToMap": {
                    "productNumber": {
                        "url": "/products/productsList/search",
                        "searchCriteria": {
                            "pageNumber": "0",
                            "pageSize": "10",
                            "searchMap": {},
                            "sortMap": {}
                        }
                    }
                },
                "defaultData": {},
                "defaultViewConfig": {
                    "height": 375,
                    "width": 485,
                    "anchor": [
                        1,
                        2
                    ],
                    "zindex": 1,
                    "listensForList": [
                        "productNumber"
                    ],
                    "gridColumns": {
                        "1": {
                            "name": "Product Number",
                            "fieldName": "productNumber",
                            "visible": false,
                            "order": "1",
                            "sortOrder": "1"
                        },
                        "2": {
                            "name": "Description",
                            "fieldName": "description",
                            "visible": true,
                            "order": "2",
                            "sortOrder": "1"
                        },
                        "3": {
                            "name": "Status",
                            "fieldName": "status",
                            "visible": true,
                            "order": "3",
                            "sortOrder": "1"
                        },
                        "4": {
                            "name": "Name",
                            "fieldName": "name",
                            "visible": true,
                            "order": "4",
                            "sortOrder": "1"
                        },
                        "5": {
                            "name": "Marked Out",
                            "fieldName": "markedOut",
                            "visible": true,
                            "order": "5",
                            "sortOrder": "1"
                        },
                        "6": {
                            "name": "Base Item",
                            "fieldName": "baseItem",
                            "visible": true,
                            "order": "6",
                            "sortOrder": "1"
                        },
                        "7": {
                            "name": "Base Item Threshold",
                            "fieldName": "baseItemThreshold",
                            "visible": true,
                            "order": "7",
                            "sortOrder": "1"
                        },
                        "8": {
                            "name": "UPC Check",
                            "fieldName": "upcCheck",
                            "visible": true,
                            "order": "8",
                            "sortOrder": "1"
                        },
                        "9": {
                            "name": "UPC Check Every X qty",
                            "fieldName": "upcCheckEveryXqty",
                            "visible": true,
                            "order": "9",
                            "sortOrder": "1"
                        },
                        "10": {
                            "name": "Capture Lot Number",
                            "fieldName": "captureLotNumber",
                            "visible": true,
                            "order": "10",
                            "sortOrder": "1"
                        },
                        "11": {
                            "name": "Capture Serial Number",
                            "fieldName": "captureSerialNumber",
                            "visible": true,
                            "order": "11",
                            "sortOrder": "1"
                        },
                        "12": {
                            "name": "Heads Up Messages",
                            "fieldName": "headsUpMessages",
                            "visible": true,
                            "order": "12",
                            "sortOrder": "1"
                        }
                    }
                },
                "dataURL": {
                    "url": "/products/productsList/search",
                    "searchCriteria": {
                        "pageNumber": "0",
                        "pageSize": "Long.MAX_VALUE",
                        "searchMap": {},
                        "sortMap": {}
                    }
                }
            },
            "actualViewConfig": {
                dateFormat: {
                    selectedFormat: "mm-dd-yyyy",
                    availableFormats: ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                },
                "height": 375,
                "width": 485,
                "anchor": [
                    1,
                    1
                ],
                "zindex": 1,
                "listensForList": [
                    "productNumber"
                ],
                "gridColumns": {
                    "1": {
                        "name": "Product Number",
                        "fieldName": "productNumber",
                        "visible": false,
                        "order": "1",
                        "sortOrder": "1"
                    },
                    "2": {
                        "name": "Description",
                        "fieldName": "description",
                        "visible": true,
                        "order": "2",
                        "sortOrder": "1"
                    },
                    "3": {
                        "name": "Status",
                        "fieldName": "status",
                        "visible": true,
                        "order": "3",
                        "sortOrder": "1"
                    },
                    "4": {
                        "name": "Name",
                        "fieldName": "name",
                        "visible": true,
                        "order": "4",
                        "sortOrder": "1"
                    },
                    "5": {
                        "name": "Weight",
                        "fieldName": "weight",
                        "visible": true,
                        "order": "5",
                        "sortOrder": "1"
                    },
                    "6": {
                        "name": "UOM",
                        "fieldName": "uom",
                        "visible": true,
                        "order": "6",
                        "sortOrder": "1"
                    },
                    "7": {
                        "name": "Marked Out",
                        "fieldName": "markedOut",
                        "visible": true,
                        "order": "7",
                        "sortOrder": "1"
                    },
                    "8": {
                        "name": "Cube",
                        "fieldName": "cube",
                        "visible": true,
                        "order": "8",
                        "sortOrder": "1"
                    },
                    "9": {
                        "name": "Base Item",
                        "fieldName": "baseItem",
                        "visible": true,
                        "order": "10",
                        "sortOrder": "1"
                    },
                    "10": {
                        "name": "Base Item Threshold",
                        "fieldName": "baseItemThreshold",
                        "visible": true,
                        "order": "10",
                        "sortOrder": "1"
                    },
                    "11": {
                        "name": "UPC Check",
                        "fieldName": "upcCheck",
                        "visible": true,
                        "order": "11",
                        "sortOrder": "1"
                    },
                    "12": {
                        "name": "UPC Check Every X qty",
                        "fieldName": "upcCheckEveryXqty",
                        "visible": true,
                        "order": "13",
                        "sortOrder": "1"
                    },
                    "13": {
                        "name": "Capture Lot Number",
                        "fieldName": "captureLotNumber",
                        "visible": true,
                        "order": "13",
                        "sortOrder": "1"
                    },
                    "14": {
                        "name": "Capture Serial Number",
                        "fieldName": "captureSerialNumber",
                        "visible": true,
                        "order": "14",
                        "sortOrder": "1"
                    },
                    "15": {
                        "name": "Heads Up Messages",
                        "fieldName": "headsUpMessages",
                        "visible": true,
                        "order": "16",
                        "sortOrder": "1"
                    }
                }
            },
            "updateWidget": true,
            "clientId": 101,
            "isMaximized": false
        };
    }));

    //Dependency Injection tests
    describe('Dependency Injection specs', function () {
*/
/*        var $scope;
        var element;
        var $compile;
        var $controller;

        beforeEach(inject(function(_$compile_, _$rootScope_){

            $compile = _$compile_;
            $scope = _$rootScope_.$new();
            $scope.isEnabled = false;
            $scope.name = 'Tom';

        }));

        it('should remove disabled attribute from form controls', function() {
            //HTML template
            var html = '<ui-grid><p ng-bind="name"></p></ui-grid>';

            //compile the directive
            element = $compile(html)($scope);
            $scope.$digest();

            expect(element.find('p').text()).toEqual(1);
        });*//*




        */
/*beforeEach(inject(function (_$controller_, _$compile_) {
            //inject controller
            controller = _$controller_('SearchProductGridController', {
                $scope: scope,
                UtilsService: UtilsService,
                WidgetGridService: WidgetGridService
            });

            $compile = _$compile_;
            html = angular.element('<div><div class="gridStyle" ui-grid="gridOptions" ui-grid-infinite-scroll="" ui-grid-selection="" ui-grid-move-columns="" ui-grid-resize-columns=""></div></div>');
            element = $compile(html)(scope);
        }));

        it('should inject dependencies', (function() {

            //html = '<div ui-grid="gridOptions" ui-grid-infinite-scroll ui-grid-selection ui-grid-move-columns ui-grid-resize-columns>{{name}}</div>';
            //html = '<h1 ng-bind="name"></h1>';

            //element = $compile(html)(scope);
            scope.$digest();

            //expect(element.find('h1')).length().toEqual('Tom');

            expect(controller).toBeDefined();
            expect(scope).toBeDefined();
        }));*//*


        beforeEach(inject(function (_$rootScope_, _$compile_, _$controller_) {
            scope = _$rootScope_.$new();

            //element = angular.element('<div ng-grid="gridOptions" style="width: 1000px; height: 1000px"></div>');
            var element = angular.element('<div ng-controller="SearchProductGridController">' +
                '<div class="gridStyle" ui-grid="gridOptions"></div>' +
                '</div>');

            scope = _$rootScope_;
            scope.widgetdetails = widgetdetails;

            controller = _$controller_('SearchProductGridController', {
                $scope: scope,
                UtilsService: UtilsService,
                WidgetGridService: WidgetGridService
            });

            _$compile_(element)(scope);
            scope.$digest();
        }));

        it('it should inject dependencies', function() {

        });
    });
});
    */
/*

    //Resetting filter function tests
    describe('Resetting filter function specs', function () {
        beforeEach(inject(function(_$controller_) {
            //inject controller
            controller = _$controller_('SearchProductGridController', {
                $scope: scope,
                UtilsService: UtilsService,
                WidgetGridService: WidgetGridService
            });
        }));

        it('should reset the filters', function() {
            //Set mocks to test reset function
            scope.filterCriteria = {"username":"jack123"};
            scope.sortCriteria = {"lastName":"ASC"};

            //setup spies
            spyOn(scope.gridApi.grid, 'resetColumnSorting').andReturn();
            spyOn(WidgetGridService, 'updateColumnSortCriteria').andReturn();
            spyOn(scope, 'clearGrid').andReturn();
            spyOn(scope, 'loadRecords').andReturn();

            //invoke the controller function
            scope.resetFilters();

            expect(scope.filterCriteria).toEqual({});
            expect(scope.sortCriteria).toEqual({});

            expect(scope.gridApi.grid.resetColumnSorting).toHaveBeenCalled();
            expect(WidgetGridService.updateColumnSortCriteria).toHaveBeenCalled();
            expect(scope.clearGrid).toHaveBeenCalled();
            expect(scope.loadRecords).toHaveBeenCalled();
        })
    });

    //Fetching live results function tests
    describe('Fetching live results function specs', function () {
        beforeEach(inject(function(_$controller_) {
            //inject controller
            controller = _$controller_('SearchProductGridController', {
                $scope : scope,
                SearchProductGridService : SearchProductGridService,
                LocalStoreService : LocalStoreService,
                UtilsService : UtilsService
            });
        }));

        it('should reset the filters', function() {
            //setup spies
            spyOn(scope, 'clearGrid').andReturn(null);
            spyOn(scope, 'loadUserRecords').andReturn(null);
            spyOn(scope, 'fetchLiveResults').andCallThrough();

            //reset the filters and test the results
            scope.fetchLiveResults();

            expect(scope.fetchLiveResults).toHaveBeenCalled();
            expect(scope.clearGrid).toHaveBeenCalled();
            expect(scope.loadUserRecords).toHaveBeenCalledWith(false);
        })
    });


    //Clearing grid function tests
    describe('Clearing grid function specs', function() {
        beforeEach(inject(function(_$controller_) {
            //inject controller
            controller = _$controller_('SearchProductGridController', {
                $scope : scope,
                SearchProductGridService : SearchProductGridService,
                LocalStoreService : LocalStoreService,
                UtilsService : UtilsService
            });
        }));

        it('should call clearGrid function and reset the grid', function() {
            //set mocks
            scope.gridOptions.ngGrid = {
                $viewport : {
                    scrollTop : function() {
                        return;
                    }
                }
            };
            scope.gridOptions.selectAll = function() {
                return;
            };

            //setup spies
            spyOn(scope.gridOptions.ngGrid.$viewport, 'scrollTop').andReturn(true);
            spyOn(scope.gridOptions, 'selectAll').andReturn(true);
            spyOn(scope, 'clearGrid').andCallThrough();

            //invoke the function
            scope.clearGrid();

            //inspect the results
            expect(controller.pageNumber).toEqual(0);

            expect(scope.clearGrid).toHaveBeenCalled();
            expect(scope.gridOptions.selectAll).toHaveBeenCalledWith(false);
            expect(scope.gridOptions.ngGrid.$viewport.scrollTop).toHaveBeenCalledWith(0);
        });
    });



    //Loading User Records function tests
    describe('Loading user records function specs', function() {
        var deferred;
        var mockSearchSortJSON = {
            "pageSize": "10",
            "pageNumber": "1",
            "sortMap": {
                "fields": ["productNumber", "description"],
                "directions": ["asc", "desc"],
                "columns": ["productNumber", "description"]
            },
            "searchMap": {
                "productNumber":["99-4939"],
                "description":["Toro Pulley"]
            }
        };
        var mockGridData = {
            "data": [
                {"productNumber": "99-4939"},
                {"productNumber": "98-9305"}
            ]
        };

        var expectedPageNumber = 1;
        var expectedPageSize = 10;
        var expectedSearchCriteria = {
            "productNumber":"99-4939",
            "description":"Toro Pulley"
        };
        var expectedSortCriteria = {
            fields: ["productNumber", "description"],
            directions: ["asc", "desc"],
            columns: ["productNumber", "description"]
        };

        beforeEach(inject(function (_$controller_, _$q_) {
            //setup promise
            deferred = _$q_.defer();
            deferred.resolve(mockGridData);

            //setup spies
            spyOn(LocalStoreService, 'addLSItem').andReturn(null);
            spyOn(UtilsService, 'buildSearchSortJSON').andReturn(mockSearchSortJSON);
            spyOn(SearchProductGridService, 'getProductRecords').andReturn(deferred.promise);

            //inject controller
            controller = _$controller_('SearchProductGridController', {
                $scope: scope,
                SearchProductGridService: SearchProductGridService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });
        }));

        it('should load user records and replace existing grid records', function () {
            //set test specific mocks
            scope.gridOptions.ngGrid = {
                config : {
                    sortInfo: {
                        fields: ["productNumber", "description"],
                        directions: ["asc", "desc"],
                        columns: ["productNumber", "description"]
                    }
                }
            };
            scope.widgetdetails.data = {
                "searchCriteria": {}
            };
            scope.filterInput = {
                "productNumber":"99-4939",
                "description":"Toro Pulley"
            };
            scope.gridRecords = [{
                "productNumber": "99-4939"
            }]; // this will get overwritten if the test is successful
            controller.pageNumber = 1;
            controller.pageSize = 10;

            //invoke the function
            scope.loadUserRecords(false);
            scope.$digest();

            expect(LocalStoreService.addLSItem).toHaveBeenCalled();
            expect(UtilsService.buildSearchSortJSON).toHaveBeenCalledWith(expectedPageNumber, expectedPageSize, expectedSearchCriteria, expectedSortCriteria);
            expect(SearchProductGridService.getProductRecords).toHaveBeenCalled();

            expect(scope.widgetdetails.data.searchCriteria.pageNumber).toEqual(mockSearchSortJSON.pageNumber);
            expect(scope.widgetdetails.data.searchCriteria.pageSize).toEqual(mockSearchSortJSON.pageSize);
            expect(scope.widgetdetails.data.searchCriteria.sortMap).toEqual(mockSearchSortJSON.sortMap);
            expect(scope.widgetdetails.data.searchCriteria.searchMap).toEqual(mockSearchSortJSON.searchMap);
            expect(scope.gridRecords[0]).toEqual({"productNumber": "99-4939"});
            expect(scope.gridRecords[1]).toEqual({"productNumber": "98-9305"});

            expect(controller.pageNumber).toEqual(2);
        });

        //grid selection tests
        describe('Grid row selection and broadcast specs', function() {
            var rootScope;

            beforeEach(inject(function(_$controller_, _$rootScope_) {
                rootScope = _$rootScope_.$new();

                //inject controller
                controller = _$controller_('SearchProductGridController', {
                    $scope: scope,
                    $rootScope : rootScope,
                    SearchProductGridService: SearchProductGridService,
                    LocalStoreService: LocalStoreService,
                    UtilsService: UtilsService
                });
            }));

            it('should broadcast information for selected rows', function() {
                var mockArgs = [1, 2, 3];
                //set spies
                spyOn(scope.gridOptions, 'afterSelectionChange').andCallThrough();
                spyOn(UtilsService, 'processSelectedItemsAsArray').andReturn(mockArgs);
                spyOn(scope, '$broadcast').andCallThrough();

                //invoke function;
                scope.gridOptions.afterSelectionChange();
                scope.$digest();

                expect(scope.gridOptions.afterSelectionChange).toHaveBeenCalled();
                expect(UtilsService.processSelectedItemsAsArray).toHaveBeenCalled();
                expect(scope.$broadcast).toHaveBeenCalledWith('fromcontroller', mockArgs);
            });
        });


        //Scope event and broadcast listener tests
        describe('ngGrid event listener specs', function() {
            beforeEach(inject(function(_$controller_) {
                controller = _$controller_('SearchProductGridController', {
                    $scope : scope,
                    SearchProductGridService : SearchProductGridService,
                    WidgetGridService : WidgetGridService,
                    UtilsService : UtilsService
                });
            }));

            it('should fire when ngGridEventColumns event is broadcast', function() {
                //var mockArgs = [1, 2, 3];
                var mockArgs = {
                    "visibleColumns" : [1, 2, 3]
                };
                var mockWidgetInstance = {"mock":"data"};

                spyOn(WidgetGridService, 'handleGridEventColumns').andReturn(mockArgs);

                scope.$digest();
                scope.widgetdetails = mockWidgetInstance;
                scope.$broadcast('ngGridEventColumns', mockArgs);

                expect(WidgetGridService.handleGridEventColumns).toHaveBeenCalledWith(mockArgs, mockWidgetInstance);
                expect(scope.gridColumns).toEqual(mockArgs.visibleColumns);
            });

            it('should fire when ngGridEventScroll event is broadcast', function() {
                spyOn(scope, 'loadUserRecords').andReturn(null);
                scope.$digest();
                scope.$broadcast('ngGridEventScroll');

                expect(scope.loadUserRecords).toHaveBeenCalledWith(true);
            });

            it('should fire when gridUpdateOnCogMenu event is broadcast', function() {
                var mockArgs = [1, 2, 3];

                scope.$digest();
                scope.$broadcast('gridUpdatedonCogMenu', mockArgs);

                expect(scope.myDefs).toEqual(mockArgs);
            });

            it('should load new grid records when watcher detects a change to sortInfo', function() {
                spyOn(scope, 'loadUserRecords').andReturn();
                spyOn(scope, 'clearGrid').andReturn();

                //set isInitialising vairable to false so the watcher executes,
                //then trigger the watcher by updating the property it's watching
                controller.isInitialising = false;
                scope.gridOptions.ngGrid = {
                    config : {
                        sortInfo: {
                            fields: ["productNumber", "description"],
                            directions: ["asc", "desc"],
                            columns: ["productNumber", "description"]
                        }
                    }
                };
                scope.$digest();

                //assert results
                expect(controller.isInitialising).toBeFalsy();
                expect(scope.clearGrid).toHaveBeenCalled();
                expect(scope.loadUserRecords).toHaveBeenCalledWith(false);
            });
        });

        //WidgetInstance properties set from LocalStorage
        describe('widgetInstance properties set correctly from LocalStorage', function() {
            it('should set \'isMaximised\' property to false', inject(function(_$controller_) {
                //set mocks and spy on LocalStorage function
                scope.widgetdetails.isMaximized = false;
                spyOn(LocalStoreService, 'getLSItem').andReturn(scope.widgetdetails);

                controller = _$controller_('SearchProductGridController', {
                    $scope : scope,
                    SearchProductGridService : SearchProductGridService,
                    WidgetGridService : WidgetGridService,
                    UtilsService : UtilsService,
                    LocalStoreService : LocalStoreService
                });

                expect(scope.widgetdetails.isMaximized).toBeFalsy()
            }));

            it('should set \'isMaximised\' property to true', inject(function(_$controller_) {
                //set mocks and spy on LocalStorage function
                scope.widgetdetails.isMaximized = true;
                spyOn(LocalStoreService, 'getLSItem').andReturn(scope.widgetdetails);

                controller = _$controller_('SearchProductGridController', {
                    $scope : scope,
                    SearchProductGridService : SearchProductGridService,
                    WidgetGridService : WidgetGridService,
                    UtilsService : UtilsService,
                    LocalStoreService : LocalStoreService
                });

                expect(scope.widgetdetails.isMaximized).toBeTruthy()
            }));
        });

        //Clearing grid function tests
        describe('Setting Grid Height Specs', function() {
            var mockViewDimensions = {
                "height" : 1000
            };

            beforeEach(inject(function(_$controller_) {
                //inject controller
                controller = _$controller_('SearchProductGridController', {
                    $scope : scope,
                    SearchProductGridService : SearchProductGridService,
                    LocalStoreService : LocalStoreService,
                    UtilsService : UtilsService
                });
            }));

            it('should set grid height from LocalStorage', function() {
                //set spy to return mock
                spyOn(LocalStoreService, 'getLSItem').andReturn(mockViewDimensions);

                //invoke function
                controller.setGridHeight();

                //assert results
                //function is expected to decrement 160
                expect(controller.gridHeight).toEqual(275);
            });

            it('should set grid height from widgetInstance', function() {
                //set spy to return mock
                spyOn(LocalStoreService, 'getLSItem').andReturn(null);
                scope.widgetdetails.actualViewConfig = mockViewDimensions;

                //invoke function
                controller.setGridHeight();

                //assert results
                //function is expected to decrement 140
                expect(controller.gridHeight).toEqual(900);
            });
        });
    });
});
*/

