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


/*
'use strict';

describe('Search User Grid Controller Specs', function() {

    //Global vars
    var scope;
    var controller;
    var SearchUserGridService;
    var LocalStoreService;
    var UtilsService;
    var WidgetGridService;
    var modal;


 //Mock translateProvide
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
    
    //Global test setup
    beforeEach(inject(function(_$rootScope_, _SearchUserGridService_, _LocalStoreService_, _UtilsService_, _WidgetGridService_, _$modal_) {
        //assign locals
        scope = _$rootScope_.$new();
        SearchUserGridService = _SearchUserGridService_;
        LocalStoreService = _LocalStoreService_;
        UtilsService = _UtilsService_;
        WidgetGridService = _WidgetGridService_;
        modal = _$modal_;

        //Skeleton mock of widgetDetails
        //Each test will add it's required properties to this
        scope.widgetdetails = {
            "data" : {
                "grid": {
                  "1": {
                    "values": ["Admin", "Jill", "Jack"]
                  },
                  "2": {
                    "values": ["User", "User1", "User2"]
                  },
                  "3": {
                    "values": ["admin123", "jill123", "jack123"]
                  }
                }
            },
            "clientId":"100",
            "isMaximised" : false,
            "widgetDefinition": {
                "id": 5,
                "name": "search-user-grid-widget",
                "dataURL":{
                    "url": "/users/userlist/search",
                    "searchCriteria": {
                        "searchMap": {},
                        "sortMap": {},
                        "pageSize": "10",
                        "pageNumber": "0"
                    }
                },
                "broadcastList":["userName","firstName"],
                "listensForList": ["userName","firstName"],
                "defaultViewConfig": {
                    "height": 375,
                    "width": 485,
                    "dateFormat": {
                      "selectedFormat": "mm-dd-yyyy",
                      "availableFormats": [
                        "mm-dd-yyyy",
                        "MMM dd, yyyy",
                        "dd-mm-yyyy"
                      ]
                    },
                    "anchor": [
                      1,
                      363
                    ],
                    "zindex": 1,
                    "gridColumns": {
                      "1": {
                        "name": "First Name",
                        "fieldName": "firstName",
                        "visible": false,
                        "order": "1",
                        "sortOrder": "1"
                      },
                      "2": {
                        "name": "Last Name",
                        "fieldName": "lastName",
                        "visible": false,
                        "order": "2",
                        "sortOrder": "1"
                      },
                      "3": {
                        "name": "User Name",
                        "fieldName": "userName",
                        "visible": false,
                        "order": "3",
                        "sortOrder": "1"
                      }
                    }
              }
            },
            "actualViewConfig": {
                "height": 375,
                "dateFormat": {
                "selectedFormat": "mm-dd-yyyy",
                "availableFormats": [
                  "mm-dd-yyyy",
                  "MMM dd, yyyy",
                  "dd-mm-yyyy"
                ]
              },
            },
            "nggridSpecific": {
              "sortInfo": {
                "fields": [
                  "firstName",
                  "lastName",
                  "userName"
                ],
                "directions": [
                  "asc",
                  "asc",
                  "asc"
                ]
              },
              "colDefs": [
                {
                  "field": "firstName",
                  "displayName": "First Name",
                  "visible": true
                },
                {
                  "field": "userName",
                  "displayName": "User Name",
                  "visible": true
                }
              ]
            }
        };
    }));


    //Dependency Injection tests
    describe('Dependency Injection specs', function() {
        beforeEach(inject(function(_$controller_) {
            //set spies on dependencies
            //always return null - we are only testing dependency injection
            spyOn(LocalStoreService, 'getLSItem').andReturn(null);

            //inject controller
            controller = _$controller_('SearchUserGridController', {
                $scope : scope,
                SearchUserGridService : SearchUserGridService,
                LocalStoreService : LocalStoreService,
                UtilsService : UtilsService
            });
        }));

        it('should inject $scope', function() {
            expect(scope).toBeDefined();
            expect(LocalStoreService.getLSItem).toHaveBeenCalled();
        });
        
        it('should inject $controller', function() {
            expect(controller).toBeDefined();
            expect(LocalStoreService.getLSItem).toHaveBeenCalled();
        });
    });


    //Grid characteristics instantiated from widgetDetails
    describe('Grid characteristics instantiated from widgetInstance', function() {

        var expectedSortOptions;
        var expectedColumnDefs;
        var expectedGridData;

        beforeEach(function() {            
            */
        /*var sortOptions = {
                "fields": ["firstName", "lastName", "userName"],
                "directions": ["asc", "asc", "asc"]
           };

            //define expectations to test against
            expectedSortOptions = sortOptions;*//*

            
            expectedSortOptions = scope.widgetdetails.nggridSpecific.sortInfo;
            expectedColumnDefs = scope.widgetdetails.nggridSpecific.colDefs;
            expectedGridData = scope.widgetdetails.data.data;


            //set spies on dependencies
            spyOn(LocalStoreService, 'getLSItem').andReturn(null);                        
        });

        it('should load grid characteristics from widgetInstance', inject(function(_$controller_) {
            //inject controller
            controller = _$controller_('SearchUserGridController', {
                $scope : scope,
                SearchUserGridService : SearchUserGridService,
                LocalStoreService : LocalStoreService,
                UtilsService : UtilsService
            });

            //get actual results from controller
            var actualSortOptions = scope.gridOptions.sortInfo;            
            expect(actualSortOptions).toEqual(expectedSortOptions);
        }));
    });


    //Resetting filter function tests
    describe('Resetting filter function specs', function () {
        beforeEach(inject(function(_$controller_) {
            //inject controller
            controller = _$controller_('SearchUserGridController', {
                $scope : scope,
                SearchUserGridService : SearchUserGridService,
                LocalStoreService : LocalStoreService,
                UtilsService : UtilsService
            });
        }));

        it('should reset the filters', function() {
            //Set mocks to test resetFilters function
            scope.filterInput = {"dummy":"data"};
            scope.sortOptions = {"some":"object"};
            scope.gridOptions.filterOptions.filterText = 'some text';
            scope.gridOptions.ngGrid = {
                config : {
                    sortInfo: {
                        fields: ["firstName", "lastName"],
                        directions: ["asc", "desc"],
                        columns: ["firstName", "lastName"]
                    }
                }
            };
            scope.gridOptions.ngGrid.lastSortedColumns = ["lastName"];

            //setup spies
            spyOn(scope, 'clearGrid').andReturn(null);
            spyOn(scope, 'loadUserRecords').andReturn(null);
            spyOn(scope, 'resetFilters').andCallThrough();

            //reset the filters and test the results
            scope.resetFilters();

            expect(scope.filterInput).toEqual({});
            expect(scope.gridOptions.filterOptions.filterText).toEqual('');
            expect(scope.gridOptions.ngGrid.config.sortInfo).toEqual({
                fields:[],
                directions: [],
                columns:[]
            });
            expect(scope.gridOptions.ngGrid.lastSortedColumns).toEqual([]);

            expect(scope.resetFilters).toHaveBeenCalled();
            expect(scope.clearGrid).toHaveBeenCalled();
            expect(scope.loadUserRecords).toHaveBeenCalledWith(false);
        })
    });


    //Fetching live results function tests
    describe('Fetching live results function specs', function () {
        beforeEach(inject(function(_$controller_) {
            //inject controller
            controller = _$controller_('SearchUserGridController', {
                $scope : scope,
                SearchUserGridService : SearchUserGridService,
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
            controller = _$controller_('SearchUserGridController', {
                $scope : scope,
                SearchUserGridService : SearchUserGridService,
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
                "fields": ["firstName", "lastName"],
                "directions": ["asc", "desc"],
                "columns": ["firstName", "lastName"]
            },
            "searchMap": {
                "firstName":["Jill"],
                "lastName":["Smith"]
            }
        };
        var mockGridData = {
            "data": [
                {"firstName": "Jack"},
                {"firstName": "Jill"}
            ]
        };

        var expectedPageNumber = 1;
        var expectedPageSize = 10;
        var expectedSearchCriteria = {
            "firstName":"Jill",
            "lastName":"Smith"
        };
        var expectedSortCriteria = {
            fields: ["firstName", "lastName"],
            directions: ["asc", "desc"],
            columns: ["firstName", "lastName"]
        };

        beforeEach(inject(function (_$controller_, _$q_) {
            //setup promise
            deferred = _$q_.defer();
            deferred.resolve(mockGridData);

            //setup spies
            spyOn(LocalStoreService, 'addLSItem').andReturn(null);
            spyOn(UtilsService, 'buildSearchSortJSON').andReturn(mockSearchSortJSON);
            spyOn(SearchUserGridService, 'getUserRecords').andReturn(deferred.promise);

            //inject controller
            controller = _$controller_('SearchUserGridController', {
                $scope: scope,
                SearchUserGridService: SearchUserGridService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });
        }));

        it('should load user records and replace existing grid records', function () {
            //set test specific mocks
            scope.gridOptions.ngGrid = {
                config : {
                    sortInfo: {
                        fields: ["firstName", "lastName"],
                        directions: ["asc", "desc"],
                        columns: ["firstName", "lastName"]
                    }
                }
            };
            scope.widgetdetails.data = {
                "searchCriteria": {}
            };
            scope.filterInput = {
                "firstName":"Jill",
                "lastName":"Smith"
            };
            scope.gridRecords = [{
                "firstName": "Bob"
            }]; // this will get overwritten if the test is successful
            controller.pageNumber = 1;
            controller.pageSize = 10;

            //invoke the function
            scope.loadUserRecords(false);
            scope.$digest();

            expect(LocalStoreService.addLSItem).toHaveBeenCalled();
            expect(UtilsService.buildSearchSortJSON).toHaveBeenCalledWith(expectedPageNumber, expectedPageSize, expectedSearchCriteria, expectedSortCriteria);
            expect(SearchUserGridService.getUserRecords).toHaveBeenCalled();

            expect(scope.widgetdetails.data.searchCriteria.pageNumber).toEqual(mockSearchSortJSON.pageNumber);
            expect(scope.widgetdetails.data.searchCriteria.pageSize).toEqual(mockSearchSortJSON.pageSize);
            expect(scope.widgetdetails.data.searchCriteria.sortMap).toEqual(mockSearchSortJSON.sortMap);
            expect(scope.widgetdetails.data.searchCriteria.searchMap).toEqual(mockSearchSortJSON.searchMap);
            expect(scope.gridRecords[0]).toEqual({"firstName": "Jack"});
            expect(scope.gridRecords[1]).toEqual({"firstName": "Jill"});

            expect(controller.pageNumber).toEqual(2);
        });

        it('should load user records and add to existing grid records', function () {
            //set test specific mocks
            scope.gridOptions.ngGrid = {
                config : {
                    sortInfo: {
                        fields: ["firstName", "lastName"],
                        directions: ["asc", "desc"],
                        columns: ["firstName", "lastName"]
                    }
                }
            };
            scope.widgetdetails.data = {
                "searchCriteria": {}
            };
            scope.filterInput = {
                "firstName":"Jill",
                "lastName":"Smith"
            };
            scope.gridRecords = [{
                "firstName": "Bob"
            }]; // this will get appended to if the test is successful (expected)
            controller.pageNumber = 1;
            controller.pageSize = 10;

            //invoke the function
            scope.loadUserRecords(true);
            scope.$digest();

            expect(LocalStoreService.addLSItem).toHaveBeenCalled();
            expect(UtilsService.buildSearchSortJSON).toHaveBeenCalledWith(expectedPageNumber, expectedPageSize, expectedSearchCriteria, expectedSortCriteria);
            expect(SearchUserGridService.getUserRecords).toHaveBeenCalled();

            expect(scope.widgetdetails.data.searchCriteria.pageNumber).toEqual(mockSearchSortJSON.pageNumber);
            expect(scope.widgetdetails.data.searchCriteria.pageSize).toEqual(mockSearchSortJSON.pageSize);
            expect(scope.widgetdetails.data.searchCriteria.sortMap).toEqual(mockSearchSortJSON.sortMap);
            expect(scope.widgetdetails.data.searchCriteria.searchMap).toEqual(mockSearchSortJSON.searchMap);

            expect(scope.gridRecords[0]).toEqual({"firstName": "Bob"});
            expect(scope.gridRecords[1]).toEqual({"firstName": "Jack"});
            expect(scope.gridRecords[2]).toEqual({"firstName": "Jill"});

            expect(controller.pageNumber).toEqual(2);
        });
    });


    //grid selection tests
    describe('Grid row selection and broadcast specs', function() {
        var rootScope;

        beforeEach(inject(function(_$controller_, _$rootScope_) {
            rootScope = _$rootScope_.$new();

            //inject controller
            controller = _$controller_('SearchUserGridController', {
                $scope: scope,
                $rootScope : rootScope,
                SearchUserGridService: SearchUserGridService,
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
            controller = _$controller_('SearchUserGridController', {
                $scope : scope,
                SearchUserGridService : SearchUserGridService,
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
                        fields: ["firstName", "lastName"],
                        directions: ["asc", "desc"],
                        columns: ["firstName", "lastName"]
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

            controller = _$controller_('SearchUserGridController', {
                $scope : scope,
                SearchUserGridService : SearchUserGridService,
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

            controller = _$controller_('SearchUserGridController', {
                $scope : scope,
                SearchUserGridService : SearchUserGridService,
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
            controller = _$controller_('SearchUserGridController', {
                $scope : scope,
                SearchUserGridService : SearchUserGridService,
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

    //Enabling/disabling Actions button based on number of selected grid rows
    describe('Enable/Disable Actions button based on selected grid records', function() {
        beforeEach(inject(function(_$controller_) {
            controller = _$controller_('SearchUserGridController', {
                $scope : scope,
                SearchUserGridService : SearchUserGridService,
                WidgetGridService : WidgetGridService,
                UtilsService : UtilsService,
                LocalStoreService : LocalStoreService
            });
        }));

        it('should disable the Actions button when no records are selected', function() {
            //no rows selected
            scope.selectedRows = [];

            //invoke the function being tested and assert results
            scope.gridOptions.afterSelectionChange();
            expect(scope.actionsEnabled).toBe(false);
        });

        it('should enable the Actions button when one or more records are selected', function() {
            //one or more rows selected
            scope.selectedRows = [{"userName":"joe123", "firstName":"Joe", "lastName":"blogs"}];
            
            //set spies
            spyOn(scope.gridOptions, 'afterSelectionChange').andCallThrough();
            spyOn(UtilsService, 'processSelectedItemsAsArray').andReturn(scope.selectedRows);
            spyOn(scope, '$broadcast').andCallThrough();

            //invoke the function being tested and assert results
            scope.gridOptions.afterSelectionChange();
            scope.$digest();
            expect(scope.actionsEnabled).toBe(true);
        });
    });

    //Action Pallet Specs
    describe('Action Pallet specs', function(){
        beforeEach(inject(function(_$controller_) {

            spyOn(scope, '$on').andCallThrough();

            controller = _$controller_('SearchUserGridController', {
                $scope : scope,
                SearchUserGridService : SearchUserGridService,
                WidgetGridService : WidgetGridService,
                UtilsService : UtilsService,
                LocalStoreService : LocalStoreService,
                $modal : modal
            });
        }));

        it('should open the Action Pallet Modal', function() {
            spyOn(modal, 'open').andReturn();

            //invoke the function and assert the result
            scope.showActionPallet();
            expect(modal.open).toHaveBeenCalled();
        });

        it('should perform the \'delete-user\' Action Pallet action', function() {
            //broadcast the event for 'delete-user'
            scope.$broadcast('ProcessPalletAction', 'delete-user');

            //this test *should* be extended when the underlying
            //logic for 'delete-user' action is implemented. At the moment
            //there is nothing explicit to assert
            expect(scope.$on).toHaveBeenCalled();
        });

        it('should perform the \'disable-user\' Action Pallet action', function() {
            //broadcast the event for 'disable-user'
            scope.$broadcast('ProcessPalletAction', 'disable-user');

            //this test *should* be extended when the underlying
            //logic for 'disable-user' action is implemented. At the moment
            //there is nothing explicit to assert
            expect(scope.$on).toHaveBeenCalled();
        });
    });

    //Modal Action Confirmation Specs
    describe('Modal Action Confirmation specs', function(){
        beforeEach(inject(function(_$controller_) {

            spyOn(scope, '$on').andCallThrough();

            controller = _$controller_('SearchUserGridController', {
                $scope : scope,
                SearchUserGridService : SearchUserGridService,
                WidgetGridService : WidgetGridService,
                UtilsService : UtilsService,
                LocalStoreService : LocalStoreService,
                $modal : modal
            });
        }));

        it('should open the Modal Action Confirmation', function() {
            spyOn(modal, 'open').andReturn();

            //invoke the function and assert the result
            scope.showActionConfirmation();
            expect(modal.open).toHaveBeenCalled();
        });

        it('should perform the \'delete-user\' Action Confirm', function() {
            //broadcast the event for 'delete-user'
            scope.$broadcast('ActionConfirmed', 'delete-user');
            expect(scope.$on).toHaveBeenCalled();

        });

        it('should perform the \'disable-user\' Action Pallet action', function() {
            //broadcast the event for 'disable-user'
            scope.$broadcast('ActionConfirmed', 'disable-user');

            expect(scope.$on).toHaveBeenCalled();
        });
    });
});*/

'use strict';

describe('Search User Grid Controller Specs', function() {

    //Global vars
    var scope;
    var controller;
    var SearchUserGridService;
    var LocalStoreService;
    var UtilsService;
    var WidgetGridService;
    var modal;
    var log;
    var compile;
    var WidgetService;
    var uiGridConstants;
    var timeout;
    var filter;
    var SearchUserGridController;
    var modalInstance;
    var mockModalInstance;

    var mockUserConfigData = {
        "userName": "jack123",
        "userId": 100,
        "userPermissions": ["manage-canvas", "clone-canvas", "delete-user", "disable-user"],
        "homeCanvas": {
            "name": "Work Execution",
            "canvasId": "221",
            "canvasType": "COMPANY",
            "displayOrder": "2"
        },
        "activeCanvas": {
            "name": "Work Execution",
            "canvasId": "221",
            "canvasType": "COMPANY",
            "displayOrder": "2"
        },
        "seeHomeCanvasIndicator": true,
        "openCanvases": [{
            "name": "ProductManagement",
            "canvasId": "201",
            "canvasType": "COMPANY",
            "displayOrder": "1"
        }, {
            "name": "Work Execution",
            "canvasId": "221",
            "canvasType": "COMPANY",
            "displayOrder": "2"
        }, {
            "name": "AssignmentManagement",
            "canvasId": "231",
            "canvasType": "COMPANY",
            "displayOrder": "3"
        }, {
            "name": "GroupManagement",
            "canvasId": "235",
            "canvasType": "COMPANY",
            "displayOrder": "4"
        }],
        "menuPermissions": {"manage-canvas": "Manage Canvas", "clone-canvas": "Clone Canvas"}
    };

    //Mock translateProvide
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

    //Global test setup
    beforeEach(inject(function(_$controller_, _$rootScope_, _SearchUserGridService_, _LocalStoreService_, _UtilsService_, _WidgetGridService_, _$modal_, _$log_, _$compile_, _WidgetService_, _uiGridConstants_, _$timeout_, _$filter_) {
        //assign locals
        scope = _$rootScope_.$new();
        SearchUserGridController = _$controller_;
        SearchUserGridService = _SearchUserGridService_;
        LocalStoreService = _LocalStoreService_;
        UtilsService = _UtilsService_;
        WidgetGridService = _WidgetGridService_;
        modal = _$modal_;
        log = _$log_;
        compile=_$compile_;
        WidgetService=_WidgetService_;
        uiGridConstants=_uiGridConstants_;
        timeout=_$timeout_;
        filter=_$filter_;

        LocalStoreService.addLSItem(null, 'UserConfig', mockUserConfigData);

        mockModalInstance= {
            open: function()
            {
                return {
                    result : {
                        then: function(callback) {
                            callback("item1");
                        }
                    }
                };
            }
        };

        //Skeleton mock of widgetDetails
        //Each test will add it's required properties to this
        scope.widgetdetails = {
            "data" : {
                "grid": {
                    "1": {
                        "values": ["Admin", "Jill", "Jack"]
                    },
                    "2": {
                        "values": ["User", "User1", "User2"]
                    },
                    "3": {
                        "values": ["admin123", "jill123", "jack123"]
                    }
                }
            },
            "clientId":"100",
            "isMaximised" : false,
            "widgetDefinition": {
                "id": 5,
                "name": "search-user-grid-widget",
                "dataURL":{
                    "url": "/users/userlist/search",
                    "searchCriteria": {
                        "searchMap": {},
                        "sortMap": {},
                        "pageSize": "10",
                        "pageNumber": "0"
                    }
                },
                "broadcastList":["userName","firstName"],
                "listensForList": ["userName","firstName"],
                "defaultViewConfig": {
                    "height": 375,
                    "width": 485,
                    "dateFormat": {
                        "selectedFormat": "mm-dd-yyyy",
                        "availableFormats": [
                            "mm-dd-yyyy",
                            "MMM dd, yyyy",
                            "dd-mm-yyyy"
                        ]
                    },
                    "anchor": [
                        1,
                        363
                    ],
                    "zindex": 1,
                    "gridColumns": {
                        "1": {
                            "name": "First Name",
                            "fieldName": "firstName",
                            "visible": false,
                            "order": "1",
                            "sortOrder": "1"
                        },
                        "2": {
                            "name": "Last Name",
                            "fieldName": "lastName",
                            "visible": false,
                            "order": "2",
                            "sortOrder": "1"
                        },
                        "3": {
                            "name": "User Name",
                            "fieldName": "userName",
                            "visible": false,
                            "order": "3",
                            "sortOrder": "1"
                        }
                    }
                }
            },
            "actualViewConfig": {
                "height": 375,
                "dateFormat": {
                    "selectedFormat": "mm-dd-yyyy",
                    "availableFormats": [
                        "mm-dd-yyyy",
                        "MMM dd, yyyy",
                        "dd-mm-yyyy"
                    ]
                },
                "autoRefreshConfig":{
                    "globalOverride":true,
                    "enabled":true,
                    "interval":1
                }
            },
            "nggridSpecific": {
                "sortInfo": {
                    "fields": [
                        "firstName",
                        "lastName",
                        "userName"
                    ],
                    "directions": [
                        "asc",
                        "asc",
                        "asc"
                    ]
                },
                "colDefs": [
                    {
                        "field": "firstName",
                        "displayName": "First Name",
                        "visible": true
                    },
                    {
                        "field": "userName",
                        "displayName": "User Name",
                        "visible": true
                    }
                ]
            }
        };

        scope.widgetdetails.widgetDefinition.dataURL.searchCriteria.sortMap = {
            "shift": "ASC"
        };

        scope.widgetdetails.widgetDefinition.dataURL.searchCriteria.searchMap = {
            "firstName": ["admin"]
        };

        spyOn(WidgetGridService, 'getColumnDefinitions').andReturn(
            [{
                "field": "userName",
                "displayName": "Lucas Login",
                "visible": true,
                "sort": {
                    "direction": "asc"
                },
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null
                }
            }, {
                "field": "wmsUser",
                "displayName": "Host Login",
                "visible": false,
                "sort": {
                    "direction": "asc"
                },
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null
                }
            }, {
                "field": "firstName",
                "displayName": "First Name",
                "visible": true,
                "sort": {
                    "direction": "asc"
                },
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null
                }
            }, {
                "field": "lastName",
                "displayName": "Last Name",
                "visible": true,
                "sort": {
                    "direction": "asc"
                },
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null
                }
            }, {
                "field": "skill",
                "displayName": "Skill",
                "visible": true,
                "sort": {
                    "direction": "asc"
                },
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null
                }
            }, {
                "field": "shift",
                "displayName": "Shifts",
                "visible": true,
                "sort": {
                    "direction": "asc"
                },
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null
                }
            }, {
                "field": "j2uLanguage",
                "displayName": "J2U Language",
                "visible": true,
                "sort": {
                    "direction": "asc"
                },
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null
                }
            }, {
                "field": "u2jLanguage",
                "displayName": "U2J Language",
                "visible": true,
                "sort": {
                    "direction": "asc"
                },
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null
                }
            }, {
                "field": "hhLanguage",
                "displayName": "HH Language",
                "visible": true,
                "sort": {
                    "direction": "asc"
                },
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null
                }
            }, {
                "field": "amdLanguage",
                "displayName": "AMD Language",
                "visible": true,
                "sort": {
                    "direction": "asc"
                },
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null
                }
            }, {
                "field": "enable",
                "displayName": "Status",
                "visible": true,
                "sort": {
                    "direction": "asc"
                },
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null
                }
            }, {
                "field": "employeeId",
                "displayName": "Employee Id",
                "visible": false,
                "sort": {
                    "direction": "asc"
                },
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null
                }
            }, {
                "field": "title",
                "displayName": "Title",
                "visible": false,
                "sort": {
                    "direction": "asc"
                },
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null
                }
            }, {
                "field": "startDate",
                "cellFilter": "date:'mm-dd-yyyy'",
                "displayName": "Start Date",
                "visible": true,
                "sort": {
                    "direction": "asc"
                },
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null
                }
            }, {
                "field": "birthDate",
                "cellFilter": "date:'mm-dd-yyyy'",
                "displayName": "Birth Date",
                "visible": false,
                "sort": {
                    "direction": "asc"
                },
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null
                }
            }, {
                "field": "mobileNumber",
                "displayName": "Mobile Number",
                "visible": false,
                "sort": {
                    "direction": "asc"
                },
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null
                }
            }, {
                "field": "emailAddress",
                "displayName": "Email Address",
                "visible": true,
                "sort": {
                    "direction": "asc"
                },
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null
                }
            }]
        );

        controller = _$controller_('SearchUserGridController', {
            $scope : scope,
            SearchUserGridService:SearchUserGridService,
            LocalStoreService:LocalStoreService,
            UtilsService:UtilsService,
            WidgetGridService:WidgetGridService,
            modal:modal,
            log:log,
            compile:compile,
            WidgetService:WidgetService,
            uiGridConstants:uiGridConstants,
            timeout:timeout,
            filter:filter
        });
    }));

    it('Should Check Sort and Search Criteria', function() {
        // test filterCriteria
        expect(scope.filterCriteria).toEqual({ firstName : 'admin' } );
        // test sortCriteria
        expect(scope.sortCriteria).toEqual({ shift : 'ASC' } );
    });

    // Tests for Delete buttons

    it('should show the Delete button when user has permission', function() {
        expect(scope.showDeleteBtn).toBeDefined();
    });

    it('should disable the Delete button when no records are selected', function() {
        //no rows selected
        scope.selectedUsers = [];
        expect(scope.enableDeleteBtn).toBeDefined();
        expect(scope.enableDeleteBtn()).toBe(true);
    });

    it('should enable the Delete button when one or more records are selected', function() {
        //one or more rows selected
        scope.selectedUsers = ["joe123", "Joe", "blogs"];
        expect(scope.enableDeleteBtn()).toBe(false);
    });

    // Tests for Disable buttons 

    it('should show the Disable button when user has permission', function() {
        expect(scope.showDisableBtn).toBeDefined();
    });


    it('should disable the Disable button when no records are selected', function() {
        //no rows selected
        scope.selectedUsers = [];
        expect(scope.enableDisableBtn).toBeDefined();
        expect(scope.enableDisableBtn()).toBe(true);
    });

    it('should enable the Disable button when one or more records are selected', function() {
        //one or more rows selected
        scope.selectedUsers = ["joe123", "Joe", "blogs"];
        expect(scope.enableDisableBtn()).toBe(false);
    });

    // Tests for Enable buttons 

    it('should show the Enable button when user has permission', function() {
        expect(scope.showEnableBtn).toBeDefined();
    });

    it('should disable the Enable button when no records are selected', function() {
        //no rows selected
        scope.selectedUsers = [];
        expect(scope.enableEnableBtn).toBeDefined();
        expect(scope.enableEnableBtn()).toBe(true);
    });

    it('should enable the Retrain Voice Log button when one or more records are selected', function() {
        //one or more rows selected
        scope.selectedUsers = ["joe123", "Joe", "blogs"];
        expect(scope.enableEnableBtn()).toBe(false);
    });

    // Tests for Retrain Voice Log buttons 

    it('should show the Retrain Voice Log button when user has permission', function() {
        expect(scope.showVoiceLogBtn).toBeDefined();
    });

    it('should disable the Retrain Voice Log button when no records are selected', function() {
        //no rows selected
        scope.selectedUsers = [];
        expect(scope.enableVoiceLogBtn).toBeDefined();
        expect(scope.enableVoiceLogBtn()).toBe(true);
    });

    it('should enable the Retrain Voice Log button when one or more records are selected', function() {
        //one or more rows selected
        scope.selectedUsers = ["joe123", "Joe", "blogs"];
        expect(scope.enableVoiceLogBtn()).toBe(false);
    });

    // Tests for Manual Refresh button 

    it('should call loadRecords function to refresh grid data', function() {
        spyOn(scope, 'loadRecords').andReturn(null);
        spyOn(scope, 'clearGrid').andReturn(null);

        expect(scope.searchUserGridManualRefresh).toBeDefined();
        scope.searchUserGridManualRefresh();
        expect(scope.clearGrid).toHaveBeenCalled();
        expect(scope.loadRecords).toHaveBeenCalled();
    });

      // Tests for Manual Refresh button 

    it('should clear the search filters and call loadRecords', function() {
        spyOn(scope, 'loadRecords').andReturn(null);
        spyOn(scope, 'clearGrid').andReturn(null);

        expect(scope.unfilterSearchUsers).toBeDefined();
        scope.unfilterSearchUsers();
        expect(scope.selectedRows).toEqual([]);
        expect(scope.selectedUsers).toEqual([]);
        expect(scope.activeUsers).toEqual([]);
        expect(scope.disabledUsers).toEqual([]);
        expect(controller.pageNumber).toEqual(0);
        expect(scope.filterCriteria).toEqual({});
        expect(scope.clearGrid).toHaveBeenCalled();
        expect(scope.loadRecords).toHaveBeenCalled();
    });
})