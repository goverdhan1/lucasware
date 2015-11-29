package com.lucas.services.user;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import com.lucas.entity.device.DeviceMessageOption;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.device.DeviceService;
import com.lucas.testsupport.AbstractSpringFunctionalTests;

@TransactionConfiguration(transactionManager = "resourceTransactionManager")
public class DeviceServiceFunctionalTest extends AbstractSpringFunctionalTests{

    private static final String DEVICE_ID = "32085421875d5167";

    private static final Logger log = LoggerFactory.getLogger(UserServiceFunctionalTests.class);

    @Inject
    private DeviceService deviceService;
    
    @Transactional
    @Test
    public void testGetMobileApplicationSetOptions() throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception {

        DeviceMessageOption deviceMsgOption;
        try {
            deviceMsgOption = this.deviceService.getMobileApplicationSetOptions(DEVICE_ID);
        } catch (Exception e) {
            throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
        }

        Assert.notNull(deviceMsgOption, "DeviceMessageOption is Null");
        Assert.notNull(deviceMsgOption.getBuilding().getBuildingId(), "DeviceMessageOption is Not Persisted");
        Assert.isTrue(deviceMsgOption.getBuilding().getBuildingId() != null, "DeviceMessageOption is null");

    }
    
    @Transactional
    @Test
    public void testGetMobileApplicationSetOptionsWithOutDeviceID() throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception {

        DeviceMessageOption deviceMsgOption = null;
        Throwable result = null;
        try {
            deviceMsgOption = this.deviceService.getMobileApplicationSetOptions("32085421875dxxxx");
        } catch (Exception e) {
            result = e;
        }
        
        Assert.isTrue(result instanceof Exception);

    }


}
