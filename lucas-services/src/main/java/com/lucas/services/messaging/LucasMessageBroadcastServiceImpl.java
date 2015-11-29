package com.lucas.services.messaging;


import com.lucas.exception.LucasRuntimeException;
import com.rabbitmq.client.AMQP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Service
public class LucasMessageBroadcastServiceImpl implements LucasMessageBroadcastService {

    private static Logger LOG = LoggerFactory.getLogger(LucasMessageBroadcastServiceImpl.class);

    @Inject
    @Qualifier(value = "connectionFactory")
    private ConnectionFactory connectionFactory;

    @Value("${lucas.mq.broadcast.exchange}")
    private String broadCastExchange;

    private RabbitAdmin rabbitAdmin;
    private Connection connection;
    private com.rabbitmq.client.Channel channel;

    @PostConstruct
    public void init() throws Exception {
        Assert.notNull(connectionFactory, "Connection Factory is Null");
        Assert.notNull(broadCastExchange, "Broadcast Exchange is Null");
        try {
        this.rabbitAdmin = new RabbitAdmin(this.connectionFactory);
        this.connection = connectionFactory.createConnection();
        this.channel = connection.createChannel(false);
        this.channel.exchangeDeclare(broadCastExchange, "direct");
        } catch (Exception e) {
            LOG.error("Exception Generated ",e);
            throw new LucasRuntimeException(e);
      }
    }

    @Override
    public void sendMessage(String queueName, String bindingKey, String data) throws Exception {
        Assert.notNull("Queue Name is Empty ", queueName);
        Assert.notNull("Binding Key is Empty ", bindingKey);
        Assert.notNull("Data is Empty ", data);
        this.rabbitAdmin.declareQueue(new Queue(queueName, true));
        this.channel.queueBind(queueName, broadCastExchange, bindingKey);
        this.channel.basicPublish(broadCastExchange, bindingKey, null, data.getBytes());
    }


    @Override
    public void sendData(String queueName, String bindingKey
           , byte[] data) throws LucasRuntimeException {
        Assert.notNull("Queue Name is Empty ", queueName);
        Assert.notNull("Binding Key is Empty ", bindingKey);
        Assert.isTrue(data.length != 0, "Data is Empty ");
        try {
            this.rabbitAdmin.declareQueue(new Queue(queueName, true));
            this.channel.queueBind(queueName, broadCastExchange, bindingKey);
            this.channel.basicPublish(broadCastExchange, bindingKey, null, data);
        } catch (Exception e) {
            LOG.error("Exception Generated ",e);
            throw new LucasRuntimeException(e);
        }
    }


    @Override
    public void sendBinaryFile(String queueName, String bindingKey
            , String fileName) throws LucasRuntimeException {
        Assert.notNull(fileName, "File Name is Null");
        try {
            final File file = new File(fileName);
            if (!file.exists()) {
                throw new FileNotFoundException(fileName + " Not Found ");
            }
            final FileInputStream fileInputStream = new FileInputStream(file);
            final byte[] fileData = new byte[(int) file.length()];
            fileInputStream.read(fileData);
            this.sendData(queueName, bindingKey, fileData);
            fileInputStream.close();
        } catch (Exception e) {
            LOG.error("Exception Generated ",e);
            throw new LucasRuntimeException(e);
        }
    }

    @PreDestroy
    public void close() throws LucasRuntimeException {
        try {
            this.channel.close();
            this.connection.close();
        } catch (Exception e) {
            LOG.error("Exception Generated ",e);
            throw new LucasRuntimeException(e);
        }
    }
}
