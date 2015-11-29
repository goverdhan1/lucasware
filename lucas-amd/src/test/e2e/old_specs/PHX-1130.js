"use strict";

describe('PHX 1130 FE Canvas Header', function () {

    // re usable login
    var login = function(username, password) {
        browser.get('');
        element(by.model('username')).sendKeys(username);
        element(by.model('password')).sendKeys(password);
        element(by.css('.loginBtn')).click();
    };

    var logout = function() {
        // implicitly tests the logout functionality
        element(by.id('btn-user-menu')).click();
        element(by.id('btn-logout')).click();
        element(by.id('btn-logout-confirmation')).click();
    };

    var createNewCanvas =  function(canvasName) {
        // click add canvas button
        element(by.id('add-canvas-btn')).click();
        // check the create new canvas button existence 
        expect(element.all(by.css('#btn-create-new-canvas')).count()).toBe(1);
        // click New Canvas UI button
        element(by.id('btn-create-new-canvas')).click();
        // check the Create New Canvas UI existence
        expect(element.all(by.css('#createNewCanvas')).count()).toBe(1);

        element(by.model('canvasName')).sendKeys(canvasName);
        // click save button
        element(by.id('btn-create-canvas')).click();
        // check the canvas is created
        expect(element.all(by.css('#createNewCanvas')).count()).toBe(0);
    };


    // Tests for Jack
    it('test create-canvas and publish-canvas permissions presence', function () {
        login('jack123', 'secret');

        // create a private canvas
        createNewCanvas('PublishTestCanvas');

        logout();

        login('jack123', 'secret');

        // Add a new canvas so we have a blank canvas to test loading the widgets in isolation
        element(by.id('add-canvas-btn')).click();
        expect(element(by.cssContainingText('li a', 'PublishTestCanvas')).isPresent()).toBeTruthy();
        element(by.id('btn-close-create-new-canvas')).click();

        // Test the presence of Clone Canvas button
        expect(element.all(by.id('btn-user-clone-canvas')).count()).toBe(1);
        // Test the presence of Publish Canvas button
        expect(element.all(by.id('btn-user-publish-canvas')).count()).toBe(1);
    });

    // Tests for Jill
    it('test create-canvas and publish-canvas permissions absence', function () {
        login('jill123', 'secret');
        // Test the absence of Clone Canvas button
        expect(element.all(by.id('btn-user-clone-canvas')).count()).toBe(0);
        // Test the absence of Publish Canvas button
        expect(element.all(by.id('btn-user-publish-canvas')).count()).toBe(0);
    });

    
    // Clean up after each tests has finished
    afterEach(function () {
        // implicitly tests the logout functionality
        element(by.id('btn-user-menu')).click();
        element(by.id('btn-logout')).click();
        element(by.id('btn-logout-confirmation')).click();
    });
});