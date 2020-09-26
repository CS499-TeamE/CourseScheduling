import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ChooseFileController {
    @FXML private Button clearButton;
    @FXML private AnchorPane anchorPane;
    @FXML private Button selectFileButton;
    @FXML private TextField textField;
    @FXML private Button submitButton;
    @FXML private HBox hBox;

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
        if ( !(textField.getText().isBlank()) ) {
            String filePath = textField.getText();
            System.out.println(filePath);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Cannot submit before selecting file path.");
            alert.showAndWait();
        }
    }

    public void clear(ActionEvent actionEvent) {
        textField.clear();
    }
}
