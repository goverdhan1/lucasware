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

amdApp.controller('CopyGroupModalController', ['$log', '$scope', '$modalInstance', 'groupList',
function(log, scope, modalInstance, groupList) {

    //initialise model for capturing new Group Name
    scope.fields = {};
    scope.fields.groupName = "";
    scope.fields.groupDesc = "";

    //initialise indicators used to control modal presentation
    scope.groupNameValid = false;
    scope.groupIsEmpty = false;
    scope.groupAlreadyExists = false;


    //watcher function invoked by ng-change directive on new Group Name field
    scope.validateNewGroupName = function() {
        //Make sure something has been entered
        if (angular.isUndefined(scope.fields.groupName) || scope.fields.groupName.trim().length === 0) {
            scope.groupNameValid = false;
            scope.groupIsEmpty = true;
            return;
        }

        //Ensure entered Group Name doesn't already exist - trim off any leading/trailing spaces
        if (groupList.indexOf(scope.fields.groupName.trim()) > -1) {
            scope.groupNameValid = false;
            scope.groupAlreadyExists = true;
            return;
        }

        //The Group Name entered is valid - enable Save button and hide messages
        scope.groupNameValid = true;
        scope.groupIsEmpty = false;
        scope.groupAlreadyExists = false;
    };
}]);
