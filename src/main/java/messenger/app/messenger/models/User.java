package messenger.app.messenger.models;

public class User {
    int id;
    String username;
    String lastLoginDateTime;

    @Override
    public String toString() {
        return username;
    }

    public int getId() {
        System.out.printf(String.valueOf(id));
        return id;
    }

    public String getUsername() {
        return username;
    }
}
