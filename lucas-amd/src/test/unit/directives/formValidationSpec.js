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

describe('confirmPassword directive Unit tests', function () {
    var $compile = null;
    var scope, httpBackend, form;

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

    beforeEach(inject(function (_$compile_, _$rootScope_, _$httpBackend_) {
        $compile = _$compile_;
        $rootScope = _$rootScope_;
        httpBackend=_$httpBackend_;
        scope=$rootScope.$new();
        httpBackend.when("GET").respond(null);
        var element = angular.element('<div><form name="form">'+
            '<input type="password" id="password" name="password" ng-model="fields.password">'+
            'scope type="password" ng-model="fields.confirm_password" id="confirm_password" name="confirm_password" pwmatch="true" pwcheck="password">'+
            '<input type="submit" />'+
            '</form></div>');
        scope.fields={password:"abcd", confirm_password:"abcd1"};
        $compile(element)(scope);
        scope.$digest();
        form=scope.form;
    }));
        it('should mark as valid Form', function () {
            //scope.form.password.$setViewValue('abcd');
            //scope.form.confirm_password.$setViewValue('abcd');

            scope.fields.password='abcd';
            scope.fields.confirm_password='abcd';
            scope.$digest();
            expect(scope.form.$valid).toBe(true);
        });
        xit('should mark as invalid Form', function () {
            scope.fields.password='abcd';
            scope.fields.confirm_password='abcd1';

            //scope.form.password.$setViewValue('abcd');
            //scope.form.confirm_password.$setViewValue('abcd1');
            scope.$digest();
            expect(scope.form.$valid).toBe(false);
        });
});