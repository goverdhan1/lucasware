package com.lucas.services.messaging;


import java.util.Date;

import javax.inject.Inject;

import org.springframework.integration.Message;
import org.springframework.integration.annotation.Header;
import org.springframework.integration.annotation.Payload;
import org.springframework.integration.message.GenericMessage;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.lucas.services.util.StringUtils;

@Service
public class ProcessorServiceImpl implements ProcessorService {

    private static int count = 1;

    @Inject
    private LucasService lucasService;

    @Override
    public GenericMessage processRequest(@Payload Object payload
            , @Header("correlationId") String correlationId) throws Exception {
        final long startTime = new Date().getTime();
        if (payload != null) {
            final String data = lucasService.processService(200, new String((byte[]) payload));
            LOG.debug(count + " Payload Received in ProcessorService from Client :=> " + StringUtils.truncate(data, 200));
            final Message message = MessageBuilder.withPayload(count + " " + data.toString().toUpperCase()
                    + "Processing Service " + correlationId + "  " + new Date()).build();
            synchronized (LucasService.class) {
                count++;
            }
            final long endTime = new Date().getTime();
            LOG.info("Time Taken " + (endTime - startTime));
            return (GenericMessage) message;
        } else {
            LOG.info("Payload Received in ProcessorService from  Client is Null");
            return null;
        }
    }
}

