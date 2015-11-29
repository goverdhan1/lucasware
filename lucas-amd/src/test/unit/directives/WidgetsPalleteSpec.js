/**
 * Created by Shaik Basha on 1/23/2015.
 */

'use strict';

describe('widgetsPallete Directive Unit tests', function () {
    var $compile = null;
    var scope = null;
    var element = null;
    var httpBackend;

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

    beforeEach(inject(function (_$compile_, _$rootScope_, _$httpBackend_, $templateCache) {
        $compile = _$compile_;
        httpBackend = _$httpBackend_;
        scope = _$rootScope_.$new();

        var templateHtml =
            '<div class="row display-flex"> ' +
            '<div class="menu display-flex flex2"> ' +
            '<ul class="nav nav-pills"> ' +
            '<li role="presentation" ' +
            'ng-repeat="obj in names" ' +
            'data-ng-class="selectedCategory == obj.name ? \'active\': \'\'" ' +
            'data-ng-click="selectCategory(obj.name)"> ' +
            '<a href=""> ' +
            '{{obj.name}} ({{ widgets[obj.name].length }}) ' +
            '< /a> </li > < /ul> </div > ' +
            '<div class="content flex4"> ' +
            '<ul class="widget"> ' +
            '<li data-ng-repeat="obj in widgets[selectedCategory]" ' +
            'data-ng-class="selectedWidget.id == obj.id ? \'active\': \'\'"  ' +
            'data-ng-click="selectWidget(obj)"> ' +
            '<div class="display-flex display-flex-row"> ' +
            '<div class="logo flex1"> ' +
            '<img data-ng-src="images/{{obj.shortName}}.png" ' +
            'width="40"></div> <div class="content flex4"> <h2>{{obj.title}} </h2> <p>' +
            '{{obj.description}} </p> </div></li> </ul> </div> </div>';

        httpBackend.when("GET").respond(templateHtml);

        scope.availableWidgets = {
            "Administration": [
                {
                    "id": 8,
                    "name": "create-or-edit-user-form-widget",
                    "shortName": "CreateUser",
                    "title": "Create Or Edit User",
                    "description": "ABC",
                    "type": "FORM"
                },
                {
                    "id": 12,
                    "name": "group-maintenance-widget",
                    "shortName": "GroupMaintenance",
                    "title": "Group Maintenance",
                    "description": "DEF",
                    "type": "FORM"
                },
                {
                    "id": 9,
                    "name": "search-user-grid-widget",
                    "shortName": "SearchUser",
                    "title": "Search User",
                    "description": "JKL",
                    "type": "GRID"
                }
            ],
            "Work Execution": [
                {
                    "id": 14,
                    "name": "search-product-grid-widget",
                    "shortName": "SearchProduct",
                    "title": "Search Product",
                    "description": "GHI",
                    "type": "GRID"
                }
            ],
            "Reporting": [
                {
                    "id": 10,
                    "name": "pickline-by-wave-barchart-widget",
                    "shortName": "PicklineByWave",
                    "title": "Pickline By Wave Bar chart",
                    "description": "MNO",
                    "type": "GRAPH_2D"
                }
            ]
        };
        element = angular.element('<widgets-pallete widgets="availableWidgets"></widgets-pallete>');
        $compile(element)(scope);
        httpBackend.flush();
        scope.$apply();
    }));

    it('should inject test dependencies', function () {
        expect(scope).toBeDefined();
        expect($compile).toBeDefined();
        expect(element).toBeDefined();
    });

    it('Should test widgetsPallete directive', function () {
        // get the isolated scope
        var isolatedScope = element.isolateScope();

        spyOn(isolatedScope, 'onSelect').andCallThrough();

        // test the directive presence
        expect(element.length).toBeGreaterThan(0);
        // test the menu items (categories)
        expect(element.find('.menu li a').length).toBe(3);

        // first selection is Administration
        expect(element.find('.content li').length).toBe(3);

        // test "Work Execution"
        isolatedScope.selectCategory('Work Execution');
        isolatedScope.$digest();
        expect(element.find('.content li').length).toBe(1);

        // test "Reporting"
        isolatedScope.selectCategory('Reporting');
        isolatedScope.$digest();
        expect(element.find('.content li').length).toBe(1);

        // test selectWidget method
        isolatedScope.selectWidget({
            "id": 10,
            "name": "pickline-by-wave-barchart-widget",
            "shortName": "PicklineByWave",
            "title": "Pickline By Wave Bar chart",
            "description": "MNO",
            "type": "GRAPH_2D"
        });
        expect(isolatedScope.onSelect).toHaveBeenCalledWith({
            selectedWidget: {
                "id": 10,
                "name": "pickline-by-wave-barchart-widget",
                "shortName": "PicklineByWave",
                "title": "Pickline By Wave Bar chart",
                "description": "MNO",
                "type": "GRAPH_2D"
            }
        });
    });
});