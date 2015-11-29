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
import com.lucas.entity.equipment.EquipmentTypePosition;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of..
 */
@Repository("equipmentTypePositionDAO")
public class EquipmentTypePositionDAOImpl extends ResourceDAO<EquipmentTypePosition>
        implements EquipmentTypePositionDAO {


    @Override
    public EquipmentTypePosition findEquipmentTypePositionById(Long equipmentTypePositionId) {
        final Session session = getSession();
        final Criteria criteria = session.createCriteria(EquipmentTypePosition.class);
        criteria.add(Restrictions.eq("equipmentTypePositionId", equipmentTypePositionId));
        final EquipmentTypePosition equipmentTypePosition = (EquipmentTypePosition) criteria.uniqueResult();
        return equipmentTypePosition;
    }

    @Override
    public List<EquipmentTypePosition> findEquipmentTypePositionByEquipmentTypeId(Long equipmentTypeId) {
        final Session session = getSession();
        final Criteria criteria = session.createCriteria(EquipmentTypePosition.class);
        criteria.createAlias("equipmentType", "et");
        criteria.add(Restrictions.eq("et.equipmentTypeId", equipmentTypeId));
        final List<EquipmentTypePosition> equipmentTypePositionList = criteria.list();
        return equipmentTypePositionList;
    }

    @Override
    public boolean saveEquipmentTypePositions(List<EquipmentTypePosition> equipmentTypePositionList) {
        if (equipmentTypePositionList != null) {
            for (EquipmentTypePosition equipmentTypePosition : equipmentTypePositionList) {
                super.save(equipmentTypePosition);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteEquipmentTypePositionByEquipmentTypeId(Long equipmentTypeId) {
        try {
            final List<EquipmentTypePosition> equipmentTypePositionList = findEquipmentTypePositionByEquipmentTypeId(equipmentTypeId);
            for (EquipmentTypePosition equipmentTypePosition : equipmentTypePositionList) {
                equipmentTypePosition.setEquipmentType(null);
                super.delete(equipmentTypePosition);
            }
            return true;
        } catch (Exception e) {
             return false;
        }
    }
}
