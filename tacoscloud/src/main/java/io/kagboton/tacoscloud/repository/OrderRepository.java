package io.kagboton.tacoscloud.repository;

import io.kagboton.tacoscloud.domain.Order;

public interface OrderRepository {

    Order save(Order order);

}
