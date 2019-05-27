package by.epam.pialetskialiaksei.util.validation;

public class FacultyInputValidator {

    /**
     * Validates user input for faculty.
     *
     * @return <code>true</code> if parameters valid, <code>false</code>
     * otherwise.
     */
    public static boolean validateParameters(String facultyNameRu, String facultyNameEng, int facultyBudgetSeats,
                                             int facultyTotalSeats) {
//        if (!FieldValidation.isCyrillicWord(facultyNameRu)
//                || !FieldValidation.isLatinWord(facultyNameEng)) {
//            return false;
//        }
//
//        if (!FieldValidation.isPositiveDecimalNumber(facultyBudgetSeats,
//                facultyTotalSeats)) {
//            return false;
//        }
//
//        if (!FieldValidation.isPositiveByte(facultyBudgetSeats,
//                facultyTotalSeats)) {
//            return false;
//        }

        return FieldValidation.checkBudgetLowerTotal(facultyBudgetSeats, facultyTotalSeats) &&
//                FieldValidation.isPositiveByte(facultyBudgetSeats, facultyTotalSeats) &&
                FieldValidation.isPositiveDecimalNumber(facultyBudgetSeats, facultyTotalSeats) &&
                FieldValidation.isCyrillicWord(facultyNameRu) &&
                FieldValidation.isLatinWord(facultyNameEng);
    }
}
