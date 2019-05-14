package by.epam.pialetskialiaksei.model;

import by.epam.pialetskialiaksei.entity.Faculty;
import by.epam.pialetskialiaksei.entity.Subject;

import java.util.List;
import java.util.Objects;

public class FacultyInfoModel {
    private Faculty faculty;
    private List<Subject> subjects;

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public FacultyInfoModel(Faculty faculty, List<Subject> subjects) {
        this.faculty = faculty;
        this.subjects = subjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FacultyInfoModel that = (FacultyInfoModel) o;
        return Objects.equals(getFaculty(), that.getFaculty()) &&
                Objects.equals(getSubjects(), that.getSubjects());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFaculty(), getSubjects());
    }
}
