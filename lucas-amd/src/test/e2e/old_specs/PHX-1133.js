"use strict";

describe('PHX 1133 FE Canvasses Palette', function () {

    // re usable login
    var login = function(username, password) {
        browser.get('');
        element(by.model('username')).sendKeys(username);
        element(by.model('password')).sendKeys(password);
        element(by.css('.loginBtn')).click();
    };

    // Tests create-canvas permission
    it('test create-canvas permission presence', function () {
        // jack login
        login('jack123', 'secret');
        // open the canvases popup
        element(by.id('add-canvas-btn')).click();
        // Test the presence of Create New Canvas button
         expect(element.all(by.id('btn-create-new-canvas')).count()).toBe(1);
        // close the pop up
        element(by.id('btn-close-create-new-canvas')).click();
    });

    it('create DeleteProductTestCanvas canvas', function() {
         // jack login
        login('jack123', 'secret');
        // click add canvas button
        element(by.id('add-canvas-btn')).click();
        // check the create new canvas button existence 
        expect(element.all(by.css('#btn-create-new-canvas')).count()).toBe(1);
        // click New Canvas UI button
        element(by.id('btn-create-new-canvas')).click();
        // check the Create New Canvas UI existence
        expect(element.all(by.css('#createNewCanvas')).count()).toBe(1);

        element(by.model('canvasName')).sendKeys('DeleteProductTestCanvas');
        // click save button
        element(by.id('btn-create-canvas')).click();
        // check the canvas is created
        expect(element.all(by.css('#createNewCanvas')).count()).toBe(0);
    });

    // test delete functionality
    xit("Test delete functionality", function() {
        // jack login
        login('jack123', 'secret');
        // open the canvases popup
        element(by.id('add-canvas-btn')).click();

        element(by.cssContainingText('li a','DeleteProductTestCanvas'))
        .element(by.xpath('..'))
        .element(by.css('span'))
        .click();

        // check the delete popup presence
        expect(element.all(by.css('.deleteCanvasModal')).count()).toBe(1);

        // click delete
        element(by.id('btn-delete-canvas-yes')).click();
        
        // close the pop up
        element(by.id('btn-close-create-new-canvas')).click();
    });

     // test delete functionality result
    xit("Test delete functionality check result", function() {
        // jack login
        login('jack123', 'secret');
        // open the canvases popup
        element(by.id('add-canvas-btn')).click();
        // check the element is deleted
        expect(element.all(by.cssContainingText('li a','DeleteProductTestCanvas')).count()).toEqual(0);
        // close the pop up
        element(by.id('btn-close-create-new-canvas')).click();
    });

    // Tests revoked permission  for Jill
    it('test permissions revoked', function () {
        // jill login
        login('jill123', 'secret');
        // open the canvases popup
        element(by.id('add-canvas-btn')).click();

        // Test the absence of Create New Canvas button
         expect(element.all(by.id('btn-create-new-canvas')).count()).toBe(0);
        
        // Test the revoked permission for JillCanvas2
        expect(element.all(by.cssContainingText('li.canvas-permissions-revoked a','JillCanvas2')).count()).toEqual(1);

        // close the pop up
        element(by.id('btn-close-create-new-canvas')).click();
    });

    // Tests already opened canvases for Jill
    it('test already opened canvases', function () {
        // jill login
        login('jill123', 'secret');
        // open the canvases popup
        element(by.id('add-canvas-btn')).click();

        // open JillCanvas1 canvas
        element(by.cssContainingText('li a','JillCanvas1')).click();

        // open the canvases popup
        element(by.id('add-canvas-btn')).click();
        
        // Test the JillCanvas1 opened
        expect(element.all(by.cssContainingText('li.canvas-already-loaded a','JillCanvas1')).count()).toEqual(1);

        // close the pop up
        element(by.id('btn-close-create-new-canvas')).click();
    });
    
    // Clean up after each tests has finished
    afterEach(function () {
        // implicitly tests the logout functionality
        element(by.id('btn-user-menu')).click();
        element(by.id('btn-logout')).click();
        element(by.id('btn-logout-confirmation')).click();
    });
});