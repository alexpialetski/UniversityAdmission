package by.epam.pialetskialiaksei.sql.builder;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.entity.Faculty;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.sql.builder.api.SetBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FacultyBuilder extends SetBuilder<Faculty> {
    private final static Logger LOG = LogManager
            .getLogger(FacultyBuilder.class);
    @Override
    public Faculty build(ResultSet rs) {
        Faculty faculty = new Faculty();
        try {
            faculty.setId(rs.getInt(Fields.ENTITY_ID));
            faculty.setNameRu(rs.getString(Fields.FACULTY_NAME_RU));
            faculty.setNameEng(rs.getString(Fields.FACULTY_NAME_ENG));
            faculty.setTotalSeats(rs.getByte(Fields.FACULTY_TOTAL_SEATS));
            faculty.setBudgetSeats(rs.getByte(Fields.FACULTY_BUDGET_SEATS));
        } catch (SQLException e) {
            LOG.error("Can not unmarshal ResultSet to faculty", e);
        }
        return faculty;
    }

    @Override
    public Faculty buildForeign(ResultSet rs) {
        Faculty faculty = new Faculty();
        try {
            faculty.setId(rs.getInt(Fields.FACULTY_FOREIGN_KEY_ID));
            faculty.setNameEng(rs.getString(Fields.FACULTY_FOREIGN_NAME_ENG));
            faculty.setNameRu(rs.getString(Fields.FACULTY_FOREIGN_NAME_RU));
            faculty.setTotalSeats(rs.getByte(Fields.FACULTY_TOTAL_SEATS));
            faculty.setBudgetSeats(rs.getByte(Fields.FACULTY_BUDGET_SEATS));
        } catch (SQLException e) {
            LOG.error("Can not unmarshal ResultSet to faculty", e);
        }
        return faculty;
    }
}
