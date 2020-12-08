package controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Department;

import javax.tools.Tool;

@SuppressWarnings("unchecked")
/**
 * Controls the ChooseFile GUI. The ChooseFile GUI is the first thing the user will see when the application is loaded.
 * The ChooseFile GUI allows the user to select up to ten input files to generate schedules for.
 */
public class ChooseFileController {
    @FXML private Button clearButton;
    @FXML private AnchorPane anchorPane;
    @FXML private BorderPane borderPane;
    @FXML private TextField textField;
    @FXML private Button submitButton;
    @FXML private Button addInputButton;
    @FXML private HBox hBoxMid;
    @FXML private HBox hBoxTop;
    @FXML private VBox vBoxMid;


    private List<TextField> textFieldList = new ArrayList<>();
    private List<String> files = new ArrayList<>();
    private Stage stage;


    /**
     * Creates the first input field that cannot ever be removed and also adds tool tips.
     */
    public void initialize() {
        this.addInputButton.setTooltip(new Tooltip("Press this to add a new field for an input file."));
        this.submitButton.setTooltip(new Tooltip("Pres this to submit all selected files for scheduling."));

        HBox newHBox = new HBox();
        // Create dynamic components
        Label newLabel = new Label("File Path : ");
        Button newFileButton = new Button("Select File");
        newFileButton.setTooltip(new Tooltip("Press this to select an input file."));
        Button newClearButton = new Button("Clear");
        newClearButton.setTooltip(new Tooltip("Press this to clear the file path."));
        TextField newTextField = new TextField();
        newTextField.clear();
        textFieldList.add(newTextField);

        // Set actions of new buttons
        newFileButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("CSV Files", "*.csv")
                        , new FileChooser.ExtensionFilter("TSV Files", "*.tsv")
                );

                fileChooser.setTitle("Open Input File");
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    newTextField.setText(file.getAbsolutePath());
                }
            }
        });

        newClearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //textFieldList.remove(newTextField);
                newTextField.clear();
                resize();
            }
        });

        // Add new elements to hBox
        newHBox.getChildren().add(newLabel);
        newHBox.getChildren().add(newTextField);
        newHBox.getChildren().add(newFileButton);
        newHBox.getChildren().add(newClearButton);
        newHBox.setSpacing(15);
        Insets insets = new Insets(10,10,10,10);
        newHBox.setPadding(insets);

        ScrollPane scrollPane = new ScrollPane(newHBox);
        scrollPane.setFitToHeight(true);
        vBoxMid.getChildren().add(newHBox);
    }

    /**
     * Sets the stage for the ChooseFileController object
     * @param stage Stage object
     */
    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    /**
     * When the selectFile button is clicked, the JavaFX FileChooser object is opened with CSV and TSV extension filters
     * added.
     * @param actionEvent
     */
    public void selectFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
                , new FileChooser.ExtensionFilter("TSV Files", "*.tsv")
        );

        fileChooser.setTitle("Open Input File");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            textField.setText(file.getAbsolutePath());
        }
    }

    /**
     * When submit is pressed, all input file paths given are checked to see if they exist, and that there are no empty
     * fields. If all checks are passed the input files are passed to the parser and the FinalizeInput GUI is launched.
     * @param actionEvent
     * @throws IOException
     */
    public void submit(ActionEvent actionEvent) throws IOException {
        Boolean missingFiles = false;
        for(TextField textField : textFieldList) {
            System.out.println(textField.getText());
            if (textField.getText().equals("")) {
                missingFiles = true;
                break;
            }
        }

        if (!(textFieldList.isEmpty()) && !missingFiles) {

            for (int i = 0; i < textFieldList.size(); i++) {
                for (int j = i + 1 ; j < textFieldList.size(); j++) {

                    if (textFieldList.get(i).getText().equals(textFieldList.get(j).getText())) {

                        String headerText = "Duplicate file path: " + textFieldList.get(i).getText();
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(headerText);
                        DialogPane dialogPane = alert.getDialogPane();
                        dialogPane.getStylesheets().add("darktheme.css");
                        alert.showAndWait();
                        return;

                    }

                }
            }

            for (TextField fileName : textFieldList) {
                String filePath = fileName.getText();
                if (!files.contains(filePath)) {
                    String[] filePathSplit = filePath.split("\\.");
                    if (!filePathSplit[filePathSplit.length - 1].equals("csv") &&
                            !filePathSplit[filePathSplit.length - 1].equals("tsv")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        String header = filePath + " is not a .csv or .tsv file.";
                        alert.setHeaderText(header);
                        DialogPane dialogPane = alert.getDialogPane();
                        dialogPane.getStylesheets().add("darktheme.css");
                        alert.showAndWait();
                        return;
                    }
                    files.add(filePath);
                }
                //System.out.println(filePath);
            }

            for (String file : files) {
                File temp = new File(file);
                if (!temp.exists()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    String header = temp.getAbsolutePath() + " does not exist";
                    alert.setHeaderText(header);
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStylesheets().add("darktheme.css");
                    alert.showAndWait();
                    files.remove(file);
                    return;
                }
            }

            MainController mainController = MainController.getInstance();
            mainController.initializeData(files);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FinalizeInput.fxml"));
            Parent pane = (Parent) fxmlLoader.load();
            ((FinalizeInputController) fxmlLoader.getController()).setStage(stage);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            resize();
            ((FinalizeInputController) fxmlLoader.getController()).initialize(mainController.getData());

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Cannot submit before selecting file path.");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add("darktheme.css");
            alert.showAndWait();
        }

    }

    /**
     * When the add input button is pressed new textfields with select file, clear, and remove buttons are created and
     * added to the GUI.
     * @param actionEvent
     */
    public void addInput(ActionEvent actionEvent)
    {
        HBox newHBox = new HBox();

        // Create dynamic components
        Label newLabel = new Label("File Path : ");
        Button newFileButton = new Button("Select File");
        newFileButton.setTooltip(new Tooltip("Press this to select an input file."));
        Button newClearButton = new Button("Clear");
        newClearButton.setTooltip(new Tooltip("Press this to clear the file path."));
        Button newRemoveButton = new Button("X");
        newRemoveButton.setTooltip(new Tooltip("Press this to remove this text field."));
        TextField newTextField = new TextField();
        newTextField.clear();
        textFieldList.add(newTextField);

        if (textFieldList.size() == 40) {
            addInputButton.setDisable(true);
        }


        // Set actions of new buttons
        newFileButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("CSV Files", "*.csv")
                        , new FileChooser.ExtensionFilter("TSV Files", "*.tsv")
                );

                fileChooser.setTitle("Open Input File");
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    newTextField.setText(file.getAbsolutePath());
                }
            }
        });
        newClearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //textFieldList.remove(newTextField);
                newTextField.clear();
                resize();
            }
        });
        newRemoveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addInputButton.setDisable(false);
                textFieldList.remove(newTextField);
                vBoxMid.getChildren().remove(newHBox);
                resize();
            }

        });

        // Add new elements to hBox
        newHBox.getChildren().add(newLabel);
        newHBox.getChildren().add(newTextField);
        newHBox.getChildren().add(newFileButton);
        newHBox.getChildren().add(newClearButton);
        newHBox.getChildren().add(newRemoveButton);
        newHBox.setSpacing(15);
        Insets insets = new Insets(10,10,10,10);
        newHBox.setPadding(insets);


        vBoxMid.getChildren().add(newHBox);
        resize();
    }

    /**
     * Initializes the text fields with the files chosen when the back button in the FinalizeInput GUI is pressed
     */
    public void initializeTextFields() {
        MainController mainController = MainController.getInstance();
        this.files = mainController.getFiles();
        System.out.println(this.files);
        for (int i = 0; i < files.size(); i++) {//String file : files) {
            if (i > 0) {
                this.addInputButton.fire();
            }
            textFieldList.get(textFieldList.size() - 1).setText(files.get(i));
        }
    }

    /**
     * Returns the list of file paths as Strings
     * @return returns a List of String Objects that represent file paths for the input files
     */
    private List<String> getFiles() {
        return this.files;
    }

    /**
     * Resizes the stage to the size of the scene
     */
    public void resize()
    {
        this.stage.sizeToScene();
    }
}
