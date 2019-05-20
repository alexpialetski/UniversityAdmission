package by.epam.pialetskialiaksei.sql.builder.manager;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.entity.Entrant;
import by.epam.pialetskialiaksei.entity.Mail;
import by.epam.pialetskialiaksei.sql.builder.api.SetBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MailBuilder extends SetBuilder<Mail> {
    private final static Logger LOG = LogManager
            .getLogger(MailBuilder.class);
    @Override
    public Mail build(ResultSet rs) {
        Mail mail = new Mail();
        try {
            mail.setId(rs.getInt(Fields.ENTITY_ID));
            mail.setMailId(rs.getString(Fields.MAIL_MAIL_ID));
            mail.setKey(rs.getString(Fields.MAIL_KEY));
        } catch (SQLException e) {
            LOG.error("Can not unmarshal ResultSet to mail", e);
        }
        return mail;
    }
}
