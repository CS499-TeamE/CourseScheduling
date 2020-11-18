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


    public void initialize() {
        this.addInputButton.setTooltip(new Tooltip("Press this to add a new field for an input file."));
        this.submitButton.setTooltip(new Tooltip("Pres this to submit all selected files for scheduling."));
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

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

    public void submit(ActionEvent actionEvent) throws IOException {
        Boolean missingFiles = false;
        for(TextField textField : textFieldList) {
            if (textField.getText().equals("")) {
                missingFiles = true;
                break;
            }
        }
        if (!(textFieldList.isEmpty()) && !missingFiles) {

            for (TextField fileName : textFieldList) {
                String filePath = fileName.getText();
                if (!files.contains(filePath)) {
                    files.add(filePath);
                }
                //System.out.println(filePath);
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

        if (textFieldList.size() == 10) {
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
                textFieldList.remove(newTextField);
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

    public void initializeTextFields() {
        MainController mainController = MainController.getInstance();
        this.files = mainController.getFiles();
        System.out.println(this.files);
        for (String file : files) {
            this.addInputButton.fire();
            textFieldList.get(textFieldList.size() - 1).setText(file);
        }
    }

    private List<String> getFiles() {
        return this.files;
    }

    public void resize()
    {
        this.stage.sizeToScene();
    }
}
