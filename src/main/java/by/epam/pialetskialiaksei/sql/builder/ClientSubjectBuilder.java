package by.epam.pialetskialiaksei.sql.builder;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.entity.ClientSubject;
import by.epam.pialetskialiaksei.entity.Mark;
import by.epam.pialetskialiaksei.entity.Subject;
import by.epam.pialetskialiaksei.sql.builder.api.SetBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientSubjectBuilder extends SetBuilder<ClientSubject> {
    private final static Logger LOG = LogManager
            .getLogger(ClientSubjectBuilder.class);
    @Override
    public ClientSubject build(ResultSet rs) {
        return null;
    }
    public ClientSubject build(ResultSet rs, Subject subject) {
        Mark mark = new Mark();
        try {
            mark.setEntrantId(rs.getInt("entrant_id"));
            mark.setSubjectId(rs.getInt(Fields.ENTITY_ID));
            mark.setMark(rs.getInt(Fields.MARK_VALUE));
        } catch (SQLException e) {
            LOG.error("Can not unmarshal ResultSet to subject", e);
        }
        ClientSubject clientSubject = new ClientSubject(subject,mark);
        return clientSubject;
    }
}
