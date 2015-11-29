/**
 * Copyright (c) Lucas Systems LLC
 */

package com.lucas.services.benchmark;

import javax.inject.Inject;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.commons.lang.StringUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lucas.entity.process.LucasProcessBean;
@Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
@Service("benchmarkService")
public class BenchmarkServiceImpl implements BenchmarkService, JavaDelegate {
	
	private static Logger LOG = LoggerFactory.getLogger(BenchmarkServiceImpl.class);
	
	private  final StandardPBEStringEncryptor stringEncryptor;
	
	@Inject
	public BenchmarkServiceImpl(StandardPBEStringEncryptor stringEncryptor) {
		this.stringEncryptor = stringEncryptor;
	}
	
	//Method representing business logic
	@Override
	public boolean authenticate(String userName, String password) {	  
		boolean boo = false;
		if (userName.equals("bob")) { 		
			boo = true;
		} else {
			boo = false;
		}
		return boo;
	}

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		LOG.debug("Starting execution...");
		LucasProcessBean lpb = (LucasProcessBean)execution.getVariable("lpb");
		encryptDecryptEffort();
		lpb.getUser().setAuthenticated(authenticate(lpb.getUser().getUserName(), lpb.getUser().getHashedPassword()));
	}
	
	/**
	 * A made up method to confirm if encryption is set up correctly.
	 */
	@Override
	public void checkEncryptorSeeding(){ 
		String decryptedToken = stringEncryptor.decrypt("vpO7cByDL8kdnvPpN+yO/wi+kPILHpcF");	
		Assert.isTrue(StringUtils.equals(decryptedToken, "aPlainToken"));
	}
	
	/**
	 * Just some made up processing.
	 */
	@Override
	public void encryptDecryptEffort(){
		String name = "bob";
		long start = System.currentTimeMillis();
		String ep = stringEncryptor.encrypt(name);
		long stop = System.currentTimeMillis();
		
		LOG.debug(String.format("Encrypting Bob in %d ms to %s ", stop - start, ep));
		start = System.currentTimeMillis();
		String dp = stringEncryptor.decrypt(ep) ;
		stop = System.currentTimeMillis();
		
		if (!name.equals(dp)){
			throw new RuntimeException("Decryption failed!");
		}
		
		LOG.debug(String.format("Decrypting Bob in %d ms to %s ",  stop - start, dp));
	}

}
