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

describe('Search Group Controller Unit Tests', function() {

    // Global vars
    var def = null;
    var SearchGroupGridController = null;
    var scope = null;
    var modal = null;
    var rootScope;
    var httpBackend;
    var compile, actionButtons, compileButtons;
    var GroupGridWidgetService = null;
    var WidgetGridService = null;

    // Global test setup
    beforeEach(module('amdApp', function($translateProvider) {
        $translateProvider.translations('en', {
            "language-code": "EN"
        }, 'fr', {
            "language-code": "fr"
        }, 'de', {
            "language-code": "de"
        }).preferredLanguage('en');
        $translateProvider.useLoader('LocaleLoader');
    }));

    beforeEach(inject(function($rootScope, $modal, $q, $templateCache, $httpBackend, $compile, _GroupGridWidgetService_, _WidgetGridService_) {
        scope = $rootScope.$new();
        modal = $modal;
        rootScope = $rootScope;
        httpBackend = $httpBackend;
        httpBackend.whenGET().respond({});
        GroupGridWidgetService=_GroupGridWidgetService_;
        WidgetGridService=_WidgetGridService_;
        compile = $compile;
        scope.widgetdetails = {
            "clientId": 100,
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
                ],
                "gridColumns": {
                    "0": {
                        "name": "Group Name",
                        "fieldName": "groupName",
                        "order": "1",
                        "sortOrder": "1",
                        "visible": true,
                        "allowFilter": true
                    },
                    "1": {
                        "name": "Description",
                        "fieldName": "description",
                        "order": "2",
                        "sortOrder": "2",
                        "visible": true,
                        "allowFilter": true
                    },
                    "2": {
                        "name": "User Count",
                        "fieldName": "userCount",
                        "order": "3",
                        "sortOrder": "3",
                        "visible": true,
                        "allowFilter": true
                    }
                },
                "autoRefreshConfig": {
                    "globalOverride": false,
                    "enabled": false,
                    "interval": 120
                }

            },
            "widgetDefinition": {
                "name": "search-group-grid-widget",
                "id": 14,
                "widgetActionConfig": {
                    "widget-access": {
                        "search-group-grid-widget-access": true
                    },
                    "widget-actions": {
                        "edit-group": true,
                        "create-group": true,
                        "delete-group": true,
                        "view-group": true
                    }
                },
                "broadcastList": ["groupName"],
                "shortName": "SearchGroup",
                "defaultData": {},
                "defaultViewConfig": {
                    "listensForList": ["groupName"],
                    "width": 485,
                    "height": 375,
                    "anchor": [1, 363],
                    "zindex": 1,
                    "autoRefreshConfig": {
                        "globalOverride": false,
                        "enabled": false,
                        "interval": 120
                    }
                },
                "title": "Search Group",
                "dataURL": {
                    "searchCriteria": {
                        "searchMap": {},
                        "pageNumber": "0",
                        "pageSize": "10",
                        "sortMap": {}
                    },
                    "url": "/groups/grouplist/search"
                },
                "reactToMap": {
                    "groupName": {
                        "searchCriteria": {
                            "searchMap": {
                                "groupName": ["$groupName"]
                            },
                            "pageNumber": "0",
                            "pageSize": "10"
                        },
                        "url": "/groups/grouplist/search"
                    }
                }
            }
        };
    }));

    // Inject Specs
    describe('Search Group Grid Controller Grid Specs', function() {


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


        beforeEach(inject(function($controller, _$q_) {

            //setup dummy deferred exec.
            def = _$q_.defer();

            scope.widgetdetails.widgetDefinition.dataURL.searchCriteria.sortMap = {
                "groupName": "ASC"
            };

            scope.widgetdetails.widgetDefinition.dataURL.searchCriteria.searchMap = {
                "groupName": ["admin"]
            };

            SearchGroupGridController = $controller('SearchGroupGridController', {
                $scope: scope,
                $modal: modal,
                GroupGridWidgetService: GroupGridWidgetService,
                $compile: compile,
                WidgetGridService:WidgetGridService
            });
            spyOn(rootScope, '$on').andCallThrough();
            actionButtons='<div id="searchGroupGridActionButtons" disabled="true" class="searchGroupGridActionButtons">'+
                      '<button ng-click="deleteSelectedGroup()" ng-if="checkGroupPermission(\'delete-group\')" ng-disabled="selectedRows.length!==1" class="btn-small btn-success" id="deleteGroup"  tooltip-placement="right" tooltip-append-to-body="true" tooltip="{{\'user.selectGroupFromGridToDelete\' | translate}} "><i class="glyphicon glyphicon-trash"></i></button> '+
                      '</div>';
            actionButtons=angular.element(actionButtons);
            compileButtons = compile(actionButtons)(scope);
            scope.$digest();

            spyOn(modal, 'open').andReturn(mockModalInstance);

        }));

        it('should inject ModalInstance', function() {
            expect(modal).toBeDefined();
        });
        it('should inject $scope', function() {
            expect(scope).toBeDefined();
        });
        it('should be defined in scope', function() {
            scope.$digest();
            expect(scope.selectedRows).toBeDefined();
            expect(scope.widgetdetails).toBeDefined();
            expect(scope.sortCriteria).toBeDefined();
            expect(scope.filterCriteria).toBeDefined();
            expect(scope.columnDefinitions).toBeDefined();
            expect(scope.columnDefinitions).toBeDefined();
            expect(scope.gridOptions).toBeDefined();
        });

        it('Should Check Sort and Search Criteria', function() {
            // test filterCriteria
            expect(scope.filterCriteria).toEqual({
                groupName: 'admin'
            });
            // test sortCriteria
            expect(scope.sortCriteria).toEqual({
                groupName: 'ASC'
            });
        });


        it('should broadcast information for selected rows', function() {
            var mockGridObj = {
                colMovable: {
                    on: {
                        columnPositionChanged: function() {}
                    }
                },
                colResizable: {
                    on: {
                        columnSizeChanged: function() {}
                    }
                },
                core: {
                    on: {
                        needLoadMoreData: function() {

                        },
                        rowSelectionChanged: function() {

                        },
                        sortChanged: function() {}
                    }
                },
                grid: {},
                infiniteScroll: {
                    on: {
                        needLoadMoreData: function() {

                        },
                        rowSelectionChanged: function() {

                        },
                        sortChanged: function() {}
                    }
                },
                listeners: [],
                selection: {
                    getSelectedRows: function() {
                        return [{
                            description: "LucasAdmin2",
                            groupName: "LucasAdmin2",
                            userCount: "44"
                        }];
                    },
                    on: {
                        needLoadMoreData: function() {

                        },
                        rowSelectionChanged: function(scope, callback) {
                            callback();
                        },
                        rowSelectionChangedBatch: function() {}
                    }
                }
            };
            // spy on $broadcast
            spyOn(scope, '$broadcast').andCallThrough();
            // spy on rowSelectionChanged
            spyOn(mockGridObj.selection.on, 'rowSelectionChanged').andCallThrough();

            scope.gridOptions.onRegisterApi(mockGridObj);
            // test gridApi
            expect(scope.gridApi).toEqual(mockGridObj);

            // test rowSelectionChanged
            expect(mockGridObj.selection.on.rowSelectionChanged).toHaveBeenCalled();
            // test broadcast
            expect(scope.$broadcast).toHaveBeenCalled();
        });

        it('should test enable/disable Delete Group button based row selection', function(){

            // initial button disable
            expect(compileButtons.find("#deleteGroup")[0].disabled).toEqual(true);

            // Mock selectedRows length equal to one to ensure the delete group button is enabled
            scope.selectedRows.length=1;
            scope.$digest();
            expect(compileButtons.find("#deleteGroup")[0].disabled).toEqual(false);


            // Test to disable the Delete Group button when more than one rows are selected
            scope.selectedRows.length=2;
            scope.$digest();
            expect(compileButtons.find("#deleteGroup")[0].disabled).toEqual(true);

        });

        it('should hide Delete Group button', function(){
            spyOn(scope, 'checkGroupPermission').andReturn(false);
            scope.$digest();
            expect(compileButtons.find("#deleteGroup").length).toBe(0);

        });

        it('should show Delete Group button', function(){
            spyOn(scope, 'checkGroupPermission').andReturn(true);
            scope.$digest();
            expect(compileButtons.find("#deleteGroup").length).toBe(1);
        });

        it('should test deleteSelectedGroup() method', function() {
            //resolve the promise
            def.resolve({
                "status":"success"
            });

            //setup spies
            spyOn(GroupGridWidgetService, 'deleteGroup').andReturn(def.promise);

            //define necessary scope variables required for test
            scope.selectedRows = [{
                "groupName" : "A Group"
            }];
            scope.gridApi = {
                selection: {
                    clearSelectedRows: function() {}
                }
            };

            //invoke controller function
            scope.deleteSelectedGroup();

            //close the modal - this mocks the user pressing the OK button. We have to trigger a
            //digest cycle as this returns a promise.
            scope.modalInstance.close();
            scope.$digest();

            //expect a call to be made to the service to delete the groups passing the correct group name
            expect(GroupGridWidgetService.deleteGroup).toHaveBeenCalled();
            expect(GroupGridWidgetService.deleteGroup.callCount).toEqual(1);
            expect(GroupGridWidgetService.deleteGroup.mostRecentCall.args[0]).toEqual("A Group");
        });

        it('handle SearchGroupGridControllerRefresh event', function() {
            scope.gridApi = {
                selection: {
                    clearSelectedRows: function() {}
                }
            };
            spyOn(scope, 'loadRecords').andCallThrough();
            spyOn(scope, 'clearGrid').andCallThrough();
            rootScope.$emit('SearchGroupGridControllerRefresh', null);
            scope.$apply();
            expect(scope.$on).toHaveBeenCalled();
            expect(scope.clearGrid).toHaveBeenCalled();
            expect(scope.loadRecords).toHaveBeenCalled()
        });
    });
});
