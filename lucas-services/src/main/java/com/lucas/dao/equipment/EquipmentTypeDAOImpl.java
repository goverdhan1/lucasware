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
package com.lucas.dao.equipment;

import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.equipment.EquipmentStyle;
import com.lucas.entity.equipment.EquipmentType;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.filter.FilterType;
import com.lucas.services.search.LucasDateRange;
import com.lucas.services.search.LucasNumericRange;
import com.lucas.services.search.SearchAndSortCriteria;

import com.lucas.services.sort.SortType;
import com.lucas.services.util.CollectionsUtilService;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.*;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of..
 */
@Repository("equipmentTypeDAO")
public class EquipmentTypeDAOImpl extends ResourceDAO<EquipmentType>
        implements EquipmentTypeDAO {

    @Override
    public EquipmentType findByEquipmentTypeId(Long equipmentTypeId) {
        final Session session = getSession();
        final Criteria criteria = session.createCriteria(EquipmentType.class);
        criteria.add(Restrictions.eq("equipmentTypeId", equipmentTypeId));
        criteria.add(Restrictions.isNull("equipmentTypeStatus"));
        final EquipmentType equipmentType = (EquipmentType) criteria.uniqueResult();
        return equipmentType;
    }

    @Override
    public EquipmentType findByEquipmentTypeName(String equipmentTypeName) {
        final Session session = getSession();
        final Criteria criteria = session.createCriteria(EquipmentType.class);
        criteria.add(Restrictions.eq("equipmentTypeName", equipmentTypeName));
        criteria.add(Restrictions.isNull("equipmentTypeStatus"));
        final EquipmentType equipmentType = (EquipmentType) criteria.uniqueResult();
        return equipmentType;
    }

    @Override
    public List<EquipmentType> getAllEquipmentType() {
        final Session session = getSession();
        final Criteria criteria = session.createCriteria(EquipmentType.class);
        criteria.add(Restrictions.isNull("equipmentTypeStatus"));
        final List<EquipmentType> equipmentTypeList = criteria.list();
        return equipmentTypeList;
    }

    @Override
    public boolean saveEquipmentType(EquipmentType equipmentType) {
        if (equipmentType != null) {
            super.save(equipmentType);
            return true;
        }
        return false;
    }

    @Override
    public boolean saveEquipmentTypes(List<EquipmentType> equipmentTypeList) {
        if (equipmentTypeList != null) {
            for (EquipmentType equipmentType : equipmentTypeList) {
                super.save(equipmentType);
            }
            return true;
        }
        return false;
    }

    /* (non-Javadoc)
    * @see com.lucas.services.equipment.EquipmentTypeDAO#getEquipmentTypeListBySearchAndSortCriteria()
    */
    @Override
    public List<EquipmentType> getEquipmentTypeListBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria)  throws Exception {
        final Session session = getSession();
        final Criteria criteria=this.getEquipmentTypeSearchAndSortCriteria(session, searchAndSortCriteria);
        final List<EquipmentType> equipmentTypeList=criteria.list();
        return equipmentTypeList;
    }

    /* (non-Javadoc)
   * @see com.lucas.services.equipment.EquipmentTypeDAO#getTotalCountForSearchAndSortCriteria()
   */
    public Long getTotalCountForSearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException {
        final Session session = getSession();
        final Criteria criteria=this.getEquipmentTypeSearchAndSortCriteria(session, searchAndSortCriteria);
        return super.getTotalCountForSearchAndSortCriteria(criteria);
    }

    /**
     * getEquipmentTypeSearchAndSortCriteria() provide the functionality for searching and sorting on EquipmentType and its
     * related tables based on the search and sort
     *
     * @param session of hibernate  is accepted by this method as the formal argument
     * @param searchAndSortCriteria is accepted by this method as the formal argument containing the criteria for fetch
     * @return Criteria of hibernate containing the basics for searching and sorting on EquipmentType.
     * @throws Exception
     */
    private Criteria getEquipmentTypeSearchAndSortCriteria(final Session session, final SearchAndSortCriteria searchAndSortCriteria) throws ParseException {
        final Criteria criteria = session.createCriteria(EquipmentType.class, "equipmentType");
        criteria.add(Restrictions.isNull("equipmentTypeStatus"));

        if (!CollectionsUtilService.isNullOrEmpty(searchAndSortCriteria.getSearchMap())) {
            final String percentageSymbol ="%";
            for (Map.Entry<String, Object> searchEntry : searchAndSortCriteria.getSearchMap().entrySet()) {
                final Object value = searchEntry.getValue();
                String key = searchEntry.getKey();
                String referenceColumn="";

                if(EquipmentTypeDAO.equipmentStyle.equals(key)){
                    criteria.createAlias("equipmentType.equipmentStyle","es");
                    referenceColumn="es.equipmentStyleName";
                    key="equipmentStyle";
                }

                final Type type = this.getFieldType(key);
                if(!referenceColumn.isEmpty()){
                    key=referenceColumn;
                }

                if (value instanceof List) {

                    if (value == null || (((List<String>) value).size() == 0)) {
                        throw new LucasRuntimeException(String.format("Invalid Search Value for %s", value));
                    }

                    Disjunction disjunction = null;
                    for (String dataElement : (List<String>) value) {
                        //There will be only one item in the list with either Empty, Non Empty or No Filter
                        //"No Filter" is not handled here since it will return back everything from database
                        if(dataElement.equals(FilterType.EMPTY.toString())) {
                            if ((type != null) && (type == String.class) || (type == EquipmentStyle.class)) {
                                criteria.add(Restrictions.or(Restrictions.isNull(key), Restrictions.eq(key, "")));
                            } else {
                                criteria.add(Restrictions.isNull(key));
                            }
                        } else if(dataElement.equals(FilterType.NON_EMPTY.toString())) {
                            if ((type != null) && (type == String.class) || (type == EquipmentStyle.class)) {
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

            for (Map.Entry<String, SortType> sortEntry : searchAndSortCriteria.getSortMap().entrySet()) {
                String key = sortEntry.getKey();
                 SortType value = sortEntry.getValue();

                if(EquipmentTypeDAO.equipmentStyle.equals(key)){
                    key="es.equipmentStyleName";
                }

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
     * getEquipmentTypeList() provide the specification for fetching the
     * list of the EquipmentTypeNames form database
     *
     * @return List of EquipmentType
     */
    @Override
    public List<EquipmentType> getEquipmentTypeList() throws ParseException {
        final Session session = getSession();
        final Criteria criteria = session.createCriteria(EquipmentType.class);
        final List<EquipmentType> equipmentTypeList = criteria.list();
        return equipmentTypeList;
    }

    @Override
    public List<String> getEquipmentTypeNameList(String statusType, Boolean equal) throws Exception {
        final Session session = getSession();
        final Criteria criteria = session.createCriteria(EquipmentType.class);
        final Criterion isNullCondition=Restrictions.isNull("equipmentTypeStatus");
        if (equal != null) {
            if (equal) {
                final Criterion equalToStatusTypeCondition = Restrictions.eq("equipmentTypeStatus", statusType);
                criteria.add(Restrictions.or(isNullCondition, equalToStatusTypeCondition));
            } else {
                final Criterion notEqualToStatusTypeCondition = Restrictions.ne("equipmentTypeStatus", statusType);
                criteria.add(Restrictions.or(isNullCondition, notEqualToStatusTypeCondition));
            }
            criteria.setProjection(Projections.projectionList()
                    .add(Projections.property("equipmentTypeName")));
        }

        return criteria.list();
    }

}
