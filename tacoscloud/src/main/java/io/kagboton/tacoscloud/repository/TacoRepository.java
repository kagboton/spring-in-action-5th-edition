package io.kagboton.tacoscloud.repository;

import io.kagboton.tacoscloud.domain.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco, Long> {
}
