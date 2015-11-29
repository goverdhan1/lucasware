/*
 Lucas Systems Inc
 11279 Perry Highway
 Wexford
 PA 15090
 United States


 The information in this file contains trade secrets and confidential
 information which is the property of Lucas Systems Inc.

 All trademarks, trade names, copyrights and other intellectual property
 rights created, developed, embodied in or arising in connection with
 this software shall remain the sole property of Lucas Systems Inc.

 Copyright (c) Lucas Systems, 2014
 ALL RIGHTS RESERVED

 */

'use strict';

describe('Group Maintenance Controller Tests', function() {

    var scope = null;
    var controller = null;
    var LocalStoreService = null;
    var UtilsService = null;
    var GroupMaintenanceService = null;
    var modal = null;
    var rootScope;

    var mockWidgetDetails = null;

    //deferred promises for unit testing API calls
    var groupNames = null;
    var permissionCategories = null;
    var usersList;

    //
    // Load translation provider
    //
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

    //
    // Global test setup
    //
    beforeEach(inject(function (_$rootScope_, _GroupMaintenanceService_, _LocalStoreService_, _UtilsService_, _$q_, _$modal_) {
        //initialise dependencies
        rootScope = _$rootScope_;
        scope = _$rootScope_.$new();
        GroupMaintenanceService = _GroupMaintenanceService_;
        LocalStoreService = _LocalStoreService_;
        UtilsService = _UtilsService_;
        modal = _$modal_;

        //skeleton mock widgetInstance
        mockWidgetDetails = {
            "id": null,
            "clientId": 100,
            "widgetDefinition": {
                "id": 12,
                "name": "group-maintenance-widget",
                "widgetActionConfig" : {
                    "widget-actions" : {
                        "create-group" : false,
                        "edit-group" : false
                    }
                }
            }
        };

        //Resolve promises
        groupNames = _$q_.defer();
        groupNames.resolve([
            "administrator",
            "picker",
            "supervisor"
        ]);
        permissionCategories = _$q_.defer();
        permissionCategories.resolve([
            "canvas-management",
            "user-management",
            "data-export",
            "product-management"
        ]);

        usersList = _$q_.defer();
        usersList.resolve([{
            "firstName": "Lucas System",
            "lastName": "Lucas System",
            "skill": null,
            "userName": "system"
        }, {
            "firstName": "Admin",
            "lastName": "User",
            "skill": null,
            "userName": "admin123"
        }]);

        // setup spies for CreateEditUserService mock
        spyOn(GroupMaintenanceService, 'getGroupNames').andReturn(groupNames.promise);
        spyOn(GroupMaintenanceService, 'getPermissionCategories').andReturn(permissionCategories.promise);
        spyOn(GroupMaintenanceService, 'loadUsers').andReturn(usersList.promise);

        //LocalStorage Mock
        spyOn(LocalStoreService, 'getLSItem').andCallFake(function() {
            if (arguments[0] == 'ActiveCanvasId') {
                return 403;
            } else if (arguments[0] == 'FavoriteCanvasListUpdated') {
                return [{
                    "canvasName": "JackCanvas403",
                    "canvasId": 403,
                    "canvasType": "PRIVATE",
                    "displayOrder": 2,
                    "widgetInstanceList": [{
                        "data": {
                            "grid": {
                                "0": {
                                    "values": ["Administrators", "Hired in the Last 30 Days", "Picked to Train Users Groups", "LucasAdmin1", "LucasAdmin2", "LucasAdmin3", "LucasAdmin4", "LucasAdmin5", "LucasAdmin6", "LucasAdmin7", "LucasAdmin8", "LucasAdmin9", "LucasAdmin10", "LucasAdmin11", "LucasAdmin12", "LucasAdmin13", "LucasAdmin14", "LucasAdmin15", "LucasAdmin16", "LucasAdmin17", "LucasAdmin18", "LucasAdmin19", "LucasAdmin20"]
                                },
                                "1": {
                                    "values": ["Administrators", "Hired in the Last 30 Days", "Picked to Train Users Groups", "LucasAdmin1", "LucasAdmin2", "LucasAdmin3", "LucasAdmin4", "LucasAdmin5", "LucasAdmin6", "LucasAdmin7", "LucasAdmin8", "LucasAdmin9", "LucasAdmin10", "LucasAdmin11", "LucasAdmin12", "LucasAdmin13", "LucasAdmin14", "LucasAdmin15", "LucasAdmin16", "LucasAdmin17", "LucasAdmin18", "LucasAdmin19", "LucasAdmin20"]
                                },
                                "2": {
                                    "values": ["Administrators", "New Hires", "Pick to train users", "LucasAdmin1", "LucasAdmin2", "LucasAdmin3", "LucasAdmin4", "LucasAdmin5", "LucasAdmin6", "LucasAdmin7", "LucasAdmin8", "LucasAdmin9", "LucasAdmin10", "LucasAdmin11", "LucasAdmin12", "LucasAdmin13", "LucasAdmin14", "LucasAdmin15", "LucasAdmin16", "LucasAdmin17", "LucasAdmin18", "LucasAdmin19", "LucasAdmin20"]
                                },
                                "3": {
                                    "values": ["18", "10", "5", "10", "44", "49", "3", "29", "18", "43", "27", "47", "26", "43", "10", "26", "11", "46", "24", "8", "24", "18", "35"]
                                }
                            },
                            "totalRecords": 200
                        },
                        "id": "37",
                        "widgetDefinition": {
                            "name": "search-group-grid-widget",
                            "id": 15,
                            "shortName": "SearchGroup",
                            "defaultViewConfig": {
                                "minimumWidth": 300,
                                "minimumHeight": 480,
                                "gridColumns": {
                                    "0": {
                                        "name": "Group Name",
                                        "fieldName": "groupName",
                                        "visible": true,
                                        "sortOrder": "1",
                                        "order": "1",
                                        "allowFilter": true
                                    },
                                    "1": {
                                        "name": "Description",
                                        "fieldName": "description",
                                        "visible": true,
                                        "sortOrder": "0",
                                        "order": "2",
                                        "allowFilter": true
                                    },
                                    "2": {
                                        "name": "User Count",
                                        "fieldName": "userCount",
                                        "visible": true,
                                        "sortOrder": "0",
                                        "order": "3",
                                        "allowFilter": true
                                    }
                                },
                                "deviceWidths": {
                                    "320": "mobile",
                                    "600": "tablet",
                                    "800": "desktop",
                                    "1200": "wideScreen"
                                },
                                "autoRefreshConfig": {
                                    "enabled": true,
                                    "interval": 120,
                                    "globalOverride": false
                                },
                                "anchor": [275, 295],
                                "zindex": 0,
                                "reactToList": [],
                                "dateFormat": {
                                    "selectedFormat": "mm-dd-yyyy",
                                    "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                                },
                                "originalMinimumWidth": 300,
                                "originalMinimumHeight": 480
                            },
                            "broadcastList": ["groupName"],
                            "reactToList": ["userName"],
                            "defaultData": {},
                            "widgetActionConfig": {
                                "widget-access": {
                                    "search-group-widget-access": true
                                },
                                "widget-actions": {
                                    "delete-group": true
                                }
                            },
                            "title": "Search group",
                            "dataURL": {
                                "searchCriteria": {
                                    "pageNumber": "0",
                                    "pageSize": "10",
                                    "searchMap": {},
                                    "sortMap": {}
                                },
                                "url": "/groups/grouplist/search"
                            }
                        },
                        "actualViewConfig": {
                            "minimumWidth": 300,
                            "minimumHeight": 480,
                            "gridColumns": {
                                "0": {
                                    "name": "Group Name",
                                    "fieldName": "groupName",
                                    "visible": true,
                                    "sortOrder": "1",
                                    "order": "1",
                                    "allowFilter": true
                                },
                                "1": {
                                    "name": "Description",
                                    "fieldName": "description",
                                    "visible": true,
                                    "sortOrder": "0",
                                    "order": "2",
                                    "allowFilter": true
                                },
                                "2": {
                                    "name": "User Count",
                                    "fieldName": "userCount",
                                    "visible": true,
                                    "sortOrder": "0",
                                    "order": "3",
                                    "allowFilter": true
                                }
                            },
                            "autoRefreshConfig": {
                                "enabled": true,
                                "interval": 120,
                                "globalOverride": false
                            },
                            "anchor": [275, 295],
                            "zindex": 0,
                            "dateFormat": {
                                "selectedFormat": "mm-dd-yyyy",
                                "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                            },
                            "position": 1
                        },
                        "updateWidget": true,
                        "clientId": 104,
                        "isMaximized": false
                    }, {
                        "data": {
                            "groupPermissions": {
                                "warehouse-manager": ["create-canvas", "publish-canvas", "edit-company-canvas", "create-assignment", "view-report-productivity", "configure-location"],
                                "system": ["group-maintenance-widget-access", "create-group", "edit-group", "search-user-widget-access", "create-edit-user-widget-access", "create-user", "edit-user", "disable-user", "delete-user", "enable-user", "retrain-voice-model", "create-canvas", "publish-canvas", "edit-company-canvas", "user-list-download-excel", "user-list-download-pdf", "create-product", "view-product", "edit-product", "delete-product", "create-assignment", "view-report-productivity", "configure-location", "authenticated-user", "edit-multi-user", "pickline-by-wave-widget-access", "search-product-grid-widget-access"],
                                "basic-authenticated-user": ["authenticated-user"],
                                "supervisor": ["group-maintenance-widget-access", "create-group", "edit-group", "search-user-widget-access", "create-edit-user-widget-access", "create-user", "edit-user", "disable-user", "delete-user", "enable-user", "retrain-voice-model", "publish-canvas", "user-list-download-excel", "user-list-download-pdf", "create-product", "view-product", "edit-product", "delete-product", "edit-multi-user", "pickline-by-wave-widget-access", "search-product-grid-widget-access", "equipment-type-widget-access", "equipment-management-widget-access", "create-equipment-type", "edit-equipment-type", "delete-equipment-type", "create-equipment", "edit-equipment", "delete-equipment", "unassign-user", "view-checklist", "search-group-widget-access", "delete-group"],
                                "administrator": ["publish-canvas", "authenticated-user"],
                                "picker": ["create-edit-user-widget-access", "create-user", "edit-user", "create-assignment", "authenticated-user"]
                            },
                            "availablePermissions": ["publish-canvas", "authenticated-user", "create-edit-user-widget-access", "create-user", "edit-user", "create-assignment", "group-maintenance-widget-access", "create-group", "edit-group", "search-user-widget-access", "disable-user", "delete-user", "enable-user", "retrain-voice-model", "user-list-download-excel", "user-list-download-pdf", "create-product", "view-product", "edit-product", "delete-product", "edit-multi-user", "pickline-by-wave-widget-access", "search-product-grid-widget-access", "equipment-type-widget-access", "equipment-management-widget-access", "create-equipment-type", "edit-equipment-type", "delete-equipment-type", "create-equipment", "edit-equipment", "delete-equipment", "unassign-user", "view-checklist", "search-group-widget-access", "delete-group", "create-canvas", "edit-company-canvas", "view-report-productivity", "configure-location"]
                        },
                        "id": "38",
                        "widgetDefinition": {
                            "name": "group-maintenance-widget",
                            "id": 12,
                            "shortName": "GroupMaintenance",
                            "defaultViewConfig": {
                                "minimumWidth": 300,
                                "minimumHeight": 480,
                                "listensForList": ["groupName"],
                                "deviceWidths": {
                                    "320": "mobile",
                                    "540": "tablet",
                                    "800": "desktop",
                                    "1200": "wideScreen"
                                },
                                "anchor": [2, 2],
                                "zindex": 0,
                                "reactToList": [],
                                "dateFormat": {
                                    "selectedFormat": "mm-dd-yyyy",
                                    "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                                },
                                "originalMinimumWidth": 300,
                                "originalMinimumHeight": 480
                            },
                            "broadcastList": ["groupName"],
                            "reactToList": null,
                            "defaultData": {},
                            "widgetActionConfig": {
                                "widget-access": {
                                    "group-maintenance-widget-access": true
                                },
                                "widget-actions": {
                                    "create-group": true,
                                    "edit-group": true
                                }
                            },
                            "title": "Group Maintenance",
                            "dataURL": {
                                "searchCriteria": {
                                    "pageNumber": "0",
                                    "pageSize": "2147483647",
                                    "searchMap": {},
                                    "sortMap": {
                                        "permissionGroupName": "ASC"
                                    }
                                },
                                "url": "/users/groups/permissions"
                            }
                        },
                        "actualViewConfig": {
                            "minimumWidth": 300,
                            "minimumHeight": 480,
                            "listensForList": ["groupName"],
                            "anchor": [2, 2],
                            "zindex": 0,
                            "dateFormat": {
                                "selectedFormat": "mm-dd-yyyy",
                                "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                            },
                            "position": 2
                        },
                        "updateWidget": true,
                        "clientId": 105,
                        "isMaximized": false,
                        "widgetInteractionConfig": [{
                            "widgetInstance": {
                                "id": "37",
                                "name": "SearchGroup, 37"
                            },
                            "dataElement": "groupName",
                            "active": true
                        }]
                    }]
                }];
            } else {
                return {
                    "ProfileData": {
                        "username": "jack123"
                    }
                };
            }
        });
    }));


    //
    // Ensure controller dependencies are injected globally
    //
    describe('Dependency injection tests', function () {
        beforeEach(inject(function (_$controller_) {
            scope.widgetdetails = angular.copy(mockWidgetDetails);

            controller = _$controller_('GroupMaintenanceFormController', {
                $scope: scope,
                GroupMaintenanceService: GroupMaintenanceService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });
        }));

        it('should inject controller with dependencies', function () {
            expect(controller).toBeDefined();
            expect(scope).toBeDefined();
            expect(GroupMaintenanceService).toBeDefined();
            expect(LocalStoreService).toBeDefined();
            expect(UtilsService).toBeDefined();
        });
    });


    //
    // Ensure form in rendered with first tab (Login Information) active
    // and form is in disabled state
    //
    describe('Rendering form in default state', function() {

        //setup
        beforeEach(inject(function(_$controller_) {

            //provide controller scope with a 'widgetdetails' mock
            scope.widgetdetails = angular.copy(mockWidgetDetails);

            //inject controller
            controller = _$controller_('GroupMaintenanceFormController', {
                $scope: scope,
                GroupMaintenanceService: GroupMaintenanceService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });
        }));


        //initialise controller in default state with first tab selected
        describe('form initialised in default state with first tab selected', function() {

            //form is disabled
            it('should initialise the form in its default SEARCH state', function() {
                expect(scope.MODE_SEARCH).toBeTruthy();
            });

            //first tab is selected
            it('should initialise with the first tab selected', function() {
                expect(scope.tabs.length).toEqual(3);
                expect(scope.tabs[0]).toBeTruthy(); //first tab is selected
                expect(scope.tabs[1]).toBeFalsy();
                expect(scope.tabs[2]).toBeFalsy();
            });
        });
    });


    //
    // Static data is loaded when controller is instantiated
    //
    describe('Rendering form with static data', function() {

        //setup
        beforeEach(inject(function(_$controller_) {

            //provide controller scope with a 'widgetdetails' mock
            scope.widgetdetails = angular.copy(mockWidgetDetails);

            //inject controller
            controller = _$controller_('GroupMaintenanceFormController', {
                $scope: scope,
                GroupMaintenanceService: GroupMaintenanceService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });
        }));

        //should load and assign static data to scope
        describe('loading Group Names and Permission Categories', function() {
            //groups
            it('should load and cache list of Group Names', function() {
                //required to resolve the promise
                scope.$digest();

                expect(GroupMaintenanceService.getGroupNames).toHaveBeenCalled();
                expect(scope.groups.length).toBeGreaterThan(0);
                expect(scope.groups).toContain('picker');
            });

            //permission categories
            it('should load and cache Permission Categories', function() {
                //required to resolve the promise
                scope.$digest();

                expect(GroupMaintenanceService.getPermissionCategories).toHaveBeenCalled();
                expect(scope.fields.categories.available).toBeDefined();

                expect(scope.fields.categories.available.length).toBeGreaterThan(0);
                expect(scope.fields.categories.available).toContain('data-export');
            });
        });
    });


    //
    // Monitoring the Group Name field
    //
    describe('Monitoring the Group Name field to enable searching for a group', function() {

        //setup
        beforeEach(inject(function(_$controller_) {

            //provide controller scope with a 'widgetdetails' mock
            scope.widgetdetails = angular.copy(mockWidgetDetails);

            //inject controller
            controller = _$controller_('GroupMaintenanceFormController', {
                $scope: scope,
                GroupMaintenanceService: GroupMaintenanceService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });

            //invoke the controller function
            scope.$digest();
        }));

        //Enabling the search button only when a value has been entered into Group Name field
        it('should enable the search button only when a value is present in the Group Name field', function() {
            //search button disabled by default
            expect(scope.searchButtonEnabled).toBeFalsy();

            //Set value in group model (Group Name)
            scope.fields.groupName = "administrator";

            //invoke the watcher function
            scope.groupNameWatcher();

            //assert search button becomes enabled
            expect(scope.searchButtonEnabled).toBeTruthy();
        });

        //Search button should not become enabled if a Group Name has not been specified
        it('should not enabled the search button when a value is not present in Group Name field', function() {
            //search button disabled by default
            expect(scope.searchButtonEnabled).toBeFalsy();

            //Set value in group model (Group Name) to empty
            scope.fields.groupName = "";

            //invoke the watcher function
            scope.groupNameWatcher();

            //assert search button becomes enabled
            expect(scope.searchButtonEnabled).toBeFalsy();
        });

        //Enter key should allow search only when a value has been entered into Group Name field
        it('should allow Enter Key search when a Group Name has been specified', function() {
            var event = {
                "keyCode" : 13 //enter key
            };

            //Spy on searchGroup function so we can check it was called or not
            spyOn(scope, 'searchGroup').andReturn();

            //ensure group name has NOT been specified
            scope.fields.groupName = "";

            //invoke enterKey watcher function
            scope.enterKeySearch(event);

            //Assert the search group function was not called
            expect(scope.searchGroup).not.toHaveBeenCalled();

            //specify a Group Name
            scope.fields.groupName = "picker";

            //invoke the enterKey watcher function
            scope.enterKeySearch(event);

            //Assert search group was called
            expect(scope.searchGroup).toHaveBeenCalled();
        });
    });


    //
    // Rendering the widget in different states
    //
    describe('Rendering the widget in different states', function() {

        //setup
        beforeEach(inject(function(_$controller_) {

            //provide controller scope with a 'widgetdetails' mock
            scope.widgetdetails = angular.copy(mockWidgetDetails);

            //inject controller
            controller = _$controller_('GroupMaintenanceFormController', {
                $scope: scope,
                GroupMaintenanceService: GroupMaintenanceService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });

            //invoke the controller function
            scope.$digest();
        }));

        //Rendering widget in SEARCH mode
        it('should render widget in SEARCH mode', function() {
            //invoke controller function and assert results
            scope.setWidgetState('MODE_SEARCH');

            expect(scope.MODE_SEARCH).toBeTruthy();
            expect(scope.groupNameEnabled).toBeTruthy();
            expect(scope.searchButtonEnabled).toBeFalsy();
            expect(scope.searchButtonVisible).toBeTruthy();
            expect(scope.clearButtonEnabled).toBeFalsy();
            expect(scope.clearButtonVisible).toBeFalsy();
            expect(scope.formControlsEnabled).toBeFalsy();
            expect(scope.saveButtonEnabled).toBeFalsy();
            expect(scope.saveAsButtonEnabled).toBeFalsy();
            expect(scope.tabs[0]).toBeTruthy();
            expect(scope.groupNotFound).toBeFalsy();
        });

        //Rendering widget in EDIT mode
        it('should render widget in EDIT mode', function() {
            //Mock user permissions
            scope.permissions = {
                "create-group" : false,
                "edit-group" : true
            };

            //invoke controller function and assert results
            scope.setWidgetState('MODE_EDIT');

            expect(scope.MODE_EDIT).toBeTruthy();
            expect(scope.groupNameEnabled).toBeFalsy();
            expect(scope.searchButtonEnabled).toBeFalsy();
            expect(scope.searchButtonVisible).toBeFalsy();
            expect(scope.clearButtonEnabled).toBeTruthy();
            expect(scope.clearButtonVisible).toBeTruthy();
            //manage template controls based on user permissions
            expect(scope.formControlsEnabled).toBeTruthy();
            expect(scope.saveButtonEnabled).toBeTruthy();
            expect(scope.saveAsButtonEnabled).toBeFalsy();
        });

        //Rendering widget in CREATE mode (with 'create-group' permission)
        it('should render widget in CREATE mode', function() {
            //Mock user permissions
            scope.permissions = {
                "create-group" : true,
                "edit-group" : true
            };

            //invoke controller function and assert results
            scope.setWidgetState('MODE_CREATE');

            expect(scope.MODE_CREATE).toBeTruthy();
            expect(scope.groupNameEnabled).toBeFalsy();
            expect(scope.searchButtonEnabled).toBeFalsy();
            expect(scope.searchButtonVisible).toBeFalsy();
            expect(scope.clearButtonEnabled).toBeTruthy();
            expect(scope.clearButtonVisible).toBeTruthy();
            expect(scope.formControlsEnabled).toBeTruthy();
            expect(scope.saveButtonEnabled).toBeTruthy();
            expect(scope.saveAsButtonEnabled).toBeFalsy();
        });

        //Rendering widget in CREATE mode (without 'create-group' permission)
        it('should render widget in CREATE mode', function() {
            //Mock user permissions
            scope.permissions = {
                "create-group" : false,
                "edit-group" : true
            };

            //invoke controller function and assert results
            scope.setWidgetState('MODE_CREATE');

            expect(scope.MODE_CREATE).toBeTruthy();
            expect(scope.groupNameEnabled).toBeFalsy();
            expect(scope.searchButtonEnabled).toBeFalsy();
            expect(scope.searchButtonVisible).toBeFalsy();
            expect(scope.clearButtonEnabled).toBeTruthy();
            expect(scope.clearButtonVisible).toBeTruthy();
            expect(scope.formControlsEnabled).toBeFalsy();
            expect(scope.saveButtonEnabled).toBeFalsy();
            expect(scope.saveAsButtonEnabled).toBeFalsy();
            expect(scope.groupNotFound).toBeTruthy();
        });

        //Rendering widget in SAVE mode
        it('should render widget in SAVE mode', function() {
            //invoke controller function and assert results
            scope.setWidgetState('MODE_SAVE');

            expect(scope.MODE_SAVE).toBeTruthy();
            expect(scope.groupNameEnabled).toBeFalsy();
            expect(scope.searchButtonEnabled).toBeFalsy();
            expect(scope.searchButtonVisible).toBeFalsy();
            expect(scope.clearButtonEnabled).toBeFalsy();
            expect(scope.clearButtonVisible).toBeTruthy();
            expect(scope.formControlsEnabled).toBeFalsy();
            expect(scope.saveButtonEnabled).toBeFalsy();
            expect(scope.saveAsButtonEnabled).toBeFalsy();
        });
    });


    //
    // Resetting the widget
    //
    describe('Resetting the widget', function() {

        //setup
        beforeEach(inject(function(_$controller_) {

            //provide controller scope with a 'widgetdetails' mock
            scope.widgetdetails = angular.copy(mockWidgetDetails);

            //inject controller
            controller = _$controller_('GroupMaintenanceFormController', {
                $scope: scope,
                GroupMaintenanceService: GroupMaintenanceService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });

            //invoke the controller function
            scope.$digest();
        }));

        //unit test resetWidget controller function
        describe('Function: resetWidget()', function() {
            //setup
            beforeEach(inject(function(_$controller_) {

                //setup controller spies
                spyOn(scope, 'setWidgetState').andCallThrough();

                //invoke the controller function
                scope.resetWidget();
                scope.$digest();
            }));

            //should reset field ng-models back to default selections
            it('should reset the field models to default selections', function() {
                //widget models
                expect(scope.fields.groupName).toEqual("");
                expect(scope.fields.categories.selected).toEqual("");

                //should clear the stored permissions list, ready for a new search
                expect(scope.fields.originalAssignedPermissions.length).toEqual(0);
                expect(scope.fields.tempAssignedPermissions.length).toEqual(0);

                //should clear the permission selection boxes
                expect(scope.widgetdetails.availableItems.length).toEqual(0);
                expect(scope.widgetdetails.selectedAvailableItems.length).toEqual(0);
                expect(scope.widgetdetails.existingItems.length).toEqual(0);
                expect(scope.widgetdetails.selectedExistingItems.length).toEqual(0);

                //ensure form is rendered into correct state
                expect(scope.setWidgetState).toHaveBeenCalledWith('MODE_SEARCH');
                expect(scope.MODE_SEARCH).toBeTruthy();
            });

            //should disable the form and select first tab
            it('should default back to first tab', function() {
                expect(scope.tabs[0]).toBeTruthy();
                expect(scope.tabs[1]).toBeFalsy();
                expect(scope.tabs[2]).toBeFalsy();
            });
        });

        //Should reset scope assignments
        describe('Checking for unsaved changes when resetting widget', function() {

            //Mock the modalInstance object that is returned from the $modal.open call
            var mockModalInstance = {
                result: {
                    then: function (confirmCallback, cancelCallback) {
                        //Store the callbacks for later when the user clicks on the OK or Cancel button of the dialog
                        this.confirmCallBack = confirmCallback;
                        this.cancelCallback = cancelCallback;
                    }
                },
                close: function (item) {
                    //The user clicked OK on the modal dialog, call the stored confirm callback with the selected item
                    this.result.confirmCallBack(item);
                },
                dismiss: function (type) {
                    //The user clicked cancel on the modal dialog, call the stored cancel callback
                    this.result.cancelCallback(type);
                }
            };

            //setup
            beforeEach(function() {
                //setup spies
                spyOn(modal, 'open').andReturn(mockModalInstance);
                spyOn(scope, 'resetWidget').andReturn();
                spyOn(scope, 'persistUnsavedAssignmentChanges').andReturn();

                //digest the scope to resolve any promises requested at controller
                //initialisation
                scope.$digest();
            });

            //should warn of unsaved changes when widget is in CREATE mode
            it('should warn of unsaved changes when widget is in CREATE mode', function () {
                //test specific spies
                spyOn(GroupMaintenanceService, 'comparePermissionSets').andReturn(false);

                //set widget to be in CREATE mode
                scope.MODE_CREATE = true;

                //invoke controller function
                scope.clear();

                //assert results
                expect(scope.resetWidget).not.toHaveBeenCalled();
                expect(scope.persistUnsavedAssignmentChanges).not.toHaveBeenCalled();
                expect(GroupMaintenanceService.comparePermissionSets).not.toHaveBeenCalled();
                expect(modal.open).toHaveBeenCalled();
            });

            //resetting the widget without any unsaved changes
            it('should not display modal when all changes have been saved', function () {
                //test specific spies - mock no unsaved changes
                spyOn(GroupMaintenanceService, 'comparePermissionSets').andReturn(true);

                //invoke controller function
                scope.clear();

                //assert results
                expect(scope.resetWidget).toHaveBeenCalled();
                expect(scope.persistUnsavedAssignmentChanges).toHaveBeenCalled();
                expect(GroupMaintenanceService.comparePermissionSets).toHaveBeenCalled();
                expect(modal.open).not.toHaveBeenCalled();
            });

            //resetting the widget with unsaved changes present
            it('should display modal when unsaved changes are present', function () {
                //test specific spies - mock having unsaved changes
                spyOn(GroupMaintenanceService, 'comparePermissionSets').andReturn(false);

                //invoke controller function
                scope.clear();

                //assert results
                expect(scope.resetWidget).not.toHaveBeenCalled();
                expect(scope.persistUnsavedAssignmentChanges).toHaveBeenCalled();
                expect(GroupMaintenanceService.comparePermissionSets).toHaveBeenCalled();
                expect(modal.open).toHaveBeenCalled();
            });

            //should reset the widget when user agrees to discarding changes
            it('should reset the widget when user agrees to discarding changes', function () {
                //test specific spies - mock no unsaved changes
                spyOn(GroupMaintenanceService, 'comparePermissionSets').andReturn(false);

                //invoke controller function
                scope.clear();

                //invoke close method (Yes button) on modal to confirm discard
                scope.modalInstance.close();

                //assert results
                expect(scope.resetWidget).toHaveBeenCalled();
                expect(scope.persistUnsavedAssignmentChanges).toHaveBeenCalled();
                expect(GroupMaintenanceService.comparePermissionSets).toHaveBeenCalled();
                expect(modal.open).toHaveBeenCalled();
            });

            //should not reset the user does not discard unsaved changes
            it('should not reset the user does not discard unsaved changes', function () {
                //test specific spies - mock no unsaved changes
                spyOn(GroupMaintenanceService, 'comparePermissionSets').andReturn(false);

                //invoke controller function
                scope.clear();

                //invoke close method (No button) on modal to cancel the operation
                scope.modalInstance.dismiss();

                //assert results
                expect(scope.resetWidget).not.toHaveBeenCalled();
                expect(scope.persistUnsavedAssignmentChanges).toHaveBeenCalled();
                expect(GroupMaintenanceService.comparePermissionSets).toHaveBeenCalled();
                expect(modal.open).toHaveBeenCalled();
            });
        });
    });


    //
    // Saving a user
    //
    describe('Saving a user', function() {

        var def = null;

        //test setup
        beforeEach(inject(function(_$controller_, _$q_) {

            //deferred exec
            def = _$q_.defer();

            //provide controller scope with a 'widgetdetails' mock
            scope.widgetdetails = angular.copy(mockWidgetDetails);

            //inject controller
            controller = _$controller_('GroupMaintenanceFormController', {
                $scope: scope,
                GroupMaintenanceService: GroupMaintenanceService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });

            //spies
            spyOn(GroupMaintenanceService, 'saveGroup').andReturn(def.promise);
            spyOn(scope, 'persistUnsavedAssignmentChanges').andReturn();
            spyOn(scope, 'setWidgetState').andReturn();
            spyOn(rootScope, '$emit').andCallThrough();

            //digest the scope to resolve any promises requested at controller
            //initialisation
            scope.$digest();
        }));

        //should have a group
        it('should save a group', function () {
            //resolve the promise
            def.resolve();

            //invoke controller function
            scope.save();
            scope.$digest();

            //assert results
            expect(GroupMaintenanceService.saveGroup).toHaveBeenCalled();
            expect(scope.persistUnsavedAssignmentChanges).toHaveBeenCalled();
            expect(scope.setWidgetState).toHaveBeenCalledWith("MODE_SAVE");
            expect(scope.setWidgetState).toHaveBeenCalledWith("MODE_EDIT");
            // test rootScope.$emit must have been called
            expect(rootScope.$emit).toHaveBeenCalled(); 
        });

        //should return back to edit mode if save was unsuccessful
        it('should return widget to edit mode it save was unsuccessful', function () {
            //reject the promise
            def.reject();

            //invoke controller function
            scope.save();
            scope.$digest();

            //assert results
            expect(GroupMaintenanceService.saveGroup).toHaveBeenCalled();
            expect(scope.persistUnsavedAssignmentChanges).toHaveBeenCalled();
            expect(scope.setWidgetState).toHaveBeenCalledWith("MODE_EDIT");
        });
    });


    //
    // Searching for a group
    //
    describe('Searching for a group', function() {

        var groupDetails = null;

        //setup
        beforeEach(inject(function(_$controller_, _$q_) {

            //deferred exec
            groupDetails = _$q_.defer();
            groupDetails.resolve({
                "groupName" : "administrator",
                "groupDescription" : "dummy description for group admin",
                "assignedPermissions" : [
                    "authenticated-user",
                    "create-group",
                    "edit-group"
                ],
                enrolledUsers: ["jack123", "jill123", "system"],
                "assignedUsers" : [
                    "jack123",
                    "jill123",
                    "system"]
            });

            //provide controller scope with a 'widgetdetails' mock
            scope.widgetdetails = angular.copy(mockWidgetDetails);

            //inject controller
            controller = _$controller_('GroupMaintenanceFormController', {
                $scope: scope,
                GroupMaintenanceService: GroupMaintenanceService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });

            //spies
            spyOn(scope, 'setWidgetState').andReturn();
            spyOn(scope, 'loadPermissionsForCategory').andReturn();
            spyOn(GroupMaintenanceService, 'getGroupDetails').andReturn(groupDetails.promise);

            //digest the scope to resolve any promises requested at controller
            //initialisation
            scope.$digest();
        }));

        //searching for an existing group
        it('should search for an existing group', function () {

            //test specific spies
            spyOn(GroupMaintenanceService, 'checkGroupExists').andReturn(true);

            //invoke controller function
            scope.searchGroup("administrator");
            scope.$digest();

            //assert function calls were made
            expect(scope.setWidgetState).toHaveBeenCalledWith("MODE_EDIT");
            expect(scope.loadPermissionsForCategory).toHaveBeenCalled();
            expect(GroupMaintenanceService.getGroupDetails).toHaveBeenCalledWith("administrator");

            // test loadUsers has been called
            expect(GroupMaintenanceService.loadUsers).toHaveBeenCalled();

            // test response of loadUsers
            GroupMaintenanceService.loadUsers().then(function(response) {
                expect(response).toEqual([{
                    "firstName": "Lucas System",
                    "lastName": "Lucas System",
                    "skill": null,
                    "userName": "system"
                }, {
                    "firstName": "Admin",
                    "lastName": "User",
                    "skill": null,
                    "userName": "admin123"
                }])
            });
            scope.$digest();

            //assert scope assignments
            expect(scope.fields.groupDescription).toEqual("dummy description for group admin");

            expect(scope.fields.originalAssignedPermissions.length).toEqual(3);
            expect(scope.fields.originalAssignedPermissions[0]).toEqual("authenticated-user");
            expect(scope.fields.originalAssignedPermissions[1]).toEqual("create-group");
            expect(scope.fields.originalAssignedPermissions[2]).toEqual("edit-group");

            expect(scope.fields.tempAssignedPermissions.length).toEqual(3);
            expect(scope.fields.tempAssignedPermissions[0]).toEqual("authenticated-user");
            expect(scope.fields.tempAssignedPermissions[1]).toEqual("create-group");
            expect(scope.fields.tempAssignedPermissions[2]).toEqual("edit-group");

            expect(scope.fields.originalAssignedUsers.length).toEqual(3);
            expect(scope.fields.originalAssignedUsers[0]).toEqual("jack123");
            expect(scope.fields.originalAssignedUsers[1]).toEqual("jill123");
            expect(scope.fields.originalAssignedUsers[2]).toEqual("system");

            expect(scope.fields.tempAssignedUsers.length).toEqual(3);
            expect(scope.fields.tempAssignedUsers[0]).toEqual("jack123");
            expect(scope.fields.tempAssignedUsers[1]).toEqual("jill123");
            expect(scope.fields.tempAssignedUsers[2]).toEqual("system");
        });

        //searching for a group that doesn't exist
        it('should search for a new (non-existing) group', function () {

            //test specific spies
            spyOn(GroupMaintenanceService, 'checkGroupExists').andReturn(false);

            //invoke controller function
            scope.searchGroup("administrator");
            scope.$digest();

            //assert function calls were made
            expect(scope.setWidgetState).toHaveBeenCalledWith("MODE_CREATE");
            expect(scope.loadPermissionsForCategory).toHaveBeenCalled();

            // should not have called function 'getGroupPermissions' since this is a new
            // group, it will not have any
            expect(GroupMaintenanceService.getGroupDetails).not.toHaveBeenCalled();

            //assert scope assignments
            expect(scope.fields.originalAssignedPermissions.length).toEqual(0);
            expect(scope.fields.tempAssignedPermissions.length).toEqual(0);

            expect(scope.fields.originalAssignedUsers.length).toEqual(0);
            expect(scope.fields.tempAssignedUsers.length).toEqual(0);
        });
    });


    //
    // Validating user permissions
    //
    describe('Validating user has permissions to perform widget actions', function() {

        //test setup
        beforeEach(inject(function(_$controller_) {
            //provide controller scope with a 'widgetdetails' mock
            scope.widgetdetails = angular.copy(mockWidgetDetails);

            //inject controller
            controller = _$controller_('GroupMaintenanceFormController', {
                $scope: scope,
                GroupMaintenanceService: GroupMaintenanceService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });

            //digest the scope to resolve any promises requested at controller
            //initialisation
            scope.$digest();
        }));

        //
        it('should correctly identify if user has a permission', function() {
            //setup user so they only have 'edit-user' permission
            scope.permissions = {
                'edit-group' : true,
                'create-group' : false
            };

            //invoke controller function and assert it returns correct result
            expect(scope.checkUserPermission('edit-group')).toBeTruthy();
            expect(scope.checkUserPermission('create-group')).toBeFalsy();
            expect(scope.checkUserPermission('delete-group')).toBeFalsy();
        });
    });
    
    
    //
    // Keeps track of the unsaved permission assignment changes
    //
    describe('Tracking permission assignment changes', function () {

        //test setup
        beforeEach(inject(function(_$controller_) {
            //provide controller scope with a 'widgetdetails' mock
            scope.widgetdetails = angular.copy(mockWidgetDetails);

            //inject controller
            controller = _$controller_('GroupMaintenanceFormController', {
                $scope: scope,
                GroupMaintenanceService: GroupMaintenanceService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });

            //spies
            spyOn(GroupMaintenanceService, 'checkPermissionAssignmentChanges')
                .andReturn(["create-group", "edit-group"]);

            //digest the scope to resolve any promises requested at controller
            //initialisation
            scope.$digest();
        }));

        //should update tempAssignmentPermission with latest assignment changes
        it('should persist assignment changes and store in scope variable tempAssignmentPermissions ', function () {
            //invoke controller function
            scope.persistUnsavedAssignmentChanges();

            //assert results
            expect(GroupMaintenanceService.checkPermissionAssignmentChanges).toHaveBeenCalled();

            //check that scope variable have been updated correctly with response from server call
            expect(scope.fields.tempAssignedPermissions.length).toEqual(2);
            expect(scope.fields.tempAssignedPermissions[0]).toEqual("create-group");
            expect(scope.fields.tempAssignedPermissions[1]).toEqual("edit-group");
        });
    });


    //
    // populates the available permissions and assigned permissions
    // based on the selected category
    //
    describe('Getting the Available and Assigned group permissions for select category', function () {

        var availablePermissions = null;
        var assignedPermissions = null;

        //test setup
        beforeEach(inject(function(_$controller_, _$q_) {

            //deferred executions
            availablePermissions = _$q_.defer();
            assignedPermissions = _$q_.defer();

            //resolve promises
            availablePermissions.resolve(["edit-group", "authenticated-user"]);
            assignedPermissions.resolve(["create-group", "group-maintenance-widget-access"]);

                    //provide controller scope with a 'widgetdetails' mock
            scope.widgetdetails = angular.copy(mockWidgetDetails);

            //inject controller
            controller = _$controller_('GroupMaintenanceFormController', {
                $scope: scope,
                GroupMaintenanceService: GroupMaintenanceService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });

            //spies
            spyOn(GroupMaintenanceService, 'getAvailablePermissionsForCategory').andReturn(availablePermissions.promise);
            spyOn(GroupMaintenanceService, 'getAssignedPermissionsForCategory').andReturn(assignedPermissions.promise);
            spyOn(scope, 'persistUnsavedAssignmentChanges').andReturn();

            //digest the scope to resolve any promises requested at controller
            //initialisation
            scope.$digest();
        }));

        //should get and set available and assigned permissions for selected category
        it('should update scope variables with Available and Assigned permissions for selected category', function () {
            //invoke controller function
            scope.loadPermissionsForCategory("group-management");
            scope.$digest();

            //assert scope and service calls were made
            expect(scope.persistUnsavedAssignmentChanges).toHaveBeenCalled();
            expect(GroupMaintenanceService.getAvailablePermissionsForCategory).toHaveBeenCalled();
            expect(GroupMaintenanceService.getAssignedPermissionsForCategory).toHaveBeenCalled();

            //assert scope variables have been updates
            expect(scope.widgetdetails.availableItems.length).toEqual(2);
            expect(scope.widgetdetails.availableItems[0]).toEqual("edit-group");
            expect(scope.widgetdetails.availableItems[1]).toEqual("authenticated-user");

            expect(scope.widgetdetails.existingItems.length).toEqual(2);
            expect(scope.widgetdetails.existingItems[0]).toEqual("create-group");
            expect(scope.widgetdetails.existingItems[1]).toEqual("group-maintenance-widget-access");
        });

    });


    //
    // Copying a group
    //
    describe('Copying a group', function() {

        //Mock the modalInstance object that is returned from the .open call
        var mockModalInstance = {
            result: {
                then: function (confirmCallback, cancelCallback) {
                    //Store the callbacks for later when the user clicks on the OK or Cancel button of the dialog
                    this.confirmCallBack = confirmCallback;
                    this.cancelCallback = cancelCallback;
                }
            },
            close: function (item) {
                //The user clicked OK on the modal dialog, call the stored confirm callback with the selected item
                this.result.confirmCallBack(item);
            },
            dismiss: function (type) {
                //The user clicked cancel on the modal dialog, call the stored cancel callback
                this.result.cancelCallback(type);
            }
        };

        //deferred execution used for mocking the save functionality
        var savingGroup = null;

        //test setup
        beforeEach(inject(function(_$controller_, _$q_) {

            //deferred exec
            savingGroup = _$q_.defer();

            //provide controller scope with a 'widgetdetails' mock
            scope.widgetdetails = angular.copy(mockWidgetDetails);

            //inject controller
            controller = _$controller_('GroupMaintenanceFormController', {
                $scope: scope,
                GroupMaintenanceService: GroupMaintenanceService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });

            //setup spies
            spyOn(modal, 'open').andReturn(mockModalInstance);
            spyOn(scope, 'save').andReturn(savingGroup.promise);
            spyOn(scope, 'setWidgetState').andReturn();

            //digest the scope to resolve any promises requested at controller
            //initialisation
            scope.$digest();
        }));

        //should open model promoting for new group
        it('should open modal to enter new Group Name', function() {
            //invoke controller function
            scope.copy();
            expect(modal.open).toHaveBeenCalled();
        });

        //should save the Group when pressing save button in modal
        it('should copy the Group', function() {

            var groupObj = {
                "groupName" : "my new group",
                "groupDesc" : "this is a new group created for testing"
            };

            //invoke the controller function
            scope.copy();

            //invoke close method (save) on modal
            scope.modalInstance.close(groupObj);

            //resolve the promise for successfully saving the group
            savingGroup.resolve();
            scope.$digest();

            //assert results
            expect(scope.save).toHaveBeenCalledWith("my new group");
            expect(scope.fields.groupName).toEqual("my new group");
            expect(scope.fields.tempGroupDescription).toEqual("this is a new group created for testing");
            expect(scope.setWidgetState).toHaveBeenCalledWith("MODE_EDIT");
        });


        //should handle when there is a problem saving the groupl
        it('should handle when copying group fails', function() {

            var groupObj = {
                "groupName" : "my new group",
                "groupDesc" : "this is a new group created for testing"
            };

            //invoke the controller function
            scope.copy();

            //invoke close method (save) on modal
            scope.modalInstance.close(groupObj);

            //REJECT the promise for unsuccessfully save
            savingGroup.reject();
            scope.$digest();

            //assert results
            expect(scope.save).toHaveBeenCalledWith("my new group");
            expect(scope.fields.groupName).not.toEqual("my new group");
            expect(scope.fields.groupDescription).not.toEqual("this is a new group created for testing");
            expect(scope.setWidgetState).toHaveBeenCalledWith("MODE_EDIT");
        });

        //should not save the Group when pressing cancel button in modal
        it('should not copy the user', function() {
            //invoke the controller function
            scope.copy();

            //invoke close method (save) on modal
            scope.modalInstance.dismiss();

            // assert results
            // should not invoke save function
            expect(scope.save).not.toHaveBeenCalled();
        });
    });

    describe('handle WidgetBroadcast event', function() {
        var rootScope;
        var localStoreService;
        var $q;
        //setup
        beforeEach(inject(function(_$controller_, $injector, _LocalStoreService_, $rootScope, _$q_) {
            $q = _$q_;
            localStoreService = _LocalStoreService_;
            rootScope = $rootScope;
            //inject controller
            scope.widgetdetails = angular.copy(mockWidgetDetails);
            controller = _$controller_('GroupMaintenanceFormController', {
                $scope: scope,
                $injector: $injector,
                GroupMaintenanceService: GroupMaintenanceService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });
        }));

        it('should handle WidgetBroadcast event', function() {
            spyOn(scope, 'resetWidget').andReturn();
            scope.widgetdetails.id = 38;

            var def = $q.defer();
            spyOn(scope, 'searchGroup').andReturn(def.promise);

            var broadcastObj = new BroadcastObject();
            broadcastObj.setWidgetId(37);
            broadcastObj.addDataElement("groupName");
            broadcastObj.addDataValue("groupName", "administrator");

            rootScope.$emit('WidgetBroadcast', broadcastObj);
            expect(scope.resetWidget).toHaveBeenCalled();

            expect(scope.searchGroup).toHaveBeenCalled();
            expect(scope.searchGroup.callCount).toEqual(1);
            expect(scope.searchGroup.mostRecentCall.args[0]).toEqual(["administrator"]);

        });
    });

    describe('handle getWidgetInterationConfiguration() method', function() {
        var localStoreService;
        //setup
        beforeEach(inject(function(_$controller_, $injector, _LocalStoreService_) {

            //provide controller scope with a 'widgetdetails' mock
            scope.widgetdetails = angular.copy(mockWidgetDetails);
            localStoreService = _LocalStoreService_;
            //inject controller
            controller = _$controller_('GroupMaintenanceFormController', {
                $scope: scope,
                $injector: $injector,
                GroupMaintenanceService: GroupMaintenanceService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });
        }));

        it('should test getWidgetInterationConfiguration', function() {
            scope.widgetdetails.id = 38;
            expect(scope.getWidgetInterationConfiguration()).toEqual([{
                widgetInstance: {
                    id: '37',
                    name: 'SearchGroup, 37'
                },
                dataElement: 'groupName',
                active: true
            }]);
        });

    });


    // Ensure form data is persisted when scope is destroyed
    describe('Persisting data when scope if destroyed', function() {

        //setup
        beforeEach(inject(function(_$controller_) {

            // provide controller scope with a 'widgetdetails' mock
            scope.widgetdetails = angular.copy(mockWidgetDetails);

            // set up syp on Utils Service that is to be injected into the controller
            spyOn(UtilsService, 'persistWidgetData').andReturn();

            // inject controller
            controller = _$controller_('GroupMaintenanceFormController', {
                $scope: scope,
                GroupMaintenanceService: GroupMaintenanceService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });
        }));

        //should persist data when scope ifs destroyed
        it('should persist data when scope ifs destroyed', function() {
            //destroy the scope
            scope.$destroy();
            expect(UtilsService.persistWidgetData).toHaveBeenCalled();
        });
    });


    // Ensure form is rendered with persisted data
    describe('Rendering form with persisted data', function() {

        //setup
        beforeEach(inject(function(_$controller_) {

            //provide controller scope with a 'widgetdetails' mock
            scope.widgetdetails = angular.copy(mockWidgetDetails);

            //set up syp on Utils Service that is to be injected into the controller
            spyOn(UtilsService, 'getPersistedWidgetData').andReturn({
                "originalValues": {
                    "fields": {
                        "categories": {
                            "selected": "EAI",
                            "available": ["EAI", "Equipment", "Groups", "Users", "canvas", "data-export", "product-management", "user-management", "widget-permissions"]
                        },
                        "groupName": "administrator",
                        "originalAssignedPermissions": ["authenticated-user", "create-user", "edit-user", "create-group", "create-canvas", "delete-canvas", "group-maintenance-widget-access", "search-user-widget-access", "create-edit-user-widget-access"],
                        "tempAssignedPermissions": ["authenticated-user", "create-user", "edit-user", "create-group", "create-canvas", "delete-canvas", "group-maintenance-widget-access", "search-user-widget-access", "create-edit-user-widget-access"],
                        "originalAssignedUsers": ["jack123", "jill123", "system"],
                        "tempAssignedUsers": ["jack123", "jill123", "system"],
                        "groupDescription": "dummy description for group administrator",
                        "tempGroupDescription": "dummy description for group administrator"
                    }
                },
                "fields": {
                    "categories": {
                        "selected": "EAI",
                        "available": ["EAI", "Equipment", "Groups", "Users", "canvas", "data-export", "product-management", "user-management", "widget-permissions"]
                    },
                    "groupName": "administrator",
                    "originalAssignedPermissions": ["authenticated-user", "create-user", "edit-user", "create-group", "create-canvas", "delete-canvas", "group-maintenance-widget-access", "search-user-widget-access", "create-edit-user-widget-access"],
                    "tempAssignedPermissions": ["authenticated-user", "create-user", "edit-user", "create-group", "create-canvas", "delete-canvas", "group-maintenance-widget-access", "search-user-widget-access", "create-edit-user-widget-access"],
                    "originalAssignedUsers": ["jack123", "jill123", "system"],
                    "tempAssignedUsers": ["jack123", "jill123", "system"],
                    "groupDescription": "dummy description for group administrator",
                    "tempGroupDescription": "dummy description for group administrator",
                    "originalEnrolledUsers": [{
                        "lastName": "User2",
                        "skill": null,
                        "userName": "jack123",
                        "firstName": "Jack"
                    }, {
                        "lastName": "User1",
                        "skill": null,
                        "userName": "jill123",
                        "firstName": "Jill"
                    }, {
                        "lastName": "Lucas System",
                        "skill": null,
                        "userName": "system",
                        "firstName": "Lucas System"
                    }],
                    "tempEnrolledUsers": [{
                        "lastName": "User2",
                        "skill": null,
                        "userName": "jack123",
                        "firstName": "Jack",
                        "$$hashKey": "uiGrid-0BH"
                    }, {
                        "lastName": "User1",
                        "skill": null,
                        "userName": "jill123",
                        "firstName": "Jill",
                        "$$hashKey": "uiGrid-0BJ"
                    }, {
                        "lastName": "Lucas System",
                        "skill": null,
                        "userName": "system",
                        "firstName": "Lucas System",
                        "$$hashKey": "uiGrid-0BL"
                    }]
                },
                "availableItems": ["message-segments-widget-access", "create-message-segment", "edit-message-segment", "delete-message-segment"],
                "existingItems": [],
                "selectedAvailableItems": [],
                "selectedExistingItems": [],
                "availableGridItems": [{
                    "lastName": "Lucas System",
                    "skill": null,
                    "userName": "system",
                    "firstName": "Lucas System"
                }, {
                    "lastName": "User",
                    "skill": null,
                    "userName": "admin123",
                    "firstName": "Admin",
                    "$$hashKey": "uiGrid-00F"
                }, {
                    "lastName": "User1",
                    "skill": null,
                    "userName": "jill123",
                    "firstName": "Jill"
                }, {
                    "lastName": "User2",
                    "skill": null,
                    "userName": "jack123",
                    "firstName": "Jack"
                }, {
                    "lastName": "Lucas-Design-System",
                    "skill": "ADVANCED",
                    "userName": "design",
                    "firstName": "Lucas-Design-System",
                    "$$hashKey": "uiGrid-00H"
                }, {
                    "lastName": "User3",
                    "skill": null,
                    "userName": "joe123",
                    "firstName": "Joe",
                    "$$hashKey": "uiGrid-00J"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username6",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-00L"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username7",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-00N"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username8",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-00P"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username9",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-00R"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username10",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-00T"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username11",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-00V"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username12",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-00X"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username13",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-00Z"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username14",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-011"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username15",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-013"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username16",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-015"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username17",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-017"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username18",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-019"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username19",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-01B"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username20",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-01D"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username21",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-01F"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username22",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-01H"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username23",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-01J"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username24",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-01L"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username25",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-01N"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username26",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-01P"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username27",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-01R"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username28",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-01T"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username29",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-01V"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username30",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-01X"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username31",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-01Z"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username32",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-021"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username33",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-023"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username34",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-025"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username35",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-027"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username36",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-029"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username37",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-02B"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username38",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-02D"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username39",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-02F"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username40",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-02H"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username41",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-02J"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username42",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-02L"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username43",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-02N"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username44",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-02P"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username45",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-02R"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username46",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-02T"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username47",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-02V"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username48",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-02X"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username49",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-02Z"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username50",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-031"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username51",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-033"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username52",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-035"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username53",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-037"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username54",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-039"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username55",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-03B"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username56",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-03D"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username57",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-03F"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username58",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-03H"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username59",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-03J"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username60",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-03L"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username61",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-03N"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username62",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-03P"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username63",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-03R"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username64",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-03T"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username65",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-03V"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username66",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-03X"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username67",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-03Z"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username68",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-041"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username69",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-043"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username70",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-045"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username71",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-047"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username72",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-049"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username73",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-04B"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username74",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-04D"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username75",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-04F"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username76",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-04H"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username77",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-04J"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username78",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-04L"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username79",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-04N"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username80",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-04P"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username81",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-04R"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username82",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-04T"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username83",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-04V"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username84",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-04X"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username85",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-04Z"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username86",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-051"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username87",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-053"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username88",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-055"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username89",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-057"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username90",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-059"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username91",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-05B"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username92",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-05D"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username93",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-05F"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username94",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-05H"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username95",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-05J"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username96",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-05L"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username97",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-05N"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username98",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-05P"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username99",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-05R"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username100",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-05T"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username101",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-05V"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username102",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-05X"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username103",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-05Z"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username104",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-061"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username105",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-063"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username106",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-065"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username107",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-067"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username108",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-069"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username109",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-06B"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username110",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-06D"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username111",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-06F"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username112",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-06H"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username113",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-06J"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username114",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-06L"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username115",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-06N"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username116",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-06P"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username117",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-06R"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username118",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-06T"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username119",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-06V"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username120",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-06X"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username121",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-06Z"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username122",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-071"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username123",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-073"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username124",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-075"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username125",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-077"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username126",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-079"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username127",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-07B"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username128",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-07D"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username129",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-07F"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username130",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-07H"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username131",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-07J"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username132",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-07L"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username133",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-07N"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username134",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-07P"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username135",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-07R"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username136",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-07T"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username137",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-07V"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username138",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-07X"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username139",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-07Z"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username140",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-081"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username141",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-083"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username142",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-085"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username143",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-087"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username144",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-089"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username145",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-08B"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username146",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-08D"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username147",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-08F"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username148",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-08H"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username149",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-08J"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username150",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-08L"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username151",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-08N"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username152",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-08P"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username153",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-08R"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username154",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-08T"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username155",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-08V"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username156",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-08X"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username157",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-08Z"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username158",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-091"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username159",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-093"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username160",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-095"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username161",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-097"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username162",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-099"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username163",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-09B"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username164",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-09D"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username165",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-09F"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username166",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-09H"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username167",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-09J"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username168",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-09L"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username169",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-09N"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username170",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-09P"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username171",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-09R"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username172",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-09T"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username173",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-09V"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username174",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-09X"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username175",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-09Z"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username176",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-0A1"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username177",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-0A3"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username178",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-0A5"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username179",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-0A7"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username180",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-0A9"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username181",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-0AB"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username182",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-0AD"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username183",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-0AF"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username184",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-0AH"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username185",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-0AJ"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username186",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-0AL"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username187",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-0AN"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username188",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-0AP"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username189",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-0AR"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username190",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-0AT"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username191",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-0AV"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username192",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-0AX"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username193",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-0AZ"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username194",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-0B1"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username195",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-0B3"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username196",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-0B5"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username197",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-0B7"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username198",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-0B9"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username199",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-0BB"
                }, {
                    "lastName": "dummy-LastName",
                    "skill": null,
                    "userName": "dummy-username200",
                    "firstName": "dummy-firstName",
                    "$$hashKey": "uiGrid-0BD"
                }, {
                    "lastName": "E2E",
                    "skill": null,
                    "userName": "e2euser",
                    "firstName": "E2E",
                    "$$hashKey": "uiGrid-0BF"
                }],
                "existingGridItems": [{
                    "lastName": "User2",
                    "skill": null,
                    "userName": "jack123",
                    "firstName": "Jack",
                    "$$hashKey": "uiGrid-0BH"
                }, {
                    "lastName": "User1",
                    "skill": null,
                    "userName": "jill123",
                    "firstName": "Jill",
                    "$$hashKey": "uiGrid-0BJ"
                }, {
                    "lastName": "Lucas System",
                    "skill": null,
                    "userName": "system",
                    "firstName": "Lucas System",
                    "$$hashKey": "uiGrid-0BL"
                }],
                "selectedAvailableGridItems": [],
                "selectedExistingGridItems": [],
                "formState": "MODE_EDIT",
                "activeTab": 0
            });

            // inject controller
            controller = _$controller_('GroupMaintenanceFormController', {
                $scope: scope,
                GroupMaintenanceService: GroupMaintenanceService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });

            spyOn(scope, 'setWidgetState').andReturn();

            //invoke controller function
            scope.init();
        }));

        //should initialise form using persisted data
        it('should initialise the form using persisted data', function() {

            expect(UtilsService.getPersistedWidgetData).toHaveBeenCalled();

            expect(scope.setWidgetState).toHaveBeenCalledWith("MODE_EDIT");

            expect(scope.fields.categories.selected).toEqual("EAI");

            expect(scope.widgetdetails.availableItems.length).toEqual(4);
            expect(scope.widgetdetails.availableItems[0]).toEqual("message-segments-widget-access");

            expect(scope.widgetdetails.existingItems.length).toEqual(0);

            expect(scope.widgetgriddetails.availableItems.length).toEqual(202);
            expect(scope.widgetgriddetails.availableItems[0]).toEqual({
                lastName: 'Lucas System',
                skill: null,
                userName: 'system',
                firstName: 'Lucas System'
            });

            expect(scope.widgetgriddetails.existingItems.length).toEqual(3);
            expect(scope.widgetgriddetails.existingItems[0]).toEqual({
                lastName: 'User2',
                skill: null,
                userName: 'jack123',
                firstName: 'Jack',
                $$hashKey: 'uiGrid-0BH'
            });

        });
    });

});