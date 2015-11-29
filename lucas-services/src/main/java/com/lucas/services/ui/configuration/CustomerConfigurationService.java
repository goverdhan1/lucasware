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

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 12/23/2014  Time: 12:48 PM
 *  This Interface provide the specifications for the functionality of CRUD
 * Operations on the com.lucas.entity.ui.CustomerConfiguration by using
 * com.lucas.dao.ui.configuration.CustomerConfigurationDAO implementation.
 */

public interface CustomerConfigurationService {

    /**
     * findByCustomerConfigurationById() provide the functionality for
     * fetching the Customer Configurations by supplying the Customer
     * name which is unique key this method usage
     * com.lucas.dao.ui.configuration.CustomerConfigurationDAO.getByCustomerConfigurationByName()
     *
     * @param name accept the instance of java.lang.String containing
     *             name of the customer.
     * @return the persistence state CustomerConfiguration instance
     */
    public CustomerConfiguration getByCustomerConfigurationByName(String name);

    /**
     *  saveCustomerConfiguration()  provide the functionality for persisting the CustomerConfiguration
     *  instance which can be in detached state or in transient state. this method usage
     *  com.lucas.dao.ui.configuration.CustomerConfigurationDAO.saveCustomerConfiguration()
     * @param customerConfiguration  accept the instance of
     *                               com.lucas.entity.ui.configuration.CustomerConfiguration
     *                               containing the data for customer configuration.
     * @return the persistence state CustomerConfiguration instance
     */
    public CustomerConfiguration saveCustomerConfiguration(CustomerConfiguration customerConfiguration);
}