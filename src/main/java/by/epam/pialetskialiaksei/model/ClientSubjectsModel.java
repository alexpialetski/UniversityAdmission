package by.epam.pialetskialiaksei.model;

import by.epam.pialetskialiaksei.entity.ClientSubject;

import java.util.List;

public class ClientSubjectsModel {
    protected List<ClientSubject> clientSubjects;

    public ClientSubjectsModel(List<ClientSubject> subjects) {
        this.clientSubjects = subjects;
    }

    public List<ClientSubject> getClientSubjects() {
        return clientSubjects;
    }

    public void setClientSubjects(List<ClientSubject> clientSubjects) {
        this.clientSubjects = clientSubjects;
    }

    @Override
    public String toString() {
        return "SubjectsModel{" +
                "clientSubjects=" + clientSubjects +
                '}';
    }
}
