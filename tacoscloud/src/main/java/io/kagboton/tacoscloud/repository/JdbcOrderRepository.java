package io.kagboton.tacoscloud.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.kagboton.tacoscloud.domain.Order;
import io.kagboton.tacoscloud.domain.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;


    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbc) {

        this.orderInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order")
                .usingGeneratedKeyColumns("id");

        this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order_Tacos");

        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order) {
        order.setPlacedAt(new Date());

        // save order into Taco_Order database
        long orderId = saveOrderDetails(order);
        order.setId(orderId);
        List<Taco> tacos = order.getTacos();

        // save taco and order into Taco_Order_Tacos database
        for (Taco taco : tacos){
            saveTacoToOrder(taco, orderId);
        }

        return order;
    }


    private long saveOrderDetails(Order order) {
        // use the objectMapper to convert the order object into a map
        @SuppressWarnings("unchecked")
        Map<String, Object> values = objectMapper.convertValue(order, Map.class);
        values.put("placedAt", order.getPlacedAt());

        // use orderInserter to save the order map to the database
        long orderId =
                orderInserter
                        .executeAndReturnKey(values)
                        .longValue();

        return orderId;
    }

    private void saveTacoToOrder(Taco taco, long orderId) {
        // build the values map
        Map<String, Object> values = new HashMap<>();
        values.put("taco", taco.getId());
        values.put("tacoOrder", orderId);

        // use orderTacoInserter to save taco and order ids to database
        orderTacoInserter.execute(values);
    }

}
