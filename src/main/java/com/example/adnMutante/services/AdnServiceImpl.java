package com.example.adnMutante.services;

import com.example.adnMutante.entities.Adn;
import com.example.adnMutante.repositories.AdnRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import java.io.Serializable;
import java.util.Arrays;

@Service
public class AdnServiceImpl implements AdnService<Adn> {
    @Autowired
    AdnRepository adnRepository;

    private int countMutantAdn = 0; // Contador de ADN mutante
    private int countHumanAdn = 0; // Contador de ADN humano

    @Override
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("count_mutant_adn", countMutantAdn);
        stats.put("count_human_adn", countHumanAdn);
        double ratio = (countHumanAdn == 0) ? 0 : (double) countMutantAdn / countHumanAdn;
        stats.put("ratio", ratio);
        return stats;
    }

    // Método para incrementar los contadores, llamado cuando se verifica ADN
    public void incrementMutantCount() {
        countMutantAdn++;
    }

    public void incrementHumanCount() {
        countHumanAdn++;
    }

    //Verifica que el Arreglo adn no sea null
    public boolean adnNull(String[] adn){
        if (adn == null){
            return true;
        }
        return false;
    }

    //Verifica que el Arreglo no este vacio y no contenga elementos null
    public boolean adnNullControl(String[] adn){
        if (adn.length == 0 || Arrays.stream(adn).anyMatch(Objects::isNull)) {
            return true;
        }
        return false;
    }

    //Verifica que sea un Arreglo de N Strings cada uno de tamaño N
    public boolean sizeConditions(String[] adn, Integer size){
        boolean cumpleSize = Arrays.stream(adn)
                .allMatch(s -> s.length() == size);
        return cumpleSize;
    }

    //Verifica que cada elemento del Arreglo sea alguna de las letras permitidas: 'A','G','C','T'
    public boolean contentCondition(String[] adn) {
        String regex = "^[AGCT]+$";
        return Arrays.stream(adn)
                .allMatch(s -> s.matches(regex));
    }

    //Armar matriz NxN
    public char[][] armarMatriz(String[] adn, Integer size){
        char[][] matriz = new char[size][];

        for (int i = 0; i < size; i++){
            matriz[i] = adn[i].toCharArray();
        }
        return matriz;
    }
    public boolean checkHorizontal(char[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j <= matriz[i].length - 4; j++) {
                if (matriz[i][j] == matriz[i][j + 1] &&
                        matriz[i][j] == matriz[i][j + 2] &&
                        matriz[i][j] == matriz[i][j + 3]) {

                    // Comprobar que no haya más letras iguales
                    boolean hasExtra = false;
                    if (j > 0 && matriz[i][j] == matriz[i][j - 1]) {
                        hasExtra = true;
                    }
                    if (j + 4 < matriz[i].length && matriz[i][j] == matriz[i][j + 4]) {
                        hasExtra = true;
                    }

                    if (!hasExtra) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkVertical(char[][] matriz) {
        for (int j = 0; j < matriz[0].length; j++) {
            for (int i = 0; i <= matriz.length - 4; i++) {
                if (matriz[i][j] == matriz[i + 1][j] &&
                        matriz[i][j] == matriz[i + 2][j] &&
                        matriz[i][j] == matriz[i + 3][j]) {

                    boolean hasExtra = false;
                    if (i > 0 && matriz[i][j] == matriz[i - 1][j]) {
                        hasExtra = true;
                    }
                    if (i + 4 < matriz.length && matriz[i][j] == matriz[i + 4][j]) {
                        hasExtra = true;
                    }

                    if (!hasExtra) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkDiagonal(char[][] matriz) {
        for (int i = 0; i <= matriz.length - 4; i++) {
            for (int j = 0; j <= matriz[i].length - 4; j++) {
                if (matriz[i][j] == matriz[i + 1][j + 1] &&
                        matriz[i][j] == matriz[i + 2][j + 2] &&
                        matriz[i][j] == matriz[i + 3][j + 3]) {

                    boolean hasExtra = false;
                    if (i > 0 && j > 0 && matriz[i][j] == matriz[i - 1][j - 1]) {
                        hasExtra = true;
                    }
                    if (i + 4 < matriz.length && j + 4 < matriz[i].length && matriz[i][j] == matriz[i + 4][j + 4]) {
                        hasExtra = true;
                    }

                    if (!hasExtra) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkDiagonalInversa(char[][] matriz) {
        for (int i = 0; i <= matriz.length - 4; i++) {
            for (int j = 3; j < matriz[i].length; j++) {
                if (matriz[i][j] == matriz[i + 1][j - 1] &&
                        matriz[i][j] == matriz[i + 2][j - 2] &&
                        matriz[i][j] == matriz[i + 3][j - 3]) {

                    boolean hasExtra = false;
                    if (i > 0 && j < matriz[i].length - 1 && matriz[i][j] == matriz[i - 1][j + 1]) {
                        hasExtra = true;
                    }
                    if (i + 4 < matriz.length && j - 4 >= 0 && matriz[i][j] == matriz[i + 4][j - 4]) {
                        hasExtra = true;
                    }

                    if (!hasExtra) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Control de condiciones
    public boolean conditionControl(char[][] matriz) {
        return checkHorizontal(matriz) || checkVertical(matriz) || checkDiagonal(matriz) || checkDiagonalInversa(matriz);
    }

    @Override
    public boolean isMutant(String[] adn) {
        if (adnNull(adn) || adnNullControl(adn)) {
            incrementHumanCount(); // Incrementar humano si el ADN es nulo o no válido
            return false;
        }

        int N = adn.length;
        if (sizeConditions(adn, N) && contentCondition(adn)) {
            char[][] matriz = armarMatriz(adn, N);
            boolean isMutant = conditionControl(matriz); // Verificar si es mutante

            // Incrementar contadores según el resultado
            if (isMutant) {
                incrementMutantCount(); // Incrementar mutante si es verdadero
            } else {
                incrementHumanCount(); // Incrementar humano si es falso
            }

            return isMutant; // Retornar el resultado de la verificación
        }

        incrementHumanCount(); // Incrementar humano si no cumple las condiciones
        return false; // Retornar false si no es mutante
    }


    @Transactional
    public void saveMutant(Adn adnMutante){
        adnRepository.save(adnMutante);
    }
}
