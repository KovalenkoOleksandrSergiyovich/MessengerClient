module messenger.app.messenger {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    requires unirest.java;
    requires com.google.gson;
    requires org.json;
    requires javafx.web;
    requires socket.io.client;
    requires engine.io.client;
    opens messenger.app.messenger.models to com.google.gson;
    opens messenger.app.messenger to javafx.fxml;
    opens messenger.app.messenger.controllers to javafx.fxml, com.google.gson;
    opens messenger.app.messenger.responses to javafx.fxml, com.google.gson;
    opens messenger.app.messenger.servers to javafx.fxml, com.google.gson;
    exports messenger.app.messenger;
}