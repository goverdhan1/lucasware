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

 Copyright (c) Lucas Systems, 2015
 ALL RIGHTS RESERVED
 */
 
"use strict";

describe('PHX 1201 - Search User Automatic Refresh Settings', function () {

    //test variables
    var doSetup = true;
    var widgetPallet = null;

    // re usable login
    var login = function(username, password) {
        browser.get('');
        element(by.model('username')).sendKeys(username);
        element(by.model('password')).sendKeys(password);
        element(by.css('.loginBtn')).click();
    };

    //load a widget
    var loadWidget = function(category, widget) {
        //load the widget pallet
        element(by.css('#load-widget a')).click();
        widgetPallet = element(by.id('widgetsPallette'));

        //select category and then load the desired widget
        widgetPallet.element(by.cssContainingText('.menu ul li', category)).click();
        widgetPallet.element(by.cssContainingText('.content ul li h2', widget)).click();
    };

    beforeEach(function() {
        if(doSetup) {
            //login
            login('jack123', 'secret');

            //load Create/Edit User and Group Maintenance widget
            loadWidget('Administration', 'Search User');

            //click on cog menu
            element(by.css("#wid100 .ui-widget-header .glyphicon-cog")).click();
        }

        //no need to keep doing the same test setup - only run it once
        doSetup = false;
    });

    //Test Automatic refresh settings are visible in the cog menu
    it("should show Automatic Refresh Settings in cog menu", function() {
        //Automatic refresh setting should be visible on the cog menu
        expect(element(by.css(".modal-dialog automatic-refresh")).isPresent()).toBeTruthy();
        expect(element(by.css("automatic-refresh #globalOverride")).isPresent()).toBeTruthy();
        expect(element(by.css("automatic-refresh #refreshEnabled")).isPresent()).toBeTruthy();
        expect(element(by.css("automatic-refresh #refreshFrequency")).isPresent()).toBeTruthy();

        //assert default state
        expect(element(by.css("automatic-refresh #globalOverride")).isEnabled()).toBeTruthy();
        expect(element(by.css("automatic-refresh #refreshEnabled")).isEnabled()).toBeFalsy();
        expect(element(by.css("automatic-refresh #refreshFrequency")).isEnabled()).toBeFalsy();
    });

    //Test Automatic Refresh Settings can be enabled correctly
    it('should be possible to enable/disable Automatic Refresh Settings', function () {
        //check the override setting and expect the Refresh Enabled control to become active
        element(by.css("automatic-refresh #globalOverride")).click();
        expect(element(by.css("automatic-refresh #refreshEnabled")).isEnabled()).toBeTruthy();
        expect(element(by.css("automatic-refresh #refreshFrequency")).isEnabled()).toBeFalsy();

        //check the Refresh Enabled setting and expect frequency control to become active
        element(by.css("automatic-refresh #refreshEnabled")).click();
        expect(element(by.css("automatic-refresh #refreshFrequency")).isEnabled()).toBeTruthy();

        //uncheck the override setting and expect the Refresh Enabled and Frequency controls to become inactive again
        element(by.css("automatic-refresh #globalOverride")).click();
        expect(element(by.css("automatic-refresh #refreshEnabled")).isEnabled()).toBeFalsy();
        expect(element(by.css("automatic-refresh #refreshFrequency")).isEnabled()).toBeFalsy();
    });

    //Test the Refresh Frequency can be set to a valid number (> 20)
    it('should allow valid numeric value > 20', function () {
        //enable the frequency control by enabling the override setting
        element(by.css("automatic-refresh #globalOverride")).click();

        //set the frequency > 20
        element(by.css("automatic-refresh #refreshFrequency")).clear();
        element(by.css("automatic-refresh #refreshFrequency")).sendKeys("35");

        //expect no errors to be displayed, and the save button to be present
        expect(element(by.id("autoRefreshInvalidValueErrMsg")).isPresent()).toBeFalsy();
        expect(element(by.css(".modal-footer button.btn-success")).isPresent()).toBeTruthy();
    });

    //Test the Refresh Frequency cannot be set to an invalid number (< 20)
    it('should not allow numeric value < 20', function () {
        //set the frequency < 20
        element(by.css("automatic-refresh #refreshFrequency")).clear();
        element(by.css("automatic-refresh #refreshFrequency")).sendKeys("15");

        //expect error to be displayed, and the save button to be hidden
        expect(element(by.id("autoRefreshInvalidValueErrMsg")).isPresent()).toBeTruthy();
        expect(element(by.css(".modal-footer button.btn-success")).isPresent()).toBeFalsy();
    });

    //Test the Refresh Frequency cannot be set to an empty value
    it('should not allow null/undefined value', function () {
        //clear the frequency
        element(by.css("automatic-refresh #refreshFrequency")).clear();

        //expect error to be displayed, and the save button to be hidden
        expect(element(by.id("autoRefreshInvalidValueErrMsg")).isPresent()).toBeTruthy();
        expect(element(by.css(".modal-footer button.btn-success")).isPresent()).toBeFalsy();
    });

    //Test the Refresh Frequency cannot be set to a value that is not a number
    it('should not allow non-numeric value', function () {
        //set the frequency to a non-numeric value
        element(by.css("automatic-refresh #refreshFrequency")).clear();
        element(by.css("automatic-refresh #refreshFrequency")).sendKeys("jhdfdf");

        //expect error to be displayed, and the save button to be hidden
        expect(element(by.id("autoRefreshInvalidValueErrMsg")).isPresent()).toBeTruthy();
        expect(element(by.css(".modal-footer button.btn-success")).isPresent()).toBeFalsy();

        //Close the modal, discarding changes
        element(by.css(".modal-footer button.btn-warning")).click();
        expect(element(by.css(".modal-dialog")).isPresent()).toBeFalsy();
    });

    //Test it persists the settings after saving them
    it('should persist saved Automatic Refresh Settings', function () {
        //open up the cog menu
        element(by.css("#wid100 .ui-widget-header .glyphicon-cog")).click();
        expect(element(by.css(".modal-dialog automatic-refresh")).isPresent()).toBeTruthy();

        //configure the Automatic Refresh settings
        element(by.css("automatic-refresh #globalOverride")).click();
        element(by.css("automatic-refresh #refreshEnabled")).click();
        element(by.css("automatic-refresh #refreshFrequency")).clear();
        element(by.css("automatic-refresh #refreshFrequency")).sendKeys("35");

        //save the settings
        element(by.css(".modal-footer button.btn-success")).click();
        expect(element(by.css(".modal-dialog")).isPresent()).toBeFalsy();

        //open up the cog menu again and assert same value are present
        element(by.css("#wid100 .ui-widget-header .glyphicon-cog")).click();
        expect(element(by.css("automatic-refresh #globalOverride")).isSelected()).toBeTruthy();
        expect(element(by.css("automatic-refresh #refreshEnabled")).isSelected()).toBeTruthy();
        expect(element(by.css("automatic-refresh #refreshFrequency")).getAttribute('value')).toEqual("35");
    });
});