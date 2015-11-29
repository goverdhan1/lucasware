package com.lucas.messaging;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lucas.testsupport.TCPInteracter;

/**
 * These tests need the AMQP message broker to be up.
 * The AMQP broker has consciously not been started via the build process because of the complexities involved.
 * It will be explicitly started in each environment.
 * Please see more here: <<wiki link>>
 * @author Pankaj Tandon
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/META-INF/spring/messaging-context.xml"})
public class MessagingIntegrationTests {

	@Value("${inbound.tcp.port}")
	private String remoteHostPort;

	@Value("${outbound.tcp.port}")
	private String localHostPort;
	private static Logger logger = LoggerFactory.getLogger(MessagingIntegrationTests.class);
	
	@Test
	public void testConsecutiveRoundTripsOnSamePorts(){
		
		int successCount = 0;
		/**
		 * 
		 * This test works successfully when a RabbitMQ server is up and running on the default port.
		 * It is commented out on 2/19/2014, because at this time RabbitMQ is not able to be run on the
		 * build server EC2 instance.
		 * 
		 * 
		TCPInteracter tcp = new TCPInteracter("blah1", "localhost", Integer.parseInt(remoteHostPort), Integer.parseInt(localHostPort), 10);
		tcp.sendAndReceive();
		logger.info("RoundTrip for " +  tcp.getTokenForRoundTrip() + " in: " + tcp.getRoundTripMilliseconds() + " ms.");
		if (tcp.isRoundTripSuccessful()){
			successCount++;
		}
		
		TCPInteracter tcp2 = new TCPInteracter("blah2", "localhost", Integer.parseInt(remoteHostPort), Integer.parseInt(localHostPort), 10);
		tcp2.sendAndReceive();
		logger.info("RoundTrip for " +  tcp2.getTokenForRoundTrip() + " in: " + tcp2.getRoundTripMilliseconds() + " ms.");
		if (tcp2.isRoundTripSuccessful()){
			successCount++;
		}
		TCPInteracter tcp3 = new TCPInteracter("blah3", "localhost", Integer.parseInt(remoteHostPort), Integer.parseInt(localHostPort), 10);
		tcp3.sendAndReceive();
		logger.info("RoundTrip for " +  tcp3.getTokenForRoundTrip() + " in: " + tcp3.getRoundTripMilliseconds() + " ms.");
		if (tcp3.isRoundTripSuccessful()){
			successCount++;
		}
		Assert.assertTrue(successCount == 3); 
		*/
	}
	
}
