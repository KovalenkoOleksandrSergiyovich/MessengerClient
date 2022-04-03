package messenger.app.messenger.controllers;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import messenger.app.messenger.models.*;
import messenger.app.messenger.servers.*;
import org.json.JSONObject;

import java.util.ArrayList;


public class TalkViewController extends IBaseController {
    @FXML
    public TextArea messageArea;
    @FXML
    public VBox messagesList;
    @FXML
    public Label talkTitle;
    @FXML
    public ComboBox<TalkUser> talkUserList;

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
        if (currentTalk != null) {
            setTalkTitle();
            if (currentTalk.getType() == TalkType.publicTalk) {
                ObservableList<TalkUser> talkUsers = FXCollections.observableArrayList(currentTalk.getTalkUsers());
                talkUserList.setItems(talkUsers);
                talkUserList.setVisible(true);
            } else {
                talkUserList.setVisible(false);
            }

            talkWebSocket.onJoinTalk(currentTalk);
            if (!setUpMessengerListeners) {
                listenToMessenger();
                setUpMessengerListeners = true;
            }
            getTalkMessages();
        }
    }

    private void getTalkMessages() {
        messagesList.getChildren().clear();
        messages = talkApi.getTalkMessage(currentTalk);
        messages.forEach(this::addToList);
        messengerViewPanel.setVvalue(1.0);
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
                    messages.add(message);
                    addToList(message);
                }
            });

        });

        talkWebSocket.listenToRemoveMessage(data -> {
            Message message = new Gson().fromJson(((JSONObject) data[0]).toString(), Message.class);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    removeMessageFromList(message);
                }
            });

        });
    }

    public void removeMessageFromList(Message message) {
        messages.removeIf(msg -> msg.getId() == message.getId());
        messagesList.getChildren().clear();
        messages.forEach(this::addToList);
    }

    private void addToList(Message message) {
        messagesList.getChildren().add(createMessengerContainer(message));
    }

    private HBox createMessengerContainer(Message message) {
        HBox messageBox = new HBox();
        Label messageContainer = new Label();

        if (currentTalk.getType() == TalkType.publicTalk) {
            User messengerWriter = currentTalk.getTalkUser(message.getUserId());
            Label userNameLabel = new Label();
            userNameLabel.setText(messengerWriter != null ? messengerWriter.getUsername() : "visitor");
            userNameLabel.setPadding(new Insets(3, 10, 3, 0));
            userNameLabel.setTextFill(Color.rgb(205,205,205));
            messageBox.getChildren().add(userNameLabel);
        }
        messageContainer.setText(message.getText());
        messageContainer.setPadding(new Insets(3, 5, 3, 5));
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

        if (userCanDeleteMessage(message)) {
            ContextMenu contextMenu = new ContextMenu();
            MenuItem deleteItem = new MenuItem();

            deleteItem.textProperty().bind(Bindings.format("Remove message"));
            deleteItem.setOnAction(event -> {
                if (new TalkConfirmService().confirmRemoveMessage(currentTalk, message)) {
                    talkWebSocket.onRemoveMessage(currentTalk, message.getId());
                }
            });
            contextMenu.getItems().addAll(deleteItem);
            messageContainer.setContextMenu(contextMenu);
        }
        messageBox.getChildren().add(messageContainer);
        return messageBox;
    }

    private boolean userCanDeleteMessage(Message message) {
        return message.getUserId() == AuthToken.getUserId()
                || (currentTalk.getType() == TalkType.publicTalk
                && currentTalk.checkIfUserAdmin(AuthToken.getUserId()));
    }
}
