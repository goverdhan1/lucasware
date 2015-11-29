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

"use strict";

describe('PHX-975 FE Canvases Palette that lists all widgets available for loading', function () {

    // Global matcher helpers
    var hasClass = function (element, className) {
        return element.getAttribute('class').then(function (classes) {
            return classes.split(' ').indexOf(className) !== -1;
        });
    };

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

    var loadCanvas =  function(canvasName) {
        // open the canvases popup
        element(by.id('add-canvas-btn')).click();

        // open JillCanvas1 canvas
        element(by.cssContainingText('li a', canvasName)).click();
    };

    var deleteCanvas =  function(canvasName) {
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

        // close the pop up
        element(by.id('btn-close-create-new-canvas')).click();
    };

    //Test setup - run only once
    var setupComplete = false;
    var runCleanup = false;
    beforeEach(function () {
        if (!setupComplete) {
            //log the user in
            login('jack123', 'secret');

            //createNewCanvas('BlankNewCanvas');

            logout();

            login('jack123', 'secret');

            //Add a new canvas so we have a blank canvas to test loading the widgets in isolation
            element(by.id('add-canvas-btn')).click();
            element(by.cssContainingText('li a', 'BlankNewCanvas')).click();

            setupComplete = true;
        }
    });

    describe('loading each widget', function () {

        var widgetPallet = null;
        var widgetPalletCategories = null;

        var workExecCategory = null;
        var adminCategory = null;
        var reportCategory = null;

        //before loading each widget, open the Widget Pallet. This removes some boilder-plate
        //code from each test case
        beforeEach(function() {
            //if the widget pallet is not open, open it!
            if (widgetPallet === null) {
                //Load up the Widget Pallet
                element(by.css('#load-widget a')).click();

                widgetPallet = element(by.id('widgetsPallette'));
                widgetPalletCategories = widgetPallet.all(by.css('.menu ul li a'));

                workExecCategory = widgetPallet.element(by.cssContainingText('.menu ul li','Work Execution'));
                adminCategory = widgetPallet.element(by.cssContainingText('.menu ul li','Administration'));
                reportCategory = widgetPallet.element(by.cssContainingText('.menu ul li','Reporting'));
            }
        });

        xit('should load the widget pallet, with all categories', function() {
            //assert widget pallet is loaded 3 with categories
            expect(widgetPallet.isPresent()).toBeTruthy();
            expect(widgetPalletCategories.count()).toEqual(4);

            widgetPalletCategories.getText().then(function(text) {
                var categories = text.toString();

                //assert the 3 categories are present
                expect(categories).toContain('Work Execution');
                expect(categories).toContain('Administration');
                expect(categories).toContain('Reporting');
            });
        });

        xit('should display correct widgets in "Work Execution" category', function () {
            //select Work Execution category
            workExecCategory.click();
            expect(workExecCategory.getAttribute('class')).toContain('active');

            //check correct widgets are shown
            var widgetList = widgetPallet.all(by.css('.content ul li'));
            expect(widgetList.count()).toEqual(2);

            widgetList.all(by.css('h2')).getText().then(function(text) {
                var widgets = text.toString();

                expect(widgets).toContain("Search Product");
                expect(widgets).toContain("Pack Factor Hierarchy");
            });
        });

        xit('should display correct widgets in "Administration" category', function () {
            //select Administration category
            adminCategory.click();
            expect(adminCategory.getAttribute('class')).toContain('active');

            //check correct widgets are shown
            var widgetList = widgetPallet.all(by.css('.content ul li'));
            expect(widgetList.count()).toEqual(4);

            widgetList.all(by.css('h2')).getText().then(function(text) {
                var widgets = text.toString();

                expect(widgets).toContain("Create Or Edit User");
                expect(widgets).toContain("Search User");
                expect(widgets).toContain("Group Maintenance");
                expect(widgets).toContain("Search group");
            });
        });

        xit('should display correct widgets in "Reporting" category', function () {
            //select Reporting category
            reportCategory.click();
            expect(reportCategory.getAttribute('class')).toContain('active');

            //check correct widgets are shown
            var widgetList = widgetPallet.all(by.css('.content ul li'));
            expect(widgetList.count()).toEqual(1);

            widgetList.all(by.css('h2')).getText().then(function(text) {
                var widgets = text.toString();

                expect(widgets).toContain("Pickline By Wave Bar chart");
            });
        });

        xit('should load the "Create or Edit User" widget', function () {
            //select the Administration category
            adminCategory.click();

            //select the Create or Edit User widget
            widgetPallet.element(by.cssContainingText('.content ul li h2', 'Create Or Edit User')).click();

            var loadedWidgets = element.all(by.css('.ui-widget-header .widget-title'));
            loadedWidgets.getText().then(function (text) {
                //assert widget is loaded on the canvas
                expect(text.toString()).toContain('create-or-edit-user-form-widget');
            });

            //assert widget pallet was closed
            expect(widgetPallet.isPresent()).toBeFalsy();
            widgetPallet = null;
        });

        xit('should load the "Search User" widget', function () {
            //select the Administration category
            adminCategory.click();

            //select the Search User widget
            widgetPallet.element(by.cssContainingText('.content ul li h2', 'Search User')).click();

            var loadedWidgets = element.all(by.css('.ui-widget-header .widget-title'));
            loadedWidgets.getText().then(function (text) {
                //assert widget is loaded on the canvas
                expect(text.toString()).toContain('search-user-grid-widget');
            });

            //assert widget pallet was closed
            expect(widgetPallet.isPresent()).toBeFalsy();
            widgetPallet = null;
        });

        xit('should load the "Group Maintenance" widget', function () {
            //select the Administration category
            adminCategory.click();

            //select the Group Maintenance widget
            widgetPallet.element(by.cssContainingText('.content ul li h2', 'Group Maintenance')).click();

            var loadedWidgets = element.all(by.css('.ui-widget-header .widget-title'));
            loadedWidgets.getText().then(function (text) {
                //assert widget is loaded on the canvas
                expect(text.toString()).toContain('group-maintenance-widget');
            });

            //assert widget pallet was closed
            expect(widgetPallet.isPresent()).toBeFalsy();
            widgetPallet = null;
        });

        xit('should load the "Search Group" widget', function () {
            //select the Administration category
            adminCategory.click();

            //select the Search Group widget
            widgetPallet.element(by.cssContainingText('.content ul li h2', 'Search group')).click();

            var loadedWidgets = element.all(by.css('.ui-widget-header .widget-title'));
            loadedWidgets.getText().then(function (text) {
                //assert widget is loaded on the canvas
                expect(text.toString()).toContain('search-group-grid-widget');
            });

            //assert widget pallet was closed
            expect(widgetPallet.isPresent()).toBeFalsy();
            widgetPallet = null;
        });

        xit('should load the "Pick Line by Wave" widget', function () {
            //select the Reporting category
            reportCategory.click();

            //select the Pickline By Wave Bar chart widget
            widgetPallet.element(by.cssContainingText('.content ul li h2', 'Pickline By Wave Bar chart')).click();

            var loadedWidgets = element.all(by.css('.ui-widget-header .widget-title'));
            loadedWidgets.getText().then(function (text) {
                //assert widget is loaded on the canvas
                expect(text.toString()).toContain('pickline-by-wave-barchart-widget');
            });

            //assert widget pallet was closed
            expect(widgetPallet.isPresent()).toBeFalsy();
            widgetPallet = null;
        });

        xit('should load the "Search Product" widget', function () {
            //select the Administration category
            workExecCategory.click();

            //select the Search Product widget
            widgetPallet.element(by.cssContainingText('.content ul li h2', 'Search Product')).click();

            var loadedWidgets = element.all(by.css('.ui-widget-header .widget-title'));
            loadedWidgets.getText().then(function (text) {
                //assert widget is loaded on the canvas
                expect(text.toString()).toContain('search-product-grid-widget');
            });

            //assert widget pallet was closed
            expect(widgetPallet.isPresent()).toBeFalsy();
            widgetPallet = null;
        });

        xit('should load the "Pack Factor Hierarchy" widget', function () {
            //select the Administration category
            workExecCategory.click();

            //select the Pack Factor Hierarchy widget
            widgetPallet.element(by.cssContainingText('.content ul li h2', 'Pack Factor Hierarchy')).click();

            var loadedWidgets = element.all(by.css('.ui-widget-header .widget-title'));
            loadedWidgets.getText().then(function (text) {
                //assert widget is loaded on the canvas
                expect(text.toString()).toContain('pack-factor-hierarchy-widget');
            });

            //assert widget pallet was closed
            expect(widgetPallet.isPresent()).toBeFalsy();
            widgetPallet = null;
            runCleanup = true;
        });
    });

    afterEach(function() {
        if (runCleanup) {
            // loadCanvas();
            // deleteCanvas('BlankNewCanvas');
            logout();
        }
    });
});
