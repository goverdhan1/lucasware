'use strict';

amdApp.controller('ActionConfirmationController', ['$scope', '$modalInstance',
function(scope, modalInstance) {

    //Function to handle pallet confirmation actions
    scope.handleActionSelected = function(action) {
        //emit the action event back to the widget
        console.log ('*** ActionConfirmationController emit the ActionConfirmed event back to widget')
        scope.$emit('ActionConfirmed', action);
        scope.close();
    };

    //close the modal
	scope.close = function() {
        modalInstance.close();
	};

}]);
