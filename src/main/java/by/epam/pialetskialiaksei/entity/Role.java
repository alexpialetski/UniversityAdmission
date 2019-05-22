package by.epam.pialetskialiaksei.entity;

public enum Role {
    ADMIN(2), CLIENT(1);
    private final int val;
    Role(int v) { val = v; }
    public int getVal() { return val; }
    public String getName() {
        return name().toLowerCase();
    }
}
