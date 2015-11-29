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

/**
 * Services related to grid placed on a widget.
 */
amdApp.factory('WidgetGridService', ['$log', 'WidgetService', 'RestApiService', '$q', 'UtilsService',
    function (log, WidgetService, RestApiService, q, UtilsService) {

        var factory = {},
            languages = undefined,
            shifts = undefined,
            skills = undefined;

        //Make rest call to retrieve records from server
        factory.getRecords = function (widgetInstanceObj, sort, filter, page, size) {
            console.log('***[WidgetGridService.getRecords]***');
            var url = widgetInstanceObj.widgetDefinition.dataURL.url;
            var criteria = factory.buildSearchSortJSON(page, size, filter, sort);

            // Mock data for Equpment type grid

            if (url === '/equipments/equipment-type-list/search') {
                var def = q.defer();
                //Fetch records
                var responseData = {
                    "status": "success",
                    "code": "200",
                    "message": "Request processed successfully",
                    "level": null,
                    "uniqueKey": null,
                    "token": null,
                    "explicitDismissal": null,
                    "data": {
                        "grid": {

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
                };

                var gridData = responseData.data;
                var gridColumns = widgetInstanceObj.actualViewConfig.gridColumns;
                //Row builder logic. It converts the data received
                //from the server into data rows in format UIGrid requires
                responseData = factory.parseGridRecords(gridColumns, gridData);
                def.resolve(responseData);
                return def.promise;
            }

            //Fetch records
            var promise = RestApiService.post(url, null, criteria).then(function (response) {

                var gridData = response;
                var gridColumns = widgetInstanceObj.actualViewConfig.gridColumns;

                //Row builder logic. It converts the data received
                //from the server into data rows in format UIGrid requires
                return factory.parseGridRecords(gridColumns, gridData);
            });

            //send back data
            return promise;
        };

        // For now using mock data for EAI Message Segments
        factory.getEAIMessageSegments = function (widgetInstanceObj, sort, filter, page, size) {
            console.log('***[WidgetGridService.getEAIMessageSegments]***');
            var def = q.defer();

            //Fetch records
            var responseData = {
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
                            "values": ["Control", "ToHdr", "ToHdr", "ROTxtHdr", "ROTxtDtl", "TOltm"]
                        },
                        "1": {
                            "values": ["Control record", "Control record", "Control record", "Control record", "Control record", "Control record"]
                        },
                        "2": {
                            "values": ["125", "112", "333", "123", "567", "678"]
                        },
                        "3": {
                            "values": ["0", "0", "0", "2", "1", "1"]
                        },
                        "4": {
                            "values": ["6", "6", "3", "4", "5", "5"]
                        }
                    }
                }
            };

            var gridData = responseData.data;
            var gridColumns = widgetInstanceObj.actualViewConfig.gridColumns;
            //Row builder logic. It converts the data received
            //from the server into data rows in format UIGrid requires
            responseData = factory.parseGridRecords(gridColumns, gridData);

            setTimeout(function() {
                def.resolve(responseData);
            }, 500);

            return def.promise;
        };

        // For now using mock data for EAI Messages
        factory.getEAIMessages = function (widgetInstanceObj, sort, filter, page, size) {
            console.log('***[WidgetGridService.getEAIMessages]***');
            var def = q.defer();

            //Fetch records
            var responseData = {
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
                            "values": ["Logon request", "Logon request", "Logon request", "Logon request", "Logon request", "Logon request"]
                        },
                        "1": {
                            "values": ["Handheld device logon request", "Handheld device logon request", "Handheld device logon request", "Handheld device logon request", "Handheld device logon request", "Handheld device logon request"]
                        },
                        "2": {
                            "values": ["Fixed length", "Fixed length", "Fixed length", "Fixed length", "Fixed length", "Fixed length"]
                        },
                        "3": {
                            "values": ["1", "1", "2", "2", "1", "1"]
                        },
                        "4": {
                            "values": ["Yes", "Yes", "Yes", "Yes", "Yes", "Yes"]
                        }
                    }
                }
            };

            var gridData = responseData.data;
            var gridColumns = widgetInstanceObj.actualViewConfig.gridColumns;
            //Row builder logic. It converts the data received
            //from the server into data rows in format UIGrid requires
            responseData = factory.parseGridRecords(gridColumns, gridData);

            setTimeout(function() {
                def.resolve(responseData);
            }, 500);

            return def.promise;
        };

        // For now using mock data for EAI Message Mappings
        factory.getEAIMessageMappings = function (widgetInstanceObj, sort, filter, page, size) {
            console.log('***[WidgetGridService.getEAIMessageMappings]***');
            var def = q.defer();

            //Fetch records
            var responseData = {
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
                            "values": ["Pick Completion", "Pick Completion", "Pick Completion", "Pick Completion", "Pick Completion", "Pick Completion"]
                        },
                        "1": {
                            "values": ["Pick Completion request to host", "Pick Completion request to host", "Pick Completion request to host", "Pick Completion request to host", "Pick Completion request to host", "Pick Completion request to host"]
                        },
                        "2": {
                            "values": ["LS_Pick Completion", "LS_Pick Completion", "LS_Pick Completion", "LS_Pick Completion", "LS_Pick Completion", "LS_Pick Completion"]
                        },
                        "3": {
                            "values": ["HS_Pick Completion", "HS_Pick Completion", "HS_Pick Completion", "HS_Pick Completion", "HS_Pick Completion", "HS_Pick Completion"]
                        },
                        "4": {
                            "values": ["Yes", "Yes", "Yes", "Yes", "Yes", "Yes"]
                        }
                    }
                }
            };

            var gridData = responseData.data;
            var gridColumns = widgetInstanceObj.actualViewConfig.gridColumns;
            //Row builder logic. It converts the data received
            //from the server into data rows in format UIGrid requires
            responseData = factory.parseGridRecords(gridColumns, gridData);

            setTimeout(function() {
                def.resolve(responseData);
            }, 500);

            return def.promise;
        };

        // For now using mock data for EAI Events
        factory.getEAIEvents = function (widgetInstanceObj, sort, filter, page, size) {
            console.log('***[WidgetGridService.getEAIEvents]***');
            var def = q.defer();

            //Fetch records
            var responseData = {
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
                            "values": ["Products Download", "Products Download", "Products Download", "Products Download", "Products Download", "Products Download"]
                        },
                        "1": {
                            "values": ["Download product master from host", "Download product master from host", "Download product master from host", "Download product master from host", "Download product master from host", "Download product master from host"]
                        },
                        "2": {
                            "values": ["Inbound", "Inbound", "Inbound", "Inbound", "Inbound", "Inbound"]
                        },
                        "3": {
                            "values": ["Host", "Host", "Host", "Host", "Host", "Host"]
                        },
                        "4": {
                            "values": ["Products", "Products", "Products", "Products", "Products", "Products"]
                        },
                        "5": {
                            "values": ["Products", "Products", "Products", "Products", "Products", "Products"]
                        }
                    }
                }
            };

            var gridData = responseData.data;
            var gridColumns = widgetInstanceObj.actualViewConfig.gridColumns;
            //Row builder logic. It converts the data received
            //from the server into data rows in format UIGrid requires
            responseData = factory.parseGridRecords(gridColumns, gridData);

            setTimeout(function() {
                def.resolve(responseData);
            }, 500);

            return def.promise;
        };

        // For now using mock data for EAI Event Handlers
        factory.getEAIEventHandlers = function (widgetInstanceObj, sort, filter, page, size) {
            console.log('***[WidgetGridService.getEAIEventHandlers]***');
            var def = q.defer();

            //Fetch records
            var responseData = {
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
                            "values": ["Complete pick request Data Download", "Complete pick request Data Download", "Complete pick request Data Download", "Complete pick request Data Download", "Complete pick request Data Download", "Complete pick request Data Download"]
                        },
                        "1": {
                            "values": ["Complete pick request master import from host", "Complete pick request master import from host", "Complete pick request master import from host", "Complete pick request master import from host", "Complete pick request master import from host", "Complete pick request master import from host"]
                        },
                        "2": {
                            "values": ["Inbound Asynchronous", "Inbound Asynchronous", "Inbound Asynchronous", "Inbound Asynchronous", "Inbound Asynchronous", "Inbound Asynchronous"]
                        },
                        "3": {
                            "values": ["Downlaod product request", "Downlaod product request", "Downlaod product request", "Downlaod product request", "Downlaod product request", "Downlaod product request"]
                        },
                        "4": {
                            "values": ["Complete pick request", "Complete pick request", "Complete pick request", "Complete pick request", "Complete pick request", "Complete pick request"]
                        },
                        "5": {
                            "values": ["1", "1", "1", "1", "1", "1"]
                        }
                    }
                }
            };

            var gridData = responseData.data;
            var gridColumns = widgetInstanceObj.actualViewConfig.gridColumns;
            //Row builder logic. It converts the data received
            //from the server into data rows in format UIGrid requires
            responseData = factory.parseGridRecords(gridColumns, gridData);

            setTimeout(function() {
                def.resolve(responseData);
            }, 500);

            return def.promise;
        };

        // Make rest call to delete selected users.
        factory.deleteSelectedUsers=function(userList){
              var promise = RestApiService.post("users/delete", null, userList);
              return promise;
        };

        // Make rest call to disable selected users.
        factory.disableSelectedUsers=function(userList){
              var promise = RestApiService.post("users/disable", null, userList);
              return promise;
        };

        // Make rest call to enable selected users.
        factory.enableSelectedUsers=function(userList){
              var promise = RestApiService.post("users/enable", null, userList);
              return promise;
        };

        // Make rest call to retrain voice Model for selected users.
        factory.retrainVoiceLogforSelectedUsers=function(userList){
              var promise = RestApiService.post("users/retrainvoice", null, userList);
              return promise;
        };

        factory.getSupportedLanguages = function() {

            //Data is cached, no need to fetch from server again!
            if (angular.isDefined(languages)) {

                console.log("cache languages")

                //Send back promise containing languages
                var def = q.defer();
                def.resolve(languages);
                return def.promise;
            }
            else {

                languages = RestApiService.getAll("/languages").then(function(response) {

                    //initialise object
                    languages = {
                        amd : [],
                        hh : [],
                        j2u : [],
                        u2j : []
                    };

                    //loop over languages and construct array of supported languages for
                    //AMD, Device, Jen2User, User2Jen
                    for (var i = 0; i < response.length; i++) {

                        var langObj = response[i];

                        if (langObj.hasOwnProperty('amdLanguage') && angular.equals(langObj.amdLanguage, true)) {
                            languages.amd.push(langObj.languageCode);
                        }

                        if (langObj.hasOwnProperty('hhLanguage') && angular.equals(langObj.hhLanguage, true)) {
                            languages.hh.push(langObj.languageCode);
                        }

                        if (langObj.hasOwnProperty('j2uLanguage') && angular.equals(langObj.j2uLanguage, true)) {
                            languages.j2u.push(langObj.languageCode);
                        }

                        if (langObj.hasOwnProperty('u2jLanguage') && angular.equals(langObj.u2jLanguage, true)) {
                            languages.u2j.push(langObj.languageCode);
                        }
                    }

                 return languages;
                 });

                //Send back promise containing languages
                var def = q.defer();
                def.resolve(languages);
                return def.promise;
            }
        };

        //
        // Gets a list of available SHIFTS.
        // Makes an API call to "/shifts" if data has not already been cached
        //
        factory.getAvailableShifts = function () {

            //Data is cached, no need to fetch from server again!
            if (angular.isDefined(shifts)) {

                console.log("cache shifts")

                //Send back promise containing shifts
                var def = q.defer();
                def.resolve(shifts);
                return def.promise;
            }
            else {

                shifts= RestApiService.getAll("shifts").then(function(response) {
                    var shiftNames=[];
                    //loop over shifts and construct array of supported shifts
                   for (var i = 0; i < response.length; i++) {
                        var langObj = response[i];
                        if (langObj.hasOwnProperty('shiftName')) {
                            shiftNames.push(langObj.shiftName);
                        }
                    }
                    return shiftNames;
                 });

                //Send back promise containing languages
                var def = q.defer();
                def.resolve(shifts);
                return def.promise;
            }
        };

        //
        // Gets a list of available SKILLS.
        // There is no proposed endpoint for this call, so for the
        // time being they are hardcoded. This may change in the
        // future
        //
        factory.getAvailableSkills = function() {

            //If data has already been cached, no need to process
            //it again. Just return it.
            if (angular.isDefined(skills)) {

                //Send back promise containing groups
                var def = q.defer();
                def.resolve(skills);
                return def.promise;
            }
            else {
                //set and cache skills
                skills = [
                    "Basic",
                    "Standard",
                    "Advanced"
                ];

                //Send back promise containing skills
                var def = q.defer();
                def.resolve(skills);
                return def.promise;
            }
        };

        //Row builder worker function. It converts the data received
        //from the server into data rows in format UIGrid expects
        factory.parseGridRecords = function (gridColumns, gridData) {
            console.log('***[WidgetGridService.parseGridRecords]***');

            var gridRows = []; //final array containing grid records
            var row = 0;       //row counter

            //for each property in actualViewConfig, create row object and push to gridRows array
            for (var column in gridColumns) {

                var i = 0;
                //counter for array element
                var columnName = gridColumns[column].fieldName;

                var columnValues = gridData.grid[column].values;

                for (var j = 0; j < columnValues.length; j++) {

                    var tempObj = {};
                    //first time round, set up each row object and assign first property
                    if (row === 0) {
                        //Create rows
                        tempObj[columnName] = columnValues[i];
                        gridRows.push(tempObj);
                    }
                    //for every subsequent time, add property to existing rows
                    else {
                        tempObj = gridRows[i];
                        tempObj[columnName] = columnValues[j];
                    }
                    i++;
                }
                row++;
            }

            //send back rows
            return {records:gridRows, totalRecords:gridData.totalRecords};
        };

        //update the column definitions with the selected date format, so dates
        //are displayed correctly in the grid for the users locale
        factory.updateColumnDateFormat = function (columnDefinitions, dateFormat) {
            angular.forEach(columnDefinitions, function (column) {
                //set the date format
                if (column.hasOwnProperty('cellFilter')) {
                    column.cellFilter = 'date:\'' + dateFormat + '\'';
                }
            });

            return columnDefinitions;
        };

        var languageURL = "";
        var shiftURL = "";
        //Fetch data for languages

        //construct column definitions for UIGrid based on column information returned
        //in the widget definitions
        factory.buildColumnDefinitions = function (widgetInstance) {
            var columnInformation = widgetInstance.actualViewConfig.gridColumns;
            var dateFormat = (widgetInstance.actualViewConfig.hasOwnProperty('dateFormat')
            && widgetInstance.actualViewConfig.dateFormat.hasOwnProperty('selectedFormat'))
                ? widgetInstance.actualViewConfig.dateFormat.selectedFormat
                : 'dd-MM-yyyy'; //default i.e. 28-02-2015

            var columnDefinitions = [];
            angular.forEach(columnInformation, function (column) {
                var tempColumn = {};
                //Column
                if (column.hasOwnProperty('fieldName')) {
                    tempColumn.field = column.fieldName;

                    //Date format
                    //If fieldName contains "date" then set date format for this column.
                    //This eventually will need to be a more robust check - the data type should
                    //come from the server, rather than just checking for a string.
                    if (column.fieldName.toLowerCase().indexOf("date") != -1) {
                        tempColumn.cellFilter = 'date:\'' + dateFormat + '\'';
                    }
                }
                //Display name
                if (column.hasOwnProperty('name')) {
                    tempColumn.displayName = column.name;
                }
                //Column visibility - visible/hidden
                if (column.hasOwnProperty('visible')) {
                    tempColumn.visible = column.visible;
                }
                //Default sort order
                if (column.hasOwnProperty('sortOrder')
                    && (column.sortOrder > 0 || column.sortOrder < 0)) {
                    tempColumn.sort = {
                        'direction': (column.sortOrder > 0) ? 'asc' : 'desc'
                    }
                }
                //Column size
                if (column.hasOwnProperty('width')) {
                    if (column.width == 0) {
                        //default size to avoid columns all being bunched up
                        tempColumn.width = 150;
                    } else {
                        tempColumn.width = column.width;
                    }
                } else {
                    //default size to avoid columns all being bunched up
                    tempColumn.width = 150;
                }
                //Filter - visible/hidden
                if (column.hasOwnProperty('allowFilter')) {
                    tempColumn.enableFiltering = column.allowFilter;
                    tempColumn.filter={'term':null};
                }

                //filterType - ENUMERATION/ALPHANUMERIC/NUMERIC/DATE
                if (column.hasOwnProperty('filterType')) {
                    tempColumn.filterType = column.filterType;
                }

                //add column to list of columns
                columnDefinitions.push(tempColumn);
            });
            return columnDefinitions;
        };

        //returns an array of column definitions in the format UIGrid requires them
        //to be able to construct the gird correctly
        factory.getColumnDefinitions = function (widgetInstance) {
            var columnDefinitions = [];

            //if we've persisted data, then we must use that
            if (widgetInstance.actualViewConfig.hasOwnProperty('gridConfiguration')
                && widgetInstance.actualViewConfig.gridConfiguration.hasOwnProperty('columnDefinition')) {
                columnDefinitions = widgetInstance.actualViewConfig.gridConfiguration.columnDefinition;
            }
            //if we're building the grid for the first time, then a little more processing
            //is required. We have to construct the column definitions based on the column information
            //returned in the widget definition
            else {
                columnDefinitions = this.buildColumnDefinitions(widgetInstance);
            }

            return columnDefinitions;
        };

        //update the column definition width for selected column
        factory.updateColumnWidth = function (columnDefinitions, column) {
            //Find the column definition and update it with the new width
            for (var i = 0; i < columnDefinitions.length; i++) {
                if (angular.equals(columnDefinitions[i].field, column.field)) {
                    columnDefinitions[i].width = column.width;
                    break;
                }
            }

            return columnDefinitions;
        };

        //returns the sort criteria object required to send with server request for
        //grid records. If we have persisted configuration we must use it
        factory.getSortCriteria = function (actualViewConfig, columnDefinitions) {
            var sortCriteria = {};

            //check first for persisted configuration
            if (actualViewConfig.hasOwnProperty('gridConfiguration')
                && actualViewConfig.gridConfiguration.hasOwnProperty('sortCriteria')) {
                sortCriteria = actualViewConfig.gridConfiguration.sortCriteria;
            }
            else {
                //build initial search criteria from column definitions
                sortCriteria = this.buildSortCriteria(columnDefinitions);
            }


            console.log(JSON.stringify(sortCriteria))
            
            return sortCriteria;
        };

        //builds a "Sort Criteria" object based on the current sorted columns
        //sort criteria is used for server side sorting when requesting data
        factory.buildSortCriteria = function (columns) {
            var sortColumns = {};

            for (var i = 0; i < columns.length; i++) {
                //include sort criteria only for columns that are being displayed
                if (columns[i].visible === true && columns[i].hasOwnProperty('sort') && columns[i].sort.direction) {
                    sortColumns[columns[i].field] = columns[i].sort.direction.toUpperCase();
                }
            }
            return sortColumns;
        };

        //returns the filter criteria object required to send with server request for
        //grid records. If we have persisted configuration we must use it
        factory.getFilterCriteria = function (actualViewConfig) {

            var filterCriteria = {};
            //check first for persisted configuration
            if (actualViewConfig.hasOwnProperty('gridConfiguration')
                && actualViewConfig.gridConfiguration.hasOwnProperty('filterCriteria')) {
                filterCriteria = actualViewConfig.gridConfiguration.filterCriteria;
            }
            return filterCriteria;
        };

        //builds a "Filter Criteria" object based on the current sorted columns
        //filter criteria is used for server side filtering when requesting data
        factory.buildFilterCriteria = function (columns) {
            var filterColumns = {};

            for (var i = 0; i < columns.length; i++) {
                //include filter criteria only for columns that are being displayed
/*
                if (columns[i].visible === true && columns[i].hasOwnProperty('allowFilter') && columns[i].filter.term!==null) {
                    filterColumns[columns[i].field] = columns[i].filter.term;
                }
*/
                
                if (columns[i].visible === true && columns[i].filter.term!==null && columns[i].filter.term!=="null") {
                    filterColumns[columns[i].field] = columns[i].filter.term;
                }
            }

            console.log(filterColumns);
            return filterColumns;
        };



        //constructs the searchSortCriteria to send with the server request
        //example SearchSort JSON
        // {
        //    "sortMap" : {
        //        <column> : <direction>,
        //        "username" : "ASC",
        //        "lastname" : "DESC"
        //    },
        //    "searchMap" : {
        //        <column> : <[search text]>
        //        "username" : ["jill123"],
        //        "lastname" : ["Smith"]
        //    },
        //    "pageSize" : "10",
        //    "pageNumber" : "0"
        // }
        factory.buildSearchSortJSON = function (pageNumber, pageSize, search, sort) {

            var searchSortCriteria = {};

            searchSortCriteria.pageNumber = pageNumber.toString();
            searchSortCriteria.pageSize = pageSize.toString();

            //Sort criteria
            searchSortCriteria.sortMap = sort;

            //Search criteria
            searchSortCriteria.searchMap = {};
            console.log(search)
            for (var field in search) {
                var str = search[field];

                if (str!== null) {
                    searchSortCriteria.searchMap[field] = str;
                }

            }
            console.log('searchSortCriteria ' + JSON.stringify(searchSortCriteria));
            return searchSortCriteria;
        };

        factory.updateColumnSortCriteria = function (sortedColumnsArray, columnDefinition) {
            //if there are not sorted columns, remove any previous sort criteria
            //from the column definitions to reflect this
            if (sortedColumnsArray.length === 0) {
                for (var i = 0; i < columnDefinition.length; i++) {
                    if (columnDefinition[i].hasOwnProperty('sort')) {
                        delete columnDefinition[i].sort;
                    }
                }
            }
            //if we have columns then we need to ensure each of the column definitions
            //are updated with the lates
            else {
                //for every column definition, check if we have a sort criteria to update
                for (var i = 0; i < columnDefinition.length; i++) {

                    var columnHasSort = false;

                    for (var j = 0; j < sortedColumnsArray.length; j++) {
                        if (angular.equals(columnDefinition[i].field, sortedColumnsArray[j].field)) {
                            //apply the latest sort criteria to the column definition
                            columnDefinition[i].sort = sortedColumnsArray[j].sort;
                            columnHasSort = true;
                            break;
                        }
                    }

                    //remove sort from column definition if it has one that was previously applied,
                    //and we didn't find a new sort to update it with
                    if (columnHasSort === false && columnDefinition[i].hasOwnProperty('sort')) {
                        delete columnDefinition[i].sort;
                    }
                }
            }

            return columnDefinition;
        };

        //Returns a set of default set of grid options.
        //These can be overridden in the calling controller
        factory.getDefaultGridOptions = function (gridName) {
            var options = {
                //grid generics
                showGridFooter: true,
                gridFooterTemplate:'<div class="gridFooter" ng-if="grid.options.showGridFooter"><span class="total-count" ng-if="grid.options.totalRecords !== 0">{{\'user.totalCount\' | translate}}: {{grid.options.totalRecords}}</span><span ng-if="grid.selection.selectedCount !== 0 && grid.options.enableFooterTotalSelected"> ({{\'user.selectedItems\' | translate}}: {{grid.selection.selectedCount}}) </span> <span class="pause-auto-refresh" ng-if="grid.options.pauseAutoRefresh || grid.selection.selectedCount!==0"> {{\'user.autoRefreshPaused\' | translate}} </span> </div>',
                enableGridMenu: false,
                enablePaging: true,

                //grid column options
                enableColumnMenus: true,
                enableColumnMoving: true,

                //grid selection options
                enableRowSelection: true,
                enableFullRowSelection : true,
                enableRowHeaderSelection: true,
                enableSelectAll: true,
                multiSelect: true,
                noUnselect: false,

                //grid infinite scroll options
                enableInfiniteScroll: true,
                infiniteScroll: 10,

                //grid sorting options
                enableSorting: true,
                useExternalSorting: true,

                //grid filtering
                enableFiltering: true,
                useExternalFiltering: true,
                gridName:gridName
            };

            if (gridName === 'equipment-type-grid') {
                options.enableFiltering = false;
            }

            if (UtilsService.isTouchDevice()){
                options.modifierKeysToMultiSelect=false;
            }
            else{
                options.modifierKeysToMultiSelect=true;
            }
            return options;
        };

        //Takes grid configuration as input and builds data persistence object
        //to store in LocalStorage
        factory.saveGridState = function (widgetInstance, columnDefinition, filterCriteria, sortCriteria) {

            var gridConfiguration = {};

            //can't persist data if widgetInstance is not passed
            if (typeof widgetInstance === 'undefined') {
                return;
            }

            //save column definitions, if we have them
            if (typeof columnDefinition !== 'undefined'
                && columnDefinition !== null) {
                gridConfiguration.columnDefinition = columnDefinition;
            }

            //save filter criteria, if we have it
            if (typeof filterCriteria !== 'undefined'
                && filterCriteria !== null) {
                gridConfiguration.filterCriteria = filterCriteria;
            }

            //save sort criteria, if we have it
            if (typeof sortCriteria !== 'undefined'
                && sortCriteria !== null) {
                gridConfiguration.sortCriteria = sortCriteria;
            }

            //assign gridConfiguration to widgetInstance and save in local storage
            widgetInstance.actualViewConfig.gridConfiguration = gridConfiguration;
            WidgetService.updateFavoriteCanvasListLocalStorage(widgetInstance);
        };

        // helper function which paress updates/changes on columnDefinitions and make the necessary changes
        factory.parseColumnDefinitions = function(scope, newObj, oldObj) {
            //update local storage with column changes
            var gridColumns = scope.widgetdetails.actualViewConfig.gridColumns;
            var updateGridColumns = {};

            var searchMap = {};
            for (var i = 0; i < newObj.length; i++) {
                if (newObj[i].filter && newObj[i].filter.term) {
                    searchMap[newObj[i].field] = [newObj[i].filter.term];
                }
            }
            // searchMap contains filters
            scope.widgetdetails.widgetDefinition.dataURL.searchCriteria.searchMap = searchMap;

            //update sort criteria based on new Column Ddefinitions
            scope.sortCriteria = factory.buildSortCriteria(scope.columnDefinitions);

            angular.forEach(gridColumns, function(column) {
                for (var i = 0; i < newObj.length; i++) {
                    if (angular.equals(newObj[i].field, column.fieldName)) {
                        if (!updateGridColumns[i+1]) {
                            updateGridColumns[i+1] = {};
                        }

                        if (angular.equals(newObj[i].field, column.fieldName)) {
                            updateGridColumns[i+1]["allowFilter"] =  column.allowFilter;
                            updateGridColumns[i+1]["fieldName"] =  column.fieldName;
                            updateGridColumns[i+1]["name"] =  column.name;
                            updateGridColumns[i+1]["order"] =  column.order;
                            updateGridColumns[i+1]["sortOrder"] =  column.sortOrder;
                            updateGridColumns[i+1]["width"] =  newObj[i].width;
                            updateGridColumns[i+1]["visible"] = newObj[i].visible;

                            //remove any filters against the column if the column has been removed/hidden
                            if(newObj[i].visible === false
                                && scope.filterCriteria.hasOwnProperty(newObj[i].field)) {
                                delete scope.filterCriteria[newObj[i].field]
                            }
                        }
                        break;
                    }
                }
            });

            console.log(updateGridColumns);

            // Save the changes back to the actualViewConfig and persist to local storage
            // save gridColumns
            scope.widgetdetails.actualViewConfig.gridColumns = updateGridColumns;
            // save sortCriteria
            scope.widgetdetails.actualViewConfig.sortCriteria = scope.sortCriteria;
            if (scope.sortCriteria) {
                scope.widgetdetails.widgetDefinition.dataURL.searchCriteria.sortMap = scope.sortCriteria;
            }
            // save columnDefinitions
            scope.widgetdetails.actualViewConfig.columnDefinitions = scope.columnDefinitions;
            // save to LocalStorage
            WidgetService.updateFavoriteCanvasListLocalStorage(scope.widgetdetails);
        };

        //
        // Function that registers a new broadcast event to
        // transmit grid widget specific data
        //
        factory.broadcastData = function(widgetId, broadcastElements, selectedRows) {

            //Create a new broadcast object and set the widget ID
            var broadcastObj = new BroadcastObject();
            broadcastObj.setWidgetId(widgetId);

            //For each broadcast element, extract the values from the selected rows to be broadcast
            var broadcastElements = (angular.isDefined(broadcastElements)) ? broadcastElements : [];
            if (broadcastElements.length > 0) {
                // loop over each broadcast element
                for (var idx in broadcastElements) {
                    var element = broadcastElements[idx];
                    broadcastObj.addDataElement(element);

                    //for each row, pull the value for this element
                    for (var row in selectedRows) {
                        broadcastObj.addDataValue(element, selectedRows[row][element]);
                    }
                }

                //Register the broadcast event
                UtilsService.broadcast(broadcastObj);
            }
        };

        return factory;
    }
 ]);
