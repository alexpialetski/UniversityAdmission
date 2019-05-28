package by.epam.pialetskialiaksei.util.validation;

public class SubjectInputValidator {

	/**
	 * Validates user input for subject.
	 *
	 * @return <code>true</code> if parameters valid, <code>false</code>
	 *         otherwise.
	 */
	public static boolean validateParameters(String subjectNameRu,
			String subjectNameEng) {
		return FieldValidation.isCyrillicWord(subjectNameRu)&&
				FieldValidation.isLatinWord(subjectNameEng)&&
				FieldValidation.checkScript(subjectNameEng, subjectNameRu);
	}
}
