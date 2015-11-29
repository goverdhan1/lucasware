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
 * Services related to Create User Widget.
 */
amdApp.factory('WidgetDefinitionService', ['$rootScope', '$q', 'Restangular', 'AuthTokenService', '$modal', '$http', 'RestApiService', 'WidgetService', '$log', 'UtilsService',
	function (rootScope, q, restangular, AuthTokenService, modal, http, restApiService, WidgetService, log, UtilsService) {
		return {
			//this fn returns the picked widgets' definition in order to display it in active canvas
			getWidgetDefinition: function (widgetId, widgetName, widgetType) {
				var chartRequest = false;
				var widName = widgetName;
				if (widName === "assignment-management-piechart-widget") {
					widgetName = "search-user-grid-widget";
					chartRequest = true;
				}

				var restUrl = 'widgets/' + widgetName + '/definition';

				var widType = widgetType;
				var clientWidgetInstanceId = UtilsService.getClientWidgetInstanceId();

				var widId = widgetId;
				
				// Mock EquipmentType data
				if (restUrl === 'widgets/equipment-type-grid-widget/definition') {
					var def = q.defer();
	                var widgetInstanceObj = {
	                    "clientId": clientWidgetInstanceId,
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
	                                "1": {
	                                    "name": "Equipment Style",
	                                    "allowFilter": true,
	                                    "order": "1",
	                                    "visible": true,
	                                    "fieldName": "equipment_style",
	                                    "width": 150,
	                                    "sortOrder": "1"
	                                },
	                                "2": {
	                                    "name": "Equipment Type",
	                                    "allowFilter": true,
	                                    "order": "2",
	                                    "visible": false,
	                                    "fieldName": "equipment_type_name",
	                                    "width": 150,
	                                    "sortOrder": "1"
	                                },
	                                "3": {
	                                    "name": "Description",
	                                    "allowFilter": true,
	                                    "order": "3",
	                                    "visible": true,
	                                    "fieldName": "equipment_type_description",
	                                    "width": 150,
	                                    "sortOrder": "1"
	                                },
	                                "4": {
	                                    "name": "Requires Check List",
	                                    "allowFilter": true,
	                                    "order": "4",
	                                    "visible": true,
	                                    "fieldName": "requires_check_list",
	                                    "width": 150,
	                                    "sortOrder": "1"
	                                },
	                                "5": {
	                                    "name": "Shipping Container",
	                                    "allowFilter": true,
	                                    "order": "5",
	                                    "visible": true,
	                                    "fieldName": "shipping_container",
	                                    "width": 150,
	                                    "sortOrder": "1"
	                                },
	                                "6": {
	                                    "name": "Requires Certification",
	                                    "allowFilter": true,
	                                    "order": "6",
	                                    "visible": true,
	                                    "fieldName": "requires_certification",
	                                    "width": 150,
	                                    "sortOrder": "1"
	                                },
	                                "7": {
	                                    "name": "Total Positions",
	                                    "allowFilter": true,
	                                    "order": "7",
	                                    "visible": true,
	                                    "fieldName": "Positions",
	                                    "width": 150,
	                                    "sortOrder": "1"
	                                },
	                                "8": {
	                                    "name": "Count",
	                                    "allowFilter": true,
	                                    "order": "8",
	                                    "visible": true,
	                                    "fieldName": "count",
	                                    "width": 150,
	                                    "sortOrder": "1"
	                                },
	                                "9": {
	                                    "name": "Created",
	                                    "allowFilter": true,
	                                    "order": "9",
	                                    "visible": true,
	                                    "fieldName": "created_date_time",
	                                    "width": 150,
	                                    "sortOrder": "1"
	                                },
	                                "10": {
	                                    "name": "Updated",
	                                    "allowFilter": true,
	                                    "order": "10",
	                                    "visible": true,
	                                    "fieldName": "updated_date_time",
	                                    "width": 150,
	                                    "sortOrder": "1"
	                                }
	                            }
	                    },
	                    "widgetDefinition": {
	                        "name": "equipment-type-grid-widget",
	                        "id": 22,
	                        "widgetActionConfig": {
	                            "widget-access": {
	                                "equipment-type-widget-access": true
	                            },
	                            "widget-actions": {
	                                "create-equipment-type": true,
	                                "edit-equipment-type": true,
	                                "delete-equipment-type": true
	                            }
	                        },
	                        "dataURL": {
	                            "url": "/equipments/equipment-type-list/search",
	                            "searchCriteria": {
	                                "pageNumber": "0",
	                                "pageSize": "10",
	                                "searchMap": {},
	                                "sortMap": {}
	                            }
	                        },
	                        "defaultViewConfig": {
	                            "dateFormat": {
	                                "selectedFormat": "mm-dd-yyyy",
	                                "availableFormats": [
	                                    "mm-dd-yyyy",
	                                    "MMM dd, yyyy",
	                                    "dd-mm-yyyy"
	                                ]
	                            },
	                            "anchor": [
	                                1,
	                                363
	                            ],
	                            "zindex": 1,
	                            "minimumWidth": 485,
	                            "minimumHeight": 375,
	                            "gridColumns": {
	                                "1": {
	                                    "name": "Equipment Style",
	                                    "allowFilter": true,
	                                    "order": "1",
	                                    "visible": true,
	                                    "fieldName": "equipment_style",
	                                    "width": 150,
	                                    "sortOrder": "1"
	                                },
	                                "2": {
	                                    "name": "Equipment Type",
	                                    "allowFilter": true,
	                                    "order": "2",
	                                    "visible": false,
	                                    "fieldName": "equipment_type_name",
	                                    "width": 150,
	                                    "sortOrder": "1"
	                                },
	                                "3": {
	                                    "name": "Description",
	                                    "allowFilter": true,
	                                    "order": "3",
	                                    "visible": true,
	                                    "fieldName": "equipment_type_description",
	                                    "width": 150,
	                                    "sortOrder": "1"
	                                },
	                                "4": {
	                                    "name": "Requires Check List",
	                                    "allowFilter": true,
	                                    "order": "4",
	                                    "visible": true,
	                                    "fieldName": "requires_check_list",
	                                    "width": 150,
	                                    "sortOrder": "1"
	                                },
	                                "5": {
	                                    "name": "Shipping Container",
	                                    "allowFilter": true,
	                                    "order": "5",
	                                    "visible": true,
	                                    "fieldName": "shipping_container",
	                                    "width": 150,
	                                    "sortOrder": "1"
	                                },
	                                "6": {
	                                    "name": "Requires Certification",
	                                    "allowFilter": true,
	                                    "order": "6",
	                                    "visible": true,
	                                    "fieldName": "requires_certification",
	                                    "width": 150,
	                                    "sortOrder": "1"
	                                },
	                                "7": {
	                                    "name": "Total Positions",
	                                    "allowFilter": true,
	                                    "order": "7",
	                                    "visible": true,
	                                    "fieldName": "Positions",
	                                    "width": 150,
	                                    "sortOrder": "1"
	                                },
	                                "8": {
	                                    "name": "Count",
	                                    "allowFilter": true,
	                                    "order": "8",
	                                    "visible": true,
	                                    "fieldName": "count",
	                                    "width": 150,
	                                    "sortOrder": "1"
	                                },
	                                "9": {
	                                    "name": "Created",
	                                    "allowFilter": true,
	                                    "order": "9",
	                                    "visible": true,
	                                    "fieldName": "created_date_time",
	                                    "width": 150,
	                                    "sortOrder": "1"
	                                },
	                                "10": {
	                                    "name": "Updated",
	                                    "allowFilter": true,
	                                    "order": "10",
	                                    "visible": true,
	                                    "fieldName": "updated_date_time",
	                                    "width": 150,
	                                    "sortOrder": "1"
	                                }
	                            },
	                            "listensForList": [
	                                "xxx"
	                            ],
	                            "deviceWidths": {
	                                "320": "mobile",
	                                "600": "tablet",
	                                "800": "desktop",
	                                "1200": "wideScreen"
	                            },
	                            "autoRefreshConfig": {
	                                "globalOverride": false,
	                                "enabled": false,
	                                "interval": 120
	                            }
	                        },
	                        "shortName": "EquipmentType",
	                        "title": "Equipment Type",
	                        "reactToMap": {
	                            "userName": {
	                                "url": "/equipment/equipmentlist/search",
	                                "searchCriteria": {
	                                    "pageNumber": "0",
	                                    "pageSize": "10",
	                                    "searchMap": {}
	                                }
	                            }
	                        },
	                        "broadcastList": [
	                            "xxx"
	                        ],
	                        "defaultData": {}
	                    }
	                };

	                WidgetService.putWidgetInstance(widgetInstanceObj);
	                def.resolve(widgetInstanceObj);
	                return def.promise;
				}

				// Mock search Message Segments data
				else if (restUrl == "widgets/eai-message-segments-grid-widget/definition") {
				    var def = q.defer();
				    var widgetInstanceObj = {
						"clientId": clientWidgetInstanceId,
						"actualViewConfig": {
							"height": 500,
							"width": 1100,
							"anchor": [
								1,
								2
							],
							"zindex": 1,
							"listensForList": [
								"messagesegmentName"
							],
							"gridColumns": {
				                "0": {
				                    "name": "Segment Name",
				                    "visible": true,
				                    "fieldName": "name",
				                    "sortOrder": "1",
				                    "width": 150,
				                    "order": "1",
				                    "allowFilter": true
				                },
				                "1": {
				                    "name": "Segment Description",
				                    "visible": true,
				                    "fieldName": "description",
				                    "sortOrder": "1",
				                    "width": 150,
				                    "order": "2",
				                    "allowFilter": true
				                },
				                "2": {
				                    "name": "Length",
				                    "visible": true,
				                    "fieldName": "length",
				                    "sortOrder": "1",
				                    "width": 150,
				                    "order": "3",
				                    "allowFilter": true
				                },
				                "3": {
				                    "name": "Usage in Segments",
				                    "visible": true,
				                    "fieldName": "usageinsegments",
				                    "sortOrder": "1",
				                    "width": 150,
				                    "order": "4",
				                    "allowFilter": true
				                },
				                "4": {
				                    "name": "Usage in Messages",
				                    "visible": true,
				                    "fieldName": "usageinmessages",
				                    "sortOrder": "1",
				                    "width": 150,
				                    "order": "5",
				                    "allowFilter": true
				                }
				            }
						},
				        "widgetDefinition": {
				            "name": "eai-message-segments-grid-widget",
				            "id": 14,
				            "widgetActionConfig": {
				                "widget-access": {
				                    "eai-message-segments-grid-widget-access": true
				                },
				                "widget-actions": {
				                    "edit-messagesegment": true,
				                    "create-messagesegment": true,
				                    "delete-messagesegment": true,
				                    "view-messagesegment": true
				                }
				            },
				            "broadcastList": ["messagesegmentName"],
				            "shortName": "Searchmessagesegment",
				            "defaultData": {},
				            "defaultViewConfig": {
				                "listensForList": ["messagesegmentName"],
				                "width": 485,
				                "height": 375,
				                "anchor": [1, 363],
				                "zindex": 1
				            },
				            "title": "Search messagesegment",
				            "dataURL": {
				                "searchCriteria": {
				                    "searchMap": {},
				                    "pageNumber": "0",
				                    "pageSize": "10",
				                    "sortMap": {}
				                },
				                "url": "/messagesegments/messagesegmentlist/search"
				            },
				            "reactToMap": {
				                "messagesegmentName": {
				                    "searchCriteria": {
				                        "searchMap": {
				                            "messagesegmentName": ["$messagesegmentName"]
				                        },
				                        "pageNumber": "0",
				                        "pageSize": "10"
				                    },
				                    "url": "/messagesegments/messagesegmentlist/search"
				                }
				            }
				        }
				    };

				    WidgetService.putWidgetInstance(widgetInstanceObj);
				    def.resolve(widgetInstanceObj);
				    return def.promise;
				}

				// Mock search Messages data
				else if (restUrl == "widgets/eai-messages-grid-widget/definition") {
				    var def = q.defer();
				    var widgetInstanceObj = {
						"clientId": clientWidgetInstanceId,
						"actualViewConfig": {
							"height": 500,
							"width": 1100,
							"anchor": [
								1,
								2
							],
							"zindex": 1,
							"listensForList": [
								"messageName"
							],
							"gridColumns": {
				                "0": {
				                    "name": "Message Name",
				                    "visible": true,
				                    "fieldName": "name",
				                    "sortOrder": "1",
				                    "width": 150,
				                    "order": "1",
				                    "allowFilter": true
				                },
				                "1": {
				                    "name": "Message Description",
				                    "visible": true,
				                    "fieldName": "description",
				                    "sortOrder": "1",
				                    "width": 150,
				                    "order": "2",
				                    "allowFilter": true
				                },
				                "2": {
				                    "name": "Message Format",
				                    "visible": true,
				                    "fieldName": "messageformat",
				                    "sortOrder": "1",
				                    "width": 150,
				                    "order": "3",
				                    "allowFilter": true
				                },
				                "3": {
				                    "name": "Usage Pre-Dfined",
				                    "visible": true,
				                    "fieldName": "usagepre-dfined",
				                    "sortOrder": "1",
				                    "width": 150,
				                    "order": "4",
				                    "allowFilter": true
				                },
				                "4": {
				                    "name": "Usage in Messages",
				                    "visible": true,
				                    "fieldName": "usageinmessages",
				                    "sortOrder": "1",
				                    "width": 150,
				                    "order": "5",
				                    "allowFilter": true
				                }
				            }
						},
				        "widgetDefinition": {
				            "name": "eai-messages-grid-widget",
				            "id": 14,
				            "widgetActionConfig": {
				                "widget-access": {
				                    "eai-messages-grid-widget-access": true
				                },
				                "widget-actions": {
				                    "edit-message": true,
				                    "create-message": true,
				                    "delete-message": true,
				                    "view-message": true
				                }
				            },
				            "broadcastList": ["messageName"],
				            "shortName": "Searchmessage",
				            "defaultData": {},
				            "defaultViewConfig": {
				                "listensForList": ["messageName"],
				                "width": 485,
				                "height": 375,
				                "anchor": [1, 363],
				                "zindex": 1
				            },
				            "title": "Search message",
				            "dataURL": {
				                "searchCriteria": {
				                    "searchMap": {},
				                    "pageNumber": "0",
				                    "pageSize": "10",
				                    "sortMap": {}
				                },
				                "url": "/messages/messagelist/search"
				            },
				            "reactToMap": {
				                "messageName": {
				                    "searchCriteria": {
				                        "searchMap": {
				                            "messageName": ["$messageName"]
				                        },
				                        "pageNumber": "0",
				                        "pageSize": "10"
				                    },
				                    "url": "/messages/messagelist/search"
				                }
				            }
				        }
				    };

				    WidgetService.putWidgetInstance(widgetInstanceObj);
				    def.resolve(widgetInstanceObj);
				    return def.promise;
				}

				// Mock search Message Mappings data
				else if (restUrl == "widgets/eai-message-mappings-grid-widget/definition") {
				    var def = q.defer();
				    var widgetInstanceObj = {
						"clientId": clientWidgetInstanceId,
						"actualViewConfig": {
							"height": 500,
							"width": 1100,
							"anchor": [
								1,
								2
							],
							"zindex": 1,
							"listensForList": [
								"messagemappingName"
							],
							"gridColumns": {
				                "0": {
				                    "name": "Mapping Name",
				                    "visible": true,
				                    "fieldName": "name",
				                    "sortOrder": "1",
				                    "width": 150,
				                    "order": "1",
				                    "allowFilter": true
				                },
				                "1": {
				                    "name": "Description",
				                    "visible": true,
				                    "fieldName": "description",
				                    "sortOrder": "1",
				                    "width": 150,
				                    "order": "2",
				                    "allowFilter": true
				                },
				                "2": {
				                    "name": "Source Message",
				                    "visible": false,
				                    "fieldName": "sourcemessage",
				                    "sortOrder": "1",
				                    "width": 150,
				                    "order": "3",
				                    "allowFilter": true
				                },
				                "3": {
				                    "name": "Destination Message",
				                    "visible": true,
				                    "fieldName": "destinationmessage",
				                    "sortOrder": "1",
				                    "width": 150,
				                    "order": "4",
				                    "allowFilter": true
				                },
				                "4": {
				                    "name": "Usage in Events",
				                    "visible": true,
				                    "fieldName": "usageinevents",
				                    "sortOrder": "1",
				                    "width": 150,
				                    "order": "5",
				                    "allowFilter": true
				                }
				            }
						},
				        "widgetDefinition": {
	                        "name": "eai-message-mappings-grid-widget",
	                        "id": 22,
	                        "widgetActionConfig": {
	                            "widget-access": {
	                                "message-mappings-widget-access": true
	                            },
	                            "widget-actions": {
	                                "create-message-mappings": true,
	                                "edit-message-mappings": true,
	                                "delete-message-mappings": true
	                            }
	                        },
	                        "dataURL": {
	                            "url": "/messagemappings/messagemapping-list/search",
	                            "searchCriteria": {
	                                "pageNumber": "0",
	                                "pageSize": "10",
	                                "searchMap": {},
	                                "sortMap": {}
	                            }
	                        },
	                        "defaultViewConfig": {
	                            "dateFormat": {
	                                "selectedFormat": "mm-dd-yyyy",
	                                "availableFormats": [
	                                    "mm-dd-yyyy",
	                                    "MMM dd, yyyy",
	                                    "dd-mm-yyyy"
	                                ]
	                            },
	                            "anchor": [
	                                1,
	                                363
	                            ],
	                            "zindex": 1,
	                            "minimumWidth": 485,
	                            "minimumHeight": 375,
	                            "gridColumns": {
	                                "0": {
					                    "name": "Mapping Name",
					                    "visible": true,
					                    "fieldName": "name",
					                    "sortOrder": "1",
					                    "width": 150,
					                    "order": "1",
					                    "allowFilter": true
					                },
					                "1": {
					                    "name": "Description",
					                    "visible": true,
					                    "fieldName": "description",
					                    "sortOrder": "1",
					                    "width": 150,
					                    "order": "2",
					                    "allowFilter": true
					                },
					                "2": {
					                    "name": "Source Message",
					                    "visible": false,
					                    "fieldName": "sourcemessage",
					                    "sortOrder": "1",
					                    "width": 150,
					                    "order": "3",
					                    "allowFilter": true
					                },
					                "3": {
					                    "name": "Destination Message",
					                    "visible": true,
					                    "fieldName": "destinationmessage",
					                    "sortOrder": "1",
					                    "width": 150,
					                    "order": "4",
					                    "allowFilter": true
					                },
					                "4": {
					                    "name": "Usage in Events",
					                    "visible": true,
					                    "fieldName": "usageinevents",
					                    "sortOrder": "1",
					                    "width": 150,
					                    "order": "5",
					                    "allowFilter": true
					                }
	                            },
	                            "listensForList": [
	                                "xxx"
	                            ],
	                            "deviceWidths": {
	                                "320": "mobile",
	                                "600": "tablet",
	                                "800": "desktop",
	                                "1200": "wideScreen"
	                            },
	                            "autoRefreshConfig": {
	                                "globalOverride": false,
	                                "enabled": false,
	                                "interval": 120
	                            }
	                        },
	                        "shortName": "MessageMapping",
	                        "title": "Message Mapping",
	                        "reactToMap": {
	                            "userName": {
	                                "url": "/messagemapping/messagemappinglist/search",
	                                "searchCriteria": {
	                                    "pageNumber": "0",
	                                    "pageSize": "10",
	                                    "searchMap": {}
	                                }
	                            }
	                        },
	                        "broadcastList": [
	                            "xxx"
	                        ],
	                        "defaultData": {}
	                    }
	                };

				    WidgetService.putWidgetInstance(widgetInstanceObj);
				    def.resolve(widgetInstanceObj);
				    return def.promise;
				}

				// Mock search Events data
				else if (restUrl == "widgets/eai-events-grid-widget/definition") {
				    var def = q.defer();
				    var widgetInstanceObj = {
						"clientId": clientWidgetInstanceId,
						"actualViewConfig": {
							"height": 500,
							"width": 1100,
							"anchor": [
								1,
								2
							],
							"zindex": 1,
							"listensForList": [
								"eventName"
							],
							"gridColumns": {
				                "0": {
				                    "name": "Event Name",
				                    "visible": true,
				                    "fieldName": "name",
				                    "sortOrder": "1",
				                    "width": 150,
				                    "order": "1",
				                    "allowFilter": true
				                },
				                "1": {
				                    "name": "Description",
				                    "visible": true,
				                    "fieldName": "description",
				                    "sortOrder": "1",
				                    "width": 150,
				                    "order": "2",
				                    "allowFilter": true
				                },
				                "2": {
				                    "name": "Type",
				                    "visible": false,
				                    "fieldName": "type",
				                    "sortOrder": "1",
				                    "width": 150,
				                    "order": "3",
				                    "allowFilter": true
				                },
				                "3": {
				                    "name": "Source",
				                    "visible": true,
				                    "fieldName": "source",
				                    "sortOrder": "1",
				                    "width": 150,
				                    "order": "4",
				                    "allowFilter": true
				                },
				                "4": {
				                    "name": "Category",
				                    "visible": true,
				                    "fieldName": "category",
				                    "sortOrder": "1",
				                    "width": 150,
				                    "order": "5",
				                    "allowFilter": true
				                },
				                "5": {
				                    "name": "Sub-Category",
				                    "visible": true,
				                    "fieldName": "sub-category",
				                    "sortOrder": "1",
				                    "width": 150,
				                    "order": "5",
				                    "allowFilter": true
				                }
				            }
						},
				        "widgetDefinition": {
	                        "name": "eai-events-grid-widget",
	                        "id": 22,
	                        "widgetActionConfig": {
	                            "widget-access": {
	                                "events-widget-access": true
	                            },
	                            "widget-actions": {
	                                "create-events": true,
	                                "edit-events": true,
	                                "delete-events": true
	                            }
	                        },
	                        "dataURL": {
	                            "url": "/events/event-list/search",
	                            "searchCriteria": {
	                                "pageNumber": "0",
	                                "pageSize": "10",
	                                "searchMap": {},
	                                "sortMap": {}
	                            }
	                        },
	                        "defaultViewConfig": {
	                            "dateFormat": {
	                                "selectedFormat": "mm-dd-yyyy",
	                                "availableFormats": [
	                                    "mm-dd-yyyy",
	                                    "MMM dd, yyyy",
	                                    "dd-mm-yyyy"
	                                ]
	                            },
	                            "anchor": [
	                                1,
	                                363
	                            ],
	                            "zindex": 1,
	                            "minimumWidth": 485,
	                            "minimumHeight": 375,
	                            "gridColumns": {
	                                "0": {
					                    "name": "Event Name",
					                    "visible": true,
					                    "fieldName": "name",
					                    "sortOrder": "1",
					                    "width": 150,
					                    "order": "1",
					                    "allowFilter": true
					                },
					                "1": {
					                    "name": "Description",
					                    "visible": true,
					                    "fieldName": "description",
					                    "sortOrder": "1",
					                    "width": 150,
					                    "order": "2",
					                    "allowFilter": true
					                },
					                "2": {
					                    "name": "Type",
					                    "visible": false,
					                    "fieldName": "type",
					                    "sortOrder": "1",
					                    "width": 150,
					                    "order": "3",
					                    "allowFilter": true
					                },
					                "3": {
					                    "name": "Source",
					                    "visible": true,
					                    "fieldName": "source",
					                    "sortOrder": "1",
					                    "width": 150,
					                    "order": "4",
					                    "allowFilter": true
					                },
					                "4": {
					                    "name": "Category",
					                    "visible": true,
					                    "fieldName": "category",
					                    "sortOrder": "1",
					                    "width": 150,
					                    "order": "5",
					                    "allowFilter": true
					                },
					                "5": {
					                    "name": "Sub-Category",
					                    "visible": true,
					                    "fieldName": "sub-category",
					                    "sortOrder": "1",
					                    "width": 150,
					                    "order": "5",
					                    "allowFilter": true
					                }
	                            },
	                            "listensForList": [
	                                "xxx"
	                            ],
	                            "deviceWidths": {
	                                "320": "mobile",
	                                "600": "tablet",
	                                "800": "desktop",
	                                "1200": "wideScreen"
	                            },
	                            "autoRefreshConfig": {
	                                "globalOverride": false,
	                                "enabled": false,
	                                "interval": 120
	                            }
	                        },
	                        "shortName": "Event",
	                        "title": "Event",
	                        "reactToMap": {
	                            "userName": {
	                                "url": "/event/eventlist/search",
	                                "searchCriteria": {
	                                    "pageNumber": "0",
	                                    "pageSize": "10",
	                                    "searchMap": {}
	                                }
	                            }
	                        },
	                        "broadcastList": [
	                            "xxx"
	                        ],
	                        "defaultData": {}
	                    }
	                };

				    WidgetService.putWidgetInstance(widgetInstanceObj);
				    def.resolve(widgetInstanceObj);
				    return def.promise;
				}

                // Mock search Event Handlers data
                else if (restUrl == "widgets/eai-event-handlers-grid-widget/definition") {
                    var def = q.defer();
                    var widgetInstanceObj = {
                        "clientId": clientWidgetInstanceId,
                        "actualViewConfig": {
                            "height": 500,
                            "width": 1100,
                            "anchor": [
                                1,
                                2
                            ],
                            "zindex": 1,
                            "listensForList": [
                                "eventhandlerName"
                            ],
                            "gridColumns": {
                                "0": {
                                    "name": "Event Handler Name",
                                    "visible": true,
                                    "fieldName": "name",
                                    "sortOrder": "1",
                                    "width": 150,
                                    "order": "1",
                                    "allowFilter": true
                                },
                                "1": {
                                    "name": "Description",
                                    "visible": true,
                                    "fieldName": "description",
                                    "sortOrder": "1",
                                    "width": 150,
                                    "order": "2",
                                    "allowFilter": true
                                },
                                "2": {
                                    "name": "Type",
                                    "visible": false,
                                    "fieldName": "type",
                                    "sortOrder": "1",
                                    "width": 150,
                                    "order": "3",
                                    "allowFilter": true
                                },
                                "3": {
                                    "name": "Inbound Mapping",
                                    "visible": true,
                                    "fieldName": "inboundmapping",
                                    "sortOrder": "1",
                                    "width": 150,
                                    "order": "4",
                                    "allowFilter": true
                                },
                                "4": {
                                    "name": "Outbound Mapping",
                                    "visible": true,
                                    "fieldName": "outboundmapping",
                                    "sortOrder": "1",
                                    "width": 150,
                                    "order": "5",
                                    "allowFilter": true
                                },
                                "5": {
                                    "name": "Usage in Events",
                                    "visible": true,
                                    "fieldName": "usageinevents",
                                    "sortOrder": "1",
                                    "width": 150,
                                    "order": "5",
                                    "allowFilter": true
                                }
                            }
                        },
                        "widgetDefinition": {
                            "name": "eai-event-handlers-grid-widget",
                            "id": 22,
                            "widgetActionConfig": {
                                "widget-access": {
                                    "event-handlers-widget-access": true
                                },
                                "widget-actions": {
                                    "create-event-handlers": true,
                                    "edit-event-handlers": true,
                                    "delete-event-handlers": true
                                }
                            },
                            "dataURL": {
                                "url": "/eventhandlers/eventhandler-list/search",
                                "searchCriteria": {
                                    "pageNumber": "0",
                                    "pageSize": "10",
                                    "searchMap": {},
                                    "sortMap": {}
                                }
                            },
                            "defaultViewConfig": {
                                "dateFormat": {
                                    "selectedFormat": "mm-dd-yyyy",
                                    "availableFormats": [
                                        "mm-dd-yyyy",
                                        "MMM dd, yyyy",
                                        "dd-mm-yyyy"
                                    ]
                                },
                                "anchor": [
                                    1,
                                    363
                                ],
                                "zindex": 1,
                                "minimumWidth": 485,
                                "minimumHeight": 375,
                                "gridColumns": {
                                    "0": {
                                        "name": "Event Handler Name",
                                        "visible": true,
                                        "fieldName": "name",
                                        "sortOrder": "1",
                                        "width": 150,
                                        "order": "1",
                                        "allowFilter": true
                                    },
                                    "1": {
                                        "name": "Description",
                                        "visible": true,
                                        "fieldName": "description",
                                        "sortOrder": "1",
                                        "width": 150,
                                        "order": "2",
                                        "allowFilter": true
                                    },
                                    "2": {
                                        "name": "Type",
                                        "visible": true,
                                        "fieldName": "type",
                                        "sortOrder": "1",
                                        "width": 150,
                                        "order": "3",
                                        "allowFilter": true
                                    },
                                    "3": {
                                        "name": "Inbound Mapping",
                                        "visible": true,
                                        "fieldName": "inboundmapping",
                                        "sortOrder": "1",
                                        "width": 150,
                                        "order": "4",
                                        "allowFilter": true
                                    },
                                    "4": {
                                        "name": "Outbound Mapping",
                                        "visible": true,
                                        "fieldName": "outboundmapping",
                                        "sortOrder": "1",
                                        "width": 150,
                                        "order": "5",
                                        "allowFilter": true
                                    },
                                    "5": {
                                        "name": "Usage in Events",
                                        "visible": true,
                                        "fieldName": "usageinevents",
                                        "sortOrder": "1",
                                        "width": 150,
                                        "order": "5",
                                        "allowFilter": true
                                    }
                                },
                                "listensForList": [
                                    "xxx"
                                ],
                                "deviceWidths": {
                                    "320": "mobile",
                                    "600": "tablet",
                                    "800": "desktop",
                                    "1200": "wideScreen"
                                },
                                "autoRefreshConfig": {
                                    "globalOverride": false,
                                    "enabled": false,
                                    "interval": 120
                                }
                            },
                            "shortName": "EventHandler",
                            "title": "Event Handler",
                            "reactToMap": {
                                "userName": {
                                    "url": "/eventhandler/eventhandlerlist/search",
                                    "searchCriteria": {
                                        "pageNumber": "0",
                                        "pageSize": "10",
                                        "searchMap": {}
                                    }
                                }
                            },
                            "broadcastList": [
                                "xxx"
                            ],
                            "defaultData": {}
                        }
                    };

                    WidgetService.putWidgetInstance(widgetInstanceObj);
                    def.resolve(widgetInstanceObj);
                    return def.promise;
                }

				// Mock user group data
				else if (restUrl == "widgets/user-group-widget/definition") {
					var def = q.defer();
					var widgetInstanceObj = {
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
                        "shiftURL":{
                            url:"/users/shifts"
                        },
                        "languageURL":{
                            url:"/users/languages"
                        },

						"updateWidget": true,
						"clientId": clientWidgetInstanceId,
						"isMaximized": false
					};

					WidgetService.putWidgetInstance(widgetInstanceObj);
					def.resolve(widgetInstanceObj);
					return def.promise;
				}

                else if(restUrl == "widgets/pack-factor-hierarchy-grid-widget/definition"){
                    //mock data for pack factor widget starts here
                    var def = q.defer();
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
                        "name": "pack-factor-hierarchy-grid-widget",
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
                            "name": "pack-factor-hierarchy-grid-widget",
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
                        "clientId": clientWidgetInstanceId,
                        "isMaximized": false
                    };
                    WidgetService.putWidgetInstance(widgetInstanceObj);
                    def.resolve(widgetInstanceObj);
                    return def.promise;
                    //mock data for pack factor widget ends here
                }

				else {
					var promise = restApiService.getAll(restUrl, null, null);
					promise.then(function (response) {

						if (chartRequest) {
							chartRequest = false;
							var widgetDefinitionObj = {
								"id": widId,
								//"name" : 'pickline-by-wave-barchart-widget',
								"name": widName,
								"shortName": 'PicklineByWave',
								"type": 'GRAPH_2D',
								"subtype": 'CHART_BAR',
								"broadCastMap": {
									"Completed": "series.key",
									"Wave": "point.label"
								},
								"dataURL": {
									"url": "/waves/picklines",
									"searchcriteria": {
										"searchmap": {}
									}
                                },
                                "shiftURL":{
                                    url:"/users/shifts"
                                },
                                "languageURL":{
                                    url:"/users/languages"
                                },
								"actualViewConfig": {
									"anchor": [0, 0],
									"height": 350,
									"width": 500,
									"zindex": 1,
									/*
									 * MOCK ORIENTATION PROPERTY UNTIL PHX-668 IS SENDING IT
									 */
									"orientation": {
										"option": [{
											"legend": "horizontal",
											"value": "h"
										}, {
											"legend": "vertical",
											"value": "v"
										}],
										"selected": "h"
									}
								},
								"listensForList": ["Area", "Wave", "Score"]
							};
							var widgetInstanceObj = {
								"data": [{
									"chart": {
										"Complete": [{
											"value": "26",
											"label": "Wave1"
										}, {
											"value": "12",
											"label": "Wave2"
										}, {
											"value": "24",
											"label": "Wave3"
										}, {
											"value": "13",
											"label": "Wave4"
										}],
										"Total": [{
											"value": "26",
											"label": "Wave1"
										}, {
											"value": "24",
											"label": "Wave2"
										}, {
											"value": "25",
											"label": "Wave3"
										}, {
											"value": "25",
											"label": "Wave4"
										}]
									}
								}],
								"id": null,
								"widgetDefinition": widgetDefinitionObj,
								"actualViewConfig": widgetDefinitionObj.actualViewConfig,
								"updateWidget": true,
								"clientId": clientWidgetInstanceId,
								"isMaximized": false
							};
							WidgetService.putWidgetInstance(widgetInstanceObj);
						}
                        else {
                            var widgetDefinitionObj = response;

                            // temporary code: update only for grid widgets
                            if (widgetDefinitionObj.defaultViewConfig.gridColumns) {
                                for (var key in widgetDefinitionObj.defaultViewConfig.gridColumns) {
                                    // ignore the width
                                    delete widgetDefinitionObj.defaultViewConfig.gridColumns[key].width;
                                }
                            }

                            if ( typeof widgetDefinitionObj.defaultViewConfig.reactToList === "undefined") {
                                //this is client side specific property; to handle the intuitive dropdowns of reactToList and listensForList at LHS and RHS.
                                widgetDefinitionObj.defaultViewConfig['reactToList'] = WidgetService.getReactToArrayFromMap(widgetDefinitionObj.reactToMap, widgetDefinitionObj.defaultViewConfig.listensForList);
                            }
                            var defaultViewConfig = widgetDefinitionObj.defaultViewConfig;

                            // Mock data, will be removed when the data comes from the server
                            widgetDefinitionObj.defaultViewConfig.dateFormat = {
                                selectedFormat: "mm-dd-yyyy",
                                availableFormats: ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                            };

                            var widgetInstanceObj = {
                                "data": [],
                                "id": null,
                                "widgetDefinition": widgetDefinitionObj,
                                "actualViewConfig": defaultViewConfig,
                                "updateWidget": true,
                                "clientId": clientWidgetInstanceId,
                                "isMaximized": false
                            };

                            // set the min width to be 300 (320 mobile), this may later come from database
                            widgetInstanceObj.actualViewConfig.minimumWidth = 300;
                            widgetInstanceObj.actualViewConfig.minimumHeight = 480;
                            widgetInstanceObj.actualViewConfig.originalMinimumWidth = 300;
                            widgetInstanceObj.actualViewConfig.originalMinimumHeight = 480;

                            widgetInstanceObj.widgetDefinition.id = widId;

                            //mocking broadcastmap, reacttoMap, listensForList for createUserForm, picklineWaveChart and searchUserGrid widgets.
                            if (widgetInstanceObj.widgetDefinition.name === 'create-or-edit-user-form-widget') {
                                widgetInstanceObj.actualViewConfig.listensForList = ["userName", "Wave", "Score"];
                                widgetInstanceObj.widgetDefinition.broadcastMap = ["userName", "firstName"];
                                widgetInstanceObj.widgetDefinition.reactToMap = {
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
                                };
                            } else if (widgetInstanceObj.widgetDefinition.name === 'search-product-grid-widget') {
                                if (!widgetInstanceObj.widgetActionConfig) {
                                    widgetInstanceObj.widgetActionConfig = {}
                                }
                                widgetInstanceObj.widgetActionConfig["widget-actions"] = {
                                    'configure-classification': true
                                };
                            } else if (widgetInstanceObj.widgetDefinition.name === 'search-user-grid-widget') {
                                widgetInstanceObj.actualViewConfig.listensForList = ["Score"];
                                widgetInstanceObj.widgetDefinition.broadcastMap = ["userName", "firstName", "lastName"];
                                widgetInstanceObj.widgetDefinition.reactToMap = {
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
                                    "Score": {
                                        "url": "/users",
                                        "searchCriteria": {
                                            "pageSize": "10",
                                            "pageNumber": "0",
                                            "searchMap": {
                                                "Wave": ["Score 1", "Score 2", "Score 3"]
                                            }
                                        }
                                    }
                                };
                            } else if (widgetInstanceObj.widgetDefinition.name === 'pickline-by-wave-barchart-widget') {
                                widgetInstanceObj.actualViewConfig.listensForList = ["lastName", "userName", "firstName"];
                                widgetInstanceObj.widgetDefinition.broadcastList = ["Wave", "Score"];
                                widgetInstanceObj.widgetDefinition.reactToMap = {
                                    "userName": {
                                        "url": "/users",
                                        "searchCriteria": {
                                            "pageSize": "10",
                                            "pageNumber": "0",
                                            "searchMap": {
                                                "userName": ["jack123", "jill123", "admin123"]
                                            }
                                        }
                                    },
                                    "firstName": {
                                        "url": "/users",
                                        "searchCriteria": {
                                            "pageSize": "10",
                                            "pageNumber": "0",
                                            "searchMap": {
                                                "firstName": ["firstName 1", "firstName 2", "firstName 3"]
                                            }
                                        }
                                    }
                                };
                            }
							//get widget data
							WidgetService.getWidgetData(widgetInstanceObj).then(function (widgetData) {
								if (widgetData) {
									widgetInstanceObj['data'] = widgetData;
								}
								WidgetService.putWidgetInstance(widgetInstanceObj);
							}, function (error) {
								log.error("error: " + error);
							});
						}
					});
					return promise;
				}
			},
			getExistingWidgetDefinition: function (widgetName) {
				var localAuthenticatedInfo = AuthTokenService.getAuthToken();
				http.defaults.headers.common['Authentication-token'] = localAuthenticatedInfo.sessionToken;
				var restUrl = 'widgets/' + widgetName + '/definition';
				return restApiService.getOne(restUrl, null, null).then(function (response) {
					return response;
				});
			}
		};
	}]);
