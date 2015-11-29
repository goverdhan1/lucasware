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

describe('WidgetInteractionsController Unit Tests', function() {

    // Global vars
    var localWidgetInteractionsController = null;
    var localScope = null;
    var localUtilsService = null;

    // Global test setup
    beforeEach(module('amdApp', function($translateProvider) {
        $translateProvider.translations('en', {
            "language-code": "EN",
        }, 'fr', {
            "language-code": "fr",
        }, 'de', {
            "language-code": "de",
        }).preferredLanguage('en');
        $translateProvider.useLoader('LocaleLoader');
    }));

    beforeEach(inject(function($rootScope, $controller, UtilsService) {
        localUtilsService = UtilsService;
        spyOn(localUtilsService, 'getWidgetDetailsOfActiveCanvas').andReturn([]);
        localScope = $rootScope.$new();
        localWidgetInteractionsController = $controller('WidgetInteractionsController', {
            $scope: localScope,
            $element: '',
            UtilsService: localUtilsService
        });
    }));

    it('injection tests', function() {
        expect(localWidgetInteractionsController).toBeDefined();
    });

    it('should get getWidgetInstancesList', function() {
        expect(localWidgetInteractionsController.getWidgetInstancesList()).toEqual([]);
    });
});