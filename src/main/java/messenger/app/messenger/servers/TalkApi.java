package messenger.app.messenger.servers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import messenger.app.messenger.models.*;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TalkApi extends RestApi {


    public ArrayList<Talk> getPublicTalks(String title) {
        try {
            HttpResponse<JsonNode> jsonResponse
                    = Unirest.get(getUrl("talks/public"))
                    .header("Authorization", AuthToken.getToken())
                    .queryString("title", title)
                    .asJson();
            Type talkListType = new TypeToken<ArrayList<Talk>>(){}.getType();
            return new Gson().fromJson(jsonResponse.getBody().toString(), talkListType);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public ArrayList<Talk> getUserTalks() {
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

    public Talk createTalk(String title, TalkType type) {
        try {
            HttpResponse<JsonNode> jsonResponse
                    = Unirest.post(getUrl("talks"))
                    .header("Authorization", AuthToken.getToken())
                    .field("title", title)
                    .field("imagePath", "")
                    .field("type", type.getResponse())
                    .asJson();
            return new Gson().fromJson(jsonResponse.getBody().toString(), Talk.class);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Talk createConversation(User user) {
        try {
            HttpResponse<JsonNode> jsonResponse
                    = Unirest.post(getUrl(String.format("talks/conversation/%d", user.getId())))
                    .header("Authorization", AuthToken.getToken())
                    .asJson();
            return new Gson().fromJson(jsonResponse.getBody().toString(), Talk.class);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Talk joinTalk(Talk talk) {
        try {
            HttpResponse<JsonNode> jsonResponse
                    = Unirest.post(getUrl(String.format("talks/%d/user", talk.getId())))
                    .header("Authorization", AuthToken.getToken())
                    .asJson();
            return new Gson().fromJson(jsonResponse.getBody().toString(), Talk.class);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
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

    public boolean removeMessage(Talk talk, Message message) {
        try {
            HttpResponse<JsonNode> jsonResponse
                    = Unirest.delete(getUrl(String.format("talks/%d/messages", talk.getId())))
                    .header("Authorization", AuthToken.getToken())
                    .field("messageId", message.getId())
                    .field("talkId", talk.getId())
                    .asJson();
            return true;
        } catch (UnirestException e) {
            e.printStackTrace();
            return false;
        }
    }
}
