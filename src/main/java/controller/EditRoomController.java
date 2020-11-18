package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    @FXML private Label roomGuiLabel;
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

        roomCapText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    roomCapText.setText(newValue.replaceAll("[^\\d]", ""));
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
        this.roomNumText.setTooltip(new Tooltip("Add a room number to this room."));
        this.roomCapText.setTooltip(new Tooltip("Add a capacity for how many students can fit in this room."));
        this.submit.setTooltip(new Tooltip("Add this room to the list of rooms."));
        this.cancel.setTooltip(new Tooltip("Back to department editor without adding room."));

        this.addListeners();
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.setRoom(room);
        this.roomList = roomList;
        this.roomGuiLabel.setText("Add Room");

        if (edit) {
            this.roomNumText.setTooltip(new Tooltip("Edit the room number of this room."));
            this.roomCapText.setTooltip(new Tooltip("Edit this capacity for how many students can fit in this room."));
            this.submit.setTooltip(new Tooltip("Submit the changes to this room."));
            this.cancel.setTooltip(new Tooltip("Back to department editor without editing room."));
            this.roomGuiLabel.setText("Edit Room");
            this.roomNumText.setText(String.valueOf(this.room.getRoomNumber()));
            this.roomCapText.setText(String.valueOf(this.room.getRoomCapacity()));
        }
    }

    public void submit(ActionEvent actionEvent) throws IOException {
        if (this.roomNumText.getText() == null || this.roomNumText.getText().trim().isEmpty() ||
                this.roomCapText.getText() == null || this.roomCapText.getText().trim().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Empty fields");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add("darktheme.css");
            alert.showAndWait();

        } else {
            int roomNum = Integer.parseInt(this.roomNumText.getText());
            int roomCapacity = Integer.parseInt(this.roomCapText.getText());

            for (Room room: roomList) {

                if (edit) {
                    if (room.getRoomNumber() == roomNum && room.getRoomNumber() != this.room.getRoomNumber()) {
                        String headerText = "Room number: " + roomNum + " already exists.";
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(headerText);
                        DialogPane dialogPane = alert.getDialogPane();
                        dialogPane.getStylesheets().add("darktheme.css");
                        alert.showAndWait();
                        return;
                    }
                } else {

                    if (room.getRoomNumber() == roomNum) {
                        String headerText = "Room number: " + roomNum + " already exists.";
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(headerText);
                        DialogPane dialogPane = alert.getDialogPane();
                        dialogPane.getStylesheets().add("darktheme.css");
                        alert.showAndWait();
                        return;
                    }
                }
            }

            if (Integer.parseInt(this.roomCapText.getText()) <= 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Room capacity cannot be less than or equal to 0.");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add("darktheme.css");
                alert.showAndWait();
                return;
            }

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
