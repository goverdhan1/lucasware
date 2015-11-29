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

describe('Create Edit User Form Service related Tests', function() {

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
    beforeEach(inject(function(_CreateEditUserFormService_, _RestApiService_, _$rootScope_) {
        service = _CreateEditUserFormService_;
        restApiService = _RestApiService_;
        scope = _$rootScope_.$new();
    }));


    //
    // Ensure service dependency is injected globally
    //
    describe('Dependency inject test', function() {

        it('should inject CreateEditUserFormService', function(){
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
    // Should return a list of supported languages from endpoint "/languages"
    //
    describe('Function: getSupportedLanguages()', function() {

        var def = null;

        beforeEach(inject(function(_$q_) {
            //resolve the promise
            def = _$q_.defer();
            def.resolve([
                {
                    languageCode:"EN_GB",
                    amdLanguage:true,
                    hhLanguage:true,
                    j2uLanguage:true,
                    u2jLanguage:true
                },
                {
                    languageCode:"EN_US",
                    amdLanguage:true,
                    hhLanguage:true,
                    j2uLanguage:true,
                    u2jLanguage:true
                }
            ]);

            //spyOn function used to check if we have cached data
            spyOn(angular, 'isDefined').andCallThrough();
            spyOn(restApiService, 'getAll').andReturn(def.promise);
        }));

        //First time round the call should hit the server, as nothing is cached
        it('should return a list of supported languages from server', function() {
            //invoke the service function
            var result = service.getSupportedLanguages();
            scope.$digest();

            //assert expected behaviour
            result.then(function(r) {
                //Should not be reading from cache
                expect(angular.isDefined).toHaveBeenCalled();
                expect(angular.isDefined).toHaveBeenCalledWith(undefined);
                expect(restApiService.getAll).toHaveBeenCalled();
                expect(restApiService.getAll).toHaveBeenCalledWith("languages", null, null);

                //Expect object to contain AMD languages
                expect(r.hasOwnProperty('amd')).toBeTruthy();
                expect(r.amd.length).toBeGreaterThan(0);
                expect(r.amd).toContain('EN_GB');

                //Expect object to contain HH languages
                expect(r.hasOwnProperty('hh')).toBeTruthy();
                expect(r.hh.length).toBeGreaterThan(0);
                expect(r.hh).toContain('EN_GB');

                //Expect object to contain J2U languages
                expect(r.hasOwnProperty('j2u')).toBeTruthy();
                expect(r.j2u.length).toBeGreaterThan(0);
                expect(r.j2u).toContain('EN_GB');

                //Expect object to contain U2J languages
                expect(r.hasOwnProperty('u2j')).toBeTruthy();
                expect(r.u2j.length).toBeGreaterThan(0);
                expect(r.u2j).toContain('EN_GB');
            });


            //Subsequent calls should be read from cached data
            result = null;
            result = service.getSupportedLanguages();
            scope.$digest();

            //assert expected behaviour
            result.then(function(r) {
                //Should be reading from cache
                expect(angular.isDefined).toHaveBeenCalled();
                expect(angular.isDefined).not.toHaveBeenCalledWith(undefined);
                expect(restApiService.getAll).not.toHaveBeenCalled();

                //Expect object to contain AMD languages
                expect(r.hasOwnProperty('amd')).toBeTruthy();
                expect(r.amd.length).toBeGreaterThan(0);
                expect(r.amd).toContain('EN_GB');

                //Expect object to contain HH languages
                expect(r.hasOwnProperty('hh')).toBeTruthy();
                expect(r.hh.length).toBeGreaterThan(0);
                expect(r.hh).toContain('EN_GB');

                //Expect object to contain J2U languages
                expect(r.hasOwnProperty('j2u')).toBeTruthy();
                expect(r.j2u.length).toBeGreaterThan(0);
                expect(r.j2u).toContain('EN_GB');

                //Expect object to contain U2J languages
                expect(r.hasOwnProperty('u2j')).toBeTruthy();
                expect(r.u2j.length).toBeGreaterThan(0);
                expect(r.u2j).toContain('EN_GB');
            });
        });
    });


    //
    // Should return a list of available shifts from endpoint "/shifts"
    //
    describe('Function: getAvailableShifts()', function() {

        var def = null;

        beforeEach(inject(function(_$q_) {
            //resolve the promise
            def = _$q_.defer();
            def.resolve([
                {
                    shiftId:1,
                    shiftName:"day",
                    startTime:"9am",
                    endTime:"6pm"
                },
                {
                    shiftId:2,
                    shiftName:"night",
                    startTime:"9pm",
                    endTime:"6am"
                }
            ]);

            //spyOn function used to check if we have cached data
            spyOn(angular, 'isDefined').andCallThrough();
            spyOn(restApiService, 'getAll').andReturn(def.promise);
        }));

        //First time round the call should hit the server, as nothing is cached
        it('should return a list of shifts from server', function() {
            //invoke the service function
            var result = service.getAvailableShifts();
            scope.$digest();

            //assert expected behaviour
            result.then(function(r) {
                //should not be reading from cache
                expect(angular.isDefined).toHaveBeenCalled();
                expect(angular.isDefined).toHaveBeenCalledWith(undefined);
                expect(restApiService.getAll).toHaveBeenCalled();
                expect(restApiService.getAll).toHaveBeenCalledWith('shifts', null, null);
                expect(r.length).toBeGreaterThan(0);

                expect(r[0].hasOwnProperty('shiftId')).toBeTruthy();
                expect(r[0].hasOwnProperty('shiftName')).toBeTruthy();
                expect(r[0].hasOwnProperty('startTime')).toBeTruthy();
                expect(r[0].hasOwnProperty('endTime')).toBeTruthy();
            });

            //Subsequent calls should read from cached data
            result = null;
            result = service.getAvailableShifts();
            scope.$digest();

            //assert expected behaviour
            result.then(function(r) {
                //should be reading from cache
                expect(angular.isDefined).toHaveBeenCalled();
                expect(angular.isDefined).not.toHaveBeenCalledWith(undefined);
                expect(restApiService.getAll).not.toHaveBeenCalled();
                expect(r.length).toBeGreaterThan(0);

                expect(r[0].hasOwnProperty('shiftId')).toBeTruthy();
                expect(r[0].hasOwnProperty('shiftName')).toBeTruthy();
                expect(r[0].hasOwnProperty('startTime')).toBeTruthy();
                expect(r[0].hasOwnProperty('endTime')).toBeTruthy();
            });
        });
    });


    //
    // Should return a list of available groups from endpoint "/groups"
    //
    describe('Function: getAvailableGroups()', function() {

        var def = null;

        beforeEach(inject(function(_$q_) {
            //resolve the promise
            def = _$q_.defer();
            def.resolve([
                "picker",
                "admin",
                "warehouse-manager"
            ]);

            //spyOn function used to check if we have cached data
            spyOn(angular, 'isDefined').andCallThrough();
            spyOn(restApiService, 'getAll').andReturn(def.promise);
        }));

        //First time round the call should hit the server, as nothing is cached
        it('should return a list of groups from server', function() {
            //invoke the service function
            var result = service.getAvailableGroups();
            scope.$digest();

            //assert expected behaviour
            result.then(function(r) {
                expect(angular.isDefined).toHaveBeenCalled();
                expect(angular.isDefined).toHaveBeenCalledWith(undefined);
                expect(restApiService.getAll).toHaveBeenCalled();
                expect(restApiService.getAll).toHaveBeenCalledWith('groups', null, null);
                expect(r.length).toBeGreaterThan(0);
                expect(r).toContain("warehouse-manager");
            });

            //Subsequent calls should read from cached data
            //invoke the service function
            result = null;
            result = service.getAvailableGroups();
            scope.$digest();

            //assert expected behaviour
            result.then(function(r) {
                expect(angular.isDefined).toHaveBeenCalled();
                expect(angular.isDefined).not.toHaveBeenCalledWith(undefined);
                expect(restApiService.getAll).not.toHaveBeenCalled();
                expect(r.length).toBeGreaterThan(0);
                expect(r).toContain("warehouse-manager");
            });
        });
    });


    //
    // Should return a list of available skills"
    //
    describe('Function: getAvailableSkills()', function() {

        beforeEach(function() {
            //spyOn function used to check if we have cached data
            spyOn(angular, 'isDefined').andCallThrough();
        });

        //First time round the data should br processed and then cached
        it('should process and return a list of skills', function() {
            //invoke the service function
            var result = service.getAvailableSkills();

            //assert expected behaviour
            result.then(function(r) {
                expect(angular.isDefined).toHaveBeenCalled();
                expect(angular.isDefined).toHaveBeenCalledWith(undefined);
                expect(r.length).toBeGreaterThan(0);
                expect(r).toContain("advanced");
            });

            //invoke the service function
            //Subsequent calls should not perform any processing and read directly from cache
            result = null;
            result = service.getAvailableSkills();

            //assert expected behaviour
            result.then(function(r) {
                expect(angular.isDefined).toHaveBeenCalled();
                expect(angular.isDefined).not.toHaveBeenCalledWith(undefined);
                expect(r.length).toBeGreaterThan(0);
                expect(r).toContain("advanced");
            });
        });
    });


    //
    // Should return a list of user names from endpoint "/users/usernames"
    //
    describe('Function: getUsernames()', function() {

        var def = null;

        beforeEach(inject(function(_$q_) {
            //resolve the promise
            def = _$q_.defer();
            def.resolve([
                "jack123",
                "jill123",
                "joe123"
            ]);

            //spyOn function used to check if we have cached data
            spyOn(angular, 'isDefined').andCallThrough();
            spyOn(restApiService, 'getAll').andReturn(def.promise);
        }));

        //First time round the call should hit the server, as nothing is cached
        it('should return a list of usernames from server', function() {
            //invoke the service function
            var result = service.getUsernames();
            scope.$digest();

            //assert expected behaviour
            result.then(function(r) {
                expect(angular.isDefined).toHaveBeenCalled();
                expect(angular.isDefined).toHaveBeenCalledWith(undefined);
                expect(restApiService.getAll).toHaveBeenCalled();
                expect(restApiService.getAll).toHaveBeenCalledWith('users/usernames', null, null);
                expect(r.length).toBeGreaterThan(0);
                expect(r).toContain("jack123");
            });

            //Subsequent calls should read from cached data
            //invoke the service function
            result = null;
            result = service.getUsernames();

            //assert expected behaviour
            result.then(function(r) {
                expect(angular.isDefined).toHaveBeenCalled();
                expect(angular.isDefined).not.toHaveBeenCalledWith(undefined);
                expect(restApiService.getAll).not.toHaveBeenCalled();
                expect(r.length).toBeGreaterThan(0);
                expect(r).toContain("jack123");
            });
        });
    });


    //
    // Given a username, should return user details from endpoint "/users/details/{username}"
    //
    describe('Function: getUserDetails()', function() {

        var def = null;

        beforeEach(inject(function(_$q_) {
            //resolve the promise
            def = _$q_.defer();
            def.resolve({
                data : [{
                    username : "jack123",
                    skill : "ADVANCED",
                    shift : {
                        shiftId : 1
                    },
                    j2uLanguage: {
                        languageCode : "EN_GB"
                    },
                    u2jLanguage : {
                        languageCode : "EN_GB"
                    },
                    hhLanguage : {
                        languageCode : "EN_GB"
                    },
                    amdLanguage : {
                        languageCode : "EN_GB"
                    },
                    employeeNumber : "123456",
                    startDate : "20010509",
                    firstName : "Jack",
                    lastName : "Bloggs",
                    birthDate : "19810712",
                    mobileNumber : "01234567890",
                    emailAddress : "jack123@gmail.com"
                }]
            });

            //spyOn function used to make API call
            spyOn(restApiService, 'post').andReturn(def.promise);
        }));

        //Should get user details from server
        it('should return details of given user', function() {
            //invoke service function
            var result = service.getUserDetails("jack123");
            scope.$digest();

            //assert results
            expect(restApiService.post).toHaveBeenCalled();
            expect(restApiService.post.mostRecentCall.args[0]).toEqual('/users/details');
            expect(restApiService.post.mostRecentCall.args[1]).toBe(null);
            expect(restApiService.post.mostRecentCall.args[2]).toEqual(['jack123']);

            result.then(function(r) {
                expect(r.hasOwnProperty("emailAddress")).toBeTruthy();
                expect(r.hasOwnProperty("firstName")).toBeTruthy();
                expect(r.hasOwnProperty("lastName")).toBeTruthy();
                expect(r.hasOwnProperty("username")).toBeTruthy();
                expect(r.hasOwnProperty("title")).toBeTruthy();
                expect(r.hasOwnProperty("mobileNumber")).toBeTruthy();
                expect(r.hasOwnProperty("startDate")).toBeTruthy();
                expect(r.hasOwnProperty("employeeNumber")).toBeTruthy();
                expect(r.hasOwnProperty("j2uLanguage")).toBeTruthy();
                expect(r.hasOwnProperty("u2jLanguage")).toBeTruthy();
                expect(r.hasOwnProperty("hhLanguage")).toBeTruthy();
                expect(r.hasOwnProperty("amdLanguage")).toBeTruthy();
                expect(r.hasOwnProperty("skill")).toBeTruthy();
                expect(r.hasOwnProperty("shift")).toBeTruthy();
            });
        });
    });


    //
    // Given a username, should return list of groups user is assigned to
    // using endpoint "/groups/{username}"
    //
    describe('Function: getGroupsForUser()', function() {

        var def = null;

        beforeEach(inject(function(_$q_) {
            //resolve promise
            def = _$q_.defer();
            def.resolve({
                data : {
                    "jack123" : [
                        "picker",
                        "admin"
                    ]
                }
            });

            //spyOn function used to make API call
            spyOn(restApiService, 'post').andReturn(def.promise);
        }));

        //Should get user groups using API
        it('should return details of given user', function() {
            //invoke service function
            var result = service.getGroupsForUser("jack123");
            scope.$digest();

            //assert results
            expect(restApiService.post).toHaveBeenCalled();
            expect(restApiService.post.mostRecentCall.args[0]).toEqual('/users/groups');
            expect(restApiService.post.mostRecentCall.args[1]).toBe(null);
            expect(restApiService.post.mostRecentCall.args[2]).toEqual(['jack123']);

            result.then(function(r) {
                expect(r.length).toBeGreaterThan(0);
                expect(r).toContain("picker");
            });
        });
    });


    //
    // Saves/creates user. Sends user details to endpoint "/users/save"
    //
    describe('Function: saveUser()', function() {

        var def = null;

        var userPayload;
        var groupsPayload;

        var userDetails;
        var assignedGroups;

        beforeEach(inject(function(_$q_) {
            //create and resolve promise
            def = _$q_.defer();
            def.resolve(userPayload);

            //mock the function parameters
            userDetails = {};
            userDetails.username = ["jack123"];
            userDetails.firstName = "Jack";
            userDetails.lastName = "Bloggs";
            userDetails.employeeNumber = "JA1072";
            userDetails.skill = "ADVANCED";
            userDetails.mobileNumber = "1234567890";
            userDetails.emailAddress = "jack123@domain.com";
            userDetails.startDate = "2014-01-04";
            userDetails.birthDate = "1815-06-29";
            userDetails.shift = "1";
            userDetails.amdLanguage = "EN_US";
            userDetails.hhLanguage = "EN_GB";
            userDetails.u2jLanguage = "DE_DE";
            userDetails.j2uLanguage = "DE_DE";
            userDetails.password = "";
            userDetails.hostLogin = "";
            userDetails.hostPassword = "";

            assignedGroups = ["picker", "supervisor"];

            //spyOn function used to make API call
            spyOn(restApiService, 'post').andReturn(def.promise);
        }));

        //should save the user
        it('should save the user', function() {

            //invoke service function, passing in mock parameters
            var result = service.saveUser(userDetails, assignedGroups);
            scope.$digest();

            //expected payloads to be constructed by the service functions
            userPayload = {
                "username": "jack123",
                "firstName":"Jack",
                "lastName":"Bloggs",
                "employeeNumber":"JA1072",
                "skill":"ADVANCED",
                "mobileNumber":"1234567890",
                "emailAddress":"jack123@domain.com",
                "startDate":"2014-01-04",
                "birthDate":"1815-06-29",
                "shift":{
                    "shiftId":"1"
                },
                "amdLanguage":{
                    "languageCode":"EN_US"
                },
                "hhLanguage":{
                    "languageCode":"EN_GB"
                },
                "u2jLanguage":{
                    "languageCode":"DE_DE"
                },
                "j2uLanguage":{
                    "languageCode":"DE_DE"
                },
                "hostLogin" : "",
                "hostPassword" : ""
            };
            groupsPayload = {
                "userName" : "jack123",
                "assignedGroups" : [
                    "picker",
                    "supervisor"
                ]
            };

            //assert results
            expect(restApiService.post.callCount).toEqual(2);

            var callOne = restApiService.post.argsForCall[0];
            var callTwo = restApiService.post.argsForCall[1];

            expect(callOne[0]).toEqual("/users/save");
            expect(callOne[1]).toBe(null);
            expect(callOne[2]).toEqual(userPayload);

            expect(callTwo[0]).toEqual("/users/groups/assign");
            expect(callTwo[1]).toBe(null);
            expect(callTwo[2]).toEqual(groupsPayload);
        });
    });


    //
    // Checks if the user exists or not
    // This one is a little tricky to test, since it reads a private variable that isn't
    // accessible. So to be able to test this function, we fist need to call "getUsersnames" to
    // set the private variable this function reads.
    //
    describe('Function: checkUserExists()', function() {

        var def = null;

        beforeEach(inject(function(_$q_) {
            //resolve the promise
            def = _$q_.defer();
            def.resolve([
                "jack123",
                "jill123",
                "joe123"
            ]);

            //return a dummy promise
            spyOn(restApiService, 'getAll').andReturn(def.promise);

            //Invoke the getUsernames function to set the private usernames variable
            service.getUsernames();
            scope.$digest();
        }));

        it('should return TRUE if user exists, FALSE if the user does not exist', function() {
            //invoke the function and asset the results
            expect(service.checkUserExists("jack123")).toBeTruthy();
            expect(service.checkUserExists("someuser1234")).toBeFalsy();
        });
    });


    //
    // Reloads the user cache in order to keep client-side cache in-sync with DB
    //
    describe('Function: reloadUserCache()', function() {

        var def = null;

        beforeEach(inject(function(_$q_) {
            //setup new promise
            def = _$q_.defer();
        }));

        //reloading the user cache
        it('should reload the user cache', function() {
            //spyon the service function that goes to the server and fetches the users, and
            //return a resolved promise
            def.resolve(["jack123", "jill123"]);
            spyOn(service, 'getUsernames').andReturn(def.promise);

            //invoke the service function to reload the cache and digest the scope to
            //resolve the promises
            service.reloadUsernameCache();
            scope.$digest();

            //Assert the results
            expect(service.getUsernames).toHaveBeenCalled();
            expect(service.checkUserExists("jack123")).toBeTruthy();
            expect(service.checkUserExists("jill123")).toBeTruthy();
        });

        //should fail gracefully when something goes wrong
        it('should fail gracefully when an error occurs reloading user cache', function() {
            //spyon the service function that goes to the server and fetches the users, and
            //return a rejected promise
            def.reject("Internal server error");
            spyOn(service, 'getUsernames').andReturn(def.promise);

            //invoke the service function to reload the cache and digest the scope to
            //resolve the promises
            service.reloadUsernameCache();
            scope.$digest();

            //Assert the results
            expect(service.getUsernames).toHaveBeenCalled();
            expect(service.checkUserExists("jack123")).toBeFalsy();
            expect(service.checkUserExists("jill123")).toBeFalsy();
        });
    });
    
    describe('handle getMultiUsersDetails method', function() {
        var def;
        beforeEach(inject(function(_$q_) {
            //setup new promise
            def = _$q_.defer();
        }));

        it('handle getMultiUsersDetails method', function() {
            spyOn(restApiService, 'post').andReturn(def.promise);
            service.getMultiUsersDetails();
            scope.$digest();
            expect(restApiService.post).toHaveBeenCalled();
        });
    });

    describe('handle saveMultiUsersDetails method', function() {
        var def;
        beforeEach(inject(function(_$q_) {
            //setup new promise
            def = _$q_.defer();
        }));

        it('handle saveMultiUsersDetails method', function() {
            spyOn(restApiService, 'post').andReturn(def.promise);
            service.saveMultiUsersDetails();
            scope.$digest();
            expect(restApiService.post).toHaveBeenCalled();
        });
    });

});