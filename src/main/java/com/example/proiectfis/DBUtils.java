package com.example.proiectfis;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.sql.*;


import java.io.IOException;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Node;

public class DBUtils {

    public static void changeScene(ActionEvent event, String fxmlFIle, String title, String username, String role){

        Parent root=null;

        if(username != null && role !=null){

            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFIle));
                root = loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInformation(username,role);

            }catch(IOException e){
                e.printStackTrace();
            }
        }else{
            try{
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFIle));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600 , 400));
        stage.show();
    }

    public static void registerUser(ActionEvent event, String username, String password, String role){
        Connection connection = null;
        PreparedStatement psInsert= null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql//localho:3306/schemafis","root","admin" );
            psCheckUserExists = connection.prepareStatement("SELECT * FROM useres WHERE username = ?");
            psCheckUserExists.setString(1,username);
            resultSet = psCheckUserExists.executeQuery();

            if(resultSet.isBeforeFirst()){
                System.out.println("User already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username");
                alert.show();
            }else{
                psInsert = connection.prepareStatement("INSERT INTO users(username,password,role VALUES (?,?,?)");
                psInsert.setString(1,username);
                psInsert.setString(2,password);
                psInsert.setString(3,role);
                psInsert.executeUpdate();

            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(resultSet != null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psCheckUserExists != null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psInsert != null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void logInUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement preapredStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql//localho:3306/schemafis","root","admin" );
            preapredStatement = connection.prepareStatement("SELECT password,role FROM users WHERE username = ?");
            preapredStatement.setString(1,username);

            if(resultSet.isBeforeFirst()){
                System.out.println("User not found in the databse");
                Alert alert= new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect!");
                alert.show();
            }else{
                while(resultSet.next()){
                    String retrievedPassword = resultSet.getString("password");
                    String retrievedRole = resultSet.getString("role");
                    if(retrievedPassword.equals(password)){
                        changeScene(event, "loggedin.fxml","Welcome!",username, retrievedRole);//legatura meniu
                    }else{
                        System.out.println("Password did not match!");
                        Alert alert= new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Provided credentials are incorrect!");
                        alert.show();
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(resultSet != null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(preapredStatement != null){
                try{
                    preapredStatement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
