package com.pontoeletronico;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Scene mainScene;

    @Override
    public void start(Stage primaryStage) {
        loadLoginView(primaryStage);
    }

    public static void loadLoginView(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/pontoeletronico/Login.fxml"));
            VBox vbox = loader.load();
            mainScene = new Scene(vbox);
            stage.setScene(mainScene);
            stage.setTitle("Ponto Eletrônico - Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Scene getMainScene() {
        return mainScene;
    }

    public static MainViewController getMainViewController() {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/pontoeletronico/MainView.fxml"));
        try {
            VBox vbox = loader.load();
            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void loadMainView(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
            VBox vbox = loader.load();
            mainScene.setRoot(vbox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void restartApplication() {
        Stage stage = (Stage) mainScene.getWindow();
        loadLoginView(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
