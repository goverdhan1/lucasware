/**
 * Created by Shaik Basha on 11/14/2014.
 */

'use strict';

describe('UserGroupFormController Tests', function () {

    var scope = null;
    var userGroupFormController = null;
    var localStoreService = null;
    var utilsService = null;

    var mockWidgetDetails = null;

    //Mock translateProvide
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

    //Global test setup
    beforeEach(inject(function ($rootScope, LocalStoreService, UtilsService, $controller) {
        //initialise dependencies
        scope = $rootScope.$new();
        localStoreService = LocalStoreService;
        utilsService = UtilsService;

        //skeleton mock widgetInstance
        mockWidgetDetails = {
            data: {
                "userGroups": {
                    "jack": [
                        "Admin",
                        "OpsManager",
                        "Supervisor"
                    ],
                    "jill": [
                        "OpsManager",
                        "OpsMgrNorth"
                    ],
                    "john": [
                        "Picker"
                    ]
                },

                "availableGroups": {
                    "Admin": [
                        "create-user",
                        "edit-user",
                        "delete-user"
                    ],
                    "OpsManager": [],
                    "OpsMgrNorth": [],
                    "Picker": [
                        "create-user",
                        "create-canvas"
                    ],
                    "Supervisor": [
                        "delete-widget"
                    ]
                }
            },
            "name": "user-group-widget",
            "id": 13,
            "shortName": "UserGroup",
            "title": "UserGroup",
            "subtype": "FORM",
            "widgetActionConfig": {
                "group-assignment-create": true,
                "group-assignment-edit": true
            },
            "broadcastList": [
                "userName"
            ],

            "widgetDefinition": {
                "name": "user-group-widget",
                "id": 13,
                "shortName": "UserGroup",
                "title": "UserGroup",
                "subtype": "FORM",
                "widgetActionConfig": {
                    "group-assignment-create": true,
                    "group-assignment-edit": true
                },
                "broadcastList": [
                    "userName"
                ],
                "reactToMap": {
                    "userName": {
                        "url": "/user/groups",
                        "searchCriteria": {
                            "pageNumber": "0",
                            "pageSize": "Long.MAX_VALUE",
                            "searchMap": {
                                "permissionGroupName": [
                                    "$groupName"
                                ]
                            },
                            "sortMap": {
                                "permissionGroupName": "ASC"
                            }
                        }
                    }
                },
                "defaultData": {},
                "defaultViewConfig": {
                    "height": 500,
                    "width": 1100,
                    "anchor": [
                        1,
                        2
                    ],
                    "zindex": 1,
                    "listensForList": [
                        "groupName"
                    ]
                }
            },
            "actualViewConfig": {
                "height": 500,
                "width": 1100,
                "anchor": [
                    1,
                    2
                ],
                "zindex": 1,
                "listensForList": [
                    "groupName"
                ]
            },
            "dataURL": {
                "url": "/user/groups",
                "searchCriteria": {
                    "pageNumber": "0",
                    "pageSize": "Long.MAX_VALUE",
                    "searchMap": {},
                    "sortMap": {
                        "userGroupName": "ASC"
                    }
                }
            },
            "updateWidget": true,
            "clientId": 101,
            "isMaximized": false
        };

        scope.widgetdetails = mockWidgetDetails;
        spyOn(utilsService, 'parseNames').andCallThrough();
        userGroupFormController = $controller('UserGroupFormController', {
            $scope: scope,
            LocalStoreService: localStoreService,
            UtilsService: utilsService
        });
    }));

    it('Dependency injection tests', function () {
        expect(userGroupFormController).toBeDefined();
        expect(scope).toBeDefined();
        expect(localStoreService).toBeDefined();
        expect(utilsService).toBeDefined();
    });

    it('Should check initial values', function () {
        expect(scope.widgetdetails.strAvailableItems).toEqual("userGroup.availableGroups");
        expect(scope.widgetdetails.strExistingItems).toEqual("userGroup.existingGroups");
        expect(scope.widgetdetails.resultantPermissions).toEqual([]);
        expect(scope.widgetdetails.selectedItemName).toEqual(null);
        expect(scope.widgetdetails.selectedAvailableItems).toEqual([]);
        expect(scope.widgetdetails.selectedExistingItems).toEqual([]);

    });

    it('Should check scope.widgetdetails.userNames', function () {
        expect(utilsService.parseNames).toHaveBeenCalled();
        expect(scope.widgetdetails.userNames).toEqual([{"name": "jack"}, {"name": "jill"}, {"name": "john"}]);
    });

    it('Should check scope.allGroups', function () {
        expect(scope.allGroups).toEqual(["Admin", "OpsManager", "OpsMgrNorth", "Picker", "Supervisor"]);
    });

    it('Should check updateSelectedUser', function () {
        spyOn(scope, "onChange").andCallThrough();
        scope.updateSelectedUser("jack");
        expect(scope.onChange).toHaveBeenCalled();
        expect(scope.widgetdetails.existingItems).toEqual(["Admin", "OpsManager", "Supervisor"]);
        expect(scope.widgetdetails.availableItems).toEqual(["Admin", "OpsManager", "OpsMgrNorth", "Picker", "Supervisor"]);
        expect(scope.widgetdetails.resultantPermissions).toEqual(['create-user', 'edit-user', 'delete-user', 'delete-widget']);
    })

});