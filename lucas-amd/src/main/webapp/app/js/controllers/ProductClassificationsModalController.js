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

amdApp.controller("ProductClassificationsModalController", ['$scope', '$modalInstance', 'ProductClassificationsService',
    function(scope, modalInstance, ProductClassificationsService) {

        //this allows us to access controller variables not assigned to scope when unit testing
        var self = this;
        scope.closeModal = function() {
            modalInstance.close();
        };

        scope.save = function(save) {
            //add logic to save the data to database
            ProductClassificationsService.saveClassifications(scope.classifications);
            scope.closeModal();
        };
        scope.addClassification = function() {
            scope.classifications.push({
                name: "",
                values: "",
                cannotDelete: true,
                editable: true
            });
        };
        scope.buildClassifications = function() {
            var classifications = [];
            var origClassifications = scope.classifications;

            angular.forEach(origClassifications[2].values, function(classification, key) {
                angular.forEach(origClassifications[3].values[0], function(value, key) {
                    if (key == classification) {
                        var classificationValues = '';
                        angular.forEach(value, function(classificationValue, key) {
                            classificationValues += classificationValue + '; ';
                        });

                        classifications.push({
                            name: classification,
                            values: classificationValues,
                            cannotDelete: false,
                            editable: false
                        });
                    }
                });
            });

            scope.classifications = classifications;
        };
        scope.delete = function(index) {
            ProductClassificationsService.deleteClassification(scope.classifications[index]);
            scope.classifications.splice(index, 1);
            if (scope.classifications.length < 10) scope.addClassification();
        };
        //mocking data starts here
        scope.classifications = ProductClassificationsService.getClassifications().then(function(response) {
            scope.classifications = response.grid;
            scope.buildClassifications();

            var classificationsToAdd = scope.classifications.length < 10 ? 10 - scope.classifications.length : 0;
            console.log(classificationsToAdd);
            for (var i = 0; i < classificationsToAdd; i++) scope.addClassification();
            return scope.classifications;
        });
        //mocking data ends here
        scope.disable = function(index) {
            scope.classifications[index].cannotDelete = false,
            scope.classifications[index].editable = false
        }

    }
]);
