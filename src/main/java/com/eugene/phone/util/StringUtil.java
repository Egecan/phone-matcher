package com.eugene.phone.util;

import java.util.Locale;

public class StringUtil {

    public static String toUpperCase(String word) {
        return word.toUpperCase(Locale.US);
    }

    public static String stripPunctuation(String value) {

        return value.replaceAll("[\\W]|_", "");
    }
}
