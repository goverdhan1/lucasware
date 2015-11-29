package com.lucas.services.user;

import com.lucas.testsupport.AbstractSpringFunctionalTests;
import org.junit.Test;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.List;

@TransactionConfiguration(transactionManager = "resourceTransactionManager")
public class SupportedLanguageServiceFunctionalTests extends AbstractSpringFunctionalTests {

    @Inject
    private SupportedLanguageService supportedLanguageService;


    @Transactional(readOnly = true)
    @Test
    public void testGetAllPermissionGroupNames() throws ParseException {

        final List<String> languageList = this.supportedLanguageService.findAllLanguageCodes();

        Assert.notNull(languageList);
    }

}