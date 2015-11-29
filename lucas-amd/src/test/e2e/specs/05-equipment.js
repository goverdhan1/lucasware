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

//import the common amd helper functions
var amdUtils = require( '../resources/amdUtils.js');

//
//
// EXAMPLE TEST TO CHECK SPECS ARE BEING EXECUTED
// REMOVE THIS WHEN REAL SPECS ARE ADDED
//
//
describe("AMD Equipment Specs", function() {

    beforeEach(function() {
        //login
        amdUtils.login("jack123", "secret");
    });

    afterEach(function() {
        //logout
        amdUtils.logout();
    });

    it('should run this example equipment test', function() {
        expect(element(by.id('btn-user-menu')).isPresent()).toBeTruthy();
    });
});