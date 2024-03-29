package com.br.teste.attus;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Testee Tech Attus",version = "1",description = "Teste para Aprovação de candidatura para Vaga na Attus"))
public class TesteTechBackendAttusApplication {

    public static void main(String[] args) {
        SpringApplication.run(TesteTechBackendAttusApplication.class, args);
    }

}
