package messenger.app.messenger.servers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import messenger.app.messenger.models.Talk;
import messenger.app.messenger.models.User;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class UsersApi extends RestApi {

    public ArrayList<User> getUsers(String username) {
        try {
            HttpResponse<JsonNode> jsonResponse
                    = Unirest.get(getUrl("users"))
                    .header("Authorization", AuthToken.getToken())
                    .queryString("username", username)
                    .asJson();
            Type usersListType = new TypeToken<ArrayList<User>>(){}.getType();
            return new Gson().fromJson(jsonResponse.getBody().toString(), usersListType);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
