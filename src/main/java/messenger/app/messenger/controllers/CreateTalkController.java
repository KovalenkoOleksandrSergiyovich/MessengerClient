package messenger.app.messenger.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import messenger.app.messenger.models.Talk;
import messenger.app.messenger.models.TalkType;
import messenger.app.messenger.models.User;
import messenger.app.messenger.servers.TalkApi;
import messenger.app.messenger.servers.UsersApi;

import java.util.ArrayList;

public class CreateTalkController extends IBaseController {

    @FXML
    public TextField searchTalkInput;

    @FXML
    public ComboBox<Talk> talksComboBox;

    @FXML
    public Button joinTalkBtn;

    @FXML
    public TextField talkTitleInput;

    IBaseController parentController;

    @FXML
    public TextField searchUserInput;
    @FXML
    public Button startConversationBtn;
    @FXML
    public ComboBox<User> usersComboBox;

    UsersApi usersApi = new UsersApi();
    TalkApi talkApi = new TalkApi();

    ArrayList<User> usersList = new ArrayList<>();
    ArrayList<Talk> talksList = new ArrayList<>();

    @FXML
    public void initialize() {
        ScreenController.addController("create-talk", this);
    }

    @Override
    void screenActivated() {
        parentController = ScreenController.getController("main");
        startConversationBtn.setDisable(true);
        joinTalkBtn.setDisable(true);
        downloadUsersList("");
        downloadTalksList("");
    }

    private void downloadUsersList(String username) {
        usersList = usersApi.getUsers(username);
        ObservableList<User> items = FXCollections.observableArrayList(usersList);
        usersComboBox.setItems(items);
    }

    private void downloadTalksList(String title) {
        talksList = talkApi.getPublicTalks(title);
        ObservableList<Talk> items = FXCollections.observableArrayList(talksList);
        talksComboBox.setItems(items);
    }

    public void onSearchUser() {
        downloadUsersList(searchUserInput.getText());
    }

    public void onSearchTalk() {
        downloadTalksList(searchTalkInput.getText());
    }

    public void userSelected(ActionEvent actionEvent) {
        startConversationBtn.setDisable(false);
    }

    public void talkSelected(ActionEvent actionEvent) {
        joinTalkBtn.setDisable(false);
    }

    public void onConversationButtonClick(ActionEvent actionEvent) {
        Talk talk = talkApi.createConversation(usersComboBox.getValue());
        if (talk != null && parentController != null) {
            parentController.refresh();
        }
    }

    public void onJoinButtonClick(ActionEvent actionEvent) {
        Talk talk = talkApi.joinTalk(talksComboBox.getValue());
        if (talk != null && parentController != null) {
            parentController.refresh();
        }
    }

    public void onCreateTalkClick(ActionEvent actionEvent) {
        Talk talk = talkApi.createTalk(talkTitleInput.getText(), TalkType.publicTalk);
        if (talk != null && parentController != null) {
            parentController.refresh();
        }
    }
}
