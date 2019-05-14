package by.epam.pialetskialiaksei.sql.builder;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.entity.Subject;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.sql.builder.api.SetBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectBuilder implements SetBuilder<Subject> {
    private final static Logger LOG = LogManager
            .getLogger(SubjectBuilder.class);
    @Override
    public Subject build(ResultSet rs) {
        Subject subject = new Subject();
        try {
            subject.setId(rs.getInt(Fields.ENTITY_ID));
            subject.setNameRu(rs.getString(Fields.SUBJECT_NAME_RU));
            subject.setNameEng(rs.getString(Fields.SUBJECT_NAME_ENG));
        } catch (SQLException e) {
            LOG.error("Can not unmarshal ResultSet to subject", e);
        }
        return subject;
    }
}
