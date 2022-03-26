package messenger.app.messenger.servers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import messenger.app.messenger.models.Message;
import messenger.app.messenger.models.Talk;

import java.util.Optional;

public class TalkConfirmService {
    public boolean confirmRemoveTalk(Talk talk) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove talk");
        alert.setHeaderText("Are you sure want to remove talk " + talk.getTitle() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return new TalkApi().removeTalk(talk);
        }
        return false;
    };


    public boolean confirmRemoveMessage(Talk talk, Message message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove message");
        alert.setHeaderText("Are you sure want to remove message from " + talk.getTitle() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return new TalkApi().removeMessage(talk, message);
        }
        return false;
    };
}
