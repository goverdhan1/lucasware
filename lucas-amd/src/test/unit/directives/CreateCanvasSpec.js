describe('createCanvas Directive Unit tests', function () {
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

        scope.canvasLayoutId = 1;

        var templateHtml =
            '<div class="canvaslayout canvaslayout1 display-flex flex1 flex-aligncenter">' +
            '<a href="" class="btn-load-widget">Load Widget</a></div>';

        httpBackend.when("GET").respond(templateHtml);

        //create the directive
        element = $compile('<create-canvas></create-canvas>')(scope);
        scope.$digest();
        httpBackend.flush();
    }));

    it('should inject test dependencies', function () {
        expect(scope).toBeDefined();
        expect($compile).toBeDefined();
        expect(element).toBeDefined();
    });

    it('Should test createCanvas directive', function () {
        expect(element.length).toBeGreaterThan(0);
        expect(element.find('.btn-load-widget').length).toBe(1);
    });
});