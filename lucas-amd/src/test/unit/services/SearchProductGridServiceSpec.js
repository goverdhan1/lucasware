'use strict';

describe('Search Product Grid Service Specs', function () {

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

        beforeEach(inject(function (SearchProductGridService) {
            service = SearchProductGridService;
        }));

        it('SearchProductGridService to be defined', function () {
            expect(service).toBeDefined();
        });
    });
});