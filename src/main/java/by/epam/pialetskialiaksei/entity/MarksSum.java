package by.epam.pialetskialiaksei.entity;

public class MarksSum extends Entity {

	private static final long serialVersionUID = 2565574420335652970L;
	private int diplomaMark;
	private int preliminarySum;
	private int totalSum;

	public MarksSum() {
	}

	public MarksSum(int diplomaMark, int preliminarySum, int totalSum) {
		this.diplomaMark = diplomaMark;
		this.preliminarySum = preliminarySum;
		this.totalSum = totalSum;
	}

	public int getDiplomaMark() {
		return diplomaMark;
	}

	public void setDiplomaMark(int diplomaMark) {
		this.diplomaMark = diplomaMark;
	}

	public int getPreliminarySum() {
		return preliminarySum;
	}

	public void setPreliminarySum(int preliminarySum) {
		this.preliminarySum = preliminarySum;
	}

	public int getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(int totalSum) {
		this.totalSum = totalSum;
	}

	@Override
	public String toString() {
		return "MarksSum{" +
				"diplomaMark=" + diplomaMark +
				", preliminarySum=" + preliminarySum +
				", totalSum=" + totalSum +
				"} " + super.toString();
	}
}
