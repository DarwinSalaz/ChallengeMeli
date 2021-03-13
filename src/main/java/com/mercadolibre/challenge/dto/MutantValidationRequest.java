package com.mercadolibre.challenge.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class MutantValidationRequest {

    @NotNull(message = "dna is not null")
    @NotEmpty(message = "dna is not empty")
    private String[] dna;

    public String[] getDna() {
        return dna;
    }

}
