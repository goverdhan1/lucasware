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
import com.lucas.entity.equipment.LucasUserCertifiedEquipmentType;
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
@Repository("lucasUserCertifiedEquipmentTypeDAO")
public class LucasUserCertifiedEquipmentTypeDAOImpl extends ResourceDAO<LucasUserCertifiedEquipmentType>
        implements LucasUserCertifiedEquipmentTypeDAO {

    @Override
    public LucasUserCertifiedEquipmentType saveLucasUserCertifiedEquipmentType(LucasUserCertifiedEquipmentType lucasUserCertifiedEquipmentType) {
        return super.save(lucasUserCertifiedEquipmentType);
    }

    @Override
    public List<LucasUserCertifiedEquipmentType> findLucasUserCertifiedEquipmentTypeByEquipmentTypeId(Long equipmentTypeId) {
        final Session session = getSession();
        final Criteria criteria = session.createCriteria(LucasUserCertifiedEquipmentType.class);
        criteria.createAlias("equipmentType", "et");
        criteria.add(Restrictions.eq("et.equipmentTypeId", equipmentTypeId));
        final List<LucasUserCertifiedEquipmentType> lucasUserCertifiedEquipmentTypeList = criteria.list();
        return lucasUserCertifiedEquipmentTypeList;
    }

    @Override
    public boolean deleteLucasUserCertifiedEquipmentTypeByEquipmentTypeId(Long equipmentTypeId) {
        try {
            final List<LucasUserCertifiedEquipmentType> lucasUserCertifiedEquipmentTypeList = findLucasUserCertifiedEquipmentTypeByEquipmentTypeId(equipmentTypeId);
            for (LucasUserCertifiedEquipmentType lucasUserCertifiedEquipmentType : lucasUserCertifiedEquipmentTypeList) {
                super.delete(lucasUserCertifiedEquipmentType);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
