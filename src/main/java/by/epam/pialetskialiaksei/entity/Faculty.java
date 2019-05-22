package by.epam.pialetskialiaksei.entity;

public class Faculty extends Entity {

    private static final long serialVersionUID = 1590962657803610445L;
    private String nameRu;
    private String nameEng;
    private String infoRu;
    private String infoEng;
    private byte budgetSeats;
    private byte totalSeats;
    private byte passingScore;

    public Faculty() {
    }

    public Faculty(String nameRu, String nameEng, String infoRu, String infoEng, byte passingScore, byte budgetSeats, byte totalSeats) {
        this.nameRu = nameRu;
        this.nameEng = nameEng;
        this.infoRu = infoRu;
        this.infoEng = infoEng;
        this.passingScore = passingScore;
        this.budgetSeats = budgetSeats;
        this.totalSeats = totalSeats;
    }
    public Faculty(String nameRu, String nameEng, String infoRu, String infoEng, byte budgetSeats, byte totalSeats) {
        this(nameRu, nameEng, infoRu, infoEng, (byte)0, budgetSeats, totalSeats);
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getInfoRu() {
        return infoRu;
    }

    public void setInfoRu(String infoRu) {
        this.infoRu = infoRu;
    }

    public String getInfoEng() {
        return infoEng;
    }

    public void setInfoEng(String infoEng) {
        this.infoEng = infoEng;
    }

    public byte getBudgetSeats() {
        return budgetSeats;
    }

    public void setBudgetSeats(byte budgetSeats) {
        this.budgetSeats = budgetSeats;
    }

    public byte getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(byte totalSeats) {
        this.totalSeats = totalSeats;
    }

    public byte getPassingScore() {
        return passingScore;
    }

    public void setPassingScore(byte passingScore) {
        this.passingScore = passingScore;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "nameRu='" + nameRu + '\'' +
                ", nameEng='" + nameEng + '\'' +
                ", infoRu='" + infoRu + '\'' +
                ", infoEng='" + infoEng + '\'' +
                ", budgetSeats=" + budgetSeats +
                ", totalSeats=" + totalSeats +
                ", passingScore=" + passingScore +
                "} " + super.toString();
    }
}
