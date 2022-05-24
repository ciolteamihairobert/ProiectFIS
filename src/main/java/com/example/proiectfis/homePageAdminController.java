package com.example.proiectfis;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class homePageAdminController implements Initializable {
    @FXML
    private TableView tv_disp;

    @FXML
    private TableColumn <?,String>id_pariu;

    @FXML
    private TableColumn<bet, String> status_col_id;

    @FXML
    private TableColumn <?,String>bet_col_id;

    @FXML
    private TableColumn <?,String>echipa1_id;
    @FXML
    private TableColumn <?,String>echipa2_id;
    @FXML
    private TableColumn <?,String>data_id;

    @FXML
    private Button button_BetAnsView;

    @FXML
    private Button button_display;

    @FXML
    private Button button_add;

    @FXML
    private TextField id_t1;

    @FXML
    private TextField id_t2;

    @FXML
    private TextField tf_data;

    @FXML
    private Button button_answer;

    @FXML
    private TextField answer;

    @FXML
    private TextField tf_ans_team1;
    @FXML
    private TextField tf_ans_team2;

    ObservableList<tabele> oblist = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_display.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Connection con = null;
                ResultSet rs = null;
                oblist.removeAll(oblist);
                try {
                    con = DBUtils.getConnection();
                    rs = con.createStatement().executeQuery("SELECT * FROM games");

                    while (rs.next()) {
                        oblist.add(new tabele(rs.getString("echipa1"), rs.getString("echipa2"), rs.getString("data")));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                echipa1_id.setCellValueFactory(new PropertyValueFactory<>("echipa1"));
                echipa2_id.setCellValueFactory(new PropertyValueFactory<>("echipa2"));
                data_id.setCellValueFactory(new PropertyValueFactory<>("data"));

                tv_disp.setItems(oblist);

            }
        });

        button_add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.addGame(event,id_t1.getText(),id_t2.getText(),tf_data.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Game added succesfully!");
                alert.show();
            }
        });

        ObservableList<bet> list = FXCollections.observableArrayList();
        button_BetAnsView.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Connection con = null;
                ResultSet rs = null;
                oblist.removeAll(oblist);
                try {
                    con = DBUtils.getConnection();
                    rs = con.createStatement().executeQuery("SELECT * FROM cbet");
                    while (rs.next()) {
                        list.add(new bet(rs.getString("echipa1"), rs.getString("echipa2"), rs.getString("data"),rs.getString("pariu"),rs.getString("amount"),rs.getString("status")));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                echipa1_id.setCellValueFactory(new PropertyValueFactory<>("echipa1"));
                echipa2_id.setCellValueFactory(new PropertyValueFactory<>("echipa2"));
                data_id.setCellValueFactory(new PropertyValueFactory<>("data"));
                id_pariu.setCellValueFactory(new PropertyValueFactory<>("pariu"));
                bet_col_id.setCellValueFactory(new PropertyValueFactory<>("amount"));
                status_col_id.setCellValueFactory(new PropertyValueFactory<bet, String>("status"));
                tv_disp.setItems(list);
            }
        });

       button_answer.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               try {
                   DBUtils.Update(answer.getText(),tf_ans_team1.getText(),tf_ans_team2.getText());
               } catch (SQLException e) {
                   throw new RuntimeException(e);
               }
           }
       });


    }
}