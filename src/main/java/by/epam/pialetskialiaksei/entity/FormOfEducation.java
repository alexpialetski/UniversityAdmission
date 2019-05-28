package by.epam.pialetskialiaksei.entity;

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
