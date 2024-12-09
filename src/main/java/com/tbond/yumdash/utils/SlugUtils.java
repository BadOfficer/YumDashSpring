package com.tbond.yumdash.utils;

import java.text.Normalizer;
import java.util.Locale;

public class SlugUtils {
    public static String generateSlug(String title) {
        String normalized = Normalizer.normalize(title, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]+", "")
                .toLowerCase(Locale.ROOT);

        return normalized.replaceAll("[^a-z0-9\\s-]", "")
                .trim()
                .replaceAll("\\s+", "-");
    }
}
