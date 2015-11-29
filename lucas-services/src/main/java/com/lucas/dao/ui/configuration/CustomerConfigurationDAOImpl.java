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

import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.ui.configuration.CustomerConfiguration;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 12/23/2014  Time: 12:42 PM
 * This Class provide the implementation for the functionality specified
 * in the com.lucas.dao.ui.configuration.CustomerConfigurationDAO
 */

@Repository
public class CustomerConfigurationDAOImpl extends ResourceDAO<CustomerConfiguration>
        implements CustomerConfigurationDAO {

    @Override
    public CustomerConfiguration findByCustomerConfigurationById(Integer id) {
        return super.load(id);
    }

    @Override
    public CustomerConfiguration getByCustomerConfigurationByName(String clientName) {
        final Session session = getSession();
        final Criteria criteria = session.createCriteria(CustomerConfiguration.class);
        criteria.add(Restrictions.eq("clientName", clientName).ignoreCase());
        final CustomerConfiguration customerConfiguration = (CustomerConfiguration) criteria.uniqueResult();
        return customerConfiguration;
    }


    @Override
    public CustomerConfiguration saveCustomerConfiguration(CustomerConfiguration customerConfiguration) {
        final CustomerConfiguration customerConfiguration1 = this.getByCustomerConfigurationByName(customerConfiguration.getClientName());
        if (customerConfiguration1 == null) {
            super.save(customerConfiguration);
        } else {
            customerConfiguration.setClientId(customerConfiguration1.getClientId());
            customerConfiguration.setCreatedByUserName(customerConfiguration1.getCreatedByUserName());
            customerConfiguration.setCreatedDateAndTime(customerConfiguration1.getCreatedDateAndTime());
            super.save(customerConfiguration);
        }
        return customerConfiguration;
    }
}
