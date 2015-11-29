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

describe('Group Maintenance Service related Tests', function() {

    //Locals
    var service = null;
    var restApiService = null;
    var scope = null;

    //
    // Global test setup
    //
    beforeEach(module('amdApp', function($translateProvider) {
        $translateProvider.translations('en', {
            "language-code" : "EN"
        }, 'fr', {
            "language-code" : "fr"
        }, 'de', {
            "language-code" : "de"
        }).preferredLanguage('en');
        $translateProvider.useLoader('LocaleLoader');
    }));


    //
    // Inject dependencies
    //
    beforeEach(inject(function(_GroupMaintenanceService_, _RestApiService_, _$rootScope_) {
        service = _GroupMaintenanceService_;
        restApiService = _RestApiService_;
        scope = _$rootScope_.$new();
    }));


    //
    // Ensure service dependency is injected globally
    //
    describe('Dependency injection test', function() {

        it('should inject GroupMaintenanceService', function(){
            expect(service).toBeDefined();
        });

        it('should inject RestApiService', function(){
            expect(restApiService).toBeDefined();
        });

        it('should inject scope', function() {
           expect(scope).toBeDefined();
        });
    });


    //
    // Returns all available groups in the system.
    // Uses endpoint "/groups"
    //
    describe('Function: getGroupNames()', function() {

        var def = null;

        beforeEach(inject(function(_$q_) {
            //resolve the promise
            def = _$q_.defer();
            def.resolve([
                "picker",
                "admin",
                "warehouse-manager",
                "supervisor"
            ]);

            //spyOn function used to check if we have cached data
            spyOn(angular, 'isDefined').andCallThrough();
            spyOn(restApiService, 'getAll').andReturn(def.promise);
        }));

        //First time round the call should hit the server, as nothing is cached
        it('should return a list of groups from server', function() {
            //invoke the service function
            var result = service.getGroupNames();
            scope.$digest();

            //assert expected behaviour
            result.then(function(r) {
                expect(angular.isDefined).toHaveBeenCalled();
                expect(angular.isDefined).toHaveBeenCalledWith(undefined);
                expect(restApiService.getAll).toHaveBeenCalled();
                expect(restApiService.getAll).toHaveBeenCalledWith('groups', null, null);
                expect(r.length).toEqual(4);
                expect(r).toContain("warehouse-manager");
            });

            //Subsequent calls should read from cached data
            //invoke the service function
            result = null;
            result = service.getGroupNames();
            scope.$digest();

            //assert expected behaviour
            result.then(function(r) {
                expect(angular.isDefined).toHaveBeenCalled();
                expect(angular.isDefined).not.toHaveBeenCalledWith(undefined);
                expect(restApiService.getAll).not.toHaveBeenCalled();
                expect(r.length).toEqual(4);
                expect(r).toContain("warehouse-manager");
            });
        });
    });


    //
    // Should return permissions grouped by categories.
    // Uses endpoint "/permissions"
    //
    describe('Function: getPermissions()', function() {

        var def = null;

        beforeEach(inject(function(_$q_) {
            //resolve the promise
            def = _$q_.defer();
            def.resolve({
                "product-management" : [
                    "delete-product",
                    "edit-product"
                ],
                "user-management" : [
                    "create-user",
                    "edit-user"
                ]
            });

            //spyOn function used to check if we have cached data
            spyOn(angular, 'isDefined').andCallThrough();
            spyOn(restApiService, 'getOne').andReturn(def.promise);
        }));

        //First time round the call should hit the server, as nothing is cached
        it('should return permissions organised by category from server', function() {
            //invoke the service function
            var result = service.getPermissions();
            scope.$digest();

            //assert expected behaviour
            result.then(function(r) {
                //Should not be reading from cache
                expect(angular.isDefined).toHaveBeenCalled();
                expect(angular.isDefined).toHaveBeenCalledWith(undefined);
                expect(restApiService.getOne).toHaveBeenCalled();
                expect(restApiService.getOne).toHaveBeenCalledWith("permissions", null, null);

                //Expect object to contain Product Management permissions
                expect(r.hasOwnProperty('product-management')).toBeTruthy();
                expect(r['product-management'].length).toEqual(2);
                expect(r['product-management']).toContain('delete-product');
                expect(r['product-management']).toContain('edit-product');

                //Expect object to contain User Management permissions
                expect(r.hasOwnProperty('user-management')).toBeTruthy();
                expect(r['user-management'].length).toEqual(2);
                expect(r['user-management']).toContain('create-user');
                expect(r['user-management']).toContain('edit-user');
            });

            //Subsequent calls should be read from cached data
            result = null;
            result = service.getPermissions();
            scope.$digest();

            //assert expected behaviour
            result.then(function(r) {
                //Should be reading from cache
                expect(angular.isDefined).toHaveBeenCalled();
                expect(angular.isDefined).not.toHaveBeenCalledWith(undefined);
                expect(restApiService.getOne).not.toHaveBeenCalled();

                //Expect object to contain Product Management permissions
                expect(r.hasOwnProperty('product-management')).toBeTruthy();
                expect(r['product-management'].length).toEqual(2);
                expect(r['product-management']).toContain('delete-product');
                expect(r['product-management']).toContain('edit-product');

                //Expect object to contain User Management permissions
                expect(r.hasOwnProperty('user-management')).toBeTruthy();
                expect(r['user-management'].length).toEqual(2);
                expect(r['user-management']).toContain('create-user');
                expect(r['user-management']).toContain('edit-user');
            });
        });
    });


    //
    // Should return a list of Permission Categories from the Permissions object
    //
    describe('Function: getPermissionCategories()', function() {

        var def = null;

        beforeEach(inject(function(_$q_) {
            //resolve the promise
            def = _$q_.defer();
            def.resolve({
                "product-management" : [
                    "delete-product",
                    "edit-product"
                ],
                "user-management" : [
                    "create-user",
                    "edit-user"
                ]
            });

            //spyOn function used to check if we have cached data
            spyOn(angular, 'isDefined').andCallThrough();
            spyOn(service, 'getPermissions').andCallThrough();
            spyOn(restApiService, 'getOne').andReturn(def.promise);
        }));

        //It should extract the Permission Categories from the Permission object
        it('should return a list of Permission Categories', function() {
            //Invoke the service function
            var result = service.getPermissionCategories();
            scope.$digest();

            //first time round it should get the permission object from the server
            result.then(function(r) {
                //assert the results
                expect(angular.isDefined).toHaveBeenCalled();
                expect(service.getPermissions).toHaveBeenCalled();
                expect(restApiService.getOne).toHaveBeenCalled();

                //assert list contains permission categories
                expect(r.length).toEqual(2);
                expect(r).toContain("product-management");
                expect(r).toContain("user-management");
            });

            //All subsequent calls should read from cache
            result = service.getPermissionCategories();
            scope.$digest();

            result.then(function(r) {
                //Should be reading from cache
                expect(angular.isDefined).toHaveBeenCalled();
                expect(angular.isDefined).not.toHaveBeenCalledWith(undefined);
                expect(service.getPermissions).not.toHaveBeenCalled();
                expect(restApiService.getOne).not.toHaveBeenCalled();

                //assert list contains permission categories
                expect(r.length).toEqual(2);
                expect(r).toContain("product-management");
                expect(r).toContain("user-management");
            });
        });
    });


    //
    // gets the current permissions assigned to the given group
    //
    describe('Function: getGroupPermissions()', function() {

        var def = null;
        var promise = {
            "groupName" : "administrator",
            "groupDescription" : "dummy description for group admin",
            "assignedPermissions" : [
                "authenticated-user",
                "create-user",
                "edit-user",
                "create-group",
                "create-canvas",
                "delete-canvas",
                "group-maintenance-widget-access",
                "search-user-widget-access",
                "create-edit-user-widget-access"
            ],
            "assignedUsers" : [
                "jack123",
                "jill123",
                "system"]
        };

        beforeEach(inject(function(_$q_, $httpBackend) {
            //resolve the promise
            def = _$q_.defer();
            def.resolve(promise);
            $httpBackend.whenGET().respond({});
            $httpBackend.whenPOST().respond({});
            $httpBackend.flush();
        }));

        //It should return permissions for the given group
        it('should return permissions assigned to a group', function() {
            //Invoke the service function
            var result = service.getGroupDetails("administrator");
            scope.$digest();

            //check the results
            result.then(function(details) {
                //assert the results
                expect(details.groupName).toEqual("administrator");
                expect(details.groupDescription).toEqual("dummy description for group admin");

                expect(details.assignedPermissions.length).toEqual(9);
                expect(details.assignedPermissions).toContain("create-user");

                expect(details.assignedUsers.length).toEqual(3);
                expect(details.assignedUsers).toContain("jill123");
            });
        });
    });


    //
    // gets all permissions for a given category
    //
    describe('Function: getAvailablePermissionForCategory()', function() {

        var def = null;

        beforeEach(inject(function (_$q_) {
            //setup a deferred exec
            def = _$q_.defer();
            def.resolve({
                "product-management": [
                    "delete-product",
                    "edit-product"
                ],
                "user-management": [
                    "create-user",
                    "edit-user"
                ]
            });

            //spies
            spyOn(service, 'getPermissions').andReturn(def.promise);
        }));


        //should return permissions from given category. Will read from cached permissions
        //object if defined, otherwise will get from server.
        it('should get available permissions for given category', function () {

            //Invoke the service function
            var result = service.getAvailablePermissionsForCategory("product-management");
            scope.$digest();

            //check the results - first time round it should hit the server as the
            //permissions are not defined.
            result.then(function (permissions) {
                //assert the results
                expect(service.getPermissions).toHaveBeenCalled();
                expect(permissions).toBeDefined();
                expect(permissions.length).toEqual(2);
                expect(permissions[0]).toEqual("delete-product");
                expect(permissions[1]).toEqual("edit-product");
            });

            //Second time round, it should read from cached data
            result = null;
            result = service.getAvailablePermissionsForCategory("product-management");
            scope.$digest();

            //check the results
            result.then(function (permissions) {
                //assert the results
                expect(service.getPermissions).not.toHaveBeenCalled();
                expect(permissions).toBeDefined();
                expect(permissions.length).toEqual(2);
                expect(permissions[0]).toEqual("delete-product");
                expect(permissions[1]).toEqual("edit-product");
            });
        });
    });


    //
    // given a list of all permissions assigned to a particular group, and a permission category
    // as input, this function should return a sub-set of the original permission list containing
    // only the permissions for the given category.
    //
    describe('Function: getAssignedPermissionForCategory()', function() {

        //test vars
        var assignedPermissions = ["create-group", "edit-group", "create-product"];
        var category = "group-management";
        var def = null;

        beforeEach(inject(function (_$q_) {
            //setup a deferred exec
            def = _$q_.defer();
            def.resolve([
                    "create-group",
                    "edit-group"
            ]);

            //spies
            spyOn(service, 'getAvailablePermissionsForCategory').andReturn(def.promise);
        }));

        //should return empty array if category is not passed
        it('should return empty array if category is not passed', function () {

            //invoke the service function
            var result = service.getAssignedPermissionsForCategory(undefined, assignedPermissions);
            scope.$digest();

            //assert results
            result.then(function (permissions) {
                expect(permissions).toBeDefined();
                expect(permissions.length).toEqual(0);
            });

        });

        //should return empty array if assigned permission list is not defined
        it('should return empty array if category is not passed', function () {

            //invoke the service function
            var result = service.getAssignedPermissionsForCategory(category, undefined);
            scope.$digest();

            //assert results
            result.then(function (permissions) {
                expect(permissions).toBeDefined();
                expect(permissions.length).toEqual(0);
            });
        });

        //should return assigned permissions that exist in the provided category
        it('should return assigned permissions for provided category', function () {

            //invoke the service function
            var result = service.getAssignedPermissionsForCategory(category, assignedPermissions);
            scope.$digest();

            //assert results
            result.then(function (permissions) {
                expect(permissions).toBeDefined();
                expect(permissions.length).toEqual(2);
                expect(permissions[0]).toEqual("create-group");
                expect(permissions[1]).toEqual("edit-group");
            });
        });
    });


    //
    // This function maintains the unsaved currently assigned permissions.
    //     availablePermissions = unassigned permissions
    //     assignedPermissions = assigned permissions
    //     currentPermissionList = the current list of all assigned permissions.
    // The purpose of this function is to keep the currentPermissionList in sync
    // with the latest assignment changes. The currentPermissionList must NOT contain
    // and of the available (unassigned permissions) and MUST contain the assigned permissions
    //
    describe('Function: checkPermissionAssignmentChanges()', function () {

        //test vars
        var availablePermissions = ["edit-group"];
        var assignedPermissions = ["create-group", "group-maintenance-widget-access"];
        var currentPermissionList = ["edit-group", "create-group", "authenticated-user"];

        //should removed available permissions and add any missing assigned permissions
        it('should remove available permissions and add assigned permissions', function () {

            //invoke service function
            var result = service.checkPermissionAssignmentChanges(availablePermissions, assignedPermissions, currentPermissionList);

            //assert results
            expect(result.length).toEqual(3);
            expect(result[0]).toEqual("create-group");
            expect(result[1]).toEqual("authenticated-user");
            expect(result[2]).toEqual("group-maintenance-widget-access");
        });
    });


    //
    // This function compares two sets of permissions for equality.
    // Equality is determined by each array containing the same permissions,
    // irrespective of order
    //
    describe('Function: comparePermissionSets()', function () {

        //test vars
        var arrayOne;
        var arrayTwo;

        //should exit quickly and return false if arrays are not the same length
        it('should return false if the arrays contain different number of elements', function () {

            arrayOne = ["1", "2"];
            arrayTwo = ["1"];

            //invoke service function
            var result = service.comparePermissionSets(arrayOne, arrayTwo);

            //assert results
            expect(result).toBeFalsy();
        });

        //arrays are the same length but contain different elements
        it('should return false when arrays are the same length but contain different elements', function () {

            arrayOne = ["1", "2"];
            arrayTwo = ["1", "3"];

            //invoke service function
            var result = service.comparePermissionSets(arrayOne, arrayTwo);

            //assert results
            expect(result).toBeFalsy();
        });

        //arrays are the same length and contain same elements in same order
        it('should return true when arrays are the same length and contain same elements in same order', function () {

            arrayOne = ["1", "2", "3"];
            arrayTwo = ["1", "2", "3"];

            //invoke service function
            var result = service.comparePermissionSets(arrayOne, arrayTwo);

            //assert results
            expect(result).toBeTruthy();
        });

        //arrays are the same length and contain same elements but in different order
        it('should return true when arrays are the same length and contain same elements but in different order', function () {

            arrayOne = ["1", "2", "3"];
            arrayTwo = ["3", "1", "2"];

            //invoke service function
            var result = service.comparePermissionSets(arrayOne, arrayTwo);

            //assert results
            expect(result).toBeTruthy();
        });
    });


    //
    // Saving the group back to the server after making modifications
    //
    describe('Function: saveGroup()', function () {

        var def = null;

        //
        // NOTHING TO TEST YET AS ENDPOINT IS BEING MOCKED
        //

        // place holder code below

        var def = null;

        beforeEach(inject(function(_$q_, $httpBackend) {
            //resolve promise
            def = _$q_.defer();
            def.resolve();

            //spyOn function used to make API call
            //spyOn(restApiService, 'post').andReturn(def.promise);
            spyOn(service, 'reloadCache').andReturn(true);
            spyOn(service, 'loadUsers').andReturn({});
            $httpBackend.whenGET().respond({});
            $httpBackend.whenPOST().respond({});
            $httpBackend.flush();
        }));

        //should save the group
        it('should save the group', function() {
            //mock parameters
            var usr = "jack123";
            var groupName = "a new group";
            var assignedPermissions = ["edit-group", "create-group"];

            //invoke service function
            var result = service.saveGroup(usr, groupName, assignedPermissions);
            scope.$digest();

            //assert results
            result.then(function(r) {
                //expect(restApiService.post).toHaveBeenCalled("groups/save");
                expect(service.reloadCache).toHaveBeenCalled();
            });
        });
    });


    //
    // Should reload the group name cache
    //
    describe('Function: reloadCache()', function () {

        var def = null;

        beforeEach(inject(function (_$q_) {
            //deferred exec
            def = _$q_.defer();
            def.resolve(["administrator", "picker"]);

            //spies
            spyOn(service, 'getGroupNames').andReturn(def.promise);
            spyOn(service, 'loadUsers').andReturn({});
        }));

        //should reload the cached group name list
        it('should reload the group names cache', function () {
            //invoke service function
            service.reloadCache();
            scope.$digest();

            //expect the getGroupNames function to have been called to get fresh
            //results from the server
            expect(service.getGroupNames).toHaveBeenCalled();

            //assert groupNames cache have been updated
            var result = service.getGroupNames();

            result.then(function(groupNames) {
               expect(groupNames.length).toEqual(2);
               expect(groupNames[0]).toEqual("administrator");
               expect(groupNames[1]).toEqual("picker");
            });
        });
    });


    //
    // Checks if the group exists or not
    // This one is a little tricky to test, since it reads a private variable that isn't
    // accessible. So to be able to test this function, we fist need to call "getGroupNames" to
    // set the private variable this function reads.
    //
    describe('Function: checkGroupExists()', function() {

        var def = null;

        beforeEach(inject(function(_$q_) {
            //resolve the promise
            def = _$q_.defer();
            def.resolve([
                "administrator",
                "warehouse-manager",
                "basic-authenticated-user"
            ]);

            //return a dummy promise
            spyOn(restApiService, 'getAll').andReturn(def.promise);
        }));

        it('should return TRUE when the group exists, FALSE when it does not', function() {

            //Invoke the getGroupNames function to set the private groupNames variable
            service.getGroupNames();
            scope.$digest();

            //invoke the function and asset the results
            //These groups should exist
            expect(service.checkGroupExists("administrator")).toBeTruthy();
            expect(service.checkGroupExists("warehouse-manager")).toBeTruthy();
            expect(service.checkGroupExists("basic-authenticated-user")).toBeTruthy();
            //The group does not exist
            expect(service.checkGroupExists("some-new-group")).toBeFalsy();
        });
    });
});