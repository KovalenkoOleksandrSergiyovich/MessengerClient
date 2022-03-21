package messenger.app.messenger.controllers;

import javafx.event.ActionEvent;
public class AuthSuccess {
    public void onSignButtonClick(ActionEvent actionEvent) {
        ScreenController.updateScene("auth", "auth-login");
    }
}
