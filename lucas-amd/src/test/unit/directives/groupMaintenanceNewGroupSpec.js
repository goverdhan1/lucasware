/**
 * Created by Shaik Basha on 11/15/2014.
 */

describe("groupMaintenanceNewGroup Directive Unit Tests", function () {
    var compile;
    var scope;
    var element;

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

    beforeEach(inject(function ($compile, $rootScope, $httpBackend) {
        compile = $compile;
        scope = $rootScope;

        $httpBackend.when("GET").respond({});

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

        var html = "<group-maintenance-new-group></group-maintenance-new-group>"

        //create the directive
        element = $compile(html)(scope);
        scope.$digest();
    }));

    it('should inject test dependencies', function () {
        expect(scope).toBeDefined();
        expect(compile).toBeDefined();
        expect(element).toBeDefined();
    });

    it('should check input rendered correctly', function () {
        expect(element.find("input").length).toBe(1);
    });

    it('should call onSelect method', function () {
        spyOn(scope, "onSelect").andCallThrough()
        spyOn(scope, "$broadcast").andCallThrough();
        scope.newGroupName = "picker";
        scope.onBlur();
        expect(scope.onSelect).toHaveBeenCalledWith(scope.newGroupName);
        expect(scope.$broadcast).toHaveBeenCalled();
    });

});
