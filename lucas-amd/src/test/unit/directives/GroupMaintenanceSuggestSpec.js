/**
 * Created by Shaik Basha on 11/13/2014.
 */


describe('GroupMaintenanceSuggest directive Unit tests', function () {
    var $compile;
    var scope;
    var element;
    var $httpBackend;

    beforeEach(inject(function (_$compile_, $rootScope, _$httpBackend_) {
        $compile = _$compile_;
        scope = $rootScope;

        // Set up the mock http service responses
        $httpBackend = _$httpBackend_;
        // Handle all the GET requests

        var html = '<div group-maintenance-suggest class="autocomplete"><input ng-model="newGroupName" data-ng-blur="onBlur()" data-ng-focus="onFocus()"> ' +
                '<ul class="list-group" data-ng-hide="hideDropDown || (widgetdetails.groupNames | filter: newGroupName).length == 0" ' +
        ' data-ng-mousedown="pauseSelection = true"><li class="list-group-item" data-ng-mousedown="onSelect(group)"' +
        ' data-ng-repeat="group in widgetdetails.groupNames | filter: newGroupName track by group.name">' +
        '{{group.name}} </li> </ul></div>';

        $httpBackend.when('GET').respond(html);


        scope.widgetdetails = {
            groupNames: [
                {
                    name: "admin"
                },
                {
                    name: "picker"
                },
                {
                    name: "supervisor"
                }
            ]
        };

        element = $compile(html)(scope);
        scope.$digest();
    }));


    it('Should create moveData directive', function () {
        expect(element.length).toBeGreaterThan(0);
    });

    it('Should check the elements method', function () {
        // Should two select elements to be present
        expect(element.find('input').length).toBe(1);
        // four buttons should be present
        expect(element.find('li').length).toBe(3);
    });

});
