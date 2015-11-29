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

package com.lucas.dao.user;

import com.lucas.dao.support.GenericDAO;
import com.lucas.entity.ui.canvas.Canvas;
import com.lucas.entity.ui.canvas.OpenUserCanvas;
import com.lucas.entity.user.User;
import com.lucas.entity.user.UserPreferences;
import com.lucas.services.search.SearchAndSortCriteria;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserDAO extends GenericDAO<User> {

	User findByUserName(String username);
	User findByUserId(Long userId);
	User findByHashedPassword(String hashedPassword);
	
	User getUser(String username);
	
    List<String> getActiveUsernameList() throws ParseException;	
	List<User> getUserList();
	List<User> getUserList(String pageSize, String pageNumber);
    List<Object[]> getAllUsersProperties(List<String> propList) throws ParseException;

    /**
	 * This method returns the total count of records given search and sort criteria excluding pagesize count.
	 * @param searchAndSortCriteria
	 * @return total count of records
	 * @throws ParseException
	 */
	Long getTotalCountForSearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException;
	
	Long getUserCount();

	List<OpenUserCanvas> getOpenCanvasesForUserId(Long userId);

}
