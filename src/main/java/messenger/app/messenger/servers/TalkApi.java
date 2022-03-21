package messenger.app.messenger.servers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import messenger.app.messenger.models.Message;
import messenger.app.messenger.models.Talk;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Optional;

public class TalkApi extends RestApi {

    public ArrayList<Talk> getTalks() {
        try {
            HttpResponse<JsonNode> jsonResponse
                    = Unirest.get(getUrl("talks/user/talks"))
                    .header("Authorization", AuthToken.getToken())
                    .asJson();
            Type talkListType = new TypeToken<ArrayList<Talk>>(){}.getType();
            return new Gson().fromJson(jsonResponse.getBody().toString(), talkListType);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public ArrayList<Message> getTalkMessage(Talk talk) {
        try {
            HttpResponse<JsonNode> jsonResponse
                    = Unirest.get(getUrl(String.format("talks/%d/messages", talk.getId())))
                    .header("Authorization", AuthToken.getToken())
                    .asJson();
            Type messageListType = new TypeToken<ArrayList<Message>>(){}.getType();
            return new Gson().fromJson(jsonResponse.getBody().toString(), messageListType);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public boolean removeTalk(Talk talk) {
        try {
            HttpResponse<JsonNode> jsonResponse
                    = Unirest.delete(getUrl(String.format("talks/%d", talk.getId())))
                    .header("Authorization", AuthToken.getToken())
                    .asJson();
            return true;
        } catch (UnirestException e) {
            e.printStackTrace();
            return false;
        }
    }
}
