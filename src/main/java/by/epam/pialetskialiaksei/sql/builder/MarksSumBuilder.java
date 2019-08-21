package by.epam.pialetskialiaksei.sql.builder;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.entity.MarksSum;
import by.epam.pialetskialiaksei.sql.builder.api.SetBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MarksSumBuilder extends SetBuilder<MarksSum> {
    private final static Logger LOG = LogManager
            .getLogger(MarksSumBuilder.class);

    public MarksSum build(ResultSet rs) {
        MarksSum marksSum = new MarksSum();
        try {
            marksSum.setDiplomaMark(rs.getInt(Fields.MARKS_SUM_DIPLOMA_MARK));
            marksSum.setPreliminarySum(rs.getInt(Fields.MARKS_SUM_PRELIMINARY_SUM));
            marksSum.setTotalSum(rs.getInt(Fields.MARKS_SUM_TOTAL_SUM));
        } catch (SQLException e) {
            LOG.error("Can not unmarshal ResultSet to marks sum", e);
        }
        return marksSum;
    }
}
