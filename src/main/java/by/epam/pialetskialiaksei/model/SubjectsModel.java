package by.epam.pialetskialiaksei.model;

import by.epam.pialetskialiaksei.entity.Subject;

import java.util.ArrayList;
import java.util.List;

public class SubjectsModel {
    protected List<Subject> subjects = new ArrayList<>();

    public SubjectsModel(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return "SubjectsModel{" +
                "clientSubjects=" + subjects +
                '}';
    }
}
