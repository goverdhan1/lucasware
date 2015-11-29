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

'use strict';

/*
 * Common helper functions for AMD E2E testing
 */
var amdUtils = {

    /*
     * Login to AMD
     */
    login: function(username, password) {
        browser.get('');
        element(by.model('username')).sendKeys(username);
        element(by.model('password')).sendKeys(password);
        element(by.css('.loginBtn')).click();
    },

    /*
     * Logout of AMD
     */
    logout: function() {
        element(by.id('btn-user-menu')).click();
        element(by.id('btn-logout')).click();
        element(by.id('btn-logout-confirmation')).click();
    },

    relogin: function(username, password) {
        amdUtils.logout();
        amdUtils.login(username, password);
    },

    deleteCanvasInFooter: function(canvasName) {
        // click delete icon
        element(by.cssContainingText('li a', canvasName))
            .element(by.xpath('..'))
            .element(by.css('button'))
            .click();

        // check the delete popup presence
        expect(element.all(by.css('.modal-dialog')).count()).toBe(1);

        // click delete
        element(by.id('btn-canvas-close-yes')).click();
    },

    deleteCanvasInCanvasPallete: function(canvasName) {
        // click add canvas button
        element(by.id('add-canvas-btn')).click();

        // click delete icon
        element(by.cssContainingText('li a', canvasName))
            .element(by.xpath('..'))
            .element(by.css('span'))
            .click();

        // check the delete popup presence
        expect(element.all(by.css('.deleteCanvasModal')).count()).toBe(1);

        // click delete
        element(by.id('btn-delete-canvas-yes')).click();

        // close the pop up
        element(by.id('btn-close-create-new-canvas')).click();
    },

    deleteCanvasInFooterAndCanvasPallete: function(canvasName) {
        amdUtils.deleteCanvasInFooter(canvasName);
        amdUtils.deleteCanvasInCanvasPallete(canvasName);
    },

    scrollIntoView: function() {
        arguments[0].scrollIntoView();
    }
};

module.exports = amdUtils;