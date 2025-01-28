package ru.codexus.linkjet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.codexus.linkjet.utils.RandomStringGenerator;

public class AppTest {

    @Test
    void getShortLink() {
        String randomString = RandomStringGenerator.generate(12);
        System.out.println(randomString);

        Assertions.assertEquals(12, randomString.length());
    }
}
