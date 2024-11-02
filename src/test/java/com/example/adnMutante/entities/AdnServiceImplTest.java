package com.example.adnMutante.entities;

import com.example.adnMutante.services.AdnServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdnServiceImplTest {

    private AdnServiceImpl adnService;

    @BeforeEach
    public void setUp() {
        adnService = new AdnServiceImpl();
    }

    @Test
    public void testArrayVacio() {
        String[] adn = {};
        assertFalse(adnService.isMutant(adn), "El ADN no debe ser considerado mutante");
    }

    @Test
    public void testArrayDeNxM() {
        String[] adn = {"AAGG", "TTCC", "GGAA", "CC"}; // Ejemplo de NxM
        assertFalse(adnService.isMutant(adn), "El ADN no debe ser considerado mutante");
    }

    @Test
    public void testArrayConNumeros() {
        String[] adn = {"A1C2", "TTCC", "GGAA", "CCAA"}; // Incluye números
        assertFalse(adnService.isMutant(adn), "El ADN no debe ser considerado mutante");
    }

    @Test
    public void testArrayNull() {
        String[] adn = null;
        assertFalse(adnService.isMutant(adn), "El ADN no debe ser considerado mutante");
    }

    @Test
    public void testArrayDeNxNDeNulls() {
        String[] adn = {null, null, null}; // Array de nulls
        assertFalse(adnService.isMutant(adn), "El ADN no debe ser considerado mutante");
    }

    @Test
    public void testArrayConLetrasInvalidas() {
        String[] adn = {"ABCD", "HHHH", "AAAA", "CCCC"}; // Incluye letras no válidas
        assertFalse(adnService.isMutant(adn), "El ADN no debe ser considerado mutante");
    }
}
