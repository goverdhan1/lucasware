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


describe('PHX 1137 Create new canvas', function() {

    //Test setup
    beforeEach(function() {
        browser.get('');
        element(by.model('username')).sendKeys('jack123');
        element(by.model('password')).sendKeys('secret');
        element(by.css('.loginBtn')).click();
        // click add canvas button
        element(by.id('add-canvas-btn')).click();
        // check the create new canvas button existence 
        expect(element.all(by.css('#btn-create-new-canvas')).count()).toBe(1);
        // click New Canvas UI button
        element(by.id('btn-create-new-canvas')).click();
        // check the Create New Canvas UI existence
        expect(element.all(by.css('#createNewCanvas')).count()).toBe(1);

    });

    // test create canvas
    it('test canvas name required validation', function() {

        // click save button
        element(by.id('btn-create-canvas')).click();
        // check the error message is shown
        expect(element.all(by.css('#name-required')).count()).toBe(1);
        element(by.id('btn-cancel-canvas')).click();
    });

    it('test canvas creation', function() {
        element(by.model('canvasName')).sendKeys('ProductTestCanvas123');
        // click save button
        element(by.id('btn-create-canvas')).click();
        // check the canvas is created
        expect(element.all(by.css('#createNewCanvas')).count()).toBe(0);

    });

    it('test dulplicate canvas validation', function() {
        element(by.model('canvasName')).sendKeys('ProductTestCanvas123');

        // click save button
        element(by.id('btn-create-canvas')).click();
        // check the error message is shown
        expect(element.all(by.css('#name-already-exists')).count()).toBe(1);
        element(by.id('btn-cancel-canvas')).click();
    });

    //Clean up after each tests has finished
    afterEach(function() {
        // implicitly tests the logout functionality
        element(by.id('btn-user-menu')).click();
        element(by.id('btn-logout')).click();
        element(by.id('btn-logout-confirmation')).click();
    });

});