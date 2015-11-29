'use strict';

amdApp.directive('sender', ['$timeout', '$rootScope',
function($timeout, rootScope) {
	var linkFunction = function(scope, element, attrs) {
		scope.sentInfo = null;
		scope.$on('fromcontroller', function(angularEvent, args) {
			if (scope.incomingInfo.broadcastList) {				
				var fromInfo = scope.incomingInfo.name;
				var sendItems = {
					"items": args,
					"from": fromInfo
				};
				scope.sentInfo = sendItems;				
				rootScope.$broadcast("broadcastedItems", sendItems);
			}
		});
	};
	return {
		restrict : 'E',
		scope : {
			incomingInfo : "=sendinfo"
		},
		replace : true,
		link : linkFunction,
		templateUrl : 'views/sender.html'
	};
}]);