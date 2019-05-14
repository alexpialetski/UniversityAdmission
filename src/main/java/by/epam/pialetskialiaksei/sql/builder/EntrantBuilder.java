package by.epam.pialetskialiaksei.sql.builder;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.entity.Entrant;
import by.epam.pialetskialiaksei.sql.DAO.EntrantDAO;
import by.epam.pialetskialiaksei.sql.builder.api.SetBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EntrantBuilder implements SetBuilder<Entrant> {
    private final static Logger LOG = LogManager
            .getLogger(EntrantBuilder.class);
    @Override
    public Entrant build(ResultSet rs) {
        Entrant entrant = new Entrant();
        try {
            entrant.setId(rs.getInt(Fields.ENTITY_ID));
            entrant.setCity(rs.getString(Fields.ENTRANT_CITY));
            entrant.setDistrict(rs.getString(Fields.ENTRANT_DISTRICT));
            entrant.setSchool(rs.getString(Fields.ENTRANT_SCHOOL));
            entrant.setUserId(rs.getInt(Fields.USER_FOREIGN_KEY_ID));
            entrant.setDiplomaMark(rs.getInt(Fields.ENTRANT_DIPLOMA_MARK));
            entrant.setBlockedStatus(rs.getBoolean(Fields.ENTRANT_IS_BLOCKED));
        } catch (SQLException e) {
            LOG.error("Can not unmarshal ResultSet to entrant", e);
        }
        return entrant;
    }
}
