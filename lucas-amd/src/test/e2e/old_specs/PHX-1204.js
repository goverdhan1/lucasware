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

describe('PHX-1204 - FE: EAI Message Segments widget', function() {

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
    it('Test loading of Message Segments widget', function() {
        // select load widget
        element(by.css('#load-widget a')).click();

        var categoriesList = element.all(by.repeater('obj in names'));

        // select EAI
        element(by.id('widgetsPallette')).element(by.cssContainingText('.menu ul li', 'EAI')).click();
        expect(categoriesList.get(3).getText()).toContain('EAI');

        // test presence of Message Segments widget in list
        expect(element.all(by.cssContainingText('li', 'GRID-EAIMessageSegmentsWidget')).count()).toBe(1);
        element.all(by.cssContainingText('li', 'GRID-EAIMessageSegmentsWidget')).click();
        expect(element.all(by.cssContainingText('#wid100 .panel-heading', "eai-message-segments-grid-widget, 100")).count()).toBeGreaterThan(0);
        element(by.id('wid100-remove-widget-btn')).click();
        element(by.id('btn-delete-confirmation')).click();
    });
});
