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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

 public class homePageAdminController implements Initializable {
    @FXML
    private TableView<tabele> tabelView1;


    @FXML
    private TableColumn<tabele, String> echipa1_id;
    @FXML
    private TableColumn<tabele, String> echipa2_id;
    @FXML
    private TableColumn<tabele, String> data_id;

    @FXML
    private TextField answer;

    @FXML
            private TextField id_t1;
     @FXML
     private TextField id_t2;

     @FXML
     private Button button_add;
     @FXML
     private Button button_answer;

     @FXML
     private TextField tf_data;

@FXML
        private Button button_display;


     ObservableList<tabele> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        button_display.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Connection con = null;
                ResultSet rs = null;
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


                tabelView1.setItems(oblist);

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



    }
}