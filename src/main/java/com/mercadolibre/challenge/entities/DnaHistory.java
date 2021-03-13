package com.mercadolibre.challenge.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "dna_history")
@NoArgsConstructor
@AllArgsConstructor
public class DnaHistory {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;

    @NotNull
    @Column(name = "dna")
    private String dna;

    @NotNull
    @Column(name = "is_mutant")
    private Boolean isMutant;

    public DnaHistory(String dna, Boolean isMutant) {
        this.dna = dna;
        this.isMutant = isMutant;
    }
}
