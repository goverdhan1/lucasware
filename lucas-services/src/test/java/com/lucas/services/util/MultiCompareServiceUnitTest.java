package com.lucas.services.util;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.MutablePair;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import com.lucas.entity.user.User;
import com.lucas.exception.LucasRuntimeException;


public class MultiCompareServiceUnitTest {

	
	@Before
	public void setup() {

	}
	
	@Test
    public void testGetMutablePairMap() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, LucasRuntimeException, IntrospectionException, ParseException  {
		
		List<User> userList = new ArrayList<User>();
		User user1 = new User();
		user1.setUserName("admin123");
		userList.add(user1);
        User user2 = new User();
        user2.setUserName("jill123");
        userList.add(user2);
        User user3 = new User();  
        user3.setUserName("jack123");
        userList.add(user3);
        User user4 = new User();  
        user4.setUserName("jack123");
        userList.add(user4);



		List<String> multiUserEditableFieldsList = new ArrayList<String>();
		multiUserEditableFieldsList.add("skill");
    	// Assert 1 ( skill are different,not null -> expectation (null , false)
    	// Assert 2 ( skill are same, not null -> expectation (actualValue, false)
    	// Assert 3 ( skill are same, but  one of them is null -> expectation (actualValue, true)
    	// Assert 4 ( skill are different, one of them is null -> expectation (null, false)   
        
    	
    	//Adding test data for assert 1
    	// Assert 1 ( skills are different,not null -> expectation (null)
    	userList.get(0).setSkill("Beginner");
    	userList.get(1).setSkill("Advanced");
    	userList.get(2).setSkill("Standard");
    	Map<String, String> userEditableFields = MultiCompare.getMutablePairMap(userList, multiUserEditableFieldsList);
    	Assert.isTrue(userEditableFields.get("skill") == null);
    	
    	//Adding test data for assert 2
    	// Assert 2 ( skill are same, not null -> expectation (actualValue, false)
    	userList.get(0).setSkill("Advanced");
    	userList.get(1).setSkill("Advanced");
    	userList.get(2).setSkill("Advanced");
    	userEditableFields = MultiCompare.getMutablePairMap(userList, multiUserEditableFieldsList);   	
    	Assert.isTrue(userEditableFields.get("skill").toString() != null);	
    	
    	//Adding test data for assert 3
    	// Assert 3 ( skill are same, but  one of them is null -> expectation (actualValue)
    	userList.get(0).setSkill("Advanced");
    	userList.get(1).setSkill("Advanced");
    	userList.get(2).setSkill(null);
    	userEditableFields = MultiCompare.getMutablePairMap(userList, multiUserEditableFieldsList);   	
    	Assert.isTrue(userEditableFields.get("skill").toString() != null);  	
    	
    	//Adding test data for assert 4
    	// Assert 4 ( skill are different, one of them is null -> expectation (null) 
    	userList.get(0).setSkill(null);
    	userList.get(1).setSkill("Advanced");
    	userList.get(2).setSkill("Standard");
    	userEditableFields = MultiCompare.getMutablePairMap(userList, multiUserEditableFieldsList);  	
    	Assert.isTrue(userEditableFields.get("skill") == null);

    	//Adding test data for assert 4
    	// Assert 4 ( skill are different, one of them is null -> expectation (null) 
    	userList.get(0).setSkill("Advanced");
    	userList.get(1).setSkill(null);
    	userList.get(2).setSkill("Standard");
    	userEditableFields = MultiCompare.getMutablePairMap(userList, multiUserEditableFieldsList);  	
    	Assert.isTrue(userEditableFields.get("skill") == null);
    	
    	//Adding test data for assert 4
    	// Assert 4 ( skill are different, one of them is null -> expectation (null) 
    	userList.get(0).setSkill("Standard");
    	userList.get(1).setSkill(null);
    	userList.get(2).setSkill("Advanced");
    	userEditableFields = MultiCompare.getMutablePairMap(userList, multiUserEditableFieldsList);  	
    	Assert.isTrue(userEditableFields.get("skill") == null);
    	
    	//Adding test data for assert 5
    	// Assert 5 ( skill are null -> expectation (null) 
    	userList.get(0).setSkill(null);
    	userList.get(1).setSkill(null);
    	userList.get(2).setSkill(null);
    	userEditableFields = MultiCompare.getMutablePairMap(userList, multiUserEditableFieldsList);  	
    	Assert.isTrue(userEditableFields.get("skill") == null);
	}

}