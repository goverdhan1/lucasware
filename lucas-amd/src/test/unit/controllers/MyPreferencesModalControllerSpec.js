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

describe('Preferences Modal Controller Unit Tests', function () {

    var controller;
    var $scope;
    var $modalInstance;
    var UserService;
    var PreferencesService;


    //
    // Load translation provider
    //
    beforeEach(module('amdApp', function($translateProvider) {
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
    // Global Test Setup - inject necessary dependencies
    //
    beforeEach(inject(function(_$rootScope_, _UserService_, _PreferencesService_) {
        $scope = _$rootScope_.$new();
        UserService = _UserService_;
        PreferencesService = _PreferencesService_;
        $modalInstance = {
            result: {
                then: function (confirmCallback, cancelCallback) {
                    //Store the callbacks for later when the user clicks on the OK or Cancel button of the dialog
                    this.confirmCallBack = confirmCallback;
                    this.cancelCallback = cancelCallback;
                }
            },
            close: function (item) {
                //The user clicked OK on the modal dialog, call the stored confirm callback with the selected item
                this.result.confirmCallBack(item);
            },
            dismiss: function (type) {
                //The user clicked cancel on the modal dialog, call the stored cancel callback
                this.result.cancelCallback(type);
            }
        };
    }));


    //
    // Controller initialisation
    //
    describe('Controller initialisation', function() {

        var def;

        beforeEach(inject(function(_$controller_, _$q_) {
            //spies
            def = _$q_.defer();
            spyOn(UserService, 'getCurrentUser').andReturn('jack123');
            spyOn(PreferencesService, 'getPreferenceData').andReturn(def.promise);

            //inject the controller
            controller = _$controller_('MyPreferencesModalController', {
                $scope: $scope,
                $modalInstance : $modalInstance,
                UserService : UserService,
                PreferenceService : PreferencesService
            });
        }));

        it('should inject dependencies and initialise controller', function() {
            expect($scope).toBeDefined();
            expect($modalInstance).toBeDefined();

            expect(UserService).toBeDefined();
            expect(UserService.getCurrentUser).toHaveBeenCalled();
            expect(UserService.getCurrentUser.callCount).toEqual(1);

            expect(PreferencesService).toBeDefined();
            expect(PreferencesService.getPreferenceData).toHaveBeenCalled();
            expect(PreferencesService.getPreferenceData.callCount).toEqual(1);
            expect(PreferencesService.getPreferenceData.mostRecentCall.args[0]).toEqual('jack123');
        });
    });


    //
    // Getting and rendering the preference data
    //
    describe('Getting and rendering preference data', function() {

        var def;

        beforeEach(inject(function(_$controller_, _$q_) {
            //spies
            def = _$q_.defer();
            spyOn(PreferencesService, 'getPreferenceData').andReturn(def.promise);
            spyOn(UserService, 'getCurrentUser').andReturn('jack123');

            //inject the controller
            controller = _$controller_('MyPreferencesModalController', {
                $scope: $scope,
                $modalInstance : $modalInstance,
                UserService : UserService,
                PreferenceService : PreferencesService
            });
        }));

        it('should get the preference data and assign it to scope bindings', function() {
            //resolve the promise with the preference data
            def.resolve({
                "options" : {
                    "DATE_FORMAT" : [{
                        "displayOrder" : "10",
                        "key" : "DD-MM-YYYY",
                        "value" : "DD-MM-YYYY"
                    },
                    {
                        "displayOrder" : "20",
                        "key" : "MM-DD-YYYY",
                        "value" : "MM-DD-YYYY"
                    }],
                    "TIME_FORMAT" : [{
                        "displayOrder" : "10",
                        "key" : "12HR",
                        "value" : "12 Hour"
                    },
                    {
                        "displayOrder" : "20",
                        "key" : "24HR",
                        "value" : "24 Hour"
                    }]
                },
                "preferences" : {
                    "dateFormat" : "DD-MM-YYYY",
                    "timeFormat" : "24 Hour",
                    "dataRefreshFrequency" : 120
                }
            });
            $scope.$digest();

            //Assert the scope bindings have been assigned the correct values
            expect(UserService).toBeDefined();
            expect(UserService.getCurrentUser).toHaveBeenCalled();
            expect(UserService.getCurrentUser.callCount).toEqual(1);

            expect(PreferencesService).toBeDefined();
            expect(PreferencesService.getPreferenceData).toHaveBeenCalled();
            expect(PreferencesService.getPreferenceData.callCount).toEqual(1);
            expect(PreferencesService.getPreferenceData.mostRecentCall.args[0]).toEqual('jack123');

            expect($scope.dateFormats).toBeDefined();
            expect($scope.dateFormats.length).toEqual(2);
            expect($scope.dateFormats[0].value).toEqual("DD-MM-YYYY");
            expect($scope.dateFormats[1].value).toEqual("MM-DD-YYYY");

            expect($scope.timeFormats).toBeDefined();
            expect($scope.timeFormats.length).toEqual(2);
            expect($scope.timeFormats[0].value).toEqual("12 Hour");
            expect($scope.timeFormats[1].value).toEqual("24 Hour");

            expect($scope.selectedDateFormat).toBeDefined();
            expect($scope.selectedDateFormat).toEqual("DD-MM-YYYY");


            expect($scope.selectedTimeFormat).toBeDefined();
            expect($scope.selectedTimeFormat).toEqual("24 Hour");

            expect($scope.refreshInterval).toBeDefined();
            expect($scope.refreshInterval).toEqual(120);
        });
    });


    //
    // Saving & cancelling preference changes
    //
    describe('Saving & Cancelling preference changes', function() {

        var $rootScope;
        var preferenceDataPromise;
        var savePreferencesPromise;

        beforeEach(inject(function(_$controller_, _$q_, _$rootScope_) {

            $rootScope = _$rootScope_.$new();

            preferenceDataPromise = _$q_.defer();
            savePreferencesPromise = _$q_.defer();

            //spies
            spyOn(UserService, 'getCurrentUser').andReturn('jack123');
            spyOn(PreferencesService, 'getPreferenceData').andReturn(preferenceDataPromise.promise);
            spyOn(PreferencesService, 'saveUserPreferences').andReturn(savePreferencesPromise.promise);
            spyOn($rootScope, '$emit').andReturn();

            //inject the controller
            controller = _$controller_('MyPreferencesModalController', {
                $rootScope : $rootScope,
                $scope: $scope,
                $modalInstance : $modalInstance,
                UserService : UserService,
                PreferenceService : PreferencesService
            });
        }));

        it('should discard any changes', function() {
            // mock closing the modal
            $modalInstance.dismiss();

            //assert the preferences we're not save
            expect(PreferencesService.saveUserPreferences).not.toHaveBeenCalled();
        });

        it('should save preference changes', function () {
            //resolve promises, so scope bindings are updated with
            //preference data
            savePreferencesPromise.resolve({});
            preferenceDataPromise.resolve({
                "options" : {
                    "DATE_FORMAT" : [{
                        "displayOrder" : "10",
                        "key" : "DD-MM-YYYY",
                        "value" : "DD-MM-YYYY"
                    },
                        {
                            "displayOrder" : "20",
                            "key" : "MM-DD-YYYY",
                            "value" : "MM-DD-YYYY"
                        }],
                    "TIME_FORMAT" : [{
                        "displayOrder" : "10",
                        "key" : "12HR",
                        "value" : "12HR"
                    },
                        {
                            "displayOrder" : "20",
                            "key" : "24HR",
                            "value" : "24HR"
                        }]
                },
                "preferences" : {
                    "dateFormat" : "DD-MM-YYYY",
                    "timeFormat" : "24HR",
                    "dataRefreshFrequency" : 120
                }
            });
            $scope.$digest();

            //close the modal, resulting in saving the changes
            $modalInstance.close();
            $scope.$digest();

            //assert a call was made to save the changes
            expect(PreferencesService.saveUserPreferences).toHaveBeenCalled();
            expect(PreferencesService.saveUserPreferences.callCount).toEqual(1);

            expect($rootScope.$emit).toHaveBeenCalled();

            var args = PreferencesService.saveUserPreferences.mostRecentCall.args[0];
            expect(args.userName).toEqual("jack123");
            expect(args.dateFormat).toEqual("DD-MM-YYYY");
            expect(args.timeFormat).toEqual("24HR");
            expect(args.dataRefreshFrequency).toEqual(120);
        })
    });
});