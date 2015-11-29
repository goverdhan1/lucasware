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

/**
 * Services related to Pack Factor
 */
amdApp.factory('PackFactorService', ['$q',
    function ($q) {
        return {
            // gets components
            /*getComponents: function () {
                return RestApiService.getAll('/user/componentsList', null, null);
            },
            deleteComponent: function(payload){
                return RestApiService.post('/user/deleteComponent', null, payload);
            },
            saveComponents:function(payload){
                return RestApiService.post('/user/saveComponents', null, payload);
            }*/
            getComponents: function () {
                var def = $q.defer();
                // Resolve the promise
                def.resolve([
                    {
                        name: "Pallet",
                        abbreviation: "PE",
                        id: 1
                    },
                    {
                        name: "Tier",
                        abbreviation: "TR",
                        id: 2
                    },
                    {
                        name: "Case",
                        abbreviation: "CS",
                        id: 3
                    },
                    {
                        name: "Inner Pack",
                        abbreviation: "IP",
                        id: 4
                    },
                    {
                        name: "Each",
                        abbreviation: "EC",
                        id: 5
                    },
                    {
                        name: "ABCD",
                        abbreviation: "AB",
                        id: 6
                    },
                    {
                        name: "XYZ",
                        abbreviation: "xy",
                        id: 7
                    },
                    {
                        name: "PQRST",
                        abbreviation: "PQ",
                        id: 8
                    }
                ]);
                return def.promise;
            },
            deleteComponent: function(payload){
                var def = $q.defer();
                // Resolve the promise
                def.resolve(true);
                return def.promise;
            },
            saveComponents:function(payload){
                var def = $q.defer();
                // Resolve the promise
                def.resolve(true);
                return def.promise;
            },
            getDetails: function () {
                var def = $q.defer();
                var widgetInstanceObj = {
                    data: [
                        {
                            "hierarchyID":"1",
                            "hierarchyName":"Ready To Eat Salad",
                            "levels":[
                                {
                                    "componentID": 6,
                                    "componentName": "Pallet",
                                    "defaultDimensions": {
                                        "height": null,
                                        "width": null,
                                        "depth": null,
                                        "weight": null,
                                        "cube": null
                                    },
                                    "factor": {
                                        "childComponentID": 5,
                                        "childComponentName": "Tier",
                                        "factor": 3
                                    }
                                },
                                {
                                    "componentID": 5,
                                    "componentName": "Tier",
                                    "defaultDimensions": {
                                        "height": null,
                                        "width": null,
                                        "depth": null,
                                        "weight": null,
                                        "cube": null
                                    },
                                    "factor": {
                                        "childComponentID": 3,
                                        "childComponentName": "Case",
                                        "factor": 2
                                    }
                                },
                                {
                                    "componentID": 3,
                                    "componentName": "Case",
                                    "defaultDimensions": {
                                        "height": null,
                                        "width": null,
                                        "depth": null,
                                        "weight": null,
                                        "cube": null
                                    },
                                    "factor": {
                                        "childComponentID": 2,
                                        "childComponentName": "Inner Pack",
                                        "factor": 8
                                    }
                                },
                                {
                                    "componentID": 2,
                                    "componentName": "Inner Pack",
                                    "defaultDimensions": {
                                        "height": null,
                                        "width": null,
                                        "depth": null,
                                        "weight": null,
                                        "cube": null
                                    },
                                    "factor": {
                                        "childComponentID": 1,
                                        "childComponentName": "Each",
                                        "factor": 4
                                    }
                                },
                                {
                                    "componentID": 1,
                                    "componentName": "Each",
                                    "defaultDimensions": {
                                        "height": 3.5,
                                        "width": 3.5,
                                        "depth": 3.5,
                                        "weight": null,
                                        "cube": null
                                    },
                                    "factor": {
                                        "childComponentID": null,
                                        "childComponentName": null,
                                        "factor": null
                                    }
                                }

                            ]
                        },
                        {
                            "hierarchyID":"2",
                            "hierarchyName":"kids train",
                            "levels":[
                                {
                                    "componentID": 6,
                                    "componentName": "Pallet",
                                    "defaultDimensions": {
                                        "height": null,
                                        "width": null,
                                        "depth": null,
                                        "weight": null,
                                        "cube": null
                                    },
                                    "factor": {
                                        "childComponentID": 5,
                                        "childComponentName": "Tier",
                                        "factor": 3
                                    }
                                },
                                {
                                    "componentID": 5,
                                    "componentName": "Tier",
                                    "defaultDimensions": {
                                        "height": null,
                                        "width": null,
                                        "depth": null,
                                        "weight": null,
                                        "cube": null
                                    },
                                    "factor": {
                                        "childComponentID": 3,
                                        "childComponentName": "Case",
                                        "factor": 2
                                    }
                                },
                                {
                                    "componentID": 3,
                                    "componentName": "Case",
                                    "defaultDimensions": {
                                        "height": null,
                                        "width": null,
                                        "depth": null,
                                        "weight": null,
                                        "cube": null
                                    },
                                    "factor": {
                                        "childComponentID": 2,
                                        "childComponentName": "Inner Pack",
                                        "factor": 8
                                    }
                                },
                                {
                                    "componentID": 2,
                                    "componentName": "Inner Pack",
                                    "defaultDimensions": {
                                        "height": null,
                                        "width": null,
                                        "depth": null,
                                        "weight": null,
                                        "cube": null
                                    },
                                    "factor": {
                                        "childComponentID": 1,
                                        "childComponentName": "Each",
                                        "factor": 4
                                    }
                                },
                                {
                                    "componentID": 1,
                                    "componentName": "Each",
                                    "defaultDimensions": {
                                        "height": 3.5,
                                        "width": 3.5,
                                        "depth": 3.5,
                                        "weight": null,
                                        "cube": null
                                    },
                                    "factor": {
                                        "childComponentID": null,
                                        "childComponentName": null,
                                        "factor": null
                                    }
                                }

                            ]
                        },
                        {
                            "hierarchyID":"3",
                            "hierarchyName":"Fancy T-shirts",
                            "levels":[
                                {
                                    "componentID": 6,
                                    "componentName": "Pallet",
                                    "defaultDimensions": {
                                        "height": null,
                                        "width": null,
                                        "depth": null,
                                        "weight": null,
                                        "cube": null
                                    },
                                    "factor": {
                                        "childComponentID": 5,
                                        "childComponentName": "Tier",
                                        "factor": 3
                                    }
                                },
                                {
                                    "componentID": 5,
                                    "componentName": "Tier",
                                    "defaultDimensions": {
                                        "height": null,
                                        "width": null,
                                        "depth": null,
                                        "weight": null,
                                        "cube": null
                                    },
                                    "factor": {
                                        "childComponentID": 3,
                                        "childComponentName": "Case",
                                        "factor": 2
                                    }
                                },
                                {
                                    "componentID": 3,
                                    "componentName": "Case",
                                    "defaultDimensions": {
                                        "height": null,
                                        "width": null,
                                        "depth": null,
                                        "weight": null,
                                        "cube": null
                                    },
                                    "factor": {
                                        "childComponentID": 2,
                                        "childComponentName": "Inner Pack",
                                        "factor": 8
                                    }
                                },
                                {
                                    "componentID": 2,
                                    "componentName": "Inner Pack",
                                    "defaultDimensions": {
                                        "height": null,
                                        "width": null,
                                        "depth": null,
                                        "weight": null,
                                        "cube": null
                                    },
                                    "factor": {
                                        "childComponentID": 1,
                                        "childComponentName": "Each",
                                        "factor": 4
                                    }
                                },
                                {
                                    "componentID": 1,
                                    "componentName": "Each",
                                    "defaultDimensions": {
                                        "height": 3.5,
                                        "width": 3.5,
                                        "depth": 3.5,
                                        "weight": null,
                                        "cube": null
                                    },
                                    "factor": {
                                        "childComponentID": null,
                                        "childComponentName": null,
                                        "factor": null
                                    }
                                }

                            ]
                        }
                    ]
                    ,
                    "name": "pack-factor-hierarchy-widget",
                    "id": 15,
                    "shortName": "packFactorHierarchyWidget",
                    "title": "Pack FactorHierarchy Widget",
                    "subtype": "FORM",
                    "widgetActionConfig": {
                        "viewComponents": true,
                        "searchComponents": true
                    },
                    //ToDo- check if any value needed to broadcast
                    "broadcastList": [],

                    "widgetDefinition": {
                        "name": "pack-factor-hierarchy-widget",
                        "id": 15,
                        "shortName": "packFactorHierarchyWidget",
                        "title": "Pack FactorHierarchy Widget",
                        "subtype": "FORM",
                        "widgetActionConfig": {
                            "viewComponents": true,
                            "editComponents": true
                        },
                        //ToDo- check if any value needed to broadcast
                        "broadcastList": [],
                        //ToDo- check if any value needed for react to map
                        "reactToMap": {
                        }
                    },
                    "actualViewConfig": {
                        "height": 325,
                        "width": 550,
                        "anchor": [
                            1,
                            2
                        ],
                        "zindex": 1,
                        //ToDo- check if any value required here
                        "listensForList": []
                    },
                    "dataURL": {
                        "url": "/user/packFactorHierarchies",
                        //search criteria will be empty for pack factor hierarchy widget
                        "searchCriteria": {}
                    },
                    "updateWidget": true,
                    "clientId": 100,
                    "isMaximized": false
                };

                // Resolve the promise
                def.resolve(widgetInstanceObj);
                return def.promise;
            }
        };
    }]);