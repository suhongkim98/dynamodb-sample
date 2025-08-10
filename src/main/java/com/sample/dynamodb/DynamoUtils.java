package com.sample.dynamodb;

public class DynamoUtils {

    private static final String KEY_SPLITTER = "#";

    public static String join(final Object ...args) {
        StringBuilder key = new StringBuilder();
        for (var arg : args) {
            if (key.isEmpty()) {
                key.append(arg);
            } else {
                key.append(KEY_SPLITTER).append(arg);
            }
        }
        return key.toString();
    }
}
