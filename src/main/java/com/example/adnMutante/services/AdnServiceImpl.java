package com.example.adnMutante.services;

import com.example.adnMutante.entities.Adn;
import com.example.adnMutante.repositories.AdnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Arrays;

@Service
public class AdnServiceImpl implements AdnService<Adn> {
    @Autowired
    AdnRepository adnRepository;

    //Verifica que sea un Arreglo de N Strings cada uno de tamaño N
    public boolean sizeConditions(String[] dna, Integer size){
        boolean cumpleSize = Arrays.stream(dna)
                .allMatch(s -> s.length() == size);
        return cumpleSize;
    }

    //Armar matriz NxN
    public char[][] armarMatriz(String[] dna, Integer size){
        char[][] matriz = new char[size][];

        for (int i = 0; i < size; i++){
            matriz[i] = dna[i].toCharArray();
        }
        return matriz;
    }

    // Control de condiciones
    public boolean conditionControl(char[][] matriz) {
        return checkHorizontal(matriz) || checkVertical(matriz) || checkDiagonal(matriz) || checkDiagonalInversa(matriz);
    }

    public boolean checkHorizontal(char[][] matriz){
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length - 3; j ++){
                if(
                        matriz[i][j] == matriz[i][j+1] &&
                                matriz[i][j] == matriz[i][j+2] &&
                                matriz[i][j] == matriz[i][j+3])
                    return true;
            }
        }
        return false;
    }

    public boolean checkVertical (char[][] matriz){
        for (int j=0; j < matriz[0].length; j++){
            for (int i=0; i <= matriz.length -4; i++){
                if(
                        matriz[i][j] == matriz[i+1][j] &&
                                matriz[i][j] == matriz[i+2][j] &&
                                matriz[i][j] == matriz[i+3][j])
                {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkDiagonal(char[][] matriz){
        for (int i = 0; i <= matriz.length - 4; i++){
            for (int j = 0; j<= matriz[i].length - 4; j++){
                if(
                        matriz[i][j]==matriz[i+1][j+1] &&
                                matriz[i][j]==matriz[i+2][j+2] &&
                                matriz[i][j]==matriz[i+3][j+3]
                ){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkDiagonalInversa(char[][] matriz){
        for (int i = 0; i<= matriz.length - 4 ; i++ ){
            for (int j=3; j< matriz[i].length; j++){
                if(
                        matriz[i][j] == matriz[i+1][j-1] &&
                                matriz[i][j] == matriz[i+2][j-2] &&
                                matriz[i][j] == matriz[i+3][j-3]
                ){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isMutant(String[] adn){
        //String[] dna = dnaString.split(",");
        int N = adn.length;
        if (sizeConditions(adn, N)){
            char[][] matriz = armarMatriz(adn, N);
            return conditionControl(matriz);
        }
        return false;
    }

    public void saveMutant(Adn adnMutante){
        adnRepository.save(adnMutante);
    }

}
