"use strict";

// commented out as this depends on load widget button, and due to the new UI changes now there is no load widget button
// Need to update the test case once the future part of PHX-927 is completed
xdescribe('PHX-798 - E2E Tests for Cog menu create or edit user widget', function () {

    //Test setup
    beforeEach(function () {
        browser.get('');
        element(by.model('username')).sendKeys('jack123');
        element(by.model('password')).sendKeys('secret');
        element(by.css('.loginBtn')).click();
        element(by.css('#load-widget a')).click()
        element(by.css('.list-group a[id="8"]')).click();
    });

    it('Date tests for Cog menu create or edit user widget', function () {
        // set date to 11-01-2014
        var startDate = element(by.model("defaultFields.startDate.value"));
        startDate.clear();
        startDate.sendKeys('11-01-2014');
        // Load cog menu
        element(by.css('#wid100 .cogMenuBtn')).click();
        // select MMM dd, yyyy format
        element.all(by.css('label.radio-inline')).get(1).element(by.css('input')).click();
        // click the save button
        element(by.css('.modal-footer .btn-save')).click();
        // check the converted format
        expect(element(by.model("defaultFields.startDate.value")).getAttribute('value')).toBe('Nov 01, 2014');
    });

});