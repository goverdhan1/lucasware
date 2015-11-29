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
amdApp.directive('moveDataGrid', ['UtilsService', '$parse', '$timeout',
    function(UtilsService, $parse, $timeout) {
        var link = function(scope, elem, attr) {

            scope.gridOptions = {
                data: [],

                //grid column options
                enableColumnMenus: true,
                enableColumnMoving: true,

                //grid selection options
                enableRowSelection: true,
                enableFullRowSelection: true,
                enableRowHeaderSelection: true,
                enableSelectAll: false,
                multiSelect: true,
                noUnselect: false,

                //grid sorting options
                enableSorting: true,
                useExternalSorting: true,

                //grid filtering
                enableFiltering: true,
                useExternalFiltering: true
            };

            scope.existingGridOptions = {
                data: [],

                //grid column options
                enableColumnMenus: true,
                enableColumnMoving: true,

                //grid selection options
                enableRowSelection: true,
                enableFullRowSelection: true,
                enableRowHeaderSelection: true,
                enableSelectAll: false,
                multiSelect: true,
                noUnselect: false,

                //grid sorting options
                enableSorting: true,
                useExternalSorting: true,

                //grid filtering
                enableFiltering: true,
                useExternalFiltering: true
            };


            if (UtilsService.isTouchDevice()) {
                scope.existingGridOptions.modifierKeysToMultiSelect = false;
                scope.gridOptions.modifierKeysToMultiSelect = false;
            } else {
                scope.existingGridOptions.modifierKeysToMultiSelect = true;
                scope.gridOptions.modifierKeysToMultiSelect = true;
            }

            if (scope.widgetgriddetails && scope.widgetgriddetails.availableItems) {
                scope.gridOptions.data = scope.widgetgriddetails.availableItems;
            }

            scope.$watch(function() {
                return scope.widgetgriddetails;
            }, function(newValue, oldValue) {
                if (newValue) {
                    if (newValue.existingItems) {
                        scope.existingGridOptions.data = scope.widgetgriddetails.existingItems;
                        if (typeof scope.existingGridOptions.columnDefs != "undefined" && scope.existingGridOptions.columnDefs.length > 0) {
                            for (var i = 0, l = scope.existingGridOptions.columnDefs.length; i < l; i++) {
                                scope.existingGridOptions.columnDefs[i]["width"] = 150;
                            }
                        } else {
                            scope.existingGridOptions.columnDefs = [];
                            if (scope.existingGridOptions.data.length > 0) {
                                angular.forEach(scope.existingGridOptions.data[0], function(val, key) {
                                    scope.existingGridOptions.columnDefs.push({
                                        enableColumnMoving: true,
                                        enableColumnResizing: true,
                                        name: key,
                                        type: "string",
                                        width: 150
                                    });
                                });
                            }
                        }
                    } else {
                        scope.existingGridOptions.data = [];
                    }

                    if (newValue.availableItems) {
                        scope.gridOptions.data = scope.widgetgriddetails.availableItems;
                        if (typeof scope.gridOptions.columnDefs != "undefined" && scope.gridOptions.columnDefs.length > 0) {
                            for (var i = 0, l = scope.gridOptions.columnDefs.length; i < l; i++) {
                                scope.gridOptions.columnDefs[i]["width"] = 150;
                            }
                        } else {
                            scope.gridOptions.columnDefs = [];
                            if (scope.gridOptions.data.length > 0) {
                                angular.forEach(scope.existingGridOptions.data[0], function(val, key) {
                                    scope.gridOptions.columnDefs.push({
                                        enableColumnMoving: true,
                                        enableColumnResizing: true,
                                        name: key,
                                        type: "string",
                                        width: 150
                                    });
                                });
                            }
                        }
                    } else {
                        scope.gridOptions.data = [];
                    }

                    if (scope.existingGridOptions.data) {
                        filterBasedOnExistingItems();
                    }

                    $timeout(function() {
                        UtilsService.triggerWindowResize();
                    });
                }
            }, true);

            scope.gridOptions.onRegisterApi = function(grid) {
                //set scope object for grid interaction
                scope.gridApi = grid;

                //
                // Register row selection events
                //
                scope.gridApi.selection.on.rowSelectionChanged(scope, function(row) {
                    scope.widgetgriddetails.selectedAvailableItems = scope.gridApi.selection.getSelectedRows();
                });

                scope.gridApi.selection.on.rowSelectionChangedBatch(scope, function(rows) {
                    scope.widgetgriddetails.selectedAvailableItems = scope.gridApi.selection.getSelectedRows();
                });
            };

            scope.existingGridOptions.onRegisterApi = function(grid) {
                //set scope object for grid interaction
                scope.existingGridApi = grid;

                //
                // Register row selection events
                //
                scope.existingGridApi.selection.on.rowSelectionChanged(scope, function(row) {
                    scope.widgetgriddetails.selectedExistingItems = scope.existingGridApi.selection.getSelectedRows();
                });

                scope.existingGridApi.selection.on.rowSelectionChangedBatch(scope, function(rows) {
                    scope.widgetgriddetails.selectedExistingItems = scope.existingGridApi.selection.getSelectedRows();
                });
            };


            /**
             * Maintains a scope level variable to control the template controls.
             * The $observe function gets read on every scope digest cycle so it
             * will keep the form controls consistently rendered in the correct state
             */
            attr.$observe('isDisabled', function() {
                scope.isDisabled = attr.isDisabled == "true";
            });

            var filterBasedOnExistingItems = function() {
                if (scope.widgetgriddetails && scope.widgetgriddetails.availableItems) {
                    scope.gridOptions.data = scope.widgetgriddetails.availableItems.filter(function(item, pos) {
                        return UtilsService.findIndexByProperty(scope.widgetgriddetails.existingItems, 'userName', item['userName']) == null;
                    });
                    if (!scope.gridOptions.data) {
                        scope.gridOptions.data = [];
                    }
                }
            };

            /**
             *  Moves the selected available records to existing records
             */
            scope.moveOneToExistingData = function() {
                var i, index;
                for (i = 0; i < scope.widgetgriddetails.selectedAvailableItems.length; i++) {
                    scope.widgetgriddetails.existingItems.push(scope.widgetgriddetails.selectedAvailableItems[i]);
                }
                scope.widgetgriddetails.selectedAvailableItems = [];

                filterBasedOnExistingItems();

                scope.existingGridOptions.data = scope.widgetgriddetails.existingItems;
            };

            /**
             *  Moves all the existing records to available records irrespective of the user selection
             */
            scope.moveAllToExistingData = function() {
                var i, index;
                for (i = 0; i < scope.widgetgriddetails.availableItems.length; i++) {
                    if (scope.widgetgriddetails.existingItems.indexOf(scope.widgetgriddetails.availableItems[i]) == -1) {
                        scope.widgetgriddetails.existingItems.push(scope.widgetgriddetails.availableItems[i]);
                    }
                }
                scope.widgetgriddetails.selectedAvailableItems = [];
                scope.existingGridOptions.data = scope.widgetgriddetails.existingItems;
                scope.gridOptions.data = [];
            };

            /**
             *  Moves the selected existing records to available records
             */
            scope.moveOneToAvailableData = function() {
                var i, index;
                if (scope.widgetgriddetails && scope.widgetgriddetails.selectedExistingItems && scope.widgetgriddetails.selectedExistingItems.length > 0) {
                    for (i = 0; i < scope.widgetgriddetails.selectedExistingItems.length; i++) {
                        index = scope.widgetgriddetails.existingItems.indexOf(scope.widgetgriddetails.selectedExistingItems[i]);
                        if (index != -1) {
                            scope.widgetgriddetails.existingItems.splice(index, 1);
                        }
                    }
                    scope.widgetgriddetails.selectedExistingItems = [];
                    scope.existingGridOptions.data = scope.widgetgriddetails.existingItems;
                    filterBasedOnExistingItems();
                }
            };


            /**
             *  Moves all the available records to existing records irrespective of the user selection
             */
            scope.moveAllToAvailableData = function() {
                // to empty all references
                scope.widgetgriddetails.existingItems.length = 0;
                scope.widgetgriddetails.selectedExistingItems = [];

                scope.existingGridOptions.data = [];
                scope.gridOptions.data = scope.widgetgriddetails.availableItems;
            };
        };

        return {
            restrict: 'EA',
            scope: {
                "widgetgriddetails": "="
            },
            templateUrl: "views/move-data-grid.html",
            link: link
        };
    }
]);