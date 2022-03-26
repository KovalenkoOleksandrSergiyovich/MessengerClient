package messenger.app.messenger.models;

import messenger.app.messenger.servers.AuthToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class Talk {
    int id;
    String title;
    int type;
    String imagePath;
    String creationDateTime ;

    TalkUser[] talkUsers = {};

    @Override
    public String toString() {
        return getTitle();
    }

    public TalkUser[] getNotCurrentUser() {
        return Arrays.stream(talkUsers).filter(user -> !Objects.equals(user.user.toString(), AuthToken.getUsername())).toArray(TalkUser[]::new);
    }

    public int getId() {
        return id;
    }

    public TalkType getType() {
        return TalkType.fromOrdinal(type);
    }

    public TalkUser[] getTalkUsers() {
        return talkUsers;
    }

    public String getTitle() {
        return title != null ? title : getNotCurrentUser()[0].toString();
    }

    public boolean checkIfUserAdmin(int userId) {
        return (Arrays.stream(talkUsers)
                .filter(userInTalk -> userInTalk.getStatus() == UserStatus.admin && userInTalk.getUserId() == userId)
                .toArray().length != 0);
    }

    public User getTalkUser(int userId) {
        Stream<TalkUser> talkUsers = Arrays.stream(this.talkUsers)
                .filter(userInTalk -> userInTalk.getUserId() == userId);
        return talkUsers.findFirst().get().getUser();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Talk talk = (Talk) o;
        return id == talk.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
