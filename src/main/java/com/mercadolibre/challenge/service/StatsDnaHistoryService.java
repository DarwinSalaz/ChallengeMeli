package com.mercadolibre.challenge.service;

import com.mercadolibre.challenge.dto.StatsResult;
import com.mercadolibre.challenge.repositories.DnaHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class StatsDnaHistoryService {

    @Autowired
    DnaHistoryRepository repository;

    public StatsResult getStatsDnaValidationHistory() {
        Double countMutantDna = repository.getCountByState(true);
        Double countHumanDna = repository.getCountByState(false);
        BigDecimal ratio = countHumanDna == 0 ? null :
                new BigDecimal(countMutantDna / countHumanDna).setScale(2, RoundingMode.HALF_UP);

        return new StatsResult(countMutantDna.intValue(), countHumanDna.intValue(), ratio);
    }

}
