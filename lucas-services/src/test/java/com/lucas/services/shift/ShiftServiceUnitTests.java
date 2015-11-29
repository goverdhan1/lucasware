/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.services.shift;

import com.lucas.dao.user.ShiftDAO;
import com.lucas.services.user.ShiftService;
import com.lucas.services.user.ShiftServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Appender;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value={StandardPBEStringEncryptor.class, StringUtils.class})
public class ShiftServiceUnitTests {

	@Mock
	private Appender mockAppender;

	private ShiftService shiftService;
	
	private List<String> shiftNamesList = new ArrayList<>();

    @Mock
    private ShiftDAO shiftDAO;

	@Before
	public void setup(){

        shiftService =  Mockito.spy(new ShiftServiceImpl(shiftDAO));

        shiftNamesList.add("day");
        shiftNamesList.add("night");

    }

    @Test
    public void testFindAllShiftNames() throws ParseException {
    	when(shiftDAO.findAllShiftNames()).thenReturn(shiftNamesList);
    	List<String> expectedShiftNamesList = shiftService.findAllShiftNames();
        Assert.assertEquals(2, expectedShiftNamesList.size());
    }
}
