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
 * Services related to EAI Events
 */
amdApp.factory('EAIEventsService', ['RestApiService', 'LocalStoreService', 'WidgetGridService', '$q',
    function(restApiService, LocalStoreService, WidgetGridService, $q) {
        var factory = {
            deleteEAIEventSegment: function(payload) {
                var def = $q.defer();
                // Resolve the promise
                def.resolve(true);
                return def.promise;
            },

            saveEAIEventSegments: function(payload) {
                var def = $q.defer();
                // Resolve the promise
                def.resolve(true);
                return def.promise;
            },

            getEAIEventHandlerRegistration: function(id) {
                var def = $q.defer();
                var details = {
                    "id": "1",
                    "name": "Products Data Download",
                    "details": {
                        "description": "Products master import from host",
                        "type": "Inbound Asynchronous",
                        "inboundMapping": "Download products request",
                        "transportType": "FTP Inbound"
                    },
                    "transport": {
                        "host": "10.1.26.126",
                        "portNumber": "21",
                        "userName": "ftpuser",
                        "password": "ftppassword",
                        "remoteDirectory": "/ftproot/export",
                        "inboundDirectory": "/ftproot/import",
                        "inboundFileName": "containerdownload.csv",
                        "pollingFrequency": "1000",
                        "deleteSourceFile": "true"
                    }
                };

                def.resolve(details);
                return def.promise;
            },

            getEventDefinition: function(id) {
                var def = $q.defer();
                var details = {
                    "id": "1",
                    "name": "Products Data Download",
                    "details": {
                        "description": "Download product master from Host",
                        "type": "Inbound",
                        "source": "Host",
                        "category": "Master Data",
                        "subcategory": "Products"
                    },
                    "eventHandlers": {
                        "columns": [
                            "Seq",
                            "Event Handler",
                            "Type",
                            "Execution Order"
                        ],
                        "data": [
                            [
                                "1",
                                "Products data download",
                                "Inbound Asynchronous",
                                ""
                            ],
                            [
                                "2",
                                "",
                                "Inbound Asynchronous",
                                "Sequential"
                            ]
                        ]
                    }
                };

                def.resolve(details);
                return def.promise;
            },

            getEAIDataTypes: function() {
                var def = $q.defer();
                var dataTypes = [
                    {
                        "name": "DateTime"
                    },
                    {
                        "name": "Integer"
                    },
                    {
                        "name": "SegID"
                    },
                    {
                        "name": "Segment"
                    },
                    {
                        "name": "String"
                    }
                ];

                def.resolve(dataTypes);
                return def.promise;
            },

            getEAIEventSegments: function() {
                var def = $q.defer();
                var segments = [
                    {
                        "name": "TOHdr"
                    },
                    {
                        "name": "TOItm"
                    }
                ];

                def.resolve(segments);
                return def.promise;
            },

            getEAIEventHandlerRegistrationFormatDelimiterTypes: function() {
                var def = $q.defer();
                var segments = [
                    {
                        "name": ", (comma)"
                    },
                    {
                        "name": "; (semicolon)"
                    }
                ];

                def.resolve(segments);
                return def.promise;
            },

            getEAITransportTypes: function() {
                return factory.getEAIEventStaticData(["EAI_TRANSPORT_TYPES"]);
            },

            getEAIEventStaticData: function(payload) {
                return restApiService.post('/application/codes', null, payload).then(
                    function(transportTypes) {
                        return transportTypes;
                    },
                    function(error) {
                        throw new LucasBusinessException(error);
                    }
                );
            }
        };

        return factory;
    }
]);
