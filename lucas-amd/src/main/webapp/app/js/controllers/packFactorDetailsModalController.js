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

amdApp.controller("packFactorDetailsModalController", ['$scope', '$modalInstance', 'PackFactorService', 'LocalStoreService', '$q',
    function(scope, modalInstance, PackFactorService, LocalStoreService, $q) {

        //this allows us to access controller variables not assigned to scope when unit testing
        var self = this;
        scope.availableComponents = {};
        scope.allComponents = {};
        scope.details = {};

        scope.closeModal = function() {
            modalInstance.close();
        };

        var move = function(origin, destination) {
            var temp = scope.details.levels[destination];
            scope.details.levels[destination] = scope.details.levels[origin];
            scope.details.levels[origin] = temp;
        };

        scope.moveUp = function(index) {
            move(index, index - 1);
        };

        scope.moveDown = function(index) {
            move(index, index + 1);
        };

        scope.deleteRow = function(index) {
            scope.details.levels.splice(index, 1);
        }

        scope.addLevel = function() {
            scope.details.levels.unshift({
                "componentID": null,
                "componentName": "",
                "defaultDimensions": {
                    "height": null,
                    "width": null,
                    "depth": null,
                    "weight": null,
                    "cube": null
                },
                "factor": {
                    "childComponentID": null,
                    "childComponentName": "",
                    "factor": null
                }
            });
        }

        scope.selectedComponentChanged = function(componentName, origComponent) {
            delete scope.availableComponents.origComponent;
            scope.availableComponents.componentName = scope.allComponents.componentName;
        }

        var buildAvailableComponents = function() {
            var availableComponents = scope.allComponents;
            angular.forEach(scope.details.levels, function(component) {
                delete availableComponents[component.componentName];
            });

            scope.availableComponents = availableComponents;
        }

        var buildAllComponents = function() {
            var allComponents = {};
            angular.forEach(scope.allComponents, function(component) {
                allComponents[component.name] = component;
            });

            scope.allComponents = allComponents;
        }

        var promiseComponents = PackFactorService.getComponents();
        var promiseDetails = PackFactorService.getDetails();

        $q.all([promiseComponents, promiseDetails]).then(function(response) {
            var selectedPFHIndex = LocalStoreService.getLSItem('selectedPFHIndex') || 0;
            scope.allComponents = response[0];
            scope.details = response[1].data[selectedPFHIndex];
            buildAllComponents();
            buildAvailableComponents();
        });
    }
]);
