package com.example.proiectfis;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {

    @FXML
    private Button button_login;

    @FXML
    private TextField tf_username;

    @FXML
    private TextField tf_password;

    @FXML
    private RadioButton rb_admin;
    @FXML
    private RadioButton rb_player;

    public void initialize(URL location , ResourceBundle resources) {

        ToggleGroup toggleGroup = new ToggleGroup();
        rb_admin.setToggleGroup(toggleGroup);
        rb_player.setToggleGroup(toggleGroup);

        rb_admin.setSelected(true);
        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.logInUser(actionEvent, tf_username.getText(), tf_password.getText());

            }
        });

        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent, "/homePage.fxml", "log in!", null, null);//log in reusit _ > pagina de meniu
            }
        });
    }
    public void setUserInformation(String username,String password){
        System.out.println("merge");
    }
}
