/**
 * Created by Shaik Basha on 2/10/2015.
 * used in ng repeat to avoid automatic sorting of keys
 */

'use strict';

amdApp.filter('toArray', function () {

    return function (obj) {
        if (!(obj instanceof Object)) {
            return obj;
        }

        return Object.keys(obj).map(function (key) {
            return Object.defineProperties(obj[key], {
                "$key": {
                    __proto__: null, value: key
                },
                "$value": {
                    __proto__: null, value: (obj[key])
                }
            });
        });
    }
});