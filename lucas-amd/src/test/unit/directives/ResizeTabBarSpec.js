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

"use strict";

describe('resizeTabBar Directive Unit tests', function() {
    var $compile = null;
    var localScope = null;
    var element = null;
    var controller;

    // Load the amdApp module, which contains the directive
    beforeEach(module('amdApp', function (_$translateProvider_) {
        _$translateProvider_.translations('en', {
            "language-code": "EN"
        }, 'fr', {
            "language-code": "fr"
        }, 'de', {
            "language-code": "de"
        }).preferredLanguage('en');
        _$translateProvider_.useLoader('LocaleLoader');
    }));

    describe('should render resizeTabBar properly', function() {
        // The injector unwraps the underscores (_) from around the parameter names when matching
        beforeEach(inject(function(_$compile_, _$rootScope_){
            $compile = _$compile_;
            localScope = _$rootScope_.$new();

            //create the directive
            element = angular.element('<div resize-tab-bar></div>');
            $compile(element)(localScope);
            
            localScope.$digest();
            controller = element.controller('ResizeTabBarController');
            
        }));

        it('should inject test dependencies', function() {
            expect(localScope).toBeDefined();
            expect($compile).toBeDefined();
            expect(element).toBeDefined();
        });

        it('should render resizeTabBar properly', function() {
            expect(element.length).toBeGreaterThan(0);
        });
    });

    // need to check how to access the controller inside the directive
    xit('should handle init() method', function() {
    });
});