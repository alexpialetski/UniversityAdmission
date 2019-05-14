package by.epam.pialetskialiaksei.entity;

import java.util.Objects;

/**
 * Subject entity. Every subject is characterized by its name.
 *
 * @author Mark Norkin
 *
 */
public class Subject extends Entity {

	private static final long serialVersionUID = -5388561545513613948L;
	private String nameRu;
	private String nameEng;

	public Subject(String nameRu, String nameEng) {
		this.nameRu = nameRu;
		this.nameEng = nameEng;
	}

	public Subject() {
	}

	public String getNameRu() {
		return nameRu;
	}

	public void setNameRu(String nameRu) {
		this.nameRu = nameRu;
	}

	public String getNameEng() {
		return nameEng;
	}

	public void setNameEng(String nameEng) {
		this.nameEng = nameEng;
	}

	@Override
	public String toString() {
		return "Subject [nameRu=" + nameRu + ", nameEng=" + nameEng + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o){ return true;}
		if (o == null || getClass() != o.getClass()) {return false;}
		Subject subject = (Subject) o;
		if(!subject.nameRu.equals(this.nameRu)|| !subject.nameEng.equals(this.nameEng)){
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getNameRu(), getNameEng());
	}
}
