package com.loopin.loopinbackend.domain.test;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestInit implements CommandLineRunner {
    private final TestDataHelper testDataHelper;

    @Override
    public void run(String... args) throws Exception {
        testDataHelper.createTestPostWithNewUser("test", "test content");
    }
}
