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

describe('widgetInteractions Directive Unit tests', function() {
    var $compile,
        $timeout,
        scope,
        element;

    var httpBackend;

    // Load the amdApp module, which contains the directive
    beforeEach(module('amdApp', function(_$translateProvider_) {
        _$translateProvider_.translations('en', {
            "language-code": "EN"
        }, 'fr', {
            "language-code": "fr"
        }, 'de', {
            "language-code": "de"
        }).preferredLanguage('en');
        _$translateProvider_.useLoader('LocaleLoader');
    }));

    beforeEach(inject(function(_$compile_, _$rootScope_, _$httpBackend_, _$timeout_) {
        $compile = _$compile_;
        httpBackend = _$httpBackend_;
        $timeout = _$timeout_;
        var templateHtml = "";
        templateHtml += "<div class=\"widgetInteractions\">";
        templateHtml += "    ";
        templateHtml += "   <button class=\"btn-small btn-success add-row\" ";
        templateHtml += "           data-ng-click=\"addRow()\">";
        templateHtml += "       <span class=\"glyphicon glyphicon-plus\"><\/span>";
        templateHtml += "   <\/button>";
        templateHtml += "";
        templateHtml += "   <div class=\"table-responsive\">";
        templateHtml += "            <table class=\"table table-striped\">";
        templateHtml += "                <tr data-ng-repeat=\"row in widgetInteractionConfig\">";
        templateHtml += "                    <td>";
        templateHtml += "                       <input type=\"checkbox\" data-ng-model=\"row.active\">";
        templateHtml += "                    <\/td>";
        templateHtml += "                    <td>";
        templateHtml += "                       <select data-ng-model=\"row.widgetInstance\" ";
        templateHtml += "                               data-ng-options=\"instance.name for instance in widgetInstances track by instance.id\">";
        templateHtml += "                       <\/select>";
        templateHtml += "                    <\/td>";
        templateHtml += "                    <td>";
        templateHtml += "                       <select data-ng-model=\"row.dataElement\" ";
        templateHtml += "                               data-ng-options=\"data for data in listenTo\">";
        templateHtml += "                       <\/select>";
        templateHtml += "                       <button class=\"btn-small btn-danger\" ";
        templateHtml += "                               data-ng-click=\"removeRow(row, $index)\">";
        templateHtml += "                           <span class=\"glyphicon glyphicon-remove\"><\/span>";
        templateHtml += "                       <\/button>";
        templateHtml += "                    <\/td>";
        templateHtml += "                <\/tr>";
        templateHtml += "            <\/table>";
        templateHtml += "    <\/div>";
        templateHtml += "<\/div>";

        httpBackend.when('GET').respond(templateHtml);

        scope = _$rootScope_.$new();
        scope.widgetInteractionConfig = [];

        //create the directive
        element = $compile("<widget-interactions data-widget-id='32' data-listen-to='listenTo' data-listen-to-widgets='[\"SearchUser\"]'data-widget-interaction-config='widgetInteractionConfig'></widget-interactions>")(scope);

        scope.$digest();
        httpBackend.flush();
    }));

    it('should inject test dependencies', function() {
        expect(scope).toBeDefined();
        expect($compile).toBeDefined();
        expect(element).toBeDefined();
        expect(element.isolateScope()).toBeDefined();
    });

    // test addRow method
    it('Should handle addRow method', function() {
        var isolatedScope = element.isolateScope();
        isolatedScope.addRow();
        // test row is added
        expect(angular.equals(isolatedScope.widgetInteractionConfig, [{}])).toBeTruthy();
        scope.$digest();
        // test presence of DOM elements
        expect(element.find('button.btn-danger').length).toBe(1);
    });

    // test checkbox active selection
    it('Should checkbox active selection', function() {
        var isolatedScope = element.isolateScope();
        isolatedScope.addRow();
        scope.$digest();
        expect(angular.equals(isolatedScope.widgetInteractionConfig, [{}])).toBeTruthy();
        $timeout(function() {
            element.find('input[type="checkbox"]').prop('checked', true);
            scope.$digest();
            expect(element.find('input[type="checkbox"]').prop('checked')).toBeTruthy();
            expect(angular.equals(isolatedScope.widgetInteractionConfig, [{
                active: true
            }])).toBeTruthy();
        }, 0);
    });

    // test removeRow method
    it('Should handle removeRow method', function() {
        var isolatedScope = element.isolateScope();
        isolatedScope.addRow();
        scope.$digest();
        expect(angular.equals(isolatedScope.widgetInteractionConfig, [{}])).toBeTruthy();
        isolatedScope.removeRow(null, 0);
        expect(isolatedScope.widgetInteractionConfig).toEqual([]);
        scope.$digest();
        // test absence of DOM elements
        expect(element.find('button.btn-danger').length).toBe(0);
    });

    // test removeRow method through DOM 
    it('Should handle removeRow method', function() {
        var isolatedScope = element.isolateScope();
        isolatedScope.addRow();
        scope.$digest();
        expect(angular.equals(isolatedScope.widgetInteractionConfig, [{}])).toBeTruthy();
        scope.$digest();
        element.find('button.btn-danger').trigger('click');
        expect(isolatedScope.widgetInteractionConfig).toEqual([]);
    });

});