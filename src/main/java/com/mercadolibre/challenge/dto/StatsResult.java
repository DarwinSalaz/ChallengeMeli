package com.mercadolibre.challenge.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@JsonIgnoreProperties
public class StatsResult {

    @JsonProperty("count_mutant_dna")
    Integer countMutantDna;

    @JsonProperty("count_human_dna")
    Integer countHumanDna;

    @JsonProperty("ratio")
    BigDecimal ratio;

}
