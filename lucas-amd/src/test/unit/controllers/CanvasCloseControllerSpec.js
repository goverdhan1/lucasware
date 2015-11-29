'use strict';

describe('CanvasCloseController Unit Tests', function () {

    // Global vars
    var localScope;
    var localStoreService;
    var localCanvasService;
    var localController;
    var localModalInstance;
    var localState;
    var localUtilsService;
    var mockPickedCanvas = {
        "name": "Work Execution",
        "canvasId": "221",
        "canvasType": "COMPANY",
        "displayOrder": "3"
    };
    var mockCanvasBar = [
        {
            "name": "ProductManagement",
            "canvasId": "201",
            "canvasType": "COMPANY",
            "displayOrder": "1"
        }, {
            "name": "Work Execution",
            "canvasId": "221",
            "canvasType": "COMPANY",
            "displayOrder": "2"
        }, {
            "name": "AssignmentManagement",
            "canvasId": "231",
            "canvasType": "COMPANY",
            "displayOrder": "3"
        }, {
            "name": "GroupManagement",
            "canvasId": "235",
            "canvasType": "COMPANY",
            "displayOrder": "4"
        }
    ];
    var mockUserConfigData = {
        "userName": "jack123",
        "userId": 100,
        "permissionSet": ["manage-canvas", "clone-canvas", "delete-user", "disable-user"],
        "homeCanvas": {
            "name": "Work Execution",
            "canvasId": "221",
            "canvasType": "COMPANY",
            "displayOrder": "2"
        },
        "activeCanvas": {
            "name": "Work Execution",
            "canvasId": "221",
            "canvasType": "COMPANY",
            "displayOrder": "2"
        },
        "seeHomeCanvasIndicator": true,
        "openCanvases": [{
            "name": "ProductManagement",
            "canvasId": "201",
            "canvasType": "COMPANY",
            "displayOrder": "1"
        }, {
            "name": "Work Execution",
            "canvasId": "221",
            "canvasType": "COMPANY",
            "displayOrder": "2"
        }, {
            "name": "AssignmentManagement",
            "canvasId": "231",
            "canvasType": "COMPANY",
            "displayOrder": "3"
        }, {
            "name": "GroupManagement",
            "canvasId": "235",
            "canvasType": "COMPANY",
            "displayOrder": "4"
        }],
        "menuPermissions": {"manage-canvas": "Manage Canvas", "clone-canvas": "Clone Canvas"}
    };

    beforeEach(module('amdApp'));

    // Global test setup
    beforeEach(module('amdApp', function ($translateProvider) {
        $translateProvider.translations('en', {
            "language-code": "EN"
        }, 'fr', {
            "language-code": "fr"
        }, 'de', {
            "language-code": "de"
        }).preferredLanguage('en');
        $translateProvider.useLoader('LocaleLoader');
    }));

    beforeEach(inject(function ($controller, $rootScope, $modal, $state, LocalStoreService, CanvasService, $templateCache, UtilsService) {
        //mock opening modal, so we can test closing it
        $templateCache.put('views/modals/canvas-detail-close.html', '');
        localModalInstance = $modal.open({
            templateUrl: 'views/modals/canvas-detail-close.html',
            backdrop: 'static'
        });

        localState = $state;
        localStoreService = LocalStoreService;
        localCanvasService = CanvasService;
        localUtilsService = UtilsService;

        spyOn(localState, 'go').andCallThrough();
        localScope = $rootScope.$new();
        localStoreService.addLSItem(null, 'UserConfig', mockUserConfigData);
        spyOn(localModalInstance, 'close').andCallThrough();
        localController = $controller('CanvasCloseController', {
            $scope: localScope,
            $state: localState,
            LocalStoreService: localStoreService,
            CanvasService: localCanvasService,
            $modalInstance: localModalInstance,
            pickedCanvas: mockPickedCanvas,
            canvasBar: mockCanvasBar
        });

    }));

    // Modal Tests
    describe('should test cancel method', function () {
        beforeEach(function () {
            localScope.$digest();
        });
        it('should close the modal', function () {
            localScope.cancel();
            expect(localModalInstance.close).toHaveBeenCalled();
            expect(localState.go).toHaveBeenCalled();
        });
    });

    describe('should test closeCanvas method', function () {
        beforeEach(function () {
            localScope.$digest();
        });
        xit('should test closeCanvas method', function () {
            var openCanvases = [{
                "name": "ProductManagement",
                "canvasId": "201",
                "canvasType": "COMPANY",
                "displayOrder": "1"
            }, {
                "name": "AssignmentManagement",
                "canvasId": "231",
                "canvasType": "COMPANY",
                "displayOrder": "3"
            }, {
                "name": "GroupManagement",
                "canvasId": "235",
                "canvasType": "COMPANY",
                "displayOrder": "4"
            }];

            var canvases = [{
                "activeCanvas": {
                    "name": 'AssignmentManagement',
                    "canvasId": '231',
                    "canvasType": 'COMPANY',
                    "displayOrder": '3'
                },
                "openCanvases": openCanvases
            }];

            spyOn(localCanvasService, 'saveOpenAndActiveCanvas').andCallThrough();
            spyOn(localStoreService, 'addLSItem').andCallThrough();
            spyOn(localStoreService, 'removeLSItem').andCallThrough();
            localScope.closeCanvas();
            expect(localCanvasService.saveOpenAndActiveCanvas).toHaveBeenCalled();
            var userConfig = localStoreService.getLSItem('UserConfig');
            expect(userConfig.openCanvases).toEqual(openCanvases);
            expect(localState.go).toHaveBeenCalled();
            expect(localStoreService.addLSItem).toHaveBeenCalled();
            expect(localStoreService.removeLSItem).toHaveBeenCalled();
        });
    });

});