package messenger.app.messenger.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import messenger.app.messenger.models.User;
import messenger.app.messenger.servers.AuthApi;
import messenger.app.messenger.servers.AuthToken;

public class RegistrationViewController {
    @FXML
    public TextField password;
    @FXML
    public TextField passwordRepeat;
    @FXML
    public TextField username;
    @FXML
    public Label errorMessage;

    public void onRegistrationClick(ActionEvent actionEvent) {
        if (isFormValid()) {
            createUserAccount();
        }
    }

    private boolean isFormValid() {
        if (username.getText().isEmpty()) {
            errorMessage.setText("Username required");
            return false;
        }
        if (password.getText().isEmpty()) {
            errorMessage.setText("Password required");
            return false;
        }
        if (password.getText().compareTo(passwordRepeat.getText()) !=0 ) {
            errorMessage.setText("Passwords must be same");
            return false;
        }
        errorMessage.setText("");
        return true;
    }

    private void createUserAccount() {
        User newUser = new AuthApi().registration(username.getText(), password.getText());
        if (newUser != null) {
            AuthToken.setUsername(newUser.toString());
            ScreenController.updateScene("auth", "auth-success");
        }
    }

    public void onSignInButtonClick(ActionEvent actionEvent) {
        ScreenController.updateScene("auth", "auth-login");
    }
}
