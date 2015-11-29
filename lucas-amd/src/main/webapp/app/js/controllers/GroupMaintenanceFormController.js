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

amdApp.controller('GroupMaintenanceFormController', ['$log', '$rootScope', '$timeout', '$scope','UtilsService', 'LocalStoreService', 'GroupMaintenanceService', '$modal', '$q', '$injector',
    function (log, rootScope, $timeout, scope, UtilsService, LocalStoreService, GroupMaintenanceService, modal, q, $injector) {

        //store the user logged in - we need so we can let the server
        //know who is updating the records
        scope.loginUser = LocalStoreService.getLSItem('ProfileData').username;

        //store permissions in a smaller object for easier access
        scope.permissions = scope.widgetdetails.widgetDefinition.widgetActionConfig['widget-actions'];

        //-----------------------------------------------------------
        //TEMP CODE - PERMISSIONS NEED CREATING AND ASSIGNING TO USER
        //-----------------------------------------------------------
        scope.permissions['create-group'] = true;
        scope.permissions['edit-group'] = true;
        //-----------------------------------------------------------
        // TEMP CODE - END
        //-----------------------------------------------------------

        //Translations for 'MoveData' directive, used on Groups tab
        scope.widgetdetails.strAvailableItems = "groupAssignment.availablePermissions";
        scope.widgetdetails.strExistingItems = "groupAssignment.assignedPermissions";

        //initialise objects to hold ng-models for widget controls
        scope.fields = {};
        scope.widgetdetails.selectedAvailableItems = [];
        scope.widgetdetails.selectedExistingItems = [];
        scope.widgetdetails.availableItems = [];
        scope.widgetdetails.existingItems = [];

        scope.widgetgriddetails = {};
        scope.widgetgriddetails.selectedAvailableItems = [];
        scope.widgetgriddetails.selectedExistingItems = [];
        scope.widgetgriddetails.availableItems = [];
        scope.widgetgriddetails.existingItems = [];

        //variables to control the widget state. There are 4 states the widget can be
        //rendered in:
        // 1. MODE_SEARCH - form is awaiting a group search
        // 2. MODE_EDIT - searched against existing group name
        // 3. MODE_CREATE - searched against unknown group name
        // 4. MODE_SAVE - waiting on server response following save action
        scope.MODE_SEARCH = true;
        scope.MODE_EDIT = false;
        scope.MODE_CREATE = false;
        scope.MODE_SAVE = false;

        //initialise variables to manage template controls
        scope.groupNameEnabled = true;
        scope.groupDescriptionEnabled = false;
        scope.searchButtonEnabled = false;
        scope.searchButtonVisible = true;
        scope.clearButtonEnabled = false;
        scope.clearButtonVisible = false;
        scope.formControlsEnabled = false;
        scope.saveButtonEnabled = false;
        scope.saveAsButtonEnabled = false;

        //messages
        scope.groupNotFound = false;

        //Async function to get list of group names
        var loadGroupList = function() {
            return GroupMaintenanceService.getGroupNames();
        };

        //Async function to get permissions grouped by category
        var loadPermissionCategories = function() {
            return GroupMaintenanceService.getPermissionCategories();
        };

        //Manages the Tabs active state
        //First tab is selected by default
        scope.tabs = [
            true,  // Engage Permissions
            false, // Work Execution
            false  // Enroll Users
        ];

        //Asynchronously fetch the required data needed for rendering the form in
        //its initial state
        q.all([
            loadGroupList(),
            loadPermissionCategories()
        ]).then(function(responses) {
            // assign data to scope
            scope.groups = responses[0];
            scope.fields.categories = {
                selected : "",
                available : responses[1]
            };

            // Initialise the controller
            scope.init();

        }, function (error) {
            console.log("FAILED TO LOAD WIDGET DATA!");
        });
        

        // Initialise the controller
        scope.init = function() {
            //
            // Get persisted data for this widget - if there is no persisted data,
            // initialise the widget in its default state
            //
            var widgetData = UtilsService.getPersistedWidgetData(scope.widgetdetails.clientId);
            if (angular.isObject(widgetData)) {
                // set the form back to how we last left it, including the tab that was active
                scope.setWidgetState(widgetData.formState);
                scope.tabs[widgetData.activeTab] = true;

                // populate the form with the previous data..
                scope.originalValues = widgetData.originalValues;

                scope.fields = widgetData.fields;

                scope.fields.categories.selected = widgetData.fields.categories.selected;

                scope.widgetdetails.availableItems = widgetData.availableItems;
                scope.widgetdetails.existingItems = widgetData.existingItems;
                scope.widgetdetails.selectedAvailableItems = widgetData.selectedAvailableItems;
                scope.widgetdetails.selectedExistingItems = widgetData.selectedExistingItem;

                // grid
                scope.widgetgriddetails.availableItems = widgetData.availableGridItems;
                scope.widgetgriddetails.existingItems = widgetData.existingGridItems;
                scope.widgetgriddetails.selectedAvailableItems = widgetData.selectedAvailableGridItems;
                scope.widgetgriddetails.selectedExistingItems = widgetData.selectedExistingGridItems;

            } else {
                scope.setWidgetState("MODE_SEARCH");
            }
        };


        //
        // ---------------------------------------------------------------------------------------------
        // CONTROLLER FUNCTIONS
        // ---------------------------------------------------------------------------------------------
        //


        //
        // Control access to widget functionality based on user permissions.
        //
        scope.checkUserPermission = function(perm) {
            //Control widget functionality based on user permissions
            if (scope.permissions.hasOwnProperty(perm)
                && angular.equals(scope.permissions[perm], true)) {
                //user has edit permissions
                return true;
            }
            //User does not have permission
            return false;
        };


        //
        // Triggered by ng-change directive on Group Name field.
        // Used to monitor when to active/disable certain form controls
        //
        scope.groupNameWatcher = function() {
            console.log("trigger GroupName watcher function!");
            if (angular.isUndefined(scope.fields.groupName)
                || scope.fields.groupName.length < 1) {

                //disable search button - can't search for null user
                scope.searchButtonEnabled = false;
                return;
            }

            scope.searchButtonEnabled = true;
        };


        //
        // Listens for enter key press on Group Name field. For usability we want
        // an enter key press to trigger a search
        //
        scope.enterKeySearch = function(event) {
            //detect enter key and perform a search (if a value has been entered!)
            if(angular.equals(event.keyCode, 13)
                && angular.isDefined(scope.fields.groupName)
                && scope.fields.groupName.length > 0) {
                scope.searchGroup(scope.fields.groupName);
            }
        };




        //
        // Controls the widget behaviour by dynamically rendering the widget controls
        //
        scope.setWidgetState = function(mode) {
            //reset widget states
            scope.MODE_SEARCH = false;
            scope.MODE_EDIT = false;
            scope.MODE_CREATE = false;
            scope.MODE_SAVE = false;

            //check permissions
            var permissionEdit = scope.checkUserPermission('edit-group');
            var permissionCreate = scope.checkUserPermission('create-group');

            //Search state
            if (angular.equals(mode, "MODE_SEARCH")) {
                console.log("Setting widget to SEARCH state");
                scope.MODE_SEARCH = true;

                scope.groupNameEnabled = true;
                scope.groupDescriptionVisible = false;
                scope.groupDescriptionEnabled = false;
                scope.searchButtonEnabled = false;
                scope.searchButtonVisible = true;

                scope.clearButtonEnabled = false;
                scope.clearButtonVisible = false;

                scope.formControlsEnabled = false;
                scope.permissionCategoriesEnabled = false;
                scope.saveButtonEnabled = false;
                scope.saveAsButtonEnabled = false;

                //Default back to first tab
                scope.tabs[0] = true;

                //Reset error/info messages
                scope.groupNotFound = false;
            }

            //Edit state
            else if (angular.equals(mode, "MODE_EDIT")) {
                console.log("Setting widget to EDIT state");
                scope.MODE_EDIT = true;

                scope.groupNameEnabled = false;
                scope.groupDescriptionVisible = true;
                scope.permissionCategoriesEnabled = true;
                scope.searchButtonEnabled = false;
                scope.searchButtonVisible = false;

                scope.clearButtonEnabled = true;
                scope.clearButtonVisible = true;

                //manage template controls based on user permissions
                scope.formControlsEnabled = permissionEdit;
                scope.saveButtonEnabled = permissionEdit;
                scope.saveAsButtonEnabled = permissionCreate;
                scope.groupDescriptionEnabled = permissionEdit;
            }

            //Create state
            else if (angular.equals(mode, "MODE_CREATE")) {
                console.log("Setting widget to CREATE mode");
                scope.MODE_CREATE = true;

                //check if user is allowed to create groups
                if (!permissionCreate) {
                    console.log('user not permitted to CREATE groups!');

                    scope.groupNameEnabled = false;
                    scope.groupDescriptionVisible = true;
                    scope.groupDescriptionEnabled = false;
                    scope.searchButtonEnabled = false;
                    scope.searchButtonVisible = false;

                    scope.clearButtonEnabled = true;
                    scope.clearButtonVisible = true;

                    scope.formControlsEnabled = false;
                    scope.permissionCategoriesEnabled = false;
                    scope.saveButtonEnabled = false;
                    scope.saveAsButtonEnabled = false;

                    //show error message
                    scope.groupNotFound = true;

                    return;
                }

                //If we get this far, user has permission to create groups
                scope.groupNameEnabled = false;
                scope.groupDescriptionVisible = true;
                scope.groupDescriptionEnabled = true;
                scope.searchButtonEnabled = false;
                scope.searchButtonVisible = false;

                scope.clearButtonEnabled = true;
                scope.clearButtonVisible = true;

                //manage template controls based on user permissions
                //override the edit permissions, since we must have the form
                //enabled in-order to input the new group details
                scope.formControlsEnabled = true;
                scope.permissionCategoriesEnabled = true;
                scope.saveButtonEnabled = true;

                //disabled the 'Save As' button - we can't copy a group that hasn't been
                //created yet!
                scope.saveAsButtonEnabled = false;
            }

            //Saving state
            else if (angular.equals(mode, "MODE_SAVE")) {
                console.log("Setting widget to SAVE mode");
                scope.MODE_SAVE = true;

                //disable form controls until save operation is complete
                scope.groupNameEnabled = false;
                scope.groupDescriptionVisible = true;
                scope.groupDescriptionEnabled = false;
                scope.searchButtonEnabled = false;
                scope.searchButtonVisible = false;

                scope.clearButtonEnabled = false;
                scope.clearButtonVisible = true;

                scope.formControlsEnabled = false;
                scope.permissionCategoriesEnabled = false;
                scope.saveButtonEnabled = false;
                scope.saveAsButtonEnabled = false;
            }
        };

        scope.onSelectTab = function() {
            $timeout(function() {
                UtilsService.triggerWindowResize();
            });
        };

        //
        // Gets and displays the selected groups details on the widget
        //
        scope.searchGroup = function(groupName) {

            //initialise the objects for storing the original set of permissions,
            //and the working set of permissions that track unsaved assignment changes
            scope.fields.originalAssignedPermissions = [];
            scope.fields.tempAssignedPermissions = [];
            scope.fields.originalAssignedUsers= [];
            scope.fields.tempAssignedUsers= [];

            scope.originalValues = {};



            //set default Permission Category selection to the first element in the array
            //Also trigger the change event so the correct permissions are rendered based on
            //the selection of a category.
            if (angular.isDefined(scope.fields.categories.available)
                && scope.fields.categories.available.length > 0) {
                //Set the default selection and invoke function to render permissions
                scope.fields.categories.selected = scope.fields.categories.available[0];
            }

            //check if group exists - If so we are entering EDIT mode to make changes to
            //an existing group. Otherwise, we are intending to CREATE a new group
            if(GroupMaintenanceService.checkGroupExists(groupName)) {
                console.log("*** Viewing Group [" + groupName + "]");
                scope.setWidgetState("MODE_EDIT");

                scope.widgetgriddetails.selectedAvailableItems = [];
                scope.widgetgriddetails.selectedExistingItems = [];
                scope.widgetgriddetails.availableItems = [];
                scope.widgetgriddetails.existingItems = [];

                //fetch the details for the selected group. We need these to render the widget
                GroupMaintenanceService.getGroupDetails(groupName).then(function(details) {

                    //store assigned permissions and users for the selected group.
                    //Here we store these into two different variables:
                    //  1. originalAssignedPermissions - the original set of permissions assigned to the group
                    //  2. tempAssignedPermissions - a temporary "ACTIVE" set of permissions assigned to the group
                    //We need to track both of these to detect for any unsaved changes.
                    scope.fields.originalAssignedPermissions = angular.copy(details.assignedPermissions);
                    scope.fields.tempAssignedPermissions = angular.copy(details.assignedPermissions);

                    //do the same for the assigned users
                    scope.fields.originalAssignedUsers = angular.copy(details.enrolledUsers);
                    scope.fields.tempAssignedUsers = angular.copy(details.enrolledUsers);

                    //do the same for group description
                    //set the group description
                    scope.fields.groupDescription = angular.copy(details.groupDescription);
                    scope.fields.tempGroupDescription = angular.copy(details.groupDescription);

                    //Load the permissions for the selected category
                    scope.loadPermissionsForCategory(scope.fields.categories.selected);

                    // fetch  list of user details (username, firstName , lastName, skill)
                    var properties = [
                        "userName", "firstName", "lastName", "skill"
                    ];
                    var i;

                    // load the users
                    GroupMaintenanceService.loadUsers(properties).then(function(response) {
                        scope.usersList = response;

                        scope.fields.originalEnrolledUsers = [];

                        scope.fields.originalAssignedUsers.forEach(function(name, ind) {
                            for (i = 0; i < scope.usersList.length; i++) {
                                if (scope.usersList[i].userName == name) {
                                    scope.fields.originalEnrolledUsers.push(scope.usersList[i]);
                                    break;
                                }
                            }
                        });

                        scope.fields.tempEnrolledUsers = angular.copy(scope.fields.originalEnrolledUsers);

                        // assignedUsers -> existingItems
                        scope.widgetgriddetails = {
                            availableItems: scope.usersList,
                            existingItems: scope.fields.tempEnrolledUsers,
                            selectedAvailableItems: [],
                            selectedExistingItems: []
                        }

                        $timeout(function() {
                            UtilsService.triggerWindowResize();
                        });
                    }, function(e) {
                        throw (new Error(e));
                    }).catch(function(ex) {
                        console.log("Failed to load list of users" + groupName);
                    });

                    // make a deep copy of pristine form values. We will compare against this later to
                    // detect unsaved changes
                    scope.originalValues.fields = angular.copy(scope.fields);

                },
                function(e) {
                    throw (new Error(e));
                }).catch(function(ex) {
                console.log("Failed to get details for group " + groupName);
            });



            } else {
                console.log("*** Creating Group [" + groupName + "]");
                scope.setWidgetState("MODE_CREATE");

                //Load permissions for the initial selected category
                scope.loadPermissionsForCategory(scope.fields.categories.selected);
            }
        };


        //
        // Gets the permissions in the widget for the selected category
        //
        scope.loadPermissionsForCategory = function(category) {

            //Before we do anything, we must ensure we persist any unsaved changes made to a previous
            //category selection. This is because a user can make changes to permissions in several categories,
            //before saving the changes.
            scope.persistUnsavedAssignmentChanges();

            //clear scope variables out ready for rendering with current category selection
            scope.widgetdetails.availableItems = [];
            scope.widgetdetails.existingItems = [];

            if (angular.isDefined(category) && category.length > 0) {

                // get the available permissions for the selected category
                GroupMaintenanceService.getAvailablePermissionsForCategory(category).then(function(result) {
                    scope.widgetdetails.availableItems = result;
                });

                //for the searched group, get the assigned permissions for the currently selected category
                GroupMaintenanceService.getAssignedPermissionsForCategory(category, scope.fields.tempAssignedPermissions).then(function(result) {
                    scope.widgetdetails.existingItems = result;
                });
            }
        };


        //
        //This functions is used to keep track of the permission assignment changes.
        //A user can make changes to more than one category before saving, so we MUST
        //track the changes when switching between categories
        //
        scope.persistUnsavedAssignmentChanges = function(){

            var available = scope.widgetdetails.availableItems;
            var assigned = scope.widgetdetails.existingItems;
            var tempPermissionList = scope.fields.tempAssignedPermissions;

            //Update our current "temporary" working set of permission assignments with latest changes
            scope.fields.tempAssignedPermissions = GroupMaintenanceService.checkPermissionAssignmentChanges(available, assigned, tempPermissionList);
        };


        //
        // First checks for any unsaved changes, before resetting the widget back to
        // it's default state
        //
        scope.clear = function() {

            var matching;


            //If we are creating a new group, there is nothing to compare. Instead we should always prompt
            //the user when attempting to clear the widget before saving the new group.
            if(angular.equals(scope.MODE_CREATE, true)) {
                matching = false;
            }
            else {
                //ensure our 'unsaved' set of permissions are up-to-date before making any comparisons!
                scope.persistUnsavedAssignmentChanges();

                //Compare the original set of permissions to the set that have been edited.
                //If they differ, then we have unsaved changes
                matching = GroupMaintenanceService.comparePermissionSets(scope.fields.originalAssignedPermissions, scope.fields.tempAssignedPermissions);
            }

            //the last thing to check is if the user has changed the group description. We only need to
            //perform this check if the above comparisions pass. If they has already identified some thing
            //has changed there is no need to keep checking.
            if (matching) {
                matching = (scope.fields.groupDescription !== scope.fields.tempGroupDescription) ? false : true;
            }

            if(angular.equals(matching, true)) {

                var updatedEnrolledUsers = UtilsService.getProperty(scope.widgetgriddetails.existingItems, 'userName');

                updatedEnrolledUsers.sort();

                if (!GroupMaintenanceService.comparePermissionSets(updatedEnrolledUsers, scope.fields.originalAssignedUsers) ||
                    (scope.originalValues && scope.originalValues.fields && !angular.equals(scope.fields.categories.selected, scope.originalValues.fields.categories.selected))) {
                    //Unsaved changes detected. Display warning modal!
                    scope.modalInstance = modal.open({
                        templateUrl: 'views/modals/warn-unsaved-changes.html',
                        backdrop: 'static'
                    });

                    //Handle button actions from the modal
                    scope.modalInstance.result.then(function() {
                        //Used agreed to discard unsaved changes
                        console.log("Discarding changes: " + new Date());

                        scope.widgetgriddetails.selectedAvailableItems = [];
                        scope.widgetgriddetails.selectedExistingItems = [];
                        scope.widgetgriddetails.availableItems = [];
                        scope.widgetgriddetails.existingItems = [];

                        scope.resetWidget();

                    }, function() {
                        //User cancelled discarding unsaved changes
                        console.log("Action cancelled: " + new Date());
                    });
                    return;
                }

                scope.widgetgriddetails.selectedAvailableItems = [];
                scope.widgetgriddetails.selectedExistingItems = [];
                scope.widgetgriddetails.availableItems = [];
                scope.widgetgriddetails.existingItems = [];

                //no unsaved changes detected - reset the widget
                scope.resetWidget();
            }
            else {
                //Unsaved changes detected. Display warning modal!
                scope.modalInstance = modal.open({
                    templateUrl : 'views/modals/warn-unsaved-changes.html',
                    backdrop : 'static'
                });

                //Handle button actions from the modal
                scope.modalInstance.result.then(function() {
                    //Used agreed to discard unsaved changes
                    console.log("Discarding changes: "  + new Date());
                    scope.resetWidget();

                }, function() {
                    //User cancelled discarding unsaved changes
                    console.log("Action cancelled: " + new Date());
                });
            }
        };


        //
        // Create a new Group if the group does not yet exist, or updates an existing
        // group
        //
        scope.save = function(groupName) {

            console.log("*** Saving Group [" + groupName + "]");

            //disabled widget controls while save operation is happening
            scope.setWidgetState('MODE_SAVE');

            //ensure our 'unsaved' set of permissions are up-to-date before making any comparisons!
            scope.persistUnsavedAssignmentChanges();

            // Build groupDetails object to pass to saving group function
            var groupDetails = {};
            groupDetails.name = groupName;
            groupDetails.description = scope.fields.groupDescription;
            groupDetails.permissions = scope.fields.tempAssignedPermissions;
            groupDetails.users = scope.fields.tempAssignedUsers;

            //Pass form details to service to make the server call
            return GroupMaintenanceService.saveGroup(groupDetails).then(function() {
                //Success - Update the "original" permissions with the
                //new saved values. This ensures we are always comparing
                //with the current values when checking for unsaved changes
                scope.fields.originalAssignedPermissions = angular.copy(scope.fields.tempAssignedPermissions);
                scope.fields.originalAssignedUsers = angular.copy(scope.fields.tempAssignedUsers);
                scope.fields.groupDescription = angular.copy(scope.fields.tempGroupDescription);

                //After saving, go to edit mode
                scope.setWidgetState('MODE_EDIT');

                // broadcast a Group Grid Refresh Event to update the grid with the latest changes
                rootScope.$emit("SearchGroupGridControllerRefresh", null);

            }, function(ex) {
                //Error - enable the save buttons
                console.log(ex);
                scope.setWidgetState('MODE_EDIT');
            });
        };


        //
        // Makes a copy of the selected group. Pops up a modal to capture the
        // new Group name
        //
        scope.copy = function() {

            console.log("*** Copying group [" + scope.fields.groupName + "]");

            //Open modal for copying a user
            scope.modalInstance = modal.open({
                templateUrl : 'views/modals/copy-group-prompt.html',
                backdrop : 'static',
                controller : 'CopyGroupModalController',
                resolve : {
                    groupList : function() {
                        return scope.groups;
                    }
                }
            });

            //Handle button actions from the modal
            scope.modalInstance.result.then(
                //Modal was closed
                function(group) {
                    //Saving the new group
                    console.log("*** Creating new group: " + group.groupName + " - " + group.groupDesc);

                    //store the original value - if something goes wrong, we need to put back the
                    //old data
                    var origName = angular.copy(scope.fields.groupName);
                    var origDesc = angular.copy(scope.fields.groupDescription);

                    //update the scope bindings
                    scope.fields.groupName = group.groupName;
                    scope.fields.tempGroupDescription = group.groupDesc;

                    //save the new group
                    scope.save(group.groupName).then(
                        //success handler
                        function(result) {
                            //empty success handler - we do not need to do any logic here
                        },
                        //error handler
                        function(error) {
                            console.log("*** Failed to save group - ERROR: " + error);
                            //reset the groupName and descriptions, as the copy was unsuccessful
                            scope.fields.groupName = origName;
                            scope.fields.groupDescription = origDesc;
                        }
                    ).finally(function() {
                        //set the widget back to EDIT mode to allow the user
                        //to edit the newly created group (depending on permissions),
                        //OR if there was an error, the user will be able to continue where
                        //they left off.
                        scope.setWidgetState("MODE_EDIT");
                    });
                },
                //Modal was closed (dismissed)
                function() {
                    //Cancelled the Copy action
                    console.log("Action cancelled: " + new Date());
                }
            );
        };

        // update the id for the newly created widget
        var updateWidgetId = rootScope.$on('updateWidgetId', function(event, widgetId, clientId) {
            if (scope.widgetdetails.clientId == clientId) {
                scope.widgetdetails.id = widgetId;
            }
        });

        // Get configured widget interactions
        scope.getWidgetInterationConfiguration = function() {
            // local variables
            var widgetInteractionConfig = [],
                activeCanvasId,
                favoriteCanvasListUpdated,
                activeCanvas,
                // To avoid circular dependecies use $injector
                LocalStoreService = $injector.get('LocalStoreService'),
                HomeCanvasListService = $injector.get('HomeCanvasListService'),
                CanvasService = $injector.get('CanvasService'),
                i;

            // Read from the LocalStorage 
            activeCanvasId = LocalStoreService.getLSItem("ActiveCanvasId");
            favoriteCanvasListUpdated = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');
            activeCanvas = HomeCanvasListService.findByCanvasId(favoriteCanvasListUpdated, activeCanvasId);

            // proceed only if there is an active canvas
            if (activeCanvas && activeCanvas.widgetInstanceList) {
                for (i = 0; i < activeCanvas.widgetInstanceList.length; i++) {
                    if (activeCanvas.widgetInstanceList[i].id == scope.widgetdetails.id) {
                        widgetInteractionConfig = activeCanvas.widgetInstanceList[i].widgetInteractionConfig;
                        break;
                    }
                }
            }
            return widgetInteractionConfig;
        };

        // This is the watcher that listens for the broadcast event
        var widgetBroadcastEvent = rootScope.$on('WidgetBroadcast', function(event, broadcastObject) {
            //Place holder for logic for reacting to broadcasts
            //PHX-1254 will extend this functionality

            var data = broadcastObject.getData();
            var widgetId = broadcastObject.getWidgetId();

            console.log("*** Reacting to broadcast from widget " + widgetId);
            console.log("*** Received data " + JSON.stringify(data));

            var widgetInterationConfiguration = scope.getWidgetInterationConfiguration();

            for (var i = 0; i < widgetInterationConfiguration.length; i++) {
                if (widgetInterationConfiguration[i].widgetInstance.id == widgetId &&
                    widgetInterationConfiguration[i].active &&
                    data[widgetInterationConfiguration[i].dataElement]) {
                    // process the dataElement
                    scope.resetWidget();
                    // search only if the groups are present
                    if (data[widgetInterationConfiguration[i].dataElement].length) {
                        scope.searchGroup(scope.fields.groupName = data[widgetInterationConfiguration[i].dataElement]);
                    }
                    break;
                }
            }
        });

        //
        // Resets the widget back to it's original state
        //
        scope.resetWidget = function() {
            //go back to widgets default state, ready for a new search
            scope.setWidgetState("MODE_SEARCH");

            scope.fields.groupName = "";
            scope.fields.groupDescription = "";
            scope.fields.tempGroupDescription = "";
            scope.fields.categories.selected = "";

            scope.fields.originalAssignedPermissions = [];
            scope.fields.tempAssignedPermissions = [];

            scope.fields.originalAssignedUsers = [];
            scope.fields.tempAssignedUsers = [];

            scope.widgetdetails.availableItems = [];
            scope.widgetdetails.selectedAvailableItems = [];
            scope.widgetdetails.existingItems = [];
            scope.widgetdetails.selectedExistingItems = [];
        };

        scope.$on('$destroy', function() {
            if (updateWidgetId) {
                updateWidgetId();
            }
            widgetBroadcastEvent();

            var clientId = scope.widgetdetails.clientId;
            var data = {};

            // cache all form data for persistence
            data.originalValues = scope.originalValues;
            data.fields = (scope.fields) ? scope.fields : {};

            data.availableItems = (scope.widgetdetails.availableItems && scope.widgetdetails.availableItems.length) ? scope.widgetdetails.availableItems : [];
            data.existingItems = (scope.widgetdetails.existingItems && scope.widgetdetails.existingItems.length) ? scope.widgetdetails.existingItems : [];
            data.selectedAvailableItems = (scope.widgetdetails.selectedAvailableItems && scope.widgetdetails.selectedAvailableItems.length) ? scope.widgetdetails.selectedAvailableItems : [];
            data.selectedExistingItems = (scope.widgetdetails.selectedExistingItems && scope.widgetdetails.selectedExistingItems.length) ? scope.widgetdetails.selectedExistingItems : [];
            
            // grid
            data.availableGridItems = (scope.widgetgriddetails.availableItems && scope.widgetgriddetails.availableItems.length) ? scope.widgetgriddetails.availableItems : [];
            data.existingGridItems = (scope.widgetgriddetails.existingItems && scope.widgetgriddetails.existingItems.length) ? scope.widgetgriddetails.existingItems : [];
            data.selectedAvailableGridItems = (scope.widgetgriddetails.selectedAvailableItems && scope.widgetgriddetails.selectedAvailableItems.length) ? scope.widgetgriddetails.selectedAvailableItems : [];
            data.selectedExistingGridItems = (scope.widgetgriddetails.selectedExistingItems && scope.widgetgriddetails.selectedExistingItems.length) ? scope.widgetgriddetails.selectedExistingItems : [];
            

            data.formState = (function() {
                if (scope.MODE_SEARCH) return "MODE_SEARCH";
                else if (scope.MODE_EDIT) return "MODE_EDIT";
                else if (scope.MODE_CREATE) return "MODE_CREATE";
                else return "MODE_SEARCH"; //default to search mode
            })();
            data.activeTab = (function() {
                for (var i = 0; i < scope.tabs.length; i++) {
                    if (scope.tabs[i]) {
                        return i;
                    }
                }
                return 0;
            })();

            // persist this widgets data
            UtilsService.persistWidgetData(clientId, data);
        });

}]);