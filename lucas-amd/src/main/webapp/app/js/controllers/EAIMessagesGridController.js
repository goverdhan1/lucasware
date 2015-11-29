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

amdApp.controller('EAIMessagesGridController', ['$rootScope', '$log', '$scope', '$compile', 'WidgetGridService', 'UtilsService', 'LocalStoreService', '$modal', 'WidgetService', 'uiGridConstants', '$timeout',
    function(rootScope, log, scope, compile, WidgetGridService, UtilsService, LocalStoreService, modal, WidgetService, uiGridConstants, timeout) {

        //this allows us to access controller variables not assigned to scope when unit testing
        var self = this;

        self.widgetIdentifier = scope.widgetdetails.clientId + "|" + scope.widgetdetails.widgetDefinition.name + "|" + scope.widgetdetails.widgetDefinition.id;

        scope.selectedRows = [];
        scope.selectedRow = {};
        scope.sortCriteria = {};
        scope.filterCriteria = {};
        scope.selectedMessages = [];

        //set pageNumber and pageSize
        self.pageNumber = 0;
        self.pageSize = scope.widgetdetails.widgetDefinition.dataURL.searchCriteria.pageSize;

        //
        //set column definitions
        //
        scope.columnDefinitions = WidgetGridService.getColumnDefinitions(scope.widgetdetails);

        //
        //set sort criteria
        //
        scope.sortCriteria = WidgetGridService.getSortCriteria(scope.widgetdetails.actualViewConfig, scope.columnDefinitions);

        // initialize filterCriteria
        if (scope.filterCriteria)
            scope.filterCriteria = {};

        // initialize search and sort based on the dataURL
        if (scope.widgetdetails.widgetDefinition.dataURL && scope.widgetdetails.widgetDefinition.dataURL.searchCriteria) {
            // initialize search based on the dataURL
            if (scope.widgetdetails.widgetDefinition.dataURL.searchCriteria.searchMap) {
                angular.forEach(scope.widgetdetails.widgetDefinition.dataURL.searchCriteria.searchMap, function(value, key) {
                    scope.filterCriteria[key] = value[0];
                    var col = _.where(scope.columnDefinitions, {
                        field: key
                    });
                    if (col.length > 0) {
                        col[0].filter.term = value[0];
                    }
                });
            }
            // initialize sort based on the dataURL
            if (scope.widgetdetails.widgetDefinition.dataURL.searchCriteria.sortMap) {
                // empty the sort
                angular.forEach(scope.columnDefinitions, function(value, key) {
                    value.sort = {};
                });

                angular.forEach(scope.widgetdetails.widgetDefinition.dataURL.searchCriteria.sortMap, function(value, key) {
                    var col = _.where(scope.columnDefinitions, {
                        field: key
                    });
                    if (col.length > 0) {
                        if (!col[0].sort) {
                            col[0].sort = {};
                        }
                        col[0].sort.direction = value.toLowerCase();
                    }
                });

                scope.sortCriteria = scope.widgetdetails.widgetDefinition.dataURL.searchCriteria.sortMap;
            }
        }

        //get default options. Any of these can be overridden
        //for example, if you wanted to turn off sorting you can do:
        //  scope.gridOptions.enableSorting = false;
        scope.gridOptions = WidgetGridService.getDefaultGridOptions('eai-messages-grid');
        // Set column and Data options specific to this grid
        scope.gridOptions.columnDefs = scope.columnDefinitions;
        scope.gridOptions.data = scope.gridRecords;

        //Register grid events for interactions
        scope.gridOptions.onRegisterApi = function(grid) {
            //set scope object for grid interaction
            scope.gridApi = grid;
            //
            //Register scrolling event
            //
            scope.gridApi.infiniteScroll.on.needLoadMoreData(scope, function() {
                console.log('Triggered infinite scroll');
                scope.loadRecords(true);
            });

            //
            // Register row selection event
            //
            scope.gridApi.selection.on.rowSelectionChanged(scope, function(row) {
                scope.selectedMessages = [];

                //get the new array of selected rows
                scope.selectedRows = scope.gridApi.selection.getSelectedRows();

                if( 1 == scope.selectedRows.length) {
                    scope.selectedRow.message = scope.selectedRows[0].name;
                }

                //Broadcast widget data
                WidgetGridService.broadcastData(scope.widgetdetails.clientId, scope.widgetdetails.widgetDefinition.broadcastList, scope.selectedRows);
            });

            //
            //Register batch selection event (i.e. Select All, Unselect All)
            //
            scope.gridApi.selection.on.rowSelectionChangedBatch(scope, function(rows) {
                scope.selectedRows = scope.gridApi.selection.getSelectedRows();
                scope.selectedMessages = [];
            });

            //
            //Register column move event
            //

            scope.gridApi.colMovable.on.columnPositionChanged(scope, function(col, oldPosition, newPosition) {
                if (oldPosition !== newPosition) {
                    console.log('--- Moved Column: ' + col.field + '---');

                    //Update column definitions to reflect new column position,
                    //and notify the grid of changes via the api
                    scope.columnDefinitions.splice(newPosition, 0, scope.columnDefinitions.splice(oldPosition, 1)[0]);
                    scope.gridApi.core.notifyDataChange(scope.gridApi.grid, uiGridConstants.dataChange.COLUMN);
                }
            });

            //
            //Register column resize event
            //
            scope.gridApi.colResizable.on.columnSizeChanged(scope, function(col, changeDelta) {

                //set new column width, and notify ui-grid of column changes via the grid api
                scope.columnDefinitions = WidgetGridService.updateColumnWidth(scope.columnDefinitions, scope.gridApi.grid.getColumn(col.field));
                scope.gridApi.core.notifyDataChange(scope.gridApi.grid, uiGridConstants.dataChange.COLUMN);
            });
        };

        /**
         * LOCAL FUNCTIONS
         */

        //load Message records from DB
        scope.loadRecords = function(append) {

            var sort = scope.sortCriteria;
            var filter = scope.filterCriteria;
            var page = self.pageNumber;
            var size = self.pageSize;

            //load up next batch of records
            WidgetGridService.getEAIMessages(scope.widgetdetails, sort, filter, page, size)
                .then(function(gridRows) {

                    if (append === true) {
                        //add new records to existing data set
                        scope.gridRecords = scope.gridRecords.concat(gridRows.records);
                    } else {
                        //Scroll to top of grid, before loading new rows. If we don't do this,
                        //the position of the scroll bar will trigger infinite scroll event and load
                        //a second set of records
                        //
                        //There may be a better "AngularJS" way to do this...
                        $('.ui-grid-render-container .ui-grid-viewport').scrollTop(0);

                        scope.gridRecords = angular.copy(gridRows.records);
                    }

                    //push rows to the grid and inform data has finished
                    //loading. This will re-enable the listener for scrolling
                    scope.gridOptions.data = scope.gridRecords;
                    scope.gridOptions.totalRecords = gridRows.totalRecords

                    if (scope.gridApi && scope.gridApi.infiniteScroll != "undefined") {
                        scope.gridApi.infiniteScroll.dataLoaded();
                    }

                    //increment for next pagination
                    self.pageNumber++;

                }, function(error) {
                    console.log('server request error - no records loaded');
                    scope.gridApi.infiniteScroll.dataLoaded();
                });
        };

        //Load first set of records when initialising the grid
        scope.loadRecords(false);

        scope.launchMessageDefinitionButton = function() {
            return scope.selectedRows.length == 1;
        };

        scope.launchMessageDefinition = function(index) {
            modal.open({
                templateUrl: 'views/modals/eai-message-definition.html',
                controller: 'EaiMessageDefinitionModalController',
                backdrop: 'static',
                windowClass: 'eai-message-segment-window',
                resolve: {
                    messageName: function() {
                        return (index == 0) ? '' : scope.selectedRow.message;
                    }
                }
            });
        };

        // Adding action buttons
        var widgetHeader = angular.element("#wid"+scope.widgetdetails.clientId).find(".panel-heading");
        var actionButtons = '<button class="btn-small btn-success" id="launchMessageDefinition" ng-click="launchMessageDefinition()" ng-if="launchMessageDefinitionButton()" tooltip-placement="right" tooltip-append-to-body="true" tooltip="Definition"><i class="glyphicon glyphicon-list"></i></button>' +
                            '<button class="btn-small btn-success" id="launchMessageDefinitionNew" ng-click="launchMessageDefinition(0)" tooltip-placement="right" tooltip-append-to-body="true" tooltip="New"><span>New</span></button>';

        actionButtons = angular.element(actionButtons);
        var compileButtons = compile(actionButtons)(scope);
        actionButtons.appendTo(widgetHeader);

        //clears/resets the grid data
        scope.clearGrid = function() {
            //unselect any selected rows
            scope.gridApi.selection.clearSelectedRows();

            //reset pagination so we get first set of records
            self.pageNumber = 0;
        };

        //fetch results from server
        scope.fetchLiveResults = function() {
            //clear grid and load with new data
            self.pageNumber = 0;
            scope.loadRecords(false);
        };

        /**
         * EVENT LISTENERS/WATCHERS
         */

        //Watch for changes to column definitions. When they change, we must reflect the changes
        //in the actualViewConfig so the state can be maintained
        scope.$watch('columnDefinitions', function(newObj, oldObj) {
            if (!angular.equals(newObj, oldObj)) {
                // Persist current state of grid
                WidgetGridService.saveGridState(scope.widgetdetails, scope.columnDefinitions, scope.filterCriteria, scope.sortCriteria);
                WidgetGridService.parseColumnDefinitions(scope, newObj, oldObj);
            }
        }, true);

        //This is triggered from any Cog Menu changes. It's job is to apply the changes made in the
        //cog menu to the current grid view
        scope.$on('updateExistingWidget', function(event, args) {
            //make sure we only perform this action when the call is made for
            //this widgetInstance only
            if (args.clientId == scope.widgetdetails.clientId) {

                //if the date format was changed, then we must update the
                //column definitions to reflect this, so the dates are correctly displayed
                //in the grid
                var oldDateFormat = (scope.widgetdetails.actualViewConfig.hasOwnProperty('dateFormat') && scope.widgetdetails.actualViewConfig.dateFormat.hasOwnProperty('selectedFormat')) ? scope.widgetdetails.actualViewConfig.dateFormat.selectedFormat : null;
                var newDateFormat = (args.actualViewConfig.hasOwnProperty('dateFormat') && args.actualViewConfig.dateFormat.hasOwnProperty('selectedFormat')) ? args.actualViewConfig.dateFormat.selectedFormat : null;
                if (!angular.equals(newDateFormat, oldDateFormat)) {
                    scope.columnDefinitions = WidgetGridService.updateColumnDateFormat(scope.columnDefinitions, newDateFormat);
                }

                //assign cog menu changes to local widgetInstance so we are working with
                //a consistent object
                scope.widgetdetails = args;

                //now make the necessary changes to the column definitions to
                //update the grid view (show/hide columns etc.)
                var gridColumns = scope.widgetdetails.actualViewConfig.gridColumns;
                angular.forEach(gridColumns, function(column) {
                    for (var i = 0; i < scope.columnDefinitions.length; i++) {
                        if (angular.equals(scope.columnDefinitions[i].field, column.fieldName) && !angular.equals(scope.columnDefinitions[i].visible, column.visible)) {
                            scope.columnDefinitions[i].visible = column.visible;
                        }
                    }
                });

                //notify ui-grid of column changes via the grid api
                scope.gridApi.core.notifyDataChange(scope.gridApi.grid, uiGridConstants.dataChange.COLUMN);
            }
        });

        // update the id for the newly created widget
        var updateWidgetId = rootScope.$on('updateWidgetId', function(event, widgetId, clientId) {
            if (scope.widgetdetails.clientId == clientId) {
                scope.widgetdetails.id = widgetId;
            }
        });

        //Perform any business logic before the current state of the scope is lost.
        //Typical behaviour here includes saving any data for persistence etc.
        scope.$on('$destroy', function() {
            if (angular.isDefined(scope.filterTimeout)) {
                timeout.cancel(scope.filterTimeout);
            }
            if (updateWidgetId) {
                updateWidgetId();
            }
        });
    }
]);