package by.epam.pialetskialiaksei.sql.DAO;


import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.entity.Faculty;
import by.epam.pialetskialiaksei.entity.FacultySubject;
import by.epam.pialetskialiaksei.entity.Subject;
import by.epam.pialetskialiaksei.model.FacultyInfoModel;
import by.epam.pialetskialiaksei.sql.DAO.api.SqlDAO;
import by.epam.pialetskialiaksei.sql.builder.FacultyBuilder;
import by.epam.pialetskialiaksei.sql.builder.FacultySubjectBuilder;
import by.epam.pialetskialiaksei.sql.builder.SubjectBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class FacultySubjectDAO extends SqlDAO {
    private FacultySubjectBuilder facultySubjectBuilder = new FacultySubjectBuilder();
    private FacultyBuilder facultyBuilder = new FacultyBuilder();
    private SubjectBuilder subjectBuilder = new SubjectBuilder();

    //    private static final String FIND_ALL_FACULTY_SUBJECTS = "SELECT * FROM university_admission.faculty_subjects;";
    private static final String FIND_ALL_FACULTY_SUBJECTS = "SELECT faculty_subjects.id,\n" +
                                                            "       faculty.id as Faculty_idFaculty, faculty.name_ru as Faculty_name_ru, faculty.name_eng as Faculty_name_eng, faculty.total_seats, faculty.budget_seats,\n" +
                                                            "       subject.id as Subject_idSubject, subject.name_ru as Subject_name_ru, subject.name_eng as Subject_name_eng\n" +
                                                            "FROM university_admission.faculty_subjects\n" +
                                                            "       INNER JOIN subject on faculty_subjects.Subject_idSubject = subject.id\n" +
                                                            "       INNER JOIN faculty on faculty_subjects.Faculty_idFaculty = faculty.id;";
    private static final String FIND_FACULTY_SUBJECT_BY_ID = "SELECT faculty_subjects.id,\n" +
                                                            "       faculty.id as Faculty_idFaculty, faculty.name_ru as Faculty_name_ru, faculty.name_eng as Faculty_name_eng, faculty.total_seats, faculty.budget_seats,\n" +
                                                            "       subject.id as Subject_idSubject, subject.name_ru as Subject_name_ru, subject.name_eng as Subject_name_eng\n" +
                                                            "FROM university_admission.faculty_subjects\n" +
                                                            "       INNER JOIN subject on faculty_subjects.Subject_idSubject = subject.id\n" +
                                                            "       INNER JOIN faculty on faculty_subjects.Faculty_idFaculty = faculty.id\n" +
                                                            "WHERE university_admission.faculty_subjects.Faculty_idFaculty = ?\n" +
                                                            "LIMIT 3;";
    private static final String FIND_FACULTY_SUBJECTS = "SELECT * FROM university_admission.faculty_subjects WHERE university_admission.faculty_subjects.id = ? LIMIT 3;";
    private static final String INSERT_FACULTY_SUBJECT = "INSERT INTO university_admission.faculty_subjects (university_admission.faculty_subjects.Faculty_idFaculty, university_admission.faculty_subjects.Subject_idSubject) VALUES (?,?);";
    private static final String DELETE_FACULTY_SUBJECT = "DELETE FROM university_admission.faculty_subjects WHERE university_admission.faculty_subjects.Faculty_idFaculty=? AND university_admission.faculty_subjects.Subject_idSubject=? LIMIT 1;";
    private static final String DELETE_ALL_FACULTY_SUBJECTS = "DELETE FROM university_admission.faculty_subjects WHERE university_admission.faculty_subjects.Faculty_idFaculty=?";

    private final static Logger LOG = LogManager
            .getLogger(FacultySubjectDAO.class);

    public void create(FacultySubject entity) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(INSERT_FACULTY_SUBJECT,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            int counter = 1;
            pstmt.setInt(counter++, entity.getFacultyId());
            pstmt.setInt(counter, entity.getSubjectId());

            pstmt.execute();
            connection.commit();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getInt(Fields.GENERATED_KEY));
            }
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not create a faculty subject", e);
        } finally {
            close(connection);
            close(pstmt);
            close(rs);
        }
    }

    public void delete(FacultySubject entity) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(DELETE_FACULTY_SUBJECT);
            // pstmt.setInt(1, entity.getId());
            pstmt.setInt(1, entity.getFacultyId());
            pstmt.setInt(2, entity.getSubjectId());

            pstmt.execute();
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not delete a faculty subject", e);
        } finally {
            close(connection);
            close(pstmt);
        }
    }

    public void deleteAllSubjects(Faculty entity) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(DELETE_ALL_FACULTY_SUBJECTS);
            pstmt.setInt(1, entity.getId());

            pstmt.execute();
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not delete all clientSubjects of a given Faculty", e);
        } finally {
            close(connection);
            close(pstmt);
        }
    }

    public List<Subject> findById(int entityPK) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Subject> subjects = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_FACULTY_SUBJECT_BY_ID);
            pstmt.setInt(1, entityPK);
            rs = pstmt.executeQuery();
//            connection.commit();
            subjects = new ArrayList<>();
            while (rs.next()) {
                subjects.add(subjectBuilder.buildForeign(rs));
            }
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not find a faculty subject", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return subjects;
    }

    public List<FacultyInfoModel> findAll() {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<FacultyInfoModel> facultyInfoModels = new ArrayList<>();
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_ALL_FACULTY_SUBJECTS);
            rs = pstmt.executeQuery();
            int i = 1;
            Faculty faculty = null;
            List<Subject> subjects = new ArrayList<>();
            while (rs.next()) {
                if (i % 3 == 0) {
                    faculty = facultyBuilder.buildForeign(rs);
                    subjects.add(subjectBuilder.buildForeign(rs));
                    facultyInfoModels.add(new FacultyInfoModel(faculty, subjects));
                    subjects = new ArrayList<>();
                    i = 1;
                    continue;
                }
                subjects.add(subjectBuilder.buildForeign(rs));
                ++i;
            }
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not find all faculty clientSubjects", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return facultyInfoModels;
    }

    public List<FacultySubject> find(int facultyId) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<FacultySubject> facultySubjects = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_FACULTY_SUBJECTS);
            pstmt.setInt(1, facultyId);
            rs = pstmt.executeQuery();
//            connection.commit();
            facultySubjects = new ArrayList<>();
            while (rs.next()) {
//                facultySubjects.add(unmarshal(rs));
                facultySubjects.add(facultySubjectBuilder.build(rs));
            }
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not find a faculty subject", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return facultySubjects;
    }

    /**
     * Unmarshals specified Faculty Subjects database record to java Faculty
     * Subjects entity instance.
     *
     * @param rs
     *            - ResultSet record of Faculty Subject
     * @return entity instance of this record
     */
}