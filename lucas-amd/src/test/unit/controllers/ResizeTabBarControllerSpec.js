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

describe('ResizeTabBarController Unit Tests', function() {

    // Global vars
    var localScope,
        rootScope,
        localController,
        localWindow,
        localInterval,
        htmlStr = "<div class=\"clearer resizeTabBar\" dropdown=\"\" resize-tab-bar=\"\" style=\"width:'300px'\"><ul class=\"nav nav-pills tabs\" ng-transclude=\"\">" +
                "        <!-- ngRepeat: canvasBarItem in canvasBar --><li data-ng-repeat=\"canvasBarItem in canvasBar\" ui-sref-active=\"active\" class=\"ng-scope\" style=\"display: inline;\">" +
                "            <div class=\"btn-group\">" +
                "            <a ui-sref=\"canvases.detail({canvasId:canvasBarItem.canvasId})\" class=\"btn btn-default ng-binding\" ui-sref-active=\"btn-primary\" href=\"#\/canvases\/201\"> Hazmat<\/a>" +
                "                <button data-ng-click=\"CloseCanvas(canvasBarItem.canvasId)\" id=\"closeCanvas_201\" class=\"btn btn-default\">×<\/button>" +
                "            <\/div>" +
                "        <\/li><!-- end ngRepeat: canvasBarItem in canvasBar --><li data-ng-repeat=\"canvasBarItem in canvasBar\" ui-sref-active=\"active\" class=\"ng-scope\" style=\"display: inline;\">" +
                "            <div class=\"btn-group\">" +
                "            <a ui-sref=\"canvases.detail({canvasId:canvasBarItem.canvasId})\" class=\"btn btn-default ng-binding btn-primary\" ui-sref-active=\"btn-primary\" href=\"#\/canvases\/221\"> Work Execution<\/a>" +
                "                <button data-ng-click=\"CloseCanvas(canvasBarItem.canvasId)\" id=\"closeCanvas_221\" class=\"btn btn-default\">×<\/button>" +
                "            <\/div>" +
                "        <\/li><!-- end ngRepeat: canvasBarItem in canvasBar --><li data-ng-repeat=\"canvasBarItem in canvasBar\" ui-sref-active=\"active\" class=\"ng-scope\" style=\"display: none;\">" +
                "            <div class=\"btn-group\">" +
                "            <a ui-sref=\"canvases.detail({canvasId:canvasBarItem.canvasId})\" class=\"btn btn-default ng-binding\" ui-sref-active=\"btn-primary\" href=\"#\/canvases\/231\"> abc<\/a>" +
                "                <button data-ng-click=\"CloseCanvas(canvasBarItem.canvasId)\" id=\"closeCanvas_231\" class=\"btn btn-default\">×<\/button>" +
                "            <\/div>" +
                "        <\/li><!-- end ngRepeat: canvasBarItem in canvasBar --><li data-ng-repeat=\"canvasBarItem in canvasBar\" ui-sref-active=\"active\" class=\"ng-scope\" style=\"display: none;\">" +
                "            <div class=\"btn-group\">" +
                "            <a ui-sref=\"canvases.detail({canvasId:canvasBarItem.canvasId})\" class=\"btn btn-default ng-binding\" ui-sref-active=\"btn-primary\" href=\"#\/canvases\/235\"> xyz<\/a>" +
                "                <button data-ng-click=\"CloseCanvas(canvasBarItem.canvasId)\" id=\"closeCanvas_235\" class=\"btn btn-default\">×<\/button>" +
                "            <\/div>" +
                "        <\/li><!-- end ngRepeat: canvasBarItem in canvasBar -->" +
                "    <\/ul><button type=\"button\" class=\"btn btn-default btn-sm dropdown-toggle\" aria-haspopup=\"true\" aria-expanded=\"false\" style=\"visibility: visible;\"><span class=\"caret\"><\/span><\/button><ul class=\"dropdown-menu options\" role=\"menu\" ng-transclude=\"\">" +
                "        <!-- ngRepeat: canvasBarItem in canvasBar --><li data-ng-repeat=\"canvasBarItem in canvasBar\" ui-sref-active=\"active\" class=\"ng-scope\" style=\"display: none;\">" +
                "            <div class=\"btn-group\">" +
                "            <a ui-sref=\"canvases.detail({canvasId:canvasBarItem.canvasId})\" class=\"btn btn-default ng-binding\" ui-sref-active=\"btn-primary\" href=\"#\/canvases\/201\"> Hazmat<\/a>" +
                "                <button data-ng-click=\"CloseCanvas(canvasBarItem.canvasId)\" id=\"closeCanvas_201\" class=\"btn btn-default\">×<\/button>" +
                "            <\/div>" +
                "        <\/li><!-- end ngRepeat: canvasBarItem in canvasBar --><li data-ng-repeat=\"canvasBarItem in canvasBar\" ui-sref-active=\"active\" class=\"ng-scope\" style=\"display: none;\">" +
                "            <div class=\"btn-group\">" +
                "            <a ui-sref=\"canvases.detail({canvasId:canvasBarItem.canvasId})\" class=\"btn btn-default ng-binding btn-primary\" ui-sref-active=\"btn-primary\" href=\"#\/canvases\/221\"> Work Execution<\/a>" +
                "                <button data-ng-click=\"CloseCanvas(canvasBarItem.canvasId)\" id=\"closeCanvas_221\" class=\"btn btn-default\">×<\/button>" +
                "            <\/div>" +
                "        <\/li><!-- end ngRepeat: canvasBarItem in canvasBar --><li data-ng-repeat=\"canvasBarItem in canvasBar\" ui-sref-active=\"active\" class=\"ng-scope\" style=\"display: block;\">" +
                "            <div class=\"btn-group\">" +
                "            <a ui-sref=\"canvases.detail({canvasId:canvasBarItem.canvasId})\" class=\"btn btn-default ng-binding\" ui-sref-active=\"btn-primary\" href=\"#\/canvases\/231\"> abc<\/a>" +
                "                <button data-ng-click=\"CloseCanvas(canvasBarItem.canvasId)\" id=\"closeCanvas_231\" class=\"btn btn-default\">×<\/button>" +
                "            <\/div>" +
                "        <\/li><!-- end ngRepeat: canvasBarItem in canvasBar --><li data-ng-repeat=\"canvasBarItem in canvasBar\" ui-sref-active=\"active\" class=\"ng-scope\" style=\"display: block;\">" +
                "            <div class=\"btn-group\">" +
                "            <a ui-sref=\"canvases.detail({canvasId:canvasBarItem.canvasId})\" class=\"btn btn-default ng-binding\" ui-sref-active=\"btn-primary\" href=\"#\/canvases\/235\"> xyz<\/a>" +
                "                <button data-ng-click=\"CloseCanvas(canvasBarItem.canvasId)\" id=\"closeCanvas_235\" class=\"btn btn-default\">×<\/button>" +
                "            <\/div>" +
                "        <\/li><!-- end ngRepeat: canvasBarItem in canvasBar -->" +
                "    <\/ul><\/div>";

    // Global test setup
    beforeEach(module('amdApp', function($translateProvider) {
        $translateProvider.translations('en', {
            "language-code" : "EN"
        }, 'fr', {
            "language-code" : "fr"
        }, 'de', {
            "language-code" : "de"
        }).preferredLanguage('en');
        $translateProvider.useLoader('LocaleLoader');
    }));

    beforeEach(inject(function($controller, $rootScope, $window, $interval, $httpBackend) {
        rootScope = $rootScope;
        localScope = $rootScope.$new();
        $httpBackend.when('GET').respond({});
        localWindow = $window;
        localInterval = $interval;
        localController = $controller('ResizeTabBarController', {
            $scope : localScope,
            localWindow: $window, 
            $interval: localInterval
        });
    }));

    it('Should handle the init() method', function() {
        var el = $(htmlStr);
        spyOn(localController, 'adjustDisplay').andCallThrough();
        localController.init(el);
        // tigger the resize event
        $(window).trigger("resize");
        // test the presence of resizeTabBar class
        expect(localController.elem).toHaveClass('resizeTabBar')
        // make sure the adjustDisplay is called due to resizing
        expect(localController.adjustDisplay).toHaveBeenCalled();
    });

    it('Should handle the returnDigit() method', function() {
        expect( localController.returnDigit("343px") ).toEqual('343');
        expect( localController.returnDigit("108px") ).toEqual('108');
    });

    it('Should handle the getWidth() method', function() {
        var el = $('<div />')
                    .css('width', "343px")
                    .css('padding-left', 0)
                    .css('padding-right', 0);
        expect( localController.getWidth(el) ).toEqual(343);
    });

    it('Should handle the adjustDisplay() method', function() {
        var el = $(htmlStr).width(1280);
        $($(el.children()[0]).children()).width(10);
        $(el.children()[1]).width(30);
        localController.init(el);
        localInterval.flush();
        localController.adjustDisplay()
        // as the width is more, test the absence of drop up button
        expect( angular.element(localController.elem.children()[1]).css('visibility') ).toEqual("hidden");

        // reduce the width so that only few elements will be visible and then test the presence of dropup button
        el.width(300);
        $($(el.children()[0]).children()[0] ).width(400);
        localController.init(el);
        localInterval.flush();
        localController.adjustDisplay()
        // test the presence of drop up button
        expect( angular.element(localController.elem.children()[1]).css('visibility') ).toEqual("visible");
    });

    it('Should handle refreshFavoriteCanvasListUpdated event', function() {
        // initial setup
        var el = $(htmlStr).width(1280);
        $($(el.children()[0]).children()).width(10);
        $(el.children()[1]).width(30);
        localController.init(el);

        // spy on the methods $emit and adjustDisplay
        spyOn(rootScope, '$emit').andCallThrough();
        spyOn(localController, 'adjustDisplay').andCallThrough();
        // Emit the event "refreshFavoriteCanvasListUpdated"
        rootScope.$emit('refreshFavoriteCanvasListUpdated');
        // flush the interval
        localInterval.flush(0);
        // asser $emit have been called
        expect(rootScope.$emit).toHaveBeenCalled();
        // asser adjustDisplay have been called
        expect(localController.adjustDisplay).toHaveBeenCalled();
    });
});