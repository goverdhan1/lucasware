package com.lucas.services.messaging.client;

import java.util.ArrayList;
import java.util.List;

import com.lucas.exception.LucasRuntimeException;
import com.rabbitmq.client.GetResponse;
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
import org.springframework.util.StopWatch;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.io.File;
import java.io.FileOutputStream;

@Service
public class LucasMessageBroadcastClientServiceImpl implements LucasMessageBroadcastClientService {

    private static Logger LOG = LoggerFactory.getLogger(LucasMessageBroadcastClientServiceImpl.class);

    @Inject
    @Qualifier(value = "connectionFactory")
    private ConnectionFactory connectionFactory;

    @Value("${lucas.mq.broadcast.exchange}")
    private String broadCastExchange;

    @Inject
    @Qualifier(value = "rabbitAdmin")
    private RabbitAdmin rabbitAdmin;

    private Connection connection;
    private com.rabbitmq.client.Channel channel;

    @PostConstruct
    public void init() throws Exception {
        Assert.notNull(connectionFactory, "Connection Factory is Null");
        Assert.notNull(broadCastExchange, "Broadcast Exchange is Null");
        Assert.notNull(rabbitAdmin, "Rabbit Admin is Null");
        try {
            this.connection = connectionFactory.createConnection();
            this.channel = connection.createChannel(false);
            this.channel.exchangeDeclare(broadCastExchange, "direct");
        } catch (Exception e) {
            LOG.error("Exception Generated ", e);
            throw new LucasRuntimeException(e);
        }
    }

    static volatile int counter = 0;

    @Override
    public byte[] receiveMessage(String queueName, String bindingKey, long timeout) throws Exception {
        Assert.notNull("Queue Name is Empty ", queueName);
        Assert.notNull("Binding Key is Empty ", bindingKey);
        this.rabbitAdmin.declareQueue(new Queue(queueName, true));
        this.channel.queueBind(queueName, broadCastExchange, bindingKey);
        //final QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        GetResponse getResponse = channel.basicGet(queueName, true);
        //String message = null;
        byte[] byteArray = null;
        if (getResponse != null) {
            byteArray = getResponse.getBody();
        }
        return byteArray;
    }

    @Override
    public String receiveFirstMessage(String queueName, String bindingKey,
                                      long timeout) throws Exception {
        Assert.notNull("Queue Name is Empty ", queueName);
        Assert.notNull("Binding Key is Empty ", bindingKey);
        this.rabbitAdmin.declareQueue(new Queue(queueName, true));
        this.channel.queueBind(queueName, broadCastExchange, bindingKey);
        final GetResponse getResponse = channel.basicGet(queueName, true);
        byte[] byteArray = null;
        if (getResponse != null) {
            byteArray = getResponse.getBody();
        }
        final String s = (byteArray != null ? new String(byteArray) : null);
        LOG.debug("Retrieved from queue: " + queueName + ", value: " + s);
        return s;
    }

    @Override
    public File receiveBinaryFile(String fileName, String queueName, String bindingKey) throws Exception {
        Assert.notNull("File Name is Empty ", fileName);
        final byte[] fileByte = this.receiveData(queueName, bindingKey);
        Assert.isTrue(fileByte.length != 0, "File Data is Empty");
        final File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        final FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(fileByte);
        fileOutputStream.close();
        return file;
    }

    @Override
    public byte[] receiveData(String queueName, String bindingKey) throws Exception {
        Assert.notNull("Queue Name is Empty ", queueName);
        Assert.notNull("Binding Key is Empty ", bindingKey);
        this.rabbitAdmin.declareQueue(new Queue(queueName, true));
        this.channel.queueBind(queueName, broadCastExchange, bindingKey);
        final GetResponse getResponse = channel.basicGet(queueName, true);
        byte[] byteArray = null;
        if (getResponse != null) {
            byteArray = getResponse.getBody();
        }
        return byteArray;
    }

    @Override
    public String[] receiveAllMessagesWithinTimePeriod(String queueName,
                                                       String bindingKey, long timeoutMillis, long timePeriodMillis)
            throws Exception {
        final List<String> list = new ArrayList<String>();
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        while (true) {
            if (stopWatch.getTotalTimeMillis() > timePeriodMillis) {
                break;
            }
            final String s = receiveFirstMessage(queueName, bindingKey, timeoutMillis);
            if (s != null) {
                list.add(s);
            } else {
                break;
            }
        }
        stopWatch.stop();
        return list.toArray(new String[list.size()]);
    }

    @Override
    public boolean clearQueue(final String queueName) throws Exception {
        final boolean result = this.rabbitAdmin.deleteQueue(queueName);
        LOG.info((result) ? "\t\tQueue Deletion Successful for lucas.broadCastQueue" : "\t\tQueue Deletion UnSuccessful for lucas.broadCastQueue");
        return result;
    }


    @PreDestroy
    public void close() throws Exception {
        this.channel.close();
        this.connection.close();
    }

}
