package com.mercadolibre.challenge.utils;

import com.mercadolibre.challenge.dto.ValidationError;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

public class Utils {

    public static char[][] toMultArray(String[] dna) throws ValidationError {
        try {
            return Arrays.stream(dna).map(String::toCharArray).toArray(char[][]::new);
        } catch (Exception e) {
            throw new ValidationError("Error en el formato de entrada del campo 'dna'");
        }
    }

    public static void validateSize(String[] array, int minSize) throws ValidationError {
        if (array.length < minSize && Arrays.stream(array).anyMatch(it -> it.length() != array.length)) {
            throw new ValidationError("Error en el formato de entrada del campo 'dna', tamaño invalido");
        }
    }

    public static void validateValidChars(String[] dnaArray) throws ValidationError {
        Pattern pattern = Pattern.compile("^[ACTG]+$");

        if(Arrays.stream(dnaArray).anyMatch(Objects::isNull)) {
            throw new ValidationError("Formato invalido, no se permiten objetos nulos en la información del adn");
        }

        if(Arrays.stream(dnaArray).anyMatch(it -> !pattern.matcher(it).matches())) {
            throw new ValidationError("Caracteres invalidos en la información del adn, solo se permiten: [A, C, T, G]");
        }
    }

}
