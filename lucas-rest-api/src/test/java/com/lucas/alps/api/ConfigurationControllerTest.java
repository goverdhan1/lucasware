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
package com.lucas.alps.api;

import com.lucas.entity.ui.configuration.CustomerConfiguration;
import com.lucas.entity.ui.configuration.LucasConfiguration;
import com.lucas.services.ui.configuration.CustomerConfigurationService;
import com.lucas.services.ui.configuration.LucasConfigurationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 12/26/2014  Time: 3:24 PM
 * This Class provide the implementation for the functionality of..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration("/META-INF/spring/api-bootstrap-context.xml")
})
public class ConfigurationControllerTest extends AbstractControllerTests {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationControllerTest.class);

    @Inject
    private LucasConfigurationService lucasConfigurationService;

    @Inject
    private CustomerConfigurationService customerConfigurationService;

    private static final String USERNAME_JACK = "jack123";

    private static final String PASSWORD_JACK = "secret";


    @Test
    @Transactional
    @Rollback(true)
    public void testCustomerLogo() throws Exception {

        final String clientLogoUploadPath = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "logo" + File.separator + "upload" + File.separator + "cat.png";
        final CustomerConfiguration customerConfiguration = new CustomerConfiguration();
        final byte[] logo = this.getLogoFile(clientLogoUploadPath);
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

        final String clientLogoDownloadPath = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "logo" + File.separator + "download" + File.separator + "cat.png";
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/configuration/image/customer-logo/Caterpillar";
        resultActions = this.mockMvc.perform(get(url).header("Authentication-token", token).accept(MediaType.IMAGE_PNG_VALUE));
        LOG.debug("controller returns : {}", resultActions.andReturn().getResponse().getContentAsByteArray());
        byte[] fileData = resultActions.andReturn().getResponse().getContentAsByteArray();
        Assert.isTrue(this.saveLogoFileToDisk(fileData, clientLogoDownloadPath), "Client Logo Image is not Saved on Disk");
    }


    @Test
    @Transactional
    @Rollback(true)
    public void testLucasLogo() throws Exception {
        final String lucasLogoUploadPath = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "logo" + File.separator + "upload" + File.separator + "jennifer.png";
        final LucasConfiguration lucasConfiguration = new LucasConfiguration();
        final byte[] lucasLogo = this.getLogoFile(lucasLogoUploadPath);
        lucasConfiguration.setLucasLogo(lucasLogo);
        lucasConfiguration.setUser(userService.getUser("jack123"));
        lucasConfiguration.setCreatedByUserName("jack");
        lucasConfiguration.setUpdatedByUserName("jack");
        lucasConfiguration.setUpdatedDataTime(new Date());
        lucasConfiguration.setCreatedDataTime(new Date());
        final LucasConfiguration lucasConfigurationPersisted = this.lucasConfigurationService.saveLucasConfiguration(lucasConfiguration);
        Assert.notNull(lucasConfigurationPersisted, "Lucas Configuration isn't Persisted");

        final String lucasLogoDownloadPath = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "logo" + File.separator + "download" + File.separator + "jennifer.png";
        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/configuration/image/lucas-logo";
        resultActions = this.mockMvc.perform(get(url).header("Authentication-token", token).accept(MediaType.IMAGE_PNG_VALUE));
        LOG.debug("controller returns : {}", resultActions.andReturn().getResponse().getContentAsByteArray());
        byte[] fileData = resultActions.andReturn().getResponse().getContentAsByteArray();
        Assert.isTrue(this.saveLogoFileToDisk(fileData, lucasLogoDownloadPath), "Lucas Logo Image is not Saved on Disk");
    }


    private byte[] getLogoFile(final String logoPath) throws Exception {
        final File clientLogoFile = new File(logoPath);
        final FileInputStream fileInputStream = new FileInputStream(clientLogoFile);
        final byte[] fileByte = new byte[(int) clientLogoFile.length()];
        fileInputStream.read(fileByte);
        fileInputStream.close();
        return fileByte;
    }

    private boolean saveLogoFileToDisk(final byte[] logoFile, String clientLogoPath) throws Exception {
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
