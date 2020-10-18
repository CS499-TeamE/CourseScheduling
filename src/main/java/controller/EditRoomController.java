package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Room;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class EditRoomController {
    @FXML private TextField roomNumText;
    @FXML private TextField roomCapText;
    @FXML private Room room;
    @FXML private List<Room> roomList;
    @FXML private Stage stage;
    @FXML private FinalizeInputController mainController;
    @FXML private Button submit;
    @FXML private Button cancel;
    private boolean edit;

    public void addListeners() {
        roomNumText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    roomNumText.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public void setRoom(Room room) {

        this.room = room;
    }

    public void setStage(Stage stage) {

        this.stage = stage;
    }

    public void setEdit(boolean bool) {

        this.edit = bool;
    }

    public void setMainController(FinalizeInputController controller) {

        this.mainController = controller;
    }

    public void initialize(Room room, List<Room> roomList) {
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.setRoom(room);
        this.roomList = roomList;

        if (edit) {
            this.roomNumText.setText(String.valueOf(this.room.getRoomNumber()));
            this.roomCapText.setText(String.valueOf(this.room.getRoomCapacity()));
        }
    }

    public void submit(ActionEvent actionEvent) throws IOException {
        if (this.roomNumText.getText() == null || this.roomNumText.getText().trim().isEmpty() ||
                this.roomCapText.getText() == null || this.roomCapText.getText().trim().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Empty fields");
            alert.showAndWait();

        } else {

            int roomNum = Integer.parseInt(this.roomNumText.getText());
            int roomCapacity = Integer.parseInt(this.roomCapText.getText());
            this.room.setRoomNumber(roomNum);
            this.room.setRoomCapacity(roomCapacity);

            if (!edit) {
                roomList.add(room);
            }

            Comparator<Room> comparator = Comparator.comparingInt(Room::getRoomNumber);
            roomList.sort(comparator);
            this.mainController.setRoomComboBoxItems(FXCollections.observableArrayList(roomList));
            this.mainController.roomComboBox.setDisable(false);
            this.mainController.roomEdit.setDisable(false);
            this.mainController.roomDelete.setDisable(false);

            if (roomList.size() == 1) {
                this.mainController.roomComboBox.getSelectionModel().selectFirst();
            }

            this.stage.close();
        }
    }

    public void cancel(ActionEvent actionEvent) {
        this.stage.close();
    }
}
