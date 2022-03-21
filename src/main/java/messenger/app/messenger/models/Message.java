package messenger.app.messenger.models;

public class Message {
    int id;
    String text;
    String sendDateTime;
    int talkId;
    int userId;

    @Override
    public String toString() {
        return text;
    }
}
