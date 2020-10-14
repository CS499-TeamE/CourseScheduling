package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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

        if (edit) {
            this.professorNameText.setText(this.professor.getName());

            if (this.professor.getPreference() == null) {
                this.timeComboBox.getSelectionModel().selectFirst();
            } else {
                this.timeComboBox.getSelectionModel().select(this.professor.getPreference());
            }
        }
    }

    public void addCourse(ActionEvent actionEvent) {
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

    public void cancel(ActionEvent actionEvent) {
        this.stage.close();
    }
}
