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
package com.lucas.services.ui.configuration;

import com.lucas.entity.ui.configuration.CustomerConfiguration;
import com.lucas.testsupport.AbstractSpringFunctionalTests;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 12/26/2014  Time: 12:30 PM
 * This Class provide the implementation for the functionality of..
 */

public class CustomerConfigurationServiceTest extends AbstractSpringFunctionalTests {

    private static Logger LOG = LoggerFactory.getLogger(CustomerConfigurationServiceTest.class);

    @Inject
    private CustomerConfigurationService customerConfigurationService;


    @Test
    @Transactional
    @Rollback(true)
    public void testCustomerConfigurationSave() throws Exception {
        final String clientLogoPath = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "logo" + File.separator + "upload" + File.separator + "cat.png";
        final CustomerConfiguration customerConfiguration = new CustomerConfiguration();
        final byte[] logo = this.getLogoFile(clientLogoPath);
        customerConfiguration.setClientLogo(logo);
        final String clientName = "Caterpillar";
        customerConfiguration.setClientName(clientName);
        customerConfiguration.setCreatedByUserName("jack");
        customerConfiguration.setDefaultLanguage("english");
        customerConfiguration.setUpdatedByUserName("jack");
        customerConfiguration.setUpdatedDataTime(new Date());
        customerConfiguration.setCreatedDateAndTime(new Date());
        final CustomerConfiguration persistedCustomerConfiguration = this.customerConfigurationService.saveCustomerConfiguration(customerConfiguration);
        Assert.notNull(persistedCustomerConfiguration, "CustomerConfiguration is Not Persisted");
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testGetCustomerConfigurationByName() throws Exception {
        this.testCustomerConfigurationSave();
        final CustomerConfiguration customerConfiguration = this.customerConfigurationService.getByCustomerConfigurationByName("Caterpillar");
        Assert.notNull(customerConfiguration, "Customer Configuration is Null ");
        Assert.isTrue(saveLogoFileToDisk(customerConfiguration.getClientLogo()), "Logo Image is not Saved on Disk");
    }



    private byte[] getLogoFile(final String logoPath) throws Exception {
        final File clientLogoFile = new File(logoPath);
        final FileInputStream fileInputStream = new FileInputStream(clientLogoFile);
        final byte[] fileByte = new byte[(int) clientLogoFile.length()];
        fileInputStream.read(fileByte);
        fileInputStream.close();
        return fileByte;
    }

    private boolean saveLogoFileToDisk(final byte[] logoFile) throws Exception {
        final String clientLogoPath = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "logo" + File.separator + "download" + File.separator + "cat.png";
        boolean result = false;
        try {
            final File clientLogoFile = new File(clientLogoPath);
            if (!clientLogoFile.exists()) {
                clientLogoFile.createNewFile();
            }
            final FileOutputStream fileOutputStream = new FileOutputStream(clientLogoFile);
            fileOutputStream.write(logoFile);
            fileOutputStream.flush();
            fileOutputStream.close();
            result = true;
        } catch (Exception e) {
            LOG.error(e.toString());
            return result;
        }
        return result;
    }
}
