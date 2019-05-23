package by.epam.pialetskialiaksei.entity;

/**
 * Entrant entity. This transfer object characterized by city, district, school,
 * foreign user id field and blocked state, which is <code>false</code by
 * default, but may be changed by admin.
 *
 * @author Mark Norkin
 *
 */
public class FormOfEducation extends Entity {

	private static final long serialVersionUID = 2565574420335652970L;
	private String formRu;
	private String formEng;

	public FormOfEducation(){}

	public FormOfEducation(String formRu, String formEng) {
		this.formRu = formRu;
		this.formEng = formEng;
	}

	public String getFormRu() {
		return formRu;
	}

	public void setFormRu(String formRu) {
		this.formRu = formRu;
	}

	public String getFormEng() {
		return formEng;
	}

	public void setFormEng(String formEng) {
		this.formEng = formEng;
	}

	@Override
	public String toString() {
		return "FacultyOfEducation{" +
				"formRu='" + formRu + '\'' +
				", formEng='" + formEng + '\'' +
				"} " + super.toString();
	}
}
