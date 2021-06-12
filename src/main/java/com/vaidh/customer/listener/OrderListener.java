package com.vaidh.customer.listener;

import com.vaidh.customer.message.OrderEventMessage;
import org.springframework.context.ApplicationListener;

public interface OrderListener extends ApplicationListener<OrderEventMessage> {
}
