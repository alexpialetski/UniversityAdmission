package by.epam.pialetskialiaksei.sql.builder;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.entity.EntrantReportSheet;
import by.epam.pialetskialiaksei.sql.builder.api.SetBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportSheetBuilder extends SetBuilder<EntrantReportSheet> {
    private final static Logger LOG = LogManager
            .getLogger(ReportSheetBuilder.class);

    public EntrantReportSheet build(ResultSet rs) {
        EntrantReportSheet reportSheet = new EntrantReportSheet();
        try {
            reportSheet.setUserId(rs.getInt(Fields.USER_FOREIGN_KEY_ID));
            reportSheet.setFacultyId(rs.getInt(Fields.REPORT_SHEET_FACULTY_ID));
            reportSheet.setFirstName(rs.getString(Fields.REPORT_SHEET_USER_FIRST_NAME));
            reportSheet.setLastName(rs.getString(Fields.REPORT_SHEET_USER_LAST_NAME));
            reportSheet.setEmail(rs.getString(Fields.REPORT_SHEET_USER_EMAIL));
            reportSheet.setPreliminarySum(rs.getShort(Fields.REPORT_SHEET_ENTRANT_PRELIMINARY_SUM));
            reportSheet.setDiplomaMark(rs.getShort(Fields.REPORT_SHEET_ENTRANT_DIPLOMA_MARK));
            reportSheet.setTotalSum(rs.getShort(Fields.REPORT_SHEET_ENTRANT_TOTAL_SUM));
        } catch (SQLException e) {
            LOG.error("Can not unmarshal ResultSet to report sheet", e);
        }
        return reportSheet;
    }
}
