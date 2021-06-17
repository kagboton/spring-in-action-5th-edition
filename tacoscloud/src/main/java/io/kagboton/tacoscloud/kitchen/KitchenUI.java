package io.kagboton.tacoscloud.kitchen;

import io.kagboton.tacoscloud.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KitchenUI {

    public void displayOrder(Order order) {
        // TODO: Beef this up to do more than just log the received taco.
        //       To display it in some sort of UI.
        log.info("RECEIVED ORDER:  " + order);
    }

}
