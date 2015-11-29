describe('Unit testing showselected directive', function () {
    var $compile;
    var $rootScope;
    var deferred;
    var localElement;
    var mockUtilsService;

    beforeEach(module("amdApp", function ($provide) {
        mockUtilsService = {
            showListenItems: function () {
                return [];
            },
            saveActiveCanvas: function() {
            }
        };
        $provide.value("UtilsService", mockUtilsService);
    }));

    beforeEach(inject(function ($injector, _$compile_, _$rootScope_, $httpBackend) {
        $compile = _$compile_;
        $rootScope = _$rootScope_;

        // Set up the mock http service responses
        $httpBackend = $injector.get('$httpBackend');
        // Handle all the POST requests
        $httpBackend.when('GET').respond({});

        localElement = $compile("<div showselected></div>")($rootScope);
        $rootScope.widgetdetails = {
            clientId: 61,
            isMaximized: false,
            updateWidget: true,
            data: [
                {
                    chart: [
                        {
                            color: "#d62728",
                            key: "Completed",
                            values: [
                                {
                                    label: "Wave1", value: "300"
                                },
                                {
                                    label: "Wave2", value: "455"
                                },
                                {
                                    label: "Wave3", value: "367"
                                },
                                {
                                    label: "Wave4", value: "407"
                                }
                            ]
                        },
                        {
                            color: "#1f77b4",
                            key: "Total",
                            values: [
                                {
                                    label: "Wave1", value: "350"
                                },
                                {
                                    label: "Wave2", value: "470"
                                },
                                {
                                    label: "Wave3", value: "390"
                                },
                                {
                                    label: "Wave4", value: "459"
                                }
                            ]
                        }
                    ]
                }
            ],
            actualViewConfig: {
                anchor: [0, 0],
                height: 500,
                width: 600,
                zindex: 0
            },
            widgetDefinition: {
                id: 11,
                broadcastMap: {
                    Completed: "series.key",
                    Wave: "point.label"
                },
                dataURL: {

                },
                name: "assignment-management-piechart-widget",
                shortName: "PicklineByWave",
                subtype: "CHART_BAR",
                type: "GRAPH_2D",
                actualViewConfig: {
                    anchor: [0, 0],
                    height: 500,
                    width: 600,
                    zindex: 0
                }
            }
        };

        $rootScope.$digest();
    }));

    it('Should create dragandresize directive', function () {
        expect(localElement.length).toBeGreaterThan(0);
    });

    it('Should handle filteredItems', function () {
        spyOn(localElement.scope(), '$broadcast').andCallThrough();
        localElement.scope().$broadcast('filteredItems');
        expect(localElement.scope().$broadcast).toHaveBeenCalledWith('filteredItems');
    });

    it('Should handle clearFilterItems', function () {
        spyOn(localElement.scope(), '$broadcast').andCallThrough();
        localElement.scope().$broadcast('clearFilterItems');
        expect(localElement.scope().$broadcast).toHaveBeenCalledWith('clearFilterItems');
    });

    it('Should handle $destroy', function () {
        spyOn(localElement.scope(), '$broadcast').andCallThrough();
        localElement.scope().$broadcast('$destroy');
        expect(localElement.scope().$broadcast).toHaveBeenCalledWith('$destroy');
    });

});