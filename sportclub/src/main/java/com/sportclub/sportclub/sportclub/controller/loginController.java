package com.sportclub.sportclub.sportclub.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class loginController {
    public TextField txtUserName;
    public TextField txtPassword;
    public Button btnSignIn;
    public AnchorPane SignPageAnchorPane;

    public void SignInOnAction(ActionEvent actionEvent) throws IOException {

        String username = txtUserName.getText();
        String password = txtPassword.getText();

        //System.out.println("SigninOnAction");

        if (username.equals("") && password.equals("")||username.equals("saman") && password.equals("4228")){
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/MainLayout.fxml"));
            SignPageAnchorPane.getChildren().clear();
            SignPageAnchorPane.getChildren().add(load);

        }else {
            //System.out.println("Invalid Username or Password");
            new Alert(Alert.AlertType.ERROR,"something wrong !").show();
        }

    }

}
