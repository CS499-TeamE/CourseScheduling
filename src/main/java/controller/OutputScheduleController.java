package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Department;
import model.FilePrinter;
import model.Schedule;
import org.apache.commons.csv.CSVFormat;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class OutputScheduleController {
    @FXML private Stage stage;
    @FXML private Button save;
    @FXML private Button backButton;
    @FXML private TextArea textArea;
    @FXML private ComboBox<Department> departmentComboBox;
    private List<Department> departmentList;
    private List<Schedule> scheduleList;

    public OutputScheduleController() {

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setDepartmentList(List<Department> departmentsList)
    {
        this.departmentList = departmentsList;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public void initialize(List<Department> departmentList, List<Schedule> scheduleList) {
        this.setDepartmentList(departmentList);
        this.setScheduleList(scheduleList);
        this.departmentComboBox.setItems(FXCollections.observableList(this.departmentList));
        this.departmentComboBox.getSelectionModel().selectFirst();
        this.textArea.setDisable(false);
        String printedSchedule = "";
        List<String> scheduleStringList = Schedule.getScheduleInfo(this.scheduleList.get(0));
        for(String string : scheduleStringList) {
            if (string == "") {
                printedSchedule = printedSchedule + string;
            } else {
                printedSchedule = printedSchedule + "\n" + string;
            }

        }
        this.textArea.setText(printedSchedule);
    }

    public void updateTextArea(ActionEvent actionEvent) {
        String printedSchedule = "";
        List<String> scheduleStringList = Schedule.getScheduleInfo(this.scheduleList.get(
                this.departmentComboBox.getSelectionModel().getSelectedIndex()));
        for(String string : scheduleStringList) {
            if (string == "") {
                printedSchedule = printedSchedule + string;
            } else {
                printedSchedule = printedSchedule + "\n" + string;
            }

        }
        this.textArea.setText(printedSchedule);
    }

    public void saveSchedules(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".csv", ".csv"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".tsv", ".tsv"));
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(file.getPath() + " has been saved.");
            alert.showAndWait();
            FilePrinter writer = new FilePrinter(this.departmentComboBox.getSelectionModel().getSelectedItem(),
                    this.scheduleList.get(this.departmentComboBox.getSelectionModel().getSelectedIndex()),
                    file);
            if(file.getName().contains(".tsv"))
            {
                writer.tsvPrinter(CSVFormat.TDF);
            }
            else
            {
                writer.csvPrinter(CSVFormat.EXCEL);
            }
        }
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure want to go back? Doing so will " +
                "lose all progress.", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FinalizeInput.fxml"));
            Parent pane = (Parent) fxmlLoader.load();
            ((FinalizeInputController) fxmlLoader.getController()).setStage(stage);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            resize();
            ((FinalizeInputController) fxmlLoader.getController()).initialize(departmentList);
        }
    }

    public void resize()
    {
        this.stage.sizeToScene();
    }

}
