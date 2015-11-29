'use strict';
describe('SelectCanvas Controller Tests', function () {

    // Global vars
    var localScope = null;
    var localSelectCanvasController = null;
    var deferred = null;
    var localSelectCanvasService = null;
    var localUserService = null;
    var localStoreService;

    var userPermissionsMockData = {
        "status": "success",
        "code": "200",
        "message": "Request processed successfully",
        "level": null,
        "uniqueKey": null,
        "token": null,
        "explicitDismissal": null,
        "data": {
            "user": {
                "userId": 3,
                "userName": "jack123",
                "permissionSet": [
                    "create-canvas"
                ]
            }
        }
    };

    var canvasesMockData = {
        "status": "success",
        "code": "200",
        "message": "Request processed successfully",
        "level": null,
        "uniqueKey": null,
        "token": null,
        "explicitDismissal": null,
        "data": {
            "canvasByCategory": {
                "private": [{
                    "canvasId": 100,
                    "name": "Overview Warehouse1"
                }, {
                    "canvasId": 101,
                    "name": "bcd"
                }],
                "company": [{
                    "canvasId": 110,
                    "name": "cde"
                }, {
                    "canvasId": 111,
                    "name": "def"
                }],
                "lucas": [{
                    "canvasId": 201,
                    "name": "efg"
                }, {
                    "canvasId": 202,
                    "name": "fgh"
                }]
            },
            "user": {
                "userId": 3,
                "userName": "jack123",
                "permissionSet": ["create-canvas"]
            }
        }
    };

    var mockProfileData = {
        "username": "jack123",
        "userId": 3,
        "userPermissions": [
            "edit-canvas",
            "create-canvas",
            "publish-canvas"
        ]
    };

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

    var localHttpBackend;

    beforeEach(inject(function ($controller, $rootScope, $q, $templateCache, SelectCanvasService, UserService, $httpBackend, LocalStoreService) {
            localScope = $rootScope.$new();
            localSelectCanvasService = SelectCanvasService;
            localUserService = UserService;
            localStoreService = LocalStoreService;
            localHttpBackend = $httpBackend;
            $httpBackend.when('GET').respond({});
            
            LocalStoreService.addLSItem(null, 'ProfileData', mockProfileData);
            localSelectCanvasController = $controller("SelectCanvasController", {
                $scope: localScope,
                SelectCanvasService: localSelectCanvasService,
                UserService: localUserService,
                $modalInstance: {
                    result: {},
                    opened: {},
                    close: function () {
                    },
                    dismiss: function () {
                    }
                }
            });
        })
    );

    it("test create-canvas permission", function() {
        expect(localScope.isCreateCanvas).toBeTruthy();
    });

    it('handle getAllScreens() method', function() {
        spyOn(localSelectCanvasService, 'getAllScreens').andCallThrough();
        localSelectCanvasService.getAllScreens();
        expect(localSelectCanvasService.getAllScreens).toHaveBeenCalled();
    });

    describe("Should handle selection of the screen", function () {
        var localLocalStoreService;

        beforeEach(inject(function (LocalStoreService) {
            localLocalStoreService = LocalStoreService;
            spyOn(localScope, "selectScreen").andCallThrough();
            spyOn(localLocalStoreService, 'addLSItem').andCallThrough();
            spyOn(localLocalStoreService, 'getLSItem').andCallThrough();
        }));

        it("Should handle selection of the screen when canvas is not active", function () {
            localLocalStoreService.addLSItem(localScope, 'FavoriteCanvasListUpdated',
                [canvasesMockData.data.canvasByCategory.private[0]]);
            localScope.selectScreen(
                'private', {
                    "canvasId": 100,
                    "name": "Overview Warehouse1"
                });
            expect(localScope.selectScreen).toHaveBeenCalled();
            expect(localLocalStoreService.getLSItem).toHaveBeenCalled();
            expect(localLocalStoreService.addLSItem).toHaveBeenCalled();
        });

        it("Should handle selection of the screen when canvas is already active", function () {
            localLocalStoreService.addLSItem(localScope, 'FavoriteCanvasListUpdated',
                [canvasesMockData.data.canvasByCategory.private[0]]);
            localScope.selectScreen(
                'private', {
                    "canvasId": 100,
                    "name": "Overview Warehouse1"
                });
            expect(localScope.selectScreen).toHaveBeenCalled();
        });
    });
    
    it("Should handle the createNewCanvas method", function () {
        spyOn(localScope, 'createNewCanvas').andCallThrough();
        localScope.createNewCanvas();
        expect(localScope.createNewCanvas).toHaveBeenCalled();
    });

    it("Should handle the closeModal method", function () {
        spyOn(localScope, 'closeModal').andCallThrough();
        localScope.closeModal();
        expect(localScope.closeModal).toHaveBeenCalled();
    });
});