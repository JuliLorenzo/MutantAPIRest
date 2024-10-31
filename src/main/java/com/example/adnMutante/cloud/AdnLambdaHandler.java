package com.example.adnMutante.cloud;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.adnMutante.entities.Adn;
import com.example.adnMutante.services.AdnService;
import org.springframework.beans.factory.annotation.Autowired;

public class AdnLambdaHandler implements RequestHandler<Adn, Void> {

    @Autowired
    private AdnService adnService;

    @Override
    public Void handleRequest(Adn input, Context context) {
        if (adnService.isMutant(input.getAdnArray())) {
            adnService.saveMutant(input);
            // Si es mutante, devuelve HTTP 200 (OK)
            return null; // Lambda no devuelve nada, pero puedes registrar la información
        } else {
            // Si no es mutante, puedes lanzar una excepción o manejarlo de otra manera
            throw new RuntimeException("Forbidden"); // Esto hará que devuelva HTTP 403
        }
    }
}