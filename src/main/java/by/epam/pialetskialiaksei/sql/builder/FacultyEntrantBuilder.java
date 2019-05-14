package by.epam.pialetskialiaksei.sql.builder;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.entity.Faculty;
import by.epam.pialetskialiaksei.entity.FacultyEntrant;
import by.epam.pialetskialiaksei.sql.builder.api.SetBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FacultyEntrantBuilder extends SetBuilder<FacultyEntrant> {
    private final static Logger LOG = LogManager
            .getLogger(FacultyEntrantBuilder.class);
    @Override
    public FacultyEntrant build(ResultSet rs) {
        FacultyEntrant facultyEntrant = new FacultyEntrant();
        try {
            facultyEntrant.setId(rs.getInt(Fields.ENTITY_ID));
            facultyEntrant.setFacultyId(rs
                    .getInt(Fields.FACULTY_FOREIGN_KEY_ID));
            facultyEntrant.setEntrantId(rs
                    .getInt(Fields.ENTRANT_FOREIGN_KEY_ID));
        } catch (SQLException e) {
            LOG.error("Can not unmarshal ResultSet to faculty entrant", e);
        }
        return facultyEntrant;
    }
}
