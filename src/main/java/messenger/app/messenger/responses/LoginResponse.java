package messenger.app.messenger.responses;

import messenger.app.messenger.models.User;

public class LoginResponse {
    private String accessToken;
    private User user;

    public String getAccessToken() {
        return accessToken;
    }
    public User getUser() {
        return user;
    }
}
