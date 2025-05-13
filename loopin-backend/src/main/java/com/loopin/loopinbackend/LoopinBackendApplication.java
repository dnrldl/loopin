package com.loopin.loopinbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LoopinBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoopinBackendApplication.class, args);
    }

}
