package com.lucas.services.benchmark;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import com.lucas.testsupport.AbstractSpringFunctionalTests;

public class BenchmarkServiceFunctionalTests extends AbstractSpringFunctionalTests{

	@Inject
	private BenchmarkService benchmarkService;
    @Test
	public void testAuthentication(){    	
    	boolean boo = benchmarkService.authenticate("bob", "doesn'tmatter");
    	Assert.assertTrue(boo);
    	boo = benchmarkService.authenticate("jack123", "doesn'tmatter");
    	Assert.assertTrue(!boo);
	}
    
    @Test
    public void testEncryptionSeeding(){
    	benchmarkService.checkEncryptorSeeding();
    }
}
