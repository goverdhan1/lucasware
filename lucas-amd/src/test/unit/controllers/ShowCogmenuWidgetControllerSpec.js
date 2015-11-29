'use strict';

describe('ShowCogmenuWidget Controller Unit Tests', function () {

    // Global vars
    var controller = null;
    var $scope = null;
    var $modalInstance = null;
    var CogService = null;
    var WidgetService = null;
    var LocalStoreService = null;
    var mockWidgetInstance = null;

    // Global test setup
    beforeEach(module('amdApp'));

    beforeEach(inject(function (_$rootScope_, _$modal_, _$controller_, _CogService_, _LocalStoreService_, _WidgetService_) {
        //setup mock widgetInstance object
        mockWidgetInstance = {
            "updateWidget": true,
        	"clientId": 101,
        	"isMaximized": false,
        	"widgetDefinition": {
        		"defaultViewConfig": {}
        	},
        	"actualViewConfig":{}
        };

        //setup dependencies
        $scope = _$rootScope_.$new();
        $modalInstance = _$modal_.open({
            templateUrl : 'views/cog/pickline-by-wave-barchart-widget.html'
        });
        CogService = _CogService_;
        WidgetService = _WidgetService_;
        LocalStoreService = _LocalStoreService_;

        //setup spies
        spyOn($modalInstance, 'close').andReturn();
        spyOn(CogService, 'refreshWidgetWithUpdatedValues').andReturn();
        spyOn(WidgetService, 'getWidgetInstanceObj').andReturn(mockWidgetInstance);
        spyOn(LocalStoreService, 'getLSItem').andReturn(101);
        spyOn(LocalStoreService, 'removeLSItem').andReturn();

        //instantiate controller with dependencies
        controller = _$controller_('ShowCogmenuWidgetController', {
            $scope: $scope,
            $modalInstance: $modalInstance,
            CogService: CogService,
            LocalStoreService: LocalStoreService
        });
    }));


    //Controller instantiation test
    it('should grab widgetInstance from local storage when instantiating and assign to scope', function() {
        expect(LocalStoreService.getLSItem).toHaveBeenCalledWith('ActiveWidgetId');
        expect(controller.cogWidgetInstance).toEqual(mockWidgetInstance);
        expect($scope.cogWidgetInstance).toEqual(mockWidgetInstance);
        expect($scope.isReset).toBe(false);
    });    

    it('should close the model', function () {
        //invoke the function
        $scope.cancel();
        //assert the results
        expect($modalInstance.close).toHaveBeenCalled();
    });

    it('should save the cog menu settings and close the modal', function () {
        //invoke the function
        $scope.saveCogSettings(mockWidgetInstance);
        //assert the results
        expect(CogService.refreshWidgetWithUpdatedValues).toHaveBeenCalled();
        expect($modalInstance.close).toHaveBeenCalled();
    });
});