/*
 * Lucas Systems Inc 11279 Perry Highway Wexford PA 15090 United States
 * 
 * 
 * The information in this file contains trade secrets and confidential information which is the
 * property of Lucas Systems Inc.
 * 
 * All trademarks, trade names, copyrights and other intellectual property rights created,
 * developed, embodied in or arising in connection with this software shall remain the sole property
 * of Lucas Systems Inc.
 * 
 * Copyright (c) Lucas Systems, 2014 ALL RIGHTS RESERVED
 */
package com.lucas.dao.device;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.device.Device;
import com.lucas.entity.device.DeviceMessageOption;

@Repository
public class DeviceDAOImpl extends ResourceDAO<DeviceMessageOption> implements DeviceDAO {

    public DeviceMessageOption getMessageByDeviceId(String deviceId) {

        Session session = getSession();

        Criteria criteria = session.createCriteria(DeviceMessageOption.class, "messageOption");

        criteria.createAlias("messageOption.building", "building");

        criteria.createAlias("building.device", "device");

        criteria.add(Restrictions.eq("device.deviceId", deviceId).ignoreCase());

        DeviceMessageOption deviceMsg = (DeviceMessageOption) criteria.uniqueResult();

        return deviceMsg;
    }

    @Override
    public Device getDeviceByID(String deviceId) {

        Session session = getSession();
        Criteria criteria = session.createCriteria(Device.class);

        criteria.add(Restrictions.eq("deviceId", deviceId).ignoreCase());

        Device device = (Device) criteria.uniqueResult();

        return device;

    }


}
