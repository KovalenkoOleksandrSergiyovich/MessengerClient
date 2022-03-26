module messenger.app.messenger {
    requires javafx.controls;
    requires javafx.fxml;

    requires unirest.java;
    requires com.google.gson;
    requires org.json;
    requires socket.io.client;
    requires engine.io.client;

    opens messenger.app.messenger.models to com.google.gson;
    opens messenger.app.messenger to javafx.fxml;
    opens messenger.app.messenger.controllers to javafx.fxml, com.google.gson;
    opens messenger.app.messenger.responses to javafx.fxml, com.google.gson;
    opens messenger.app.messenger.servers to javafx.fxml, com.google.gson;
    exports messenger.app.messenger;
}