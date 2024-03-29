package by.epam.pialetskialiaksei.sql.DAO;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.entity.*;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.sql.DAO.api.SqlDAO;
import by.epam.pialetskialiaksei.sql.builder.MarkBuilder;
import by.epam.pialetskialiaksei.sql.builder.SubjectBuilder;
import by.epam.pialetskialiaksei.sql.builder.api.SetBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SubjectDAO extends SqlDAO {

    private static final String FIND_ALL_SUBJECTS = "SELECT * FROM university_admission.subject;";
    private static final String FIND_SUBJECT_BY_ID = "SELECT * FROM university_admission.subject WHERE subject.id=? LIMIT 1;";
    private final static String FIND_SUBJECT_BY_NAME = "SELECT * FROM university_admission.subject WHERE subject.name_ru=? OR subject.name_eng=? LIMIT 1;";
    private static final String INSERT_SUBJECT = "INSERT INTO subject(name_ru,name_eng) VALUES(?,?)";
    private static final String UPDATE_SUBJECT = "UPDATE subject SET subject.name_ru=?, subject.name_eng=? WHERE subject.id=?;";
    private static final String DELETE_SUBJECT = "DELETE FROM university_admission.subject WHERE subject.id=? LIMIT 1;";
    private static final String FIND_ALL_FACULTY_SUBJECTS = "SELECT university_admission.subject.id, university_admission.subject.name_ru, university_admission.subject.name_eng FROM university_admission.subject,university_admission.faculty_subjects WHERE faculty_subjects.Faculty_idFaculty = ? AND university_admission.faculty_subjects.Subject_idSubject=university_admission.subject.id ;";
    private static final String FIND_ALL_NOT_FACULTY_SUBJECTS = "SELECT university_admission.subject.id, university_admission.subject.name_ru, university_admission.subject.name_eng FROM university_admission.subject LEFT JOIN university_admission.Faculty_Subjects ON university_admission.Faculty_Subjects.Subject_idSubject = university_admission.subject.id AND university_admission.Faculty_Subjects.Faculty_idFaculty = ? WHERE university_admission.Faculty_Subjects.id IS NULL;";
    private static final String FIND_MARKS_OF_ENTRANT = "SELECT university_admission.subject.id,\n" +
            "       university_admission.subject.name_ru,\n" +
            "       university_admission.subject.name_eng,\n" +
            "       university_admission.mark.value,\n" +
            "       university_admission.mark.Entrant_idEntrant as Entrant_idEntrant,\n" +
            "       university_admission.exam_type.exam_type\n" +
            "FROM university_admission.mark\n" +
            "       INNER JOIN university_admission.subject\n" +
            "                  ON university_admission.mark.Subject_idSubject = university_admission.subject.id\n" +
            "       INNER JOIN university_admission.exam_type\n" +
            "                  ON university_admission.mark.exam_type_id= university_admission.exam_type.id\n" +
            "WHERE university_admission.mark.Entrant_idEntrant = ?;";


    private final static Logger LOG = LogManager
            .getLogger(SubjectDAO.class);


    public void create(Subject entity) throws DaoException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(INSERT_SUBJECT,
                    Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, entity.getNameRu());
            pstmt.setString(2, entity.getNameEng());

            pstmt.execute();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getInt(Fields.GENERATED_KEY));
            }
        } catch (SQLException e) {
            throw new DaoException("Can not create a subject", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }

    }

    public void update(Subject entity) throws DaoException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(UPDATE_SUBJECT);
            pstmt.setString(1, entity.getNameRu());
            pstmt.setString(2, entity.getNameEng());

            pstmt.setInt(3, entity.getId());

            pstmt.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException("Can not update a subject", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
        }
    }

    public void update(List<Subject> entity) throws DaoException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = getConnection();
            for (Subject subject : entity) {
                pstmt = connection.prepareStatement(UPDATE_SUBJECT);
                pstmt.setString(1, subject.getNameRu());
                pstmt.setString(2, subject.getNameEng());
                pstmt.setInt(3, subject.getId());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DaoException("Can not update a subject", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
        }
    }

    public void delete(Subject entity) throws DaoException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(DELETE_SUBJECT);
            pstmt.setInt(1, entity.getId());

            pstmt.execute();
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException("Can not delete a subject", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
        }
    }

    public void delete(List<Integer> entity) throws DaoException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = getConnection();
            for(int id: entity){
                pstmt = connection.prepareStatement(DELETE_SUBJECT);
                pstmt.setInt(1, id);
                pstmt.execute();
            }
        } catch (SQLException e) {
            throw new DaoException("Can not delete a subject", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
        }
    }

    public Subject find(int entityPK) throws DaoException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Subject subject = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_SUBJECT_BY_ID);
            pstmt.setInt(1, entityPK);
            rs = pstmt.executeQuery();
            connection.commit();
            if (!rs.next()) {
                subject = null;
            } else {
                subject = (Subject) createBuilder().build(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Can not find a subject", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return subject;
    }

    public Subject find(String subjectName) throws DaoException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Subject subject = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_SUBJECT_BY_NAME);
            pstmt.setString(1, subjectName);
            pstmt.setString(2, subjectName);

            rs = pstmt.executeQuery();
            if (!rs.next()) {
                subject = null;
            } else {
                subject = (Subject) createBuilder().build(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Can not find a subject", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return subject;
    }

    public List<Subject> findAll() throws DaoException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Subject> subjects = new ArrayList<Subject>();
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_ALL_SUBJECTS);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                subjects.add((Subject) createBuilder().build(rs));
            }
        } catch (SQLException e) {
            throw new DaoException("Can not find all clientSubjects", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return subjects;
    }

    public List<Subject> findAllFacultySubjects(Faculty faculty) throws DaoException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Subject> facultySubjects = new ArrayList<Subject>();
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_ALL_FACULTY_SUBJECTS);
            pstmt.setInt(1, faculty.getId());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                facultySubjects.add((Subject) createBuilder().build(rs));
            }
        } catch (SQLException e) {
            throw new DaoException("Can not find all faculty clientSubjects", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return facultySubjects;
    }

    public List<Subject> findAllNotFacultySubjects(Faculty faculty) throws DaoException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Subject> facultySubjects = new ArrayList<Subject>();
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_ALL_NOT_FACULTY_SUBJECTS);
            pstmt.setInt(1, faculty.getId());
            rs = pstmt.executeQuery();
            connection.commit();
            while (rs.next()) {
                facultySubjects.add((Subject) createBuilder().build(rs));
            }
        } catch (SQLException e) {
            throw new DaoException("Can not find all not faculty clientSubjects", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return facultySubjects;
    }

    public List<ClientSubject> findMarks(Entrant entrant) throws DaoException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ClientSubject> facultySubjects = new ArrayList<>();
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_MARKS_OF_ENTRANT);
            pstmt.setInt(1, entrant.getId());
            rs = pstmt.executeQuery();
            MarkBuilder markBuilder = new MarkBuilder();
            while (rs.next()) {
                Subject subject = (Subject) createBuilder().build(rs);
                Mark mark = markBuilder.buildForeign(rs);
                facultySubjects.add(new ClientSubject(subject, mark));
            }
        } catch (SQLException e) {
            throw new DaoException("Can not find all not faculty clientSubjects", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return facultySubjects;
    }

    @Override
    protected SetBuilder createBuilder() {
        return new SubjectBuilder();
    }
}

