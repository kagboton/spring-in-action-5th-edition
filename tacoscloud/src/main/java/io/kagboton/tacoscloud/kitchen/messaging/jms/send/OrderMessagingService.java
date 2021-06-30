package io.kagboton.tacoscloud.kitchen.messaging.jms.send;

import io.kagboton.tacoscloud.domain.Order;

public interface OrderMessagingService {
    void sendOrder(Order order);
}
