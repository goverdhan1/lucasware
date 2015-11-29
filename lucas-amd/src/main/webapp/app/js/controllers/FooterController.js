'use strict';

amdApp.controller('FooterController', ['$scope', '$modal',
function(scope, modal) {
	scope.showVersion = function() {
		var versionInstance = modal.open({
			templateUrl : 'views/about-modal.html',
			controller : 'AboutLucasController',
			windowClass : 'aboutLucasModal',
        	        backdrop: 'static' 
		});
	};	
}]);
