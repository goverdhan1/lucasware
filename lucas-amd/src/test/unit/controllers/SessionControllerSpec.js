'use strict';
describe('Session Controller Unit Tests', function () {

    // Global vars
    var localScope = null;
    var localSessionController = null;
    var localSessionService = null;
    var deferred = null;
    var $httpBackend = null;

    // Global test setup
    beforeEach(module('amdApp', function ($translateProvider) {
        $translateProvider.translations('en', {
            "language-code": "EN"
        }, 'fr', {
            "language-code": "fr"
        }, 'de', {
            "language-code": "de"
        }).preferredLanguage('en');
    }));

    beforeEach(inject(function ($injector, $controller, $rootScope, SessionService) {
        // Set up the mock http service responses
        $httpBackend = $injector.get('$httpBackend');

        // Handle all the POST requests
        $httpBackend.when('POST').respond({});

        // Handle all the GET requests
        $httpBackend.when('GET').respond({});

        localScope = $rootScope.$new();
        localSessionService = SessionService;

        localSessionController = $controller("SessionController", {
            $scope: localScope,
            SessionService: localSessionService
        });
    }));

    it("Should handle logoutSession", function () {
        spyOn(localScope, "logoutSession").andCallThrough();
        localScope.logoutSession();
        expect(localScope.logoutSession).toHaveBeenCalled();
    });

    it("Should handle SessionService.getUsername()", function () {
        spyOn(localSessionService, 'getUsername').andReturn("testuser");
        localSessionService.create({});
        localScope.$digest();
        expect(localSessionService.getUsername).toHaveBeenCalled();
    });

    it("Should handle SessionService.isSessionActive()", function () {
        spyOn(localSessionService, 'isSessionActive').andReturn("true");
        localSessionService.isSessionActive();
        localScope.$digest();
        expect(localSessionService.isSessionActive).toHaveBeenCalled();
    });

});