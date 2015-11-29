/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.services.user;

import com.lucas.dao.user.WmsUserDAO;
import com.lucas.entity.user.WmsUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service("wmsUserService")
@Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
public class WmsUserServiceImpl implements WmsUserService {

	private static Logger LOG = LoggerFactory.getLogger(WmsUserServiceImpl.class);
    private WmsUserDAO wmsUserDAO;


    @Inject
    public WmsUserServiceImpl(WmsUserDAO wmsUserDAO) {
        this.wmsUserDAO = wmsUserDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public WmsUser findOneUserById (Long userId) {
        return wmsUserDAO.findOneWmsUserById(userId);
    }


    @Override
    @Transactional(readOnly = true)
    public List<WmsUser> findAllWmsUsers() {
        return wmsUserDAO.findAllWmsUsers();
    }

}
