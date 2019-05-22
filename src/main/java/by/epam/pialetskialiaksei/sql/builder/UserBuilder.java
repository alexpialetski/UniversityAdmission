package by.epam.pialetskialiaksei.sql.builder;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.entity.Entrant;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.sql.builder.api.SetBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserBuilder extends SetBuilder<User> {
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
        } catch (SQLException e) {
            LOG.error("Can not unmarshal result set to user", e);
        }
        return user;
    }

    @Override
    public User buildForeign(ResultSet rs) {
        User user = new User();
        try {
            user.setId(rs.getInt(Fields.USER_FOREIGN_KEY_ID));
            user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
            user.setLastName(rs.getString(Fields.USER_LAST_NAME));
            user.setEmail(rs.getString(Fields.USER_EMAIL));
            user.setPassword(rs.getString(Fields.USER_PASSWORD));
            user.setRole(rs.getString(Fields.USER_ROLE));
        } catch (SQLException e) {
            LOG.error("Can not unmarshal result set to user", e);
        }
        return user;
    }
}
