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

import com.lucas.dao.ui.configuration.LucasConfigurationDAO;
import com.lucas.entity.ui.configuration.LucasConfiguration;
import com.lucas.entity.user.User;
import com.lucas.services.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 12/23/2014  Time: 12:50 PM
 * This Class provide the implementation for the functionality of  CRUD
 * Operations on the com.lucas.entity.ui.LucasConfiguration by using
 * com.lucas.dao.ui.configuration.LucasConfigurationDAO implementation.
 * which is specified in LucasConfigurationService Interface.
 */

@Service
public class LucasConfigurationServiceImpl implements LucasConfigurationService {

    @Inject
    private LucasConfigurationDAO lucasConfigurationDAO;

    @Inject
    private UserService userService;

    @Transactional
    @Override
    public LucasConfiguration getLucasConfigurationByUserId(Long id) {
            return this.lucasConfigurationDAO.getLucasConfigurationByUserId(id);
    }

    @Transactional
    @Override
    public LucasConfiguration saveLucasConfiguration(final LucasConfiguration lucasConfiguration)throws Exception {
        final User user=this.userService.getUser(lucasConfiguration.getUser().getUserName());
        lucasConfiguration.setUser(user);
        return this.lucasConfigurationDAO.saveLucasConfiguration(lucasConfiguration);
    }
}
