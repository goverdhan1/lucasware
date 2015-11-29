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

describe('PreferencesService related Tests', function () {

    // locals
    var $scope;
    var PreferencesService;
    var RestApiService;
    var $q;

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


    beforeEach(inject(function (_$rootScope_, _PreferencesService_, _RestApiService_, _$q_) {
        $scope = _$rootScope_.$new();
        PreferencesService = _PreferencesService_;
        RestApiService = _RestApiService_;
        $q = _$q_;
    }));


    describe('Dependency Injection', function () {

        it('should inject the required dependencies', function () {
           expect($scope).toBeDefined();
           expect(PreferencesService).toBeDefined();
           expect(RestApiService).toBeDefined();
           expect($q).toBeDefined();
        });
    });


    describe('Getting Preferences', function () {

        var def;

        beforeEach(function() {
            //mock the server response
            def = $q.defer();
            spyOn(RestApiService, 'getOne').andReturn(def.promise);
        });

        it('should get the user preferences', function () {
            //resolve the promise
            def.resolve({
                "dateFormat" : "DD-MM-YYYY",
                "timeFormat" : "24HR",
                "dataRefreshFrequency" : "120"
            });

            //invoke service function and assert results
            var result = null;
            PreferencesService.getUserPreferences('jack123')
                .then(function(r) {
                    result = r;
                }
            );
            $scope.$digest();

            expect(RestApiService.getOne).toHaveBeenCalled();
            expect(RestApiService.getOne.callCount).toBe(1);

            var args = RestApiService.getOne.mostRecentCall.args;
            expect(args[0]).toEqual('users/preferences/jack123');

            expect(result.dateFormat).toEqual("DD-MM-YYYY");
            expect(result.timeFormat).toEqual("24HR");
            expect(result.dataRefreshFrequency).toEqual("120");
        });
    });


    describe('Saving Preferences', function () {

        beforeEach(function() {
            spyOn(RestApiService, 'post').andReturn();
        });

        it('should save the user preferences', function () {
            //preferences to save
            var prefs = {
                "userName" : "jack123",
                "dateFormat" : "DD-MM-YYYY",
                "timeFormat" : "24HR",
                "dataRefreshFrequency" : "120"
            };

            //invoke service function and expect a call to be made to the
            //Save Preference API
            PreferencesService.saveUserPreferences(prefs);

            expect(RestApiService.post).toHaveBeenCalled();
            expect(RestApiService.post.callCount).toBe(1);

            var args = RestApiService.post.mostRecentCall.args;
            expect(args[0]).toEqual('users/preferences/save');
            expect(args[1]).toBe(null);
            expect(args[2].userName).toEqual('jack123');
            expect(args[2].dateFormat).toEqual('DD-MM-YYYY');
            expect(args[2].timeFormat).toEqual('24HR');
            expect(args[2].dataRefreshFrequency).toEqual('120');
        });
    });


    describe('Getting Preference Options', function () {

        var def;

        beforeEach(function() {
            def = $q.defer();
            spyOn(RestApiService, 'post').andReturn(def.promise);
        });

        it('should get the preference options required for rendering the UI', function () {
            //resolve the promise
            def.resolve({
                "DATE_FORMAT": [
                    {
                        "value": "MM-DD-YYYY",
                        "displayOrder": "10",
                        "key": "MM-DD-YYYY"
                    },
                    {
                        "value": "DD-MM-YYYY",
                        "displayOrder": "20",
                        "key": "DD-MM-YYYY"
                    }
                ],
                "TIME_FORMAT": [
                    {
                        "value": "12HR",
                        "displayOrder": "10",
                        "key": "12HR"
                    },
                    {
                        "value": "24HR",
                        "displayOrder": "20",
                        "key": "24HR"
                    }
                ]
            });

            //invoke service function and assert results
            var result = null;
            PreferencesService.getPreferencesOptions().then(function(r) {
                result = r;
            });
            $scope.$digest();

            expect(RestApiService.post).toHaveBeenCalled();
            expect(RestApiService.post.callCount).toBe(1);

            var args = RestApiService.post.mostRecentCall.args;
            expect(args[0]).toEqual('application/codes');
            expect(args[1]).toBe(null);
            expect(args[2].length).toEqual(2);
            expect(args[2][0]).toEqual("DATE_FORMAT");
            expect(args[2][1]).toEqual("TIME_FORMAT");

            expect(result.DATE_FORMAT.length).toEqual(2);
            expect(result.DATE_FORMAT[0].key).toEqual("MM-DD-YYYY");
            expect(result.DATE_FORMAT[1].key).toEqual("DD-MM-YYYY");

            expect(result.TIME_FORMAT.length).toEqual(2);
            expect(result.TIME_FORMAT[0].key).toEqual("12HR");
            expect(result.TIME_FORMAT[1].key).toEqual("24HR");
        });
    });


    describe('Compiling the User Preferences and available Preference Options', function () {

        var defUserPreferences;
        var defPreferenceOptions;

        beforeEach(function() {
            //deferred executions
            defUserPreferences = $q.defer();
            defPreferenceOptions = $q.defer();

            //spies
            spyOn(PreferencesService, 'getUserPreferences')
                .andReturn(defUserPreferences.promise);
            spyOn(PreferencesService, 'getPreferencesOptions')
                .andReturn(defPreferenceOptions.promise);
        });

        it('should get the preference options required for rendering the UI', function () {
            //resolve the promises
            defUserPreferences.resolve({
                "dateFormat" : "DD-MM-YYYY",
                "timeFormat" : "24HR",
                "dataRefreshFrequency" : "120"
            });

            defPreferenceOptions.resolve({
                "DATE_FORMAT": [
                    {
                        "value": "MM-DD-YYYY",
                        "displayOrder": "10",
                        "key": "MM-DD-YYYY"
                    },
                    {
                        "value": "DD-MM-YYYY",
                        "displayOrder": "20",
                        "key": "DD-MM-YYYY"
                    }
                ],
                "TIME_FORMAT": [
                    {
                        "value": "12HR",
                        "displayOrder": "10",
                        "key": "12HR"
                    },
                    {
                        "value": "24HR",
                        "displayOrder": "20",
                        "key": "24HR"
                    }
                ]
            });

            //invoke service function and store the result
            var result = null;
            PreferencesService.getPreferenceData("jack123").then(function(r) {
                result = r;
            });
            $scope.$digest();

            //assert results
            expect(PreferencesService.getUserPreferences).toHaveBeenCalled();
            expect(PreferencesService.getUserPreferences.callCount).toBe(1);
            expect(PreferencesService.getUserPreferences.mostRecentCall.args[0]).toEqual("jack123");

            expect(PreferencesService.getPreferencesOptions).toHaveBeenCalled();
            expect(PreferencesService.getPreferencesOptions.callCount).toBe(1);

            expect(result.hasOwnProperty("preferences")).toBeTruthy();
            expect(result.preferences.dateFormat).toEqual("DD-MM-YYYY");
            expect(result.preferences.timeFormat).toEqual("24HR");
            expect(result.preferences.dataRefreshFrequency).toEqual(120);

            expect(result.hasOwnProperty("options")).toBeTruthy();
            expect(result.options.DATE_FORMAT[0].key).toEqual("MM-DD-YYYY");
            expect(result.options.DATE_FORMAT[1].key).toEqual("DD-MM-YYYY");

            expect(result.options.TIME_FORMAT[0].key).toEqual("12HR");
            expect(result.options.TIME_FORMAT[1].key).toEqual("24HR");
        });
    });
});