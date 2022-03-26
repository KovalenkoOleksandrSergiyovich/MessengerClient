package messenger.app.messenger.models;

public class TalkUser {
    User user;
    int status;

    @Override
    public String toString() {
        return user.username;
    }

    public int getUserId() {
        return user.getId();
    }

    public User getUser() {
        return user;
    }

    public UserStatus getStatus() {
        return UserStatus.fromOrdinal(status);
    }
}
