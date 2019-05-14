package by.epam.pialetskialiaksei.sql.builder;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.entity.Entrant;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.sql.builder.api.SetBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserBuilder implements SetBuilder<User> {
    private final static Logger LOG = LogManager
            .getLogger(UserBuilder.class);
    @Override
    public User build(ResultSet rs) {
        User user = new User();
        try {
            user.setId(rs.getInt(Fields.ENTITY_ID));
            user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
            user.setLastName(rs.getString(Fields.USER_LAST_NAME));
            user.setEmail(rs.getString(Fields.USER_EMAIL));
            user.setPassword(rs.getString(Fields.USER_PASSWORD));
            user.setRole(rs.getString(Fields.USER_ROLE));
            user.setLang(rs.getString(Fields.USER_LANG));
//            user.setActiveStatus(rs.getBoolean(Fields.USER_ACTIVE_STATUS));
        } catch (SQLException e) {
            LOG.error("Can not unmarshal result set to user", e);
        }
        return user;
    }
}
