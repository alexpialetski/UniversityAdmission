package by.epam.pialetskialiaksei.sql.builder;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.entity.FacultyEntrant;
import by.epam.pialetskialiaksei.entity.FacultySubject;
import by.epam.pialetskialiaksei.sql.builder.api.SetBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FacultySubjectBuilder extends SetBuilder<FacultySubject> {
    private final static Logger LOG = LogManager
            .getLogger(FacultySubjectBuilder.class);
    @Override
    public FacultySubject build(ResultSet rs) {
        FacultySubject facultySubject = new FacultySubject();
        try {
            facultySubject.setId(rs.getInt(Fields.ENTITY_ID));
            facultySubject.setFacultyId(rs
                    .getInt(Fields.FACULTY_FOREIGN_KEY_ID));
            facultySubject.setSubjectId(rs
                    .getInt(Fields.SUBJECT_FOREIGN_KEY_ID));

        } catch (SQLException e) {
            LOG.error("Can not unmarshal ResultSet to faculty subject", e);
        }
        return facultySubject;
    }
}
