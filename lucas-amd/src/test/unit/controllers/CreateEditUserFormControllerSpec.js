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

describe('CreateEditUserForm  Controller Tests', function() {

    var scope = null;
    var controller = null;
    var LocalStoreService = null;
    var UtilsService = null;
    var WidgetDefinitionService = null;
    var CreateEditUserFormService = null;
    var modal = null;

    var mockWidgetDetails = null;

    //deferred promises for unit testing API calls
    var usernames = null;
    var languages = null;
    var shifts = null;
    var skills = null;
    var groups = null;

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
    beforeEach(inject(function (_$rootScope_, _$compile_, _CreateEditUserFormService_, _LocalStoreService_, _UtilsService_, _WidgetDefinitionService_, _$q_, _$modal_) {
        //initialise dependencies
        scope = _$rootScope_.$new();
        CreateEditUserFormService = _CreateEditUserFormService_;
        WidgetDefinitionService = _WidgetDefinitionService_;
        LocalStoreService = _LocalStoreService_;
        UtilsService = _UtilsService_;
        modal = _$modal_;

        var element = _$compile_('<ng-form name="form">'+
            '<input type="password" id="password" name="password" ng-model="password">'+
            '<input type="password" ng-model="confirm_password" id="confirm_password" name="confirm_password" pwmatch="true" pwcheck="password">'+
            '</ng-form>')(scope);


        //skeleton mock widgetInstance
        mockWidgetDetails = {
            "id": null,
            "clientId": 100,
            "widgetDefinition": {
                "id": 8,
                "name": "create-or-edit-user-form-widget",
                "definitionData": {
                    "User": [
                        {"startDate": "2014-10-29T15:51:02.365Z"}
                    ]
                },
                "defaultViewConfig": {
                    "dateFormat": {
                        "selectedFormat": "mm-dd-yyyy",
                        "availableFormats": [
                            "mm-dd-yyyy",
                            "MMM dd, yyyy",
                            "dd-mm-yyyy"
                        ]
                    }
                },
                "widgetActionConfig" : {
                    "widget-actions" : {
                        "create-user" : false,
                        "edit-user" : false
                    }
                }
            },
            "actualViewConfig": {
                "height": 240
            }
        };

        //Resolve promises
        usernames = _$q_.defer();
        usernames.resolve([
            "jack123",
            "jill123",
            "joe123"
        ]);
        languages = _$q_.defer();
        languages.resolve(
            {
                amd:["EN_GB", "EN_US"],
                hh:["EN_GB", "EN_US"],
                j2u:["EN_GB", "EN_US"],
                u2j:["EN_GB", "EN_US"]
            }
        );
        shifts = _$q_.defer();
        shifts.resolve([
            {
                shiftId:1,
                shiftName:"day",
                startTime:"9am",
                endTime:"6pm"
            },
            {
                shiftId:2,
                shiftName:"night",
                startTime:"9pm",
                endTime:"6am"
            }
        ]);
        skills = _$q_.defer();
        skills.resolve([
            "standard",
            "advanced"
        ]);
        groups = _$q_.defer();
        groups.resolve([
            "picker",
            "admin",
            "warehouse-manager"
        ]);

        //setup spies for CreateEditUserService mock
        spyOn(CreateEditUserFormService, 'getUsernames').andReturn(usernames.promise);
        spyOn(CreateEditUserFormService, 'getSupportedLanguages').andReturn(languages.promise);
        spyOn(CreateEditUserFormService, 'getAvailableShifts').andReturn(shifts.promise);
        spyOn(CreateEditUserFormService, 'getAvailableSkills').andReturn(skills.promise);
        spyOn(CreateEditUserFormService, 'getAvailableGroups').andReturn(groups.promise);

        //LocalStorage Mock
        spyOn(LocalStoreService, 'getLSItem').andCallFake(function() {
            if (arguments[0] == 'ActiveCanvasId') {
                return 405;
            } else if (arguments[0] == 'FavoriteCanvasListUpdated') {
                return [{
                    "canvasName": "JackCanvas405",
                    "canvasId": 405,
                    "canvasType": "PRIVATE",
                    "displayOrder": 1,
                    "isLoaded": true,
                    "widgetInstanceList": [{
                        "data": null,
                        "id": 29,
                        "actualViewConfig": {
                            "position": 1,
                            "dateFormat": {
                                "selectedFormat": "mm-dd-yyyy",
                                "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                            },
                            "anchor": [1, 363],
                            "zindex": 0,
                            "minimumWidth": 300,
                            "minimumHeight": 480,
                            "orientation": {
                                "option": null,
                                "selected": null
                            },
                            "gridColumns": {
                                "1": {
                                    "allowFilter": true,
                                    "fieldName": "userName",
                                    "name": "Lucas Login",
                                    "order": "1",
                                    "sortOrder": "1",
                                    "width": 150,
                                    "visible": true
                                },
                                "2": {
                                    "allowFilter": true,
                                    "fieldName": "wmsUser",
                                    "name": "Host Login",
                                    "order": "2",
                                    "sortOrder": "1",
                                    "width": 150,
                                    "visible": false
                                },
                                "3": {
                                    "allowFilter": true,
                                    "fieldName": "firstName",
                                    "name": "First Name",
                                    "order": "3",
                                    "sortOrder": "1",
                                    "width": 150,
                                    "visible": true
                                },
                                "4": {
                                    "allowFilter": true,
                                    "fieldName": "lastName",
                                    "name": "Last Name",
                                    "order": "4",
                                    "sortOrder": "1",
                                    "width": 150,
                                    "visible": true
                                },
                                "5": {
                                    "allowFilter": true,
                                    "fieldName": "skill",
                                    "name": "Skill",
                                    "order": "5",
                                    "sortOrder": "1",
                                    "width": 150,
                                    "visible": true
                                },
                                "6": {
                                    "allowFilter": true,
                                    "fieldName": "shift",
                                    "name": "Shifts",
                                    "order": "6",
                                    "sortOrder": "1",
                                    "width": 150,
                                    "visible": true
                                },
                                "7": {
                                    "allowFilter": true,
                                    "fieldName": "j2uLanguage",
                                    "name": "J2U Language",
                                    "order": "7",
                                    "sortOrder": "1",
                                    "width": 150,
                                    "visible": true
                                },
                                "8": {
                                    "allowFilter": true,
                                    "fieldName": "u2jLanguage",
                                    "name": "U2J Language",
                                    "order": "8",
                                    "sortOrder": "1",
                                    "width": 150,
                                    "visible": true
                                },
                                "9": {
                                    "allowFilter": true,
                                    "fieldName": "hhLanguage",
                                    "name": "HH Language",
                                    "order": "9",
                                    "sortOrder": "1",
                                    "width": 150,
                                    "visible": true
                                },
                                "10": {
                                    "allowFilter": true,
                                    "fieldName": "amdLanguage",
                                    "name": "AMD Language",
                                    "order": "10",
                                    "sortOrder": "1",
                                    "width": 150,
                                    "visible": true
                                },
                                "11": {
                                    "allowFilter": true,
                                    "fieldName": "enable",
                                    "name": "Status",
                                    "order": "11",
                                    "sortOrder": "1",
                                    "width": 150,
                                    "visible": true
                                },
                                "12": {
                                    "allowFilter": true,
                                    "fieldName": "employeeId",
                                    "name": "Employee Id",
                                    "order": "12",
                                    "sortOrder": "1",
                                    "width": 150,
                                    "visible": false
                                },
                                "13": {
                                    "allowFilter": true,
                                    "fieldName": "title",
                                    "name": "Title",
                                    "order": "13",
                                    "sortOrder": "1",
                                    "width": 150,
                                    "visible": false
                                },
                                "14": {
                                    "allowFilter": true,
                                    "fieldName": "startDate",
                                    "name": "Start Date",
                                    "order": "14",
                                    "sortOrder": "1",
                                    "width": 150,
                                    "visible": true
                                },
                                "15": {
                                    "allowFilter": true,
                                    "fieldName": "birthDate",
                                    "name": "Birth Date",
                                    "order": "15",
                                    "sortOrder": "1",
                                    "width": 150,
                                    "visible": false
                                },
                                "16": {
                                    "allowFilter": true,
                                    "fieldName": "mobileNumber",
                                    "name": "Mobile Number",
                                    "order": "16",
                                    "sortOrder": "1",
                                    "width": 150,
                                    "visible": false
                                },
                                "17": {
                                    "allowFilter": true,
                                    "fieldName": "emailAddress",
                                    "name": "Email Address",
                                    "order": "17",
                                    "sortOrder": "1",
                                    "width": 150,
                                    "visible": true
                                }
                            },
                            "listensForList": ["Score"],
                            "autoRefreshConfig": {
                                "globalOverride": false,
                                "enabled": true,
                                "interval": 120
                            }
                        },
                        "widgetDefinition": {
                            "name": "search-user-grid-widget",
                            "id": 9,
                            "title": "Search User",
                            "widgetActionConfig": {
                                "widget-access": {
                                    "search-user-widget-access": true
                                },
                                "widget-actions": {
                                    "delete-user": true,
                                    "retrain-voice-model": true,
                                    "disable-user": true,
                                    "enable-user": true
                                }
                            },
                            "shortName": "SearchUser",
                            "dataURL": {
                                "url": "/users/userlist/search",
                                "searchCriteria": {
                                    "pageNumber": "0",
                                    "pageSize": "100",
                                    "searchMap": {},
                                    "sortMap": {}
                                }
                            },
                            "defaultViewConfig": {
                                "dateFormat": {
                                    "selectedFormat": "mm-dd-yyyy",
                                    "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                                },
                                "anchor": [1, 363],
                                "zindex": 1,
                                "minimumWidth": 485,
                                "minimumHeight": 375,
                                "orientation": {
                                    "option": null,
                                    "selected": null
                                },
                                "gridColumns": {
                                    "1": {
                                        "name": "Lucas Login",
                                        "order": "1",
                                        "values": [],
                                        "allowFilter": true,
                                        "fieldName": "userName",
                                        "sortOrder": "1",
                                        "visible": true,
                                        "width": 150
                                    },
                                    "2": {
                                        "name": "Host Login",
                                        "order": "2",
                                        "values": [],
                                        "allowFilter": true,
                                        "fieldName": "wmsUser",
                                        "sortOrder": "1",
                                        "visible": false,
                                        "width": 150
                                    },
                                    "3": {
                                        "name": "First Name",
                                        "order": "3",
                                        "values": [],
                                        "allowFilter": true,
                                        "fieldName": "firstName",
                                        "sortOrder": "1",
                                        "visible": true,
                                        "width": 150
                                    },
                                    "4": {
                                        "name": "Last Name",
                                        "order": "4",
                                        "values": [],
                                        "allowFilter": true,
                                        "fieldName": "lastName",
                                        "sortOrder": "1",
                                        "visible": true,
                                        "width": 150
                                    },
                                    "5": {
                                        "name": "Skill",
                                        "order": "5",
                                        "values": [],
                                        "allowFilter": true,
                                        "fieldName": "skill",
                                        "sortOrder": "1",
                                        "visible": true,
                                        "width": 150
                                    },
                                    "6": {
                                        "name": "Shifts",
                                        "order": "6",
                                        "values": [],
                                        "allowFilter": true,
                                        "fieldName": "shift",
                                        "sortOrder": "1",
                                        "visible": true,
                                        "width": 150
                                    },
                                    "7": {
                                        "name": "J2U Language",
                                        "order": "7",
                                        "values": [],
                                        "allowFilter": true,
                                        "fieldName": "j2uLanguage",
                                        "sortOrder": "1",
                                        "visible": true,
                                        "width": 150
                                    },
                                    "8": {
                                        "name": "U2J Language",
                                        "order": "8",
                                        "values": [],
                                        "allowFilter": true,
                                        "fieldName": "u2jLanguage",
                                        "sortOrder": "1",
                                        "visible": true,
                                        "width": 150
                                    },
                                    "9": {
                                        "name": "HH Language",
                                        "order": "9",
                                        "values": [],
                                        "allowFilter": true,
                                        "fieldName": "hhLanguage",
                                        "sortOrder": "1",
                                        "visible": true,
                                        "width": 150
                                    },
                                    "10": {
                                        "name": "AMD Language",
                                        "order": "10",
                                        "values": [],
                                        "allowFilter": true,
                                        "fieldName": "amdLanguage",
                                        "sortOrder": "1",
                                        "visible": true,
                                        "width": 150
                                    },
                                    "11": {
                                        "name": "Status",
                                        "order": "11",
                                        "values": [],
                                        "allowFilter": true,
                                        "fieldName": "enable",
                                        "sortOrder": "1",
                                        "visible": true,
                                        "width": 150
                                    },
                                    "12": {
                                        "name": "Employee Id",
                                        "order": "12",
                                        "values": [],
                                        "allowFilter": true,
                                        "fieldName": "employeeId",
                                        "sortOrder": "1",
                                        "visible": false,
                                        "width": 150
                                    },
                                    "13": {
                                        "name": "Title",
                                        "order": "13",
                                        "values": [],
                                        "allowFilter": true,
                                        "fieldName": "title",
                                        "sortOrder": "1",
                                        "visible": false,
                                        "width": 150
                                    },
                                    "14": {
                                        "name": "Start Date",
                                        "order": "14",
                                        "values": [],
                                        "allowFilter": true,
                                        "fieldName": "startDate",
                                        "sortOrder": "1",
                                        "visible": true,
                                        "width": 150
                                    },
                                    "15": {
                                        "name": "Birth Date",
                                        "order": "15",
                                        "values": [],
                                        "allowFilter": true,
                                        "fieldName": "birthDate",
                                        "sortOrder": "1",
                                        "visible": false,
                                        "width": 150
                                    },
                                    "16": {
                                        "name": "Mobile Number",
                                        "order": "16",
                                        "values": [],
                                        "allowFilter": true,
                                        "fieldName": "mobileNumber",
                                        "sortOrder": "1",
                                        "visible": false,
                                        "width": 150
                                    },
                                    "17": {
                                        "name": "Email Address",
                                        "order": "17",
                                        "values": [],
                                        "allowFilter": true,
                                        "fieldName": "emailAddress",
                                        "sortOrder": "1",
                                        "visible": true,
                                        "width": 150
                                    }
                                },
                                "isMaximized": null,
                                "listensForList": ["userName"],
                                "deviceWidths": {
                                    "320": "mobile",
                                    "600": "tablet",
                                    "800": "desktop",
                                    "1200": "wideScreen"
                                },
                                "autoRefreshConfig": {
                                    "globalOverride": false,
                                    "enabled": true,
                                    "interval": 120
                                }
                            },
                            "broadcastList": ["userName"],
                            "reactToList": ["userName"],
                            "defaultData": {}
                        },
                        "dataURL": {
                            "url": "/users/userlist/search",
                            "searchCriteria": {
                                "pageNumber": "0",
                                "pageSize": "100",
                                "searchMap": {},
                                "sortMap": {}
                            }
                        },
                        "widgetInteractionConfig": [],
                        "clientId": 100
                    }, {
                        "data": null,
                        "id": 30,
                        "actualViewConfig": {
                            "position": 2,
                            "dateFormat": {
                                "selectedFormat": "mm-dd-yyyy",
                                "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                            },
                            "anchor": [275, 295],
                            "zindex": 0,
                            "minimumWidth": 300,
                            "minimumHeight": 480,
                            "orientation": {
                                "option": null,
                                "selected": null
                            },
                            "isMaximized": false,
                            "listensForList": ["userName", "Wave", "Score"],
                            "autoRefreshConfig": {
                                "globalOverride": false,
                                "enabled": false,
                                "interval": 0
                            }
                        },
                        "widgetDefinition": {
                            "id": 8,
                            "name": "create-or-edit-user-form-widget",
                            "shortName": "CreateUser",
                            "widgetActionConfig": {
                                "widget-access": {
                                    "create-edit-user-widget-access": true
                                },
                                "widget-actions": {
                                    "edit-user": true,
                                    "create-user": true
                                }
                            },
                            "definitionData": {
                                "User": [null]
                            },
                            "defaultViewConfig": {
                                "dateFormat": {
                                    "selectedFormat": "mm-dd-yyyy",
                                    "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                                },
                                "anchor": [275, 295],
                                "zindex": 1,
                                "minimumWidth": 567,
                                "minimumHeight": 240,
                                "orientation": {
                                    "option": null,
                                    "selected": null
                                },
                                "gridColumns": {},
                                "isMaximized": false,
                                "listensForList": ["userName"],
                                "deviceWidths": {
                                    "320": "mobile",
                                    "540": "tablet",
                                    "800": "desktop",
                                    "1200": "wideScreen"
                                },
                                "autoRefreshConfig": {
                                    "globalOverride": false,
                                    "enabled": false,
                                    "interval": 0
                                }
                            },
                            "broadcastList": ["userName"],
                            "reactToList": ["userName"]
                        },
                        "dataURL": null,
                        "widgetInteractionConfig": [{
                                "active": true,
                                "widgetInstance": {
                                    "id": 29,
                                    "name": "SearchUser, 29"
                                },
                                "dataElement": "userName"
                            }],
                        "clientId": 101
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
        beforeEach(inject(function (_$controller_, _LocalStoreService_) {
            scope.widgetdetails = angular.copy(mockWidgetDetails);

            controller = _$controller_('CreateEditUserFormController', {
                $scope: scope,
                CreateEditUserFormService: CreateEditUserFormService,
                WidgetDefinitionService: WidgetDefinitionService,
                LocalStoreService: _LocalStoreService_,
                UtilsService: UtilsService
            });
        }));

        it('should inject controller with dependencies', function () {
            expect(controller).toBeDefined();
            expect(scope).toBeDefined();
            expect(CreateEditUserFormService).toBeDefined();
            expect(WidgetDefinitionService).toBeDefined();
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
            controller = _$controller_('CreateEditUserFormController', {
                $scope: scope,
                CreateEditUserFormService: CreateEditUserFormService,
                WidgetDefinitionService: WidgetDefinitionService,
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
                expect(scope.tabs.length).toEqual(4);
                expect(scope.tabs[0]).toBeTruthy(); //first tab is selected
                expect(scope.tabs[1]).toBeFalsy();
                expect(scope.tabs[2]).toBeFalsy();
                expect(scope.tabs[3]).toBeFalsy();
            });
        });
    });


    //
    // Ensure form is rendered with persisted data
    //
    describe('Rendering form with persisted data', function() {

        //setup
        beforeEach(inject(function(_$controller_) {

            //provide controller scope with a 'widgetdetails' mock
            scope.widgetdetails = angular.copy(mockWidgetDetails);

            //set up syp on Utils Service that is to be injected into the controller
            spyOn(UtilsService, 'getPersistedWidgetData').andReturn({
                "formState" : "MODE_EDIT",
                "activeTab" : 1,
                "originalValues" : "",
                "fields" : {
                    "username" : "jack123"
                },
                "availableGroups" : ["warehouse-manager"],
                "assignedGroups" : ["picker"]
            });

            //inject controller
            controller = _$controller_('CreateEditUserFormController', {
                $scope: scope,
                CreateEditUserFormService: CreateEditUserFormService,
                WidgetDefinitionService: WidgetDefinitionService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });

            spyOn(scope, 'setFormState').andReturn();

            //invoke controller function
            scope.init();
        }));

        //should initialise form using persisted data
        it('should initialise the form using persisted data', function() {

            expect(UtilsService.getPersistedWidgetData).toHaveBeenCalled();

            expect(scope.setFormState).toHaveBeenCalledWith("MODE_EDIT");

            expect(scope.fields.username).toEqual("jack123");

            expect(scope.widgetdetails.availableItems.length).toEqual(1);
            expect(scope.widgetdetails.availableItems[0]).toEqual("warehouse-manager");

            expect(scope.widgetdetails.existingItems.length).toEqual(1);
            expect(scope.widgetdetails.existingItems[0]).toEqual("picker");
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
            controller = _$controller_('CreateEditUserFormController', {
                $scope: scope,
                CreateEditUserFormService: CreateEditUserFormService,
                WidgetDefinitionService: WidgetDefinitionService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });
        }));

        //should load and assign static data to scope
        describe('loading users, languages, skills, shifts, groups', function() {

            //users
            it('should load and cache list of users', function() {
                //required to resolve the promise
                scope.$digest();

                expect(CreateEditUserFormService.getUsernames).toHaveBeenCalled();
                expect(scope.users.length).toBeGreaterThan(0);
                expect(scope.users).toContain('jack123');
            });

            //languages
            it('should load and cache supported languages', function() {
                //required to resolve the promise
                scope.$digest();

                expect(CreateEditUserFormService.getSupportedLanguages).toHaveBeenCalled();
                expect(scope.languages).toBeDefined();

                expect(scope.languages.hasOwnProperty('amd')).toBeTruthy();
                expect(scope.languages.amd.length).toBeGreaterThan(0);
                expect(scope.languages.amd).toContain('EN_GB');

                expect(scope.languages.hasOwnProperty('hh')).toBeTruthy();
                expect(scope.languages.hh.length).toBeGreaterThan(0);
                expect(scope.languages.hh).toContain('EN_GB');

                expect(scope.languages.hasOwnProperty('j2u')).toBeTruthy();
                expect(scope.languages.j2u.length).toBeGreaterThan(0);
                expect(scope.languages.j2u).toContain('EN_GB');

                expect(scope.languages.hasOwnProperty('u2j')).toBeTruthy();
                expect(scope.languages.u2j.length).toBeGreaterThan(0);
                expect(scope.languages.u2j).toContain('EN_GB');
            });
        });

        //shifts
        it('should load and cache list of shifts', function() {
            //required to resolve the promise
            scope.$digest();

            expect(CreateEditUserFormService.getAvailableShifts).toHaveBeenCalled();
            expect(scope.shifts.length).toBeGreaterThan(0);
        });

        //skills
        it('should load and cache list of skills', function() {
            //required to resolve the promise
            scope.$digest();

            expect(CreateEditUserFormService.getAvailableSkills).toHaveBeenCalled();
            expect(scope.skills.length).toBeGreaterThan(0);
        });

        //groups
        it('should load and cache list of available groups', function() {
            //required to resolve the promise
            scope.$digest();

            expect(CreateEditUserFormService.getAvailableGroups).toHaveBeenCalled();
            expect(scope.groups.length).toBeGreaterThan(0);
        });
    });


    //
    // Monitoring the Lucas Login field
    //
    describe('Monitoring the Lucas Login field to enable searching for a user', function() {

        //setup
        beforeEach(inject(function(_$controller_) {

            //provide controller scope with a 'widgetdetails' mock
            scope.widgetdetails = angular.copy(mockWidgetDetails);

            //inject controller
            controller = _$controller_('CreateEditUserFormController', {
                $scope: scope,
                CreateEditUserFormService: CreateEditUserFormService,
                WidgetDefinitionService: WidgetDefinitionService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });

            //invoke the controller function
            scope.$digest();
        }));

        //Enabling the search button only when a value has been entered into Lucas Login field
        it('should enable the search button only when a value is present in the Lucas Login field', function() {
            //search button disabled by default
            expect(scope.searchButtonEnabled).toBeFalsy();

            //Set value in username model (Lucas Login)
            scope.fields.username = "jack123";

            //invoke the watcher function
            scope.lucasLoginWatcher();

            //assert search button becomes enabled
            expect(scope.searchButtonEnabled).toBeTruthy();
        });

        //Search button should not become enabled if a Lucas Login has not been specified
        it('should not enabled the search button when a value is not present in Lucas Login field', function() {
            //search button disabled by default
            expect(scope.searchButtonEnabled).toBeFalsy();

            //Set value in username model (Lucas Login) to empty
            scope.fields.username = "";

            //invoke the watcher function
            scope.lucasLoginWatcher();

            //assert search button becomes enabled
            expect(scope.searchButtonEnabled).toBeFalsy();
        });

        //Enter key should allow search only when a value has been entered into Lucas Login field
        it('should allow Enter Key search when a Lucas Login has been specified', function() {
            var event = {
                "keyCode" : 13
            };

            //Spy on searchUser function so we can check it was called or not
            spyOn(scope, 'searchUser').andReturn();

            //ensure lucas login has NOT been specified
            scope.fields.username = "";

            //invoke enterKey watcher function
            scope.enterKeySearch(event);

            //Assert the dsearch user function was not called
            expect(scope.searchUser).not.toHaveBeenCalled();

            //specify a Lucas Login
            scope.fields.username = "jack123";

            //invoke the enterKey watcher function
            scope.enterKeySearch(event);

            //Assert search user was called
            expect(scope.searchUser).toHaveBeenCalled();
        });

        it('handle searchUser for MultiEditMode', function() {
            scope.usernameList = ['jack123', 'jill123'];
            spyOn(scope, 'getMultiUsersDetails').andReturn();
            scope.searchUser(['jack123', 'jill123'])
            expect(scope.getMultiUsersDetails).toHaveBeenCalled();
            expect(scope.fields.username).toEqual('jack123, jill123');
        });
    });


    //
    // Rendering the form in different states
    //
    describe('Rendering the form in different states', function() {

        //setup
        beforeEach(inject(function(_$controller_) {

            //provide controller scope with a 'widgetdetails' mock
            scope.widgetdetails = angular.copy(mockWidgetDetails);

            //inject controller
            controller = _$controller_('CreateEditUserFormController', {
                $scope: scope,
                CreateEditUserFormService: CreateEditUserFormService,
                WidgetDefinitionService: WidgetDefinitionService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });

            //invoke the controller function
            scope.$digest();
        }));

        //Rendering form in SEARCH mode
        it('should render form in SEARCH mode', function() {
            //invoke controller function and assert results
            scope.setFormState('MODE_SEARCH');

            expect(scope.MODE_SEARCH).toBeTruthy();
            expect(scope.lucasLoginEnabled).toBeTruthy();
            expect(scope.searchButtonEnabled).toBeFalsy();
            expect(scope.searchButtonVisible).toBeTruthy();
            expect(scope.clearButtonEnabled).toBeFalsy();
            expect(scope.clearButtonVisible).toBeFalsy();
            expect(scope.formFieldsEnabled).toBeFalsy();
            expect(scope.saveButtonEnabled).toBeFalsy();
            expect(scope.saveAsButtonEnabled).toBeFalsy();
            expect(scope.tabs[0]).toBeTruthy();
            expect(scope.userNotFound).toBeFalsy();
        });

        //Rendering form in EDIT mode
        it('should render form in EDIT mode', function() {
            //Mock user permissions
            scope.permissions = {
                "create-user" : false,
                "edit-user" : true
            };

            //invoke controller function and assert results
            scope.setFormState('MODE_EDIT');

            expect(scope.MODE_EDIT).toBeTruthy();
            expect(scope.lucasLoginEnabled).toBeFalsy();
            expect(scope.searchButtonEnabled).toBeFalsy();
            expect(scope.searchButtonVisible).toBeFalsy();
            expect(scope.clearButtonEnabled).toBeTruthy();
            expect(scope.clearButtonVisible).toBeTruthy();
            //manage template controls based on user permissions
            expect(scope.formFieldsEnabled).toBeTruthy();
            expect(scope.saveButtonEnabled).toBeTruthy();
            expect(scope.saveAsButtonEnabled).toBeFalsy();
        });

        //Rendering form in CREATE mode (with 'create-user' permission)
        it('should render form in CREATE mode', function() {
            //Mock user permissions
            scope.permissions = {
                "create-user" : true,
                "edit-user" : true
            };

            //invoke controller function and assert results
            scope.setFormState('MODE_CREATE');

            expect(scope.MODE_CREATE).toBeTruthy();
            expect(scope.lucasLoginEnabled).toBeFalsy();
            expect(scope.searchButtonEnabled).toBeFalsy();
            expect(scope.searchButtonVisible).toBeFalsy();
            expect(scope.clearButtonEnabled).toBeTruthy();
            expect(scope.clearButtonVisible).toBeTruthy();
            expect(scope.formFieldsEnabled).toBeTruthy();
            expect(scope.saveButtonEnabled).toBeTruthy();
            expect(scope.saveAsButtonEnabled).toBeFalsy();
        });

        //Rendering form in CREATE mode (without 'create-user' permission)
        it('should render form in CREATE mode', function() {
            //Mock user permissions
            scope.permissions = {
                "create-user" : false,
                "edit-user" : true
            };

            //invoke controller function and assert results
            scope.setFormState('MODE_CREATE');

            expect(scope.MODE_CREATE).toBeTruthy();
            expect(scope.lucasLoginEnabled).toBeFalsy();
            expect(scope.searchButtonEnabled).toBeFalsy();
            expect(scope.searchButtonVisible).toBeFalsy();
            expect(scope.clearButtonEnabled).toBeTruthy();
            expect(scope.clearButtonVisible).toBeTruthy();
            expect(scope.formFieldsEnabled).toBeFalsy();
            expect(scope.saveButtonEnabled).toBeFalsy();
            expect(scope.saveAsButtonEnabled).toBeFalsy();
            expect(scope.userNotFound).toBeTruthy();
        });

        //Rendering form in SAVE mode
        it('should render form in SAVE mode', function() {
            //invoke controller function and assert results
            scope.setFormState('MODE_SAVE');

            expect(scope.MODE_SAVE).toBeTruthy();
            expect(scope.lucasLoginEnabled).toBeFalsy();
            expect(scope.searchButtonEnabled).toBeFalsy();
            expect(scope.searchButtonVisible).toBeFalsy();
            expect(scope.clearButtonEnabled).toBeFalsy();
            expect(scope.clearButtonVisible).toBeTruthy();
            expect(scope.formFieldsEnabled).toBeFalsy();
            expect(scope.saveButtonEnabled).toBeFalsy();
            expect(scope.saveAsButtonEnabled).toBeFalsy();
        });
    });


    //
    // Resetting the form
    //
    describe('Resetting the form', function() {

        //setup
        beforeEach(inject(function(_$controller_) {

            //provide controller scope with a 'widgetdetails' mock
            scope.widgetdetails = angular.copy(mockWidgetDetails);

            //inject controller
            controller = _$controller_('CreateEditUserFormController', {
                $scope: scope,
                CreateEditUserFormService: CreateEditUserFormService,
                WidgetDefinitionService: WidgetDefinitionService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });

            //invoke the controller function
            scope.$digest();
        }));

        //unit test resetForm controller function
        describe('Function: resetForm()', function() {
            //setup
            beforeEach(inject(function(_$controller_, _$compile_) {

                //provide controller scope with a 'widgetdetails' mock
                scope.widgetdetails = angular.copy(mockWidgetDetails);

                //inject controller
                controller = _$controller_('CreateEditUserFormController', {
                    $scope: scope,
                    CreateEditUserFormService: CreateEditUserFormService,
                    WidgetDefinitionService: WidgetDefinitionService,
                    LocalStoreService: LocalStoreService,
                    UtilsService: UtilsService
                });

                //setup controller spies
                spyOn(scope, 'setFormState').andCallThrough();

                //invoke the controller function
                scope.resetForm();
                scope.$digest();
            }));

            //should reset field ng-models back to default selections
            it('should reset the field models to default selections', function() {
                //languages

                spyOn(scope.form.confirm_password.$error, 'pwmatch').andReturn(false);

                expect(scope.fields.amdLanguage).toEqual("");
                expect(scope.fields.hhLanguage).toEqual("");
                expect(scope.fields.j2uLanguage).toEqual("");
                expect(scope.fields.u2jLanguage).toEqual("");
                //shift
                expect(scope.fields.shift).toEqual("");
                //skill
                expect(scope.fields.skill).toEqual("");
                //groups
                expect(scope.widgetdetails.availableItems.length).toEqual(0);
                expect(scope.widgetdetails.selectedAvailableItems.length).toEqual(0);
                expect(scope.widgetdetails.existingItems.length).toEqual(0);
                expect(scope.widgetdetails.selectedExistingItems.length).toEqual(0);

                //ensure form is rendered into correct state
                expect(scope.setFormState).toHaveBeenCalledWith('MODE_SEARCH');
                expect(scope.MODE_SEARCH).toBeTruthy();
            });

            //should disable the form and select first tab
            it('should disable the form and select first tab', function() {
                expect(scope.tabs[0]).toBeTruthy();
                expect(scope.tabs[1]).toBeFalsy();
                expect(scope.tabs[2]).toBeFalsy();
                expect(scope.tabs[3]).toBeFalsy();
            });
        });

        //Should reset scope assignments
        describe('Warn of unsaved changes', function() {

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

            //setup
            beforeEach(function() {
                //setup spies
                spyOn(modal, 'open').andReturn(mockModalInstance);
                spyOn(scope, 'resetForm').andCallThrough();
            });

            //should clear the form and not display modal
            it('should reset form without displaying warning modal', function() {
                //mock no outstanding changes - make new obj match old object
                scope.originalValues = {
                    fields : scope.fields,
                    groups : scope.widgetdetails.existingItems
                };

                //Invoke the controller function to clear the form
                scope.clear();
                scope.$digest();

                expect(modal.open).not.toHaveBeenCalled();
                expect(scope.resetForm).toHaveBeenCalled();
            });

            //should warn of unsaved changes
            it('should display modal warning of unsaved changes', function() {
                //mock outstanding changes - set original values to empty object so it doesn't match
                //the scope.fields object
                scope.fields = {
                    "username" : "jack123"
                };
                scope.originalValues = {
                    fields : {},
                    groups : []
                };

                //Invoke the controller function to clear the form
                scope.clear();
                scope.$digest();

                //assert the results
                expect(modal.open).toHaveBeenCalled();
                expect(scope.resetForm).not.toHaveBeenCalled();
            });
        });
    });


    //
    // Saving a user
    //
    describe('Saving a user', function() {

        var def = null;
        var rootScope = null;

        //setup
        beforeEach(inject(function(_$controller_, _$q_, _$rootScope_) {

            rootScope = _$rootScope_.$new();

            def = _$q_.defer();
            def.resolve();

            //setup spies
            spyOn(CreateEditUserFormService, 'saveUser').andReturn(def.promise);

            //provide controller scope with mock 'widgetdetails'
            scope.widgetdetails = mockWidgetDetails;

            //inject controller
            controller = _$controller_('CreateEditUserFormController', {
                $rootScope : rootScope,
                $scope: scope,
                CreateEditUserFormService: CreateEditUserFormService,
                WidgetDefinitionService: WidgetDefinitionService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });

            //setup controller spies
            spyOn(scope, 'setFormState').andCallThrough();
            spyOn(rootScope, "$emit").andReturn();

            //provide controller scope with a mocks
            scope.widgetdetails = angular.copy(mockWidgetDetails);
            scope.widgetdetails.existingItems = [
                "admin"
            ];
            scope.fields = {
                username: "jill123",
                amdLanguage: "EN_GB",
                hhLanguage: "EN_GB",
                j2uLanguage: "EN_GB",
                u2jLanguage: "EN_GB"
            };
            scope.originalValues = {};
        }));

        //should save a user
        it('should save a user', function() {
            //invoke the controller function and apply the scope
            scope.save(true);

            scope.$digest();

            //should emit event to refresh the User Grid
            expect(rootScope.$emit).toHaveBeenCalled();
            expect(rootScope.$emit.callCount).toEqual(1);
            expect(rootScope.$emit.mostRecentCall.args[0]).toEqual("SearchUserGridRefresh");

            //assert other expected results
            expect(CreateEditUserFormService.saveUser).toHaveBeenCalled();

            expect(scope.originalValues.fields.username).toEqual("jill123");
            expect(scope.originalValues.fields.amdLanguage).toEqual("EN_GB");
            expect(scope.originalValues.fields.hhLanguage).toEqual("EN_GB");
            expect(scope.originalValues.fields.j2uLanguage).toEqual("EN_GB");
            expect(scope.originalValues.fields.u2jLanguage).toEqual("EN_GB");

            //expect the form state to enter into SAVE mode while saving, and then
            //enter EDIT mode after save is successful
            expect(scope.setFormState).toHaveBeenCalledWith('MODE_SAVE');
            expect(scope.setFormState).toHaveBeenCalledWith('MODE_EDIT');
            expect(scope.MODE_EDIT).toBeTruthy();
        });

        // should enable the save buttons
        it("should enable the Save buttons when passwords match", function(){
            scope.saveButtonEnabled = true;
            scope.saveAsButtonEnabled = true;

            spyOn(scope, 'validatePasswords').andReturn(true);
            expect(scope.enableSaveButton()).toBeTruthy();
            expect(scope.enableSaveAsButton()).toBeTruthy();
        });

        // should disable the save buttons
        it("should disable the Save buttons when passwords do not match", function(){
            scope.saveButtonEnabled = true;
            scope.saveAsButtonEnabled = true;

            spyOn(scope, 'validatePasswords').andReturn(false);
            expect(scope.enableSaveButton()).toBeFalsy();
            expect(scope.enableSaveAsButton()).toBeFalsy();
        });

    });

    // Saving a user - MultiEditMode
    describe('Saving a user in MultiEditMode', function() {

        var def = null;
        var rootScope = null;
        var modal;

        // Mock the modalInstance object that is returned from the $modal.open call
        var mockModalInstance = {
            result: {
                then: function(confirmCallback, cancelCallback) {
                    //Store the callbacks for later when the user clicks on the OK or Cancel button of the dialog
                    this.confirmCallBack = confirmCallback;
                    this.cancelCallback = cancelCallback;
                }
            },
            close: function(item) {
                //The user clicked OK on the modal dialog, call the stored confirm callback with the selected item
                this.result.confirmCallBack(item);
            },
            dismiss: function(type) {
                //The user clicked cancel on the modal dialog, call the stored cancel callback
                this.result.cancelCallback(type);
            }
        };

        // setup
        beforeEach(inject(function(_$controller_, _$q_, _$rootScope_, $httpBackend, $modal) {

            rootScope = _$rootScope_.$new();
            def = _$q_.defer();
            def.resolve();

            //setup spies
            spyOn(CreateEditUserFormService, 'saveMultiUsersDetails').andReturn(def.promise);

            modal = $modal;

            spyOn(modal, 'open').andReturn(mockModalInstance);

            scope.$digest();

            //provide controller scope with mock 'widgetdetails'
            scope.widgetdetails = mockWidgetDetails;

            $httpBackend.whenGET().respond({});
            $httpBackend.whenPOST().respond({});

            //inject controller
            controller = _$controller_('CreateEditUserFormController', {
                $rootScope: rootScope,
                $scope: scope,
                CreateEditUserFormService: CreateEditUserFormService,
                WidgetDefinitionService: WidgetDefinitionService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });

            //setup controller spies
            spyOn(scope, 'setFormState').andCallThrough();
            spyOn(rootScope, "$emit").andReturn();

            //provide controller scope with a mocks
            scope.widgetdetails = angular.copy(mockWidgetDetails);
            scope.widgetdetails.existingItems = [
                "admin"
            ];
            scope.fields = {
                username: "jill123",
                amdLanguage: "EN_GB",
                hhLanguage: "EN_GB",
                j2uLanguage: "EN_GB",
                u2jLanguage: "EN_GB"
            };
            scope.originalValues = {};
        }));

        //should save a user
        it('should save a user', function() {
            //invoke the controller function and apply the scope
            scope.MODE_MULTI_EDIT = true;
            scope.usernameList = ['jack123', 'jill123'];
            scope.userEditableFields = {
                "skill": null,
                "hhLanguage": null,
                "amdLanguage": null,
                "j2uLanguage": null,
                "u2jLanguage": null
            };

            spyOn(scope, 'getMultiUsersDetails').andReturn();

            scope.save(true);

            // invoke close method (Yes button) on modal to confirm discard
            scope.modalMultiEditSaveChanges.close();
            scope.$digest();

            expect(modal.open).toHaveBeenCalled();

            expect(scope.details).toEqual({
                "userNameList": ["jack123", "jill123"],
                "multiEditFields": {
                    "skill": {
                        "forceUpdate": false
                    },
                    "hhLanguage": {
                        "value": "EN_GB",
                        "forceUpdate": "false"
                    },
                    "amdLanguage": {
                        "value": "EN_GB",
                        "forceUpdate": "false"
                    },
                    "j2uLanguage": {
                        "value": "EN_GB",
                        "forceUpdate": "false"
                    },
                    "u2jLanguage": {
                        "value": "EN_GB",
                        "forceUpdate": "false"
                    }
                }
            });

            expect(scope.getMultiUsersDetails).toHaveBeenCalled();
            
            //should emit event to refresh the User Grid
            expect(rootScope.$emit).toHaveBeenCalled();
            expect(rootScope.$emit.callCount).toEqual(1);
            expect(rootScope.$emit.mostRecentCall.args[0]).toEqual("SearchUserGridRefresh");

            //assert other expected results
            expect(CreateEditUserFormService.saveMultiUsersDetails).toHaveBeenCalled();

            expect(scope.MODE_MULTI_EDIT).toBeTruthy();
        });
    });


    //
    // Searching for a user
    //
    describe('Searching for a user', function() {

        var def1 = null;
        var def2 = null;

        //setup
        beforeEach(inject(function(_$controller_, _$q_) {

            //resolve promise
            def1 = _$q_.defer();
            def2 = _$q_.defer();

            def1.resolve({
                skill : "ADVANCED",
                shift : {
                    shiftId : 1
                },
                j2uLanguage: {
                    languageCode : "EN_GB"
                },
                u2jLanguage : {
                    languageCode : "EN_GB"
                },
                hhLanguage : {
                    languageCode : "EN_GB"
                },
                amdLanguage : {
                    languageCode : "EN_GB"
                },
                employeeNumber : "123456",
                startDate : "20010509",
                firstName : "Jack",
                lastName : "Bloggs",
                birthDate : "19810712",
                mobileNumber : "01234567890",
                emailAddress : "jack123@gmail.com"
            });

            def2.resolve([
                "picker",
                "admin"
            ]);

            //setup service dependency spies
            spyOn(CreateEditUserFormService, 'getUserDetails').andReturn(def1.promise);
            spyOn(CreateEditUserFormService, 'getGroupsForUser').andReturn(def2.promise);


            //provide controller scope with a 'widgetdetails' mock
            scope.widgetdetails = angular.copy(mockWidgetDetails);

            //inject controller
            controller = _$controller_('CreateEditUserFormController', {
                $scope: scope,
                CreateEditUserFormService: CreateEditUserFormService,
                WidgetDefinitionService: WidgetDefinitionService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });

            //setup controller spies
            spyOn(scope, 'setFormState').andCallThrough();

            //digest the socpe to resolve any promises requested at controller
            //initialisation
            scope.$digest();
        }));

        //should retrieve existing user details and assign them to scope bindings
        it('should fetch existing user details and assign to scope', function() {
            //mimic the user existing (function returns true)
            spyOn(CreateEditUserFormService, 'checkUserExists').andReturn(true);

            //invoke the controller function and apply scope
            scope.searchUser(["jack123"]);
            scope.$digest();

            //assert results
            expect(CreateEditUserFormService.checkUserExists).toHaveBeenCalledWith("jack123");
            expect(CreateEditUserFormService.getUserDetails).toHaveBeenCalledWith("jack123");
            expect(CreateEditUserFormService.getGroupsForUser).toHaveBeenCalledWith("jack123");

            //ensure models are updated with user details
            expect(scope.fields.skill).toEqual("ADVANCED");
            expect(scope.fields.shift).toEqual(1);
            expect(scope.fields.j2uLanguage).toEqual("EN_GB");
            expect(scope.fields.u2jLanguage).toEqual("EN_GB");
            expect(scope.fields.hhLanguage).toEqual("EN_GB");
            expect(scope.fields.amdLanguage).toEqual("EN_GB");
            expect(scope.fields.employeeNumber).toEqual("123456");
            expect(scope.fields.startDate).toEqual("20010509");
            expect(scope.fields.firstName).toEqual("Jack");
            expect(scope.fields.lastName).toEqual("Bloggs");
            expect(scope.fields.birthDate).toEqual("19810712");
            expect(scope.fields.mobileNumber).toEqual("01234567890");
            expect(scope.fields.emailAddress).toEqual("jack123@gmail.com");
            expect(scope.widgetdetails.existingItems).toEqual(["picker","admin"]);

            //form should enter EDIT mode
            expect(scope.setFormState).toHaveBeenCalledWith("MODE_EDIT");
            expect(scope.MODE_EDIT).toBeTruthy();
        });

        //should not attempt to fetch user details if we are dealing with a new user
        it('should not fetch user details when creating new user', function() {
            //mimic the user NOT existing (function returns false)
            spyOn(CreateEditUserFormService, 'checkUserExists').andReturn(false);

            //invoke the controller function and apply scope
            scope.searchUser("jimmy123");
            scope.$digest();

            //assert results
            expect(CreateEditUserFormService.checkUserExists).toHaveBeenCalledWith("jimmy123");
            expect(CreateEditUserFormService.getUserDetails).not.toHaveBeenCalled();
            expect(CreateEditUserFormService.getGroupsForUser).not.toHaveBeenCalled();

            //form should enter CREATE mode
            expect(scope.setFormState).toHaveBeenCalledWith("MODE_CREATE");
            expect(scope.MODE_CREATE).toBeTruthy();
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
            controller = _$controller_('CreateEditUserFormController', {
                $scope: scope,
                CreateEditUserFormService: CreateEditUserFormService,
                WidgetDefinitionService: WidgetDefinitionService,
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
                'edit-user' : true,
                'create-user' : false
            };

            //invoke controller function and assert it returns correct result
            expect(scope.checkUserPermission('edit-user')).toBeTruthy();
            expect(scope.checkUserPermission('create-user')).toBeFalsy();
            expect(scope.checkUserPermission('delete-user')).toBeFalsy();
        });
    });



    //
    // Copy a user
    //
    describe('Copying a user', function() {

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

        //test setup
        beforeEach(inject(function(_$controller_) {
            //provide controller scope with a 'widgetdetails' mock
            scope.widgetdetails = angular.copy(mockWidgetDetails);

            //inject controller
            controller = _$controller_('CreateEditUserFormController', {
                $scope: scope,
                CreateEditUserFormService: CreateEditUserFormService,
                WidgetDefinitionService: WidgetDefinitionService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });

            //setup spies
            spyOn(modal, 'open').andReturn(mockModalInstance);
            spyOn(scope, 'save').andReturn();

            //digest the scope to resolve any promises requested at controller
            //initialisation
            scope.$digest();
        }));

        //should retrieve user details and assign them to scope bindings
        it('should open modal to enter new Lucas Login', function() {
            //invoke controller function
            scope.copy();
            expect(modal.open).toHaveBeenCalled();
        });

        //should save the user when pressing save button in modal
        it('should copy the user', function() {
            //invoke the controller function
            scope.copy();

            //invoke close method (save) on modal
            scope.modalInstance.close("a new user");

            //assert results
            expect(scope.fields.username).toEqual("a new user");
            //should not copy password fields
            expect(scope.fields.password).toEqual("");
            expect(scope.fields.confirmPassword).toEqual("");
            expect(scope.fields.hostLogin).toEqual("");
            expect(scope.fields.hostPassword).toEqual("");
            //should invoke save function
            expect(scope.save).toHaveBeenCalledWith("a new user");
        });

        //should not save the user when pressing cancel button in modal
        it('should not copy the user', function() {
            //invoke the controller function
            scope.copy();

            //invoke close method (save) on modal
            scope.modalInstance.dismiss();

            //assert results
            //should not invoke save function
            expect(scope.save).not.toHaveBeenCalled();
        });
    });


    //
    // Validating password
    //
    describe('Validating passwords', function() {

        //test setup
        beforeEach(inject(function(_$controller_) {
            //provide controller scope with a 'widgetdetails' mock
            scope.widgetdetails = angular.copy(mockWidgetDetails);

            //inject controller
            controller = _$controller_('CreateEditUserFormController', {
                $scope: scope,
                CreateEditUserFormService: CreateEditUserFormService,
                WidgetDefinitionService: WidgetDefinitionService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });

            //digest the scope to resolve any promises requested at controller
            //initialisation
            scope.$digest();
        }));

        //should identify passwords match
        it('should correctly validate matching passwords', function() {
            //set matching passwords
            scope.fields.password = "1234";
            scope.fields.confirmPassword = "1234";

            //invoke controller function and assert it returns correct result
            expect(scope.validatePasswords()).toBeTruthy();
        });

        //should identify password mis-match
        it('should correctly validate password mis-match', function() {
            //set matching passwords
            scope.fields.password = "123456";
            scope.fields.confirmPassword = "1234";

            //invoke controller function and assert it returns correct result
            expect(scope.validatePasswords()).toBeFalsy();
        });

        //should identify password match on undefined and empty string
        it('should correctly validate password match on undefined and empty string', function() {
            //set matching passwords
            scope.fields.password = "";
            scope.fields.confirmPassword = undefined;

            //invoke controller function and assert it returns correct result
            expect(scope.validatePasswords()).toBeTruthy();
        });
    });


    //
    // Ensure form data is persisted when scope is destroyed
    //
    describe('Persisting data when scope if destroyed', function() {

        //setup
        beforeEach(inject(function(_$controller_) {

            //provide controller scope with a 'widgetdetails' mock
            scope.widgetdetails = angular.copy(mockWidgetDetails);

            //set up syp on Utils Service that is to be injected into the controller
            spyOn(UtilsService, 'persistWidgetData').andReturn();

            //inject controller
            controller = _$controller_('CreateEditUserFormController', {
                $scope: scope,
                CreateEditUserFormService: CreateEditUserFormService,
                WidgetDefinitionService: WidgetDefinitionService,
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
            controller = _$controller_('CreateEditUserFormController', {
                $scope: scope,
                $injector: $injector,
                CreateEditUserFormService: CreateEditUserFormService,
                WidgetDefinitionService: WidgetDefinitionService,
                LocalStoreService: localStoreService,
                UtilsService: UtilsService
            });
        }));

        it('should handle WidgetBroadcast event', function() {
            spyOn(scope, 'resetForm').andReturn();
            scope.widgetdetails.id = 30;

            var def = $q.defer();
            spyOn(scope, 'searchUser').andReturn(def.promise);

            var broadcastObj = new BroadcastObject();
            broadcastObj.setWidgetId(29);
            broadcastObj.addDataElement("userName");
            broadcastObj.addDataValue("userName", "system");

            rootScope.$emit('WidgetBroadcast', broadcastObj);
            expect(scope.resetForm).toHaveBeenCalled();

            expect(scope.searchUser).toHaveBeenCalled();
            expect(scope.searchUser.callCount).toEqual(1);
            expect(scope.searchUser.mostRecentCall.args[0]).toEqual(["system"]);

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
            controller = _$controller_('CreateEditUserFormController', {
                $scope: scope,
                $injector: $injector,
                CreateEditUserFormService: CreateEditUserFormService,
                WidgetDefinitionService: WidgetDefinitionService,
                LocalStoreService: localStoreService,
                UtilsService: UtilsService
            });
        }));

        it('should test getWidgetInterationConfiguration', function() {
            scope.widgetdetails.id = 30;
            expect(scope.getWidgetInterationConfiguration()).toEqual([{
                active: true,
                widgetInstance: {
                    id: 29,
                    name: 'SearchUser, 29'
                },
                dataElement: 'userName'
            }]);
        });

    });
    
    describe('handle getMultiUsersDetails method', function() {
        var localCreateEditUserFormService;
        var def;

        //setup
        beforeEach(inject(function(_$controller_, $injector, LocalStoreService, CreateEditUserFormService, $q, $httpBackend) {
            def = $q.defer();
            //provide controller scope with a 'widgetdetails' mock
            scope.widgetdetails = angular.copy(mockWidgetDetails);
            scope.originalValues = {};

            localCreateEditUserFormService = CreateEditUserFormService;

            $httpBackend.whenGET().respond({});
            $httpBackend.whenPOST().respond({});

            //inject controller
            controller = _$controller_('CreateEditUserFormController', {
                $scope: scope,
                $injector: $injector,
                CreateEditUserFormService: CreateEditUserFormService,
                WidgetDefinitionService: WidgetDefinitionService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });
        }));

        it('handle getMultiUsersDetails method', function() {

            spyOn(localCreateEditUserFormService, 'getMultiUsersDetails').andReturn(def.promise);

            def.resolve({
                "userEditableFields": {
                    "skill": null,
                    "hhLanguage": null,
                    "amdLanguage": null,
                    "j2uLanguage": null,
                    "u2jLanguage": null
                }
            });

            scope.fields = {
                "amdLanguage": "",
                "hhLanguage": "",
                "j2uLanguage": "",
                "u2jLanguage": "",
                "shift": "",
                "skill": "",
                "username": "jack123, design"
            };
            scope.getMultiUsersDetails();
            scope.$apply();

            expect(scope.userEditableFields).toEqual({
                "skill": null,
                "hhLanguage": null,
                "amdLanguage": null,
                "j2uLanguage": null,
                "u2jLanguage": null
            });

            expect(scope.fields).toEqual({
                "amdLanguage": null,
                "hhLanguage": null,
                "j2uLanguage": null,
                "u2jLanguage": null,
                "shift": "",
                "skill": null,
                "username": "jack123, design"
            });

            expect(scope.MODE_MULTI_EDIT).toBeTruthy();
        });
    });
    
    describe('handle selectAll  method', function() {
        //setup
        beforeEach(inject(function(_$controller_, $injector, LocalStoreService, CreateEditUserFormService) {
            //provide controller scope with a 'widgetdetails' mock
            scope.widgetdetails = angular.copy(mockWidgetDetails);
            scope.originalValues = {};

            //inject controller
            controller = _$controller_('CreateEditUserFormController', {
                $scope: scope,
                $injector: $injector,
                CreateEditUserFormService: CreateEditUserFormService,
                WidgetDefinitionService: WidgetDefinitionService,
                LocalStoreService: LocalStoreService,
                UtilsService: UtilsService
            });
        }));

        it('handle selectAll  method', function() {
            scope.details = {
                multiEditFields: {
                    "amdLanguage": {
                        "value": "",
                        "forceUpdate": false
                    },
                    "hhLanguage": {
                        "value": "",
                        "forceUpdate": false
                    },
                    "j2uLanguage": {
                        "value": "",
                        "forceUpdate": false
                    },
                    "u2jLanguage": {
                        "value": "",
                        "forceUpdate": false
                    }
                }
            };
            scope.selectedAll = false;
            scope.selectAll();
            expect(scope.details.multiEditFields).toEqual({
                "amdLanguage": {
                    "value": "",
                    "forceUpdate": true
                },
                "hhLanguage": {
                    "value": "",
                    "forceUpdate": true
                },
                "j2uLanguage": {
                    "value": "",
                    "forceUpdate": true
                },
                "u2jLanguage": {
                    "value": "",
                    "forceUpdate": true
                }
            });
        });
    });

});