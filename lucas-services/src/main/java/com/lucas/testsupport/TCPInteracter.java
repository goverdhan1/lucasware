package com.lucas.testsupport;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import com.lucas.exception.LucasRuntimeException;

public class TCPInteracter {
	
	private static Logger logger = LoggerFactory.getLogger(TCPInteracter.class);
	
	private String tokenForRoundTrip;
	private String remoteHostToSendTo;
	private int remoteHostPort;
	private int localPortToListenOn;
	private int secondsToListenForBeforeBreaking;
	
	private boolean roundTripSuccessful;
	private long roundTripMilliseconds;
	private StopWatch stopWatch;
	
	private TCPInteracter(){} 
	
	/**
	 * 
	 * @param tokenForRoundTrip - Token to send to the TCP port and also the token to expect receipt for.
	 * @param remoteHostToSendTo - host to send TCP token to
	 * @param remoteHostPort - remote port
	 * @param localPortToListenOn - local port
	 * @param secondsToListenForBeforeBreaking - With for these amount of seconds before breaking out of the wait loop.
	 */
	public TCPInteracter(String tokenForRoundTrip, 
							String remoteHostToSendTo,
							int remoteHostPort,
							int localPortToListenOn,
							int secondsToListenForBeforeBreaking){
		this.tokenForRoundTrip = tokenForRoundTrip;
		this.remoteHostToSendTo = remoteHostToSendTo;
		this.remoteHostPort = remoteHostPort;
		this.localPortToListenOn = localPortToListenOn;
		this.secondsToListenForBeforeBreaking = secondsToListenForBeforeBreaking;
	}
	
	public void sendAndReceive(){
		stopWatch = new StopWatch();
		stopWatch.start();
		sendToRemoteServer(this.tokenForRoundTrip, this.remoteHostToSendTo, this.remoteHostPort);
		waitAndReceiveOnLocalServer(this.tokenForRoundTrip, this.localPortToListenOn, this.secondsToListenForBeforeBreaking);
		long elapsed = stopWatch.getTotalTimeMillis();
		this.setRoundTripMilliseconds(elapsed);
	}
	
	public void sendToRemoteServer(String token, String host, int port){
		Socket clientSocket = null;
		DataOutputStream outToServer = null;
		try {
			clientSocket = new Socket(InetAddress.getByName(host), port);
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			outToServer.writeBytes(token);
			writeByte(outToServer, '\r', true);
            writeByte(outToServer, '\n', true);
			logger.info("Wrote: " + token);
		} catch (Exception e) {
			throw new LucasRuntimeException(e);
		} finally {
			try {
				if (outToServer != null) {
					outToServer.close();
				}
				if (clientSocket != null) {
					clientSocket.close();
				}
			} catch (Exception e) {
				// squelch
			}
		}
	}
	
	/**
	 * Listens on the specified <code>port</code> on localhost until it receives the <code>quitToken</code> 
	 * OR, if <code>waitSeconds</code> is > 0, if waitSeconds is reached, whichever occurs first.
	 * @param quitToken
	 * @param port
	 * @throws Exception
	 */
	public void waitAndReceiveOnLocalServer(final String tokenToQuitOn,  final int port, int waitSeconds) {
		Thread thread = new Thread(new Runnable() {

			public void run() {
				String clientSentence = "";
				ServerSocket welcomeSocket = null;
				Socket connectionSocket = null;
				try {
					logger.debug("Going to open port " + localPortToListenOn);
					welcomeSocket = new ServerSocket(localPortToListenOn);
					logger.debug("Opened port " + localPortToListenOn);
					while (true) {
						logger.debug("Going to accept");
						connectionSocket = welcomeSocket.accept();
						logger.debug("Accepted");
						BufferedReader inFromClient = new BufferedReader(
								new InputStreamReader(
										connectionSocket.getInputStream()));
						
						clientSentence = inFromClient.readLine();
						logger.info("Received: " + clientSentence);
						if (StringUtils.equals(clientSentence, tokenToQuitOn)) {
							if (stopWatch != null){
								stopWatch.stop();
							}
							setRoundTripSuccessful(true);
							logger.info("My work here is done! Received quitToken, bailing out...");
							break;
						}
					}
				} catch (Exception e) {
					throw new LucasRuntimeException(e);
				} finally {
					try {
						if (connectionSocket != null) {
							connectionSocket.close();
						}
						if (welcomeSocket != null) {
							welcomeSocket.close();
						}
					} catch (Exception e) {
						// squelch
					}
				}
			}
		});
		thread.setDaemon(true);
        thread.start();
        
		
        if (thread.isAlive() && !thread.isInterrupted()) {
        	//Thread is still alive and kicking...
			if (secondsToListenForBeforeBreaking > 0) {
				// wait if waitSeconds is specified;
				try {
					StopWatch miniWatch = new StopWatch();
					miniWatch.start();
					//Check every 100 ms if the job is finished via the roundTripSuccessful flag.
					while (!this.roundTripSuccessful && (miniWatch.getTotalTimeMillis() < (secondsToListenForBeforeBreaking * 1000))){
						thread.sleep(100);
					}
					if (stopWatch != null && stopWatch.isRunning()){
						stopWatch.stop();
					}
					if (thread.isAlive()){
						thread.interrupt();
					}
					logger.info(String.format("Interrupting wait after %s seconds", miniWatch.getTotalTimeMillis()));
					miniWatch.stop();
				} catch (Exception e){
					logger.warn("Error while waiting for thread completion!", e);
				}
			}
        }
	   
	}
		
    private void writeByte(OutputStream os, int b, boolean noDelay) throws Exception {
        os.write(b);
        logger.trace("Wrote 0x" + Integer.toHexString(b));
        if (noDelay) {
                return;
        }
        Thread.sleep(500);
    }

	public boolean isRoundTripSuccessful() {
		return roundTripSuccessful;
	}

	public void setRoundTripSuccessful(boolean roundTripSuccessful) {
		this.roundTripSuccessful = roundTripSuccessful;
	}

	public long getRoundTripMilliseconds() {
		return roundTripMilliseconds;
	}

	public void setRoundTripMilliseconds(long roundTripMilliseconds) {
		this.roundTripMilliseconds = roundTripMilliseconds;
	}

	public String getTokenForRoundTrip() {
		return tokenForRoundTrip;
	}
	
}
