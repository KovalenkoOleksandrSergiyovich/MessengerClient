package messenger.app.messenger.models;

public class User {
    Number id;
    String username;
    String lastLoginDateTime;

    @Override
    public String toString() {
        return username;
    }
}
