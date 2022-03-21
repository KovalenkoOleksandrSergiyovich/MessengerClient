package messenger.app.messenger.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import messenger.app.messenger.servers.AuthApi;


public class AuthController {
    @FXML
    public TextField username;
    @FXML
    public TextField password;

    @FXML
    protected void onLoginButtonClick(ActionEvent actionEvent) {
        boolean isLoginSuccess = new AuthApi().login(username.getText(), password.getText());
        if (isLoginSuccess) {
            ScreenController.activate("main");
        }
    }

    public void onPasswordInput(InputMethodEvent inputMethodEvent) {
        System.out.println(inputMethodEvent.getTarget());
    }

    public void onCreateButtonClick(ActionEvent actionEvent) {
        ScreenController.updateScene("auth", "auth-registration");
    }
}
