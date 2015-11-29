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

import com.lucas.dao.ui.configuration.CustomerConfigurationDAO;
import com.lucas.entity.ui.configuration.CustomerConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 12/23/2014  Time: 12:48 PM
 * This Class provide the implementation for the functionality of CRUD
 * Operations on the com.lucas.entity.ui.CustomerConfiguration by using
 * com.lucas.dao.ui.configuration.CustomerConfigurationDAO implementation.
 * which is specified in CustomerConfigurationService Interface.
 */

@Service
public class CustomerConfigurationServiceImpl implements CustomerConfigurationService {

    @Inject
    private CustomerConfigurationDAO customerConfigurationDAO;

    @Transactional
    @Override
    public CustomerConfiguration getByCustomerConfigurationByName(String name) {
        return this.customerConfigurationDAO.getByCustomerConfigurationByName(name);
    }

    @Transactional
    @Override
    public CustomerConfiguration saveCustomerConfiguration(CustomerConfiguration customerConfiguration) {
        return this.customerConfigurationDAO.saveCustomerConfiguration(customerConfiguration);
    }
}
