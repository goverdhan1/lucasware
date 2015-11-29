"use strict";

describe('PHX 1134 - User & Group Maintenance', function () {

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
            loadWidget('Administration', 'Create Or Edit User');
            loadWidget('Administration', 'Group Maintenance');
        }

        //no need to keep doing the same test setup - only run it once
        doSetup = false;
    });

    //Test warning of unsaved changes for new user
    it("should prompt of unsaved changes when creating a new User", function() {

        //Find the widget element
        var widget = element(by.css("#wid100 widgetloader .createEditUserForm"));

        //type in a new Lucas Login and press search button
        widget.element(by.model("fields.username")).sendKeys("mynewuser");
        widget.element(by.css("button .glyphicon-search")).click();

        //clear the widget - should prompt with unsaved changes message
        widget.element(by.css("button .glyphicon-arrow-left")).click();

        //Assert model is displayed
        expect(element(by.css(".modal-dialog")).isPresent()).toBeTruthy();
        expect(element(by.css(".modal-dialog .modal-header")).getText()).toContain("Unsaved Changes");

        //close the modal
        element(by.css(".modal-dialog .modal-footer .btn-warning")).click();
        expect(element(by.css(".modal-dialog")).isPresent()).toBeFalsy();
    });

    //Test warning of unsaved changes for new group
    it("should prompt of unsaved changes when creating a new Group", function() {

        //Find the widget and type in a new Group name
        var widget = element(by.css("#wid101 widgetloader .group-maintenance-widget"));

        //type in a new Group Name and press search button
        widget.element(by.model("fields.groupName")).sendKeys("mynewgroup");
        widget.element(by.css("button .glyphicon-search")).click();

        //clear the widget - should prompt with unsaved changes message
        widget.element(by.css("button .glyphicon-arrow-left")).click();

        //Assert model is displayed
        expect(element(by.css(".modal-dialog")).isPresent()).toBeTruthy();
        expect(element(by.css(".modal-dialog .modal-header")).getText()).toContain("Unsaved Changes");

        //close the modal
        element(by.css(".modal-dialog .modal-footer .btn-warning")).click();
        expect(element(by.css(".modal-dialog")).isPresent()).toBeFalsy();
    });
});