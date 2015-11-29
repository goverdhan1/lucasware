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

amdApp.controller("EaiMessageSegmentDefinitionModalController", ['$scope', '$modalInstance', 'EAIMessagesService',
    function(scope, modalInstance, EAIMessagesService) {

        //this allows us to access controller variables not assigned to scope when unit testing
        var self = this;
        scope.closeModal = function() {
            modalInstance.close();
        };

        scope.save = function(save) {
            //add logic to save the data to database
            EAIMessagesService.saveEAIMessageSegmentDefinition(scope.definition);
            scope.closeModal();
        };

        var move = function(origin, destination) {
            var temp = scope.definition.levels[destination];
            scope.definition.levels[destination] = scope.definition.levels[origin];
            scope.definition.levels[origin] = temp;
        };

        scope.moveUp = function(index) {
            move(index, index - 1);
        };

        scope.moveDown = function(index) {
            move(index, index + 1);
        };

        scope.addLevel = function() {
            var newRow = {
                "seq": scope.definition.levels.length + 1,
                "name": "",
                "type": "",
                "description": "",
                "length": "",
                "starts": "",
                "ends": "",
                "value": ""
            };
            scope.definition.levels.push(newRow);
        };

        scope.deleteRow = function(index) {
            scope.definition.levels.splice(index, 1);
        };

        scope.disable = function(index) {
            if (!(scope.definition[index].name && scope.definition[index].name.length > 0)) return;
            scope.definition[index].editable = false;
            scope.definition[index].cannotDelete = false;
            if (!(scope.definition[scope.definition.length - 1].name && scope.definition[scope.definition.length - 1].name.length > 0)) return;
            scope.addLevel();
        };

        scope.enter = function(index) {
            scope.disable(index);
        };

        //mocking data starts here
        scope.definition = EAIMessagesService.getEAIMessageSegmentDefinition().then(function(response) {
            var levels = [];
            scope.definition = response;
            angular.forEach(response.levels, function(definition, key) {
                if(definition.type == 'Segment') {
                    definition.isSegment = true;
                    definition.Segment = {"name":"Segment"};
                }
                levels.push(definition);
            });
            scope.definition.levels = levels;
        });

        scope.dataTypes = EAIMessagesService.getEAIDataTypes().then(function(response) {
            scope.dataTypes = response;
        });

        scope.messageSegments = EAIMessagesService.getEAIMessageSegments().then(function(response) {
            scope.messageSegments = response;
        });
        //mocking data ends here

    }
]);
