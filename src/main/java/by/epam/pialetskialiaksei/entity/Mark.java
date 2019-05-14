package by.epam.pialetskialiaksei.entity;

/**
 * Mark entity. Every instance is characterized by foreign keys from subject and
 * entrant, mark value and exam type (preliminary or diploma). So every mark
 * references to specific subject and entrant that get it on some exam.
 *
 * @author Mark Norkin
 *
 */
public class Mark extends Entity {

	private static final long serialVersionUID = -6225323023971292703L;
	private int subjectId;
	private int entrantId;
	private int mark;

	public Mark(int subjectId, int entrantId, byte mark) {
		super();
		this.subjectId = subjectId;
		this.entrantId = entrantId;
		this.mark = mark;
	}

	public Mark() {
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public int getEntrantId() {
		return entrantId;
	}

	public void setEntrantId(int entrantId) {
		this.entrantId = entrantId;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	@Override
	public String toString() {
		return "Mark{" +
				"subjectId=" + subjectId +
				", entrantId=" + entrantId +
				", mark=" + mark +
				"} " + super.toString();
	}
}
