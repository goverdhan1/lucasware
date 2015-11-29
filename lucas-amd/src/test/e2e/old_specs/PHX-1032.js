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

describe('PHX-1032 - FE: Product Classifications', function() {

    //Test setup
    beforeEach(function() {
        browser.get('');
        element(by.model('username')).sendKeys('jack123');
        element(by.model('password')).sendKeys('secret');
        element(by.css('.loginBtn')).click();
    });

    //Clean up after each tests has finished
    afterEach(function() {
        // implicitly tests the logout functionality
        element(by.id('btn-user-menu')).click();
        element(by.id('btn-logout')).click();
        element(by.id('btn-logout-confirmation')).click();
    });

    // commented out the tests, as the loading from the actual endpoint will be addressed in the future tickets
    it('Test loading of Product Classifications pop up', function() {
        // select load widget
        element(by.css('#load-widget a')).click();

        // select Work Execution
        element(by.cssContainingText('.menu li', 'Work Execution (2)')).click();
        expect(element(by.css('.menu li.active')).getText()).toBe('Work Execution (2)');
        expect(element.all(by.css('.content li')).count()).toBe(2);

        // test presence of search group widget in list
        expect(element.all(by.cssContainingText('li', 'GRID-SearchProductGridWidget')).count()).toBe(1);
        element.all(by.cssContainingText('li', 'GRID-SearchProductGridWidget')).click();
        expect(element.all(by.cssContainingText('#wid100 .panel-heading', "search-product-grid-widget, 100")).count()).toBeGreaterThan(0);

        // test presence of details icon
        expect(element.all(by.css('#btnProductClassifications')).count()).toBe(1);
        element(by.css('#btnProductClassifications')).click();
        expect(element.all(by.cssContainingText('#productClassifications .heading',"Product Classifications")).count()).toBeGreaterThan(0);

        // close the popup
        element(by.id('btnCancel')).click();
    });
});
