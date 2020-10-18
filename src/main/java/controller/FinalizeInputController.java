package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import model.*;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class FinalizeInputController
{
    private Stage stage;
    @FXML private ComboBox<Department> departmentComboBox;
    @FXML public ComboBox<Course> courseComboBox;
    @FXML public ComboBox<Professor> professorComboBox;
    @FXML public ComboBox<Room> roomComboBox;
    @FXML public Button courseAdd;
    @FXML public Button courseEdit;
    @FXML public Button courseDelete;
    @FXML public Button professorAdd;
    @FXML public Button professorEdit;
    @FXML public Button professorDelete;
    @FXML public Button roomAdd;
    @FXML public Button roomEdit;
    @FXML public Button roomDelete;
    @FXML private Button submit;
    private List<Department> departments;
    private List<Room> roomList;
    private List<Course> courseList;
    private List<Professor> professorList;

    public FinalizeInputController() {

    }

    public void setStage(Stage stage) {
        this.stage = stage;

    }

    public void initialize(List<Department> departments) {
        this.departments = departments;
        this.departmentComboBox.setItems(FXCollections.observableArrayList(this.departments));
        this.setCombosDisable(true);
        this.setAllButtonsDisable(true);
    }

    public void updateDepartmentValues(ActionEvent actionEvent) {
        this.courseList = departmentComboBox.getValue().getCoursesList();
        Comparator<Course> courseComparator = Comparator.comparing(Course::getCourseId);
        courseList.sort(courseComparator);
        this.professorList = departmentComboBox.getValue().getProfessorList();
        Comparator<Professor> professorComparator = Comparator.comparing(Professor::getName);
        this.professorList.sort(professorComparator);
        this.roomList = departmentComboBox.getValue().getRoomsList();
        Comparator<Room> roomComparator = Comparator.comparingInt(Room::getRoomNumber);
        this.roomList.sort(roomComparator);
        this.courseComboBox.setItems(FXCollections.observableArrayList(courseList));
        this.professorComboBox.setItems(FXCollections.observableArrayList(professorList));
        this.roomComboBox.setItems(FXCollections.observableArrayList(roomList));
        this.setCombosDisable(false);
        this.roomAdd.setDisable(false);
        this.courseAdd.setDisable(false);
        this.professorAdd.setDisable(false);

        if (!courseList.isEmpty()) {
            this.courseComboBox.getSelectionModel().selectFirst();
            this.courseEdit.setDisable(false);
            this.courseDelete.setDisable(false);
        }

        if (!professorList.isEmpty()) {
            this.professorComboBox.getSelectionModel().selectFirst();
            this.professorEdit.setDisable(false);
            this.professorDelete.setDisable(false);
        }

        if (!roomList.isEmpty()) {
            this.roomComboBox.getSelectionModel().selectFirst();
            this.roomEdit.setDisable(false);
            this.roomDelete.setDisable(false);
        }
    }

    public void setCombosDisable(boolean bool) {
        if (!bool) {
            if (this.courseList.size() > 0) {
                this.courseComboBox.setDisable(false);
            }

            if (this.professorList.size() > 0) {
                this.professorComboBox.setDisable(false);
            }

            if (this.roomList.size() > 0) {
                this.roomComboBox.setDisable(false);
            }
        } else {
            this.courseComboBox.setDisable(true);
            this.professorComboBox.setDisable(true);
            this.roomComboBox.setDisable(true);
        }
    }

    public void courseValueChosen(ActionEvent actionEvent) {
        courseEdit.setDisable(false);
        courseDelete.setDisable(false);
    }

    public void professorValueChosen(ActionEvent actionEvent) {
        professorEdit.setDisable(false);
        professorDelete.setDisable(false);
    }

    public void roomValueChosen(ActionEvent actionEvent) {
        roomEdit.setDisable(false);
        roomDelete.setDisable(false);
    }

    public void setAllButtonsDisable(boolean bool) {
        courseAdd.setDisable(bool);
        courseEdit.setDisable(bool);
        courseDelete.setDisable(bool);
        professorAdd.setDisable(bool);
        professorEdit.setDisable(bool);
        professorDelete.setDisable(bool);
        roomAdd.setDisable(bool);
        roomEdit.setDisable(bool);
        roomDelete.setDisable(bool);
    }

    public void addCourse(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EditCourse.fxml"));
        Parent pane = (Parent) fxmlLoader.load();
        EditCourseController editCourse = fxmlLoader.getController();
        Stage courseStage = new Stage();
        editCourse.setStage(courseStage);
        editCourse.setEdit(false);
        editCourse.setMainController(this);
        editCourse.initialize(new Course(), this.courseList, this.roomList);
        Scene scene = new Scene(pane);
        courseStage.setScene(scene);
        courseStage.show();
    }

    public void editCourse(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EditCourse.fxml"));
        Parent pane = (Parent) fxmlLoader.load();
        EditCourseController editCourse = fxmlLoader.getController();
        Stage courseStage = new Stage();
        editCourse.setStage(courseStage);
        editCourse.setEdit(true);
        editCourse.setMainController(this);
        editCourse.initialize(this.courseComboBox.getValue(), this.courseList, this.roomList);
        Scene scene = new Scene(pane);
        courseStage.setScene(scene);
        courseStage.show();
    }

    public void deleteCourse(ActionEvent actionEvent) throws IOException {
        Course course = this.courseComboBox.getValue();

        if (this.courseComboBox.getSelectionModel().isSelected(0)) {
            this.courseComboBox.getSelectionModel().selectNext();
        } else {
            this.courseComboBox.getSelectionModel().selectPrevious();
        }

        this.courseComboBox.getItems().remove(course);
        this.courseList.remove(course);
        if (this.courseList.isEmpty()) {
            this.courseComboBox.setDisable(true);
            this.courseEdit.setDisable(true);
            this.courseDelete.setDisable(true);
        }
    }

    public void addProfessor(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EditProfessor.fxml"));
        Parent pane = (Parent) fxmlLoader.load();
        EditProfessorController editProfessor = fxmlLoader.getController();
        Stage professorStage = new Stage();
        editProfessor.setStage(professorStage);
        editProfessor.setEdit(false);
        editProfessor.setMainController(this);
        editProfessor.initialize(new Professor(), this.professorList, this.courseList);
        Scene scene = new Scene(pane);
        professorStage.setScene(scene);
        professorStage.show();
    }

    public void editProfessor(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EditProfessor.fxml"));
        Parent pane = (Parent) fxmlLoader.load();
        EditProfessorController editProfessor = fxmlLoader.getController();
        Stage professorStage = new Stage();
        editProfessor.setStage(professorStage);
        editProfessor.setEdit(true);
        editProfessor.setMainController(this);
        editProfessor.initialize(this.professorComboBox.getValue(), this.professorList, this.courseList);
        Scene scene = new Scene(pane);
        professorStage.setScene(scene);
        professorStage.show();
    }

    public void deleteProfessor(ActionEvent actionEvent) throws IOException {
        Professor professor = this.professorComboBox.getValue();

        if (this.professorComboBox.getSelectionModel().isSelected(0)) {
            this.professorComboBox.getSelectionModel().selectNext();
        } else {
            this.professorComboBox.getSelectionModel().selectPrevious();
        }

        this.professorComboBox.getItems().remove(professor);
        this.professorList.remove(professor);
        if (this.professorList.isEmpty()) {
            this.professorComboBox.setDisable(true);
            this.professorEdit.setDisable(true);
            this.professorDelete.setDisable(true);
        }
    }

    public void addRoom(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EditRoom.fxml"));
        Parent pane = (Parent) fxmlLoader.load();
        EditRoomController editRoom =  fxmlLoader.getController();
        Stage roomStage = new Stage();
        editRoom.setStage(roomStage);
        editRoom.setEdit(false);
        editRoom.initialize(new Room(), this.roomList);
        editRoom.setMainController(this);
        Scene scene = new Scene(pane);
        roomStage.setScene(scene);
        roomStage.show();
    }

    public void editRoom(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EditRoom.fxml"));
        Parent pane = (Parent) fxmlLoader.load();
        EditRoomController editRoom =  fxmlLoader.getController();
        Stage roomStage = new Stage();
        editRoom.setEdit(true);
        editRoom.setStage(roomStage);
        editRoom.initialize(this.roomComboBox.getValue(), this.roomList);
        editRoom.setMainController(this);
        Scene scene = new Scene(pane);
        roomStage.setScene(scene);
        roomStage.show();
    }

    public void deleteRoom(ActionEvent actionEvent) {
        Room room = this.roomComboBox.getValue();
        if (this.roomComboBox.getSelectionModel().isSelected(0)) {
            this.roomComboBox.getSelectionModel().selectNext();
        } else {
            this.roomComboBox.getSelectionModel().selectPrevious();
        }
        this.roomComboBox.getItems().remove(room);
        this.roomList.remove(room);
        if (this.roomList.isEmpty()) {
            this.roomComboBox.setDisable(true);
            this.roomEdit.setDisable(true);
            this.roomDelete.setDisable(true);
        }
    }

    public void submit(ActionEvent actionEvent) {
        for (Department department : departments) {
            department.printDepartmentInfo();
        }
    }

    public void setCourseComboBoxItems(ObservableList<Course> courseList) {
        this.courseComboBox.setItems(courseList);
    }

    public void setProfessorComboBoxItems(ObservableList<Professor> professorList) {
        this.professorComboBox.setItems(professorList);
    }

    public void setRoomComboBoxItems(ObservableList<Room> roomList) {
        this.roomComboBox.setItems(roomList);
    }

}
