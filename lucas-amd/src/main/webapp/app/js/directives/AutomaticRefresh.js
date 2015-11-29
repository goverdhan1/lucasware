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

 Copyright (c) Lucas Systems, 2015
 ALL RIGHTS RESERVED
 */
(function() {
    'use strict';

	angular.module('amdApp').directive('automaticRefresh', AutomaticRefresh);

	//inject angular variables
	AutomaticRefresh.$inject = [];

	//based on declared angular variables, pass your specific args in that order
	function AutomaticRefresh() {
		return {
			restrict : 'E',
			scope : {
				"refreshConfig" : "="
			},
			templateUrl : 'views/cog/automatic-refresh-config.html'
		};
	};
})();

