'use strict';

amdApp.controller('ActionPalletController', ['$scope', '$modalInstance',
function(scope, modalInstance) {

    //Function to handle pallet actions
    scope.processPalletAction = function(action) {
        //emit the action event back to the widget
        scope.$emit('ProcessPalletAction', action);
        scope.close();
    };

    //close the modal
	scope.close = function() {
        modalInstance.close();
	};

}]);
