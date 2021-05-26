package io.kagboton.tacoscloud.repository;

import io.kagboton.tacoscloud.domain.Taco;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TacoRepository extends PagingAndSortingRepository<Taco, Long> {

}
