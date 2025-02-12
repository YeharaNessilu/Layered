package com.sportclub.sportclub.sportclub;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static void main(String[] args) {

        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Hello World!");

        Parent load = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Scene scene = new Scene(load,1046,749);
        stage.setTitle("Sport Club");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/01_SportClub_Logo.png")));
        stage.setScene(scene);
        stage.setResizable(false);


        stage.show();
    }




}