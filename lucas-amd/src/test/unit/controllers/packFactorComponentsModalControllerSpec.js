'use strict';

describe('Pack Factor Components popup Controller Unit Tests', function() {

    // Global vars
    var def = null;
    var localPackFactorComponentsModalController = null;
    var localScope = null;
    var localModalInstance = null;
    var localPackFactorService = null;

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
        localPackFactorService = {
            getAboutDetails : function() {
                def = $q.defer();
                // Resolve the promise
                def.resolve({'buildNumber':'12345', 'buildTimestamp':'20140820115933'});
                return def.promise;
            },
                getComponents: function () {
                    def = $q.defer();
                    // Resolve the promise
                    def.resolve([
                        {
                            name: "Pallette",
                            abbreviation: "PE",
                            id: 1
                        },
                        {
                            name: "Tier",
                            abbreviation: "TR",
                            id: 2
                        }
                    ]);
                    return def.promise;
                },
                deleteComponent: function(payload){
                    def = $q.defer();
                    // Resolve the promise
                    def.resolve(true);
                    return def.promise;
                },
                saveComponents:function(payload){
                    def = $q.defer();
                    // Resolve the promise
                    def.resolve(true);
                    return def.promise;
                }
        };
    }));

    // Inject Specs
    describe('About Lucas Controller Injection Specs', function() {

        beforeEach(inject(function ($controller) {
            localPackFactorComponentsModalController = $controller('packFactorComponentsModalController', {
                $scope: localScope,
                $modalInstance: localModalInstance,
                mockPackFactorService : localPackFactorService
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
            expect(localScope.save).toBeDefined();
            expect(localScope.addComponent).toBeDefined();
            expect(localScope.delete).toBeDefined();
            expect(localScope.disable).toBeDefined();
            expect(localScope.enter).toBeDefined();
            expect(localScope.components).toBeDefined();

        });
    });
});