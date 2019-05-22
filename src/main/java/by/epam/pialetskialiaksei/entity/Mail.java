package by.epam.pialetskialiaksei.entity;

/**
 * Entrant entity. This transfer object characterized by city, district, school,
 * foreign user id field and blocked state, which is <code>false</code by
 * default, but may be changed by admin.
 *
 * @author Mark Norkin
 *
 */
public class Mail extends Entity {

	private static final long serialVersionUID = 2565574420335652970L;
	private String mailId;
	private String key;

	public Mail() {
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Mail(String mailId, String key) {
		this.mailId = mailId;
		this.key = key;
	}

	@Override
	public String toString() {
		return "Mail{" +
				"mailId='" + mailId + '\'' +
				", key='" + key + '\'' +
				"} " + super.toString();
	}
}