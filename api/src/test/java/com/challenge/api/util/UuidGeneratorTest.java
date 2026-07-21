package com.challenge.api.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class UuidGeneratorTest {

    @Test
    void generate_returnsUuid() {
        UUID uuid = UuidGenerator.generate();

        assertNotNull(uuid);
    }
}
