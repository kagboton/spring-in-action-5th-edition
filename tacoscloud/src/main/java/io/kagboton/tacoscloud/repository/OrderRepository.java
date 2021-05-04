package io.kagboton.tacoscloud.repository;

import io.kagboton.tacoscloud.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
