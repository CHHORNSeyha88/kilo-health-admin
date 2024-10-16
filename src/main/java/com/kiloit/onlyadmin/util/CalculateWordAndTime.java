package com.kiloit.onlyadmin.util;

public class CalculateWordAndTime {
    private static final int AVERAGE_READING_SPEED_WPM = 250;

    public static int countWords(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        String[] words = text.split("\\s+");
        return words.length;
    }

    public static int calculateReadingTime(String text) {
        int wordCount = countWords(text);
        return (int) Math.ceil((double) wordCount / AVERAGE_READING_SPEED_WPM );
    }
}
