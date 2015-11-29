/**
 * Created by Shaik Basha on 2/11/2015.
 */

describe('Unit testing Filter: toArray', function () {
    var toArrayFilter;

    var jackMockData = {
        "status": "success",
        "code": "200",
        "message": "Request processed successfully",
        "level": null,
        "uniqueKey": null,
        "token": null,
        "explicitDismissal": null,
        "data": {
            "canvasByCategory": {
                "private": [{
                    "canvasId": 100,
                    "name": "abc"
                }, {
                    "canvasId": 101,
                    "name": "bcd"
                }],
                "company": [{
                    "canvasId": 110,
                    "name": "cde"
                }, {
                    "canvasId": 111,
                    "name": "def"
                }],
                "lucas": [{
                    "canvasId": 201,
                    "name": "efg"
                }, {
                    "canvasId": 202,
                    "name": "fgh"
                }]
            },
            "user": {
                "userId": 3,
                "userName": "jack123",
                "permissionSet": ["create-canvas"]
            }
        }
    };

    var jillMockData = {
        "status": "success",
        "code": "200",
        "message": "Request processed successfully",
        "level": null,
        "uniqueKey": null,
        "token": null,
        "explicitDismissal": null,
        "data": {
            "canvasByCategory": {
                "private": [{
                    "canvasId": 100,
                    "name": "abc"
                }],
                "company": [{
                    "canvasId": 110,
                    "name": "cde"
                }]
            },
            "user": {
                "userId": 3,
                "userName": "jack123",
                "permissionSet": ["create-canvas"]
            }
        }
    };

    beforeEach(module('amdApp'));
    beforeEach(inject(function ($filter) {
        toArrayFilter = $filter("toArray");
    }));

    it('should be able to format the object into arrays for user jack', function () {
        expect(toArrayFilter(jackMockData.data.canvasByCategory)).toEqual(
            [
                [
                    {
                        "canvasId": 100,
                        "name": "abc"
                    },
                    {
                        "canvasId": 101,
                        "name": "bcd"
                    }
                ],
                [
                    {
                        "canvasId": 110,
                        "name": "cde"
                    },
                    {
                        "canvasId": 111,
                        "name": "def"
                    }
                ],
                [
                    {
                        "canvasId": 201,
                        "name": "efg"
                    },
                    {
                        "canvasId": 202,
                        "name": "fgh"
                    }
                ]
            ]
        );
    });
    it('should be able to format the object into arrays for user jill', function () {
        expect(toArrayFilter(jillMockData.data.canvasByCategory)).toEqual(
            [
                [
                    {
                        "canvasId": 100,
                        "name": "abc"
                    }
                ],
                [
                    {
                        "canvasId": 110,
                        "name": "cde"
                    }
                ]
            ]
        );
    });
});