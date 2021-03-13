package com.mercadolibre.challenge.utils;

import com.mercadolibre.challenge.dto.ValidationError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class UtilsTest {

    @Test
    public void toMultArrayTest() throws Exception {
        String[] array = {"ABCD", "EFGH"};

        char[][] result = Utils.toMultArray(array);

        assertEquals(2, result.length);
        assertEquals(4, result[0].length);
        assertEquals('G', result[1][2]);
    }

    @Test(expected = ValidationError.class)
    public void toMultArrayFailTest() throws ValidationError {
        String[] array = {"ABCD", null};
        Utils.toMultArray(array);
    }

    @Test
    public void validateSizeTest() throws ValidationError {
        String[] array = {"ABCD", "EFGH", "IJKL", "MNOP"};
        try {
            Utils.validateSize(array, 4);
            assertTrue(true);
        } catch (ValidationError e) {
            fail();
        }
    }

    @Test(expected = ValidationError.class)
    public void validateSizeTestFail() throws ValidationError {
        String[] array = {"ABCD", "EFGH"};
        Utils.validateSize(array, 4);
    }

    @Test
    public void validateValidCharsTest() throws ValidationError {
        String[] array = {"ACTG", "ACAA", "CCGG", "TTTT"};
        try {
            Utils.validateValidChars(array);
            assertTrue(true);
        } catch (ValidationError e) {
            fail();
        }
    }

    @Test(expected = ValidationError.class)
    public void validateValidCharsTestFail() throws ValidationError {
        String[] array = {"ABCD", "EFGH"};
        Utils.validateValidChars(array);
    }

}
