'use strict';

describe('BottomRightController Unit Tests', function() {

    // Global vars
    var localScope = null;
    var mockLocalStoreService = null;
    var localStoreService = null;
    var localController = null;
    var mockHomeCanvasListService = null;
    var localHomeCanvasListService = null;
    var localModalInstance = null;


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


    beforeEach(inject(function($controller, $rootScope, $q, $modal) {

        localScope = $rootScope.$new();

        mockLocalStoreService = {
            getLSItem : function () {
                return ['Morning Shift Canvas', 'Pick Monitoring', 'Assignment Management'];
            }
        };

        mockHomeCanvasListService = {
            findByCanvasId: function(){
                return 'Morning Shift Canvas';
            }
        };

        localStoreService = mockLocalStoreService;
        localHomeCanvasListService = mockHomeCanvasListService;
        localModalInstance = $modal;

        localController = $controller('BottomRightController', {
            $scope : localScope,
            LocalStoreService : localStoreService,
            HomeCanvasListService : localHomeCanvasListService
        });

    }));


    beforeEach(function () {
        spyOn(localScope, '$broadcast').andCallThrough();
        localScope.$broadcast('$stateChangeSuccess', '', {canvasId: 2});
    });

    // Dependency Injection Specs
    describe('dependency injection tests ', function() {

        it('should inject BottomRightController', function() {
            expect(localController).toBeDefined();
        });

        it('should inject localScope', function() {
            expect(localScope).toBeDefined();
        });

        it('should inject LocalStoreService', function() {
            expect(localStoreService).toBeDefined();
        });

        it('should inject ModalInstance', function() {
            expect(localModalInstance).toBeDefined();
        });

        it("Should handle $stateChangeSuccess event", function () {
            expect(localScope.$broadcast).toHaveBeenCalled();
        });

    });

    //Unit Specs
    describe('local store display favourite canvas spec ', function() {

        it('should show favourite canvas', function() {
            var a = localStoreService.getLSItem();

            expect(a[0]).toEqual('Morning Shift Canvas');
        });

        it('promise should return correct canvas when resolved', function() {
            localScope.$digest();
            expect(localScope.activeCanvas).toEqual('Morning Shift Canvas');
        });

    });

    describe('Canvas Modal Specs', function() {
        it("should open the modal", function() {
            spyOn(localModalInstance, 'open').andCallThrough();
            localScope.openSelectCanvas();
            expect(localModalInstance.open).toHaveBeenCalled();
        });
    });


});