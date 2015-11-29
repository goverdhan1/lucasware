/**
 *  Created by Basha on 10/30/2014.
 *
 *  "dateformat" filter formats the date based on the server provided date irrespective of client locale
 */

'use strict';

amdApp.filter("dateformat", function ($filter, $log) {
    return function (input, dateFormat) {
        input = $.trim(input)
        if (input.length == 0) {
            $log.error("dateformat error: ", "empty date");
            return input;
        }
        try {
            // the date coming from the server will be in format "2014-01-01T00:00:00.000Z"
            var day = parseInt(input.substring(8, 10)),
                month = parseInt(input.substring(5, 7)) - 1,
                year = parseInt(input.substring(0, 4)),
                date;
            // check for valid date
            date = new Date(year, month, day)
            dateFormat = dateFormat.replace(/m/g, "M");
            // if the date is valid return the formatted date
            return $filter('date')(date, dateFormat);
        } catch (e) {
            // catch date exception
            $log.error("dateformat error: ", e);
            return input;
        }
    };
});