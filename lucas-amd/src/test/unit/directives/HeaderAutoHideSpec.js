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

describe('HeaderAutoHide Directive Unit tests', function () {
    var $compile = null;
    var scope = null;
    var element = null;
    var httpBackend;
    var $timeout;

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

    beforeEach(inject(function (_$compile_, _$rootScope_, _$httpBackend_, _$timeout_){
        $compile = _$compile_;
        httpBackend = _$httpBackend_;
        httpBackend.when('GET').respond({});
        scope = _$rootScope_.$new();
        $timeout  = _$timeout_;
        //create the directive
        element = $compile("<header-auto-hide></header-auto-hide>")(scope);

        spyOn(element, 'bind').andCallThrough();

        scope.$digest();
        httpBackend.flush();
    }));

    it('should inject test dependencies', function () {
        expect(scope).toBeDefined();
        expect($compile).toBeDefined();
        expect(element).toBeDefined();
    });

    it('test HeaderAutoHide directive events, methods', function () {
        var isolatedScope = element.isolateScope();

        expect(element.length).toBeGreaterThan(0);
        element.triggerHandler('mouseenter');
        expect(isolatedScope.onMouseOver).toBe(true);
        element.triggerHandler('mouseleave');
        expect(isolatedScope.onMouseOver).toBe(false);
    });
});