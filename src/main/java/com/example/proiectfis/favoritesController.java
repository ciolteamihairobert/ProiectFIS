package com.example.proiectfis;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class favoritesController implements Initializable {

    @FXML
    private TableView tv_disp;

    @FXML
    private TableColumn col_fav_echipa;

    @FXML
    private Button button_back;

    ObservableList<favo> list = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection con = null;
        ResultSet rs = null;
        try {
            con = DBUtils.getConnection();
            rs = con.createStatement().executeQuery("SELECT * FROM favorites");
            list.removeAll(list);
            while (rs.next()) {
                list.add(new favo(rs.getString("echipa")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_fav_echipa.setCellValueFactory(new PropertyValueFactory<>("echipa"));
        tv_disp.setItems(list);


        button_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/homePage.fxml", "Home", null, null);
            }
        });
    }



}
