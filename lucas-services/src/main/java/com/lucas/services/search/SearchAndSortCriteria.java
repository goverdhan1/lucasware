package com.lucas.services.search;

import com.lucas.services.filter.FilterType;
import com.lucas.services.sort.SortType;
import com.lucas.services.util.CollectionsUtilService;
import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.commons.lang3.EnumUtils;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 9/1/14  Time: 11:18 AM
 * This Class provide the implementation for the functionality of searching and sorting
 * of the data of the database on the basic of the provided arguments.
 */

public class SearchAndSortCriteria implements Serializable {

    private Integer pageSize;
    private Integer pageNumber;
    private Map<String, Object> searchMap = new HashMap<String, Object>();
    private Map<String, SortType> sortMap = new HashMap<String, SortType>();

    public SearchAndSortCriteria() {
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Map<String, Object> getSearchMap() {
        return searchMap;
    }

    public void setSearchMap(Map<String, Object> searchMap) {
        this.searchMap = searchMap;
    }

    public Map<String, SortType> getSortMap() {
        return sortMap;
    }

    public void setSortMap(Map<String, SortType> sortMap) {
        this.sortMap = sortMap;
    }



    /**
     * Method to check the search map data is correct before we allow to generate the criteria object
     *
     * @author DiepLe
     *@param dateEnumList
     */
    public void checkSearchMapData(List<String> dateEnumList) throws IllegalStateException, IllegalArgumentException, Exception {

        if (!CollectionsUtilService.isNullOrEmpty(getSearchMap())) {

            for (Map.Entry<String, Object> searchEntry : getSearchMap().entrySet()) {
                final Object value = searchEntry.getValue();

                // check for null parameters
                Map<String, Object> criteriaObject =  (Map<String, Object> ) value;
                List<String> dateEnumListFromRequester = (List<String>) criteriaObject.get("values");
                String filterType = null;
                try {
                    filterType = criteriaObject.get("filterType").toString();
                } catch (Exception ex) {
                    throw new IllegalStateException(String.format("Filter type cannot be null"));
                }

                if (filterType == null || filterType.isEmpty()) {
                    // cant generate a criteria with a filter type
                    throw new IllegalStateException(String.format("Filter type cannot be null"));
                }

                // also make sure the filter type passed in is correct
                if (!EnumUtils.isValidEnum(FilterType.class, filterType)){
                    throw new IllegalStateException(String.format("Invalid filter type %s", value));
                }

                // non empty & empty can have zero size values list
                if((dateEnumListFromRequester == null || dateEnumListFromRequester.size() == 0) &&
                        (!filterType.equals(FilterType.EMPTY.toString())) &&
                        (!filterType.equals(FilterType.NON_EMPTY.toString()))) {
                    throw new IllegalArgumentException(String.format("Invalid argument values for %s", value));
                }

                // only the date_enum is required to sanity check here.
                // The filter Enum is already check in the DefaultGenericDAO class
                if (filterType.equals(FilterType.DATE.toString()) && dateEnumListFromRequester.size() == 1) {
                    // now check the dateEnumListFromRequester exists in the dateEnumList from database

                    for (String dateValue : dateEnumListFromRequester) {
                        try {
                            DateTime chkEnumDate = new DateTime(dateValue);
                            break; // proper date format
                        } catch (Exception ex) {
                            // this is actual date enumeration value so sanity check to see if it's exist
                            if (!dateEnumList.contains(dateValue)) {
                                throw new IllegalArgumentException(String.format("Invalid argument values for %s", value));
                            }
                        }
                    }
                }
            }
        }
    }

}
