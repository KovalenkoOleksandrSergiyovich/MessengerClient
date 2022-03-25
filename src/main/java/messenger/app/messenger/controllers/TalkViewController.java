package messenger.app.messenger.controllers;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import messenger.app.messenger.models.Message;
import messenger.app.messenger.models.Talk;
import messenger.app.messenger.servers.AuthToken;
import messenger.app.messenger.servers.TalkApi;
import messenger.app.messenger.servers.TalkWebSocket;
import messenger.app.messenger.servers.TalksStore;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


public class TalkViewController extends IBaseController {
    @FXML
    public TextArea messageArea;
    public VBox messagesList;
    public Label talkTitle;

    TalkWebSocket talkWebSocket = new TalkWebSocket();
    TalkApi talkApi = new TalkApi();

    Talk currentTalk;
    boolean setUpMessengerListeners = false;
    ArrayList<Message> messages = new ArrayList<Message>();

    @FXML
    public ScrollPane messengerViewPanel;

    @FXML
    public void initialize() {
        ScreenController.addController("talk", this);
    }

    @Override
    void screenActivated() {
        currentTalk = TalksStore.getActiveTalk();
        setTalkTitle();
        talkWebSocket.onJoinTalk(currentTalk);
        if (!setUpMessengerListeners) {
            listenToMessenger();
            setUpMessengerListeners = true;
        }
        getTalkMessages();
    }

    private void getTalkMessages() {
        messages = talkApi.getTalkMessage(currentTalk);
        messages.forEach(this::addToList);
        System.out.println(messages);
    }

    private void setTalkTitle() {
        talkTitle.setText(currentTalk.getTitle());
    }

    public void onSendButtonClick(ActionEvent actionEvent) {
        String text = messageArea.getText();
        if (!text.isEmpty()) {
            talkWebSocket.onSendMessage(currentTalk, messageArea.getText());
            messageArea.setText("");
        }
    }

    private void listenToMessenger() {
        talkWebSocket.listenToMessages(data -> {
            Message message = new Gson().fromJson(((JSONObject) data[0]).toString(), Message.class);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    addToList(message);
                }
            });

        });
    }

    private void addToList(Message message) {
        messagesList.getChildren().add(createMessengerContainer(message));
    }

    private Label createMessengerContainer(Message message) {
        Label messageContainer = new Label();
        messageContainer.setText(message.getText());
        messageContainer.setPadding(new Insets(3, 5, 3, 5));
        Label lbl = new Label();
        Color col1 = Color.rgb(205,205,205);
        Color col2 = Color.rgb(183,244,255);
        CornerRadii corn = new CornerRadii(10);

        if (message.getUserId() == AuthToken.getUserId()) {
            messageContainer.setBackground(new Background(new BackgroundFill(col1, corn, Insets.EMPTY)));
            messageContainer.setAlignment(Pos.BASELINE_RIGHT);
        } else {
            messageContainer.setBackground(new Background(new BackgroundFill(col2, corn, Insets.EMPTY)));
            messageContainer.setAlignment(Pos.BASELINE_LEFT);
        }
        return messageContainer;
    }
}
