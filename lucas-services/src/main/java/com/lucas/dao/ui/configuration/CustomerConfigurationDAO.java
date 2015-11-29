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
package com.lucas.dao.ui.configuration;

import com.lucas.dao.support.GenericDAO;
import com.lucas.entity.ui.configuration.CustomerConfiguration;


/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 12/23/2014  Time: 12:41 PM
 * This Interface provide the specifications for the functionality of CRUD
 * Operations on the com.lucas.entity.ui.configurationCustomerConfiguration.
 */

public interface CustomerConfigurationDAO extends GenericDAO<CustomerConfiguration> {

    /**
     * findByCustomerConfigurationById() provide the functionality for
     * fetching the Customer Configurations by supplying the Customer
     * configuration primary key.
     *
     * @param id accept the instance of java.lang.Integer containing
     *           customer configuration primary key
     * @return the persistence state CustomerConfiguration instance
     */
    public CustomerConfiguration findByCustomerConfigurationById(Integer id);

    /**
     * findByCustomerConfigurationById() provide the functionality for
     * fetching the Customer Configurations by supplying the Customer
     * name which is unique key
     *
     * @param name accept the instance of java.lang.String containing
     *             name of the customer.
     * @return the persistence state CustomerConfiguration instance
     */
    public CustomerConfiguration getByCustomerConfigurationByName(String name);

    /**
     *  saveCustomerConfiguration()  provide the functionality for persisting the CustomerConfiguration
     *  instance which can be in detached state or in transient state.
     * @param customerConfiguration  accept the instance of
     *                               com.lucas.entity.ui.configuration.CustomerConfiguration
     *                               containing the data for customer configuration.
     * @return the persistence state CustomerConfiguration instance
     */
    public CustomerConfiguration saveCustomerConfiguration(CustomerConfiguration customerConfiguration);
}
