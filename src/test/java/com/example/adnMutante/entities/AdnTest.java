package com.example.adnMutante.entities;

import com.example.adnMutante.services.AdnService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AdnTest {

    @Autowired
    private AdnService adnService; // Inyección del servicio

    @Test
    public void arrayVacio() {
        String[] emptyArray = {};
        boolean result = adnService.isMutant(emptyArray); // Llamar al método del servicio
        assertFalse(result, "El ADN vacío no debe ser mutante.");
    }

    @Test
    public void arrayDeNxM() {
        String[] nonSquareArray = { "AGTC", "GATC", "AT", "GCTA" };
        boolean result = adnService.isMutant(nonSquareArray);
        assertFalse(result, "El ADN de dimensiones NxM no debe ser mutante.");
    }

    @Test
    public void arrayConNumeros() {
        String[] arrayWithNumbers = { "1234", "AGTC", "GATC", "ATGC" };
        boolean result = adnService.isMutant(arrayWithNumbers);
        assertFalse(result, "El ADN que contiene números no debe ser mutante.");
    }

    @Test
    public void arrayNull() {
        String[] nullArray = null;
        boolean result = adnService.isMutant(nullArray);
        assertFalse(result, "El ADN nulo no debe ser mutante.");
    }

    @Test
    public void arrayDeNulls() {
        String[] arrayOfNulls = { null, null, null, null };
        boolean result = adnService.isMutant(arrayOfNulls);
        assertFalse(result, "El ADN que contiene solo nulls no debe ser mutante.");
    }

    @Test
    public void arrayConLetrasInvalidas() {
        String[] arrayWithInvalidChars = { "ABCD", "EFGH", "IJKL", "MNOP" };
        boolean result = adnService.isMutant(arrayWithInvalidChars);
        assertFalse(result, "El ADN que contiene letras no válidas no debe ser mutante.");
    }
}
