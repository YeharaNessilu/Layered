package com.sportclub.sportclub.sportclub.controller;

import com.sportclub.sportclub.sportclub.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainLayoutController implements Initializable{

        public AnchorPane ancMainLayout;
        public Button btnMember;
        public Button btnCoach;
        public Button btnDashBoard;
        public Button btnMemberDetail;
        public Button btnLogOut;
        public Button btnPayment;



        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            System.out.println("Main Layout Loaded");
            navigateTo("/view/Dashboard.fxml");



        }

        public void MemberOnAction(ActionEvent actionEvent) {
            navigateTo("/view/memberView.fxml");

        }

        public void CoachOnAction(ActionEvent actionEvent) {
        navigateTo("/view/Coach.fxml");

        }

        public void SportOnAction(ActionEvent actionEvent) {
            navigateTo("/view/Sports.fxml");

        }

        public void DashBoardOnAction(ActionEvent actionEvent) {
            navigateTo("/view/Dashboard.fxml");

        }

        public void MemberDetailOnAction(ActionEvent actionEvent) {
            navigateTo("/view/MemberDetail.fxml");

        }

        public void paymentOnAction(ActionEvent actionEvent) {
            navigateTo("/view/Payment.fxml");

        }


        public void LogOutOnAction(ActionEvent actionEvent) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
                AnchorPane root = loader.load();

                Scene scene = new Scene(root);

                Stage stage = (Stage) btnLogOut.getScene().getWindow();

                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error loading the login screen.").show();
            }


        }





    public void navigateTo(String fxmlPath) {
        try {
            ancMainLayout.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

            load.prefWidthProperty().bind(ancMainLayout.widthProperty());
            load.prefHeightProperty().bind(ancMainLayout.heightProperty());

           ancMainLayout.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }





}

