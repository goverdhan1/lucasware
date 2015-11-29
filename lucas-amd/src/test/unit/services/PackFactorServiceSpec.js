'use strict';

describe('pack factor Service Specs', function () {

    var service = null;

    beforeEach(module('amdApp', function ($translateProvider) {
        $translateProvider.translations('en', {
            "language-code": "EN"
        }, 'fr', {
            "language-code": "fr"
        }, 'de', {
            "language-code": "de"
        }).preferredLanguage('en');
        $translateProvider.useLoader('LocaleLoader');
    }));

    //dependency injection tests
    describe('Dependency Injection Tests', function () {

        beforeEach(inject(function (PackFactorService) {
            service = PackFactorService;
        }));

        it('SearchProductGridService to be defined', function () {
            expect(service).toBeDefined();
        });
    });
});