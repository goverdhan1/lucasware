'use strict';
describe('Locale Controller Unit Tests', function() {

    // Global vars
    var localScope = null;
    var localTranslate = null;
    var localLocaleController = null;

    // Global test setup
    beforeEach(module('amdApp', function($translateProvider) {
        $translateProvider.translations('en', {
            "language-code": "EN",
        }, 'fr', {
            "language-code": "fr",
        }, 'de', {
            "language-code": "de"
        }).preferredLanguage('en');

        $translateProvider.useLoader('LocaleLoader');
    }));

    beforeEach(inject(function($rootScope, $controller, $translate, $q) {
        localScope = $rootScope.$new();
        localTranslate = $translate;
        localLocaleController = $controller('LocaleController', {
            $scope: localScope,
            $translate: localTranslate
        });
    }));

    describe('LocaleController Specs', function() {
        it('Should be able to change the language', function() {
            localScope.changeLanguage("de");
            expect(localTranslate.use()).toBe("en");
        });
    });
});