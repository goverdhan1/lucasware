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
import com.lucas.entity.device.DeviceMessageOption;
import com.lucas.entity.equipment.Equipment;
import com.lucas.entity.equipment.EquipmentType;
import com.lucas.entity.equipment.LucasUserCertifiedEquipmentType;

import com.lucas.entity.user.User;
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
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of..
 */
@Repository("equipmentDAO")
public class EquipmentDAOImpl extends ResourceDAO<Equipment>
        implements EquipmentDAO {

    @Override
    public Equipment findByEquipmentId(Long equipmentId) {
        final Session session = getSession();
        final Criteria criteria = session.createCriteria(Equipment.class);
        criteria.add(Restrictions.eq("equipmentId", equipmentId));
        criteria.add(Restrictions.isNull(equipmentStatus));
        final Equipment equipment = (Equipment) criteria.uniqueResult();
        return equipment;
    }

    @Override
    public Equipment findByEquipmentName(String equipmentName) {
        final Session session = getSession();
        final Criteria criteria = session.createCriteria(Equipment.class);
        criteria.add(Restrictions.eq("equipmentName", equipmentName));
        criteria.add(Restrictions.isNull(equipmentStatus));
        final Equipment equipment = (Equipment) criteria.uniqueResult();
        return equipment;
    }

    @Override
    public List<Equipment> getAllEquipment() {
        final Session session = getSession();
        final Criteria criteria = session.createCriteria(Equipment.class);
        criteria.add(Restrictions.isNull(equipmentStatus));
        final List<Equipment> equipmentList = criteria.list();
        return equipmentList;
    }

    /* (non-Javadoc)
     * @see com.lucas.services.equipment.EquipmentDAO#saveEquipment()
     */
    @Override
    public boolean saveEquipment(Equipment equipment) {
        if (equipment != null) {
            super.save(equipment);
            return true;
        }
        return false;
    }

    @Override
    public boolean saveEquipments(List<Equipment> equipmentList) {
        if (equipmentList != null) {
            for (Equipment equipment : equipmentList) {
                super.save(equipment);
            }
            return true;
        }
        return false;
    }

    /**
     * findEquipmentListByTypeId()  provide the specification for soft deleting
     * the EquipmentType from DB
     *
     * @param equipmentTypeId
     * @return
     */
    @Override
    public List<Equipment> findEquipmentListByTypeId(Long equipmentTypeId) {
        final Session session = getSession();
        final Criteria criteria = session.createCriteria(Equipment.class);
        criteria.createAlias("equipmentType", "et");
        criteria.add(Restrictions.eq("et.equipmentTypeId", equipmentTypeId));
        final List<Equipment> equipmentList = criteria.list();
        return equipmentList;
    }

    /* (non-Javadoc)
    * @see com.lucas.services.equipment.EquipmentDAO#deleteEquipment()
    */
    @Override
    public boolean deleteEquipment(Equipment equipment) {
        try {
            super.delete(equipment);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /* (non-Javadoc)
     * @see com.lucas.services.equipment.EquipmentDAO#getEquipmentListBySearchAndSortCriteria()
     */
    @Override
    public List<Equipment> getEquipmentListBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException{
            final Session session = getSession();
            final Criteria criteria = this.getEquipmentSearchAndSortCriteria(session, searchAndSortCriteria);
            final List<Equipment> equipmentList = criteria.list();
            return equipmentList;
    }

    /**
     * getEquipmentSearchAndSortCriteria() provide the functionality for searching and sorting on Equipment and its
     * related tables based on the search and sort
     *
     * @param session of hibernate  is accepted by this method as the formal argument
     * @param searchAndSortCriteria is accepted by this method as the formal argument containing the criteria for fetch
     * @return Criteria of hibernate containing the basics for searching and sorting on Equipment.
     * @throws Exception
     */
    private Criteria getEquipmentSearchAndSortCriteria(final Session session, final SearchAndSortCriteria searchAndSortCriteria) throws ParseException {
        final Criteria criteria = session.createCriteria(Equipment.class, "equipment");
        criteria.add(Restrictions.isNull(equipmentStatus));

        if (!CollectionsUtilService.isNullOrEmpty(searchAndSortCriteria.getSearchMap())) {
            final String percentageSymbol ="%";
            for (Map.Entry<String, Object> searchEntry : searchAndSortCriteria.getSearchMap().entrySet()) {
                final Object value = searchEntry.getValue();
                String key = searchEntry.getKey();
                String referenceColumn="";

                if(EquipmentDAO.equipmentTypeName.equals(key)){
                    criteria.createAlias("equipment.equipmentType","et");
                    referenceColumn="et.equipmentTypeName";
                    key="equipmentType";
                }else if(EquipmentDAO.userName.equals(key)){
                    criteria.createAlias("equipment.user","us");
                    referenceColumn="us.userName";
                    key="user";
                }else if(EquipmentDAO.firstName.equals(key)){
                    criteria.createAlias("equipment.user","us");
                    referenceColumn="us.firstName";
                    key="user";
                }else if(EquipmentDAO.lastName.equals(key)){
                    criteria.createAlias("equipment.user","us");
                    referenceColumn="us.lastName";
                    key="user";
                }else if(EquipmentDAO.equipmentStyleName.equals(key)){
                    criteria.createAlias("equipment.equipmentType","et");
                    criteria.createAlias("et.equipmentStyle","es");
                    referenceColumn="es.equipmentStyleName";
                    key="equipmentType";
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
                            if ((type != null) && (type == String.class) && (type == EquipmentType.class)&& (type == User.class)) {
                                criteria.add(Restrictions.or(Restrictions.isNull(key), Restrictions.eq(key, "")));
                            } else {
                                criteria.add(Restrictions.isNull(key));
                            }
                        } else if(dataElement.equals(FilterType.NON_EMPTY.toString())) {
                            if ((type != null) && (type == String.class)&& (type == EquipmentType.class)&& (type == User.class)) {
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
                final SortType value = sortEntry.getValue();

                if(EquipmentDAO.equipmentTypeName.equals(key)){
                    key="et.equipmentTypeName";
                }else if(EquipmentDAO.userName.equals(key)){
                    key="us.userName";
                }else if(EquipmentDAO.firstName.equals(key)){
                    key="us.firstName";
                }else if(EquipmentDAO.lastName.equals(key)){
                    key="us.lastName";
                }else if(EquipmentDAO.equipmentStyleName.equals(key)){
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

    /* (non-Javadoc)
     * @see com.lucas.services.equipment.EquipmentDAO#getTotalCountForSearchAndSortCriteria()
     */
    @Override
    public Long getTotalCountForSearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws  ParseException {
        final Session session = getSession();
        final Criteria criteria=this.getEquipmentSearchAndSortCriteria(session, searchAndSortCriteria);
        return super.getTotalCountForSearchAndSortCriteria(criteria);
    }


}
