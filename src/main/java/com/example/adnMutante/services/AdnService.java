package com.example.adnMutante.services;

import com.example.adnMutante.entities.Adn;

import java.io.Serializable;

public interface AdnService<A>{
    public boolean isMutant(String[] adn);
    public void saveMutant(Adn adn);

}
