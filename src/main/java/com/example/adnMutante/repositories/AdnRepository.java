package com.example.adnMutante.repositories;

import com.example.adnMutante.entities.Adn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdnRepository extends JpaRepository<Adn, Long> {
}
