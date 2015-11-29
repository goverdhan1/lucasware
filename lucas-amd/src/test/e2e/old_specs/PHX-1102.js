"use strict";

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
    it('Test loading of Pack Factor Details pop up', function() {
        // select load widget
        element(by.css('#load-widget a')).click();

        // select Work Execution
        element(by.cssContainingText('.menu li', 'Work Execution (2)')).click();
        expect(element(by.css('.menu li.active')).getText()).toBe('Work Execution (2)');
        expect(element.all(by.css('.content li')).count()).toBe(2);

        // test presence of search group widget in list
        expect(element.all(by.cssContainingText('li', 'PackFactorHierarchyWidget')).count()).toBe(1);
        element.all(by.cssContainingText('li', 'PackFactorHierarchyWidget')).click();
        expect(element.all(by.cssContainingText('#wid100 .panel-heading', "pack-factor-hierarchy-widget, 100")).count()).toBeGreaterThan(0);

        // test presence of details icon
        expect(element.all(by.css('#btnSearchComponents')).count()).toBe(1);
        element(by.css('#btnSearchComponents')).click();
        expect(element.all(by.cssContainingText('#packFactorDetails .heading',"Edit Pack Factor Hierarchies")).count()).toBeGreaterThan(0);

        // close the popup
        element(by.id('btnCancel')).click();
    });
});
