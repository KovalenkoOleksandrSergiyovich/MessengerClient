package messenger.app.messenger.servers;

import messenger.app.messenger.models.Talk;

public class TalksStore {
    static Talk activeTalk;

    public static void setActiveTalk(Talk talk) {
        activeTalk = talk;
    }

    public static Talk getActiveTalk() {
        return activeTalk;
    }
}
