package by.epam.pialetskialiaksei.entity;

public class FacultyEntrant extends Entity {

	private static final long serialVersionUID = 1099698953477481899L;

	private int facultyId;
	private int entrantId;

	public FacultyEntrant(int facultyId, int entrantId) {
		this.facultyId = facultyId;
		this.entrantId = entrantId;
	}

	public FacultyEntrant(Faculty faculty, Entrant entrant) {
		this(faculty.getId(), entrant.getId());
	}

	public FacultyEntrant() {
	}

	public int getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(int facultyId) {
		this.facultyId = facultyId;
	}

	public int getEntrantId() {
		return entrantId;
	}

	public void setEntrantId(int entrantId) {
		this.entrantId = entrantId;
	}

	@Override
	public String toString() {
		return "FacultyEntrants [facultyId=" + facultyId + ", entrantId="
				+ entrantId + "]";
	}

}
