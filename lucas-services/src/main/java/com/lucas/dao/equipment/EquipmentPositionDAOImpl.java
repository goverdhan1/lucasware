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
import com.lucas.entity.equipment.EquipmentPosition;
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
@Repository("equipmentPositionDAO")
public class EquipmentPositionDAOImpl extends ResourceDAO<EquipmentPosition>
        implements EquipmentPositionDAO {


    @Override
    public EquipmentPosition findEquipmentPositionById(Long equipmentPositionId) {
        final Session session = getSession();
        final Criteria criteria = session.createCriteria(EquipmentPosition.class);
        criteria.add(Restrictions.eq("equipmentPositionId", equipmentPositionId));
        final EquipmentPosition equipmentPosition = (EquipmentPosition) criteria.uniqueResult();
        return equipmentPosition;
    }

    @Override
    public List<EquipmentPosition> findEquipmentPositionByEquipmentId(Long equipmentId) {
        final Session session = getSession();
        final Criteria criteria = session.createCriteria(EquipmentPosition.class);
        criteria.createAlias("equipment", "e");
        criteria.add(Restrictions.eq("e.equipmentId", equipmentId));
        final List<EquipmentPosition> equipmentPositionList = criteria.list();
        return equipmentPositionList;
    }

    /* (non-Javadoc)
   * @see com.lucas.services.equipment.EquipmentPositionDAO#deleteEquipmentPositionByEquipmentId()
   */
    @Override
    public boolean deleteEquipmentPositionByEquipmentId(Long equipmentId) {
        try {
            final List<EquipmentPosition> equipmentPositionList = findEquipmentPositionByEquipmentId(equipmentId);
            for (EquipmentPosition equipmentPosition : equipmentPositionList) {
                equipmentPosition.setEquipment(null);
                super.delete(equipmentPosition);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
