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

describe('CanvasDetailController Unit Tests', function() {

    // Global vars
    var rootScope;
    var localScope = null;
    var mockLocalStoreService = null;
    var localStoreService = null;
    var localUtilsService = null;
    var localController = null;
    var localWidgetService = null;
    var localHomeCanvasListService = null;
    var mockWidgetDetails = {
        "updateWidget": false,
        "clientId": 100,
        "isMaximized": false
    };

    var mockFavoriteCanvasList = [
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
    var mockUserConfigData = {
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
            "canvasName": "Hazmat",
            "canvasId": "201",
            "canvasType": "saved",
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

    // Global test setup
    beforeEach(module('amdApp', function($translateProvider) {
        $translateProvider.translations('en', {
            "language-code" : "EN"
        }, 'fr', {
            "language-code" : "fr"
        }, 'de', {
            "language-code" : "de"
        }).preferredLanguage('en');
        $translateProvider.useLoader('LocaleLoader');
    }));


    beforeEach(inject(function($controller, $rootScope, UtilsService, HomeCanvasListService, LocalStoreService, WidgetService , $httpBackend) {
        rootScope = $rootScope;
        localScope = $rootScope.$new();

        LocalStoreService.addLSItem(null, 'FavoriteCanvasList', mockFavoriteCanvasList);
        LocalStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', mockFavoriteCanvasList);

        localWidgetService = WidgetService;
        localUtilsService = UtilsService;

        spyOn(localUtilsService, 'triggerWindowResize').andReturn();

        $httpBackend.when("GET").respond({});


        spyOn(localWidgetService, 'updateFavoriteCanvasListLocalStorage').andCallThrough();
        //spyOn(localWidgetService, 'addProperty').andCallThrough();

        localStoreService = LocalStoreService;
        localHomeCanvasListService = HomeCanvasListService;
        localWidgetService.updateFavoriteCanvasListLocalStorage(mockWidgetDetails);
        //localWidgetService.addProperty('updateWidget', '123');

        localStoreService.addLSItem(null, 'UserConfig', mockUserConfigData);

        localController = $controller('CanvasDetailController', {
            $scope : localScope,
            UtilsService : localUtilsService,
            LocalStoreService : localStoreService,
            HomeCanvasListService : localHomeCanvasListService,
            WidgetService : localWidgetService
        });

    }));


    // Dependency Injection Specs
    describe('dependency injection tests ', function() {

        beforeEach(inject(function($controller) {
            localController = $controller('CanvasDetailController', {
                    $scope : localScope,
                    UtilsService : localUtilsService,
                    LocalStoreService : localStoreService,
                    HomeCanvasListService : localHomeCanvasListService,
                    WidgetService : localWidgetService
            });
        }));

        it('should inject LocalController', function() {
            expect(localController).toBeDefined();
        });

        it('should inject localScope', function() {
            expect(localScope).toBeDefined();
        });

        it('should inject HomeCanvasListService', function() {
            expect(localHomeCanvasListService).toBeDefined();
        });

        it('should inject LocalStoreService', function() {
            expect(localStoreService).toBeDefined();
        });

        it('should inject localWidgetService', function() {
            expect(localWidgetService).toBeDefined();
        });

        /*it("Should handle addProperty method", function () {
            expect(localWidgetService.addProperty).toHaveBeenCalled();
        });*/

        it("Should handle updateFavoriteCanvasListLocalStorage method", function () {
            expect(localWidgetService.updateFavoriteCanvasListLocalStorage).toHaveBeenCalled();
        });

    });

    //Unit Specs
    describe('Canvas Details Spec test ', function() {

        beforeEach(inject(function ($controller) {
            localController = $controller('CanvasDetailController', {
                $scope: localScope,
                LocalStoreService: localStoreService,
                HomeCanvasListService: localHomeCanvasListService,
                UtilsService: localUtilsService,
                WidgetService: localWidgetService
            });

            spyOn(localScope, '$broadcast').andCallThrough();
            spyOn(localStoreService, 'addLSItem').andCallThrough();
            spyOn(localHomeCanvasListService, 'saveActiveAndOpenCanvases').andCallThrough();
        }));

        xit("Should handle $stateChangeSuccess event", function () {
            var toState = {
                name: "canvases.detail",
                url: "/{canvasId:[0-9]{1,4}}"
            }
            var toParams = {
                canvasId: "231"
            };
            var fromState = {
                name: "canvases.detail",
                url: "/{canvasId:[0-9]{1,4}}"
            };
            var fromParams = {
                canvasId: "201"
            };

            localScope.$broadcast("$stateChangeSuccess", toState, toParams, fromState, fromParams);
            localScope.$digest();
            expect(localScope.$broadcast).toHaveBeenCalled();
            expect(localStoreService.addLSItem).toHaveBeenCalled();
            expect(localHomeCanvasListService.saveActiveAndOpenCanvases).toHaveBeenCalled();
        });

        xit("Should handle $stateChangeSuccess event for empty favoriteCanvasList", function () {
            var toState = {
                name: "canvases.detail",
                url: "/{canvasId:[0-9]{1,4}}"
            }
            var toParams = {
                canvasId: "231"
            };
            var fromState = {
                name: "canvases.detail",
                url: "/{canvasId:[0-9]{1,4}}"
            };
            var fromParams = {
                canvasId: "201"
            };

            localStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', []);

            localScope.$broadcast("$stateChangeSuccess", toState, toParams, fromState, fromParams);
            localScope.$digest();
            expect(localScope.$broadcast).toHaveBeenCalled();
            expect(localHomeCanvasListService.saveActiveAndOpenCanvases).toHaveBeenCalledWith({
                "userName": "jack123",
                "userId": 100,
                "permissionSet": ["manage-canvas", "clone-canvas", "delete-user", "disable-user"],
                "homeCanvas": {},
                "activeCanvas": {},
                "seeHomeCanvasIndicator": true,
                "openCanvases": [],
                "menuPermissions": {"manage-canvas": "Manage Canvas", "clone-canvas": "Clone Canvas"}
            })
        });

    });

    describe('Canvas Details Add New Widget test', function() {

        // nested scope.$on events
        it("Should handle AddNewWidget event", function() {
            localScope.widgetInstanceListforCanvas = [];
            // emit
            rootScope.$broadcast("addNewWidget", {
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
            });
            // assert widgetinstancelist
            expect(localScope.widgetInstanceListforCanvas).toEqual([{
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
            }]);
        });

    });

    describe('Canvas Details Update Widget ', function() {

        beforeEach(inject(function($controller, $templateCache, LocalStoreService) {

            localController = $controller('CanvasDetailController', {
                $scope : localScope,
                LocalStoreService : localStoreService,
                HomeCanvasListService : localHomeCanvasListService,
                UtilsService : localUtilsService,
                WidgetService : localWidgetService
            });

            localStoreService.addLSItem(null, 'FavoriteCanvasList', mockFavoriteCanvasList);
            localStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', mockFavoriteCanvasList);

            // Load the .html files into the templateCache,
            $templateCache.put('views/indexpage/index-tmpl.html', '');
            $templateCache.put('views/indexpage/logo.html', '');
            $templateCache.put('views/indexpage/login.html', '');
            $templateCache.put('views/indexpage/locale.html', '');
            $templateCache.put('views/indexpage/content1.html', '');
            $templateCache.put('views/indexpage/content2.html', '');
            $templateCache.put('views/indexpage/footer.html', '');
            //spyOn(localScope, '$broadcast').andCallThrough();

        }));

        xit("Should handle UpdateWidget event", function () {
            spyOn(localScope, "$on").andCallThrough();
            // nested scope.$on events
            localScope.$broadcast('UpdateWidget', mockWidgetDetails);
            localScope.$digest();
            expect(mockWidgetDetails.updateWidget).toBe(true);
        });

    });


    // Handling destroy method
    describe('Should call the destroy method', function() {

        beforeEach(inject(function($controller) {

            localController = $controller('CanvasDetailController', {
                $scope : localScope,
                LocalStoreService : localStoreService,
                HomeCanvasListService : localHomeCanvasListService,
                UtilsService : localUtilsService,
                WidgetService : localWidgetService
            });

            localStoreService.addLSItem(null, 'FavoriteCanvasList', mockFavoriteCanvasList);
            localStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', mockFavoriteCanvasList);

        }));

        xit('Should call the destroy method', function() {
            spyOn(localScope, "$broadcast").andCallThrough();
            var toState = {
                name: "canvases.detail",
                url: "/{canvasId:[0-9]{1,4}}"
            }
            var toParams = {
                canvasId: "231"
            };
            var fromState = {
                name: "canvases.detail",
                url: "/{canvasId:[0-9]{1,4}}"
            };
            var fromParams = {
                canvasId: "201"
            };

            localScope.$broadcast("$stateChangeSuccess", toState, toParams, fromState, fromParams);
            localScope.$broadcast("$destroy");
            localScope.$digest();
            expect(localScope.$broadcast).toHaveBeenCalled();
            expect(localScope.$broadcast).toHaveBeenCalledWith("$destroy");

        });
    });

    it('Should handle the "min-width" in getStyle() method', function() {
        var styleObj,
            widgetInstance = {
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
                        "width": 567,
                        "height": 240,
                        "anchor": [275, 295],
                        "zindex": 0,
                        "dateFormat": {
                            "selectedFormat": "mm-dd-yyyy",
                            "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                        },
                        "isMaximized": false,
                        "listensForList": ["userName", "Wave", "Score"],
                        "reactToList": [],
                        "minimumWidth": 300,
                        "minimumHeight": 480,
                        "originalMinimumWidth": 300,
                        "originalMinimumHeight": 480
                    },
                    "broadcastMap": ["userName", "firstName"]
                },
                "actualViewConfig": {
                    "width": 567,
                    "height": 240,
                    "anchor": [275, 295],
                    "zindex": 0,
                    "dateFormat": {
                        "selectedFormat": "mm-dd-yyyy",
                        "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                    },
                    "isMaximized": false,
                    "listensForList": ["userName", "Wave", "Score"],
                    "reactToList": [],
                    "minimumWidth": 300,
                    "minimumHeight": 480,
                    "originalMinimumWidth": 300,
                    "originalMinimumHeight": 480
                },
                "updateWidget": true,
                "clientId": 100,
                "isMaximized": false
            };
        
        styleObj = localScope.getStyle(widgetInstance);
        expect(styleObj["min-width"]).toBe(300);

        // append the uiViewContentCanvas to body
        var uiViewContentCanvas = $("<div class='uiViewContentCanvas' />").appendTo("body");
        uiViewContentCanvas.width(500);
        widgetInstance.actualViewConfig.resizedMinimumWidth = 400;
        
        styleObj = localScope.getStyle(widgetInstance);
        // now it expects the size to be same as resizedMinimumWidth
        expect(styleObj["min-width"]).toBe(400);

        uiViewContentCanvas.width(1000);
        widgetInstance.actualViewConfig.originalResizedMinimumWidth = 800;
        
        styleObj = localScope.getStyle(widgetInstance);
        // now it expects the size to be same as originalResizedMinimumWidth
        expect(styleObj["min-width"]).toBe(800);

        uiViewContentCanvas.width(500);
        styleObj = localScope.getStyle(widgetInstance);
        // now it expects the size to be 500 as widget width 800 does not fit in uiViewContentCanvas width of 500
        expect(styleObj["min-width"]).toBe(500);
    });

    it('Should handle the "min-height" in getStyle() method', function() {
        var styleObj,
            widgetInstance = {
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
                        "width": 567,
                        "height": 240,
                        "anchor": [275, 295],
                        "zindex": 0,
                        "dateFormat": {
                            "selectedFormat": "mm-dd-yyyy",
                            "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                        },
                        "isMaximized": false,
                        "listensForList": ["userName", "Wave", "Score"],
                        "reactToList": [],
                        "minimumWidth": 300,
                        "minimumHeight": 480,
                        "originalMinimumWidth": 300,
                        "originalMinimumHeight": 480
                    },
                    "broadcastMap": ["userName", "firstName"]
                },
                "actualViewConfig": {
                    "width": 567,
                    "height": 240,
                    "anchor": [275, 295],
                    "zindex": 0,
                    "dateFormat": {
                        "selectedFormat": "mm-dd-yyyy",
                        "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                    },
                    "isMaximized": false,
                    "listensForList": ["userName", "Wave", "Score"],
                    "reactToList": [],
                    "minimumWidth": 300,
                    "minimumHeight": 480,
                    "originalMinimumWidth": 300,
                    "originalMinimumHeight": 480
                },
                "updateWidget": true,
                "clientId": 100,
                "isMaximized": false
            };
        
        styleObj = localScope.getStyle(widgetInstance);
        expect(styleObj["min-height"]).toBe(480);

        
        widgetInstance.actualViewConfig.resizedMinimumHeight = 500;
        styleObj = localScope.getStyle(widgetInstance);
        // now it expects the size to be same as resizedMinimumHeight
        expect(styleObj["min-height"]).toBe(500);

        widgetInstance.actualViewConfig.originalResizedMinimumHeight = 800;
        styleObj = localScope.getStyle(widgetInstance);
        // now it expects the size to be same as originalResizedMinimumHeight
        expect(styleObj["min-height"]).toBe(800);
    });
    
    // handle the event 'RemoveWidget'
    it('Should handle RemoveWidget event', function() {
        localScope.widgetInstanceListforCanvas = [{
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

        // emit
        rootScope.$emit("RemoveWidget", 0);
       
        // assert widgetinstancelist
        expect(localScope.widgetInstanceListforCanvas).toEqual([]);
    });
});