
/**
 * Created by Shaik Basha on 12/1/2014.
 */

"use strict";

var gridTestUtils = require( '../../resources/gridTestUtils.js');

// commented out as this depends on load widget button, and due to the new UI changes now there is no load widget button
// Need to update the test case once the future part of PHX-927 is completed
xdescribe('PHX-851 -  Products Maintenance UI which lists all the products in the DC', function () {
    //Test setup
    beforeEach(function () {
        browser.get('');
        element(by.model('username')).sendKeys('jack123');
        element(by.model('password')).sendKeys('secret');
        element(by.css('.loginBtn')).click();
        element(by.css('#load-widget a')).click()
        element(by.css('.list-group a[id="14"]')).click();
    });

    //Clean up after each tests has finished
    afterEach(function () {
        //logout the user
        element(by.id('btn-logout')).click();
        element(by.id('btn-logout-confirmation')).click();
    });

    it('Should Filter SearchProductGrid Widget', function () {
        //show the filters
        element(by.css('#wid100 #showFilterBtn')).click();

        // Filter for description Toro Pulley
        element(by.id('filter_description')).sendKeys('TORO');

        //fetch results
        element(by.id('queryBtn')).click();
        element(by.id('hideFilterBtn')).click();

        //Assert grid have correct row
        gridTestUtils.expectCellValueMatch('searchProductGrid_100', 0, 0, 'TORO PULLEY');
    });

    it('Should Sort SearchProductGrid Widget', function () {
        //first, we must reset the grid so no sort is applied
        element(by.id('showFilterBtn')).click();
        element(by.id('resetBtn')).click();
        element(by.id('hideFilterBtn')).click();

        //apply sort ascending for column Description
        //value should match 'HOUSING ASM-REDUCER RH'
        gridTestUtils.clickHeaderCell('searchProductGrid_100', 0)
        gridTestUtils.expectCellValueMatch('searchProductGrid_100', 0, 0, 'HOUSING ASM-REDUCER RH');

        //apply sort descending for column Description
        //value should match 'Water / Fuel Filter ASM'
        gridTestUtils.clickHeaderCell('searchProductGrid_100', 0);
        gridTestUtils.expectCellValueMatch('searchProductGrid_100', 0, 0, 'Water / Fuel Filter ASM');
    });
});