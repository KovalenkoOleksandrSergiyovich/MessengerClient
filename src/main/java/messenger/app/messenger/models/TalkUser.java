package messenger.app.messenger.models;

public class TalkUser {
    User user;
    UserStatus status;

    @Override
    public String toString() {
        return user.username;
    }
}
