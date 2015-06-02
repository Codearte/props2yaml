package io.codearte.p2yaml;

public class ValueConverter {

    public static Object asObject(String string) {
        if (string.equalsIgnoreCase("true") || string.equalsIgnoreCase("false")) {
            return Boolean.valueOf(string);
        } else {

            try {
                return Integer.parseInt(string);
            } catch (NumberFormatException e) {

            }
            try {
                return Long.parseLong(string);
            } catch (NumberFormatException e) {

            }
            try {
                return Double.parseDouble(string);
            } catch (NumberFormatException e) {

            }

            return string;
        }
    }
}
