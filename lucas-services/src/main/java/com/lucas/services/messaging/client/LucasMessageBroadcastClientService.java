package com.lucas.services.messaging.client;

import java.io.File;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 6/23/14  Time: 2:30 PM
 * This Class provide the implementation for the functionality of..
 */

public interface LucasMessageBroadcastClientService {


    public byte[] receiveMessage(String queueName, String bindingKey, long timeout) throws Exception;

    public String receiveFirstMessage(String queueName, String bindingKey, long timeout) throws Exception;

    public String[] receiveAllMessagesWithinTimePeriod(String queueName, String bindingKey, long timeoutMillis, long timePeriodMillis) throws Exception;

    public byte[] receiveData(String queueName, String bindingKey) throws Exception;

    public boolean clearQueue(String queueName) throws Exception;

    public File receiveBinaryFile(String fileName, String queueName, String bindingKey) throws Exception;

}
