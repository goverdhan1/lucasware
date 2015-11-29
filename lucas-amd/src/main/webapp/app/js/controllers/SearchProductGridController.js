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

amdApp.controller('SearchProductGridController', ['$rootScope', '$log', '$scope', 'WidgetGridService', 'UtilsService', 'LocalStoreService', 'SearchProductGridService', '$modal', 'WidgetService', 'uiGridConstants', '$timeout',
    function(rootScope, log, scope, WidgetGridService, UtilsService, LocalStoreService, SearchProductGridService, modal, WidgetService, uiGridConstants, timeout) {

        //  console.log('*** WidgetDetails *** ' + JSON.stringify(scope.widgetdetails));

        //this allows us to access controller variables not assigned to scope when unit testing
        var self = this;

        //construct a widget identifier, to avoid having to do this each time we access LocalStorage
        //Example: "100|search-product-grid-widget|9"
        self.widgetIdentifier = scope.widgetdetails.clientId + "|" + scope.widgetdetails.widgetDefinition.name + "|" + scope.widgetdetails.widgetDefinition.id;

        scope.selectedRows = [];
        scope.sortCriteria = {};
        scope.filterCriteria = {};


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

        //
        //set filter/search criteria
        //
        scope.filterCriteria = WidgetGridService.getFilterCriteria(scope.widgetdetails.actualViewConfig);



        //function to define the grid dimensions
        self.setGridHeight = function() {
            console.log("--- setting grid height ---");
            self.gridHeight = scope.widgetdetails.actualViewConfig.height - 120;
            //
            //NOT ABLE TO UNIT TEST JQUERY SELECTOR
            //
            if (scope.widgetdetails.isMaximized) {
                self.gridHeight = $(".panel-body").height() - 10;
                $(".gridStyle .ngViewport").css({
                    "height": self.gridHeight + "px"
                });
            } else {
                console.log("not maximised..");
                //$(".gridStyle .ngViewport").css({
                $(".gridStyle").css({
                    "height": self.gridHeight + "px"
                });
            }
        };

        //get default options. Any of these can be overridden
        //for example, if you wanted to turn off sorting you can do:
        //  scope.gridOptions.enableSorting = false;
        scope.gridOptions = WidgetGridService.getDefaultGridOptions('search-product-grid');

        //Set column and Data options specific to this grid
        scope.gridOptions.columnDefs = scope.columnDefinitions;
        scope.gridOptions.data = scope.gridRecords;
        //    scope.gridOptions.prototype.gridName="searchProductGrid";

        //Register grid events for interactions
        scope.gridOptions.onRegisterApi = function(grid) {
            //set scope object for grid interaction
            scope.gridApi = grid;
            self.setGridHeight();



            //
            //Register scrolling event
            //
            scope.gridApi.infiniteScroll.on.needLoadMoreData(scope, function() {
                console.log('Triggered infinite scroll');
                scope.loadRecords(true);
            });

            //
            //Register row selection event
            //
            scope.gridApi.selection.on.rowSelectionChanged(scope, function(row) {
                //get the new array of selected rows
                scope.selectedRows = scope.gridApi.selection.getSelectedRows();

                //Issue the broadcast to listening widgets
                if (scope.widgetdetails.widgetDefinition.broadcastList) {
                    var getItemsAsArr = UtilsService.processSelectedItemsAsArray(scope.selectedRows, scope.widgetdetails.widgetDefinition.broadcastList);
                    scope.$broadcast('fromcontroller', getItemsAsArr);
                }
            });

            //
            //Register batch selection event (i.e. Select All, Unselect All)
            //
            scope.gridApi.selection.on.rowSelectionChangedBatch(scope, function(rows) {
                scope.selectedRows = scope.gridApi.selection.getSelectedRows();
            });


            //
            // Register sorting event
            //

            scope.gridApi.core.on.sortChanged(scope, function(grid, columns) {
                //update sort criteria on column definition according to latest sort
                scope.columnDefinitions = WidgetGridService.updateColumnSortCriteria(columns, scope.columnDefinitions);

                //construct the sort criteria to send with the server request for data
                scope.sortCriteria = WidgetGridService.buildSortCriteria(columns);

                //clear the grid and fetch sorted records from server
                scope.clearGrid();
                scope.loadRecords(false);
            });

            //
            //Register filter event
            //
            /*
                    scope.gridApi.core.on.filterChanged(scope, function() {


                            if (angular.isDefined(scope.filterTimeout)) {
                                timeout.cancel(scope.filterTimeout);
                            }

                            scope.filterTimeout = timeout(function () {

                                // construct the filter criteria to send with the server request for data
                                scope.filterCriteria = WidgetGridService.buildFilterCriteria(scope.gridOptions.columnDefs);

                                // clear the grid and fetch filter records from server
                                scope.clearGrid();

                                scope.loadRecords(false);

                            }, 2000);
                    });
            */


            scope.$on('searchOnEnterKey', function() {

                timeout.cancel(scope.filterTimeout);

                scope.filterCriteria = WidgetGridService.buildFilterCriteria(scope.gridOptions.columnDefs);

                // clear the grid and fetch filter records from server
                scope.clearGrid();

                scope.loadRecords(false);


            });

            scope.$on('searchOnFilterChanged', function() {

                if (angular.isDefined(scope.filterTimeout)) {
                    timeout.cancel(scope.filterTimeout);
                }

                scope.filterTimeout = timeout(function() {

                    // construct the filter criteria to send with the server request for data
                    scope.filterCriteria = WidgetGridService.buildFilterCriteria(scope.gridOptions.columnDefs);

                    // clear the grid and fetch filter records from server
                    scope.clearGrid();

                    scope.loadRecords(false);

                }, 2000);

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

        //load product records from DB
        scope.loadRecords = function(append) {

            var sort = scope.sortCriteria;
            var filter = scope.filterCriteria;
            var page = self.pageNumber;
            var size = self.pageSize;

            // Hack to take care of the anomaly from back end where 
            // this column has been removed but stil comes in the data
            // when the end point is invoked.
            // remove once it has been taken care of from BE
            if (sort.headsUpMessages) {
                delete sort.headsUpMessages;
            }

            //load up next batch of records
            WidgetGridService.getRecords(scope.widgetdetails, sort, filter, page, size)
                .then(function(gridRows) {

                    if (append === true) {
                        //add new records to existing data set
                        scope.gridRecords = scope.gridRecords.concat(gridRows);
                    } else {
                        //Scroll to top of grid, before loading new rows. If we don't do this,
                        //the position of the scroll bar will trigger infinite scroll event and load
                        //a second set of records
                        //
                        //There may be a better "AngularJS" way to do this...
                        $('.ui-grid-render-container .ui-grid-viewport').scrollTop(0);

                        scope.gridRecords = angular.copy(gridRows);
                    }

                    //push rows to the grid and inform data has finished
                    //loading. This will re-enable the listener for scrolling
                    scope.gridOptions.data = scope.gridRecords;
                    scope.gridApi.infiniteScroll.dataLoaded();

                    //increment for next pagination
                    self.pageNumber++;

                }, function(error) {
                    console.log('server request error - no records loaded');
                    scope.gridApi.infiniteScroll.dataLoaded();
                });
        };

        //Load first set of records when initialising the grid
        scope.loadRecords(false);

        //clears/resets the grid data
        scope.clearGrid = function() {
            //unselect any selected rows
            scope.gridApi.selection.clearSelectedRows();

            //reset pagination so we get first set of records
            self.pageNumber = 0;
        };

        //reset filter input
        scope.resetFilters = function() {

            //clear filters
            scope.filterCriteria = {};

            //clear sort criteria, including resetting the sort on the column definition
            scope.gridApi.grid.resetColumnSorting();
            scope.sortCriteria = {};
            scope.columnDefinitions = WidgetGridService.updateColumnSortCriteria([], scope.columnDefinitions);

            //clear grid and load with new data
            scope.clearGrid();
            scope.loadRecords(false);
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

        scope.launchProductClassifications =  scope.$on("launchProductClassifications",function(event,clientID){
            if(clientID != scope.widgetdetails.clientId) return;
            modal.open({
                templateUrl: 'views/modals/product-classifications.html',
                controller: 'ProductClassificationsModalController',
                backdrop: 'static' 
            });
        });

        //Perform any business logic before the current state of the scope is lost.
        //Typical behaviour here includes saving any data for persistence etc.
        scope.$on('$destroy', function() {            
            if (angular.isDefined(scope.filterTimeout)) {
                timeout.cancel(scope.filterTimeout);
            }
        });
    }
]);
