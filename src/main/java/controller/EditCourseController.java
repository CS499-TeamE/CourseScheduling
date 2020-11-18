package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Course;
import model.Room;

import javax.tools.Tool;
import java.util.Comparator;
import java.util.List;

public class EditCourseController {
    @FXML private TextField courseIdText;
    @FXML private TextField courseCapText;
    @FXML private ComboBox<Room> roomComboBox;
    @FXML private Button submit;
    @FXML private Button cancel;
    @FXML private Label courseGuiLabel;
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

    public void addListeners() {
        courseCapText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    courseCapText.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public void initialize(Course course, List<Course> courseList, List<Room> roomList) {
        this.addListeners();
        this.courseIdText.setTooltip(new Tooltip("Add a course ID."));
        this.courseCapText.setTooltip(new Tooltip("Add the max number of students for this course."));
        this.roomComboBox.setTooltip(new Tooltip("Contains a list of room preferences that this course can have."));
        this.submit.setTooltip(new Tooltip("Add this course to the list of courses."));
        this.cancel.setTooltip(new Tooltip("Back to department editor without adding course."));

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

        this.courseGuiLabel.setText("Add Course");

        if (edit) {
            this.courseIdText.setTooltip(new Tooltip("Edit the course ID."));
            this.courseCapText.setTooltip(new Tooltip("Edit the max number of students for this course."));
            this.submit.setTooltip(new Tooltip("Submit changes of this course."));
            this.cancel.setTooltip(new Tooltip("Back to department editor without editing course."));
            this.courseGuiLabel.setText("Edit Course");

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
            alert.setHeaderText("Cannot submit with empty fields.");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add("darktheme.css");
            alert.showAndWait();

        }
        else {
            for (Course course : this.courseList) {
                if (edit) {

                    if (courseIdText.getText().equals(course.getCourseId())
                            && !this.course.getCourseId().equals(courseIdText.getText())) {
                        String headerText = "Course ID: " + courseIdText.getText() + " already exists.";
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(headerText);
                        DialogPane dialogPane = alert.getDialogPane();
                        dialogPane.getStylesheets().add("darktheme.css");
                        alert.showAndWait();
                        return;
                    }

                } else {

                    if (courseIdText.getText().equals(course.getCourseId())) {
                        String headerText = "Course ID: " + courseIdText.getText() + " already exists.";
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(headerText);
                        DialogPane dialogPane = alert.getDialogPane();
                        dialogPane.getStylesheets().add("darktheme.css");
                        alert.showAndWait();
                        return;
                    }

                }
            }

            if (Integer.parseInt(this.courseCapText.getText()) <= 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Course capacity cannot be less than or equal to 0.");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add("darktheme.css");
                alert.showAndWait();
                return;
            }

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
