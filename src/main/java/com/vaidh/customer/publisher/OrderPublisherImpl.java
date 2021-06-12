package com.vaidh.customer.publisher;

import com.vaidh.customer.message.OrderEventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

public class OrderPublisherImpl implements OrderPublisher {

    Logger logger = LoggerFactory.getLogger(OrderPublisherImpl.class);


    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishCustomEvent(final String message) {
        logger.info("publishing event ", message);
        System.out.println("Publishing custom event. ");
        OrderEventMessage customSpringEvent = new OrderEventMessage(this, message);
        applicationEventPublisher.publishEvent(customSpringEvent);
    }
}
