package by.epam.pialetskialiaksei.model;

import by.epam.pialetskialiaksei.entity.Faculty;

import java.util.List;

public class FacultyResultModel {
    private Faculty faculty;
    private List<EntrantResultModel> entrantResults;

    public FacultyResultModel() {
    }

    public FacultyResultModel(Faculty faculty, List<EntrantResultModel> entrantResults) {
        this.faculty = faculty;
        this.entrantResults = entrantResults;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public List<EntrantResultModel> getEntrantResults() {
        return entrantResults;
    }

    public void setEntrantResults(List<EntrantResultModel> entrantResults) {
        this.entrantResults = entrantResults;
    }
}
