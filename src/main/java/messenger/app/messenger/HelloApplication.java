package messenger.app.messenger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import messenger.app.messenger.controllers.ScreenController;
import messenger.app.messenger.servers.AuthToken;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ScreenController.setStage(stage);
        FXMLLoader authLoader = new FXMLLoader(HelloApplication.class.getResource("auth-scene-view.fxml"));
        FXMLLoader mainLoader = new FXMLLoader(HelloApplication.class.getResource("main-page-view.fxml"));

        ScreenController.addScene("auth", new Scene(authLoader.load()));
        ScreenController.addScene("main", new Scene(mainLoader.load()));

        ScreenController.addScreen("auth-login", FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login-view.fxml"))));
        ScreenController.addScreen("auth-registration", FXMLLoader.load(Objects.requireNonNull(getClass().getResource("registration-view.fxml"))));
        ScreenController.addScreen("auth-success", FXMLLoader.load(Objects.requireNonNull(getClass().getResource("auth-success.fxml"))));
        ScreenController.addScreen("create-talk", FXMLLoader.load(Objects.requireNonNull(getClass().getResource("create-talk.fxml"))));

        ScreenController.updateScene("auth", "auth-login");
        ScreenController.activate("auth");

        AuthToken.getTokenFromFile();
        if (AuthToken.hasToken()) {
            ScreenController.activate("main");
        } else {
            ScreenController.activate("auth");
        };
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Messenger App");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}