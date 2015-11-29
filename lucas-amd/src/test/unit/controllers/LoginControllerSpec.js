'use strict';

describe('LoginController Unit Tests', function () {

    // Global vars
    var localScope = null;
    var deferred = null;
    var localLoginController;
    var localLoginService;
    var localStoreService;
    var log;
    var httpBackend;
    var mockProfileData = {
        "status": "success",
        "code": "200",
        "message": "Request processed successfully",
        "level": null,
        "uniqueKey": null,
        "token": null,
        "explicitDismissal": null,
        "data": {
            "username": "jack123",
            "userId": 3,
            "userPermissions": ["authenticated-user", "user-management-canvas-view", "create-assignment", "user-management-canvas-edit", "user-list-download-excel", "user-list-download-pdf", "pick-monitoring-canvas-edit", "pick-monitoring-canvas-view"],
            "firstName": "Jack",
            canvasId: 2,
            canvasName: "Morning Shift Canvas",
            widgetInstanceList: [],
            canvasList: [
                {
                    "name": "ProductManagement",
                    "canvasId": "201",
                    "canvasType": "COMPANY",
                    "displayOrder": "1"
                },
                {
                    "name": "Work Execution",
                    "canvasId": "221",
                    "isActive": "1",
                    "canvasType": "COMPANY",
                    "displayOrder": "2"
                },
                {
                    "name": "AssignmentManagement",
                    "canvasId": "231",
                    "canvasType": "COMPANY",
                    "displayOrder": "3"
                },
                {
                    "name": "GroupManagement",
                    "canvasId": "235",
                    "canvasType": "COMPANY",
                    "displayOrder": "4"
                }
            ]
        }
    };
    // Global test setup
    beforeEach(module('amdApp', function ($translateProvider) {
        $translateProvider.translations('en', {
            "language-code": "EN",
        }, 'fr', {
            "language-code": "fr",
        }, 'de', {
            "language-code": "de",
        }).preferredLanguage('en');

        $translateProvider.useLoader('LocaleLoader');
    }));

    beforeEach(inject(function ($rootScope, $controller, $templateCache, UserService, LocalStoreService, $httpBackend, LoginService) {
        // Load the .html files into the templateCache,
        // these will be used after the user is created by the code in LoginController.js
        $templateCache.put('views/canvaspage/canvas-container.html', '');
        $templateCache.put('views/indexpage/locale.html', '');
        $templateCache.put('views/canvaspage/topleft-tmpl.html', '');
        $templateCache.put('views/canvaspage/topcenter-tmpl.html', '');
        $templateCache.put('views/canvaspage/topright-tmpl.html', '');
        $templateCache.put('views/canvaspage/canvas-detail.html', '');
        $templateCache.put('views/canvaspage/bottomleft-tmpl.html', '');
        $templateCache.put('views/canvaspage/bottomright-tmpl.html', '');

        log = {error: function () {
        }};

        localStoreService = LocalStoreService;
        localLoginService = LoginService;

        httpBackend = $httpBackend;
        httpBackend.when('GET').respond(mockProfileData);
        httpBackend.when('POST').respond(mockProfileData);


    }));

    beforeEach(inject(function ($controller, $rootScope) {
        localScope = $rootScope.$new();
        spyOn(localScope, '$on').andCallThrough();
        localLoginController = $controller('LoginController', {
            $scope: localScope,
            LoginService: localLoginService
        });
        spyOn(localScope, "$broadcast").andCallThrough();
    }));

    // Correct About favorite canvas Specs
    describe('Should get users favorite canvas', function () {
        it('promise should return correct favorite canvas when resolved', function () {
            spyOn(localLoginService, 'login');
            localScope.loginSubmit();
            httpBackend.flush();
            localScope.$digest();
            expect(localLoginService.login).toHaveBeenCalled();
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

    // Login Failure events
    describe("Should handle login failure events", function () {
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
            expect(localScope.username).toBe('');
            expect(localScope.password).toBe('');
        });
    });
});