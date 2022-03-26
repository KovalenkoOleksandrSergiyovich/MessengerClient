package messenger.app.messenger.models;

public class Message {
    int id;
    String text;
    String sendDateTime;
    int talkId;
    int userId;
    User user;

    @Override
    public String toString() {
        return text;
    }

    public String getText() {
        return text;
    }

    public int getUserId() {
        return userId;
    }
    public int getId() {
        return id;
    }

   public void setUser(User user) {

   }
}
