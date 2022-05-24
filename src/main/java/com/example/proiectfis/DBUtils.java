package com.example.proiectfis;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class DBUtils {



    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, String role) {
        Parent root = null;
        if (username != null && role != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInformation(username, role);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 745, 497));
        stage.show();
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }

    private static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", "");
    }

    public static void registerUser(ActionEvent event, String username, String password, String role) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/schemafis", "root", "admin");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("User already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username.");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO users (username, password, role) VALUES (?, ?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, encodePassword(username, password));
                psInsert.setString(3, role);
                psInsert.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Register succesfully done!");
                alert.show();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckUserExists != null) {
                try {
                    psCheckUserExists.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public static void logInUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/schemafis", "root", "admin");
            preparedStatement = connection.prepareStatement("SELECT password, role FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in the database!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    String retrievedRole = resultSet.getString("role");
                    if (retrievedPassword.equals(encodePassword(username, password))) {
                        if (retrievedRole.equals("Player")) {
                            changeScene(event, "/homePage.fxml", "Home", null, null);
                        } else {
                            changeScene(event, "/homePageAdmin.fxml", "Home", null, null);
                        }
                    } else {
                        System.out.println("Passwords did not match!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The provided credentials are incorrect!");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/schemafis", "root", "admin");
        return connection;
    }

    public static void addGame(ActionEvent event, String echipa1, String echipa2, String data)  {
        Connection connection=null;
        PreparedStatement psInsert=null;
        PreparedStatement psCheckItemExists=null;
        ResultSet resultSet=null;
        try{
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/schemafis", "root", "admin");
            psCheckItemExists = connection.prepareStatement("SELECT * FROM games WHERE echipa1 = ?");
            psCheckItemExists.setString(1, echipa1);
            resultSet=psCheckItemExists.executeQuery();

            if(resultSet.isBeforeFirst()){
                System.out.println("Game already exists!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("This game is already added");
                alert.show();
            }else{
                psInsert = connection.prepareStatement("INSERT INTO games (echipa1, echipa2, data) VALUES (?, ?, ?)");
                psInsert.setString(1,echipa1);
                psInsert.setString(2,echipa2);
                psInsert.setString(3,data);
                psInsert.executeUpdate();

            }
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (resultSet!=null){
                try{
                    resultSet.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if (psCheckItemExists!=null){
                try{
                    psCheckItemExists.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if (psInsert!=null){
                try{
                    psInsert.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection!=null){
                try{
                    connection.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public static void addGameToBet(ActionEvent event,String echipa1, String echipa2,String data,String amount,String status)  {
        Connection connection=null;
        PreparedStatement psInsert=null;
        PreparedStatement psCheckItemExists=null;
        ResultSet resultSet=null;
        try{
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/schemafis", "root", "admin");
            psCheckItemExists = connection.prepareStatement("SELECT * FROM cbet WHERE echipa1= ? AND echipa2=?");
            psCheckItemExists.setString(1,echipa1);
            psCheckItemExists.setString(2,echipa2);
            resultSet=psCheckItemExists.executeQuery();

            if(resultSet.isBeforeFirst()){
                System.out.println("Bet already exist!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("This bet is already placed!");
                alert.show();
            }else{
                psInsert = connection.prepareStatement("INSERT INTO cbet (echipa1, echipa2,data,amount,status) VALUES (?, ?, ?, ?, ?)");
                psInsert.setString(1,echipa1);
                psInsert.setString(2,echipa2);
                psInsert.setString(3,data);
                psInsert.setString(4,amount);
                psInsert.setString(5,status);
                psInsert.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (resultSet!=null){
                try{
                    resultSet.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if (psCheckItemExists!=null){
                try{
                    psCheckItemExists.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if (psInsert!=null){
                try{
                    psInsert.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection!=null){
                try{
                    connection.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}

