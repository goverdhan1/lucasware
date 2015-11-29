package com.lucas.services.shift;

import com.lucas.services.user.ShiftService;
import com.lucas.testsupport.AbstractSpringFunctionalTests;
import org.junit.Test;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.List;

@TransactionConfiguration(transactionManager = "resourceTransactionManager")
public class ShiftServiceFunctionalTests extends AbstractSpringFunctionalTests {

    @Inject
    private ShiftService shiftService;


    @Transactional(readOnly = true)
    @Test
    public void testGetAllShiftNames() throws ParseException {

        final List<String> shiftNames = this.shiftService.findAllShiftNames();

        Assert.notNull(shiftNames);
        }

}