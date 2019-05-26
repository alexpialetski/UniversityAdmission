package by.epam.pialetskialiaksei.model;

import by.epam.pialetskialiaksei.entity.FormOfEducation;
import by.epam.pialetskialiaksei.entity.MarksSum;
import by.epam.pialetskialiaksei.entity.User;


public class EntrantResultModel {
    private User user;
    private FormOfEducation formOfEducation;
    private MarksSum marksSum;

    public EntrantResultModel() {
    }

    public EntrantResultModel(User user, FormOfEducation formOfEducation, MarksSum marksSum) {
        this.user = user;
        this.formOfEducation = formOfEducation;
        this.marksSum = marksSum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public void setFormOfEducation(FormOfEducation formOfEducation) {
        this.formOfEducation = formOfEducation;
    }

    public MarksSum getMarksSum() {
        return marksSum;
    }

    public void setMarksSum(MarksSum marksSum) {
        this.marksSum = marksSum;
    }

    @Override
    public String toString() {
        return "EntrantResultModel{" +
                "user=" + user +
                ", formOfEducation=" + formOfEducation +
                ", marksSum=" + marksSum +
                '}';
    }
}
