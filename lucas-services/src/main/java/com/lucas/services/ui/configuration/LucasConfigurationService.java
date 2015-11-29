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

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 12/23/2014  Time: 12:50 PM
 * This Interface provide the specifications for the functionality of CRUD
 * Operations on the com.lucas.entity.ui.LucasConfiguration by using
 * com.lucas.dao.ui.configuration.LucasConfigurationDAO implementation.
 */

public interface LucasConfigurationService {

    /**
     * getLucasConfigurationByUserId() provide the functionality for
     * fetching the LucasConfiguration instance by supplying the
     * Super User Id  this method usage
     * com.lucas.dao.ui.configuration.LucasConfigurationDAO.getLucasConfigurationByUserId()
     *
     * @param id accept the instance of java.lang.Integer containing
     *           the super User id which is primary key for User
     * @return the persistence state LucasConfiguration instance
     */
    public LucasConfiguration getLucasConfigurationByUserId(Long id);


    /**
     * saveLucasConfiguration()  provide the functionality for
     * for persisting the  LucasConfiguration
     * instance which can be in detached state or in transient state.this method usage
     * com.lucas.dao.ui.configuration.LucasConfigurationDAO.saveLucasConfiguration()
     *
     * @param lucasConfiguration ccept the instance of
     *                           com.lucas.entity.ui.configuration.LucasConfiguration
     *                           containing the data for lucas configuration.
     * @return the persistence state LucasConfiguration instance
     *
     * @throws Exception
     *
     */
    public LucasConfiguration saveLucasConfiguration(LucasConfiguration lucasConfiguration) throws Exception;
}
