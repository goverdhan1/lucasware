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

describe('PHX 1138 Publishing a canvas', function() {

    //Test setup
    beforeEach(function() {
        browser.get('');
        element(by.model('username')).sendKeys('jack123');
        element(by.model('password')).sendKeys('secret');
        element(by.css('.loginBtn')).click();
    });

    it('Create canvas "ProductPublishTest123"', function() {
        // click add canvas button
        element(by.id('add-canvas-btn')).click();
         // click New Canvas UI button
        element(by.id('btn-create-new-canvas')).click();
        // create canvas 'ProductPublishTest123'
        element(by.model('canvasName')).sendKeys('ProductPublishTest123');
        // click save button
        element(by.id('btn-create-canvas')).click();
        // check the canvas is created
        expect(element.all(by.css('#createNewCanvas')).count()).toBe(0);
    });

    xit('Publish canvas "ProductPublishTest123"', function() {

        // Load 'ProductPublishTest123' canvas
        element(by.id('add-canvas-btn')).click();
        element(by.cssContainingText('li a', 'ProductPublishTest123')).click();


         // click user options menu
        element(by.id('btn-user-menu')).click();
        // click Publish Canvas UI button
        element(by.id('btn-user-publish-canvas')).click();
        // check the Publish New Canvas UI existence
        expect(element(by.css('#publishCanvas')).isPresent()).toBeTruthy();

        // click Publish Canvas button
        element(by.id('btn-publish-canvas')).click();

        // check the canvas is published
        expect(element(by.css('#publishCanvas')).isPresent()).toBeFalsy();
    });

    // Clean up after each tests has finished
    afterEach(function() {
        // implicitly tests the logout functionality
        element(by.id('btn-user-menu')).click();
        element(by.id('btn-logout')).click();
        element(by.id('btn-logout-confirmation')).click();
    });

});