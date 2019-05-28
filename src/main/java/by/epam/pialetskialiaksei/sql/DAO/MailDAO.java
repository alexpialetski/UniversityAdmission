package by.epam.pialetskialiaksei.sql.DAO;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.entity.Mail;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.sql.DAO.api.SqlDAO;
import by.epam.pialetskialiaksei.sql.builder.api.SetBuilder;
import by.epam.pialetskialiaksei.sql.builder.MailBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MailDAO extends SqlDAO {

    private static final String FIND_MAIL_RECORD = "SELECT * FROM university_admission.mail WHERE mailId=?;";
    private static final String DELETE_MAIL_RECORD = "DELETE  FROM university_admission.mail WHERE mailId=?;";
    private static final String UPDATE_MAIL_RECORD = "UPDATE  university_admission.mail SET `key` = ? WHERE mailId = ?;";
    private static final String CREATE_MAIL_RECORD = "INSERT INTO university_admission.mail(mailId, `key`) VALUES(?,?);";

    private final static Logger LOG = LogManager
            .getLogger(MailDAO.class);

    public void create(Mail entity) throws DaoException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_MAIL_RECORD);
            pstmt.setString(1, entity.getMailId());
            rs = pstmt.executeQuery();
            if(rs==null || !rs.next()){
                pstmt = connection.prepareStatement(CREATE_MAIL_RECORD);
                int counter = 1;
                pstmt.setString(counter++, entity.getMailId());
                pstmt.setString(counter++, entity.getKey());
                pstmt.execute();
            }else{
                pstmt = connection.prepareStatement(UPDATE_MAIL_RECORD);
                pstmt.setString(1, entity.getKey());
                pstmt.setString(2, entity.getMailId());
                pstmt.executeUpdate();
                close(rs);
            }

        } catch (SQLException e) {
            throw new DaoException("Can not create an email key", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
        }
    }

    public void update(Mail entity) throws DaoException {
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
        } catch (SQLException e) {
            throw new DaoException("Can not update an email key", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
        }
    }

    public void delete(Mail entity) throws DaoException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(DELETE_MAIL_RECORD);
            pstmt.setString(1, entity.getMailId());

            pstmt.execute();
        } catch (SQLException e) {
            throw new DaoException("Can not delete an email key", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
        }
    }

    public Mail find(Mail entitie) throws DaoException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Mail mail = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_MAIL_RECORD);
            pstmt.setString(1, entitie.getMailId());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                mail = (Mail) createBuilder().build(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Can not find an mail", e);
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
}


