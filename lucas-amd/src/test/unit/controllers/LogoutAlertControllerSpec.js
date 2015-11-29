"use strict";

describe("LogoutAlert Controller Unit Tests", function () {
    // Global variables
    var localLogoutAlertController = null;
    var localScope = null;
    var localTimeout = null;
    var localLogoutAlertService = null;

    beforeEach(module('amdApp'));

    beforeEach(inject(function ($controller, $rootScope, LogoutAlertService, $timeout) {
        localScope = $rootScope.$new();
        localLogoutAlertService = LogoutAlertService;
        localTimeout = $timeout;

        localLogoutAlertController = $controller('LogoutAlertController', {
            $rootScope: localScope,
            $scope: localScope,
            LogoutAlertService: localLogoutAlertService,
            $timeout: localTimeout
        });
    }));


    it("Should handle closeAlert method", function () {
        spyOn(localScope, 'closeAlert').andCallThrough();
        localScope.closeAlert();
        expect(localScope.closeAlert).toHaveBeenCalled();
    });

    it("Should handle $destroy method", function () {
        spyOn(localScope, '$broadcast').andCallThrough();
        localScope.$broadcast('$destroy');
        expect(localScope.$broadcast).toHaveBeenCalledWith('$destroy');
    });

    it("Should handle $stateChangeSucces event", function () {
        spyOn(localScope, '$broadcast').andCallThrough();
        localScope.$broadcast('$stateChangeSuccess',  {}, {}, {name: "indexpage.home"}, {});
        expect(localScope.$broadcast).toHaveBeenCalledWith('$stateChangeSuccess',  {}, {}, {name: "indexpage.home"}, {});
    });

});