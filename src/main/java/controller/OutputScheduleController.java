package controller;

import javafx.scene.Node;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.*;
import javafx.scene.Node;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;
import org.apache.commons.csv.CSVFormat;

import javax.swing.*;
import javax.tools.Tool;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.paint.Color.RED;

/**
 * The OutputScheduleController controls the OutputSchedule GUI. The OutputSchedule GUI displays the schedules that were
 * generated by the Schedule Algorithm. If the schedule has errors then the red thumbs down will be highlighted. If the
 * schedule that was generated has no errors then the green thumbs down will be highlighted. The OutputScheduleController
 * also deals with saving the schedule as a TSV or CSV and also printing the report as a PDF.
 */
public class OutputScheduleController {
    @FXML private Stage stage;
    @FXML private Button save;
    @FXML private Button backButton;
    @FXML private TextArea textArea;
    @FXML private Label thumbsUp;
    @FXML private Label thumbsDown;
    @FXML private TextFlow textFlow;
    @FXML private Button printButton;
    @FXML private ComboBox<Department> departmentComboBox;
    @FXML private ScrollPane scheduleScroll;
    private List<Department> departmentList;
    private List<Schedule> scheduleList;

    /**
     * Constructor for the OutputScheduleController
     */
    public OutputScheduleController() {

    }

    /**
     * Sets the stage for the OutputScheduleController
     * @param stage Stage object
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Sets the list of Departments for the OutputScheduleController
     * @param departmentsList List of Department Objects
     */
    public void setDepartmentList(List<Department> departmentsList)
    {
        this.departmentList = departmentsList;
    }

    /**
     * Sets the list of Schedules for the OutputScheduleController
     * @param scheduleList list of Schedule objects
     */
    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    /**
     * Initializes the OutputScheduleController, and adds tool tips for all buttons, text areas, and combo box.
     * @param departmentList List of Department objects
     * @param scheduleList List of Schedule objects
     */
    public void initialize(List<Department> departmentList, List<Schedule> scheduleList) {
        this.textArea.setEditable(false);
        this.backButton.setTooltip(new Tooltip("Go back to the Department Editor."));
        this.save.setTooltip(new Tooltip("Save schedule as a TSV or CSV file."));
        this.printButton.setTooltip(new Tooltip("Print the report."));
        this.departmentComboBox.setTooltip(new Tooltip("Choose which department's schedule to view."));

        this.setDepartmentList(departmentList);
        this.setScheduleList(scheduleList);
        this.departmentComboBox.setItems(FXCollections.observableList(this.departmentList));
        this.departmentComboBox.getSelectionModel().selectFirst();
        this.textArea.setDisable(false);
        String printedSchedule = "";

        getHeaders();
        for(PossibleClass n : this.scheduleList.get(0).getClassList())
        {
            Text text = new Text(n.getClassInfo() + "\n");
            if(n.isHasConflict() == true)
            {
                text.setFill(RED);
            }
            this.textFlow.getChildren().add(text);
        }
        updateIcons();
        printErrors();
    }

    /**
     *
     * @param actionEvent
     */
    public void updateTextArea(ActionEvent actionEvent)
    {
        textFlow.getChildren().clear();

        getHeaders();
        for(PossibleClass n : this.scheduleList.get(this.departmentComboBox.getSelectionModel().getSelectedIndex()).getClassList())
        {
            Text text = new Text(n.getClassInfo() + "\n");
            if(n.isHasConflict() == true)
            {
                text.setFill(RED);
            }
            textFlow.getChildren().add(text);
        }
        updateIcons();
        printErrors();
    }

    private void getHeaders()
    {
        Text text = new Text("Course\t|\t" + "Max Attendance\t|\t" + "Room\t|\t" + "Room Capacity\t|\t" + "Professor\t\t\t|\t" + "Meeting Time\t\n");
        this.textFlow.getChildren().add(text);
    }


    public void updateIcons()
    {
        if(this.scheduleList.get(this.departmentComboBox.getSelectionModel().getSelectedIndex()).getFitness() == 1.0)
        {
            thumbsUp.setDisable(false);
            thumbsDown.setDisable(true);
        }
        else
        {
            thumbsDown.setDisable(false);
            thumbsUp.setDisable(true);
        }
    }

    public void saveSchedules(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure want to save? " +
                "Schedule contains errors.", ButtonType.YES, ButtonType.NO);
        if (!thumbsDown.isDisable()) {
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add("darktheme.css");
            alert.showAndWait();
        }

        if (thumbsDown.isDisable() || alert.getResult() == ButtonType.YES) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".csv", ".csv"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".tsv", ".tsv"));
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setHeaderText(file.getPath() + " has been saved.");
                alert2.showAndWait();
                FilePrinter writer = new FilePrinter(this.departmentComboBox.getSelectionModel().getSelectedItem(),
                        this.scheduleList.get(this.departmentComboBox.getSelectionModel().getSelectedIndex()),
                        file);
                if (file.getName().contains(".tsv")) {
                    writer.tsvPrinter(CSVFormat.TDF);
                } else {
                    writer.csvPrinter(CSVFormat.EXCEL);
                }
            }
        }
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure want to go back? Doing so will " +
                "lose all progress.", ButtonType.YES, ButtonType.NO);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add("darktheme.css");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            MainController.getInstance().getScheduleList().clear();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FinalizeInput.fxml"));
            Parent pane = (Parent) fxmlLoader.load();
            ((FinalizeInputController) fxmlLoader.getController()).setStage(stage);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            resize();
            ((FinalizeInputController) fxmlLoader.getController()).initialize(departmentList);
        }
    }
    public void backToStart(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure want to go back to the start? " +
                "Doing so will lose all progress.", ButtonType.YES, ButtonType.NO);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add("darktheme.css");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            MainController.getInstance().getScheduleList().clear();
            MainController mainController = MainController.getInstance();
            mainController.getData().clear();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ChooseFile.fxml"));
            Parent pane = (Parent) fxmlLoader.load();
            ((ChooseFileController) fxmlLoader.getController()).setStage(stage);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            resize();
        }
    }

    public void print(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure want to print? " +
                "Schedule contains errors.", ButtonType.YES, ButtonType.NO);
        if (!thumbsDown.isDisable()) {
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add("darktheme.css");
            alert.showAndWait();
        }

        if (thumbsDown.isDisable() || alert.getResult() == ButtonType.YES) {
            printButton.setDisable(true);//disable button
            JTextPane jtp = new JTextPane();
            //jtp.setBackground(Color.white);
            StringBuilder sb = new StringBuilder();
            for (Node node : textFlow.getChildren()) {
                if (node instanceof Text) {
                    sb.append(((Text) node).getText());
                }
            }
            String fullText = sb.toString();
            Printify printData = new Printify(fullText, this.departmentComboBox.getSelectionModel().getSelectedItem().toString());
            jtp.setText(printData.getPrintData());

            //jtp.setText(fullText);
            boolean show = true;
            try {
                jtp.print(null, null, show, null, null, show);
                printButton.setDisable(false);
            } catch (java.awt.print.PrinterException ex) {
                ex.printStackTrace();
                Alert alert2 = new Alert(Alert.AlertType.ERROR, "Unable to Print");
                DialogPane dialogPane = alert2.getDialogPane();
                dialogPane.getStylesheets().add("darktheme.css");
                alert2.showAndWait();
            }
            printButton.setDisable(false);//re-enable button
        }
    }

    public void resize()
    {
        this.stage.sizeToScene();
    }

    private void printErrors()
    {
        List<String> errors = new ArrayList<>();
        errors = this.scheduleList.get(this.departmentComboBox.getSelectionModel().getSelectedIndex()).getConflicts();

        for(String error : errors)
        {
            textArea.appendText(error);
        }
    }



}
