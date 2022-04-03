package messenger.app.messenger.controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import messenger.app.messenger.models.Talk;
import messenger.app.messenger.servers.TalkApi;
import messenger.app.messenger.servers.TalkConfirmService;
import messenger.app.messenger.servers.TalkWebSocket;
import messenger.app.messenger.servers.TalksStore;

import java.util.ArrayList;

public class MainPageController extends IBaseController {

    TalkApi apiService = new TalkApi();
    TalkWebSocket talkWebSocket = new TalkWebSocket();

    @FXML
    public ListView<Talk> talkListView = new ListView<Talk>();

    @FXML
    public ScrollPane mainPane;

    @FXML
    public void initialize() {
        ScreenController.addController("main", this);
    }

    public void screenActivated() {
        loadTalks();
    }

    protected void onMainPageLoad() {
        Pane createTalk = ScreenController.getScreen("create-talk");
        mainPane.setContent(createTalk);
        createTalk.prefWidthProperty().bind(mainPane.widthProperty());
        ScreenController.callInitControllerMethod("create-talk");
    }

    private void loadTalks() {
        ArrayList<Talk> talkList = apiService.getUserTalks();
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

    @Override
    void refresh() {
        super.refresh();
        loadTalks();
    }

    public void onListClick(MouseEvent mouseEvent) {
        Talk selectedTalk = talkListView.getSelectionModel().getSelectedItem();
        if (TalksStore.getActiveTalk() != null) {
            talkWebSocket.onLeaveTalk(TalksStore.getActiveTalk());
        }
        if (TalksStore.getActiveTalk() == null || !TalksStore.getActiveTalk().equals(selectedTalk)) {
            TalksStore.setActiveTalk(talkListView.getSelectionModel().getSelectedItem());
            Pane createTalk = ScreenController.getScreen("talk");
            mainPane.setContent(createTalk);
            createTalk.prefWidthProperty().bind(mainPane.widthProperty());
            ScreenController.callInitControllerMethod("talk");
        }
    }

    public void onLogOutClick(ActionEvent actionEvent) {
        ScreenController.activate("auth");
    }
}
