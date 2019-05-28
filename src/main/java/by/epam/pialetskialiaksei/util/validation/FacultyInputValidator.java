package by.epam.pialetskialiaksei.util.validation;

public class FacultyInputValidator {

    /**
     * Validates user input for faculty.
     *
     * @return <code>true</code> if parameters valid, <code>false</code>
     * otherwise.
     */
    public static boolean validateParameters(String facultyNameRu, String facultyNameEng, int facultyBudgetSeats,
                                             int facultyTotalSeats, String infoRu, String infoEng) {

        return FieldValidation.checkBudgetLowerTotal(facultyBudgetSeats, facultyTotalSeats) &&
                FieldValidation.isPositiveDecimalNumber(facultyBudgetSeats, facultyTotalSeats) &&
                FieldValidation.isCyrillicWord(facultyNameRu) &&
                FieldValidation.isLatinWord(facultyNameEng) &&
                FieldValidation.checkScript(infoEng, infoRu);
    }
}
