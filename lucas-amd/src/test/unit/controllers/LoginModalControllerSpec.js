'use strict';
describe('LoginModal Controller Unit Tests', function () {

    // Global vars
    var localScope = null;
    var localLoginModalController = null;
    var deferred = null;

    // Global test setup
    beforeEach(module('amdApp', function ($translateProvider) {
        $translateProvider.translations('en', {
            "language-code": "EN",
        }, 'fr', {
            "language-code": "fr",
        }, 'de', {
            "language-code": "de"
        }).preferredLanguage('en');

        $translateProvider.useLoader('LocaleLoader');
    }));

    beforeEach(inject(function ($rootScope, $controller) {
        localScope = $rootScope.$new();
        spyOn(localScope, "$on").andCallThrough();
        localLoginModalController = $controller('LoginModalController', {
            $scope: localScope,
            invalidResponse: true,
            $modalInstance: {}
        });
        spyOn(localScope, "$broadcast").andCallThrough();
        spyOn(localScope, "loginModalSubmit").andCallThrough();
    }));

    // Handle loginModalSubmit method
    describe('Handle loginModalSubmit method', function () {
        beforeEach(inject(function ($controller, $rootScope) {
            localScope = $rootScope.$new();
            localLoginModalController = $controller('LoginModalController', {
                $scope: localScope,
                invalidResponse: true,
                $modalInstance: {}
            });
            spyOn(localScope, "$broadcast").andCallThrough();
        }));

        it('should handle loginModalSubmit method', function () {
            spyOn(localScope, 'loginModalSubmit').andCallThrough();
            localScope.loginModalSubmit();
            expect(localScope.loginModalSubmit).toHaveBeenCalled();
        });
    });

    // Handling destroy method
    describe('Should call the destroy method', function () {
        it('Should call the destroy method', function () {
            localScope.$broadcast("$destroy");
            expect(localScope.$broadcast).toHaveBeenCalledWith("$destroy");
            expect(localScope.$on).toHaveBeenCalled();
        });
    });

    // Handle 1001:loginRequired
    describe('Should handle failure events', function () {
        it("Should handle 1001:loginRequired", function () {
            var data = {
                code: "1001",
                data: null,
                message: "Invalid Username or Password",
                reason: null,
                status: "failure",
                token: null,
                uniqueKey: null
            };

            localScope.$broadcast("1001:loginRequired", data);
            expect(localScope.$broadcast).toHaveBeenCalledWith("1001:loginRequired", data);
            expect(localScope.$on).toHaveBeenCalled();
            expect(localScope.form.username).toBe('');
            expect(localScope.form.password).toBe('');
        });
    });

    // Handle closeWelcomeModal
    describe('Should handle close methods of scope and modal', function () {
        var mockModalInstance = {
            close: function () {
                return "Modal closed successfully";
            }
        };

        beforeEach(inject(function ($controller, $rootScope) {
            localScope = $rootScope.$new();
            localLoginModalController = $controller('LoginModalController', {
                $scope: localScope,
                invalidResponse: true,
                $modalInstance: mockModalInstance
            });
            spyOn(localScope, "closeWelcomeModal").andCallThrough();
            spyOn(mockModalInstance, "close").andCallThrough();
        }));

        it("Should handle closeWelcomeModal method", function () {
            localScope.closeWelcomeModal();
            expect(localScope.closeWelcomeModal).toHaveBeenCalled();
        });

        it("Should handle close method of modalInstance", function () {
            localScope.closeWelcomeModal();
            expect(mockModalInstance.close).toHaveBeenCalled();
        });
    });

});