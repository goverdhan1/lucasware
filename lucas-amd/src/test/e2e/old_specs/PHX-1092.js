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

describe('PHX-1092 Canvases: Implement Canvas header work in the new canvas solution', function() {

    // Global helpers
    var scrollIntoView = function() {
        arguments[0].scrollIntoView();
    };

    var initialWindowSize;
    
	browser.manage().window().getSize().then(function(size) {
		initialWindowSize = size;
		console.log( "width", initialWindowSize.width);
		console.log( "height", initialWindowSize.height);
	});
	
    //Test setup
    beforeEach(function() {
        browser.get('');
        element(by.model('username')).sendKeys('jack123');
        element(by.model('password')).sendKeys('secret');
        element(by.css('.loginBtn')).click();
        browser.driver.manage().window().setSize(300, 500);
    });

    //Clean up after each tests has finished
    afterEach(function() {
        // implicitly tests the logout functionality
        element(by.id('btn-user-menu')).click();
        element(by.id('btn-logout')).click();
        element(by.id('btn-logout-confirmation')).click();
        browser.driver.manage().window().setSize(initialWindowSize.width, initialWindowSize.height);
    });

    it('Test loading of Search Group widget', function() {
        element(by.id('btn-user-menu')).click();
        // select load widget
        element(by.css('.topright #load-widget a')).click();

        // select Administration
        element(by.cssContainingText('.menu li', 'Administration (4)')).click();
        expect(element(by.css('.menu li.active')).getText()).toBe('Administration (4)');
        expect(element.all(by.css('.content li')).count()).toBe(4);

        // test presence of search group widget in list
        element.all(by.cssContainingText('li', 'GRID-UserCreationFormWidget')).click();
        expect(element.all(by.cssContainingText('#wid100 .panel-heading',
            "create-or-edit-user-form-widget, 100")).count()).toBeGreaterThan(0);

        element(by.id('btn-user-menu')).click();
        element(by.css('.topright #load-widget a')).click();
        // select Administration
        element(by.cssContainingText('.menu li', 'Administration (4)')).click();
        expect(element(by.css('.menu li.active')).getText()).toBe('Administration (4)');
        expect(element.all(by.css('.content li')).count()).toBe(4);

        // test presence of search group widget in list
        element.all(by.cssContainingText('li', 'GRID-UserCreationFormWidget')).click();

        expect(element.all(by.cssContainingText('#wid101 .panel-heading',
            "create-or-edit-user-form-widget, 101")).count()).toBeGreaterThan(0);

        // scroll to the bottom
        browser.executeScript(scrollIntoView, browser.findElement(by.id('wid101')));
        browser.driver.sleep(3000);
        // check the header is hidden
        browser.executeScript('return $("header.hideHeader").length').then(function(res) {
            expect(res).toBe(1);
        });

		// scroll to the top
        browser.executeScript(scrollIntoView, browser.findElement(by.css('body')));
        browser.driver.sleep(3000);

        // check the header is visible
        browser.executeScript('return $("header.showHeader").length').then(function(res) {
            expect(res).toBe(1);
        });
    });
});