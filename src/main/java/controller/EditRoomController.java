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

/**
 * The EditRoomController controls the EditRoom GUI. EditRoom GUI is launched from the FinalizeInput GUI
 * and is for changing the values of a currently existing room or creating a new room.
 */
public class EditRoomController {
    @FXML private TextField roomNumText;
    @FXML private TextField roomCapText;
    @FXML private Room room;
    @FXML private List<Room> roomList;
    @FXML private Stage stage;
    @FXML private FinalizeInputController finalizeInputController;
    @FXML private Button submit;
    @FXML private Button cancel;
    @FXML private Label roomGuiLabel;
    private boolean edit;

    /**
     * Adds a listener to the room capacity and room number text fields so that only numeric values can be input
     */
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

    /**
     * Sets the current Room object being created or edited
     * @param room room object being created or edited
     */
    public void setRoom(Room room) {

        this.room = room;
    }

    /**
     * Sets the stage for the EditRoomController
     * @param stage Stage object
     */
    public void setStage(Stage stage) {

        this.stage = stage;
    }

    /**
     * Sets the Edit flag for EditRoomController. If edit is true then a room is being edited. If edit is
     * false then a room is being created.
     * @param bool a boolean object
     */
    public void setEdit(boolean bool) {

        this.edit = bool;
    }

    /**
     * Sets the FinalizeInputController object for the EditRoomController
     * @param controller FinalizeInputController object
     */
    public void setFinalizeInputController(FinalizeInputController controller) {

        this.finalizeInputController = controller;
    }

    /**
     * Sets the tools tips for all buttons and text fields. Also sets the room object and roomList.
     * @param room Room Object
     * @param roomList List of Room objects
     */
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

    /**
     * When the submit button is pressed the input fields are checked to see that they aren't empty, there is no
     * Room object that already has the same name, and that the taught courses combobox is not empty. If all checks
     * are passed then if a room is being edited then the values will change to the new values, and if a room
     * is being created then the new room with the new values made will be added to the Department's course list.
     * @param actionEvent
     */
    public void submit(ActionEvent actionEvent) throws IOException {
        if (this.roomNumText.getText() == null || this.roomNumText.getText().trim().isEmpty() ||
                this.roomCapText.getText() == null || this.roomCapText.getText().trim().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Empty fields");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add("darktheme.css");
            alert.showAndWait();

        } else {
            String roomNum = this.roomNumText.getText();
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

            Comparator<Room> comparator = Comparator.comparing(Room::getRoomNumber);
            roomList.sort(comparator);
            this.finalizeInputController.setRoomComboBoxItems(FXCollections.observableArrayList(roomList));
            this.finalizeInputController.roomComboBox.setDisable(false);
            this.finalizeInputController.roomEdit.setDisable(false);
            this.finalizeInputController.roomDelete.setDisable(false);

            if (roomList.size() == 1) {
                this.finalizeInputController.roomComboBox.getSelectionModel().selectFirst();
            }

            this.stage.close();
        }
    }

    /**
     * When the cancel button is pressed nothing is changed and control is given back to the FinalizeInput GUI
     * @param actionEvent
     */
    public void cancel(ActionEvent actionEvent) {
        this.stage.close();
    }
}
