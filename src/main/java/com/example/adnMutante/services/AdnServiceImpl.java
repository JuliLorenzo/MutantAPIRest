package com.example.adnMutante.services;

import com.example.adnMutante.entities.Adn;
import com.example.adnMutante.repositories.AdnRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Objects;

import java.io.Serializable;
import java.util.Arrays;

@Service
public class AdnServiceImpl implements AdnService<Adn> {
    @Autowired
    AdnRepository adnRepository;

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
    public boolean sizeConditions(String[] dna, Integer size){
        boolean cumpleSize = Arrays.stream(dna)
                .allMatch(s -> s.length() == size);
        return cumpleSize;
    }

    //Verifica que cada elemento del Arreglo sea alguna de las letras permitidas: 'A','G','C','T'
    public boolean contentCondition(String[] adn) {
        // Define un patrón de regex que solo acepta caracteres A, G, C o T.
        String regex = "^[AGCT]+$";
        return Arrays.stream(adn)
                .allMatch(s -> s.matches(regex));
    }

    //Armar matriz NxN
    public char[][] armarMatriz(String[] dna, Integer size){
        char[][] matriz = new char[size][];

        for (int i = 0; i < size; i++){
            matriz[i] = dna[i].toCharArray();
        }
        return matriz;
    }
    public boolean checkHorizontal(char[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j <= matriz[i].length - 4; j++) { // Evitar desbordamiento
                if (matriz[i][j] == matriz[i][j + 1] &&
                        matriz[i][j] == matriz[i][j + 2] &&
                        matriz[i][j] == matriz[i][j + 3]) {

                    // Comprobar que no haya más letras iguales en ambos lados
                    boolean hasExtra = false;
                    if (j > 0 && matriz[i][j] == matriz[i][j - 1]) { // Chequear a la izquierda
                        hasExtra = true;
                    }
                    if (j + 4 < matriz[i].length && matriz[i][j] == matriz[i][j + 4]) { // Chequear a la derecha
                        hasExtra = true;
                    }

                    if (!hasExtra) {
                        return true; // Solo si tiene exactamente 4 iguales
                    }
                }
            }
        }
        return false;
    }

    public boolean checkVertical(char[][] matriz) {
        for (int j = 0; j < matriz[0].length; j++) {
            for (int i = 0; i <= matriz.length - 4; i++) { // Evitar desbordamiento
                if (matriz[i][j] == matriz[i + 1][j] &&
                        matriz[i][j] == matriz[i + 2][j] &&
                        matriz[i][j] == matriz[i + 3][j]) {

                    // Comprobar que no haya más letras iguales en ambos lados
                    boolean hasExtra = false;
                    if (i > 0 && matriz[i][j] == matriz[i - 1][j]) { // Chequear arriba
                        hasExtra = true;
                    }
                    if (i + 4 < matriz.length && matriz[i][j] == matriz[i + 4][j]) { // Chequear abajo
                        hasExtra = true;
                    }

                    if (!hasExtra) {
                        return true; // Solo si tiene exactamente 4 iguales
                    }
                }
            }
        }
        return false;
    }

    public boolean checkDiagonal(char[][] matriz) {
        for (int i = 0; i <= matriz.length - 4; i++) {
            for (int j = 0; j <= matriz[i].length - 4; j++) { // Evitar desbordamiento
                if (matriz[i][j] == matriz[i + 1][j + 1] &&
                        matriz[i][j] == matriz[i + 2][j + 2] &&
                        matriz[i][j] == matriz[i + 3][j + 3]) {

                    // Comprobar que no haya más letras iguales en ambos lados
                    boolean hasExtra = false;
                    if (i > 0 && j > 0 && matriz[i][j] == matriz[i - 1][j - 1]) { // Chequear arriba izquierda
                        hasExtra = true;
                    }
                    if (i + 4 < matriz.length && j + 4 < matriz[i].length && matriz[i][j] == matriz[i + 4][j + 4]) { // Chequear abajo derecha
                        hasExtra = true;
                    }

                    if (!hasExtra) {
                        return true; // Solo si tiene exactamente 4 iguales
                    }
                }
            }
        }
        return false;
    }

    public boolean checkDiagonalInversa(char[][] matriz) {
        for (int i = 0; i <= matriz.length - 4; i++) {
            for (int j = 3; j < matriz[i].length; j++) { // Evitar desbordamiento
                if (matriz[i][j] == matriz[i + 1][j - 1] &&
                        matriz[i][j] == matriz[i + 2][j - 2] &&
                        matriz[i][j] == matriz[i + 3][j - 3]) {

                    // Comprobar que no haya más letras iguales en ambos lados
                    boolean hasExtra = false;
                    if (i > 0 && j < matriz[i].length - 1 && matriz[i][j] == matriz[i - 1][j + 1]) { // Chequear arriba derecha
                        hasExtra = true;
                    }
                    if (i + 4 < matriz.length && j - 4 >= 0 && matriz[i][j] == matriz[i + 4][j - 4]) { // Chequear abajo izquierda
                        hasExtra = true;
                    }

                    if (!hasExtra) {
                        return true; // Solo si tiene exactamente 4 iguales
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

    public boolean isMutant(String[] adn){
        //String[] dna = dnaString.split(",");
        if (adnNull(adn) || adnNullControl(adn)){
            return false;
        }

        int N = adn.length;
        if (sizeConditions(adn, N) && contentCondition(adn)){
            char[][] matriz = armarMatriz(adn, N);
            return conditionControl(matriz);
        }
        return false;
    }

    @Transactional
    public void saveMutant(Adn adnMutante){
        adnRepository.save(adnMutante);
    }
}
