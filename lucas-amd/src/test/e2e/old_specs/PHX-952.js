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

describe('User Preferences E2E tests', function () {

    //Test setup
    var doSetup = true;
    beforeEach(function () {
        if(doSetup){
            browser.get('');
            element(by.model('username')).sendKeys('jack123');
            element(by.model('password')).sendKeys('secret');
            element(by.css('.loginBtn')).click();
            doSetup = false;
        }
    });

    it('should display the My Preferences modal', function() {
        //open the user menu
        element(by.id('btn-user-menu')).click();

        //select the preferences option
        element(by.id('btn-my-preferences')).click();

        //find the modal dialog
        var preferencesModal = element(by.css('.modal-dialog'));

        expect(preferencesModal.isPresent()).toBeTruthy();
        expect(preferencesModal.element(by.css('h4')).getText()).toEqual('My Preferences');
        expect(preferencesModal.element(by.id('preferencesSelectDateFormat')).isPresent()).toBeTruthy();
        expect(preferencesModal.element(by.id('preferencesSelectTimeFormat')).isPresent()).toBeTruthy();
        expect(preferencesModal.element(by.id('preferencesRefreshFrequency')).isPresent()).toBeTruthy();
        expect(preferencesModal.element(by.id('btn-my-preferences-save')).isPresent()).toBeTruthy();
        expect(preferencesModal.element(by.id('btn-my-preferences-cancel')).isPresent()).toBeTruthy();
    });

    it('should save changes to preferences', function() {

        var preferencesModal = element(by.css('.modal-dialog'));
        var dateFormat = preferencesModal.element(by.id('preferencesSelectDateFormat'));
        var timeFormat = preferencesModal.element(by.id('preferencesSelectTimeFormat'));
        var refreshFreq = preferencesModal.element(by.id('preferencesRefreshFrequency'));

        //change the date format
        dateFormat.click();
        dateFormat.all(by.css('option')).then(function(options) {
            options[2].click();//select YYYY-MM-DD format
        });

        //change the time format
        timeFormat.click();
        timeFormat.all(by.css('option')).then(function(options) {
            options[0].click();//select 12HR format
        });

        //change the auto refresh frequency
        refreshFreq.clear();
        refreshFreq.sendKeys('60');

        //save the changes
        preferencesModal.element(by.id('btn-my-preferences-save')).click();

        //open the modal and assert the values were saved
        element(by.id('btn-user-menu')).click();
        element(by.id('btn-my-preferences')).click();

        expect(dateFormat.element(by.css('option:checked')).getText()).toEqual('YYYY-MM-DD');
        expect(timeFormat.element(by.css('option:checked')).getText()).toEqual('12HR');
        expect(refreshFreq.getAttribute('value')).toEqual('60');
    });

    it('should not save changes to preferences when Cancel button is pressed', function() {

        var preferencesModal = element(by.css('.modal-dialog'));
        var dateFormat = preferencesModal.element(by.id('preferencesSelectDateFormat'));
        var timeFormat = preferencesModal.element(by.id('preferencesSelectTimeFormat'));
        var refreshFreq = preferencesModal.element(by.id('preferencesRefreshFrequency'));

        //change the date format
        dateFormat.click();
        dateFormat.all(by.css('option')).then(function(options) {
            options[0].click();//select DD-MM-YYYY format
        });

        //change the time format
        timeFormat.click();
        timeFormat.all(by.css('option')).then(function(options) {
            options[1].click();//select 24HR format
        });

        //change the auto refresh frequency
        refreshFreq.clear();
        refreshFreq.sendKeys('200');

        //save the changes
        preferencesModal.element(by.id('btn-my-preferences-cancel')).click();

        //open the modal and assert the values were not saved, and remain as they were before
        element(by.id('btn-user-menu')).click();
        element(by.id('btn-my-preferences')).click();

        expect(dateFormat.element(by.css('option:checked')).getText()).toEqual('YYYY-MM-DD');
        expect(timeFormat.element(by.css('option:checked')).getText()).toEqual('12HR');
        expect(refreshFreq.getAttribute('value')).toEqual('60');

    });
});