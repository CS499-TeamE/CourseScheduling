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

/**
 * The EditProfessorController controls the EditProfessor GUI. EditProfessor GUI is launched from the FinalizeInput GUI
 * and is for changing the values of a currently existing professor or creating a new professor.
 */
public class EditProfessorController {
    @FXML private TextField professorNameText;
    @FXML private TextField meetingTimeTextField;
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
    private FinalizeInputController finalizeInputController;
    private boolean edit;

    /**
     * Sets the current Professor object being created or edited
     * @param professor Professor object being created or edited
     */
    public void setProfessor(Professor professor) {

        this.professor = professor;
    }

    /**
     * Sets the stage for the EditProfessorController
     * @param stage Stage object
     */
    public void setStage(Stage stage) {

        this.stage = stage;
    }

    /**
     * Sets the Edit flag for EditProfessorController. If edit is true then a professor is being edited. If edit is
     * false then a professor is being created.
     * @param bool a boolean object
     */
    public void setEdit(boolean bool) {

        this.edit = bool;
    }

    /**
     * Sets the FinalizeInputController object for the EditCourseController
     * @param controller FinalizeInputController object
     */
    public void setFinalizeInputController(FinalizeInputController controller) {

        this.finalizeInputController = controller;
    }

    /**
     * Sets the tools tips for all buttons and text fields. Also sets the professor object, courseList, and professorList.
     * @param professor Professor Object
     * @param courseList List of Course objects
     * @param professorList List of Professor objects
     */
    public void initialize(Professor professor, List<Professor> professorList, List<Course> courseList) {
        this.professorNameText.setTooltip(new Tooltip("Add the name of the professor."));
        this.taughtCourses.setTooltip(new Tooltip("Contains the list of courses this professor teaches."));
        this.availableCourses.setTooltip(new Tooltip("Contains the list of available courses this professor can teach."));
        this.meetingTimeTextField.setTooltip((new Tooltip("Contains the list of available time preferences for this professor.")));
        this.submit.setTooltip(new Tooltip("Add this professor to the list of professors."));
        this.cancel.setTooltip(new Tooltip("Back to department editor without adding professor."));


        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.setProfessor(professor);
        this.professorList = professorList;
        this.courseList = courseList;
        this.availableCourses.setItems(FXCollections.observableArrayList(this.courseList));
        this.availableCourses.getSelectionModel().selectFirst();
        this.meetingTimeTextField.setText("None");

//        ArrayList<String> meetingTimes = new ArrayList<>();
//        String[] times = {"None", "All Tues-Thurs classes", "All Mon-Wed classes", "Morning classes only",
//                "Afternoon classes only", "Evening classes only"};
//        meetingTimes.addAll(Arrays.asList(times));
//
//        this.timeComboBox.setItems(FXCollections.observableArrayList(meetingTimes));
//        this.timeComboBox.getSelectionModel().selectFirst();
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

            if (this.professor.getPreference() != null) {
//                this.meetingTimeTextField.getSelectionModel().selectFirst();
//            } else {
                this.meetingTimeTextField.setText(this.professor.getPreference());
            }
        }
    }

    /**
     * The currently selected Available Course is added to the Professor's Taught Courses. If the course is already in
     * the Professor's taught courses, then an error window will pop up.
     * @param actionEvent
     */
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

    /**
     * Removes the selected course from the Taught Courses combobox.
     * @param actionEvent
     */
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

    /**
     * When the submit button is pressed the input fields are checked to see that they aren't empty, there is no
     * Professor object that already has the same name, and that the taught courses combobox is not empty. If all checks
     * are passed then if a professor is being edited then the values will change to the new values, and if a professor
     * is being created then the new professor with the new values made will be added to the Department's course list.
     * @param actionEvent
     */
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
            this.professor.setPreference(this.meetingTimeTextField.getText());

            if (!edit) {
                this.professorList.add(this.professor);
            }

            Comparator<Professor> comparator = Comparator.comparing(Professor::getName);
            this.professorList.sort(comparator);

            this.finalizeInputController.setProfessorComboBoxItems(FXCollections.observableArrayList(this.professorList));
            this.finalizeInputController.professorComboBox.setDisable(false);
            this.finalizeInputController.professorEdit.setDisable(false);
            this.finalizeInputController.professorDelete.setDisable(false);

            if (this.professorList.size() == 1) {
                this.finalizeInputController.professorComboBox.getSelectionModel().selectFirst();
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
