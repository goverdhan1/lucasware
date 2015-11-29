"use strict";

var gridTestUtils = require( '../../resources/gridTestUtils.js');

describe('PHX-906 - FE: Search Group widget', function() {

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
    it('Test loading of Search Group widget', function() {
        // select load widget
        element(by.css('#load-widget a')).click();

        // select Work Execution
        element(by.cssContainingText('.menu li', 'Administration (4)')).click();
        expect(element(by.css('.menu li.active')).getText()).toBe('Administration (4)');
        expect(element.all(by.css('.content li')).count()).toBe(4);

        // test presence of search group widget in list
        expect(element.all(by.cssContainingText('li', 'GRID-SearchGroupGridWidget')).count()).toBe(1);
        element.all(by.cssContainingText('li', 'GRID-SearchGroupGridWidget')).click();
        expect(element.all(by.cssContainingText('#wid100 .panel-heading', "search-group-grid-widget, 100")).count()).toBeGreaterThan(0);
    });
    it("Should check the auto refresh is paused ", function(){
        gridTestUtils.clickGridRowHeader('searchGroupGrid_100', 1);
        expect( element(by.css('#wid100 .pause-auto-refresh') ).isPresent()).toBeTruthy();
    });



});
