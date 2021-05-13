package io.kagboton.tacoscloud.repository;

import io.kagboton.tacoscloud.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
