describe('MoveData Directive Unit tests', function () {
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

    beforeEach(inject(function (_$compile_, _$rootScope_, _$httpBackend_) {
        $compile = _$compile_;
        httpBackend = _$httpBackend_;
        scope = _$rootScope_.$new();

        scope.widgetdetails = {
            availableItems: [
                "create-user",
                "disable-user",
                "create-canvas",
                "delete-widget",
                "edit-user",
                "delete-user",
                "change-zone"
            ],
            existingItems: [
                "create-user",
                "edit-user",
                "delete-user"
            ]
        };

        var templateHtml =
            '<div class="col-xs-5">' +
            '<select multiple data-ng-multiple="true" class="availableItems" data-ng-model="widgetdetails.selectedAvailableItems" ' +
            'data-ng-options="availableItem for availableItem in widgetdetails.availableItems ' +
            ' | filter: removeExistingDataFilter | orderBy: \'toString()\'"></select>' +
            '</div>' +
            '<div class="col-xs-2 buttons">' +
            '<div><button data-ng-click="moveOneToExistingData()"> > </button></div>' +
            '<div><button data-ng-click="moveOneToAvailableData()"> < </button></div>' +
            '<div><button data-ng-click="moveAllToExistingData()"> >> </button></div>' +
            '<div><button data-ng-click="moveAllToAvailableData()"> << </button></div>' +
            '</div>' +
            '<div class="col-xs-5">' +
            '<select multiple data-ng-multiple="true" class="existingItems" data-ng-model="widgetdetails.selectedExistingItems" ' +
            'data-ng-options="existingItem for existingItem in widgetdetails.existingItems ' +
            ' | orderBy: \'toString()\'"></select>' +
            '</div>';

        httpBackend.when("GET").respond(templateHtml);

        //create the directive
        element = $compile("<move-data></move-data>")(scope);
        scope.$digest();
        httpBackend.flush();
    }));


    it('should inject test dependencies', function () {
        expect(scope).toBeDefined();
        expect($compile).toBeDefined();
        expect(element).toBeDefined();
    });


    it('Should create moveData directive', function () {
        expect(element.length).toBeGreaterThan(0);
    });

    it('Should check the elements method', function () {
        // Total option elements should be 7
        expect(element.find('option').length).toBe(7);
        // availableItems elements should be 4
        expect(element.find('.availableItems option').length).toBe(4);
        // existingItems elements should be 3
        expect(element.find('.existingItems option').length).toBe(3);
    });

    it("Should handle moveOneToExistingData method", function () {
        scope.widgetdetails.existingItems = [];
        scope.widgetdetails.selectedAvailableItems = ["delete-widget"];
        scope.moveOneToExistingData();
        expect(scope.widgetdetails.existingItems).toEqual(["delete-widget"]);
        expect(scope.widgetdetails.selectedAvailableItems).toEqual([]);
    });

    it("Should handle moveOneToAvailableData method", function () {
        scope.widgetdetails.selectedExistingItems = ["change-zone"];
        scope.widgetdetails.existingItems = [
            "create-user",
            "disable-user",
            "create-canvas",
            "delete-widget",
            "edit-user",
            "delete-user",
            "change-zone"
        ];
        scope.moveOneToAvailableData();
        expect(scope.widgetdetails.existingItems).toEqual([
            "create-user",
            "disable-user",
            "create-canvas",
            "delete-widget",
            "edit-user",
            "delete-user"
        ]);
        expect(scope.widgetdetails.selectedExistingItems).toEqual([]);
    });

    it("Should handle moveAllToExistingData method", function () {
        scope.widgetdetails.existingItems = [];
        scope.moveAllToExistingData();
        expect(scope.widgetdetails.existingItems).toEqual(scope.widgetdetails.availableItems);
        expect(scope.widgetdetails.selectedAvailableItems).toEqual([]);
    });

    it("Should handle moveAllToAvailableData method", function () {
        scope.moveAllToAvailableData();
        expect(scope.widgetdetails.existingItems.length).toEqual(0);
        expect(scope.widgetdetails.selectedExistingItems).toEqual([]);
    });

    it("Should handle removeExistingDataFilter method", function () {
        scope.widgetdetails.existingItems = [
            "create-user",
            "disable-user",
            "create-canvas",
            "delete-widget",
            "edit-user",
            "delete-user",
            "change-zone"
        ];
        expect(scope.removeExistingDataFilter("edit-user")).toEqual(false);
        expect(scope.removeExistingDataFilter("new-user")).toEqual(true);
    });

});