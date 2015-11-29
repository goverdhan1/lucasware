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

 

describe('uiGridFilterInput Directive Unit tests', function () {
    var compile = null;
    var $rootScope = null;
    var httpBackend;
    var scope;
    var WidgetGridService;


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

    beforeEach(inject(function (_$compile_, _$rootScope_, _$httpBackend_, WidgetGridService) {
        compile = _$compile_;
        $rootScope = _$rootScope_;
        httpBackend=_$httpBackend_;
        scope=$rootScope.$new();
        WidgetGridService=WidgetGridService;
        scope.defaultFilterOptions=["noFilter", "nonEmpty", "empty"];
        var templateHtml ='<datalist id="filterList">'+
            '<option ng-repeat="items in defaultFilterOptions" value="{{items}}">'+
            '</datalist>';
        httpBackend.when("GET").respond(templateHtml);
        httpBackend.when("POST").respond({});
        scope.colFilter={
            'option': null, 
            'alphaOption':  null, 
            'alphaValue':  null, 
            'numericOption': null, 
            'numericValue': null, 
            'startDate': null, 
            'startTime': null, 
            'endDate': null, 
            'endTime': null
        }


    }));

    it('Should test uiGridFilterInput directive for "search-user-grid"', function () {

        scope.grid={
            'options':{'gridName':'search-user-grid'}
        };

        scope.col={
            'colDef':{'name':"skill"}
        }

        //create the directive
        var element = compile('<input class="ui-grid-filter-input" type="text" />')(scope);
        scope.$digest();
        //  Check that the compiled element contains the templated content
        expect(element.find('.ui-grid-custom-filter-input')).toBeTruthy();


    });
    it('Should test uiGridFilterInput directive for "search-group-grid"', function () {

        scope.grid={
            'options':{'gridName':'search-group-grid'}
        };

        scope.col={
            'colDef':{'name':"groupName"}
        }

        //create the directive
        var element = compile('<input class="ui-grid-filter-input" type="text" />')(scope);
        scope.$digest();
        //  Check that the compiled element contains the templated content
        expect(element.find('.ui-grid-custom-filter-input')).toBeTruthy();


    });

});