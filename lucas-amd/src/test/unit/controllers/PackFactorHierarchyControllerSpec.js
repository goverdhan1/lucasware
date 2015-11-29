'use strict';

describe('Pack Factor Hierarchy Controller Unit Tests', function() {

    // Global vars
    var def = null;
    var localPackFactorHierarchyController = null;
    var localScope = null;
    var localModalInstance = null;

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

    beforeEach(inject(function($rootScope, $modal, $q, $templateCache) {
        localScope = $rootScope.$new();
        localModalInstance = $modal;
    }));

    // Inject Specs
    describe('About Lucas Controller Injection Specs', function() {

        beforeEach(inject(function ($controller) {
            localPackFactorHierarchyController = $controller('PackFactorHierarchyController', {
                $scope: localScope,
                $modalInstance: localModalInstance
            });
        }));

        it('should inject ModalInstance', function() {
            expect(localModalInstance).toBeDefined();
        });
        it('should inject $scope', function() {
            expect(localScope).toBeDefined();
        });
        it('should be defined in scope', function() {
            localScope.$digest();
            expect(localScope.selectHierarchy).toBeDefined();
            expect(localScope.launchComponents).toBeDefined();
            expect(localScope.launchDetails).toBeDefined();
            expect(localScope.selectedRowIndex).toBeDefined();
        });
    });
});