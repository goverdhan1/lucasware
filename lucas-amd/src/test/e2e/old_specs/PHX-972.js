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


describe('PHX 972 Canvases Cloning an existing canvas', function() {

    //Test setup
    beforeEach(function() {
        browser.get('');
        element(by.model('username')).sendKeys('jack123');
        element(by.model('password')).sendKeys('secret');
        element(by.css('.loginBtn')).click();
        // click user options menu
        element(by.id('btn-user-menu')).click();
        // click Clone Canvas UI button
        element(by.id('btn-user-clone-canvas')).click();
        // check the clone New Canvas UI existence
        expect(element(by.css('#cloneCanvas')).isPresent()).toBeTruthy();
    });

    // test clone canvas
    it('test canvas name required validation', function() {
        // click save button
        element(by.id('btn-clone-canvas')).click();
        // check the error message is shown
        expect(element.all(by.css('#name-required')).count()).toBe(1);
        element(by.id('btn-cancel-canvas')).click();
    });


    it('test dulplicate canvas validation', function() {
        element(by.model('canvasName')).sendKeys('Work Execution');

        // click save button
        element(by.id('btn-clone-canvas')).click();
        // check the error message is shown
        expect(element.all(by.css('#name-already-exists')).count()).toBe(1);
        element(by.id('btn-cancel-canvas')).click();
    });

    it('test clone canvas', function() {
        element(by.model('canvasName')).sendKeys('ProductCloneTest');
        // click save button
        element(by.id('btn-clone-canvas')).click();
        // check the canvas is cloned
        expect(element.all(by.css('#cloneNewCanvas')).count()).toBe(0);
        // test the presence of canvas
        expect(element.all(by.cssContainingText('.resizeTabBar li a', 'ProductCloneTest')).count()).toBeGreaterThan(0);
    });

    //Clean up after each tests has finished
    afterEach(function() {
        // implicitly tests the logout functionality
        element(by.id('btn-user-menu')).click();
        element(by.id('btn-logout')).click();
        element(by.id('btn-logout-confirmation')).click();
    });

});