package com.example.proiectfis;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;
import java.nio.file.OpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {



        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
            stage.setTitle("Register");
            stage.setScene(new Scene(root, 745, 497));
            stage.show();
         }catch (IOException exception){
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE,null,exception);
          }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
