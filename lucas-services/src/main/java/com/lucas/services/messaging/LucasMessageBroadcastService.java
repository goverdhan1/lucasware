package com.lucas.services.messaging;


import com.rabbitmq.client.AMQP;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 6/20/14  Time: 3:38 PM
 * This Class provide the implementation for the functionality of..
 */

public interface LucasMessageBroadcastService {

    public void sendMessage(String queueName, String bindingKey, String data) throws Exception;

    public void sendData(String queueName, String bindingKey
            , byte[] data) throws Exception;

    public void sendBinaryFile(String queueName, String bindingKey
            , String fileName)throws Exception;
}
