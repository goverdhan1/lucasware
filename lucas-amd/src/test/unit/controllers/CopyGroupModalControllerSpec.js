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

describe('CopyGroupModal  Controller Tests', function() {

    var scope = null;
    var controller = null;
    var modalInstance = null;
    var groupList = null;

    //
    // Load translation provider
    //
    beforeEach(module('amdApp', function ($translateProvider) {
        $translateProvider.translations('en', {
            "language-code": "EN"
        }, 'fr', {
            "language-code": "fr"
        }, 'de', {
            "language-code": "de"
        }).preferredLanguage('en');
        $translateProvider.useLoader('LocaleLoader');
    }));

    //
    // Global test setup
    //
    beforeEach(inject(function (_$rootScope_) {
        //initialise dependencies
        scope = _$rootScope_.$new();
        modalInstance = {
            templateUrl : 'views/modals/copy-group-prompt.html',
            backdrop : 'static',
            controller : 'CopyGroupModalController',
            resolve : {
                groupList : function() {
                    return scope.groups;
                }
            }
        };
        groupList = ["administrator", "warehouse-manager"];
    }));


    //
    // Ensure controller dependencies are injected globally
    //
    describe('Dependency injection tests', function () {
        beforeEach(inject(function (_$controller_, _$modal_) {
            controller = _$controller_('CopyGroupModalController', {
                $scope: scope,
                $modalInstance : _$modal_.open(modalInstance),
                groupList : groupList
            });
        }));

        it('should inject controller with dependencies', function () {
            expect(controller).toBeDefined();
            expect(scope).toBeDefined();
            expect(modalInstance).toBeDefined();
            expect(groupList).toBeDefined();
        });
    });


    //
    // Test that the modal validation works as expected
    //
    describe('Modal validation for new Group Name', function () {

        //setup
        beforeEach(inject(function (_$controller_, _$modal_) {
            //inject controller
            controller = _$controller_('CopyGroupModalController', {
                $scope: scope,
                $modalInstance : _$modal_.open(modalInstance),
                groupList : groupList
            });
        }));

        //Prevents null or empty Group Name
        it('should not allow Group Name to be empty', function () {
            //Assert default state
            expect(scope.groupNameValid).toBeFalsy();
            expect(scope.groupIsEmpty).toBeFalsy();
            expect(scope.groupAlreadyExists).toBeFalsy();

            //set null/empty group name
            scope.fields.groupName = "";

            //invoke controller function
            scope.validateNewGroupName();

            //Assert results
            expect(scope.groupNameValid).toBeFalsy();
            expect(scope.groupIsEmpty).toBeTruthy();
            expect(scope.groupAlreadyExists).toBeFalsy();
        });

        //Prevents Group Name that already exists
        it('should not allow Group Name that already exists', function () {
            //Assert default state
            expect(scope.groupNameValid).toBeFalsy();
            expect(scope.groupIsEmpty).toBeFalsy();
            expect(scope.groupAlreadyExists).toBeFalsy();

            //set null/empty lucas login
            scope.fields.groupName = "warehouse-manager";

            //invoke controller function
            scope.validateNewGroupName();

            //Assert results
            expect(scope.groupNameValid).toBeFalsy();
            expect(scope.groupIsEmpty).toBeFalsy();
            expect(scope.groupAlreadyExists).toBeTruthy();
        });

        //Allow unique Group Name
        it('should allow a unique Group Name', function () {
            //Assert default state
            expect(scope.groupNameValid).toBeFalsy();
            expect(scope.groupIsEmpty).toBeFalsy();
            expect(scope.groupAlreadyExists).toBeFalsy();

            //set null/empty lucas login
            scope.fields.groupName = "my new group";

            //invoke controller function
            scope.validateNewGroupName();

            //Assert results
            expect(scope.groupNameValid).toBeTruthy();
            expect(scope.groupIsEmpty).toBeFalsy();
            expect(scope.groupAlreadyExists).toBeFalsy();
        });
    });
});