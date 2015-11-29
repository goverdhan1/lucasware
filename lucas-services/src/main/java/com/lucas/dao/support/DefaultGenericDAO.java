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
package com.lucas.dao.support;

import com.lucas.entity.application.CodeLookup;
import com.lucas.entity.user.PermissionGroup;
import com.lucas.entity.user.User;
import com.lucas.exception.LucasBusinessException;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.filter.FilterType;
import com.lucas.services.search.LucasDateRange;
import com.lucas.services.search.LucasNumericRange;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.sort.SortType;
import com.lucas.services.util.CollectionsUtilService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.joda.time.*;
import org.springframework.util.ReflectionUtils;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class DefaultGenericDAO<DOMAINTYPE> implements
		GenericDAO<DOMAINTYPE> {

	public abstract EntityManager getEntityManager();

	public abstract void setEntityManager(EntityManager entityManager);

	protected final Class<DOMAINTYPE> entityType;

    private static final String percentageSymbol = "%";
    private static int PARAMETER_LIMIT = 999;

	@SuppressWarnings("unchecked")
	public DefaultGenericDAO() {
		entityType = (Class<DOMAINTYPE>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public Session getSession() {
		return (Session) getEntityManager().getDelegate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public DOMAINTYPE save(Object data) {
		/*
		 * saveOrUpdate is better than persist/merge as it allows us to change
		 * the ID on an object without going back to the db to check its
		 * version.
		 * 
		 * The 'Putting it all together' table at:
		 * http://blog.xebia.com/2009/03/
		 * 23/jpa-implementation-patterns-saving-detached-entities/ is very
		 * helpful for this
		 */
		try {
			Session session = getSession();
			session.saveOrUpdate(data);
		} catch (ConstraintViolationException e) {
			throw new LucasBusinessException(e.getConstraintViolations());
		} catch (NonUniqueObjectException e) { // this should update and sync things back up
			data = getEntityManager().merge(data);
		}
		return (DOMAINTYPE) data;
	}


    @Override
    public DOMAINTYPE load(Object id) {
        return getEntityManager().find(entityType, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<DOMAINTYPE> loadAll() {
        return getEntityManager().createQuery(
                "FROM " + renderClassName(entityType)).getResultList();
    }

    public void delete(DOMAINTYPE entity) {
        getSession().delete(entity);
    }

    public void flush() {
        getSession().flush();
    }

    public void clear() {
        getSession().clear();
    }

    @SuppressWarnings("unchecked")
    public List<DOMAINTYPE> findByCriteria(Criterion... criterion) {
        Criteria crit = getSession().createCriteria(entityType);
        for (Criterion c : criterion) {
            crit.add(c);
        }
        return crit.list();
    }

    private static String renderClassName(Class<?> clazz) {
        String className = clazz.getCanonicalName();
        int lastDot = className.lastIndexOf(".");
        return className.substring(lastDot + 1);
    }

    @Override
    public List<DOMAINTYPE> getBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria)
            throws ParseException {

        final Session session = getSession();
        final List<DOMAINTYPE> results = buildCriteria(session, searchAndSortCriteria).list();
        return (List<DOMAINTYPE>) results;
    }

    /*
     * This method returns the total count of records given search and sort criteria excluding pagesize count.
     */
    public Long getTotalCountForSearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException {
        Session session = getSession();
        Criteria criteria = this.buildNewCriteria(session, searchAndSortCriteria);

        //reset the pagination to get the total records which was previously built in criteria
        criteria.setFirstResult(0);
        criteria.setMaxResults(Integer.MAX_VALUE);
        ScrollableResults scroll = criteria.scroll();
        scroll.last();

        Long totalResult = new Long(scroll.getRowNumber()) + 1;
        scroll.close();
        return totalResult;
    }

    /* (non-Javadoc)
      * @see com.lucas.dao.support.GenericDAO#getTotalCountForSearchAndSortCriteria()
      */
    @Override
    public Long getTotalCountForSearchAndSortCriteria(final Criteria criteria)throws ParseException{
        criteria.setFirstResult(0);
        criteria.setMaxResults(Integer.MAX_VALUE);
        criteria.setProjection(null).setResultTransformer(Criteria.ROOT_ENTITY);
        final Long totalResult = ((Number)criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
        return totalResult;
    }

    public Criteria buildCriteria(Session session, SearchAndSortCriteria searchAndSortCriteria){

        final Criteria criteria = session.createCriteria(entityType, "entity");

        if (!CollectionsUtilService.isNullOrEmpty(searchAndSortCriteria.getSearchMap())) {
            final String percentageSymbol ="%";
            for (Map.Entry<String, Object> searchEntry : searchAndSortCriteria.getSearchMap().entrySet()) {
                final String key = searchEntry.getKey();
                final Object value = searchEntry.getValue();

                Type type = this.getFieldType(key);
                if (value instanceof List) {

                    if (value == null || (((List<String>) value).size() == 0)) {
                        throw new LucasRuntimeException(String.format("Invalid Search Value for %s", value));
                    }

                    Disjunction disjunction = null;
                    for (String dataElement : (List<String>) value) {
                        //There will be only one item in the list with either Empty, Non Empty or No Filter
                        //"No Filter" is not handled here since it will return back everything from database
                        if(dataElement.equals(FilterType.EMPTY.toString())) {
                            if ((type != null) && (type == String.class)) {
                                criteria.add(Restrictions.or(Restrictions.isNull(key), Restrictions.eq(key, "")));
                            } else {
                                criteria.add(Restrictions.isNull(key));
                            }
                        } else if(dataElement.equals(FilterType.NON_EMPTY.toString())) {
                            if ((type != null) && (type == String.class)) {
                                criteria.add(Restrictions.and(Restrictions.isNotNull(key), Restrictions.ne(key, "")));
                            } else {
                                criteria.add(Restrictions.isNotNull(key));
                            }
                        }else if(dataElement.equals("Yes")){ //FilterType.TRUE
                            criteria.add(Restrictions.eq(key, Boolean.TRUE));
                        }else if(dataElement.equals("No")){ // FilterType.FALSE
                            criteria.add(Restrictions.eq(key, Boolean.FALSE));
                        } else {
                            if(disjunction == null) {
                                disjunction = Restrictions.disjunction();
                            }
                            disjunction.add(Restrictions.ilike(key,  percentageSymbol+dataElement+percentageSymbol ));
                            criteria.add(disjunction);
                        }
                    }

                    /*if(disjunction != null) {
                    	criteria.add(disjunction);
                    }*/
                }

                if (value instanceof LucasDateRange) {
                    criteria.add(Restrictions.between(key, ((LucasDateRange) value).getStartDateOfRange().toDate(), ((LucasDateRange) value).getEndDate().toDate()));
                }


                if (value instanceof LucasNumericRange) {
                    criteria.add(Restrictions.between(key, ((LucasNumericRange) value).getStartOfRange(), ((LucasNumericRange) value).getEndOfRange()));
                }
            }
        }

        if (!CollectionsUtilService.isNullOrEmpty(searchAndSortCriteria.getSortMap())) {

            for (Entry<String, SortType> sortEntry : searchAndSortCriteria.getSortMap().entrySet()) {
                final String key = sortEntry.getKey();
                final SortType value = sortEntry.getValue();

                if (value.getType().equalsIgnoreCase(SortType.ASC.getType())) {
                    criteria.addOrder(Order.asc(key));
                }

                if (value.getType().equalsIgnoreCase(SortType.DESC.getType()) ) {
                    criteria.addOrder(Order.desc(key));
                }
            }
        }

        if (searchAndSortCriteria.getPageSize() != null && searchAndSortCriteria.getPageNumber() != null) {

            if (searchAndSortCriteria.getPageSize() != 0 || searchAndSortCriteria.getPageNumber() != 0) {
                final Integer offset = searchAndSortCriteria.getPageSize() * searchAndSortCriteria.getPageNumber();
                criteria.setFirstResult(offset);
                criteria.setMaxResults(searchAndSortCriteria.getPageSize());
            }
        } else {
            criteria.setFirstResult(0);
            criteria.setMaxResults(10);
        }

        return criteria;
    }

    /**
     * New version of getBySearchAndSortCriteria based on the new filter type functionality
     *
     * @author DiepLe
     * @param searchAndSortCriteria
     * @return
     * @throws ParseException
     */
    @Override
    public List<DOMAINTYPE> getNewBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria)
            throws ParseException, IllegalArgumentException, LucasRuntimeException {

        final Session session = getSession();
        final List<DOMAINTYPE> results = buildNewCriteria(session, searchAndSortCriteria).list();
        return (List<DOMAINTYPE>) results;
    }

    protected Criteria modifyNewCriteriaForCurrentEntity(Criteria criteria, SearchAndSortCriteria searchAndSortCriteria) {
        return criteria;
    }

    protected boolean skipSearchKeyForCurrentEntity(String searchPropertyKey) {
        return false;
    }

    protected String modifySearchKeyForCurrentEntity(String searchPropertyKey) {
        return searchPropertyKey;
    }

    /**
     * build the criteria based on the new filter requirements
     *
     * @author DiepLe
     * @param searchAndSortCriteria
     * @return
     */
    public Criteria buildNewCriteria(Session session, SearchAndSortCriteria searchAndSortCriteria) throws ParseException, IllegalArgumentException, LucasRuntimeException {

        Criteria criteria = session.createCriteria(entityType, "entity");
        Type type = null;

        modifyNewCriteriaForCurrentEntity(criteria, searchAndSortCriteria);

        if (!CollectionsUtilService.isNullOrEmpty(searchAndSortCriteria.getSearchMap())) {

            for (Map.Entry<String, Object> searchEntry : searchAndSortCriteria.getSearchMap().entrySet()) {
                String searchPropertyKey = modifySearchKeyForCurrentEntity(searchEntry.getKey());
                final Object value = searchEntry.getValue();

                if (skipSearchKeyForCurrentEntity(searchPropertyKey))
                    continue;
                type = this.getFieldType(searchPropertyKey);

                if (value instanceof LinkedHashMap) {

                    if (value == null || ((LinkedHashMap) value).size() == 0) {
                        throw new IllegalArgumentException(String.format("Invalid argument values for %s", value));
                    }

                    // check for null parameters
                    Map<String, Object> criteriaObject =  (Map<String, Object> ) value;
                    List<String> arrList = (List<String>) criteriaObject.get("values");
                    String filterType = criteriaObject.get("filterType").toString();

                    // non empty & empty can have zero size values list
                    if((arrList == null || arrList.size() == 0) &&
                      (!filterType.equals(FilterType.EMPTY.toString())) &&
                      (!filterType.equals(FilterType.NON_EMPTY.toString()))) {
                        throw new IllegalArgumentException(String.format("Invalid argument values for %s", value));
                    }

                    if (filterType.equals(FilterType.ENUMERATION.toString())) {
                        criteria.add(generateInClauseCriterion(searchPropertyKey, arrList));
                    }
                    else if (filterType.equals(FilterType.ALPHANUMERIC.toString())) {
                        generateAlphaNumericCriteriaObject(criteria,  searchPropertyKey, arrList);
                    }
                    else if (filterType.equals(FilterType.NUMERIC.toString())) {
                        // note that this support numeric ranges as well
                        generateNumericCriteriaObject(criteria, searchPropertyKey, arrList);
                    }
                    else if (filterType.equals(FilterType.DATE.toString())) {
                        generateDateCriteriaObject(criteria, searchPropertyKey, arrList);
                    }
                    else if (filterType.equals(FilterType.BOOLEAN.toString())) {
                        generateBooleanCriteriaObject(criteria, searchPropertyKey, arrList);
                    }
                    else if(filterType.equals(FilterType.EMPTY.toString())) {
                        if ((type != null) && (type == String.class)) {
                            criteria.add(Restrictions.or(Restrictions.isNull(searchPropertyKey), Restrictions.eq(searchPropertyKey, "")));
                        } else if ((type != null) && (type == java.util.Date.class || type == DateTime.class)) {
                            criteria.add(Restrictions.isNull(searchPropertyKey));
                        }
                        else {
                            criteria.add(Restrictions.isNull(searchPropertyKey));
                        }
                    }
                    else if(filterType.equals(FilterType.NON_EMPTY.toString())) {
                        if ((type != null) && (type == String.class)) {
                            criteria.add(Restrictions.and(Restrictions.isNotNull(searchPropertyKey), Restrictions.ne(searchPropertyKey, "")));
                        } else if ((type != null) && (type == java.util.Date.class || type == DateTime.class)) {
                            criteria.add(Restrictions.isNotNull(searchPropertyKey));
                        } else {
                            criteria.add(Restrictions.isNotNull(searchPropertyKey));
                        }
                    }
                } else {
                    throw new IllegalArgumentException(String.format("Invalid argument values for %s", value));
                }
            }
        }

        if (!CollectionsUtilService.isNullOrEmpty(searchAndSortCriteria.getSortMap())) {

            for (Map.Entry<String, SortType> sortEntry : searchAndSortCriteria.getSortMap().entrySet()) {
                final String key = sortEntry.getKey();
                final SortType value = sortEntry.getValue();

                if (value.getType().equalsIgnoreCase(SortType.ASC.getType())) {
                    criteria.addOrder(Order.asc(key));
                }

                if (value.getType().equalsIgnoreCase(SortType.DESC.getType()) ) {
                    criteria.addOrder(Order.desc(key));
                }
            }
        }

        if (searchAndSortCriteria.getPageSize() != null && searchAndSortCriteria.getPageNumber() != null) {

            if (searchAndSortCriteria.getPageSize() != 0 || searchAndSortCriteria.getPageNumber() != 0) {
                final Integer offset = searchAndSortCriteria.getPageSize() * searchAndSortCriteria.getPageNumber();
                criteria.setFirstResult(offset);
                criteria.setMaxResults(searchAndSortCriteria.getPageSize());
            }
        } else {
            criteria.setFirstResult(0);
            criteria.setMaxResults(10);
        }

        return criteria;
    }



    /*
     * This method is used in case other entities need to add any aliase tables
     */
    protected List<DOMAINTYPE> getBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria, Criteria criteria)
            throws ParseException {

        final List<DOMAINTYPE> results = criteria.list();
        return (List<DOMAINTYPE>) results;
    }
    
    /**
     * Default Method to return the type of the class from a given string key.
     * 
     * @param aKey
     * @return Type of the class
     */
    @Override
    public Type getFieldType(String aKey) {
        Class<?> type = null;
        try {
            type = ReflectionUtils.findField(Class.forName(entityType.getCanonicalName()), aKey).getType();
        } catch (ClassNotFoundException e) {
            throw new LucasRuntimeException(String.format("Invalid Search Field for %s", aKey));
        }

        return type;
    }


    /**
     * build the sql in clause criteria based on the new filter requirements
     *
     * @author DiepLe
     * @param propertyName
     * @param values
     * @return
     */
    private Criterion generateInClauseCriterion(String propertyName, List<String> values) {
        Criterion criterion = null;

        int listSize = values.size();
        for (int i = 0; i < listSize; i += PARAMETER_LIMIT) {
            List subList;
            if (listSize > i + PARAMETER_LIMIT) {
                subList = values.subList(i, (i + PARAMETER_LIMIT));
            } else {
                subList = values.subList(i, listSize);
            }
            if (criterion != null) {
                criterion = Restrictions.or(criterion, Restrictions.in(propertyName, subList));
            } else {
                criterion = Restrictions.in(propertyName, subList);
            }
        }
        return criterion;
    }

    /**
     * Method to generate the criteria object based on the Alpha numeric filter types
     *
     * @author DiepLe
     *
     * @param criteriaObj : the criteria object to generate
     * @param propertyName : the target database field name
     * @param filterSearchValue : the search values
     * @return Criteria object
     */
    private void generateAlphaNumericCriteriaObject(Criteria criteriaObj,
                                                          String propertyName, List<String> filterSearchValue) {

        /*
        Note that there are 4 possible combinations of alphanumeric filter type:
        1) start with - jac%
        2) contains -. %jac%
        3) end with - %ck123 and
        4) exact matches - jack123
         */

        for (String searchText: filterSearchValue) {
            if (searchText.contains(percentageSymbol)) {

                int count = StringUtils.countMatches(searchText, percentageSymbol);
                if (count == 1) {
                    // this is the beginning OR end with pattern matching
                    // so work which one and generate the correct criteria accordingly
                    if ((searchText.startsWith(percentageSymbol)) || (searchText.endsWith(percentageSymbol))) {
                        criteriaObj.add(Restrictions.ilike(propertyName, searchText));
                    }
                } else if (count == 2) {
                    // this is the contains pattern matching
                    // check to make sure the pattern matching is beginning and end of line only

                    if ((searchText.startsWith(percentageSymbol)) && (searchText.endsWith(percentageSymbol))) {
                        criteriaObj.add(Restrictions.ilike(propertyName, searchText));
                    }
                }
            } else {
                // this is the exact match pattern
                criteriaObj.add(Restrictions.eq(propertyName, searchText));
            }
        }
    }

    /**
     * Method to generate the criteria object based on the numeric filter types
     *
     * @author DiepLe
     *
     * @param criteriaObj : the criteria object to generate
     * @param propertyName : the target database field name
     * @param filterSearchValue : the search values
     * @return
     */
    private void generateNumericCriteriaObject(Criteria criteriaObj,
                                               String propertyName, List<String> filterSearchValue) {

        if (filterSearchValue.size() > 1) {
            // numeric ranges
            Long startOfRange = Long.parseLong(filterSearchValue.get(0));
            Long endOfRange = Long.parseLong(filterSearchValue.get(1));
            if (startOfRange > endOfRange) { // sanity check and swap if required
                Long tmp = startOfRange;
                startOfRange = endOfRange;
                endOfRange = tmp;
            }
            LucasNumericRange lucasNumericRange = new LucasNumericRange(startOfRange, endOfRange);
            criteriaObj.add((Restrictions.between(propertyName, lucasNumericRange.getStartOfRange(), lucasNumericRange.getEndOfRange())));
            return;
        }


        /*
        Note that there are 6 possible combinations of numeric filter type:
        1) equals
        2) greater than
        3) greater than, equal to
        4) less than
        5) less than, equal
        6) not equal
         */

        // first remove square brackets and non numerics
        String searchText = filterSearchValue.get(0).replace("[", "").replace("]", "");

        // now split the searchText into operation/numeric format
        final List<String> numericFilterList = com.lucas.services.util.StringUtils.processNumericSearchValue(StringUtils.deleteWhitespace(searchText));

        // numericFilterList now contains operations/numeric value
        String operations = numericFilterList.get(0);
        String numericValue = numericFilterList.get(1);

        boolean isDecimal = org.apache.commons.lang.StringUtils.contains(numericValue, ".");

        switch (operations) {
            case "=" :
                if (isDecimal) {
                    criteriaObj.add(Restrictions.eq(propertyName, Double.valueOf(numericValue)));
                } else {
                    criteriaObj.add(Restrictions.eq(propertyName, Long.valueOf(numericValue)));
                }
                break;

            case "<=" :
                if (isDecimal) {
                    criteriaObj.add(Restrictions.le(propertyName, Double.valueOf(numericValue)));
                } else {
                    criteriaObj.add(Restrictions.le(propertyName, Long.valueOf(numericValue)));
                }
                break;

            case "<" :
                if (isDecimal) {
                    criteriaObj.add(Restrictions.lt(propertyName, Double.valueOf(numericValue)));
                } else {
                    criteriaObj.add(Restrictions.lt(propertyName, Long.valueOf(numericValue)));
                }
                break;

            case ">" :
                if (isDecimal) {
                    criteriaObj.add(Restrictions.gt(propertyName, Double.valueOf(numericValue)));
                } else {
                    criteriaObj.add(Restrictions.gt(propertyName, Long.valueOf(numericValue)));
                }
                break;

            case ">=" :
                if (isDecimal) {
                    criteriaObj.add(Restrictions.ge(propertyName, Double.valueOf(numericValue)));
                } else {
                    criteriaObj.add(Restrictions.ge(propertyName, Long.valueOf(numericValue)));
                }
                break;

            case "!=" :
                if (isDecimal) {
                    criteriaObj.add(Restrictions.ne(propertyName, Double.valueOf(numericValue)));
                } else {
                    criteriaObj.add(Restrictions.ne(propertyName, Long.valueOf(numericValue)));
                }
                break;

            case "<>" :
                if (isDecimal) {
                    criteriaObj.add(Restrictions.ne(propertyName, Double.valueOf(numericValue)));
                } else {
                    criteriaObj.add(Restrictions.ne(propertyName, Long.valueOf(numericValue)));
                }
        }

    }

    /**
     * Method to generate the criteria object based on the date filter types
     *
     * @author DiepLe
     *
     * @param criteriaObj : the criteria object to generate
     * @param propertyName : the target database field name
     * @param filterSearchValue : the search values
     * @return
     */
    private void generateDateCriteriaObject(Criteria criteriaObj, String propertyName, List<String> filterSearchValue) {

        /*
        Note that we are ignoring timezone for  now and assuming the client and server are in the same timezone
        */

        if (filterSearchValue.size() > 1) {
            // This is the user entered custom date range
            LocalDateTime startDateRange = LocalDateTime.parse(filterSearchValue.get(0));
            LocalDateTime endDateRange = LocalDateTime.parse(filterSearchValue.get(1));

            // switch date around if not in proper order
            if (LocalDateTime.parse(filterSearchValue.get(0)).compareTo(endDateRange) == 1) {
                startDateRange = LocalDateTime.parse(filterSearchValue.get(1));
                endDateRange =  LocalDateTime.parse(filterSearchValue.get(0)).plusHours(23).plusMinutes(59).plusSeconds(59);
            };

            criteriaObj.add(Restrictions.between(propertyName, startDateRange.toDate(), endDateRange.toDate()));
            return;
        } else if ((filterSearchValue.size() == 1)) {
            // single value date format
            try {
                Date targetDate = new DateTime(filterSearchValue.get(0)).toDate();
                criteriaObj.add( Restrictions.eq(propertyName, targetDate));
                return;
            } catch (Exception ex) {
                /* if we reached here it's the enumerations date filter type */
                // now split the searchText into operation/numeric format
                String dateEnum = filterSearchValue.get(0).toUpperCase();

                try {
                    generateGivenEnumDateCriteriaObject(criteriaObj, propertyName, dateEnum);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * Method to generate given 'enum' date
     *
     * @author DiepLe
     * @param criteriaObj result criteria object
     * @param propertyName target field name
     * @param dateEnum AMD date enum filter type
     * @throws ParseException
     */
    private void generateGivenEnumDateCriteriaObject(Criteria criteriaObj, String propertyName, String dateEnum) throws ParseException {

        if (dateEnum.equals("TODAY")) {
            DateTime localToday = new DateTime();
            DateTime startDate = localToday.withTimeAtStartOfDay();
            DateTime endDate = localToday.withTimeAtStartOfDay().plusHours(23).plusMinutes(59).plusSeconds(59);
            criteriaObj.add(Restrictions.between(propertyName, startDate.toDate(), endDate.toDate()));
        } else if (dateEnum.equals("YESTERDAY")){
            DateTime today = new DateTime();
            DateTime yesterdayMin = today.plusDays(-1).withTimeAtStartOfDay();
            DateTime yesterdayMax = yesterdayMin.plusHours(23).plusMinutes(59).plusSeconds(50);
            criteriaObj.add(Restrictions.between(propertyName, yesterdayMin.toDate(), yesterdayMax.toDate()));
        } else if (dateEnum.equals("THIS_WEEK")){
            LocalDate localToday = new LocalDate();
            LocalDate weekStart = localToday.dayOfWeek().withMinimumValue();
            LocalDate weekEnd = localToday.dayOfWeek().withMaximumValue();
            criteriaObj.add(Restrictions.between(propertyName, weekStart.toDate(), weekEnd.toDate()));
        } else if (dateEnum.equals("LAST_WEEK")){
            LocalDate today = new LocalDate();
            LocalDate thisWeekStart = today.dayOfWeek().withMinimumValue();
            LocalDate lastWeekStart = thisWeekStart.minusWeeks(1);
            LocalDate startOfPreviousWeek = lastWeekStart.dayOfWeek().withMinimumValue();
            LocalDate endOfPreviousWeek = lastWeekStart.dayOfWeek().withMaximumValue().toDateMidnight().toLocalDate();
            criteriaObj.add(Restrictions.between(propertyName, startOfPreviousWeek.toDate(), endOfPreviousWeek.toDate()));
        } else if (dateEnum.equals("THIS_MONTH")){
            LocalDate today = new LocalDate();
            LocalDate startOfMonth = today.dayOfMonth().withMinimumValue();
            LocalDate endOfMonth = today.dayOfMonth().withMaximumValue();
            criteriaObj.add(Restrictions.between(propertyName, startOfMonth.toDate(), endOfMonth.toDate()));
        } else if (dateEnum.equals("LAST_MONTH")){
            LocalDate firstDayOfThisMonth = new LocalDate().withDayOfMonth(1);
            LocalDate lastMonth = firstDayOfThisMonth.minusMonths(1);
            LocalDate startOfLastMonth = lastMonth.dayOfMonth().withMinimumValue();
            LocalDate endOfLastMonth = lastMonth.dayOfMonth().withMaximumValue();
            DateTime endDate = endOfLastMonth.toDateTimeAtStartOfDay().plusHours(23).plusMinutes(59).plusSeconds(59);
            criteriaObj.add(Restrictions.between(propertyName, startOfLastMonth.toDate(), endDate.toDate()));
        }
    }

    /**
     * Method to test generate the criteria api for the boolean data type
     *
     * @author DiepLe
     *
     * @param criteriaObj
     * @param propertyName
     * @param dataElement
     */
    private void generateBooleanCriteriaObject(Criteria criteriaObj, String propertyName, List<String> dataElement) {

        if(dataElement.get(0).equals("1") || dataElement.get(0).toUpperCase().equals("TRUE")){
            criteriaObj.add(Restrictions.eq(propertyName, Boolean.TRUE));
        }else if(dataElement.get(0).equals("0") || dataElement.get(0).toUpperCase().equals("FALSE")){
            criteriaObj.add(Restrictions.eq(propertyName, Boolean.FALSE));
        }

    }
}
