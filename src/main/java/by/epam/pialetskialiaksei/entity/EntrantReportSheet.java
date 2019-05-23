package by.epam.pialetskialiaksei.entity;

public class EntrantReportSheet{

	private int facultyId;
	private String firstName;
	private String lastName;
	private String email;
	private short preliminarySum;
	private short diplomaMark;
	private short totalSum;

	public EntrantReportSheet() {
	}

	public EntrantReportSheet(int facultyId, String firstName, String lastName, String email, short preliminarySum, short diplomaMark, short totalSum) {
		this.facultyId = facultyId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.preliminarySum = preliminarySum;
		this.diplomaMark = diplomaMark;
		this.totalSum = totalSum;
	}

	public int getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(int facultyId) {
		this.facultyId = facultyId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public short getPreliminarySum() {
		return preliminarySum;
	}

	public void setPreliminarySum(short preliminarySum) {
		this.preliminarySum = preliminarySum;
	}

	public short getDiplomaMark() {
		return diplomaMark;
	}

	public void setDiplomaMark(short diplomaMark) {
		this.diplomaMark = diplomaMark;
	}

	public short getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(short totalSum) {
		this.totalSum = totalSum;
	}

	@Override
	public String toString() {
		return "EntrantReportSheet{" +
				"facultyId=" + facultyId +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", preliminarySum=" + preliminarySum +
				", diplomaMark=" + diplomaMark +
				", totalSum=" + totalSum +
				'}';
	}
}