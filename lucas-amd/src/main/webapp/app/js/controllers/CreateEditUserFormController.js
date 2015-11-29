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

amdApp.controller('CreateEditUserFormController', ['$log', '$rootScope', '$scope', 'WidgetDefinitionService', 'UtilsService', 'LocalStoreService', 'CreateEditUserFormService', '$modal', '$q', '$injector', '$filter',
function(log, rootScope, scope, WidgetDefinitionService, UtilsService, LocalStoreService, CreateEditUserFormService, modal, q,  $injector, $filter) {

    //
    // Initialise the controller
    //
    scope.init = function() {

        //
        // Functions that will be used to asynchronously fetch the required data needed for
        // rendering the form
        //
        var loadUserList = function () {
            return CreateEditUserFormService.getUsernames();
        };
        var loadLanguages = function () {
            return CreateEditUserFormService.getSupportedLanguages();
        };
        var loadShifts = function () {
            return CreateEditUserFormService.getAvailableShifts();
        };
        var loadSkills = function () {
            return CreateEditUserFormService.getAvailableSkills();
        };
        var loadGroups = function () {
            return CreateEditUserFormService.getAvailableGroups();
        };

        //Request the initial form data
        q.all([
            loadUserList(),
            loadLanguages(),
            loadShifts(),
            loadSkills(),
            loadGroups()
        ]).then(function (responses) {
            //cache initial data on scope
            scope.users = responses[0];
            scope.languages = responses[1];
            scope.shifts = responses[2];
            scope.skills = responses[3];
            scope.groups = responses[4];
        }, function (error) {
            console.log("FAILED TO LOAD FORM DATA!");
        });

        // initialzie form field enabled
        scope.formFields = {
            passwordEnabled: true,
            confirmPasswordEnabled: true,
            hostLoginEnabled: true,
            hostPassswordEnabled: true,
            skillEnabled: true,
            shiftEnabled: true,
            j2uLanguageEnabled: true,
            u2jLanguageEnabled: true,
            hhLanguageEnabled: true,
            amdLanguageEnabled: true,
            employeeNumberEnabled: true,
            startDateEnabled: true,
            firstNameEnabled: true,
            lastNameEnabled: true,
            birthDateEnabled: true,
            mobileNumberEnabled: true,
            emailAddressEnabled: true,
            moveDataEnabled: true
        };


        //
        // Array to maintain the currently active tab in the widget
        //
        scope.tabs = [
            false,  // Login Information tab
            false, // Languages tab
            false, // Optional tab
            false  // Groups tab
        ];


        //
        // Store the users permissions to control functionality in this widget
        //
        scope.permissions = scope.widgetdetails.widgetDefinition.widgetActionConfig['widget-actions'];


        //
        // Translations for 'MoveData' directive, used on Groups tab
        //
        scope.widgetdetails.strAvailableItems = 'userGroup.availableGroups';
        scope.widgetdetails.strExistingItems = 'userGroup.selectedGroups';


        //
        // Initialise objects for scope template bindings
        //
        scope.fields = {};
        scope.widgetdetails.availableItems = [];
        scope.widgetdetails.existingItems = [];

        scope.datepickers = {
            startDate: false,
            birthDate: false
        };
        scope.dateOptions = {
            'year-format': 'yyyy',
            'starting-day': 1
        };


        //
        // Get persisted data for this widget - if there is no persisted data,
        // initialise the widget in its default state
        //
        var widgetData = UtilsService.getPersistedWidgetData(scope.widgetdetails.clientId);
        if(angular.isObject(widgetData)) {
            //set the form back to how we last left it, including the tab that was active
            scope.setFormState(widgetData.formState);
            scope.tabs[widgetData.activeTab] = true;

            //populate the form with the previous data..
            scope.originalValues = widgetData.originalValues;
            scope.fields = widgetData.fields;
            scope.widgetdetails.availableItems = widgetData.availableGroups;
            scope.widgetdetails.existingItems = widgetData.assignedGroups;
            scope.usernameList = widgetData.usernameList;
            
            // re do the search
            if (scope.usernameList && scope.usernameList.length) {
                scope.searchUser(scope.userNameList);
            }

            //we trigger this watcher manually, as it needs to validate the lucas login
            //after manual scope update. If we don't do this, the Lucas Login model scope binding
            //is not correct
            scope.lucasLoginWatcher();

        } else {
            // load the widget in its default state
            scope.setFormState("MODE_SEARCH");
        }
    };


    //
    // ---------------------------------------------------------------------------------------------
    // CONTROLLER FUNCTIONS
    // ---------------------------------------------------------------------------------------------
    //

    scope.open = function($event, which) {
        $event.preventDefault();
        $event.stopPropagation();
        scope.closeAll();
        scope.datepickers[which] = true;
    };

    // close all datepickers
    scope.closeAll = function() {
        for (var key in scope.datepickers) {
            scope.datepickers[key] = false;
        }
    };

    //
    // Triggered by ng-change directive on Lucas Login field.
    // Used to monitor when to active/disable certain form controls
    //
    scope.lucasLoginWatcher = function() {
        console.log("trigger Lucas Login watcher function!");
        if (angular.isUndefined(scope.fields.username)
            || scope.fields.username.length < 1) {

            //disable search button - can't search for null user
            scope.searchButtonEnabled = false;
            return;
        }

        scope.searchButtonEnabled = true;
    };


    //
    // Listens for enter key press on Lucas Login input field. For usability we want
    // an enter key press to trigger a search
    //
    scope.enterKeySearch = function(event) {
        //detect enter key and perform a search (if a value has been entered!)
        if(angular.equals(event.keyCode, 13)
            && angular.isDefined(scope.fields.username)
            && scope.fields.username.length > 0) {
            scope.searchUser(scope.fields.username);
        }
    };


    //
    // Controls the form behaviour based on setting the forms state
    //
    scope.setFormState = function(mode) {
        //reset form states
        scope.MODE_SEARCH = false;
        scope.MODE_EDIT = false;
        scope.MODE_CREATE = false;
        scope.MODE_SAVE = false;
        scope.MODE_MULTI_EDIT = false;


        //check permissions
        var permissionEdit = scope.checkUserPermission('edit-user');
        var permissionCreate = scope.checkUserPermission('create-user');



        //Search state
        if (angular.equals(mode, "MODE_SEARCH")) {
            console.log("Setting form to SEARCH mode");
            scope.MODE_SEARCH = true;

            scope.lucasLoginEnabled = true;
            scope.searchButtonEnabled = false;
            scope.searchButtonVisible = true;

            scope.clearButtonEnabled = false;
            scope.clearButtonVisible = false;

            //manage template controls based on user permissions
            scope.formFieldsEnabled = false;
            scope.saveButtonEnabled = false;
            scope.saveAsButtonEnabled = false;

            //Default back to first tab
            scope.tabs[0] = true;

            //Reset error/info messages
            scope.userNotFound = false;
        }

        //Edit state
        else if (angular.equals(mode, "MODE_EDIT")) {
            console.log("Setting form to EDIT mode");
            scope.MODE_EDIT = true;

            scope.lucasLoginEnabled = false;
            scope.searchButtonEnabled = false;
            scope.searchButtonVisible = false;

            scope.clearButtonEnabled = true;
            scope.clearButtonVisible = true;

            //manage template controls based on user permissions
            scope.formFieldsEnabled = permissionEdit;
            scope.saveButtonEnabled = permissionEdit;
            scope.saveAsButtonEnabled = permissionCreate;
        }

        // MultiEdit
        else if (angular.equals(mode, "MODE_MULTI_EDIT")) {
            scope.MODE_MULTI_EDIT = true;
            scope.lucasLoginEnabled = true;
            scope.searchButtonEnabled = false;
            scope.searchButtonVisible = false;
            scope.clearButtonEnabled = true;
            scope.clearButtonVisible = true;

            // manage template controls based on user permissions
            scope.formFieldsEnabled = true;
            scope.formFieldsDisabledForMultiEdit = true;

            // disable all properties
            scope.formFields = UtilsService.setAllChildProperties(scope.formFields, false);

            if (permissionEdit || permissionCreate) {
                scope.saveButtonEnabled = true;
            } else {
                scope.saveButtonEnabled = false;
            }
            scope.saveAsButtonEnabled = false;
        }

        //Create state
        else if (angular.equals(mode, "MODE_CREATE")) {
            console.log("Setting form to CREATE mode");
            scope.MODE_CREATE = true;

            //check if user is allowed to create users
            if (!permissionCreate) {
                console.log('user not permitted to CREATE!');

                scope.lucasLoginEnabled = false;
                scope.searchButtonEnabled = false;
                scope.searchButtonVisible = false;

                scope.clearButtonEnabled = true;
                scope.clearButtonVisible = true;

                scope.formFieldsEnabled = false;
                scope.saveButtonEnabled = false;
                scope.saveAsButtonEnabled = false;

                //show error message
                scope.userNotFound = true;

                return;
            }

            //If we get this far, user has permission to create users
            scope.lucasLoginEnabled = false;
            scope.searchButtonEnabled = false;
            scope.searchButtonVisible = false;

            scope.clearButtonEnabled = true;
            scope.clearButtonVisible = true;

            //manage template controls based on user permissions
            //override the edit permissions, since we must have the form
            //enabled in-order to input the new user details
            scope.formFieldsEnabled = true;
            scope.saveButtonEnabled = true;

            //disabled the 'Save As' button - we can't copy a user that hasn't been
            //created yet!
            scope.saveAsButtonEnabled = false;
        }

        //Saving state
        if (angular.equals(mode, "MODE_SAVE")) {
            console.log("Setting form to SAVE mode");
            scope.MODE_SAVE = true;

            //disable form controls until save operation is complete
            scope.lucasLoginEnabled = false;
            scope.searchButtonEnabled = false;
            scope.searchButtonVisible = false;

            scope.clearButtonEnabled = false;
            scope.clearButtonVisible = true;

            scope.formFieldsEnabled = false;
            scope.saveButtonEnabled = false;
            scope.saveAsButtonEnabled = false;
        }
    };

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

    // gets the users details in Multi Edit Mode
    scope.getMultiUsersDetails = function() {
        CreateEditUserFormService.getMultiUsersDetails(scope.usernameList).then(function(response) {
            // set the form state to Multi Edit
            scope.setFormState('MODE_MULTI_EDIT');
            scope.userEditableFields = response.userEditableFields;

            // enable only the users specific fields
            angular.forEach(response.userEditableFields, function(val, key) {
                console.log(key, val);
                scope.formFields[key + "Enabled"] = true;
                scope.fields[key] = val;
            });
            // copy the fields
            scope.originalValues.fields = angular.copy(scope.fields);
        });
    };

    // Returns the user details if that user exists. If it's a new user,
    // the returning object will be NULL
    //
    scope.searchUser = function(username) {
        console.log("*** Searching for user: " + username + " ***");
        
        // enable all properties
        scope.formFields = UtilsService.setAllChildProperties(scope.formFields, true);

        // populate the Available Groups selection box
        scope.widgetdetails.availableItems = angular.copy(scope.groups);

        // initialise object to store the 'pristine' state of the form
        // This will be used layer to check for unsaved form changes
        scope.originalValues = {};

        if (username instanceof Array) {
            if (username.length == 1) {
                scope.username = username = username[0];
            } else {

                // display the list of user names
                scope.fields.username = scope.usernameList[0] + ", " + scope.usernameList[1];
                if (scope.usernameList.length > 2) {
                    var translate = $filter('translate')
                    scope.fields.username += ' ' + translate('core.and') + ' ' + (scope.usernameList.length - 2) + ' ' + translate('core.more');
                }

                return scope.getMultiUsersDetails();
            }
        }

        //Decide if we are editing an existing user, or attempting to create a new one
        if (CreateEditUserFormService.checkUserExists(username)) {
            //editing existing user
            scope.setFormState('MODE_EDIT');

            //Asynchronously fetch the users details assigned groups
            //from the server and apply to the form
            q.all([
                CreateEditUserFormService.getUserDetails(username),
                CreateEditUserFormService.getGroupsForUser(username)
            ]).then(function(responses) {
                var dtl = responses[0];
                var grp = responses[1];

                //User details
                if (angular.isDefined(dtl) && angular.isObject(dtl)) {
                    //populate the form fields
                    //Skill
                    if (dtl.hasOwnProperty('skill')) {
                        scope.fields.skill = dtl.skill;
                    }
                    //Shift
                    if (dtl.hasOwnProperty('shift')) {
                        scope.fields.shift = dtl.shift.shiftId;
                    }
                    //Jen To User Language
                    if (dtl.hasOwnProperty('j2uLanguage')) {
                        scope.fields.j2uLanguage = dtl.j2uLanguage.languageCode;
                    }
                    //User To Jen Language
                    if (dtl.hasOwnProperty('u2jLanguage')) {
                        scope.fields.u2jLanguage = dtl.u2jLanguage.languageCode;
                    }
                    //Handheld Language
                    if (dtl.hasOwnProperty('hhLanguage')) {
                        scope.fields.hhLanguage = dtl.hhLanguage.languageCode;
                    }
                    //AMD Language
                    if (dtl.hasOwnProperty('amdLanguage')) {
                        scope.fields.amdLanguage = dtl.amdLanguage.languageCode;
                    }
                    //Employee No.
                    if (dtl.hasOwnProperty('employeeNumber')) {
                        scope.fields.employeeNumber = dtl.employeeNumber;
                    }
                    //Start Date
                    if (dtl.hasOwnProperty('startDate')) {
                        scope.fields.startDate = dtl.startDate;
                    }
                    //First Name
                    if (dtl.hasOwnProperty('firstName')) {
                        scope.fields.firstName = dtl.firstName;
                    }
                    //Last Name
                    if (dtl.hasOwnProperty('lastName')) {
                        scope.fields.lastName = dtl.lastName;
                    }
                    //Birth Date
                    if (dtl.hasOwnProperty('birthDate')) {
                        scope.fields.birthDate = dtl.birthDate;
                    }
                    //Mobile No.
                    if (dtl.hasOwnProperty('mobileNumber')) {
                        scope.fields.mobileNumber = dtl.mobileNumber;
                    }
                    //Email Address
                    if (dtl.hasOwnProperty('emailAddress')) {
                        scope.fields.emailAddress = dtl.emailAddress;
                    }
                }

                //Users assigned groups
                //update the form ng-model to display the users currently assigned groups
                if (angular.isDefined(grp) && angular.isObject(grp)) {
                    scope.widgetdetails.existingItems = grp;
                } else {
                    scope.widgetdetails.existingItems = [];
                }

                //make a deep copy of pristine form values. We will compare against this later to
                //detect unsaved changes
                scope.originalValues.fields = angular.copy(scope.fields);
                scope.originalValues.groups = angular.copy(grp);

            }, function(ex) {
                //Failed to fetch user details
                UtilsService.raiseNotification(ex);

                //exit gracefully
                scope.resetForm();
            });
        } else {
            //unknown user entered - by default this means we are creating a new user
            scope.setFormState('MODE_CREATE');
        }
    };


    //
    // Reset the form back to it's default (read-only) state
    //
    scope.clear = function() {

        //compare the forms current and original form values. If they match,
        //then no changes have been made or all changes have been saved. If we find a
        //discrepancy we know we have unsaved changes - WARN THE USER!
        if (!angular.equals(scope.fields, scope.originalValues.fields)
            || !angular.equals(scope.widgetdetails.existingItems, scope.originalValues.groups)) {

            //display modal warning of unsaved changes
            scope.modalInstance = modal.open({
                templateUrl: "views/modals/warn-unsaved-changes.html",
                backdrop: 'static'
            });

            //Handle button actions from the modal
            scope.modalInstance.result.then(function() {
                //Used agreed to discard unsaved changes
                console.log("Discarding unsaved changes: "  + new Date());
                scope.resetForm();

            }, function() {
                //User cancelled discarding unsaved changes
                console.log("Cancelled discard: " + new Date());
            });
        }
        else {
            //reset the form ready for a new search
            scope.resetForm();
        }
    };

    // Enable Save button
    scope.enableSaveButton = function(){
        if(!scope.saveButtonEnabled){
            return false;
        }

        //disable save if passwords do not match
        if (!scope.validatePasswords())
            return false;

        return true;
    };

    // Enable Save button
    scope.enableSaveAsButton = function(){
        if(!scope.saveAsButtonEnabled){
            return false;
        }

        //disable save if passwords do not match
        if (!scope.validatePasswords())
            return false;

        return true;
    };

    // Function to validate if passwords match
    scope.validatePasswords = function() {

        // Make sure we are not checking undefined objects. If the value is undefined, cast to empty string
        scope.fields.password = (typeof scope.fields.password === 'undefined') ?  "" : scope.fields.password;
        scope.fields.confirmPassword = (typeof scope.fields.confirmPassword === 'undefined') ?  "" : scope.fields.confirmPassword;

        return (angular.equals(scope.fields.password, scope.fields.confirmPassword));
    };

    scope.selectAll = function() {
        if (scope.selectedAll) {
            scope.selectedAll = false;
        } else {
            scope.selectedAll = true;
        }
        angular.forEach(scope.details.multiEditFields, function(item) {
            item.forceUpdate = scope.selectedAll;
        });
    };

    //
    // Creates a new user if user doesn't already exist, or
    // updates an existing user
    //
    scope.save = function() {
        var details = scope.fields;
        var assignedGroups = scope.widgetdetails.existingItems;
        var isMultiEdit = scope.MODE_MULTI_EDIT;

        if (isMultiEdit) {
            
            scope.details = {
                "userNameList": scope.usernameList,
                "multiEditFields": {}
            };

            angular.forEach(scope.userEditableFields, function(key, item) {
                scope.details.multiEditFields[item] = {
                    "value": scope.fields[item],
                    "forceUpdate": "false"
                }
            });

            // Confirmation modal to save multi edit details
            scope.modalMultiEditSaveChanges = modal.open({
                templateUrl: "views/modals/warn-multi-edit-save-force-update.html",
                backdrop: 'static',
                scope: scope
            });

            // Handle button actions from the modal
            scope.modalMultiEditSaveChanges.result.then(function() {

                angular.forEach(scope.details.multiEditFields, function(item) {
                    // when the value is not present the forceUpdate should be false otherwise BE fails
                    if (!item.value) {
                        item.forceUpdate = false;
                    }
                });

                // save the users
                CreateEditUserFormService.saveMultiUsersDetails(scope.details).then(function(response) {
                    console.log(response)
                    scope.selectedAll = false;
                    // broadcast a User Grid Refresh Event to update the grid with the latest changes
                    rootScope.$emit("SearchUserGridRefresh", null);
                    // refresh the create edit user
                    scope.getMultiUsersDetails();

                }, function(ex) {
                    // Error - enable the save buttons
                    console.log(ex);
                    scope.selectedAll = false;
                });

            }, function() {
                // User cancelled discarding unsaved changes
                console.log("Cancelled discard");
                scope.selectedAll = false;
            });

        } else {

            // disabled the save button while save is happening
            scope.setFormState('MODE_SAVE');

            // Pass form details to service to make the server call
            CreateEditUserFormService.saveUser(details, assignedGroups).then(function() {
                //Success - first clear the password fields, we don't want to display them
                scope.fields.password = "";
                scope.fields.confirmPassword = "";
                scope.fields.hostPassword = "";

                //Update the "original" values with the
                //new saved values. This ensures we are always comparing
                //with the updated values when checking for unsaved changes
                scope.originalValues.fields = angular.copy(scope.fields);
                scope.originalValues.groups = angular.copy(scope.widgetdetails.existingItems);

                //After saving, go to edit mode
                scope.setFormState('MODE_EDIT');

                //update the AMD username cache so the UI is kept in
                //sync with the active DB users
                CreateEditUserFormService.reloadUsernameCache();

                //broadcast a User Grid Refresh Event to update the grid with the latest changes
                rootScope.$emit("SearchUserGridRefresh", null);

            }, function(ex) {
                //Error - enable the save buttons
                console.log(ex);
                scope.setFormState('MODE_EDIT');
            });

        }
    };


    //
    // Makes a copy of the selected user. Displays a modal to capture new
    // Lucas Login
    //
    scope.copy = function() {
        console.log("copying user [" + scope.fields.username + "]");

        //Open modal for copying a user
        scope.modalInstance = modal.open({
            templateUrl : 'views/modals/copy-user-prompt.html',
            backdrop : 'static',
            controller : 'CopyUserModalController',
            resolve : {
                userList : function() {
                    return scope.users;
                }
            }
        });

        //Handle button actions from the modal
        scope.modalInstance.result.then(function(user) {
            //Saving the new users
            console.log("Creating new user: " + user);

            //update form models to reflect new user, and blank out the password fields,
            //we don't want to copy passwords!
            scope.fields.username = user;
            scope.fields.password = "";
            scope.fields.confirmPassword = "";
            scope.fields.hostLogin = "";
            scope.fields.hostPassword = "";

            //Now save the user
            scope.save(user);

        }, function() {
            //Cancelled the Copy action
            console.log("Copy cancelled at: " + new Date());
        });
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

    //This is the watcher that listens for the broadcast event
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
                scope.resetForm();
                // search only if the users are present
                if( data[widgetInterationConfiguration[i].dataElement].length )  {
                    scope.searchUser(scope.fields.username = scope.usernameList = data[widgetInterationConfiguration[i].dataElement]);
                }
                break;
            }
        }
    });

    //
    // Reset the form back to it's default (read-only) state,
    // ready to search for another user
    //
    scope.resetForm = function() {

        //Reset form state
        scope.setFormState('MODE_SEARCH');

        //clear ng-models for form fields
        scope.fields = {};
        scope.originalValues = {};

        //Reset dropdowns back to default selections
        scope.fields.amdLanguage = "";
        scope.fields.hhLanguage = "";
        scope.fields.j2uLanguage = "";
        scope.fields.u2jLanguage = "";
        scope.fields.shift = "";
        scope.fields.skill = "";
        scope.form.confirm_password.$error.pwmatch=false;

        //Reset Group Assignment controls to default state
        scope.widgetdetails.availableItems = [];
        scope.widgetdetails.selectedAvailableItems = [];

        scope.widgetdetails.existingItems = [];
        scope.widgetdetails.selectedExistingItems = [];
    };

    scope.$on('$destroy', function() {
        //un-register scope watchers - failure to do this will result in memory leaks!
        updateWidgetId();
        widgetBroadcastEvent();

        //When this child scope is no longer needed, the scope instance is destroyed
        //allowing the garbage collector to reclaim resources. In order to persist
        //the current state of the controller, we must store it when it's destroyed and retrieve it when a new
        //child scope is initiated.
        var clientId = scope.widgetdetails.clientId;
        var data = {};

        //cache all form data for persistence
        data.originalValues = scope.originalValues;
        data.fields = (scope.fields) ? scope.fields : {};
        data.usernameList = scope.usernameList;
        data.availableGroups = (scope.widgetdetails.availableItems.length) ? scope.widgetdetails.availableItems : [];
        data.assignedGroups = (scope.widgetdetails.existingItems.length) ? scope.widgetdetails.existingItems : [];
        data.formState = (function() {
            if (scope.MODE_SEARCH) return "MODE_SEARCH";
            else if (scope.MODE_EDIT) return "MODE_EDIT";
            else if(scope.MODE_MULTI_EDIT) return "MODE_MULTI_EDIT";
            else if (scope.MODE_CREATE) return "MODE_CREATE";
            else return "MODE_SEARCH"; //default to search mode
        })();
        data.activeTab = (function() {
            for (var i = 0; i < scope.tabs.length; i++) {
                if(scope.tabs[i]) {
                    return i;
                }
            }
            return 0;
        })();

        //persist this widgets data
        UtilsService.persistWidgetData(clientId, data);
    });

    // Initialise the controller
    scope.init();
}]);