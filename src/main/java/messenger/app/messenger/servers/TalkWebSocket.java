package messenger.app.messenger.servers;

import io.socket.emitter.Emitter;
import messenger.app.messenger.models.Talk;
import org.json.JSONObject;

public class TalkWebSocket {
    WebsocketClientEndpoint websocketClient = new WebsocketClientEndpoint();

    public void onJoinTalk(Talk talk) {
        JSONObject object = new JSONObject();
        object.put("talk", talk.getId());
        object.put("user", AuthToken.getUserId());
        websocketClient.emitEvent("joinTalk", object);
    }

    public void onLeaveTalk(Talk talk) {
        JSONObject object = new JSONObject();
        object.put("talk", talk.getId());
        object.put("user", AuthToken.getUserId());
        websocketClient.emitEvent("leaveTalk", object);
    }

    public void onSendMessage(Talk talk, String message) {
        JSONObject object = new JSONObject();
        object.put("talk", talk.getId());
        object.put("user", AuthToken.getUserId());
        object.put("message", message);
        websocketClient.emitEvent("msgToServer", object);
    }

    public void listenToMessages(Emitter.Listener fn) {
        websocketClient.listenEvent("msgToClient", fn);
    }
}
