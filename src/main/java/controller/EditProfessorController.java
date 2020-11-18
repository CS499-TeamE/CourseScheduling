package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Course;
import model.Professor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class EditProfessorController {
    @FXML private TextField professorNameText;
    @FXML private ComboBox<String> timeComboBox;
    @FXML private ComboBox<Course> taughtCourses;
    @FXML private ComboBox<Course> availableCourses;
    @FXML private Button submit;
    @FXML private Button cancel;
    @FXML private Button remove;
    @FXML private Button add;
    @FXML private Label professorGuiLabel;
    private Stage stage;
    private Professor professor;
    private List<Professor> professorList;
    private List<Course> courseList;
    private FinalizeInputController mainController;
    private boolean edit;

    public void setProfessor(Professor professor) {

        this.professor = professor;
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

    public void initialize(Professor professor, List<Professor> professorList, List<Course> courseList) {
        this.professorNameText.setTooltip(new Tooltip("Add the name of the professor."));
        this.taughtCourses.setTooltip(new Tooltip("Contains the list of courses this professor teaches."));
        this.availableCourses.setTooltip(new Tooltip("Contains the list of available courses this professor can teach."));
        this.timeComboBox.setTooltip((new Tooltip("Contains the list of available time preferences for this professor.")));
        this.submit.setTooltip(new Tooltip("Add this professor to the list of professors."));
        this.cancel.setTooltip(new Tooltip("Back to department editor without adding professor."));


        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.setProfessor(professor);
        this.professorList = professorList;
        this.courseList = courseList;
        this.availableCourses.setItems(FXCollections.observableArrayList(this.courseList));
        this.availableCourses.getSelectionModel().selectFirst();

        ArrayList<String> meetingTimes = new ArrayList<>();
        String[] times = {"None", "All Tues-Thurs classes", "All Mon-Wed classes", "Morning classes only",
                "Afternoon classes only", "Evening classes only"};
        meetingTimes.addAll(Arrays.asList(times));

        this.timeComboBox.setItems(FXCollections.observableArrayList(meetingTimes));
        this.timeComboBox.getSelectionModel().selectFirst();
        this.taughtCourses.setItems(FXCollections.observableArrayList(this.professor.getTaughtCourses()));
        this.taughtCourses.getSelectionModel().selectFirst();

        if (this.professor.getTaughtCourses().isEmpty()) {
            this.taughtCourses.setDisable(true);
            this.remove.setDisable(true);
        }

        this.professorGuiLabel.setText("Add Professor");

        if (edit) {
            this.submit.setTooltip(new Tooltip("Submit changes of this course."));
            this.cancel.setTooltip(new Tooltip("Back to department editor without editing professor."));
            this.professorNameText.setTooltip(new Tooltip("Edit the name of the professor."));
            this.professorGuiLabel.setText("Edit Professor");
            this.professorNameText.setText(this.professor.getName());

            if (this.professor.getPreference() == null) {
                this.timeComboBox.getSelectionModel().selectFirst();
            } else {
                this.timeComboBox.getSelectionModel().select(this.professor.getPreference());
            }
        }
    }

    public void addCourse(ActionEvent actionEvent) {
        if (this.taughtCourses.getItems().contains(this.availableCourses.getValue())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Professor already teaches this course.");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add("darktheme.css");
            alert.showAndWait();
            return;
        }

        this.professor.getTaughtCourses().add(this.availableCourses.getValue());
        Comparator<Course> comparator = Comparator.comparing(Course::getCourseId);
        this.professor.getTaughtCourses().sort(comparator);
        this.taughtCourses.setItems(FXCollections.observableArrayList(this.professor.getTaughtCourses()));
        if (this.taughtCourses.isDisable()) {
            this.taughtCourses.setDisable(false);
            this.remove.setDisable(false);
            this.taughtCourses.getSelectionModel().selectFirst();
        }
    }

    public void removeCourse(ActionEvent actionEvent) {
        Course course = this.taughtCourses.getValue();
        if (this.taughtCourses.getSelectionModel().isSelected(0)) {
            this.taughtCourses.getSelectionModel().selectNext();
        } else {
            this.taughtCourses.getSelectionModel().selectPrevious();
        }
        this.taughtCourses.getItems().remove(course);
        this.professor.getTaughtCourses().remove(course);
        if (this.professor.getTaughtCourses().isEmpty()) {
            this.taughtCourses.setDisable(true);
            this.remove.setDisable(true);
        }
    }

    public void submit(ActionEvent actionEvent) {
        if (this.professorNameText.getText().trim().isEmpty() || this.taughtCourses.getItems().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Cannot submit with empty fields.");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add("darktheme.css");
            alert.showAndWait();

        } else {
            for (Professor professor : this.professorList) {
                if (edit) {

                    if (professorNameText.getText().equals(professor.getName())
                            && !this.professor.getName().equals(professorNameText.getText())) {

                        String headerText = "Professor Name: " + professorNameText.getText() + " already exists.";
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(headerText);
                        DialogPane dialogPane = alert.getDialogPane();
                        dialogPane.getStylesheets().add("darktheme.css");
                        alert.showAndWait();
                        return;

                    }

                } else {

                    if (professorNameText.getText().equals(professor.getName())) {
                        String headerText = "Professor Name: " + professorNameText.getText() + " already exists.";
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(headerText);
                        DialogPane dialogPane = alert.getDialogPane();
                        dialogPane.getStylesheets().add("darktheme.css");
                        alert.showAndWait();
                        return;
                    }

                }
            }


            this.professor.setName(this.professorNameText.getText());
            this.professor.setPreference(this.timeComboBox.getValue());

            if (!edit) {
                this.professorList.add(this.professor);
            }

            Comparator<Professor> comparator = Comparator.comparing(Professor::getName);
            this.professorList.sort(comparator);

            this.mainController.setProfessorComboBoxItems(FXCollections.observableArrayList(this.professorList));
            this.mainController.professorComboBox.setDisable(false);
            this.mainController.professorEdit.setDisable(false);
            this.mainController.professorDelete.setDisable(false);

            if (this.professorList.size() == 1) {
                this.mainController.professorComboBox.getSelectionModel().selectFirst();
            }

            this.stage.close();
        }
    }

    public void cancel(ActionEvent actionEvent) {
        this.stage.close();
    }
}
