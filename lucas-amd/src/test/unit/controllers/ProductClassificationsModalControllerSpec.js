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

describe('Product Classifications popup Controller Unit Tests', function() {

    // Global vars
    var def = null;
    var localProductClassificationsModalController = null;
    var localScope = null;
    var localModalInstance = null;
    var localProductClassificationsService = null;

    // Global test setup
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

    beforeEach(inject(function($rootScope, $modal, $q, $templateCache) {
        localScope = $rootScope.$new();
        localModalInstance = $modal;
        localProductClassificationsService = {
            getClassifications: function() {
                def = $q.defer();
                // Resolve the promise
                def.resolve([{
                    "status": "success",
                    "code": "200",
                    "message": "Request processed successfully",
                    "level": null,
                    "uniqueKey": null,
                    "token": null,
                    "explicitDismissal": null,
                    "data": {
                        "grid": {
                            "1": {
                                "values": [
                                    "custom_classification_1",
                                    "custom_classification_2",
                                    "custom_classification_3"
                                ]
                            },
                            "2": {
                                "values": [
                                    "Brand",
                                    "Category",
                                    "Sub-Category"
                                ]
                            },
                            "3": {
                                "values": [{
                                        "Brand": [
                                            "Kellogg's",
                                            "Nestle",
                                            "Quaker"
                                        ],
                                        "Category": [
                                            "Kellogg's",
                                            "Nestle",
                                            "Quaker"
                                        ],
                                        "Sub-Category": [
                                            "Kellogg's",
                                            "Nestle",
                                            "Quaker"
                                        ]
                                    },
                                    null,
                                    null
                                ]
                            }
                        }
                    }
                }]);
                return def.promise;
            },
            deleteComponent: function(payload) {
                def = $q.defer();
                // Resolve the promise
                def.resolve(true);
                return def.promise;
            },
            saveClassifications: function(payload) {
                def = $q.defer();
                // Resolve the promise
                def.resolve(true);
                return def.promise;
            }
        };
    }));

    // Inject Specs
    describe('About Lucas Controller Injection Specs', function() {

        beforeEach(inject(function($controller) {
            localProductClassificationsModalController = $controller('ProductClassificationsModalController', {
                $scope: localScope,
                $modalInstance: localModalInstance,
                mockProductClassificationsService: localProductClassificationsService
            });
        }));

        it('should inject ModalInstance', function() {
            expect(localModalInstance).toBeDefined();
        });
        it('should inject $scope', function() {
            expect(localScope).toBeDefined();
        });
        it('should be defined in scope', function() {
            localScope.$digest();
            expect(localScope.closeModal).toBeDefined();
            expect(localScope.save).toBeDefined();
            expect(localScope.addClassification).toBeDefined();
            expect(localScope.buildClassifications).toBeDefined();
            expect(localScope.disable).toBeDefined();
            expect(localScope.classifications).toBeDefined();
        });
    });
});
