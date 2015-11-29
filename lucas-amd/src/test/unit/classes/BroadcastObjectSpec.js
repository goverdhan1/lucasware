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

describe('BroadcastObject Unit Tests', function() {

    var object = null;

    // Test setup
    beforeEach(function() {
        // initialise the object we're going to be testing
        if(object == null) {
            object = new BroadcastObject();
        }
    });

    // Specs
    describe('BroadcastObject specs', function() {

        it('should initialise a new object', function() {
            expect(object).toBeDefined();
            expect(object instanceof BroadcastObject).toBeTruthy();
        });

        it('should set/get the widgetId', function() {
            object.setWidgetId(1234);
            expect(object.getWidgetId()).toEqual(1234);
        });

        it('should set/get the broadcast data', function() {
            var data = {
                "username" : ["jack123", "jill123"]
            };

            object.setData(data);
            var result = object.getData();

            expect(result.hasOwnProperty("username")).toBeTruthy();
            expect(result.username.length).toBe(2);
            expect(result.username[0]).toEqual("jack123");
            expect(result.username[1]).toEqual("jill123");
        });

        it('should add a new property to the data element', function() {
            object.addDataElement("firstName");

            var result = object.getData();
            expect(result.hasOwnProperty("firstName")).toBeTruthy();
        });

        it('should add a value to the given data element', function() {
            object.addDataValue("firstName", "Lucas");

            var result = object.getData();
            expect(result.hasOwnProperty("firstName")).toBeTruthy();
            expect(result.firstName.length).toBe(1);
            expect(result.firstName[0]).toEqual("Lucas");
        });
    });
});