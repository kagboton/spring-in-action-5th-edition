package io.kagboton.tacoscloud.messaging;

import io.kagboton.tacoscloud.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsOrderMessagingService implements OrderMessagingService {

    private JmsTemplate jmsTemplate;

    @Autowired
    public JmsOrderMessagingService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void sendOrder(Order order){
        jmsTemplate.send( // send message to the default destination
                session -> session.createObjectMessage(order)
        );
    }
}
