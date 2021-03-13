package com.mercadolibre.challenge.repositories;

import com.mercadolibre.challenge.entities.DnaHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DnaHistoryRepository extends JpaRepository<DnaHistory, Long> {

    DnaHistory findByDna(String dna);

    @Query("SELECT count(ah.id) FROM DnaHistory ah WHERE ah.isMutant = ?1")
    Double getCountByState(Boolean isMutant);

}
