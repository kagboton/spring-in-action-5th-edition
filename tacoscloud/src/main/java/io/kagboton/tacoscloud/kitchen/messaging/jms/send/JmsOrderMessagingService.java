package io.kagboton.tacoscloud.messaging.jms;

import io.kagboton.tacoscloud.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;

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

    private Message addOrderSource(Message message) throws JMSException{
        message.setStringProperty("X_ORDER_SOURCE", "WEB");
        return message;
    }
}
