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

import com.lucas.entity.ui.configuration.LucasConfiguration;
import com.lucas.services.user.UserService;
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

public class LucasConfigurationServiceTest extends AbstractSpringFunctionalTests {

    private static Logger LOG = LoggerFactory.getLogger(LucasConfigurationServiceTest.class);

    @Inject
    private LucasConfigurationService lucasConfigurationService;

    @Inject
    private UserService userService;

    @Test
    @Transactional
    @Rollback(true)
    public void testSaveLucasConfiguration() throws Exception {
        final String clientLogoPath = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "logo" + File.separator + "upload" + File.separator + "jennifer.png";
        final LucasConfiguration lucasConfiguration = new LucasConfiguration();
        final byte[] lucasLogo = this.getLogoFile(clientLogoPath);
        lucasConfiguration.setLucasLogo(lucasLogo);
        lucasConfiguration.setUser(userService.getUser("jack123"));
        lucasConfiguration.setCreatedByUserName("jack");
        lucasConfiguration.setUpdatedByUserName("jack");
        lucasConfiguration.setUpdatedDataTime(new Date());
        lucasConfiguration.setCreatedDataTime(new Date());
        final LucasConfiguration lucasConfigurationPersisted = this.lucasConfigurationService.saveLucasConfiguration(lucasConfiguration);
        Assert.notNull(lucasConfigurationPersisted, "Lucas Configuration isn't Persisted");
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testGetLucasConfigurationByUserName() throws Exception {
        this.testSaveLucasConfiguration();
        // 4L refer to user jack123
        final LucasConfiguration lucasConfiguration = this.lucasConfigurationService.getLucasConfigurationByUserId(4L);
        Assert.notNull(lucasConfiguration, "Lucas Configuration is Null");
        Assert.isTrue(this.saveLogoFileToDisk(lucasConfiguration.getLucasLogo()), "Logo Image is not Saved on Disk");
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
        final String clientLogoPath = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "logo" + File.separator + "download" + File.separator + "jennifer.png";
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
