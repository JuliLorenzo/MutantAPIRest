package com.example.adnMutante;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.adnMutante.repositories")
@EntityScan(basePackages = "com.example.adnMutante.entities")
public class AdnMutanteApplication {
	public static void main(String[] args) {
		SpringApplication.run(AdnMutanteApplication.class, args);
	}

}
