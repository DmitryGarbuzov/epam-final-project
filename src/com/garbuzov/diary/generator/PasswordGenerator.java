package com.garbuzov.diary.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PasswordGenerator {

    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static List<String> charCategories = new ArrayList<>();
    private static PasswordGenerator instance;

    private PasswordGenerator() {
        charCategories.add(LOWER);
        charCategories.add(UPPER);
        charCategories.add(DIGITS);
    }

    public static PasswordGenerator getInstance() {
        if (instance == null) {
            instance = new PasswordGenerator();
        }
        return instance;
    }

    public String generate(int length) {
        StringBuilder password = new StringBuilder(length);
        Random random = new Random(System.nanoTime());

        for (int i = 0; i < length; i++) {
            String charCategory = charCategories.get(random.nextInt(charCategories.size()));
            int position = random.nextInt(charCategory.length());
            password.append(charCategory.charAt(position));
        }

        return password.toString();
    }
}
