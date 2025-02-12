package com.sportclub.sportclub.sportclub.controller;


import com.sportclub.sportclub.sportclub.Bo.BOFactory;
import com.sportclub.sportclub.sportclub.Bo.Custom.PaymentBO;
import com.sportclub.sportclub.sportclub.dao.custom.impl.CoachDaoImpl;
import com.sportclub.sportclub.sportclub.dao.custom.impl.PaymentDaoImpl;
import com.sportclub.sportclub.sportclub.dao.custom.impl.memberdaoImpl;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    public AnchorPane ancMainLayout;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblNumMembers;

    @FXML
    private Label lblNumCoach;

    @FXML
    private Label lblNumMoney;



    void countMembers(){
        memberdaoImpl memberModel = new memberdaoImpl();

        try {
            int count=memberModel.countCustomers();
            lblNumMembers.setText(String.valueOf(count));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    void countCoachs(){
        CoachDaoImpl CoachModel = new CoachDaoImpl();

        try {
            int count= CoachModel.countCoach();
            lblNumCoach.setText(String.valueOf(count));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    void totalIncome(){
        PaymentDaoImpl PaymentModel = new PaymentDaoImpl();



        try {
            int count= PaymentModel.countIncome();
            lblNumMoney.setText(String.valueOf("Rs "+count));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    private void timenow(){
        Thread thread =new Thread(() ->{
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
            SimpleDateFormat sdf1 = new SimpleDateFormat("MMMM,  dd, yyyy");
            while (true){
                try{
                    Thread.sleep(1000);

                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println(e);
                }
                final String timenow = sdf.format(new Date());
                String timenow1 = sdf1.format(new Date());

                Platform.runLater(() ->{
                    lblTime.setText(timenow);
                    lblDate.setText(timenow1);
                });
            }
        });
        thread.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        timenow();
        countMembers();
        countCoachs();
        totalIncome();



    }
}
