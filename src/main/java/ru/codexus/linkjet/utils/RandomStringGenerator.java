package ru.codexus.linkjet.utils;

import java.util.Random;

public final class RandomStringGenerator {

    private RandomStringGenerator() {
    }

    @SuppressWarnings("SpellCheckingInspection")
    public static String generate(int length) {
        String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        char[] randomString = new char[length];
        Random rnd = new Random();

        for (int i = 0; i < length; i++) {
            int index = (int) (rnd.nextFloat() * ALPHABET.length());
            randomString[i] = ALPHABET.charAt(index);
        }

        return new String(randomString);
    }
}
