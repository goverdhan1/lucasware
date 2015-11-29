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

describe('Widget Definition Service related Tests', function () {

    beforeEach(module('amdApp'));

    var localRestApiService;
    var localWidgetDefinitionService;
    var localWidgetService;
    var deferred = null;
    var httpBackend;
    var localStoreService;
    var mockWidgetData = {
        "status": "success",
        "code": "200",
        "message": "Request processed successfully",
        "level": null,
        "uniqueKey": null,
        "token": null,
        "explicxitDismissal": null,
        "data": {
		    "name": "pickline-by-wave-barchart-widget",
		    "id": 10,
		    "shortName": "PicklineByWave",
		    "title": "Pickline By Wave Bar chart",
		    "widgetActionConfig": {},
		    "broadcastList": null,
		    "defaultData": {},
		    "defaultViewConfig": {
		      "height": 300,
		      "width": 500,
		      "dateFormat": {
		        "selectedFormat": "mm-dd-yyyy",
		        "availableFormats": [
		          "mm-dd-yyyy",
		          "MMM dd, yyyy",
		          "dd-mm-yyyy"
		        ]
		      },
		      "anchor": [
		        1,
		        2
		      ],
		      "zindex": 1,
		      "orientation": {
		        "option": [
		          {
		            "legend": "horizontal",
		            "value": "h"
		          },
		          {
		            "legend": "vertical",
		            "value": "v"
		          }
		        ],
		        "selected": "h"
		      },
		      "listensForList": [
		        "lastName",
		        "userName",
		        "firstName"
		      ]
		    },
		    "dataURL": {
		      "url": "/waves/picklines",
		      "searchCriteria": {
		        "pageNumber": "0",
		        "pageSize": "10",
		        "searchMap": {},
		        "sortMap": {}
		      }
		    },
		    "broadcastMap": [
		      "Wave",
		      "Score"
		    ],
		    "reactToMap": {
		      "userName": {
		        "url": "/users",
		        "searchCriteria": {
		          "pageSize": "10",
		          "pageNumber": "0",
		          "searchMap": {
		            "userName": [
		              "jack123",
		              "jill123",
		              "admin123"
		            ]
		          }
		        }
		      },
		      "firstName": {
		        "url": "/users",
		        "searchCriteria": {
		          "pageSize": "10",
		          "pageNumber": "0",
		          "searchMap": {
		            "firstName": [
		              "firstName 1",
		              "firstName 2",
		              "firstName 3"
		            ]
		          }
		        }
		      }
		    }
		  }
    };

    beforeEach(inject(function (RestApiService, WidgetDefinitionService, $httpBackend, LocalStoreService, WidgetService) {
        localRestApiService = RestApiService;
        localWidgetDefinitionService = WidgetDefinitionService;
        localWidgetService = WidgetService; 
        localStoreService = LocalStoreService;
        httpBackend = $httpBackend;
        httpBackend.when('GET').respond(mockWidgetData);
        httpBackend.when('POST').respond(mockWidgetData);
    }));

    //this is commented out because of optimizing getwidgetdefinition.
    it('getWidgetDefinition should return WidgetDefinition', inject(function ($rootScope) {
    	spyOn(localWidgetService, 'putWidgetInstance').andCallThrough();
    	spyOn(localWidgetService, 'getWidgetData').andCallThrough();
    	
        localWidgetDefinitionService.getWidgetDefinition(10, "pickline-by-wave-barchart-widget", "GRAPH_2D", "CHART_BAR");
        httpBackend.flush();
        expect(localWidgetService.getWidgetData).toHaveBeenCalled();
        expect(localWidgetService.putWidgetInstance).toHaveBeenCalled();        
    }));


    // Unit: Assert WidgetDefinitionService.getWidgetDefinition method calls the restApiService based on dataURL.
    it('getWidgetDefinition calls the restApiService', inject(function ($rootScope) {
        spyOn(localRestApiService, 'post').andCallThrough();
        localWidgetDefinitionService.getWidgetDefinition(10, "pickline-by-wave-barchart-widget", "GRAPH_2D", "CHART_BAR");
        httpBackend.flush();
        expect(localRestApiService.post).toHaveBeenCalled();
    }));

    it('test minimumWidth and minimumHeight are set', function() {
    	var response;
    	spyOn(localRestApiService, 'post').andCallThrough();
        localWidgetDefinitionService.getWidgetDefinition(10, "pickline-by-wave-barchart-widget", "GRAPH_2D", "CHART_BAR").then(function(res) {
        	response = res;
        });
        httpBackend.flush();
        // test the properties have been set properly
        expect(response.defaultViewConfig.minimumWidth).toEqual(300);
        expect(response.defaultViewConfig.minimumHeight).toEqual(480);
        expect(response.defaultViewConfig.originalMinimumWidth).toEqual(300);
        expect(response.defaultViewConfig.originalMinimumHeight).toEqual(480);
		expect(localRestApiService.post).toHaveBeenCalled();
    });
}); 