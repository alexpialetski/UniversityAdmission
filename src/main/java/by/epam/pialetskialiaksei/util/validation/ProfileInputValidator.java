package by.epam.pialetskialiaksei.util.validation;

public class ProfileInputValidator {
    /**
     * Validates user input for faculty.
     *
     * @return <code>true</code> if parameters valid, <code>false</code>
     * otherwise.
     */
    public static boolean validateUserParameters(String firstName,
                                                 String lastName, String email, String password) {
        return FieldValidation.isFilled(firstName, lastName)
                && (!email.isEmpty() && email.contains("@"))
                && (password.length() >= 4);
    }

    public static boolean validateUserParameters(String firstName, String lastName) {
        return FieldValidation.isFilled(firstName, lastName);
    }

    public static boolean validateEntrantParameters(String city,
                                                    String district, String school) {
        return FieldValidation.isFilled(city, district) && (!school.isEmpty());
    }

}
