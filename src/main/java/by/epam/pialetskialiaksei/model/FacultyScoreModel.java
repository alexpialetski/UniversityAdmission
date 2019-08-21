package by.epam.pialetskialiaksei.model;

import by.epam.pialetskialiaksei.entity.Faculty;

public class FacultyScoreModel {
    private Faculty faculty;
    private int budgetScore;
    private int notBudgetScore;

    public FacultyScoreModel(Faculty faculty, int budgetScore, int notBudgetScore) {
        this.faculty = faculty;
        this.budgetScore = budgetScore;
        this.notBudgetScore = notBudgetScore;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public int getBudgetScore() {
        return budgetScore;
    }

    public void setBudgetScore(int budgetScore) {
        this.budgetScore = budgetScore;
    }

    public int getNotBudgetScore() {
        return notBudgetScore;
    }

    public void setNotBudgetScore(int notBudgetScore) {
        this.notBudgetScore = notBudgetScore;
    }
}
