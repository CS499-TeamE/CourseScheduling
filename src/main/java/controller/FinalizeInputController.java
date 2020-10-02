package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class FinalizeInputController
{
    private Stage stage;
    @FXML private ComboBox<String> preferenceCombo;

    public FinalizeInputController(Stage stage)
    {
        this.stage = stage;
    }


//    public void initialize()
//    {
//        preferenceCombo.getItems().add("N/A");
//        preferenceCombo.getItems().add("All Mon-Wed classes");
//        preferenceCombo.getItems().add("All Tues-Thurs classes");
//        preferenceCombo.getItems().add("Morning classes only");
//        preferenceCombo.getItems().add("Afternoon classes only");
//        preferenceCombo.getItems().add("Evening classes only");;
//    }


}
