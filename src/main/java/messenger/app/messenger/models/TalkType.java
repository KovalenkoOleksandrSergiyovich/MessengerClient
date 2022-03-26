package messenger.app.messenger.models;

public enum TalkType {
    publicTalk,
    privateTalk;

    private int value;

    public int getResponse() {
        return value;
    }

    private static TalkType[] allValues = values();
    public static TalkType fromOrdinal(int n) {return allValues[n];}
}
