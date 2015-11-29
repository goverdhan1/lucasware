"use strict";

describe("LogoutAlert Service Unit Tests", function () {
    // Global variables
    var localLogoutAlertService = null;
    var localScope = null;
    var localTimeout = null;

    beforeEach(module('amdApp'));

    var injectLogoutAlertService = inject(function (LogoutAlertService) {
        localLogoutAlertService = LogoutAlertService;
    });


    beforeEach(function() {
        injectLogoutAlertService();
    });


    it("Should handle setAlerts method", function () {
        spyOn(localLogoutAlertService, 'setAlerts').andCallThrough();
        localLogoutAlertService.setAlerts([
            {
                type: 'success',
                msg: 'Logout Successful'
            }
        ]);
        expect(localLogoutAlertService.setAlerts).toHaveBeenCalledWith([
            {
                type: 'success',
                msg: 'Logout Successful'
            }
        ]);
    });

    it("Should handle getAlerts method", function () {
        localLogoutAlertService.setAlerts([
            {
                type: 'success',
                msg: 'Logout Successful'
            }
        ]);
        expect(localLogoutAlertService.getAlerts()[0].msg).toBe('Logout Successful');
    });


});