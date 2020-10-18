package model;

import controller.ChooseFileController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.ChooseFileController;

import java.util.ArrayList;

public class ChooseFile extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ChooseFile.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        ((ChooseFileController) fxmlLoader.getController()).setStage(primaryStage);
        primaryStage.setResizable(true);
        primaryStage.centerOnScreen();
        primaryStage.sizeToScene();
        primaryStage.setTitle("Open Input File");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}