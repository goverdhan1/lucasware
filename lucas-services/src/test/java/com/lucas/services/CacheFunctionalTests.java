package com.lucas.services;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

import org.hibernate.ejb.EntityManagerFactoryImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.lucas.testsupport.AbstractSpringFunctionalTests;

@TransactionConfiguration(transactionManager = "resourceTransactionManager")
public class CacheFunctionalTests extends AbstractSpringFunctionalTests {

   

    @Inject
    private EntityManagerFactory resourceEntityManagerFactory;

   
    @Before
    public void setup() {

       
    }

    @Test
    public void testCache() {
    	EntityManagerFactoryInfo emfi = (EntityManagerFactoryInfo)resourceEntityManagerFactory;
    	EntityManagerFactory emf = emfi.getNativeEntityManagerFactory();
    	EntityManagerFactoryImpl empImpl = (EntityManagerFactoryImpl)emf;
    }

  

}
