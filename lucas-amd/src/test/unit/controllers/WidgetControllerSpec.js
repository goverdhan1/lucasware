'use strict';

describe('WidgetController Unit Tests', function () {

    // Global vars
    var controller = null;
    var $scope = null;
    var $state = null;
    var WidgetService = null;
    var LocalStoreService = null;

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

    beforeEach(inject(function (_$rootScope_, _$state_, _$controller_, _WidgetService_, _LocalStoreService_) {
        WidgetService = _WidgetService_;
        LocalStoreService = _LocalStoreService_;
        $state = _$state_;
        $scope = _$rootScope_.$new();
        $scope.widgetdetails = {
            clientId: 61,
            isMaximized: false,
            updateWidget: true,
            data: [
                {
                    chart: [
                        {
                            color: "#d62728",
                            key: "Completed",
                            values: [
                                {
                                    label: "Wave1", value: "300"
                                },
                                {
                                    label: "Wave2", value: "455"
                                },
                                {
                                    label: "Wave3", value: "367"
                                },
                                {
                                    label: "Wave4", value: "407"
                                }
                            ]
                        },
                        {
                            color: "#1f77b4",
                            key: "Total",
                            values: [
                                {
                                    label: "Wave1", value: "350"
                                },
                                {
                                    label: "Wave2", value: "470"
                                },
                                {
                                    label: "Wave3", value: "390"
                                },
                                {
                                    label: "Wave4", value: "459"
                                }
                            ]
                        }
                    ]
                }
            ],
            actualViewConfig: {
                anchor: [0, 0],
                height: 500,
                width: 600,
                zindex: 0
            },
            widgetDefinition: {
                id: 11,
                broadcastMap: {
                    Completed: "series.key",
                    Wave: "point.label"
                },
                dataURL: {

                },
                name: "assignment-management-piechart-widget",
                shortName: "PicklineByWave",
                subtype: "CHART_BAR",
                type: "GRAPH_2D",
                actualViewConfig: {
                    anchor: [0, 0],
                    height: 500,
                    width: 600,
                    zindex: 0
                }
            }
        };

        spyOn(LocalStoreService, 'addLSItem').andCallThrough();
        spyOn(WidgetService, 'loadCogMenuPopup').andReturn();
        spyOn(WidgetService, 'updateFavoriteCanvasListLocalStorage').andReturn($scope.widgetdetails);
        spyOn($state, 'transitionTo').andReturn();

        controller = _$controller_('WidgetController', {
            $scope: $scope,
            $state : $state,
            WidgetService: WidgetService,
            LocalStoreService: LocalStoreService
        });
    }));

    // handleCogWidget
    it('Should handle handleCogWidget method', function () {
        spyOn(LocalStoreService, 'getLSItem').andReturn($scope.widgetdetails);
        //invoke the function being tested
        $scope.handleCogWidget($scope.widgetdetails);
        //assert the results
        expect(LocalStoreService.addLSItem).toHaveBeenCalled();
        expect(WidgetService.loadCogMenuPopup).toHaveBeenCalledWith($scope.widgetdetails);
    });

    it('Should handle handleMaxState method', function () {
        spyOn(LocalStoreService, 'getLSItem').andReturn();
        //invoke the function being tested
        $scope.handleMaxState();
        //assert the results
        expect(LocalStoreService.addLSItem).toHaveBeenCalled();
        expect(LocalStoreService.getLSItem).toHaveBeenCalledWith('ActiveCanvasId');
        expect($state.transitionTo).toHaveBeenCalled();
    });

    // setActive method
    it('Should handle setActive method', function () {
        //invoke the function
        $scope.setActive($scope.widgetdetails);
        //assert the results
        expect(LocalStoreService.addLSItem).toHaveBeenCalled();
        expect($scope.widgetdetails.actualViewConfig.zindex).toBe(1);
    });

    describe('Should handle zIndexWidgetInstance', function () {
        beforeEach(inject(function (_$controller_) {

            spyOn(LocalStoreService, 'getLSItem').andReturn('61|11');

            controller = _$controller_('WidgetController', {
                $scope: $scope,
                $state : $state,
                WidgetService: WidgetService,
                LocalStoreService: LocalStoreService
            });
        }));

        it('Should handle zIndexWidgetInstance', function () {
            expect(LocalStoreService.getLSItem).toHaveBeenCalledWith('zIndexAnchor');
            expect($scope.widgetdetails.actualViewConfig.zindex).toBe(1);
        });
    });

    it('Should handle onDropComplete', inject(function(LocalStoreService) {
        LocalStoreService.addLSItem(null, 'ActiveCanvasId', "221");
        LocalStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', [{
            "name": "Hazmat",
            "canvasId": "201",
            "canvasType": "saved",
            "displayOrder": "1"
        }, {
            "name": "Work Execution",
            "canvasId": "221",
            "canvasType": "company",
            "displayOrder": "2",
            "widgetInstanceList": [{
                "data": [],
                "id": null,
                "widgetDefinition": {
                    "name": "create-or-edit-user-form-widget",
                    "shortName": "CreateUser",
                    "widgetActionConfig": {
                        "widget-access": {
                            "create-edit-user-widget-access": false
                        },
                        "widget-actions": {
                            "edit-user": false,
                            "create-user": false
                        }
                    },
                    "definitionData": {
                        "User": [null]
                    },
                    "reactToMap": {
                        "lastName": {
                            "url": "/users",
                            "searchCriteria": {
                                "pageSize": "10",
                                "pageNumber": "0",
                                "searchMap": {
                                    "lastName": ["jack123", "jill123", "admin123"]
                                }
                            }
                        },
                        "Wave": {
                            "url": "/users",
                            "searchCriteria": {
                                "pageSize": "10",
                                "pageNumber": "0",
                                "searchMap": {
                                    "Wave": ["Wave 1", "Wave 2", "Wave 3"]
                                }
                            }
                        }
                    },
                    "broadcastList": ["userName"],
                    "defaultViewConfig": {
                        "dateFormat": {
                            "selectedFormat": "mm-dd-yyyy",
                            "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                        },
                        "isMaximized": false,
                        "listensForList": ["userName", "Wave", "Score"],
                        "width": 567,
                        "height": 240,
                        "anchor": [275, 295],
                        "zindex": 0,
                        "reactToList": [],
                        "minWidth": 300,
                        "minHeight": 480,
                        "originalMinWidth": 300,
                        "originalMinHeight": 480
                    },
                    "broadcastMap": ["userName", "firstName"]
                },
                "actualViewConfig": {
                    "dateFormat": {
                        "selectedFormat": "mm-dd-yyyy",
                        "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                    },
                    "isMaximized": false,
                    "listensForList": ["userName", "Wave", "Score"],
                    "width": 567,
                    "height": 240,
                    "anchor": [275, 295],
                    "zindex": 0,
                    "reactToList": [],
                    "minWidth": 300,
                    "minHeight": 480,
                    "originalMinWidth": 300,
                    "originalMinHeight": 480
                },
                "updateWidget": true,
                "clientId": 100,
                "isMaximized": false
            }, {
                "data": [],
                "id": null,
                "widgetDefinition": {
                    "name": "create-or-edit-user-form-widget",
                    "shortName": "CreateUser",
                    "widgetActionConfig": {
                        "widget-access": {
                            "create-edit-user-widget-access": false
                        },
                        "widget-actions": {
                            "edit-user": false,
                            "create-user": false
                        }
                    },
                    "definitionData": {
                        "User": [null]
                    },
                    "reactToMap": {
                        "lastName": {
                            "url": "/users",
                            "searchCriteria": {
                                "pageSize": "10",
                                "pageNumber": "0",
                                "searchMap": {
                                    "lastName": ["jack123", "jill123", "admin123"]
                                }
                            }
                        },
                        "Wave": {
                            "url": "/users",
                            "searchCriteria": {
                                "pageSize": "10",
                                "pageNumber": "0",
                                "searchMap": {
                                    "Wave": ["Wave 1", "Wave 2", "Wave 3"]
                                }
                            }
                        }
                    },
                    "broadcastList": ["userName"],
                    "defaultViewConfig": {
                        "dateFormat": {
                            "selectedFormat": "mm-dd-yyyy",
                            "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                        },
                        "isMaximized": false,
                        "listensForList": ["userName", "Wave", "Score"],
                        "width": 567,
                        "height": 240,
                        "anchor": [275, 295],
                        "zindex": 0,
                        "reactToList": [],
                        "minWidth": 300,
                        "minHeight": 480,
                        "originalMinWidth": 300,
                        "originalMinHeight": 480
                    },
                    "broadcastMap": ["userName", "firstName"]
                },
                "actualViewConfig": {
                    "dateFormat": {
                        "selectedFormat": "mm-dd-yyyy",
                        "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                    },
                    "isMaximized": false,
                    "listensForList": ["userName", "Wave", "Score"],
                    "width": 567,
                    "height": 240,
                    "anchor": [275, 295],
                    "zindex": 0,
                    "reactToList": [],
                    "minWidth": 300,
                    "minHeight": 480,
                    "originalMinWidth": 300,
                    "originalMinHeight": 480
                },
                "updateWidget": true,
                "clientId": 101,
                "isMaximized": false
            }]
        }, {
            "name": "abc",
            "canvasId": "231",
            "canvasType": "saved",
            "displayOrder": "3"
        }, {
            "name": "xyz",
            "canvasId": "235",
            "canvasType": "company",
            "displayOrder": "4"
        }]);
        var updatedWidgetInstanceList = [{
            "data": [],
            "id": null,
            "widgetDefinition": {
                "name": "create-or-edit-user-form-widget",
                "shortName": "CreateUser",
                "widgetActionConfig": {
                    "widget-access": {
                        "create-edit-user-widget-access": false
                    },
                    "widget-actions": {
                        "edit-user": true,
                        "create-user": true
                    }
                },
                "definitionData": {
                    "User": [null]
                },
                "reactToMap": {
                    "lastName": {
                        "url": "/users",
                        "searchCriteria": {
                            "pageSize": "10",
                            "pageNumber": "0",
                            "searchMap": {
                                "lastName": ["jack123", "jill123", "admin123"]
                            }
                        }
                    },
                    "Wave": {
                        "url": "/users",
                        "searchCriteria": {
                            "pageSize": "10",
                            "pageNumber": "0",
                            "searchMap": {
                                "Wave": ["Wave 1", "Wave 2", "Wave 3"]
                            }
                        }
                    }
                },
                "broadcastList": ["userName"],
                "defaultViewConfig": {
                    "dateFormat": {
                        "selectedFormat": "mm-dd-yyyy",
                        "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                    },
                    "isMaximized": false,
                    "listensForList": ["userName", "Wave", "Score"],
                    "width": 567,
                    "height": 240,
                    "anchor": [275, 295],
                    "zindex": 0,
                    "reactToList": [],
                    "minWidth": 300,
                    "minHeight": 480,
                    "originalMinWidth": 300,
                    "originalMinHeight": 480
                },
                "broadcastMap": ["userName", "firstName"]
            },
            "actualViewConfig": {
                "dateFormat": {
                    "selectedFormat": "mm-dd-yyyy",
                    "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                },
                "isMaximized": false,
                "listensForList": ["userName", "Wave", "Score"],
                "width": 567,
                "height": 240,
                "anchor": [275, 295],
                "zindex": 0,
                "reactToList": [],
                "minWidth": 300,
                "minHeight": 480,
                "originalMinWidth": 300,
                "originalMinHeight": 480
            },
            "updateWidget": true,
            "clientId": 101,
            "isMaximized": false,
            "strAvailableItems": "userGroup.availableGroups",
            "strExistingItems": "userGroup.selectedGroups",
            "availableItems": ["administrator", "basic-authenticated-user", "picker", "supervisor", "system", "warehouse-manager"],
            "selectedAvailableItems": [],
            "existingItems": [],
            "selectedExistingItems": []
        }, {
            "data": [],
            "id": null,
            "widgetDefinition": {
                "name": "create-or-edit-user-form-widget",
                "shortName": "CreateUser",
                "widgetActionConfig": {
                    "widget-access": {
                        "create-edit-user-widget-access": false
                    },
                    "widget-actions": {
                        "edit-user": false,
                        "create-user": false
                    }
                },
                "definitionData": {
                    "User": [null]
                },
                "reactToMap": {
                    "lastName": {
                        "url": "/users",
                        "searchCriteria": {
                            "pageSize": "10",
                            "pageNumber": "0",
                            "searchMap": {
                                "lastName": ["jack123", "jill123", "admin123"]
                            }
                        }
                    },
                    "Wave": {
                        "url": "/users",
                        "searchCriteria": {
                            "pageSize": "10",
                            "pageNumber": "0",
                            "searchMap": {
                                "Wave": ["Wave 1", "Wave 2", "Wave 3"]
                            }
                        }
                    }
                },
                "broadcastList": ["userName"],
                "defaultViewConfig": {
                    "dateFormat": {
                        "selectedFormat": "mm-dd-yyyy",
                        "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                    },
                    "isMaximized": false,
                    "listensForList": ["userName", "Wave", "Score"],
                    "width": 567,
                    "height": 240,
                    "anchor": [275, 295],
                    "zindex": 0,
                    "reactToList": [],
                    "minWidth": 300,
                    "minHeight": 480,
                    "originalMinWidth": 300,
                    "originalMinHeight": 480
                },
                "broadcastMap": ["userName", "firstName"]
            },
            "actualViewConfig": {
                "dateFormat": {
                    "selectedFormat": "mm-dd-yyyy",
                    "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                },
                "isMaximized": false,
                "listensForList": ["userName", "Wave", "Score"],
                "width": 567,
                "height": 240,
                "anchor": [275, 295],
                "zindex": 0,
                "reactToList": [],
                "minWidth": 300,
                "minHeight": 480,
                "originalMinWidth": 300,
                "originalMinHeight": 480
            },
            "updateWidget": true,
            "clientId": 100,
            "isMaximized": false
        }];
        $scope.widgetInstanceList = [{
            "data": [],
            "id": null,
            "widgetDefinition": {
                "name": "create-or-edit-user-form-widget",
                "shortName": "CreateUser",
                "widgetActionConfig": {
                    "widget-access": {
                        "create-edit-user-widget-access": false
                    },
                    "widget-actions": {
                        "edit-user": false,
                        "create-user": false
                    }
                },
                "definitionData": {
                    "User": [null]
                },
                "reactToMap": {
                    "lastName": {
                        "url": "/users",
                        "searchCriteria": {
                            "pageSize": "10",
                            "pageNumber": "0",
                            "searchMap": {
                                "lastName": ["jack123", "jill123", "admin123"]
                            }
                        }
                    },
                    "Wave": {
                        "url": "/users",
                        "searchCriteria": {
                            "pageSize": "10",
                            "pageNumber": "0",
                            "searchMap": {
                                "Wave": ["Wave 1", "Wave 2", "Wave 3"]
                            }
                        }
                    }
                },
                "broadcastList": ["userName"],
                "defaultViewConfig": {
                    "dateFormat": {
                        "selectedFormat": "mm-dd-yyyy",
                        "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                    },
                    "isMaximized": false,
                    "listensForList": ["userName", "Wave", "Score"],
                    "width": 567,
                    "height": 240,
                    "anchor": [275, 295],
                    "zindex": 0,
                    "reactToList": [],
                    "minWidth": 300,
                    "minHeight": 480,
                    "originalMinWidth": 300,
                    "originalMinHeight": 480
                },
                "broadcastMap": ["userName", "firstName"]
            },
            "actualViewConfig": {
                "dateFormat": {
                    "selectedFormat": "mm-dd-yyyy",
                    "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                },
                "isMaximized": false,
                "listensForList": ["userName", "Wave", "Score"],
                "width": 567,
                "height": 240,
                "anchor": [275, 295],
                "zindex": 0,
                "reactToList": [],
                "minWidth": 300,
                "minHeight": 480,
                "originalMinWidth": 300,
                "originalMinHeight": 480
            },
            "updateWidget": true,
            "clientId": 100,
            "isMaximized": false
        }, {
            "data": [],
            "id": null,
            "widgetDefinition": {
                "name": "create-or-edit-user-form-widget",
                "shortName": "CreateUser",
                "widgetActionConfig": {
                    "widget-access": {
                        "create-edit-user-widget-access": false
                    },
                    "widget-actions": {
                        "edit-user": true,
                        "create-user": true
                    }
                },
                "definitionData": {
                    "User": [null]
                },
                "reactToMap": {
                    "lastName": {
                        "url": "/users",
                        "searchCriteria": {
                            "pageSize": "10",
                            "pageNumber": "0",
                            "searchMap": {
                                "lastName": ["jack123", "jill123", "admin123"]
                            }
                        }
                    },
                    "Wave": {
                        "url": "/users",
                        "searchCriteria": {
                            "pageSize": "10",
                            "pageNumber": "0",
                            "searchMap": {
                                "Wave": ["Wave 1", "Wave 2", "Wave 3"]
                            }
                        }
                    }
                },
                "broadcastList": ["userName"],
                "defaultViewConfig": {
                    "dateFormat": {
                        "selectedFormat": "mm-dd-yyyy",
                        "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                    },
                    "isMaximized": false,
                    "listensForList": ["userName", "Wave", "Score"],
                    "width": 567,
                    "height": 240,
                    "anchor": [275, 295],
                    "zindex": 0,
                    "reactToList": [],
                    "minWidth": 300,
                    "minHeight": 480,
                    "originalMinWidth": 300,
                    "originalMinHeight": 480
                },
                "broadcastMap": ["userName", "firstName"]
            },
            "actualViewConfig": {
                "dateFormat": {
                    "selectedFormat": "mm-dd-yyyy",
                    "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                },
                "isMaximized": false,
                "listensForList": ["userName", "Wave", "Score"],
                "width": 567,
                "height": 240,
                "anchor": [275, 295],
                "zindex": 0,
                "reactToList": [],
                "minWidth": 300,
                "minHeight": 480,
                "originalMinWidth": 300,
                "originalMinHeight": 480
            },
            "updateWidget": true,
            "clientId": 101,
            "isMaximized": false,
            "strAvailableItems": "userGroup.availableGroups",
            "strExistingItems": "userGroup.selectedGroups",
            "availableItems": ["administrator", "basic-authenticated-user", "picker", "supervisor", "system", "warehouse-manager"],
            "selectedAvailableItems": [],
            "existingItems": [],
            "selectedExistingItems": []
        }];
        
        $scope.onDropComplete(1, {data: 0});
        // check widgetInstanceList is updated in scope
        expect($scope.widgetInstanceList).toEqual(updatedWidgetInstanceList);
        // check the FavoriteCanvasListUpdated
        expect(LocalStoreService.getLSItem('FavoriteCanvasListUpdated')).toEqual([{
            "name": "Hazmat",
            "canvasId": "201",
            "canvasType": "saved",
            "displayOrder": "1"
        }, {
            "name": "Work Execution",
            "canvasId": "221",
            "canvasType": "company",
            "displayOrder": "2",
            "widgetInstanceList": [{
                "data": [],
                "id": null,
                "widgetDefinition": {
                    "name": "create-or-edit-user-form-widget",
                    "shortName": "CreateUser",
                    "widgetActionConfig": {
                        "widget-access": {
                            "create-edit-user-widget-access": false
                        },
                        "widget-actions": {
                            "edit-user": true,
                            "create-user": true
                        }
                    },
                    "definitionData": {
                        "User": [null]
                    },
                    "reactToMap": {
                        "lastName": {
                            "url": "/users",
                            "searchCriteria": {
                                "pageSize": "10",
                                "pageNumber": "0",
                                "searchMap": {
                                    "lastName": ["jack123", "jill123", "admin123"]
                                }
                            }
                        },
                        "Wave": {
                            "url": "/users",
                            "searchCriteria": {
                                "pageSize": "10",
                                "pageNumber": "0",
                                "searchMap": {
                                    "Wave": ["Wave 1", "Wave 2", "Wave 3"]
                                }
                            }
                        }
                    },
                    "broadcastList": ["userName"],
                    "defaultViewConfig": {
                        "dateFormat": {
                            "selectedFormat": "mm-dd-yyyy",
                            "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                        },
                        "isMaximized": false,
                        "listensForList": ["userName", "Wave", "Score"],
                        "width": 567,
                        "height": 240,
                        "anchor": [275, 295],
                        "zindex": 0,
                        "reactToList": [],
                        "minWidth": 300,
                        "minHeight": 480,
                        "originalMinWidth": 300,
                        "originalMinHeight": 480
                    },
                    "broadcastMap": ["userName", "firstName"]
                },
                "actualViewConfig": {
                    "dateFormat": {
                        "selectedFormat": "mm-dd-yyyy",
                        "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                    },
                    "isMaximized": false,
                    "listensForList": ["userName", "Wave", "Score"],
                    "width": 567,
                    "height": 240,
                    "anchor": [275, 295],
                    "zindex": 0,
                    "reactToList": [],
                    "minWidth": 300,
                    "minHeight": 480,
                    "originalMinWidth": 300,
                    "originalMinHeight": 480
                },
                "updateWidget": true,
                "clientId": 101,
                "isMaximized": false,
                "strAvailableItems": "userGroup.availableGroups",
                "strExistingItems": "userGroup.selectedGroups",
                "availableItems": ["administrator", "basic-authenticated-user", "picker", "supervisor", "system", "warehouse-manager"],
                "selectedAvailableItems": [],
                "existingItems": [],
                "selectedExistingItems": []
            }, {
                "data": [],
                "id": null,
                "widgetDefinition": {
                    "name": "create-or-edit-user-form-widget",
                    "shortName": "CreateUser",
                    "widgetActionConfig": {
                        "widget-access": {
                            "create-edit-user-widget-access": false
                        },
                        "widget-actions": {
                            "edit-user": false,
                            "create-user": false
                        }
                    },
                    "definitionData": {
                        "User": [null]
                    },
                    "reactToMap": {
                        "lastName": {
                            "url": "/users",
                            "searchCriteria": {
                                "pageSize": "10",
                                "pageNumber": "0",
                                "searchMap": {
                                    "lastName": ["jack123", "jill123", "admin123"]
                                }
                            }
                        },
                        "Wave": {
                            "url": "/users",
                            "searchCriteria": {
                                "pageSize": "10",
                                "pageNumber": "0",
                                "searchMap": {
                                    "Wave": ["Wave 1", "Wave 2", "Wave 3"]
                                }
                            }
                        }
                    },
                    "broadcastList": ["userName"],
                    "defaultViewConfig": {
                        "dateFormat": {
                            "selectedFormat": "mm-dd-yyyy",
                            "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                        },
                        "isMaximized": false,
                        "listensForList": ["userName", "Wave", "Score"],
                        "width": 567,
                        "height": 240,
                        "anchor": [275, 295],
                        "zindex": 0,
                        "reactToList": [],
                        "minWidth": 300,
                        "minHeight": 480,
                        "originalMinWidth": 300,
                        "originalMinHeight": 480
                    },
                    "broadcastMap": ["userName", "firstName"]
                },
                "actualViewConfig": {
                    "dateFormat": {
                        "selectedFormat": "mm-dd-yyyy",
                        "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                    },
                    "isMaximized": false,
                    "listensForList": ["userName", "Wave", "Score"],
                    "width": 567,
                    "height": 240,
                    "anchor": [275, 295],
                    "zindex": 0,
                    "reactToList": [],
                    "minWidth": 300,
                    "minHeight": 480,
                    "originalMinWidth": 300,
                    "originalMinHeight": 480
                },
                "updateWidget": true,
                "clientId": 100,
                "isMaximized": false
            }]
        }, {
            "name": "abc",
            "canvasId": "231",
            "canvasType": "saved",
            "displayOrder": "3"
        }, {
            "name": "xyz",
            "canvasId": "235",
            "canvasType": "company",
            "displayOrder": "4"
        }]);
    }));

});