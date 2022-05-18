package com.example.proiectfis;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {


        ///TODO la linia 20 e eroarea de nu ne lasa sa rulam programul
        //try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
            stage.setTitle("Register");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
//        /}catch (NullPointerException exception){
         //   System.out.println("Nu merge");
//        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
