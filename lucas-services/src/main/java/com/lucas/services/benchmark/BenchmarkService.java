/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.services.benchmark;

public interface BenchmarkService {
	
	boolean authenticate(String userName, String password);
	
	void encryptDecryptEffort();
	
	void checkEncryptorSeeding(); 

}
