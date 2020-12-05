package controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import model.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * Controls the FinalizeInput GUI. The FinalizeInput GUI lets the user view their current inputs for courses,
 * rooms, and professors and can add, delete, or edit them. Pressing the submit button generates the schedules for
 * the current available departments.
 */
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
    @FXML private Button backButton;



    private List<Department> departments;
    private List<Room> roomList;
    private List<Course> courseList;
    private List<Professor> professorList;
    private ArrayList<String> files;

    /**
     * Constructor for the FinalizeInputController
     */
    public FinalizeInputController() {

    }

    /**
     * Sets the stage for the FinalizeInputController
     * @param stage Stage object
     */
    public void setStage(Stage stage) {
        this.stage = stage;

    }

    /**
     * Initializes the FinalizeInputController by adding tool tips to buttons and comboboxes, and by
     * setting the current departments available for editing.
     * @param departments list of department objects
     */
    public void initialize(List<Department> departments) {
        submit.setTooltip(new Tooltip("Submit department info and generate schedules."));
        backButton.setTooltip(new Tooltip("Go back to choose input files for generating schedules."));

        departmentComboBox.setTooltip(new Tooltip("Contains a list of all the departments."));
        roomComboBox.setTooltip(new Tooltip("Contains a list of all the rooms for the selected department."));
        professorComboBox.setTooltip(new Tooltip("Contains a list of all the professors for the selected department."));
        courseComboBox.setTooltip(new Tooltip("Contains a list of all the courses for the selected department."));

        courseAdd.setTooltip(new Tooltip("Press this to add a new course to this department."));
        courseEdit.setTooltip(new Tooltip("Press this to edit the currently selected course."));
        courseDelete.setTooltip(new Tooltip("Press this to delete the currently selected course."));

        professorAdd.setTooltip(new Tooltip("Press this to add a new professor to this department."));
        professorEdit.setTooltip(new Tooltip("Press this to edit the currently selected professor."));
        professorDelete.setTooltip(new Tooltip("Press this to delete the currently selected professor."));

        roomAdd.setTooltip(new Tooltip("Press this to add a new room to this department."));
        roomEdit.setTooltip(new Tooltip("Press this to edit the currently selected room."));
        roomDelete.setTooltip(new Tooltip("Press this to delete the currently selected room."));

        this.departments = departments;
        this.departmentComboBox.setItems(FXCollections.observableArrayList(this.departments));
        this.setCombosDisable(true);
        this.setAllButtonsDisable(true);
    }

    /**
     * Updates the department values when a user changes values of the department combobox
     * @param actionEvent
     */
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

    /**
     * Sets the disable parameter of all the comboboxes
     * @param bool value of setDisable for comboboxes
     */
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

    /**
     * Enables the course edit and course delete buttons when the first course value is chosen
     * from the CourseValueCombobox
     * @param actionEvent
     */
    public void courseValueChosen(ActionEvent actionEvent) {
        courseEdit.setDisable(false);
        courseDelete.setDisable(false);
    }

    /**
     * Enables the professor edit and professor delete buttons when the first professor value is chosen
     * from the ProfessorValueCombobox
     * @param actionEvent
     */
    public void professorValueChosen(ActionEvent actionEvent) {
        professorEdit.setDisable(false);
        professorDelete.setDisable(false);
    }

    /**
     * Enables the room edit and room delete buttons when the first room value is chosen
     * from the RoomValueCombobox
     * @param actionEvent
     */
    public void roomValueChosen(ActionEvent actionEvent) {
        roomEdit.setDisable(false);
        roomDelete.setDisable(false);
    }

    /**
     * Sets the disable value of all buttons within the GUI
     * @param bool value of setDisable for all buttons
     */
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

    /**
     * When a user presses the Add course button, the FinalizeInputController launches the EditCourse GUI
     * with empty values
     * @param actionEvent
     * @throws IOException
     */
    public void addCourse(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EditCourse.fxml"));
        Parent pane = (Parent) fxmlLoader.load();
        EditCourseController editCourse = fxmlLoader.getController();
        Stage courseStage = new Stage();
        editCourse.setStage(courseStage);
        editCourse.setEdit(false);
        editCourse.setFinalizeInputController(this);
        editCourse.initialize(new Course(), this.courseList, this.roomList);
        Scene scene = new Scene(pane);
        courseStage.setScene(scene);
        courseStage.show();
    }

    /**
     * When a user presses the Edit course button, the FinalizeInputController launches the EditCourse GUI
     * with the values of the selected course
     * @param actionEvent
     * @throws IOException
     */
    public void editCourse(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EditCourse.fxml"));
        Parent pane = (Parent) fxmlLoader.load();
        EditCourseController editCourse = fxmlLoader.getController();
        Stage courseStage = new Stage();
        editCourse.setStage(courseStage);
        editCourse.setEdit(true);
        editCourse.setFinalizeInputController(this);
        editCourse.initialize(this.courseComboBox.getValue(), this.courseList, this.roomList);
        Scene scene = new Scene(pane);
        courseStage.setScene(scene);
        courseStage.show();
    }

    /**
     * Removes the currently selected course from the list of courses
     * @param actionEvent
     * @throws IOException
     */
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

    /**
     * When a user presses the Add Professor button, the FinalizeInputController launches the EditProfessor GUI
     * with empty values
     * @param actionEvent
     * @throws IOException
     */
    public void addProfessor(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EditProfessor.fxml"));
        Parent pane = (Parent) fxmlLoader.load();
        EditProfessorController editProfessor = fxmlLoader.getController();
        Stage professorStage = new Stage();
        editProfessor.setStage(professorStage);
        editProfessor.setEdit(false);
        editProfessor.setFinalizeInputController(this);
        editProfessor.initialize(new Professor(), this.professorList, this.courseList);
        Scene scene = new Scene(pane);
        professorStage.setScene(scene);
        professorStage.show();
    }

    /**
     * When a user presses the Edit professor button, the FinalizeInputController launches the EditProfessor GUI
     * with the values of the selected professor
     * @param actionEvent
     * @throws IOException
     */
    public void editProfessor(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EditProfessor.fxml"));
        Parent pane = (Parent) fxmlLoader.load();
        EditProfessorController editProfessor = fxmlLoader.getController();
        Stage professorStage = new Stage();
        editProfessor.setStage(professorStage);
        editProfessor.setEdit(true);
        editProfessor.setFinalizeInputController(this);
        editProfessor.initialize(this.professorComboBox.getValue(), this.professorList, this.courseList);
        Scene scene = new Scene(pane);
        professorStage.setScene(scene);
        professorStage.show();
    }

    /**
     * Removes the currently selected Professor from the list of Professors
     * @param actionEvent
     * @throws IOException
     */
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

    /**
     * When a user presses the Add room button, the FinalizeInputController launches the EditRoom GUI
     * with empty values
     * @param actionEvent
     * @throws IOException
     */
    public void addRoom(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EditRoom.fxml"));
        Parent pane = (Parent) fxmlLoader.load();
        EditRoomController editRoom =  fxmlLoader.getController();
        Stage roomStage = new Stage();
        editRoom.setStage(roomStage);
        editRoom.setEdit(false);
        editRoom.initialize(new Room(), this.roomList);
        editRoom.setFinalizeInputController(this);
        Scene scene = new Scene(pane);
        roomStage.setScene(scene);
        roomStage.show();
    }

    /**
     * When a user presses the Edit room button, the FinalizeInputController launches the EditRoom GUI
     * with the values of the selected room
     * @param actionEvent
     * @throws IOException
     */
    public void editRoom(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EditRoom.fxml"));
        Parent pane = (Parent) fxmlLoader.load();
        EditRoomController editRoom =  fxmlLoader.getController();
        Stage roomStage = new Stage();
        editRoom.setEdit(true);
        editRoom.setStage(roomStage);
        editRoom.initialize(this.roomComboBox.getValue(), this.roomList);
        editRoom.setFinalizeInputController(this);
        Scene scene = new Scene(pane);
        roomStage.setScene(scene);
        roomStage.show();
    }

    /**
     * Removes the currently selected room from the list of rooms
     * @param actionEvent
     * @throws IOException
     */
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

    /**
     * After the submit button is pressed, all departments are checked to make sure that there are no empty coure, room,
     * or department lists, and also checks to make sure none of the lists contain duplicate names. When all checks have
     * been passed, the OutputSchedule GUI is launched.
     * @param actionEvent
     * @throws IOException
     */
    public void submit(ActionEvent actionEvent) throws IOException, InterruptedException {
        for (Department department : departments) {
            if (department.getCoursesList().isEmpty()) {

                String headerText = department.getDepartmentName() + " department has no courses.";
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(headerText);
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add("darktheme.css");
                alert.showAndWait();
                return;

            }

            if (department.getProfessorList().isEmpty()) {

                String headerText = department.getDepartmentName() + " department has no professors.";
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(headerText);
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add("darktheme.css");
                alert.showAndWait();
                return;

            }

            if (department.getRoomsList().isEmpty()) {

                String headerText = department.getDepartmentName() + " department has no rooms.";
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(headerText);
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add("darktheme.css");
                alert.showAndWait();
                return;

            }

            for (int i = 0; i < department.getCoursesList().size(); i++) {
                for (int j = i + 1 ; j < department.getCoursesList().size(); j++) {

                    if (department.getCoursesList().get(i).getCourseId().equals(department.getCoursesList().get(j).getCourseId())) {

                        String headerText = department.getDepartmentName() + " contains duplicate course name: " +
                                "" + department.getCoursesList().get(i).getCourseId();
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(headerText);
                        DialogPane dialogPane = alert.getDialogPane();
                        dialogPane.getStylesheets().add("darktheme.css");
                        alert.showAndWait();
                        return;

                    }

                }
            }

            for (int i = 0; i < department.getRoomsList().size(); i++) {
                for (int j = i + 1 ; j < department.getRoomsList().size(); j++) {

                    if (department.getRoomsList().get(i).getRoomNumber() == department.getRoomsList().get(j).getRoomNumber()) {

                        String headerText = department.getDepartmentName() + " contains duplicate room number: " +
                                "" + department.getRoomsList().get(i).getRoomNumber();
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(headerText);
                        DialogPane dialogPane = alert.getDialogPane();
                        dialogPane.getStylesheets().add("darktheme.css");
                        alert.showAndWait();
                        return;

                    }

                }
            }

            for (int i = 0; i < department.getProfessorList().size(); i++) {
                for (int j = i + 1 ; j < department.getProfessorList().size(); j++) {

                    if (department.getProfessorList().get(i).getName().equals(department.getProfessorList().get(j).getName())) {

                        String headerText = department.getDepartmentName() + " contains duplicate professor name: " +
                                "" + department.getProfessorList().get(i).getName();
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(headerText);
                        DialogPane dialogPane = alert.getDialogPane();
                        dialogPane.getStylesheets().add("darktheme.css");
                        alert.showAndWait();
                        return;

                    }

                }
            }
        }


        MainController mainController = MainController.getInstance();
        mainController.initializePopulation(departments);

        List<Schedule> scheduleList = mainController.getScheduleList();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/OutputSchedule.fxml"));
        Parent pane = (Parent) fxmlLoader.load();
        ((OutputScheduleController) fxmlLoader.getController()).setStage(stage);
        ((OutputScheduleController) fxmlLoader.getController()).initialize(departments, scheduleList);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        resize();
    }

    /**
     * When the back button is pressed, a yes or no dialog box is launched and if yes the ChooseFile GUI is launched, if
     * no control is given back to the FinalizeInput GUI.
     * @param actionEvent
     * @throws IOException
     */
    public void back(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure want to go back? Doing so will " +
                "lose all progress.", ButtonType.YES, ButtonType.NO);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add("darktheme.css");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            MainController mainController = MainController.getInstance();
            mainController.getData().clear();
            departments.clear();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ChooseFile.fxml"));
            Parent pane = (Parent) fxmlLoader.load();
            ((ChooseFileController) fxmlLoader.getController()).setStage(stage);
            ((ChooseFileController) fxmlLoader.getController()).initializeTextFields();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            resize();
        }
    }

    /**
     * Sets the items of the CourseComboBox
     * @param courseList an ObservableList of course objects
     */
    public void setCourseComboBoxItems(ObservableList<Course> courseList) {
        this.courseComboBox.setItems(courseList);
    }

    /**
     * Sets the items of the ProfessorComboBox
     * @param professorList an ObservableList of professor objects
     */
    public void setProfessorComboBoxItems(ObservableList<Professor> professorList) {
        this.professorComboBox.setItems(professorList);
    }

    /**
     * Sets the items of the RoomComboBox
     * @param roomList an ObservableList of room objects
     */
    public void setRoomComboBoxItems(ObservableList<Room> roomList) {
        this.roomComboBox.setItems(roomList);
    }

    /**
     * Resizes the stage to the size of the scene
     */
    public void resize()
    {
        this.stage.sizeToScene();
    }



}
