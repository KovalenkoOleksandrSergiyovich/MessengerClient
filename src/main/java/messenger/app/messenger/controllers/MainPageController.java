package messenger.app.messenger.controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import messenger.app.messenger.models.Message;
import messenger.app.messenger.models.Talk;
import messenger.app.messenger.servers.AuthToken;
import messenger.app.messenger.servers.TalkApi;
import messenger.app.messenger.servers.TalkConfirmService;

import java.util.ArrayList;

public class MainPageController extends IBaseController {

    TalkApi apiService = new TalkApi();

    @FXML
    public ListView<Talk> talkListView = new ListView<Talk>();

    @FXML
    public VBox messagesView;
    public ScrollPane mainPane;

    @FXML
    public void initialize() {
        ScreenController.addController("main", this);
    }

    public void screenActivated() {
        System.out.println("second");
        loadTalks();
    }

    protected void onMainPageLoad() {
        System.out.println("onMainPageLoad");
        Pane createTalk = ScreenController.getScreen("create-talk");
        System.out.println(createTalk);
        mainPane.setContent(createTalk);
    }

    private void loadTalks() {
        ArrayList<Talk> talkList = apiService.getTalks();
        ObservableList<Talk> items = FXCollections.observableArrayList (talkList);
        talkListView.setItems(items);
        addContestMenuToItems();

    }

    private void addContestMenuToItems() {
        talkListView.setCellFactory(lv -> {
            ListCell<Talk> cell = new ListCell<>();

            ContextMenu contextMenu = new ContextMenu();
            MenuItem deleteItem = new MenuItem();

            deleteItem.textProperty().bind(Bindings.format("Remove \"%s\"", cell.itemProperty()));
            deleteItem.setOnAction(event -> {
                if (new TalkConfirmService().confirmRemoveTalk(cell.getItem())) {
                    talkListView.getItems().remove(cell.getItem());
                }
            });
            contextMenu.getItems().addAll(deleteItem);

            cell.textProperty().bind(Bindings.
                    when(cell.emptyProperty()).
                    then("").
                    otherwise(Bindings.format("%s", cell.itemProperty())));

            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(contextMenu);
                }
            });
            return cell;
        });
    }

    public void onCreateTalkButtonClick(ActionEvent actionEvent) {
        onMainPageLoad();
    }

    public void onListClick(MouseEvent mouseEvent) {
        Talk selectedTalk = talkListView.getSelectionModel().getSelectedItem();
        ArrayList<Message> messageList = apiService.getTalkMessage(selectedTalk);
        messagesView.setVisible(true);
    }
}
