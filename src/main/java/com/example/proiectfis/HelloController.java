package com.example.proiectfis;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    ///TODO NU nu ne intereseaza clasa asta
    @FXML

    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}