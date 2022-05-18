package com.example.proiectfis;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

public class HelloApplication extends Application {
///TODO NU nu ne intereseaza clasa asta
    @FXML
    private Stage primaryStage;
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
        primaryStage.setTitle("Register");
        primaryStage.setScene(new Scene(root,600,400));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}