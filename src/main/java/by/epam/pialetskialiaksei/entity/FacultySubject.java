package by.epam.pialetskialiaksei.entity;

/**
 * Faculty clientSubjects entity. Main purpose of this class is to tell which clientSubjects
 * are needed to entrant, so then he can apply for some faculty. This clientSubjects
 * are also called preliminary.
 *
 * @author Mark Norkin
 *
 */
public class FacultySubject extends Entity {

	private static final long serialVersionUID = 1165092452837127706L;
	private int subjectId;
	private int facultyId;

	public FacultySubject(int subjectId, int facultyId) {
		super();
		this.subjectId = subjectId;
		this.facultyId = facultyId;
	}

	public FacultySubject(Subject subject, Faculty faculty) {
		this(subject.getId(), faculty.getId());
	}

	public FacultySubject() {
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public int getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(int facultyId) {
		this.facultyId = facultyId;
	}

	@Override
	public String toString() {
		return "FacultySubjects [subjectId=" + subjectId + ", facultyId="
				+ facultyId + "]";
	}

}
