package io.kagboton.tacoscloud.repository;

import io.kagboton.tacoscloud.domain.Order;
import io.kagboton.tacoscloud.domain.User;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
