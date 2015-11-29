/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.services.user;

import com.lucas.entity.user.WmsUser;

import java.util.List;


public interface WmsUserService {

	WmsUser findOneUserById(Long userId);
    List<WmsUser> findAllWmsUsers();

}
