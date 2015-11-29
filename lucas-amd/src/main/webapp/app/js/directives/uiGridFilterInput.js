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

amdApp.directive('uiGridFilterInput', ['WidgetGridService', 'SearchUserGridService', 'GroupGridWidgetService', '$modal', '$filter', '$compile', '$translate', '$rootScope',
    function(WidgetGridService, SearchUserGridService, GroupGridWidgetService, modal, filter, compile, translate, rootScope) {
        var linkFunction = function(scope, element, attrs) {

            scope.filterOptions = [];
            scope.filterValues = null;
            scope.colFilter.option = null;
            scope.colFilter.alphaOption = null;
            scope.colFilter.alphaValue = null;
            scope.colFilter.numericOption = null;
            scope.colFilter.numericValue = null;
            scope.colFilter.startDate = null;
            scope.colFilter.startTime = null;
            scope.colFilter.endDate = null;
            scope.colFilter.endTime = null;

            scope.startDateOpen = function($event) {
                $event.preventDefault();
                $event.stopPropagation();
                scope.startDateOpened = true;
            };
            scope.endDateOpen = function($event) {
                $event.preventDefault();
                $event.stopPropagation();
                scope.endDateOpened = true;
            };
            scope.hstep = 1;
            scope.mstep = 15;
            var d = new Date();
            d.setHours(0);
            d.setMinutes(0);
            scope.colFilter.startTime = d;
            scope.colFilter.endTime = d;
            scope.defaultFilterOptions=[];
            SearchUserGridService.getColumnFilterValues(["FILTER_COMMON"]).then(function(r){
                scope.defaultFilterOptions=r.FILTER_COMMON;
            });

            // Method to append alphanumber special characters to search with
            scope.alphanumericString = function(alphaOption, alphaValue) {
                scope.filterValues = [];
                if (alphaOption == "STARTWITH") {
                    scope.filterValues.push(alphaValue + "%");
                } else if (alphaOption == "ENDSWITH") {
                    scope.filterValues.push("%" + alphaValue);
                } else if (alphaOption == "CONTAINS") {
                    scope.filterValues.push("%" + alphaValue + "%");
                } else if (alphaOption == "MATCHES") {
                    scope.filterValues.push(alphaValue);
                }
            }

            // Method to append numberic special characters to search with 
            scope.numericString = function(numericOption, numericValue) {
                scope.filterValues = [];
                if (numericOption == "EQUALS") {
                    scope.filterValues.push("=" + numericValue);
                } else if (numericOption == "GREATER") {
                    scope.filterValues.push(">" + numericValue);
                } else if (numericOption == "GREATERANDEQUAL") {
                    scope.filterValues.push(">=" + numericValue);
                } else if (numericOption == "LESS") {
                    scope.filterValues.push("<" + numericValue);
                } else if (numericOption == "LESSANDEQUAL") {
                    scope.filterValues.push("=<" + numericValue);
                } else if (numericOption == "NOTEQUAL") {
                    scope.filterValues.push("!=" + numericValue);
                }

            }

            // Method to form array of enumerated values
            scope.filterValuesArray = function(filterValue) {
                var idx = scope.filterValues.indexOf(filterValue);
                // is currently selected
                if (idx > -1) {
                    scope.filterValues.splice(idx, 1);
                }
                // is newly selected
                else {
                    scope.filterValues.push(filterValue);
                }
            };

            scope.getDateRangeFilter = function(sd, st, ed, et) {
                sd = filter('date')(sd, "shortDate");
                st = filter('date')(st, "shortTime");
                ed = filter('date')(ed, "shortDate");
                et = filter('date')(et, "shortTime");
                scope.filterValues = [];
                scope.filterValues.push(sd + "T" + st);
                scope.filterValues.push(ed + "T" + et);
            }

            scope.$watch('colFilter.option', function(newFilter, oldFilter) {
                scope.oldColFilterOption = angular.copy(oldFilter);
            })

            scope.buildFilterTerm = function(columnName) {

                if (scope.colFilter.option == "ENUMERATION") {
                    scope.filterValues = [];
                    scope.displayFilterOptions = [];
                    scope.displayFilterOptions = scope.filterOptions;
                    //display modal for enumeration options
                    scope.enumModalInstance = modal.open({
                        templateUrl: "views/modals/filtertype-enumeration.html",
                        backdrop: 'static',
                        scope: scope
                    });

                    //Handle button actions from the modal
                    scope.enumModalInstance.result.then(function() {
                        scope.colFilter.term = {
                            "filterType": scope.colFilter.option,
                            "values": scope.filterValues
                        };

                        rootScope.$emit("searchOnEnterKey");
                    }, function() {
                        scope.filterValues = null;
                        scope.colFilter.option = scope.oldColFilterOption;
                    });
                scope.colFilter.option = "hiddenCustomFilter";    
                } else if (scope.colFilter.option == "ALPHANUMERIC") {
                    scope.filterValues = [];
                    //display modal for enumeration options
                    scope.alphaModalInstance = modal.open({
                        templateUrl: "views/modals/filtertype-alphanumeric.html",
                        backdrop: 'static',
                        scope: scope
                    });

                    //Handle button actions from the modal
                    scope.alphaModalInstance.result.then(function() {
                        scope.alphanumericString(scope.colFilter.alphaOption, scope.colFilter.alphaValue);
                        scope.colFilter.term = {
                            "filterType": scope.colFilter.option,
                            "values": scope.filterValues
                        };

                        rootScope.$emit("searchOnEnterKey");
                    }, function() {
                        scope.colFilter.alphaOption = null;
                        scope.colFilter.alphaValue = null;
                        scope.filterValues = null;
                        scope.colFilter.option = scope.oldColFilterOption;
                    });
                scope.colFilter.option = "hiddenCustomFilter";    
                } else if (scope.colFilter.option == "NUMERIC") {
                    //display modal for enumeration options
                    scope.numericModalInstance = modal.open({
                        templateUrl: "views/modals/filtertype-numeric.html",
                        backdrop: 'static',
                        scope: scope
                    });



                    //Handle button actions from the modal
                    scope.numericModalInstance.result.then(function() {
                        scope.numericString(scope.colFilter.numericOption, scope.colFilter.numericValue);
                        scope.colFilter.term = {
                            "filterType": scope.colFilter.option,
                            "values": scope.filterValues
                        };

                        rootScope.$emit("searchOnEnterKey");
                    }, function() {
                        scope.colFilter.numericOption = null;
                        scope.colFilter.numericValue = null;
                        scope.filterValues = null;
                        scope.colFilter.option = scope.oldColFilterOption;
                    });
                scope.colFilter.option = "hiddenCustomFilter";    
                } else if (scope.colFilter.option == "DATE") {
                    //display modal for enumeration options
                    scope.dateModalInstance = modal.open({
                        templateUrl: "views/modals/filtertype-date.html",
                        backdrop: 'static',
                        scope: scope
                    });

                    //Handle button actions from the modal
                    scope.dateModalInstance.result.then(function() {
                        scope.getDateRangeFilter(scope.colFilter.startDate, scope.colFilter.startTime, scope.colFilter.endDate, scope.colFilter.endTime);
                        scope.colFilter.term = {
                            "filterType": scope.colFilter.option,
                            "values": scope.filterValues
                        };

                        rootScope.$emit("searchOnEnterKey");
                    }, function() {
                        scope.filterValues = null;
                        scope.colFilter.option = scope.oldColFilterOption;
                    });
                scope.colFilter.option = "hiddenCustomFilter";        
                } else if (scope.colFilter.option == "EMPTY" || scope.colFilter.option == "NONEMPTY") {
                    scope.filterValues = null;
                    scope.colFilter.term = {
                        "filterType": scope.colFilter.option
                    };
                    rootScope.$emit("searchOnEnterKey");
                } else if (scope.colFilter.option == "noFilter") {
                    scope.colFilter.term = null;
                    scope.filterValues=null;
                    rootScope.$emit("searchOnEnterKey");
                    scope.unFilter=true;
                    scope.colFilter.option=null;
                } else {
                    scope.filterValues = null;
                    scope.colFilter.term = {
                        "filterType": scope.col.colDef.filterType,
                        "values": [scope.colFilter.option]
                    };
                    rootScope.$emit("searchOnEnterKey");
                }
            };


            if (scope.grid.options.gridName == "search-product-grid") {
                scope.template = "views/ui-grid-filter-input.html";
                var ele = compile("<div ng-include='template'></div>")(scope);
                element.parent().html(ele);

                //   scope.defaultFilterOptions=[scope.user.defaultFilterOptions.nonEmpty, scope.user.defaultFilterOptions.empty];
                scope.defaultFilterOptions = ["Non Empty", "Empty"];

                scope.filterOptions = [];
                if (scope.col.colDef.name == "skill") {
                    //Skills
                    WidgetGridService.getAvailableSkills().then(function(r) {
                        if (r.length > 0) {
                            scope.filterOptions = r;
                            scope.filterOptions = scope.defaultFilterOptions.concat(scope.filterOptions);
                        }
                    });
                } else if (scope.col.colDef.name == "j2uLanguage") {
                    //Languages
                    WidgetGridService.getSupportedLanguages().then(function(r) {

                        if (r.j2u.length > 0) {
                            scope.filterOptions = r.j2u;
                            scope.filterOptions = scope.defaultFilterOptions.concat(scope.filterOptions);
                        }
                    });
                } else if (scope.col.colDef.name == "u2jLanguage") {
                    //Languages
                    WidgetGridService.getSupportedLanguages().then(function(r) {
                        if (r.u2j.length > 0) {
                            scope.filterOptions = r.u2j;
                            scope.filterOptions = scope.defaultFilterOptions.concat(scope.filterOptions);
                        }
                    });
                } else if (scope.col.colDef.name == "hhLanguage") {
                    //Languages
                    WidgetGridService.getSupportedLanguages().then(function(r) {
                        if (r.hh.length > 0) {
                            scope.filterOptions = r.hh;
                            scope.filterOptions = scope.defaultFilterOptions.concat(scope.filterOptions);
                        }
                    });
                } else if (scope.col.colDef.name == "amdLanguage") {
                    //Languages
                    WidgetGridService.getSupportedLanguages().then(function(r) {
                        if (r.amd.length > 0) {
                            scope.filterOptions = r.amd;
                            scope.filterOptions = scope.defaultFilterOptions.concat(scope.filterOptions);
                        }
                    });
                } else if (scope.col.colDef.name == "shift.shiftName") {
                    //Shifts
                    WidgetGridService.getAvailableShifts().then(function(r) {
                        if (r.length > 0) {
                            scope.filterOptions = r;
                            scope.filterOptions = scope.defaultFilterOptions.concat(scope.filterOptions);
                        }
                    });
                } else {
                    scope.filterOptions = scope.defaultFilterOptions;
                }

                scope.positionTop = element.offset().top + 20 + "px";
                scope.positionLeft = element.offset().left + "px";


                element.next("div.ui-grid-filter-button").remove();
                element.parent().append("<div class='ui-grid-filter-button'><i class='ui-grid-icon-down-dir' id='drop_" + element.attr("aria-owns") + "'></i></div>");

                //pop-up element used to display matches
                var dropdownPopup = '<ul class="dropdown-menu ng-hide filter-dropdown" role="menu" id="dropdownPopup_' + element.find("input").attr("aria-owns") + '"' +
                    'ng-style="{top: positionTop, left:positionLeft}" style="display:block; z-index: 1000">' +
                    '<li ng-repeat="match in filterOptions track by $index" ng-click="dropdownSelect($index)" role="option">' +
                    '<a tabindex="-1" bind-html-unsafe="match"></a>' +
                    '</li></ul>';
                dropdownPopup = angular.element(dropdownPopup);
                var $popup = compile(dropdownPopup)(scope);
                dropdownPopup.appendTo('body');

                angular.element("#drop_" + element.attr("aria-owns")).bind('click', function(evt) {
                    angular.element(".dropdown-menu").addClass("ng-hide");
                    scope.positionTop = element.offset().top + 20 + "px";
                    scope.positionLeft = element.offset().left + "px";
                    scope.$apply();
                    angular.element("#dropdownPopup_" + element.attr("aria-owns")).removeClass("ng-hide");
                    angular.element("#dropdownPopup_" + element.attr("aria-owns")).addClass("ng-show");

                });

                element.bind('keypress', function() {
                    var code = event.keyCode || event.which;
                    angular.element("#dropdownPopup_" + element.attr("aria-owns")).removeClass("ng-show");
                    angular.element("#dropdownPopup_" + element.attr("aria-owns")).addClass("ng-hide");

                    if (code === 13) {
                        // broadcast searchOnEnterKey event
                        rootScope.$broadcast("searchOnEnterKey");
                    } else {
                        // broadcast searchOnFilterChanged event
                        rootScope.$broadcast("searchOnFilterChanged");
                    }
                });

                element.bind('focus', function() {

                    angular.element("#dropdownPopup_" + element.attr("aria-owns")).removeClass("ng-show");
                    angular.element("#dropdownPopup_" + element.attr("aria-owns")).addClass("ng-hide");

                });

                scope.searchOnSelect = function() {
                    rootScope.$broadcast("searchOnEnterKey");

                };

                scope.dropdownSelect = function(index) {
                    scope.col.filters[0].term = scope.filterOptions[index];
                    angular.element("#dropdownPopup_" + element.attr("aria-owns")).removeClass("ng-show");
                    angular.element("#dropdownPopup_" + element.attr("aria-owns")).addClass("ng-hide");

                    rootScope.$broadcast("searchOnEnterKey");
                }

                scope.template = "views/ui-grid-filter-input.html";
                var ele = compile("<div ng-include='template'></div>")(scope);
                element.parent().html(ele);
            }

            if (scope.grid.options.gridName == "search-user-grid") {

                // initialize filter options in the ui-grid filter inputs.
                SearchUserGridService.columnFilterOptions(scope.col.colDef.name).then(function(r){
                    scope.filterOptions=r;
                });
            
                scope.template = "views/ui-grid-custom-filter-input.html";
                var ele = compile("<div ng-include='template'></div>")(scope);
                element.parent().html(ele);

            }
            if (scope.grid.options.gridName == "search-group-grid") {

                scope.template = "views/ui-grid-custom-filter-input.html";
                var ele = compile("<div ng-include='template'></div>")(scope);
                element.parent().html(ele);

            }

        };
        return {
            restrict: 'C',
            replace: true,
            link: linkFunction
        };
    }
]);