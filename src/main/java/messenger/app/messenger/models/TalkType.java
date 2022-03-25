package messenger.app.messenger.models;

public enum TalkType {
    publicTalk,
    privateTalk;

    private int value;

    public int getResponse() {
        return value;
    }
}
