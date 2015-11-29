"use strict";

describe("TopRight Controller Unit Tests", function () {

    // Global vars
    var localScope = null;
    var localTopRightController = null;
    var localModal;
    var localStoreService;
    var mockUserService = null;
    var mockUserServiceException = null;
    var deferred = null;
    var $httpBackend = null;
    var mockProfileData = {
        "username": "jack123",
        "userId": 3,
        "userPermissions": [
            "create-canvas",
            "edit-company-canvas",
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
    beforeEach(inject(function ($injector, $controller, $rootScope, LocalStoreService, $q, $modal) {

        // Set up the mock http service responses
        $httpBackend = $injector.get('$httpBackend');
        // Handle all the POST requests
        $httpBackend.when('GET').respond({});

        localStoreService = LocalStoreService;

        mockUserService = {
            getUserInfo: function () {
                deferred = $q.defer();
                // Resolve the promise
                deferred.resolve({
                    restangularCollection: true,
                    plain: function () {
                        return [
                            {
                                'firstName': 'Jack'
                            },
                            {
                                'firstName': 'Jill'
                            }
                        ];
                    }
                });
                return deferred.promise;
            },
            logout: function () {
            }
        };

        mockUserServiceException = {
            getUserInfo: function () {
                deferred = $q.defer();
                deferred.reject('error while getting userinfo');
                return deferred.promise;
            }
        };

        localModal = $modal;

        LocalStoreService.addLSItem(null, 'ProfileData', mockProfileData);

        //remove the cached UserInfo if any
        LocalStoreService.removeLSItem('UserInfo');
        spyOn(mockUserService, 'getUserInfo').andCallThrough();
        localScope = $rootScope.$new();
        localScope.activeCanvas = {
            "name": "ProductManagement",
            "canvasId": "201",
            "canvasType": "PRIVATE",
            "displayOrder": "1"
        };
        localTopRightController = $controller('TopRightController', {
            $scope: localScope,
            UserService: mockUserService,
            $modal: localModal
        });

    }));

    it("test create-canvas, edit-company-canvas and publish-canvas permissions", function() {
        expect(localScope.isCreateCanvas).toBeTruthy();
        expect(localScope.isEditCompanyCanvas).toBeTruthy();
        expect(localScope.isPublishCanvas()).toBeTruthy();
    });

    describe("Should handle getUserInfo method", function () {
        it("Should handle getUserInfo method", function () {
            localScope.$digest();
            expect(mockUserService.getUserInfo).toHaveBeenCalled();
        });
    });

    describe("Should handle for existing user", function () {
        beforeEach(inject(function ($controller, $rootScope, LocalStoreService) {
            localScope = $rootScope.$new();
            LocalStoreService.addLSItem(localScope, 'UserInfo', 'Jack');
            localTopRightController = $controller('TopRightController', {
                $scope: localScope,
                UserService: mockUserService
            });
        }));
        it("Should handle for existing user", function () {
            localScope.$digest();
            expect(localScope.userName).toBe("Jack");
        });
    });

    describe("Should handle getUserInfo exception", function () {
        beforeEach(inject(function ($controller, $rootScope, LocalStoreService) {
            localScope = $rootScope.$new();
            //remove the cached UserInfo if any
            LocalStoreService.removeLSItem('UserInfo');
            spyOn(mockUserServiceException, 'getUserInfo').andCallThrough();
            localTopRightController = $controller('TopRightController', {
                $scope: localScope,
                UserService: mockUserServiceException
            });
        }));
        it("Should handle getUserInfo exception", function () {
            localScope.$digest();
            expect(mockUserServiceException.getUserInfo).toHaveBeenCalled();
        });
    });

    describe("Should handle handleLogout method", function () {
        it("Should handle handleLogout method", function () {
            spyOn(localScope, 'handleLogout').andCallThrough();
            localScope.handleLogout();
            expect(localScope.handleLogout).toHaveBeenCalled();
        });
    });

    it("Should handle makeHomeCanvas method", function () {
        spyOn(localScope, 'makeHomeCanvas').andCallThrough();
        spyOn(localModal, 'open').andCallThrough();
        // call makeHomeCanvas and test the modal.open has been called
        localScope.makeHomeCanvas();
        expect(localScope.makeHomeCanvas).toHaveBeenCalled();
        expect(localModal.open).toHaveBeenCalled();
    });

    it("Should handle cloneCanvas method", function () {
        spyOn(localScope, 'cloneCanvas').andCallThrough();
        spyOn(localModal, 'open').andCallThrough();
        // call cloneCanvas and test the modal.open has been called
        localScope.cloneCanvas();
        expect(localScope.cloneCanvas).toHaveBeenCalled();
        expect(localModal.open).toHaveBeenCalled();
    });

    it("Should handle publishCanvas method", function() {
        spyOn(localScope, 'publishCanvas').andCallThrough();
        spyOn(localModal, 'open').andCallThrough();
        localStoreService.addLSItem(null, "ActiveCanvasId", 201);
        localStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', [{
            "name": "ProductManagement",
            "canvasId": "201",
            "canvasType": "PRIVATE",
            "displayOrder": "1"
        }, {
            "name": "Work Execution",
            "canvasId": "221",
            "isActive": "1",
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
        }, {
            "canvasId": 244,
            "canvasType": "COMPANY",
            "name": "test12345"
        }]);

        // call publishCanvas and test the modal.open has been called
        localScope.publishCanvas();
        expect(localScope.publishCanvas).toHaveBeenCalled();
        expect(localModal.open).toHaveBeenCalled();
    });
});
