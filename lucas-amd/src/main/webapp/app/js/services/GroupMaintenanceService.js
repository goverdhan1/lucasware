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
 * Services related to Group Maintenance widget.
 */
amdApp.factory('GroupMaintenanceService', ['RestApiService', '$log', '$q', '$timeout',
function(restApiService, log, q, timeout) {

    //Class variables to cache data
    var groupNames = undefined;
    var permissions = undefined;
    var groupDetails = undefined;
    var userList;

    var factory = {};

    //
    // Gets a list of available GROUPS.
    // Makes an API call to "/groups" if data has not already been cached
    //
    factory.getGroupNames = function() {
        //Data is cached, no need to fetch from server again!
        if (angular.isDefined(groupNames)) {

            //Send back promise containing groups
            var def = q.defer();
            def.resolve(groupNames);

            return def.promise;
        }
        else {
            //get groups from API
            return restApiService.getAll("groups", null, null).then(function(response) {
                 //cache the server response for future performance
                 groupNames = response;
                 return groupNames;
            }, function(ex) {
            });
        }
    };


    //
    // Returns permissions grouped by category
    // Makes an API call to "/permissions" if data has not already been cached
    //
    factory.getPermissions = function() {
        //check for cached data
        if (angular.isDefined(permissions)) {
            //Send back promise containing groups
            var def = q.defer();
            def.resolve(permissions);

            return def.promise;
        }
        else {
            return restApiService.getOne("permissions", null, null).then(function(response) {
                //cache original object for faster retrieval
                permissions = response;
                return permissions;
            }, function(ex) {
                console.log("Failed to load permissions by category!");
            });
        }
    };


    //
    // Get list of permission categories
    //
    factory.getPermissionCategories = function() {
        //If the permissions object is already defined, read it from
        //cache and return the categories (object keys)
        if(angular.isDefined(permissions)) {
            var def = q.defer();
            def.resolve(Object.keys(permissions));
            return def.promise;
        }
        //Otherwise, first get the permissions object, then extract
        //the categories.
        else {
            return this.getPermissions().then(function(response) {
                //cache permissions object for faster retrieval
                permissions = response;
                return Object.keys(permissions);
            });
        }
    };


    //
    // Returns group details.
    //
    factory.getGroupDetails = function(group) {
        //Data is cached, no need to fetch from server again!
        if (angular.isDefined(groupDetails)) {

            //Send back promise containing groups
            var def = q.defer();
            def.resolve(groupDetails);

            return def.promise;
        } else {
            //get groups from API
            return restApiService.getOne("/groups/" + group + "/details", null, null).then(function(response) {
                //cache the server response for future performance
                groupDetails = response;
                return groupDetails;
            },
            function(error){

                console.log(error);
                return error;
            });
        }
    };


    //
    // Returns all available permissions for a given category
    // API not yet developed, so this returns static mock data
    //
    factory.getAvailablePermissionsForCategory = function(category) {

        //If we already have the permissions object, there is no need to
        //hit the server again. Just read from the cached object
        if(angular.isDefined(permissions)) {

            var def = q.defer();
            def.resolve(permissions[category]);

            return def.promise;
        }

        //If we don't have a cached object, go and fetch it
        return this.getPermissions().then(function(response) {
            //cache the object for faster retrieval in future calls
            permissions = response;
            return permissions[category];
        });
    };


    //
    // Given a array of Permissions, and a Category as input, this function will return
    // an array (subset) of the permission list for the given category.
    //
    factory.getAssignedPermissionsForCategory = function(category, permissionList) {
        var def = q.defer();

        //if category isn't defined, return empty array
        if(angular.isUndefined(category) || category.length == 0) {
            def.resolve([]);
            return def.promise;
        }

        //if permissionList isn't define, or is empty, return empty array
        if(angular.isUndefined(permissionList) || permissionList.length == 0) {
            def.resolve([]);
            return def.promise;
        }

        //Get all available permissions for the given category. We need these to check against
        // to be able to define a subset
        var permissionsForCategory = this.getAvailablePermissionsForCategory(category);

        //If permissions are empty, return empty array, else perform the business logic to extract the
        //permissions assigned to the given category
        if (permissionsForCategory == null) {
            def.resolve([]);
            return def.promise;
        } else {
            var assignedPermissions = [];
            return permissionsForCategory.then(function(response) {
                //for every permission, see if it exists in the current category
                for (var i = 0; i < permissionList.length; i++) {
                    if (response.indexOf(permissionList[i]) > -1) {
                        assignedPermissions.push(permissionList[i]);
                    }
                }
                //send back subset of permissions
                return assignedPermissions;
            }, function(error) {
                //if something goes wrong, return empty array
                return assignedPermissions;
            });
        }
    };


    //
    // This function keeps track of any unsaved permission assignment changes. It checks the current permission list
    // against te available and assigned permissions.
    //
    // If a given permission exists in the "availablePermissions" array, we know it's not assigned so it must not
    // exist in our list of currently assigned permissions
    //
    // If a given permission exists in the "assignedPermissions" array, we know it is assigned so it must exist in
    // out list of currently assigned permissions
    //
    factory.checkPermissionAssignmentChanges = function(availablePermissions, assignedPermissions, currentPermissionList) {

        var i;
        var permission;
        var indexOfPermission;

        //first check for "availablePermissions" - These are permissions that are NOT assigned
        for (i = 0; i < availablePermissions.length; i++) {
            permission = availablePermissions[i];
            indexOfPermission = currentPermissionList.indexOf(permission);

            //If the permission is present, REMOVE IT!
            if (indexOfPermission > -1) {
                currentPermissionList.splice(indexOfPermission, 1);
            }
        }

        //next, check for "assignedPermissions" - these are permissions that SHOULD be assigned
        for (i = 0; i < assignedPermissions.length; i++) {
            permission = assignedPermissions[i];
            indexOfPermission = currentPermissionList.indexOf(permission);

            //If the permission is missing, ADD IT!
            if (indexOfPermission == -1) {
                currentPermissionList.push(permission);
            }
        }

        return currentPermissionList;
    };


    //
    // This functions compares two permission sets (Array of permissions) for equality.
    // Equality is determined on each permission set containing the same permissions,
    // regardless of order.
    //   true = arrays match
    //   false = arrays do not match
    //
    factory.comparePermissionSets = function(originalPermissions, newPermissions) {
        //check the lengths - this saves us a lot of time!
        if (originalPermissions.length != newPermissions.length) {
            //Permission sets do not match!
            return false;
        }

        //the arrays are the same length - check for same elements
        for(var i = 0; i < originalPermissions.length; i++) {
            var permission = originalPermissions[i];

            //check this permission exists in the newPermissions
            if(newPermissions.indexOf(permission) > -1) {
                //permision exists - continuing
                continue;
            }

            //If we get here, the permission wasn't found!
            return false;
        }

        //If we get this far, we have finished checking all permissions,
        //and identified the arrays match.
        return true;
    };


    //
    // Used for creating a new group and for editing an existing one.
    // Sends group object to endpoint '/group/save'. The end point will
    // evaluate if the group already exists (by group name) and if so will
    // do an update, else it will do an insert
    //
    factory.saveGroup = function(groupDetails) {

        //post body to send to server
        var payload = {
            "groupName"           : groupDetails.name,
            "description"         : groupDetails.description,
            "assignedPermissions" : groupDetails.permissions, //list of assigned permissions
            "enrolledUsers"       : groupDetails.users //list of assigned users
        };

        return restApiService.post('/groups/' + groupDetails.name + '/save', null, payload).then(function(response) {
            return response;
        },
        function(error){
            console.log(error);
            return error;
        });
    };


    //
    // Reloads the group name cache - hits the /groups endpoint
    // to get an up-to-date list of all current group names. This function
    // is called when a group is saved (created/updated) a cached list of groups
    // in sync with the DB
    //
    factory.reloadCache = function() {
        //clear any cached data
        groupNames = undefined;

        //load a fresh list of group names from server
        this.getGroupNames().then(function(groupNameList) {
            groupNames = groupNameList;
        }, function(ex) {
            //an error occurred
            console.log("error refreshing cache [" + JSON.stringify(ex) + "]");
            groupNames = (angular.isDefined(groupNames) ? groupNames : []);
        });

        userList = null;
        var properties = [
            "userName", "firstName", "lastName", "skill"
        ];
        factory.loadUsers(properties);
    };

    //
    // Check if group exists
    // Returns boolean: TRUE (Group exist); FALSE (Group does not exist)
    //
    factory.checkGroupExists = function(group) {
        return (groupNames.indexOf(group) > -1);
    };

    // returns the users based on the properties
    factory.loadUsers = function(properties) {
        if (userList) {
            // Send back promise containing userList
            var def = q.defer();
            def.resolve(userList);
            return def.promise;
        } else {
            return restApiService.post("users/userproperties/", null, properties).then(function(response) {
                // cache the server response for future performance
                userList = response;
                return userList;
            }, function(ex) {
                throw new LucasBusinessException(ex);
            });
        }
    };

    return factory;
}]);
