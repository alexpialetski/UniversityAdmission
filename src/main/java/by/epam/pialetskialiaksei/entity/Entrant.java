package by.epam.pialetskialiaksei.entity;

public class Entrant extends Entity {

	private static final long serialVersionUID = 2565574420335652970L;
	private String city;
	private String district;
	private String school;
	private int userId;
	private int diplomaMark;


	public int getDiplomaMark() {
		return diplomaMark;
	}

	public void setDiplomaMark(int diplomaMark) {
		this.diplomaMark = diplomaMark;
	}

	public Entrant(String city, String district, String school, User user) {
		this(city, district, school, user.getId());
	}

	public Entrant(String city, String district, String school, int userId) {
		this.city = city;
		this.district = district;
		this.school = school;
		this.userId = userId;
	}

	public Entrant() {
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	@Override
	public String toString() {
		return "Entrant{" +
				"city='" + city + '\'' +
				", district='" + district + '\'' +
				", school='" + school + '\'' +
				", userId=" + userId +
				", diplomaMark=" + diplomaMark +
				"} " + super.toString();
	}
}
