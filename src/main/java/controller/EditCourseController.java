package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Course;
import model.Room;

import java.util.Comparator;
import java.util.List;

public class EditCourseController {
    @FXML private TextField courseIdText;
    @FXML private TextField courseCapText;
    @FXML private ComboBox<Room> roomComboBox;
    @FXML private Button submit;
    @FXML private Button cancel;
    private Stage stage;
    @FXML private Course course;
    private FinalizeInputController mainController;
    private boolean edit;
    private List<Course> courseList;

    public void setCourse(Course course) {

        this.course = course;
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

    public void initialize(Course course, List<Course> courseList, List<Room> roomList) {
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.setCourse(course);
        this.courseList = courseList;
        this.roomComboBox.setItems(FXCollections.observableArrayList(roomList));
        Room emptyRoom = new Room();
        emptyRoom.setRoomNumber(0);
        this.roomComboBox.getItems().add(emptyRoom);
        this.roomComboBox.getSelectionModel().select(emptyRoom);

        if (roomList.isEmpty()) {
            this.roomComboBox.setDisable(true);
        }

        if (edit) {
            this.courseIdText.setText(String.valueOf(this.course.getCourseId()));
            this.courseCapText.setText(String.valueOf(this.course.getMaxEnrollment()));

            if (this.course.getRoomPreference() != 0) {
                Room roomPreference = null;
                for (Room room : roomList) {
                    if (room.getRoomNumber() == course.getRoomPreference()) {
                        roomPreference = room;
                        break;
                    }
                }
                this.roomComboBox.getSelectionModel().select(roomPreference);
            } else {
                this.roomComboBox.getSelectionModel().select(emptyRoom);
            }
        }
    }

    public void submit(ActionEvent actionEvent) {
        if (this.courseIdText.getText() == null || this.courseIdText.getText().trim().isEmpty() ||
        this.courseCapText.getText() == null || this.courseCapText.getText().trim().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Empty fields");
            alert.showAndWait();

        } else {

            this.course.setCourseId(this.courseIdText.getText());
            this.course.setMaxStudents(Integer.parseInt(this.courseCapText.getText()));
            this.course.setRoomPreference(this.roomComboBox.getValue().getRoomNumber());

            if (!edit) {
                this.courseList.add(this.course);
            }

            Comparator<Course> comparator = Comparator.comparing(Course::getCourseId);
            courseList.sort(comparator);
            this.mainController.setCourseComboBoxItems(FXCollections.observableArrayList(courseList));
            this.mainController.courseComboBox.setDisable(false);
            this.mainController.courseEdit.setDisable(false);
            this.mainController.courseDelete.setDisable(false);

            if (courseList.size() == 1) {
                this.mainController.courseComboBox.getSelectionModel().selectFirst();
            }

            this.stage.close();
        }
    }

    public void cancel(ActionEvent actionEvent) {
        this.stage.close();
    }

}
