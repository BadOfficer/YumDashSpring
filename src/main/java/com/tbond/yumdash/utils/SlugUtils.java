package com.tbond.yumdash.utils;

import java.text.Normalizer;
import java.util.Locale;

public class SlugUtils {
    public static String generateSlug(String title, String category) {

        String normalizedCategory = Normalizer.normalize(category, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]+", "")
                .toLowerCase(Locale.ROOT);

        String normalizedTitle = Normalizer.normalize(title, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]+", "")
                .toLowerCase(Locale.ROOT);


        String combinedSlug = normalizedCategory + "-" + normalizedTitle;

       
        return combinedSlug.replaceAll("[^a-z0-9\\s-]", "")
                .trim()
                .replaceAll("\\s+", "-");
    }
}
