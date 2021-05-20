package io.kagboton.tacoscloud.repository;

import io.kagboton.tacoscloud.domain.Order;
import io.kagboton.tacoscloud.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
