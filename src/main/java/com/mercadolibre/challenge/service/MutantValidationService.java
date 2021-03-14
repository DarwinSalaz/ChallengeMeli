package com.mercadolibre.challenge.service;

import com.mercadolibre.challenge.dto.ValidationError;
import com.mercadolibre.challenge.entities.DnaHistory;
import com.mercadolibre.challenge.repositories.DnaHistoryRepository;
import com.mercadolibre.challenge.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MutantValidationService {

    private static final int sizeSequence = 4;
    private static final int countSequenceToMutant = 2;
    private static final List<String> orientations = Arrays.asList("H", "OR", "V", "OL");

    @Autowired
    DnaHistoryRepository repository;

    public Boolean isMutant (String[] dna) throws ValidationError {
        DnaHistory dnaHistory = findDnaHistory(dna);
        // Si ya existe un registro del adn obtenemos directamente si es clasificado como mutante desde la bd
        if (dnaHistory != null) {
            return dnaHistory.getIsMutant();
        }

        Boolean isMutant = validateDnaMutant(dna);
        saveDnaHistory(dna, isMutant);

        return isMutant;
    }

    public Boolean validateDnaMutant(String[] dna) throws ValidationError {
        int cantSequences = 0;

        // Se transforma la información en un array multidimencional
        char[][] matrixDNA = Utils.toMultArray(dna);

        // Recorrer cada elemento en la matriz, se valida si es el inicio de una secuencia
        for (int i = 0; i < dna.length; i++) {
            for (int j = 0; j < dna.length; j++) {
                int row = i;
                int column = j;
                // Obtener para el elemento actual las POSIBLES orientaciones...
                // que puedan ser secuencia (Horizontal, Vertical, Oblicuas)
                List<String> possibleSequence = getPossibleSequence(row, column, matrixDNA);
                // Validar con el resultado anterior si realmente existe una secuencia en cada orientación
                List<String> sequenceOrientations = possibleSequence.stream()
                        .filter(ps -> validateSequence(ps, row, column, matrixDNA))
                        .collect(Collectors.toList());
                // Sumar al resultado las secuencias encontradas
                cantSequences += sequenceOrientations.size();
                // Si hay más de una secuencia no es necesario evaluar más
                if (cantSequences >= countSequenceToMutant) return true;
            }
        }

        return false;
    }

    public void validateStructure(String[] dna) throws ValidationError {
        Utils.validateValidChars(dna);
        Utils.validateSize(dna, sizeSequence);
    }

    private List<String> getPossibleSequence(int row, int column, char[][] matrixDNA) {
        return orientations.stream()
                .filter(o -> validatePossibleSeq(o, row, column, matrixDNA))
                .collect(Collectors.toList());
    }

    // Valida en la dirección si hay posibilidad de una secuencia
    // V -> Vertical * H -> Horizontal * OL -> Oblicuo Izquierdo * OR -> Oblicuo Derecho
    private boolean validatePossibleSeq (String orientation, int row, int column, char[][] matrixDNA) {
        char charComp = matrixDNA[row][column];
        char c;
        try {
            switch (orientation) {
                case "H":
                    c = matrixDNA[row][column + sizeSequence - 1];
                    return charComp == c;
                case "OR":
                    c = matrixDNA[row + sizeSequence - 1][column + sizeSequence - 1];
                    return charComp == c;
                case "V":
                    c = matrixDNA[row + sizeSequence - 1][column];
                    return charComp == c;
                case "OL":
                    c = matrixDNA[row + sizeSequence - 1][column - sizeSequence + 1];
                    return charComp == c;
                default:
                    return false;
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean validateSequence (String orientation, int row, int column, char[][] matrixDNA) {
        char charComp = matrixDNA[row][column];
        switch (orientation) {
            case "H":
                return charComp == matrixDNA[row][column + 1] && charComp == matrixDNA[row][column + 2];
            case "OR":
                return charComp == matrixDNA[row + 1][column + 1] && charComp == matrixDNA[row + 2][column + 2];
            case "V":
                return charComp == matrixDNA[row + 1][column] && charComp == matrixDNA[row + 2][column];
            case "OL":
                return charComp == matrixDNA[row + 1][column - 1] && charComp == matrixDNA[row + 2][column - 2];
            default:
                return false;
        }
    }

    public DnaHistory findDnaHistory(String[] dna) {
        String dnaKey = String.join("-", dna);

        return repository.findByDna(dnaKey);
    }

    public void saveDnaHistory(String[] dna, Boolean isMutant) {
        String dnaKey = String.join("-", dna);

        repository.save(new DnaHistory(dnaKey, isMutant));
    }

}
