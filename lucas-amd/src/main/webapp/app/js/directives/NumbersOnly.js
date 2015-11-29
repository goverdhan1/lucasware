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

	angular.module('amdApp').directive('numbersOnly', NumbersOnly);

	//inject angular variables
	NumbersOnly.$inject = [];

	function NumbersOnly() {
		return {
			require : 'ngModel',
            link : function(scope, element, attrs, model) {
                model.$parsers.push(function (inputValue) {
                    // this next if is necessary for when using ng-required on your input.
                    // In such cases, when a letter is typed first, this parser will be called
                    // again, and the 2nd time, the value will be undefined
                    if (inputValue == undefined) return '';

                    //strip out anything that is not a number, and apply the new value to the
                    //ngModel for the input element
                    var transformedInput = inputValue.replace(/[^0-9]/g, '').replace(/^\s+|\s+$/g, '');
                    if (transformedInput != inputValue) {
                        model.$setViewValue(transformedInput);
                        model.$render();
                    }
                    return transformedInput;
                });
            }
		};
	}
})();