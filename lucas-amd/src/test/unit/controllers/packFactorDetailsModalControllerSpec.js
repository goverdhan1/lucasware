'use strict';

describe('Pack Factor Details popup Controller Unit Tests', function() {

    // Global vars
    var def = null;
    var localPackFactorDetailsModalController = null;
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
            localPackFactorDetailsModalController = $controller('packFactorDetailsModalController', {
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
            expect(localScope.closeModal).toBeDefined();
            expect(localScope.addLevel).toBeDefined();
            expect(localScope.details).toBeDefined();
            expect(localScope.moveUp).toBeDefined();
            expect(localScope.moveDown).toBeDefined();
            expect(localScope.deleteRow).toBeDefined();
            expect(localScope.selectedComponentChanged).toBeDefined();
        });
    });
});