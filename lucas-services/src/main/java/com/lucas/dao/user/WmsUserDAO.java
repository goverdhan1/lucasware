/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.dao.user;

import com.lucas.entity.user.WmsUser;

import java.util.List;

public interface WmsUserDAO {
	
    WmsUser findOneWmsUserById(Long id);
    List findAllWmsUsers();

}
