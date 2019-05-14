package by.epam.pialetskialiaksei.sql.builder;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.entity.Mark;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.sql.builder.api.SetBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MarkBuilder implements SetBuilder<Mark> {
    private final static Logger LOG = LogManager
            .getLogger(MarkBuilder.class);
    @Override
    public Mark build(ResultSet rs) {
        Mark mark = new Mark();
        try {
            mark.setId(rs.getInt(Fields.ENTITY_ID));
            mark.setEntrantId(rs.getInt(Fields.ENTRANT_FOREIGN_KEY_ID));
            mark.setSubjectId(rs.getInt(Fields.SUBJECT_FOREIGN_KEY_ID));
            mark.setMark(rs.getByte(Fields.MARK_VALUE));
//            mark.setExamType(rs.getString(Fields.MARK_EXAM_TYPE));
        } catch (SQLException e) {
            LOG.error("Can not unmarshal ResultSet to mark", e);
        }
        return mark;
    }
}
