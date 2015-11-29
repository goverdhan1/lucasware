'use strict';

describe('MakeHomeCanvasModalController related Tests', function () {
    // local variables
    var localMakeHomeCanvasModalController;
    var localModalInstance;
    var localScope;
    var rootScope;
    var localCanvasService;
    var localStoreService;
    var mockUserConfig = {
        "userName": "jack123",
        "userId": 100,
        "permissionSet": ["manage-canvas", "clone-canvas", "delete-user", "disable-user"],
        "homeCanvas": {
            "canvasName": "Hazmat",
            "canvasId": "201",
            "canvasType": "saved",
            "isDataFetched": false,
            "widgetInstanceList": []
        },
        "activeCanvas": {
            "canvasName": "Work Execution",
            "canvasId": "221",
            "canvasType": "company",
            "isDataFetched": false,
            "widgetInstanceList": []
        },
        "seeHomeCanvasIndicator": true,
        "openCanvases": [{
            "canvasName": "abc",
            "canvasId": "231",
            "canvasType": "saved",
            "isDataFetched": false,
            "widgetInstanceList": []
        }, {
            "canvasName": "Hazmat",
            "canvasId": "201",
            "canvasType": "saved",
            "isDataFetched": false,
            "widgetInstanceList": []
        }, {
            "canvasName": "Work Execution",
            "canvasId": "221",
            "canvasType": "company",
            "isDataFetched": false,
            "widgetInstanceList": []
        }, {
            "canvasName": "xyz",
            "canvasId": "235",
            "canvasType": "company",
            "isDataFetched": false,
            "widgetInstanceList": []
        }],
        "menuPermissions": {"manage-canvas": "Manage Canvas", "clone-canvas": "Clone Canvas"}
    };
    var mockFavoriteCanvasListUpdated = [
        {
            "canvasName": "abc",
            "canvasId": "231",
            "canvasType": "saved",
            "isDataFetched": false,
            "widgetInstanceList": []
        },
        {
            "canvasName": "Hazmat",
            "canvasId": "201",
            "canvasType": "saved",
            "isDataFetched": true,
            "widgetInstanceList": [
                {
                    "data": {
                        "groupPermissions": {
                            "warehouse-manager": [
                                "create-assignment",
                                "view-report-productivity",
                                "configure-location",
                                "create-canvas",
                                "delete-canvas"
                            ],
                            "basic-authenticated-user": [
                                "authenticated-user"
                            ],
                            "supervisor": [
                                "user-management-canvas-view",
                                "user-management-canvas-edit",
                                "pick-monitoring-canvas-view",
                                "pick-monitoring-canvas-edit",
                                "user-list-download-excel",
                                "user-list-download-pdf",
                                "create-canvas",
                                "delete-user",
                                "disable-user",
                                "disable-user",
                                "enable-user",
                                "edit-multi-user",
                                "group-maintenance-create",
                                "group-maintenance-edit",
                                "create-product",
                                "view-product",
                                "edit-product",
                                "delete-product"
                            ],
                            "admin": [
                                "create-canvas",
                                "disable-user",
                                "user-management-canvas-view",
                                "pick-monitoring-canvas-view",
                                "authenticated-user"
                            ],
                            "picker": [
                                "create-assignment",
                                "authenticated-user",
                                "user-management-canvas-view",
                                "pick-monitoring-canvas-view",
                                "disable-user"
                            ]
                        },
                        "availablePermissions": [
                            "create-canvas",
                            "disable-user",
                            "user-management-canvas-view",
                            "pick-monitoring-canvas-view",
                            "authenticated-user",
                            "create-assignment",
                            "user-management-canvas-edit",
                            "pick-monitoring-canvas-edit",
                            "user-list-download-excel",
                            "user-list-download-pdf",
                            "delete-user",
                            "disable-user",
                            "enable-user",
                            "edit-multi-user",
                            "group-maintenance-create",
                            "group-maintenance-edit",
                            "create-product",
                            "view-product",
                            "edit-product",
                            "delete-product",
                            "view-report-productivity",
                            "configure-location",
                            "create-canvas",
                            "delete-canvas"
                        ]
                    },
                    "id": null,
                    "widgetDefinition": {
                        "defaultViewConfig": {
                            "listensForList": [
                                "groupName"
                            ],
                            "width": 1100,
                            "height": 500,
                            "anchor": [
                                2,
                                2
                            ],
                            "zindex": 0,
                            "reactToList": [],
                            "dateFormat": {
                                "selectedFormat": "mm-dd-yyyy",
                                "availableFormats": [
                                    "mm-dd-yyyy",
                                    "MMM dd, yyyy",
                                    "dd-mm-yyyy"
                                ]
                            }
                        },
                        "broadcastList": [
                            "groupName"
                        ],
                        "dataURL": {
                            "searchCriteria": {
                                "searchMap": {},
                                "sortMap": {
                                    "permissionGroupName": "ASC"
                                },
                                "pageSize": "2147483647",
                                "pageNumber": "0"
                            },
                            "url": "/users/groups/permissions"
                        },
                        "reactToMap": {
                            "groupName": {
                                "searchCriteria": {
                                    "searchMap": {
                                        "permissionGroupName": [
                                            "$groupName"
                                        ]
                                    },
                                    "sortMap": {
                                        "permissionGroupName": "ASC"
                                    },
                                    "pageSize": "2147483647",
                                    "pageNumber": "0"
                                },
                                "url": "/users/groups/permissions"
                            }
                        },
                        "shortName": "GroupMaintenance",
                        "defaultData": {},
                        "title": "Group Maintenance",
                        "id": 12,
                        "widgetActionConfig": {
                            "group-maintenance-create": true,
                            "group-maintenance-edit": true
                        },
                        "name": "group-maintenance-widget"
                    },
                    "actualViewConfig": {
                        "listensForList": [
                            "groupName"
                        ],
                        "width": 1100,
                        "height": 500,
                        "anchor": [
                            2,
                            2
                        ],
                        "zindex": 0,
                        "reactToList": [],
                        "dateFormat": {
                            "selectedFormat": "mm-dd-yyyy",
                            "availableFormats": [
                                "mm-dd-yyyy",
                                "MMM dd, yyyy",
                                "dd-mm-yyyy"
                            ]
                        }
                    },
                    "updateWidget": true,
                    "clientId": 101,
                    "isMaximized": false
                }
            ]
        },
        {
            "canvasName": "Work Execution",
            "canvasId": "221",
            "canvasType": "company",
            "isDataFetched": true,
            "widgetInstanceList": [
                {
                    "data": {
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
                        "zindex": 0,
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
                    "clientId": 102,
                    "isMaximized": false
                }
            ]
        },
        {
            "canvasName": "xyz",
            "canvasId": "235",
            "canvasType": "company",
            "isDataFetched": true,
            "widgetInstanceList": [
                {
                    "data": [],
                    "id": null,
                    "widgetDefinition": {
                        "id": 8,
                        "name": "create-or-edit-user-form-widget",
                        "shortName": "CreateUser",
                        "widgetActionConfig": {
                            "create-assignment": false,
                            "view-report-productivity": false,
                            "configure-location": false,
                            "delete-canvas": false,
                            "create-canvas": false
                        },
                        "definitionData": {
                            "User": [
                                {
                                    "startDate": "2014-12-19T20:12:06.521Z"
                                }
                            ],
                            "handheldScreenLanguageList": [
                                "ENGLISH",
                                "FRENCH",
                                "GERMAN"
                            ],
                            "amdScreenLanguageList": [
                                "ENGLISH",
                                "FRENCH",
                                "GERMAN"
                            ],
                            "jenniferToUserLanguageList": [
                                "ENGLISH",
                                "FRENCH",
                                "GERMAN"
                            ],
                            "userToJenniferLanguageList": [
                                "ENGLISH",
                                "FRENCH",
                                "GERMAN"
                            ]
                        },
                        "reactToMap": {
                            "lastName": {
                                "url": "/users",
                                "searchCriteria": {
                                    "pageSize": "10",
                                    "pageNumber": "0",
                                    "searchMap": {
                                        "lastName": [
                                            "jack123",
                                            "jill123",
                                            "admin123"
                                        ]
                                    }
                                }
                            },
                            "Wave": {
                                "url": "/users",
                                "searchCriteria": {
                                    "pageSize": "10",
                                    "pageNumber": "0",
                                    "searchMap": {
                                        "Wave": [
                                            "Wave 1",
                                            "Wave 2",
                                            "Wave 3"
                                        ]
                                    }
                                }
                            }
                        },
                        "broadcastList": [
                            "userName"
                        ],
                        "defaultViewConfig": {
                            "dateFormat": {
                                "selectedFormat": "mm-dd-yyyy",
                                "availableFormats": [
                                    "mm-dd-yyyy",
                                    "MMM dd, yyyy",
                                    "dd-mm-yyyy"
                                ]
                            },
                            "isMaximized": false,
                            "listensForList": [
                                "userName",
                                "Wave",
                                "Score"
                            ],
                            "width": 567,
                            "height": 240,
                            "anchor": [
                                275,
                                295
                            ],
                            "zindex": 0,
                            "reactToList": []
                        },
                        "broadcastMap": [
                            "userName",
                            "firstName"
                        ]
                    },
                    "actualViewConfig": {
                        "dateFormat": {
                            "selectedFormat": "mm-dd-yyyy",
                            "availableFormats": [
                                "mm-dd-yyyy",
                                "MMM dd, yyyy",
                                "dd-mm-yyyy"
                            ]
                        },
                        "isMaximized": false,
                        "listensForList": [
                            "userName",
                            "Wave",
                            "Score"
                        ],
                        "width": 567,
                        "height": 240,
                        "anchor": [
                            82,
                            42
                        ],
                        "zindex": 0,
                        "reactToList": []
                    },
                    "updateWidget": true,
                    "clientId": 101,
                    "isMaximized": false
                },
                {
                    "data": {
                        "grid": {
                            "1": {
                                "values": [
                                    "",
                                    "2014-01-01T00:00:00.000Z",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    ""
                                ]
                            },
                            "2": {
                                "values": [
                                    "admin@user.com",
                                    "user1@normal.com",
                                    "user2@normal.com",
                                    "NULL",
                                    "NULL",
                                    "NULL",
                                    "NULL",
                                    "NULL",
                                    "NULL",
                                    "NULL"
                                ]
                            },
                            "3": {
                                "values": [
                                    "",
                                    "1072",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    ""
                                ]
                            },
                            "4": {
                                "values": [
                                    "Admin",
                                    "Jill",
                                    "Jack",
                                    "dummy-firstName",
                                    "dummy-firstName",
                                    "dummy-firstName",
                                    "dummy-firstName",
                                    "dummy-firstName",
                                    "dummy-firstName",
                                    "dummy-firstName"
                                ]
                            },
                            "5": {
                                "values": [
                                    "",
                                    "lucas",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    ""
                                ]
                            },
                            "6": {
                                "values": [
                                    "User",
                                    "User1",
                                    "User2",
                                    "dummy-LastName",
                                    "dummy-LastName",
                                    "dummy-LastName",
                                    "dummy-LastName",
                                    "dummy-LastName",
                                    "dummy-LastName",
                                    "dummy-LastName"
                                ]
                            },
                            "7": {
                                "values": [
                                    "",
                                    "1234567890",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    ""
                                ]
                            },
                            "8": {
                                "values": [
                                    "false",
                                    "false",
                                    "false",
                                    "false",
                                    "false",
                                    "false",
                                    "false",
                                    "false",
                                    "false",
                                    "false"
                                ]
                            },
                            "9": {
                                "values": [
                                    "2014-01-01T00:00:00.000Z",
                                    "2014-01-01T00:00:00.000Z",
                                    "2014-01-01T00:00:00.000Z",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    ""
                                ]
                            },
                            "10": {
                                "values": [
                                    "",
                                    "MRS",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    ""
                                ]
                            },
                            "11": {
                                "values": [
                                    "1",
                                    "2",
                                    "3",
                                    "4",
                                    "5",
                                    "6",
                                    "7",
                                    "8",
                                    "9",
                                    "10"
                                ]
                            },
                            "12": {
                                "values": [
                                    "admin123",
                                    "jill123",
                                    "jack123",
                                    "dummy-username6",
                                    "dummy-username7",
                                    "dummy-username8",
                                    "dummy-username9",
                                    "dummy-username10",
                                    "dummy-username11",
                                    "dummy-username12"
                                ]
                            },
                            "13": {
                                "values": [
                                    null,
                                    "ENGLISH",
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null
                                ]
                            },
                            "14": {
                                "values": [
                                    null,
                                    "FRENCH",
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null
                                ]
                            },
                            "15": {
                                "values": [
                                    null,
                                    "GERMAN",
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null
                                ]
                            },
                            "16": {
                                "values": [
                                    null,
                                    "ENGLISH",
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null
                                ]
                            }
                        },
                        "searchCriteria": {
                            "pageNumber": "0",
                            "pageSize": "10",
                            "sortMap": {
                                "emailAddress": "ASC",
                                "firstName": "ASC",
                                "lastName": "ASC",
                                "startDate": "ASC",
                                "userName": "ASC",
                                "j2uLanguage": "ASC",
                                "u2jLanguage": "ASC",
                                "hhLanguage": "ASC",
                                "amdLanguage": "ASC"
                            },
                            "searchMap": {}
                        }
                    },
                    "id": null,
                    "widgetDefinition": {
                        "name": "search-user-grid-widget",
                        "id": 9,
                        "shortName": "SearchUser",
                        "title": "Search User",
                        "reactToMap": {
                            "lastName": {
                                "url": "/users",
                                "searchCriteria": {
                                    "pageSize": "10",
                                    "pageNumber": "0",
                                    "searchMap": {
                                        "lastName": [
                                            "jack123",
                                            "jill123",
                                            "admin123"
                                        ]
                                    }
                                }
                            },
                            "Score": {
                                "url": "/users",
                                "searchCriteria": {
                                    "pageSize": "10",
                                    "pageNumber": "0",
                                    "searchMap": {
                                        "Wave": [
                                            "Score 1",
                                            "Score 2",
                                            "Score 3"
                                        ]
                                    }
                                }
                            }
                        },
                        "widgetActionConfig": {
                            "delete-user": true,
                            "disable-user": true
                        },
                        "broadcastList": [
                            "userName",
                            "hostLogin"
                        ],
                        "defaultData": {},
                        "defaultViewConfig": {
                            "dateFormat": {
                                "selectedFormat": "mm-dd-yyyy",
                                "availableFormats": [
                                    "mm-dd-yyyy",
                                    "MMM dd, yyyy",
                                    "dd-mm-yyyy"
                                ]
                            },
                            "gridColumns": {
                                "1": {
                                    "name": "Birth Date",
                                    "fieldName": "birthDate",
                                    "visible": false,
                                    "order": "1",
                                    "sortOrder": "1"
                                },
                                "2": {
                                    "name": "Email Address",
                                    "fieldName": "emailAddress",
                                    "visible": true,
                                    "order": "2",
                                    "sortOrder": "1"
                                },
                                "3": {
                                    "name": "Employee Id",
                                    "fieldName": "employeeId",
                                    "visible": false,
                                    "order": "3",
                                    "sortOrder": "1"
                                },
                                "4": {
                                    "name": "First Name",
                                    "fieldName": "firstName",
                                    "visible": true,
                                    "order": "4",
                                    "sortOrder": "1"
                                },
                                "5": {
                                    "name": "Host Login",
                                    "fieldName": "hostLogin",
                                    "visible": false,
                                    "order": "5",
                                    "sortOrder": "1"
                                },
                                "6": {
                                    "name": "Last Name",
                                    "fieldName": "lastName",
                                    "visible": true,
                                    "order": "6",
                                    "sortOrder": "1"
                                },
                                "7": {
                                    "name": "Mobile Number",
                                    "fieldName": "mobileNumber",
                                    "visible": false,
                                    "order": "7",
                                    "sortOrder": "1"
                                },
                                "8": {
                                    "name": "Needs Authentication",
                                    "fieldName": "needsAuthentication",
                                    "visible": false,
                                    "order": "8",
                                    "sortOrder": "1"
                                },
                                "9": {
                                    "name": "Start Date",
                                    "fieldName": "startDate",
                                    "visible": true,
                                    "order": "10",
                                    "sortOrder": "1"
                                },
                                "10": {
                                    "name": "Title",
                                    "fieldName": "title",
                                    "visible": false,
                                    "order": "10",
                                    "sortOrder": "1"
                                },
                                "11": {
                                    "name": "User Id",
                                    "fieldName": "userId",
                                    "visible": false,
                                    "order": "11",
                                    "sortOrder": "1"
                                },
                                "12": {
                                    "name": "User Name",
                                    "fieldName": "userName",
                                    "visible": true,
                                    "order": "13",
                                    "sortOrder": "1"
                                },
                                "13": {
                                    "name": "J2uLanguage",
                                    "fieldName": "j2uLanguage",
                                    "visible": true,
                                    "order": "13",
                                    "sortOrder": "1"
                                },
                                "14": {
                                    "name": "U2jLanguage",
                                    "fieldName": "u2jLanguage",
                                    "visible": true,
                                    "order": "14",
                                    "sortOrder": "1"
                                },
                                "15": {
                                    "name": "HhLanguage",
                                    "fieldName": "hhLanguage",
                                    "visible": true,
                                    "order": "15",
                                    "sortOrder": "1"
                                },
                                "16": {
                                    "name": "AmdLanguage",
                                    "fieldName": "amdLanguage",
                                    "visible": true,
                                    "order": "16",
                                    "sortOrder": "1"
                                }
                            },
                            "listensForList": [
                                "Score"
                            ],
                            "width": 485,
                            "height": 375,
                            "anchor": [
                                25,
                                785
                            ],
                            "zindex": 0,
                            "reactToList": [
                                "hostLogin"
                            ]
                        },
                        "dataURL": {
                            "searchCriteria": {
                                "pageNumber": "0",
                                "pageSize": "10",
                                "searchMap": {},
                                "sortMap": {}
                            },
                            "url": "/users/userlist/search"
                        },
                        "broadcastMap": [
                            "userName",
                            "firstName",
                            "lastName"
                        ]
                    },
                    "actualViewConfig": {
                        "dateFormat": {
                            "selectedFormat": "mm-dd-yyyy",
                            "availableFormats": [
                                "mm-dd-yyyy",
                                "MMM dd, yyyy",
                                "dd-mm-yyyy"
                            ]
                        },
                        "gridColumns": {
                            "1": {
                                "name": "Birth Date",
                                "fieldName": "birthDate",
                                "visible": false,
                                "order": "1",
                                "sortOrder": "1"
                            },
                            "2": {
                                "name": "Email Address",
                                "fieldName": "emailAddress",
                                "visible": true,
                                "order": "2",
                                "sortOrder": "1"
                            },
                            "3": {
                                "name": "Employee Id",
                                "fieldName": "employeeId",
                                "visible": false,
                                "order": "3",
                                "sortOrder": "1"
                            },
                            "4": {
                                "name": "First Name",
                                "fieldName": "firstName",
                                "visible": true,
                                "order": "4",
                                "sortOrder": "1"
                            },
                            "5": {
                                "name": "Host Login",
                                "fieldName": "hostLogin",
                                "visible": false,
                                "order": "5",
                                "sortOrder": "1"
                            },
                            "6": {
                                "name": "Last Name",
                                "fieldName": "lastName",
                                "visible": true,
                                "order": "6",
                                "sortOrder": "1"
                            },
                            "7": {
                                "name": "Mobile Number",
                                "fieldName": "mobileNumber",
                                "visible": false,
                                "order": "7",
                                "sortOrder": "1"
                            },
                            "8": {
                                "name": "Needs Authentication",
                                "fieldName": "needsAuthentication",
                                "visible": false,
                                "order": "8",
                                "sortOrder": "1"
                            },
                            "9": {
                                "name": "Start Date",
                                "fieldName": "startDate",
                                "visible": true,
                                "order": "10",
                                "sortOrder": "1"
                            },
                            "10": {
                                "name": "Title",
                                "fieldName": "title",
                                "visible": false,
                                "order": "10",
                                "sortOrder": "1"
                            },
                            "11": {
                                "name": "User Id",
                                "fieldName": "userId",
                                "visible": false,
                                "order": "11",
                                "sortOrder": "1"
                            },
                            "12": {
                                "name": "User Name",
                                "fieldName": "userName",
                                "visible": true,
                                "order": "13",
                                "sortOrder": "1"
                            },
                            "13": {
                                "name": "J2uLanguage",
                                "fieldName": "j2uLanguage",
                                "visible": true,
                                "order": "13",
                                "sortOrder": "1"
                            },
                            "14": {
                                "name": "U2jLanguage",
                                "fieldName": "u2jLanguage",
                                "visible": true,
                                "order": "14",
                                "sortOrder": "1"
                            },
                            "15": {
                                "name": "HhLanguage",
                                "fieldName": "hhLanguage",
                                "visible": true,
                                "order": "15",
                                "sortOrder": "1"
                            },
                            "16": {
                                "name": "AmdLanguage",
                                "fieldName": "amdLanguage",
                                "visible": true,
                                "order": "16",
                                "sortOrder": "1"
                            }
                        },
                        "listensForList": [
                            "Score"
                        ],
                        "width": 485,
                        "height": 375,
                        "anchor": [
                            25,
                            785
                        ],
                        "zindex": 0,
                        "reactToList": [
                            "hostLogin"
                        ]
                    },
                    "updateWidget": true,
                    "clientId": 102,
                    "isMaximized": false,
                    "nggridSpecific": {
                        "sortInfo": {
                            "fields": [
                                "emailAddress",
                                "firstName",
                                "lastName",
                                "startDate",
                                "userName",
                                "j2uLanguage",
                                "u2jLanguage",
                                "hhLanguage",
                                "amdLanguage"
                            ],
                            "directions": [
                                "asc",
                                "asc",
                                "asc",
                                "asc",
                                "asc",
                                "asc",
                                "asc",
                                "asc",
                                "asc"
                            ]
                        },
                        "colDefs": [
                            {
                                "field": "emailAddress",
                                "displayName": "Email Address",
                                "visible": true
                            },
                            {
                                "field": "firstName",
                                "displayName": "First Name",
                                "visible": true
                            },
                            {
                                "field": "lastName",
                                "displayName": "Last Name",
                                "visible": true
                            },
                            {
                                "field": "startDate",
                                "displayName": "Start Date",
                                "visible": true
                            },
                            {
                                "field": "userName",
                                "displayName": "User Name",
                                "visible": true
                            },
                            {
                                "field": "j2uLanguage",
                                "displayName": "J2uLanguage",
                                "visible": true
                            },
                            {
                                "field": "u2jLanguage",
                                "displayName": "U2jLanguage",
                                "visible": true
                            },
                            {
                                "field": "hhLanguage",
                                "displayName": "HhLanguage",
                                "visible": true
                            },
                            {
                                "field": "amdLanguage",
                                "displayName": "AmdLanguage",
                                "visible": true
                            }
                        ]
                    }
                }
            ]
        }
    ];

    // initial configuration
    beforeEach(module('amdApp'));

    // store the injectables
    beforeEach(inject(function ($rootScope, $templateCache, $controller, $modal, CanvasService, LocalStoreService, $stateParams, $httpBackend) {
        $templateCache.put('views/modals/make-home-canvas.html', '');
        localModalInstance = $modal.open({
            templateUrl: 'views/modals/make-home-canvas.html',
            backdrop: 'static'
        });
        $httpBackend.when('GET').respond({});
        rootScope = $rootScope;
        localScope = $rootScope.$new();
        localCanvasService = CanvasService;
        localStoreService = LocalStoreService;
        localStoreService.addLSItem(null, 'UserConfig', mockUserConfig);
        localStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', mockFavoriteCanvasListUpdated);
        $stateParams.canvasId = '231';
        localMakeHomeCanvasModalController = $controller('MakeHomeCanvasModalController', {
            $scope: localScope,
            $modalInstance: localModalInstance
        });
    }));

    it('handle closeModal() method ', function () {
        spyOn(localModalInstance, 'close').andCallThrough();
        localScope.$digest();
        localScope.closeModal();
        expect(localModalInstance.close).toHaveBeenCalled();
    });

    it('handle makeHomeCanvas() method ', function () {
        spyOn(localScope, 'closeModal').andCallThrough();
        spyOn(localModalInstance, 'close').andCallThrough();
        spyOn(localCanvasService, 'makeHomeCanvas').andCallThrough();
        localScope.$digest();
        localScope.makeHomeCanvas();
        localScope.$digest();
        expect(localCanvasService.makeHomeCanvas).toHaveBeenCalled();
        expect(localScope.closeModal).toHaveBeenCalled();
        var userConfig = localStoreService.getLSItem('UserConfig');
        expect(userConfig.homeCanvas.canvasId).toBe('231');
        expect(userConfig.homeCanvas.canvasName).toBe('abc');
        expect(userConfig.homeCanvas.canvasType).toBe('saved');
        expect(userConfig.homeCanvas.isDataFetched).toBe(false);
        expect(userConfig.homeCanvas.widgetInstanceList).toEqual([]);
    });
});
