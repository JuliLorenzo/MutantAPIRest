package com.example.adnMutante.controllers;

import com.example.adnMutante.entities.Adn;
import com.example.adnMutante.services.AdnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/")

public class AdnController {
    @Autowired
    private AdnService adnService;

    @PostMapping("/mutante/")
    public ResponseEntity<Void> isMutant(@RequestBody Adn adn){
        if (adn == null || adn.getAdn() == null) {
            return ResponseEntity.badRequest().build();
        }

        if (adnService.isMutant(adn.getAdnArray())) {
            adnService.saveMutant(adn);
            // Si es mutante, devuelve HTTP 200 (OK)
            return ResponseEntity.ok().build();
        } else {
            // Si no es mutante, devuelve HTTP 403 (Forbidden)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/stats")
    public Map<String, Object> getStatistics() {
        return adnService.getStatistics();
    }

}
