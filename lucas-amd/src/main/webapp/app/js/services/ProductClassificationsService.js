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
amdApp.factory('ProductClassificationsService', ['$q',
    function($q) {
        return {
            getClassifications: function() {
                var def = $q.defer();
                // Resolve the promise
                def.resolve({
                    "grid": {
                        "1": {
                            "values": [
                                "custom_classification_1",
                                "custom_classification_2",
                                "custom_classification_3"
                            ]
                        },
                        "2": {
                            "values": [
                                "Brand",
                                "Category",
                                "Sub-Category"
                            ]
                        },
                        "3": {
                            "values": [{
                                    "Brand": [
                                        "Kellogg's",
                                        "Nestle",
                                        "Quaker"
                                    ],
                                    "Category": [
                                        "Kellogg's",
                                        "Nestle",
                                        "Quaker"
                                    ],
                                    "Sub-Category": [
                                        "Kellogg's",
                                        "Nestle",
                                        "Quaker"
                                    ]
                                },
                                null,
                                null
                            ]
                        }
                    }
                });
                return def.promise;
            },
            deleteClassification: function(payload) {
                var def = $q.defer();
                // Resolve the promise
                def.resolve(true);
                return def.promise;
            },
            saveClassifications: function(payload) {
                var def = $q.defer();
                // Resolve the promise
                def.resolve(true);
                return def.promise;
            }
        };
    }
]);
