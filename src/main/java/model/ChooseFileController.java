package CourseScheduling.src.main.java.model;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Deprecated
@SuppressWarnings("unchecked")
public class ChooseFileController {
    @FXML private Button clearButton;
    @FXML private AnchorPane anchorPane;
    @FXML private BorderPane borderPane;
    @FXML private Button selectFileButton;
    @FXML private TextField textField;
    @FXML private Button submitButton;
    @FXML private Button addInputButton;
    @FXML private HBox hBoxMid;
    @FXML private HBox hBoxTop;
    @FXML private VBox vBoxMid;


    private List<TextField> textFieldList = new ArrayList<>();

    private Stage stage;

    public void setStage(Stage stage) {
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

    public void submit(ActionEvent actionEvent) {
        if ( !(textFieldList.isEmpty()) )
        {
            for(TextField fileName : textFieldList)
            {
                String filePath = fileName.getText();
                System.out.println(filePath);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Cannot submit before selecting file path.");
            alert.showAndWait();
        }
    }
    public void addInput(ActionEvent actionEvent)
    {
        HBox newHBox = new HBox();

        // Create dynamic components
        Label newLabel = new Label("File Path : ");
        Button newFileButton = new Button("Select File");
        Button newClearButton = new Button("Clear");
        Button newRemoveButton = new Button("X");
        TextField newTextField = new TextField();


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
                    textFieldList.add(newTextField);
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

    public void resize()
    {
        this.stage.sizeToScene();
    }
}
