package messenger.app.messenger.servers;

import messenger.app.messenger.models.Talk;

import java.util.ArrayList;

public class TalksStore {
    static Talk activeTalk;
    static ArrayList<Talk> talks = new ArrayList<Talk>();

    public static void setTalks(ArrayList<Talk> talks) {
        TalksStore.talks = talks;
    }

    public static ArrayList<Talk> getTalks() {
        return TalksStore.talks;
    }

    public static void setActiveTalk(Talk talk) {
        activeTalk = talk;
    }

    public static Talk getActiveTalk() {
        return activeTalk;
    }
}
