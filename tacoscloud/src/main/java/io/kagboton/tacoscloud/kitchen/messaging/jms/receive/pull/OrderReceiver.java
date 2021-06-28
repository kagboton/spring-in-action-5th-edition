package io.kagboton.tacoscloud.kitchen.messaging.jms.receive.pull;

import io.kagboton.tacoscloud.domain.Order;

public interface OrderReceiver {

  Order receiveOrder();

}