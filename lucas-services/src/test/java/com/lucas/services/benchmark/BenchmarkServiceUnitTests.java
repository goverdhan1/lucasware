package com.lucas.services.benchmark;

import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


@RunWith(PowerMockRunner.class)
@PrepareForTest(value={StandardPBEStringEncryptor.class})
public class BenchmarkServiceUnitTests {
	
	@Mock
	private StandardPBEStringEncryptor stringEncryptor;

	//Cannot use InjectMocks here because we are using PowerMock to mock the final class StandardPBEStringEncryptor and Mockito does
	//not know about Powermock mocks. So explicitly create the benchMarkService instance in the @Before method
	//by passing in the powerMock mocked instance of StandardPBEStringEncryptor.
	//@InjectMocks
	private BenchmarkService benchmarkService;
	
	@Before
	public void setup(){
		mockStatic(StandardPBEStringEncryptor.class);		
		benchmarkService = new BenchmarkServiceImpl(stringEncryptor);
	}
	
	@Test
	public void testEncryption(){
		when(stringEncryptor.encrypt("bob")).thenReturn("encryptedString");
		when(stringEncryptor.decrypt("encryptedString")).thenReturn("bob");
		
		benchmarkService.encryptDecryptEffort();
	}
	
	@Test (expected=RuntimeException.class)
	public void testEncryptionWithException(){
		when(stringEncryptor.encrypt("bob")).thenReturn("encryptedString");
		when(stringEncryptor.decrypt("encryptedString")).thenReturn("bob1");
		
		benchmarkService.encryptDecryptEffort();
	}
}
