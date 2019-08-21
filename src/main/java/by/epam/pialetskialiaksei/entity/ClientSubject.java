package by.epam.pialetskialiaksei.entity;

public class ClientSubject extends Entity {
    private static final long VersionUID = 2565574420335652970L;
    private Subject subject;
    private Mark mark;
    public ClientSubject(Subject subject, Mark mark){
        this.subject = subject;
        this.mark = mark;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "ClientSubject{" +
                "subject=" + subject +
                ", mark=" + mark +
                "} ";
    }
}
