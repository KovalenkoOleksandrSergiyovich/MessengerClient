package messenger.app.messenger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import messenger.app.messenger.controllers.ScreenController;
import messenger.app.messenger.servers.AuthToken;
import messenger.app.messenger.servers.Resources;
import messenger.app.messenger.servers.WebsocketClientEndpoint;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;

public class MessengerApplication extends Application {

   private WebsocketClientEndpoint websocket = new WebsocketClientEndpoint();

    @Override
    public void start(Stage stage) throws IOException {
        ScreenController.setStage(stage);
        FXMLLoader authLoader = new FXMLLoader(MessengerApplication.class.getResource("auth-scene-view.fxml"));
        FXMLLoader mainLoader = new FXMLLoader(MessengerApplication.class.getResource("main-page-view.fxml"));

        ScreenController.addScene("auth", new Scene(authLoader.load()));
        ScreenController.addScene("main", new Scene(mainLoader.load()));

        ScreenController.addScreen("auth-login", FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login-view.fxml"))));
        ScreenController.addScreen("auth-registration", FXMLLoader.load(Objects.requireNonNull(getClass().getResource("registration-view.fxml"))));
        ScreenController.addScreen("auth-success", FXMLLoader.load(Objects.requireNonNull(getClass().getResource("auth-success.fxml"))));
        ScreenController.addScreen("create-talk", FXMLLoader.load(Objects.requireNonNull(getClass().getResource("create-talk.fxml"))));
        ScreenController.addScreen("talk", FXMLLoader.load(Objects.requireNonNull(getClass().getResource("talk-view.fxml"))));

        ScreenController.updateScene("auth", "auth-login");
        ScreenController.activate("auth");

        AuthToken.getTokenFromFile();
        if (AuthToken.hasToken()) {
            ScreenController.activate("main");
        } else {
            ScreenController.activate("auth");
        };
        FXMLLoader fxmlLoader = new FXMLLoader(MessengerApplication.class.getResource("login-view.fxml"));
        initWebSocket();
        stage.setTitle("Messenger App");
        stage.show();
    }

    private void initWebSocket() {
        websocket.createSocket(URI.create("ws://localhost:3000"));
        websocket.connect();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        websocket.disconnect();
    }
}