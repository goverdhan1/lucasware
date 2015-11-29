/**
 * Created by Shaik Basha on 11/27/2014.
 */

"use strict";

amdApp.controller("PackFactorHierarchyController",
    ['$scope', '$modal', 'LocalStoreService', 
        function (scope,$modal, LocalStoreService) {

            //this allows us to access controller variables not assigned to scope when unit testing
            var self = this;

            scope.selectedRowIndex = 0;
            scope.selectHierarchy = function(index){
                scope.selectedRowIndex = index;
                LocalStoreService.addLSItem(null, 'selectedPFHIndex', index);
            };
            scope.launchComponents = scope.$on("launchComponents",function(event,clientID){
                if(clientID != scope.widgetdetails.clientId) return;
                $modal.open({
                    templateUrl: 'views/modals/packFactorComponents.html',
                    controller: 'packFactorComponentsModalController',
                    backdrop: 'static' 
                });
            });
            scope.launchDetails =  scope.$on("launchDetails",function(event,clientID){
                if(clientID != scope.widgetdetails.clientId) return;
                $modal.open({
                    templateUrl: 'views/modals/pack-factor-details.html',
                    controller: 'packFactorDetailsModalController',
                    backdrop: 'static' 
                });
            });

        }
    ]);