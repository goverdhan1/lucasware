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

describe('WidgetGridService Unit Tests', function() {

    var WidgetGridService;
    var UtilsService;
    var LocalStoreService;
    var deferred;

    //global test setup
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

    //inject the service globally, so we don't have to do it for each test
    beforeEach(inject(function (_WidgetGridService_, _LocalStoreService_, _UtilsService_) {
        WidgetGridService = _WidgetGridService_;
        LocalStoreService = _LocalStoreService_;
        UtilsService=_UtilsService_;
    }));


    //Dependency Injection tests
    describe('Dependency Injection Specs', function () {

        it('WidgetGridService to be defined', function () {

            expect(WidgetGridService).toBeDefined();
        });
    });


    //Get records from server
    describe('Function: getRecords()', function () {

        var scope;
        var RestApiService;
        var widgetInstance;
        var sortCriteria;
        var filterCriteria;
        var pageNumber;
        var pageSize;

        //inject dependencies this function uses
        beforeEach(inject(function (_$rootScope_, _RestApiService_, _$q_) {

            RestApiService = _RestApiService_;
            scope = _$rootScope_.$new();

            deferred = _$q_.defer(); //promise
            deferred.resolve([{
                username : 'jack123'
            }, {
                username : 'jill123'
            }, {
                username : 'joe123'
            }]);

            //data parameters
            widgetInstance = {
                widgetDefinition : {
                    dataURL : {
                        url : 'dummy/endpoint'
                    }
                },
                actualViewConfig : {
                    gridColumns : {
                        1 : {
                            field : 'username',
                            visible : true
                        }
                    }
                }
            };
            sortCriteria = {};
            filterCriteria = {};
            pageNumber = 0;
            pageSize = 10;

            //setup spies to ensure functions are called as expected
            spyOn(WidgetGridService, 'buildSearchSortJSON')
                .andReturn({
                    sortMap : sortCriteria,
                    searchMap : filterCriteria,
                    pageSize : pageSize,
                    pageNumber : pageNumber
                });

            spyOn(RestApiService, 'post')
                .andReturn(deferred.promise);

            spyOn(WidgetGridService, 'parseGridRecords')
                .andReturn([{
                    username : 'jack123'
                }, {
                    username : 'jill123'
                }, {
                    username : 'joe123'
                }]);
            spyOn(WidgetGridService, 'getAvailableSkills')
                .andReturn([
                    "basic",
                    "standard",
                    "advanced"
                ]);

            spyOn(WidgetGridService, 'getSupportedLanguages')
                .andReturn([{
                    languageCode : "en_gb", //English (United Kingdom)
                    amdLanguage : true,
                    hhLanguage : true,
                    j2uLanguage : true,
                    u2jLanguage : true
                },
                    {
                        languageCode : "en_us", //English (United States)
                        amdLanguage : true,
                        hhLanguage : true,
                        j2uLanguage : true,
                        u2jLanguage : true
                    },
                    {
                        languageCode : "es", //Spanish
                        amdLanguage : true,
                        hhLanguage : true,
                        j2uLanguage : true,
                        u2jLanguage : true
                    },
                    {
                        languageCode : "de",  //German
                        amdLanguage : true,
                        hhLanguage : true,
                        j2uLanguage : true,
                        u2jLanguage : true
                    },
                    {
                        languageCode : "fr", //French
                        amdLanguage : true,
                        hhLanguage : true,
                        j2uLanguage : true,
                        u2jLanguage : true
                    },
                    {
                        languageCode : "fr_ca", //French (Canada)
                        amdLanguage : true,
                        hhLanguage : true,
                        j2uLanguage : true,
                        u2jLanguage : true
                    }]);

            spyOn(WidgetGridService, 'retrainVoiceLogforSelectedUsers')
                .andReturn({status:"success"});

            spyOn(WidgetGridService, 'getAvailableShifts')
                .andReturn([{
                    shiftId: "01",
                    shiftName: "Morning",
                    startTime: "0600",
                    endTime: "1500"
                },
                    {
                        shiftId: "02",
                        shiftName: "Day",
                        startTime: "0900",
                        endTime: "1800"
                    },
                    {
                        shiftId: "03",
                        shiftName: "Evening",
                        startTime: "1500",
                        endTime: "2200"
                    },
                    {
                        shiftId: "04",
                        shiftName: "Night",
                        startTime: "2100",
                        endTime: "0600"
                    }
                ]);



        }));

        it('should get grid records', function () {

            var result = WidgetGridService.getRecords(widgetInstance, sortCriteria, filterCriteria, pageNumber, pageSize)
                .then(function(response) {
                    result = response;
                });
            

            scope.$digest();

            expect(WidgetGridService.buildSearchSortJSON).toHaveBeenCalled();
            expect(RestApiService.post).toHaveBeenCalled();
            expect(WidgetGridService.parseGridRecords).toHaveBeenCalled();
            expect(result[0].username).toBe('jack123');
            expect(result[1].username).toBe('jill123');
            expect(result[2].username).toBe('joe123');
        });

        //SHIFTS
        it('should load and cache list of shifts', function() {
            //required to resolve the promise
            scope.$digest();

            expect(WidgetGridService.getAvailableShifts).toBeDefined();
        });

        //skills
        it('should load and cache list of skills', function() {
            //required to resolve the promise
            scope.$digest();

            expect(WidgetGridService.getAvailableSkills).toBeDefined();
        });

        //languages
        it('should load and cache supported languages', function() {
            //required to resolve the promise
            scope.$digest();
            expect(WidgetGridService.getSupportedLanguages).toBeDefined();
        });


        it('should delete selected users', function() {
            //required to resolve the promise
            scope.$digest();
            expect(WidgetGridService.deleteSelectedUsers).toBeDefined();
        });

        it('should disable selected users', function() {
            //required to resolve the promise
            scope.$digest();
            expect(WidgetGridService.disableSelectedUsers).toBeDefined();
        });

        it('should enable selected users', function() {
            //required to resolve the promise
            scope.$digest();
            expect(WidgetGridService.enableSelectedUsers).toBeDefined();
        });

        it('should retrain voice log for selected users', function() {
            //required to resolve the promise
            scope.$digest();
            expect(WidgetGridService.retrainVoiceLogforSelectedUsers).toBeDefined();
            expect(WidgetGridService.retrainVoiceLogforSelectedUsers()).toEqual({status:"success"});
        });
    });


    //Construct grid rows from column/data definitions
    describe('Function: parseGridRecords()', function() {

        var columns;
        var data;

        beforeEach(function() {

            //data parameters
            columns = {
                1 : {
                    fieldName : 'username',
                    visible : true
                },
                2 : {
                    fieldName : 'firstName',
                    visible : true
                }
            };

            data = {
                grid : {
                    1 : {
                        values: [
                            'jack123',
                            'jill123',
                            'joe123'
                        ]
                    },
                    2 : {
                        values: [
                            'Jack',
                            'Jill',
                            'Joe'
                        ]
                    }
                }
            }
        });

        it('should construct grid rows from column and data definition', function() {

            var result = WidgetGridService.parseGridRecords(columns, data);

            expect(result.records[0].username).toBe('jack123');
            expect(result.records[0].firstName).toBe('Jack');

            expect(result.records[1].username).toBe('jill123');
            expect(result.records[1].firstName).toBe('Jill');

            expect(result.records[2].username).toBe('joe123');
            expect(result.records[2].firstName).toBe('Joe');
        })
    });


    //Assign new data format to date columns
    describe('Function: updateColumnDateFormat()', function() {

        var columnDefinitions;
        var dateFormat;

        beforeEach(function() {

            //data parameters
            columnDefinitions = [{
                    field : 'startDate',
                    visible : true,
                    cellFilter : 'date: dd-MM-yyyy'
                },
                {
                    field : 'username',
                    visible : true
                }
            ];

            dateFormat = 'MMM dd, yyyy'
        });

        it('should assign new date format only to startDate field', function() {

            var result = WidgetGridService.updateColumnDateFormat(columnDefinitions, dateFormat);

            expect(result[0].cellFilter).toBe('date:\'MMM dd, yyyy\'');
            expect(result[1].hasOwnProperty('cellFilter')).toBeFalsy();
        });
    });


    //Generate column definitions from column information
    describe('Function: buildColumnDefinitions()', function() {

        var widgetInstance;

        beforeEach(function() {
            widgetInstance = {
                actualViewConfig : {
                    gridColumns : []
                }
            }
        });

        it('should build definition with \'field\' property', function() {

            //data parameters
            var column = {
                fieldName : 'username'
            };
            widgetInstance.actualViewConfig.gridColumns.push(column);

            //build the column definition
            var result = WidgetGridService.buildColumnDefinitions(widgetInstance);

            expect(result[0].field).toBe('username');
        });

        it('should use selected date format in column definition ' +
            'when column is \'date\' type', function() {

            //data parameters
            var column = {
                fieldName : 'startDate'
            };
            widgetInstance.actualViewConfig.gridColumns.push(column);
            widgetInstance.actualViewConfig.dateFormat = {
                selectedFormat: 'MMM dd, yyyy'
            };

            //build the column definition
            var result = WidgetGridService.buildColumnDefinitions(widgetInstance);

            expect(result[0].cellFilter).toBe('date:\'MMM dd, yyyy\'');
        });

        it('should use default date format \'dd-MM-yyyy\' in column definition ' +
            'when date format is not specified and column is \'date\' type', function() {

            //data parameters
            var column = {
                fieldName : 'startDate'
            };
            widgetInstance.actualViewConfig.gridColumns.push(column);

            //build the column definition
            var result = WidgetGridService.buildColumnDefinitions(widgetInstance);

            expect(result[0].cellFilter).toBe('date:\'dd-MM-yyyy\'');
        });

        it('should build definition with \'displayName\' property', function() {

            //data parameters
            var column = {
                fieldName : 'username',
                name : 'User Name'
            };
            widgetInstance.actualViewConfig.gridColumns.push(column);

            //build the column definition
            var result = WidgetGridService.buildColumnDefinitions(widgetInstance);

            expect(result[0].displayName).toBe('User Name');
        });

        it('should build definition with \'visible\' property', function() {

            //data parameters
            var column = {
                fieldName : 'username',
                name : 'User Name',
                visible : true
            };
            widgetInstance.actualViewConfig.gridColumns.push(column);

            //build the column definition
            var result = WidgetGridService.buildColumnDefinitions(widgetInstance);

            expect(result[0].visible).toBeTruthy();
        });

        it('should build definition with \'sort\' property, ascending', function() {

            //data parameters
            var column = {
                fieldName : 'username',
                name : 'User Name',
                visible : true,
                sortOrder : 1
            };
            widgetInstance.actualViewConfig.gridColumns.push(column);

            //build the column definition
            var result = WidgetGridService.buildColumnDefinitions(widgetInstance);

            expect(result[0].sort.direction).toBe('asc');
        });

        it('should build definition with \'sort\' property, decending', function() {

            //data parameters
            var column = {
                fieldName : 'username',
                name : 'User Name',
                visible : true,
                sortOrder : -1
            };
            widgetInstance.actualViewConfig.gridColumns.push(column);

            //build the column definition
            var result = WidgetGridService.buildColumnDefinitions(widgetInstance);

            expect(result[0].sort.direction).toBe('desc');
        });

        it('should NOT build definition with \'sort\' property when set = 0', function() {

            //data parameters
            var column = {
                fieldName : 'username',
                name : 'User Name',
                visible : true,
                sortOrder : 0
            };
            widgetInstance.actualViewConfig.gridColumns.push(column);

            //build the column definition
            var result = WidgetGridService.buildColumnDefinitions(widgetInstance);

            expect(result[0].hasOwnProperty()).toBeFalsy();
        });

        it('should build definition with specified \'width\' property', function() {

            //data parameters
            var column = {
                fieldName : 'username',
                name : 'User Name',
                visible : true,
                width : 120
            };
            widgetInstance.actualViewConfig.gridColumns.push(column);

            //build the column definition
            var result = WidgetGridService.buildColumnDefinitions(widgetInstance);

            expect(result[0].width).toBe(120);
        });

        it('should build definition with default \'width\' property = 150, when one is not specified', function() {

            //data parameters
            var column = {
                fieldName : 'username',
                name : 'User Name',
                visible : true
            };
            widgetInstance.actualViewConfig.gridColumns.push(column);

            //build the column definition
            var result = WidgetGridService.buildColumnDefinitions(widgetInstance);

            expect(result[0].width).toBe(150);
        });
    });


    //Gets the column definitions
    describe('Function: getColumnDefinitions()', function() {

        var widgetInstance;

        beforeEach(function() {
            widgetInstance = {
                actualViewConfig : {
                    gridConfiguration : {}
                }
            };

            //setup spy on column builder function
            spyOn(WidgetGridService, 'buildColumnDefinitions').andReturn();
        });

        it('should get column definitions from widgetInstance when persisted', function() {

            //persist column definitions to widgetInstance
            widgetInstance.actualViewConfig.gridConfiguration.columnDefinition =
                [{
                field : 'username',
                visible : true
            }];

            //invoke the service function
            var result = WidgetGridService.getColumnDefinitions(widgetInstance);

            expect(result.length).toBe(1);
            expect(result[0].field).toBe('username');
            expect(WidgetGridService.buildColumnDefinitions).not.toHaveBeenCalled();
        });

        it('should build column definitions if not persisted to widgetInstance', function() {

            //invoke the service function
            WidgetGridService.getColumnDefinitions(widgetInstance);

            expect(WidgetGridService.buildColumnDefinitions).toHaveBeenCalled();
        });
    });


    //updates the column definition width property for the passed column
    describe('Function: updateColumnWidth()', function() {

        var selectedColumn;
        var columnDefinitions;

        beforeEach(function() {

            selectedColumn = {
                field : 'username',
                width : 150
            };
            columnDefinitions = [{
                field : 'username',
                width : 100,
                visible : true
            }, {
                field : 'firstName',
                width : 100,
                visible : true
            }];
        });

        it('should update the column widget for the selected column', function() {

            //invoke the service function
            var result = WidgetGridService.updateColumnWidth(columnDefinitions, selectedColumn);

            expect(result[0].width).toBe(150);
            expect(result[1].width).toBe(100); //should not have update this column
        });
    });


    //gets the sort criteria
    describe('Function: getSortCriteria()', function() {

        var actualViewConfig;

        beforeEach(function() {

            actualViewConfig = {
                gridConfiguration : {}
            };

            //setup spy on column builder function
            spyOn(WidgetGridService, 'buildSortCriteria').andReturn();
        });

        it('should get sort criteria from widgetInstance if persisted', function() {

            //persist sort criteria to widgetInstance
            actualViewConfig.gridConfiguration.sortCriteria = {
                'username' : 'ASC',
                'firstName' : 'DESC'
            };

            //invoke service function
            var result = WidgetGridService.getSortCriteria(actualViewConfig, []);

            expect(result).toEqual({
                'username' : 'ASC',
                'firstName' : 'DESC'
            });
            expect(WidgetGridService.buildSortCriteria).not.toHaveBeenCalled();
        });

        it('should build sort criteria if not persisted to widgetInstance', function() {

            //invoke service function
            WidgetGridService.getSortCriteria(actualViewConfig, []);

            expect(WidgetGridService.buildSortCriteria).toHaveBeenCalled();
        });
    });


    //gets the filter criteria
    describe('Function: getFilterCriteria()', function() {


        var actualViewConfig;

        beforeEach(function() {

            actualViewConfig = {
                gridConfiguration : {}
            };
        });

        it('should get filter criteria from widgetInstance if persisted', function() {

            //persist filter criteria to widgetInstance
            actualViewConfig.gridConfiguration.filterCriteria = {
                'username' : 'jack123'
            };

            //invoke service function
            var result = WidgetGridService.getFilterCriteria(actualViewConfig);

            expect(result).toEqual({
                'username' : 'jack123'
            });
        });

        it('should build and return filter critera object', function() {

            //invoke service function
            var result = WidgetGridService.buildFilterCriteria(actualViewConfig);

            expect(result).toEqual({});
        });

        it('should return empty filter critera object if not persisted to widgetinstance', function() {

            //invoke service function
            var result = WidgetGridService.getFilterCriteria(actualViewConfig);

            expect(result).toEqual({});
        });


    });


    //builds the sort criteria object from column definitions for
    //visible columns only
    describe('Function: buildSortCriteria()', function() {

        var columns;

        beforeEach(function () {
            columns = [{
                field : 'username',
                visible : true,
                sort : {
                    direction : 'asc'
                }
            }, {
                field : 'firstName',
                visible : true,
                sort : {
                    direction : 'desc'
                }
            }, {
                field : 'lastName',
                visible : false,
                sort : {
                    direction : 'asc'
                }
            }, {
                field : 'emailAddress',
                visible : true
            }
            ];
        });

        it('should construct sortCriteria object', function () {

            //invoke service function
            var result = WidgetGridService.buildSortCriteria(columns)

            expect(result).toEqual({
                username : 'ASC',
                firstName : 'DESC'
            });
        });
    });


    //constructs the search sort JSON to send with the post request
    describe('Function: buildSearchSortJSON()', function () {

        var search;
        var sort;
        var pageNumber;
        var pageSize;

        beforeEach(function () {

            search = {
                'username' : 'jack123'
            };
            sort = {
                'username' : 'ASC'
            };
            pageNumber = 0;
            pageSize = 10;
        });

        it('should construct a search sort criteria object', function () {

            //invoke service function
            var result = WidgetGridService.buildSearchSortJSON(pageNumber, pageSize, search, sort);

            expect(result).toEqual({
                pageNumber : '0',
                pageSize : '10',
                sortMap : {
                    username : 'ASC'
                },
                searchMap : {
                    username : 'jack123'
                }
            });
        });
    });


    //updates the column definitions synchronised with the current sort order
    //applied to the grid
    describe('Function: updateColumnSortCriteria()', function () {

        var sortedColumns;
        var columnDefinitions;

        beforeEach(function () {

            //data parameters
            columnDefinitions = [{
                field : 'username',
                sort : {
                    direction : 'ASC'
                }
            }, {
                field : 'firstName',
                sort : {
                    direction : 'DESC'
                }
            }, {
                field : 'lastName'
            }];
        });

        it('should remove any previous sort criteria on column definition no columns are currently sorted', function () {

            //mock no sorted column
            sortedColumns = [];

            //invoke the service function
            var result = WidgetGridService.updateColumnSortCriteria(sortedColumns, columnDefinitions);

            expect(result[0].hasOwnProperty('sort')).toBeFalsy();
            expect(result[1].hasOwnProperty('sort')).toBeFalsy();
            expect(result[2].hasOwnProperty('sort')).toBeFalsy();
        });

        it('should apply new sort criteria to column definition', function () {
            //mock sorted columns
            sortedColumns = [{
                field : 'firstName',
                sort : {
                    direction : 'ASC'
                }
            }];

            //invoke the service function
            var result = WidgetGridService.updateColumnSortCriteria(sortedColumns, columnDefinitions);

            expect(result[0].hasOwnProperty('sort')).toBeFalsy();
            expect(result[1].hasOwnProperty('sort')).toBeTruthy();
            expect(result[1].sort).toEqual({
                direction : 'ASC'
            });
            expect(result[2].hasOwnProperty('sort')).toBeFalsy();
        });
    });


    //gets the default grid options for Desktop
    describe('Function: getDefaultGridOptions() for desktop device' , function () {

        it('should return a default set of grid options', function () {

            spyOn(UtilsService, 'isTouchDevice').andReturn(false);

            //invoke the service call
            var result = WidgetGridService.getDefaultGridOptions();
            expect(result.showGridFooter).toBeTruthy();
            expect(result.gridFooterTemplate).toBeDefined();
            expect(result.enableGridMenu).toBeFalsy();
            expect(result.enablePaging).toBeTruthy();
            expect(result.enableColumnMenus).toBeTruthy();
            expect(result.enableColumnMoving).toBeTruthy();
            expect(result.enableRowSelection).toBeTruthy();
            expect(result.enableRowHeaderSelection).toBeTruthy();
            expect(result.enableSelectAll).toBeTruthy();
            expect(result.multiSelect).toBeTruthy();
            expect(result.noUnselect).toBeFalsy();
            expect(result.enableInfiniteScroll).toBeTruthy();
            expect(result.infiniteScroll).toEqual(10);
            expect(result.enableSorting).toBeTruthy();
            expect(result.useExternalSorting).toBeTruthy();
            expect(result.enableFiltering).toBeTruthy();
            expect(result.useExternalFiltering).toBeTruthy();
            expect(result.modifierKeysToMultiSelect).toBeTruthy();
        });

    });


    //gets the default grid options for Touch Device
    describe('Function: getDefaultGridOptions() for Touch Device', function () {

        it('should return a default set of grid options', function () {

            spyOn(UtilsService, 'isTouchDevice').andReturn(true);

            //invoke the service call
            var result = WidgetGridService.getDefaultGridOptions();
            expect(result.showGridFooter).toBeTruthy();
            expect(result.gridFooterTemplate).toBeDefined();
            expect(result.enableGridMenu).toBeFalsy();
            expect(result.enablePaging).toBeTruthy();
            expect(result.enableColumnMenus).toBeTruthy();
            expect(result.enableColumnMoving).toBeTruthy();
            expect(result.enableRowSelection).toBeTruthy();
            expect(result.enableRowHeaderSelection).toBeTruthy();
            expect(result.enableSelectAll).toBeTruthy();
            expect(result.multiSelect).toBeTruthy();
            expect(result.noUnselect).toBeFalsy();
            expect(result.enableInfiniteScroll).toBeTruthy();
            expect(result.infiniteScroll).toEqual(10);
            expect(result.enableSorting).toBeTruthy();
            expect(result.useExternalSorting).toBeTruthy();
            expect(result.enableFiltering).toBeTruthy();
            expect(result.useExternalFiltering).toBeTruthy();
            expect(result.modifierKeysToMultiSelect).toBeFalsy();
        });

    });


    //persists grid configuration
    describe('Function: saveGridState()', function () {

        var WidgetService;

        beforeEach(inject(function(_WidgetService_) {

            WidgetService = _WidgetService_;

            //setup spy
            spyOn(WidgetService, 'updateFavoriteCanvasListLocalStorage').andReturn();
        }));

        it('should not attempt to persist grid configuration when missing widgetInstance arg', function () {

            //invoke the service function
            WidgetGridService.saveGridState();

            //should not have attempted to save grid configuration
            expect(WidgetService.updateFavoriteCanvasListLocalStorage).not.toHaveBeenCalled();
        });

        it('should persist grid configuration', function () {

            //test parameters
            var widgetInstance = {
                'actualViewConfig': {
                }
            };

            var columnDefinition = [{
                'name': 'username',
                'visible': true
            }];

            var filterCriteria = {
                'firstName': 'jack'
            };

            var sortCriteria = {
                'lastName': {
                    'direction': 'ASC'
                }
            };

            //invoke the service function with test parameters
            WidgetGridService.saveGridState(widgetInstance, columnDefinition, filterCriteria, sortCriteria);

            //expected object to be passed in call to store in local storage
            var expectedObject = {
                'actualViewConfig': {
                    'gridConfiguration': {
                        'columnDefinition': columnDefinition,
                        'filterCriteria': filterCriteria,
                        'sortCriteria': sortCriteria
                    }
                }
            };

            //should have saved grid configuration
            expect(WidgetService.updateFavoriteCanvasListLocalStorage).toHaveBeenCalledWith(expectedObject);
        });
    });

    //compiles the broadcastObject with the widget properties and data
    //that is to be broadcast
    describe('Function: broadcastData()', function () {

        beforeEach(inject(function (_UtilsService_, _WidgetGridService_) {
            //Inject required dependencies
            UtilsService = _UtilsService_;
            WidgetGridService = _WidgetGridService_;

            //Spies
            spyOn(UtilsService, 'broadcast').andReturn();
        }));

        it('should broadcast widget data', function () {
            //test data
            var widgetId = 1234;
            var broadcastElements = ["userName", "firstName"];
            var selectedRows = [
                {"userName":"jack123", "firstName":"Jack"},
                {"userName":"system", "firstName":"Lucas"}
            ];

            //invoke service function
            WidgetGridService.broadcastData(widgetId, broadcastElements,selectedRows);
            expect(UtilsService.broadcast.callCount).toBe(1);
            expect(UtilsService.broadcast.mostRecentCall.args[0] instanceof BroadcastObject).toBeTruthy();

            //check the BroadcastObject for correctness
            var resultObj = UtilsService.broadcast.mostRecentCall.args[0];
            expect(resultObj.getWidgetId()).toEqual(1234);

            expect(resultObj.getData().userName.length).toBe(2);
            expect(resultObj.getData().firstName.length).toBe(2);

            expect(resultObj.getData().userName[0]).toEqual("jack123");
            expect(resultObj.getData().userName[1]).toEqual("system");

            expect(resultObj.getData().firstName[0]).toEqual("Jack");
            expect(resultObj.getData().firstName[1]).toEqual("Lucas");
        });

        it('should not issue a broadcast if there are no broadcast elements', function () {
            //test data
            var widgetId = 1234;
            var broadcastElements = [];
            var selectedRows = [
                {"userName":"jack123", "firstName":"Jack"},
                {"userName":"system", "firstName":"Lucas"}
            ];

            //invoke service function
            WidgetGridService.broadcastData(widgetId, broadcastElements,selectedRows);
            expect(UtilsService.broadcast).not.toHaveBeenCalled();
        });

        it('should  issue a broadcast even if there are no selected rows', function () {
            //test data
            var widgetId = 1234;
            var broadcastElements = ["userName", "firstName"];
            var selectedRows = [];

            //invoke service function
            WidgetGridService.broadcastData(widgetId, broadcastElements,selectedRows);
            expect(UtilsService.broadcast).toHaveBeenCalled();
        });
    });

    it('handle parseColumnDefinitions() method', function() {
        var initialFavoriteCanvasList = [{
                "name": "ProductManagement",
                "canvasId": "201",
                "canvasType": "COMPANY",
                "displayOrder": "1"
            }, {
                "name": "Work Execution",
                "canvasId": "221",
                "canvasType": "COMPANY",
                "displayOrder": "2",
                "widgetInstanceList": [{
                    "data": {
                        "grid": {
                            "1": {
                                "values": ["99-4937", "99-4938", "99-4939"]
                            },
                            "2": {
                                "values": ["99-4937", "99-4938", "99-4939"]
                            },
                            "3": {
                                "values": ["TORO PULLEY", "Water / Fuel Filter ASM", "HOUSING ASM-REDUCER RH"]
                            },
                            "4": {
                                "values": ["Available", "Available", "Available"]
                            },
                            "5": {
                                "values": ["No", "No", "No"]
                            },
                            "6": {
                                "values": ["Yes", "Yes", "Yes"]
                            },
                            "7": {
                                "values": ["Yes", "Yes", "Yes"]
                            },
                            "8": {
                                "values": ["Yes", "Yes", "Yes"]
                            },
                            "9": {
                                "values": ["Yes", "Yes", "Yes"]
                            },
                            "10": {
                                "values": ["No", "Yes", "Yes"]
                            },
                            "11": {
                                "values": ["No", "No", "No"]
                            },
                            "12": {
                                "values": ["customer_SKU_nbr01", "customer_SKU_nbr02", "customer_SKU_nbr03"]
                            },
                            "13": {
                                "values": ["manufacturer_item_nbr01", "manufacturer_item_nbr02", "manufacturer_item_nbr03"]
                            },
                            "14": {
                                "values": ["No", "No", "No"]
                            }
                        }
                    },
                    "id": 14,
                    "widgetDefinition": {
                        "id": 14,
                        "shortName": "SearchProduct",
                        "title": "Search Product",
                        "widgetActionConfig": {
                            "widget-access": {
                                "search-product-grid-widget-access": true
                            },
                            "widget-actions": {
                                "edit-product": true,
                                "create-product": true,
                                "delete-product": true,
                                "view-product": true
                            }
                        },
                        "broadcastList": ["productName"],
                        "reactToMap": {
                            "productName": {
                                "url": "/products/productlist/search",
                                "searchCriteria": {
                                    "pageSize": "10",
                                    "pageNumber": "0",
                                    "searchMap": {
                                        "productName": ["$productName"]
                                    }
                                }
                            }
                        },
                        "dataURL": {
                            "url": "/products/productlist/search",
                            "searchCriteria": {
                                "sortMap": {
                                    "name": "ASC",
                                    "description": "ASC",
                                    "productStatusCode": "ASC",
                                    "performSecondValidation": "ASC",
                                    "captureLotNumber": "ASC",
                                    "captureSerialNumber": "ASC",
                                    "isBaseItem": "ASC",
                                    "captureCatchWeight": "ASC",
                                    "headsUpMessages": "ASC",
                                    "hazardousMaterial": "ASC",
                                    "customerSkuNbr": "ASC",
                                    "manufacturerItemNbr": "ASC",
                                    "captureExpirationDate": "ASC"
                                },
                                "pageSize": "10",
                                "pageNumber": "0",
                                "searchMap": {}
                            }
                        },
                        "defaultViewConfig": {
                            "minimumWidth": 300,
                            "minimumHeight": 480,
                            "anchor": [1, 363],
                            "zindex": 0,
                            "gridColumns": {
                                "1": {
                                    "allowFilter": true,
                                    "fieldName": "productCode",
                                    "name": "Product Code",
                                    "order": "1",
                                    "sortOrder": "1",
                                    "width": 150
                                },
                                "2": {
                                    "allowFilter": true,
                                    "fieldName": "name",
                                    "name": "Product Name",
                                    "order": "2",
                                    "sortOrder": "1",
                                    "width": 150
                                },
                                "3": {
                                    "allowFilter": true,
                                    "fieldName": "description",
                                    "name": "Description",
                                    "order": "3",
                                    "sortOrder": "1",
                                    "width": 150
                                },
                                "4": {
                                    "allowFilter": true,
                                    "fieldName": "productStatusCode",
                                    "name": "Status",
                                    "order": "4",
                                    "sortOrder": "1",
                                    "width": 150
                                },
                                "5": {
                                    "allowFilter": true,
                                    "fieldName": "performSecondValidation",
                                    "name": "2nd Validation",
                                    "order": "5",
                                    "sortOrder": "1",
                                    "width": 150
                                },
                                "6": {
                                    "allowFilter": true,
                                    "fieldName": "captureLotNumber",
                                    "name": "Capture Lot Nbr",
                                    "order": "6",
                                    "sortOrder": "1",
                                    "width": 150
                                },
                                "7": {
                                    "allowFilter": true,
                                    "fieldName": "captureSerialNumber",
                                    "name": "Capture Serial Nbr",
                                    "order": "7",
                                    "sortOrder": "1",
                                    "width": 150
                                },
                                "8": {
                                    "allowFilter": true,
                                    "fieldName": "isBaseItem",
                                    "name": "Base Item",
                                    "order": "8",
                                    "sortOrder": "1",
                                    "width": 150
                                },
                                "9": {
                                    "allowFilter": true,
                                    "fieldName": "captureCatchWeight",
                                    "name": "Capture Catch Weight",
                                    "order": "9",
                                    "sortOrder": "1",
                                    "width": 150
                                },
                                "10": {
                                    "allowFilter": true,
                                    "fieldName": "headsUpMessages",
                                    "name": "HeadsUp Messages",
                                    "order": "10",
                                    "sortOrder": "1",
                                    "width": 150
                                },
                                "11": {
                                    "allowFilter": true,
                                    "fieldName": "hazardousMaterial",
                                    "name": "Haz Material",
                                    "order": "11",
                                    "sortOrder": "1",
                                    "width": 150
                                },
                                "12": {
                                    "allowFilter": true,
                                    "fieldName": "customerSkuNbr",
                                    "name": "Customer SKU Number",
                                    "order": "12",
                                    "sortOrder": "1",
                                    "width": 150
                                },
                                "13": {
                                    "allowFilter": true,
                                    "fieldName": "manufacturerItemNbr",
                                    "name": "Manufacturer Item Number",
                                    "order": "13",
                                    "sortOrder": "1",
                                    "width": 150
                                },
                                "14": {
                                    "allowFilter": true,
                                    "fieldName": "captureExpirationDate",
                                    "name": "Expiration Date Check",
                                    "order": "14",
                                    "sortOrder": "1",
                                    "width": 150
                                }
                            },
                            "listensForList": ["productName"],
                            "deviceWidths": {
                                "320": "mobile",
                                "540": "tablet",
                                "800": "desktop",
                                "1200": "wideScreen"
                            },
                            "reactToList": [],
                            "dateFormat": {
                                "selectedFormat": "mm-dd-yyyy",
                                "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                            },
                            "originalMinimumWidth": 300,
                            "originalMinimumHeight": 480,
                            "sortCriteria": {
                                "name": "ASC",
                                "description": "ASC",
                                "productStatusCode": "ASC",
                                "performSecondValidation": "ASC",
                                "captureLotNumber": "ASC",
                                "captureSerialNumber": "ASC",
                                "isBaseItem": "ASC",
                                "captureCatchWeight": "ASC",
                                "headsUpMessages": "ASC",
                                "hazardousMaterial": "ASC",
                                "customerSkuNbr": "ASC",
                                "manufacturerItemNbr": "ASC",
                                "captureExpirationDate": "ASC"
                            },
                            "columnDefinitions": [{
                                "field": "productCode",
                                "displayName": "Product Code",
                                "visible": false,
                                "sort": {
                                    "direction": "asc"
                                },
                                "width": 150,
                                "enableFiltering": true,
                                "filter": {
                                    "term": null
                                },
                                "name": "productCode",
                                "enableColumnMoving": true,
                                "enableColumnResizing": true,
                                "type": "string"
                            }, {
                                "field": "name",
                                "displayName": "Product Name",
                                "visible": true,
                                "sort": {
                                    "direction": "asc"
                                },
                                "width": 150,
                                "enableFiltering": true,
                                "filter": {
                                    "term": null
                                },
                                "name": "name",
                                "enableColumnMoving": true,
                                "enableColumnResizing": true,
                                "type": "string"
                            }, {
                                "field": "description",
                                "displayName": "Description",
                                "visible": true,
                                "sort": {
                                    "direction": "asc"
                                },
                                "width": 150,
                                "enableFiltering": true,
                                "filter": {
                                    "term": null
                                },
                                "name": "description",
                                "enableColumnMoving": true,
                                "enableColumnResizing": true,
                                "type": "string"
                            }, {
                                "field": "productStatusCode",
                                "displayName": "Status",
                                "visible": true,
                                "sort": {
                                    "direction": "asc"
                                },
                                "width": 150,
                                "enableFiltering": true,
                                "filter": {
                                    "term": null
                                },
                                "name": "productStatusCode",
                                "enableColumnMoving": true,
                                "enableColumnResizing": true,
                                "type": "string"
                            }, {
                                "field": "performSecondValidation",
                                "displayName": "2nd Validation",
                                "visible": true,
                                "sort": {
                                    "direction": "asc"
                                },
                                "width": 150,
                                "enableFiltering": true,
                                "filter": {
                                    "term": null
                                },
                                "name": "performSecondValidation",
                                "enableColumnMoving": true,
                                "enableColumnResizing": true,
                                "type": "string"
                            }, {
                                "field": "captureLotNumber",
                                "displayName": "Capture Lot Nbr",
                                "visible": true,
                                "sort": {
                                    "direction": "asc"
                                },
                                "width": 150,
                                "enableFiltering": true,
                                "filter": {
                                    "term": null
                                },
                                "name": "captureLotNumber",
                                "enableColumnMoving": true,
                                "enableColumnResizing": true,
                                "type": "string"
                            }, {
                                "field": "captureSerialNumber",
                                "displayName": "Capture Serial Nbr",
                                "visible": true,
                                "sort": {
                                    "direction": "asc"
                                },
                                "width": 150,
                                "enableFiltering": true,
                                "filter": {
                                    "term": null
                                },
                                "name": "captureSerialNumber",
                                "enableColumnMoving": true,
                                "enableColumnResizing": true,
                                "type": "string"
                            }, {
                                "field": "isBaseItem",
                                "displayName": "Base Item",
                                "visible": true,
                                "sort": {
                                    "direction": "asc"
                                },
                                "width": 150,
                                "enableFiltering": true,
                                "filter": {
                                    "term": null
                                },
                                "name": "isBaseItem",
                                "enableColumnMoving": true,
                                "enableColumnResizing": true,
                                "type": "string"
                            }, {
                                "field": "captureCatchWeight",
                                "displayName": "Capture Catch Weight",
                                "visible": true,
                                "sort": {
                                    "direction": "asc"
                                },
                                "width": 150,
                                "enableFiltering": true,
                                "filter": {
                                    "term": null
                                },
                                "name": "captureCatchWeight",
                                "enableColumnMoving": true,
                                "enableColumnResizing": true,
                                "type": "string"
                            }, {
                                "field": "headsUpMessages",
                                "displayName": "HeadsUp Messages",
                                "visible": true,
                                "sort": {
                                    "direction": "asc"
                                },
                                "width": 150,
                                "enableFiltering": true,
                                "filter": {
                                    "term": null
                                },
                                "name": "headsUpMessages",
                                "enableColumnMoving": true,
                                "enableColumnResizing": true,
                                "type": "string"
                            }, {
                                "field": "hazardousMaterial",
                                "displayName": "Haz Material",
                                "visible": true,
                                "sort": {
                                    "direction": "asc"
                                },
                                "width": 150,
                                "enableFiltering": true,
                                "filter": {
                                    "term": null
                                },
                                "name": "hazardousMaterial",
                                "enableColumnMoving": true,
                                "enableColumnResizing": true,
                                "type": "string"
                            }, {
                                "field": "customerSkuNbr",
                                "displayName": "Customer SKU Number",
                                "visible": true,
                                "sort": {
                                    "direction": "asc"
                                },
                                "width": 150,
                                "enableFiltering": true,
                                "filter": {
                                    "term": null
                                },
                                "name": "customerSkuNbr",
                                "enableColumnMoving": true,
                                "enableColumnResizing": true,
                                "type": "string"
                            }, {
                                "field": "manufacturerItemNbr",
                                "displayName": "Manufacturer Item Number",
                                "visible": true,
                                "sort": {
                                    "direction": "asc"
                                },
                                "width": 150,
                                "enableFiltering": true,
                                "filter": {
                                    "term": null
                                },
                                "name": "manufacturerItemNbr",
                                "enableColumnMoving": true,
                                "enableColumnResizing": true,
                                "type": "string"
                            }, {
                                "field": "captureExpirationDate",
                                "cellFilter": "date:'mm-dd-yyyy'",
                                "displayName": "Expiration Date Check",
                                "visible": true,
                                "sort": {
                                    "direction": "asc"
                                },
                                "width": 150,
                                "enableFiltering": true,
                                "filter": {
                                    "term": null
                                },
                                "name": "captureExpirationDate",
                                "enableColumnMoving": true,
                                "enableColumnResizing": true,
                                "type": "string"
                            }]
                        },
                        "defaultData": {},
                        "name": "search-product-grid-widget"
                    },
                    "actualViewConfig": {
                        "minimumWidth": 300,
                        "minimumHeight": 480,
                        "anchor": [1, 363],
                        "zindex": 0,
                        "gridColumns": {
                            "1": {
                                "allowFilter": true,
                                "fieldName": "productCode",
                                "name": "Product Code",
                                "order": "1",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "2": {
                                "allowFilter": true,
                                "fieldName": "name",
                                "name": "Product Name",
                                "order": "2",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "3": {
                                "allowFilter": true,
                                "fieldName": "description",
                                "name": "Description",
                                "order": "3",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "4": {
                                "allowFilter": true,
                                "fieldName": "productStatusCode",
                                "name": "Status",
                                "order": "4",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "5": {
                                "allowFilter": true,
                                "fieldName": "performSecondValidation",
                                "name": "2nd Validation",
                                "order": "5",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "6": {
                                "allowFilter": true,
                                "fieldName": "captureLotNumber",
                                "name": "Capture Lot Nbr",
                                "order": "6",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "7": {
                                "allowFilter": true,
                                "fieldName": "captureSerialNumber",
                                "name": "Capture Serial Nbr",
                                "order": "7",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "8": {
                                "allowFilter": true,
                                "fieldName": "isBaseItem",
                                "name": "Base Item",
                                "order": "8",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "9": {
                                "allowFilter": true,
                                "fieldName": "captureCatchWeight",
                                "name": "Capture Catch Weight",
                                "order": "9",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "10": {
                                "allowFilter": true,
                                "fieldName": "headsUpMessages",
                                "name": "HeadsUp Messages",
                                "order": "10",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "11": {
                                "allowFilter": true,
                                "fieldName": "hazardousMaterial",
                                "name": "Haz Material",
                                "order": "11",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "12": {
                                "allowFilter": true,
                                "fieldName": "customerSkuNbr",
                                "name": "Customer SKU Number",
                                "order": "12",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "13": {
                                "allowFilter": true,
                                "fieldName": "manufacturerItemNbr",
                                "name": "Manufacturer Item Number",
                                "order": "13",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "14": {
                                "allowFilter": true,
                                "fieldName": "captureExpirationDate",
                                "name": "Expiration Date Check",
                                "order": "14",
                                "sortOrder": "1",
                                "width": 150
                            }
                        },
                        "listensForList": ["productName"],
                        "deviceWidths": {
                            "320": "mobile",
                            "540": "tablet",
                            "800": "desktop",
                            "1200": "wideScreen"
                        },
                        "reactToList": [],
                        "dateFormat": {
                            "selectedFormat": "mm-dd-yyyy",
                            "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                        },
                        "originalMinimumWidth": 300,
                        "originalMinimumHeight": 480,
                        "sortCriteria": {
                            "name": "ASC",
                            "description": "ASC",
                            "productStatusCode": "ASC",
                            "performSecondValidation": "ASC",
                            "captureLotNumber": "ASC",
                            "captureSerialNumber": "ASC",
                            "isBaseItem": "ASC",
                            "captureCatchWeight": "ASC",
                            "headsUpMessages": "ASC",
                            "hazardousMaterial": "ASC",
                            "customerSkuNbr": "ASC",
                            "manufacturerItemNbr": "ASC",
                            "captureExpirationDate": "ASC"
                        },
                        "columnDefinitions": [{
                            "field": "productCode",
                            "displayName": "Product Code",
                            "visible": false,
                            "sort": {
                                "direction": "asc"
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "productCode",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "name",
                            "displayName": "Product Name",
                            "visible": true,
                            "sort": {
                                "direction": "asc"
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "name",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "description",
                            "displayName": "Description",
                            "visible": true,
                            "sort": {
                                "direction": "asc"
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "description",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "productStatusCode",
                            "displayName": "Status",
                            "visible": true,
                            "sort": {
                                "direction": "asc"
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "productStatusCode",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "performSecondValidation",
                            "displayName": "2nd Validation",
                            "visible": true,
                            "sort": {
                                "direction": "asc"
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "performSecondValidation",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "captureLotNumber",
                            "displayName": "Capture Lot Nbr",
                            "visible": true,
                            "sort": {
                                "direction": "asc"
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "captureLotNumber",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "captureSerialNumber",
                            "displayName": "Capture Serial Nbr",
                            "visible": true,
                            "sort": {
                                "direction": "asc"
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "captureSerialNumber",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "isBaseItem",
                            "displayName": "Base Item",
                            "visible": true,
                            "sort": {
                                "direction": "asc"
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "isBaseItem",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "captureCatchWeight",
                            "displayName": "Capture Catch Weight",
                            "visible": true,
                            "sort": {
                                "direction": "asc"
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "captureCatchWeight",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "headsUpMessages",
                            "displayName": "HeadsUp Messages",
                            "visible": true,
                            "sort": {
                                "direction": "asc"
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "headsUpMessages",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "hazardousMaterial",
                            "displayName": "Haz Material",
                            "visible": true,
                            "sort": {
                                "direction": "asc"
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "hazardousMaterial",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "customerSkuNbr",
                            "displayName": "Customer SKU Number",
                            "visible": true,
                            "sort": {
                                "direction": "asc"
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "customerSkuNbr",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "manufacturerItemNbr",
                            "displayName": "Manufacturer Item Number",
                            "visible": true,
                            "sort": {
                                "direction": "asc"
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "manufacturerItemNbr",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "captureExpirationDate",
                            "cellFilter": "date:'mm-dd-yyyy'",
                            "displayName": "Expiration Date Check",
                            "visible": true,
                            "sort": {
                                "direction": "asc"
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "captureExpirationDate",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }]
                    },
                    "updateWidget": true,
                    "clientId": 100,
                    "isMaximized": false
                }]
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
        }];
        // set up initial LS
        LocalStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', initialFavoriteCanvasList);
        // prepare the parameters to be passed
        var scopeObj = {
            filterCriteria: {},
            columnDefinitions: [{
                "field": "productCode",
                "displayName": "Product Code",
                "visible": false,
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null
                },
                "name": "productCode",
                "enableColumnMoving": true,
                "enableColumnResizing": true,
                "type": "string"
            }, {
                "field": "name",
                "displayName": "Product Name",
                "visible": true,
                "sort": {
                    "direction": "desc",
                    "priority": 0
                },
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null,
                    "$$hashKey": "03O"
                },
                "name": "name",
                "enableColumnMoving": true,
                "enableColumnResizing": true,
                "type": "string"
            }, {
                "field": "description",
                "displayName": "Description",
                "visible": true,
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null,
                    "$$hashKey": "03S"
                },
                "name": "description",
                "enableColumnMoving": true,
                "enableColumnResizing": true,
                "type": "string"
            }, {
                "field": "productStatusCode",
                "displayName": "Status",
                "visible": true,
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null,
                    "$$hashKey": "03W"
                },
                "name": "productStatusCode",
                "enableColumnMoving": true,
                "enableColumnResizing": true,
                "type": "string"
            }, {
                "field": "performSecondValidation",
                "displayName": "2nd Validation",
                "visible": true,
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null,
                    "$$hashKey": "040"
                },
                "name": "performSecondValidation",
                "enableColumnMoving": true,
                "enableColumnResizing": true,
                "type": "string"
            }, {
                "field": "captureLotNumber",
                "displayName": "Capture Lot Nbr",
                "visible": true,
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null,
                    "$$hashKey": "044"
                },
                "name": "captureLotNumber",
                "enableColumnMoving": true,
                "enableColumnResizing": true,
                "type": "string"
            }, {
                "field": "captureSerialNumber",
                "displayName": "Capture Serial Nbr",
                "visible": true,
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null,
                    "$$hashKey": "048"
                },
                "name": "captureSerialNumber",
                "enableColumnMoving": true,
                "enableColumnResizing": true,
                "type": "string"
            }, {
                "field": "isBaseItem",
                "displayName": "Base Item",
                "visible": true,
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null,
                    "$$hashKey": "04C"
                },
                "name": "isBaseItem",
                "enableColumnMoving": true,
                "enableColumnResizing": true,
                "type": "string"
            }, {
                "field": "captureCatchWeight",
                "displayName": "Capture Catch Weight",
                "visible": true,
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null,
                    "$$hashKey": "04G"
                },
                "name": "captureCatchWeight",
                "enableColumnMoving": true,
                "enableColumnResizing": true,
                "type": "string"
            }, {
                "field": "headsUpMessages",
                "displayName": "HeadsUp Messages",
                "visible": true,
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null,
                    "$$hashKey": "04K"
                },
                "name": "headsUpMessages",
                "enableColumnMoving": true,
                "enableColumnResizing": true,
                "type": "string"
            }, {
                "field": "hazardousMaterial",
                "displayName": "Haz Material",
                "visible": true,
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null
                },
                "name": "hazardousMaterial",
                "enableColumnMoving": true,
                "enableColumnResizing": true,
                "type": "string"
            }, {
                "field": "customerSkuNbr",
                "displayName": "Customer SKU Number",
                "visible": true,
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null
                },
                "name": "customerSkuNbr",
                "enableColumnMoving": true,
                "enableColumnResizing": true,
                "type": "string"
            }, {
                "field": "manufacturerItemNbr",
                "displayName": "Manufacturer Item Number",
                "visible": true,
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null
                },
                "name": "manufacturerItemNbr",
                "enableColumnMoving": true,
                "enableColumnResizing": true,
                "type": "string"
            }, {
                "field": "captureExpirationDate",
                "cellFilter": "date:'mm-dd-yyyy'",
                "displayName": "Expiration Date Check",
                "visible": true,
                "width": 150,
                "enableFiltering": true,
                "filter": {
                    "term": null
                },
                "name": "captureExpirationDate",
                "enableColumnMoving": true,
                "enableColumnResizing": true,
                "type": "string"
            }],
            widgetdetails: {
                "data": {
                    "grid": {
                        "1": {
                            "values": ["99-4937", "99-4938", "99-4939"]
                        },
                        "2": {
                            "values": ["99-4937", "99-4938", "99-4939"]
                        },
                        "3": {
                            "values": ["TORO PULLEY", "Water / Fuel Filter ASM", "HOUSING ASM-REDUCER RH"]
                        },
                        "4": {
                            "values": ["Available", "Available", "Available"]
                        },
                        "5": {
                            "values": ["No", "No", "No"]
                        },
                        "6": {
                            "values": ["Yes", "Yes", "Yes"]
                        },
                        "7": {
                            "values": ["Yes", "Yes", "Yes"]
                        },
                        "8": {
                            "values": ["Yes", "Yes", "Yes"]
                        },
                        "9": {
                            "values": ["Yes", "Yes", "Yes"]
                        },
                        "10": {
                            "values": ["No", "Yes", "Yes"]
                        },
                        "11": {
                            "values": ["No", "No", "No"]
                        },
                        "12": {
                            "values": ["customer_SKU_nbr01", "customer_SKU_nbr02", "customer_SKU_nbr03"]
                        },
                        "13": {
                            "values": ["manufacturer_item_nbr01", "manufacturer_item_nbr02", "manufacturer_item_nbr03"]
                        },
                        "14": {
                            "values": ["No", "No", "No"]
                        }
                    }
                },
                "id": 14,
                "widgetDefinition": {
                    "id": 14,
                    "shortName": "SearchProduct",
                    "title": "Search Product",
                    "widgetActionConfig": {
                        "widget-access": {
                            "search-product-grid-widget-access": true
                        },
                        "widget-actions": {
                            "edit-product": true,
                            "create-product": true,
                            "delete-product": true,
                            "view-product": true
                        }
                    },
                    "broadcastList": ["productName"],
                    "reactToMap": {
                        "productName": {
                            "url": "/products/productlist/search",
                            "searchCriteria": {
                                "pageSize": "10",
                                "pageNumber": "0",
                                "searchMap": {
                                    "productName": ["$productName"]
                                }
                            }
                        }
                    },
                    "dataURL": {
                        "url": "/products/productlist/search",
                        "searchCriteria": {
                            "sortMap": {
                                "name": "ASC",
                                "description": "ASC",
                                "productStatusCode": "ASC",
                                "performSecondValidation": "ASC",
                                "captureLotNumber": "ASC",
                                "captureSerialNumber": "ASC",
                                "isBaseItem": "ASC",
                                "captureCatchWeight": "ASC",
                                "headsUpMessages": "ASC",
                                "hazardousMaterial": "ASC",
                                "customerSkuNbr": "ASC",
                                "manufacturerItemNbr": "ASC",
                                "captureExpirationDate": "ASC"
                            },
                            "pageSize": "10",
                            "pageNumber": "0",
                            "searchMap": {}
                        }
                    },
                    "defaultViewConfig": {
                        "minimumWidth": 300,
                        "minimumHeight": 480,
                        "anchor": [1, 363],
                        "zindex": 0,
                        "gridColumns": {
                            "1": {
                                "allowFilter": true,
                                "fieldName": "productCode",
                                "name": "Product Code",
                                "order": "1",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "2": {
                                "allowFilter": true,
                                "fieldName": "name",
                                "name": "Product Name",
                                "order": "2",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "3": {
                                "allowFilter": true,
                                "fieldName": "description",
                                "name": "Description",
                                "order": "3",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "4": {
                                "allowFilter": true,
                                "fieldName": "productStatusCode",
                                "name": "Status",
                                "order": "4",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "5": {
                                "allowFilter": true,
                                "fieldName": "performSecondValidation",
                                "name": "2nd Validation",
                                "order": "5",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "6": {
                                "allowFilter": true,
                                "fieldName": "captureLotNumber",
                                "name": "Capture Lot Nbr",
                                "order": "6",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "7": {
                                "allowFilter": true,
                                "fieldName": "captureSerialNumber",
                                "name": "Capture Serial Nbr",
                                "order": "7",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "8": {
                                "allowFilter": true,
                                "fieldName": "isBaseItem",
                                "name": "Base Item",
                                "order": "8",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "9": {
                                "allowFilter": true,
                                "fieldName": "captureCatchWeight",
                                "name": "Capture Catch Weight",
                                "order": "9",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "10": {
                                "allowFilter": true,
                                "fieldName": "headsUpMessages",
                                "name": "HeadsUp Messages",
                                "order": "10",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "11": {
                                "allowFilter": true,
                                "fieldName": "hazardousMaterial",
                                "name": "Haz Material",
                                "order": "11",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "12": {
                                "allowFilter": true,
                                "fieldName": "customerSkuNbr",
                                "name": "Customer SKU Number",
                                "order": "12",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "13": {
                                "allowFilter": true,
                                "fieldName": "manufacturerItemNbr",
                                "name": "Manufacturer Item Number",
                                "order": "13",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "14": {
                                "allowFilter": true,
                                "fieldName": "captureExpirationDate",
                                "name": "Expiration Date Check",
                                "order": "14",
                                "sortOrder": "1",
                                "width": 150
                            }
                        },
                        "listensForList": ["productName"],
                        "deviceWidths": {
                            "320": "mobile",
                            "540": "tablet",
                            "800": "desktop",
                            "1200": "wideScreen"
                        },
                        "reactToList": [],
                        "dateFormat": {
                            "selectedFormat": "mm-dd-yyyy",
                            "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                        },
                        "originalMinimumWidth": 300,
                        "originalMinimumHeight": 480,
                        "sortCriteria": {
                            "name": "ASC",
                            "description": "ASC",
                            "productStatusCode": "ASC",
                            "performSecondValidation": "ASC",
                            "captureLotNumber": "ASC",
                            "captureSerialNumber": "ASC",
                            "isBaseItem": "ASC",
                            "captureCatchWeight": "ASC",
                            "headsUpMessages": "ASC",
                            "hazardousMaterial": "ASC",
                            "customerSkuNbr": "ASC",
                            "manufacturerItemNbr": "ASC",
                            "captureExpirationDate": "ASC"
                        },
                        "columnDefinitions": [{
                            "field": "productCode",
                            "displayName": "Product Code",
                            "visible": false,
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "productCode",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "name",
                            "displayName": "Product Name",
                            "visible": true,
                            "sort": {
                                "direction": "desc",
                                "priority": 0
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null,
                                "$$hashKey": "03O"
                            },
                            "name": "name",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "description",
                            "displayName": "Description",
                            "visible": true,
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null,
                                "$$hashKey": "03S"
                            },
                            "name": "description",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "productStatusCode",
                            "displayName": "Status",
                            "visible": true,
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null,
                                "$$hashKey": "03W"
                            },
                            "name": "productStatusCode",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "performSecondValidation",
                            "displayName": "2nd Validation",
                            "visible": true,
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null,
                                "$$hashKey": "040"
                            },
                            "name": "performSecondValidation",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "captureLotNumber",
                            "displayName": "Capture Lot Nbr",
                            "visible": true,
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null,
                                "$$hashKey": "044"
                            },
                            "name": "captureLotNumber",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "captureSerialNumber",
                            "displayName": "Capture Serial Nbr",
                            "visible": true,
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null,
                                "$$hashKey": "048"
                            },
                            "name": "captureSerialNumber",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "isBaseItem",
                            "displayName": "Base Item",
                            "visible": true,
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null,
                                "$$hashKey": "04C"
                            },
                            "name": "isBaseItem",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "captureCatchWeight",
                            "displayName": "Capture Catch Weight",
                            "visible": true,
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null,
                                "$$hashKey": "04G"
                            },
                            "name": "captureCatchWeight",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "headsUpMessages",
                            "displayName": "HeadsUp Messages",
                            "visible": true,
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null,
                                "$$hashKey": "04K"
                            },
                            "name": "headsUpMessages",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "hazardousMaterial",
                            "displayName": "Haz Material",
                            "visible": true,
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "hazardousMaterial",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "customerSkuNbr",
                            "displayName": "Customer SKU Number",
                            "visible": true,
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "customerSkuNbr",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "manufacturerItemNbr",
                            "displayName": "Manufacturer Item Number",
                            "visible": true,
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "manufacturerItemNbr",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "captureExpirationDate",
                            "cellFilter": "date:'mm-dd-yyyy'",
                            "displayName": "Expiration Date Check",
                            "visible": true,
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "captureExpirationDate",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }]
                    },
                    "defaultData": {},
                    "name": "search-product-grid-widget"
                },
                "actualViewConfig": {
                    "minimumWidth": 300,
                    "minimumHeight": 480,
                    "anchor": [1, 363],
                    "zindex": 0,
                    "gridColumns": {
                        "1": {
                            "allowFilter": true,
                            "fieldName": "productCode",
                            "name": "Product Code",
                            "order": "1",
                            "sortOrder": "1",
                            "width": 150
                        },
                        "2": {
                            "allowFilter": true,
                            "fieldName": "name",
                            "name": "Product Name",
                            "order": "2",
                            "sortOrder": "1",
                            "width": 150
                        },
                        "3": {
                            "allowFilter": true,
                            "fieldName": "description",
                            "name": "Description",
                            "order": "3",
                            "sortOrder": "1",
                            "width": 150
                        },
                        "4": {
                            "allowFilter": true,
                            "fieldName": "productStatusCode",
                            "name": "Status",
                            "order": "4",
                            "sortOrder": "1",
                            "width": 150
                        },
                        "5": {
                            "allowFilter": true,
                            "fieldName": "performSecondValidation",
                            "name": "2nd Validation",
                            "order": "5",
                            "sortOrder": "1",
                            "width": 150
                        },
                        "6": {
                            "allowFilter": true,
                            "fieldName": "captureLotNumber",
                            "name": "Capture Lot Nbr",
                            "order": "6",
                            "sortOrder": "1",
                            "width": 150
                        },
                        "7": {
                            "allowFilter": true,
                            "fieldName": "captureSerialNumber",
                            "name": "Capture Serial Nbr",
                            "order": "7",
                            "sortOrder": "1",
                            "width": 150
                        },
                        "8": {
                            "allowFilter": true,
                            "fieldName": "isBaseItem",
                            "name": "Base Item",
                            "order": "8",
                            "sortOrder": "1",
                            "width": 150
                        },
                        "9": {
                            "allowFilter": true,
                            "fieldName": "captureCatchWeight",
                            "name": "Capture Catch Weight",
                            "order": "9",
                            "sortOrder": "1",
                            "width": 150
                        },
                        "10": {
                            "allowFilter": true,
                            "fieldName": "headsUpMessages",
                            "name": "HeadsUp Messages",
                            "order": "10",
                            "sortOrder": "1",
                            "width": 150
                        },
                        "11": {
                            "allowFilter": true,
                            "fieldName": "hazardousMaterial",
                            "name": "Haz Material",
                            "order": "11",
                            "sortOrder": "1",
                            "width": 150
                        },
                        "12": {
                            "allowFilter": true,
                            "fieldName": "customerSkuNbr",
                            "name": "Customer SKU Number",
                            "order": "12",
                            "sortOrder": "1",
                            "width": 150
                        },
                        "13": {
                            "allowFilter": true,
                            "fieldName": "manufacturerItemNbr",
                            "name": "Manufacturer Item Number",
                            "order": "13",
                            "sortOrder": "1",
                            "width": 150
                        },
                        "14": {
                            "allowFilter": true,
                            "fieldName": "captureExpirationDate",
                            "name": "Expiration Date Check",
                            "order": "14",
                            "sortOrder": "1",
                            "width": 150
                        }
                    },
                    "listensForList": ["productName"],
                    "deviceWidths": {
                        "320": "mobile",
                        "540": "tablet",
                        "800": "desktop",
                        "1200": "wideScreen"
                    },
                    "reactToList": [],
                    "dateFormat": {
                        "selectedFormat": "mm-dd-yyyy",
                        "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                    },
                    "originalMinimumWidth": 300,
                    "originalMinimumHeight": 480,
                    "sortCriteria": {
                        "name": "ASC",
                        "description": "ASC",
                        "productStatusCode": "ASC",
                        "performSecondValidation": "ASC",
                        "captureLotNumber": "ASC",
                        "captureSerialNumber": "ASC",
                        "isBaseItem": "ASC",
                        "captureCatchWeight": "ASC",
                        "headsUpMessages": "ASC",
                        "hazardousMaterial": "ASC",
                        "customerSkuNbr": "ASC",
                        "manufacturerItemNbr": "ASC",
                        "captureExpirationDate": "ASC"
                    },
                    "columnDefinitions": [{
                        "field": "productCode",
                        "displayName": "Product Code",
                        "visible": false,
                        "width": 150,
                        "enableFiltering": true,
                        "filter": {
                            "term": null
                        },
                        "name": "productCode",
                        "enableColumnMoving": true,
                        "enableColumnResizing": true,
                        "type": "string"
                    }, {
                        "field": "name",
                        "displayName": "Product Name",
                        "visible": true,
                        "sort": {
                            "direction": "desc",
                            "priority": 0
                        },
                        "width": 150,
                        "enableFiltering": true,
                        "filter": {
                            "term": null,
                            "$$hashKey": "03O"
                        },
                        "name": "name",
                        "enableColumnMoving": true,
                        "enableColumnResizing": true,
                        "type": "string"
                    }, {
                        "field": "description",
                        "displayName": "Description",
                        "visible": true,
                        "width": 150,
                        "enableFiltering": true,
                        "filter": {
                            "term": null,
                            "$$hashKey": "03S"
                        },
                        "name": "description",
                        "enableColumnMoving": true,
                        "enableColumnResizing": true,
                        "type": "string"
                    }, {
                        "field": "productStatusCode",
                        "displayName": "Status",
                        "visible": true,
                        "width": 150,
                        "enableFiltering": true,
                        "filter": {
                            "term": null,
                            "$$hashKey": "03W"
                        },
                        "name": "productStatusCode",
                        "enableColumnMoving": true,
                        "enableColumnResizing": true,
                        "type": "string"
                    }, {
                        "field": "performSecondValidation",
                        "displayName": "2nd Validation",
                        "visible": true,
                        "width": 150,
                        "enableFiltering": true,
                        "filter": {
                            "term": null,
                            "$$hashKey": "040"
                        },
                        "name": "performSecondValidation",
                        "enableColumnMoving": true,
                        "enableColumnResizing": true,
                        "type": "string"
                    }, {
                        "field": "captureLotNumber",
                        "displayName": "Capture Lot Nbr",
                        "visible": true,
                        "width": 150,
                        "enableFiltering": true,
                        "filter": {
                            "term": null,
                            "$$hashKey": "044"
                        },
                        "name": "captureLotNumber",
                        "enableColumnMoving": true,
                        "enableColumnResizing": true,
                        "type": "string"
                    }, {
                        "field": "captureSerialNumber",
                        "displayName": "Capture Serial Nbr",
                        "visible": true,
                        "width": 150,
                        "enableFiltering": true,
                        "filter": {
                            "term": null,
                            "$$hashKey": "048"
                        },
                        "name": "captureSerialNumber",
                        "enableColumnMoving": true,
                        "enableColumnResizing": true,
                        "type": "string"
                    }, {
                        "field": "isBaseItem",
                        "displayName": "Base Item",
                        "visible": true,
                        "width": 150,
                        "enableFiltering": true,
                        "filter": {
                            "term": null,
                            "$$hashKey": "04C"
                        },
                        "name": "isBaseItem",
                        "enableColumnMoving": true,
                        "enableColumnResizing": true,
                        "type": "string"
                    }, {
                        "field": "captureCatchWeight",
                        "displayName": "Capture Catch Weight",
                        "visible": true,
                        "width": 150,
                        "enableFiltering": true,
                        "filter": {
                            "term": null,
                            "$$hashKey": "04G"
                        },
                        "name": "captureCatchWeight",
                        "enableColumnMoving": true,
                        "enableColumnResizing": true,
                        "type": "string"
                    }, {
                        "field": "headsUpMessages",
                        "displayName": "HeadsUp Messages",
                        "visible": true,
                        "width": 150,
                        "enableFiltering": true,
                        "filter": {
                            "term": null,
                            "$$hashKey": "04K"
                        },
                        "name": "headsUpMessages",
                        "enableColumnMoving": true,
                        "enableColumnResizing": true,
                        "type": "string"
                    }, {
                        "field": "hazardousMaterial",
                        "displayName": "Haz Material",
                        "visible": true,
                        "width": 150,
                        "enableFiltering": true,
                        "filter": {
                            "term": null
                        },
                        "name": "hazardousMaterial",
                        "enableColumnMoving": true,
                        "enableColumnResizing": true,
                        "type": "string"
                    }, {
                        "field": "customerSkuNbr",
                        "displayName": "Customer SKU Number",
                        "visible": true,
                        "width": 150,
                        "enableFiltering": true,
                        "filter": {
                            "term": null
                        },
                        "name": "customerSkuNbr",
                        "enableColumnMoving": true,
                        "enableColumnResizing": true,
                        "type": "string"
                    }, {
                        "field": "manufacturerItemNbr",
                        "displayName": "Manufacturer Item Number",
                        "visible": true,
                        "width": 150,
                        "enableFiltering": true,
                        "filter": {
                            "term": null
                        },
                        "name": "manufacturerItemNbr",
                        "enableColumnMoving": true,
                        "enableColumnResizing": true,
                        "type": "string"
                    }, {
                        "field": "captureExpirationDate",
                        "cellFilter": "date:'mm-dd-yyyy'",
                        "displayName": "Expiration Date Check",
                        "visible": true,
                        "width": 150,
                        "enableFiltering": true,
                        "filter": {
                            "term": null
                        },
                        "name": "captureExpirationDate",
                        "enableColumnMoving": true,
                        "enableColumnResizing": true,
                        "type": "string"
                    }]
                },
                "updateWidget": true,
                "clientId": 100,
                "isMaximized": false
            }
        };
        var newObj = [{
            "field": "productCode",
            "displayName": "Product Code",
            "visible": false,
            "width": 150,
            "enableFiltering": true,
            "filter": {
                "term": null
            },
            "name": "productCode",
            "enableColumnMoving": true,
            "enableColumnResizing": true,
            "type": "string"
        }, {
            "field": "name",
            "displayName": "Product Name",
            "visible": true,
            "sort": {
                "direction": "desc",
                "priority": 0
            },
            "width": 150,
            "enableFiltering": true,
            "filter": {
                "term": null,
                "$$hashKey": "03O"
            },
            "name": "name",
            "enableColumnMoving": true,
            "enableColumnResizing": true,
            "type": "string"
        }, {
            "field": "description",
            "displayName": "Description",
            "visible": true,
            "width": 150,
            "enableFiltering": true,
            "filter": {
                "term": null,
                "$$hashKey": "03S"
            },
            "name": "description",
            "enableColumnMoving": true,
            "enableColumnResizing": true,
            "type": "string"
        }, {
            "field": "productStatusCode",
            "displayName": "Status",
            "visible": true,
            "width": 150,
            "enableFiltering": true,
            "filter": {
                "term": null,
                "$$hashKey": "03W"
            },
            "name": "productStatusCode",
            "enableColumnMoving": true,
            "enableColumnResizing": true,
            "type": "string"
        }, {
            "field": "performSecondValidation",
            "displayName": "2nd Validation",
            "visible": true,
            "width": 150,
            "enableFiltering": true,
            "filter": {
                "term": null,
                "$$hashKey": "040"
            },
            "name": "performSecondValidation",
            "enableColumnMoving": true,
            "enableColumnResizing": true,
            "type": "string"
        }, {
            "field": "captureLotNumber",
            "displayName": "Capture Lot Nbr",
            "visible": true,
            "width": 150,
            "enableFiltering": true,
            "filter": {
                "term": null,
                "$$hashKey": "044"
            },
            "name": "captureLotNumber",
            "enableColumnMoving": true,
            "enableColumnResizing": true,
            "type": "string"
        }, {
            "field": "captureSerialNumber",
            "displayName": "Capture Serial Nbr",
            "visible": true,
            "width": 150,
            "enableFiltering": true,
            "filter": {
                "term": null,
                "$$hashKey": "048"
            },
            "name": "captureSerialNumber",
            "enableColumnMoving": true,
            "enableColumnResizing": true,
            "type": "string"
        }, {
            "field": "isBaseItem",
            "displayName": "Base Item",
            "visible": true,
            "width": 150,
            "enableFiltering": true,
            "filter": {
                "term": null,
                "$$hashKey": "04C"
            },
            "name": "isBaseItem",
            "enableColumnMoving": true,
            "enableColumnResizing": true,
            "type": "string"
        }, {
            "field": "captureCatchWeight",
            "displayName": "Capture Catch Weight",
            "visible": true,
            "width": 150,
            "enableFiltering": true,
            "filter": {
                "term": null,
                "$$hashKey": "04G"
            },
            "name": "captureCatchWeight",
            "enableColumnMoving": true,
            "enableColumnResizing": true,
            "type": "string"
        }, {
            "field": "headsUpMessages",
            "displayName": "HeadsUp Messages",
            "visible": true,
            "width": 150,
            "enableFiltering": true,
            "filter": {
                "term": null,
                "$$hashKey": "04K"
            },
            "name": "headsUpMessages",
            "enableColumnMoving": true,
            "enableColumnResizing": true,
            "type": "string"
        }, {
            "field": "hazardousMaterial",
            "displayName": "Haz Material",
            "visible": true,
            "width": 150,
            "enableFiltering": true,
            "filter": {
                "term": null
            },
            "name": "hazardousMaterial",
            "enableColumnMoving": true,
            "enableColumnResizing": true,
            "type": "string"
        }, {
            "field": "customerSkuNbr",
            "displayName": "Customer SKU Number",
            "visible": true,
            "width": 150,
            "enableFiltering": true,
            "filter": {
                "term": null
            },
            "name": "customerSkuNbr",
            "enableColumnMoving": true,
            "enableColumnResizing": true,
            "type": "string"
        }, {
            "field": "manufacturerItemNbr",
            "displayName": "Manufacturer Item Number",
            "visible": true,
            "width": 150,
            "enableFiltering": true,
            "filter": {
                "term": null
            },
            "name": "manufacturerItemNbr",
            "enableColumnMoving": true,
            "enableColumnResizing": true,
            "type": "string"
        }, {
            "field": "captureExpirationDate",
            "cellFilter": "date:'mm-dd-yyyy'",
            "displayName": "Expiration Date Check",
            "visible": true,
            "width": 150,
            "enableFiltering": true,
            "filter": {
                "term": null
            },
            "name": "captureExpirationDate",
            "enableColumnMoving": true,
            "enableColumnResizing": true,
            "type": "string"
        }];
        var oldObj = [{
            "field": "productCode",
            "displayName": "Product Code",
            "visible": false,
            "sort": {
                "direction": "asc"
            },
            "width": 150,
            "enableFiltering": true,
            "filter": {
                "term": null
            },
            "name": "productCode",
            "enableColumnMoving": true,
            "enableColumnResizing": true,
            "type": "string"
        }, {
            "field": "name",
            "displayName": "Product Name",
            "visible": true,
            "sort": {
                "direction": "asc"
            },
            "width": 150,
            "enableFiltering": true,
            "filter": {
                "term": null
            },
            "name": "name",
            "enableColumnMoving": true,
            "enableColumnResizing": true,
            "type": "string"
        }, {
            "field": "description",
            "displayName": "Description",
            "visible": true,
            "sort": {
                "direction": "asc"
            },
            "width": 150,
            "enableFiltering": true,
            "filter": {
                "term": null
            },
            "name": "description",
            "enableColumnMoving": true,
            "enableColumnResizing": true,
            "type": "string"
        }, {
            "field": "productStatusCode",
            "displayName": "Status",
            "visible": true,
            "sort": {
                "direction": "asc"
            },
            "width": 150,
            "enableFiltering": true,
            "filter": {
                "term": null
            },
            "name": "productStatusCode",
            "enableColumnMoving": true,
            "enableColumnResizing": true,
            "type": "string"
        }, {
            "field": "performSecondValidation",
            "displayName": "2nd Validation",
            "visible": true,
            "sort": {
                "direction": "asc"
            },
            "width": 150,
            "enableFiltering": true,
            "filter": {
                "term": null
            },
            "name": "performSecondValidation",
            "enableColumnMoving": true,
            "enableColumnResizing": true,
            "type": "string"
        }, {
            "field": "captureLotNumber",
            "displayName": "Capture Lot Nbr",
            "visible": true,
            "sort": {
                "direction": "asc"
            },
            "width": 150,
            "enableFiltering": true,
            "filter": {
                "term": null
            },
            "name": "captureLotNumber",
            "enableColumnMoving": true,
            "enableColumnResizing": true,
            "type": "string"
        }, {
            "field": "captureSerialNumber",
            "displayName": "Capture Serial Nbr",
            "visible": true,
            "sort": {
                "direction": "asc"
            },
            "width": 150,
            "enableFiltering": true,
            "filter": {
                "term": null
            },
            "name": "captureSerialNumber",
            "enableColumnMoving": true,
            "enableColumnResizing": true,
            "type": "string"
        }, {
            "field": "isBaseItem",
            "displayName": "Base Item",
            "visible": true,
            "sort": {
                "direction": "asc"
            },
            "width": 150,
            "enableFiltering": true,
            "filter": {
                "term": null
            },
            "name": "isBaseItem",
            "enableColumnMoving": true,
            "enableColumnResizing": true,
            "type": "string"
        }, {
            "field": "captureCatchWeight",
            "displayName": "Capture Catch Weight",
            "visible": true,
            "sort": {
                "direction": "asc"
            },
            "width": 150,
            "enableFiltering": true,
            "filter": {
                "term": null
            },
            "name": "captureCatchWeight",
            "enableColumnMoving": true,
            "enableColumnResizing": true,
            "type": "string"
        }, {
            "field": "headsUpMessages",
            "displayName": "HeadsUp Messages",
            "visible": true,
            "sort": {
                "direction": "asc"
            },
            "width": 150,
            "enableFiltering": true,
            "filter": {
                "term": null
            },
            "name": "headsUpMessages",
            "enableColumnMoving": true,
            "enableColumnResizing": true,
            "type": "string"
        }, {
            "field": "hazardousMaterial",
            "displayName": "Haz Material",
            "visible": true,
            "sort": {
                "direction": "asc"
            },
            "width": 150,
            "enableFiltering": true,
            "filter": {
                "term": null
            },
            "name": "hazardousMaterial",
            "enableColumnMoving": true,
            "enableColumnResizing": true,
            "type": "string"
        }, {
            "field": "customerSkuNbr",
            "displayName": "Customer SKU Number",
            "visible": true,
            "sort": {
                "direction": "asc"
            },
            "width": 150,
            "enableFiltering": true,
            "filter": {
                "term": null
            },
            "name": "customerSkuNbr",
            "enableColumnMoving": true,
            "enableColumnResizing": true,
            "type": "string"
        }, {
            "field": "manufacturerItemNbr",
            "displayName": "Manufacturer Item Number",
            "visible": true,
            "sort": {
                "direction": "asc"
            },
            "width": 150,
            "enableFiltering": true,
            "filter": {
                "term": null
            },
            "name": "manufacturerItemNbr",
            "enableColumnMoving": true,
            "enableColumnResizing": true,
            "type": "string"
        }, {
            "field": "captureExpirationDate",
            "cellFilter": "date:'mm-dd-yyyy'",
            "displayName": "Expiration Date Check",
            "visible": true,
            "sort": {
                "direction": "asc"
            },
            "width": 150,
            "enableFiltering": true,
            "filter": {
                "term": null
            },
            "name": "captureExpirationDate",
            "enableColumnMoving": true,
            "enableColumnResizing": true,
            "type": "string"
        }];
        var updatedFavoriteCanvasListUpdated = [{
            "name": "ProductManagement",
            "canvasId": "201",
            "canvasType": "COMPANY",
            "displayOrder": "1"
        }, {
            "name": "Work Execution",
            "canvasId": "221",
            "canvasType": "COMPANY",
            "displayOrder": "2",
            "widgetInstanceList": [{
                "data": {
                    "grid": {
                        "1": {
                            "values": ["99-4937", "99-4938", "99-4939"]
                        },
                        "2": {
                            "values": ["99-4937", "99-4938", "99-4939"]
                        },
                        "3": {
                            "values": ["TORO PULLEY", "Water / Fuel Filter ASM", "HOUSING ASM-REDUCER RH"]
                        },
                        "4": {
                            "values": ["Available", "Available", "Available"]
                        },
                        "5": {
                            "values": ["No", "No", "No"]
                        },
                        "6": {
                            "values": ["Yes", "Yes", "Yes"]
                        },
                        "7": {
                            "values": ["Yes", "Yes", "Yes"]
                        },
                        "8": {
                            "values": ["Yes", "Yes", "Yes"]
                        },
                        "9": {
                            "values": ["Yes", "Yes", "Yes"]
                        },
                        "10": {
                            "values": ["No", "Yes", "Yes"]
                        },
                        "11": {
                            "values": ["No", "No", "No"]
                        },
                        "12": {
                            "values": ["customer_SKU_nbr01", "customer_SKU_nbr02", "customer_SKU_nbr03"]
                        },
                        "13": {
                            "values": ["manufacturer_item_nbr01", "manufacturer_item_nbr02", "manufacturer_item_nbr03"]
                        },
                        "14": {
                            "values": ["No", "No", "No"]
                        }
                    }
                },
                "id": 14,
                "widgetDefinition": {
                    "id": 14,
                    "shortName": "SearchProduct",
                    "title": "Search Product",
                    "widgetActionConfig": {
                        "widget-access": {
                            "search-product-grid-widget-access": true
                        },
                        "widget-actions": {
                            "edit-product": true,
                            "create-product": true,
                            "delete-product": true,
                            "view-product": true
                        }
                    },
                    "broadcastList": ["productName"],
                    "reactToMap": {
                        "productName": {
                            "url": "/products/productlist/search",
                            "searchCriteria": {
                                "pageSize": "10",
                                "pageNumber": "0",
                                "searchMap": {
                                    "productName": ["$productName"]
                                }
                            }
                        }
                    },
                    "dataURL": {
                        "url": "/products/productlist/search",
                        "searchCriteria": {
                            "sortMap": {
                                "name": "ASC",
                                "description": "ASC",
                                "productStatusCode": "ASC",
                                "performSecondValidation": "ASC",
                                "captureLotNumber": "ASC",
                                "captureSerialNumber": "ASC",
                                "isBaseItem": "ASC",
                                "captureCatchWeight": "ASC",
                                "headsUpMessages": "ASC",
                                "hazardousMaterial": "ASC",
                                "customerSkuNbr": "ASC",
                                "manufacturerItemNbr": "ASC",
                                "captureExpirationDate": "ASC"
                            },
                            "pageSize": "10",
                            "pageNumber": "0",
                            "searchMap": {}
                        }
                    },
                    "defaultViewConfig": {
                        "minimumWidth": 300,
                        "minimumHeight": 480,
                        "anchor": [1, 363],
                        "zindex": 0,
                        "gridColumns": {
                            "1": {
                                "allowFilter": true,
                                "fieldName": "productCode",
                                "name": "Product Code",
                                "order": "1",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "2": {
                                "allowFilter": true,
                                "fieldName": "name",
                                "name": "Product Name",
                                "order": "2",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "3": {
                                "allowFilter": true,
                                "fieldName": "description",
                                "name": "Description",
                                "order": "3",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "4": {
                                "allowFilter": true,
                                "fieldName": "productStatusCode",
                                "name": "Status",
                                "order": "4",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "5": {
                                "allowFilter": true,
                                "fieldName": "performSecondValidation",
                                "name": "2nd Validation",
                                "order": "5",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "6": {
                                "allowFilter": true,
                                "fieldName": "captureLotNumber",
                                "name": "Capture Lot Nbr",
                                "order": "6",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "7": {
                                "allowFilter": true,
                                "fieldName": "captureSerialNumber",
                                "name": "Capture Serial Nbr",
                                "order": "7",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "8": {
                                "allowFilter": true,
                                "fieldName": "isBaseItem",
                                "name": "Base Item",
                                "order": "8",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "9": {
                                "allowFilter": true,
                                "fieldName": "captureCatchWeight",
                                "name": "Capture Catch Weight",
                                "order": "9",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "10": {
                                "allowFilter": true,
                                "fieldName": "headsUpMessages",
                                "name": "HeadsUp Messages",
                                "order": "10",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "11": {
                                "allowFilter": true,
                                "fieldName": "hazardousMaterial",
                                "name": "Haz Material",
                                "order": "11",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "12": {
                                "allowFilter": true,
                                "fieldName": "customerSkuNbr",
                                "name": "Customer SKU Number",
                                "order": "12",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "13": {
                                "allowFilter": true,
                                "fieldName": "manufacturerItemNbr",
                                "name": "Manufacturer Item Number",
                                "order": "13",
                                "sortOrder": "1",
                                "width": 150
                            },
                            "14": {
                                "allowFilter": true,
                                "fieldName": "captureExpirationDate",
                                "name": "Expiration Date Check",
                                "order": "14",
                                "sortOrder": "1",
                                "width": 150
                            }
                        },
                        "listensForList": ["productName"],
                        "deviceWidths": {
                            "320": "mobile",
                            "540": "tablet",
                            "800": "desktop",
                            "1200": "wideScreen"
                        },
                        "reactToList": [],
                        "dateFormat": {
                            "selectedFormat": "mm-dd-yyyy",
                            "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                        },
                        "originalMinimumWidth": 300,
                        "originalMinimumHeight": 480,
                        "sortCriteria": {
                            "name": "ASC",
                            "description": "ASC",
                            "productStatusCode": "ASC",
                            "performSecondValidation": "ASC",
                            "captureLotNumber": "ASC",
                            "captureSerialNumber": "ASC",
                            "isBaseItem": "ASC",
                            "captureCatchWeight": "ASC",
                            "headsUpMessages": "ASC",
                            "hazardousMaterial": "ASC",
                            "customerSkuNbr": "ASC",
                            "manufacturerItemNbr": "ASC",
                            "captureExpirationDate": "ASC"
                        },
                        "columnDefinitions": [{
                            "field": "productCode",
                            "displayName": "Product Code",
                            "visible": false,
                            "sort": {
                                "direction": "asc"
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "productCode",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "name",
                            "displayName": "Product Name",
                            "visible": true,
                            "sort": {
                                "direction": "asc"
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "name",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "description",
                            "displayName": "Description",
                            "visible": true,
                            "sort": {
                                "direction": "asc"
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "description",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "productStatusCode",
                            "displayName": "Status",
                            "visible": true,
                            "sort": {
                                "direction": "asc"
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "productStatusCode",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "performSecondValidation",
                            "displayName": "2nd Validation",
                            "visible": true,
                            "sort": {
                                "direction": "asc"
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "performSecondValidation",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "captureLotNumber",
                            "displayName": "Capture Lot Nbr",
                            "visible": true,
                            "sort": {
                                "direction": "asc"
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "captureLotNumber",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "captureSerialNumber",
                            "displayName": "Capture Serial Nbr",
                            "visible": true,
                            "sort": {
                                "direction": "asc"
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "captureSerialNumber",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "isBaseItem",
                            "displayName": "Base Item",
                            "visible": true,
                            "sort": {
                                "direction": "asc"
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "isBaseItem",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "captureCatchWeight",
                            "displayName": "Capture Catch Weight",
                            "visible": true,
                            "sort": {
                                "direction": "asc"
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "captureCatchWeight",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "headsUpMessages",
                            "displayName": "HeadsUp Messages",
                            "visible": true,
                            "sort": {
                                "direction": "asc"
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "headsUpMessages",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "hazardousMaterial",
                            "displayName": "Haz Material",
                            "visible": true,
                            "sort": {
                                "direction": "asc"
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "hazardousMaterial",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "customerSkuNbr",
                            "displayName": "Customer SKU Number",
                            "visible": true,
                            "sort": {
                                "direction": "asc"
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "customerSkuNbr",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "manufacturerItemNbr",
                            "displayName": "Manufacturer Item Number",
                            "visible": true,
                            "sort": {
                                "direction": "asc"
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "manufacturerItemNbr",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }, {
                            "field": "captureExpirationDate",
                            "cellFilter": "date:'mm-dd-yyyy'",
                            "displayName": "Expiration Date Check",
                            "visible": true,
                            "sort": {
                                "direction": "asc"
                            },
                            "width": 150,
                            "enableFiltering": true,
                            "filter": {
                                "term": null
                            },
                            "name": "captureExpirationDate",
                            "enableColumnMoving": true,
                            "enableColumnResizing": true,
                            "type": "string"
                        }]
                    },
                    "defaultData": {},
                    "name": "search-product-grid-widget"
                },
                "actualViewConfig": {
                    "minimumWidth": 300,
                    "minimumHeight": 480,
                    "anchor": [1, 363],
                    "zindex": 0,
                    "gridColumns": {
                        "1": {
                            "allowFilter": true,
                            "fieldName": "productCode",
                            "name": "Product Code",
                            "order": "1",
                            "sortOrder": "1",
                            "width": 150
                        },
                        "2": {
                            "allowFilter": true,
                            "fieldName": "name",
                            "name": "Product Name",
                            "order": "2",
                            "sortOrder": "1",
                            "width": 150
                        },
                        "3": {
                            "allowFilter": true,
                            "fieldName": "description",
                            "name": "Description",
                            "order": "3",
                            "sortOrder": "1",
                            "width": 150
                        },
                        "4": {
                            "allowFilter": true,
                            "fieldName": "productStatusCode",
                            "name": "Status",
                            "order": "4",
                            "sortOrder": "1",
                            "width": 150
                        },
                        "5": {
                            "allowFilter": true,
                            "fieldName": "performSecondValidation",
                            "name": "2nd Validation",
                            "order": "5",
                            "sortOrder": "1",
                            "width": 150
                        },
                        "6": {
                            "allowFilter": true,
                            "fieldName": "captureLotNumber",
                            "name": "Capture Lot Nbr",
                            "order": "6",
                            "sortOrder": "1",
                            "width": 150
                        },
                        "7": {
                            "allowFilter": true,
                            "fieldName": "captureSerialNumber",
                            "name": "Capture Serial Nbr",
                            "order": "7",
                            "sortOrder": "1",
                            "width": 150
                        },
                        "8": {
                            "allowFilter": true,
                            "fieldName": "isBaseItem",
                            "name": "Base Item",
                            "order": "8",
                            "sortOrder": "1",
                            "width": 150
                        },
                        "9": {
                            "allowFilter": true,
                            "fieldName": "captureCatchWeight",
                            "name": "Capture Catch Weight",
                            "order": "9",
                            "sortOrder": "1",
                            "width": 150
                        },
                        "10": {
                            "allowFilter": true,
                            "fieldName": "headsUpMessages",
                            "name": "HeadsUp Messages",
                            "order": "10",
                            "sortOrder": "1",
                            "width": 150
                        },
                        "11": {
                            "allowFilter": true,
                            "fieldName": "hazardousMaterial",
                            "name": "Haz Material",
                            "order": "11",
                            "sortOrder": "1",
                            "width": 150
                        },
                        "12": {
                            "allowFilter": true,
                            "fieldName": "customerSkuNbr",
                            "name": "Customer SKU Number",
                            "order": "12",
                            "sortOrder": "1",
                            "width": 150
                        },
                        "13": {
                            "allowFilter": true,
                            "fieldName": "manufacturerItemNbr",
                            "name": "Manufacturer Item Number",
                            "order": "13",
                            "sortOrder": "1",
                            "width": 150
                        },
                        "14": {
                            "allowFilter": true,
                            "fieldName": "captureExpirationDate",
                            "name": "Expiration Date Check",
                            "order": "14",
                            "sortOrder": "1",
                            "width": 150
                        }
                    },
                    "listensForList": ["productName"],
                    "deviceWidths": {
                        "320": "mobile",
                        "540": "tablet",
                        "800": "desktop",
                        "1200": "wideScreen"
                    },
                    "reactToList": [],
                    "dateFormat": {
                        "selectedFormat": "mm-dd-yyyy",
                        "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                    },
                    "originalMinimumWidth": 300,
                    "originalMinimumHeight": 480,
                    "sortCriteria": {
                        "name": "ASC",
                        "description": "ASC",
                        "productStatusCode": "ASC",
                        "performSecondValidation": "ASC",
                        "captureLotNumber": "ASC",
                        "captureSerialNumber": "ASC",
                        "isBaseItem": "ASC",
                        "captureCatchWeight": "ASC",
                        "headsUpMessages": "ASC",
                        "hazardousMaterial": "ASC",
                        "customerSkuNbr": "ASC",
                        "manufacturerItemNbr": "ASC",
                        "captureExpirationDate": "ASC"
                    },
                    "columnDefinitions": [{
                        "field": "productCode",
                        "displayName": "Product Code",
                        "visible": false,
                        "sort": {
                            "direction": "asc"
                        },
                        "width": 150,
                        "enableFiltering": true,
                        "filter": {
                            "term": null
                        },
                        "name": "productCode",
                        "enableColumnMoving": true,
                        "enableColumnResizing": true,
                        "type": "string"
                    }, {
                        "field": "name",
                        "displayName": "Product Name",
                        "visible": true,
                        "sort": {
                            "direction": "asc"
                        },
                        "width": 150,
                        "enableFiltering": true,
                        "filter": {
                            "term": null
                        },
                        "name": "name",
                        "enableColumnMoving": true,
                        "enableColumnResizing": true,
                        "type": "string"
                    }, {
                        "field": "description",
                        "displayName": "Description",
                        "visible": true,
                        "sort": {
                            "direction": "asc"
                        },
                        "width": 150,
                        "enableFiltering": true,
                        "filter": {
                            "term": null
                        },
                        "name": "description",
                        "enableColumnMoving": true,
                        "enableColumnResizing": true,
                        "type": "string"
                    }, {
                        "field": "productStatusCode",
                        "displayName": "Status",
                        "visible": true,
                        "sort": {
                            "direction": "asc"
                        },
                        "width": 150,
                        "enableFiltering": true,
                        "filter": {
                            "term": null
                        },
                        "name": "productStatusCode",
                        "enableColumnMoving": true,
                        "enableColumnResizing": true,
                        "type": "string"
                    }, {
                        "field": "performSecondValidation",
                        "displayName": "2nd Validation",
                        "visible": true,
                        "sort": {
                            "direction": "asc"
                        },
                        "width": 150,
                        "enableFiltering": true,
                        "filter": {
                            "term": null
                        },
                        "name": "performSecondValidation",
                        "enableColumnMoving": true,
                        "enableColumnResizing": true,
                        "type": "string"
                    }, {
                        "field": "captureLotNumber",
                        "displayName": "Capture Lot Nbr",
                        "visible": true,
                        "sort": {
                            "direction": "asc"
                        },
                        "width": 150,
                        "enableFiltering": true,
                        "filter": {
                            "term": null
                        },
                        "name": "captureLotNumber",
                        "enableColumnMoving": true,
                        "enableColumnResizing": true,
                        "type": "string"
                    }, {
                        "field": "captureSerialNumber",
                        "displayName": "Capture Serial Nbr",
                        "visible": true,
                        "sort": {
                            "direction": "asc"
                        },
                        "width": 150,
                        "enableFiltering": true,
                        "filter": {
                            "term": null
                        },
                        "name": "captureSerialNumber",
                        "enableColumnMoving": true,
                        "enableColumnResizing": true,
                        "type": "string"
                    }, {
                        "field": "isBaseItem",
                        "displayName": "Base Item",
                        "visible": true,
                        "sort": {
                            "direction": "asc"
                        },
                        "width": 150,
                        "enableFiltering": true,
                        "filter": {
                            "term": null
                        },
                        "name": "isBaseItem",
                        "enableColumnMoving": true,
                        "enableColumnResizing": true,
                        "type": "string"
                    }, {
                        "field": "captureCatchWeight",
                        "displayName": "Capture Catch Weight",
                        "visible": true,
                        "sort": {
                            "direction": "asc"
                        },
                        "width": 150,
                        "enableFiltering": true,
                        "filter": {
                            "term": null
                        },
                        "name": "captureCatchWeight",
                        "enableColumnMoving": true,
                        "enableColumnResizing": true,
                        "type": "string"
                    }, {
                        "field": "headsUpMessages",
                        "displayName": "HeadsUp Messages",
                        "visible": true,
                        "sort": {
                            "direction": "asc"
                        },
                        "width": 150,
                        "enableFiltering": true,
                        "filter": {
                            "term": null
                        },
                        "name": "headsUpMessages",
                        "enableColumnMoving": true,
                        "enableColumnResizing": true,
                        "type": "string"
                    }, {
                        "field": "hazardousMaterial",
                        "displayName": "Haz Material",
                        "visible": true,
                        "sort": {
                            "direction": "asc"
                        },
                        "width": 150,
                        "enableFiltering": true,
                        "filter": {
                            "term": null
                        },
                        "name": "hazardousMaterial",
                        "enableColumnMoving": true,
                        "enableColumnResizing": true,
                        "type": "string"
                    }, {
                        "field": "customerSkuNbr",
                        "displayName": "Customer SKU Number",
                        "visible": true,
                        "sort": {
                            "direction": "asc"
                        },
                        "width": 150,
                        "enableFiltering": true,
                        "filter": {
                            "term": null
                        },
                        "name": "customerSkuNbr",
                        "enableColumnMoving": true,
                        "enableColumnResizing": true,
                        "type": "string"
                    }, {
                        "field": "manufacturerItemNbr",
                        "displayName": "Manufacturer Item Number",
                        "visible": true,
                        "sort": {
                            "direction": "asc"
                        },
                        "width": 150,
                        "enableFiltering": true,
                        "filter": {
                            "term": null
                        },
                        "name": "manufacturerItemNbr",
                        "enableColumnMoving": true,
                        "enableColumnResizing": true,
                        "type": "string"
                    }, {
                        "field": "captureExpirationDate",
                        "cellFilter": "date:'mm-dd-yyyy'",
                        "displayName": "Expiration Date Check",
                        "visible": true,
                        "sort": {
                            "direction": "asc"
                        },
                        "width": 150,
                        "enableFiltering": true,
                        "filter": {
                            "term": null
                        },
                        "name": "captureExpirationDate",
                        "enableColumnMoving": true,
                        "enableColumnResizing": true,
                        "type": "string"
                    }]
                },
                "updateWidget": true,
                "clientId": 100,
                "isMaximized": false
            }]
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
        }];
        // call the parseColumnDefinitions
        WidgetGridService.parseColumnDefinitions(scopeObj, newObj, oldObj);
        // compare the actual and expected LS
        expect(LocalStoreService.getLSItem('FavoriteCanvasListUpdated')).toEqual(updatedFavoriteCanvasListUpdated);
    });
});