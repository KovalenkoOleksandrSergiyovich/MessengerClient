package messenger.app.messenger.models;

import messenger.app.messenger.servers.AuthToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Talk {
    int id;
    String title = null;
    TalkType type = TalkType.privateTalk;
    String imagePath = null;
    String creationDateTime = null;

    TalkUser[] talkUsers = {};

    Talk ( int id, String title, TalkType type, String imagePath, String creationDateTime, TalkUser[] talkUsers) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.imagePath = imagePath;
        this.creationDateTime = creationDateTime;
        this.talkUsers = talkUsers;
    }

    @Override
    public String toString() {
        return title != null ? title : getNotCurrentUser()[0].toString();
    }

    public TalkUser[] getNotCurrentUser() {
        return Arrays.stream(talkUsers).filter(user -> !Objects.equals(user.user.toString(), AuthToken.getUsername())).toArray(TalkUser[]::new);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
