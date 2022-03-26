package messenger.app.messenger.models;

public enum UserStatus {
    user,
    admin;

    private int value;

    public int getResponse() {
        return value;
    }

    private static UserStatus[] allValues = values();
    public static UserStatus fromOrdinal(int n) {return allValues[n];}
}
