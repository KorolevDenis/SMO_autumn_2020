package app.SMO;

public enum Type {
    Request("Request"),
    HandleRelease("HandleRelease");

    private final String name;

    Type(String s) {
        name = s;
    }

    public String toString() {
        return this.name;
    }
}