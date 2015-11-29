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

"use strict";

describe('Unit testing dragandresize directive', function () {
    var $compile;
    var $rootScope;
    var $httpBackend;
    var $timeout;

    beforeEach(module('amdApp'));

    beforeEach(inject(function (_$compile_, _$rootScope_, _$httpBackend_, _$timeout_, UserService) {
        // The injector unwraps the underscores (_) from around the parameter names when matching
        $compile = _$compile_;
        $rootScope = _$rootScope_;
        $timeout = _$timeout_;

        // Set up the mock http service responses
        $httpBackend = _$httpBackend_;
        // Handle all the POST requests
        $httpBackend.when('GET').respond({});


        // For temporarily mock the profile data, later we have to call the actual service and mock the httpBackend
        var mockProfileData = {
            "status": "success",
            "code": "200",
            "message": "Request processed successfully",
            "level": null,
            "uniqueKey": null,
            "token": null,
            "explicitDismissal": null,
            "data": {
                "username": "jack123",
                "userId": "cat",
                "userPermissions": [
                    "authenticated-user",
                    "user-management-canvas-view",
                    "create-assignment",
                    "user-management-canvas-edit",
                    "user-list-download-excel",
                    "user-list-download-pdf",
                    "pick-monitoring-canvas-edit",
                    "pick-monitoring-canvas-view"
                ]
            }
        };
        UserService.saveProfileToLS(mockProfileData);
    }));

    it('Should create dragandresize directive', function () {
        // Compile a piece of HTML containing the directive
        var element = $compile("<div><div dragandresize></div></div>")($rootScope);
        $rootScope.$digest();
        
        // not working
        // spyOn(element, 'resizable').andCallThrough();

        // flush the pending timeouts
        $timeout.flush();
        // Check that the compiled element contains the templated content
        expect(element.html().length).toBeGreaterThan(0);
        // test the resize handles presence
        expect(element.find('.ui-resizable-handle').length).toBeGreaterThan(0);

        // not working even though the resizable has been called on the DOM Element
        // expect(element.resizable).toHaveBeenCalled();
    });
});