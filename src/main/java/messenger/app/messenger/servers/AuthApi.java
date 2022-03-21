package messenger.app.messenger.servers;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import messenger.app.messenger.controllers.ScreenController;
import messenger.app.messenger.models.User;
import messenger.app.messenger.responses.LoginResponse;

public class AuthApi extends RestApi {

    public boolean login(String username, String password) {
        try {
            HttpResponse<JsonNode> jsonResponse
                    = Unirest.post("http://localhost:3000/auth/login")
                    .field("username", username)
                    .field("password", password)
                    .asJson();
            LoginResponse loginResponse =  new Gson().fromJson(jsonResponse.getBody().toString(), LoginResponse.class);
            AuthToken.setTokenAndName(loginResponse.getAccessToken(), loginResponse.getUser().toString());
            ScreenController.activate("main");
            return true;
        } catch (UnirestException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User registration(String username, String password) {
        try {
            HttpResponse<JsonNode> jsonResponse
                    = Unirest.post("http://localhost:3000/auth/registration")
                    .field("username", username)
                    .field("password", password)
                    .asJson();
            return new Gson().fromJson(jsonResponse.getBody().toString(), User.class);
        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }
    }
}
