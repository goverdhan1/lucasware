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
import com.lucas.entity.ui.configuration.LucasConfiguration;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 12/23/2014  Time: 12:43 PM
 * This Class provide the implementation for the functionality specified
 * in the com.lucas.dao.ui.configuration.LucasConfigurationDAO
 */

@Repository
public class LucasConfigurationDAOImpl extends ResourceDAO<LucasConfiguration>
        implements LucasConfigurationDAO {


    @Override
    public LucasConfiguration getLucasConfigurationByUserId(Long id) {
        final Session session = getSession();
        final Criteria criteria = session.createCriteria(LucasConfiguration.class);
        criteria.add(Restrictions.eq("user.userId", id));
        final LucasConfiguration lucasConfiguration = (LucasConfiguration) criteria.uniqueResult();
        return lucasConfiguration;
    }


    @Override
    public LucasConfiguration saveLucasConfiguration(LucasConfiguration lucasConfiguration) {
        final LucasConfiguration lucasConfiguration1 = this.getLucasConfigurationByUserId(3L);
        if (lucasConfiguration1 == null) {
            super.save(lucasConfiguration);
        } else {
            lucasConfiguration.setId(lucasConfiguration1.getId());
            lucasConfiguration.setCreatedByUserName(lucasConfiguration1.getCreatedByUserName());
            lucasConfiguration.setCreatedDataTime(lucasConfiguration1.getCreatedDataTime());
            super.save(lucasConfiguration);
        }
        return lucasConfiguration;
    }
}
