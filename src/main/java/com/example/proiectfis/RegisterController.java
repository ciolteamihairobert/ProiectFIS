package com.example.proiectfis;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.sql.*;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private Button button_register;

    @FXML
    private Button button_log_in;

    @FXML
    private RadioButton rb_admin;

    @FXML
    private RadioButton rb_player;

    @FXML
    private TextField tf_username;

    @FXML
    private TextField tf_password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ToggleGroup toggleGroup = new ToggleGroup();
       rb_admin.setToggleGroup(toggleGroup);
       rb_player.setToggleGroup(toggleGroup);

        rb_admin.setSelected(true);

        button_register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String toggleName = ((RadioButton) toggleGroup.getSelectedToggle()).getText();//ia admin/player de pe buton

                if(!tf_username.getText().trim().isEmpty() &&  !tf_password.getText().trim().isEmpty()){
                    DBUtils.registerUser(actionEvent, tf_username.getText(),tf_password.getText(), toggleName );
                }else{
                    System.out.println("Please fill in all information");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information");
                    alert.show();
                }
            }
        });

        button_log_in.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent , "/loggedin.fxml","log in!",null,null);//log in reusit _ > pagina de meniu
            }
        });



    }
}
