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

//import the common amd helper functions
var amdUtils = require('../resources/amdUtils.js');

var initialWindowSize;

browser.manage().window().getSize().then(function(size) {
    initialWindowSize = size;
    console.log("width", initialWindowSize.width);
    console.log("height", initialWindowSize.height);
});

describe("Canvas Specs", function() {

    it('login once', function() {
        // login
        amdUtils.login("e2euser", "secret");
    });

    describe('Canvas layout and configurations', function() {

        it('Test loading of amd logo', function() {
            // test presence of amd logo
            expect(element.all(by.id('amdLogo')).count()).toEqual(1);
        });

        it('Test loading of customer logo', function() {
            // test presence of customer logo
            expect(element.all(by.id('customerLogo')).count()).toEqual(1);
        });

        it('Test loading of user preferences', function() {
            // test presence of user menu
            expect(element.all(by.id('btn-user-menu')).count()).toEqual(1);
            // open the dropdown
            element(by.id('btn-user-menu')).click();
            // test presence of preferences button
            expect(element.all(by.id('btn-my-preferences')).count()).toEqual(1);
            // close the dropdown
            element(by.id('btn-user-menu')).click();
        });

    });

    describe('Create new canvas', function() {

        describe('Create new canvas tests', function() {

            //Test setup
            beforeEach(function() {
                // click add canvas button
                element(by.id('add-canvas-btn')).click();
                // check the create new canvas button existence 
                expect(element.all(by.css('#btn-create-new-canvas')).count()).toBe(1);
                // click New Canvas UI button
                element(by.id('btn-create-new-canvas')).click();
                // check the Create New Canvas UI existence
                expect(element.all(by.css('#createNewCanvas')).count()).toBe(1);

            });

            // test create canvas
            it('test canvas name required validation', function() {
                // click save button
                element(by.id('btn-create-canvas')).click();
                // check the error message is shown
                expect(element.all(by.css('#name-required')).count()).toBe(1);
                element(by.id('btn-cancel-canvas')).click();
            });

            it('test canvas creation', function() {
                element(by.model('canvasName')).sendKeys('ProductTestCanvas123');
                // click save button
                element(by.id('btn-create-canvas')).click();
                // check the canvas is created
                expect(element.all(by.css('#createNewCanvas')).count()).toBe(0);
            });

            it('test dulplicate canvas validation', function() {
                element(by.model('canvasName')).sendKeys('ProductTestCanvas123');
                // click save button
                element(by.id('btn-create-canvas')).click();
                // check the error message is shown
                expect(element.all(by.css('#name-already-exists')).count()).toBe(1);
                element(by.id('btn-cancel-canvas')).click();
            });

        });

        describe('clean up', function() {
            it('delete the canvas created in footer and canvas pallete', function() {
                amdUtils.deleteCanvasInFooterAndCanvasPallete('ProductTestCanvas123');
            });
        });

    });

    describe('Loading widgets', function() {

        var widgetPallet;
        var widgetPalletCategories;
        var workExecCategory;
        var adminCategory;
        var reportCategory;
        var eaiCategory;

        it('should load the widget pallet, with all categories', function() {
            // Load up the Widget Pallet
            element(by.css('#load-widget a')).click();

            widgetPallet = element(by.id('widgetsPallette'));
            widgetPalletCategories = widgetPallet.all(by.css('.menu ul li a'));

            workExecCategory = widgetPallet.element(by.cssContainingText('.menu ul li', 'Work Execution'));
            adminCategory = widgetPallet.element(by.cssContainingText('.menu ul li', 'Administration'));
            reportCategory = widgetPallet.element(by.cssContainingText('.menu ul li', 'Reporting'));
            eaiCategory = widgetPallet.element(by.cssContainingText('.menu ul li', 'EAI'));

            // assert widget pallet is loaded 4 with categories
            expect(widgetPallet.isPresent()).toBeTruthy();
            expect(widgetPalletCategories.count()).toEqual(4);

            widgetPalletCategories.getText().then(function(text) {
                var categories = text.toString();

                // assert the 3 categories are present
                expect(categories).toContain('Work Execution');
                expect(categories).toContain('Administration');
                expect(categories).toContain('Reporting');
            });

            // close the popup
            element(by.css('#btn-cancel-widgets-pallete')).click();

            // re login
            amdUtils.relogin('e2euser', 'secret');
        });

    });

    describe('Canvas Interactions', function() {
        it('should test adding and deleteing the "Search User" widget', function() {
            // Open Load a widget UI
            element(by.css('#load-widget a')).click();
            //select the Administration category
            element(by.id('widgetsPallette')).element(by.cssContainingText('.menu ul li', 'Administration')).click();

            // select the Search User widget
            element(by.id('widgetsPallette')).element(by.cssContainingText('.content ul li h2', 'Search User')).click();

            // Delete the widget
            element(by.id('wid100-remove-widget-btn')).click();
            element(by.id('btn-delete-confirmation')).click();
        });

        it('should test the deletion of "Search User" widget', function() {
            expect(element(by.id('wid100-remove-widget-btn')).isPresent()).toBeFalsy();
        });

        it('close the "TestCanvas900" canvas', function() {
            // close the canvas TestCanvas900
            element(by.css('#closeCanvas_900')).click();
            element(by.css('#btn-canvas-close-yes')).click();
            // check the canvas is removed
            expect(element(by.id('closeCanvas_900')).isPresent()).toBeFalsy();
        });

        it('Load the "TestCanvas900" canvas', function() {
            // close the canvas TestCanvas900
            element(by.id('add-canvas-btn')).click();
            element(by.id('900')).click();
            // check the canvas is removed
            expect(element(by.id('closeCanvas_900')).isPresent()).toBeTruthy();

            // re login
            amdUtils.relogin('e2euser', 'secret');
        })

    });

    describe('Deletion of private canvases ', function() {
        it('create a private canvas', function() {
            // click add canvas button
            element(by.id('add-canvas-btn')).click();
            // check the create new canvas button existence 
            expect(element.all(by.css('#btn-create-new-canvas')).count()).toBe(1);
            // click New Canvas UI button
            element(by.id('btn-create-new-canvas')).click();
            // check the Create New Canvas UI existence
            expect(element.all(by.css('#createNewCanvas')).count()).toBe(1);
            //create the canvas

            element(by.model('canvasName')).sendKeys('PrivateTestCanvas');
            // click save button
            element(by.id('btn-create-canvas')).click();
            // check the canvas is created
            expect(element.all(by.css('#createNewCanvas')).count()).toBe(0);
        });

        it('delete the canvas from the pallete and footer', function() {
            amdUtils.deleteCanvasInFooterAndCanvasPallete('PrivateTestCanvas');
            // test removal
            expect(element(by.cssContainingText('li a', 'PrivateTestCanvas')).isPresent()).toBeFalsy();
        });

    });

    describe('Canvases Cloning', function() {

        describe('Canvases Cloning tests', function() {
            beforeEach(function() {
                // click user options menu
                element(by.id('btn-user-menu')).click();
                // click Clone Canvas UI button
                element(by.id('btn-user-clone-canvas')).click();
                // check the clone New Canvas UI existence
                expect(element(by.css('#cloneCanvas')).isPresent()).toBeTruthy();
            });

            // test clone canvas
            it('test canvas name required validation', function() {
                // click save button
                element(by.id('btn-clone-canvas')).click();
                // check the error message is shown
                expect(element.all(by.css('#name-required')).count()).toBe(1);
                element(by.id('btn-cancel-canvas')).click();
            });

            it('test dulplicate canvas validation', function() {
                element(by.model('canvasName')).sendKeys('Work Execution');

                // click save button
                element(by.id('btn-clone-canvas')).click();
                // check the error message is shown
                expect(element.all(by.css('#name-already-exists')).count()).toBe(1);
                element(by.id('btn-cancel-canvas')).click();
            });

            it('test clone canvas', function() {
                element(by.model('canvasName')).sendKeys('ProductCloneTest');
                // click save button
                element(by.id('btn-clone-canvas')).click();
                // check the canvas is cloned
                expect(element.all(by.css('#cloneNewCanvas')).count()).toBe(0);
                // test the presence of canvas
                expect(element.all(by.cssContainingText('.resizeTabBar li a', 'ProductCloneTest')).count()).toBeGreaterThan(0);
            });
        });

        describe('clean up', function() {
            it('delete the canvas created in footer and canvas pallete', function() {
                amdUtils.deleteCanvasInFooterAndCanvasPallete('ProductCloneTest');
            });
        });

    });

    describe('Rendering of active canvas in live modes', function() {
        describe('Rendering of active canvas in live modes tests', function() {

            it('add "Search User" widget', function() {
                // Open Load a widget UI
                element(by.css('#load-widget a')).click();
                //select the Administration category
                element(by.id('widgetsPallette')).element(by.cssContainingText('.menu ul li', 'Administration')).click();

                // select the Search User widget
                element(by.id('widgetsPallette')).element(by.cssContainingText('.content ul li h2', 'Search User')).click();
            });

            it('add "Create Edit User" widget', function() {
                // Open Load a widget UI
                element(by.css('#load-widget a')).click();
                //select the Administration category
                element(by.id('widgetsPallette')).element(by.cssContainingText('.menu ul li', 'Administration')).click();

                // select theCreate Or Edit User widget
                element(by.id('widgetsPallette')).element(by.cssContainingText('.content ul li h2', 'Create Or Edit User')).click();
            });

            it('Create canvas "LoadTestCanvas"', function() {
                // click add canvas button
                element(by.id('add-canvas-btn')).click();
                // click New Canvas UI button
                element(by.id('btn-create-new-canvas')).click();
                // create canvas 'LoadTestCanvas'
                element(by.model('canvasName')).sendKeys('LoadTestCanvas');
                // click save button
                element(by.id('btn-create-canvas')).click();
                // check the canvas is created
                expect(element.all(by.css('#createNewCanvas')).count()).toBe(0);
            });

            it('relogin and test the widgets are loaded in the live mode', function() {
                // re login
                amdUtils.relogin('e2euser', 'secret');

                // select TestCanvas900 canvas
                element(by.cssContainingText('li a', 'TestCanvas900')).click();

                // test the widgets are loaded
                expect(element(by.id('wid100')).isPresent()).toBeTruthy();
                expect(element(by.id('wid101')).isPresent()).toBeTruthy();
            });

        });

        describe('clean up', function() {
            it('Delete the widgets', function() {
                // Delete the widget
                element(by.id('wid100-remove-widget-btn')).click();
                element(by.id('btn-delete-confirmation')).click();
                element(by.id('wid101-remove-widget-btn')).click();
                element(by.id('btn-delete-confirmation')).click();
            })

            it('delete the canvas created in footer and canvas pallete', function() {
                amdUtils.deleteCanvasInFooterAndCanvasPallete('LoadTestCanvas');
            });
        });

    });

    describe('Flipping of canvases', function() {

        describe('Flipping of canvasestests', function() {
            it('Create canvas "FlipTestCanvas"', function() {
                // click add canvas button
                element(by.id('add-canvas-btn')).click();
                // click New Canvas UI button
                element(by.id('btn-create-new-canvas')).click();
                // create canvas 'FlipTestCanvas'
                element(by.model('canvasName')).sendKeys('FlipTestCanvas');
                // click save button
                element(by.id('btn-create-canvas')).click();
                // check the canvas is created
                expect(element.all(by.css('#createNewCanvas')).count()).toBe(0);
            });

            it('Select canvas "TestCanvas900"', function() {
                element(by.cssContainingText('li a', 'TestCanvas900')).click();
                // test the canvas is selected
                expect(element(by.cssContainingText('li a.btn-primary', 'TestCanvas900')).isPresent()).toBeTruthy();
            });

            it('relogin and test the filipped canvases and open canvases', function() {
                // re login
                amdUtils.relogin('e2euser', 'secret');

                // test the TestCanvas900 canvas is selected
                expect(element(by.cssContainingText('li a.btn-primary', 'TestCanvas900')).isPresent()).toBeTruthy();
                // check the order of open canvases
                expect(element.all(by.css('.resizeTabBar .nav li a')).get(0).getText()).toEqual('TestCanvas900');
                expect(element.all(by.css('.resizeTabBar .nav li a')).get(1).getText()).toEqual('FlipTestCanvas');
            });

        });

        describe('clean up', function() {
            it('delete the canvas created in footer and canvas pallete', function() {
                amdUtils.deleteCanvasInFooterAndCanvasPallete('FlipTestCanvas');
            });
        });
    });

    describe('Canvas header and footer tests', function() {
        describe('Canvas header and footer tests', function() {
            it('Create canvas "FooterTestCanvas"', function() {
                // click add canvas button
                element(by.id('add-canvas-btn')).click();
                // click New Canvas UI button
                element(by.id('btn-create-new-canvas')).click();
                // create canvas 'FooterTestCanvas'
                element(by.model('canvasName')).sendKeys('FooterTestCanvas');
                // click save button
                element(by.id('btn-create-canvas')).click();
                // check the canvas is created
                expect(element.all(by.css('#createNewCanvas')).count()).toBe(0);
            });

            // load a widget button collapses into the preferences drop-down when in mobile/table view

            it('check load a widget button collapses into the preferences drop-down when in mobile/table view', function() {
                browser.driver.manage().window().setSize(300, 500);
                // open settings menu
                element(by.id('btn-user-menu')).click();
                expect(element(by.css('.topright #load-widget')).isDisplayed()).toBeTruthy();
                // close settings menu
                element(by.id('btn-user-menu')).click();
            })

            it('add "Search User" widget', function() {
                // open settings menu
                element(by.id('btn-user-menu')).click();

                // Open Load a widget UI
                element(by.css('.topright #load-widget a')).click();
                //select the Administration category
                element(by.id('widgetsPallette')).element(by.cssContainingText('.menu ul li', 'Administration')).click();

                // select the Search User widget
                element(by.id('widgetsPallette')).element(by.cssContainingText('.content ul li h2', 'Search User')).click();
            });

            it('add "Create Edit User" widget', function() {
                // open settings menu
                element(by.id('btn-user-menu')).click();

                // Open Load a widget UI
                element(by.css('.topright #load-widget a')).click();
                //select the Administration category
                element(by.id('widgetsPallette')).element(by.cssContainingText('.menu ul li', 'Administration')).click();

                // select theCreate Or Edit User widget
                element(by.id('widgetsPallette')).element(by.cssContainingText('.content ul li h2', 'Create Or Edit User')).click();
            });

            it('scroll to bottom and check header is hidden', function() {
                // scroll to the bottom
                browser.executeScript(amdUtils.scrollIntoView, browser.findElement(by.css('footer')));

                browser.driver.sleep(3000);
                // check the header is hidden
                browser.executeScript('return $("header.hideHeader").length').then(function(res) {
                    expect(res).toBe(1);
                });

                // scroll to the top
                browser.executeScript(amdUtils.scrollIntoView, browser.findElement(by.css('body')));
                browser.driver.sleep(3000);

                // check the header is visible
                browser.executeScript('return $("header.showHeader").length').then(function(res) {
                    expect(res).toBe(1);
                });
            });

            // open canvases in canvas bar should collapse into drop-up menu when bar is full
            it('check the "FooterTestCanvas" is moved to drop up', function() {
                // open the drop up
                element(by.css('.resizeTabBar button.dropdown-toggle')).click();
                expect(element(by.cssContainingText('.resizeTabBar ul.dropdown-menu li a', 'FooterTestCanvas')).isPresent()).toBeTruthy();
                // set the original size
                browser.driver.manage().window().setSize(initialWindowSize.width, initialWindowSize.height);
            });

        });

        describe('clean up', function() {
            it('delete the canvas created in footer and canvas pallete', function() {
                amdUtils.deleteCanvasInFooterAndCanvasPallete('FooterTestCanvas');
            });
        });

    });

    describe('Publishing a canvas', function() {

        describe('Publishing a canvas tests', function() {
            it('Create canvas "PublishTestCanvas"', function() {
                // click add canvas button
                element(by.id('add-canvas-btn')).click();
                // click New Canvas UI button
                element(by.id('btn-create-new-canvas')).click();
                // create canvas 'PublishTestCanvas'
                element(by.model('canvasName')).sendKeys('PublishTestCanvas');
                // click save button
                element(by.id('btn-create-canvas')).click();
                // check the canvas is created
                expect(element.all(by.css('#createNewCanvas')).count()).toBe(0);
            });

            it('Publish canvas "PublishTestCanvas"', function() {
                // click user options menu
                element(by.id('btn-user-menu')).click();
                // click Publish Canvas UI button
                element(by.id('btn-user-publish-canvas')).click();
                // check the Publish New Canvas UI existence
                expect(element(by.css('#publishCanvas')).isPresent()).toBeTruthy();

                // click Publish Canvas button
                element(by.id('btn-publish-canvas')).click();

                // check the canvas is published
                expect(element(by.css('#publishCanvas')).isPresent()).toBeFalsy();
            });
        });

        describe('clean up', function() {
            it('delete the canvas created in footer and canvas pallete', function() {
                amdUtils.deleteCanvasInFooter('PublishTestCanvas');
            });
        });
    });


    it('logout', function() {
        amdUtils.logout();
    });
});