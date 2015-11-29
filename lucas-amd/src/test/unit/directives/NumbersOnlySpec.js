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

describe('NumbersOnly Directive unit tests', function() {

    var $scope;
    var element;
    var ngModelController;

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

    beforeEach(inject(function(_$compile_, _$rootScope_, _$httpBackend_) {

        _$httpBackend_.when("GET").respond({data : {}});

        //setup the scope and model bindings
        $scope = _$rootScope_;

        //compile the element to the scope
        element = angular.element('<form name="preferencesForm"><input ng-model="testModel" type="text" numbers-only></form>');
        _$compile_(element)($scope);
        $scope.$digest();

        //grab the ngModel controller binding for the ng-model attribute. We'll use this to trigger the directive
        //when the we change the inputs value
        ngModelController = element.find('input').controller('ngModel');
    }));

    it('should compile and bind the directive to rootScope', function () {
        expect($scope).toBeDefined();
        expect(element).toBeDefined();
    });

    it('should permit integer values', function() {
        //set the value to an integer value
        ngModelController.$setViewValue('30');
        $scope.$digest();

        //assert model is updated with correct value
        expect($scope.testModel).toEqual('30');
    });

    it('should strip out any string values', function() {
        //set the value to a string
        ngModelController.$setViewValue('hdfjh');
        $scope.$digest();

        //assert model is updated with correct value
        expect($scope.testModel).toEqual('');

        //set value to mix of string and number
        ngModelController.$setViewValue('hd45fjh');
        $scope.$digest();

        //assert model is updated with correct value
        expect($scope.testModel).toEqual('45');
    });

    it('should strip out any spaces', function() {
        //set the value to a string
        ngModelController.$setViewValue('  12   0   ');
        $scope.$digest();

        //assert model is updated with correct value
        expect($scope.testModel).toEqual('120');
    });
});