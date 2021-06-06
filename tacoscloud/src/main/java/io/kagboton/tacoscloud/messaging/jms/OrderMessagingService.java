package io.kagboton.tacoscloud.messaging;

import io.kagboton.tacoscloud.domain.Order;

public interface OrderMessagingService {
    void sendOrder(Order order);
}
