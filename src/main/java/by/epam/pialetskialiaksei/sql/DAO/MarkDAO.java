package by.epam.pialetskialiaksei.sql.DAO;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.entity.ClientSubject;
import by.epam.pialetskialiaksei.entity.Entrant;
import by.epam.pialetskialiaksei.entity.Mark;
import by.epam.pialetskialiaksei.entity.Subject;
import by.epam.pialetskialiaksei.sql.DAO.api.SqlDAO;
import by.epam.pialetskialiaksei.sql.builder.FacultySubjectBuilder;
import by.epam.pialetskialiaksei.sql.builder.MarkBuilder;
import by.epam.pialetskialiaksei.sql.builder.SubjectBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MarkDAO extends SqlDAO {
    private MarkBuilder markBuilder= new MarkBuilder();
    private SubjectBuilder subjectBuilder = new SubjectBuilder();

    private static final String FIND_ALL_MARKS = "SELECT * FROM university_admission.mark;";
    private static final String FIND_MARK = "SELECT * FROM university_admission.mark WHERE mark.id = ? LIMIT 1;";
    private static final String INSERT_MARK = "INSERT INTO university_admission.mark(mark.Entrant_idEntrant,mark.Subject_idSubject,mark.value) VALUES (?,?,?);";
    private static final String UPDATE_MARK = "UPDATE mark SET mark.Entrant_idEntrant=?, mark.Subject_idSubject=?,mark.value=? WHERE mark.id=? LIMIT 1;";
    private static final String DELETE_MARK = "DELETE FROM university_admission.mark WHERE mark.id=? LIMIT 1;";
    private static final String FIND_MARKS_OF_ENTRANT = "SELECT university_admission.subject.id as Subject_idSubject,\n" +
                                                        "       university_admission.subject.name_ru as Subject_name_ru,\n" +
                                                        "       university_admission.subject.name_eng as Subject_name_eng,\n" +
                                                        "       university_admission.mark.id,\n" +
                                                        "       university_admission.mark.value,\n" +
                                                        "       university_admission.mark.Entrant_idEntrant,\n" +
                                                        "       university_admission.mark.Subject_idSubject\n" +
//                                                        "       university_admission.exam_type.exam_type\n" +
                                                        "FROM university_admission.mark\n" +
                                                        "       INNER JOIN university_admission.subject\n" +
                                                        "                  ON university_admission.mark.Subject_idSubject = university_admission.subject.id\n" +
//                                                        "       INNER JOIN university_admission.exam_type\n" +
//                                                        "                  ON university_admission.mark.exam_type_id= university_admission.exam_type.id\n" +
                                                        "WHERE university_admission.mark.Entrant_idEntrant = ?;";
    private static final String FIND_SUBJECTS_OF_ENTRANT = "SELECT university_admission.subject.id as Subject_idSubject,\n" +
                                                            "       university_admission.subject.name_ru as Subject_name_ru,\n" +
                                                            "       university_admission.subject.name_eng as Subject_name_eng\n" +
                                                            "FROM university_admission.mark\n" +
                                                            "       inner join university_admission.subject on mark.Subject_idSubject = subject.id\n" +
                                                            "WHERE university_admission.mark.Entrant_idEntrant = ?;";
    private final static Logger LOG = LogManager
            .getLogger(MarkDAO.class);

    public void create(Mark entity) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(INSERT_MARK,
                    Statement.RETURN_GENERATED_KEYS);
            int counter = 1;
            // pstmt.setInt(1, user.getId());
            pstmt.setInt(counter++, entity.getEntrantId());
            pstmt.setInt(counter++, entity.getSubjectId());
            pstmt.setInt(counter++, entity.getMark());
//            pstmt.setString(counter++, entity.getExamType());

            pstmt.execute();
            connection.commit();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getInt(Fields.GENERATED_KEY));
            }
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not create a mark", e);
        } finally {
            close(connection);
            close(pstmt);
            close(rs);
        }
    }

    public void create(Mark[] entity) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = getConnection();
            for(Mark mark: entity) {
                pstmt = connection.prepareStatement(INSERT_MARK);
                int counter = 1;
                // pstmt.setInt(1, user.getId());
                pstmt.setInt(counter++, mark.getEntrantId());
                pstmt.setInt(counter++, mark.getSubjectId());
                pstmt.setInt(counter++, mark.getMark());

                pstmt.execute();
            }
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not create a mark", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
        }
    }

    public void update(Mark entity) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(UPDATE_MARK);
            int counter = 1;
            pstmt.setInt(counter++, entity.getEntrantId());
            pstmt.setInt(counter++, entity.getSubjectId());
            pstmt.setInt(counter++, entity.getMark());
//            pstmt.setString(counter++, entity.getExamType());
            pstmt.setInt(counter, entity.getId());

            pstmt.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not update a mark", e);
        } finally {
//            close(connection);
            releaseConnection(connection);
            close(pstmt);
        }
    }

    public void update(Mark[] entity) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = getConnection();
            for(Mark mark: entity){
                pstmt = connection.prepareStatement(UPDATE_MARK);
                int counter = 1;
                pstmt.setInt(counter++, mark.getEntrantId());
                pstmt.setInt(counter++, mark.getSubjectId());
                pstmt.setInt(counter++, mark.getMark());
//                pstmt.setString(counter++, mark.getExamType());
                pstmt.setInt(counter, mark.getId());
                pstmt.executeUpdate();
            }
//            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not update a mark", e);
        } finally {
//            close(connection);
            releaseConnection(connection);
            close(pstmt);
        }
    }

    public void delete(Mark entity) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(DELETE_MARK);
            pstmt.setInt(1, entity.getId());

            pstmt.execute();
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not delete a mark", e);
        } finally {
            close(connection);
            close(pstmt);
        }
    }

    public Mark find(int entityPK) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Mark mark = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_MARK);
            pstmt.setInt(1, entityPK);
            rs = pstmt.executeQuery();
            connection.commit();
            if (rs.next()) {
//                mark = unmarshal(rs);
                mark = markBuilder.build(rs);
            }
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not find a mark", e);
        } finally {
            close(connection);
            close(pstmt);
            close(rs);
        }
        return mark;
    }

    public List<Mark> findAll() {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Mark> users = new ArrayList<>();
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_ALL_MARKS);
            rs = pstmt.executeQuery();
            connection.commit();
            while (rs.next()) {
//                users.add(unmarshal(rs));
                users.add(markBuilder.build(rs));
            }
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not find all marks", e);
        } finally {
            close(connection);
            close(pstmt);
            close(rs);
        }
        return users;
    }

    public List<ClientSubject> findMarks(Entrant entrant){
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ClientSubject> clientSubjects = new ArrayList<>();
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_MARKS_OF_ENTRANT);
            pstmt.setInt(1, entrant.getId());
            rs = pstmt.executeQuery();
//            connection.commit();
            while (rs.next()) {
//                Subject subject = unmarshalSubject(rs);
                Subject subject = subjectBuilder.buildForeign(rs);
//                Mark mark = unmarshal(rs);
                Mark mark = markBuilder.build(rs);
                ClientSubject clientSubject = new ClientSubject(subject,mark);
                clientSubjects.add(clientSubject);
            }
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not find all not faculty clientSubjects", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return clientSubjects;
    }

    public List<Subject> findSubjectsOfEntrant(Entrant entrant){
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Subject> subjects = new ArrayList<>();
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_SUBJECTS_OF_ENTRANT);
            pstmt.setInt(1, entrant.getId());
            rs = pstmt.executeQuery();
//            connection.commit();
            while (rs.next()) {
//                Subject subject = unmarshalSubject(rs);
                Subject subject = subjectBuilder.buildForeign(rs);
                subjects.add(subject);
            }
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not find all not faculty clientSubjects", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return subjects;
    }

//    public Mark findDiplomaMark(Entrant entrant){
//        Connection connection = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        Mark mark = null;
//        try {
//            connection = getConnection();
//            pstmt = connection.prepareStatement(FIND_DIPLOMA_MARK);
//            pstmt.setInt(1, entrant.getId());
//            rs = pstmt.executeQuery();
////            connection.commit();
//            if (!rs.next()) {
//                mark = null;
//            } else {
//                mark = unmarshal(rs);
//            }
//        } catch (SQLException e) {
//            rollback(connection);
//            LOG.error("Can not find a mark", e);
//        } finally {
//            releaseConnection(connection);
//            close(pstmt);
//            close(rs);
//        }
//        return mark;
//    }

    /**
     * Unmarshals database Mark record to Mark java instance.
     *
     * @param rs - ResultSet record in Mark table
     * @return Mark instance of this record
     */
}
