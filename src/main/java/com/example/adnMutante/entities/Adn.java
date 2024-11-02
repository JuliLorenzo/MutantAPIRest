package com.example.adnMutante.entities;

import lombok.*;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ADN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class Adn implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ADN")
    private String adn;
    public String[] getAdnArray() {
        if (this.adn == null) {
            return new String[0];  // Devuelve un array vacío si adn es null
        }
        // Convierte el String a un array de String
        return adn.split(","); // Puedes usar un separador como coma
    }

    public void setAdnArray(String[] adnArray) {
        // Convierte el array a un String
        this.adn = String.join(",", adnArray); // Convierte el array a una cadena
    }
}
