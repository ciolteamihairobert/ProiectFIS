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

public class homePageController implements Initializable {
    @FXML
    private TableView tv_disp;

    @FXML
    private TableColumn<?, String> echipa1_id;
    @FXML
    private TableColumn<?, String> echipa2_id;
    @FXML
    private TableColumn<?, String> data_id;

    @FXML
    private TableColumn<?, String> bet_col_id;

    @FXML
    private TableColumn<?, String> status_col_id;

    @FXML
    private TableColumn<?, String> id_pariu;

    @FXML
    private Button button_disp;

    @FXML
    private Button button_bet;

    @FXML
    private TextField t1_id;

    @FXML
    private TextField t2_id;

    @FXML
    private TextField tf_bet;

    @FXML
    private TextField tf_data;

    @FXML
    private TextField tf_amount;

    @FXML
    private TextField tf_idg_favorites;

    @FXML
    private Button button_favorites;

    @FXML
    private Button button_display_favorites;

    @FXML
    private Button button_see_past;
    ObservableList<tabele> oblist = FXCollections.observableArrayList();
    ObservableList<bet> list = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            button_disp.setOnAction(new EventHandler<ActionEvent>() {


                @Override
                public void handle(ActionEvent actionEvent) {
                    Connection con = null;
                    ResultSet rs = null;
                    oblist.removeAll(oblist);
                    list.removeAll(list);
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
                    //bet_col_id.setCellValueFactory(new PropertyValueFactory<>("game_id"));
                    tv_disp.setItems(oblist);
                }
            });

            button_bet.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if(t1_id.getText().isEmpty()||t2_id.getText().isEmpty()||tf_data.getText().isEmpty()||tf_bet.getText().isEmpty()||tf_amount.getText().isEmpty()){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Please enter your bet!");
                        alert.show();
                    }else {
                        DBUtils.addGameToBet(actionEvent, t1_id.getText(), t2_id.getText(), tf_data.getText(), tf_bet.getText(), tf_amount.getText(), "in validation...");
                    }
                    Connection con = null;
                    ResultSet rs = null;
                    oblist.removeAll(oblist);
                    list.removeAll(list);
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
                    status_col_id.setCellValueFactory(new PropertyValueFactory<>("status"));
                    tv_disp.setItems(list);
                }
            });

            button_display_favorites.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    DBUtils.changeScene(event, "/favorites.fxml", "Favorites", null, null);
                }
            });

            button_favorites.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(tf_idg_favorites.getText().isEmpty()){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Please enter your favorite!");
                        alert.show();
                    }else {
                        DBUtils.addFav(event, tf_idg_favorites.getText());
                        tf_idg_favorites.setText("");
                    }
                }
            });

            button_see_past.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Connection con = null;
                    ResultSet rs = null;
                    oblist.removeAll(oblist);
                    list.removeAll(list);
                    try {
                        con = DBUtils.getConnection();
                        rs = con.createStatement().executeQuery("SELECT * FROM cbet WHERE status='Accepted' OR status='Denied'");
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
                    status_col_id.setCellValueFactory(new PropertyValueFactory<>("status"));
                    tv_disp.setItems(list);
                }
            });
    }
}
