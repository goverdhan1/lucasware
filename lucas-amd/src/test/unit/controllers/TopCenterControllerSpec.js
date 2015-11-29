"use strict";

describe("TopCenter Controller Unit Tests", function () {

    // Global vars
    var localScope = null;
    var localUserService;
    var localTopCenterController = null;
    var mockHomeCanvasListService = null;
    var deferred = null;

    beforeEach(module('amdApp'));

    beforeEach(inject(function ($controller, $rootScope, $q, UserService, $httpBackend) {
        localScope = $rootScope.$new();
        mockHomeCanvasListService = {
            getCanvasData: function () {
                deferred = $q.defer();
                deferred.resolve({});
                return deferred.promise;
            }
        };
        localUserService = UserService;

        $httpBackend.when("GET").respond({});
        spyOn(localUserService, 'getCustomerLogo').andCallThrough();
        localTopCenterController = $controller('TopCenterController', {
            $scope: localScope,
            HomeCanvasListService: mockHomeCanvasListService,
            UserService: localUserService
        })
    }));

    it("Should get CanvasData", function () {
        spyOn(mockHomeCanvasListService, 'getCanvasData').andCallThrough();
        localScope.$broadcast('$stateChangeSuccess', '', {canvasId: 2});
        expect(mockHomeCanvasListService.getCanvasData).toHaveBeenCalled();
    });

    it("Should handle $stateChangeSuccess event", function () {
        spyOn(localScope, '$broadcast').andCallThrough();
        localScope.$broadcast('$stateChangeSuccess', '', {canvasId: 2});
        expect(localScope.$broadcast).toHaveBeenCalled();
    });

    it('Should handle getCustomerLogo() method', function() {
        localScope.$apply();
        expect(localUserService.getCustomerLogo).toHaveBeenCalled();
    });

});