package com.lucas.services.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Header;
import org.springframework.integration.annotation.Payload;
import org.springframework.integration.message.GenericMessage;

public interface ProcessorService {

    public static Logger LOG = LoggerFactory.getLogger(ProcessorService.class);

    public GenericMessage processRequest(@Payload Object payload, @Header String correlationId) throws Exception;
}
