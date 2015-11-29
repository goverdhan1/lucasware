package com.lucas.services.messaging;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

@Service
public class LucasRpcClientService {

    private static Logger LOG = LoggerFactory.getLogger(LucasRpcClientService.class);

    @Inject
    @Qualifier(value = "clientConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Value("${lucas.mq.request.exchange}")
    private String exchangeName;

    @Value("${lucas.mq.request.binding}")
    private String routeKey;

    @Value("${lucas.mq.response.queue}")
    private String replyQueue;

    @Value("${lucas.mq.request.queue}")
    private String requestQueue;

    @Value("${lucas.mq.purge.queue}")
    private String purgeQueue;

    @Value("${lucas.mq.rpc.client.timeout}")
    private int rpcClientTimeOut;

    @PostConstruct
    public void init() {
        Assert.notNull(connectionFactory, "ConnectionFactory is Null in LucasRpcClientService");
        Assert.notNull(exchangeName, "ExchangeName is Null in LucasRpcClientService");
        Assert.notNull(routeKey, "RouteKey is Null in LucasRpcClientService");
        Assert.notNull(replyQueue, "ReplyQueue is Null in LucasRpcClientService");
        Assert.notNull(requestQueue, "RequestQueue is Null in LucasRpcClientService");
        Assert.notNull(rpcClientTimeOut, "RpcClientTimeOut is Null in LucasRpcClientService");
        Assert.notNull(purgeQueue, "Purge Queue Flag is Null");
    }

    private LucasRpcClient lucasRpcClient = null;

    public final LucasRpcClient getInstance(boolean newInstance) throws Exception {
        if (!newInstance && lucasRpcClient != null) {
            return lucasRpcClient;
        } else {
            final Connection connection = this.connectionFactory.newConnection();
            final Channel channel = connection.createChannel();
            if (Boolean.parseBoolean(this.purgeQueue.trim())) {
                AMQP.Queue.PurgeOk requestQueuePurgeOk = channel.queuePurge(this.requestQueue);
                AMQP.Queue.PurgeOk replyQueuePurgeOk = channel.queuePurge(this.replyQueue);
                LOG.info("Request Queue Purged Number of Msg Purged " + requestQueuePurgeOk.getMessageCount());
                LOG.info("Response Queue Purged Number of Msg Purged " + replyQueuePurgeOk.getMessageCount());
            }
            lucasRpcClient = LucasRpcClient.getRpcInstance(this.replyQueue
                    , channel, this.exchangeName, this.routeKey, this.rpcClientTimeOut);
            return lucasRpcClient;
        }
    }

    public final LucasRpcClient getInstance(String replyQueue) throws Exception {
        Assert.notNull("Reply Queue is Empty ", replyQueue);
        final Connection connection = this.connectionFactory.newConnection();
        final Channel channel = connection.createChannel();
        if (Boolean.parseBoolean(this.purgeQueue.trim())) {
            AMQP.Queue.PurgeOk requestQueuePurgeOk = channel.queuePurge(this.requestQueue);
            AMQP.Queue.PurgeOk replyQueuePurgeOk = channel.queuePurge(replyQueue);
            LOG.info("Request Queue Purged Number of Msg Purged " + requestQueuePurgeOk.getMessageCount());
            LOG.info("Response Queue Purged Number of Msg Purged " + replyQueuePurgeOk.getMessageCount());
        }
        lucasRpcClient = LucasRpcClient.getRpcInstance(this.replyQueue
                , channel, this.exchangeName, routeKey, this.rpcClientTimeOut);
        return lucasRpcClient;
    }


    /**
     * This class is needed because the <code>replyQueue</code> needs to be explicitly set to a
     * preconfigured value. This was not the case with the
     * out-of-the-box RPCClient where a new queue was created with every call.
     *
     * @author Adarsh
     */
    public static class LucasRpcClient extends RpcClient {

        private static String replyQueue;

        public final static LucasRpcClient getRpcInstance(String replyQueue, Channel channel
                , String exchange, String routingKey, int timeout) throws IOException {
            LucasRpcClient.replyQueue = replyQueue;
            return new LucasRpcClient(channel, exchange, routingKey, timeout);
        }

        public final static LucasRpcClient getRpcInstance(String replyQueue, Channel channel
                , String exchange, String routingKey) throws IOException {
            LucasRpcClient.replyQueue = replyQueue;
            return new LucasRpcClient(channel, exchange, routingKey);
        }

        private LucasRpcClient(Channel channel, String exchange, String routingKey
                , int timeout) throws IOException {
            super(channel, exchange, routingKey, timeout);
        }

        private LucasRpcClient(Channel channel, String exchange, String routingKey)
                throws IOException {
            super(channel, exchange, routingKey);
        }

        @Override
        protected String setupReplyQueue() throws IOException {
            return this.replyQueue;
        }

        public byte[] primitiveCall(final String correlationId, byte[] message)
                throws IOException, ShutdownSignalException, TimeoutException {
            final AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder()
                    .headers(new HashMap<String, Object>() {
                        {
                            put("correlationId", correlationId);
                        }
                    }).correlationId(correlationId)
                    .replyTo(replyQueue)
                    .messageId(correlationId)
                    .timestamp(new Date());
            return super.primitiveCall(builder.build(), message);
        }
    }
}