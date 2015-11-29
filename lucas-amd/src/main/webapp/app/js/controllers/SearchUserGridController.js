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

amdApp.controller('SearchUserGridController', ['$rootScope', '$log', '$scope', '$compile', 'WidgetGridService', 'UtilsService', 'LocalStoreService', 'SearchUserGridService', '$modal', 'WidgetService', 'uiGridConstants', '$timeout', '$interval', 'PreferencesService', 'UserService',
function(rootScope, log, scope, compile, WidgetGridService, UtilsService, LocalStoreService, SearchUserGridService, modal, WidgetService, uiGridConstants, timeout, $interval, PreferencesService, UserService) {

       //this allows us to access controller variables not assigned to scope when unit testing
       var self = this;

        //construct a widget identifier, to avoid having to do this each time we accessdeleteSelectedUsers LocalStorage
        //Example: "100|search-user-grid-widget|9"
        self.widgetIdentifier = scope.widgetdetails.clientId + "|" + scope.widgetdetails.widgetDefinition.name + "|" + scope.widgetdetails.widgetDefinition.id;

        scope.selectedRows = [];
    scope.sortCriteria = {};
    scope.filterCriteria = {};
    scope.selectedUsers=[];

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
                var col = _.where(scope.columnDefinitions, {field: key });
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
                var col = _.where(scope.columnDefinitions, {field: key });
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

    //opens up the modal for delete users
    scope.deleteSelectedUsers=function(){

        if(scope.selectedUsers.length>0){
            var modalInstance = modal.open({
                templateUrl : "views/modals/delete-user-confirmation.html",
                scope : scope,
                backdrop: 'static' 
            });

            modalInstance.result.then(function () {
                WidgetGridService.deleteSelectedUsers(scope.selectedUsers).then(function(result){
                    if(result.status=="success")
                    {
                        //clear the grid and fetch sorted records from server
                        scope.gridApi.selection.clearSelectedRows();
                        if(self.pageNumber>0)
                        {
                            self.pageSize=self.pageSize*self.pageNumber;
                        }
                        scope.clearGrid();
                        scope.loadRecords(false);
                        self.pageSize=scope.widgetdetails.widgetDefinition.dataURL.searchCriteria.pageSize;
                    }
                });
            }, function (error) {
                console.log(error);
                return error;
            });
        }
    };

    //opens up the modal for disable users
    scope.disableSelectedUsers=function(){
        if(scope.selectedUsers.length>0){
            var modalInstance = modal.open({
                templateUrl : "views/modals/disable-user-confirmation.html",
                scope : scope,
                backdrop: 'static'
            });

            modalInstance.result.then(function () {
                WidgetGridService.disableSelectedUsers(scope.activeUsers).then(function(result){
                    if(result.status=="success")
                    {
                        //clear the grid and fetch sorted records from server
                        scope.gridApi.selection.clearSelectedRows();
                        if(self.pageNumber>0)
                        {
                            self.pageSize=self.pageSize*self.pageNumber;
                        }
                        scope.clearGrid();
                        scope.loadRecords(false);
                        self.pageSize=scope.widgetdetails.widgetDefinition.dataURL.searchCriteria.pageSize;
                    }
                });
            }, function (error) {
                console.log(error);
                return error;
            });
        }
    }

    //opens up the modal for disable users
    scope.enableSelectedUsers=function(){
        if(scope.selectedUsers.length>0){
            var modalInstance = modal.open({
                templateUrl : "views/modals/enable-user-confirmation.html",
                scope : scope,
                backdrop: 'static'
            });

            modalInstance.result.then(function () {
                WidgetGridService.enableSelectedUsers(scope.activeUsers).then(function(result){
                    if(result.status=="success")
                    {
                        //clear the grid and fetch sorted records from server
                        scope.gridApi.selection.clearSelectedRows();
                        if(self.pageNumber>0)
                        {
                            self.pageSize=self.pageSize*self.pageNumber;
                        }
                        scope.clearGrid();
                        scope.loadRecords(false);
                        self.pageSize=scope.widgetdetails.widgetDefinition.dataURL.searchCriteria.pageSize;
                    }
                });
            }, function (error) {
                console.log(error);
                return error;
            });
        }
    }

    //opens up the modal for retrain voice log users
    scope.retrainVoiceLog=function(){
            var modalInstance = modal.open({
                templateUrl : "views/modals/retrain-voice-log-user-confirmation.html",
                scope : scope,
                backdrop: 'static'
            });

            modalInstance.result.then(function () {
                WidgetGridService.retrainVoiceLogforSelectedUsers(scope.selectedUsers).then(function(result){
                    if(result.status=="success")
                    {
                        //clear the grid and fetch sorted records from server
                        scope.gridApi.selection.clearSelectedRows();
                        if(self.pageNumber>0)
                        {
                            self.pageSize=self.pageSize*self.pageNumber;
                        }
                        scope.clearGrid();
                        scope.loadRecords(false);
                        self.pageSize=scope.widgetdetails.widgetDefinition.dataURL.searchCriteria.pageSize;
                    }
                });
            }, function (error) {
                console.log(error);
                return error;
            });
    }

    //method to enable/disable delete button based on user selection
    scope.enableDeleteBtn=function(){
        var flag=true;
            if(scope.selectedUsers.length)
            {
                flag=false;
            }
            return flag;
    }

    //method to enable/disable disable button based on user selection
    scope.enableDisableBtn=function(){
        var flag=true;
        if(scope.selectedUsers.length)
        {
            flag=false;
        }
        return flag;
    }

    //method to enable/disable enable button based on user selection
    scope.enableEnableBtn=function(){
        var flag=true;
        if(scope.selectedUsers.length)
        {
            flag=false;
        }
        return flag;
    }

    //method to enable/disable retrain voice log button based on user selection
    scope.enableVoiceLogBtn=function(){
        var flag=true;
        if(scope.selectedUsers.length)
        {
            flag=false;
        }
        return flag;
    }

    //method to show/hide delete users button based on permissions
    scope.showDeleteBtn=function(){
        return scope.widgetdetails.widgetDefinition.widgetActionConfig["widget-actions"]["delete-user"];
    }

    //method to show/hide disable users button based on permissions
    scope.showDisableBtn=function(){
        return scope.widgetdetails.widgetDefinition.widgetActionConfig["widget-actions"]["disable-user"];
    }

    //method to show/hide enable users button based on permissions
    scope.showEnableBtn=function(){
        return scope.widgetdetails.widgetDefinition.widgetActionConfig["widget-actions"]["enable-user"];
    }

    //method to show/hide retrain voice log button based on permissions
    scope.showVoiceLogBtn=function(){
        return scope.widgetdetails.widgetDefinition.widgetActionConfig["widget-actions"]["retrain-voice-model"];
    }

    //method to manually refersh search users grid. 
    scope.searchUserGridManualRefresh=function(){
        //clear the grid and fetch sorted records from server
        //scope.gridApi.selection.clearSelectedRows();
            if(self.pageNumber>0)
            {
                self.pageSize=self.pageSize*self.pageNumber;
            }
                scope.clearGrid();
                scope.loadRecords(false);
                self.pageSize=scope.widgetdetails.widgetDefinition.dataURL.searchCriteria.pageSize;
    };

   //method to auto refresh search users grid.
    var autoRefreshPromise;
    scope.searchUserGridAutoRefresh = function(){
        //first cancel any existing intervals
        $interval.cancel(autoRefreshPromise);

        var globalOverride = scope.widgetdetails.actualViewConfig.autoRefreshConfig.globalOverride;
        var refreshEnabled = scope.widgetdetails.actualViewConfig.autoRefreshConfig.enabled;
        var refreshInterval = scope.widgetdetails.actualViewConfig.autoRefreshConfig.interval;

        //if we have any rows selected, then we must NOT restart the auto refresh interval.
        //auto data refresh should remain paused if we have selected rows
        if (scope.selectedUsers.length) return;

        //if auto refresh is configured at the widget level, use these setting, else
        //use the global settings from user preferences
        if(globalOverride)
        {
            //If we have disabled auto-refresh at the widget level, then do not start
            //another interval, else we want the interval run at the specified intervals
            if(refreshEnabled){
                //scope.refreshInterval = refreshInterval;
                autoRefreshPromise = $interval(function(){
                    console.log("Auto Refresh at Widget Level - Interval: " + refreshInterval);
                    //refresh the grid
                    scope.clearGrid();
                    scope.loadRecords(false);
                }, refreshInterval*1000, false); //multiply by 1000 to convent to seconds
            }
        } else {
            //get the auto refresh interval from global User Preferences
            PreferencesService.getUserPreferences(UserService.getCurrentUser()).then(function(preferences) {

                //start the new interval
                autoRefreshPromise = $interval(function(){
                    console.log("Auto Refresh at Global Level - Interval: " + preferences.dataRefreshFrequency);
                    //refresh grid data
                    scope.clearGrid();
                    scope.loadRecords(false);
                }, preferences.dataRefreshFrequency*1000, false); //multiply by 1000 to convert to seconds
            });
        }
    };

    //initialize auto refersh for search user grid
    scope.searchUserGridAutoRefresh();

    scope.unfilterSearchUsers=function(){
        scope.selectedRows = [];
        scope.selectedUsers=[];
        scope.activeUsers=[];
        scope.disabledUsers=[];

        //set pageNumber and pageSize
        self.pageNumber = 0;
        self.pageSize = scope.widgetdetails.widgetDefinition.dataURL.searchCriteria.pageSize;
             
        //reset filter/search criteria
        scope.filterCriteria =  {};

        for(var i=0; i<scope.columnDefinitions.length; i++)
        {
            scope.gridOptions.columnDefs[i].filter.term=null
            scope.gridOptions.columnDefs[i].filter.option=null
        }
        scope.clearGrid();
        scope.loadRecords(false);
    }

    //get default options. Any of these can be overridden
    //for example, if you wanted to turn off sorting you can do:
    //  scope.gridOptions.enableSorting = false;
    scope.gridOptions = WidgetGridService.getDefaultGridOptions('search-user-grid');

    //Set column and Data options specific to this grid
    scope.gridOptions.columnDefs = scope.columnDefinitions;
    scope.gridOptions.data = scope.gridRecords;
//    scope.gridOptions.prototype.gridName="searchUserGrid";

    //Register grid events for interactions
    scope.gridOptions.onRegisterApi = function(grid) {
        //set scope object for grid interaction
        scope.gridApi = grid;

        //
        //Register scrolling event
        //
        scope.gridApi.infiniteScroll.on.needLoadMoreData(scope, function () {
                console.log('Triggered infinite scroll');
            scope.loadRecords(true);
        });

        //
        //Register row selection event
        //
        scope.gridApi.selection.on.rowSelectionChanged(scope, function(row) {
            scope.selectedUsers=[];
            scope.activeUsers=[];
            scope.disabledUsers=[];

            //get the new array of selected rows
            scope.selectedRows = scope.gridApi.selection.getSelectedRows();
            for(var i=0;i<scope.selectedRows.length; i++)
            {
                scope.selectedUsers.push(scope.selectedRows[i].userName);
                if(scope.selectedRows[i].enable){
                    scope.activeUsers.push(scope.selectedRows[i].userName);
                }
                else{
                    scope.disabledUsers.push(scope.selectedRows[i].userName);
                }
            }

            //Pause grid auto referesh if rows are selected.
            if (scope.selectedUsers.length) {
                $interval.cancel(autoRefreshPromise);
            } else {
                scope.searchUserGridAutoRefresh();
            }


            //Broadcast widget data
            WidgetGridService.broadcastData(scope.widgetdetails.id, scope.widgetdetails.widgetDefinition.broadcastList, scope.selectedRows);
        });

        //
        //Register batch selection event (i.e. Select All, Unselect All)
        //
        scope.gridApi.selection.on.rowSelectionChangedBatch(scope, function(rows){
            scope.selectedRows = scope.gridApi.selection.getSelectedRows();
            scope.selectedUsers=[];
            scope.activeUsers=[];
            scope.disabledUsers=[];
            for(var i=0;i<scope.selectedRows.length; i++)
            {
                scope.selectedUsers.push(scope.selectedRows[i].userName);
                if(scope.selectedRows[i].enable){
                    scope.activeUsers.push(scope.selectedRows[i].userName);
                }
                else{
                    scope.disabledUsers.push(scope.selectedRows[i].userName);
                }
            }

            //Pause grid auto referesh if rows are selected.
            if (scope.selectedUsers.length) {
                $interval.cancel(autoRefreshPromise);
            } else {
                scope.searchUserGridAutoRefresh();
            }
            
            //Broadcast widget data
            // do not broad cast if the event is empty
            if (arguments[1]) {
                WidgetGridService.broadcastData(scope.widgetdetails.id, scope.widgetdetails.widgetDefinition.broadcastList, scope.selectedRows);
            }
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

        rootScope.$on('searchOnEnterKey', function() {

            timeout.cancel(scope.filterTimeout);

            scope.filterCriteria = WidgetGridService.buildFilterCriteria(scope.gridOptions.columnDefs);

            // clear the grid and fetch filter records from server
            scope.clearGrid();
            scope.loadRecords(false);
        });

        rootScope.$on('searchOnFilterChanged', function() {

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

    //load user records from DB
    scope.loadRecords = function(append) {

        var sort = scope.sortCriteria;
        var filter = scope.filterCriteria;
        var page = self.pageNumber;
        var size = self.pageSize;

        //load up next batch of records
        WidgetGridService.getRecords(scope.widgetdetails, sort, filter, page, size)
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
            scope.gridOptions.totalRecords=gridRows.totalRecords;
            scope.gridOptions.pauseAutoRefresh=!scope.widgetdetails.actualViewConfig.autoRefreshConfig.enabled;
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

    var widgetHeader=angular.element("#wid"+scope.widgetdetails.clientId).find(".panel-heading");
    var actionButtons='<div id="searchUserGridActionButtons" class="searchUserGridActionButtons">'+
                      '<button class="btn-small btn-success" id="deleteUser" ng-click="deleteSelectedUsers()" ng-disabled="enableDeleteBtn()" ng-if="showDeleteBtn()" tooltip-placement="right" tooltip-append-to-body="true" tooltip="{{\'user.selectUserFromGridToDelete\' | translate}} "><i class="glyphicon glyphicon-trash"></i></button> '+
                      '<button class="btn-small btn-success" id="disableUser" ng-click="disableSelectedUsers()" ng-disabled="enableDisableBtn()" ng-if="showDisableBtn()" tooltip-placement="right" tooltip-append-to-body="right" tooltip="{{\'user.selectUserFromGridToDisable\' | translate}}"><i class="glyphicon glyphicon-off glyphicon-ban-circle"></i></button>&nbsp;'+
                      '<button class="btn-small btn-success" id="enableUser" ng-click="enableSelectedUsers()" ng-disabled="enableEnableBtn()" ng-if="showEnableBtn()" tooltip-placement="right" tooltip-append-to-body="right" tooltip="{{\'user.selectUserFromGridToEnable\' | translate}}"><i class="glyphicon glyphicon-off"></i></button>&nbsp;'+                      
                      '<button class="btn-small btn-success" id="retrainVoiceModel" ng-click="retrainVoiceLog()" ng-disabled="enableVoiceLogBtn()" ng-if="showVoiceLogBtn()" tooltip-placement="right" tooltip-append-to-body="right" tooltip="{{\'user.retrainVoiceLog\' | translate}}"><i class="glyphicon glyphicon-headphones"></i></button> &nbsp;'+
                      '</div>'+
                      '<button class="btn-small btn-success pull-right tweaked-margin-right" id="unfilterSearchUsers" ng-click="unfilterSearchUsers()" tooltip-placement="right" tooltip-append-to-body="true" tooltip="{{\'user.unfilter\' | translate}} "><i class="glyphicon glyphicon-filter"></i> </button>'+
                      '<button class="btn-small btn-success pull-right tweaked-margin-right" id="searchUserGridManualRefresh" ng-click="searchUserGridManualRefresh()" tooltip-placement="right" tooltip-append-to-body="true" tooltip="{{\'user.manualRefresh\' | translate}} "><i class="glyphicon glyphicon-refresh"></i> </button>';
                      
    actionButtons=angular.element(actionButtons);
    var compileButtons = compile(actionButtons)(scope);
    actionButtons.appendTo(widgetHeader);

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
     * Action Pallet functionality
     */

    //opens up the widget Action Pallet modal
    scope.showActionPallet = function() {
        modal.open({
            templateUrl : "views/modals/search-user-grid-widget-action-pallet.html",
            scope : scope,
            controller : 'ActionPalletController',
            backdrop: 'static'
        });
    };

    //opens up the widget Action Confirmation modal
    scope.showActionConfirmation = function() {
        modal.open({
            templateUrl : "views/modals/action-confirmation.html",
            scope : scope,
            controller : 'ActionConfirmationController',
            backdrop: 'static'
        });
    };

    //perform the selected action
    scope.$on('ProcessPalletAction', function(event, action) {
        scope.action = action;
        console.log('*** SearchUserGridConroller processing ProcessPalletAction event: with action = ' + action);
        scope.actiontext = 'actionPallet.actions.' + action;
        scope.showActionConfirmation();
    });

    // user confirmed to continue with the selected action
    scope.$on('ActionConfirmed', function(event, action) {
        console.log(JSON.stringify(scope.selectedRows));
        SearchUserGridService.updateUserRecords(action, scope.selectedRows).then(function(response) {
            console.log('*** SearchUserGridConroller.updateUserRecords: response = ' + JSON.stringify(response));
            //Successful update so refresh the widget grid data
            if (response) {
                //clear grid and load with new data
                scope.clearGrid();
                scope.loadRecords(false);
            }
        }, function(error) {
            //handle error
            console.log(error);
            return error;
        });
    });


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

    //This event is triggered when the user updates their User Preferences. We have to listen for this
    //event because they may have updated the Auto Refresh event
    rootScope.$on('UserPreferencesSaved', function(event, args) {
            //update the Auto Refresh interval - the user may have modified the setting in the cog menu
            scope.searchUserGridAutoRefresh();
    });

    //This is triggered from any Cog Menu changes. It's job is to apply the changes made in the
    //cog menu to the current grid view
    scope.$on('updateExistingWidget', function(event, args) {
        //make sure we only perform this action when the call is made for
        //this widgetInstance only
    
         if (args.clientId == scope.widgetdetails.clientId) {

            //if the date format was changed, then we must update the
            //column definitions to reflect this, so the dates are correctly displayed
            //in the grid
            var oldDateFormat = (scope.widgetdetails.actualViewConfig.hasOwnProperty('dateFormat')
                                 && scope.widgetdetails.actualViewConfig.dateFormat.hasOwnProperty('selectedFormat'))
                                ? scope.widgetdetails.actualViewConfig.dateFormat.selectedFormat
                                : null;
            var newDateFormat = (args.actualViewConfig.hasOwnProperty('dateFormat')
                                 && args.actualViewConfig.dateFormat.hasOwnProperty('selectedFormat'))
                                ? args.actualViewConfig.dateFormat.selectedFormat
                                : null;
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
                    if (angular.equals(scope.columnDefinitions[i].field, column.fieldName)
                        && !angular.equals(scope.columnDefinitions[i].visible, column.visible)) {
                       scope.columnDefinitions[i].visible = column.visible;
                    }
                }
            });

            //notify ui-grid of column changes via the grid api
            scope.gridApi.core.notifyDataChange(scope.gridApi.grid, uiGridConstants.dataChange.COLUMN);

            //update the Auto Refresh interval - the user may have modified the setting in the cog menu
            scope.searchUserGridAutoRefresh();
        }
    });

    //react to the Create/Edit user form savng a user - the grid needs to refresh
    //to keep data in sync with latest user information
    rootScope.$on("SearchUserGridRefresh", function() {
        //refresh the grid
        scope.clearGrid();
        scope.loadRecords(false);
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

        //Cancel the interval to free up resources
        $interval.cancel(autoRefreshPromise);

        if (updateWidgetId) {
            updateWidgetId();
        }
    });
}]);