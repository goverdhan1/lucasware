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
 * Services related to EAI Messages
 */
amdApp.factory('EAIMessagesService', ['RestApiService', 'LocalStoreService', 'WidgetGridService', '$q',
    function(restApiService, LocalStoreService, WidgetGridService, $q) {
        return {
            deleteEAIMessageSegment: function(payload) {
                var def = $q.defer();
                // Resolve the promise
                def.resolve(true);
                return def.promise;
            },

            saveEAIMessageSegments: function(payload) {
                var def = $q.defer();
                // Resolve the promise
                def.resolve(true);
                return def.promise;
            },

            getEAIMessageSegmentDefinition: function() {
                var def = $q.defer();
                var details = {
                    "id": "1",
                    "segmentName": "Control",
                    "segmentDescription": "Control record for transfer order",
                    "levels": [{
                        "seq": 1,
                        "name": "SegmentID",
                        "type": "SegID",
                        "description": "Segment Identifier",
                        "length": "5",
                        "starts": "1",
                        "ends": "5",
                        "value": "HH"
                    }, {
                        "seq": 2,
                        "name": "TabName",
                        "type": "String",
                        "description": "TabName",
                        "length": "10",
                        "starts": "6",
                        "ends": "15",
                        "value": ""
                    }, {
                        "seq": 3,
                        "name": "TOHdr",
                        "type": "Segment",
                        "description": "TabName",
                        "length": "3",
                        "starts": "16",
                        "ends": "18",
                        "value": ""
                    }, {
                        "seq": 4,
                        "name": "DocNum",
                        "type": "Integer",
                        "description": "Document Number",
                        "length": "10",
                        "starts": "19",
                        "ends": "28",
                        "value": ""
                    }]
                };

                def.resolve(details);
                return def.promise;
            },

            getEAIMessageDefinition: function(format) {
                var def = $q.defer();
                var details = {
                    "id": "1",
                    "messageName": "Inbound Pick",
                    "description": "Inbound pick simple message",
                    "levels": [{
                        "seq": 1,
                        "type": "String",
                        "name": "Record ID",
                        "parentSegment": "",
                        "description": "Unique ID for the record",
                        "length": "10",
                        "starts": "1",
                        "ends": "10",
                        "separator": "",
                        "repeat": ""
                    }, {
                        "seq": 2,
                        "type": "String",
                        "name": "Route number",
                        "parentSegment": "",
                        "description": "Unique ID for the record",
                        "length": "3",
                        "starts": "11",
                        "ends": "13",
                        "separator": "",
                        "repeat": ""
                    }, {
                        "seq": 3,
                        "type": "String",
                        "name": "Stop",
                        "parentSegment": "",
                        "description": "Stop number",
                        "length": "3",
                        "starts": "14",
                        "ends": "16",
                        "separator": "",
                        "repeat": ""
                    }, {
                        "seq": 4,
                        "type": "DateTime",
                        "name": "Ship Date",
                        "parentSegment": "",
                        "description": "Ship date in YYYYMMDD format",
                        "length": "8",
                        "starts": "17",
                        "ends": "24",
                        "separator": "",
                        "repeat": ""
                    }, {
                        "seq": 5,
                        "type": "DateTime",
                        "name": "Dispatch Date",
                        "parentSegment": "",
                        "description": "Dispatch date in YYYYMMDD format",
                        "length": "6",
                        "starts": "25",
                        "ends": "30",
                        "separator": "",
                        "repeat": ""
                    }, {
                        "seq": 6,
                        "type": "Integer",
                        "name": "Work Type",
                        "parentSegment": "",
                        "description": "2 digit work type code",
                        "length": "2",
                        "starts": "31",
                        "ends": "32",
                        "separator": "",
                        "repeat": ""
                    }]
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

            getEAIMessageSegments: function() {
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

            getEAIMessageDefinitionFormatDelimiterTypes: function() {
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

            getEAIMessageDefinitionFormats: function() {
                var def = $q.defer();
                var segments = [
                    {
                        "name": "XML"
                    },
                    {
                        "name": "JSON"
                    },
                    {
                        "name": "Delimited"
                    },
                    {
                        "name": "Fixed Length"
                    }
                ];

                def.resolve(segments);
                return def.promise;
            }
        };
    }
]);
