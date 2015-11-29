'use strict';

describe('AboutLucasController Unit Tests', function() {

    // Global vars
    var def = null;
    var localAboutController = null;
    var localScope = null;
    var localModalInstance = null;
    var localAboutService = null;
    var mockAboutService = null;
    var mockAboutServiceException = null;

    // Global test setup
    beforeEach(module('amdApp', function($translateProvider) {
        $translateProvider.translations('en', {
            "language-code" : "EN",
        }, 'fr', {
            "language-code" : "fr",
        }, 'de', {
            "language-code" : "de",
        }).preferredLanguage('en');
        $translateProvider.useLoader('LocaleLoader');
    }));

    beforeEach(inject(function($rootScope, $modal, $q, $templateCache) {
        // define and initialise mock dependencies
        mockAboutService = {
            getAboutDetails : function() {
                def = $q.defer();
                // Resolve the promise
                def.resolve({'buildNumber':'12345', 'buildTimestamp':'20140820115933'});
                return def.promise;
            }
        };
        mockAboutServiceException = {
            getAboutDetails : function() {
                def = $q.defer();
                // Reject the promise
                def.reject('failed to get about details');
                return def.promise;
            }
        };
        localScope = $rootScope.$new();
        localModalInstance = $modal;
    }));

    // Inject Specs
    describe('About Lucas Controller Injection Specs', function() {

        beforeEach(inject(function($controller) {
            localAboutService = mockAboutService;
            localAboutController = $controller('AboutLucasController', {
                $scope : localScope,
                $modalInstance : localModalInstance,
                AboutService : localAboutService
            });
        }));

        it('should inject AboutLucasController', function() {
            expect(localAboutController).toBeDefined();
        });

        it('should inject AboutService', function() {
            expect(localAboutService).toBeDefined();
        });

        it('should inject ModalInstance', function() {
            expect(localModalInstance).toBeDefined();
        });

        it('should inject $scope', function() {
            expect(localScope).toBeDefined();
        });
    });

    // Correct About Details Specs
    describe('Correct Build Details', function() {

        beforeEach(inject(function($controller) {
            localAboutService = mockAboutService;
            localAboutController = $controller('AboutLucasController', {
                $scope : localScope,
                $modalInstance : localModalInstance,
                AboutService : localAboutService
            });
        }));

        it('promise should return correct about details when resolved', function() {
            var result = null;
            // Here we check the result of the returned promise
            localAboutService.getAboutDetails().then(function(response) {
                // This function is used if the promise is 'resolved'
                result = response;
            });

            // We need this here since promises are only
            // resolved/dispatched only on the next $digest cycle
            localScope.$digest();
            expect(result).toEqual({'buildNumber':'12345', 'buildTimestamp':'20140820115933'});
        });

        it('scope should have correct build number', function() {
            localScope.$digest();
            expect(localScope.buildNumber).toBeDefined();
            expect(localScope.buildNumber).toBe('12345');
        });

        it('scope should have correct build timestamp', function() {
            localScope.$digest();
            expect(localScope.buildTimestamp).toBeDefined();
            expect(localScope.buildTimestamp).toBe('20140820115933');
        });
    });

    // Exception About Details Specs
    describe('Error getting about details', function() {

        beforeEach(inject(function($controller) {
            localAboutService = mockAboutServiceException;
            localAboutController = $controller('AboutLucasController', {
                $scope : localScope,
                $modalInstance : localModalInstance,
                AboutService : localAboutService
            });
        }));

        it('should handle error getting About Details', function() {
            var result = null;
            // Here we check the result of the returned promise
            localAboutService.getAboutDetails().then(function(response) {
                // This function is used if the promise is 'resolved'
            }, function(error) {
                // This function is used if the promise is 'rejected'
                result = error;
            });

            // We need this here since promises are only
            // resolved/dispatched only on the next $digest cycle
            localScope.$digest();
            expect(result).toEqual('failed to get about details');
        });
    });

    // Modal Specs
    describe('About Modal Specs', function() {

        beforeEach(inject(function($controller, $templateCache, $modal) {
            // Load the modal html into the templateCache
            // so we can mock opening it. This is necessary
            // as we need to test closing the modal
            $templateCache.put('views/about-modal.html', '');
            localModalInstance = $modal.open({
                templateUrl : 'views/about-modal.html',
                backdrop: 'static'
            });

            localAboutService = mockAboutService;
            localAboutController = $controller('AboutLucasController', {
                $scope : localScope,
                $modalInstance : localModalInstance,
                AboutService : localAboutService
            });
            spyOn(localModalInstance, 'close').andCallThrough();
        }));

        it('should be defined in scope', function() {
            localScope.$digest();
            expect(localScope.closeModal).toBeDefined();
        });

        it('should close the modal', function() {
            localScope.$digest();
            localScope.closeModal();
            expect(localModalInstance.close).toHaveBeenCalled();
        });
    });
});