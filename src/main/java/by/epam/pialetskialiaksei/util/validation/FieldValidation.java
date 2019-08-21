package by.epam.pialetskialiaksei.util.validation;

import java.util.regex.Pattern;

public class FieldValidation {

    private static final String positiveDecimalNumberRegEx = "\\d+";
//    private static final String filledRegex = "^\\p{L}[\\p{L}\\s]*";
    private static final String filledRegex = ".+";
    private static final String isLatinWord = "[a-zA-Z ]+";
    private static final String isCyrillicWord = "[а-яА-Я ]+";
    private static final String hasScript = "<script>.*</script>";

    private static <T> boolean checkNull(
            @SuppressWarnings("unchecked") T... values) {
        if (values == null) {
            return true;
        } else {
            for (T value : values) {
                if (value == null) {
                    return true;
                }
            }
            return false;
        }
    }

    public static boolean isFilled(String... values) {

        if (checkNull(values)) {
            return false;
        }
        for (String value : values) {
            if (!value.matches(filledRegex)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isCyrillicWord(String... values) {

        if (checkNull(values)) {
            return false;
        }

        for (String value : values) {
            if (!value.matches(isCyrillicWord)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isLatinWord(String... values) {

        if (checkNull(values)) {
            return false;
        }

        for (String value : values) {
            if (!value.matches(isLatinWord)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPositiveDecimalNumber(int... values) {

        if (checkNull(values)) {
            return false;
        }

        for (int value : values) {
            if (value < 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkBudgetLowerTotal(int budget, int total) {
        return budget < total;
    }

    public static boolean checkScript(String... fields) {
        for (String field : fields) {
            if (Pattern.compile(hasScript).matcher(field).find()){
                return false;
            }
        }
        return true;
    }
}
