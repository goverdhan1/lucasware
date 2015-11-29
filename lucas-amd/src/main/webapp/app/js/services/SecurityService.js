'use strict';
amdApp.factory('SecurityService', ['RestApiService', 'UserService', 'LocalStoreService',
    function (restApiService, UserService, LocalStoreService) {
        var api = {
            // return the permissions list if any or empty
            getPermissions: function () {
                var profileData =  LocalStoreService.getLSItem('ProfileData');
                if (profileData && profileData.userPermissions)
                    return profileData.userPermissions;
                else
                    return [];
            },

            //return true if the permission is present in the userpermission list.
            hasPermission: function (permission) {
                var permissions = api.getPermissions();
                return permissions && permissions.indexOf(permission) != -1;
            }
        };
        return api;
    }])
;