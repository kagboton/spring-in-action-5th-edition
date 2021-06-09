package io.kagboton.tacoscloud.messaging.jms;

import io.kagboton.tacoscloud.domain.Order;

public interface OrderMessagingService {
    void sendOrder(Order order);
}
