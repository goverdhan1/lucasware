/**
 * Created by Amit Kumar
 */
"use strict";
amdApp.controller("packFactorComponentsModalController",
    ['$scope','$modalInstance','PackFactorService',
        function (scope,modalInstance,PackFactorService) {

            //this allows us to access controller variables not assigned to scope when unit testing
            var self = this;
            scope.closeModal = function () {
                modalInstance.close();
            };
            //commenting out as backend is not implemented as of now
            //PackFactorService.getComponents().then(function (response) {
            //    console.log(response);
            //});
            scope.save = function(save){
                //add logic to save the data to database
                PackFactorService.saveComponents(scope.components);
                scope.closeModal();
            };
            scope.addComponent = function(){
                scope.components.push({name: "",abbreviation: "",editable:true,cannotDelete:true});
            };
            scope.delete = function(index){
                PackFactorService.deleteComponent(scope.components[index]);
                scope.components.splice(index,1);
                if(scope.components.length < 7) scope.addComponent();
            };
            scope.disable = function(index){
                if(!(scope.components[index].name && scope.components[index].name.length > 0 )) return;
                scope.components[index].editable = false;
                scope.components[index].cannotDelete = false;
                if(!(scope.components[scope.components.length -1].name && scope.components[scope.components.length -1].name.length > 0 )) return;
                scope.addComponent();
            };
            scope.enter = function(index){
                scope.disable(index);
            };
            //mocking data starts here
            scope.components= PackFactorService.getComponents().then(function(response){
                scope.components = response;
                var componentsToAdd = scope.components.length < 6 ? 7 - scope.components.length:1;
                for(var i=0 ;i< componentsToAdd;i++) scope.addComponent();
            });
            //mocking data ends here

        }
    ]);