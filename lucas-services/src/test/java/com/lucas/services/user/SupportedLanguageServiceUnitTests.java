/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
package com.lucas.services.user;

import com.lucas.dao.user.PermissionDAO;
import com.lucas.dao.user.SupportedLanguageDAO;
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
public class SupportedLanguageServiceUnitTests {

	@Mock
	private Appender mockAppender;

	private SupportedLanguageService supportedLanguageService;
	
	private List<String> supportedLanguageCodesList = new ArrayList<>();

    @Mock
    private SupportedLanguageDAO supportedLanguageDAO;


	@Before
	public void setup(){

		supportedLanguageService =  Mockito.spy(new SupportedLanguageServiceImpl(supportedLanguageDAO));

        supportedLanguageCodesList.add("EN_US");
        supportedLanguageCodesList.add("FR_FR");
        supportedLanguageCodesList.add("DE_DE");
        supportedLanguageCodesList.add("ES_ES");

    }

    @Test
    public void testGetAllPermissionGroupNames() throws ParseException {
    	when(supportedLanguageDAO.findAllLanguageCodes()).thenReturn(supportedLanguageCodesList);
    	List<String> expectedLanguageList = supportedLanguageService.findAllLanguageCodes();
        Assert.assertEquals(4, expectedLanguageList.size());
    }
}