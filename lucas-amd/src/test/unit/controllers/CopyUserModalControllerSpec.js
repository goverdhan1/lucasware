'use strict';

describe('CopyUserModal  Controller Tests', function() {

    var scope = null;
    var controller = null;
    var modalInstance = null;
    var userList = null;

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
            templateUrl : 'views/modals/copy-user-prompt.html',
            backdrop : 'static',
            controller : 'CopyUserModalController',
            resolve : {
                userList : function() {
                    return scope.users;
                }
            }
        };
        userList = ["jack123", "jill123"];
    }));


    //
    // Ensure controller dependencies are injected globally
    //
    describe('Dependency injection tests', function () {
        beforeEach(inject(function (_$controller_, _$modal_) {
            controller = _$controller_('CopyUserModalController', {
                $scope: scope,
                $modalInstance : _$modal_.open(modalInstance),
                userList : userList
            });
        }));

        it('should inject controller with dependencies', function () {
            expect(controller).toBeDefined();
            expect(scope).toBeDefined();
            expect(modalInstance).toBeDefined();
            expect(userList).toBeDefined();
        });
    });


    //
    // Test that the modal validation works as expected
    //
    describe('Modal validation for Lucas Login', function () {

        //setup
        beforeEach(inject(function (_$controller_, _$modal_) {
            //inject controller
            controller = _$controller_('CopyUserModalController', {
                $scope: scope,
                $modalInstance : _$modal_.open(modalInstance),
                userList : userList
            });
        }));

        //Prevents null or empty Lucas Login
        it('should not allow Lucas Login to be empty', function () {
            //Assert default state
            expect(scope.isValidLogin).toBeFalsy();
            expect(scope.userIsEmpty).toBeFalsy();
            expect(scope.userAlreadyExists).toBeFalsy();

            //set null/empty lucas login
            scope.newLucasLogin = "";

            //invoke controller function
            scope.validateNewLucasLogin();

            //Assert results
            expect(scope.isValidLogin).toBeFalsy();
            expect(scope.userIsEmpty).toBeTruthy();
            expect(scope.userAlreadyExists).toBeFalsy();
        });

        //Prevents Lucas Login that already exists
        it('should not allow Lucas Login that already exists', function () {
            //Assert default state
            expect(scope.isValidLogin).toBeFalsy();
            expect(scope.userIsEmpty).toBeFalsy();
            expect(scope.userAlreadyExists).toBeFalsy();

            //set null/empty lucas login
            scope.newLucasLogin = "jack123";

            //invoke controller function
            scope.validateNewLucasLogin();

            //Assert results
            expect(scope.isValidLogin).toBeFalsy();
            expect(scope.userIsEmpty).toBeFalsy();
            expect(scope.userAlreadyExists).toBeTruthy();
        });

        //Allow unique Lucas Login
        it('should not allow Lucas Login to be empty', function () {
            //Assert default state
            expect(scope.isValidLogin).toBeFalsy();
            expect(scope.userIsEmpty).toBeFalsy();
            expect(scope.userAlreadyExists).toBeFalsy();

            //set null/empty lucas login
            scope.newLucasLogin = "a new user";

            //invoke controller function
            scope.validateNewLucasLogin();

            //Assert results
            expect(scope.isValidLogin).toBeTruthy();
            expect(scope.userIsEmpty).toBeFalsy();
            expect(scope.userAlreadyExists).toBeFalsy();
        });
    });
});