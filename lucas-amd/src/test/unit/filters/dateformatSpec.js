/**
 * Created by Shaik Basha on 10/31/2014.
 */

describe('Unit testing Filter: dateformat', function () {
    var dateformatFilter;

    beforeEach(module('amdApp'));
    beforeEach(inject(function ($filter) {
        dateformatFilter = $filter("dateformat");
    }));

    it('should be able to format the date input to the "mm-dd-yyyy" format', function () {
        expect(dateformatFilter("2014-05-01T00:00:00.000Z", "mm-dd-yyyy")).toBe("05-01-2014");
    });

    it('should be able to format the date input to the "MMM dd, yyyy" format ', function () {
        expect(dateformatFilter("2014-01-01T00:00:00.000Z", "MMM dd, yyyy")).toBe("Jan 01, 2014");
    });

    it('should be able to format the date input to the "dd-mm-yyyy" format', function () {
        expect(dateformatFilter("2014-01-21T00:00:00.000Z", "dd-mm-yyyy")).toBe("21-01-2014");
    });

});