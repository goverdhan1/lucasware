'use strict';

describe('Chart Controller Tests', function() {

    // Global vars
    var localChartController = null;
    var localScope = null;

    beforeEach(module('amdApp'));

    // Global test setup
    beforeEach(module('amdApp', function($translateProvider) {
        $translateProvider.translations('en', {
            "language-code" : "EN"
        }, 'fr', {
            "language-code" : "fr"
        }, 'de', {
            "language-code" : "de"
        }).preferredLanguage('en');
        $translateProvider.useLoader('LocaleLoader');
    }));

    // Inject dependencies
    beforeEach(inject(function($rootScope, $controller) {
        localScope = $rootScope.$new();

        localScope.chart = {
            type : 'pie chart'
        };

        localChartController = $controller('ChartController', {
            $scope: localScope
        });
    }));


    // Unit Specs
    describe('dependency injection test', function() {

        it('should inject ChartController', function() {
            expect(localChartController).toBeDefined();
        });

        it('should inject localScope', function() {
            expect(localScope).toBeDefined();
        });
    });

    describe('Chart data test', function() {

        it('should have chart data to be defined', function() {
            expect(localScope.chart).toBeDefined();
        });

        it('should have chart data type value', function() {
            expect(localScope.chart.type).toBeDefined();
        });

    });

});