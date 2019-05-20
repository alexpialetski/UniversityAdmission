package by.epam.pialetskialiaksei.sql.DAO;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.entity.Entrant;
import by.epam.pialetskialiaksei.entity.Faculty;
import by.epam.pialetskialiaksei.entity.Mail;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.sql.DAO.api.SqlDAO;
import by.epam.pialetskialiaksei.sql.builder.EntrantBuilder;
import by.epam.pialetskialiaksei.sql.builder.api.SetBuilder;
import by.epam.pialetskialiaksei.sql.builder.manager.MailBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MailDAO extends SqlDAO {

    private static final String FIND_MAIL_RECORD = "SELECT * FROM university_admission.mail WHERE mailId=?;";
    private static final String DELETE_MAIL_RECORD = "DELETE  FROM university_admission.mail WHERE mailId=?;";
    private static final String UPDATE_MAIL_RECORD = "UPDATE  university_admission.mail SET `key` = ? WHERE mailId = ?;";
    private static final String CREATE_MAIL_RECORD = "INSERT INTO university_admission.mail(mailId, `key`) VALUES(?,?);";

    private final static Logger LOG = LogManager
            .getLogger(MailDAO.class);

    public void create(Mail entity) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(CREATE_MAIL_RECORD,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            int counter = 1;
            pstmt.setString(counter++, entity.getMailId());
            pstmt.setString(counter++, entity.getKey());
            pstmt.execute();
//            connection.commit();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getInt(Fields.GENERATED_KEY));
            }
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not create an entrant", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
    }

    public void update(Mail entity) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(UPDATE_MAIL_RECORD);
            int counter = 1;
            pstmt.setString(counter++, entity.getKey());
            pstmt.setString(counter++, entity.getMailId());

            pstmt.setInt(counter, entity.getId());

            pstmt.executeUpdate();
//            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not update an entrant", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
        }
    }

    public void delete(Mail entity) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(DELETE_MAIL_RECORD);
            pstmt.setString(1, entity.getMailId());

            pstmt.execute();
//            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not delete an entrant", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
        }
    }

    public Mail find(Mail entitie) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Mail mail = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_MAIL_RECORD);
            pstmt.setString(1, entitie.getMailId());
            rs = pstmt.executeQuery();
//            connection.commit();
            if (!rs.next()) {
                mail = null;
            } else {
//                mail = unmarshal(rs);
//                mail = entrantBuilder.build(rs);
                mail = (Mail) createBuilder().build(rs);
            }
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not find an mail", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return mail;
    }


    @Override
    protected SetBuilder createBuilder() {
        return new MailBuilder();
    }

    /**
     * Unmarshals Entrant record in database to Java Entrant instance.
     *
     * @param rs - ResultSet record
     * @return Entrant instance of this record
     */

}


