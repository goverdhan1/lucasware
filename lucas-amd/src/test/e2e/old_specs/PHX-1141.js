/*
Lucas Systems Inc
11279 Perry Highway
Wexford
PA 15090
United States
 
 
The information in this file contains trade secrets and confidential
information which is the property of Lucas Systems Inc.
 
All trademarks, trade names, copyrights and other intellectual property
rights cloned, developed, embodied in or arising in connection with
this software shall remain the sole property of Lucas Systems Inc.
 
Copyright (c) Lucas Systems, 2014
ALL RIGHTS RESERVED
 
*/

"use strict";

describe('PHX 1141 Widget Deletion Canvas Interactions', function() {

    //Test setup
    beforeEach(function() {
        browser.get('');
        element(by.model('username')).sendKeys('jack123');
        element(by.model('password')).sendKeys('secret');
        element(by.css('.loginBtn')).click();

        // open the canvases popup
        //element(by.id('add-canvas-btn')).click();
        //  Load Morning Shift Canvas
        //element(by.cssContainingText('li a', 'Morning Shift Canvas')).click();
    });

    describe('Test widget deletion', function() {
        it('should delete the "Search User" widget', function() {
            // Open Load a widget UI
            element(by.css('#load-widget a')).click();
            //select the Administration category
            element(by.id('widgetsPallette')).element(by.cssContainingText('.menu ul li', 'Administration')).click();

            //select the Search User widget
            element(by.id('widgetsPallette')).element(by.cssContainingText('.content ul li h2', 'Search User')).click();

            // Delete the widget
            element(by.id('wid100-remove-widget-btn')).click();
            element(by.id('btn-delete-confirmation')).click();
        });

        it('should test the deletion of "Search User" widget', function() {
            expect(element(by.id('wid100-remove-widget-btn')).isPresent()).toBeFalsy();
        });

    });

    // Clean up after each tests has finished
    afterEach(function() {
        // implicitly tests the logout functionality
        element(by.id('btn-user-menu')).click();
        element(by.id('btn-logout')).click();
        element(by.id('btn-logout-confirmation')).click();
    });

});