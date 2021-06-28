package io.kagboton.tacoscloud.kitchen.messaging.jms.receive.listener;

import io.kagboton.tacoscloud.domain.Order;
import io.kagboton.tacoscloud.kitchen.KitchenUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Profile("jms-listener")
@Component
public class OrderListener {
  
  private KitchenUI ui;

  @Autowired
  public OrderListener(KitchenUI ui) {
    this.ui = ui;
  }

  @JmsListener(destination = "tacocloud.order.queue")
  public void receiveOrder(Order order) {
    ui.displayOrder(order);
  }
  
}
