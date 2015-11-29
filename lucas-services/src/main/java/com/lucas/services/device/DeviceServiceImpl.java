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
package com.lucas.services.device;

import java.util.*;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lucas.dao.device.DeviceDAO;
import com.lucas.entity.device.Device;
import com.lucas.entity.device.DeviceMessageOption;
import com.lucas.exception.DeviceDoesNotExistException;

@Service("deviceService")
public class DeviceServiceImpl implements DeviceService {

    private static Logger LOG = LoggerFactory.getLogger(DeviceServiceImpl.class);
    private final DeviceDAO deviceDAO;

    @Inject
    public DeviceServiceImpl(DeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public DeviceMessageOption getMobileApplicationSetOptions(String deviceId) {
        final Device device = this.deviceDAO.getDeviceByID(deviceId);
        if (device == null) {
            throw new DeviceDoesNotExistException(String.format("No device exists with deviceId %s. Cannot get device!", device.getDeviceId()));
        }
        DeviceMessageOption deviceMsg = null;

        try {
            if (device.getDeviceId() != null) {
                deviceMsg = this.deviceDAO.getMessageByDeviceId(device.getDeviceId());
            } else {
                LOG.debug("No Device found in the System ");
            }
        } catch (Exception e) {
            LOG.debug("No Device found in the System " + e.getMessage());
        }


        return deviceMsg;
    }

}
